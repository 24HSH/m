package com.hsh24.mall.user.dao;

import com.hsh24.mall.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserWeixinDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	User getUser(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	int createUser(User user);

}
