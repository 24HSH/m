package com.hsh24.mall.pay.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.pay.IPayService;
import com.hsh24.mall.api.trade.ITradeService;
import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.util.ClientUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class PayAction extends BaseAction {

	private static final long serialVersionUID = 3200362561478926494L;

	@Resource
	private IPayService payService;

	@Resource
	private ITradeService tradeService;

	/**
	 * 交易支付.
	 */
	private String tradeNo;

	private Trade trade;

	/**
	 * 给卖家留言.
	 */
	private String remark;

	/**
	 * 订单明细编号.
	 */
	private String orderId;

	/**
	 * 退款订单信息.
	 */
	private OrderRefund orderRefund;

	/**
	 * 支付首页.
	 * 
	 * @return
	 */
	public String index() {
		trade = tradeService.getTrade(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo);

		return SUCCESS;
	}

	/**
	 * 支付.
	 * 
	 * @return
	 */
	public String pay() {
		BooleanResult result =
			payService.pay(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo, remark, "wxpay",
				ClientUtil.getIpAddr(this.getServletRequest()));

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 退款.
	 * 
	 * @return
	 */
	public String refund() {
		BooleanResult result =
			payService.refund(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo, orderId, orderRefund);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderRefund getOrderRefund() {
		return orderRefund;
	}

	public void setOrderRefund(OrderRefund orderRefund) {
		this.orderRefund = orderRefund;
	}

}
