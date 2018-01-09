package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.ReportInfoHis;

public interface ReportInfoHisDao extends PagingAndSortingRepository<ReportInfoHis, String>,
		JpaSpecificationExecutor<ReportInfoHis> {

}
