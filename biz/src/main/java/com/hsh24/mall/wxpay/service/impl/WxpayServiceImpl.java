package com.hsh24.mall.wxpay.service.impl;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.hsh24.mall.api.wxpay.IWxpayService;
import com.hsh24.mall.api.wxpay.bo.Wxpay;
import com.hsh24.mall.framework.exception.ServiceException;
import com.hsh24.mall.framework.util.UUIDUtil;
import com.wideka.weixin.api.pay.IRefundService;
import com.wideka.weixin.api.pay.IUnifiedOrderService;
import com.wideka.weixin.api.pay.bo.Refund;
import com.wideka.weixin.api.pay.bo.UnifiedOrder;
import com.wideka.weixin.framework.util.EncryptUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
public class WxpayServiceImpl implements IWxpayService {

	private IUnifiedOrderService unifiedOrderService;

	private IRefundService refundService;

	private String appId;

	private String appSecret;

	private String mchId;

	private String notifyUrl;

	private String key;

	private String sslPath;

	@Override
	public String getBrandWCPayRequest(String tradeNo, String body, String detail, String attach, int totalFee,
		String ip, String timeStart, String timeExpire, String openId) throws ServiceException {
		Wxpay wxpay = new Wxpay();

		wxpay.setAppId(appId);
		wxpay.setTimeStamp(Long.toString(System.currentTimeMillis() / 1000));
		wxpay.setNonceStr(UUIDUtil.generate());
		wxpay.setPackageValue("prepay_id="
			+ getPrePayId(appId, mchId, body, detail, attach, tradeNo, totalFee, ip, timeStart, timeExpire, notifyUrl,
				openId, key));
		wxpay.setSignType("MD5");

		StringBuilder sign = new StringBuilder();
		sign.append("appId=").append(wxpay.getAppId());
		sign.append("&nonceStr=").append(wxpay.getNonceStr());
		sign.append("&package=").append(wxpay.getPackageValue());
		sign.append("&signType=").append(wxpay.getSignType());
		sign.append("&timeStamp=").append(wxpay.getTimeStamp());

		sign.append("&key=").append(key);

		try {
			wxpay.setPaySign(EncryptUtil.encryptMD5(sign.toString()).toUpperCase());
		} catch (IOException e) {
			throw new ServiceException(e.getMessage());
		}

		return JSON.toJSONString(wxpay);
	}

	/**
	 * 
	 * @param appId
	 * @param mchId
	 * @param body
	 * @param detail
	 * @param attach
	 * @param tradeNo
	 * @param totalFee
	 * @param ip
	 * @param timeStart
	 * @param timeExpire
	 * @param notifyUrl
	 * @param openId
	 * @param key
	 * @return
	 */
	private String getPrePayId(String appId, String mchId, String body, String detail, String attach, String tradeNo,
		int totalFee, String ip, String timeStart, String timeExpire, String notifyUrl, String openId, String key) {
		UnifiedOrder unifiedOrder = new UnifiedOrder();

		unifiedOrder.setAppId(appId);
		unifiedOrder.setMchId(mchId);
		unifiedOrder.setDeviceInfo("WEB");
		unifiedOrder.setNonceStr(UUIDUtil.generate());
		// unifiedOrder.setSign("");
		unifiedOrder.setBody(body);
		unifiedOrder.setDetail(detail);
		unifiedOrder.setAttach(attach);
		unifiedOrder.setOutTradeNo(tradeNo);
		unifiedOrder.setFeeType("CNY");
		unifiedOrder.setTotalFee(totalFee);
		unifiedOrder.setSpbillCreateIp(ip);
		unifiedOrder.setTimeStart(timeStart);
		unifiedOrder.setTimeExpire(timeExpire);
		unifiedOrder.setGoodsTag("");
		unifiedOrder.setNotifyUrl(notifyUrl);
		unifiedOrder.setTradeType("JSAPI");
		unifiedOrder.setProductId("");
		unifiedOrder.setLimitPay("");
		unifiedOrder.setOpenId(openId);

		return unifiedOrderService.unifiedOrder(unifiedOrder, key);
	}

	@Override
	public Refund refund(String deviceInfo, String transactionId, String outTradeNo, String outRefundNo, int totalFee,
		int refundFee, String refundFeeType) throws ServiceException {
		Refund refund = new Refund();

		refund.setAppId(appId);
		refund.setMchId(mchId);
		refund.setDeviceInfo(deviceInfo);
		refund.setNonceStr(UUIDUtil.generate());
		// refund.setSign("");
		refund.setTransactionId(transactionId);
		refund.setOutTradeNo(outTradeNo);
		refund.setOutRefundNo(outRefundNo);
		refund.setTotalFee(totalFee);
		refund.setRefundFee(refundFee);
		refund.setRefundFeeType(refundFeeType);
		refund.setOpUserId(mchId);

		try {
			return refundService.refund(refund, key, sslPath);
		} catch (RuntimeException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public IUnifiedOrderService getUnifiedOrderService() {
		return unifiedOrderService;
	}

	public void setUnifiedOrderService(IUnifiedOrderService unifiedOrderService) {
		this.unifiedOrderService = unifiedOrderService;
	}

	public IRefundService getRefundService() {
		return refundService;
	}

	public void setRefundService(IRefundService refundService) {
		this.refundService = refundService;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSslPath() {
		return sslPath;
	}

	public void setSslPath(String sslPath) {
		this.sslPath = sslPath;
	}

}
