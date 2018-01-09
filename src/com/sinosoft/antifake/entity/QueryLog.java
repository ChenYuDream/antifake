package com.sinosoft.antifake.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "anti_query_log")
public class QueryLog extends IdEntity {
	
	private String lableNo;		//防伪码
	private String phoneNo;		//联系电话
	private String userName;	//客户姓名
	private String queryTime;	//查询时间
	private String ip;			//mobile:参数; web 自动获取。 
	private String materialNo;	//产品型号
	private String isExist;		//是否存在
	private Integer queryCount;	//本次查询有效的结果集数量
	private String client;		//查询来源
	private String selfCheck;   //自检
	@Column(name="SELF_CHECK")
	public String getSelfCheck() {
		return selfCheck;
	}
	public void setSelfCheck(String selfCheck) {
		this.selfCheck = selfCheck;
	}
	@Column(name="IS_FAKE")
	public String getIsFake() {
		return isFake;
	}
	public void setIsFake(String isFake) {
		this.isFake = isFake;
	}
	private String isFake;      //是否是假货


	@Column(name="LABLE_NO")
	public String getLableNo() {
		return lableNo;
	}
	public void setLableNo(String lableNo) {
		this.lableNo = lableNo;
	}
	@Column(name="PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
//		try {
//			userName = java.net.URLDecoder.decode(userName, "utf-8");	//中文必须，解决乱码问题
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}	
		this.userName = userName;
	}
	@Column(name="QUERY_TIME")
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	@Column(name="IP")
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Column(name="MATERIAL_NO")
	public String getMaterialNo() {
		return materialNo;
	}
	public void setMaterialNo(String materialNo) {
		this.materialNo = materialNo;
	}
	@Column(name="IS_EXIST")
	public String getIsExist() {
		return isExist;
	}
	public void setIsExist(String isExist) {
		this.isExist = isExist;
	}
	@Column(name="QUERY_COUNT")
	public Integer getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(Integer queryCount) {
		this.queryCount = queryCount;
	}
	//必须定义在getter 方法上
	@Column(name="CLIENT")
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}

}
