package com.sinosoft.antifake.ibatis.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.sinosoft.antifake.helpers.CacheUtil;
import com.sinosoft.antifake.helpers.Constant;
import com.sinosoft.antifake.helpers.StringUtil;
import com.sinosoft.antifake.helpers.mail.SendMailToSchneiderForWarnValue;
import com.sinosoft.antifake.ibatis.dao.GeneralDAO;
import com.sinosoft.antifake.ibatis.dao.KpiInfoDAO;
import com.sinosoft.antifake.ibatis.dao.LableDetailDAO;
import com.sinosoft.antifake.ibatis.dao.LableSummaryDAO;
import com.sinosoft.antifake.ibatis.dao.SyncuserDAO;
import com.sinosoft.antifake.ibatis.dao.TblrecipientDAO;
import com.sinosoft.antifake.ibatis.dao.TblserialnumberDAO;
import com.sinosoft.antifake.ibatis.dao.TblserialnumberHisDAO;
import com.sinosoft.antifake.ibatis.model.KpiInfo;
import com.sinosoft.antifake.ibatis.model.LabelScrap;
import com.sinosoft.antifake.ibatis.model.LableDetail;
import com.sinosoft.antifake.ibatis.model.LableSummary;
import com.sinosoft.antifake.ibatis.model.ProductOrder;
import com.sinosoft.antifake.ibatis.model.Tblrecipient;
import com.sinosoft.antifake.ibatis.model.Tblserialnumber;
import com.sinosoft.antifake.ibatis.model.TblserialnumberHis;
import com.sinosoft.antifake.ibatis.service.LabelService;

@Service
public class LabelServiceImpl implements LabelService{
	
	@Autowired
	protected LableSummaryDAO dao;
	@Autowired
	protected LableDetailDAO detailDAO;
	@Autowired
	protected GeneralDAO generalDAO;
	@Autowired
	protected TblserialnumberHisDAO tblserialnumberHisDAO;
	@Autowired
	protected TblserialnumberDAO tblserialnumberDAO;
	@Autowired
	protected SyncuserDAO syncuserDAO;
	@Autowired
	protected TblrecipientDAO tblrecipientDAO;
	@Autowired
	protected KpiInfoDAO kpiInfoDAO;
	
	
	@Override
	public String insert(LableSummary lableSummary) {
		LableSummary toQuery = new LableSummary();
		lableSummary.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		lableSummary.setStatus("");
		toQuery.setOrderNo(lableSummary.getOrderNo());
		toQuery.setReelNo(lableSummary.getReelNo());
		/*List list = dao.getLableSummaryByAnd(toQuery, null);
		if(list.size()>0){
			return Constant.DATA_EXISTS;
		}else{*/
			dao.addLableSummary(lableSummary);
		//}
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List querySummaryData(String recipientNo, String orderNo, String trackingNo, String reelNo) {
		LableSummary toQuery = new LableSummary();
		toQuery.setRecipientNo(recipientNo);
		toQuery.setOrderNo(orderNo);
		toQuery.setTrackingNo(trackingNo);
		toQuery.setReelNo(reelNo);
		return dao.getLableSummaryByAnd(toQuery, null);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String insertDetail(LableDetail lableDetail) {
		LableDetail toQuery = new LableDetail();
		lableDetail.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		lableDetail.setStatus("");
		toQuery.setSerialNumber(lableDetail.getSerialNumber());
		List list = detailDAO.getLableDetailByAnd(toQuery, null);
		if(list.size()>0){
			return Constant.DATA_EXISTS;
		}else{
			detailDAO.addLableDetail(lableDetail);
		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryDetailData(String reelNo,String orderNo) {
		LableDetail toQuery = new LableDetail();
		toQuery.setReelNo(reelNo);
		toQuery.setOrderNo(orderNo);
		return detailDAO.getLableDetailByAnd(toQuery, null);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String addDataStatus(LableSummary lableSummary) {
		LableSummary toQuery = new LableSummary();
		toQuery.setOrderNo(lableSummary.getOrderNo());
		toQuery.setExpressCompany(lableSummary.getExpressCompany());
		toQuery.setTrackingNo(lableSummary.getTrackingNo());
		toQuery.setReelNo(lableSummary.getReelNo());
		List list = dao.getLableSummaryByAnd(toQuery, null);
		if(list.size()==0){
			return Constant.DATA_NO_EXISTS;
		}else{
			generalDAO.updateSql("addDataStatus", lableSummary);
		}
		return "";
	}

	@Override
	public String addScrapData(LabelScrap lableDetail,String userName){
		/*LableDetail toQuery = new LableDetail();
		toQuery.setSerialNumber(lableDetail.getSerialNumber());
		List list = detailDAO.getLableDetailByAnd(toQuery, null);*/
		Tblserialnumber tblserialnumber =  tblserialnumberDAO.getTblserialnumber(lableDetail.getSerialNumber());
		if(tblserialnumber!=null){
			try{
				Gson gson = new Gson();
				TblserialnumberHis tblserialnumberHis 
						= gson.fromJson(gson.toJson(tblserialnumber).replaceAll("<null>", "''"), TblserialnumberHis.class);
				tblserialnumberHis.setCreateTime(new Date());
				tblserialnumberHis.setCreater(userName);
				tblserialnumberHisDAO.addTblserialnumberHis(tblserialnumberHis);
				tblserialnumberDAO.removeTblserialnumber(lableDetail.getSerialNumber());
			}catch(Exception e){
					e.printStackTrace();
			}
		}
		lableDetail.setScraper(userName);
		try{
			generalDAO.updateSql("addLabelScrap", lableDetail);
		}catch(Exception e){
			return Constant.DATA_EXISTS;
		}
		return "";
	}

	@Override
	public void bacthInsert(List<Map<String, String>> list) {
		generalDAO.updateSql("labelSummaryBatchInsert", list);
	}

	@Override
	public void insertDetailByBatch(List<Map<String, String>> insertList) {
		// TODO Auto-generated method stub
		generalDAO.updateSql("labelDetailBatchInsert", insertList);
	}

	@Override
	public void insertLabelOrderByBatch(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		generalDAO.updateSql("labelOrderBatchInsert", list);
	}

	@Override
	public void insertProductOrderByBatch(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		generalDAO.updateSql("productOrderBatchInsert", list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void approve(String serialNumber) {
		// TODO Auto-generated method stub
		HashMap map = new HashMap();
		map.put("serialNumber", serialNumber);
		map.put("status", "1");//标记为审批通过
		generalDAO.updateSql("apprvoeScrapDetail", map);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void batchApprove(MultipartFile file) throws IllegalStateException, IOException {
		File tempFile = new File(UUID.randomUUID()+".txt");
		file.transferTo(tempFile);
		BufferedReader br = new BufferedReader(new FileReader(tempFile));
		String lineOfSerialNumber = "";
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		while((lineOfSerialNumber = br.readLine())!=null){
			HashMap map = new HashMap();
			map.put("serialNumber", lineOfSerialNumber.trim());
			map.put("status", "1");
			list.add(map);
		}
		tempFile.delete();
		generalDAO.updateSql("batchApproveScrapDetail", list);
	}

	@Override
	public void productOrderRestBatchInsert(List<Map<String, String>> insertList) {
		generalDAO.updateSql("productOrderRestBatchInsert", insertList);
	}

	@Override
	public int removeProductOrder(String productionDate, String plantNo,
			String userName) {
		HashMap map = new  HashMap();
		map.put("productionDate", productionDate);
		map.put("plantNo", plantNo);
		return generalDAO.deleteSql("restRemoveProductOrder", map);
	}

	@Override
	public String getRecipientNoByUserName(String userName) {
		return syncuserDAO.getSyncuserFull(userName).getRecipientNo();
	}

	/**
	 * 已使用标签书更新操作
	 */
	@Override
	public void updateUsedSerialNumberToRecipient(String recipientNo, int n) {
		HashMap map = new  HashMap();
		map.put("recipientNo", recipientNo);
		map.put("n", n);
		generalDAO.updateSql("updateUsedSerialNumberToRecipient", map);
		
	}
	
	/**
	 * 库存数更新操作
	 */
	@Override
	public void updateStockSerialNumberToRecipient(String recipientNo, int total) {
		HashMap map = new  HashMap();
		map.put("recipientNo", recipientNo);
		map.put("n", total);
		generalDAO.updateSql("updateStockSerialNumberToRecipient", map);
		
	}
	
	/**
	 * 报废数更新操作，同时更新已使用标签书
	 */
	@Override
	public void updateDeteleSerialNumberToRecipient(String recipientNo, int n) {
		HashMap map = new  HashMap();
		map.put("recipientNo", recipientNo);
		map.put("n", n);
		generalDAO.updateSql("updateDeleteSerialNumberToRecipient", map);
		generalDAO.updateSql("updateUsedSerialNumberToRecipientByDelete", map);
	}
	
	/**
	 * 关于低于警戒值发邮件的需求
	 */
	@Override
	public void checkWarnVakyeAndSendEmail(String recipientNo) {
		HashMap map = new  HashMap();
		/*map.put("recipientNo", recipientNo);*/
		Tblrecipient tblrecipient = new Tblrecipient();
		tblrecipient = tblrecipientDAO.getTblrecipient(recipientNo);
		//HashMap res = (HashMap) generalDAO.getObjectSql("getTblRecipient", map);
		if(!StringUtil.isNull(tblrecipient.getPreStatus())){
			map = new  HashMap();
			String recipient = tblrecipient.getRecipient();// res.get("recipient").toString();
			String preStatus = tblrecipient.getPreStatus().toString();// res.get("pre_status").toString();
			//int total = Integer.parseInt(res.get("total")!=null?res.get("total").toString():"0");//总共
			/*int used = Integer.parseInt(res.get("used")!=null?res.get("used").toString():"0");//已使用
			int scrap = Integer.parseInt(res.get("scrap")!=null?res.get("scrap").toString():"0");//报废
*/			int warnValue = StringUtil.isNull(tblrecipient.getWarnValue())?0:tblrecipient.getWarnValue();//Integer.parseInt(res.get("warn_value")!=null?res.get("warn_value").toString():"0");//警戒值
			int inventory = (StringUtil.isNull(tblrecipient.getStockNum())?0:tblrecipient.getStockNum())-(StringUtil.isNull(tblrecipient.getUsedNum())?0:tblrecipient.getUsedNum())-(StringUtil.isNull(tblrecipient.getDeleteNum())?0:tblrecipient.getDeleteNum());//total-used-scrap;
			if(inventory<warnValue){
				if(("1").equals(preStatus)){//发送邮件,并更新status为2
					SendMailToSchneiderForWarnValue.sendMailByAsynchronous(recipient);
					map.put("recipientNo", recipientNo);
					map.put("preStatus", "2");
					generalDAO.updateSql("updatePreStatu", map);
				}
				//else不做处理
			}else{//更新为1
				map.put("recipientNo", recipientNo);
				map.put("preStatus", "1");
				generalDAO.updateSql("updatePreStatu", map);
			}
		}
	}

	@Override
	public void labelSummaryInsert(Map<String,String> map) throws DuplicateKeyException {
		try{
			generalDAO.updateSql("labelSummaryInsert", map);
		}catch(Exception e){
			if(e instanceof DuplicateKeyException){
				throw new DuplicateKeyException("yes");
			}else{
				throw new DuplicateKeyException("no");
			}
		}
	}

	@Override
	public void bacthPOInsert(List<Map<String, String>> list) {
		if(list.size()>0)
			generalDAO.updateSql("productOrderRestPOBatchInsert", list);
	}

	@Override
	public void bacthMOInsert(List<Map<String, String>> list) {
		if(list.size()>0)
			generalDAO.updateSql("productOrderRestMOBatchInsert", list);
	}

	@Override
	public List<Tblrecipient> getTblrecipientByAnd(Tblrecipient tblrecipient,
			Object object) {
		return tblrecipientDAO.getTblrecipientByAnd(tblrecipient, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void countKPI() throws ParseException {
		List<HashMap<String,Object>> list = generalDAO.getListSql("KPIcount", null);
		for (HashMap<String,Object> resMap : list) {
			ProductOrder productOrder = new ProductOrder();
			productOrder.setCount(Integer.valueOf(resMap.get("count").toString()));
			productOrder.setId(resMap.get("id").toString());
			productOrder.setPlantNo(resMap.get("plant_no").toString());
			productOrder.setProductionDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resMap.get("production_date").toString()));
			productOrder.setStatus(resMap.get("status").toString());
			
			List<KpiInfo> kpilist 
				= kpiInfoDAO.getKpiInfoByAnd(
						new KpiInfo(productOrder.getId(), null, null, null, null, null, null, null), null);
			if(kpilist.size()>0){
				Map<String,String> map = new HashMap<String, String>();
				map.put("spcial_no", productOrder.getId());
				map.put("count", productOrder.getCount()+"");
				map.put("product_date", 
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(productOrder.getProductionDate()));
				generalDAO.updateSql("update_kpi_count", map);
			}else{
				List<Map<String,String>> insertList = new ArrayList<Map<String,String>>();
				Map<String,String> map = new HashMap<String, String>();
				map.put("spcial_no", productOrder.getId());
				map.put("state", productOrder.getStatus());
				map.put("count", productOrder.getCount()+"");
				map.put("uploaded_count", "0");
				map.put("timely_count", "0");
				map.put("product_date", 
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(productOrder.getProductionDate()));
				map.put("plant_no", productOrder.getPlantNo());
				insertList.add(map);
				if(insertList.size()>0)
					generalDAO.updateSql("kpiInfoBatchInsert", insertList);
			}
		}
	}

	@Override
	public void addDetailData(Map<String, String> map) throws DuplicateKeyException{
		try{
			generalDAO.updateSql("addDetailData", map);
		}catch(Exception e){
			if(e instanceof DuplicateKeyException){
				throw new DuplicateKeyException("yes");
			}else{
				throw new DuplicateKeyException("no");
			}
		}
		
	}

	@Override
	public HashMap<String, String> putRecepitInfo() {
		HashMap<String, String> cacheUtil = new HashMap<String, String>();
		List<Tblrecipient> list = tblrecipientDAO.getKPITblrecipients();
		for (Tblrecipient tblrecipient : list) {
			cacheUtil.put(tblrecipient.getPlantCode(),tblrecipient.getRecipientNo());
		}
		return cacheUtil;
	}

	@Override
	public void POInsert(HashMap<String, String> map) {
		generalDAO.updateSql("productOrderRestPOInsert", map);
		
	}

	@Override
	public void MOInsert(HashMap<String, String> map) {
		generalDAO.updateSql("productOrderRestMOInsert", map);
		
	}
	
	/**
	 * 
	 * @Description: 批量插入SFBD工厂防伪数据
	 * @param list
	 * @see com.sinosoft.antifake.ibatis.service.LabelService#insertSfbdSecurityByBatch(java.util.List)
	 * @author wangxueqiang
	 * @date 2017年12月7日 下午4:17:02
	 *
	 */
	@Override
	public void insertSfbdSecurityByBatch(List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		generalDAO.updateSql("SfbdSecurityByBatchInsert", list);
	}

}
