package com.sinosoft.antifake.helpers.excel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelWriter {

	private static Logger logger = LoggerFactory.getLogger(ExcelWriter.class);  
    
    public static void write(HttpServletResponse response, HSSFSheet worksheet) {   
          
  		OutputStream outputStream = null;
  		try {
  			//获取输出流
  			outputStream = response.getOutputStream();
  			//输出流
  			worksheet.getWorkbook().write(outputStream);
  			logger.info("excel 导出结束!");
  		} catch (IOException e) {
  			logger.error("excel 输出流异常!");
  			e.printStackTrace();
  		} catch (Exception e) {
  			e.printStackTrace();
  			logger.error("excel 生成异常!");
  		} finally{
  			try {
  				if( null != outputStream) {		//没有到输出流就报错了
  					outputStream.flush();
  					outputStream.close();
  					outputStream = null;
  				}
  			} catch (IOException e) {
  				logger.error("excel 关闭输出流异常!");
  				e.printStackTrace();
  			} 
  		}
        
    }  
}
