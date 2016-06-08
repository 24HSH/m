package com.hsh24.mall.shop.service.impl;

import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.shop.IShopService;
import com.hsh24.mall.api.shop.bo.Shop;
import com.hsh24.mall.framework.exception.ServiceException;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.shop.dao.IShopDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ShopServiceImpl implements IShopService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ShopServiceImpl.class);

	private IMemcachedCacheService memcachedCacheService;

	private IShopDao shopDao;

	@Override
	public Shop getShop(Long shopId) {
		if (shopId == null) {
			return null;
		}

		String key = shopId.toString();

		Shop shop = null;

		try {
			shop = (Shop) memcachedCacheService.get(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key, e);
		}

		if (shop != null) {
			return shop;
		}

		shop = new Shop();
		shop.setShopId(shopId);

		try {
			shop = shopDao.getShop(shop);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(shop), e);

			return null;
		}

		if (shop == null) {
			return null;
		}

		// not null then set to cache
		try {
			memcachedCacheService.set(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key, shop);
		} catch (ServiceException e) {
			logger.error(IMemcachedCacheService.CACHE_KEY_SHOP_ID + key, e);
		}

		return shop;
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public IShopDao getShopDao() {
		return shopDao;
	}

	public void setShopDao(IShopDao shopDao) {
		this.shopDao = shopDao;
	}

}
