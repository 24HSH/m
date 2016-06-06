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
	public Long createUser(User user) {
		return (Long) getSqlMapClientTemplate().insert("user.createUser", user);
	}

	@Override
	public User getUser(User user) {
		return (User) getSqlMapClientTemplate().queryForObject("user.getUser", user);
	}

}
