package com.shop.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.common.pojo.EasyUITreeNode;
import com.shop.common.pojo.TaotaoResult;
import com.shop.content.service.ContentCategoryService;

/**
 * @description 内容分类描述
 * @author feng
 *
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService categoryService;
	/**
	 * 
	 * @Description  展示内容菜单
	 * @param id
	 * @return
	 * @Author feng
	 * @Date 2017年12月4日 下午8:20:55
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(
			@RequestParam(defaultValue="0")long id){
		
		List<EasyUITreeNode> contentCategoryList = categoryService.getContentCategoryList(id);
		
		return contentCategoryList;
		
	}
	
	/**
	 * 
	 * @Description  创建内容节点
	 * @param parentId
	 * @param name
	 * @return
	 * @Author feng
	 * @Date 2017年12月4日 下午8:24:28
	 */
	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult createCategory(long parentId,String name){
		
		TaotaoResult taotaoResult = categoryService.createCategory(parentId,name);
		
		return taotaoResult;
		
	}
	/**
	 * 
	 * @Description  更新内容节点
	 * @param parentId
	 * @param name
	 * @return
	 * @Author feng
	 * @Date 2017年12月4日 下午8:35:50
	 */
	@RequestMapping("/content/category/update")
	@ResponseBody
	public TaotaoResult updateCategory(long parentId,String name){
		
		TaotaoResult taotaoResult = categoryService.updateCategory(parentId,name);
		
		return taotaoResult;
		
	}
	
	/**
	 * 
	 * @Description 删除内容节点
	 * @param id
	 * @return
	 * @Author feng
	 * @Date 2017年12月4日 下午9:16:45
	 */
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public TaotaoResult deleteCategory(long id){
		
		TaotaoResult taotaoResult = categoryService.deleteCategory(id);
		return taotaoResult;
	}
	
	
}
