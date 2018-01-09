package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Fri Dec 19 17:00:46 GMT+08:00 2014
 *
 */
import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * LabelScrap Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class LabelScrap extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String serialNumber;
	protected String scrapType;
	protected String scrapRemark;
	protected java.util.Date scrapTime;
	protected java.util.Date approveTime;
	protected String scraper;
	protected String status;
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


  /**
	*
	* Default Empty Constructor for class LabelScrap
	*
	*/
	public LabelScrap () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class LabelScrap
	*
	*/
	public LabelScrap (
		 String in_serialNumber
		,String in_scrapType
		,String in_scrapRemark
		,java.util.Date in_scrapTime
		,java.util.Date in_approveTime
		,String in_scraper
        ) {
		this.setSerialNumber(in_serialNumber);
		this.setScrapType(in_scrapType);
		this.setScrapRemark(in_scrapRemark);
		this.setScrapTime(in_scrapTime);
		this.setApproveTime(in_approveTime);
		this.setScraper(in_scraper);
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
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof LabelScrap)) {
			return false;
		}
		LabelScrap rhs = (LabelScrap) object;
		return new EqualsBuilder()
				.append(this.serialNumber, rhs.serialNumber)
				.append(this.scrapType, rhs.scrapType)
				.append(this.scrapRemark, rhs.scrapRemark)
				.append(this.scrapTime, rhs.scrapTime)
				.append(this.approveTime, rhs.approveTime)
				.append(this.scraper, rhs.scraper)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.serialNumber) 
				.append(this.scrapType) 
				.append(this.scrapRemark) 
				.append(this.scrapTime) 
				.append(this.approveTime) 
				.append(this.scraper) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("serialNumber", this.serialNumber) 
				.append("scrapType", this.scrapType) 
				.append("scrapRemark", this.scrapRemark) 
				.append("scrapTime", this.scrapTime) 
				.append("approveTime", this.approveTime) 
				.append("scraper", this.scraper) 
				.toString();
	}

	
}