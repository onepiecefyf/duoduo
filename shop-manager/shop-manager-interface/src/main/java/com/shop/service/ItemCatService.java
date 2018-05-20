package com.shop.service;

import java.util.List;

import com.shop.common.pojo.EasyUITreeNode;

public interface ItemCatService {

	/**
	 * 
	 * @Description  查询商品类目
	 * @param parentId
	 * @return List<EasyUITreeNode>
	 * @Author feng
	 * @Date 2017年11月15日 下午8:11:13
	 */
	List<EasyUITreeNode> getItemCatList(long parentId);

}
