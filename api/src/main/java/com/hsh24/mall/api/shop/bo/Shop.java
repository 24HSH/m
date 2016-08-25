package com.hsh24.mall.api.shop.bo;

import java.util.List;

import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Shop extends SearchInfo {

	private static final long serialVersionUID = 6554392084181055320L;

	private Long shopId;

	private String shopName;

	/**
	 * 区域编号.
	 */
	private Long regionId;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	private Long blockId;

	private List<Cart> cartList;

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

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

}
