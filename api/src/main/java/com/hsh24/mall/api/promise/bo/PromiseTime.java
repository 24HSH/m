package com.hsh24.mall.api.promise.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author JiakunXu
 * 
 */
public class PromiseTime implements Serializable {

	private static final long serialVersionUID = -7025221058982425525L;

	private String dateText;

	private String date;

	private String type;

	private List<PromiseTimeItem> promiseTimeItemList;

	public String getDateText() {
		return dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PromiseTimeItem> getPromiseTimeItemList() {
		return promiseTimeItemList;
	}

	public void setPromiseTimeItemList(List<PromiseTimeItem> promiseTimeItemList) {
		this.promiseTimeItemList = promiseTimeItemList;
	}

}
