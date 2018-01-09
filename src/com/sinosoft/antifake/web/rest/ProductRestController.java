package com.sinosoft.antifake.web.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.antifake.entity.*;
import com.sinosoft.antifake.repository.OperationLogDao;
import com.sinosoft.antifake.service.SerialNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springside.modules.mapper.BeanMapper;
import org.springside.modules.mapper.JsonMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.IpHelper;
import com.sinosoft.antifake.helpers.mail.SendMailToSchneider;
import com.sinosoft.antifake.service.QueryLogService;
import com.sinosoft.antifake.service.ReportInfoService;

@Controller
@RequestMapping(value = "/products")
public class ProductRestController {

	private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

	@Autowired
	private QueryLogService queryLogService;

	@Autowired
	private ReportInfoService reportInfoService;

	@Autowired
	private SerialNumberService serialNumberService;

	@Autowired
	private OperationLogDao operationLogDao;

	// xml 监控接口
	@RequestMapping(value = "/monitor/{lableNo}.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public QueryResultDTO monitor(@PathVariable("lableNo") String lableNo, QueryLog queryLog, HttpServletRequest request) {
		QueryResult queryResult = queryLogService.getQueryResult(lableNo);
		return bindDTO(queryResult, request);
	}

	// xml 接口
	// 中文 username 先encode 一下
	@RequestMapping(value = "/{lableNo}.xml", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	@ResponseBody
	public QueryResultDTO getAsXml(@PathVariable("lableNo") String lableNo, QueryLog queryLog,
			HttpServletRequest request) {

		QueryResult queryResult = queryLogService.getQueryResult(lableNo);
		// 数据库连接异常，是否返回500.jsp
		saveQueryLog(request, queryResult, queryLog, lableNo);

		return bindDTO(queryResult, request);
	}

	// json 接口
	@RequestMapping(value = "/{lableNo}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public QueryResultDTO getAsJson(@PathVariable("lableNo") String lableNo, QueryLog queryLog, String kaptcha,
			HttpServletRequest request) {

		String code = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

		// if(true)
		// //下层异常抛出，显示500.jsp 界面
		// throw new RuntimeException("ss");

		// 不显示声明抛异常的类型，默认抛所有异常?
		// 直接调web 接口，kaptcha 为null
		// 上线时打开
		/**
		 */
		if ((null == kaptcha) || !kaptcha.equals(code)) {
			logger.info("验证码错误查询:" + lableNo + "使用验证码-服务器验证码:" + kaptcha + "-" + code);
			QueryResultDTO queryResultDTO = new QueryResultDTO();
			queryResultDTO.setResultCount("-1");
			return queryResultDTO;
		}
		logger.info("验证码正确查询:" + lableNo + "使用验证码-服务器验证码:" + kaptcha + "-" + code);
		QueryResult queryResult = queryLogService.getQueryResult(lableNo);

		// 保存日志报错，还是返回错误
		saveQueryLog(request, queryResult, queryLog, lableNo);

		return bindDTO(queryResult, request);
	}

	// json 接口
	@RequestMapping(value = "/query/{lableNo}.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public NewQueryResultDTO getQueryAsJson(@PathVariable("lableNo") String lableNo, QueryLog queryLog,
			HttpServletRequest request) {
		//2014-11-26 edit by kyle method of"getNewQueryResult" to "getQueryResult"
		QueryResult queryResult = queryLogService.getNewQueryResult(lableNo);
		NewQueryResult newQueryResult = new  Gson().fromJson(new Gson().toJson(queryResult), NewQueryResult.class);

		// 保存日志报错，还是返回错误
		saveNewQueryLog(request, newQueryResult, queryLog, lableNo);

		return bindDTO(newQueryResult, request);
	}

	// http://www.pigg.co/spring-mvc-cross-domain-garbled.html
	// 在非跨域请求中,它会默认使用MappingJackson2HttpMessageConverter将对象转成json串,而它使用的编码格式是utf-8,所以这里不会出现乱码问题.
	// 而当我们使用成jsonp后,由于返回对象是String对象,那么它将使用StringHttpMessageConverter进行转换,但这里的编码格式是ISO-8859-1,所以会出现乱码.
	private final static String CHARSET = ";charset=UTF-8";

	// jsonp 接口
	@RequestMapping(value = "/{lableNo}.jsonp", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
			+ CHARSET)
	@ResponseBody
	public String getAsJsonp(@PathVariable("lableNo") String lableNo, @RequestParam(value = "callback") String callback) {
		// return new String(jsonMapper.toJsonP(callback, new Object()).getBytes("UTF-8"),
		// Charset.forName("ISO-8859-1"));
		JsonMapper jsonMapper = new JsonMapper();
		// 实现Serializable 接口
		QueryResultDTO data = new QueryResultDTO();
		data.setQueryTimes("en");
		data.setResultCount("中文");

		return jsonMapper.toJsonP(callback, data);
	}

	// 私有方法开始---------------
	// 私有方法开始---------------
	// 私有方法开始---------------
	// 私有方法开始---------------

	// RESTful 对象属性绑定
	private QueryResultDTO bindDTO(QueryResult queryResult, HttpServletRequest request) {
		QueryResultDTO dto = BeanMapper.map(queryResult, QueryResultDTO.class);
		dto.setLan(RequestContextUtils.getLocale(request).toString()); // 补充Dozer不能自动绑定的语言属性
		return dto;
	}
	// RESTful 对象属性绑定 New
	private NewQueryResultDTO bindDTO(NewQueryResult queryResult, HttpServletRequest request) {
			NewQueryResultDTO dto = BeanMapper.map(queryResult, NewQueryResultDTO.class);
			dto.setLan(RequestContextUtils.getLocale(request).toString()); // 补充Dozer不能自动绑定的语言属性
			return dto;
	}

	// 保存查询日志
	private void saveQueryLog(HttpServletRequest request, QueryResult queryResult, QueryLog queryLog, String lableNo) {

		String curTime = DateHelper.getCurrentTime();

		String userName = queryLog.getUserName(); // 记录用户名
		try {
			// 中文必须，解决乱码问题
			if (null != userName) {
				queryLog.setUserName(java.net.URLDecoder.decode(userName, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 只自动获取web 请求的ip 为什么？
		//if ("web".equals(queryLog.getClient())) { // 记录客户端类别
			String ipString = IpHelper.parseIpString(request);
			queryLog.setIp(ipString); // 记录IP
		//}
		queryLog.setIsExist(queryResult.getResultCount() > 0 ? "1" : "0"); // 记录是否存在
		queryLog.setLableNo(lableNo); // 记录防伪码
		// 如果多个序列号，记录第一个
		List<QueryResultDetail> list = queryResult.getResultDetail();
		if (list.size() > 0) {
			queryLog.setMaterialNo(list.get(0).getMaterialNo()); // 记录产品型号
		}
		queryLog.setQueryCount(queryResult.getQueryTimes()); // 记录查询次数
		queryLog.setQueryTime(curTime); // 记录查询时间
		logger.info("记录日志 lableNo:" + lableNo + "客户端:" + queryLog.getClient() + "查询次数:"
				+ String.valueOf(queryResult.getQueryTimes()) + "手机:" + queryLog.getPhoneNo());
		queryLogService.saveQueryLog(queryLog);
		logger.info("返回查询记录ID："+queryLog.getId());
		//queryResult.setQueryId(queryLog.getId());
	}
	
	// 保存查询日志
	private void saveNewQueryLog(HttpServletRequest request, NewQueryResult queryResult, QueryLog queryLog, String lableNo) {

		String curTime = DateHelper.getCurrentTime();

		String userName = queryLog.getUserName(); // 记录用户名
		try {
			// 中文必须，解决乱码问题
			if (null != userName) {
				queryLog.setUserName(java.net.URLDecoder.decode(userName, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 只自动获取web 请求的ip 为什么？
		//if ("web".equals(queryLog.getClient())) { // 记录客户端类别
			String ipString = IpHelper.parseIpString(request);
			queryLog.setIp(ipString); // 记录IP
		//}
		queryLog.setIsExist(queryResult.getResultCount() > 0 ? "1" : "0"); // 记录是否存在
		queryLog.setLableNo(lableNo); // 记录防伪码
		// 如果多个序列号，记录第一个
		List<QueryResultDetail> list = queryResult.getResultDetail();
		if (list.size() > 0) {
			queryLog.setMaterialNo(list.get(0).getMaterialNo()); // 记录产品型号
		}
		queryLog.setQueryCount(queryResult.getQueryTimes()); // 记录查询次数
		queryLog.setQueryTime(curTime); // 记录查询时间
		logger.info("记录日志 lableNo:" + lableNo + "客户端:" + queryLog.getClient() + "查询次数:"
				+ String.valueOf(queryResult.getQueryTimes()) + "手机:" + queryLog.getPhoneNo());
		queryLogService.saveQueryLog(queryLog);
		logger.info("返回查询记录ID："+queryLog.getId());
		queryResult.setQueryId(queryLog.getId());
	}

	// json 接口
	@RequestMapping(value = "/saveReportInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private String saveReportInfo(HttpServletRequest request, ReportInfo reportInfo,
			/*@RequestParam(value = "callback", required = false) String callback, */HttpServletResponse response) {
		QueryResult queryResult = queryLogService.getNewQueryResult(reportInfo.getLabelNo());
		List<QueryResultDetail> list = queryResult.getResultDetail();
		if (list.size() > 0) {
			reportInfo.setMaterialNo(list.get(0).getMaterialNo()); // 记录产品型号
		}
		//reportInfo.setReportType(String.valueOf(queryResult.getResultCount()));
		reportInfo.setIp(IpHelper.parseIpString(request));
		reportInfo.setCreateTime(DateHelper.getCurrentTime());
//		QueryLog queryLog =  queryLogService.getQueryLogById(reportInfo.getQueryId());
		/*reportInfo.setQueryClientName(queryLog.getUserName()!=null?queryLog.getUserName():"");
		reportInfo.setQueryClientPhone(queryLog.getPhoneNo()!=null?queryLog.getPhoneNo():"");*/
		reportInfoService.saveReportInfo(reportInfo);
		Gson gson = new Gson();
		gson.toJson(reportInfo);
		SendMailToSchneider.sendMailByAsynchronous(reportInfo,queryResult);
		//JsonMapper jsonMapper = new JsonMapper();
		return this.ajaxJson(response, "{\"total\":\"" + 1 + "\",\"status\":" + "success" + "}");
	}

	/**
	 * output the text, and return null value.
	 * 
	 * @param response
	 * @param jsonString
	 * @return
	 */
	public String ajaxJson(HttpServletResponse response, String jsonString) {
		return ajax(response, jsonString, "text/html");
	}

	public String ajaxGJson(HttpServletResponse response, Object obj) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String jsonString = gson.toJson(obj);
		return ajax(response, jsonString, "text/html");
	}

	/**
	 * output the ajax, and return the null value.
	 * 
	 * @param response
	 * @param content
	 * @param type
	 * @return
	 */
	public String ajax(HttpServletResponse response, String content, String type) {
		try {
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return null;
	}
}
