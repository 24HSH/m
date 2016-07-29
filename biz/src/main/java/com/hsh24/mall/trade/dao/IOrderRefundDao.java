package com.hsh24.mall.trade.dao;

import com.hsh24.mall.api.trade.bo.OrderRefund;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderRefundDao {

	/**
	 * 
	 * @param orderRefund
	 * @return
	 */
	int createOrderRefund(OrderRefund orderRefund);

}
