package com.hsh24.mall.item.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.item.IItemCatService;
import com.hsh24.mall.api.item.bo.ItemCat;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class ItemCatAction extends BaseAction {

	private static final long serialVersionUID = -6711279472564827073L;

	@Resource
	private IItemCatService itemCatService;

	private List<ItemCat> itemCatList;

	/**
	 * 
	 * @return
	 */
	public String index() {
		itemCatList = itemCatService.getItemCatList("0");

		return SUCCESS;
	}

	public List<ItemCat> getItemCatList() {
		return itemCatList;
	}

	public void setItemCatList(List<ItemCat> itemCatList) {
		this.itemCatList = itemCatList;
	}

}
