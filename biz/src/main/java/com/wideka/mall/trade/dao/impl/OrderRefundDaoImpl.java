package com.wideka.mall.trade.dao.impl;

import com.wideka.mall.api.trade.bo.OrderRefund;
import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.trade.dao.IOrderRefundDao;

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
