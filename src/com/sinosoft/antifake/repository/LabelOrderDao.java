package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.LableOrder;
import com.sinosoft.antifake.entity.LableSummary;


public interface LabelOrderDao extends PagingAndSortingRepository<LableOrder, String>, JpaSpecificationExecutor<LableOrder> {
	
}
