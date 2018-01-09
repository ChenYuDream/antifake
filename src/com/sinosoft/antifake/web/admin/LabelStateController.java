package com.sinosoft.antifake.web.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.sinosoft.antifake.helpers.CacheUtil;
import com.sinosoft.antifake.helpers.StringUtil;
import com.sinosoft.antifake.ibatis.dao.GeneralDAO;
import com.sinosoft.antifake.ibatis.service.LabelService;
import com.sinosoft.antifake.service.LabelStateService;
import com.sinosoft.antifake.service.RecepitHisService;
/**
 * 标签状态管理
 * @author kyle
 *
 */
@Controller
@RequestMapping(value="/admin/labelState")
public class LabelStateController {
	private static final int PAGE_SIZE = 10;
	@Autowired
	private LabelStateService labelStateService;
	@Autowired
	private RecepitHisService recepitHisService;
	@Autowired
	private LabelService labelSummaryService;
	
	static CacheUtil cacheUtil = new CacheUtil(100);
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
		
		//判断是否是邮件链接 如果是则将id存入session

		String id = request.getParameter("search_recipient");
		if(null!=id){
			request.getSession().setAttribute("mail_recipient_no", id);
		}
		//判断用户是否登录
		String userName = (String)request.getSession().getAttribute("USER_NAME");
		if(null == userName || !"master".equals(userName)) {
			return "redirect:/admin";
		}
		
		//查询参数
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		if(!searchParams.isEmpty()){//若查询filter中有数据，则清空mail_report_Id
			request.getSession().removeAttribute("mail_recipient_no");
		}
		//获取mail_report_Id，放入查询拦截map
		String session_id = (String)request.getSession().getAttribute("mail_recipient_no");
		if(null!=session_id){
			searchParams.put("recipient", session_id);
			request.getSession().removeAttribute("mail_recipient_no");
		}
		//Excel 导出
		if(1 == export ) {
			labelStateService.exportXLS(searchParams, response);
			//如果再跳转就报: java.lang.IllegalStateException: STREAM
			return null;
		}
		
		//查询结果
		/*Page<LableDetail> queryLogs = labelStateService.getQueryLogList(searchParams, pageNumber, PAGE_SIZE, sortType);*/
		/*model.addAttribute("queryLogs", queryLogs);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));*/
		List<HashMap<String,Object>> list = labelStateService.labelStataList(searchParams);
		model.addAttribute("queryLogs", list);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		List plantList = recepitHisService.getRecipient();
		model.addAttribute("plantList", plantList);
		return "admin/labelState/labelStateList";
	}
	/**
	 * 
	 * @param updateWarnValue
	 */
	@RequestMapping(value="/updateWarnValue")
	public String updateWarnValue(int warnValue,String recipientNo){
		labelStateService.updateWarnValue(warnValue,recipientNo);
		//检测是否要发邮件 add by kyle 2015-1-7
		if(StringUtil.isNull(recipientNo)){
			List<HashMap> list_recipientNo = labelStateService.getRecipientNo();
			for (HashMap map : list_recipientNo) {
				String no = map.get("recipient_no").toString();
				labelSummaryService.checkWarnVakyeAndSendEmail(no);
			}
		}else{
			labelSummaryService.checkWarnVakyeAndSendEmail(recipientNo);
		}
		return "redirect:/admin/labelState/list";
	}
	//
	@RequestMapping(value="/getRecipientWarnValue")
	public void getRecipientWarnValue(String recipientNo,HttpServletResponse response) throws IOException{
		response.getWriter().print(labelStateService.getRecipientWarnValue(recipientNo));
	}
}
