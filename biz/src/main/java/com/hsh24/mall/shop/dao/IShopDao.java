package com.hsh24.mall.shop.dao;

import java.util.List;

import com.hsh24.mall.api.shop.bo.Shop;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IShopDao {

	/**
	 * 
	 * @param shop
	 * @return
	 */
	List<Shop> getShopList(Shop shop);

	/**
	 * 
	 * @param shop
	 * @return
	 */
	Shop getShop(Shop shop);

}
