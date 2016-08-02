package com.hsh24.mall.address.action;

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
public class AddressAction extends BaseAction {

	private static final long serialVersionUID = 3763357543558098377L;

	/**
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

}
