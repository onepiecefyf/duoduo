package com.shop.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.pojo.TaotaoResult;
import com.shop.common.utils.CookieUtils;
import com.shop.common.utils.JsonUtils;
import com.shop.pojo.TbItem;
import com.shop.service.ItemService;

/**
 * 购物车实现
 * @author feng
 *
 */
@Controller
public class CartController {

	@Autowired
	private ItemService itemService;
	
	@Value("${TT_CART}")
	private String TT_CART;
	@Value("${TT_CART_EXPIRE}")
	private Integer TT_CART_EXPIRE;
	
	/**
	 * 
	 * @Description 添加商品到购物车 
	 * @param itemId
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 上午10:41:50
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId,Integer num,
			HttpServletRequest request,HttpServletResponse response){
		
		// 先从Cookie中查找商品
		List<TbItem> itemList = getCookieList(request);
 
		// 如果购物车为空，就去查询商品添加到购物车
		if (itemList == null || itemList.size() == 0) {
			TbItem tbItem = itemService.getItemById(itemId);
			String imageS = tbItem.getImage();
			if (StringUtils.isNotBlank(imageS)) {
				String[] image = imageS.split(",");
				tbItem.setImage(image[0]);
			}
			tbItem.setNum(num);
			
			itemList.add(tbItem);
			
			// 将商品添加到购物车
			CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(itemList),TT_CART_EXPIRE,true);
			
			return "cartSuccess";
		}
		// 购物车不为空的处理
		for (TbItem tbItem : itemList) {
			if (tbItem.getId() == itemId.longValue()) {
				tbItem.setNum(tbItem.getNum() +  num);
				
				itemList.add(tbItem);
				
				// 将商品添加到购物车
				CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(itemList),TT_CART_EXPIRE,true);
				
				break;
			}
		}
		
		return "cartSuccess";
	}

	/**
	 * 
	 * @Description 从购物中获得商品
	 * @param request
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 上午10:47:46
	 */
	private List<TbItem> getCookieList(HttpServletRequest request) {
		// 商品的信息可能含有中文，需要编码
		String itemJson = CookieUtils.getCookieValue(request, TT_CART, true);
		if (StringUtils.isNotBlank(itemJson)) {
			// 将json转化为商品信息
			List<TbItem> tbItemList = JsonUtils.jsonToList(itemJson, TbItem.class);
			return tbItemList;
		}
		return new ArrayList<>();
	}
	
	/**
	 * 
	 * @Description  展示购物车列表
	 * @param request
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 上午11:11:08
	 */
	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request){
		List<TbItem> cartList = getCookieList(request);
		request.setAttribute("cartList", cartList);
		return "cart";
	}
	
	/**
	 * 
	 * @Description 更改购物车中商品数量
	 * @param itemId
	 * @param num
	 * @param request
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 上午11:18:11
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
			HttpServletRequest request,HttpServletResponse response){
		
		List<TbItem> cookieList = getCookieList(request);
		if (cookieList != null && cookieList.size() > 0) {
			for (TbItem tbItem : cookieList) {
				if (tbItem.getId() == itemId.longValue()) {
					tbItem.setNum(num);
					cookieList.add(tbItem);
					break;// 匹配到商品，逻辑处理之后，直接跳出循环
				}
			}
			// 将商品添加到购物车
			CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cookieList),TT_CART_EXPIRE,true);
		}
		return TaotaoResult.ok();
		
	}
	
	/**
	 * 
	 * @Description 删除购物车商品
	 * @param itemId
	 * @param request
	 * @return
	 * @Author feng
	 * @Date 2018年4月5日 上午11:33:02
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		
		// 获得购物车商品
		List<TbItem> cookieList = getCookieList(request);
		if (cookieList != null && cookieList.size() > 0) {
			for (TbItem tbItem : cookieList) {
				if (tbItem.getId() == itemId.longValue()) {
					cookieList.remove(tbItem);
					break; // 匹配到商品，逻辑处理之后，直接跳出循环
				}
			}
		}
		CookieUtils.setCookie(request, response, TT_CART, JsonUtils.objectToJson(cookieList), true);
		return "redirect:/cart/cart.html";
	}
	
	
}
