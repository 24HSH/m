package com.hsh24.mall.member.action;

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
public class MemberAction extends BaseAction {

	private static final long serialVersionUID = -994523812704630235L;

	/**
	 * 
	 * @return
	 */
	public String index() {
		return SUCCESS;
	}

}
