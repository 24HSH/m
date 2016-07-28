package com.hsh24.mall.user.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.user.IUserService;
import com.hsh24.mall.api.user.IUserWeixinService;
import com.hsh24.mall.api.user.bo.User;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.exception.ServiceException;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.user.dao.IUserWeixinDao;
import com.wideka.weixin.api.auth.IOAuth2Service;
import com.wideka.weixin.api.auth.bo.UserInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserWeixinServiceImpl implements IUserWeixinService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserWeixinServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IUserService userService;

	@Resource
	private IOAuth2Service oauth2Service;

	@Resource
	private IUserWeixinDao userWeixinDao;

	@Override
	public User getUser(String accessToken, String openId, String scope) {
		if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId) || StringUtils.isBlank(scope)) {
			return null;
		}

		User user = getUser(openId.trim());
		if (user != null) {
			return userService.getUser(user.getUserId());
		}

		String[] scopes = scope.split(",");
		if (scopes == null || scopes.length == 0) {
			return null;
		}

		// 判断 scope 是否为 snsapi_userinfo
		boolean f = false;
		for (String s : scopes) {
			if ("snsapi_userinfo".equals(s)) {
				f = true;
				break;
			}
		}

		if (!f) {
			return null;
		}

		// 拉取用户信息
		UserInfo userInfo = null;
		try {
			userInfo = oauth2Service.getUserInfo(accessToken, openId);
		} catch (ServiceException e) {
			logger.error(e);
		}

		if (userInfo == null) {
			return null;
		}

		final User u = new User();
		u.setUserName(userInfo.getNickName());
		u.setPassport(openId);
		u.setSex(userInfo.getSex());
		u.setProvince(userInfo.getProvince());
		u.setCity(userInfo.getCity());
		u.setCountry(userInfo.getCountry());
		u.setHeadImgUrl(userInfo.getHeadImgUrl());

		u.setOpenId(openId.trim());

		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult result = userService.createUser(u, "sys");
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				Long userId = Long.valueOf(result.getCode());

				try {
					u.setUserId(userId);
					u.setModifyUser("sys");
					userWeixinDao.createUser(u);
				} catch (Exception e) {
					logger.error(LogUtil.parserBean(u), e);
					ts.setRollbackOnly();

					result.setCode("创建用户微信失败");
					return result;
				}

				return result;
			}
		});

		if (res.getResult()) {
			u.setUserId(Long.valueOf(res.getCode()));
			return u;
		} else {
			return null;
		}
	}

	private User getUser(String openId) {
		String key = openId;

		User user = null;

		try {
			user = (User) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_WX_OPEN_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_OPEN_ID + key, e);
		}

		if (user != null) {
			return user;
		}

		user = new User();
		user.setOpenId(openId);

		user = getUser(user);

		if (user == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_WX_OPEN_ID + key, user);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_OPEN_ID + key, e);
		}

		return user;
	}

	@Override
	public String getOpenId(Long userId) {
		if (userId == null) {
			return null;
		}

		String key = userId.toString();

		String openId = null;

		try {
			openId = (String) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_WX_USER_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_USER_ID + key, e);
		}

		if (StringUtils.isNotEmpty(openId)) {
			return openId;
		}

		User user = new User();
		user.setUserId(userId);

		user = getUser(user);

		if (user == null) {
			return null;
		}

		openId = user.getOpenId();

		if (StringUtils.isEmpty(openId)) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_WX_USER_ID + key, openId);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_WX_USER_ID + key, e);
		}

		return openId;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private User getUser(User user) {
		try {
			return userWeixinDao.getUser(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

}
