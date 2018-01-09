package com.sinosoft.antifake.entity;

/*
 *  Created on Fri Dec 19 17:00:46 GMT+08:00 2014
 *
 */
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.Formula;

/**
 * LabelScrap Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
@Entity
@Table(name="lable_scrap")
public class LableScrap extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String serialNumber;
	protected String scrapType;
	protected String scrapRemark;
	protected java.util.Date scrapTime;
	protected java.util.Date approveTime;
	protected String scraper;
	
	protected String orderNo;
	protected String reelNo;
	protected String recipient;
	protected String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Formula("(select r.recipient from tblRecipient r where r.recipient_no = (select s.recipient_no from lable_summary s where s.order_no = (select d.order_no from lable_detail d where d.serial_number = serial_number) and s.reel_no = (select d.reel_no from lable_detail d where d.serial_number = serial_number)))")
	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	
	@Formula("(select d.order_no from lable_detail d where d.serial_number = serial_number)")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Formula("(select d.reel_no from lable_detail d where d.serial_number = serial_number)")
	public String getReelNo() {
		return reelNo;
	}

	public void setReelNo(String reelNo) {
		this.reelNo = reelNo;
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
	public String getScrapType() {
		return this.scrapType;
	}
	
  /**
	* Set the scrapType
	*/	
	public void setScrapType(String aValue) {
		this.scrapType = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getScrapRemark() {
		return this.scrapRemark;
	}
	
  /**
	* Set the scrapRemark
	*/	
	public void setScrapRemark(String aValue) {
		this.scrapRemark = aValue;
	}	
  /**
	*
	* @return java.util.Date
	*/
	public java.util.Date getScrapTime() {
		return this.scrapTime;
	}
	
  /**
	* Set the scrapTime
	*/	
	public void setScrapTime(java.util.Date aValue) {
		this.scrapTime = aValue;
	}	
  /**
	*
	* @return java.util.Date
	*/
	public java.util.Date getApproveTime() {
		return this.approveTime;
	}
	
  /**
	* Set the approveTime
	*/	
	public void setApproveTime(java.util.Date aValue) {
		this.approveTime = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getScraper() {
		return this.scraper;
	}
	
  /**
	* Set the scraper
	*/	
	public void setScraper(String aValue) {
		this.scraper = aValue;
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