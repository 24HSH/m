package com.hsh24.mall.api.promise;

import java.util.List;

import com.hsh24.mall.api.promise.bo.PromiseTime;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IPromiseTimeService {

	/**
	 * 
	 * @param shopId
	 * @return
	 */
	List<PromiseTime> getPromiseTimeList(Long shopId);

}
