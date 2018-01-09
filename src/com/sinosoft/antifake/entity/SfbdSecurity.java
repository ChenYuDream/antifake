package com.sinosoft.antifake.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @Description:SFBD工厂防伪数据实体
 * @author wangxueqiang
 * @date 2017年12月7日 上午10:25:36
 *
 */
@Entity
@Table(name = "tbl_sfbd_security")
public class SfbdSecurity extends IdEntity{
	
	private String  createDate;//生产日期
	
	private String barcode;//条形码
	
	private String batchNo;//批次
	
	private String productCode;//物料代码
	
	private String companyCode;//公司编码

	private String creater;//创建人
	
	@Column(name="create_date")
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	@Column(name="barcode")
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	@Column(name="batch_no")
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	@Column(name="product_code")
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	@Column(name="company_code")
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@Column(name="creater")
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}

}