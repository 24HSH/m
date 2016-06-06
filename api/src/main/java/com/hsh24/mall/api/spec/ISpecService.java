package com.hsh24.mall.api.spec;

import java.util.List;

import com.hsh24.mall.api.spec.bo.SpecCat;
import com.hsh24.mall.api.spec.bo.SpecItem;

/**
 * 店铺规格类目接口.
 * 
 * @author JiakunXu
 * 
 */
public interface ISpecService {

	/**
	 * 获取店铺规格类目信息.
	 * 
	 * @param shopId
	 * @param specCId
	 * @return
	 */
	List<SpecCat> getSpecCatList(Long shopId, String[] specCId);

	/**
	 * 获取店铺规格类目明细信息.
	 * 
	 * @param shopId
	 * @param specItemId
	 * @return
	 */
	List<SpecItem> getSpecItemList(Long shopId, String[] specItemId);

}
