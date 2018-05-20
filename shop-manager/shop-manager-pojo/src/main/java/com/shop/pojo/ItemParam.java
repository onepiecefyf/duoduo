/**
 * @Description TODO
 * @time 2017年5月1日 下午2:48:38
 * @author feifei
 */
package com.shop.pojo;

/**
 * @Description TODO
 * @author feifei
 * @time 2017年5月1日 下午2:48:38
 */
public class ItemParam extends TbItemParam{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemCatName;

    public String getItemCatName() {
		return itemCatName;
	}
   

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}


	public ItemParam(TbItemParam tbItemParam){
		this.setId(tbItemParam.getId());
		this.setItemCatId(tbItemParam.getItemCatId());
		this.setParamData(tbItemParam.getParamData());
		this.setUpdated(tbItemParam.getUpdated());
		this.setCreated(tbItemParam.getCreated());
		this.setStatus(tbItemParam.getStatus());
	}
	
	public ItemParam (){}
	
}
