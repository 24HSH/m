package com.hsh24.mall.item.dao;

import java.util.List;

import com.hsh24.mall.api.item.bo.Item;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemDao {

	/**
	 * 
	 * @param item
	 * @return
	 */
	int getItemCount(Item item);

	/**
	 * 
	 * @param item
	 * @return
	 */
	List<Item> getItemList(Item item);

	/**
	 * 
	 * @param item
	 * @return
	 */
	Item getItem(Item item);

	/**
	 * 
	 * @param shopId
	 * @param itemId
	 * @param modifyUser
	 * @return
	 */
	int updateItem(Long shopId, String[] itemId, String modifyUser);

	/**
	 * 
	 * @param shopId
	 * @param itemList
	 * @param modifyUser
	 * @return
	 */
	int updateItem(Long shopId, List<Item> itemList, String modifyUser);

}
