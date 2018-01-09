package com.sinosoft.antifake.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinosoft.antifake.entity.ReportInfo;
import com.sinosoft.antifake.entity.rReportInfo;
import com.sinosoft.antifake.service.QueryLogService;
import com.sinosoft.antifake.service.ReportInfoService;

/**
 * 
 * @author chenshaoao
 */
@Controller
@RequestMapping(value = "/admin/reportInfo")
public class ReportInfoController {
	
	private static Logger logger = LoggerFactory.getLogger(ReportInfoController.class);
	
	private static final int PAGE_SIZE = 10;
	@Autowired
	private ReportInfoService ReprotInfoService;
	@Autowired
	private QueryLogService queryLogService;
	/**
	 * 查询疑似报告列表.
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, 
			@RequestParam(value = "export", defaultValue = "0") int export, 
			Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		try{
		//判断是否是邮件链接 如果是则将id存入session

			String id = request.getParameter("id");
		if(null!=id){
			request.getSession().setAttribute("mail_report_Id", id);
		}
		//判断用户是否登录
		String userName = (String)request.getSession().getAttribute("USER_NAME");
		if(null == userName || !"master".equals(userName)) {
			return "redirect:/admin";
		}
		//获取查询 内容
		Map<String,Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		if(!searchParams.isEmpty()){//若查询filter中有数据，则清空mail_report_Id
			request.getSession().removeAttribute("mail_report_Id");
		}
		//获取mail_report_Id，放入查询拦截map
		String session_id = (String)request.getSession().getAttribute("mail_report_Id");
		if(null!=session_id){
			searchParams.put("EQ_id", session_id);
			request.getSession().removeAttribute("mail_report_Id");
		}
		//判断动作是否为导出Excel操作
		if(1==export){
			ReprotInfoService.exportXLS(searchParams, response);
			return null;
		}
		Page<ReportInfo> pagelist = ReprotInfoService.getPagelist(searchParams,pageNumber,PAGE_SIZE,sortType);
		model.addAttribute("pagelist", pagelist);
		model.addAttribute("sortType", sortType);
		model.addAttribute("searchParams",  Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		List<rReportInfo> list = ReprotInfoService.findAll(searchParams, pageNumber, PAGE_SIZE, sortType);
		List<HashMap<String,String>> stateList = ReprotInfoService.getStateList();
		model.addAttribute("list", list);
		model.addAttribute("statelist", stateList);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "admin/reportInfo/ReportInfoList";
	}
	
	/**
	 * 根据ID获取上报信息
	 * 
	 * */
	@RequestMapping(value="/getReportInfo")
	public void getReportInfo(String id,HttpServletRequest request, HttpServletResponse response){
		ReportInfo reportInfo =  ReprotInfoService.getReportInfo(id);
		Gson gson = new Gson();
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(gson.toJson(reportInfo));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 后台编辑疑似报告保存
	 * */
	@RequestMapping(value="/updateReportInfo")
	public void updateReportInfo(ReportInfo reportInfo,HttpServletRequest request, 
			HttpServletResponse response){
		ReprotInfoService.updateReportInfo(reportInfo);
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
