package com.sinosoft.antifake.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.antifake.entity.QueryResult;
import com.sinosoft.antifake.entity.QueryResultCount;
import com.sinosoft.antifake.entity.QueryResultDetail;
import com.sinosoft.antifake.repository.QueryResultDao;

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class QueryResultService {
	
	@Autowired
	private QueryResultDao queryResultDao; 
	
	/**
	 * 查询
	 */
	public QueryResult getQueryResult(String lableNo) {
		QueryResultCount count = queryResultDao.getQueryResultCount(lableNo);
		List<QueryResultDetail> details = queryResultDao.getQueryResultDetail(lableNo);
		
		QueryResult result = new QueryResult();
		result.setResultCount(count.getResultCount());
		result.setQueryTimes(count.getQueryTimes());
		result.setResultDetail(details);
		return result;
	}
	
}
