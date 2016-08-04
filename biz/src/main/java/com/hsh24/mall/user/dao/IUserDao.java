package com.hsh24.mall.user.dao;

import com.hsh24.mall.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserDao {

	/**
	 * 
	 * @param user
	 * @return
	 */
	int createUser(User user);

	/**
	 * 
	 * @param user
	 * @return
	 */
	User getUser(User user);

}
