package com.shop.sso.service.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.shop.common.conts.RegisterTypeEnum;
import com.shop.common.pojo.TaotaoResult;
import com.shop.common.utils.JsonUtils;
import com.shop.mapper.TbUserMapper;
import com.shop.pojo.TbUser;
import com.shop.pojo.TbUserExample;
import com.shop.pojo.TbUserExample.Criteria;
import com.shop.sso.jedis.JedisClient;
import com.shop.sso.service.UserService;

/**
 * 用户管理实现
 * @author feng
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${USER_INFO}")
	private String USER_INFO;
	@Value("${USER_INFO_EXPIRE}")
	private Integer USER_INFO_EXPIRE;
	
	@Override
	public TaotaoResult checkUserInfo(String param, Integer type) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		// 根据类型查找是否已经存在该用户
		if (RegisterTypeEnum.PHONE.getKey().equals(type)) {
			criteria.andPhoneEqualTo(param);
		}else if (RegisterTypeEnum.NICKNAME.getKey().equals(type)) {
			criteria.andUsernameEqualTo(param);
		}else if (RegisterTypeEnum.EMAIL.getKey().equals(type)) {
			criteria.andEmailEqualTo(param);
		}else {
			return TaotaoResult.build(400,"非法的参数");
		}
		// 没有用户信息 true
		List<TbUser> userList = userMapper.selectByExample(example);
		if (userList == null || userList.size() == 0) {
			return TaotaoResult.ok(true);
		}
		
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult register(TbUser user) {
		
		TaotaoResult result = new TaotaoResult(false);
		
		// ------------------  验证用户注册信息是否为空   --------------------------------------------------
 		if (StringUtils.isEmpty(user.getUsername())) {
			return TaotaoResult.build(400, "用户名称为空");
		}
		
		if (StringUtils.isEmpty(user.getPassword())) {
			return TaotaoResult.build(400, "用户密码为空");
		}
		
		if (StringUtils.isEmpty(user.getPhone())) {
			return TaotaoResult.build(400, "用户手机号码为空");
		}
		
		if (StringUtils.isEmpty(user.getEmail())) {
			return TaotaoResult.build(400, "用户邮箱为空");
		}
		
		// ---------------------  验证注册信息是否已被使用    ------------------------------------------------
		if (StringUtils.isNotBlank(user.getUsername())) {
			result = checkUserInfo(user.getUsername(),RegisterTypeEnum.NICKNAME.getKey());
			if (!(boolean)result.getData()) {
				return TaotaoResult.build(400, "用户名称已经被使用");
			}
		}
		
		if (StringUtils.isNotBlank(user.getPhone())) {
			result = checkUserInfo(user.getPhone(),RegisterTypeEnum.PHONE.getKey());
				if (!(boolean)result.getData()) {
					return TaotaoResult.build(400, "用户手机号码已经被使用");
			}
		}
		
		if (StringUtils.isNotBlank(user.getEmail())) {
			result = checkUserInfo(user.getEmail(),RegisterTypeEnum.EMAIL.getKey());
				if (!(boolean)result.getData()) {
					return TaotaoResult.build(400, "用户邮箱已经被使用");
			}
		}
		
		// -------------------  插入用户信息  ------------------------------------------------------------
		String md5Hex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		
		user.setCreated(new Date());
		user.setUpdated(new Date());
		user.setPassword(md5Hex);
		
		userMapper.insertSelective(user);
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult login(TbUser user) {
		
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		// 根据用户名称查询用户
		List<TbUser> userList = userMapper.selectByExample(example);
		if (userList == null || userList.size() < 0) {
			return TaotaoResult.build(400, "用户名称或者密码错误");
		}
		// 验证密码是否正确
		if (!userList.get(0).getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))) {
			return TaotaoResult.build(400, "用户名或者密码错误");
		}
		
		// 生成token
		String token = UUID.randomUUID().toString();
		
		// 把用户的信息保存在redis中
		jedisClient.set(USER_INFO + ":" + token, JsonUtils.objectToJson(user));
		// 设置redis的保存时间，根据浏览器的设置是半个小时
		jedisClient.expire(USER_INFO + ":" + token, USER_INFO_EXPIRE);
		
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult checkLogin(String token) {
		// 从redis中查找用户的信息
		String userJson = jedisClient.get(USER_INFO + ":" + token);
		TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
		// 判断是否查询到用户的信息
		if (StringUtils.isEmpty(userJson)) {
			return TaotaoResult.build(400, "用户登录已经过期，请重新登录");
		}
		// 重新设置过期时间
		jedisClient.expire(USER_INFO + ":" + token, USER_INFO_EXPIRE);
		
		return TaotaoResult.ok(user);
	}
}
