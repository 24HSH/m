package com.hsh24.mall.api.address.bo;

import com.hsh24.mall.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class Address extends SearchInfo {

	private static final long serialVersionUID = -8036254410525614639L;

	private Long addId;

	private String addName;

	private String address;

	private Long regionId;

	/**
	 * 所属块.
	 */
	private Long blockId;

	public Long getAddId() {
		return addId;
	}

	public void setAddId(Long addId) {
		this.addId = addId;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

}
