package com.sinosoft.antifake.helpers.excel.sfbdSecurity;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sinosoft.antifake.helpers.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.antifake.entity.SfbdSecurity;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;
import com.sinosoft.antifake.helpers.excel.ExcelUtils;
import com.sinosoft.antifake.ibatis.service.LabelService;
/**
 * 
 * @Description:SFBD工厂防伪数据导入库
 * @author wangxueqiang
 * @date 2017年12月7日 上午11:51:34
 *
 */
public class SfbdSecurityBuilder {
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(SfbdSecurityBuilder.class);
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("生产日期");	
		titles.add("条形码");
		titles.add("批次");
		titles.add("物料代码");	
		return titles;
	}
	//设置表头
	public static void buildReport(HSSFSheet worksheet){ 
		HSSFCellStyle titleStyle = ExcelStyle.getTitleStyle(worksheet, HSSFColor.GREEN.index);
		List<String> titleList = getTitle();
		//第一行表头
        HSSFRow titleRow = worksheet.createRow(0);
		for (int i = 0; i < titleList.size(); i++) {
			worksheet.setColumnWidth(i, 20*255);
			HSSFCell titleCell = titleRow.createCell(i);
			titleCell.setCellStyle(titleStyle);
			titleCell.setCellValue(titleList.get(i));
		}
    }
	//填写数据
	public static void fillReport(HSSFSheet worksheet,List<SfbdSecurity> queryLogList) throws ParseException {
		//excel 从0 开始计数，第一行是表头
		int rowIndex = 1;
		for(SfbdSecurity vo : queryLogList) {
			HSSFRow row = worksheet.createRow(rowIndex);
			row.createCell(0).setCellValue( new HSSFRichTextString(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(vo.getCreateDate()))));
			row.createCell(1).setCellValue( new HSSFRichTextString(vo.getBarcode()));
			row.createCell(2).setCellValue( new HSSFRichTextString(vo.getBatchNo()));
			row.createCell(3).setCellValue( new HSSFRichTextString(vo.getProductCode()));
			rowIndex ++;
		}
	}
	//导入数据
	public static void  importFromExcel2003(InputStream fis, LabelService labelService, String creater, String import_recipient){
		POIFSFileSystem pois = null;
		try {
			pois = new POIFSFileSystem(fis);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(pois);
			for(int sheetNum=0;sheetNum<hssfWorkbook.getNumberOfSheets();sheetNum++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
				if(hssfSheet==null){
					continue;
				}
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				int n = 0;
				for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
					n++;
					Map<String,String> map = new HashMap<String, String>();
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					//判断第四列是否有数据，没有则下一个单元格
					String txCode = String.valueOf(ExcelUtils.getCellValue(hssfRow, 3));
					if(StringUtil.isNull(txCode)||"条形码".equals(txCode)){
						continue;
					}
					map.put("createDate",  String.valueOf(ExcelUtils.getCellValue(hssfRow, 2)));
					map.put("productCode",String.valueOf(ExcelUtils.getCellValue(hssfRow, 3)));
					map.put("batchNo",  String.valueOf(ExcelUtils.getCellValue(hssfRow, 4)));
					map.put("barcode",  String.valueOf(ExcelUtils.getCellValue(hssfRow, 5)));
					map.put("companyCode",  import_recipient.trim());
					map.put("creater", creater);
					map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
					list.add(map);
					if(n==100){
						labelService.insertSfbdSecurityByBatch(list);
						n = 0;
						list = new ArrayList<Map<String,String>>();
					}else if(rowNum==hssfSheet.getLastRowNum()){
						labelService.insertSfbdSecurityByBatch(list);
					}				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导入高版本excel模板
	 * wujian
	 * @param fis
	 * @param labelService
	 * @param creater
	 * @param import_recipient
	 * @return
	 */
	public static Boolean importXlsxFile(InputStream fis, LabelService labelService, String creater, String import_recipient){
		try {
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			for(int sheetNum=0;sheetNum<wb.getNumberOfSheets();sheetNum++){
				XSSFSheet sheet = wb.getSheetAt(sheetNum);
				if(sheet==null){
					continue;
				}
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				int n = 0;
				for(int rowNum=1;rowNum<=sheet.getLastRowNum();rowNum++){
					n++;
					Map<String,String> map = new HashMap<String, String>();
					XSSFRow row = sheet.getRow(rowNum);
					//判断第四列是否有数据，没有则下一个单元格
					String txCode = String.valueOf(ExcelUtils.getCellValue(row, 3));
					if(StringUtil.isNull(txCode)||"条形码".equals(txCode)){
						continue;
					}
					map.put("createDate",  String.valueOf(ExcelUtils.getCellValue(row, 2)));
					map.put("productCode",String.valueOf(ExcelUtils.getCellValue(row, 3)));
					map.put("batchNo",  String.valueOf(ExcelUtils.getCellValue(row, 4)));
					map.put("barcode",  String.valueOf(ExcelUtils.getCellValue(row, 5)));
					map.put("companyCode",  import_recipient.trim());
					map.put("creater", creater);
					map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
					list.add(map);
					if(n==100){
						labelService.insertSfbdSecurityByBatch(list);
						n = 0;
						list = new ArrayList<Map<String,String>>();
					}else if(rowNum==sheet.getLastRowNum()){
						labelService.insertSfbdSecurityByBatch(list);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
