package com.hsh24.mall.spec.dao;

import java.util.List;

import com.hsh24.mall.api.spec.bo.SpecCat;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface ISpecCatDao {

	/**
	 * 
	 * @param specCat
	 * @return
	 */
	List<SpecCat> getSpecCatList(SpecCat specCat);

}
