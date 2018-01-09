package com.sinosoft.antifake.helpers.excel.labelScrap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import com.sinosoft.antifake.entity.LableScrap;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;

public class LabelScrapBuilder {
	
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("防伪码");
		titles.add("订单号");	
		titles.add("卷号");	
		titles.add("收货单位");	
		titles.add("报废标记");	
		titles.add("报废时间");
		titles.add("审核状态");	
		titles.add("审核时间");	
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
	public static void fillReport(HSSFSheet worksheet,List<LableScrap> queryLogList) {
		//excel 从0 开始计数，第一行是表头
		int rowIndex = 1;
		for(LableScrap log : queryLogList) {
			HSSFRow row = worksheet.createRow(rowIndex);
			
			row.createCell(0).setCellValue( new HSSFRichTextString(log.getSerialNumber()));
			row.createCell(1).setCellValue( new HSSFRichTextString(log.getOrderNo()));
			row.createCell(2).setCellValue( new HSSFRichTextString(log.getReelNo()));
			row.createCell(3).setCellValue( new HSSFRichTextString(log.getRecipient()));
			row.createCell(4).setCellValue( new HSSFRichTextString(log.getScrapType().equals("1")?"IQC报废":log.getScrapType().equals("2")?"贴错产品报废":"标签印制问题报废"));
			row.createCell(5).setCellValue( new HSSFRichTextString(log.getScrapTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getScrapTime()):""));
			row.createCell(6).setCellValue( new HSSFRichTextString(log.getStatus()!=null&&!("").equals(log.getStatus())?"已审核":"未审核"));
			row.createCell(7).setCellValue( new HSSFRichTextString(log.getApproveTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getApproveTime()):""));
			rowIndex ++;
		}
	}
	
}
