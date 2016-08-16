package com.hsh24.mall.api.promise.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class PromiseTimeItem implements Serializable {

	private static final long serialVersionUID = 7927155105773158330L;

	private String startTime;

	private String endTime;

	public String getTimeText() {
		return getStartTime() + "-" + getEndTime();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
