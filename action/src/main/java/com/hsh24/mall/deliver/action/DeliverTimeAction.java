package com.hsh24.mall.deliver.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.promise.IPromiseTimeService;
import com.hsh24.mall.api.promise.bo.PromiseTime;
import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class DeliverTimeAction extends BaseAction {

	private static final long serialVersionUID = -3921065837757228968L;

	@Resource
	private IPromiseTimeService promiseTimeService;

	private List<PromiseTime> promiseTimeList;

	/**
	 * 
	 * @return
	 */
	public String index() {
		promiseTimeList = promiseTimeService.getPromiseTimeList(0L);

		return SUCCESS;
	}

	public List<PromiseTime> getPromiseTimeList() {
		return promiseTimeList;
	}

	public void setPromiseTimeList(List<PromiseTime> promiseTimeList) {
		this.promiseTimeList = promiseTimeList;
	}

}
