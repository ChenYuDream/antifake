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
 * Syncuser Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class Syncuser extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String username;
	protected String password;
	protected String recipientNo;

	
	public String getRecipientNo() {
		return recipientNo;
	}

	public void setRecipientNo(String recipientNo) {
		this.recipientNo = recipientNo;
	}

/**
	*
	* Default Empty Constructor for class Syncuser
	*
	*/
	public Syncuser () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class Syncuser
	*
	*/
	public Syncuser (
		 String in_username
		,String in_password
        ) {
		this.setUsername(in_username);
		this.setPassword(in_password);
    }

    
  /**
	*
	* @return String
	*/
	public String getUsername() {
		return this.username;
	}
	
  /**
	* Set the username
	*/	
	public void setUsername(String aValue) {
		this.username = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getPassword() {
		return this.password;
	}
	
  /**
	* Set the password
	*/	
	public void setPassword(String aValue) {
		this.password = aValue;
	}	
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Syncuser)) {
			return false;
		}
		Syncuser rhs = (Syncuser) object;
		return new EqualsBuilder()
				.append(this.username, rhs.username)
				.append(this.password, rhs.password)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.username) 
				.append(this.password) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("username", this.username) 
				.append("password", this.password) 
				.toString();
	}

	
}