package com.shop.pojo;

/**
 * 展示商品的信息实体
 * @author feng
 *
 */
public class Item extends TbItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Item() {
		super();
	}
	
	public Item(TbItem tbItem){
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setCreated(tbItem.getCreated());
		this.setId(tbItem.getId());
		this.setImage(tbItem.getImage());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
	}

	// 获得图片的方法
	public String[] getImages(){
		String imageNew = this.getImage();
		if (imageNew != null && !"".equals(imageNew)) {
			String[] images = imageNew.split(",");
			return images;
		}
		return null;
	}

}
