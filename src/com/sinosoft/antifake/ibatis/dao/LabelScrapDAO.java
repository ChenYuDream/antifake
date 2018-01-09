

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.LabelScrap;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="LabelScrapDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface LabelScrapDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets LabelScraps information based on serialNumber
     * @param serialNumber the current serial_number
     * @return LabelScrap populated LabelScrap object
     */
    public LabelScrap getLabelScrap(String serialNumber);
	public LabelScrap getLabelScrapFull(String serialNumber);



    /**
     * add a labelScrap's information
     * @param labelScrap the object to be added
     */
    public void addLabelScrap(LabelScrap labelScrap);
    /**
     * update a labelScrap's information
     * @param labelScrap the object to be updated
     */
    public void updateLabelScrap(LabelScrap labelScrap);

    /**
     * Removes a labelScrap from the database by primary Key id
     * @param serialNumber the labelScrap's serialNumber
     */
    public void removeLabelScrap(String serialNumber);

    /**
     * Gets a list of LabelScraps based on parameters passed in.
     *
     * @return List populated list of LabelScraps
     */
    public List getLabelScraps();

    public List getLabelScrapByAnd(LabelScrap labelScrap,String order_by);

    public List getLabelScrapByOr(LabelScrap labelScrap,String order_by);

    public List getLabelScrapByLike(LabelScrap labelScrap,String order_by);
}
