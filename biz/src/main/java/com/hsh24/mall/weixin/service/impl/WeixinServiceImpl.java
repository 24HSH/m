package com.hsh24.mall.weixin.service.impl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hsh24.mall.api.weixin.ITicketService;
import com.hsh24.mall.api.weixin.IWeixinService;
import com.hsh24.mall.api.weixin.bo.Ticket;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.UUIDUtil;
import com.wideka.weixin.framework.util.EncryptUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Service
public class WeixinServiceImpl implements IWeixinService {

	private Logger4jExtend logger = Logger4jCollection.getLogger(WeixinServiceImpl.class);

	@Resource
	private ITicketService ticketService;

	@Value("${weixin.app.id}")
	private String appId;

	@Value("${weixin.app.secret}")
	private String appSecret;

	@Value("${weixin.corp.id}")
	private String corpId;

	@Value("${weixin.corp.secret}")
	private String corpSecret;

	@Override
	public Ticket getTicket(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		BooleanResult result = ticketService.getTicket(appId, appSecret);
		if (!result.getResult()) {
			return null;
		}

		String t = result.getCode();
		String nonceStr = UUIDUtil.generate();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String signature;

		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=").append(t).append("&noncestr=").append(nonceStr).append("&timestamp=")
			.append(timestamp).append("&url=").append(url.trim());

		try {
			signature = EncryptUtil.encryptSHA(sb.toString());
		} catch (IOException e) {
			logger.error(e);
			return null;
		}

		Ticket ticket = new Ticket();
		ticket.setAppId(appId);
		ticket.setNonceStr(nonceStr);
		ticket.setTimestamp(timestamp);
		ticket.setSignature(signature);

		return ticket;
	}

}
