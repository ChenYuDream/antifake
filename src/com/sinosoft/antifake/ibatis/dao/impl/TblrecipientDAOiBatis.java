

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.TblrecipientDAO;
import com.sinosoft.antifake.ibatis.model.Tblrecipient;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="Tblrecipient.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class TblrecipientDAOiBatis extends SqlMapBaseDao implements TblrecipientDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.TblrecipientDAO#getTblrecipient(String)
     */

    public Tblrecipient getTblrecipient(String $keyVar){
        return (Tblrecipient)getSqlMapClientTemplate().queryForObject("getTblrecipient", $keyVar);
    }

    public Tblrecipient getTblrecipientFull(String $keyVar){
        return (Tblrecipient)getSqlMapClientTemplate().queryForObject("getTblrecipientFull", $keyVar);
    }

    public void addTblrecipient(final Tblrecipient tblrecipient) {
        getSqlMapClientTemplate().update("addTblrecipient", tblrecipient);
    }

    public void updateTblrecipient(final Tblrecipient tblrecipient) {
    getSqlMapClientTemplate().update("updateTblrecipient", tblrecipient);
    }
    public void removeTblrecipient(String $keyVar){
        getSqlMapClientTemplate().update("removeTblrecipient", $keyVar);
    }

    public List getTblrecipients() {
        return getSqlMapClientTemplate().queryForList("getTblrecipients",null);
    }
    
    public List getKPITblrecipients() {
        return getSqlMapClientTemplate().queryForList("getKPITblrecipients",null);
    }

    public List getTblrecipientByAnd(final Tblrecipient tblrecipient,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblrecipient.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblrecipientByMap", tblrecipient);
    }

    public List getTblrecipientByOr(final Tblrecipient tblrecipient,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblrecipient.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblrecipientByMap", tblrecipient);
    }
    
    public List getTblrecipientByLike(final Tblrecipient tblrecipient,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblrecipient.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblrecipientByMap", tblrecipient);
    }
}