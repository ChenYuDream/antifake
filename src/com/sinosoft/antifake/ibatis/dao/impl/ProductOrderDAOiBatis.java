

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.ProductOrderDAO;
import com.sinosoft.antifake.ibatis.model.ProductOrder;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="ProductOrder.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class ProductOrderDAOiBatis extends SqlMapBaseDao implements ProductOrderDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.ProductOrderDAO#getProductOrder(String)
     */

    public ProductOrder getProductOrder(String $keyVar){
        return (ProductOrder)getSqlMapClientTemplate().queryForObject("getProductOrder", $keyVar);
    }

    public ProductOrder getProductOrderFull(String $keyVar){
        return (ProductOrder)getSqlMapClientTemplate().queryForObject("getProductOrderFull", $keyVar);
    }

    public void addProductOrder(final ProductOrder productOrder) {
        getSqlMapClientTemplate().update("addProductOrder", productOrder);
    }

    public void updateProductOrder(final ProductOrder productOrder) {
    getSqlMapClientTemplate().update("updateProductOrder", productOrder);
    }
    public void removeProductOrder(String $keyVar){
        getSqlMapClientTemplate().update("removeProductOrder", $keyVar);
    }

    public List getProductOrders() {
        return getSqlMapClientTemplate().queryForList("getProductOrders",null);
    }

    public List getProductOrderByAnd(final ProductOrder productOrder,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        productOrder.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getProductOrderByMap", productOrder);
    }

    public List getProductOrderByOr(final ProductOrder productOrder,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        productOrder.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getProductOrderByMap", productOrder);
    }
    
    public List getProductOrderByLike(final ProductOrder productOrder,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        productOrder.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getProductOrderByMap", productOrder);
    }
}