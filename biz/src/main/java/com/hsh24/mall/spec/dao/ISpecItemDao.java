package com.hsh24.mall.spec.dao;

import java.util.List;

import com.hsh24.mall.api.spec.bo.SpecItem;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISpecItemDao {

	/**
	 * 
	 * @param specItem
	 * @return
	 */
	List<SpecItem> getSpecItemList(SpecItem specItem);

}
