package com.hsh24.mall.user.dao;

import com.hsh24.mall.api.user.bo.UserAddress;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserAddressDao {

	/**
	 * 
	 * @param userAddress
	 * @return
	 */
	UserAddress getUserAddress(UserAddress userAddress);

	/**
	 * 
	 * @param userAddress
	 * @return
	 */
	Long createUserAddress(UserAddress userAddress);

	/**
	 * 
	 * @param userAddress
	 * @return
	 */
	int removeDefaultUserAddress(UserAddress userAddress);

}
