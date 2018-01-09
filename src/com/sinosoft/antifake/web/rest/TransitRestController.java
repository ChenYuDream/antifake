package com.sinosoft.antifake.web.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springside.modules.mapper.BeanMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.entity.QueryResult;
import com.sinosoft.antifake.entity.ReportInfo;
import com.sinosoft.antifake.helpers.WebServiceHelper;
/**
 * 接口中转站
 * */
@Controller
@RequestMapping(value="/transit")
public class TransitRestController {
	
		private static Logger logger = LoggerFactory.getLogger(TransitRestController.class);
		//业务 url 写死  这样貌似不利于维护？
		private static final String url_products  ="http://localhost:8080/antifake/products";
		//系统url
		private static final String url_app  ="http://localhost:8080/antifake/app";
		//工厂写入&查询 url
		private static final String url_factory = "http://localhost:8080/antifake/factory/sync";
		// 查询 json接口
		@RequestMapping(value = "/query/{lableNo}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public QueryResultDTO monitor(@PathVariable("lableNo") String lableNo, QueryLog queryLog, HttpServletRequest request) {
			Gson gson = new Gson();
			QueryResult queryResult = gsonTo(WebServiceHelper.sendGet(url_products+"/query/"+queryLog.getLableNo()+".json", gson.toJson(queryLog)),QueryResult.class);
			return bindDTO(queryResult, request);
		}
		
		// 疑似报告上传 json 接口
		@RequestMapping(value = "/saveReportInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		private String saveReportInfo(HttpServletRequest request, ReportInfo reportInfo,HttpServletResponse response) {
			Gson gson = new Gson();
			return this.ajaxJson(response, WebServiceHelper.sendPost(url_products+"/saveReportInfo", gson.toJson(reportInfo)));
		}
		
		// 版本更新json接口
		@RequestMapping(value="/findVersionInfo",method = RequestMethod.GET)
		@ResponseBody
		public String findVersionInfo(String type,HttpServletResponse response) throws IOException {
			return this.ajaxJson(response, WebServiceHelper.sendGet(url_app+"/findVersionInfo", "type="+type));
		}
		
		// json 接口 工厂写入
		@RequestMapping(value = "/serialNumber.json", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public String getQueryAsJson(String parameterData ,String userName,String ip,String pwd,
					HttpServletRequest request,HttpServletResponse response) {
			return this.ajaxJson(response,WebServiceHelper.sendPost(url_factory+"/serialNumber.json", "parameterData="+parameterData+"&userName="+userName+"&pwd="+pwd+"&ip="+ip));
			
		}
		//JSON 接口  工厂查询
		@RequestMapping(value="serialNumberQuery.json",method=RequestMethod.POST)
		@ResponseBody
		public String serialNumberQuery(String parameterData,String userName,String pwd,String ip,
				HttpServletRequest request,HttpServletResponse response){
			return this.ajaxJson(response,WebServiceHelper.sendPost(url_factory+"/serialNumberQuery.json", "parameterData="+parameterData+"&userName="+userName+"&pwd="+pwd+"&ip="+ip));
		}
		
		
		// RESTful 对象属性绑定
		private QueryResultDTO bindDTO(QueryResult queryResult, HttpServletRequest request) {
			QueryResultDTO dto = BeanMapper.map(queryResult, QueryResultDTO.class);
			dto.setLan(RequestContextUtils.getLocale(request).toString()); // 补充Dozer不能自动绑定的语言属性
			return dto;
		}
		
		private <T> T gsonTo(String json,Class<T> c){
			T t = null;
			Gson gson = new Gson();
			t  = gson.fromJson(json, c);
			return t;
		}
		
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
