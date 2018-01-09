

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.Syncuser;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="SyncuserDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface SyncuserDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets Syncusers information based on username
     * @param username the current username
     * @return Syncuser populated Syncuser object
     */
    public Syncuser getSyncuser(String username);
	public Syncuser getSyncuserFull(String username);



    /**
     * add a syncuser's information
     * @param syncuser the object to be added
     */
    public void addSyncuser(Syncuser syncuser);
    /**
     * update a syncuser's information
     * @param syncuser the object to be updated
     */
    public void updateSyncuser(Syncuser syncuser);

    /**
     * Removes a syncuser from the database by primary Key id
     * @param username the syncuser's username
     */
    public void removeSyncuser(String username);

    /**
     * Gets a list of Syncusers based on parameters passed in.
     *
     * @return List populated list of Syncusers
     */
    public List getSyncusers();

    public List getSyncuserByAnd(Syncuser syncuser,String order_by);

    public List getSyncuserByOr(Syncuser syncuser,String order_by);

    public List getSyncuserByLike(Syncuser syncuser,String order_by);
}
