

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.ReportInfo;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="ReportInfoDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface ReportInfoDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets ReportInfos information based on id
     * @param id the current id
     * @return ReportInfo populated ReportInfo object
     */
    public ReportInfo getReportInfo(String id);
	public ReportInfo getReportInfoFull(String id);



    /**
     * add a reportInfo's information
     * @param reportInfo the object to be added
     */
    public void addReportInfo(ReportInfo reportInfo);
    /**
     * update a reportInfo's information
     * @param reportInfo the object to be updated
     */
    public void updateReportInfo(ReportInfo reportInfo);

    /**
     * Removes a reportInfo from the database by primary Key id
     * @param id the reportInfo's id
     */
    public void removeReportInfo(String id);

    /**
     * Gets a list of ReportInfos based on parameters passed in.
     *
     * @return List populated list of ReportInfos
     */
    public List getReportInfos();

    public List getReportInfoByAnd(ReportInfo reportInfo,String order_by);

    public List getReportInfoByOr(ReportInfo reportInfo,String order_by);

    public List getReportInfoByLike(ReportInfo reportInfo,String order_by);
}
