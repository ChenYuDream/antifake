package com.sinosoft.antifake.web.admin;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.web.Servlets;

import com.sinosoft.antifake.entity.ProductOrder;
import com.sinosoft.antifake.helpers.DateUtil;
import com.sinosoft.antifake.helpers.excel.productOrder.ProductOrderBuilder;
import com.sinosoft.antifake.ibatis.service.LabelService;
import com.sinosoft.antifake.service.ProductOrderService;
import com.sinosoft.antifake.service.RecepitHisService;
/**
 * 产品订单管理
 * @author kyle
 *
 */
@Controller
@RequestMapping(value="/admin/productOrder")
public class ProductOrderController {
	private static final int PAGE_SIZE = 10;
	@Autowired
	private ProductOrderService productOrderService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private RecepitHisService recepitHisService;
	
	private static Logger logger = LoggerFactory.getLogger(ProductOrderController.class);
	/**
	 * 查询日志列表.
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/list")
	public String list(
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType,
			@RequestParam(value = "page", defaultValue = "1") int pageNumber, 
			@RequestParam(value = "export", defaultValue = "0") int export, 
			Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
		
		//判断用户是否登录
		String userName = (String)request.getSession().getAttribute("USER_NAME");
		if(null == userName || !"master".equals(userName)) {
			return "redirect:/admin";
		}
		
		//查询参数
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		if(searchParams.get("LIKE_recipient")!=null&&!("").equals(searchParams.get("LIKE_recipient").toString())){
			List searchPlants = recepitHisService.getRecipientByName(searchParams.get("LIKE_recipient").toString());
			if(searchPlants.size()>0){
				searchParams.put("EQ_plantNo", ((HashMap)searchPlants.get(0)).get("recipient_no"));
			}else{
				searchParams.put("EQ_plantNo", "noPlantNo");
			}
			searchParams.remove("LIKE_recipient");
			
		}
		
		if(searchParams.get("productionDate")!=null&&!("").equals(searchParams.get("productionDate").toString())){
			/*searchParams.put("LIKE_productionDate", searchParams.get("productionDate").toString());*/
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
	        Date date = df.parse(searchParams.get("productionDate").toString());
	        Map<String, String> map = DateUtil.getFirstday_Lastday_Month(date);
	        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
	        searchParams.put("GTE_productionDate", map.get("first"));
	        searchParams.put("LTE_productionDate", map.get("last"));
	        searchParams.remove("productionDate");
		}
		//Excel 导出
		if(1 == export ) {
			productOrderService.exportXLS(searchParams, response);
			//如果再跳转就报: java.lang.IllegalStateException: STREAM
			return null;
		}
		//查询结果
		Page<ProductOrder> queryLogs = productOrderService.getQueryLogList(searchParams, pageNumber, PAGE_SIZE, sortType);
		List plantList = recepitHisService.getRecipient();
		model.addAttribute("plantList", plantList);
		model.addAttribute("queryLogs", queryLogs);
		model.addAttribute("sortType", sortType);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));
		return "admin/productOrder/productOrderList";
	}
	/**
	 * 导入数据
	 * @param file
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/import")
	public String importExcel(@RequestParam("file")MultipartFile file,HttpSession session
			,@RequestParam("import_recipient")String import_recipient){
		try {
			if(!file.isEmpty()){
				if(file.getOriginalFilename().indexOf(".xlsx")>0){//2007不考虑
					//LabelOrderBuilder.importFromExcel2007(file.getInputStream());
				}else{
					//判断用户是否登录
					String userName = (String)session.getAttribute("USER_NAME");
					if(null == userName || !"master".equals(userName)) {
						return "redirect:/admin";
					}
					ProductOrderBuilder.importFromExcel2003(file.getInputStream(),labelService,userName,import_recipient);
				}
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return "redirect:/admin/productOrder/list";
		}
	}
}
