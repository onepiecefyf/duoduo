package com.shop.item.controller;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.pojo.Item;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;
import com.shop.service.ItemService;

/**
 * 展示商品详情的Controller
 * @author feng
 *
 */
@Controller
public class ItemDetailController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Destination topicDestination;
	
	/**
	 * 
	 * @Description 展示商品详情
	 * @param itemId
	 * @param model
	 * @return
	 * @Author feng
	 * @Date 2018年3月29日 下午9:12:41
	 */
	@RequestMapping("/item/{itemId}")
	public String showItemDetail(@PathVariable long itemId,Model model){
		
		// 调用接口查询商品信息
		TbItem tbItem = itemService.getItemById(itemId);
		
		// 包装商品信息
		Item item = new Item(tbItem);
		
		TbItemDesc itemDesc = itemService.geTbItemDesc(itemId);
		
		model.addAttribute("item", item);
		
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";
	}
	
	
	
	
	
}
