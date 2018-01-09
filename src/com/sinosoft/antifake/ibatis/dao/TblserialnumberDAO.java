

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.Tblserialnumber;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="TblserialnumberDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface TblserialnumberDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets Tblserialnumbers information based on snoSerialno
     * @param snoSerialno the current SNO_SERIALNO
     * @return Tblserialnumber populated Tblserialnumber object
     */
    public Tblserialnumber getTblserialnumber(String snoSerialno);
	public Tblserialnumber getTblserialnumberFull(String snoSerialno);



    /**
     * add a tblserialnumber's information
     * @param tblserialnumber the object to be added
     */
    public void addTblserialnumber(Tblserialnumber tblserialnumber);
    /**
     * update a tblserialnumber's information
     * @param tblserialnumber the object to be updated
     */
    public void updateTblserialnumber(Tblserialnumber tblserialnumber);

    /**
     * Removes a tblserialnumber from the database by primary Key id
     * @param snoSerialno the tblserialnumber's snoSerialno
     */
    public void removeTblserialnumber(String snoSerialno);

    /**
     * Gets a list of Tblserialnumbers based on parameters passed in.
     *
     * @return List populated list of Tblserialnumbers
     */
    public List getTblserialnumbers();

    public List getTblserialnumberByAnd(Tblserialnumber tblserialnumber,String order_by);

    public List getTblserialnumberByOr(Tblserialnumber tblserialnumber,String order_by);

    public List getTblserialnumberByLike(Tblserialnumber tblserialnumber,String order_by);
}
