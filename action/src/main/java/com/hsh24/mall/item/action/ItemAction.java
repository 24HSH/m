package com.hsh24.mall.item.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.cart.ICartService;
import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.api.item.IItemCatService;
import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.api.item.bo.ItemCat;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = -8497315926605513479L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemAction.class);

	@Resource
	private IItemService itemService;

	@Resource
	private ICartService cartService;

	@Resource
	private IItemCatService itemCatService;

	private List<Item> itemList;

	private List<Cart> cartList;

	private List<ItemCat> itemCatList;

	/**
	 * 商品类目.
	 */
	private String itemCId;

	private String itemId;

	private Item item;

	/**
	 * 
	 * @return
	 */
	public String list() {
		Long shopId = this.getShop().getShopId();

		Item ietm = new Item();

		if (StringUtils.isNotBlank(itemCId)) {
			try {
				ietm.setItemCId(Long.valueOf(itemCId));
			} catch (NumberFormatException e) {
				logger.error(e);
			}
		}

		itemList = itemService.getItemList(shopId, ietm);

		cartList = cartService.getCartListByShop(this.getUser().getUserId(), shopId);

		itemCatList = itemCatService.getItemCatList("0");

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String detail() {
		item = itemService.getItem(this.getShop().getShopId(), itemId);

		return SUCCESS;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

	public List<ItemCat> getItemCatList() {
		return itemCatList;
	}

	public void setItemCatList(List<ItemCat> itemCatList) {
		this.itemCatList = itemCatList;
	}

	public String getItemCId() {
		return itemCId;
	}

	public void setItemCId(String itemCId) {
		this.itemCId = itemCId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
