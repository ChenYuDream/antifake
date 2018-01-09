package com.sinosoft.antifake.helpers.excel;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.util.Date;

public class ExcelUtils {
	public static Object getCellValue(HSSFRow row,int index){
			HSSFCell cell = row.getCell(index);
			if(cell!=null){
				if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
					return getStringValue(cell);
				}else if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						String value = DateFormatUtils.format(date, "yyyy-MM-dd");
						return value;
					}
					return getIntCellValue(cell);
				}else{
					return "";
				}
			}else{
				return "";
			}
	}
	public static Object getCellValue(XSSFRow row, int index){
		XSSFCell cell = row.getCell(index);
		if(cell!=null){
			if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
				return getStringValue(cell);
			}else if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					String value = DateFormatUtils.format(date, "yyyy-MM-dd");
					return value;
				}
				return getIntCellValue(cell);
			}else{
				return "";
			}
		}else{
			return "";
		}
	}
	public static int getIntCellValue(HSSFCell cell){
		int rtn = 0;
		try {
			rtn = (int)cell.getNumericCellValue();
		} catch (RuntimeException e) {
		}
		return rtn;
	}
	public static int getIntCellValue(XSSFCell cell){
		int rtn = 0;
		try {
			rtn = (int)cell.getNumericCellValue();
		} catch (RuntimeException e) {
		}
		return rtn;
	}
	public static String getStringValue(HSSFCell cell){
		String rtn = "";
		try {
			rtn = cell.getRichStringCellValue().getString();
		} catch (RuntimeException e) {
		}
		return rtn;
	}
	public static String getStringValue(XSSFCell cell){
		String rtn = "";
		try {
			rtn = cell.getRichStringCellValue().getString();
		} catch (RuntimeException e) {
		}
		return rtn;
	}
}
