package com.sinosoft.antifake.service;

import com.sinosoft.antifake.entity.OperationLog;
import com.sinosoft.antifake.repository.OperationLogDao;
import org.hibernate.annotations.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wj on 2017/12/24.
 */
@Component
@Transactional
public class OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    /**
     * 根据时间查询
     * @param startTime
     * @param endTime
     * @return
     */
    public List<OperationLog> getByTime(Date startTime,Date endTime){
        List<OperationLog> list = operationLogDao.findByCreateDateBetween(startTime,endTime);
        return list;
    }

    /**
     * 添加
     * @param operationLog
     * @return
     */
    public OperationLog insertObject(OperationLog operationLog){
        return operationLogDao.save(operationLog);
    }

}
