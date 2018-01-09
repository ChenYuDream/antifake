

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.TblmaterialDAO;
import com.sinosoft.antifake.ibatis.model.Tblmaterial;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="Tblmaterial.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class TblmaterialDAOiBatis extends SqlMapBaseDao implements TblmaterialDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.TblmaterialDAO#getTblmaterial(String)
     */

    public Tblmaterial getTblmaterial(String $keyVar){
        return (Tblmaterial)getSqlMapClientTemplate().queryForObject("getTblmaterial", $keyVar);
    }

    public Tblmaterial getTblmaterialFull(String $keyVar){
        return (Tblmaterial)getSqlMapClientTemplate().queryForObject("getTblmaterialFull", $keyVar);
    }

    public void addTblmaterial(final Tblmaterial tblmaterial) {
        getSqlMapClientTemplate().update("addTblmaterial", tblmaterial);
    }

    public void updateTblmaterial(final Tblmaterial tblmaterial) {
    getSqlMapClientTemplate().update("updateTblmaterial", tblmaterial);
    }
    public void removeTblmaterial(String $keyVar){
        getSqlMapClientTemplate().update("removeTblmaterial", $keyVar);
    }

    public List getTblmaterials() {
        return getSqlMapClientTemplate().queryForList("getTblmaterials",null);
    }

    public List getTblmaterialByAnd(final Tblmaterial tblmaterial,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblmaterial.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblmaterialByMap", tblmaterial);
    }

    public List getTblmaterialByOr(final Tblmaterial tblmaterial,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblmaterial.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblmaterialByMap", tblmaterial);
    }
    
    public List getTblmaterialByLike(final Tblmaterial tblmaterial,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        tblmaterial.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getTblmaterialByMap", tblmaterial);
    }
}