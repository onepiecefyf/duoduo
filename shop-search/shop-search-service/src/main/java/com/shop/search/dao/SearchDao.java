package com.shop.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shop.common.pojo.SearchItem;
import com.shop.common.pojo.SearchSolrResult;

/**
 * Solr  DAO数据查询
 * @author feng
 *
 */
@Repository
public class SearchDao {

	// 注入SolrServer对象
	@Autowired
	private SolrServer solrServer;
	
	public SearchSolrResult searchItem(SolrQuery query) throws Exception{
		
		List<SearchItem> listSearchItem = new ArrayList<>();
		
		// 调用solrServer执行查询
		QueryResponse queryResponse = solrServer.query(query);
		
		// 获取商品列表
		SolrDocumentList documentList = queryResponse.getResults();
		
		// 遍历商品列表
		for (SolrDocument solrDocument : documentList) {
			// 使用商品对象封装数据
			SearchItem searchItem = new SearchItem();
			searchItem.setId((String)solrDocument.get("id"));
			
			// 给标题设置 高亮显示
			String item_title = null;
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id") + "").get("item_title");
			// 有高亮的时候显示
			if (list != null && list.size() > 0) {
				item_title = list.get(0);
			} else {
			// 没有高亮的时候显示、
				item_title = (String) solrDocument.get("item_title");
			}
			searchItem.setItem_title(item_title);
			
			searchItem.setItem_title(solrDocument.get("item_title") + "");
			searchItem.setItem_sell_point(solrDocument.get("item_sell_point") + "");
			searchItem.setItem_price((Long) solrDocument.get("item_price"));
			searchItem.setItem_image(solrDocument.get("item_image") + "");
			searchItem.setItem_desc(solrDocument.get("item_category_name") + "");
			searchItem.setItem_category_name(solrDocument.get("item_desc") + "");
			// 将商品添加到商品列表
			listSearchItem.add(searchItem);
		}
		// 封装返回页面的数据
		SearchSolrResult searResult = new SearchSolrResult();
		searResult.setItemList(listSearchItem);
		searResult.setRecordCount(documentList.getNumFound());
		
		return searResult;
		
	}
	
}
