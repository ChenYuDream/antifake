

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.KpiInfoDAO;
import com.sinosoft.antifake.ibatis.model.KpiInfo;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="KpiInfo.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class KpiInfoDAOiBatis extends SqlMapBaseDao implements KpiInfoDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.KpiInfoDAO#getKpiInfo(String)
     */

    public KpiInfo getKpiInfo(String $keyVar){
        return (KpiInfo)getSqlMapClientTemplate().queryForObject("getKpiInfo", $keyVar);
    }

    public KpiInfo getKpiInfoFull(String $keyVar){
        return (KpiInfo)getSqlMapClientTemplate().queryForObject("getKpiInfoFull", $keyVar);
    }

    public void addKpiInfo(final KpiInfo kpiInfo) {
        getSqlMapClientTemplate().update("addKpiInfo", kpiInfo);
    }

    public void updateKpiInfo(final KpiInfo kpiInfo) {
    getSqlMapClientTemplate().update("updateKpiInfo", kpiInfo);
    }
    public void removeKpiInfo(String $keyVar){
        getSqlMapClientTemplate().update("removeKpiInfo", $keyVar);
    }

    public List getKpiInfos() {
        return getSqlMapClientTemplate().queryForList("getKpiInfos",null);
    }

    public List getKpiInfoByAnd(final KpiInfo kpiInfo,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        kpiInfo.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getKpiInfoByMap", kpiInfo);
    }

    public List getKpiInfoByOr(final KpiInfo kpiInfo,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        kpiInfo.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getKpiInfoByMap", kpiInfo);
    }
    
    public List getKpiInfoByLike(final KpiInfo kpiInfo,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        kpiInfo.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getKpiInfoByMap", kpiInfo);
    }
}