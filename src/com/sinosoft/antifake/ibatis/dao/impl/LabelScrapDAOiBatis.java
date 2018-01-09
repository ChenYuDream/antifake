

package com.sinosoft.antifake.ibatis.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sinosoft.antifake.ibatis.dao.LabelScrapDAO;
import com.sinosoft.antifake.ibatis.model.LabelScrap;
import org.springframework.orm.ObjectRetrievalFailureException;
import com.sinosoft.antifake.ibatis.dao.SqlMapBaseDao;


/**
 * This class interacts with Spring's IbatisTemplate to save/delete
 * <p>
 * <a href="LabelScrap.DAOiBatis.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:kyle.wu@movit-tech.com">kyle wu</a>
*/
public class LabelScrapDAOiBatis extends SqlMapBaseDao implements LabelScrapDAO {

    /**
     * @see com.sinosoft.antifake.ibatis.dao.LabelScrapDAO#getLabelScrap(String)
     */

    public LabelScrap getLabelScrap(String $keyVar){
        return (LabelScrap)getSqlMapClientTemplate().queryForObject("getLabelScrap", $keyVar);
    }

    public LabelScrap getLabelScrapFull(String $keyVar){
        return (LabelScrap)getSqlMapClientTemplate().queryForObject("getLabelScrapFull", $keyVar);
    }

    public void addLabelScrap(final LabelScrap labelScrap) {
        getSqlMapClientTemplate().update("addLabelScrap", labelScrap);
    }

    public void updateLabelScrap(final LabelScrap labelScrap) {
    getSqlMapClientTemplate().update("updateLabelScrap", labelScrap);
    }
    public void removeLabelScrap(String $keyVar){
        getSqlMapClientTemplate().update("removeLabelScrap", $keyVar);
    }

    public List getLabelScraps() {
        return getSqlMapClientTemplate().queryForList("getLabelScraps",null);
    }

    public List getLabelScrapByAnd(final LabelScrap labelScrap,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_AND, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        labelScrap.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLabelScrapByMap", labelScrap);
    }

    public List getLabelScrapByOr(final LabelScrap labelScrap,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_OR, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        labelScrap.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLabelScrapByMap", labelScrap);
    }
    
    public List getLabelScrapByLike(final LabelScrap labelScrap,String order_by){
        Map map = new HashMap();
        map.put(FIND_BY_LIKE, "True");
        if (order_by != null)
            map.put(ORDER_BY,order_by);
        labelScrap.setMagic(map);
        return getSqlMapClientTemplate().queryForList("getLabelScrapByMap", labelScrap);
    }
}