package com.wideka.mall.item.dao.impl;

import java.util.List;

import com.wideka.mall.api.item.bo.ItemSku;
import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.item.dao.IItemSkuDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemSkuDaoImpl extends BaseDaoImpl implements IItemSkuDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemSku> getItemSkuList(ItemSku sku) {
		return (List<ItemSku>) getSqlMapClientTemplate().queryForList("item.sku.getItemSkuList", sku);
	}

}
