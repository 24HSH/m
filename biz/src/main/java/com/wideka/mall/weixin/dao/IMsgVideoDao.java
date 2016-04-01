package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IMsgVideoDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createMsgVideo(Content content);

}
