package com.wideka.mall.user.dao.impl;

import com.wideka.mall.api.user.bo.User;
import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.user.dao.IUserDao;

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
