

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.AntiQueryLogDAO;
import com.sinosoft.antifake.ibatis.model.AntiQueryLog;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="AntiQueryLog.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class AntiQueryLogDAOiBatis extends SqlMapBaseDao implements AntiQueryLogDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.AntiQueryLogDAO#getAntiQueryLog(String)
     */

    public AntiQueryLog getAntiQueryLog(String $keyVar){
        return (AntiQueryLog)getSqlMapClientTemplate().queryForObject("getAntiQueryLog", $keyVar);
    }

    public AntiQueryLog getAntiQueryLogFull(String $keyVar){
        return (AntiQueryLog)getSqlMapClientTemplate().queryForObject("getAntiQueryLogFull", $keyVar);
    }

    public void addAntiQueryLog(final AntiQueryLog antiQueryLog) {
        getSqlMapClientTemplate().update("addAntiQueryLog", antiQueryLog);
    }

    public void updateAntiQueryLog(final AntiQueryLog antiQueryLog) {
    getSqlMapClientTemplate().update("updateAntiQueryLog", antiQueryLog);
    }
    public void removeAntiQueryLog(String $keyVar){
        getSqlMapClientTemplate().update("removeAntiQueryLog", $keyVar);
    }

    public List getAntiQueryLogs() {
        return getSqlMapClientTemplate().queryForList("getAntiQueryLogs",null);
    }

    public List getAntiQueryLogByAnd(final AntiQueryLog antiQueryLog,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        antiQueryLog.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getAntiQueryLogByMap", antiQueryLog);
    }

    public List getAntiQueryLogByOr(final AntiQueryLog antiQueryLog,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        antiQueryLog.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getAntiQueryLogByMap", antiQueryLog);
    }
    
    public List getAntiQueryLogByLike(final AntiQueryLog antiQueryLog,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        antiQueryLog.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getAntiQueryLogByMap", antiQueryLog);
    }
}