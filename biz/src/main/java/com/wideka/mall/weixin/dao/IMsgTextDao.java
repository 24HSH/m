package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IMsgTextDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createMsgText(Content content);

}
