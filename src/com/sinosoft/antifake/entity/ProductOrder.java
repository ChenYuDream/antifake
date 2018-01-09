package com.sinosoft.antifake.entity;

/*
 *  Created on Fri Nov 14 09:12:56 GMT+08:00 2014
 *
 */
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

/**
 * ProductOrder Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
@Entity
@Table(name="product_order")
public class ProductOrder extends IdEntity{

	protected String soNo;
	protected String lineNo;
	protected String plantNo;
	protected String materialNo;
	protected String product;
	protected Integer count;
	protected java.util.Date createTime;
	protected String creater;
	protected String status;
	protected String plant;
	protected String moNo;
	protected String productionDate;


    @Formula("(select r.recipient from tblRecipient r where r.recipient_no = plant_no)")
    public String getPlant() {
		return plant;
	}
	
	public void setPlant(String plant) {
		this.plant = plant;
	}
	
	
    public String getMoNo() {
		return moNo;
	}

	public void setMoNo(String moNo) {
		this.moNo = moNo;
	}

	

    public String getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}

	/**
	*
	* @return String
	*/
	public String getSoNo() {
		return this.soNo;
	}
	
  /**
	* Set the soNo
	*/	
	public void setSoNo(String aValue) {
		this.soNo = aValue;
	}	
  /**
	*
	* @return String
	*/
	public String getLineNo() {
		return this.lineNo;
	}
	
  /**
	* Set the lineNo
	*/	
	public void setLineNo(String aValue) {
		this.lineNo = aValue;
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
	public String getProduct() {
		return this.product;
	}
	
  /**
	* Set the product
	*/	
	public void setProduct(String aValue) {
		this.product = aValue;
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

@Override
public String toString() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean equals(Object o) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public int hashCode() {
	// TODO Auto-generated method stub
	return 0;
}	

	
}