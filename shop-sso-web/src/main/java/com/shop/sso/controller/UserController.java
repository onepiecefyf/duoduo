package com.shop.sso.controller;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.pojo.TaotaoResult;
import com.shop.common.utils.CookieUtils;
import com.shop.common.utils.JsonUtils;
import com.shop.pojo.TbUser;
import com.shop.sso.service.UserService;

/**
 * 用户管理
 * @author feng
 *
 */
@Controller
public class UserController {

	@Autowired
	UserService userService;
	@Value("${TT_TOKEN}")
	private String TT_TOKEN;
	
	/**
	 * 
	 * @Description 检查用户是否已经注册 
	 * @param param
	 * @param type
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:30:23
	 */
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkUserInfo(@PathVariable("param")String param,@PathVariable("type")Integer type){
		TaotaoResult taotaoResult = userService.checkUserInfo(param, type);
		return taotaoResult;
		
	}
	
	/**
	 * 
	 * @Description 用户注册
	 * @param user
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:33:00
	 */
	@RequestMapping(value = "/user/register",method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user){
		
		TaotaoResult taotaoResult = userService.register(user);
		return taotaoResult;
	}
	/**
	 * 
	 * @Description  跳转到登录页面
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:49:46
	 */
	@RequestMapping("/user/showLogin")
	public String showLogin(){
		return "login";
	}
	
	/**
	 * 
	 * @Description 跳转到注册页面 
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:51:09
	 */
	@RequestMapping("/user/showRegister")
	public String showRegister(){
		return "register";
	}
	
	/**
	 * 
	 * @Description 用户登录 
	 * @param user
	 * @return
	 * @Author feng
	 * @Date 2018年4月3日 下午10:52:42
	 */
	@RequestMapping("/user/login")
	@ResponseBody
	public TaotaoResult login(TbUser user,
			HttpServletRequest request, HttpServletResponse response){
		TaotaoResult result = userService.login(user);
		// 将token保存在Cookie中
		String token = result.getData().toString();
		// cookie中设置跨域
		CookieUtils.setCookie(request, response, TT_TOKEN, token);
		return result;
	}
	
	/**
	 * 
	 * @Description 
	 * @return
	 * @Author feng
	 * @Date 2018年4月4日 下午7:55:10
	 */
	@RequestMapping(value = "/user/token/{token}",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String checkLogin(@PathVariable("token") String token,String callback){
		TaotaoResult taotaoResult = userService.checkLogin(token);
		// 判断是否是jsonp请求
		if (StringUtils.isNotEmpty(callback)) {
			return callback + "(" + JsonUtils.objectToJson(taotaoResult) + ")";
		}
		
		return JsonUtils.objectToJson(taotaoResult);
	}
	
	
}
