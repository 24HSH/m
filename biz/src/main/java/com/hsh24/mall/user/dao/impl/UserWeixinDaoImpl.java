package com.hsh24.mall.user.dao.impl;

import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.user.dao.IUserWeixinDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserWeixinDaoImpl extends BaseDaoImpl implements IUserWeixinDao {

	@Override
	public User getUser(User user) {
		return (User) getSqlMapClientTemplate().queryForObject("user.weixin.getUser", user);
	}

	@Override
	public Long createUser(User user) {
		return (Long) getSqlMapClientTemplate().insert("user.weixin.createUser", user);
	}

}
