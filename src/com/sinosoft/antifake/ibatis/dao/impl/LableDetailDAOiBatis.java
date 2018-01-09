

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.LableDetailDAO;
import com.sinosoft.antifake.ibatis.model.LableDetail;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="LableDetail.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class LableDetailDAOiBatis extends SqlMapBaseDao implements LableDetailDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.LableDetailDAO#getLableDetail(String)
     */

    public LableDetail getLableDetail(String $keyVar){
        return (LableDetail)getSqlMapClientTemplate().queryForObject("getLableDetail", $keyVar);
    }

    public LableDetail getLableDetailFull(String $keyVar){
        return (LableDetail)getSqlMapClientTemplate().queryForObject("getLableDetailFull", $keyVar);
    }

    public void addLableDetail(final LableDetail lableDetail) {
        getSqlMapClientTemplate().update("addLableDetail", lableDetail);
    }

    public void updateLableDetail(final LableDetail lableDetail) {
    getSqlMapClientTemplate().update("updateLableDetail", lableDetail);
    }
    public void removeLableDetail(String $keyVar){
        getSqlMapClientTemplate().update("removeLableDetail", $keyVar);
    }

    public List getLableDetails() {
        return getSqlMapClientTemplate().queryForList("getLableDetails",null);
    }

    public List getLableDetailByAnd(final LableDetail lableDetail,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableDetail.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableDetailByMap", lableDetail);
    }

    public List getLableDetailByOr(final LableDetail lableDetail,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableDetail.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableDetailByMap", lableDetail);
    }
    
    public List getLableDetailByLike(final LableDetail lableDetail,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableDetail.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableDetailByMap", lableDetail);
    }
}