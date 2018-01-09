package com.sinosoft.antifake.helpers;

import javax.servlet.http.HttpServletRequest;

public class IpHelper {
	
	//获取客户端真实IP，排除代理问题
	public static String parseIpString(HttpServletRequest request) {		
		String ipString = request.getHeader("x-forwarded-for");
		if(ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
		    ipString = request.getHeader("Proxy-Client-IP");
		}
		if(ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
		    ipString = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipString == null || ipString.length() == 0 || "unknown".equalsIgnoreCase(ipString)) {
		    ipString = request.getRemoteAddr();
		}
		return ipString;
	}
}
