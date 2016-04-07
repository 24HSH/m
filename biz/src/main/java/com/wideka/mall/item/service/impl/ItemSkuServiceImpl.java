package com.wideka.mall.item.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wideka.mall.api.item.IItemSkuService;
import com.wideka.mall.api.item.bo.ItemSku;
import com.wideka.mall.framework.log.Logger4jCollection;
import com.wideka.mall.framework.log.Logger4jExtend;
import com.wideka.mall.framework.util.LogUtil;
import com.wideka.mall.item.dao.IItemSkuDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemSkuServiceImpl implements IItemSkuService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemSkuServiceImpl.class);

	private IItemSkuDao itemSkuDao;

	@Override
	public List<ItemSku> getItemSkuList(Long shopId, String itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, ItemSku> getItemSku(Long shopId, String[] skuId) {
		if (shopId == null || skuId == null || skuId.length == 0) {
			return null;
		}

		ItemSku sku = new ItemSku();
		sku.setCodes(skuId);
		sku.setLimit(skuId.length);
		sku.setOffset(0);

		List<ItemSku> itemSkuList = getItemSkuList(sku);

		if (itemSkuList == null || itemSkuList.size() == 0) {
			return null;
		}

		Map<Long, ItemSku> map = new HashMap<Long, ItemSku>();

		for (ItemSku ietm : itemSkuList) {
			map.put(ietm.getSkuId(), ietm);
		}

		return map;
	}

	private List<ItemSku> getItemSkuList(ItemSku item) {
		try {
			return itemSkuDao.getItemSkuList(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

	public IItemSkuDao getItemSkuDao() {
		return itemSkuDao;
	}

	public void setItemSkuDao(IItemSkuDao itemSkuDao) {
		this.itemSkuDao = itemSkuDao;
	}

}
