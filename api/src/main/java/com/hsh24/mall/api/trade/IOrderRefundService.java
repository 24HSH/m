package com.hsh24.mall.api.trade;

import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IOrderRefundService {

	/**
	 * 
	 * @param tradeNo
	 * @param refundNo
	 * @param orderId
	 * @param orderRefund
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrderRefund(String tradeNo, String refundNo, Long orderId, OrderRefund orderRefund,
		String modifyUser);

}
