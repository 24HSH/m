package com.hsh24.mall.trade.dao.impl;

import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.trade.dao.IOrderRefundDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OrderRefundDaoImpl extends BaseDaoImpl implements IOrderRefundDao {

	@Override
	public Long createOrderRefund(OrderRefund orderRefund) {
		return (Long) getSqlMapClientTemplate().insert("trade.order.refund.createOrderRefund", orderRefund);
	}

}
