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
	List<Shop> getShopList(Long blockId);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	List<Shop> getShopList(String[] shopId);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShop(Long shopId);

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShop(String shopId);

}
