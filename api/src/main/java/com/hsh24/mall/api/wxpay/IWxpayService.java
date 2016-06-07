package com.hsh24.mall.api.wxpay;

import com.hsh24.mall.framework.exception.ServiceException;
import com.wideka.weixin.api.pay.bo.Refund;

/**
 * 微信支付.
 * 
 * @author JiakunXu
 * 
 */
public interface IWxpayService {

	String ERROR_MESSAGE = "微信支付接口调用失败！";

	String RETURN_CODE_SUCCESS = "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";

	String RETURN_CODE_FAIL = "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";

	/**
	 * 
	 * @param tradeNo
	 * @param body
	 * @param detail
	 * @param attach
	 * @param totalFee
	 * @param ip
	 * @param timeStart
	 * @param timeExpire
	 * @param openId
	 * @return
	 * @throws ServiceException
	 */
	String getBrandWCPayRequest(String tradeNo, String body, String detail, String attach, int totalFee, String ip,
		String timeStart, String timeExpire, String openId) throws ServiceException;

	/**
	 * 申请退款.
	 * 
	 * @param deviceInfo
	 * @param transactionId
	 * @param outTradeNo
	 * @param outRefundNo
	 * @param totalFee
	 * @param refundFee
	 * @param refundFeeType
	 * @return
	 * @throws ServiceException
	 */
	Refund refund(String deviceInfo, String transactionId, String outTradeNo, String outRefundNo, int totalFee,
		int refundFee, String refundFeeType) throws ServiceException;

}
