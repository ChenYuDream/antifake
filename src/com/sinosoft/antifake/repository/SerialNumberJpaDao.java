package com.sinosoft.antifake.repository;

import com.sinosoft.antifake.entity.Tblserialnumber;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wj on 2017/12/23.
 * SerialNumber Jpa dao
 */
public interface SerialNumberJpaDao extends JpaRepository<Tblserialnumber, String> {

    Tblserialnumber findBySnoSerialno(String snoSerialno);

}
