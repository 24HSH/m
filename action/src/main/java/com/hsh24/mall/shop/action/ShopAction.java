package com.hsh24.mall.shop.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
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

	private List<Shop> shopList;

	private String shopId;

	/**
	 * 首页.
	 * 
	 * @return
	 */
	public String index() {
		shopList = shopService.getShopList(this.getAddress().getBlockId());

		if (shopList != null && shopList.size() == 1) {
			shopId = shopList.get(0).getShopId().toString();

			return NONE;
		}

		return SUCCESS;
	}

	public String select() {
		return SUCCESS;
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
