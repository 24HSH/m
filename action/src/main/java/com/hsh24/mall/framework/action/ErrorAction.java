package com.hsh24.mall.framework.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * error.
 * 
 * @author xujiakun
 * 
 */
@Controller
@Scope("request")
public class ErrorAction extends BaseAction {

	private static final long serialVersionUID = 940723518391814342L;

	/**
	 * Unauthorized.
	 * 
	 * @return
	 */
	public String unauthorized() {
		return "401";
	}

	/**
	 * Forbidden.
	 * 
	 * @return
	 */
	public String forbidden() {
		return "403";
	}

	/**
	 * Not Found.
	 * 
	 * @return
	 */
	public String notFound() {
		return "404";
	}

	/**
	 * Login Timeout.
	 * 
	 * @return
	 */
	public String loginTimeout() {
		return "440";
	}

	/**
	 * Internal Server Error.
	 * 
	 * @return
	 */
	public String internalServerError() {
		return "500";
	}

	/**
	 * Service Unavailable.
	 * 
	 * @return
	 */
	public String serviceUnavailable() {
		return "503";
	}

}
