package com.hsh24.mall.api.shop;

import com.hsh24.mall.api.shop.bo.Shop;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IShopService {

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	Shop getShop(Long shopId);

}
