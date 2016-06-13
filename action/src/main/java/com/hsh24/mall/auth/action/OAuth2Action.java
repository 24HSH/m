package com.hsh24.mall.auth.action;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.hsh24.mall.api.auth.IAuthService;
import com.hsh24.mall.api.user.IUserWeixinService;
import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.wideka.weixin.api.auth.bo.AccessToken;
import com.wideka.weixin.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class OAuth2Action extends BaseAction {

	private static final long serialVersionUID = 6386474612475679175L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(OAuth2Action.class);

	private IAuthService authService;

	private IUserWeixinService userWeixinService;

	private String redirectUrl;

	private String scope;

	public String authorize() {
		BooleanResult result = null;

		try {
			result =
				authService.authorize(URLEncoder.encode(env.getProperty("appUrl") + "/auth/redirect.htm", "UTF-8"),
					StringUtils.isBlank(scope) ? "snsapi_base" : scope.trim());
		} catch (Exception e) {
			logger.error(e);
			return ERROR;
		}

		if (result.getResult()) {
			redirectUrl = result.getCode();

			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String redirect() {
		// 用户唯一标识
		AccessToken accessToken = authService.accessToken(this.getCode());

		// 用户没有授权 或...
		if (accessToken == null || StringUtils.isEmpty(accessToken.getOpenId())) {
			return ERROR;
		}
		
		System.out.println(LogUtil.parserBean(accessToken));

		// 根据 openId 获得 userId
		User u =
			userWeixinService.getUser(accessToken.getAccessToken(), accessToken.getOpenId(), accessToken.getScope());

		if (u == null) {
			return NONE;
		}

		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_PASSPORT", u.getPassport());
		session.setAttribute("ACEGI_SECURITY_LAST_LOGINUSER", u);

		HttpServletResponse response = getServletResponse();
		if (response != null) {
			Cookie ps = new Cookie("PS", u.getPassport());
			// ps.setMaxAge(-1);
			ps.setPath("/");
			ps.setDomain(env.getProperty("domain"));
			response.addCookie(ps);
		}

		return SUCCESS;
	}

	public IAuthService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthService authService) {
		this.authService = authService;
	}

	public IUserWeixinService getUserWeixinService() {
		return userWeixinService;
	}

	public void setUserWeixinService(IUserWeixinService userWeixinService) {
		this.userWeixinService = userWeixinService;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
