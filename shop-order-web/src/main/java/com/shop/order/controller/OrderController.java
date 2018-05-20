package com.shop.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.common.utils.CookieUtils;
import com.shop.common.utils.JsonUtils;
import com.shop.pojo.TbItem;

/**
 * 订单Controller
 * @author feng
 *
 */
@Controller
public class OrderController {

	@Value("${TT_TOKEN}")
	private String TT_TOKEN;
	
	/**
	 * 
	 * @Description 展示订单 
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 下午3:12:11
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderList(HttpServletRequest request){
	
		// 从Cookie中获得商品的列表
		List<TbItem> itemList = getCookieList(request);
		
		request.setAttribute("cartList", itemList);
		
		return "order-cart";
		
	}
	
	/**
	 * 
	 * @Description 获得购物车的商品列表 
	 * @param request
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 下午3:11:52
	 */
	public List<TbItem> getCookieList(HttpServletRequest request){
		
		String itemListJson = CookieUtils.getCookieValue(request, TT_TOKEN);
		if (StringUtils.isNotEmpty(itemListJson)) {
			List<TbItem> itemList = JsonUtils.jsonToList(itemListJson, TbItem.class);
			return itemList;
		}
		return new ArrayList<>();
	}
	
}
