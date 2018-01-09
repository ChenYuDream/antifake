package com.sinosoft.antifake.helpers.excel.queryLog;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import com.sinosoft.antifake.entity.QueryLog;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;

public class QueryLogBuilder {
	
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("防伪码");	
		titles.add("联系电话");	
		titles.add("客户姓名");	
		titles.add("IP地址");	
		titles.add("产品型号");
		titles.add("查询次数");	
		titles.add("查询结果");	
		titles.add("是否存在");	
		titles.add("查询来源");	
		titles.add("查询时间");	
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
	public static void fillReport(HSSFSheet worksheet,List<QueryLog> queryLogList) {
		//excel 从0 开始计数，第一行是表头
		int rowIndex = 1;
		for(QueryLog log : queryLogList) {
			HSSFRow row = worksheet.createRow(rowIndex);
			row.createCell(0).setCellValue( new HSSFRichTextString(log.getLableNo()));
			row.createCell(1).setCellValue( new HSSFRichTextString(log.getPhoneNo()));
			row.createCell(2).setCellValue( new HSSFRichTextString(log.getUserName()));
			row.createCell(3).setCellValue( new HSSFRichTextString(log.getIp()));
			row.createCell(4).setCellValue( new HSSFRichTextString(log.getMaterialNo()));
			row.createCell(5).setCellValue( new HSSFRichTextString(""+log.getQueryCount()));
			if(log.getIsExist()!=null&&log.getIsExist().equals("1")){
				if(log.getQueryCount()>10){
					row.createCell(6).setCellValue(new HSSFRichTextString("超过10次"));
				}else{
					row.createCell(6).setCellValue(new HSSFRichTextString("存在"));
				}
			}else{
				row.createCell(6).setCellValue(new HSSFRichTextString("不存在"));
			}
			row.createCell(7).setCellValue( new HSSFRichTextString((log.getIsExist() != null && Integer.parseInt(log.getIsExist())>0)?"存在":"不存在"));
			row.createCell(8).setCellValue( new HSSFRichTextString(log.getClient()));
			row.createCell(9).setCellValue( new HSSFRichTextString(log.getQueryTime()));
			rowIndex ++;
		}
	}
	
}
