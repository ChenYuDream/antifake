package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Sun Sep 28 10:37:47 GMT+08:00 2014
 *
 */
import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * ReportInfo Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class ReportInfo extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String id;
	protected String clientName;
	protected String clientPhone;
	protected String salesAddress;
	protected String salesName;
	protected String salesPhone;
	protected String productType;
	protected String amount;
	protected String labelNo;
	protected java.util.Date createTime;
	protected String materialNo;
	protected String client;
	protected String reportType;
	protected String ip;
	protected String gpsAddress;
	protected String queryId;
	protected String tamperContent;
	protected Object comments;


  /**
	*
	* Default Empty Constructor for class ReportInfo
	*
	*/
	public ReportInfo () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class ReportInfo
	*
	*/
	public ReportInfo (
		 String in_id
		,String in_clientName
		,String in_clientPhone
		,String in_salesAddress
		,String in_salesName
		,String in_salesPhone
		,String in_productType
		,String in_amount
		,String in_labelNo
		,java.util.Date in_createTime
		,String in_materialNo
		,String in_client
		,String in_reportType
		,String in_ip
		,String in_gpsAddress
		,String in_queryId
		,String in_tamperContent
		,Object in_comments
        ) {
		this.setId(in_id);
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
    }

    
  /**
	*
	* @return String
	*/
	public String getId() {
		return this.id;
	}
	
  /**
	* Set the id
	*/	
	public void setId(String aValue) {
		this.id = aValue;
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
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
  /**
	* Set the createTime
	*/	
	public void setCreateTime(java.util.Date aValue) {
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
	public Object getComments() {
		return this.comments;
	}
	
  /**
	* Set the comments
	*/	
	public void setComments(Object aValue) {
		this.comments = aValue;
	}	
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ReportInfo)) {
			return false;
		}
		ReportInfo rhs = (ReportInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.clientName, rhs.clientName)
				.append(this.clientPhone, rhs.clientPhone)
				.append(this.salesAddress, rhs.salesAddress)
				.append(this.salesName, rhs.salesName)
				.append(this.salesPhone, rhs.salesPhone)
				.append(this.productType, rhs.productType)
				.append(this.amount, rhs.amount)
				.append(this.labelNo, rhs.labelNo)
				.append(this.createTime, rhs.createTime)
				.append(this.materialNo, rhs.materialNo)
				.append(this.client, rhs.client)
				.append(this.reportType, rhs.reportType)
				.append(this.ip, rhs.ip)
				.append(this.gpsAddress, rhs.gpsAddress)
				.append(this.queryId, rhs.queryId)
				.append(this.tamperContent, rhs.tamperContent)
				.append(this.comments, rhs.comments)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.clientName) 
				.append(this.clientPhone) 
				.append(this.salesAddress) 
				.append(this.salesName) 
				.append(this.salesPhone) 
				.append(this.productType) 
				.append(this.amount) 
				.append(this.labelNo) 
				.append(this.createTime) 
				.append(this.materialNo) 
				.append(this.client) 
				.append(this.reportType) 
				.append(this.ip) 
				.append(this.gpsAddress) 
				.append(this.queryId) 
				.append(this.tamperContent) 
				.append(this.comments) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("clientName", this.clientName) 
				.append("clientPhone", this.clientPhone) 
				.append("salesAddress", this.salesAddress) 
				.append("salesName", this.salesName) 
				.append("salesPhone", this.salesPhone) 
				.append("productType", this.productType) 
				.append("amount", this.amount) 
				.append("labelNo", this.labelNo) 
				.append("createTime", this.createTime) 
				.append("materialNo", this.materialNo) 
				.append("client", this.client) 
				.append("reportType", this.reportType) 
				.append("ip", this.ip) 
				.append("gpsAddress", this.gpsAddress) 
				.append("queryId", this.queryId) 
				.append("tamperContent", this.tamperContent) 
				.append("comments", this.comments) 
				.toString();
	}

	
}