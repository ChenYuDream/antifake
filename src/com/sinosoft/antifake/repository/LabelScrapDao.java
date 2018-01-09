package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.LableScrap;


public interface LabelScrapDao extends PagingAndSortingRepository<LableScrap, String>, JpaSpecificationExecutor<LableScrap> {
	
}
