package com.hsh24.mall.about.action;

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
public class AboutAction extends BaseAction {

	private static final long serialVersionUID = 119212378824324484L;

	/**
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

}
