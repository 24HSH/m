package com.wideka.mall.trade.dao;

import java.util.List;

import com.wideka.mall.api.trade.bo.Order;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderDao {

	/**
	 * 
	 * @param order
	 * @return
	 */
	Long createOrder4Item(Order order);

	/**
	 * 
	 * @param order
	 * @return
	 */
	int createOrder4Cart(Order order);

	/**
	 * 
	 * @param order
	 * @return
	 */
	List<Order> getOrderList(Order order);

	/**
	 * 
	 * @param order
	 * @return
	 */
	Order getOrder(Order order);

}
