package com.shop.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.common.pojo.EasyUITreeNode;
import com.shop.common.pojo.TaotaoResult;
import com.shop.content.service.ContentCategoryService;
import com.shop.mapper.TbContentCategoryMapper;
import com.shop.pojo.TbContentCategory;
import com.shop.pojo.TbContentCategoryExample;
import com.shop.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理实现
 * @author feng
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		//创建查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		criteria.andStatusEqualTo(1); // 拿到的只能是处于正常状态节点
		// 执行查询
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		// 将查询结果转化的为EasyUITreeNode
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory contentCategory : list) {
			// 创建返回的节点
			EasyUITreeNode note = new EasyUITreeNode();
			note.setId(contentCategory.getId());
			note.setState(contentCategory.getIsParent() ? "closed" : "open");
			note.setText(contentCategory.getName());
			// 将节点插入集合
			resultList.add(note);
		}
		return resultList;
	}

	
	@Override
	public TaotaoResult createCategory(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setSortOrder(1);
		contentCategory.setStatus(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		contentCategory.setIsParent(false); // 新添加的节点，肯定是子节点
		contentCategory.setParentId(parentId);
		// 插入节点
		categoryMapper.insertSelective(contentCategory);
		// 查询是否存在节点
		TbContentCategory parentNode = categoryMapper.selectByPrimaryKey(parentId);
		// 不是父节点
		if (!parentNode.getIsParent()) {
			// 设置为父节点
			parentNode.setParentId(parentId);
			parentNode.setIsParent(true);
			categoryMapper.updateByPrimaryKeySelective(parentNode);
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult updateCategory(long parentId, String name) {
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setId(parentId);
		contentCategory.setName(name);
		contentCategory.setUpdated(new Date());
		// 更新节点
		categoryMapper.updateByPrimaryKeySelective(contentCategory);
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteCategory(long id) {
		
		// 查询内容
		TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
		// 如果是父节点
		if (category.getIsParent()) {
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria createCriteria = example.createCriteria();
			createCriteria.andParentIdEqualTo(category.getParentId());
			List<TbContentCategory> categories = categoryMapper.selectByExample(example);
			for (TbContentCategory tbContentCategory : categories) {
				tbContentCategory.setStatus(0); // 设置状态为非正常
				categoryMapper.updateByPrimaryKeySelective(tbContentCategory);
			}
		}else{
			category.setStatus(0); // 设置状态为非正常
			categoryMapper.updateByPrimaryKeySelective(category);
		}
		return TaotaoResult.ok(category);
	}

}
