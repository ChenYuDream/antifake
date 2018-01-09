package com.sinosoft.antifake.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.sinosoft.antifake.entity.LableDetail;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.StringUtil;
import com.sinosoft.antifake.helpers.excel.ExcelWriter;
import com.sinosoft.antifake.helpers.excel.KPI.KpiBuilder;
import com.sinosoft.antifake.helpers.excel.labelState.LabelStateBuilder;
import com.sinosoft.antifake.ibatis.dao.GeneralDAO;
import com.sinosoft.antifake.ibatis.dao.KpiInfoDAO;
import com.sinosoft.antifake.ibatis.model.KpiInfo;
import com.sinosoft.antifake.repository.LabelStateDao;
import com.sinosoft.antifake.web.rest.SerialNumberRestController;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class KPIService {
	
	private static Logger logger = LoggerFactory.getLogger(KPIService.class);
	@Autowired
	private LabelStateDao labelStateDao;

	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	private GeneralDAO generalDAO;
	
	@Autowired
	private KpiInfoDAO kpiInfoDAO;
	
	public List<HashMap<String,Object>> labelStataList(Map<String, Object> searchParams){
		HashMap param = new HashMap();
		if(searchParams!=null&&searchParams.get("productionDate")!=null&&!("").equals(searchParams.get("productionDate"))){
			param.put("productionDate", searchParams.get("productionDate").toString()+"-1");
		}
		List list = generalDAO.getListSql("KPIstatistics", param);
		List<HashMap<String,Object>> resList = new ArrayList<HashMap<String,Object>>();
		for (Object object : list) {
			HashMap<String,Object> map = (HashMap<String,Object>)object;
			String recipient = map.get("recipient")!=null?map.get("recipient").toString():"";
			int collectsTagNum = Integer.parseInt(map.get("usedNum")!=null?map.get("usedNum").toString():"0");//采集标签数
			int productOrderNum = Integer.parseInt(map.get("orderNum")!=null?map.get("orderNum").toString():"0");//订单总产品数
			int hourNum = Integer.parseInt(map.get("hourNum")!=null?map.get("hourNum").toString():"0");//24小时上传数
			int uploadNum = Integer.parseInt(map.get("usedNum")!=null?map.get("usedNum").toString():"0");//上传总数
			String recipientNo = map.get("recipient_no").toString();
			HashMap<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("recipient", recipient);
			resMap.put("collectsTagNum", collectsTagNum);
			resMap.put("productOrderNum", productOrderNum);
			resMap.put("hourNum", hourNum);
			resMap.put("uploadNum", uploadNum);
			if(collectsTagNum==0||productOrderNum==0){
				resMap.put("rightKpi", StringUtil.getPercent(0));
			}else{
				resMap.put("rightKpi", StringUtil.getPercent(collectsTagNum,productOrderNum));
			}
			if(hourNum==0||uploadNum==0){
				resMap.put("timeKpi", StringUtil.getPercent(0));
			}else{
				resMap.put("timeKpi", StringUtil.getPercent(hourNum,uploadNum));
			}
			resMap.put("recipientNo", recipientNo);
			resList.add(resMap);
		}
		return resList;
	}

	// 读取数据库并导出报表
	public void exportXLS(Map<String, Object> searchParams, HttpServletResponse response)
			throws UnsupportedEncodingException {

		// 1.创建一个 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2.创建一个 worksheet
		HSSFSheet worksheet = workbook.createSheet("log");
		// 3.创建title,data,headers
		KpiBuilder.buildReport(worksheet);
		// 4.填充数据
		KpiBuilder.fillReport(worksheet, labelStataList(searchParams));
		// 5.设置reponse参数
		String fileName = URLEncoder.encode(("antifake-KPI统计-"), "utf-8") +(searchParams.get("productionDate")!=null&&!("").equals(searchParams.get("productionDate"))?searchParams.get("productionDate"):DateHelper.getCurrentDate()) + ".xls";
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setContentType("application/vnd.ms-excel");
		// 6. 输出流
		ExcelWriter.write(response, worksheet);

	}

	public List showPlantDetail(String plantNo, String productionDate2) {
		List<HashMap<String,Object>> resList = new ArrayList<HashMap<String,Object>>();
		//采用JDBC
		//
		//采用JPA执行
/*		String sql = " select "+
					"p.mo_no,"+
					"(select COUNT(*) from tblSerialNumber s "+
							"LEFT JOIN syncUser u on u.username = s.SNO_MODIFYUSER  "+
							"where u.recipient_no = p.plant_no) as usedNum,"+
					"SUM(count) as orderNum ,"+
					"(select COUNT(*) from  tblSerialNumber s "+
							"LEFT JOIN syncUser u on u.username = s.SNO_MODIFYUSER "+
							"where DATEDIFF ([hour], s.SNO_CREATEDATE, s.SNO_MODIFYDATE)<=24 and u.recipient_no = p.plant_no "+ 
							") as hourNum "+
					 "from product_order p "+
					"where p.plant_no = :plantNo "+
					"GROUP BY p.mo_no,p.plant_no ORDER BY p.mo_no";
		Query query = em.createNativeQuery(sql);
		query.setParameter("plantNo", plantNo);
		List results = query.getResultList();
		if(results.size()>0){
			for (Object object : results) {
				Object[] obj = (Object[])object;
				log.setUserName(String.valueOf(obj[0]));
				log.setPhoneNo(String.valueOf(obj[1]));
				String moNo = obj[0]!=null?String.valueOf(obj[0]):"";
				int collectsTagNum = Integer.parseInt(String.valueOf(obj[1]));//采集标签数
				int productOrderNum = Integer.parseInt(obj[2]!=null?String.valueOf(obj[2]):"0");//订单总产品数
				int hourNum = Integer.parseInt(String.valueOf(obj[3]));//24小时上传数
				int uploadNum = Integer.parseInt(String.valueOf(obj[1]));//上传总数
				HashMap<String,Object> resMap = new HashMap<String,Object>();
				resMap.put("moNo", moNo);
				resMap.put("collectsTagNum", collectsTagNum);
				resMap.put("productOrderNum", productOrderNum);
				resMap.put("hourNum", hourNum);
				resMap.put("uploadNum", uploadNum);
				if(collectsTagNum==0||productOrderNum==0){
					resMap.put("rightKpi", StringUtil.getPercent(0));
				}else{
					resMap.put("rightKpi", StringUtil.getPercent(collectsTagNum,productOrderNum));
				}
				if(hourNum==0||uploadNum==0){
					resMap.put("timeKpi", StringUtil.getPercent(0));
				}else{
					resMap.put("timeKpi", StringUtil.getPercent(hourNum,uploadNum));
				}
				resList.add(resMap);
			}
		}*/
		
		
		
		HashMap parmap = new  HashMap();
		if(productionDate2!=null&&!("").equals(productionDate2.trim())){
			parmap.put("productionDate", productionDate2);
		}
		parmap.put("plantNo", plantNo);
		Long nowMillis = System.currentTimeMillis();
		List list  = generalDAO.getListSql("KPIdetail", parmap);
		Long endMillis = System.currentTimeMillis();
		if(list.size()>0){
			for (Object object : list) {
				HashMap<String,Object> map = (HashMap<String,Object>)object;
				String recipient = map.get("spcial_no")!=null?map.get("spcial_no").toString():"";
				int collectsTagNum = Integer.parseInt(map.get("usedNum").toString());//采集标签数
				int productOrderNum = Integer.parseInt(map.get("orderNum")!=null?map.get("orderNum").toString():"0");//订单总产品数
				int hourNum = Integer.parseInt(map.get("hourNum").toString());//24小时上传数
				int uploadNum = Integer.parseInt(map.get("usedNum").toString());//上传总数
				String productionDate = map.get("production_date")!=null?map.get("production_date").toString():"";
				HashMap<String,Object> resMap = new HashMap<String,Object>();
				resMap.put("moNo", recipient);
				resMap.put("collectsTagNum", collectsTagNum);
				resMap.put("productOrderNum", productOrderNum);
				resMap.put("hourNum", hourNum);
				resMap.put("uploadNum", uploadNum);
				if(collectsTagNum==0||productOrderNum==0){
					resMap.put("rightKpi", StringUtil.getPercent(0));
				}else{
					resMap.put("rightKpi", StringUtil.getPercent(collectsTagNum,productOrderNum));
				}
				if(hourNum==0||uploadNum==0){
					resMap.put("timeKpi", StringUtil.getPercent(0));
				}else{
					resMap.put("timeKpi", StringUtil.getPercent(hourNum,uploadNum));
				}
				resMap.put("productionDate", productionDate);
				resList.add(resMap);
			}
		}
		return resList;
	}

	public void detailExportXLS(Map<String, Object> searchParams,
			HttpServletResponse response) throws UnsupportedEncodingException {
			// 1.创建一个 workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
				// 2.创建一个 worksheet
			HSSFSheet worksheet = workbook.createSheet("log");
				// 3.创建title,data,headers
			KpiBuilder.buildDetailReport(worksheet);
			String plantNo = searchParams.get("plantNo")!=null?searchParams.get("plantNo").toString():"";
			String productionDate = searchParams.get("detail_productionDate")!=null&&!("").equals(searchParams.get("detail_productionDate"))?searchParams.get("detail_productionDate").toString()+"-1":"";
				// 4.填充数据
			KpiBuilder.fillDetailReport(worksheet, showPlantDetail(plantNo,productionDate));
				// 5.设置reponse参数
			String fileName = URLEncoder.encode(("antifake-KPI统计详情-"), "utf-8") +(searchParams.get("detail_productionDate")!=null&&!("").equals(searchParams.get("detail_productionDate"))?searchParams.get("detail_productionDate"):DateHelper.getCurrentDate()) + ".xls";
			response.setHeader("Content-Disposition", "inline; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
				// 6. 输出流
			ExcelWriter.write(response, worksheet);
		
	}

	public void countPlantKpi(String plantNo) {
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		HashMap map = new HashMap();
		map.put("plantNo", plantNo);
		list = generalDAO.getListSql("countKpiInfoOfUploadedCount", map);
		for (HashMap<String,Object> resMap : list) {
			String spcialNo = resMap.get("spcial_no").toString();
			int uploadedCount = Integer.valueOf(resMap.get("uploadedCount").toString());
			map = new HashMap();
			map.put("uploadedCount", uploadedCount);
			map.put("spcialNo", spcialNo);
			generalDAO.updateSql("updateUploadedCountBySpecialNo", map);
		}
		map = new HashMap();
		map.put("plantNo", plantNo);
		list = generalDAO.getListSql("countKpiInfoOfTimelyCount", map);
		for (HashMap<String,Object> resMap : list) {
			String spcialNo = resMap.get("spcial_no").toString();
			int timelyCount = Integer.valueOf(resMap.get("timelyCount").toString());
			map = new HashMap();
			map.put("timelyCount", timelyCount);
			map.put("spcialNo", spcialNo);
			generalDAO.updateSql("updateTimelyCountBySpecialNo", map);
		}
		
	}

	public void countDetailKpi(String spcialNo) {
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		//KpiInfo kpiInfo = kpiInfoDAO.getKpiInfo(spcialNo);
		HashMap map = new HashMap();
		map.put("spcialNo", spcialNo);
		list = generalDAO.getListSql("countKpiInfoOfUploadedCount", map);
		for (HashMap<String,Object> resMap : list) {
			int uploadedCount = Integer.valueOf(resMap.get("uploadedCount").toString());
			map = new HashMap();
			map.put("uploadedCount", uploadedCount);
			map.put("spcialNo", spcialNo);
			generalDAO.updateSql("updateUploadedCountBySpecialNo", map);
		}
		map = new HashMap();
		map.put("spcialNo", spcialNo);
		list = generalDAO.getListSql("countKpiInfoOfTimelyCount", map);
		for (HashMap<String,Object> resMap : list) {
			int timelyCount = Integer.valueOf(resMap.get("timelyCount").toString());
			map = new HashMap();
			map.put("timelyCount", timelyCount);
			map.put("spcialNo", spcialNo);
			generalDAO.updateSql("updateTimelyCountBySpecialNo", map);
		}
	}

}
