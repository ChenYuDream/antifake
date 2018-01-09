

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.ReportInfoDAO;
import com.sinosoft.antifake.ibatis.model.ReportInfo;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="ReportInfo.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class ReportInfoDAOiBatis extends SqlMapBaseDao implements ReportInfoDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.ReportInfoDAO#getReportInfo(String)
     */

    public ReportInfo getReportInfo(String $keyVar){
        return (ReportInfo)getSqlMapClientTemplate().queryForObject("getReportInfo", $keyVar);
    }

    public ReportInfo getReportInfoFull(String $keyVar){
        return (ReportInfo)getSqlMapClientTemplate().queryForObject("getReportInfoFull", $keyVar);
    }

    public void addReportInfo(final ReportInfo reportInfo) {
        getSqlMapClientTemplate().update("addReportInfo", reportInfo);
    }

    public void updateReportInfo(final ReportInfo reportInfo) {
    getSqlMapClientTemplate().update("updateReportInfo", reportInfo);
    }
    public void removeReportInfo(String $keyVar){
        getSqlMapClientTemplate().update("removeReportInfo", $keyVar);
    }

    public List getReportInfos() {
        return getSqlMapClientTemplate().queryForList("getReportInfos",null);
    }

    public List getReportInfoByAnd(final ReportInfo reportInfo,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        reportInfo.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getReportInfoByMap", reportInfo);
    }

    public List getReportInfoByOr(final ReportInfo reportInfo,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        reportInfo.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getReportInfoByMap", reportInfo);
    }
    
    public List getReportInfoByLike(final ReportInfo reportInfo,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        reportInfo.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getReportInfoByMap", reportInfo);
    }
}