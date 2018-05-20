package com.shop.common.conts;

public enum RegisterTypeEnum {

	PHONE(1,"手机"),
	NICKNAME(2,"用户名"),
	EMAIL(3,"邮箱");
	
	
	
	private RegisterTypeEnum(Integer key, String value) {
		this.key = key;
		this.value = value;
	}
	
	private Integer key;
	private String value;
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
