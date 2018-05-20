package com.shop.service.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.common.pojo.EasyUIResult;
import com.shop.common.pojo.IDUtils;
import com.shop.common.pojo.TaotaoResult;
import com.shop.common.utils.JsonUtils;
import com.shop.item.jedis.JedisClient;
import com.shop.mapper.TbItemDescMapper;
import com.shop.mapper.TbItemMapper;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;
import com.shop.pojo.TbItemExample;
import com.shop.pojo.TbItemExample.Criteria;
import com.shop.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate; // 注入连接ACtiveMQ模板
	@Autowired
	private Destination topicDestination; // 发送消息的目的地
	
	// 注入jedis
	@Autowired
	private JedisClient jedisClient;
	
	// 注入保存redis的前缀
	@Value("${ITEM_INFO_PRE}")
	private String ITEM_INFO_PRE;
	
	@Value("${ITEM_INFO_EXPIRE}")
	private Integer ITEM_INFO_EXPIRE;
	
	@Override
	public EasyUIResult getItemList(int page, int rows) { // 分页查询
		PageHelper.startPage(page, rows);
		
		// 执行查询 查询所有
		TbItemExample example = new TbItemExample();
		List<TbItem> tbItems = itemMapper.selectByExample(example);
		
		// 创建返回结果对象
		EasyUIResult result = new EasyUIResult();
		result.setRows(tbItems);
		
		// 取得总的记录数
		PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
		result.setTotal(pageInfo.getTotal());
		
		// 返回结果
		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem tbItem, String desc) {
		// 生成商品ID
		final long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		tbItem.setStatus((byte) 1);
		Date date = new Date();
		tbItem.setCreated(date);
		tbItem.setUpdated(date);
		// 3、向商品表插入数据
		itemMapper.insert(tbItem);
		// 4、创建一个TbItemDesc对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 5、补全TbItemDesc的属性
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		// 6、向商品描述表插入数据
		itemDescMapper.insert(itemDesc);
		
		// 添加商品完成，发送ActiveMQ消息，添加索引，生成静态页面，添加缓存
		jmsTemplate.send(topicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// 创建发送消息的类型
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});
		
		// 7、TaotaoResult.ok()
		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(long itemId) {
		
		// 首先从缓存中查找
		try {
			String itemJson = jedisClient.get(ITEM_INFO_PRE + ":" + itemId + ":BASE");
			if (StringUtils.isNotBlank(itemJson)) {
				return JsonUtils.jsonToPojo(itemJson, TbItem.class);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果缓存中没有找到，再去数据库中查找
		TbItemExample example = new TbItemExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andIdEqualTo(itemId);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		if (itemList != null && itemList.size() > 0) {
			TbItem tbItem = itemList.get(0);
			// 将查找的数据添加到缓存中
			try {
				jedisClient.set(ITEM_INFO_PRE + ":" + itemId + ":BASE",JsonUtils.objectToJson(tbItem));
				// 设置过期时间
				jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":BASE", ITEM_INFO_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return tbItem;
		}
		return null;
	}

	@Override
	public TbItemDesc geTbItemDesc(long itemId) {
		// 首先从缓冲中找
		try {
			String ItemDescJson = jedisClient.get(ITEM_INFO_PRE + ":" + itemId + ":DESC");
			if (StringUtils.isNotBlank(ItemDescJson)) {
				return JsonUtils.jsonToPojo(ItemDescJson, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 如果缓存中没有的话，就从数据库中查询找
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		// 将查询到的数据添加到缓存中
		try {
			jedisClient.set(ITEM_INFO_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置缓存的有效期
			jedisClient.expire(ITEM_INFO_PRE + ":" + itemId + ":DESC", ITEM_INFO_EXPIRE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}
}
