package com.hsh24.mall.address.dao;

import java.util.List;

import com.hsh24.mall.api.address.bo.Address;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IAddressDao {

	/**
	 * 
	 * @param address
	 * @return
	 */
	List<Address> getAddressList(Address address);

	/**
	 * 
	 * @param address
	 * @return
	 */
	Address getAddress(Address address);

}
