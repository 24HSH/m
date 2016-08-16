package com.hsh24.mall.api.deliver;

import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IDeliverTimeService {

	/**
	 * 
	 * @param userId
	 * @param tradeNo
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BooleanResult setDeliverTime(Long userId, String tradeNo, String date, String startTime, String endTime);

}
