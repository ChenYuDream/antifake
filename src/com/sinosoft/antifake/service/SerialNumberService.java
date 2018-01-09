package com.sinosoft.antifake.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.repository.QueryLogDao;
import com.sinosoft.antifake.repository.SerialNumberJpaDao;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.antifake.entity.SerialNumberQueryData;
import com.sinosoft.antifake.entity.Tblserialnumber;
import com.sinosoft.antifake.repository.SerialNumberDao;
import com.sinosoft.antifake.web.rest.SerialNumberRestController.RequestFilter;

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class SerialNumberService {
    @Autowired
    private SerialNumberDao serialNumberDao;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SerialNumberJpaDao serialNumberJpaDao;
    @Autowired
    private QueryLogDao queryLogDao;


    /**
     * 插入数据
     */
    public String insert(Tblserialnumber tblsn) {
        return serialNumberDao.insert(tblsn);
    }

    /**
     * 检验用户是够存在tblUser
     */
    public boolean checkUser(String userName, String password) {
        return serialNumberDao.checkUser(userName, password);
    }

    public List<SerialNumberQueryData> serialNumberQuery(RequestFilter filter, String userName) {
        return serialNumberDao.serialNumberQuery(filter, userName);
    }

    /**
     * 查单条
     *
     * @param snoSerialno
     * @return
     */
    @Transactional(readOnly = false)
    public Map<String, Object> selectOneById(String snoSerialno) {
        Map<String, Object> resultMap = new HashMap<String, Object>(16);
        //查单条，union all
        String sql = "select * from (select n.* from dbo.tblSerialNumber n union all " +
                "select s.product_code as SNO_SERIALNO,'' as SNO_BARNO,'' as SNO_BOXNO,s.barcode as SNO_MATERIAL,s.create_date as SNO_CREATEDATE, " +
                "'' as SNO_DC,'' as SNO_JV,'' as SNO_COMPANY,'' as SNO_PRODUCTIONLINE,'' as SNO_PRODUCTIONLINENO, " +
                "'' as SNO_WORKER,'' as SNO_PRODUCTIONORDER,'' as SNO_MODIFYUSER,'' as SNO_MODIFYDATE,'' as SNO_PRODUCTINFO " +
                "from dbo.tbl_sfbd_security s) t " +
                "where  t.SNO_SERIALNO ='" + snoSerialno + "'";
        Query query = entityManager.createNativeQuery(sql, Tblserialnumber.class);
        List<Tblserialnumber> tblserialnumberList = query.getResultList();
        Tblserialnumber tblserialnumber = null;
        if (tblserialnumberList != null && tblserialnumberList.size() > 0) {
            tblserialnumber = tblserialnumberList.get(0);
        }
        //Tblserialnumber tblserialnumber = serialNumberJpaDao.findBySnoSerialno(snoSerialno);
        //把查询记录在log表中
        if (tblserialnumber != null) {
            QueryLog queryLog = new QueryLog();
            String lowUUID = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            queryLog.setId(lowUUID);
            queryLog.setLableNo(tblserialnumber.getSnoSerialno());
            queryLogDao.save(queryLog);
        }

        if (tblserialnumber == null) {
            resultMap.put("code", "-1");
            return resultMap;
        }
        resultMap.put("data", tblserialnumber);
        //使用entityManager查询剩下的属性
        //1.物料描述
        sql = " select t.ma_description as uname from tblMaterial t where t.ma_material = '" + tblserialnumber.getSnoMaterial() + "'";
        query = entityManager.createNativeQuery(sql);
        ((SQLQuery) query.unwrap(SQLQuery.class)).addScalar("uname", StandardBasicTypes.STRING);
        List resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            resultMap.put("data_desc", resultList.get(0));
        } else {
            resultMap.put("data_desc", "");
        }
        //2.查询次数
        sql = "select COUNT(1) as uname from ANTI_QUERY_LOG t where t.LABLE_NO='" + tblserialnumber.getSnoSerialno() + "'";
        query = entityManager.createNativeQuery(sql);
        ((SQLQuery) query.unwrap(SQLQuery.class)).addScalar("uname", StandardBasicTypes.STRING);
        resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            resultMap.put("data_countNum", resultList.get(0));
        } else {
            resultMap.put("data_countNum", "0");
        }
        resultMap.put("code", "0");
        return resultMap;
    }

}
