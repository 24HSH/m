package com.wideka.mall.weixin.service.impl;

import com.wideka.mall.api.weixin.IEventClickService;
import com.wideka.mall.framework.bo.BooleanResult;
import com.wideka.mall.framework.log.Logger4jCollection;
import com.wideka.mall.framework.log.Logger4jExtend;
import com.wideka.mall.framework.util.LogUtil;
import com.wideka.mall.weixin.dao.IEventClickDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class EventClickServiceImpl implements IEventClickService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(EventClickServiceImpl.class);

	private IEventClickDao eventClickDao;

	@Override
	public BooleanResult createEventClick(Content content) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (content == null) {
			result.setCode("content 信息不能为空。");
			return result;
		}

		try {
			Long id = eventClickDao.createEventClick(content);
			result.setCode(id.toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(content), e);

			result.setCode("写入 weixin_tb_event_click 表失败。");
		}

		return result;
	}

	public IEventClickDao getEventClickDao() {
		return eventClickDao;
	}

	public void setEventClickDao(IEventClickDao eventClickDao) {
		this.eventClickDao = eventClickDao;
	}

}
