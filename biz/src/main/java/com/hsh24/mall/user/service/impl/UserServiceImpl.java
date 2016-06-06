package com.hsh24.mall.user.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.user.IUserService;
import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.user.dao.IUserDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class UserServiceImpl implements IUserService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserServiceImpl.class);

	private IMemcachedCacheService memcachedCacheService;

	private IUserDao userDao;

	@Override
	public BooleanResult createUser(User user, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (user == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		user.setModifyUser(modifyUser);

		try {
			Long userId = userDao.createUser(user);
			result.setCode(userId.toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);

			result.setCode("用户信息创建失败，请稍后再试");
		}

		return result;
	}

	@Override
	public User getUser(Long userId) {
		if (userId == null) {
			return null;
		}

		User user = new User();
		user.setUserId(userId);

		try {
			return userDao.getUser(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

}
