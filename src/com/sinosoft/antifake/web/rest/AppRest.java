package com.sinosoft.antifake.web.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sinosoft.antifake.entity.AppVO;
import com.sinosoft.antifake.entity.HResult;
import com.sinosoft.antifake.entity.VersionInfo;
import com.sinosoft.antifake.service.VersionService;

@RequestMapping("/app")
@Controller
public class AppRest {

//	private static final Logger logger = Logger.getLogger("AppRest");
	
//	@Autowired
//	private IAppService appService;
	
	/**
	 * 更新设备号
	 * @param appvo
	 * @param model
	 * @return
	 * @throws IOException
	 */
//	@RequestMapping(value = "/updateDevice",method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
//	public @ResponseBody Object updateDevice(@RequestBody AppVO appvo, Model model) throws IOException{
//		HResult result = new HResult(true,"");
//		boolean flag = appService.updateDevice(appvo.getId(), appvo.getDeviceType(), appvo.getDevice());
//		if(!flag){
//			result = new HResult(false,"更新设备号失败");
//		}
//        return result;
//	}
//	
	@Autowired
	private VersionService versionService;
	@RequestMapping(value="/findVersionInfo",method = RequestMethod.GET)
	@ResponseBody
	public HResult findVersionInfo(String type) throws IOException {
		AppVO appvo = new AppVO();
		appvo.setDeviceType(type);
		HResult r;
		if (!appvo.getDeviceType().equalsIgnoreCase("ios")
				&& !appvo.getDeviceType().equalsIgnoreCase("android")) {
			r=new HResult(false,"请求的客户端设备类型不正确");
			return r;
		}
		VersionInfo versionInfo = versionService.findVersionInfo(appvo.getDeviceType());
		r=new HResult(true,"请求成功",versionInfo);
		return r;
	}
}
