

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.LableSummaryDAO;
import com.sinosoft.antifake.ibatis.model.LableSummary;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="LableSummary.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class LableSummaryDAOiBatis extends SqlMapBaseDao implements LableSummaryDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.LableSummaryDAO#getLableSummary(String)
     */

    public LableSummary getLableSummary(String $keyVar){
        return (LableSummary)getSqlMapClientTemplate().queryForObject("getLableSummary", $keyVar);
    }

    public LableSummary getLableSummaryFull(String $keyVar){
        return (LableSummary)getSqlMapClientTemplate().queryForObject("getLableSummaryFull", $keyVar);
    }

    public void addLableSummary(final LableSummary lableSummary) {
        getSqlMapClientTemplate().update("addLableSummary", lableSummary);
    }

    public void updateLableSummary(final LableSummary lableSummary) {
    getSqlMapClientTemplate().update("updateLableSummary", lableSummary);
    }
    public void removeLableSummary(String $keyVar){
        getSqlMapClientTemplate().update("removeLableSummary", $keyVar);
    }

    public List getLableSummarys() {
        return getSqlMapClientTemplate().queryForList("getLableSummarys",null);
    }

    public List getLableSummaryByAnd(final LableSummary lableSummary,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableSummary.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableSummaryByMap", lableSummary);
    }

    public List getLableSummaryByOr(final LableSummary lableSummary,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableSummary.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableSummaryByMap", lableSummary);
    }
    
    public List getLableSummaryByLike(final LableSummary lableSummary,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableSummary.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableSummaryByMap", lableSummary);
    }
}