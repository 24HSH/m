package com.hsh24.mall.spec.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hsh24.mall.api.cache.IMemcachedCacheService;
import com.hsh24.mall.api.spec.ISpecService;
import com.hsh24.mall.api.spec.bo.SpecCat;
import com.hsh24.mall.api.spec.bo.SpecItem;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.spec.dao.ISpecCatDao;
import com.hsh24.mall.spec.dao.ISpecItemDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class SpecServiceImpl implements ISpecService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(SpecServiceImpl.class);

	@Resource
	private IMemcachedCacheService memcachedCacheService;

	@Resource
	private ISpecCatDao specCatDao;

	@Resource
	private ISpecItemDao specItemDao;

	@Override
	public List<SpecCat> getSpecCatList(Long shopId, String[] specCId) {
		if (shopId == null || specCId == null || specCId.length == 0) {
			return null;
		}

		SpecCat specCat = new SpecCat();
		specCat.setShopId(shopId);
		specCat.setCodes(specCId);

		return getSpecCatList(specCat);
	}

	@Override
	public List<SpecItem> getSpecItemList(Long shopId, String[] specItemId) {
		if (shopId == null || specItemId == null || specItemId.length == 0) {
			return null;
		}

		SpecItem specItem = new SpecItem();
		specItem.setShopId(shopId);
		specItem.setCodes(specItemId);

		return getSpecItemList(specItem);
	}

	private List<SpecCat> getSpecCatList(SpecCat specCat) {
		try {
			return specCatDao.getSpecCatList(specCat);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(specCat), e);
		}

		return null;
	}

	private List<SpecItem> getSpecItemList(SpecItem specItem) {
		try {
			return specItemDao.getSpecItemList(specItem);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(specItem), e);
		}

		return null;
	}

}
