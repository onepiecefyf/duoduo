package com.shop.search.mapper;

import java.util.List;

import com.shop.common.pojo.SearchItem;

public interface SearchItemMapper {

	List<SearchItem> getItemList() throws Exception;
	
	SearchItem getItemById(long itemId);
	
}
