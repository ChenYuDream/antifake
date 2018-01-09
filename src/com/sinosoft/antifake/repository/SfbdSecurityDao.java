package com.sinosoft.antifake.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sinosoft.antifake.entity.SfbdSecurity;

/**
 * 
 * @Description:SFBD工厂防伪数据dao
 * @author wangxueqiang
 * @date 2017年12月7日 上午11:01:11
 *
 */
public interface SfbdSecurityDao extends PagingAndSortingRepository<SfbdSecurity, String>, JpaSpecificationExecutor<SfbdSecurity> {
	/**
	 * 
	 * @Description: 根据产品编码查询信息
	 * @param barcode 
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月11日 下午3:02:54
	 *
	 */
	List<SfbdSecurity> findByBarcode(String barcode);

}
