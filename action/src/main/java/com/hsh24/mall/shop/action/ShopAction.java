package com.hsh24.mall.shop.action;

import javax.servlet.http.HttpSession;

import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ShopAction extends BaseAction {

	private static final long serialVersionUID = -3517081429091032617L;

	private IShopService shopService;

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String index() {
		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_SHOP", shopService.getShop(1321L));

		return SUCCESS;
	}

	public IShopService getShopService() {
		return shopService;
	}

	public void setShopService(IShopService shopService) {
		this.shopService = shopService;
	}

}
