package com.hsh24.mall.trade.dao.impl;

import java.util.List;

import com.hsh24.mall.api.trade.bo.Order;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.trade.dao.IOrderDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OrderDaoImpl extends BaseDaoImpl implements IOrderDao {

	@Override
	public Long createOrder4Item(Order order) {
		return (Long) getSqlMapClientTemplate().insert("trade.order.createOrder4Item", order);
	}

	@Override
	public int createOrder4Cart(Order order) {
		return getSqlMapClientTemplate().update("trade.order.createOrder4Cart", order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrderList(Order order) {
		return (List<Order>) getSqlMapClientTemplate().queryForList("trade.order.getOrderList", order);
	}

	@Override
	public Order getOrder(Order order) {
		return (Order) getSqlMapClientTemplate().queryForObject("trade.order.getOrder", order);
	}

}
