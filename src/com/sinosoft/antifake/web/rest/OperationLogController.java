package com.sinosoft.antifake.web.rest;

import com.sinosoft.antifake.entity.OperationLog;
import com.sinosoft.antifake.entity.Tblserialnumber;
import com.sinosoft.antifake.service.OperationLogService;
import com.sinosoft.antifake.service.SerialNumberService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wj on 2017/12/24.
 * @author wj
 * 防伪数据查询
 * 防伪数据导出
 */
@Controller
@RequestMapping("operation/log")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SerialNumberService serialNumberService;
    @PersistenceContext
    protected EntityManager entityManager;

    @RequestMapping("search/page")
    public String toSearchPage(){
         return "admin/logSearch/antiFake_search";
    }

    @RequestMapping("export/page")
    public String toExportPage(){
        return "admin/logSearch/antiFake_export";
    }

    /**
     * 导出数据
     */
    @ResponseBody
    @RequestMapping("export")
    public void exportExcel(String startTime,String endTime,HttpServletRequest request, HttpServletResponse response){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = startTime+" 00:00:00";
        endTime = endTime + " 23:59:59";
        Date start = new Date();
        Date end = new Date();
        try {
            start = format.parse(startTime);
            end = format.parse(endTime);
        }catch (ParseException e){
            e.printStackTrace();
        }
        List<OperationLog> list = operationLogService.getByTime(start,end);
        createExcel(start,end,request,response,list);
    }

    /**
     * 根据防伪码查询商品
     * @return
     */
    @ResponseBody
    @RequestMapping("query")
    public Object queryAntiFakeNum(String lableNo){
        Map<String,Object> resultMap = new HashMap<String, Object>(16);
        try {
            resultMap = serialNumberService.selectOneById(lableNo);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code","-1");
            return resultMap;
        }

        return resultMap;
    }

    /**
     * 插入数据
     * @return
     */
    @ResponseBody
    @RequestMapping("insert")
    public Object insertData(OperationLog operationLog){
        Map<String,Object> resultMap = new HashMap<String, Object>(16);
        try {
            String lowUUID = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            operationLog.setId(lowUUID);
            operationLog.setCreateDate(new Timestamp(System.currentTimeMillis()));
            operationLogService.insertObject(operationLog);
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code","-1");
            return resultMap;
        }
        resultMap.put("code","0");
        return resultMap;
    }

    /**
     * 封装，导出excel
     * @param start
     * @param end
     * @param request
     * @param response
     */
    public void createExcel(Date start, Date end, HttpServletRequest request, HttpServletResponse response,List<OperationLog> list){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = format.format(start);
        String endTime = format.format(end);
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("antiFake_log_"+startTime+"-"+endTime);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        int rowNum = 0;
        HSSFRow row = sheet.createRow(rowNum);
        rowNum++;
        //设置样式
        HSSFPalette palette = wb.getCustomPalette();
        palette.setColorAtIndex((short)11, (byte) (68), (byte) (84), (byte) (106));
        //标题样式
        HSSFFont fontTitle=wb.createFont();
        fontTitle.setFontName("Arial");
        fontTitle.setFontHeightInPoints((short)10);
        fontTitle.setColor(HSSFColor.WHITE.index);
        fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFillForegroundColor((short)11);
        styleTitle.setFont(fontTitle);
        styleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //正文样式
        HSSFFont contentFont=wb.createFont();
        contentFont.setFontName("Arial");
        contentFont.setFontHeightInPoints((short)11);
        HSSFCellStyle contentStyle = wb.createCellStyle();
        contentStyle.setFont(contentFont);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 第四步，创建第一行所有的单元格
        String titleStr = "Label No,Customer,Customer Contact,Customer Extension,Province,City,Type,Product Line,Product Function,Product Family,Product Range,Remark,Value,ProdDate,Check,Fake,Plant,Company Name,Province,City,Supplier Type,Query Times,Created Date,Staff Name,Staff No";
        String titles[] = titleStr.split(",");
        //数组存单元格最大宽度，解决中文单元格太小问题
        int[] columnMaxLength = new int[titles.length];
        for(int i = 0;i<titles.length;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(styleTitle);
            columnMaxLength[i] = titles[i].getBytes().length;
        }
        // 第五步，导入数据
        try {

            for(OperationLog log:list){
                HSSFRow rowData = sheet.createRow(rowNum);
                rowNum++;
                //利用反射机制获取实体属性，不一个个赋值了
                Class<?> classs = Class.forName("com.sinosoft.antifake.entity.OperationLog");
                Field[] filed = classs.getDeclaredFields();
                //不要Id
                for (int i = 1; i<filed.length; i++) {
                    String fieldName = filed[i].getName();
                    //将属性的首字符大写，方便构造get，set方法
                    fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    String fieldType = filed[i].getGenericType().toString();
                    //获取实体get方法
                    Method m = log.getClass().getMethod("get" + fieldName);
                    // 调用getter方法获取属性值
                    String value;
                    if("class java.util.Date".equals(fieldType)){
                        Date dateValue = (Date) m.invoke(log);
                        if(dateValue == null){
                            value = "";
                        }else{
                            value = format2.format(dateValue);
                        }
                    }else if("class java.sql.Timestamp".equals(fieldType)){
                        Timestamp dateValue = (Timestamp) m.invoke(log);
                        if(dateValue == null){
                            value = "";
                        }else{
                            Date temp = new Date(dateValue.getTime());
                            value = format2.format(temp);
                        }
                    }else{
                        if(m.invoke(log) == null){
                            value = "";
                        }else{
                            value = ""+ m.invoke(log);
                        }
                    }
                    //写单元格（不要id，所以减了1）
                    HSSFCell cell = rowData.createCell(i-1);
                    cell.setCellStyle(contentStyle);
                    cell.setCellValue(value);
                    if(columnMaxLength[i-1]<value.getBytes().length){
                        columnMaxLength[i-1] = value.getBytes().length;
                    }
                }
            }//调整宽度
            for(int i= 0;i<columnMaxLength.length;i++){
                sheet.setColumnWidth(i,columnMaxLength[i]*2*240);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // 第六步，把excel流导出到response中，下载此文件
        OutputStream os = null;
        response.reset();
        response.setContentType("application/x-download;charset=UTF-8");
        try {
            //解决中文乱码
            String fileName = "antiFake_log_"+startTime+"-"+endTime+".xls";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            String UserAgent = request.getHeader("USER-AGENT").toLowerCase();
            //火狐乱码问题
            if (UserAgent.contains("firefox")){
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            os = response.getOutputStream();
            wb.write(os);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(os!=null){
                try {
                    os.flush();
                    os.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
