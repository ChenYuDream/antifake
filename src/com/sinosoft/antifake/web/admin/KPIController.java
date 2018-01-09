package com.sinosoft.antifake.web.admin;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springside.modules.web.Servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinosoft.antifake.service.KPIService;
/**
 * KPI统计
 * @author kyle
 *
 */
@Controller
@RequestMapping(value="/admin/kpi")
public class KPIController {
	private static final int PAGE_SIZE = 10;
	@Autowired
	private KPIService kpiService;
	/**
	 * 查询日志列表.
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, 
			@RequestParam(value = "export", defaultValue = "0") int export, 
			@RequestParam(value = "detail_export", defaultValue = "0") int detail_export,
			String detail_plantNo,
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
			kpiService.exportXLS(searchParams, response);
			//如果再跳转就报: java.lang.IllegalStateException: STREAM
			return null;
		}
		//详情导出
		if(1 == detail_export ) {
			searchParams.put("plantNo", detail_plantNo);
			kpiService.detailExportXLS(searchParams, response);
			//如果再跳转就报: java.lang.IllegalStateException: STREAM
			return null;
		}
		
		//查询结果
		/*Page<LableDetail> queryLogs = labelStateService.getQueryLogList(searchParams, pageNumber, PAGE_SIZE, sortType);*/
		/*model.addAttribute("queryLogs", queryLogs);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));*/
		List<HashMap<String,Object>> list = kpiService.labelStataList(searchParams);
		model.addAttribute("queryLogs", list);
		return "admin/KPIstatistics/KPIstatisticsList";
	}
	
	/**
	 * 按工厂查询 groupby生产订单号
	 * @param plantNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/showDetail.json",method=RequestMethod.GET)
	public void showPlantDetail(String plantNo,String productionDate
			, HttpServletRequest request, HttpServletResponse response){
		if(productionDate!=null&&!("").equals(productionDate.trim())){
			productionDate =  productionDate+"-1";
		}
		List resList = kpiService.showPlantDetail(plantNo,productionDate);
		Gson gson = new Gson();
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write(gson.toJson(resList));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 统计及时性KPI以及上传数量
	 * @param plantNo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/countPlantKpi",method=RequestMethod.GET)
	public void countPlantKpi(String plantNo,
			HttpServletRequest request, HttpServletResponse response){
		kpiService.countPlantKpi(plantNo);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/countDetailKpi",method=RequestMethod.GET)
	public void countDetailKpi(String spcialNo,
			HttpServletRequest request, HttpServletResponse response){
		kpiService.countDetailKpi(spcialNo);
		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
