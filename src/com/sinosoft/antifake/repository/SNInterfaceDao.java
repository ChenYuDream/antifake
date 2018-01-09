package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.Tblserialnumber;

public interface SNInterfaceDao extends PagingAndSortingRepository<Tblserialnumber, String>,
													JpaSpecificationExecutor<Tblserialnumber>{
}
