package com.sinosoft.antifake.web.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sinosoft.antifake.entity.LabelSummaryInsertJson;
import com.sinosoft.antifake.entity.LabelSummaryQueryJson;
import com.sinosoft.antifake.helpers.CacheUtil;
import com.sinosoft.antifake.helpers.Constant;
import com.sinosoft.antifake.helpers.StringUtil;
import com.sinosoft.antifake.ibatis.model.LabelScrap;
import com.sinosoft.antifake.ibatis.model.LableDetail;
import com.sinosoft.antifake.ibatis.model.LableSummary;
import com.sinosoft.antifake.ibatis.service.LabelService;
import com.sinosoft.antifake.service.SerialNumberService;

@Controller
@RequestMapping(value="/label/sync")
public class LabelRestController {
	private Logger log = LoggerFactory.getLogger(LabelRestController.class);
	
	@Autowired
	private LabelService labelSummaryService;
	@Autowired
	private SerialNumberService serialNumberService;
	
	static CacheUtil cacheUtil = new CacheUtil(100);
	
	/**
	 * 2.4.1	写入汇总数据
	 * @param parameterData
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value="/addSummaryData.json",method=RequestMethod.POST)
	public String addSummaryData(String parameterData,String userName,String pwd
			,HttpServletResponse response){
		LabelSummaryInsertJson snij = new LabelSummaryInsertJson();
		String info = "";
		String status = Constant.REST_JSON_STATUS_SUCCESS;
		int successtotal = 0;
		int faildtotal = 0;
		int count = 0;
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			try{
				list = gson.fromJson(parameterData
						, new TypeToken<List<Map<String,String>>>() {}.getType());
			}catch(JsonSyntaxException e){
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			}
			for (Map<String,String> map : list) {
				map.put("creater", userName);
				map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
				map.put("status", "");
				map.put("receiveStatus", "-1");
				count = Integer.valueOf(map.get("count"));
				try{
					labelSummaryService.labelSummaryInsert(map);
					//已使用标签数功能 2014-12-30 add by kyle
					String recipientNo = map.get("recipientNo");
					labelSummaryService.updateStockSerialNumberToRecipient(recipientNo,count);
					labelSummaryService.checkWarnVakyeAndSendEmail(recipientNo);
					//add end
					successtotal++;//完全成功的数量
				}catch (Exception e) {
					if(e instanceof DuplicateKeyException){
						DuplicateKeyException dupE = (DuplicateKeyException)e;
						if(("yes").equals(dupE.getMessage())){
							faildtotal++;
							status = Constant.REST_JSON_STATUS_SUCCESS;
							info  =  Constant.REST_DATA_EXISTS;
						}
					}
				}
			}
			if((faildtotal+successtotal)<list.size()){
				status = Constant.REST_JSON_STATUS_FAILED;
			}
		}else{
			status = Constant.REST_JSON_STATUS_FAILED;
			info  =  Constant.REST_USER_ERROR;
		}
		snij.setInfo(info);
		snij.setStatus(status);
		snij.setTotal(successtotal);
		return this.ajaxGJson(response, gson.toJson(snij));
	}
	
	/**
	 * 2.4.2	查询汇总数据
	 * @param recipientNo
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@RequestMapping(value="/querySummaryData.json",method=RequestMethod.POST)
	public String querySummaryData(String reelNo,String trackingNo,String orderNo,String recipientNo,String userName,String pwd,HttpServletResponse response){
		LabelSummaryQueryJson json = new LabelSummaryQueryJson();
		String info = "";
		String status = "";
		List dataList = new ArrayList();
		List<LableSummary> queryList = new ArrayList<LableSummary>();
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			try{
				dataList = labelSummaryService.querySummaryData(recipientNo,orderNo,trackingNo,reelNo);
				for (Object object : dataList) {
					LableSummary label = (LableSummary)object;
					LableSummary queryLabel = new LableSummary();
					queryLabel.setOrderNo(label.getOrderNo());
					queryLabel.setRecipientNo(label.getRecipientNo());
					queryLabel.setRecipient(label.getRecipient());
					queryLabel.setExpressCompany(label.getExpressCompany());
					queryLabel.setTrackingNo(label.getTrackingNo());
					queryLabel.setLabelSize(label.getLabelSize());
					queryLabel.setReelNo(label.getReelNo());
					queryLabel.setCount(label.getCount());
					queryList.add(queryLabel);
				}
				status = Constant.REST_JSON_STATUS_SUCCESS;
			}catch (JsonSyntaxException e) {
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			} catch (Exception e) {
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			}finally{
				json.setInfo(info);
				json.setStatus(status);
				json.setData(queryList);
				return this.ajaxGJson(response, gson.toJson(json));
			}
		}else{
			info  =  Constant.REST_USER_ERROR;
			status = Constant.REST_JSON_STATUS_FAILED;
		}
		json.setInfo(info);
		json.setStatus(status);
		json.setData(queryList);
		return this.ajaxGJson(response, gson.toJson(json));
	}
	
	/**
	 * 2.4.2.2	查询汇总数据V2.0
	 * @param recipientNo
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@RequestMapping(value="/querySummaryDataV2.json",method=RequestMethod.POST)
	public String querySummaryDataV2(String reelNo,String trackingNo,String orderNo,String recipientNo,String userName,String pwd,HttpServletResponse response){
		LabelSummaryQueryJson json = new LabelSummaryQueryJson();
		String info = "";
		String status = "";
		List dataList = new ArrayList();
		List<LableSummary> queryList = new ArrayList<LableSummary>();
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			try{
				dataList = labelSummaryService.querySummaryData(recipientNo,orderNo,trackingNo,reelNo);
				for (Object object : dataList) {
					LableSummary label = (LableSummary)object;
					LableSummary queryLabel = new LableSummary();
					queryLabel.setOrderNo(label.getOrderNo());
					queryLabel.setRecipientNo(label.getRecipientNo());
					queryLabel.setRecipient(label.getRecipient());
					queryLabel.setExpressCompany(label.getExpressCompany());
					queryLabel.setTrackingNo(label.getTrackingNo());
					queryLabel.setLabelSize(label.getLabelSize());
					queryLabel.setReelNo(label.getReelNo());
					queryLabel.setCount(label.getCount());
					//add by kyle from ethan 2014-11-27
					queryLabel.setReceiveStatus(label.getReceiveStatus());
					queryLabel.setRemark(label.getRemark());
					//add end
					queryList.add(queryLabel);
				}
				status = Constant.REST_JSON_STATUS_SUCCESS;
			}catch (JsonSyntaxException e) {
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			} catch (Exception e) {
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			}finally{
				json.setInfo(info);
				json.setStatus(status);
				json.setData(queryList);
				return this.ajaxGJson(response, gson.toJson(json));
			}
		}else{
			info  =  Constant.REST_USER_ERROR;
			status = Constant.REST_JSON_STATUS_FAILED;
		}
		json.setInfo(info);
		json.setStatus(status);
		json.setData(queryList);
		return this.ajaxGJson(response, gson.toJson(json));
	}
	
	/**
	 * 2.4.3	写入详细数据
	 * @param parameterData
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "finally", "serial" })
	@RequestMapping(value="/addDetailData.json",method=RequestMethod.POST)
	public String addDetailData(String parameterData,String userName,String pwd,HttpServletResponse response){
		LabelSummaryInsertJson snij = new LabelSummaryInsertJson();
		String info = "";
		String status = Constant.REST_JSON_STATUS_SUCCESS;
		int successtotal = 0;
		int faildtotal = 0;
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			try{
				list = gson.fromJson(parameterData
						, new TypeToken<List<Map<String,String>>>() {}.getType());
			}catch(JsonSyntaxException e){
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			}
			for (Map<String,String> map : list) {
				map.put("creater", userName);
				map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
				map.put("status", "");
				try{
					labelSummaryService.addDetailData(map);
					successtotal++;
				}catch (Exception e) {
					if(e instanceof DuplicateKeyException){
						DuplicateKeyException dupE = (DuplicateKeyException)e;
						if(("yes").equals(dupE.getMessage())){
							faildtotal++;
							status = Constant.REST_JSON_STATUS_SUCCESS;
							info  =  Constant.REST_DATA_EXISTS;
						}
					}
				}
			}
			if((successtotal+faildtotal)<list.size()){
				status = Constant.REST_JSON_STATUS_FAILED;
			}
		}else{
			info  =  Constant.REST_USER_ERROR;
			status = Constant.REST_JSON_STATUS_FAILED;
		}
		snij.setInfo(info);
		snij.setStatus(status);
		snij.setTotal(successtotal);
		return this.ajaxGJson(response, gson.toJson(snij));
	}
	
	/**
	 * 2.4.4	详细数据查询接口
	 * @param reelNo
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "finally", "rawtypes" })
	@RequestMapping(value="/queryDetailData.json",method=RequestMethod.POST)
	public String queryDetailData(String reelNo,String orderNo,String userName,String pwd,HttpServletResponse response){
		LabelSummaryQueryJson json = new LabelSummaryQueryJson();
		String info = "";
		String status = "";
		List dataList = new ArrayList();
		List<LableDetail> queryList = new ArrayList<LableDetail>();
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			try{
				dataList = labelSummaryService.queryDetailData(reelNo,orderNo);
				for (Object object : dataList) {
					LableDetail detail = (LableDetail)object;
					LableDetail queryDetail = new LableDetail();
					queryDetail.setSerialNumber(detail.getSerialNumber());
					queryDetail.setReelNo(detail.getReelNo());
					queryList.add(queryDetail);
				}
				status = Constant.REST_JSON_STATUS_SUCCESS;
			}catch (JsonSyntaxException e) {
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			} catch (Exception e) {
				e.printStackTrace();
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			}finally{
				json.setInfo(info);
				json.setStatus(status);
				json.setData(queryList);
				return this.ajaxGJson(response, gson.toJson(json));
			}
		}else{
			info  =  Constant.REST_USER_ERROR;
			status = Constant.REST_JSON_STATUS_FAILED;
		}
		json.setInfo(info);
		json.setStatus(status);
		json.setData(queryList);
		return this.ajaxGJson(response, gson.toJson(json));
	}
	
	/**
	 * 2.4.5	确认收货接口
	 * @param parameterData
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "finally", "serial" })
	@RequestMapping(value="/addDataStatus.json",method=RequestMethod.POST)
	public String addDataStatus(String parameterData,String userName,String pwd,HttpServletResponse response){
		LabelSummaryInsertJson snij = new LabelSummaryInsertJson();
		String info = "";
		String status = "";
		int n = 0;
		Gson gson = new Gson();
		String insertInfos = "";
			if(serialNumberService.checkUser(userName,pwd)){
				try{
					List<LableSummary> list = gson.fromJson(parameterData
							, new TypeToken<List<LableSummary>>() {}.getType());
					for (LableSummary lableSummary : list) {
						lableSummary.setReceiveUser(userName);
						String insertInfo = labelSummaryService.addDataStatus(lableSummary);
						if(insertInfo.equals("")){
							n++;
						}else{
							insertInfos += lableSummary.getOrderNo()+",";
						}
					}
					if(("").equals(insertInfos.trim())){
						status = Constant.REST_JSON_STATUS_SUCCESS;
					}else{
						status = Constant.REST_JSON_STATUS_FAILED;
						info  =  Constant.REST_DATA_NO_EXISTS;
					}
				}catch (JsonSyntaxException e) {
					status = Constant.REST_JSON_STATUS_FAILED;
					info  =  Constant.REST_JSON_ERROR;
				} catch (Exception e) {
					e.printStackTrace();
					status = Constant.REST_JSON_STATUS_FAILED;
					info  =  Constant.REST_JSON_ERROR;
				}finally{
					snij.setInfo(info);
					snij.setStatus(status);
					snij.setTotal(n);
					return this.ajaxGJson(response, gson.toJson(snij));
				}
			}else{
				info  =  Constant.REST_USER_ERROR;
				status = Constant.REST_JSON_STATUS_FAILED;
			}
		snij.setInfo(info);
		snij.setStatus(status);
		snij.setTotal(n);
		return this.ajaxGJson(response, gson.toJson(snij));
	}
	
	/**
	 * 2.4.6	报废标签上传接口
	 * @param parameterData
	 * @param userName
	 * @param pwd
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "finally", "serial" })
	@RequestMapping(value="/addScrapData.json",method=RequestMethod.POST)
	public String addScrapData(String parameterData,String userName,String pwd,HttpServletResponse response){
		LabelSummaryInsertJson snij = new LabelSummaryInsertJson();
		String info = "";
		String status = "";
		int n = 0;
		Gson gson = new Gson();
		String insertInfos = "";
			if(serialNumberService.checkUser(userName,pwd)){
				try{
					List<LabelScrap> list = gson.fromJson(parameterData
							, new TypeToken<List<LabelScrap>>() {}.getType());
					for (LabelScrap lableDetail : list) {
						lableDetail.setId(UUID.randomUUID().toString().replaceAll("-", ""));
						String insertInfo = labelSummaryService.addScrapData(lableDetail,userName);
						if(insertInfo.equals("")){
							n++;
						}else{
							insertInfos += lableDetail.getSerialNumber()+",";
						}
					}
				if(("").equals(insertInfos.trim())){
						status = Constant.REST_JSON_STATUS_SUCCESS;
					}else{
						status = Constant.REST_JSON_STATUS_FAILED;
						info  =  Constant.REST_DATA_EXISTS;
					}
				}catch (JsonSyntaxException e) {
					status = Constant.REST_JSON_STATUS_FAILED;
					info  =  Constant.REST_JSON_ERROR;
				} catch (Exception e) {
					status = Constant.REST_JSON_STATUS_FAILED;
					info  =  Constant.REST_JSON_ERROR;
				}finally{
					//已使用标签数功能 2014-12-30 add by kyle
					Object object = cacheUtil.get(userName);
					String recipientNo = "";
					if(StringUtil.isNull(object)){
						recipientNo = labelSummaryService.getRecipientNoByUserName(userName);
						cacheUtil.put(userName, recipientNo);
					}else{
						recipientNo = (String) object;
					}
					labelSummaryService.updateDeteleSerialNumberToRecipient(recipientNo,n);
					labelSummaryService.checkWarnVakyeAndSendEmail(recipientNo);
					//add end
					snij.setInfo(info);
					snij.setStatus(status);
					snij.setTotal(n);
					return this.ajaxGJson(response, gson.toJson(snij));
				}
			}else{
				info  =  Constant.REST_USER_ERROR;
				status = Constant.REST_JSON_STATUS_FAILED;
			}
		snij.setInfo(info);
		snij.setStatus(status);
		snij.setTotal(n);
		return this.ajaxGJson(response, gson.toJson(snij));
	}
	
	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	public String ajaxGJson(HttpServletResponse response, String obj) {
		//Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//String jsonString = gson.toJson(obj);
		return ajax(response, obj, "text/html");
	}

	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			log.error("IOException", e);
		}
		return null;
	}
}
