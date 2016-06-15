package com.hsh24.mall.api.item;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IItemCatService {

	/**
	 * 
	 * @return
	 */
	List<ItemCat> getItemCatList(String parentItemCId);

}
