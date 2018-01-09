

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.AntiQueryLog;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="AntiQueryLogDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface AntiQueryLogDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets AntiQueryLogs information based on id
     * @param id the current id
     * @return AntiQueryLog populated AntiQueryLog object
     */
    public AntiQueryLog getAntiQueryLog(String id);
	public AntiQueryLog getAntiQueryLogFull(String id);



    /**
     * add a antiQueryLog's information
     * @param antiQueryLog the object to be added
     */
    public void addAntiQueryLog(AntiQueryLog antiQueryLog);
    /**
     * update a antiQueryLog's information
     * @param antiQueryLog the object to be updated
     */
    public void updateAntiQueryLog(AntiQueryLog antiQueryLog);

    /**
     * Removes a antiQueryLog from the database by primary Key id
     * @param id the antiQueryLog's id
     */
    public void removeAntiQueryLog(String id);

    /**
     * Gets a list of AntiQueryLogs based on parameters passed in.
     *
     * @return List populated list of AntiQueryLogs
     */
    public List getAntiQueryLogs();

    public List getAntiQueryLogByAnd(AntiQueryLog antiQueryLog,String order_by);

    public List getAntiQueryLogByOr(AntiQueryLog antiQueryLog,String order_by);

    public List getAntiQueryLogByLike(AntiQueryLog antiQueryLog,String order_by);
}
