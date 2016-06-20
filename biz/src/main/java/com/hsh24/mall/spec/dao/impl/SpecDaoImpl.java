package com.hsh24.mall.spec.dao.impl;

import java.util.List;

import com.hsh24.mall.api.spec.bo.SpecCat;
import com.hsh24.mall.api.spec.bo.SpecItem;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.spec.dao.ISpecDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class SpecDaoImpl extends BaseDaoImpl implements ISpecDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SpecCat> getSpecCatList(SpecCat specCat) {
		return (List<SpecCat>) getSqlMapClientTemplate().queryForList("spec.cat.getSpecCatList", specCat);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpecItem> getSpecItemList(SpecItem specItem) {
		return (List<SpecItem>) getSqlMapClientTemplate().queryForList("spec.item.getSpecItemList", specItem);
	}

}
