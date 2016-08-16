package com.hsh24.mall.address.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.address.IAddressService;
import com.hsh24.mall.api.address.bo.Address;
import com.hsh24.mall.api.weixin.IWeixinService;
import com.hsh24.mall.api.weixin.bo.Ticket;
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

	@Resource
	private IAddressService addressService;

	@Resource
	private IWeixinService weixinService;

	private List<Address> addressList;

	private String city;

	private String search;

	private Ticket ticket;

	private String addId;

	/**
	 * 
	 * @return
	 */
	public String index() {
		// test
		city = "杭州市";
		addressList = addressService.getAddressList(city, search);

		String requestURL = env.getProperty("appUrl") + "/address/index.htm";
		HttpServletRequest request = getServletRequest();
		String queryString = request.getQueryString();

		ticket =
			weixinService.getTicket(StringUtils.isEmpty(queryString) ? requestURL : requestURL + "?" + queryString);

		return SUCCESS;
	}

	public String select() {
		HttpSession session = this.getSession();
		session.setAttribute("ACEGI_SECURITY_LAST_ADDRESS", addressService.getAddress(addId));

		return SUCCESS;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getAddId() {
		return addId;
	}

	public void setAddId(String addId) {
		this.addId = addId;
	}

}
