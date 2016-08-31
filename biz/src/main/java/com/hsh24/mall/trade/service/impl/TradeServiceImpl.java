package com.hsh24.mall.trade.service.impl;

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

import com.hsh24.mall.api.address.bo.Address;
import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.cart.ICartService;
import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.IItemSkuService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
import com.hsh24.mall.api.trade.IOrderRefundService;
import com.hsh24.mall.api.trade.IOrderService;
import com.hsh24.mall.api.trade.ITradeService;
import com.hsh24.mall.api.trade.bo.Order;
import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.api.user.IUserAddressService;
import com.hsh24.mall.api.user.bo.UserAddress;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.exception.ServiceException;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.DateUtil;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.framework.util.UUIDUtil;
import com.hsh24.mall.trade.dao.ITradeDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class TradeServiceImpl implements ITradeService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(TradeServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserAddressService userAddressService;

	@Resource
	private IOrderService orderService;

	@Resource
	private IOrderRefundService orderRefundService;

	@Resource
	private ICartService cartService;

	@Resource
	private IItemService itemService;

	@Resource
	private IItemSkuService itemSkuService;

	@Resource
	private IShopService shopService;

	@Resource
	private ITradeDao tradeDao;

	@Override
	public BooleanResult createTrade(final Long userId, final Long shopId, final String itemId, final String skuId,
		final String quantity, final Address address) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(itemId)) {
			result.setCode("商品信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(skuId)) {
			result.setCode("商品SKU信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(quantity)) {
			result.setCode("数量信息不能为空");
			return result;
		}
		try {
			Integer.valueOf(quantity);
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("数量信息不正确");
			return result;
		}

		if (address == null) {
			result.setCode("地址信息不能为空");
			return result;
		}

		try {
			result = itemService.validate(shopId, Long.valueOf(itemId), Long.valueOf(skuId));
		} catch (NumberFormatException e) {
			logger.error(e);

			result.setCode("商品，SKU信息不存在");
			return result;
		}

		if (!result.getResult()) {
			return result;
		}

		final BigDecimal price = new BigDecimal(result.getCode()).multiply(new BigDecimal(quantity));

		// 获取默认收货地址
		final UserAddress userAddress = userAddressService.getUserAddress(userId, address.getAddId());

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				// 1. 创建交易
				Trade trade = new Trade();
				trade.setUserId(userId);
				trade.setShopId(shopId);
				// 交易价格
				trade.setTradePrice(price);
				// 积分兑换
				trade.setTradePoints(BigDecimal.ZERO);
				trade.setCouponPrice(BigDecimal.ZERO);
				trade.setPostage(BigDecimal.ZERO);
				trade.setChange(BigDecimal.ZERO);
				// 买家结算
				trade.setType(ITradeService.CHECK);

				// 收货地址
				if (userAddress != null) {
					trade.setReceiverName(userAddress.getContactName());
					trade.setReceiverProvince(userAddress.getProvince());
					trade.setReceiverCity(userAddress.getCity());
					trade.setReceiverArea(userAddress.getArea());
					trade.setReceiverBackCode(userAddress.getBackCode());
					trade.setReceiverAddress(userAddress.getAddress());
					trade.setReceiverZip(userAddress.getPostalCode());
					trade.setReceiverTel(userAddress.getTel());
				} else {
					trade.setReceiverProvince(address.getProvince());
					trade.setReceiverCity(address.getCity());
					trade.setReceiverArea(address.getArea());
					trade.setReceiverBackCode(address.getBackCode());
				}

				// 14位日期 ＋ 6位随机数
				trade.setTradeNo(DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(14));

				try {
					tradeDao.createTrade(trade);
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(trade), e);
					ts.setRollbackOnly();

					result.setCode("创建交易失败");
					return result;
				}

				// 2. 创建订单
				result =
					orderService.createOrder(shopId, trade.getTradeId(), itemId, skuId, quantity, userId.toString());
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				result.setCode(trade.getTradeNo());
				return result;
			}
		});

		return res;
	}

	@Override
	public BooleanResult createTrade(final Long userId, final Long shopId, final String[] cartId, final Address address) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (cartId == null || cartId.length == 0) {
			result.setCode("购物车不能为空");
			return result;
		}

		if (address == null) {
			result.setCode("地址信息不能为空");
			return result;
		}

		// 获取选中商品总计价格，兑换积分，运费
		final Cart cart = cartService.getCartStats(userId, shopId, cartId);

		// 合计价格 合计积分 最小运费
		if (cart.getPrice() == null || cart.getPoints() == null || cart.getPostage() == null) {
			result.setCode("购物车商品总价为空");
			return result;
		}

		// 获取默认收货地址
		final UserAddress userAddress = userAddressService.getUserAddress(userId, address.getAddId());

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);

				// 1. 创建交易
				Trade trade = new Trade();
				trade.setUserId(userId);
				trade.setShopId(shopId);
				// 交易价格
				trade.setTradePrice(cart.getPrice());
				// 积分兑换
				trade.setTradePoints(BigDecimal.ZERO);
				trade.setCouponPrice(BigDecimal.ZERO);
				trade.setPostage(cart.getPostage());
				trade.setChange(BigDecimal.ZERO);
				// 买家结算
				trade.setType(ITradeService.CHECK);

				// 收货地址
				if (userAddress != null) {
					trade.setReceiverName(userAddress.getContactName());
					trade.setReceiverProvince(userAddress.getProvince());
					trade.setReceiverCity(userAddress.getCity());
					trade.setReceiverArea(userAddress.getArea());
					trade.setReceiverBackCode(userAddress.getBackCode());
					trade.setReceiverAddress(userAddress.getAddress());
					trade.setReceiverZip(userAddress.getPostalCode());
					trade.setReceiverTel(userAddress.getTel());
				} else {
					trade.setReceiverProvince(address.getProvince());
					trade.setReceiverCity(address.getCity());
					trade.setReceiverArea(address.getArea());
					trade.setReceiverBackCode(address.getBackCode());
				}

				// 14位日期 ＋ 6位随机数
				trade.setTradeNo(DateUtil.getNowDateminStr() + UUIDUtil.generate().substring(14));

				// 交易订单 关联 购物车
				StringBuilder sb = new StringBuilder();
				for (String id : cartId) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					sb.append(id);
				}
				trade.setCartId(sb.toString());

				try {
					tradeDao.createTrade(trade);
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(trade), e);
					ts.setRollbackOnly();

					result.setCode("创建交易失败");
					return result;
				}

				// 2. 创建订单
				result = orderService.createOrder(shopId, trade.getTradeId(), cartId, userId.toString());
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				result.setCode(trade.getTradeNo());
				return result;
			}
		});

		return res;
	}

	@Override
	public int getTradeCount(Long userId, String[] type) {
		if (userId == null) {
			return 0;
		}

		Trade trade = new Trade();
		trade.setUserId(userId);
		trade.setCodes(type);

		return getTradeCount(trade);
	}

	@Override
	public List<Trade> getTradeList(Long userId, String[] type) {
		if (userId == null) {
			return null;
		}

		Trade t = new Trade();
		t.setUserId(userId);
		t.setCodes(type);

		// 暂不分页
		t.setLimit(10);
		t.setOffset(0);
		t.setSort("CREATE_DATE");
		t.setOrder("DESC");

		List<Trade> tradeList = getTradeList(t);

		if (tradeList == null || tradeList.size() == 0) {
			return null;
		}

		for (Trade trade : tradeList) {
			// 验证订单状态
			trade = checkTrade(userId, trade);

			// 获取 shop 信息
			Shop shop = shopService.getShop(trade.getShopId());
			trade.setShopName(shop != null ? shop.getShopName() : "店铺");

			// 明细信息
			trade.setOrderList(orderService.getOrderList(userId, trade.getTradeId()));
		}

		return tradeList;
	}

	@Override
	public Trade getTrade(Long userId, String tradeNo) {
		if (userId == null || StringUtils.isBlank(tradeNo)) {
			return null;
		}

		Trade t = new Trade();
		t.setUserId(userId);
		t.setTradeNo(tradeNo.trim());

		Trade trade = getTrade(t);

		if (trade == null) {
			return null;
		}

		// 验证订单状态
		trade = checkTrade(userId, trade);

		// 获取 shop 信息
		Shop shop = shopService.getShop(trade.getShopId());
		trade.setShopName(shop != null ? shop.getShopName() : "店铺");

		// 明细信息
		trade.setOrderList(orderService.getOrderList(userId, trade.getTradeId()));

		return trade;
	}

	@Override
	public BooleanResult updateReceiver(Long userId, String tradeNo, Trade trade) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("订单信息不能为空");
			return result;
		}

		if (trade == null) {
			result.setCode("订单信息不能为空");
			return result;
		}

		trade.setUserId(userId);
		trade.setModifyUser(userId.toString());
		trade.setTradeNo(tradeNo.trim());

		return updateTrade(trade);
	}

	@Override
	public BooleanResult updateDeliverTime(Long userId, String tradeNo, Trade trade) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("订单信息不能为空");
			return result;
		}

		if (trade == null) {
			result.setCode("订单信息不能为空");
			return result;
		}

		trade.setUserId(userId);
		trade.setModifyUser(userId.toString());
		trade.setTradeNo(tradeNo.trim());

		return updateTrade(trade);
	}

	@Override
	public BooleanResult cancelTrade(Long userId, String tradeNo) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		final Trade trade = new Trade();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		trade.setUserId(userId);
		trade.setModifyUser(userId.toString());

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("订单信息不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		// 锁定订单
		String key = tradeNo.trim();

		// 锁定当前订单
		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_TRADE_NO + key, key,
				IMemcachedCacheService.CACHE_KEY_TRADE_NO_DEFAULT_EXP);
		} catch (ServiceException e) {
			result.setCode("当前订单已被锁定，请稍后再试");
			return result;
		}

		// 0. 查询 未付款交易订单
		Trade t = getTrade(trade);
		if (t == null) {
			result.setCode("当前订单不存在");

			remove(key);
			return result;
		}

		if (!ITradeService.CHECK.equals(t.getType()) && !ITradeService.TO_PAY.equals(t.getType())) {
			result.setCode("当前订单已付款或取消");

			remove(key);
			return result;
		}

		// 处理 订单明细数据 需要用到
		trade.setTradeId(t.getTradeId());

		result = cancelTrade(trade, t.getType(), trade.getModifyUser());

		remove(key);
		return result;
	}

	@Override
	public BooleanResult topayTrade(Long userId, String tradeNo, String remark) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Trade trade = new Trade();
		// 待付款
		trade.setType(ITradeService.TO_PAY);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		trade.setUserId(userId);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易订单不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		if (StringUtils.isNotEmpty(remark)) {
			trade.setReceiverRemark(remark);
		}

		trade.setModifyUser(userId.toString());

		return updateTrade(trade);
	}

	@Override
	public Trade getOrder(Long userId, String tradeNo, String orderId) {
		if (userId == null || StringUtils.isBlank(tradeNo) || StringUtils.isBlank(orderId)) {
			return null;
		}

		Long id = null;

		try {
			id = Long.valueOf(orderId);
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		Trade t = new Trade();
		t.setUserId(userId);
		t.setTradeNo(tradeNo.trim());

		Trade trade = getTrade(t);

		if (trade == null) {
			return null;
		}

		Order order = orderService.getOrder(userId, trade.getTradeId(), id);

		if (order != null) {
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(order);
			trade.setOrderList(orderList);
		}

		return trade;
	}

	@Override
	public BooleanResult createOrderRefund(String tradeNo, String refundNo, Long orderId, OrderRefund orderRefund,
		String modifyUser) {
		return orderRefundService.createOrderRefund(tradeNo, refundNo, orderId, orderRefund, modifyUser);
	}

	@Override
	public BooleanResult signTrade(Long userId, String tradeNo) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Trade trade = new Trade();
		// 签收
		trade.setType(ITradeService.SIGN);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		trade.setUserId(userId);
		trade.setModifyUser(userId.toString());

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易订单不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		result = updateTrade(trade);

		if (result.getResult()) {
			result.setCode("操作成功");
		}

		return result;
	}

	@Override
	public BooleanResult deleteTrade(Long userId, String tradeNo) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Trade trade = new Trade();
		// 删除
		trade.setType(ITradeService.DELETE);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		trade.setUserId(userId);
		trade.setModifyUser(userId.toString());

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易订单不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		// 锁定订单
		String key = tradeNo.trim();

		// 锁定当前订单
		try {
			memcachedCacheService.add(IMemcachedCacheService.CACHE_KEY_TRADE_NO + key, key,
				IMemcachedCacheService.CACHE_KEY_TRADE_NO_DEFAULT_EXP);
		} catch (ServiceException e) {
			result.setCode("当前订单已被锁定，请稍后再试");
			return result;
		}

		// 0. 查询 未付款交易订单
		Trade t = getTrade(trade);
		if (t == null) {
			result.setCode("当前订单不存在");

			remove(key);
			return result;
		}

		if (ITradeService.DELETE.equals(t.getType())) {
			result.setCode("当前订单已删除");

			remove(key);
			return result;
		}

		if (!ITradeService.CANCEL.equals(t.getType())) {
			result.setCode("当前订单正在处理中，不能删除");

			remove(key);
			return result;
		}

		result = updateTrade(trade);

		if (result.getResult()) {
			result.setCode("操作成功");
		}

		remove(key);
		return result;
	}

	@Override
	public BooleanResult copyTrade(Long userId, String tradeNo) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Trade trade = new Trade();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		trade.setUserId(userId);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易订单不能为空");
			return result;
		}
		trade.setTradeNo(tradeNo.trim());

		// 0. 查询 未付款交易订单
		Trade t = getTrade(trade);
		if (t == null) {
			result.setCode("当前订单不存在");
			return result;
		}

		String id = t.getCartId();

		if (StringUtils.isBlank(id)) {
			// TODO
			result.setCode("TODO");
			return result;
		}

		String[] cartId = id.split(",");

		// 根据购物车 复制订单
		List<Cart> cartList = cartService.getCartList(userId, cartId);
		if (cartList == null || cartList.size() == 0) {
			result.setCode("当前订单不存在");
			return result;
		}

		Long shopId = t.getShopId();

		for (Cart cart : cartList) {
			cartService.createCart(userId, shopId, cart.getItemId().toString(), cart.getSkuId().toString(),
				String.valueOf(cart.getQuantity()));
		}

		// 用户页面跳转店铺商品
		result.setCode(shopId.toString());
		result.setResult(true);

		return result;
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private int getTradeCount(Trade trade) {
		try {
			return tradeDao.getTradeCount(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return 0;
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private List<Trade> getTradeList(Trade trade) {
		try {
			return tradeDao.getTradeList(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return null;
	}

	/**
	 * 
	 * @param trade
	 * @return
	 */
	private Trade getTrade(Trade trade) {
		try {
			return tradeDao.getTrade(trade);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
		}

		return null;
	}

	/**
	 * 验证订单状态 订单未付款时间 超时.
	 * 
	 * @param trade
	 * @return
	 */
	private Trade checkTrade(Long userId, Trade trade) {
		if (trade == null) {
			return null;
		}

		// 订单状态
		String type = trade.getType();

		// 临时 待付款 订单
		if (!ITradeService.CHECK.equals(type) && !ITradeService.TO_PAY.equals(type)) {
			return trade;
		}

		// 下单时间
		String createDate = trade.getCreateDate();

		int quotSeconds =
			DateUtil.getQuotSeconds(DateUtil.datetime(createDate, DateUtil.DEFAULT_DATETIME_FORMAT), new Date());

		// 15分钟以内
		if (quotSeconds < 900) {
			// 赋值 返回页面 倒计时
			trade.setCount(900 - quotSeconds);
			return trade;
		}

		// 无论取消是否成功
		cancelTrade(userId, trade.getTradeNo());

		trade.setType(ITradeService.CANCEL);
		return trade;
	}

	private BooleanResult updateTrade(Trade trade) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		try {
			int c = tradeDao.updateTrade(trade);
			if (c == 1) {
				result.setCode("操作成功");
				result.setResult(true);
			} else {
				result.setCode("更新交易失败");
			}
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(trade), e);
			result.setCode("更新交易表失败");
		}

		return result;
	}

	/**
	 * 
	 * @param trade
	 * @param type
	 * @param modifyUser
	 * @return
	 */
	private BooleanResult cancelTrade(final Trade trade, String type, final String modifyUser) {
		// 是否需要释放库存
		boolean f = false;
		// item sku 表库存
		List<ItemSku> skus = null;
		// item 表库存 即不存在 sku
		List<Item> items = null;
		// 用于统计 还有 sku 的商品的合计库存数
		String[] itemIds = null;

		// 根据 type 判断 是否需要 释放已占库存
		if (ITradeService.TO_PAY.equals(type)) {
			// 需要释放库存
			f = true;

			// 1. 判断库存
			List<Order> orderList = orderService.getOrderList(trade.getUserId(), trade.getTradeId());
			if (orderList == null || orderList.size() == 0) {
				BooleanResult result = new BooleanResult();
				result.setResult(false);
				result.setCode("当前订单明细不存在");

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

				map.put(key, stock + quantity);
			}

			// 根据 map 组装 skuList
			skus = new ArrayList<ItemSku>();
			items = new ArrayList<Item>();
			itemIds = new String[orderList.size()];
			int i = 0;

			for (Map.Entry<String, Integer> m : map.entrySet()) {
				String[] key = m.getKey().split("&");

				// if skuId is null or 0 then 商品没有规格
				if (StringUtils.isBlank(key[1]) || "0".equals(key[1])) {
					Item item = new Item();
					item.setItemId(Long.valueOf(key[0]));
					item.setStock(m.getValue());

					items.add(item);

					continue;
				}

				ItemSku sku = new ItemSku();
				sku.setItemId(Long.valueOf(key[0]));
				sku.setSkuId(Long.valueOf(key[1]));
				sku.setStock(m.getValue());

				skus.add(sku);

				itemIds[i++] = key[0];
			}
		}

		// type 取消
		trade.setType(ITradeService.CANCEL);
		// 是否需要释放库存
		final boolean flag = f;
		// item sku 表库存
		final List<ItemSku> skuList = skus;
		// item 表库存 即不存在 sku
		final List<Item> itemList = items;
		// 用于统计 还有 sku 的商品的合计库存数
		final String[] itemId = itemIds;

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				// 1. 订单状态取消
				BooleanResult result = updateTrade(trade);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 2. 释放库存
				if (flag) {
					// 2.1 存在 item_sku
					if (skuList != null && skuList.size() > 0) {
						// 2.1.1 更新 item_sku stock
						result = itemSkuService.updateItemSkuStock(skuList, modifyUser);
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}

						// 2.1.2 更新 还有 sku 的 item 合计库存数
						result = itemService.updateItemStock(itemId, modifyUser);
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}
					}

					// 2.2 不存在 item_sku
					if (itemList != null && itemList.size() > 0) {
						// 2.2.1 更新 item stock
						result = itemService.updateItemStock(itemList, modifyUser);
						if (!result.getResult()) {
							ts.setRollbackOnly();

							return result;
						}
					}
				}

				return result;
			}
		});

		if (res.getResult()) {
			res.setCode("取消成功");
		}
		return res;
	}

	private void remove(String tradeNo) {
		try {
			memcachedCacheService.remove(IMemcachedCacheService.CACHE_KEY_TRADE_NO + tradeNo);
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
