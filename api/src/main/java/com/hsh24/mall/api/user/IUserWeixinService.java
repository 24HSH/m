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
	 * @param scope
	 *            用户授权的作用域，使用逗号（,）分隔.
	 * @return
	 */
	User getUser(String accessToken, String openId, String scope);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	String getOpenId(Long userId);

}
