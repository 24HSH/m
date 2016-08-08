package com.hsh24.mall.trade.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.mall.api.trade.IOrderRefundService;
import com.hsh24.mall.api.trade.bo.OrderRefund;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.trade.dao.IOrderRefundDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class OrderRefundServiceImpl implements IOrderRefundService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(OrderRefundServiceImpl.class);

	@Resource
	private IOrderRefundDao orderRefundDao;

	@Override
	public BooleanResult createOrderRefund(String tradeNo, String refundNo, Long orderId, OrderRefund orderRefund,
		String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (orderRefund == null) {
			result.setCode("退款订单信息不能为空");
			return result;
		}

		if (orderId == null) {
			result.setCode("订单信息不能为空");
			return result;
		}
		orderRefund.setOrderId(orderId);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("交易编号信息不能为空");
			return result;
		}
		orderRefund.setTradeNo(tradeNo.trim());

		if (StringUtils.isBlank(refundNo)) {
			result.setCode("退款订单编号信息不能为空");
			return result;
		}
		orderRefund.setRefundNo(refundNo.trim());

		if (StringUtils.isBlank(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}
		orderRefund.setModifyUser(modifyUser.trim());

		try {
			orderRefundDao.createOrderRefund(orderRefund);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(orderRefund), e);

			result.setCode("创建退款订单失败");
		}

		return result;
	}

}
