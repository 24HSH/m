package com.hsh24.mall.api.shop;

import java.util.List;

import com.hsh24.mall.api.shop.bo.Shop;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IShopService {

	/**
	 * 
	 * @param shop
	 * @return
	 */
	List<Shop> getShopList(Shop shop);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShop(String shopId);

}
