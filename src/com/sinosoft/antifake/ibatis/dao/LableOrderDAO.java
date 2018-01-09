

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.LableOrder;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="LableOrderDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface LableOrderDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets LableOrders information based on orderNo
     * @param orderNo the current order_no
     * @return LableOrder populated LableOrder object
     */
    public LableOrder getLableOrder(String orderNo);
	public LableOrder getLableOrderFull(String orderNo);



    /**
     * add a lableOrder's information
     * @param lableOrder the object to be added
     */
    public void addLableOrder(LableOrder lableOrder);
    /**
     * update a lableOrder's information
     * @param lableOrder the object to be updated
     */
    public void updateLableOrder(LableOrder lableOrder);

    /**
     * Removes a lableOrder from the database by primary Key id
     * @param orderNo the lableOrder's orderNo
     */
    public void removeLableOrder(String orderNo);

    /**
     * Gets a list of LableOrders based on parameters passed in.
     *
     * @return List populated list of LableOrders
     */
    public List getLableOrders();

    public List getLableOrderByAnd(LableOrder lableOrder,String order_by);

    public List getLableOrderByOr(LableOrder lableOrder,String order_by);

    public List getLableOrderByLike(LableOrder lableOrder,String order_by);
}
