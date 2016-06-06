package com.hsh24.mall.item.dao.impl;

import java.util.List;

import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.item.dao.IItemDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemDaoImpl extends BaseDaoImpl implements IItemDao {

	@Override
	public int getItemCount(Item item) {
		return (Integer) getSqlMapClientTemplate().queryForObject("item.getItemCount", item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> getItemList(Item item) {
		return (List<Item>) getSqlMapClientTemplate().queryForList("item.getItemList", item);
	}

	@Override
	public Item getItem(Item item) {
		return (Item) getSqlMapClientTemplate().queryForObject("item.getItem", item);
	}

}
