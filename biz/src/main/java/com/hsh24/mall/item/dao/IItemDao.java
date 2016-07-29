package com.hsh24.mall.item.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	int updateItem1(@Param("shopId") Long shopId, @Param("itemId") String[] itemId,
		@Param("modifyUser") String modifyUser);

	/**
	 * 
	 * @param item
	 * @return
	 */
	int updateItem2(Item item);

}
