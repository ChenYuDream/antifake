

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.TblserialnumberDAO;
import com.sinosoft.antifake.ibatis.model.Tblserialnumber;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="Tblserialnumber.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class TblserialnumberDAOiBatis extends SqlMapBaseDao implements TblserialnumberDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.TblserialnumberDAO#getTblserialnumber(String)
     */

    public Tblserialnumber getTblserialnumber(String $keyVar){
        return (Tblserialnumber)getSqlMapClientTemplate().queryForObject("getTblserialnumber", $keyVar);
    }

    public Tblserialnumber getTblserialnumberFull(String $keyVar){
        return (Tblserialnumber)getSqlMapClientTemplate().queryForObject("getTblserialnumberFull", $keyVar);
    }

    public void addTblserialnumber(final Tblserialnumber tblserialnumber) {
        getSqlMapClientTemplate().update("addTblserialnumber", tblserialnumber);
    }

    public void updateTblserialnumber(final Tblserialnumber tblserialnumber) {
    getSqlMapClientTemplate().update("updateTblserialnumber", tblserialnumber);
    }
    public void removeTblserialnumber(String $keyVar){
        getSqlMapClientTemplate().update("removeTblserialnumber", $keyVar);
    }

    public List getTblserialnumbers() {
        return getSqlMapClientTemplate().queryForList("getTblserialnumbers",null);
    }

    public List getTblserialnumberByAnd(final Tblserialnumber tblserialnumber,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblserialnumber.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblserialnumberByMap", tblserialnumber);
    }

    public List getTblserialnumberByOr(final Tblserialnumber tblserialnumber,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblserialnumber.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblserialnumberByMap", tblserialnumber);
    }
    
    public List getTblserialnumberByLike(final Tblserialnumber tblserialnumber,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblserialnumber.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblserialnumberByMap", tblserialnumber);
    }
}