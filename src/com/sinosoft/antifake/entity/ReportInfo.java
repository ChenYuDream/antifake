package com.sinosoft.antifake.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "report_info")
public class ReportInfo extends IdEntity {
	private String clientName;
	private String clientPhone;
	private String salesAddress;
	private String salesName;
	private String salesPhone;
	private String productType;
	private String amount;
	private String labelNo;
	private String materialNo;
	private String createTime;
	private String reportType;
	private String client;
	private String ip;
	private String gpsAddress;
	private String queryId;
	private String tamperContent;
	private String comments;
	private String state;
	/*private String queryClientName;
	private String queryClientPhone;
	private String queryIsExist;*/
	
	
	
	/*public String getQueryIsExist() {
		return queryIsExist;
	}

	public void setQueryIsExist(String queryIsExist) {
		this.queryIsExist = queryIsExist;
	}

	public String getQueryClientName() {
		return queryClientName;
	}

	public void setQueryClientName(String queryClientName) {
		this.queryClientName = queryClientName;
	}

	public String getQueryClientPhone() {
		return queryClientPhone;
	}

	public void setQueryClientPhone(String queryClientPhone) {
		this.queryClientPhone = queryClientPhone;
	}*/
	
	
	public String getQueryId() {
		return queryId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getTamperContent() {
		return tamperContent;
	}

	public void setTamperContent(String tamperContent) {
		this.tamperContent = tamperContent;
	}

	public String getGpsAddress() {
		return gpsAddress;
	}

	public void setGpsAddress(String gpsAddress) {
		this.gpsAddress = gpsAddress;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientPhone() {
		return clientPhone;
	}

	public String getMaterialNo() {
		return materialNo;
	}

	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}

	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	public String getSalesAddress() {
		return salesAddress;
	}

	public void setSalesAddress(String salesAddress) {
		this.salesAddress = salesAddress;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesPhone() {
		return salesPhone;
	}

	public void setSalesPhone(String salesPhone) {
		this.salesPhone = salesPhone;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLabelNo() {
		return labelNo;
	}

	public void setLabelNo(String labelNo) {
		this.labelNo = labelNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
