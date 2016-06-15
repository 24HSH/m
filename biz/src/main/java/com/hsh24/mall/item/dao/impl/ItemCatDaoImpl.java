package com.hsh24.mall.item.dao.impl;

import java.util.List;

import com.hsh24.mall.api.item.bo.ItemCat;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.item.dao.IItemCatDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemCatDaoImpl extends BaseDaoImpl implements IItemCatDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemCat> getItemCatList(ItemCat itemCat) {
		return (List<ItemCat>) getSqlMapClientTemplate().queryForList("item.cat.getItemCatList", itemCat);
	}

}
