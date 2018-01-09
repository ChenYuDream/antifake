package com.sinosoft.antifake.helpers.excel.reportInfo;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import com.sinosoft.antifake.entity.ReportInfo;
import com.sinosoft.antifake.entity.rReportInfo;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;

public class ReportInfoBuilder {
	//表头文字
		private static List<String>  getTitle() {
			List<String> titles = new ArrayList<String>();
			titles.add("防伪码");	
			titles.add("联系电话");	
			titles.add("客户姓名");
			titles.add("购买地址");
			titles.add("IP地址");	
			titles.add("产品型号");
			titles.add("查询结果");
			titles.add("查询来源");	
			titles.add("查询时间");	
			titles.add("客户姓名");	
			titles.add("手机号码");
			//titles.add("GPS地址");	
			titles.add("商家名称");	
			titles.add("商家电话");	
			titles.add("产品型号");	
			titles.add("总金额");	
			titles.add("上报时间");	
			titles.add("上报类别");	
			titles.add("篡改内容");	
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
		public static void fillReport(HSSFSheet worksheet,List<rReportInfo> queryLogList) {
			//excel 从0 开始计数，第一行是表头
			int rowIndex = 1;
			for(rReportInfo log : queryLogList) {
				int n = 0;
				HSSFRow row = worksheet.createRow(rowIndex);
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getLabelNo()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getQueryClientPhone()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getQueryClientName()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getSalesAddress()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getIp()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getMaterialNo()));
				if(log.getQueryIsExist()!=null&&log.getQueryIsExist().equals("1")){
					if(Integer.parseInt(log.getQueryCount())>10){
						row.createCell(n++).setCellValue(new HSSFRichTextString("超过10次"));
					}else{
						row.createCell(n++).setCellValue(new HSSFRichTextString("存在"));
					}
				}else{
					row.createCell(n++).setCellValue(new HSSFRichTextString("不存在"));
				}
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getClient()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getCreateTime()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getClientName()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getClientPhone()));
				//row.createCell(n++).setCellValue( new HSSFRichTextString(log.getGpsAddress()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getSalesName()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getSalesPhone()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getProductType()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getAmount()));
				row.createCell(n++).setCellValue( new HSSFRichTextString(log.getCreateTime()));
				if(log.getReportType() != null&&!("").equals(log.getReportType())){
					if(log.getReportType().equals("01")){
						row.createCell(n++).setCellValue( new HSSFRichTextString("存在"));
					}else if(log.getReportType().equals("02")){
						row.createCell(n++).setCellValue( new HSSFRichTextString("不存在"));
					}else if(log.getReportType().equals("03")){
						row.createCell(n++).setCellValue( new HSSFRichTextString("超过10次"));
					}else{
						row.createCell(n++).setCellValue( new HSSFRichTextString("仿冒"));
					}
				}else{
					row.createCell(n++).setCellValue( new HSSFRichTextString(""));
				}
				if(log.getTamperContent()!=null&&!("").equals(log.getTamperContent())){
					if(("01").equals(log.getTamperContent())){
						row.createCell(n++).setCellValue( new HSSFRichTextString("扫描的防伪码不是12位数字"));
					}else if(("02").equals(log.getTamperContent())){
						row.createCell(n++).setCellValue( new HSSFRichTextString("扫描的网址不是施耐德官网"));
					}else{
						row.createCell(n++).setCellValue( new HSSFRichTextString("扫描的二维码已被仿冒"));
					}
				}else{
					row.createCell(n++).setCellValue( new HSSFRichTextString(""));
				}
				rowIndex ++;
			}
		}
}
