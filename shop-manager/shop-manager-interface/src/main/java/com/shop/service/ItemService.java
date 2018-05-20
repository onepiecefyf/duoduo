package com.shop.service;

import com.shop.common.pojo.EasyUIResult;
import com.shop.common.pojo.TaotaoResult;
import com.shop.pojo.Item;
import com.shop.pojo.TbItem;
import com.shop.pojo.TbItemDesc;

/**
 * 商品信息的Service
 * @author feifei
 *
 */
public interface ItemService {

	/**
	 *  获得所有的商品的信息  返回封装的分页的实体类对象  
	 * @param page  当前的页数
	 * @param rows 每页显示的记录数
	 * @return 封装分页数据的实体类对象
	 */
	public EasyUIResult getItemList(int page,int rows);

	/**
	 * 
	 * @Description  添加商品
	 * @param tbItem
	 * @param desc
	 * @return
	 * @Author feng
	 * @Date 2017年11月16日 下午7:53:10
	 */
	public TaotaoResult addItem(TbItem tbItem, String desc);
	
	/**
	 * 
	 * @Description 获得商品的详情 
	 * @param itemId
	 * @return
	 * @Author feng
	 * @Date 2018年3月28日 下午8:51:34
	 */
	TbItem getItemById(long itemId);
	
	/**
	 * 
	 * @Description  获得商品描述的详情
	 * @param itemId
	 * @return
	 * @Author feng
	 * @Date 2018年3月28日 下午8:54:01
	 */
	TbItemDesc geTbItemDesc(long itemId);
	
}
