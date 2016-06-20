package com.hsh24.mall.spec.dao;

import java.util.List;

import com.hsh24.mall.api.spec.bo.SpecCat;
import com.hsh24.mall.api.spec.bo.SpecItem;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISpecDao {

	/**
	 * 
	 * @param specCat
	 * @return
	 */
	List<SpecCat> getSpecCatList(SpecCat specCat);

	/**
	 * 
	 * @param specItem
	 * @return
	 */
	List<SpecItem> getSpecItemList(SpecItem specItem);

}
