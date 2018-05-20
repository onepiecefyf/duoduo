package com.shop.content.service;

import com.shop.common.pojo.EasyUIResult;
/**
 * 内容管理接口
 * @author feng
 *
 */
import com.shop.common.pojo.TaotaoResult;
import com.shop.pojo.TbContent;
public interface ContentService {

	/**
	 * 
	 * @Description  获取内容列表
	 * @param page
	 * @param rows
	 * @return
	 * @Author feng
	 * @Date 2017年12月4日 下午9:50:21
	 */
	EasyUIResult getContentList(Integer page, Integer rows,long categoryId);

	/**
	 * 
	 * @Description 添加内容 
	 * @param tbContent
	 * @return TaotaoResult
	 * @Author feng
	 * @Date 2018年3月17日 下午3:16:19
	 */
	TaotaoResult insertContent(TbContent tbContent);
	
	/**
	 * 
	 * @Description 更新内容
	 * @param tbContent
	 * @return TaotaoResult
	 * @Author feng
	 * @Date 2018年3月17日 下午3:20:56
	 */
	TaotaoResult updateContent(TbContent tbContent);
	/**
	 * 
	 * @Description 批量删除内容
	 * @param ids
	 * @return
	 * @Author feng
	 * @Date 2018年3月17日 下午3:27:26
	 */
	TaotaoResult deleteContent(String ids);
}
