package com.shop.common.pojo;

import java.io.Serializable;
/**
 * @description EasyUITree实体描述
 * @author feng
 *
 */
public class EasyUITreeNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id; // 节点ID
	
	private String text; // 节点名称
	
	private String state; // 节点状态  close  open
	
	
	
	public EasyUITreeNode() {
		super();
	}
	public EasyUITreeNode(long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}

