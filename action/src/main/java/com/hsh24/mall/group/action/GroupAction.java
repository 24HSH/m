package com.hsh24.mall.group.action;

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
public class GroupAction extends BaseAction {

	private static final long serialVersionUID = 3598794925028263909L;

	/**
	 * 
	 * @return
	 */
	public String list() {
		return SUCCESS;
	}

}
