package com.hsh24.mall.cart.action;

import java.util.Arrays;
import java.util.List;

import com.hsh24.mall.api.cart.ICartService;
import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
public class CartAction extends BaseAction {

	private static final long serialVersionUID = -4392828383974468915L;

	private ICartService cartService;

	private List<Cart> cartList;

	private String itemId;

	private String skuId;

	/**
	 * 删除购物车.
	 */
	private String[] cartIds;

	private String cartId;

	private String quantity;

	public String stats() {
		this.setResourceResult(String.valueOf(cartService.getCartCount(this.getUser().getUserId(), this.getShop()
			.getShopId())));

		return RESOURCE_RESULT;
	}

	/**
	 * 
	 * @return
	 */
	public String index() {
		cartList = cartService.getCartList(this.getUser().getUserId(), this.getShop().getShopId());

		return SUCCESS;
	}

	/**
	 * 添加购物车.
	 * 
	 * @return
	 */
	public String add() {
		BooleanResult result =
			cartService.createCart(this.getUser().getUserId(), this.getShop().getShopId(), itemId, skuId, "1");

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

	public ICartService getCartService() {
		return cartService;
	}

	public void setCartService(ICartService cartService) {
		this.cartService = cartService;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
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
