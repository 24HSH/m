package com.hsh24.mall.api.region.bo;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Region implements Serializable {

	private static final long serialVersionUID = -7000090718177786423L;

	private Long regionId;

	private Long parentRegionId;

	private String regionName;

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getParentRegionId() {
		return parentRegionId;
	}

	public void setParentRegionId(Long parentRegionId) {
		this.parentRegionId = parentRegionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
