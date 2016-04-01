package com.wideka.mall.weixin.service.impl;

import com.wideka.mall.api.weixin.IMsgImageService;
import com.wideka.mall.framework.bo.BooleanResult;
import com.wideka.mall.framework.log.Logger4jCollection;
import com.wideka.mall.framework.log.Logger4jExtend;
import com.wideka.mall.framework.util.LogUtil;
import com.wideka.mall.weixin.dao.IMsgImageDao;
import com.wideka.weixin.api.callback.bo.Content;

/**
 * 
 * @author JiakunXu
 * 
 */
public class MsgImageServiceImpl implements IMsgImageService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(MsgImageServiceImpl.class);

	private IMsgImageDao msgImageDao;

	@Override
	public BooleanResult createMsgImage(Content content) {
		BooleanResult result = new BooleanResult();
		result.setResult(false);

		if (content == null) {
			result.setCode("content 信息不能为空。");
			return result;
		}

		try {
			Long id = msgImageDao.createMsgImage(content);
			result.setCode(id.toString());
			result.setResult(true);
		} catch (Exception e) {
			logger.error(LogUtil.parserBean(content), e);

			result.setCode("写入 weixin_tb_msg_image 表失败。");
		}

		return result;
	}

	public IMsgImageDao getMsgImageDao() {
		return msgImageDao;
	}

	public void setMsgImageDao(IMsgImageDao msgImageDao) {
		this.msgImageDao = msgImageDao;
	}

}
