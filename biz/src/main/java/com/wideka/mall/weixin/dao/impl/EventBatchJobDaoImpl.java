package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IEventBatchJobDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class EventBatchJobDaoImpl extends BaseDaoImpl implements IEventBatchJobDao {

	@Override
	public Long createEventBatchJob(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.event.batch.job.createEventBatchJob", content);
	}

}
