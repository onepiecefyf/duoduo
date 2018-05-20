package com.shop.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.pojo.EasyUIResult;
import com.shop.common.pojo.TaotaoResult;
import com.shop.common.utils.JsonUtils;
import com.shop.content.service.ContentService;
import com.shop.content.util.jedis.JedisClient;
import com.shop.mapper.TbContentMapper;
import com.shop.pojo.TbContent;
import com.shop.pojo.TbContentExample;
import com.shop.pojo.TbContentExample.Criteria;
/**
 * 内容实现
 * @author feng
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	// 注入缓存接口
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	@Value("${CONTENT_EXPIRE_TIME}")
	private Integer CONTENT_EXPIRE_TIME;
	
	@Override
	public EasyUIResult getContentList(Integer pageNum, Integer pageSize,long categoryId) {
		
		// 创建返回的结果
		EasyUIResult result = new EasyUIResult();
		
		// 先从缓存中查找，减少服务器的压力
		try {
			String listContentS = jedisClient.hget(CONTENT_KEY, categoryId + "");
			// 如果查找到缓存数据 直接返回
			if (StringUtils.isNotBlank(listContentS)){
				List<TbContent> listContent = JsonUtils.jsonToList(listContentS, TbContent.class);
				
				result.setRows(listContent);
				
				PageInfo<TbContent> pageInfo = new PageInfo<>(listContent);
				
				// 设置记录总数
				result.setTotal(pageInfo.getTotal());
				
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 分页查询
		PageHelper.startPage(pageNum, pageSize);
		
		//查询所有
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		// 添加缓存
		try {
			jedisClient.hset(CONTENT_KEY, categoryId + "",JsonUtils.objectToJson(list));
			// 设置缓存的过期时间
			jedisClient.expire(CONTENT_KEY, CONTENT_EXPIRE_TIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result.setRows(list);
		
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		
		// 设置记录总数
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public TaotaoResult insertContent(TbContent tbContent) {
		
		tbContent.setCreated(new Date());// 创建时间
		tbContent.setUpdated(new Date());// 更新时间
		
		// 插入内容数据
		contentMapper.insertSelective(tbContent);
		
		// 缓存同步
		jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContent(TbContent tbContent) {
		tbContent.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(tbContent);
		// 缓存同步
		jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteContent(String ids) {
		// 拆分数组 ,
		String[] idArray = ids.split(",");
		if (idArray != null && idArray.length > 0) {
			for (String idS : idArray) {
				// 查找是否存在该内容
				TbContent tbContent = contentMapper.selectByPrimaryKey(new Long(idS));
				if (tbContent != null) {
					// 缓存同步
					jedisClient.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
				}
				// 转换为Integer 查询内容信息
				int conut = contentMapper.deleteByPrimaryKey(new Long(idS));
				if (conut <= 0) {
					TaotaoResult.build(400, "删除内容ID" + idS + "失败");
				}
			}	
		}
		return TaotaoResult.ok();
	}

}
