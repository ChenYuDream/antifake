package com.sinosoft.antifake.entity;

/*
 *  Created on Wed Oct 22 15:22:46 GMT+08:00 2014
 *
 */
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ReportInfoHis Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
@Entity
@Table(name = "report_info_his")
public class ReportInfoHis extends IdEntity{

	protected String reportId;
	protected String clientName;
	protected String clientPhone;
	protected String salesAddress;
	protected String salesName;
	protected String salesPhone;
	protected String productType;
	protected String amount;
	protected String labelNo;
	protected String createTime;
	protected String materialNo;
	protected String client;
	protected String reportType;
	protected String ip;
	protected String gpsAddress;
	protected String queryId;
	protected String tamperContent;
	protected String comments;
	protected String state;


  /**
	*
	* Default Empty Constructor for class ReportInfoHis
	*
	*/
	public ReportInfoHis () {
		super();
	}
	public ReportInfoHis (
			String in_reportId
			,String in_clientName
			,String in_clientPhone
			,String in_salesAddress
			,String in_salesName
			,String in_salesPhone
			,String in_productType
			,String in_amount
			,String in_labelNo
			,String in_createTime
			,String in_materialNo
			,String in_client
			,String in_reportType
			,String in_ip
			,String in_gpsAddress
			,String in_queryId
			,String in_tamperContent
			,String in_comments
			,String in_state
	        ) {
			this.setReportId(in_reportId);
			this.setClientName(in_clientName);
			this.setClientPhone(in_clientPhone);
			this.setSalesAddress(in_salesAddress);
			this.setSalesName(in_salesName);
			this.setSalesPhone(in_salesPhone);
			this.setProductType(in_productType);
			this.setAmount(in_amount);
			this.setLabelNo(in_labelNo);
			this.setCreateTime(in_createTime);
			this.setMaterialNo(in_materialNo);
			this.setClient(in_client);
			this.setReportType(in_reportType);
			this.setIp(in_ip);
			this.setGpsAddress(in_gpsAddress);
			this.setQueryId(in_queryId);
			this.setTamperContent(in_tamperContent);
			this.setComments(in_comments);
			this.setState(in_state);
	    }

    
  /**
	*
	* @return String
	*/
	public String getReportId() {
		return this.reportId;
	}
	
  /**
	* Set the reportId
	*/	
	public void setReportId(String aValue) {
		this.reportId = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getClientName() {
		return this.clientName;
	}
	
  /**
	* Set the clientName
	*/	
	public void setClientName(String aValue) {
		this.clientName = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getClientPhone() {
		return this.clientPhone;
	}
	
  /**
	* Set the clientPhone
	*/	
	public void setClientPhone(String aValue) {
		this.clientPhone = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSalesAddress() {
		return this.salesAddress;
	}
	
  /**
	* Set the salesAddress
	*/	
	public void setSalesAddress(String aValue) {
		this.salesAddress = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSalesName() {
		return this.salesName;
	}
	
  /**
	* Set the salesName
	*/	
	public void setSalesName(String aValue) {
		this.salesName = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSalesPhone() {
		return this.salesPhone;
	}
	
  /**
	* Set the salesPhone
	*/	
	public void setSalesPhone(String aValue) {
		this.salesPhone = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getProductType() {
		return this.productType;
	}
	
  /**
	* Set the productType
	*/	
	public void setProductType(String aValue) {
		this.productType = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getAmount() {
		return this.amount;
	}
	
  /**
	* Set the amount
	*/	
	public void setAmount(String aValue) {
		this.amount = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getLabelNo() {
		return this.labelNo;
	}
	
  /**
	* Set the labelNo
	*/	
	public void setLabelNo(String aValue) {
		this.labelNo = aValue;
	}	
  /**
	*
	* @return java.util.Date
	*/
	public String getCreateTime() {
		return this.createTime;
	}
	
  /**
	* Set the createTime
	*/	
	public void setCreateTime(String aValue) {
		this.createTime = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getMaterialNo() {
		return this.materialNo;
	}
	
  /**
	* Set the materialNo
	*/	
	public void setMaterialNo(String aValue) {
		this.materialNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getClient() {
		return this.client;
	}
	
  /**
	* Set the client
	*/	
	public void setClient(String aValue) {
		this.client = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getReportType() {
		return this.reportType;
	}
	
  /**
	* Set the reportType
	*/	
	public void setReportType(String aValue) {
		this.reportType = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getIp() {
		return this.ip;
	}
	
  /**
	* Set the ip
	*/	
	public void setIp(String aValue) {
		this.ip = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getGpsAddress() {
		return this.gpsAddress;
	}
	
  /**
	* Set the gpsAddress
	*/	
	public void setGpsAddress(String aValue) {
		this.gpsAddress = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getQueryId() {
		return this.queryId;
	}
	
  /**
	* Set the queryId
	*/	
	public void setQueryId(String aValue) {
		this.queryId = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getTamperContent() {
		return this.tamperContent;
	}
	
  /**
	* Set the tamperContent
	*/	
	public void setTamperContent(String aValue) {
		this.tamperContent = aValue;
	}	
  /**
	*
	* @return Object
	*/
	public String getComments() {
		return this.comments;
	}
	
  /**
	* Set the comments
	*/	
	public void setComments(String aValue) {
		this.comments = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getState() {
		return this.state;
	}
	
  /**
	* Set the state
	*/	
	public void setState(String aValue) {
		this.state = aValue;
	}	

	
}