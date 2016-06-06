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
	 * @param passport
	 * @return
	 */
	User getUserByPassport(String passport);

}
