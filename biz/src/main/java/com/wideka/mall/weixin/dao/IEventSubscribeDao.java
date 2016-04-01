package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IEventSubscribeDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createEventSubscribe(Content content);

}
