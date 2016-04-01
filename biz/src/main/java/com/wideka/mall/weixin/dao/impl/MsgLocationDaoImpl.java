package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IMsgLocationDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MsgLocationDaoImpl extends BaseDaoImpl implements IMsgLocationDao {

	@Override
	public Long createMsgLocation(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.msg.location.createMsgLocation", content);
	}

}
