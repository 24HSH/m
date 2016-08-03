package com.hsh24.mall.framework.struts.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * @author JiakunXu
 * 
 */
@Component
public class ShopInterceptor implements Interceptor {

	private static final long serialVersionUID = -7498838714747075663L;

	private static final String SHOP = "shop";

	@Resource
	private IShopService shopService;

	public void destroy() {
	}

	public void init() {
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		String shopId = request.getParameter("shopId");
		if (StringUtils.isBlank(shopId)) {
			request.getSession().setAttribute("ACEGI_SECURITY_REDIRECT_URL", getUrl());
			return SHOP;
		}

		Shop shop = shopService.getShop(shopId.trim());
		if (shop == null) {
			request.getSession().setAttribute("ACEGI_SECURITY_REDIRECT_URL", getUrl());
			return SHOP;
		}

		request.getSession().setAttribute("ACEGI_SECURITY_LAST_SHOP", shop);

		return invocation.invoke();
	}

	private String getUrl() {
		// 获取当前applicationContex
		ActionContext ctx = ActionContext.getContext();
		// Map map = ctx.getSession()
		// 设置当前请求的URL
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuffer url = request.getRequestURL();

		String queryString = request.getQueryString();
		if (StringUtils.isNotEmpty(queryString)) {
			return url.toString() + "?" + queryString;
		}

		return url.toString();
	}

}
