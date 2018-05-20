package com.shop.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.pojo.SearchItem;
import com.shop.common.pojo.SearchSolrResult;
import com.shop.common.pojo.TaotaoResult;
import com.shop.search.dao.SearchDao;
import com.shop.search.mapper.SearchItemMapper;
import com.shop.search.service.SearchItemService;

@Service
public class SearchItemServiceImpl implements SearchItemService {
	
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private SearchItemMapper searchItemMapper;
	
	// 注入DAO
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public TaotaoResult getItemList() throws Exception {
		// 1 查询所有的商品数据
		List<SearchItem> listItem = searchItemMapper.getItemList();
		// 2 创建一个solrServer对象
		for (SearchItem searchItem : listItem) {
			// 3 为每一个商品创建一个SolrInputDocument对象
			SolrInputDocument document = new SolrInputDocument();
			// 4 为文档添加域
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getItem_title());
			document.addField("item_sell_point", searchItem.getItem_sell_point());
			document.addField("item_price", searchItem.getItem_price());
			document.addField("item_image", searchItem.getItem_image());
			document.addField("item_category_name", searchItem.getItem_category_name());
			document.addField("item_desc", searchItem.getItem_desc());
			// 5 像索引库中添加域
			solrServer.add(document);
		}
		// 提交
		solrServer.commit();
		// 6 返回TaoTaoResult
		return TaotaoResult.ok();
	}

	@Override
	public SearchSolrResult listItemSearch(String query, Integer page, Integer rows) throws Exception {
		
		// 实例化对象SolrQuery
		SolrQuery solrQuery = new SolrQuery();
		
		// 设置查询的条件
		solrQuery.setQuery(query);
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);
		
		// 设置默认的搜索域
		solrQuery.set("df", "item_title");
		
		// 设置高亮显示
		solrQuery.setHighlight(true); // 开启高亮显示
		solrQuery.addHighlightField("item_title"); // 设置高亮显示的域
		solrQuery.setHighlightSimplePre("<em style = \"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		
		// 调用DAO层查询数据
		SearchSolrResult result = searchDao.searchItem(solrQuery);
		// 封装返回页面的数据
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows ;
		// 说明有多余的数据
		if (recordCount % rows > 0) {
			pageCount ++ ;
		}
		result.setPageCount(pageCount);
		return result;
	}

	@Override
	public SearchItem getItemById(long itemId) {
		return searchItemMapper.getItemById(itemId);
	}

	@Override
	public TaotaoResult addSearItem(long itemId) throws Exception {
		
		// 1 查询商品是否存在
		SearchItem searchItem = getItemById(itemId);
		
		// 2 为商品创建一个SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 3  为文档添加域
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getItem_title());
		document.addField("item_sell_point", searchItem.getItem_sell_point());
		document.addField("item_price", searchItem.getItem_price());
		document.addField("item_image", searchItem.getItem_image());
		document.addField("item_category_name", searchItem.getItem_category_name());
		document.addField("item_desc", searchItem.getItem_desc());
		// 4 像索引库中添加域
		solrServer.add(document);
		// 提交
		solrServer.commit();
		// 5  返回TaoTaoResult
		return TaotaoResult.ok();
	}
	
}
