package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IEventLocationSelectDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class EventLocationSelectDaoImpl extends BaseDaoImpl implements IEventLocationSelectDao {

	@Override
	public Long createEventLocationSelect(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.event.location.select.createEventLocationSelect",
			content);
	}

}
