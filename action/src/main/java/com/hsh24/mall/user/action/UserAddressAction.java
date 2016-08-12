package com.hsh24.mall.user.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsh24.mall.api.address.bo.Address;
import com.hsh24.mall.api.user.IUserAddressService;
import com.hsh24.mall.api.user.bo.UserAddress;
import com.hsh24.mall.framework.action.BaseAction;
import com.hsh24.mall.framework.bo.BooleanResult;

/**
 * 
 * @author JiakunXu
 * 
 */
@Controller
@Scope("request")
public class UserAddressAction extends BaseAction {

	private static final long serialVersionUID = -8769317353845217884L;

	@Resource
	private IUserAddressService userAddressService;

	private UserAddress userAddress;

	/**
	 * 创建收货地址并修改订单.
	 */
	private String tradeNo;

	public String index() {
		Address address = this.getAddress();

		userAddress = userAddressService.getUserAddress(this.getUser().getUserId(), address.getAddId());

		if (userAddress != null) {
			return SUCCESS;
		}

		userAddress = new UserAddress();

		userAddress.setProvince(address.getProvince());
		userAddress.setCity(address.getCity());
		userAddress.setArea(address.getArea());
		userAddress.setBackCode(address.getBackCode());

		return SUCCESS;
	}

	/**
	 * 创建修改收货地址.
	 * 
	 * @return
	 */
	public String create() {
		if (userAddress != null) {
			Address address = this.getAddress();

			userAddress.setMdmAddId(address.getAddId());
			userAddress.setProvince(address.getProvince());
			userAddress.setCity(address.getCity());
			userAddress.setArea(address.getArea());
			userAddress.setBackCode(address.getBackCode());
		}

		BooleanResult result = userAddressService.createUserAddress(this.getUser().getUserId(), userAddress, tradeNo);

		if (result.getResult()) {
			this.setResourceResult(result.getCode());
		} else {
			this.getServletResponse().setStatus(599);
			this.setResourceResult(result.getCode());
		}

		return RESOURCE_RESULT;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

}
