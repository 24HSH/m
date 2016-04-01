package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IMsgVideoDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MsgVideoDaoImpl extends BaseDaoImpl implements IMsgVideoDao {

	@Override
	public Long createMsgVideo(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.msg.video.createMsgVideo", content);
	}

}
