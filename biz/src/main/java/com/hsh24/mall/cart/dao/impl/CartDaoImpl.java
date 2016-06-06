package com.hsh24.mall.cart.dao.impl;

import java.util.List;

import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.cart.dao.ICartDao;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CartDaoImpl extends BaseDaoImpl implements ICartDao {

	@Override
	public Long createCart(Cart cart) {
		return (Long) getSqlMapClientTemplate().insert("cart.createCart", cart);
	}

	@Override
	public int checkCart(Cart cart) {
		return getSqlMapClientTemplate().update("cart.checkCart", cart);
	}

	@Override
	public int getCartCount(Cart cart) {
		return (Integer) getSqlMapClientTemplate().queryForObject("cart.getCartCount", cart);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cart> getCartList(Cart cart) {
		return (List<Cart>) getSqlMapClientTemplate().queryForList("cart.getCartList", cart);
	}

	@Override
	public int updateCart(Cart cart) {
		return getSqlMapClientTemplate().update("cart.updateCart", cart);
	}

	@Override
	public int updateQuantity(Cart cart) {
		return getSqlMapClientTemplate().update("cart.updateQuantity", cart);
	}

	@Override
	public Cart getCartStats(Cart cart) {
		return (Cart) getSqlMapClientTemplate().queryForObject("cart.getCartStats", cart);
	}

}
