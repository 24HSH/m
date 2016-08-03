package com.hsh24.mall.login.action;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.ca.ICAService;
import com.hsh24.mall.api.ca.bo.ValidateResult;
import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.annotation.ActionMonitor;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;

/**
 * 登录模块.
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 7498561926934442624L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(LoginAction.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private ICAService caService;

	private String passport;

	private String password;

	/**
	 * 登录验证.
	 * 
	 * @return
	 */
	@ActionMonitor(actionName = "系统登录")
	public String login() {
		ValidateResult result = caService.validateUser(passport, password);

		// 验证失败
		if (ICAService.RESULT_FAILED.equals(result.getResultCode())
			|| ICAService.RESULT_ERROR.equals(result.getResultCode())) {
			this.setFailMessage(result.getMessage());

			return "incorrect";
		}

		// 验证通过
		User u = result.getUser();

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

	/**
	 * 退出系统.
	 * 
	 * @return
	 */
	@ActionMonitor(actionName = "系统退出")
	public String logout() {
		HttpSession session = this.getSession();

		// 清空cache中session信息
		try {
			memcachedCacheService.remove(session.getId());
		} catch (Exception e) {
			logger.error(e);
		}

		try {
			// login
			session.removeAttribute("ACEGI_SECURITY_LAST_PASSPORT");
			session.removeAttribute("ACEGI_SECURITY_LAST_LOGINUSER");
			session.removeAttribute("ACEGI_SECURITY_LAST_ADDRESS");
			session.removeAttribute("ACEGI_SECURITY_LAST_SHOP");

			session.invalidate();
		} catch (Exception e) {
			logger.error(e);
		}

		HttpServletResponse response = getServletResponse();
		if (response != null) {
			Cookie j = new Cookie("JSESSIONID", null);
			j.setMaxAge(0);
			j.setPath("/");
			response.addCookie(j);
		}

		return LOGOUT;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
