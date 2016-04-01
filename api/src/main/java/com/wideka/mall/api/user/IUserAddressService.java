package com.wideka.mall.api.user;

import com.wideka.mall.api.user.bo.UserAddress;
import com.wideka.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserAddressService {

	/**
	 * 获取用户默认收货地址.
	 * 
	 * @param userId
	 * @return
	 */
	UserAddress getDefaultUserAddress(String userId);

	/**
	 * 新增收货地址 设置默认收货地址 并 修改交易记录.
	 * 
	 * @param userId
	 * @param userAddress
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	BooleanResult createUserAddress(String userId, UserAddress userAddress, Long shopId, String tradeNo);

}
