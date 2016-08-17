package com.hsh24.mall.deliver.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.deliver.IDeliverTimeService;
import com.hsh24.mall.api.promise.IPromiseTimeService;
import com.hsh24.mall.api.promise.bo.PromiseTime;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;
import com.hsh24.mall.framework.log.Logger4jCollection;
import com.hsh24.mall.framework.log.Logger4jExtend;
import com.hsh24.mall.framework.util.DateUtil;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class DeliverTimeAction extends BaseAction {

	private static final long serialVersionUID = -3921065837757228968L;

	private Logger4jExtend logger = Logger4jCollection.getLogger(DeliverTimeAction.class);

	@Resource
	private IPromiseTimeService promiseTimeService;

	@Resource
	private IDeliverTimeService deliverTimeService;

	private List<PromiseTime> promiseTimeList;

	private String tradeNo;

	private String date;

	private String startTime;

	private String endTime;

	/**
	 * 
	 * @return
	 */
	public String index() {
		promiseTimeList = promiseTimeService.getPromiseTimeList(0L);

		if (StringUtils.isNotBlank(date)) {
			try {
				if (DateUtil.getQuot(DateUtil.datetime(date, DateUtil.DEFAULT_DATE_FORMAT), new Date()) > 0) {
					date = "";
					startTime = "";
					endTime = "";
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String set() {
		BooleanResult result =
			deliverTimeService.setDeliverTime(this.getUser().getUserId(), tradeNo, date, startTime, endTime);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public List<PromiseTime> getPromiseTimeList() {
		return promiseTimeList;
	}

	public void setPromiseTimeList(List<PromiseTime> promiseTimeList) {
		this.promiseTimeList = promiseTimeList;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
