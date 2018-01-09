package com.sinosoft.antifake.helpers.excel.KPI;

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

public class KpiBuilder {
	
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("工厂");	
		titles.add("采集标签数");	
		titles.add("订单产品总数");	
		titles.add("准确性KPI");	
		titles.add("24小时之内上传数");
		titles.add("上传总数");
		titles.add("及时性KPI");
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
			row.createCell(1).setCellValue( new HSSFRichTextString(log.get("collectsTagNum")!=null?log.get("collectsTagNum").toString():""));
			row.createCell(2).setCellValue( new HSSFRichTextString(log.get("productOrderNum")!=null?log.get("productOrderNum").toString():""));
			row.createCell(3).setCellValue( new HSSFRichTextString(log.get("rightKpi")!=null?log.get("rightKpi").toString():""));
			row.createCell(4).setCellValue( new HSSFRichTextString(log.get("hourNum")!=null?log.get("hourNum").toString():""));
			row.createCell(5).setCellValue( new HSSFRichTextString(log.get("uploadNum")!=null?log.get("uploadNum").toString():""));
			row.createCell(6).setCellValue( new HSSFRichTextString(log.get("timeKpi")!=null?log.get("timeKpi").toString():""));
			rowIndex ++;
		}
	}
	public static void buildDetailReport(HSSFSheet worksheet) {
		HSSFCellStyle titleStyle = ExcelStyle.getTitleStyle(worksheet, HSSFColor.GREEN.index);
		List<String> titleList = getDetailTitle();
		//第一行表头
        HSSFRow titleRow = worksheet.createRow(0);
		for (int i = 0; i < titleList.size(); i++) {
			worksheet.setColumnWidth(i, 20*255);
			HSSFCell titleCell = titleRow.createCell(i);
			titleCell.setCellStyle(titleStyle);
			titleCell.setCellValue(titleList.get(i));
		}
		
	}
	private static List<String> getDetailTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("订单号");	
		titles.add("采集标签数");	
		titles.add("订单产品总数");	
		titles.add("准确性KPI");	
		titles.add("24小时之内上传输");
		titles.add("上传总数");
		titles.add("及时性KPI");
		titles.add("订单日期");
		return titles;
	}
	public static void fillDetailReport(HSSFSheet worksheet,
			List<HashMap<String, Object>> labelStataList) {
		//excel 从0 开始计数，第一行是表头
				int rowIndex = 1;
				for(HashMap<String,Object> log : labelStataList) {
					HSSFRow row = worksheet.createRow(rowIndex);
					
					row.createCell(0).setCellValue( new HSSFRichTextString(log.get("moNo")!=null?log.get("moNo").toString():""));
					row.createCell(1).setCellValue( new HSSFRichTextString(log.get("collectsTagNum")!=null?log.get("collectsTagNum").toString():""));
					row.createCell(2).setCellValue( new HSSFRichTextString(log.get("productOrderNum")!=null?log.get("productOrderNum").toString():""));
					row.createCell(3).setCellValue( new HSSFRichTextString(log.get("rightKpi")!=null?log.get("rightKpi").toString():""));
					row.createCell(4).setCellValue( new HSSFRichTextString(log.get("hourNum")!=null?log.get("hourNum").toString():""));
					row.createCell(5).setCellValue( new HSSFRichTextString(log.get("uploadNum")!=null?log.get("uploadNum").toString():""));
					row.createCell(6).setCellValue( new HSSFRichTextString(log.get("timeKpi")!=null?log.get("timeKpi").toString():""));
					row.createCell(7).setCellValue( new HSSFRichTextString(log.get("productionDate")!=null?log.get("productionDate").toString():""));
					rowIndex ++;
				}
		
	}
	
}
