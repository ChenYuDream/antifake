package com.sinosoft.antifake.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class NewQueryResult {
	
	private int resultCount;
	private int queryTimes;
	private List<QueryResultDetail> resultDetail = Lists.newArrayList();
	private String queryId;
	
	
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public List<QueryResultDetail> getResultDetail() {
		return resultDetail;
	}
	public void setResultDetail(List<QueryResultDetail> resultDetail) {
		this.resultDetail = resultDetail;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public int getQueryTimes() {
		return queryTimes;
	}
	public void setQueryTimes(int queryTimes) {
		this.queryTimes = queryTimes;
	}
}
