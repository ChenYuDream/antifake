package com.sinosoft.antifake.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import com.sinosoft.antifake.ibatis.model.Tblrecipient;
import com.sinosoft.antifake.ibatis.service.LabelService;

public class TXTUtil {
	
	static Properties pro = new Properties();
	/**
	 * 加载路径配置文件
	 */
	static{
		try {
			pro.load(TXTUtil.class.getResourceAsStream("/filePath.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertPo(LabelService labelService, HashMap<String, String> map){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		File dir = new File(pro.getProperty("PO"));
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isDirectory())
            {  
            	list = TXTUtil.readPoTxtFile(file[i].getName(),labelService,map);
            	//labelService.bacthPOInsert(list);
            }
        }
	}
	
	public static void insertMo(LabelService labelService, HashMap<String, String> map){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		File dir = new File(pro.getProperty("MO"));
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isDirectory())
            {  
            	list = TXTUtil.readMoTxtFile(file[i].getName(),labelService,map);
            	//labelService.bacthMOInsert(list);
            }
        }
	}
	
	@SuppressWarnings({ "finally", "unchecked" })
	public static List<Map<String,String>> readPoTxtFile(String filePath, LabelService labelService, HashMap<String, String> map2){
		//List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        try {
            String encoding="GBK";
            File file=new File(pro.getProperty("PO")+filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i = 0;
                while((lineTxt = bufferedReader.readLine()) != null){
                	i++;
                	if(i==1){
                		continue;
                	}
                	if(!StringUtil.isNull(lineTxt)){
                		String[] lineStrs = lineTxt.split("	");
                    	HashMap<String,String> map = new HashMap<String,String>();
                    	int n = 0;
                    	String plantCode = StringUtil.delZeroStartWidth(lineStrs[n++].trim());
                    	/*List<Tblrecipient> checkList  = new ArrayList<Tblrecipient>();
                    	checkList = labelService.getTblrecipientByAnd(
                    				new Tblrecipient(null, null, null, null, null, null, null, plantCode), null);*/
                    	if(!StringUtil.isNull(map2.get(plantCode))){
                    		map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
                    		map.put("plantNo", map2.get(plantCode));
                        	map.put("soNo", lineStrs[n++].trim());
                        	map.put("lineNo", StringUtil.delZeroStartWidth(lineStrs[n++].trim()));
                        	map.put("productionDate", lineStrs[n++].trim());
                        	map.put("materialNo", lineStrs[n++].trim());
                        	map.put("moNo", "");
                        	String count = lineStrs[n++].trim();
                        	map.put("count", count.substring(0, count.indexOf(".")));
                        	try{
                        		labelService.POInsert(map);
                        	}catch(Exception e){
                        		System.out.println("========POInserterror:"
                        					+filePath+",soNo:"+map.get("soNo")+",lineNo:"+map.get("lineNo"));
                        	}
                        	//list.add(map);
                    	}
                	}
                }
                //System.out.println(list);
                read.close();
                StringUtil.copyFile(pro.getProperty("PO")+filePath, pro.getProperty("PO_oldFile")+filePath);
                StringUtil.delFile(pro.getProperty("PO")+filePath);
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }finally{
        	 return null;
        }
     
    }
	
	@SuppressWarnings({ "finally", "unchecked" })
	public static List<Map<String,String>> readMoTxtFile(String filePath, LabelService labelService, HashMap<String, String> map2){
		//List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        try {
            String encoding="GBK";
            File file=new File(pro.getProperty("MO")+filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i = 0;
                while((lineTxt = bufferedReader.readLine()) != null){
                	i++;
                	if(i==1){
                		continue;
                	}
                	if(!StringUtil.isNull(lineTxt)){
                		String[] lineStrs = lineTxt.split("	");
                    	HashMap<String,String> map = new HashMap<String,String>();
                    	int n = 0;
                    	String plantCode = StringUtil.delZeroStartWidth(lineStrs[n++].trim());
                    	/*List<Tblrecipient> checkList  = new ArrayList<Tblrecipient>();
                    	checkList = labelService.getTblrecipientByAnd(
                    				new Tblrecipient(null, null, null, null, null, null, null, plantCode), null);*/
                    	if(!StringUtil.isNull(map2.get(plantCode))){
                    		map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
	                    	map.put("plantNo", map2.get(plantCode).toString());
	                    	map.put("moNo", StringUtil.delZeroStartWidth(lineStrs[n++].trim()));
	                    	map.put("productionDate", lineStrs[n++].trim());
	                    	map.put("materialNo", lineStrs[n++].trim());
	                    	String count = lineStrs[n++].trim();
                        	map.put("count", count.substring(0, count.indexOf(".")));
                        	map.put("soNo", "");
                        	map.put("lineNo", "");
                        	try{
                        		labelService.MOInsert(map);
                        	}catch(Exception e){
                        		System.out.println("========MOInserterror:"
                    					+filePath+",moNo:"+map.get("moNo"));
                        	}
	                    	//list.add(map);
                    	}
                	}
                }
                //System.out.println(list);
                read.close();
                StringUtil.copyFile(pro.getProperty("MO")+filePath, pro.getProperty("MO_oldFile")+filePath);
                StringUtil.delFile(pro.getProperty("MO")+filePath);
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }finally{
        	 return null;
        }
     
    }
	
	
	
	/*public static void main(String[] args) {
		File dir = new File(pro.getProperty("PO"));
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (!file[i].isDirectory())
            {  
            	readPoTxtFile(file[i].getName());
            	
            }
        }
	}*/
}
