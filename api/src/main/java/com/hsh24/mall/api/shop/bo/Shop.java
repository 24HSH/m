package com.hsh24.mall.api.shop.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Shop implements Serializable {

	private static final long serialVersionUID = 6554392084181055320L;

	private Long shopId;

	private String shopName;

	/**
	 * 区域编号.
	 */
	private Long regionId;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

}
