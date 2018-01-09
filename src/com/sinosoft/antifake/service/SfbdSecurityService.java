package com.sinosoft.antifake.service;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.entity.SfbdSecurity;
import com.sinosoft.antifake.repository.SfbdSecurityDao;

/**
 * 
 * @Description:SFBD工厂防伪数据service
 * @author wangxueqiang
 * @date 2017年12月7日 上午11:08:32
 *
 */
@Component
public class SfbdSecurityService {
	@Autowired
	private SfbdSecurityDao sfbdSecurityDao;
	
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * 
	 * @Description: 根据条件查询SFBD工厂防伪数据
	 * @param searchParams 查询参数
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月7日 上午11:20:16
	 *
	 */
	@SuppressWarnings("unused")
	private List<SfbdSecurity> getSfbdSecurityList(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<SfbdSecurity> spec = DynamicSpecifications.bySearchFilter(filters.values(), SfbdSecurity.class);
		return sfbdSecurityDao.findAll(spec, new Sort(Direction.DESC, "createTime"));
	}
	
	/**
	 * 
	 * @Description: 根据产品码查询信息
	 * @param barcode 
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月11日 下午3:04:27
	 *
	 */
	public List<SfbdSecurity> findByBarcode(String barcode){
		return sfbdSecurityDao.findByBarcode(barcode);		
	}
	

	
}
