package com.shop.content.service;

import java.util.List;

import com.shop.common.pojo.EasyUITreeNode;
import com.shop.common.pojo.TaotaoResult;
/**
 * 内容分类接口
 * @author feng
 *
 */
public interface ContentCategoryService {

	/**
	 * 
	 * @Description 获得内容列表 
	 * @param parentId
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午2:40:29
	 */
	List<EasyUITreeNode> getContentCategoryList(long parentId);

	/**
	 * 
	 * @Description 添加内容菜单 
	 * @param parentId
	 * @param name
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午2:40:49
	 */
	TaotaoResult createCategory(long parentId, String name);

	/**
	 * 
	 * @Description 更新内容菜单 
	 * @param parentId
	 * @param name
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午2:41:09
	 */
	TaotaoResult updateCategory(long parentId, String name);

	/**
	 * 
	 * @Description 更新内容菜单 
	 * @param id
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午2:41:25
	 */
	TaotaoResult deleteCategory(long id);
	
}
