package com.shop.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.pojo.EasyUITreeNode;
import com.shop.mapper.TbItemCatMapper;
import com.shop.pojo.TbItemCat;
import com.shop.pojo.TbItemCatExample;
import com.shop.pojo.TbItemCatExample.Criteria;
import com.shop.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> itemCats = itemCatMapper.selectByExample(example);
		// 转换成EasyTreeNode列表
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : itemCats) {
			EasyUITreeNode treeNode = new EasyUITreeNode();
			
			treeNode.setId(tbItemCat.getId()); // 父节点
			treeNode.setState(tbItemCat.getIsParent() ? "closed" : "open");// 节点状态 closed表示是父节点   open 表示不是父节点
			treeNode.setText(tbItemCat.getName()); // 节点名称
			
			resultList.add(treeNode); // 添加到节点列表
		}
		return resultList;
	}

}
