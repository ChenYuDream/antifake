package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.QueryLog;

public interface QueryLogDao extends PagingAndSortingRepository<QueryLog, String>, JpaSpecificationExecutor<QueryLog> {
	/**
	 * 
	 * @Description: 根据防伪码查询
	 * @param lableNo
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月13日 上午10:21:17
	 *
	 */
	QueryLog findByLableNo(String lableNo);
	
}
