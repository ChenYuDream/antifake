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
 * Tblmaterial Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class Tblmaterial extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String maMaterial;
	protected String maAlias;
	protected String maDescription;


  /**
	*
	* Default Empty Constructor for class Tblmaterial
	*
	*/
	public Tblmaterial () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class Tblmaterial
	*
	*/
	public Tblmaterial (
		 String in_maMaterial
		,String in_maAlias
		,String in_maDescription
        ) {
		this.setMaMaterial(in_maMaterial);
		this.setMaAlias(in_maAlias);
		this.setMaDescription(in_maDescription);
    }

    
  /**
	*
	* @return String
	*/
	public String getMaMaterial() {
		return this.maMaterial;
	}
	
  /**
	* Set the maMaterial
	*/	
	public void setMaMaterial(String aValue) {
		this.maMaterial = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getMaAlias() {
		return this.maAlias;
	}
	
  /**
	* Set the maAlias
	*/	
	public void setMaAlias(String aValue) {
		this.maAlias = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getMaDescription() {
		return this.maDescription;
	}
	
  /**
	* Set the maDescription
	*/	
	public void setMaDescription(String aValue) {
		this.maDescription = aValue;
	}	
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Tblmaterial)) {
			return false;
		}
		Tblmaterial rhs = (Tblmaterial) object;
		return new EqualsBuilder()
				.append(this.maMaterial, rhs.maMaterial)
				.append(this.maAlias, rhs.maAlias)
				.append(this.maDescription, rhs.maDescription)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.maMaterial) 
				.append(this.maAlias) 
				.append(this.maDescription) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("maMaterial", this.maMaterial) 
				.append("maAlias", this.maAlias) 
				.append("maDescription", this.maDescription) 
				.toString();
	}

	
}