package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IEventEnterAgentDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class EventEnterAgentDaoImpl extends BaseDaoImpl implements IEventEnterAgentDao {

	@Override
	public Long createEventEnterAgent(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.event.enter.agent.createEventEnterAgent", content);
	}

}
