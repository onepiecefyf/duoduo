package com.shop.search.service;

import com.shop.common.pojo.SearchItem;
import com.shop.common.pojo.SearchSolrResult;
import com.shop.common.pojo.TaotaoResult;

public interface SearchItemService {

	/**
	 * 
	 * @Description 查询所有需要导入solr的数据 
	 * @return TaotaoResult
	 * @throws Exception
	 * @Author feng
	 * @Date 2018年3月17日 下午5:43:52
	 */
	TaotaoResult getItemList() throws Exception;

	/**
	 * 
	 * @Description solr查询所有的商品 
	 * @param query
	 * @param page
	 * @param iTEM_ROWS
	 * @return SearResult
	 * @Author feng
	 * @Date 2018年3月19日 下午11:05:37
	 */
	SearchSolrResult listItemSearch(String query, Integer page, Integer iTEM_ROWS) throws Exception; 
	
	/**
	 * 
	 * @Description 根据商品的id获得商品
	 * @param itemId
	 * @return SearItem
	 * @Author feng
	 * @Date 2018年3月26日 下午9:45:23
	 */
	SearchItem getItemById(long itemId);
	
	/**
	 * 
	 * @Description  添加商品
	 * @param searchItem
	 * @return TaotaoResult
	 * @Author feng
	 * @Date 2018年3月26日 下午9:47:38
	 */
	TaotaoResult addSearItem(long itemId) throws Exception;
}
