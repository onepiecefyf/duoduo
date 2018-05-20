package com.shop.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.common.pojo.SearchSolrResult;
import com.shop.search.service.SearchItemService;
/**
 * Solr查找Controller
 * @author feng
 *
 */
@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;
	
	@Value("${ITEM_ROWS}")
	private String ITEM_ROWS;
	
	@RequestMapping("/search")
	public String searItemSolr(
			@RequestParam(name = "q",required = true)String query,
			@RequestParam(name = "page",defaultValue = "1")Integer page,Model model) throws Exception{

		// query含有中文需要转码
		query = new String(query.getBytes("iso8859-1"), "utf-8");
		// 调用接口执行查询
		SearchSolrResult result = null;
		try {
			result = searchItemService.listItemSearch(query,page,new Integer(ITEM_ROWS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("totalPages", result.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("query", query);
		
		return "search";
		
	}
	
}
