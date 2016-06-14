package com.hsh24.mall.shop.dao.impl;

import java.util.List;

import com.hsh24.mall.api.shop.bo.Shop;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.shop.dao.IShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ShopDaoImpl extends BaseDaoImpl implements IShopDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Shop> getShopList(Shop shop) {
		return (List<Shop>) getSqlMapClientTemplate().queryForList("shop.getShopList", shop);
	}

	@Override
	public Shop getShop(Shop shop) {
		return (Shop) getSqlMapClientTemplate().queryForObject("shop.getShop", shop);
	}

}
