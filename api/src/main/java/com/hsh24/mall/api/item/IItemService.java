package com.hsh24.mall.api.item;

import java.util.List;
import java.util.Map;

import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.framework.bo.BooleanResult;

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

	/**
	 * 验证 itemId 和 skuId 并返回 .
	 * 
	 * @param shopId
	 * @param itemId
	 * @param skuId
	 * @return
	 */
	BooleanResult validate(Long shopId, Long itemId, Long skuId);

	/**
	 * 
	 * @param shopId
	 * @param itemId
	 * @param modifyUser
	 * @return
	 */
	BooleanResult updateItemStock(Long shopId, String[] itemId, String modifyUser);

	/**
	 * 
	 * @param shopId
	 * @param itemList
	 * @param modifyUser
	 * @return
	 */
	BooleanResult updateItemStock(Long shopId, List<Item> itemList, String modifyUser);

}
