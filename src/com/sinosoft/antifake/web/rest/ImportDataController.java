package com.sinosoft.antifake.web.rest;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinosoft.antifake.helpers.CacheUtil;
import com.sinosoft.antifake.helpers.TXTUtil;
import com.sinosoft.antifake.ibatis.service.LabelService;

@Controller
@RequestMapping("/import")
public class ImportDataController {
	
	@Autowired LabelService labelService;
	
	static CacheUtil cacheUtil = new CacheUtil(100);
	
	/*static Properties pro;
	static{
		try {
			pro.load(ImportDataController.class.getResourceAsStream("/filePath.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	@RequestMapping(value="/po")
	public void poImport(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,String> map = labelService.putRecepitInfo();
		try{
			TXTUtil.insertPo(labelService,map);
	        this.ajaxGJson(response, "success");
		}catch(Exception e){
			 this.ajaxGJson(response, e.getMessage());
		}
	}
	
	@RequestMapping(value="/mo")
	public void moImport(HttpServletRequest request,HttpServletResponse response){
		HashMap<String,String> map = labelService.putRecepitInfo();
		try{
			TXTUtil.insertMo(labelService,map);
	        this.ajaxGJson(response, "success");
		}catch(Exception e){
			 this.ajaxGJson(response, e.getMessage());
		}
	}
	
	@RequestMapping(value="/countKPI")
	public void countKPI(HttpServletRequest request,HttpServletResponse response){
		try{
			labelService.countKPI();
			this.ajaxGJson(response, "success");
		}catch(Exception e){
			e.printStackTrace();
			this.ajaxGJson(response, e.getMessage());
		}
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
		}
		return null;
	}
}
