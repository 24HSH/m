package com.hsh24.mall.auth.service.impl;

import org.apache.commons.lang.StringUtils;

import com.hsh24.mall.api.auth.IAuthService;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.exception.ServiceException;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.wideka.weixin.api.auth.IOAuth2Service;
import com.wideka.weixin.api.auth.bo.AccessToken;

/**
 * 
 * @author JiakunXu
 * 
 */
public class AuthServiceImpl implements IAuthService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(AuthServiceImpl.class);

	private IOAuth2Service oauth2Service;

	private String appId;

	private String appSecret;

	@Override
	public BooleanResult authorize(String redirectUrl) {
		return authorize(redirectUrl, null);
	}

	@Override
	public BooleanResult authorize(String redirectUrl, String state) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (StringUtils.isBlank(redirectUrl)) {
			result.setCode("redirectUrl 不能为空.");
			return result;
		}

		try {
			result.setCode(oauth2Service.authorize(appId, redirectUrl, "snsapi_userinfo", state));
			result.setResult(true);
		} catch (ServiceException e) {
			logger.error(e);

			result.setCode(e.getMessage());
		}

		return result;
	}

	@Override
	public AccessToken accessToken(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		try {
			return oauth2Service.accessToken(appId, appSecret, code);
		} catch (ServiceException e) {
			logger.error(e);
		}

		return null;
	}

	public IOAuth2Service getOauth2Service() {
		return oauth2Service;
	}

	public void setOauth2Service(IOAuth2Service oauth2Service) {
		this.oauth2Service = oauth2Service;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
