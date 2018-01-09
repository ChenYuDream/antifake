

package com.sinosoft.antifake.ibatis.dao;

import java.util.List;
import com.sinosoft.antifake.ibatis.model.Tblmaterial;

/**
 * User Data Access Object (DAO) interface.
 *
 * <p>
 * <a href="TblmaterialDAO.java.html><i>View Source</i></a>
 * </p>
 *
 * @author <a href="kyle.wu@movit-tech.com">kyle wu</a>
 */
public interface TblmaterialDAO extends com.sinosoft.antifake.ibatis.dao.DAO {
    /**
     * Gets Tblmaterials information based on maMaterial
     * @param maMaterial the current MA_MATERIAL
     * @return Tblmaterial populated Tblmaterial object
     */
    public Tblmaterial getTblmaterial(String maMaterial);
	public Tblmaterial getTblmaterialFull(String maMaterial);



    /**
     * add a tblmaterial's information
     * @param tblmaterial the object to be added
     */
    public void addTblmaterial(Tblmaterial tblmaterial);
    /**
     * update a tblmaterial's information
     * @param tblmaterial the object to be updated
     */
    public void updateTblmaterial(Tblmaterial tblmaterial);

    /**
     * Removes a tblmaterial from the database by primary Key id
     * @param maMaterial the tblmaterial's maMaterial
     */
    public void removeTblmaterial(String maMaterial);

    /**
     * Gets a list of Tblmaterials based on parameters passed in.
     *
     * @return List populated list of Tblmaterials
     */
    public List getTblmaterials();

    public List getTblmaterialByAnd(Tblmaterial tblmaterial,String order_by);

    public List getTblmaterialByOr(Tblmaterial tblmaterial,String order_by);

    public List getTblmaterialByLike(Tblmaterial tblmaterial,String order_by);
}
