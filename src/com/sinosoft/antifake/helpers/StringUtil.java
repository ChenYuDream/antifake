package com.sinosoft.antifake.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * String辅助类
 * @author Administrator
 *
 */
public class StringUtil {
	/**
 * x/total获得如“0.00%”百分比的字符
 * @param x
 * @param total
 * @return
 */
public static String getPercent(int x,int total){
	BigDecimal dec_x = new BigDecimal(x);
	BigDecimal dec_total = new BigDecimal(total);
	BigDecimal dec_result = dec_x.divide(dec_total,4,BigDecimal.ROUND_HALF_UP);
	DecimalFormat df = new DecimalFormat("0.00%");
	return df.format(dec_result);  
}

public static String  getPercent(int x){
	DecimalFormat df = new DecimalFormat("0.00%");
	return df.format(new BigDecimal(x));
}

public static boolean isNull(Object object)
{
	if(null == object || "".equals(object))
	{
		return true;
	}
	return false;
}

public static String strToDateWithLine(String str){
	if(!isNull(str)){
		return str.substring(0, 4)+"-"+str.substring(4, 6)+"-"+str.substring(6, 8);
}else{
	return "";
	}
}

/**
 * 去0
 * @param val
 * @return
 */
public static String delZeroStartWidth(String val){
	if(val.startsWith("0")){
		val = val.substring(1, val.length());
		return delZeroStartWidth(val);
	}else{
		return val;
	}
}

 /** 
 * 删除文件 
 * @param filePathAndName String 文件路径及名称 如c:/fqf.txt 
 * @param fileContent String 
 * @return boolean 
 */ 
   public static void delFile(String filePathAndName) { 
       try { 
           String filePath = filePathAndName; 
           filePath = filePath.toString(); 
           java.io.File myDelFile = new java.io.File(filePath); 
           myDelFile.delete();

       } 
       catch (Exception e) { 
           System.out.println("删除文件操作出错"); 
           e.printStackTrace();

       }

   }
   
   /** 
* 复制单个文件 
* @param oldPath String 原文件路径 如：c:/fqf.txt 
* @param newPath String 复制后路径 如：f:/fqf.txt 
* @return boolean 
*/ 
@SuppressWarnings("resource")
public static void copyFile(String oldPath, String newPath) { 
      try { 
          int bytesum = 0; 
          int byteread = 0; 
          File oldfile = new File(oldPath); 
          if (oldfile.exists()) { //文件存在时 
		  InputStream inStream = new FileInputStream(oldPath); //读入原文件 
		  FileOutputStream fs = new FileOutputStream(newPath); 
		  byte[] buffer = new byte[1444]; 
		  while ( (byteread = inStream.read(buffer)) != -1) { 
		      bytesum += byteread; //字节数 文件大小 
		              System.out.println(bytesum); 
		              fs.write(buffer, 0, byteread); 
          } 
          inStream.close(); 
      } 
  } 
  catch (Exception e) { 
      System.out.println("复制单个文件操作出错"); 
          e.printStackTrace();

  }

}

/** 
 * 移动文件到指定目录 
 * @param oldPath String 如：c:/fqf.txt 
 * @param newPath String 如：d:/fqf.txt 
 */ 
public static void moveFile(String oldPath, String newPath) { 
	copyFile(oldPath, newPath); 
	delFile(oldPath);
}
}
