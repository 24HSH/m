package com.hsh24.mall.api.user;

import com.hsh24.mall.api.user.bo.UserAddress;
import com.hsh24.mall.framework.bo.BooleanResult;

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
	UserAddress getDefaultUserAddress(Long userId);

	/**
	 * 新增收货地址 设置默认收货地址 并 修改交易记录.
	 * 
	 * @param userId
	 * @param userAddress
	 * @param shopId
	 * @param tradeNo
	 * @return
	 */
	BooleanResult createUserAddress(Long userId, UserAddress userAddress, Long shopId, String tradeNo);

}
