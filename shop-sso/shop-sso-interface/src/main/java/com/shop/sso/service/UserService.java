package com.shop.sso.service;

import com.shop.common.pojo.TaotaoResult;
import com.shop.pojo.TbUser;

/**
 * 用户管理
 * @author feng
 *
 */
public interface UserService {

	/**
	 * 
	 * @Description 检查用户注册信息
	 * @param param 注册信息
	 * @param type 注册类型 1 用户名 2 电话
	 * @return TaotaoResult
	 * @Author feng
	 * @Date 2018年4月3日 下午9:57:32
	 */
	TaotaoResult checkUserInfo(String param,Integer type);

	/**
	 * 
	 * @Description 用户注册 
	 * @param user
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:33:51
	 */
	TaotaoResult register(TbUser user);

	/**
	 * 
	 * @Description  用户登录
	 * @param user
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:53:03
	 */
	TaotaoResult login(TbUser user);

	/**
	 * 
	 * @Description 检查用户登录
	 * @param token
	 * @return
	 * @Author feng
	 * @Date 2018年4月4日 下午7:56:35
	 */
	TaotaoResult checkLogin(String token);
	
}
