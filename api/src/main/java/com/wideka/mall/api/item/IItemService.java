package com.wideka.mall.api.item;

import java.util.List;
import java.util.Map;

import com.wideka.mall.api.item.bo.Item;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemService {

	/**
	 * 
	 * @param shopId
	 * @param item
	 * @return
	 */
	int getItemCount(Long shopId, Item item);

	/**
	 * 
	 * @param shopId
	 * @param item
	 * @return
	 */
	List<Item> getItemList(Long shopId, Item item);

	/**
	 * 
	 * @param shopId
	 * @param itemId
	 * @return
	 */
	Item getItem(Long shopId, String itemId);

	/**
	 * 
	 * @param shopId
	 * @param itemId
	 * @return
	 */
	Map<Long, Item> getItem(Long shopId, String[] itemId);

}
