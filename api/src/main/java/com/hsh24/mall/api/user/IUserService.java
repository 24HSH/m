package com.hsh24.mall.api.user;

import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserService {

	/**
	 * 
	 * @param user
	 * @param modifyUser
	 * @return
	 */
	BooleanResult createUser(User user, String modifyUser);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	User getUser(Long userId);

}
