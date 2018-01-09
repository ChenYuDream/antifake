package com.sinosoft.antifake.entity;

/*
 *  Created on Wed Nov 05 13:50:30 GMT+08:00 2014
 *
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * LableSummary Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
@Entity
@Table(name = "lable_summary")
public class LableSummary extends IdEntity{
	
	@Column(name="orderNo")
	protected String orderNo;
	protected String recipientNo;
	protected String recipient;
	protected String expressCompany;
	protected String trackingNo;
	protected String labelSize;
	protected String reelNo;
	protected Integer count;
	protected java.util.Date createTime;
	protected String creater;
	protected String status;
	protected String receiveStatus;
	protected String remark;
	protected java.util.Date receiveTime;
	protected String receiveUser;
	protected String plant;


	
	@Formula("(select r.recipient from tblRecipient r where r.recipient_no = recipient_no)")
	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public java.util.Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(java.util.Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}

	/**
	*
	* @return String
	*/
	public String getOrderNo() {
		return this.orderNo;
	}
	
  /**
	* Set the orderNo
	*/	
	public void setOrderNo(String aValue) {
		this.orderNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getRecipientNo() {
		return this.recipientNo;
	}
	
  /**
	* Set the recipientNo
	*/	
	public void setRecipientNo(String aValue) {
		this.recipientNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getRecipient() {
		return this.recipient;
	}
	
  /**
	* Set the recipient
	*/	
	public void setRecipient(String aValue) {
		this.recipient = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getExpressCompany() {
		return this.expressCompany;
	}
	
  /**
	* Set the expressCompany
	*/	
	public void setExpressCompany(String aValue) {
		this.expressCompany = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getTrackingNo() {
		return this.trackingNo;
	}
	
  /**
	* Set the trackingNo
	*/	
	public void setTrackingNo(String aValue) {
		this.trackingNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getLabelSize() {
		return this.labelSize;
	}
	
  /**
	* Set the labelSize
	*/	
	public void setLabelSize(String aValue) {
		this.labelSize = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getReelNo() {
		return this.reelNo;
	}
	
  /**
	* Set the reelNo
	*/	
	public void setReelNo(String aValue) {
		this.reelNo = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getCount() {
		return this.count;
	}
	
  /**
	* Set the count
	*/	
	public void setCount(Integer aValue) {
		this.count = aValue;
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
	public String getCreater() {
		return this.creater;
	}
	
  /**
	* Set the creater
	*/	
	public void setCreater(String aValue) {
		this.creater = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getStatus() {
		return this.status;
	}
	
  /**
	* Set the status
	*/	
	public void setStatus(String aValue) {
		this.status = aValue;
	}	

	
}