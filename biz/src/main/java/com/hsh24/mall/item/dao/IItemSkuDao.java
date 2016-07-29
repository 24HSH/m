package com.hsh24.mall.item.dao;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemSku;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemSkuDao {

	/**
	 * 
	 * @param sku
	 * @return
	 */
	List<ItemSku> getItemSkuList(ItemSku sku);

	/**
	 * 
	 * @param itemSku
	 * @return
	 */
	int updateItemSku(ItemSku itemSku);

}
