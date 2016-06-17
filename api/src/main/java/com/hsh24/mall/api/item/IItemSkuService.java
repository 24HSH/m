package com.hsh24.mall.api.item;

import java.util.List;
import java.util.Map;

import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemSkuService {

	/**
	 * 
	 * @param shopId
	 * @param itemId
	 * @return
	 */
	List<ItemSku> getItemSkuList(Long itemId);

	/**
	 * 
	 * @param shopId
	 * @param skuId
	 * @return
	 */
	Map<Long, ItemSku> getItemSku(String[] skuId);

	/**
	 * 
	 * @param skuList
	 * @param modifyUser
	 * @return
	 */
	BooleanResult updateItemSkuStock(List<ItemSku> skuList, String modifyUser);
}
