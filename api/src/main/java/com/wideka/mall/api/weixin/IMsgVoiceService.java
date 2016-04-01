package com.wideka.mall.api.weixin;

import com.wideka.mall.framework.bo.BooleanResult;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public interface IMsgVoiceService {

	/**
	 * 
	 * @param content
	 * @return
	 */
	BooleanResult createMsgVoice(Content content);

}
