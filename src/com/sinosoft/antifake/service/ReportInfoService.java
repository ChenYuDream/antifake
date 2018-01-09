package com.sinosoft.antifake.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import com.sinosoft.antifake.entity.ReportInfo;
import com.sinosoft.antifake.entity.ReportInfoHis;
import com.sinosoft.antifake.entity.rReportInfo;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.excel.ExcelWriter;
import com.sinosoft.antifake.helpers.excel.reportInfo.ReportInfoBuilder;
import com.sinosoft.antifake.helpers.mail.SendMailToSchneider;
import com.sinosoft.antifake.repository.ReportInfoDao;
import com.sinosoft.antifake.repository.ReportInfoHisDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class ReportInfoService {

	@Autowired
	private ReportInfoDao reportInfoDao;
	
	@Autowired
	private ReportInfoHisDao reportInfoHisDao;
	
	@Autowired
	private QueryLogService queryLogService;

	@PersistenceContext
	protected EntityManager em;
	
	static Properties pro = new Properties();
	static{
		try {
			pro.load(SendMailToSchneider.class.getResourceAsStream("/reportstate.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getProperty(String key){
		return pro.getProperty(key)==null?"mailinfo.properties缺少此参数："+key:pro.getProperty(key);
	}

	/**
	 * 保存查询日志.
	 */
	@Transactional(readOnly = false)
	public void saveReportInfo(ReportInfo reportInfo) {
		reportInfoDao.save(reportInfo);
	}
	
	public List<rReportInfo> findAll(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortType) {
		String where = buildWhere(searchParams);
		String sql = "select  r.client_name as client_name, r.client_phone, r.sales_address, r.sales_name, r.sales_phone,"
				+ " r.product_type, "
				+ "r.amount, "
				+ "r.label_no, r.create_time, r.material_no, r.client, r.report_type, r.ip, "+
                " r.gps_address,  r.tamper_content,a.user_name as user_name,a.phone_no as phone_no,"
                + "a.is_exist as isExist,a.query_count as query_count,r.id as id "+
			    " from report_info r left join anti_query_log a on a.id = r.query_id "+where+" order by r.create_time desc";
		Query query = em.createNativeQuery(sql);
		/*query = em.createNativeQuery(sql, "test");遇到列名无效问题 无法解决*/
		query.setFirstResult((pageNumber-1) * pageSize);
		query.setMaxResults(pageSize);
		List result = query.getResultList();
		List<rReportInfo> list = new ArrayList<rReportInfo>();
		for (int i = 0; i < result.size(); i++) {
			rReportInfo r = new rReportInfo();
			Object[] obj = (Object[])result.get(i);
			r.setClientName(String.valueOf(obj[0]).equals("null")?"":String.valueOf(obj[0]));
			r.setClientPhone(String.valueOf(obj[1]).equals("null")?"":String.valueOf(obj[1]));
			r.setSalesAddress(String.valueOf(obj[2]).equals("null")?"":String.valueOf(obj[2]));
			r.setSalesName(String.valueOf(obj[3]).equals("null")?"":String.valueOf(obj[3]));
			r.setSalesPhone(String.valueOf(obj[4]).equals("null")?"":String.valueOf(obj[4]));
			r.setProductType(String.valueOf(obj[5]).equals("null")?"":String.valueOf(obj[5]));
			r.setAmount(String.valueOf(obj[6]).equals("null")?"":String.valueOf(obj[6]));
			r.setLabelNo(String.valueOf(obj[7]).equals("null")?"":String.valueOf(obj[7]));
			r.setCreateTime(String.valueOf(obj[8]).equals("null")?"":String.valueOf(obj[8]));
			r.setMaterialNo(String.valueOf(obj[9]).equals("null")?"":String.valueOf(obj[9]));
			r.setClient(String.valueOf(obj[10]).equals("null")?"":String.valueOf(obj[10]));
			r.setReportType(String.valueOf(obj[11]).equals("null")?"":String.valueOf(obj[11]));
			r.setIp(String.valueOf(obj[12]).equals("null")?"":String.valueOf(obj[12]));
			r.setGpsAddress(String.valueOf(obj[13]).equals("null")?"":String.valueOf(obj[13]));
			r.setTamperContent(String.valueOf(obj[14]).equals("null")?"":String.valueOf(obj[14]));
			r.setQueryClientName(String.valueOf(obj[15]).equals("null")?"":String.valueOf(obj[15]));
			r.setQueryClientPhone(String.valueOf(obj[16]).equals("null")?"":String.valueOf(obj[16]));
			r.setQueryIsExist(String.valueOf(obj[17]).equals("null")?"":String.valueOf(obj[17]));
			r.setQueryCount(String.valueOf(obj[18]).equals("null")?"":String.valueOf(obj[18]));
			r.setId(String.valueOf(obj[19]).equals("null")?"":String.valueOf(obj[19]));
			list.add(r);
		}
		return list;
	}
	public List<rReportInfo> findAllNoPage(Map<String, Object> searchParams) {
		String where = buildWhere(searchParams);
		String sql = "select  r.client_name as client_name, r.client_phone, r.sales_address, r.sales_name, r.sales_phone,"
				+ " r.product_type, "
				+ "r.amount, "
				+ "r.label_no, r.create_time, r.material_no, r.client, r.report_type, r.ip, "+
                " r.gps_address,  r.tamper_content,a.user_name as user_name,a.phone_no as phone_no,"
                + "a.is_exist as isExist,a.query_count as query_count,r.id as id "+
			    " from report_info r left join anti_query_log a on a.id = r.query_id "+where+" order by r.create_time desc";
		Query query = em.createNativeQuery(sql);
		/*query = em.createNativeQuery(sql, "test");遇到列名无效问题 无法解决*/
		List result = query.getResultList();
		List<rReportInfo> list = new ArrayList<rReportInfo>();
		for (int i = 0; i < result.size(); i++) {
			rReportInfo r = new rReportInfo();
			Object[] obj = (Object[])result.get(i);
			r.setClientName(String.valueOf(obj[0]).equals("null")?"":String.valueOf(obj[0]));
			r.setClientPhone(String.valueOf(obj[1]).equals("null")?"":String.valueOf(obj[1]));
			r.setSalesAddress(String.valueOf(obj[2]).equals("null")?"":String.valueOf(obj[2]));
			r.setSalesName(String.valueOf(obj[3]).equals("null")?"":String.valueOf(obj[3]));
			r.setSalesPhone(String.valueOf(obj[4]).equals("null")?"":String.valueOf(obj[4]));
			r.setProductType(String.valueOf(obj[5]).equals("null")?"":String.valueOf(obj[5]));
			r.setAmount(String.valueOf(obj[6]).equals("null")?"":String.valueOf(obj[6]));
			r.setLabelNo(String.valueOf(obj[7]).equals("null")?"":String.valueOf(obj[7]));
			r.setCreateTime(String.valueOf(obj[8]).equals("null")?"":String.valueOf(obj[8]));
			r.setMaterialNo(String.valueOf(obj[9]).equals("null")?"":String.valueOf(obj[9]));
			r.setClient(String.valueOf(obj[10]).equals("null")?"":String.valueOf(obj[10]));
			r.setReportType(String.valueOf(obj[11]).equals("null")?"":String.valueOf(obj[11]));
			r.setIp(String.valueOf(obj[12]).equals("null")?"":String.valueOf(obj[12]));
			r.setGpsAddress(String.valueOf(obj[13]).equals("null")?"":String.valueOf(obj[13]));
			r.setTamperContent(String.valueOf(obj[14]).equals("null")?"":String.valueOf(obj[14]));
			r.setQueryClientName(String.valueOf(obj[15]).equals("null")?"":String.valueOf(obj[15]));
			r.setQueryClientPhone(String.valueOf(obj[16]).equals("null")?"":String.valueOf(obj[16]));
			r.setQueryIsExist(String.valueOf(obj[17]).equals("null")?"":String.valueOf(obj[17]));
			r.setQueryCount(String.valueOf(obj[18]).equals("null")?"":String.valueOf(obj[18]));
			r.setId(String.valueOf(obj[19]).equals("null")?"":String.valueOf(obj[19]));
			list.add(r);
		}
		return list;
	}
	
	private String buildWhere(Map<String, Object> searchParams) {
		StringBuffer sb = new StringBuffer(" where 1=1 ");
		Iterator<String> it = searchParams.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = searchParams.get(key).toString();
			if(null!=value&&!("").equals(value)){
				if(("GTE_createTime").equals(key)){
					sb.append(" and r.create_time>='"+value+"' ");
				}else if(("LIKE_clientName").equals(key)){
					sb.append(" and r.client_name like'%"+value+"%' ");
				}else if(("LIKE_clientPhone").equals(key)){
					sb.append(" and r.client_phone like'%"+value+"%' ");
				}else if(("LIKE_labelNo").equals(key)){
					sb.append(" and r.label_no like'%"+value+"%' ");
				}else if(("LTE_createTime").equals(key)){
					sb.append(" and r.create_time<='"+value+"' ");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 查询列表
	 * */
	public Page<ReportInfo> getPagelist(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortType) {
		Specification<ReportInfo> spec = buildSpecification(searchParams);//查询条件构造
		PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortType);//page信息
		Page<ReportInfo> pagelist = reportInfoDao.findAll(spec, pageRequest);
		/*List<ReportInfo> list = pagelist.getContent();
		List<ReportInfo> newList = new ArrayList<ReportInfo>();
		for (ReportInfo reportInfo : list) {
			String queryId = reportInfo.getQueryId();
			QueryLog queryLog = queryLogService.getQueryLogById(queryId);
			reportInfo.setQueryClientName(queryLog.getUserName());
			reportInfo.setQueryClientPhone(queryLog.getPhoneNo());
			newList.add(reportInfo);
		}*/
		return pagelist;
	}
	
	/**
	 * 联表查询 dbo.anti_query_log log on log.id=this.queryId 
	 * */
	
	private PageRequest buildPageRequest(int pageNumber, int pageSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createTime");
		} else if ("labelNo".equals(sortType)) {
			sort = new Sort(Direction.ASC, "labelNo");
		}
		return new PageRequest(pageNumber-1, pageSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<ReportInfo> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<ReportInfo> spec = DynamicSpecifications.bySearchFilter(filters.values(), ReportInfo.class);
		return spec;
	}
	public void exportXLS(Map<String, Object> searchParams,
			HttpServletResponse response) throws UnsupportedEncodingException {
		try{
			// 1.创建一个 workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 2.创建一个 worksheet
			HSSFSheet worksheet = workbook.createSheet("reportinfo");
			// 3.创建title,data,headers
			ReportInfoBuilder.buildReport(worksheet);
			// 4.填充数据
			ReportInfoBuilder.fillReport(worksheet, findAllNoPage(searchParams));
			// 5.设置reponse参数
			String fileName = URLEncoder.encode(("antifake-疑似假货报告-"), "utf-8") + DateHelper.getCurrentDate() + ".xls";
			response.setHeader("Content-Disposition", "inline; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
			// 6. 输出流
			ExcelWriter.write(response, worksheet);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private List<ReportInfo> getAllReportInfoList(Map<String, Object> searchParams) {
		Specification<ReportInfo> spec =buildSpecification(searchParams); 
		return reportInfoDao.findAll(spec, new Sort(Direction.DESC, "createTime"));
	}
	
	/**
	 * 编辑页面获取上报详细信息
	 * */
	public ReportInfo getReportInfo(String id) {
		ReportInfo info  = new ReportInfo();
		String localSql = "select client_name,client_phone,sales_address,sales_name,sales_phone,"
				+ "product_type,amount,gps_address,comments,label_no,material_no,client,report_type,ip"
				+ ",query_id,tamper_content,state from report_info where id=:id";
		Query query = em.createNativeQuery(localSql);
		query.setParameter("id", id);
		List list = query.getResultList();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			info.setClientName(String.valueOf(obj[0]).equals("null")?"":String.valueOf(obj[0]));
			info.setClientPhone(String.valueOf(obj[1]).equals("null")?"":String.valueOf(obj[1]));
			info.setSalesAddress(String.valueOf(obj[2].equals("null")?"":String.valueOf(obj[2])));
			info.setSalesName(String.valueOf(obj[3]).equals("null")?"":String.valueOf(obj[3]));
			info.setSalesPhone(String.valueOf(obj[4]).equals("null")?"":String.valueOf(obj[4]));
			info.setProductType(String.valueOf(obj[5]).equals("null")?"":String.valueOf(obj[5]));
			info.setAmount(String.valueOf(obj[6]).equals("null")?"":String.valueOf(obj[6]));
			info.setGpsAddress(String.valueOf(obj[7]).equals("null")?"":String.valueOf(obj[7]));
			info.setComments(String.valueOf(obj[8]).equals("null")?"":String.valueOf(obj[8]));
			
			info.setLabelNo(String.valueOf(obj[9]).equals("null")?"":String.valueOf(obj[9]));
			info.setMaterialNo(String.valueOf(obj[10]).equals("null")?"":String.valueOf(obj[10]));
			info.setClient(String.valueOf(obj[11]).equals("null")?"":String.valueOf(obj[11]));
			info.setReportType(String.valueOf(obj[12]).equals("null")?"":String.valueOf(obj[12]));
			info.setIp(String.valueOf(obj[13]).equals("null")?"":String.valueOf(obj[13]));
			info.setQueryId(String.valueOf(obj[14]).equals("null")?"":String.valueOf(obj[14]));
			info.setTamperContent(String.valueOf(obj[15]).equals("null")?"":String.valueOf(obj[15]));
			info.setState(String.valueOf(obj[16]).equals("null")?"":String.valueOf(obj[16]));
		}
		return info;
	}
	/**
	 * 编辑后更新上报信息
	 * */
	@Transactional(readOnly = false)
	public void updateReportInfo(ReportInfo reportInfo) {
		ReportInfo infoForHis = getReportInfo(reportInfo.getId());
		ReportInfoHis reportHis = new ReportInfoHis(infoForHis.getId(), infoForHis.getClientName(), infoForHis.getClientPhone(),
				infoForHis.getSalesAddress(), infoForHis.getSalesName(), infoForHis.getSalesPhone(), infoForHis.getProductType(),
				infoForHis.getAmount(), infoForHis.getLabelNo(), infoForHis.getCreateTime(), infoForHis.getMaterialNo(),
				infoForHis.getClient(), infoForHis.getReportType(), infoForHis.getIp(), infoForHis.getGpsAddress(),
				infoForHis.getQueryId(), infoForHis.getTamperContent(), infoForHis.getComments(), infoForHis.getState());
		reportInfoHisDao.save(reportHis);
		String localSql = "update report_info set client_name=?,client_phone=?,sales_address=?,"+
				"sales_phone=?,product_type=?,amount=?,gps_address=?,comments=?,sales_name=?,state=? where id=?";
		Query query = em.createNativeQuery(localSql);
		query.setParameter(1, reportInfo.getClientName());
		query.setParameter(2, reportInfo.getClientPhone());
		query.setParameter(3, reportInfo.getSalesAddress());
		query.setParameter(4, reportInfo.getSalesPhone());
		query.setParameter(5, reportInfo.getProductType());
		query.setParameter(6, reportInfo.getAmount());
		query.setParameter(7, reportInfo.getGpsAddress());
		query.setParameter(8, reportInfo.getComments());
		query.setParameter(9, reportInfo.getSalesName());
		query.setParameter(10, reportInfo.getState());
		query.setParameter(11, reportInfo.getId());
		query.executeUpdate();
	}

	public List<HashMap<String,String>> getStateList() {
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		Enumeration<?> names = pro.propertyNames();
		while(names.hasMoreElements()){
			String key = names.nextElement().toString();
			String val = getProperty(key);
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("key", key);
			map.put("val", val);
			list.add(map);
		}
		return list;
	}
	
}
