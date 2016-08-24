package com.hsh24.mall.pay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.cart.ICartService;
import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.IItemSkuService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.api.pay.IPayService;
import com.hsh24.mall.api.trade.IOrderService;
import com.hsh24.mall.api.trade.ITradeService;
import com.hsh24.mall.api.trade.bo.Order;
import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.api.user.IUserWeixinService;
import com.hsh24.mall.api.wxpay.IWxpayService;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.exception.ServiceException;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.DateUtil;
import com.hsh24.mall.framework.util.UUIDUtil;
import com.wideka.weixin.api.pay.bo.Refund;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class PayServiceImpl implements IPayService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(PayServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IWxpayService wxpayService;

	@Resource
	private ITradeService tradeService;

	@Resource
	private IOrderService orderService;

	@Resource
	private IItemService itemService;

	@Resource
	private IItemSkuService itemSkuService;

	@Resource
	private ICartService cartService;

	@Resource
	private IUserWeixinService userWeixinService;

	@Override
	public BooleanResult pay(final Long userId, final String tradeNo, final String remark, String payType, String ip) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易信息不能为空");
			return result;
		}

		// 验证支付方式
		if (StringUtils.isBlank(payType)
			|| (!IPayService.PAY_TYPE_ALIPAY.equals(payType) && !IPayService.PAY_TYPE_WXPAY.equals(payType))) {
			result.setCode("请重新选择支付方式");
			return result;
		}

		String openId = IPayService.PAY_TYPE_WXPAY.equals(payType) ? userWeixinService.getOpenId(userId) : null;

		// 锁定订单
		String no = tradeNo.trim();

		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_TRADE_NO + no, no,
				IMemcachedCacheService.CACHE_KEY_TRADE_NO_DEFAULT_EXP);
		} catch (ServiceException e) {
			result.setCode("当前订单已被锁定，请稍后再试");
			return result;
		}

		// 0. 查询交易订单
		Trade trade = tradeService.getTrade(userId, tradeNo);

		if (trade == null) {
			result.setCode("当前订单不存在");

			remove(no);
			return result;
		}

		// 1. 判断是否属于未付款交易订单
		String type = trade.getType();

		if (ITradeService.CANCEL.equals(type)) {
			result.setCode("当前订单超时未支付，已取消");

			remove(no);
			return result;
		}

		if (!ITradeService.CHECK.equals(type) && !ITradeService.TO_PAY.equals(type)) {
			result.setCode("当前订单已完成支付");

			remove(no);
			return result;
		}

		// 购物车信息
		String cartIds = trade.getCartId();
		final String[] cartId = StringUtils.isNotEmpty(cartIds) ? cartIds.split(",") : null;

		// 2. 临时订单
		if (ITradeService.CHECK.equals(type)) {
			// 3. 判断库存
			List<Order> orderList = orderService.getOrderList(userId, trade.getTradeId());
			if (orderList == null || orderList.size() == 0) {
				result.setCode("当前订单明细不存在");

				remove(no);
				return result;
			}

			// 存放各个商品库存数量 存在 购物相同商品 的情况
			Map<String, Integer> map = new HashMap<String, Integer>();

			for (Order order : orderList) {
				String key = order.getItemId() + "&" + order.getSkuId();
				if (!map.containsKey(key)) {
					map.put(key, order.getStock());
				}

				int quantity = order.getQuantity();
				int stock = map.get(key);
				if (quantity > stock) {
					String propertiesName = order.getPropertiesName();
					result.setCode(order.getItemName()
						+ (StringUtils.isBlank(propertiesName) ? "" : "(" + propertiesName + ")") + " 库存不足");

					remove(no);
					return result;
				}

				map.put(key, stock - quantity);
			}

			// 根据 map 组装 skuList
			// item sku 表库存
			final List<ItemSku> skuList = new ArrayList<ItemSku>();
			// item 表库存 即不存在 sku
			final List<Item> itemList = new ArrayList<Item>();
			// 用于统计 还有 sku 的商品的合计库存数
			final String[] itemId = new String[orderList.size()];
			int i = 0;

			for (Map.Entry<String, Integer> m : map.entrySet()) {
				String[] key = m.getKey().split("&");

				// if skuId is null or 0 then 商品没有规格
				if (StringUtils.isBlank(key[1]) || "0".equals(key[1])) {
					Item item = new Item();
					item.setItemId(Long.valueOf(key[0]));
					item.setStock(m.getValue());

					itemList.add(item);

					continue;
				}

				ItemSku sku = new ItemSku();
				sku.setItemId(Long.valueOf(key[0]));
				sku.setSkuId(Long.valueOf(key[1]));
				sku.setStock(m.getValue());

				skuList.add(sku);

				itemId[i++] = key[0];
			}

			// 4. 占用库存
			BooleanResult res1 = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
				public BooleanResult doInTransaction(TransactionStatus ts) {
					BooleanResult res0 = new BooleanResult();

					String modifyUser = userId.toString();

					// 4.1 占用库存
					// 4.1.1 存在 item_sku
					if (skuList != null && skuList.size() > 0) {
						// 4.1.1.1 更新 item_sku stock
						res0 = itemSkuService.updateItemSkuStock(skuList, modifyUser);
						if (!res0.getResult()) {
							ts.setRollbackOnly();

							return res0;
						}

						// 4.1.1.2 更新 还有 sku 的 item 合计库存数
						res0 = itemService.updateItemStock(itemId, modifyUser);
						if (!res0.getResult()) {
							ts.setRollbackOnly();

							return res0;
						}
					}

					// 4.1.2 不存在 item_sku
					if (itemList != null && itemList.size() > 0) {
						// 4.1.2.1 更新 item stock
						res0 = itemService.updateItemStock(itemList, modifyUser);
						if (!res0.getResult()) {
							ts.setRollbackOnly();

							return res0;
						}
					}

					// 4.2 修改交易状态 -> topay
					res0 = tradeService.topayTrade(userId, tradeNo, remark);
					if (!res0.getResult()) {
						ts.setRollbackOnly();

						return res0;
					}

					// 4.3. 修改购物车状态
					if (cartId != null && cartId.length > 0) {
						res0 = cartService.finishCart(userId, cartId);
						if (!res0.getResult()) {
							ts.setRollbackOnly();

							return res0;
						}
					}

					return res0;
				}
			});

			if (!res1.getResult()) {
				remove(no);
				return res1;
			}
		}

		// topay 状态 去 付款 ＝＝ 已经占用库存 需要检查 当前订单 是否超过了付款有效时间
		if (ITradeService.TO_PAY.equals(type)) {

		}

		if (IPayService.PAY_TYPE_ALIPAY.equals(payType)) {
			// remove(key);
			return result;
		}

		if (IPayService.PAY_TYPE_WXPAY.equals(payType)) {
			BigDecimal price = trade.getPrice().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP);
			String timeStart = DateUtil.getNowDateminStr();
			String timeExpire =
				DateUtil.datetime(DateUtil.addMinutes(new Date(), 15), DateUtil.DEFAULT_DATEFULLTIME_FORMAT);

			try {
				result.setCode(wxpayService.getBrandWCPayRequest(tradeNo, "订单号：" + tradeNo, null, null,
					Integer.parseInt(price.toString()), ip, timeStart, timeExpire, openId));
				result.setResult(true);
			} catch (ServiceException e) {
				logger.error(e);

				result.setCode(e.getMessage());
			}

			remove(no);
			return result;
		}

		result.setCode("支付类型");

		remove(no);
		return result;
	}

	@Override
	public BooleanResult refund(final Long userId, final String tradeNo, String orderId, final OrderRefund orderRefund) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(orderId)) {
			result.setCode("订单信息不能为空");
			return result;
		}

		Long id = null;
		try {
			id = Long.valueOf(orderId);
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("订单信息不正确");
			return result;
		}
		final Long ordreId = id;

		// 锁定订单
		String key = tradeNo.trim();

		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_TRADE_NO + key, key,
				IMemcachedCacheService.CACHE_KEY_TRADE_NO_DEFAULT_EXP);
		} catch (ServiceException e) {
			result.setCode("当前订单已被锁定，请稍后再试");
			return result;
		}

		// 0. 查询交易订单
		final Trade trade = tradeService.getTrade(userId, tradeNo);
		if (trade == null) {
			result.setCode("当前订单不存在");

			remove(key);
			return result;
		}

		// 1. 判断是否属于未付款交易订单
		String type = trade.getType();
		if (!ITradeService.TO_SEND.equals(type)) {
			result.setCode("当前订单尚未付款或已发货");

			remove(key);
			return result;
		}

		// 2. 退款订单编号
		final String refundNo = UUIDUtil.generate().substring(4);

		String payType = trade.getPayType();

		if (IPayService.PAY_TYPE_ALIPAY.equals(payType)) {
			// remove(key);
			return result;
		}

		if (IPayService.PAY_TYPE_WXPAY.equals(payType)) {
			result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
				public BooleanResult doInTransaction(TransactionStatus ts) {
					BooleanResult res0 =
						tradeService.createOrderRefund(trade.getTradeNo(), refundNo, ordreId, orderRefund,
							userId.toString());

					if (!res0.getResult()) {
						ts.setRollbackOnly();

						return res0;
					}

					BigDecimal price1 =
						trade.getPrice().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP);
					int fee = Integer.parseInt(price1.toString());
					BigDecimal price2 =
						orderRefund.getRefundFee().multiply(new BigDecimal("100"))
							.setScale(0, BigDecimal.ROUND_HALF_UP);
					int refundFee = Integer.parseInt(price2.toString());

					try {
						Refund refund =
							wxpayService.refund(null, null, trade.getTradeNo(), refundNo, fee, refundFee, null);

						res0.setCode("申请退款成功");
					} catch (ServiceException e) {
						ts.setRollbackOnly();

						res0.setResult(false);
						res0.setCode(e.getMessage());
						return res0;
					}

					return res0;
				}
			});

			remove(key);
			return result;
		}

		result.setCode("支付类型");

		remove(key);
		return result;
	}

	private void remove(String tradeNo) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_TRADE_NO + tradeNo);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
