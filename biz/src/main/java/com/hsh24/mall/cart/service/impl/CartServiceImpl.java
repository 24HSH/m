package com.hsh24.mall.cart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hsh24.mall.api.cart.ICartService;
import com.hsh24.mall.api.cart.bo.Cart;
import com.hsh24.mall.api.item.IItemFileService;
import com.hsh24.mall.api.item.IItemService;
import com.hsh24.mall.api.item.IItemSkuService;
import com.hsh24.mall.api.item.bo.Item;
import com.hsh24.mall.api.item.bo.ItemFile;
import com.hsh24.mall.api.item.bo.ItemSku;
import com.hsh24.mall.cart.dao.ICartDao;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class CartServiceImpl implements ICartService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(CartServiceImpl.class);

	@Resource
	private IItemService itemService;

	@Resource
	private IItemSkuService itemSkuService;

	@Resource
	private IItemFileService itemFileService;

	@Resource
	private ICartDao cartDao;

	@Override
	public BooleanResult createCart(Long userId, Long shopId, String itemId, String skuId, String quantity) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}
		cart.setShopId(shopId);

		if (StringUtils.isBlank(itemId)) {
			result.setCode("商品信息不能为空");
			return result;
		}
		try {
			cart.setItemId(Long.valueOf(itemId));
		} catch (NumberFormatException e) {
			logger.error(itemId, e);

			result.setCode("商品信息错误");
			return result;
		}

		if (StringUtils.isNotBlank(skuId)) {
			try {
				cart.setSkuId(Long.valueOf(skuId));
			} catch (NumberFormatException e) {
				logger.error(itemId, e);

				result.setCode("SKU信息错误");
				return result;
			}
		}

		// TODO
		cart.setPointsId(0L);

		if (StringUtils.isBlank(quantity)) {
			result.setCode("购买商品数量不能为空");
			return result;
		}

		int q;

		try {
			q = Integer.parseInt(quantity);
		} catch (Exception e) {
			logger.error(quantity, e);
			result.setCode("购买商品数量非数字类型");
			return result;
		}

		if (q == 0 || q < 1) {
			result.setCode("数量不能为0或负");
			return result;
		}

		cart.setQuantity(q);

		// 1. 更新购物车(相同规格商品 只增加数量)
		if (checkCart(cart) == 1) {
			// 获取 cartId
			List<Cart> cartList = getCartList(cart);
			if (cartList == null || cartList.size() == 0) {
				result.setCode("0");
			} else {
				result.setCode(cartList.get(0).getCartId().toString());
			}

			result.setResult(true);
			return result;
		}

		// 2. 创建购物车
		try {
			cartDao.createCart(cart);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);

			result.setCode("添加购物车失败，请稍后再试");
		}

		if (result.getResult()) {
			result.setCode(cart.getCartId().toString());
		}
		return result;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private int checkCart(Cart cart) {
		try {
			return cartDao.checkCart(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return -1;
	}

	@Override
	public int getCartCountByBlock(Long userId, Long blockId) {
		// userId 必填
		if (userId == null || blockId == null) {
			return 0;
		}

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setBlockId(blockId);

		try {
			return cartDao.getCartCount(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return 0;
	}

	@Override
	public int getCartCountByShop(Long userId, Long shopId) {
		// userId 必填
		if (userId == null || shopId == null) {
			return 0;
		}

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setShopId(shopId);

		try {
			return cartDao.getCartCount(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return 0;
	}

	@Override
	public List<Cart> getCartListByBlock(Long userId, Long blockId) {
		// userId 必填
		if (userId == null || blockId == null) {
			return null;
		}

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setBlockId(blockId);

		List<Cart> cartList = getCartList(cart);

		if (cartList == null || cartList.size() == 0) {
			return null;
		}

		return getCartList(null, cartList);
	}

	@Override
	public List<Cart> getCartListByShop(Long userId, Long shopId) {
		// userId 必填
		if (userId == null || shopId == null) {
			return null;
		}

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setShopId(shopId);

		List<Cart> cartList = getCartList(cart);

		if (cartList == null || cartList.size() == 0) {
			return null;
		}

		return getCartList(shopId, cartList);
	}

	private List<Cart> getCartList(Long shopId, List<Cart> cartList) {
		// 商品信息
		String[] itemId = new String[cartList.size()];
		int i = 0;
		for (Cart ca : cartList) {
			itemId[i++] = ca.getItemId().toString();
		}

		// sku信息
		String[] skuId = new String[cartList.size()];
		int j = 0;
		for (Cart ca : cartList) {
			Long id = ca.getSkuId();
			if (id == 0L) {
				continue;
			}
			skuId[j++] = id.toString();
		}

		// 2. 获取商品信息
		Map<Long, Item> itemMap = itemService.getItem(shopId, itemId);

		// 3. 获取sku信息
		Map<Long, ItemSku> itemSkuMap = j == 0 ? new HashMap<Long, ItemSku>() : itemSkuService.getItemSku(skuId);

		// 4. 获取商品文件信息
		Map<Long, List<ItemFile>> itemFileMap = itemFileService.getItemFileList(shopId, itemId);

		for (Cart ca : cartList) {
			// 商品名称 & 商品价格
			Item item = itemMap.get(ca.getItemId());
			ca.setItemName(item.getItemName());
			ca.setPrice(item.getPrice());

			// sku名称 & 商品价格
			Long id = ca.getSkuId();
			if (id != 0L) {
				ItemSku sku = itemSkuMap.get(id);
				ca.setPropertiesName(sku.getPropertiesName());
				ca.setPrice(sku.getPrice());
			}

			if (itemFileMap != null && !itemFileMap.isEmpty()) {
				ca.setItemFileList(itemFileMap.get(ca.getItemId()));
			}
		}

		return cartList;
	}

	@Override
	public BooleanResult removeCart(Long userId, Long shopId, String[] cartId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (shopId == null) {
			result.setCode("店铺信息不能为空");
			return result;
		}
		cart.setShopId(shopId);

		if (cartId == null || cartId.length == 0) {
			result.setCode("购物车商品信息不能为空");
			return result;
		}
		cart.setCodes(cartId);

		cart.setState(ICartService.STATE_REMOVE);

		int n = updateCart(cart);
		if (n == -1) {
			result.setCode("购物车更新失败");
			return result;
		}

		result.setCode("删除成功");
		result.setResult(true);

		return result;
	}

	@Override
	public BooleanResult updateQuantity(Long userId, Long shopId, String cartId, String quantity) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("0");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (shopId == null) {
			result.setCode("0");
			return result;
		}
		cart.setShopId(shopId);

		if (StringUtils.isBlank(cartId)) {
			result.setCode("0");
			return result;
		}
		try {
			cart.setCartId(Long.valueOf(cartId));
		} catch (NumberFormatException e) {
			logger.error(cartId, e);

			result.setCode("0");
			return result;
		}

		if (StringUtils.isBlank(quantity)) {
			result.setCode("1");
			return result;
		}

		int q;

		try {
			q = Integer.parseInt(quantity);
		} catch (Exception e) {
			logger.error(quantity, e);
			result.setCode("1");
			return result;
		}

		if (q == 0 || q < 1) {
			result.setCode("1");
			return result;
		}

		cart.setQuantity(q);

		int n = updateQuantity(cart);
		if (n != 1) {
			result.setCode(String.valueOf(q - 1));
			return result;
		}

		result.setCode(String.valueOf(q));
		result.setResult(true);

		return result;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private int updateQuantity(Cart cart) {
		try {
			return cartDao.updateQuantity(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return -1;
	}

	@Override
	public Cart getCartStats(Long userId, Long shopId, String[] cartId) {
		if (userId == null || shopId == null || cartId == null || cartId.length == 0) {
			return new Cart();
		}

		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setShopId(shopId);
		cart.setCodes(cartId);

		try {
			return cartDao.getCartStats(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return cart;
	}

	@Override
	public BooleanResult finishCart(Long userId, String[] cartId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		Cart cart = new Cart();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}
		cart.setUserId(userId);
		cart.setModifyUser(userId.toString());

		if (cartId == null || cartId.length == 0) {
			result.setCode("购物车商品信息不能为空");
			return result;
		}
		cart.setCodes(cartId);

		cart.setState(ICartService.STATE_FINISH);

		int n = updateCart(cart);
		if (n == -1) {
			result.setCode("购物车更新失败");
			return result;
		}

		result.setResult(true);
		return result;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private List<Cart> getCartList(Cart cart) {
		try {
			return cartDao.getCartList(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return null;
	}

	/**
	 * 
	 * @param cart
	 * @return
	 */
	private int updateCart(Cart cart) {
		try {
			return cartDao.updateCart(cart);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(cart), e);
		}

		return -1;
	}

}
