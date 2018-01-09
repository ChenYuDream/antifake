

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.LableSummary;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="LableSummaryDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface LableSummaryDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets LableSummarys information based on id
     * @param id the current id
     * @return LableSummary populated LableSummary object
     */
    public LableSummary getLableSummary(String id);
	public LableSummary getLableSummaryFull(String id);



    /**
     * add a lableSummary's information
     * @param lableSummary the object to be added
     */
    public void addLableSummary(LableSummary lableSummary);
    /**
     * update a lableSummary's information
     * @param lableSummary the object to be updated
     */
    public void updateLableSummary(LableSummary lableSummary);

    /**
     * Removes a lableSummary from the database by primary Key id
     * @param id the lableSummary's id
     */
    public void removeLableSummary(String id);

    /**
     * Gets a list of LableSummarys based on parameters passed in.
     *
     * @return List populated list of LableSummarys
     */
    public List getLableSummarys();

    public List getLableSummaryByAnd(LableSummary lableSummary,String order_by);

    public List getLableSummaryByOr(LableSummary lableSummary,String order_by);

    public List getLableSummaryByLike(LableSummary lableSummary,String order_by);
}
