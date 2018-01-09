package com.sinosoft.antifake.helpers.mail;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.antifake.entity.QueryResult;
import com.sinosoft.antifake.entity.ReportInfo;

public class SendMailToSchneider {
	static Logger log = LoggerFactory.getLogger(SendMailToSchneider.class);
	static Properties pro = new Properties();
	static{
		try {
			pro.load(SendMailToSchneider.class.getResourceAsStream("/mailinfo.properties"));
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
    public static void sendMailByAsynchronous(final ReportInfo reportInfo, final QueryResult queryResult) {

        new Thread(new Runnable() {
            public void run() {
                try {
                	send(reportInfo,queryResult);
                } catch (Exception ex) {
                	log.error("reportInfo:"+reportInfo+",queryResult:"+queryResult, ex);
                }
            }
        }).start();
    }
	
	public static void send(ReportInfo reportInfo, QueryResult queryResult){
		MailSenderInfo mail = new MailSenderInfo();
		buildInfo(pro,mail);
		mail.setContent(buildContent(pro,reportInfo,queryResult));
		boolean flag = SimpleMailSender.sendHtmlMail(mail);
		log.info(flag==true?"success":"failure");
		
	}
	
	private static void buildInfo(Properties pro,MailSenderInfo mail){
		mail.setMailServerHost(getProperty("ServerHost"));
		mail.setMailServerPort(getProperty("ServerPort"));
		mail.setValidate(getProperty("Validate").equals("true")?true:false);
		mail.setUserName(getProperty("Username"));
		mail.setPassword(getProperty("Password"));
		mail.setFromAddress(getProperty("FromAddress"));
		mail.setToAddress(getProperty("ToAddress"));
		mail.setSubject(getProperty("Subject"));
	}
	
	private static String buildContent(Properties pro,ReportInfo reportInfo, QueryResult queryResult){
		StringBuffer sb = new StringBuffer();
		sb.append(getProperty("Content1"));
		sb.append(getProperty("Content2"));
		sb.append(getProperty("Content3").replaceAll("createtime", reportInfo.getCreateTime()!=null?reportInfo.getCreateTime():""));
		sb.append(getProperty("Content4").replaceAll("client", reportInfo.getClient()!=null?reportInfo.getClient():""));
		String reportType = "";
		if(("02").equals(reportInfo.getReportType())){
			reportType = "不存在";
		}else if(("03").equals(reportInfo.getReportType())){
			reportType = "超过10次";
		}else if(("04").equals(reportInfo.getReportType())){
			reportType = "仿冒";
		}else if(("01").equals(reportInfo.getReportType())){
			reportType = "存在";
		}
		sb.append(getProperty("Content5").replaceAll("reporttype", reportType));
		sb.append(getProperty("Content6").replaceAll("labelno", reportInfo.getLabelNo()!=null?reportInfo.getLabelNo():""));
		sb.append(getProperty("Content7").replaceAll("producttype",  reportInfo.getMaterialNo()!=null?reportInfo.getMaterialNo():""));
		sb.append(getProperty("Content8").replaceAll("querytime", String.valueOf(queryResult.getQueryTimes())!=null?String.valueOf(queryResult.getQueryTimes()):""));
		sb.append(getProperty("ContentBr"));
		sb.append(getProperty("Content9").replaceAll("clientname", reportInfo.getClientName()!=null?reportInfo.getClientName():""));
		sb.append(getProperty("Content10").replaceAll("clientphone", reportInfo.getClientPhone()!=null?reportInfo.getClientPhone():""));
		sb.append(getProperty("Content11").replaceAll("salesaddress", reportInfo.getSalesAddress()!=null?reportInfo.getSalesAddress():""));
		//sb.append(getProperty("Content12").replaceAll("gpsaddress", reportInfo.getGpsAddress()!=null?reportInfo.getGpsAddress():""));
		sb.append(getProperty("Content13").replaceAll("salesname", reportInfo.getSalesName()!=null?reportInfo.getSalesName():""));
		sb.append(getProperty("Content14").replaceAll("salesphone", reportInfo.getSalesPhone()!=null?reportInfo.getSalesPhone():""));
		sb.append(getProperty("Content15").replaceAll("materialno",reportInfo.getProductType()!=null?reportInfo.getProductType():""));
		sb.append(getProperty("Content16").replaceAll("amount", reportInfo.getAmount()!=null?reportInfo.getAmount():""));
		sb.append(getProperty("Content17"));
		sb.append(getProperty("Content18"));
		sb.append(getProperty("Link").replaceAll("urlhref", getProperty("StaticUrl")+"?id="+reportInfo.getId()));
		sb.append(getProperty("Content19"));
		return sb.toString();
	}
}
