package com.hsh24.mall.shop.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ShopAction extends BaseAction {

	private static final long serialVersionUID = -3517081429091032617L;

	private IShopService shopService;

	private List<Shop> shopList;

	private String shopId;

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String index() {
		shopList = shopService.getShopList(new Shop());

		return SUCCESS;
	}

	public String select() {
		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_SHOP", shopService.getShop(shopId));

		return SUCCESS;
	}

	public IShopService getShopService() {
		return shopService;
	}

	public void setShopService(IShopService shopService) {
		this.shopService = shopService;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

}
