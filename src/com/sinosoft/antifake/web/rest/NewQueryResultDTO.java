package com.sinosoft.antifake.web.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.sinosoft.antifake.entity.QueryResultDetail;

@XmlRootElement(name = "Information")
public class NewQueryResultDTO {

	private String resultCount;
	private String queryTimes;
	private String lan;
	private List<QueryResultDetail> resultDetail = Lists.newArrayList();
	private String queryId;
	
	@XmlElement(name= "QueryId")
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	@XmlElement(name = "ResultCount")
	public String getResultCount() {
		return resultCount;
	}
	public void setResultCount(String resultCount) {
		this.resultCount = resultCount;
	}

	@XmlElement(name = "QueryTimes")
	public String getQueryTimes() {
		return queryTimes;
	}
	public void setQueryTimes(String queryTimes) {
		this.queryTimes = queryTimes;
	}

	//配置输出xml为<userList><user><id>1</id></user></userList>
	//@XmlElementWrapper(name = "Information")
	@XmlElement(name = "ResultDetail")
	public List<QueryResultDetail> getResultDetail() {
		return resultDetail;
	}


	public void setResultDetail(List<QueryResultDetail> resultDetail) {
		this.resultDetail = resultDetail;
	}
	//XML 不解析，json 还是会使用
	@XmlTransient
	public String getLan() {
		return lan;
	}
	public void setLan(String lan) {
		this.lan = lan;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
