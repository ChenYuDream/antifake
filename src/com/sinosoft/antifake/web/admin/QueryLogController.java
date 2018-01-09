package com.sinosoft.antifake.web.admin;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.service.QueryLogService;

/**
 * 
 * @author chenshaoao
 */
@Controller
@RequestMapping(value = "/admin/queryLog")
public class QueryLogController {

	private static final int PAGE_SIZE = 10;
	@Autowired
	private QueryLogService queryLogService;
	/**
	 * 查询日志列表.
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, 
			@RequestParam(value = "export", defaultValue = "0") int export, 
			Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		//判断用户是否登录
		String userName = (String)request.getSession().getAttribute("USER_NAME");
		if(null == userName || !"master".equals(userName)) {
			return "redirect:/admin";
		}
		
		//查询参数
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		//Excel 导出
		if(1 == export ) {
			queryLogService.exportXLS(searchParams, response);
			//如果再跳转就报: java.lang.IllegalStateException: STREAM
			return null;
		}
		
		//查询结果
		Page<QueryLog> queryLogs = queryLogService.getQueryLogList(searchParams, pageNumber, PAGE_SIZE, sortType);
		model.addAttribute("queryLogs", queryLogs);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "admin/queryLog/queryLogList";
	}
}
