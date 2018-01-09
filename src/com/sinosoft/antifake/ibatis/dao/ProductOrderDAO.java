

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.ProductOrder;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="ProductOrderDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface ProductOrderDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets ProductOrders information based on id
     * @param id the current id
     * @return ProductOrder populated ProductOrder object
     */
    public ProductOrder getProductOrder(String id);
	public ProductOrder getProductOrderFull(String id);



    /**
     * add a productOrder's information
     * @param productOrder the object to be added
     */
    public void addProductOrder(ProductOrder productOrder);
    /**
     * update a productOrder's information
     * @param productOrder the object to be updated
     */
    public void updateProductOrder(ProductOrder productOrder);

    /**
     * Removes a productOrder from the database by primary Key id
     * @param id the productOrder's id
     */
    public void removeProductOrder(String id);

    /**
     * Gets a list of ProductOrders based on parameters passed in.
     *
     * @return List populated list of ProductOrders
     */
    public List getProductOrders();

    public List getProductOrderByAnd(ProductOrder productOrder,String order_by);

    public List getProductOrderByOr(ProductOrder productOrder,String order_by);

    public List getProductOrderByLike(ProductOrder productOrder,String order_by);
}
