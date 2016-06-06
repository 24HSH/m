package com.hsh24.mall.item.action;

import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = -8497315926605513479L;

	private IItemService itemService;

	private String itemId;

	private Item item;

	/**
	 * 
	 * @return
	 */
	public String list() {
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String detail() {
		item = itemService.getItem(0L, itemId);

		return SUCCESS;
	}

	public IItemService getItemService() {
		return itemService;
	}

	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
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
