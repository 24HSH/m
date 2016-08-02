package com.hsh24.mall.shop.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ShopAction extends BaseAction {

	private static final long serialVersionUID = -3517081429091032617L;

	@Resource
	private IShopService shopService;

	private String shopId;

	public String select() {
		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_SHOP", shopService.getShop(shopId));

		return SUCCESS;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
