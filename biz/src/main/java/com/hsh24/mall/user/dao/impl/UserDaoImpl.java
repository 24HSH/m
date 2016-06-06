package com.hsh24.mall.user.dao.impl;

import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.user.dao.IUserDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserDaoImpl extends BaseDaoImpl implements IUserDao {

	@Override
	public User getUserByPassport(String passport) {
		return (User) getSqlMapClientTemplate().queryForObject("user.getUserByPassport", passport);
	}

}
