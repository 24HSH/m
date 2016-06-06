package com.hsh24.mall.api.spec.bo;

import com.hsh24.mall.framework.bo.SearchInfo;

/**
 * 规格类目明细.
 * 
 * @author JiakunXu
 * 
 */
public class SpecItem extends SearchInfo {

	private static final long serialVersionUID = 8328358568543476567L;

	private Long specItemId;

	/**
	 * 店铺ID.
	 */
	private Long shopId;

	private Long specCId;

	private String specItemValue;

	private String state;

	private String modifyUser;

	public Long getSpecItemId() {
		return specItemId;
	}

	public void setSpecItemId(Long specItemId) {
		this.specItemId = specItemId;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getSpecCId() {
		return specCId;
	}

	public void setSpecCId(Long specCId) {
		this.specCId = specCId;
	}

	public String getSpecItemValue() {
		return specItemValue;
	}

	public void setSpecItemValue(String specItemValue) {
		this.specItemValue = specItemValue;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

}
