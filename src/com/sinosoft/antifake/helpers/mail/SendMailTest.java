package com.sinosoft.antifake.helpers.mail;

import org.springside.modules.utils.PropertiesLoader;

public class SendMailTest {
	public static void main(String[] args) {
			MailSenderInfo mail = new MailSenderInfo();
			mail.setMailServerHost("smtp.163.com");
			mail.setMailServerPort("25");
			mail.setValidate(true);
			mail.setUserName("wyfteam123@163.com");
			mail.setPassword("900728wyf");
			mail.setFromAddress("wyfteam123@163.com");
			mail.setToAddress("525458530@qq.com");
			
			PropertiesLoader pro = new PropertiesLoader("classpath:/mailinfo.properties");
			StringBuffer sb = new StringBuffer();
			sb.append(pro.getProperty("Content1"));
			sb.append(pro.getProperty("Content2"));
			sb.append(pro.getProperty("Content3").replaceAll("createtime", "2014-09-12 16:14:48.000"));
			sb.append(pro.getProperty("Content17"));
			mail.setSubject("A suspected counterfeit report is waiting for your check");
			mail.setContent(sb.toString());
			boolean result  = SimpleMailSender.sendHtmlMail(mail);
			System.out.println(result==true?"发送成功":"发送失败");
			
			
	}
}
