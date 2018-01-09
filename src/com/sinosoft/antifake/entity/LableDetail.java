package com.sinosoft.antifake.entity;

/*
 *  Created on Wed Nov 05 13:50:30 GMT+08:00 2014
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * LableDetail Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
@Entity
@Table(name="lable_detail")
public class LableDetail extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String serialNumber;
	protected String orderNo;
	protected String reelNo;
	protected java.util.Date createTime;
	protected String creater;
	protected String status;
	protected String scrapType;
	protected String scrapRemark;
	protected String recipient;
	protected java.util.Date scrapTime;
	protected java.util.Date approveTime;
	
	public java.util.Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(java.util.Date approveTime) {
		this.approveTime = approveTime;
	}

/**
	*
	* Default Empty Constructor for class LableDetail
	*
	*/
	public LableDetail () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class LableDetail
	*
	*/
	public LableDetail (
		 String in_serialNumber
		,String in_orderNo
		,String in_reelNo
		,java.util.Date in_createTime
		,String in_creater
		,String in_status
        ) {
		this.setSerialNumber(in_serialNumber);
		this.setReelNo(in_reelNo);
		this.setCreateTime(in_createTime);
		this.setCreater(in_creater);
		this.setStatus(in_status);
    }

    
	@Formula("(select r.recipient from tblRecipient r where r.recipient_no = (select s.recipient_no from lable_summary s where s.order_no = order_no and s.reel_no = reel_no))")
	public String getRecipient() {
		return recipient;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Column(name="orderNo")
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getScrapType() {
		return scrapType;
	}
	
	public void setScrapType(String scrapType) {
		this.scrapType = scrapType;
	}
	
	public String getScrapRemark() {
		return scrapRemark;
	}
	
	public void setScrapRemark(String scrapRemark) {
		this.scrapRemark = scrapRemark;
	}
	
	public java.util.Date getScrapTime() {
		return scrapTime;
	}

	public void setScrapTime(java.util.Date scrapTime) {
		this.scrapTime = scrapTime;
	}

	public String getscrapRemark() {
		return scrapRemark;
	}
	
	public void setscrapRemark(String scrapRemark) {
		this.scrapRemark = scrapRemark;
	}
	
	/**
	*
	* @return String
	*/
	public String getSerialNumber() {
		return this.serialNumber;
	}
	
  /**
	* Set the serialNumber
	*/	
	public void setSerialNumber(String aValue) {
		this.serialNumber = aValue;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
}