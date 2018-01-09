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
 * ProductOrder Base Java Bean
 * 
 * This class is the base class for the model
 * 
 */
 
public class ProductOrder extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {

    protected String id;
	protected String soNo;
	protected String lineNo;
	protected String plantNo;
	protected String materialNo;
	protected String product;
	protected Integer count;
	protected java.util.Date createTime;
	protected String creater;
	protected String status;
	protected java.util.Date productionDate;
	protected String moNo;


  /**
	*
	* Default Empty Constructor for class ProductOrder
	*
	*/
	public ProductOrder () {
		super();
	}
	
  /**
	*
	* Default All Fields Constructor for class ProductOrder
	*
	*/
	public ProductOrder (
		 String in_id
		,String in_soNo
		,String in_lineNo
		,String in_plantNo
		,String in_materialNo
		,String in_product
		,String in_productSize
		,String in_productModle
		,Integer in_count
		,java.util.Date in_createTime
		,String in_creater
		,String in_status
        ) {
		this.setId(in_id);
		this.setSoNo(in_soNo);
		this.setLineNo(in_lineNo);
		this.setPlantNo(in_plantNo);
		this.setMaterialNo(in_materialNo);
		this.setProduct(in_product);
		this.setCount(in_count);
		this.setCreateTime(in_createTime);
		this.setCreater(in_creater);
		this.setStatus(in_status);
    }

    
	public java.util.Date getProductionDate() {
		return productionDate;
	}
	
	public void setProductionDate(java.util.Date productionDate) {
		this.productionDate = productionDate;
	}
	
	public String getMoNo() {
		return moNo;
	}
	
	public void setMoNo(String moNo) {
		this.moNo = moNo;
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
   /**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof ProductOrder)) {
			return false;
		}
		ProductOrder rhs = (ProductOrder) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.soNo, rhs.soNo)
				.append(this.lineNo, rhs.lineNo)
				.append(this.plantNo, rhs.plantNo)
				.append(this.materialNo, rhs.materialNo)
				.append(this.product, rhs.product)
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
				.append(this.soNo) 
				.append(this.lineNo) 
				.append(this.plantNo) 
				.append(this.materialNo) 
				.append(this.product) 
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
				.append("soNo", this.soNo) 
				.append("lineNo", this.lineNo) 
				.append("plantNo", this.plantNo) 
				.append("materialNo", this.materialNo) 
				.append("product", this.product) 
				.append("count", this.count) 
				.append("createTime", this.createTime) 
				.append("creater", this.creater) 
				.append("status", this.status) 
				.toString();
	}

	
}