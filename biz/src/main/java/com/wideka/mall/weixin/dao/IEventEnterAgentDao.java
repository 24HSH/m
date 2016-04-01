package com.wideka.mall.weixin.dao;

import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IEventEnterAgentDao {

	/**
	 * 
	 * @param content
	 * @return
	 */
	Long createEventEnterAgent(Content content);

}
