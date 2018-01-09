

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.LableDetail;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="LableDetailDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface LableDetailDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets LableDetails information based on serialNumber
     * @param serialNumber the current serial_number
     * @return LableDetail populated LableDetail object
     */
    public LableDetail getLableDetail(String serialNumber);
	public LableDetail getLableDetailFull(String serialNumber);



    /**
     * add a lableDetail's information
     * @param lableDetail the object to be added
     */
    public void addLableDetail(LableDetail lableDetail);
    /**
     * update a lableDetail's information
     * @param lableDetail the object to be updated
     */
    public void updateLableDetail(LableDetail lableDetail);

    /**
     * Removes a lableDetail from the database by primary Key id
     * @param serialNumber the lableDetail's serialNumber
     */
    public void removeLableDetail(String serialNumber);

    /**
     * Gets a list of LableDetails based on parameters passed in.
     *
     * @return List populated list of LableDetails
     */
    public List getLableDetails();

    public List getLableDetailByAnd(LableDetail lableDetail,String order_by);

    public List getLableDetailByOr(LableDetail lableDetail,String order_by);

    public List getLableDetailByLike(LableDetail lableDetail,String order_by);
}
