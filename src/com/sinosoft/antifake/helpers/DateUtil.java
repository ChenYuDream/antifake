package com.sinosoft.antifake.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {
	 public static Map<String, String> getFirstday_Lastday_Month(Date date) {
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        //calendar.add(Calendar.MONTH, -1);
	        Date theDate = calendar.getTime();
	        
	        //上个月第一天
	        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
	        gcLast.setTime(theDate);
	        gcLast.set(Calendar.DAY_OF_MONTH, 1);
	        String day_first = df.format(gcLast.getTime());
	        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
	        day_first = str.toString();

	        //上个月最后一天
	        calendar.add(Calendar.MONTH, 1);    //加一个月
	        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
	        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
	        String day_last = df.format(calendar.getTime());
	        StringBuffer endStr = new StringBuffer().append(day_last).append(" 23:59:59");
	        day_last = endStr.toString();

	        Map<String, String> map = new HashMap<String, String>();
	        map.put("first", day_first);
	        map.put("last", day_last);
	        return map;
	    }
}
