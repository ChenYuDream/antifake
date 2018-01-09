package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Wed Nov 05 13:50:30 GMT+08:00 2014
 *
 */
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * LableSummary Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
public class LableSummary extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

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


  /**
	*
	* Default Empty Constructor for class LableSummary
	*
	*/
	public LableSummary () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class LableSummary
	*
	*/
	public LableSummary (
		 String in_id
		,String in_orderNo
		,String in_recipientNo
		,String in_recipient
		,String in_expressCompany
		,String in_trackingNo
		,String in_labelSize
		,String in_reelNo
		,Integer in_count
		,java.util.Date in_createTime
		,String in_creater
		,String in_status
        ) {
		this.setId(in_id);
		this.setOrderNo(in_orderNo);
		this.setRecipientNo(in_recipientNo);
		this.setRecipient(in_recipient);
		this.setExpressCompany(in_expressCompany);
		this.setTrackingNo(in_trackingNo);
		this.setLabelSize(in_labelSize);
		this.setReelNo(in_reelNo);
		this.setCount(in_count);
		this.setCreateTime(in_createTime);
		this.setCreater(in_creater);
		this.setStatus(in_status);
    }

    


public String getReceiveStatus() {
	return receiveStatus;
}

public void setReceiveStatus(String receiveStatus) {
	this.receiveStatus = receiveStatus;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
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
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof LableSummary)) {
			return false;
		}
		LableSummary rhs = (LableSummary) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.orderNo, rhs.orderNo)
				.append(this.recipientNo, rhs.recipientNo)
				.append(this.recipient, rhs.recipient)
				.append(this.expressCompany, rhs.expressCompany)
				.append(this.trackingNo, rhs.trackingNo)
				.append(this.labelSize, rhs.labelSize)
				.append(this.reelNo, rhs.reelNo)
				.append(this.count, rhs.count)
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
				.append(this.id) 
				.append(this.orderNo) 
				.append(this.recipientNo) 
				.append(this.recipient) 
				.append(this.expressCompany) 
				.append(this.trackingNo) 
				.append(this.labelSize) 
				.append(this.reelNo) 
				.append(this.count) 
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
				.append("id", this.id) 
				.append("orderNo", this.orderNo) 
				.append("recipientNo", this.recipientNo) 
				.append("recipient", this.recipient) 
				.append("expressCompany", this.expressCompany) 
				.append("trackingNo", this.trackingNo) 
				.append("labelSize", this.labelSize) 
				.append("reelNo", this.reelNo) 
				.append("count", this.count) 
				.append("createTime", this.createTime) 
				.append("creater", this.creater) 
				.append("status", this.status) 
				.toString();
	}

	
}