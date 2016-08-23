package com.hsh24.mall.api.user;

import java.util.List;

import com.hsh24.mall.api.user.bo.UserAddress;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserAddressService {

	/**
	 * 
	 * @param userId
	 * @return
	 */
	List<UserAddress> getUserAddressList(Long userId);

	/**
	 * 
	 * @param userId
	 * @param addId
	 * @return
	 */
	UserAddress getUserAddress(Long userId, String addId);

	/**
	 * 获取用户默认收货地址.
	 * 
	 * @param userId
	 * @param mdmAddId
	 * @return
	 */
	UserAddress getUserAddress(Long userId, Long mdmAddId);

	/**
	 * 新增收货地址 设置默认收货地址 并 修改交易记录.
	 * 
	 * @param userId
	 * @param userAddress
	 * @param tradeNo
	 * @return
	 */
	BooleanResult createUserAddress(Long userId, UserAddress userAddress, String tradeNo);

	/**
	 * 
	 * @param userId
	 * @param userAddress
	 * @return
	 */
	BooleanResult updateUserAddress(Long userId, UserAddress userAddress);

}
