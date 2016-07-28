package com.hsh24.mall.item.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = -8497315926605513479L;

	@Resource
	private IItemService itemService;

	private List<Item> itemList;

	private String itemId;

	private Item item;

	/**
	 * 
	 * @return
	 */
	public String list() {
		itemList = itemService.getItemList(this.getShop().getShopId(), new Item());

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
