package com.hsh24.mall.cart.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.cart.ICartService;
import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class CartAction extends BaseAction {

	private static final long serialVersionUID = -4392828383974468915L;

	@Resource
	private ICartService cartService;

	@Resource
	private IShopService shopService;

	private List<Shop> shopList;

	private String itemId;

	private String skuId;

	/**
	 * 删除购物车.
	 */
	private String[] cartIds;

	private String cartId;

	private String quantity;

	public String stats() {
		this.setResourceResult(String.valueOf(cartService.getCartCountByShop(this.getUser().getUserId(), this.getShop()
			.getShopId())));

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String index() {
		List<Cart> cartList =
			cartService.getCartListByBlock(this.getUser().getUserId(), this.getAddress().getBlockId());

		if (cartList == null || cartList.size() == 0) {
			return SUCCESS;
		}

		String[] sohpId = new String[cartList.size()];
		int i = 0;
		for (Cart cart : cartList) {
			sohpId[i++] = cart.getShopId().toString();
		}

		shopList = shopService.getShopList(sohpId);

		if (shopList == null || shopList.size() == 0) {
			return SUCCESS;
		}

		for (Shop shop : shopList) {
			Long shopId = shop.getShopId();
			List<Cart> list = new ArrayList<Cart>();

			for (Cart cart : cartList) {
				if (shopId.equals(cart.getShopId())) {
					list.add(cart);
				}
			}

			shop.setCartList(list);
		}

		return SUCCESS;
	}

	/**
	 * 添加购物车.
	 * 
	 * @return
	 */
	public String add() {
		BooleanResult result =
			cartService.createCart(this.getUser().getUserId(), this.getShop().getShopId(), itemId, skuId, quantity);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 移除购物车.
	 * 
	 * @return
	 */
	public String remove() {
		BooleanResult result = cartService.removeCart(this.getUser().getUserId(), this.getShop().getShopId(), cartIds);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	/**
	 * 购物车数量.
	 * 
	 * @return
	 */
	public String num() {
		BooleanResult result =
			cartService.updateQuantity(this.getUser().getUserId(), this.getShop().getShopId(), cartId, quantity);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String[] getCartIds() {
		return cartIds != null ? Arrays.copyOf(cartIds, cartIds.length) : null;
	}

	public void setCartIds(String[] cartIds) {
		if (cartIds != null) {
			this.cartIds = Arrays.copyOf(cartIds, cartIds.length);
		}
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
