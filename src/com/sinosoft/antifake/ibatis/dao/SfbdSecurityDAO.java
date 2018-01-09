package com.sinosoft.antifake.ibatis.dao;

import com.sinosoft.antifake.entity.SfbdSecurity;

/**
 * 
 * @Description:SFBD工厂防伪数据S
 * @author wangxueqiang
 * @date 2017年12月7日 下午5:10:24
 *
 */
public interface SfbdSecurityDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
	/**
	 * 
	 * @Description: 根据产品编码查询信息
	 * @param productCode 物料代码
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月11日 下午3:02:54
	 *
	 */
	 SfbdSecurity findByProductCode(String productCode);
}
