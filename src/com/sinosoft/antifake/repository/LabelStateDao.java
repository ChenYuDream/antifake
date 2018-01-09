package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.LableDetail;


public interface LabelStateDao extends PagingAndSortingRepository<LableDetail, String>, JpaSpecificationExecutor<LableDetail> {
	
}
