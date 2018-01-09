

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.Tblrecipient;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="TblrecipientDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface TblrecipientDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets Tblrecipients information based on recipientNo
     * @param recipientNo the current recipient_no
     * @return Tblrecipient populated Tblrecipient object
     */
    public Tblrecipient getTblrecipient(String recipientNo);
	public Tblrecipient getTblrecipientFull(String recipientNo);



    /**
     * add a tblrecipient's information
     * @param tblrecipient the object to be added
     */
    public void addTblrecipient(Tblrecipient tblrecipient);
    /**
     * update a tblrecipient's information
     * @param tblrecipient the object to be updated
     */
    public void updateTblrecipient(Tblrecipient tblrecipient);

    /**
     * Removes a tblrecipient from the database by primary Key id
     * @param recipientNo the tblrecipient's recipientNo
     */
    public void removeTblrecipient(String recipientNo);

    /**
     * Gets a list of Tblrecipients based on parameters passed in.
     *
     * @return List populated list of Tblrecipients
     */
    public List getTblrecipients();
    
    
    public List getKPITblrecipients();
    

    public List getTblrecipientByAnd(Tblrecipient tblrecipient,String order_by);

    public List getTblrecipientByOr(Tblrecipient tblrecipient,String order_by);

    public List getTblrecipientByLike(Tblrecipient tblrecipient,String order_by);
}
