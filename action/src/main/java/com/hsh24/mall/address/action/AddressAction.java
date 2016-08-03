package com.hsh24.mall.address.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.address.IAddressService;
import com.hsh24.mall.api.address.bo.Address;
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

	private List<Address> addressList;

	private String regionId;

	private String search;

	private String addId;

	/**
	 * 
	 * @return
	 */
	public String index() {
		// test
		regionId = "0";
		addressList = addressService.getAddressList(regionId, search);

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

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getAddId() {
		return addId;
	}

	public void setAddId(String addId) {
		this.addId = addId;
	}

}
