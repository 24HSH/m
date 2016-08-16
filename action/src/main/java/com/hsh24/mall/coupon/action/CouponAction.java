package com.hsh24.mall.coupon.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.framework.action.BaseAction;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class CouponAction extends BaseAction {

	private static final long serialVersionUID = -5160096167239493467L;

	public String index() {
		return SUCCESS;
	}

}
