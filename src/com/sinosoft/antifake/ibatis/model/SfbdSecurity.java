package com.sinosoft.antifake.ibatis.model;

import java.io.Serializable;
/**
 * 
 * @Description:SFBD工厂防伪数据实体
 * @author wangxueqiang
 * @date 2017年12月7日 上午10:25:36
 *
 */
public class SfbdSecurity extends com.sinosoft.antifake.ibatis.model.BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String  createDate;//生产日期
	private String barcode;//条形码
	private String batchNo;//批次
	private String productCode;//物料代码
	private String companyCode;//公司编码
	private String creater;//创建人
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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