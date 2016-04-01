package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IEventLocationSelectDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createEventLocationSelect(Content content);

}
