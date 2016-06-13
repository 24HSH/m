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
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	User getUser(String accessToken, String openId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	String getOpenId(Long userId);

}
