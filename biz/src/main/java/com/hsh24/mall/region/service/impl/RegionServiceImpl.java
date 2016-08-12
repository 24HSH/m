package com.hsh24.mall.region.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hsh24.mall.api.region.IRegionService;
import com.hsh24.mall.api.region.bo.Region;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.region.dao.IRegionDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class RegionServiceImpl implements IRegionService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(RegionServiceImpl.class);

	@Resource
	private IRegionDao regionDao;

	@Override
	public String[] getRegion(Long regionId) {
		if (regionId == null) {
			return null;
		}

		String[] regionList = new String[5];

		Region region = new Region();
		region.setRegionId(regionId);

		region = getRegion(region);

		if (region == null) {
			return null;
		}

		int i = 0;

		while (region != null) {
			regionList[i++] = region.getRegionId().toString();

			region.setRegionId(region.getParentRegionId());
			region = getRegion(region);
		}

		return regionList;
	}

	/**
	 * 
	 * @param region
	 * @return
	 */
	private Region getRegion(Region region) {
		try {
			return regionDao.getRegion(region);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(region), e);
		}

		return null;
	}

}
