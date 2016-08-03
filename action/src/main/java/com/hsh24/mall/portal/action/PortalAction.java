package com.hsh24.mall.portal.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
import com.hsh24.mall.api.weixin.IWeixinService;
import com.hsh24.mall.api.weixin.bo.Ticket;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class PortalAction extends BaseAction {

	private static final long serialVersionUID = 2191525146456353749L;

	@Resource
	private IShopService shopService;

	@Resource
	private IWeixinService weixinService;

	private List<Shop> shopList;

	private Ticket ticket;

	/**
	 * 登录首页.
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

	/**
	 * 移动商场.
	 * 
	 * @return
	 */
	public String homepage() {
		shopList = shopService.getShopList(this.getAddress().getBlockId());

		String requestURL = env.getProperty("appUrl") + "/homepage.htm";
		HttpServletRequest request = getServletRequest();
		String queryString = request.getQueryString();

		ticket =
			weixinService.getTicket(StringUtils.isEmpty(queryString) ? requestURL : requestURL + "?" + queryString);

		return SUCCESS;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

}
