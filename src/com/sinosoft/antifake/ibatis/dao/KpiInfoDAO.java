

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.KpiInfo;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="KpiInfoDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface KpiInfoDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets KpiInfos information based on spcialNo
     * @param spcialNo the current spcial_no
     * @return KpiInfo populated KpiInfo object
     */
    public KpiInfo getKpiInfo(String spcialNo);
	public KpiInfo getKpiInfoFull(String spcialNo);



    /**
     * add a kpiInfo's information
     * @param kpiInfo the object to be added
     */
    public void addKpiInfo(KpiInfo kpiInfo);
    /**
     * update a kpiInfo's information
     * @param kpiInfo the object to be updated
     */
    public void updateKpiInfo(KpiInfo kpiInfo);

    /**
     * Removes a kpiInfo from the database by primary Key id
     * @param spcialNo the kpiInfo's spcialNo
     */
    public void removeKpiInfo(String spcialNo);

    /**
     * Gets a list of KpiInfos based on parameters passed in.
     *
     * @return List populated list of KpiInfos
     */
    public List getKpiInfos();

    public List getKpiInfoByAnd(KpiInfo kpiInfo,String order_by);

    public List getKpiInfoByOr(KpiInfo kpiInfo,String order_by);

    public List getKpiInfoByLike(KpiInfo kpiInfo,String order_by);
}
