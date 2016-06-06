package com.hsh24.mall.api.user;

import com.hsh24.mall.api.user.bo.User;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IUserWeixinService {

	/**
	 * 
	 * @param openId
	 * @return
	 */
	User getUser(String openId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	String getOpenId(Long userId);

}
