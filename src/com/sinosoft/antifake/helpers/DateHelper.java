/**
 * 
 */
package com.sinosoft.antifake.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @author chenshaoao
 *
 */
public class DateHelper {
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";	//生成文件时使用
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYYMM = "yyyyMM";
	public static final String YYYY0MM = "yyyy年MM月";
	
	//格式：2012-05-05 12:12:12
    public static String getCurrentTime() {
    	return getCurrentFormatTime(DateHelper.YYYY_MM_DD_HH_MM_SS);
    }
    //格式：2012-05-05
    public static String getCurrentDate() {
    	return getCurrentFormatTime(DateHelper.YYYY_MM_DD);
    }
    //格式：2012-05
    public static String getCurrentYearAndMonth() {
    	return getCurrentFormatTime(DateHelper.YYYY_MM);
    }
    //格式：自定义 日期格式，可以到日，可以到月 !!!
    public static String getCurrentFormatTime(String formatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(Calendar.getInstance().getTime());
    }
    //获得当前年
    public static String getCurYear()  {
    	return getSpecialYear(DateHelper.YYYY_MM_DD,DateHelper.getCurrentDate());
    }
	//获得当前月 带0
	public static String getCurMonth()  {
		return getSpecialMonth(DateHelper.YYYY_MM_DD,DateHelper.getCurrentDate());
	}
	//获取指定日期的年		指定格式和格式对应的日期
	public static String getSpecialYear(String formatStr,String specialDate)  {
		SimpleDateFormat specialFormat = new SimpleDateFormat(formatStr);   
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String year = "";
		try {
			year = dateFormat.format(specialFormat.parse(specialDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return year;
	}
	//获取指定日期的月		指定格式和格式对应的日期
	public static String getSpecialMonth(String formatStr,String specialDate)  {
		SimpleDateFormat specialFormat = new SimpleDateFormat(formatStr);   
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		String month = "";
		try {
			month = dateFormat.format(specialFormat.parse(specialDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month;
	}
	//获取指定日期的天		指定格式和格式对应的日期
	public static String getSpecialDay(String formatStr,String specialDate)  {
		SimpleDateFormat specialFormat = new SimpleDateFormat(formatStr);   
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		String day = "";
		try {
			day = dateFormat.format(specialFormat.parse(specialDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	//返回当前增加或减少后的		返回年-月		传人：指定格式 + 指定日期
	public static String getChangeMonth(String formatStr,String specialDate,int change)  {
		String yearAndMonth = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat specialformat = new SimpleDateFormat(formatStr);
		try {
			calendar.setTime(specialformat.parse(specialDate));
			calendar.add(Calendar.MONTH, change);
			yearAndMonth = specialformat.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return yearAndMonth;
	}
	//返回当前增加或减少后的		返回英文缩写月
	public static String getChangeMonthEn(String specialYearAndMonth,int change)  {
		Calendar calendar = Calendar.getInstance(); 
		SimpleDateFormat specialformat = new SimpleDateFormat(DateHelper.YYYY_MM);
		try {
			calendar.setTime(specialformat.parse(specialYearAndMonth));
			calendar.add(Calendar.MONTH, change);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String yearMonth = calendar.getTime().toString().substring(4,7);
		return yearMonth;
	}
	//判断参数时间是否在时间规则内（当前月前5个工作日）	传人时间格式:DateHelper.YYYY_MM_DD
	public static boolean validateContain(String specialDate) {
		List<String> dates = new ArrayList<String>(); 
		List<String> sunday = new ArrayList<String>();
	    Calendar cal = Calendar.getInstance();  
	    cal.set(Calendar.YEAR, Integer.parseInt(getCurYear()));  
	    cal.set(Calendar.MONTH,  Integer.parseInt(getCurMonth()) - 1);  
	    cal.set(Calendar.DATE, 1);  
	         
	    SimpleDateFormat df = new SimpleDateFormat(DateHelper.YYYY_MM_DD);
	    while(cal.get(Calendar.MONTH) < Integer.parseInt(getCurMonth())){  
	    	int day = cal.get(Calendar.DAY_OF_WEEK);  
		    if(dates.size()>4){
		    	break;
		    }
		    if((!(day == Calendar.SUNDAY || day == Calendar.SATURDAY))){  
		    	dates.add((df.format((Date)cal.getTime().clone())).toString());  
		    }else{
            	sunday.add((df.format((Date)cal.getTime().clone())).toString());
            }
	    	cal.add(Calendar.DATE, 1);  
	    }
	    if(!sunday.isEmpty())
    	   dates.addAll(sunday);
	    return dates.contains(specialDate);
	}
	//获取当前月前5个工作日
	//http://my.oschina.net/lovedreamland/blog/13120
	public static List<String> getFiveWorkDay() {
		List<String> dates = new ArrayList<String>();  
		List<String> sunday = new ArrayList<String>();
	    Calendar cal = Calendar.getInstance();  
	    cal.set(Calendar.YEAR, Integer.parseInt(getCurYear()));  
	    cal.set(Calendar.MONTH,  Integer.parseInt(getCurMonth()) - 1);  
	    cal.set(Calendar.DATE, 1);  
	         
	    SimpleDateFormat df = new SimpleDateFormat(DateHelper.YYYY_MM_DD);
	    while(cal.get(Calendar.MONTH) < Integer.parseInt(getCurMonth())){  
	    	int day = cal.get(Calendar.DAY_OF_WEEK);  
		    if(dates.size()>4){
		    	break;
		    }
		    if((!(day == Calendar.SUNDAY || day == Calendar.SATURDAY))){  
		    	dates.add((df.format((Date)cal.getTime().clone())).toString());  
		    }else{
            	sunday.add((df.format((Date)cal.getTime().clone())).toString());
            } 
	    	cal.add(Calendar.DATE, 1);  
	    }
	    if(!sunday.isEmpty())
	    	   dates.addAll(sunday);
	    return dates;
	}
	//得到当天之前几天的日期
	public static String getChangeDay(String formatStr,String specialDate,int change)  {
		String yearAndMonth = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat specialformat = new SimpleDateFormat(formatStr);
		try {
			calendar.setTime(specialformat.parse(specialDate));
			calendar.add(Calendar.DAY_OF_MONTH, change);
			yearAndMonth = specialformat.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return yearAndMonth;
	}
	//获得当前月的上一个月，2012 年1 月的上一个月 -- 2011 年12 月
	public static void main(String[] args) throws Exception {
//		System.out.println("getCurrentTime:"+getCurrentTime());
//		System.out.println("getCurrentDate:"+getCurrentDate());
//		System.out.println("getCurrentYearAndMonth:"+getCurrentYearAndMonth());
//		System.out.println("getCurrentFormatTime:"+getCurrentFormatTime(DateHelper.YYYY_MM));
//		System.out.println("getCurYear:"+getCurYear());
//		System.out.println("getCurMonth -- 0 :"+getCurMonth());
		System.out.println("---------"+getSpecialYear(DateHelper.YYYY_MM,DateHelper.getChangeMonth(DateHelper.YYYY_MM,DateHelper.getCurrentYearAndMonth(),-1)));
		System.out.println("getChangeMonth:"+getChangeMonth(DateHelper.YYYYMM,DateHelper.getCurrentFormatTime(DateHelper.YYYYMM),-1));
		System.out.println(DateHelper.getChangeMonth(DateHelper.YYYY_MM,DateHelper.getCurrentYearAndMonth(),-1));
//		System.out.println("getChangeMonthEn:"+getChangeMonthEn("2012-09",1));
	}
}
