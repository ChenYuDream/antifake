package com.sinosoft.antifake.repository;

import com.sinosoft.antifake.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by wj on 2017/12/24.
 * @author wj
 * 防伪查询记录dao
 */
public interface OperationLogDao extends JpaRepository<OperationLog, String> {

    List<OperationLog> findByCreateDateBetween(Date startTime, Date endTime);

}
