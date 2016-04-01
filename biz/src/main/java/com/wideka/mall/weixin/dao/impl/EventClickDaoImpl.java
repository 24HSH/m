package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IEventClickDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class EventClickDaoImpl extends BaseDaoImpl implements IEventClickDao {

	@Override
	public Long createEventClick(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.event.click.createEventClick", content);
	}

}
