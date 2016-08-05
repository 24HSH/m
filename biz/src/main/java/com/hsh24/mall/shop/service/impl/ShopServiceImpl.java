package com.hsh24.mall.shop.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

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
@Service
public class ShopServiceImpl implements IShopService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ShopServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private IShopDao shopDao;

	@Override
	public List<Shop> getShopList(Long blockId) {
		if (blockId == null) {
			return null;
		}

		Shop shop = new Shop();
		shop.setBlockId(blockId);

		try {
			return shopDao.getShopList(shop);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(shop), e);

		}

		return null;
	}

	@Override
	public List<Shop> getShopList(String[] shopId) {
		if (shopId == null || shopId.length == 0) {
			return null;
		}

		Shop shop = new Shop();
		shop.setCodes(shopId);

		try {
			return shopDao.getShopList(shop);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(shop), e);

		}

		return null;
	}

	@Override
	public Shop getShop(String shopId) {
		if (StringUtils.isBlank(shopId)) {
			return null;
		}

		String key = shopId.trim();

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
		try {
			shop.setShopId(Long.valueOf(shopId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

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

}
