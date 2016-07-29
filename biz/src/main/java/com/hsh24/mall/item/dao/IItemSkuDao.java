package com.hsh24.mall.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	 * @param skuList
	 * @param modifyUser
	 * @return
	 */
	int updateItemSku(@Param("skuList") List<ItemSku> skuList, @Param("modifyUser") String modifyUser);

}
