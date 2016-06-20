package com.hsh24.mall.item.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hsh24.mall.api.item.IItemFileService;
import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.IItemSkuService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.api.spec.ISpecService;
import com.hsh24.mall.api.spec.bo.SpecCat;
import com.hsh24.mall.api.spec.bo.SpecItem;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.item.dao.IItemDao;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemServiceImpl implements IItemService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(ItemServiceImpl.class);

	private IItemFileService itemFileService;

	private IItemSkuService itemSkuService;

	private ISpecService specService;

	private IItemDao itemDao;

	@Override
	public int getItemCount(Long shopId, Item item) {
		if (shopId == null || item == null) {
			return 0;
		}

		item.setShopId(shopId);
		item.setLimit(100);
		item.setOffset(0);

		try {
			return itemDao.getItemCount(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return 0;
	}

	@Override
	public List<Item> getItemList(Long shopId, Item item) {
		if (shopId == null || item == null) {
			return null;
		}

		item.setShopId(shopId);

		List<Item> itemList = getItemList(item);

		if (itemList == null || itemList.size() == 0) {
			return null;
		}

		// 获得商品更多信息
		List<Item> list = new ArrayList<Item>();

		for (Item ietm : itemList) {
			list.add(getItem(shopId, ietm.getItemId(), ietm));
		}

		return list;
	}

	@Override
	public Item getItem(Long shopId, String itemId) {
		if (shopId == null || StringUtils.isBlank(itemId)) {
			return null;
		}

		// 1. 获取商品基本信息
		Item item = new Item();
		item.setShopId(shopId);

		try {
			item.setItemId(Long.valueOf(itemId));
		} catch (NumberFormatException e) {
			logger.error(itemId);

			return null;
		}

		item = getItem(item);

		if (item == null) {
			return null;
		}

		return getItem(shopId, item.getItemId(), item);
	}

	private Item getItem(Long shopId, Long itemId, Item item) {
		// 2. 获取商品文件信息
		item.setItemFileList(itemFileService.getItemFileList(shopId, itemId));

		// 3. 获取商品 sku 信息
		List<ItemSku> skuList = itemSkuService.getItemSkuList(itemId);

		// 不存在 sku 信息 直接返回
		if (skuList == null || skuList.size() == 0) {
			// 设置价格区间 = item 总表价格
			item.setOriginRange(item.getOrigin().toString());
			item.setPriceRange(item.getPrice().toString());

			return item;
		}

		// titleList 获取第一个 sku 从中获取 specCat 信息
		ItemSku sku = skuList.get(0);
		String[] properties = sku.getProperties().split(";");

		String[] specCId = new String[properties.length];
		int i = 0;
		for (String id : properties) {
			String[] cid = id.split(":");
			specCId[i++] = cid[0];
		}

		List<SpecCat> specCatList = specService.getSpecCatList(shopId, specCId);
		// 根据 specCId[] 重新排序
		if (specCatList != null && specCatList.size() > 0) {
			Map<String, SpecCat> map = new HashMap<String, SpecCat>();
			for (SpecCat sc : specCatList) {
				map.put(sc.getSpecCId().toString(), sc);
			}

			specCatList = new ArrayList<SpecCat>();
			for (String cid : specCId) {
				specCatList.add(map.get(cid));
			}
		}

		// 遍历所有的已有 sku 获取 specItem 信息；遍历的同时，统计 金额最大最小
		// 原始价格 max0 min0
		// 会员价格 max1 min1
		BigDecimal max0 = BigDecimal.ZERO;
		BigDecimal min0 = new BigDecimal("100000000");
		BigDecimal max1 = BigDecimal.ZERO;
		BigDecimal min1 = new BigDecimal("100000000");

		String[] specItemId = new String[properties.length * skuList.size()];
		int j = 0;
		// 遍历所有
		for (ItemSku sk : skuList) {
			String[] ps = sk.getProperties().split(";");
			for (String id : ps) {
				String[] cid = id.split(":");
				specItemId[j++] = cid[1];
			}

			// 判断最大最小
			if (sk.getOrigin().compareTo(min0) == -1) {
				min0 = sk.getOrigin();
			}

			if (sk.getOrigin().compareTo(max0) == 1) {
				max0 = sk.getOrigin();
			}

			if (sk.getPrice().compareTo(min1) == -1) {
				min1 = sk.getPrice();
			}

			if (sk.getPrice().compareTo(max1) == 1) {
				max1 = sk.getPrice();
			}
		}

		// 设置价格区间 = item 总表价格
		item.setOriginRange(min0.toString() + " - " + max0.toString());
		item.setPriceRange(min1.toString() + " - " + max1.toString());

		List<SpecItem> specItemList = specService.getSpecItemList(shopId, specItemId);

		// 规格组合 黑色 大 ／ 红色 大
		if (specItemList != null && specItemList.size() > 0) {
			Map<Long, String> map = new HashMap<Long, String>();

			for (SpecItem specItem : specItemList) {
				map.put(specItem.getSpecItemId(), specItem.getSpecItemValue());
			}

			for (ItemSku sk : skuList) {
				List<String> specItemValueList = new ArrayList<String>();
				String[] ps = sk.getProperties().split(";");
				for (String id : ps) {
					String[] cid = id.split(":");
					specItemValueList.add(map.get(cid[1]));
				}

				sk.setSpecItemValueList(specItemValueList);
			}
		}

		// 选中的规格信息
		item.setSpecCat(JSON.toJSONString(specCatList).replace("'", "\\'"));
		item.setSpecItem(JSON.toJSONString(specItemList).replace("'", "\\'"));

		// sku 明细
		item.setSkuList(skuList);
		// 规格列表中 规格组合信息
		item.setSpecCatList(specCatList);

		return item;
	}

	@Override
	public Map<Long, Item> getItem(Long shopId, String[] itemId) {
		if (shopId == null || itemId == null || itemId.length == 0) {
			return null;
		}

		Item item = new Item();
		item.setShopId(shopId);
		item.setCodes(itemId);
		item.setLimit(itemId.length);
		item.setOffset(0);

		List<Item> itemList = getItemList(item);

		if (itemList == null || itemList.size() == 0) {
			return null;
		}

		Map<Long, Item> map = new HashMap<Long, Item>();

		for (Item ietm : itemList) {
			map.put(ietm.getItemId(), ietm);
		}

		return map;
	}

	@Override
	public BooleanResult validate(Long shopId, Long itemId, Long skuId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (itemId == null) {
			result.setCode("商品信息不能为空");
			return result;
		}

		if (skuId == null) {
			result.setCode("商品SKU信息不能为空");
			return result;
		}

		// 某一商品价格
		BigDecimal price = BigDecimal.ZERO;

		List<ItemSku> itemSkuList = itemSkuService.getItemSkuList(itemId);

		if (itemSkuList != null && itemSkuList.size() > 0) {
			// 根据 skuId 获得 item 并 验证
			Map<Long, ItemSku> map = itemSkuService.getItemSku(new String[] { skuId.toString() });
			if (map == null || map.size() == 0) {
				result.setCode("SKU信息不存在");
				return result;
			}

			ItemSku itemSku = map.get(skuId);
			if (itemSku == null) {
				result.setCode("SKU信息不存在");
				return result;
			}

			if (!itemId.equals(itemSku.getItemId())) {
				result.setCode("商品和SKU信息不匹配");
				return result;
			}

			price = itemSku.getPrice();
		} else {
			skuId = 0L;
		}

		// 根据 itemId 获得 item
		Map<Long, Item> map = getItem(shopId, new String[] { itemId.toString() });
		if (map == null || map.size() == 0) {
			result.setCode("商品信息不存在");
			return result;
		}

		Item item = map.get(itemId);
		if (item == null) {
			result.setCode("商品信息不存在");
			return result;
		}

		result.setCode(skuId.equals(0L) ? item.getPrice().toString() : price.toString());
		result.setResult(true);
		return result;
	}

	@Override
	public BooleanResult updateItemStock(Long shopId, String[] itemId, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (itemId == null || itemId.length == 0) {
			result.setCode("商品信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		try {
			itemDao.updateItem(shopId, itemId, modifyUser);
			result.setResult(true);
		} catch (Exception e) {
			logger.error("shopId:" + shopId + LogUtil.parserBean(itemId) + "modifyUser:" + modifyUser, e);

			result.setCode("更新商品库存信息失败");
			return result;
		}

		return result;
	}

	@Override
	public BooleanResult updateItemStock(Long shopId, List<Item> itemList, String modifyUser) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}

		if (itemList == null || itemList.size() == 0) {
			result.setCode("商品信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(modifyUser)) {
			result.setCode("操作人信息不能为空");
			return result;
		}

		try {
			itemDao.updateItem(shopId, itemList, modifyUser);
			result.setResult(true);
		} catch (Exception e) {
			logger.error("shopId:" + shopId + LogUtil.parserBean(itemList) + "modifyUser:" + modifyUser, e);

			result.setCode("更新商品库存信息失败");
			return result;
		}

		return result;
	}

	private List<Item> getItemList(Item item) {
		try {
			return itemDao.getItemList(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

	private Item getItem(Item item) {
		try {
			return itemDao.getItem(item);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(item), e);
		}

		return null;
	}

	public IItemFileService getItemFileService() {
		return itemFileService;
	}

	public void setItemFileService(IItemFileService itemFileService) {
		this.itemFileService = itemFileService;
	}

	public IItemSkuService getItemSkuService() {
		return itemSkuService;
	}

	public void setItemSkuService(IItemSkuService itemSkuService) {
		this.itemSkuService = itemSkuService;
	}

	public ISpecService getSpecService() {
		return specService;
	}

	public void setSpecService(ISpecService specService) {
		this.specService = specService;
	}

	public IItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}

}
