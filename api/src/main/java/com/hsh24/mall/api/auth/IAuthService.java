package com.hsh24.mall.api.auth;

import com.hsh24.mall.framework.bo.BooleanResult;
import com.wideka.weixin.api.auth.bo.AccessToken;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IAuthService {

	/**
	 * 
	 * @param redirectUrl
	 * @param scope
	 * @return
	 */
	BooleanResult authorize(String redirectUrl, String scope);

	/**
	 * 
	 * @param redirectUrl
	 * @param scope
	 * @param state
	 * @return
	 */
	BooleanResult authorize(String redirectUrl, String scope, String state);

	/**
	 * 
	 * @param code
	 * @return
	 */
	AccessToken accessToken(String code);

}
