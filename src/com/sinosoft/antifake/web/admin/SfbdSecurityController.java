package com.sinosoft.antifake.web.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.antifake.entity.SfbdSecurity;
import com.sinosoft.antifake.helpers.excel.sfbdSecurity.SfbdSecurityBuilder;
import com.sinosoft.antifake.ibatis.service.LabelService;
import com.sinosoft.antifake.service.RecepitHisService;
import com.sinosoft.antifake.service.SfbdSecurityService;

/**
 * 
 * @Description:SFBD工厂防伪数据Controller
 * @author wangxueqiang
 * @date 2017年12月6日 下午3:15:15
 *
 */
@Controller
@RequestMapping(value = "/admin/sfdb")
public class SfbdSecurityController {
	
	@Autowired
	private SfbdSecurityService sfbdSecurityService; 	
	@Autowired
	private LabelService labelService;
	@Autowired
	private RecepitHisService recepitHisService; 

	private static Logger logger = LoggerFactory.getLogger(SfbdSecurityController.class);
	/**
	 * 
	 * @Description: 页面跳转
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月6日 下午3:18:11
	 *
	 */
	@RequestMapping(value = "/list")
	public String list(Model model){
		@SuppressWarnings("rawtypes")
		List plantList = recepitHisService.getRecipient();
		model.addAttribute("plantList", plantList);
		return "admin/sfdb/sfdbList";
	}
	
	/**
	 * 
	 * @Description: 导入数据
	 * @param file
	 * @param session
	 * @param import_recipient
	 * @return
	 * @author wangxueqiang
	 * @date 2017年12月11日 下午3:12:10
	 *
	 */
    @ResponseBody
	@RequestMapping(value="/import")
	public String importExcel(
			@RequestParam("file")MultipartFile file,
			HttpSession session,
			@RequestParam("import_recipient")String import_recipient){
		try {
			if(!file.isEmpty()){
				//判断用户是否登录
				String userName = (String)session.getAttribute("USER_NAME");
				if(null == userName || !"master".equals(userName)) {
					return "no-login";
				}
				if(file.getOriginalFilename().indexOf(".xlsx")>0){
					//07及以上版本
					SfbdSecurityBuilder.importXlsxFile(file.getInputStream(),labelService,userName,import_recipient);
					return "success";
				}else if(file.getOriginalFilename().indexOf(".xls")>0){
					SfbdSecurityBuilder.importFromExcel2003(file.getInputStream(),labelService,userName,import_recipient);
					return "success";
				}else{
					return "file-error";
				}
			}
			return "success";
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
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
/*    @ResponseBody
	@RequestMapping(value="/findByBarcode",method=RequestMethod.POST)
	public SfbdSecurity findByBarcode(HttpServletRequest request ){
    	String barcode = request.getParameter("barcode");
        List<SfbdSecurity> sfbdSecurity = sfbdSecurityService.findByBarcode(barcode);	
    	if(null != sfbdSecurity && sfbdSecurity.size()>0){   		
    		 return sfbdSecurity.get(0);
    	}else{
    		
    		return new SfbdSecurity();
    	}	  	
	}*/
}
