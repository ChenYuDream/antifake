package com.sinosoft.antifake.web.admin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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

import com.sinosoft.antifake.entity.LableSummary;
import com.sinosoft.antifake.service.RecepitHisService;
/**
 * 收货历史管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/admin/recepitHis")
public class RecepitHisController {
	private static final int PAGE_SIZE = 10;
	@Autowired
	private RecepitHisService recepitHisService;
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
		boolean flag = false;
		if(searchParams.size()==0){
			flag = true;
		}
		if(searchParams.get("LIKE_recipient")!=null&&!("").equals(searchParams.get("LIKE_recipient").toString())){
			List searchPlants = recepitHisService.getRecipientByName(searchParams.get("LIKE_recipient").toString());
			if(searchPlants.size()>0){
				searchParams.put("EQ_recipientNo", ((HashMap)searchPlants.get(0)).get("recipient_no"));
			}else{
				searchParams.put("EQ_recipientNo", "noPlantNo");
			}
			searchParams.remove("LIKE_recipient");
			
		}
		//全部
		if(searchParams.get("EQ_receiveStatus")!=null
				&&("2").equals(searchParams.get("EQ_receiveStatus").toString())){
			searchParams.remove("EQ_receiveStatus");
			flag = true;
		}
		//Excel 导出
		if(1 == export ) {
			recepitHisService.exportXLS(searchParams, response);
			//如果再跳转就报: java.lang.IllegalStateException: STREAM
			return null;
		}
		//查询结果
		Page<LableSummary> queryLogs = recepitHisService.getQueryLogList(searchParams, pageNumber, PAGE_SIZE, sortType);
		List plantList = recepitHisService.getRecipient();
		model.addAttribute("plantList", plantList);
		model.addAttribute("queryLogs", queryLogs);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		if(flag){
			searchParams.put("EQ_receiveStatus", "2");
			model.addAttribute("flag", "init");
		}
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "admin/receiptHis/receiptHisList";
	}
}
