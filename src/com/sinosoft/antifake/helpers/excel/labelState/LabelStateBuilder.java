package com.sinosoft.antifake.helpers.excel.labelState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import com.sinosoft.antifake.entity.LableDetail;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;

public class LabelStateBuilder {
	
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("工厂");	
		titles.add("已使用标签数");	
		titles.add("库存数");	
		titles.add("作废数");	
		titles.add("库存是否低于警戒值");
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
	public static void fillReport(HSSFSheet worksheet,List<HashMap<String,Object>> queryLogList) {
		//excel 从0 开始计数，第一行是表头
		int rowIndex = 1;
		for(HashMap<String,Object> log : queryLogList) {
			HSSFRow row = worksheet.createRow(rowIndex);
			
			row.createCell(0).setCellValue( new HSSFRichTextString(log.get("recipient")!=null?log.get("recipient").toString():""));
			row.createCell(1).setCellValue( new HSSFRichTextString(log.get("used")!=null?log.get("used").toString():""));
			row.createCell(2).setCellValue( new HSSFRichTextString(log.get("inventory")!=null?log.get("inventory").toString():""));
			row.createCell(3).setCellValue( new HSSFRichTextString(log.get("scrap")!=null?log.get("scrap").toString():""));
			row.createCell(4).setCellValue( new HSSFRichTextString(log.get("state")!=null?log.get("state").toString():""));
			
			rowIndex ++;
		}
	}
	
}
