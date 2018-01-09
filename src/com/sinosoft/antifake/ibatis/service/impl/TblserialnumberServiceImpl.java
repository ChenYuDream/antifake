package com.sinosoft.antifake.ibatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.antifake.ibatis.dao.TblserialnumberDAO;
import com.sinosoft.antifake.ibatis.model.Tblserialnumber;
import com.sinosoft.antifake.ibatis.service.TblserialnumberService;

@Service
public class TblserialnumberServiceImpl implements TblserialnumberService{
	
	@Autowired
	TblserialnumberDAO tblserialnumberDAO;
	
	@Override
	public String restInsert(Tblserialnumber tblserialnumber) {
		// TODO Auto-generated method stub
		tblserialnumberDAO.addTblserialnumber(tblserialnumber);
		return "OK";
	}

}
