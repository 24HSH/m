package com.hsh24.mall.item.service.impl;

import java.util.List;

import com.hsh24.mall.api.item.IItemFileService;
import com.hsh24.mall.api.item.bo.ItemFile;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.item.dao.IItemFileDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemFileServiceImpl implements IItemFileService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemFileServiceImpl.class);

	private IItemFileDao itemFileDao;

	@Override
	public List<ItemFile> getItemFileList(Long shopId, Long itemId) {
		if (itemId == null) {
			return null;
		}

		ItemFile file = new ItemFile();
		file.setShopId(shopId);
		file.setItemId(itemId);

		return getItemFileList(file);
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	private List<ItemFile> getItemFileList(ItemFile file) {
		try {
			return itemFileDao.getItemFileList(file);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(file), e);
		}

		return null;
	}

	public IItemFileDao getItemFileDao() {
		return itemFileDao;
	}

	public void setItemFileDao(IItemFileDao itemFileDao) {
		this.itemFileDao = itemFileDao;
	}

}
