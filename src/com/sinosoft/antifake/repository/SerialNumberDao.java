package com.sinosoft.antifake.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.sinosoft.antifake.entity.SerialNumberQueryData;
import com.sinosoft.antifake.entity.Tblserialnumber;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.StringUtil;
import com.sinosoft.antifake.ibatis.dao.TblserialnumberDAO;
import com.sinosoft.antifake.ibatis.dao.TblserialnumberHisDAO;
import com.sinosoft.antifake.ibatis.model.TblserialnumberHis;
import com.sinosoft.antifake.web.rest.SerialNumberRestController.RequestFilter;

@Repository
public class SerialNumberDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String TableName = "tblSerialNumber";
	
	@Autowired
	protected TblserialnumberDAO tblserialnumberDAO;
	
	@Autowired
	protected TblserialnumberHisDAO tblserialnumberHisDAO;
	
	@PersistenceContext
	protected EntityManager em;
	
	public String insert(Tblserialnumber tblsn){
		com.sinosoft.antifake.ibatis.model.Tblserialnumber tblserialnumber 
			=  tblserialnumberDAO.getTblserialnumber(tblsn.getSnoSerialno());
		String info = "";
		/*Integer n = 0;
		Object[] args = new Object[]{tblsn.getSnoSerialno()};
		n = jdbcTemplate.queryForObject("select count(*) from "+TableName+" where SNO_SERIALNO=?", args, 
				new RowMapper<Integer>()
		{
			public Integer mapRow(ResultSet rs,int index)throws SQLException{
				return rs.getInt(1);
			}
		});*/
		if(!StringUtil.isNull(tblserialnumber)){
			Gson gson = new Gson();
			tblsn.setSnoModifydate(new Date());
			tblserialnumberDAO.updateTblserialnumber(gson.fromJson(gson.toJson(tblsn).replaceAll("<null>", "''"), com.sinosoft.antifake.ibatis.model.Tblserialnumber.class));
			TblserialnumberHis tblserialnumberHis 
				= gson.fromJson(gson.toJson(tblserialnumber).replaceAll("<null>", "''"), TblserialnumberHis.class);
			tblserialnumberHis.setCreater(tblsn.getSnoModifyuser());
			tblserialnumberHis.setCreateTime(new Date());
			tblserialnumberHis.setStatus("1");
			tblserialnumberHisDAO.addTblserialnumberHis(tblserialnumberHis);
			//info = tblsn.getSnoSerialno()+"该防伪码已经存在";
		}else{
			jdbcTemplate.update("insert into "+TableName+"(SNO_SERIALNO,"
					+ "SNO_BARNO,"
					+ "SNO_BOXNO,"
					+ "SNO_MATERIAL,"
					+ "SNO_CREATEDATE,"
					+ "SNO_DC,"
					+ "SNO_JV,"
					+ "SNO_COMPANY,"
					+ "SNO_PRODUCTIONLINE,"
					+ "SNO_PRODUCTIONLINENO,"
					+ "SNO_WORKER,"
					+ "SNO_PRODUCTIONORDER,"
					+ "SNO_MODIFYUSER,"
					+ "SNO_MODIFYDATE,"
					+ "SNO_PRODUCTINFO"
					+")"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,getdate(),?)", new Object[]{
					tblsn.getSnoSerialno(),
					tblsn.getSnoBarno(),
					tblsn.getSnoBoxno(),
					tblsn.getSnoMaterial(),
					tblsn.getSnoCreatedate(),
					tblsn.getSnoDc(),
					tblsn.getSnoJv(),
					tblsn.getSnoCompany(),
					tblsn.getSnoProductionline(),
					tblsn.getSnoProductionlineno(),
					tblsn.getSnoWorker(),
					tblsn.getSnoProductionorder(),
					tblsn.getSnoModifyuser(),
					tblsn.getSnoProductinfo()
					
			});
		}
		return info;
	}

	public boolean checkUser(String userName, String password) {
		Integer n = 0;
		Object[] args = new Object[]{userName,password};
		n = jdbcTemplate.queryForObject("select count(*) from syncUser where username=? and password=?", args, 
				new RowMapper<Integer>()
		{
			public Integer mapRow(ResultSet rs,int index)throws SQLException{
				return rs.getInt(1);
			}
		});
		if(n>0)
			return true;
		return false;
	}

	public List<SerialNumberQueryData> serialNumberQuery(RequestFilter filter,String userName) {
		String filterwhere  = buildFilter(filter,userName);
		String sql  = "select COUNT(*) as num,CONVERT(varchar(100), SNO_MODIFYDATE, 23) as date from tblSerialNumber "+
						filterwhere+
					  " group by CONVERT(varchar(100), SNO_MODIFYDATE, 23)";
		Query query = em.createNativeQuery(sql);
		List result = query.getResultList();
		List<SerialNumberQueryData> list = new ArrayList<SerialNumberQueryData>();
		for (int i = 0; i < result.size(); i++) {
			Object[] object = (Object[])result.get(i);
			SerialNumberQueryData r =  new SerialNumberQueryData();
			r.setCount(Integer.parseInt(object[0].toString()));
			r.setDate(String.valueOf(object[1]));
			list.add(r);
		}
		return list;
	}

	private String buildFilter(RequestFilter filter,String userName) {
		String where  = " where 1 = 1 ";
		if(filter.getDays()!=null&&!("").equals(filter.getDays())){
			String startDate = DateHelper.getChangeDay("yyyy-MM-dd", 
					new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
					Integer.valueOf(filter.getDays())*-1);
			String endDate = DateHelper.getChangeDay("yyyy-MM-dd", 
					new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
					-1);
			where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)>='"+startDate+"' ";
			where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)<='"+endDate+"' ";
		}else if(filter.getStartDate()!=null&&!("").equals(filter.getStartDate())){
			if(filter.getEndDate()!=null&&!("").equals(filter.getEndDate())){
				String startDate = filter.getStartDate();
				String endDate = filter.getEndDate();
				where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)>='"+startDate+"' ";
				where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)<='"+endDate+"' ";
			}else{
				String startDate = filter.getStartDate();
				String endDate = DateHelper.getChangeDay("yyyy-MM-dd", 
						new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
						-1);
				where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)>='"+startDate+"' ";
				where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)<='"+endDate+"' ";
			}
		}else{//都为空 则推前10天
			String startDate = DateHelper.getChangeDay("yyyy-MM-dd", 
					new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
					Integer.valueOf(10)*-1);
			String endDate = DateHelper.getChangeDay("yyyy-MM-dd", 
					new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
					-1);
			where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)>='"+startDate+"' ";
			where += " and CONVERT(varchar(100), SNO_MODIFYDATE, 23)<='"+endDate+"' ";
		}
		return where += " and SNO_MODIFYUSER = '"+userName+"' ";
	}
	
}
