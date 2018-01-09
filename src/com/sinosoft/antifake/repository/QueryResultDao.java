package com.sinosoft.antifake.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sinosoft.antifake.entity.QueryResultCount;
import com.sinosoft.antifake.entity.QueryResultDetail;

@Repository
public class QueryResultDao {
	
//  private static Logger logger = LoggerFactory.getLogger(QueryResultDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private QueryResultCountMapper queryResultCountMapper = new QueryResultCountMapper();
	private class QueryResultCountMapper implements RowMapper<QueryResultCount> {
		public QueryResultCount mapRow(ResultSet rs, int rowNum) throws SQLException {
			QueryResultCount queryResultCount = new QueryResultCount();
			int count = rs.getInt(1);		//记录数
			int queryTimes = rs.getInt(2);	//查询次数
			queryResultCount.setResultCount(count);	
			//无论防伪码是否合法，查询记录都加 1
			queryResultCount.setQueryTimes(queryTimes += 1);
			return queryResultCount;
		}
	}
	
	private QueryResultDetailMapper queryResultDetailMapper = new QueryResultDetailMapper();
    private class QueryResultDetailMapper implements RowMapper<QueryResultDetail> {
        @SuppressWarnings("deprecation")
		public QueryResultDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        	QueryResultDetail queryResultDetail = new QueryResultDetail();
        	
        	String week = String.format("%02d", rs.getInt(4));		//只能对数字格式化
        	String year = String.valueOf(rs.getDate(8).getYear()).substring(1);
        	
        	queryResultDetail.setLableNo(		rs.getString(1));
        	queryResultDetail.setMaterialNo(	rs.getString(2));
        	queryResultDetail.setDescription(	rs.getString(3));
        	queryResultDetail.setWeek(			year + week);		//周号要加上年份
        	queryResultDetail.setSerialNo(		rs.getString(5));
        	queryResultDetail.setFactory(		rs.getString(6));
        	queryResultDetail.setProduct(		rs.getString(7));
        	queryResultDetail.setPackageDate(	rs.getString(8));
        	queryResultDetail.setOrderNo(		rs.getString(9));
            return queryResultDetail;
        }
    }
	
	
    public QueryResultCount getQueryResultCount(String lableNo) {
    	
    	StringBuffer countSql = new StringBuffer("select count(1) ");
    	countSql.append(" ,(select ISNULL(max(query_count),0)  from anti_query_log L where L.lable_no=?  )");
    	countSql.append(" from tblSerialNumber S");
    	countSql.append(" left join tblMaterial M");  	//用join 新号码没有记录 如: 000317801682
    	countSql.append(" on S.SNO_MATERIAL = M.MA_MATERIAL");
    	countSql.append(" where S.SNO_SERIALNO = ?");
    	
    	return jdbcTemplate.queryForObject(countSql.toString(),new Object[]{lableNo,lableNo}, queryResultCountMapper);
    }
    
	public List<QueryResultDetail> getQueryResultDetail(String lableNo) {
		
		StringBuffer detailSql = new StringBuffer("select CAST(S.SNO_SERIALNO as varchar) as S_SERIALNO");
		detailSql.append(" ,CAST(S.SNO_MATERIAL as varchar) as S_MATERIAL");
		detailSql.append(" ,CAST(M.MA_DESCRIPTION as varchar) as M_DES");
		detailSql.append(" ,DATEDIFF(wk,datename(yy,S.SNO_CREATEDATE)+'-01-01',S.SNO_CREATEDATE)+1 as S_WEEK");
		detailSql.append(" ,CAST(M.MA_ALIAS as varchar) as M_ALIAS");
		detailSql.append(" ,CAST(S.SNO_COMPANY as varchar) as S_COMPANY");
		detailSql.append(" ,CAST(S.SNO_PRODUCTIONLINE as varchar) as S_PRODUCTIONLINE");
		detailSql.append(" ,S.SNO_CREATEDATE as S_CREATEDATE");
		detailSql.append(" ,CAST(S.SNO_PRODUCTIONORDER as varchar) as S_PRODUCTIONORDER");
		detailSql.append(" from tblSerialNumber S");
		detailSql.append(" left join tblMaterial M");
		detailSql.append(" on S.SNO_MATERIAL = M.MA_MATERIAL");
		detailSql.append(" where S.SNO_SERIALNO = ?");

		return jdbcTemplate.query(detailSql.toString(), queryResultDetailMapper,lableNo);
	}
}
