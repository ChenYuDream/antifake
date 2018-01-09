package com.sinosoft.antifake.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.google.common.collect.Lists;
import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.entity.QueryResult;
import com.sinosoft.antifake.entity.QueryResultDetail;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.excel.ExcelWriter;
import com.sinosoft.antifake.helpers.excel.queryLog.QueryLogBuilder;
import com.sinosoft.antifake.repository.QueryLogDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class QueryLogService {

	@Autowired
	private QueryLogDao queryLogDao;

	@PersistenceContext
	protected EntityManager em;
	
	
	/**
	 * 根据ID得到查询记录
	 * */
	public QueryLog getQueryLogById(String id){
		QueryLog log = new QueryLog();
		String sql = "select user_name,phone_no from anti_query_log where id= :id";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		List results = query.getResultList();
		if(results.size()>0){
			Object[] obj = (Object[])results.get(0);
			log.setUserName(String.valueOf(obj[0]));
			log.setPhoneNo(String.valueOf(obj[1]));
		}
		return log;
	}
	
	/**
	 * 查询OLD
	 */
	public QueryResult getQueryResult(String lableNo) {
		
		//不能使用StringBuffer ，要缓存
		String countSql = "select count(1)  as resultCount ,(select ISNULL(max(query_count),0)  from anti_query_log L where L.lable_no=:lableNo  ) as queryTimes from viewSerialInfo S left join tblMaterial M on S.SNO_MATERIAL = M.MA_MATERIAL where S.SNO_SERIALNO = :lableNo";      
		
		Query countQuery =  em.createNativeQuery(countSql);
		countQuery.setParameter("lableNo", lableNo);
		List objecArraytList = countQuery.getResultList();
		int resultCount=0;
		int queryTimes=0; 
		if(objecArraytList.size()>0) {
			Object[] obj = (Object[]) objecArraytList.get(0);
			//使用obj[0],obj[1],obj[2]取出属性
			resultCount = Integer.parseInt(obj[0].toString());
			queryTimes = Integer.parseInt(obj[1].toString());
		}
		
		List<QueryResultDetail> resultDetail = Lists.newArrayList();
		
		if(resultCount>0) {
			String detailSql = "select CAST(S.SNO_SERIALNO as varchar) as S_SERIALNO"
			+ " ,CAST(S.SNO_MATERIAL as varchar) as S_MATERIAL"
			+ " ,CAST(M.MA_DESCRIPTION as varchar) as M_DES"		//可能空
			+ " ,DATEDIFF(wk,datename(yy,S.SNO_CREATEDATE)+'-01-01',S.SNO_CREATEDATE)+1 as S_WEEK"
			+ " ,CAST(M.MA_ALIAS as varchar) as M_ALIAS"			//可能空
			+ " ,CAST(S.SNO_COMPANY as varchar) as S_COMPANY"
			+ " ,CAST(S.SNO_PRODUCTIONLINE as varchar) as S_PRODUCTIONLINE"
			+ " ,S.SNO_CREATEDATE as S_CREATEDATE"
			+ " ,CAST(S.SNO_PRODUCTIONORDER as varchar) as S_PRODUCTIONORDER"  	//可能空
			+ " from viewSerialInfo S"
			+ " left join tblMaterial M"
			+ " on S.SNO_MATERIAL = M.MA_MATERIAL"
			+ " where S.SNO_SERIALNO = :lableNo";
			Query detailQuery =  em.createNativeQuery(detailSql.toString());
			detailQuery.setParameter("lableNo", lableNo);
			
			List detailArraytList = detailQuery.getResultList();
			//可能为null 的教训啊
			for(int i=0;i<detailArraytList.size();i++) {    
				QueryResultDetail queryResultDetail = new QueryResultDetail();
				Object[] obj = (Object[]) detailArraytList.get(i);
				
				String week = String.format("%02d", Integer.parseInt(String.valueOf(obj[3])));		//只能对数字格式化
	        	String year = String.valueOf(((Timestamp)obj[7]).getYear()).substring(1);
	        	
	        	queryResultDetail.setLableNo(		String.valueOf(obj[0]));
	        	queryResultDetail.setMaterialNo(	String.valueOf(obj[1]));
	        	queryResultDetail.setDescription(	String.valueOf(obj[2]));
	        	queryResultDetail.setWeek(			year + week);		//周号要加上年份
	        	queryResultDetail.setSerialNo(		String.valueOf(obj[4]));
	        	queryResultDetail.setFactory(		String.valueOf(obj[5]));
	        	queryResultDetail.setProduct(		String.valueOf(obj[6]));
	        	queryResultDetail.setPackageDate(	String.valueOf(obj[7]));
	        	queryResultDetail.setOrderNo(		String.valueOf(obj[8]));
				
				resultDetail.add(queryResultDetail);
			}  
		}
		
		QueryResult result = new QueryResult();
		result.setResultCount(resultCount);
		//无论防伪码是否合法，查询记录都加 1
		result.setQueryTimes(queryTimes += 1);
		result.setResultDetail(resultDetail);
		return result;
	}
	
	/**
	 * 查询
	 */
	public QueryResult getNewQueryResult(String lableNo) {

		// 不能使用StringBuffer ，要缓存
		String countSql = "select count(1)  as resultCount ,(select ISNULL(max(query_count),0)  from anti_query_log L where L.lable_no=:lableNo  ) as queryTimes from viewSerialInfo S left join tblMaterial M on S.SNO_MATERIAL = M.MA_MATERIAL where S.SNO_SERIALNO = :lableNo";

		Query countQuery = em.createNativeQuery(countSql);
		countQuery.setParameter("lableNo", lableNo);
		List objecArraytList = countQuery.getResultList();
		int resultCount = 0;
		int queryTimes = 0;
		if (objecArraytList.size() > 0) {
			Object[] obj = (Object[]) objecArraytList.get(0);
			// 使用obj[0],obj[1],obj[2]取出属性
			resultCount = Integer.parseInt(obj[0].toString());
			queryTimes = Integer.parseInt(obj[1].toString());
		}

		List<QueryResultDetail> resultDetail = Lists.newArrayList();

		if (resultCount > 0) {
			String detailSql = "select CAST(S.SNO_SERIALNO as varchar) as S_SERIALNO"
					+ " ,CAST(S.SNO_MATERIAL as varchar) as S_MATERIAL"
					+ " ,CAST(M.MA_DESCRIPTION as varchar) as M_DES" // 可能空
					+ " ,DATEDIFF(wk,datename(yy,S.SNO_CREATEDATE)+'-01-01',S.SNO_CREATEDATE)+1 as S_WEEK"
					+ " ,CAST(M.MA_ALIAS as varchar) as M_ALIAS" // 可能空
					+ " ,CAST(S.SNO_COMPANY as varchar) as S_COMPANY"
					+ " ,CAST(S.SNO_PRODUCTIONLINE as varchar) as S_PRODUCTIONLINE"
					+ " ,S.SNO_CREATEDATE as S_CREATEDATE"
					+ " ,CAST(S.SNO_PRODUCTIONORDER as varchar) as S_PRODUCTIONORDER" // 可能空
					+ " from viewSerialInfo S" + " left join tblMaterial M" + " on S.SNO_MATERIAL = M.MA_MATERIAL"
					+ " where S.SNO_SERIALNO = :lableNo";
			Query detailQuery = em.createNativeQuery(detailSql.toString());
			detailQuery.setParameter("lableNo", lableNo);

			List detailArraytList = detailQuery.getResultList();
			// 可能为null 的教训啊
			for (int i = 0; i < detailArraytList.size(); i++) {
				QueryResultDetail queryResultDetail = new QueryResultDetail();
				Object[] obj = (Object[]) detailArraytList.get(i);

				
				 String week = String.format("%02d", Integer.parseInt(String.valueOf(obj[3]))); // 只能对数字格式化
				 String year = String.valueOf(((Timestamp) obj[7]).getYear()).substring(1);
				 

				queryResultDetail.setLableNo(String.valueOf(obj[0]));
				queryResultDetail.setMaterialNo(String.valueOf(obj[1]));
				queryResultDetail.setDescription(String.valueOf(obj[2]));
				queryResultDetail.setWeek(year + week); // 周号要加上年份
				queryResultDetail.setSerialNo(String.valueOf(obj[4]));
				queryResultDetail.setFactory(String.valueOf(obj[5]));
				queryResultDetail.setProduct(String.valueOf(obj[6]));
				queryResultDetail.setPackageDate(String.valueOf(obj[7]));
				queryResultDetail.setOrderNo(String.valueOf(obj[8]));

				resultDetail.add(queryResultDetail);
			}
		}

		QueryResult result = new QueryResult();
		result.setResultCount(resultCount);
		// 无论防伪码是否合法，查询记录都加 1
		result.setQueryTimes(queryTimes += 1);
		result.setResultDetail(resultDetail);
		return result;
	}




	/**
	 * 保存查询日志.
	 */
	@Transactional(readOnly = false)
	public void saveQueryLog(QueryLog entity) {
		queryLogDao.save(entity);
	}

	/**
	 * 查询日志列表
	 */
	public Page<QueryLog> getQueryLogList(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<QueryLog> spec = buildSpecification(searchParams);
		return queryLogDao.findAll(spec, pageRequest);
	}

	// 读取数据库并导出报表
	public void exportXLS(Map<String, Object> searchParams, HttpServletResponse response)
			throws UnsupportedEncodingException {

		// 1.创建一个 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2.创建一个 worksheet
		HSSFSheet worksheet = workbook.createSheet("log");
		// 3.创建title,data,headers
		QueryLogBuilder.buildReport(worksheet);
		// 4.填充数据
		QueryLogBuilder.fillReport(worksheet, getAllQueryLogList(searchParams));
		// 5.设置reponse参数
		String fileName = URLEncoder.encode(("antifake-日志-"), "utf-8") + DateHelper.getCurrentDate() + ".xls";
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setContentType("application/vnd.ms-excel");
		// 6. 输出流
		ExcelWriter.write(response, worksheet);

	}

	// 私有方法开始---------------

	/**
	 * Excel 导出查询日志列表
	 */
	private List<QueryLog> getAllQueryLogList(Map<String, Object> searchParams) {

		Specification<QueryLog> spec = buildSpecification(searchParams);
		return queryLogDao.findAll(spec, new Sort(Direction.DESC, "queryTime"));
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {

		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "queryTime");
		} else if ("lableNo".equals(sortType)) {
			sort = new Sort(Direction.ASC, "lableNo");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<QueryLog> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<QueryLog> spec = DynamicSpecifications.bySearchFilter(filters.values(), QueryLog.class);
		return spec;
	}
	
	/**
	 * 
	 * @Description: 根据防伪码查询
	 * @param lableNo
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月13日 上午10:20:34
	 *
	 */
	public QueryLog findByLableNo(String lableNo){
		return queryLogDao.findByLableNo(lableNo);
	}
	/**
	 * 
	 * @Description: 保存更新
	 * @param log
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月13日 上午11:27:53
	 *
	 */
	@Transactional(readOnly = false)
	public String saveOrUpdate(QueryLog log) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
		QueryLog queryLog = this.findByLableNo(log.getLableNo());
		try {
			//存在更新否插入
			if(null != queryLog){
				queryLog.setPhoneNo(log.getPhoneNo());
				queryLog.setQueryCount(log.getQueryCount()+1);
				queryLog.setQueryTime(sdf.format(new Date()));
				queryLog.setIsExist("1");
				queryLog.setUserName(log.getUserName());
				queryLog.setMaterialNo(log.getMaterialNo());
				queryLog.setIsFake(log.getIsFake());
				queryLog.setSelfCheck(log.getSelfCheck());
				queryLogDao.save(queryLog);
			}else{
				log.setIsExist("0");
				log.setQueryCount(1);
				log.setQueryTime(sdf.format(new Date()));
				queryLogDao.save(log);
			}
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
}
