package com.hsh24.mall.trade.action;

import java.util.List;

import com.hsh24.mall.api.trade.ITradeService;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public class TradeAction extends BaseAction {

	private static final long serialVersionUID = -912767004509511731L;

	private ITradeService tradeService;

	private String itemId;

	private String skuId;

	private String quantity;

	/**
	 * 购物车.
	 */
	private String[] cartIds;

	/**
	 * 交易编号(第三方支付).
	 */
	private String tradeNo;

	/**
	 * 订单类型.
	 */
	private String type;

	private List<Trade> tradeList;

	private Trade trade;

	/**
	 * 订单明细编号.
	 */
	private String orderId;

	/**
	 * 创建临时订单.
	 * 
	 * @return
	 */
	public String create() {
		BooleanResult result = null;

		// 直接购买
		if (cartIds == null || cartIds.length == 0) {
			result =
				tradeService.createTrade(this.getUser().getUserId(), this.getShop().getShopId(), itemId, skuId,
					quantity);
			if (result.getResult()) {
				this.setResourceResult(result.getCode());
			}
		} else {
			result = tradeService.createTrade(this.getUser().getUserId(), this.getShop().getShopId(), cartIds);
			if (result.getResult()) {
				this.setResourceResult("添加购物车成功");
			}
		}

		if (!result.getResult()) {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 订单数据统计.
	 * 
	 * @return
	 */
	public String stats() {
		StringBuilder sb = new StringBuilder();

		Long userId = this.getUser().getUserId();
		Long shopId = this.getShop().getShopId();

		sb.append(tradeService.getTradeCount(userId, shopId, new String[] { "check", "topay" })).append("&");
		sb.append(tradeService.getTradeCount(userId, shopId, new String[] { "tosend" })).append("&");
		sb.append(tradeService.getTradeCount(userId, shopId, new String[] { "send" })).append("&");
		sb.append(tradeService.getTradeCount(userId, shopId, new String[] { "sign" }));

		this.setResourceResult(sb.toString());

		return RESOURCE_RESULT;
	}

	/**
	 * 订单列表.
	 * 
	 * @return
	 */
	public String list() {
		Long userId = this.getUser().getUserId();
		Long shopId = this.getShop().getShopId();

		// 待付款
		if ("topay".equals(type)) {
			tradeList = tradeService.getTradeList(userId, shopId, new String[] { "check", "topay" });
		} else if ("tosend".equals(type)) {
			tradeList = tradeService.getTradeList(userId, shopId, new String[] { "tosend" });
		} else if ("send".equals(type)) {
			tradeList = tradeService.getTradeList(userId, shopId, new String[] { "send" });
		} else if ("sign".equals(type)) {
			tradeList = tradeService.getTradeList(userId, shopId, new String[] { "sign" });
		} else {
			tradeList = tradeService.getTradeList(userId, shopId, null);
		}

		return SUCCESS;
	}

	/**
	 * 取消尚未付款的订单.
	 * 
	 * @return
	 */
	public String cancel() {
		BooleanResult result =
			tradeService.cancelTrade(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String detail() {
		trade = tradeService.getTrade(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo);

		return SUCCESS;
	}

	/**
	 * 申请退款.
	 * 
	 * @return
	 */
	public String refund() {
		trade = tradeService.getOrder(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo, orderId);

		return SUCCESS;
	}

	/**
	 * 确认收货.
	 * 
	 * @return
	 */
	public String sign() {
		BooleanResult result = tradeService.signTrade(this.getUser().getUserId(), this.getShop().getShopId(), tradeNo);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public ITradeService getTradeService() {
		return tradeService;
	}

	public void setTradeService(ITradeService tradeService) {
		this.tradeService = tradeService;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String[] getCartIds() {
		return cartIds;
	}

	public void setCartIds(String[] cartIds) {
		this.cartIds = cartIds;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Trade> getTradeList() {
		return tradeList;
	}

	public void setTradeList(List<Trade> tradeList) {
		this.tradeList = tradeList;
	}

	public Trade getTrade() {
		return trade;
	}

	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
