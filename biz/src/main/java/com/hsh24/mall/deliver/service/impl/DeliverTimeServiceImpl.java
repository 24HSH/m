package com.hsh24.mall.deliver.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.mall.api.deliver.IDeliverTimeService;
import com.hsh24.mall.api.trade.ITradeService;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class DeliverTimeServiceImpl implements IDeliverTimeService {

	@Resource
	private ITradeService tradeService;

	@Override
	public BooleanResult setDeliverTime(Long userId, String tradeNo, String date, String startTime, String endTime) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(tradeNo)) {
			result.setCode("订单信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(date)) {
			result.setCode("送达日期不能为空");
			return result;
		}

		if (StringUtils.isBlank(startTime)) {
			result.setCode("送达时间不能为空");
			return result;
		}

		if (StringUtils.isBlank(endTime)) {
			result.setCode("送达时间不能为空");
			return result;
		}

		if (userId == null) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		Trade trade = new Trade();
		trade.setDeliverDate(date);
		trade.setDeliverStartTime(startTime);
		trade.setDeliverEndTime(endTime);

		return tradeService.updateDeliverTime(userId, tradeNo, trade);
	}

}
