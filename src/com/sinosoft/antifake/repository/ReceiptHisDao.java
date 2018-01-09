package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.LableSummary;


public interface ReceiptHisDao extends PagingAndSortingRepository<LableSummary, String>, JpaSpecificationExecutor<LableSummary> {
	
}
