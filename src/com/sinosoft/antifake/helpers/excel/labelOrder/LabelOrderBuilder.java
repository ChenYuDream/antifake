package com.sinosoft.antifake.helpers.excel.labelOrder;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sinosoft.antifake.entity.LableOrder;
import com.sinosoft.antifake.helpers.excel.ExcelStyle;
import com.sinosoft.antifake.helpers.excel.ExcelUtils;
import com.sinosoft.antifake.ibatis.service.LabelService;

public class LabelOrderBuilder {
	
	//表头文字
	private static List<String>  getTitle() {
		List<String> titles = new ArrayList<String>();
		titles.add("订单号");	
		titles.add("规格");	
		titles.add("收货单位");	
		titles.add("订单数量");	
		titles.add("订单状态");
		titles.add("创建时间");	
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
	public static void fillReport(HSSFSheet worksheet,List<LableOrder> queryLogList) {
		//excel 从0 开始计数，第一行是表头
		int rowIndex = 1;
		for(LableOrder log : queryLogList) {
			HSSFRow row = worksheet.createRow(rowIndex);
			
			row.createCell(0).setCellValue( new HSSFRichTextString(log.getOrderNo()));
			row.createCell(1).setCellValue( new HSSFRichTextString(log.getLabelSize()));
			row.createCell(2).setCellValue( new HSSFRichTextString(log.getPlant()));
			row.createCell(3).setCellValue( new HSSFRichTextString(log.getCount()+""));
			row.createCell(4).setCellValue( new HSSFRichTextString(log.getStatus()));
			row.createCell(5).setCellValue( new HSSFRichTextString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(log.getCreateTime())));
			rowIndex ++;
		}
	}
	//导入数据
	public static void  importFromExcel2003(InputStream fis, LabelService labelService, String creater, String import_recipient){
		try {
			POIFSFileSystem pois = new POIFSFileSystem(fis);
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
					map.put("orderNo",  String.valueOf(ExcelUtils.getCellValue(hssfRow, 0)));
					map.put("labelSize",  String.valueOf(ExcelUtils.getCellValue(hssfRow, 1)));
					map.put("plantNo",  import_recipient.trim());
					map.put("count",  String.valueOf(ExcelUtils.getCellValue(hssfRow, 2)));
					map.put("creater", creater);
					map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
					list.add(map);
					if(n==100){
						labelService.insertLabelOrderByBatch(list);
						n = 0;
						list = new ArrayList<Map<String,String>>();
					}else if(rowNum==hssfSheet.getLastRowNum()){
						labelService.insertLabelOrderByBatch(list);
					}
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void importFromExcel2007(InputStream fis){
		try {
			POIFSFileSystem pois = new POIFSFileSystem(fis);
			/*HSSFWorkbook hssfWorkbook = new HSSFWorkbook(pois);
			for(int sheetNum=0;sheetNum<hssfWorkbook.getNumberOfSheets();sheetNum++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
				if(hssfSheet==null){
					continue;
				}
				for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					String row1 = ExcelUtils.getStringValue(hssfRow, 0);
					String row2 = ExcelUtils.getStringValue(hssfRow, 1);
					String row3 = ExcelUtils.getStringValue(hssfRow, 2);
					String row4 = ExcelUtils.getStringValue(hssfRow, 3);
					System.out.println("row:"+row1+","+row2+","+row3+","+row4);
				}
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
