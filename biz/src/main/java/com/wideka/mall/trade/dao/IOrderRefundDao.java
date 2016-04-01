package com.wideka.mall.trade.dao;

import com.wideka.mall.api.trade.bo.OrderRefund;

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
	Long createOrderRefund(OrderRefund orderRefund);

}
