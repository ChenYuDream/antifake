package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Wed Nov 05 13:50:30 GMT+08:00 2014
 *
 */
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * LableDetail Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
public class LableDetail extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String serialNumber;
	protected String orderNo;
	protected String reelNo;
	protected java.util.Date createTime;
	protected String creater;
	protected String status;
	protected String scrapType;
	protected String scrapRemark;
	protected java.util.Date approveTime;
	protected String scraper;


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
		this.setOrderNo(in_orderNo);
		this.setReelNo(in_reelNo);
		this.setCreateTime(in_createTime);
		this.setCreater(in_creater);
		this.setStatus(in_status);
    }

    


public String getScraper() {
	return scraper;
}

public void setScraper(String scraper) {
	this.scraper = scraper;
}

public java.util.Date getApproveTime() {
	return approveTime;
}

public void setApproveTime(java.util.Date approveTime) {
	this.approveTime = approveTime;
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
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof LableDetail)) {
			return false;
		}
		LableDetail rhs = (LableDetail) object;
		return new EqualsBuilder()
				.append(this.serialNumber, rhs.serialNumber)
				.append(this.orderNo, rhs.orderNo)
				.append(this.reelNo, rhs.reelNo)
				.append(this.createTime, rhs.createTime)
				.append(this.creater, rhs.creater)
				.append(this.status, rhs.status)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.serialNumber) 
				.append(this.orderNo) 
				.append(this.reelNo) 
				.append(this.createTime) 
				.append(this.creater) 
				.append(this.status) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("serialNumber", this.serialNumber) 
				.append("orderNo", this.orderNo) 
				.append("reelNo", this.reelNo) 
				.append("createTime", this.createTime) 
				.append("creater", this.creater) 
				.append("status", this.status) 
				.toString();
	}

	
}