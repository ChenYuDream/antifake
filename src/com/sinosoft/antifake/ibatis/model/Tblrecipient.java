package com.sinosoft.antifake.ibatis.model;

/*
 *  Created on Wed Feb 04 14:59:03 GMT+08:00 2015
 *
 */
import java.io.Serializable;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Tblrecipient Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class Tblrecipient extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String recipientNo;
	protected String recipient;
	protected Integer warnValue;
	protected Integer usedNum;
	protected Integer stockNum;
	protected Integer deleteNum;
	protected Integer preStatus;
	protected String plantCode;


  /**
	*
	* Default Empty Constructor for class Tblrecipient
	*
	*/
	public Tblrecipient () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class Tblrecipient
	*
	*/
	public Tblrecipient (
		 String in_recipientNo
		,String in_recipient
		,Integer in_warnValue
		,Integer in_usedNum
		,Integer in_stockNum
		,Integer in_deleteNum
		,Integer in_preStatus
		,String in_plantCode
        ) {
		this.setRecipientNo(in_recipientNo);
		this.setRecipient(in_recipient);
		this.setWarnValue(in_warnValue);
		this.setUsedNum(in_usedNum);
		this.setStockNum(in_stockNum);
		this.setDeleteNum(in_deleteNum);
		this.setPreStatus(in_preStatus);
		this.setPlantCode(in_plantCode);
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
	* @return Integer
	*/
	public Integer getWarnValue() {
		return this.warnValue;
	}
	
  /**
	* Set the warnValue
	*/	
	public void setWarnValue(Integer aValue) {
		this.warnValue = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getUsedNum() {
		return this.usedNum;
	}
	
  /**
	* Set the usedNum
	*/	
	public void setUsedNum(Integer aValue) {
		this.usedNum = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getStockNum() {
		return this.stockNum;
	}
	
  /**
	* Set the stockNum
	*/	
	public void setStockNum(Integer aValue) {
		this.stockNum = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getDeleteNum() {
		return this.deleteNum;
	}
	
  /**
	* Set the deleteNum
	*/	
	public void setDeleteNum(Integer aValue) {
		this.deleteNum = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getPreStatus() {
		return this.preStatus;
	}
	
  /**
	* Set the preStatus
	*/	
	public void setPreStatus(Integer aValue) {
		this.preStatus = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getPlantCode() {
		return this.plantCode;
	}
	
  /**
	* Set the plantCode
	*/	
	public void setPlantCode(String aValue) {
		this.plantCode = aValue;
	}	
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Tblrecipient)) {
			return false;
		}
		Tblrecipient rhs = (Tblrecipient) object;
		return new EqualsBuilder()
				.append(this.recipientNo, rhs.recipientNo)
				.append(this.recipient, rhs.recipient)
				.append(this.warnValue, rhs.warnValue)
				.append(this.usedNum, rhs.usedNum)
				.append(this.stockNum, rhs.stockNum)
				.append(this.deleteNum, rhs.deleteNum)
				.append(this.preStatus, rhs.preStatus)
				.append(this.plantCode, rhs.plantCode)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.recipientNo) 
				.append(this.recipient) 
				.append(this.warnValue) 
				.append(this.usedNum) 
				.append(this.stockNum) 
				.append(this.deleteNum) 
				.append(this.preStatus) 
				.append(this.plantCode) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("recipientNo", this.recipientNo) 
				.append("recipient", this.recipient) 
				.append("warnValue", this.warnValue) 
				.append("usedNum", this.usedNum) 
				.append("stockNum", this.stockNum) 
				.append("deleteNum", this.deleteNum) 
				.append("preStatus", this.preStatus) 
				.append("plantCode", this.plantCode) 
				.toString();
	}

	
}