package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Thu Feb 05 10:35:23 GMT+08:00 2015
 *
 */
import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * KpiInfo Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class KpiInfo extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String spcialNo;
	protected String state;
	protected Integer count;
	protected Integer uploadedCount;
	protected Integer timelyCount;
	protected java.util.Date productDate;
	protected java.util.Date createTime;
	protected String plantNo;


  /**
	*
	* Default Empty Constructor for class KpiInfo
	*
	*/
	public KpiInfo () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class KpiInfo
	*
	*/
	public KpiInfo (
		 String in_spcialNo
		,String in_state
		,Integer in_count
		,Integer in_uploadedCount
		,Integer in_timelyCount
		,java.util.Date in_productDate
		,java.util.Date in_createTime
		,String in_plantNo
        ) {
		this.setSpcialNo(in_spcialNo);
		this.setState(in_state);
		this.setCount(in_count);
		this.setUploadedCount(in_uploadedCount);
		this.setTimelyCount(in_timelyCount);
		this.setProductDate(in_productDate);
		this.setCreateTime(in_createTime);
		this.setPlantNo(in_plantNo);
    }

    
  /**
	*
	* @return String
	*/
	public String getSpcialNo() {
		return this.spcialNo;
	}
	
  /**
	* Set the spcialNo
	*/	
	public void setSpcialNo(String aValue) {
		this.spcialNo = aValue;
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
	* @return Integer
	*/
	public Integer getUploadedCount() {
		return this.uploadedCount;
	}
	
  /**
	* Set the uploadedCount
	*/	
	public void setUploadedCount(Integer aValue) {
		this.uploadedCount = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getTimelyCount() {
		return this.timelyCount;
	}
	
  /**
	* Set the timelyCount
	*/	
	public void setTimelyCount(Integer aValue) {
		this.timelyCount = aValue;
	}	
  /**
	*
	* @return java.util.Date
	*/
	public java.util.Date getProductDate() {
		return this.productDate;
	}
	
  /**
	* Set the productDate
	*/	
	public void setProductDate(java.util.Date aValue) {
		this.productDate = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof KpiInfo)) {
			return false;
		}
		KpiInfo rhs = (KpiInfo) object;
		return new EqualsBuilder()
				.append(this.spcialNo, rhs.spcialNo)
				.append(this.state, rhs.state)
				.append(this.count, rhs.count)
				.append(this.uploadedCount, rhs.uploadedCount)
				.append(this.timelyCount, rhs.timelyCount)
				.append(this.productDate, rhs.productDate)
				.append(this.createTime, rhs.createTime)
				.append(this.plantNo, rhs.plantNo)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.spcialNo) 
				.append(this.state) 
				.append(this.count) 
				.append(this.uploadedCount) 
				.append(this.timelyCount) 
				.append(this.productDate) 
				.append(this.createTime) 
				.append(this.plantNo) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("spcialNo", this.spcialNo) 
				.append("state", this.state) 
				.append("count", this.count) 
				.append("uploadedCount", this.uploadedCount) 
				.append("timelyCount", this.timelyCount) 
				.append("productDate", this.productDate) 
				.append("createTime", this.createTime) 
				.append("plantNo", this.plantNo) 
				.toString();
	}

	
}