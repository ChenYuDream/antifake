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
 * AntiQueryLog Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class AntiQueryLog extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String id;
	protected String lableNo;
	protected String phoneNo;
	protected String userName;
	protected String queryTime;
	protected String ip;
	protected String materialNo;
	protected String isExist;
	protected Integer queryCount;
	protected String client;


  /**
	*
	* Default Empty Constructor for class AntiQueryLog
	*
	*/
	public AntiQueryLog () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class AntiQueryLog
	*
	*/
	public AntiQueryLog (
		 String in_id
		,String in_lableNo
		,String in_phoneNo
		,String in_userName
		,String in_queryTime
		,String in_ip
		,String in_materialNo
		,String in_isExist
		,Integer in_queryCount
		,String in_client
        ) {
		this.setId(in_id);
		this.setLableNo(in_lableNo);
		this.setPhoneNo(in_phoneNo);
		this.setUserName(in_userName);
		this.setQueryTime(in_queryTime);
		this.setIp(in_ip);
		this.setMaterialNo(in_materialNo);
		this.setIsExist(in_isExist);
		this.setQueryCount(in_queryCount);
		this.setClient(in_client);
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
	public String getLableNo() {
		return this.lableNo;
	}
	
  /**
	* Set the lableNo
	*/	
	public void setLableNo(String aValue) {
		this.lableNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getPhoneNo() {
		return this.phoneNo;
	}
	
  /**
	* Set the phoneNo
	*/	
	public void setPhoneNo(String aValue) {
		this.phoneNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getUserName() {
		return this.userName;
	}
	
  /**
	* Set the userName
	*/	
	public void setUserName(String aValue) {
		this.userName = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getQueryTime() {
		return this.queryTime;
	}
	
  /**
	* Set the queryTime
	*/	
	public void setQueryTime(String aValue) {
		this.queryTime = aValue;
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
	public String getIsExist() {
		return this.isExist;
	}
	
  /**
	* Set the isExist
	*/	
	public void setIsExist(String aValue) {
		this.isExist = aValue;
	}	
  /**
	*
	* @return Integer
	*/
	public Integer getQueryCount() {
		return this.queryCount;
	}
	
  /**
	* Set the queryCount
	*/	
	public void setQueryCount(Integer aValue) {
		this.queryCount = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof AntiQueryLog)) {
			return false;
		}
		AntiQueryLog rhs = (AntiQueryLog) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.lableNo, rhs.lableNo)
				.append(this.phoneNo, rhs.phoneNo)
				.append(this.userName, rhs.userName)
				.append(this.queryTime, rhs.queryTime)
				.append(this.ip, rhs.ip)
				.append(this.materialNo, rhs.materialNo)
				.append(this.isExist, rhs.isExist)
				.append(this.queryCount, rhs.queryCount)
				.append(this.client, rhs.client)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.lableNo) 
				.append(this.phoneNo) 
				.append(this.userName) 
				.append(this.queryTime) 
				.append(this.ip) 
				.append(this.materialNo) 
				.append(this.isExist) 
				.append(this.queryCount) 
				.append(this.client) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("lableNo", this.lableNo) 
				.append("phoneNo", this.phoneNo) 
				.append("userName", this.userName) 
				.append("queryTime", this.queryTime) 
				.append("ip", this.ip) 
				.append("materialNo", this.materialNo) 
				.append("isExist", this.isExist) 
				.append("queryCount", this.queryCount) 
				.append("client", this.client) 
				.toString();
	}

	
}