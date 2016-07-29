package com.hsh24.mall.user.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.hsh24.mall.api.trade.ITradeService;
import com.hsh24.mall.api.trade.bo.Trade;
import com.hsh24.mall.api.user.IUserAddressService;
import com.hsh24.mall.api.user.bo.UserAddress;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.LogUtil;
import com.hsh24.mall.user.dao.IUserAddressDao;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class UserAddressServiceImpl implements IUserAddressService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(UserAddressServiceImpl.class);

	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private ITradeService tradeService;

	@Resource
	private IUserAddressDao userAddressDao;

	@Override
	public UserAddress getDefaultUserAddress(Long userId) {
		if (userId == null) {
			return null;
		}

		UserAddress userAddress = new UserAddress();
		userAddress.setUserId(userId);
		userAddress.setDefaults("Y");

		try {
			return userAddressDao.getUserAddress(userAddress);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userAddress), e);
		}

		return null;
	}

	@Override
	public BooleanResult createUserAddress(final Long userId, final UserAddress userAddress, final Long shopId,
		final String tradeNo) {
		BooleanResult res = transactionTemplate.execute(new TransactionCallback<BooleanResult>() {
			public BooleanResult doInTransaction(TransactionStatus ts) {
				// remove 默认收货地址
				BooleanResult result = removeDefaultUserAddress(userId);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 创建收货地址
				result = createUserAddress(userId, userAddress);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				// 修改交易收货地址
				Trade trade = new Trade();
				trade.setReceiverName(userAddress.getContactName());
				trade.setReceiverProvince(userAddress.getProvince());
				trade.setReceiverCity(userAddress.getCity());
				trade.setReceiverArea(userAddress.getArea());
				trade.setReceiverBackCode(userAddress.getBackCode());
				trade.setReceiverAddress(userAddress.getAddress());
				trade.setReceiverZip(userAddress.getPostalCode());
				trade.setReceiverTel(userAddress.getTel());

				result = tradeService.updateReceiver(userId, shopId, tradeNo, trade);
				if (!result.getResult()) {
					ts.setRollbackOnly();

					return result;
				}

				return result;
			}
		});

		if (res.getResult()) {
			res.setCode("保存成功");
		}
		return res;
	}

	/**
	 * 
	 * @param userId
	 * @param userAddress
	 * @return
	 */
	private BooleanResult createUserAddress(Long userId, UserAddress userAddress) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		if (userAddress == null) {
			result.setCode("收货地址信息不能为空");
			return result;
		}

		if (StringUtils.isBlank(userAddress.getContactName()) || userAddress.getContactName().length() > 10) {
			result.setCode("收货人信息不能为空或过长");
			return result;
		}

		if (StringUtils.isBlank(userAddress.getTel()) || userAddress.getTel().length() > 25) {
			result.setCode("联系电话信息不能为空或过长");
			return result;
		}

		if (StringUtils.isBlank(userAddress.getProvince())) {
			result.setCode("省不能为空");
			return result;
		}

		if (StringUtils.isBlank(userAddress.getCity())) {
			result.setCode("市不能为空");
			return result;
		}

		if (StringUtils.isBlank(userAddress.getArea())) {
			result.setCode("区不能为空");
			return result;
		}

		if (StringUtils.isBlank(userAddress.getAddress()) || userAddress.getAddress().length() > 50) {
			result.setCode("详细地址信息不能为空或过长");
			return result;
		}

		if (StringUtils.isNotBlank(userAddress.getPostalCode()) && userAddress.getPostalCode().length() > 10) {
			result.setCode("邮政编码信息不能为空或过长");
			return result;
		}

		userAddress.setUserId(userId);

		try {
			userAddressDao.createUserAddress(userAddress);
			result.setCode(userAddress.getAddId().toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userAddress), e);

			result.setCode("收货地址信息创建失败，请稍后再试");
		}

		return result;
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	private BooleanResult removeDefaultUserAddress(Long userId) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		UserAddress userAddress = new UserAddress();

		if (userId == null) {
			result.setCode("用户信息不能为空");
			return result;
		}

		userAddress.setUserId(userId);

		try {
			userAddressDao.removeDefaultUserAddress(userAddress);
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(userAddress), e);

			result.setCode("默认收货地址信息设置失败，请稍后再试");
		}

		return result;
	}

}
