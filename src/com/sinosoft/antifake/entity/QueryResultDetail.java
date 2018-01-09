package com.sinosoft.antifake.entity;

import javax.xml.bind.annotation.XmlElement;

public class QueryResultDetail {
	
	private String lableNo;
	private String materialNo;
	private String description;
	private String week;
	private String serialNo;
	private String factory;
	private String product;
	private String packageDate;
	private String orderNo;
	
	@XmlElement(name = "LableNo")
	public String getLableNo() {
		return lableNo;
	}
	public void setLableNo(String lableNo) {
		this.lableNo = lableNo;
	}
	@XmlElement(name = "MaterialNo")
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	@XmlElement(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlElement(name = "Week")
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	@XmlElement(name = "SerialNo")
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@XmlElement(name = "Factory")
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	@XmlElement(name = "Product")
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	@XmlElement(name = "PackageDate")
	public String getPackageDate() {
		return packageDate;
	}
	public void setPackageDate(String packageDate) {
		this.packageDate = packageDate;
	}
	@XmlElement(name = "OrderNo")
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	

}
