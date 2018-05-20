package com.shop.pojo;

/**
 * @Description 封装商品规格参数的实体
 * @author feifei
 * @time 2017年5月1日 下午3:46:29
 */
public class ItemParamItem extends TbItemParamItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public ItemParamItem(){}
	
	public ItemParamItem(TbItemParamItem tbItemParamItem){
		this.setId(tbItemParamItem.getId());
		this.setItemId(tbItemParamItem.getItemId());
		this.setParamData(tbItemParamItem.getParamData());
		this.setCreated(tbItemParamItem.getCreated());
		this.setUpdated(tbItemParamItem.getUpdated());
		this.setStatus(tbItemParamItem.getStatus());
	}
	
}
