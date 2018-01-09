package com.sinosoft.antifake.ibatis.dao;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;



public class SqlMapBaseDao extends SqlMapClientDaoSupport {
	protected SqlMapClientTemplate handler = this.getSqlMapClientTemplate();

	public SqlMapBaseDao() {
		super();
	}
}
