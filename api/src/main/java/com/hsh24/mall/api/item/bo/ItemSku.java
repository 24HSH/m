package com.hsh24.mall.api.item.bo;

import java.math.BigDecimal;
import java.util.List;

import com.hsh24.mall.framework.bo.SearchInfo;

/**
 * 
 * @author JiakunXu
 * 
 */
public class ItemSku extends SearchInfo {

	private static final long serialVersionUID = 2661444535345405089L;

	private Long skuId;

	/**
	 * 商品ID.
	 */
	private Long itemId;

	/**
	 * sku的销售属性组合字符串（颜色，大小，等等，可通过类目API获取某类目下的销售属性）,格式是p1:v1;p2:v2.
	 */
	private String properties;

	/**
	 * sku所对应的销售属性的中文名字串，格式如：Pid1:vid1:pid_name1:vid_name1;Pid2:vid2:pid_name2:vid_name2.
	 */
	private String propertiesName;

	/**
	 * vid_name1 vid_name2.
	 */
	private List<String> specItemValueList;

	/**
	 * 属于这个sku的商品的价格 取值范围:0-100000000;精确到2位小数;单位:元。如:200.07，表示:200元7分.
	 */
	private BigDecimal price;

	/**
	 * 属于这个sku的商品的数量.
	 */
	private int stock;

	/**
	 * 属于这个sku的商品的原价格 取值范围:0-100000000;精确到2位小数;单位:元。如:200.07，表示:200元7分.
	 */
	private BigDecimal origin;

	/**
	 * Sku级别发货时间 2013-11-11.
	 */
	private String deliveryTime;

	/**
	 * 商品编码.
	 */
	private String code;

	/**
	 * 版本.
	 */
	private String verId;

	/**
	 * 状态 D:删除 U:正常.
	 */
	private String state;

	/**
	 * 操作人ID.
	 */
	private String modifyUser;

	// >>>>>>>>>>以下是辅助属性<<<<<<<<<<

	/**
	 * 购物车ID.
	 */
	private Long cartId;

	/**
	 * 购物车商品数量.
	 */
	private int quantity;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}

	public List<String> getSpecItemValueList() {
		return specItemValueList;
	}

	public void setSpecItemValueList(List<String> specItemValueList) {
		this.specItemValueList = specItemValueList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public BigDecimal getOrigin() {
		return origin;
	}

	public void setOrigin(BigDecimal origin) {
		this.origin = origin;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
