package com.hsh24.mall.item.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.mall.api.item.IItemSkuService;
import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.item.dao.IItemSkuDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class ItemSkuServiceImpl implements IItemSkuService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemSkuServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private IItemSkuDao itemSkuDao;

	@Override
	public List<ItemSku> getItemSkuList(Long itemId) {
		if (itemId == null) {
			return null;
		}

		ItemSku sku = new ItemSku();
		sku.setItemId(itemId);
		sku.setLimit(99);
		sku.setOffset(0);

		return getItemSkuList(sku);
	}

	@Override
	public Map<Long, ItemSku> getItemSku(String[] skuId) {
		if (skuId == null || skuId.length == 0) {
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

	@Override
	public BooleanResult updateItemSkuStock(final List<ItemSku> skuList, final String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (skuList == null || skuList.size() == 0) {
			result.setCode("商品规格信息不能为空");
			return result;
		}

		if (StringUtils.isEmpty(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		result = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				BooleanResult res = new BooleanResult();
				res.setResult(false);

				for (ItemSku itemSku : skuList) {
					try {
						itemSku.setModifyUser(modifyUser);
						int c = itemSkuDao.updateItemSku(itemSku);
						if (c != 1) {
							ts.setRollbackOnly();

							res.setCode("更新SKU库存信息失败");
							return res;
						}
					} catch (Exception e) {
						logger.error(LogUtil.parserBean(skuList), e);
						ts.setRollbackOnly();

						res.setCode("修改SKU库存信息失败");
						return res;
					}
				}

				res.setResult(true);
				return res;
			}
		});

		return result;
	}

	private List<ItemSku> getItemSkuList(ItemSku item) {
		try {
			return itemSkuDao.getItemSkuList(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

}
