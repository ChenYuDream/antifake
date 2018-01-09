package com.sinosoft.antifake.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，

 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/admin")
public class LoginController {

	//登录页面
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		
		return "login";
	}
	//提交登录
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(
			@RequestParam(value = "userName") String userName,
			@RequestParam(value = "password") String password,
			Model model,
			HttpServletRequest request ) {
		
		if("admin".equals(userName) && "passw0rd".equals(password)) {
			//放session 
			request.getSession().setAttribute("USER_NAME", "master");
			String session_id = (String)request.getSession().getAttribute("mail_recipient_no");
			if(null!=session_id){
				return "redirect:/admin/labelState/list";
			}
			return "redirect:/admin/productOrder/list";
		}else if("master".equals(userName) && "passw0rd".equals(password)){
			request.getSession().setAttribute("USER_NAME", "master");
			String session_id = (String)request.getSession().getAttribute("mail_report_Id");
			if(null!=session_id){
				return "redirect:/admin/reportInfo/list";
			}
			return "redirect:/admin/queryLog/list";
			
		} else{
			model.addAttribute("message", "用户名或密码错误");
			model.addAttribute("userName", userName);
			return "login";
		}
	}
	//注销
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout( HttpServletRequest request ) {
		
		request.getSession().removeAttribute("USER_NAME");
		return "redirect:/admin";
	}
}
