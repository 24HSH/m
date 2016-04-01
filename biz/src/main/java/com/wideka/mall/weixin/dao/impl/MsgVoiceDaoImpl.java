package com.wideka.mall.weixin.dao.impl;

import com.wideka.mall.framework.dao.impl.BaseDaoImpl;
import com.wideka.mall.weixin.dao.IMsgVoiceDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MsgVoiceDaoImpl extends BaseDaoImpl implements IMsgVoiceDao {

	@Override
	public Long createMsgVoice(Content content) {
		return (Long) getSqlMapClientTemplate().insert("weixin.msg.voice.createMsgVoice", content);
	}

}
