package com.hsh24.mall.api.item;

import java.util.List;
import java.util.Map;

import com.hsh24.mall.api.item.bo.ItemSku;

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
	List<ItemSku> getItemSkuList(Long shopId, String itemId);

	/**
	 * 
	 * @param shopId
	 * @param skuId
	 * @return
	 */
	Map<Long, ItemSku> getItemSku(Long shopId, String[] skuId);

}
