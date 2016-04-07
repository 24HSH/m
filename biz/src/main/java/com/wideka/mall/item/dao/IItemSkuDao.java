package com.wideka.mall.item.dao;

import java.util.List;

import com.wideka.mall.api.item.bo.ItemSku;

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

}
