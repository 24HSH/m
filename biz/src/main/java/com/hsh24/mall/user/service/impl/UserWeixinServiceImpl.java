package com.hsh24.mall.user.service.impl;

import org.apache.commons.lang3.StringUtils;
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
public class UserWeixinServiceImpl implements IUserWeixinService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserWeixinServiceImpl.class);

	private TransactionTemplate transactionTemplate;

	private IMemcachedCacheService memcachedCacheService;

	private IUserService userService;

	private IOAuth2Service oauth2Service;

	private IUserWeixinDao userWeixinDao;

	@Override
	public User getUser(String accessToken, String openId) {
		if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId)) {
			return null;
		}

		final User u = new User();
		u.setOpenId(openId.trim());

		User user = getUser(u);
		if (user != null) {
			return userService.getUser(user.getUserId());
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

		u.setUserName(userInfo.getNickName());
		u.setPassport(openId);

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

	@Override
	public String getOpenId(Long userId) {
		if (userId == null) {
			return null;
		}

		final User u = new User();
		u.setUserId(userId);

		User user = getUser(u);

		if (user == null) {
			return null;
		}

		return user.getOpenId();
	}

	private User getUser(User user) {
		try {
			return userWeixinDao.getUser(user);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(user), e);
		}

		return null;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IOAuth2Service getOauth2Service() {
		return oauth2Service;
	}

	public void setOauth2Service(IOAuth2Service oauth2Service) {
		this.oauth2Service = oauth2Service;
	}

	public IUserWeixinDao getUserWeixinDao() {
		return userWeixinDao;
	}

	public void setUserWeixinDao(IUserWeixinDao userWeixinDao) {
		this.userWeixinDao = userWeixinDao;
	}

}
