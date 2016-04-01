package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IMsgTextDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MsgTextDaoImpl extends BaseDaoImpl implements IMsgTextDao {

	@Override
	public Long createMsgText(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.msg.text.createMsgText", content);
	}

}
