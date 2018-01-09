package com.sinosoft.antifake.ibatis.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author: heyman
 * Datetime: 2008-6-28 16:51:43
 */
public interface GeneralDAO {
    public Object getObjectSql(final String sqlname, final Object params);

    public List getListSql(final String sqlname, final Object params);

    public void updateSql(final String sqlname, final Object params);
    
    public Date getDatabaseDate();
    
    public Integer deleteSql(final String sqlname, final Object params);
}
