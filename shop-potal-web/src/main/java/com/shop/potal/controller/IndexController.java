package com.shop.potal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description 商城的首页 
 * @author feng
 *
 */
@Controller
public class IndexController {

	/**
	 * 
	 * @Description  展示商城的首页
	 * @return String
	 * @Author feng
	 * @Date 2017年11月23日 下午7:39:57
	 */
	@RequestMapping("/index")
	public String showIndex(){
		return "index";
	}
	
}
