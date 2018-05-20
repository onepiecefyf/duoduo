package com.shop.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.pojo.EasyUIResult;
import com.shop.common.pojo.TaotaoResult;
import com.shop.pojo.TbItem;
import com.shop.service.ItemService;

/**
 * @Descriotion 商品Controller 
 * @author feng
 *
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 
	 * @Description  商品列表
	 * @param page
	 * @param rows
	 * @return
	 * @Author feng
	 * @Date 2017年11月16日 下午7:49:21
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIResult getItemList(Integer page,Integer rows){
		
		EasyUIResult itemList = itemService.getItemList(page, rows);
		
		return itemList;
	}
	
	/**
	 * 
	 * @Description  保存商品
	 * @param tbItem
	 * @param desc
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午6:53:21
	 */
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult saveProduct(TbItem tbItem,String desc){
		
		TaotaoResult result = itemService.addItem(tbItem,desc);
		
		return result;
	}
	
}
