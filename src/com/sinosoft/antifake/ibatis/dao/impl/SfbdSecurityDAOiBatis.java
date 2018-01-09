package com.sinosoft.antifake.ibatis.dao.impl;
import com.sinosoft.antifake.entity.SfbdSecurity;
import com.sinosoft.antifake.ibatis.dao.SfbdSecurityDAO;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;
public class SfbdSecurityDAOiBatis extends SqlMapBaseDao implements SfbdSecurityDAO{

	@SuppressWarnings("deprecation")
	/**
	 * 
	 * @Description: 根据产品编码查询信息
	 * @param productCode
	 * @return
	 * @see com.sinosoft.antifake.ibatis.dao.SfbdSecurityDAO#findByProductCode(java.lang.String)
	 * @author wangxueqiang
	 * @date 2017年12月11日 下午4:19:08
	 *
	 */
	@Override
	public SfbdSecurity findByProductCode(String productCode) {
		 return (SfbdSecurity)getSqlMapClientTemplate().queryForObject("findByProductCode", productCode);
	}

}
