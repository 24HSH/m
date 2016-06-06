package com.hsh24.mall.wxpay.dao.impl;

import com.hsh24.mall.framework.dao.impl.BaseDaoImpl;
import com.hsh24.mall.wxpay.dao.IWxpayDao;
import com.wideka.weixin.api.pay.bo.WxNotify;

/**
 * 
 * @author JiakunXu
 * 
 */
public class WxpayDaoImpl extends BaseDaoImpl implements IWxpayDao {

	@Override
	public void createWxNotify(WxNotify wxNotify) {
		getSqlMapClientTemplate().insert("wxpay.createWxNotify", wxNotify);
	}

}
