package com.hsh24.mall.api.trade;

import java.util.List;

import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 交易接口.
 * 
 * @author JiakunXu
 * 
 */
public interface ITradeService {

	String CREATE = "create";

	/**
	 * 临时.
	 */
	String CHECK = "check";

	/**
	 * 待付款.
	 */
	String TO_PAY = "topay";

	/**
	 * 已付款.
	 */
	String TO_SEND = "tosend";

	/**
	 * 已发货.
	 */
	String SEND = "send";

	/**
	 * 已签收.
	 */
	String SIGN = "sign";

	String FEEDBACK = "feedback";

	String FEEDBACKED = "feedbacked";

	/**
	 * 申请退款.
	 */
	String TO_REFUND = "torefund";

	/**
	 * 已退款.
	 */
	String REFUND = "refund";

	String CANCEL = "cancel";

	/**
	 * 立即购买.
	 * 
	 * @param userId
	 * @param shopId
	 * @param itemId
	 * @param skuId
	 * @param quantity
	 * @return
	 */
	BooleanResult createTrade(Long userId, Long shopId, String itemId, String skuId, String quantity);

	/**
	 * 买家下单交易.
	 * 
	 * @param userId
	 *            必填.
	 * @param shopId
	 *            必填.
	 * @param cartId
	 *            购物车id.
	 * @return
	 */
	BooleanResult createTrade(Long userId, Long shopId, String[] cartId);

	/**
	 * 买家查询某店铺交易.
	 * 
	 * @param userId
	 *            必填.
	 * @param shopId
	 *            必填.
	 * @param type
	 * @return
	 */
	int getTradeCount(Long userId, Long shopId, String[] type);

	/**
	 * 买家查询某店铺交易.
	 * 
	 * @param userId
	 *            必填.
	 * @param shopId
	 *            必填.
	 * @param type
	 * @return
	 */
	List<Trade> getTradeList(Long userId, Long shopId, String[] type);

	/**
	 * 买家查看订单.
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	Trade getTrade(Long userId, Long shopId, String tradeNo);

	/**
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeNo
	 * @param trade
	 * @return
	 */
	BooleanResult updateReceiver(Long userId, Long shopId, String tradeNo, Trade trade);

	/**
	 * 取消订单.
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	BooleanResult cancelTrade(Long userId, Long shopId, String tradeNo);

	/**
	 * 临时订单 -> 待付款订单.
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeNo
	 * @param remark
	 * @return
	 */
	BooleanResult topayTrade(Long userId, Long shopId, String tradeNo, String remark);

	/**
	 * 获取某一交易某一订单明细信息(用于退款).
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeNo
	 * @param orderId
	 * @return
	 */
	Trade getOrder(Long userId, Long shopId, String tradeNo, String orderId);

	/**
	 * 退款申请.
	 * 
	 * @param shopId
	 * @param tradeNo
	 * @param refundNo
	 * @param orderId
	 * @param orderRefund
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createOrderRefund(Long shopId, String tradeNo, String refundNo, Long orderId,
		OrderRefund orderRefund, String modifyUser);

	/**
	 * 确认收货.
	 * 
	 * @param userId
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	BooleanResult signTrade(Long userId, Long shopId, String tradeNo);

}
