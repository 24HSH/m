package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IEventLocationDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createEventLocation(Content content);

}
