package com.sinosoft.antifake.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinosoft.antifake.helpers.Constant;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.excel.ExcelWriter;
import com.sinosoft.antifake.helpers.excel.labelState.LabelStateBuilder;
import com.sinosoft.antifake.ibatis.dao.GeneralDAO;
import com.sinosoft.antifake.repository.LabelStateDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class LabelStateService {

	@Autowired
	private LabelStateDao labelStateDao;

	@PersistenceContext
	protected EntityManager em;
	
	@Autowired
	private GeneralDAO generalDAO;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HashMap<String,Object>> labelStataList(Map<String, Object> searchParams){
		HashMap paramMap = new  HashMap();
		if(searchParams!=null&&searchParams.get("recipient")!=null){
			paramMap.put("FIND_BY_AND", "True");
			paramMap.put("recipient", searchParams.get("recipient").toString().trim());
		}
		List list = generalDAO.getListSql("labelStateList", paramMap);
		List<HashMap<String,Object>> resList = new ArrayList<HashMap<String,Object>>();
		for (Object object : list) {
			HashMap<String,Object> map = (HashMap<String,Object>)object;
			String recipient = "";
			if(map.get("recipient")!=null){
				recipient = map.get("recipient").toString();
			}
			String recipient_no = map.get("recipient_no")!=null?map.get("recipient_no").toString():"";
			int total = Integer.parseInt(map.get("total")!=null?map.get("total").toString():"0");//总共
			int used = Integer.parseInt(map.get("used")!=null?map.get("used").toString():"0");//已使用
			int scrap = Integer.parseInt(map.get("scrap")!=null?map.get("scrap").toString():"0");//报废
			int inventory = total-used-scrap;
			HashMap<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("recipientNo", recipient_no);
			resMap.put("recipient", recipient);
			resMap.put("used", used);
			resMap.put("inventory", inventory);
			resMap.put("scrap", scrap);
			resMap.put("state", "");
			int warnValue = Integer.parseInt(map.get("warn_value")!=null?map.get("warn_value").toString():"0");
			String flag = inventory<warnValue?Constant.DATA_YES:Constant.DATA_NO;
			resMap.put("flag", flag);
			if(searchParams!=null&&searchParams.get("flag")!=null&&!("").equals(searchParams.get("flag").toString().trim())){
				if(searchParams.get("flag").toString().equals("0")&&!flag.equals(Constant.DATA_YES)){
					continue;
				}else if(searchParams.get("flag").toString().equals("1")&&!flag.equals(Constant.DATA_NO)){
					continue;
				}
			}
			resList.add(resMap);
		}
		return resList;
	}


	// 读取数据库并导出报表
	public void exportXLS(Map<String, Object> searchParams, HttpServletResponse response)
			throws UnsupportedEncodingException {

		// 1.创建一个 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2.创建一个 worksheet
		HSSFSheet worksheet = workbook.createSheet("log");
		// 3.创建title,data,headers
		LabelStateBuilder.buildReport(worksheet);
		// 4.填充数据
		LabelStateBuilder.fillReport(worksheet, labelStataList(searchParams));
		// 5.设置reponse参数
		String fileName = URLEncoder.encode(("antifake-标签状态统计 -"), "utf-8") + DateHelper.getCurrentDate() + ".xls";
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setContentType("application/vnd.ms-excel");
		// 6. 输出流
		ExcelWriter.write(response, worksheet);

	}
	
	//根据recipient获取警戒值
	public int getWarnValue(String recipient){
		int value = 0;
		if(recipient.equals("")||recipient==null){
			return value;
		}else{
			HashMap map = new HashMap();
			map.put("recipient", recipient);
			List list = generalDAO.getListSql("getWarnValueByRecipient", map);
			if(list.size()>0){
				value = Integer.parseInt(((HashMap)list.get(0)).get("warn_value").toString());
			}
		}
		return value;
	}


	public void updateWarnValue(int warnValue, String recipientNo) {
		HashMap map = new HashMap();
		map.put("warnValue", warnValue);
		map.put("recipientNo", recipientNo);
		generalDAO.updateSql("updateWarnValue", map);
		
	}


	public int getRecipientWarnValue(String recipientNo) {
		int warnValue = 0;
		HashMap map = new HashMap();
		map.put("recipientNo", recipientNo);
		warnValue = (Integer) generalDAO.getObjectSql("getRecipientWarnValue", map);
		return warnValue;
	}


	public List getRecipientNo() {
		return generalDAO.getListSql("getAllRecipientNo", null);
	}

}
