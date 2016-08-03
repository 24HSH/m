package com.hsh24.mall.address.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.mall.address.dao.IAddressDao;
import com.hsh24.mall.api.address.IAddressService;
import com.hsh24.mall.api.address.bo.Address;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class AddressServiceImpl implements IAddressService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(AddressServiceImpl.class);

	@Resource
	private IAddressDao addressDao;

	@Override
	public List<Address> getAddressList(String regionId, String search) {
		if (StringUtils.isBlank(regionId)) {
			return null;
		}

		Address address = new Address();

		try {
			address.setRegionId(Long.valueOf(regionId));
		} catch (NumberFormatException e) {
			logger.error(regionId, e);

			return null;
		}

		if (StringUtils.isNotBlank(search)) {
			address.setSearch(search.trim());
		}

		try {
			return addressDao.getAddressList(address);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(address), e);
		}

		return null;
	}

	@Override
	public Address getAddress(String addId) {
		if (StringUtils.isBlank(addId)) {
			return null;
		}

		Address address = new Address();

		try {
			address.setAddId(Long.valueOf(addId));
		} catch (NumberFormatException e) {
			logger.error(addId, e);

			return null;
		}

		try {
			return addressDao.getAddress(address);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(address), e);
		}

		return null;
	}

}
