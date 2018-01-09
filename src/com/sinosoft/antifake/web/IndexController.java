package com.sinosoft.antifake.web;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sinosoft.antifake.entity.ProductFamily;
import com.sinosoft.antifake.entity.ProductInfoFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springside.modules.utils.PropertiesLoader;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.gson.JsonParser;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * @author chenshaoao
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

	private static Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	private Producer captchaProducer = null;
	private String access_toket = "";
	private String jsapi_ticket = "";

	@Autowired
	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}

	// index 页面
	@RequestMapping(method = RequestMethod.GET)
	public String webIndex(
			@RequestParam(value = "sn", required = false) String sn,
			Model model, HttpServletRequest request) {
		/*// default
		// 默认跳转
		String indexPage = "product/antiFake_search";

		// 手机浏览器直接访问域名
		String requestUserAgent = request.getHeader("User-Agent");
		logger.info("User-Agent:" + requestUserAgent);
		// 自动判断跳转
		indexPage = this.getJumpUrl(requestUserAgent);
		// 扫描带sn 直接跳转到手机版
		if (sn != null) {
			// 不是数字直接报错
			Pattern pattern = Pattern.compile("[0-9]*");
			if (!pattern.matcher(sn).matches()) {
				model.addAttribute("noNumber", true);
				// 值: cn/en
				String lan = RequestContextUtils.getLocale(request).toString();
				// System.out.println("lan:"+lan);
				if (lan.contains("cn")) {
					sn = "非法防伪码:" + sn;
				} else {
					sn = "Invalid Security code:" + sn;
				}
			} else {
			}
			//indexPage = "product/mobile_index";
		}
		logger.info("是否扫描防伪码:" + sn);
		model.addAttribute("sn", sn);
		// initWxConfig(model, request);*/
		return "product/antiFake_search";
	}


	// index 页面 直接带防伪码参数
	@RequestMapping(value = "{sn}", method = RequestMethod.GET)
	public String webIndexHasn(@PathVariable("sn") String sn, Model model,
			HttpServletRequest request) {
		// default
		// 默认跳转
		String indexPage = "product/web_index";

		// 手机浏览器直接访问域名
		String requestUserAgent = request.getHeader("User-Agent");
		logger.info("User-Agent:" + requestUserAgent);
		// 自动判断跳转
		indexPage = this.getJumpUrl(requestUserAgent);
		// 扫描带sn 直接跳转到手机版
		if (sn != null) {
			// 不是数字直接报错
			Pattern pattern = Pattern.compile("[0-9]*");
			if (!pattern.matcher(sn).matches()) {
				model.addAttribute("noNumber", true);
				// 值: cn/en
				String lan = RequestContextUtils.getLocale(request).toString();
				// System.out.println("lan:"+lan);
				if (lan.contains("cn")) {
					sn = "非法防伪码:" + sn;
				} else {
					sn = "Invalid Security code:" + sn;
				}
			} else {
			}
			indexPage = "product/mobile_index";
		}
		logger.info("是否扫描防伪码:" + sn);
		model.addAttribute("sn", sn);
		initWxConfig(model, request);
		return indexPage;
	}

	private void initWxConfig(Model model, HttpServletRequest request) {

		PropertiesLoader pro = new PropertiesLoader(
				"classpath:/application.properties");
		model.addAttribute("AppId", pro.getProperty("AppId"));
		String url = request.getRequestURL().toString();
		if (request.getQueryString() != null)
			url = url + "?" + request.getQueryString();
		String access_session_value = (String) request.getSession()
				.getAttribute("wx_js_access_token");
		if (null != access_session_value) {
			access_toket = access_session_value;
		} else {
			access_toket = getTicket();
			request.getSession().setAttribute("wx_js_access_token",
					access_toket);
		}

		String jsapi_session_value = (String) request.getSession()
				.getAttribute("wx_js_api_token");
		if (null != jsapi_session_value) {
			jsapi_ticket = jsapi_session_value;
		} else {
			jsapi_ticket = getJSApiTicket(request);
			request.getSession().setAttribute("wx_js_api_token", jsapi_ticket);
		}

		Map<String, String> map = sign(url);

		model.addAttribute("timestamp", map.get("timestamp"));
		model.addAttribute("nonceStr", map.get("nonceStr"));
		model.addAttribute("signature", map.get("signature"));
		model.addAttribute("jsApiList", "'scanQRCode'");

		logger.debug("生成的URL:" + url);
		logger.debug("生成的JSAPI:" + jsapi_ticket);
		logger.debug("生成的nonceStr:" + map.get("nonceStr"));
		logger.debug("生成的timestamp:" + map.get("timestamp"));
		logger.debug("生成的signature:" + map.get("signature"));

	}

	private String getJSApiTicket(HttpServletRequest request) {

		String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token="
				+ access_toket + "";

		String result = "";

		String tmp = sendGet(url);
		if (!new JsonParser().parse(tmp).getAsJsonObject().has("ticket")) {
			access_toket = getTicket();
			request.getSession().setAttribute("wx_js_access_token",
					access_toket);
			url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token="
					+ access_toket + "";

			tmp = sendGet(url);
		}
		if (new JsonParser().parse(tmp).getAsJsonObject().has("ticket"))
			result = new JsonParser().parse(tmp).getAsJsonObject()
					.get("ticket").toString();

		return result.replace("\"", "");

	}

	private String sendGet(String requestUrl) {
		logger.debug("API URL:" + requestUrl);
		StringBuffer buffer = new StringBuffer();
		try {

			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			// 打开连接
			URL url = new URL(requestUrl);

			// 增加代理
			InetSocketAddress addr = new InetSocketAddress("125.35.57.17", 9480);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理

			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection(proxy);
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod("GET");

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			// jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			logger.error("Weixin server connection timed out.");
		} catch (Exception e) {
			logger.error("https request error:{}", e);
		}
		String result = buffer.toString();

		logger.debug("GET Result:" + result);

		return result;

	}

	private String getTicket() {

		PropertiesLoader pro = new PropertiesLoader(
				"classpath:/application.properties");
		// String url =
		// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
		// + pro.getProperty("AppId")
		// + "&secret="
		// + pro.getProperty("AppSecret");

		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="
				+ pro.getProperty("AppId") + "&corpsecret="
				+ pro.getProperty("AppSecret");

		String result = "";
		String tmp = sendGet(url);
		result = new JsonParser().parse(tmp).getAsJsonObject()
				.get("access_token").toString();
		return result.replace("\"", "");
	}

	private Map<String, String> sign(String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	// 中英文切换
	@RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeLanguage(
			@RequestParam(value = "lan") String lan,
			HttpServletRequest request, HttpServletResponse response) {
		LocaleResolver localeResolver = RequestContextUtils
				.getLocaleResolver(request);
		if (localeResolver == null) {
			throw new IllegalStateException(
					"No LocaleResolver found: not in a DispatcherServlet request?");
		}
		LocaleEditor localeEditor = new LocaleEditor();
		localeEditor.setAsText(lan);
		localeResolver.setLocale(request, response,
				(Locale) localeEditor.getValue());
		return new HashMap<String, Object>();
	}

	// 生成验证码
	@RequestMapping(value = "/getCaptchaImage", method = RequestMethod.GET)
	public void getCaptchaImage(
			@RequestParam(value = "captchaKey", required = false) String captchaKey,
			HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache,must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,
				capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}

	// 私有方法开始 =====================

	// 跳转判断
	private String getJumpUrl(String requestUserAgent) {

		String indexPage = "product/web_index";
		if (requestUserAgent.toLowerCase().indexOf("mobile") > 0) {
			indexPage = "product/mobile_index";
		} else {
			// 判断手机 和 PC
			UserAgent userAgent = UserAgent
					.parseUserAgentString(requestUserAgent);
			// 浏览器类型名称
			String webTypeName = userAgent.getBrowser().getBrowserType().name();
			// 1:mobile; 0:web
			int webTypeCode = userAgent.getBrowser().getBrowserType().ordinal();
			logger.info("浏览器类型名称:" + webTypeName);
			// logger.info("浏览器类型:"+webTypeCode);
			// webTypeCode = 1;
			// 跳转手机页面
			if (1 == webTypeCode) {
				indexPage = "product/mobile_index";
			}
		}
		return indexPage;
	}
}
