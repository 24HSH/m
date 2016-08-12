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
	int createUserAddress(UserAddress userAddress);

	/**
	 * 
	 * @param userAddress
	 * @return
	 */
	int removeUserAddress(UserAddress userAddress);

}
