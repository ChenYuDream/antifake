package com.sinosoft.antifake.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.ProductOrder;
public interface ProductOrderDao extends PagingAndSortingRepository<ProductOrder, String>, JpaSpecificationExecutor<ProductOrder>{

}
