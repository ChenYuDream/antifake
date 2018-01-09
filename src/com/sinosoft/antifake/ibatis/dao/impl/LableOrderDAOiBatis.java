

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.LableOrderDAO;
import com.sinosoft.antifake.ibatis.model.LableOrder;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="LableOrder.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class LableOrderDAOiBatis extends SqlMapBaseDao implements LableOrderDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.LableOrderDAO#getLableOrder(String)
     */

    public LableOrder getLableOrder(String $keyVar){
        return (LableOrder)getSqlMapClientTemplate().queryForObject("getLableOrder", $keyVar);
    }

    public LableOrder getLableOrderFull(String $keyVar){
        return (LableOrder)getSqlMapClientTemplate().queryForObject("getLableOrderFull", $keyVar);
    }

    public void addLableOrder(final LableOrder lableOrder) {
        getSqlMapClientTemplate().update("addLableOrder", lableOrder);
    }

    public void updateLableOrder(final LableOrder lableOrder) {
    getSqlMapClientTemplate().update("updateLableOrder", lableOrder);
    }
    public void removeLableOrder(String $keyVar){
        getSqlMapClientTemplate().update("removeLableOrder", $keyVar);
    }

    public List getLableOrders() {
        return getSqlMapClientTemplate().queryForList("getLableOrders",null);
    }

    public List getLableOrderByAnd(final LableOrder lableOrder,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableOrder.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableOrderByMap", lableOrder);
    }

    public List getLableOrderByOr(final LableOrder lableOrder,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableOrder.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableOrderByMap", lableOrder);
    }
    
    public List getLableOrderByLike(final LableOrder lableOrder,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        lableOrder.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLableOrderByMap", lableOrder);
    }
}