package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IMsgVoiceDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createMsgVoice(Content content);

}
