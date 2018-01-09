package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Fri Nov 14 09:12:56 GMT+08:00 2014
 *
 */
import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * LableOrder Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class LableOrder extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String orderNo;
	protected String plantNo;
	protected String labelSize;
	protected Integer count;
	protected java.util.Date createTime;
	protected String creater;
	protected String status;


  /**
	*
	* Default Empty Constructor for class LableOrder
	*
	*/
	public LableOrder () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class LableOrder
	*
	*/
	public LableOrder (
		 String in_orderNo
		,String in_plantNo
		,String in_labelSize
		,Integer in_count
		,java.util.Date in_createTime
		,String in_creater
		,String in_status
        ) {
		this.setOrderNo(in_orderNo);
		this.setPlantNo(in_plantNo);
		this.setLabelSize(in_labelSize);
		this.setCount(in_count);
		this.setCreateTime(in_createTime);
		this.setCreater(in_creater);
		this.setStatus(in_status);
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
	public String getPlantNo() {
		return this.plantNo;
	}
	
  /**
	* Set the plantNo
	*/	
	public void setPlantNo(String aValue) {
		this.plantNo = aValue;
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
		if (!(object instanceof LableOrder)) {
			return false;
		}
		LableOrder rhs = (LableOrder) object;
		return new EqualsBuilder()
				.append(this.orderNo, rhs.orderNo)
				.append(this.plantNo, rhs.plantNo)
				.append(this.labelSize, rhs.labelSize)
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
				.append(this.orderNo) 
				.append(this.plantNo) 
				.append(this.labelSize) 
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
				.append("orderNo", this.orderNo) 
				.append("plantNo", this.plantNo) 
				.append("labelSize", this.labelSize) 
				.append("count", this.count) 
				.append("createTime", this.createTime) 
				.append("creater", this.creater) 
				.append("status", this.status) 
				.toString();
	}

	
}