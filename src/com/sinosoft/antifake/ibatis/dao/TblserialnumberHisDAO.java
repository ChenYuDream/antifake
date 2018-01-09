

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.TblserialnumberHis;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="TblserialnumberHisDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface TblserialnumberHisDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets TblserialnumberHiss information based on snoSerialno
     * @param snoSerialno the current SNO_SERIALNO
     * @return TblserialnumberHis populated TblserialnumberHis object
     */
    public TblserialnumberHis getTblserialnumberHis(String snoSerialno);
	public TblserialnumberHis getTblserialnumberHisFull(String snoSerialno);



    /**
     * add a tblserialnumberHis's information
     * @param tblserialnumberHis the object to be added
     */
    public void addTblserialnumberHis(TblserialnumberHis tblserialnumberHis);
    /**
     * update a tblserialnumberHis's information
     * @param tblserialnumberHis the object to be updated
     */
    public void updateTblserialnumberHis(TblserialnumberHis tblserialnumberHis);

    /**
     * Removes a tblserialnumberHis from the database by primary Key id
     * @param snoSerialno the tblserialnumberHis's snoSerialno
     */
    public void removeTblserialnumberHis(String snoSerialno);

    /**
     * Gets a list of TblserialnumberHiss based on parameters passed in.
     *
     * @return List populated list of TblserialnumberHiss
     */
    public List getTblserialnumberHiss();

    public List getTblserialnumberHisByAnd(TblserialnumberHis tblserialnumberHis,String order_by);

    public List getTblserialnumberHisByOr(TblserialnumberHis tblserialnumberHis,String order_by);

    public List getTblserialnumberHisByLike(TblserialnumberHis tblserialnumberHis,String order_by);
}
