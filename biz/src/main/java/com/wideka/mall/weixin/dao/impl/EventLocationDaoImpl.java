package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IEventLocationDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class EventLocationDaoImpl extends BaseDaoImpl implements IEventLocationDao {

	@Override
	public Long createEventLocation(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.event.location.createEventLocation", content);
	}

}
