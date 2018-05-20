package com.shop.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @description EasyUI的返回值
 * @author feng
 *
 */
public class EasyUIDataGridResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long total;// 总的记录数
	private List<?> rows;// 每页显示的记录数

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
