package com.shop.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description 页面跳转
 * @author feng
 *
 */
@Controller
public class PageController {

	/**
	 * 
	 * @Description  跳转到首页
	 * @return String
	 * @Author feng
	 * @Date 2017年11月14日 下午8:02:32
	 */
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	/**
	 * 
	 * @Description 页面跳转
	 * @param page
	 * @return String
	 * @Author feng
	 * @Date 2017年11月14日 下午8:02:52
	 */
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}

}
