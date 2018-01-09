package com.sinosoft.antifake.ibatis.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sinosoft.antifake.ibatis.dao.GeneralDAO;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;

/**
 * @author: heyman
 * Datetime: 2008-6-28 14:36:12
 */
public class GeneralDAOiBatis extends SqlMapBaseDao implements GeneralDAO {
    public Object getObjectSql(String sqlname, Object params) {
        return getSqlMapClientTemplate().queryForObject(sqlname, params);
    }

    public List getListSql(String sqlname, Object params) {
        return getSqlMapClientTemplate().queryForList(sqlname, params);
    }

    @SuppressWarnings("deprecation")
	public void updateSql(final String sqlname, final Object params){
    	System.out.println("===========>");
        getSqlMapClientTemplate().update(sqlname, params);
    }
    public Date getDatabaseDate() {
        return (Date) getObjectSql("getDatabaseDate", null);
    }
    public Integer deleteSql(String sqlname, Object params){
    	return (Integer)getSqlMapClientTemplate().delete(sqlname, params);
    }

}