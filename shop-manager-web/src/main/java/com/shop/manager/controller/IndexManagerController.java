package com.shop.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.pojo.TaotaoResult;
import com.shop.search.service.SearchItemService;

/**
 * 索引库管理
 * @author feng
 *
 */
@Controller
public class IndexManagerController {

	@Autowired
	private SearchItemService searchItemService;
	
	/**
	 * 
	 * @Description 导入索引 
	 * @return
	 * @throws Exception
	 * @Author feng
	 * @Date 2018年3月17日 下午7:02:43
	 */
	@RequestMapping("/index/import")
	@ResponseBody
	public TaotaoResult importIndexDocument() throws Exception{
		
		TaotaoResult result = searchItemService.getItemList();
		return result;
	}
	
	
}
