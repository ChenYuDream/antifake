package com.sinosoft.antifake.entity;

/*
 *  Created on Tue Sep 02 13:40:48 GMT+08:00 2014
 *
 */

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * Tblserialnumber Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Tblserialnumber")
public class Tblserialnumber  implements Serializable{
	@Id
	@SerializedName(value="serialNo")
    protected String snoSerialno;
	@SerializedName(value="barNo")
	protected String snoBarno;
	@SerializedName(value="boxNo")
	protected String snoBoxno;
	@SerializedName(value="material")
	protected String snoMaterial;
	@SerializedName(value="createDate")
	protected java.util.Date snoCreatedate;
	@SerializedName(value="dc")
	protected String snoDc;
	@SerializedName(value="jv")
	protected String snoJv;
	@SerializedName(value="company")
	protected String snoCompany;
	@SerializedName(value="productionLine")
	protected String snoProductionline;
	@SerializedName(value="productionLineNo")
	protected String snoProductionlineno;
	@SerializedName(value="worker")
	protected String snoWorker;
	@SerializedName(value="productionOrder")
	protected String snoProductionorder;
	@SerializedName(value="modifyuser")
	protected String snoModifyuser;
	@SerializedName(value="modifydate")
	protected java.util.Date snoModifydate;
	@SerializedName(value="productInfo")
	protected String snoProductinfo;


  /**
	*
	* Default Empty Constructor for class Tblserialnumber
	*
	*/
	public Tblserialnumber () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class Tblserialnumber
	*
	*/
	public Tblserialnumber (
		 String in_snoSerialno
		,String in_snoBarno
		,String in_snoBoxno
		,String in_snoMaterial
		,java.util.Date in_snoCreatedate
		,String in_snoDc
		,String in_snoJv
		,String in_snoCompany
		,String in_snoProductionline
		,String in_snoProductionlineno
		,String in_snoWorker
		,String in_snoProductionorder
		,String in_snoModifyuser
		,java.util.Date in_snoModifydate
		,String in_snoProductinfo
        ) {
		this.setSnoSerialno(in_snoSerialno);
		this.setSnoBarno(in_snoBarno);
		this.setSnoBoxno(in_snoBoxno);
		this.setSnoMaterial(in_snoMaterial);
		this.setSnoCreatedate(in_snoCreatedate);
		this.setSnoDc(in_snoDc);
		this.setSnoJv(in_snoJv);
		this.setSnoCompany(in_snoCompany);
		this.setSnoProductionline(in_snoProductionline);
		this.setSnoProductionlineno(in_snoProductionlineno);
		this.setSnoWorker(in_snoWorker);
		this.setSnoProductionorder(in_snoProductionorder);
		this.setSnoModifyuser(in_snoModifyuser);
		this.setSnoModifydate(in_snoModifydate);
		this.setSnoProductinfo(in_snoProductinfo);
    }

    
  /**
	*
	* @return String
	*/
	public String getSnoSerialno() {
		return this.snoSerialno;
	}
	
  /**
	* Set the snoSerialno
	*/	
	public void setSnoSerialno(String aValue) {
		this.snoSerialno = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoBarno() {
		return this.snoBarno;
	}
	
  /**
	* Set the snoBarno
	*/	
	public void setSnoBarno(String aValue) {
		this.snoBarno = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoBoxno() {
		return this.snoBoxno;
	}
	
  /**
	* Set the snoBoxno
	*/	
	public void setSnoBoxno(String aValue) {
		this.snoBoxno = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoMaterial() {
		return this.snoMaterial;
	}
	
  /**
	* Set the snoMaterial
	*/	
	public void setSnoMaterial(String aValue) {
		this.snoMaterial = aValue;
	}	
  /**
	*
	* @return java.util.Date
	*/
	public java.util.Date getSnoCreatedate() {
		return this.snoCreatedate;
	}
	
  /**
	* Set the snoCreatedate
	*/	
	public void setSnoCreatedate(java.util.Date aValue) {
		this.snoCreatedate = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoDc() {
		return this.snoDc;
	}
	
  /**
	* Set the snoDc
	*/	
	public void setSnoDc(String aValue) {
		this.snoDc = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoJv() {
		return this.snoJv;
	}
	
  /**
	* Set the snoJv
	*/	
	public void setSnoJv(String aValue) {
		this.snoJv = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoCompany() {
		return this.snoCompany;
	}
	
  /**
	* Set the snoCompany
	*/	
	public void setSnoCompany(String aValue) {
		this.snoCompany = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoProductionline() {
		return this.snoProductionline;
	}
	
  /**
	* Set the snoProductionline
	*/	
	public void setSnoProductionline(String aValue) {
		this.snoProductionline = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoProductionlineno() {
		return this.snoProductionlineno;
	}
	
  /**
	* Set the snoProductionlineno
	*/	
	public void setSnoProductionlineno(String aValue) {
		this.snoProductionlineno = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoWorker() {
		return this.snoWorker;
	}
	
  /**
	* Set the snoWorker
	*/	
	public void setSnoWorker(String aValue) {
		this.snoWorker = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoProductionorder() {
		return this.snoProductionorder;
	}
	
  /**
	* Set the snoProductionorder
	*/	
	public void setSnoProductionorder(String aValue) {
		this.snoProductionorder = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoModifyuser() {
		return this.snoModifyuser;
	}
	
  /**
	* Set the snoModifyuser
	*/	
	public void setSnoModifyuser(String aValue) {
		this.snoModifyuser = aValue;
	}	
  /**
	*
	* @return java.util.Date
	*/
	public java.util.Date getSnoModifydate() {
		return this.snoModifydate;
	}
	
  /**
	* Set the snoModifydate
	*/	
	public void setSnoModifydate(java.util.Date aValue) {
		this.snoModifydate = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getSnoProductinfo() {
		return this.snoProductinfo;
	}
	
  /**
	* Set the snoProductinfo
	*/	
	public void setSnoProductinfo(String aValue) {
		this.snoProductinfo = aValue;
	}	
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof Tblserialnumber)) {
			return false;
		}
		Tblserialnumber rhs = (Tblserialnumber) object;
		return new EqualsBuilder()
				.append(this.snoSerialno, rhs.snoSerialno)
				.append(this.snoBarno, rhs.snoBarno)
				.append(this.snoBoxno, rhs.snoBoxno)
				.append(this.snoMaterial, rhs.snoMaterial)
				.append(this.snoCreatedate, rhs.snoCreatedate)
				.append(this.snoDc, rhs.snoDc)
				.append(this.snoJv, rhs.snoJv)
				.append(this.snoCompany, rhs.snoCompany)
				.append(this.snoProductionline, rhs.snoProductionline)
				.append(this.snoProductionlineno, rhs.snoProductionlineno)
				.append(this.snoWorker, rhs.snoWorker)
				.append(this.snoProductionorder, rhs.snoProductionorder)
				.append(this.snoModifyuser, rhs.snoModifyuser)
				.append(this.snoModifydate, rhs.snoModifydate)
				.append(this.snoProductinfo, rhs.snoProductinfo)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.snoSerialno) 
				.append(this.snoBarno) 
				.append(this.snoBoxno) 
				.append(this.snoMaterial) 
				.append(this.snoCreatedate) 
				.append(this.snoDc) 
				.append(this.snoJv) 
				.append(this.snoCompany) 
				.append(this.snoProductionline) 
				.append(this.snoProductionlineno) 
				.append(this.snoWorker) 
				.append(this.snoProductionorder) 
				.append(this.snoModifyuser) 
				.append(this.snoModifydate) 
				.append(this.snoProductinfo) 
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("snoSerialno", this.snoSerialno) 
				.append("snoBarno", this.snoBarno) 
				.append("snoBoxno", this.snoBoxno) 
				.append("snoMaterial", this.snoMaterial) 
				.append("snoCreatedate", this.snoCreatedate) 
				.append("snoDc", this.snoDc) 
				.append("snoJv", this.snoJv) 
				.append("snoCompany", this.snoCompany) 
				.append("snoProductionline", this.snoProductionline) 
				.append("snoProductionlineno", this.snoProductionlineno) 
				.append("snoWorker", this.snoWorker) 
				.append("snoProductionorder", this.snoProductionorder) 
				.append("snoModifyuser", this.snoModifyuser) 
				.append("snoModifydate", this.snoModifydate) 
				.append("snoProductinfo", this.snoProductinfo) 
				.toString();
	}
}
