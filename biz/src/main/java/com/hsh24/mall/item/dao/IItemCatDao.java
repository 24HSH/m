package com.hsh24.mall.item.dao;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemCatDao {

	/**
	 * 
	 * @return
	 */
	List<ItemCat> getItemCatList(ItemCat itemCat);

}
