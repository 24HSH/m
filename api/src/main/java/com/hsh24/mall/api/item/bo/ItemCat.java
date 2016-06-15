package com.hsh24.mall.api.item.bo;

import com.hsh24.mall.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemCat extends SearchInfo {

	private static final long serialVersionUID = -1284635101288984045L;

	private Long itemCId;

	private Long parentItemCId;

	private String itemCName;

	public Long getItemCId() {
		return itemCId;
	}

	public void setItemCId(Long itemCId) {
		this.itemCId = itemCId;
	}

	public Long getParentItemCId() {
		return parentItemCId;
	}

	public void setParentItemCId(Long parentItemCId) {
		this.parentItemCId = parentItemCId;
	}

	public String getItemCName() {
		return itemCName;
	}

	public void setItemCName(String itemCName) {
		this.itemCName = itemCName;
	}

}
