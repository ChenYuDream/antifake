package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.ReportInfo;

public interface ReportInfoDao extends PagingAndSortingRepository<ReportInfo, String>,
		JpaSpecificationExecutor<ReportInfo> {

}
