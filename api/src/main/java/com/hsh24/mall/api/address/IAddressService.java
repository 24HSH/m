package com.hsh24.mall.api.address;

import java.util.List;

import com.hsh24.mall.api.address.bo.Address;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IAddressService {

	/**
	 * 
	 * @param city
	 * @param search
	 * @return
	 */
	List<Address> getAddressList(String city, String search);

	/**
	 * 
	 * @param addId
	 * @return
	 */
	Address getAddress(String addId);

}
