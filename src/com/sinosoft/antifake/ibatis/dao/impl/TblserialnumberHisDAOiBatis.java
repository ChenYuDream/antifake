

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.TblserialnumberHisDAO;
import com.sinosoft.antifake.ibatis.model.TblserialnumberHis;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="TblserialnumberHis.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class TblserialnumberHisDAOiBatis extends SqlMapBaseDao implements TblserialnumberHisDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.TblserialnumberHisDAO#getTblserialnumberHis(String)
     */

    public TblserialnumberHis getTblserialnumberHis(String $keyVar){
        return (TblserialnumberHis)getSqlMapClientTemplate().queryForObject("getTblserialnumberHis", $keyVar);
    }

    public TblserialnumberHis getTblserialnumberHisFull(String $keyVar){
        return (TblserialnumberHis)getSqlMapClientTemplate().queryForObject("getTblserialnumberHisFull", $keyVar);
    }

    public void addTblserialnumberHis(final TblserialnumberHis tblserialnumberHis) {
        getSqlMapClientTemplate().update("addTblserialnumberHis", tblserialnumberHis);
    }

    public void updateTblserialnumberHis(final TblserialnumberHis tblserialnumberHis) {
    getSqlMapClientTemplate().update("updateTblserialnumberHis", tblserialnumberHis);
    }
    public void removeTblserialnumberHis(String $keyVar){
        getSqlMapClientTemplate().update("removeTblserialnumberHis", $keyVar);
    }

    public List getTblserialnumberHiss() {
        return getSqlMapClientTemplate().queryForList("getTblserialnumberHiss",null);
    }

    public List getTblserialnumberHisByAnd(final TblserialnumberHis tblserialnumberHis,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblserialnumberHis.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblserialnumberHisByMap", tblserialnumberHis);
    }

    public List getTblserialnumberHisByOr(final TblserialnumberHis tblserialnumberHis,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblserialnumberHis.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblserialnumberHisByMap", tblserialnumberHis);
    }
    
    public List getTblserialnumberHisByLike(final TblserialnumberHis tblserialnumberHis,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblserialnumberHis.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblserialnumberHisByMap", tblserialnumberHis);
    }
}