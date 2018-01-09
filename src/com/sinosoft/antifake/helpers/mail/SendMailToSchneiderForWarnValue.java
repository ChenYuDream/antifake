package com.sinosoft.antifake.helpers.mail;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.antifake.entity.QueryResult;
import com.sinosoft.antifake.entity.ReportInfo;

public class SendMailToSchneiderForWarnValue {
	static Logger log = LoggerFactory.getLogger(SendMailToSchneiderForWarnValue.class);
	static Properties pro = new Properties();
	static{
		try {
			pro.load(SendMailToSchneiderForWarnValue.class.getResourceAsStream("/mailinfoForWarn.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getProperty(String key){
		return pro.getProperty(key)==null?"mailinfo.properties缺少此参数："+key:pro.getProperty(key);
	}
	
    /**
     * 异步方式发送邮件
     * @param emailAddr
     * @param mailTitle
     * @param mailConcept
     * @param fileAttachment
     */
    public static void sendMailByAsynchronous(final String recipient) {

        new Thread(new Runnable() {
            public void run() {
                try {
                	send(recipient);
                } catch (Exception ex) {
                	log.error("recipient:"+recipient, ex);
                }
            }
        }).start();
    }
	
	public static void send(String recipient){
		MailSenderInfo mail = new MailSenderInfo();
		buildInfo(pro,mail,recipient);
		mail.setContent(buildContent(pro,recipient));
		boolean flag = SimpleMailSender.sendHtmlMail(mail);
		log.info(flag==true?"success":"failure");
		
	}
	
	private static void buildInfo(Properties pro,MailSenderInfo mail,String recipient){
		mail.setMailServerHost(getProperty("ServerHost"));
		mail.setMailServerPort(getProperty("ServerPort"));
		mail.setValidate(getProperty("Validate").equals("true")?true:false);
		mail.setUserName(getProperty("Username"));
		mail.setPassword(getProperty("Password"));
		mail.setFromAddress(getProperty("FromAddress"));
		mail.setToAddress(getProperty("ToAddress"));
		mail.setSubject(getProperty("Subject")+recipient);
	}
	
	private static String buildContent(Properties pro,String recipient){
		StringBuffer sb = new StringBuffer();
		sb.append(getProperty("Link").replaceAll("urlhref", getProperty("StaticUrl")));
		sb.append(getProperty("Content"));
		return sb.toString();
	}
}
