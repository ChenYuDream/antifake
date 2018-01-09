

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.SyncuserDAO;
import com.sinosoft.antifake.ibatis.model.Syncuser;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="Syncuser.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class SyncuserDAOiBatis extends SqlMapBaseDao implements SyncuserDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.SyncuserDAO#getSyncuser(String)
     */

    public Syncuser getSyncuser(String $keyVar){
        return (Syncuser)getSqlMapClientTemplate().queryForObject("getSyncuser", $keyVar);
    }

    public Syncuser getSyncuserFull(String $keyVar){
        return (Syncuser)getSqlMapClientTemplate().queryForObject("getSyncuserFull", $keyVar);
    }

    public void addSyncuser(final Syncuser syncuser) {
        getSqlMapClientTemplate().update("addSyncuser", syncuser);
    }

    public void updateSyncuser(final Syncuser syncuser) {
    getSqlMapClientTemplate().update("updateSyncuser", syncuser);
    }
    public void removeSyncuser(String $keyVar){
        getSqlMapClientTemplate().update("removeSyncuser", $keyVar);
    }

    public List getSyncusers() {
        return getSqlMapClientTemplate().queryForList("getSyncusers",null);
    }

    public List getSyncuserByAnd(final Syncuser syncuser,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        syncuser.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getSyncuserByMap", syncuser);
    }

    public List getSyncuserByOr(final Syncuser syncuser,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        syncuser.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getSyncuserByMap", syncuser);
    }
    
    public List getSyncuserByLike(final Syncuser syncuser,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        syncuser.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getSyncuserByMap", syncuser);
    }
}