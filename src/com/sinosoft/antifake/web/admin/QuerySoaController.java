package com.sinosoft.antifake.web.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.entity.SfbdSecurity;
import com.sinosoft.antifake.service.QueryLogService;
import com.sinosoft.antifake.service.SfbdSecurityService;

/**
 * 
 * @Description:SOA防伪码查询 
 * @author wangxueqiang
 * @date 2017年12月6日 下午5:36:53
 *
 */
@Controller
@RequestMapping("/admin/querySoa")
public class QuerySoaController {
	private static Logger logger = LoggerFactory.getLogger(QuerySoaController.class);
	@Autowired
	private SfbdSecurityService sfbdSecurityService; 
	@Autowired
	private  QueryLogService queryLogService;
	/**
	 * 
	 * @Description: 页面跳转
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月6日 下午5:39:25
	 *
	 */
	@RequestMapping(value = "/list")
	public String list(){
		return "admin/querySoa/querySoaList";
	}
	
	/**
	 * 
	 * @Description: 根据产品码查询信息
	 * @param barcode
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月11日 下午3:04:27
	 *
	 */
	@RequestMapping(value="/findByBarcode")
	public String findByBarcode(HttpServletRequest request,Model model){
    	String barcode = request.getParameter("barcode");
    	List<SfbdSecurity> sfbdSecurity = sfbdSecurityService.findByBarcode(barcode);	
    	if(null != sfbdSecurity && sfbdSecurity.size()>0){   		
    		model.addAttribute("sfbdSecurity", sfbdSecurity.get(0));
    		model.addAttribute("count", sfbdSecurity.size());
    	}else{
    		model.addAttribute("sfbdSecurity", new SfbdSecurity());
    		model.addAttribute("count", 0);
    	}
    	QueryLog log = queryLogService.findByLableNo(barcode);
    	if(null != log){
    		model.addAttribute("queryCount", null==log.getQueryCount()||"".equals(log.getQueryCount())?"0":log.getQueryCount());
    	}
    	return "admin/querySoa/querySoaList";
	}
	
	@RequestMapping(value="/save")
	@ResponseBody
	public String saveOrUpdate(QueryLog log){
		return queryLogService.saveOrUpdate(log);		
	}
	

}
