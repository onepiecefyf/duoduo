package com.shop.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.pojo.EasyUIResult;
import com.shop.common.pojo.TaotaoResult;
import com.shop.content.service.ContentService;
import com.shop.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	/**
	 * 
	 * @Description  展示内容列表
	 * @param page
	 * @param rows
	 * @return
	 * @Author feng
	 * @Date 2017年12月4日 下午9:56:45
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIResult getContentList(Integer page,Integer rows,long categoryId){
		EasyUIResult result = contentService.getContentList( page, rows,categoryId);
		return result;
	}
	/**
	 * 
	 * @Description 保存内容 
	 * @param tbContent
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午3:37:43
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent tbContent){
		TaotaoResult result = contentService.insertContent(tbContent);
		return result;
		
	}
	/**
	 * 
	 * @Description 更新内容 
	 * @param tbContent
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午3:37:43
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent tbContent){
		TaotaoResult result = contentService.updateContent(tbContent);
		return result;
		
	}
	/**
	 * 
	 * @Description 删除内容 
	 * @param tbContent
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午3:37:43
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids){
		TaotaoResult result = contentService.deleteContent(ids);
		return result;
		
	}
	
	
}
