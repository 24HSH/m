package com.hsh24.mall.item.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.item.IItemCatService;
import com.hsh24.mall.api.item.bo.ItemCat;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.item.dao.IItemCatDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemCatServiceImpl implements IItemCatService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemCatServiceImpl.class);

	private IMemcachedCacheService memcachedCacheService;

	private IItemCatDao itemCatDao;

	@Override
	public List<ItemCat> getItemCatList(String parentItemCId) {
		if (StringUtils.isBlank(parentItemCId)) {
			return null;
		}

		ItemCat itemCat = new ItemCat();
		try {
			itemCat.setParentItemCId(Long.valueOf(parentItemCId));
		} catch (NumberFormatException e) {
			logger.error(e);

			return null;
		}

		try {
			return itemCatDao.getItemCatList(itemCat);
		} catch (Exception e) {
			logger.error(e);

		}

		return null;
	}

	public IMemcachedCacheService getMemcachedCacheService() {
		return memcachedCacheService;
	}

	public void setMemcachedCacheService(IMemcachedCacheService memcachedCacheService) {
		this.memcachedCacheService = memcachedCacheService;
	}

	public IItemCatDao getItemCatDao() {
		return itemCatDao;
	}

	public void setItemCatDao(IItemCatDao itemCatDao) {
		this.itemCatDao = itemCatDao;
	}

}
