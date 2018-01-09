package com.sinosoft.antifake.web.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sinosoft.antifake.entity.LabelSummaryInsertJson;
import com.sinosoft.antifake.entity.SerialNumberInsertJson;
import com.sinosoft.antifake.entity.SerialNumberQueryData;
import com.sinosoft.antifake.entity.SerialNumberQueryJson;
import com.sinosoft.antifake.entity.Tblserialnumber;
import com.sinosoft.antifake.helpers.CacheUtil;
import com.sinosoft.antifake.helpers.Constant;
import com.sinosoft.antifake.helpers.StringUtil;
import com.sinosoft.antifake.ibatis.service.LabelService;
import com.sinosoft.antifake.service.SerialNumberService;

@Controller
@RequestMapping(value = "/factory/sync")
public class SerialNumberRestController {
	
	private static Logger logger = LoggerFactory.getLogger(SerialNumberRestController.class);
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private LabelService labelService;
	
	static CacheUtil cacheUtil = new CacheUtil(100);
	
	// json 接口 写入
	@SuppressWarnings({ "serial", "finally" })
	@RequestMapping(value = "/serialNumber.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getQueryAsJson(String parameterData ,String userName,String ip,String pwd,
				HttpServletRequest request,HttpServletResponse response) {
		SerialNumberInsertJson snij = new SerialNumberInsertJson();
		String respSerialNo = "";
		String info = "";
		String status = "";
		String onInsertNo = "";
		int n = 0;
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			try {
				List<Tblserialnumber> tblserialnumberList = 
					gson.fromJson(parameterData, new TypeToken<List<Tblserialnumber>>() {
					}.getType());
			
				for (Tblserialnumber tblserialnumber : tblserialnumberList) {
					onInsertNo = tblserialnumber.getSnoSerialno();
					tblserialnumber.setSnoModifyuser(userName);
					String reponseInfo = "";
					reponseInfo = serialNumberService.insert(tblserialnumber);
					if(reponseInfo!=null&&!("").equals(reponseInfo)){
						info += reponseInfo+",";
					}
					if(reponseInfo.indexOf("该防伪码已经存在")<=0){
						n++;
						respSerialNo +=onInsertNo+",";
					}
				}
				respSerialNo = respSerialNo.indexOf(",")>0?respSerialNo.substring(0, respSerialNo.length()-1):respSerialNo;
				if(info.indexOf("该防伪码已经存在")>0){
					status = "failed";
				}else{
					status = "success";
				}
			} catch (JsonSyntaxException e) {
				logger.info("tostring:"+e.toString());
				status = "failed";
				info  =  "传输数据格式不正确！";
			} catch (Exception e) {
				logger.info("tostring:"+e.toString());
				status = "failed";
				info  +=  onInsertNo+"传输数据格式不正确！";
			}finally{
				//已使用标签数功能 2014-12-30 add by kyle
				Object object = cacheUtil.get(userName);
				String recipientNo = "";
				if(StringUtil.isNull(object)){
					recipientNo = labelService.getRecipientNoByUserName(userName);
					cacheUtil.put(userName, recipientNo);
				}else{
					recipientNo = (String) object;
				}
				labelService.updateUsedSerialNumberToRecipient(recipientNo,n);
				labelService.checkWarnVakyeAndSendEmail(recipientNo);
				//add end
				info = info.indexOf(",")>0?info.substring(0, info.length()-1):info;
				snij.setInfo(info);
				snij.setTotal(n);
				snij.setStatus(status);
				snij.setSerialNo(respSerialNo);
				return this.ajaxJson(response, gson.toJson(snij));
			}
		}else{
			status = Constant.REST_JSON_STATUS_FAILED;
			info  =  Constant.REST_USER_ERROR;
		}
		info = info.indexOf(",")>0?info.substring(0, info.length()-1):info;
		snij.setInfo(info);
		snij.setTotal(n);
		snij.setStatus(status);
		snij.setSerialNo(respSerialNo);
		return this.ajaxJson(response, gson.toJson(snij));
		
	}
	
	//JSON 接口  查询
	@RequestMapping(value="serialNumberQuery.json",method=RequestMethod.POST)
	@ResponseBody
	public String serialNumberQuery(String parameterData,String userName,String pwd,String ip,
			HttpServletRequest request,HttpServletResponse response){
		SerialNumberQueryJson snqj = new SerialNumberQueryJson();
		List<SerialNumberQueryData> list = new ArrayList<SerialNumberQueryData>();
		String info ="";
		String status = "";
		Gson gson = new Gson();
		if(serialNumberService.checkUser(userName,pwd)){
			RequestFilter filter = gson.fromJson(parameterData, RequestFilter.class);
			if(filter.getDays()!=null&&!("").equals(filter.getDays())){
				list = serialNumberService.serialNumberQuery(filter,userName);
				status = "success";
			}else{
				if(filter.getEndDate()!=null&&!("").equals(filter.getEndDate())&&
						(filter.getStartDate()==null||("").equals(filter.getStartDate()))){
					status = "failed";
					info = "startDate必须传值";
				}else{
					list = serialNumberService.serialNumberQuery(filter,userName);
					status = "success";
				}
			}
		}else{
			status = Constant.REST_JSON_STATUS_FAILED;
			info  =  Constant.REST_USER_ERROR;
		}
		snqj.setInfo(info);
		snqj.setList(list);
		snqj.setStatus(status);
		return this.ajaxJson(response, gson.toJson(snqj));
		
	}
	/**
	 * 2.4.3	生产计划写入接口
	 * @param parameterData
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@SuppressWarnings({ "finally", "serial" })
	@RequestMapping(value="addProductOrder.json",method=RequestMethod.POST)
	public String addProductOrder(String parameterData,String userName,String pwd,
			HttpServletRequest request,HttpServletResponse response){
		LabelSummaryInsertJson snij = new LabelSummaryInsertJson();
		Gson gson = new Gson();
		String info = "";
		String status = "";
		int n = 0;
		int total = 0;
		if(serialNumberService.checkUser(userName,pwd)){
			try{
				List<Map<String,String>> list = gson.fromJson(parameterData, 
						new TypeToken<List<Map<String,String>>>() {}.getType());
				List<Map<String,String>> insertList  = new ArrayList<Map<String,String>>();
				for (Map<String,String> map : list) {
					n++;total++;
					map.put("creater", userName);
					map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
					insertList.add(map);
					if(n==100){//每一百条执行一个批量处理
						labelService.productOrderRestBatchInsert(insertList);
						insertList  = new ArrayList<Map<String,String>>();
						n=0;
					}else if(total==list.size()){//当最后insertList不足100时
						labelService.productOrderRestBatchInsert(insertList);
					}
				}
				status = Constant.REST_JSON_STATUS_SUCCESS;
			}catch(JsonSyntaxException e){
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
				e.printStackTrace();
			}catch(Exception e){
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_DATA_EXISTS;
				total = 0;
				e.printStackTrace();
			}finally{
				snij.setInfo(info);
				snij.setStatus(status);
				snij.setTotal(total);
				return this.ajaxGJson(response, snij);
			}
		}else{
			status = Constant.REST_JSON_STATUS_FAILED;
			info  =  Constant.REST_USER_ERROR;
		}
		snij.setInfo(info);
		snij.setStatus(status);
		snij.setTotal(total);
		return this.ajaxGJson(response, snij);
	}
	
	/**
	 * 2.4.4	生产计划删除接口
	 * @param productionDate
	 * @param plantNo
	 * @param userName
	 * @param pwd
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="removeProductOrder.json",method=RequestMethod.POST)
	public String removeProductOrder(String productionDate,String plantNo,String userName,String pwd,
			HttpServletRequest request,HttpServletResponse response){
		LabelSummaryInsertJson snij = new LabelSummaryInsertJson();
		String info = "";
		String status = "";
		int total = 0;
		if(serialNumberService.checkUser(userName,pwd)){
			if(productionDate!=null&&!("").equals(productionDate.trim())){
				try{
					total = labelService.removeProductOrder(productionDate,plantNo,userName);
					status = Constant.REST_JSON_STATUS_SUCCESS;
				}catch(Exception e){
					status = Constant.REST_JSON_STATUS_FAILED;
					info  =  Constant.REST_JSON_ERROR;
					total = 0;
					e.printStackTrace();
				}finally{
					snij.setInfo(info);
					snij.setStatus(status);
					snij.setTotal(total);
					return this.ajaxGJson(response, snij);
				}
			}else{
				status = Constant.REST_JSON_STATUS_FAILED;
				info  =  Constant.REST_JSON_ERROR;
			}
		}else{
			status = Constant.REST_JSON_STATUS_FAILED;
			info  =  Constant.REST_USER_ERROR;
		}
		snij.setInfo(info);
		snij.setStatus(status);
		snij.setTotal(total);
		return this.ajaxGJson(response, snij);
	}
	
	public class RequestFilter{
		String startDate;
		String endDate;
		String days;
		
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getDays() {
			return days;
		}
		public void setDays(String days) {
			this.days = days;
		}
		
	}
	
	/*@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Gson gson = new Gson();
		String s = "{'serialNo':'2222222'}";
		Tblserialnumber map = 
				gson.fromJson(s, Tblserialnumber.class);
		try {
			Class cls = Class.forName("com.sinosoft.antifake.entity.Tblserialnumber");
			Field[] f = cls.getDeclaredFields();
			for (Field ff : f) {
				if(ff.isAnnotationPresent(Length.class)){
					ff.setAccessible(true);
					org.hibernate.validator.constraints.Length l = ff.getAnnotation(Length.class);
					System.out.println(l.max());
				}
				System.out.println(ff.getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	

	/**
	 * output the text, and return null value.
	 * 
	 * @param response
	 * @param jsonString
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	public String ajaxGJson(HttpServletResponse response, Object obj) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String jsonString = gson.toJson(obj);
		return ajax(response, jsonString, "text/html");
	}

	/**
	 * output the ajax, and return the null value.
	 * 
	 * @param response
	 * @param content
	 * @param type
	 * @return
	 */
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
			logger.error("IOException", e);
		}
		return null;
	}
	
}

