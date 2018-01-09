package com.sinosoft.antifake.helpers.excel.recepitHis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import com.sinosoft.antifake.entity.LableSummary;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;

public class RecepitHisBuilder {
	
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("订单号");	
		titles.add("收货单位");	
		titles.add("快递公司");	
		titles.add("单号");	
		titles.add("标签规格");
		titles.add("卷号");	
		titles.add("数量");	
		titles.add("收货状态");	
		titles.add("备注");	
		titles.add("收货时间");	
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
	public static void fillReport(HSSFSheet worksheet,List<LableSummary> queryLogList) {
		//excel 从0 开始计数，第一行是表头
		int rowIndex = 1;
		for(LableSummary log : queryLogList) {
			HSSFRow row = worksheet.createRow(rowIndex);
			
			row.createCell(0).setCellValue( new HSSFRichTextString(log.getOrderNo()));
			row.createCell(1).setCellValue( new HSSFRichTextString(log.getPlant()));
			row.createCell(2).setCellValue( new HSSFRichTextString(log.getExpressCompany()));
			row.createCell(3).setCellValue( new HSSFRichTextString(log.getTrackingNo()));
			row.createCell(4).setCellValue( new HSSFRichTextString(log.getLabelSize()));
			row.createCell(5).setCellValue( new HSSFRichTextString(log.getReelNo()));
			row.createCell(6).setCellValue( new HSSFRichTextString((log.getCount()+"")));
			if(log.getReceiveStatus()!=null&&log.getReceiveStatus().equals("1")){
				row.createCell(7).setCellValue(new HSSFRichTextString("已确认收货"));
			}else if(log.getReceiveStatus()!=null&&log.getReceiveStatus().equals("0")){
				row.createCell(7).setCellValue(new HSSFRichTextString("拒签"));
			}else{
				row.createCell(7).setCellValue(new HSSFRichTextString("待收货"));
			}
			row.createCell(8).setCellValue( new HSSFRichTextString(log.getRemark()));
			row.createCell(9).setCellValue( new HSSFRichTextString(
					log.getReceiveTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getReceiveTime()):""));
			rowIndex ++;
		}
	}
	
}
