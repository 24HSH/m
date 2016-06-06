package com.hsh24.mall.user.dao.impl;

import com.hsh24.mall.api.user.bo.UserAddress;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.user.dao.IUserAddressDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserAddressDaoImpl extends BaseDaoImpl implements IUserAddressDao {

	@Override
	public UserAddress getUserAddress(UserAddress userAddress) {
		return (UserAddress) getSqlMapClientTemplate().queryForObject("user.address.getUserAddress", userAddress);
	}

	@Override
	public Long createUserAddress(UserAddress userAddress) {
		return (Long) getSqlMapClientTemplate().insert("user.address.createUserAddress", userAddress);
	}

	@Override
	public int removeDefaultUserAddress(UserAddress userAddress) {
		return getSqlMapClientTemplate().update("user.address.removeDefaultUserAddress", userAddress);
	}

}
