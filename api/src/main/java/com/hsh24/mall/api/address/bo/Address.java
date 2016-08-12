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

	/**
	 * 省.
	 */
	private String province;

	/**
	 * 市.
	 */
	private String city;

	/**
	 * 区.
	 */
	private String area;

	/**
	 * 区 对应id.
	 */
	private String backCode;

	private String address;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBackCode() {
		return backCode;
	}

	public void setBackCode(String backCode) {
		this.backCode = backCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getBlockId() {
		return blockId;
	}

	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

}
