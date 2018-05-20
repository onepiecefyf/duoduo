package com.shop.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * solr搜索的结果
 * @author feng
 *
 */
public class SearchSolrResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SearchItem> itemList; // 查询商品的列表

	private Integer totalPages; // 总的页数
	
	private Long pageCount; // 对应页数
	
	private long recordCount; // 总的记录数

	public List<SearchItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	
	
}
