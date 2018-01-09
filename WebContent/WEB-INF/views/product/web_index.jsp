<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>Schneider Electric</title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<!-- 
	<meta http-equiv="X-UA-Compatible" content="IE=7" />
	 -->
	<!-- 
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	 -->
	<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon" />
	<link rel="stylesheet" type="text/css" href="${ctx }/static/styles/web.css"/>
	
	<style type="text/css">
		#helpTipDiv {
		    display: none;
		    width:456px;
		}
		.helpTip {
			width: 685px;
		}
	</style>
	
	<script src="${ctx }/static/jquery/jquery-1.9.1.min.js"></script>
	<script src="${ctx }/static/js-messages/messages_<fmt:message key="antifake.web.local" />.js"></script>
	<script src="${ctx }/static/js/antifake.js"></script>
	
	<!-- 提示插件 -->
	<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-tipsy/tipsy.css"/>
	<script src="${ctx }/static/jquery-tipsy/jquery.tipsy.js"></script>
	
	
	<script type="text/javascript">
		var baseUrl = "${ctx }";
		$(function(){
			//web 页面电话链接不显示下划线，并且IE 点击不弹出窗口
			$(".webNoUnderLine").css({"text-decoration":"none","cursor":"text"}).attr("href","#");
			
			//初始化显示下载页面
			$("#queryDiv").show();
			//显示
			$("#left_bottom").click(function(){
				$(".to_hidden").hide();
				$("#queryDiv").show();
			});
			//查询
			$("#submitId").click(function(){
				//校验 + 提交
				if(validateForm($(".validate"))) getProduct("web");
			});
			//重置
			$("#resetId").click(function(){
				$("input").val("");
			});
			//再来一个
			$(".more").click(function(){
				$(".empty").val("");
				$(".to_hidden").hide();
				$("#queryDiv").show();
				$('.anotherImage').click();
			});
			//改过源码 max-width
			$('#helpId').tipsy({
				trigger: 'click',
				gravity: 'w',
				html: true,
				title: function() { return "<img height='250px' src='${ctx }/static/images/help_get_<fmt:message key="antifake.web.local" />.jpg'>"; },
				delayIn: 100, 
				delayOut: 100				
			}); 
			//自检弹出
			$(".selfCheck").click(function(){
				window.open('${ctx }/static/html/SelfCheck_<fmt:message key="antifake.web.local" />.html','Help');
			});
			//疑似假货报告
			$(".report").click(function(){
				$("#RuserNameId").val($("#userNameId").val());
				$("#RphoneNoId").val($("#phoneNoId").val());
				$(".to_hidden").hide();
				$("#ReportDiv").show();
			});
			//提交疑似报告
			$("#submitReport").click(function(){
				//校验 + 提交
				if(validateReportForm($(".Rvalidate")))saveReportInfo("web");
			});
		});
	</script>
</head>
<body>

<!-- 下载页面 -->
<div id="main" class="to_hidden">
 <div id="left">
     <div id="left_top" align="center"><img src="${ctx }/static/images/webdl_title_<fmt:message key="antifake.web.local" />.png"></img></div>
	 <div id="left_center" align="center"><img src="${ctx }/static/images/webdl_pic_<fmt:message key="antifake.web.local" />.png"></img></div>
	 <div id="left_bottom" align="center"><img src="${ctx }/static/images/webdl_btn_enter_<fmt:message key="antifake.web.local" />.png"></img></div>
 </div>
 <div id="center"></div>
 <div id="right">
	<div id="right_left">
	 <div id="right_left_top" align="center"><img style="width:135px;height:120px;" src="${ctx }/static/images/webdl_ios_QR.png"></img></div>
	 <div id="right_left_center" align="center"><img src="${ctx }/static/images/webdl_btn_apple_<fmt:message key="antifake.web.local" />.png"></img></div>
	 <div id="right_left_bottom" align="center"><fmt:message key="antifake.main.ios.dowload"></fmt:message></div>
	</div>
	<div id="right_right">
	 <div id="right_right_top" align="center"><img style="width:135px;height:120px;" src="${ctx }/static/images/webdl_android_QR.png"></img></div>
	 <div id="right_right_center" align="center"><img src="${ctx }/static/images/webdl_btn_android_<fmt:message key="antifake.web.local" />.png"></img></div>
	 <div id="right_right_bottom" align="center"><fmt:message key="antifake.main.android.dowload"></fmt:message></div>
	</div>
 </div>
</div>
<!-- 查询页面 -->
<div id="queryDiv" class="to_hidden">
<h1><fmt:message key="antifake.web.title" /></h1>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<!-- 防 伪 码 -->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.form.lableNo" />:
				</label>
				<input id="lableNoId" class="empty validate" maxlength="12" type="text"/>
				<span class="required">*</span>
				<a id="helpId" href="#">
					<img style="vertical-align: middle;" src="${ctx }/static/images/question.gif" width="18" height="14"/> 
					<!-- 如何查看防伪码 -->
					<fmt:message key="antifake.form.help" /> 
				</a>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 客户姓名 -->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.form.userName"/>:
				</label>
				<input id="userNameId" class="validate" maxlength="32" type="text" />
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td >
				<!-- 手机号码 -->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.form.phoneNo"/>:
				</label>
				<input id="phoneNoId" class="validate"  maxlength="11" type="text" />
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td>
				<!-- 验 证 码 -->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.form.kaptcha"/>:
				</label>
				<!-- 上线时打开 class="empty validate" -->
				<input id="kaptchaId" class="empty validate" type="text" maxlength= "4"
					 onkeyup="value=this.value.replace(/\D+/g,'')"
					/>
				<span class="required">*</span>
				<span><img src="${ctx }/getCaptchaImage" id="kaptchaImageId" style="vertical-align:bottom;" width="55" height="20"/></span>
				<!-- 看不清楚，换一张 -->
				<a class="anotherImage" href="#" > <fmt:message key="antifake.form.anotherPic"/> </a>
			</td>
		</tr>
		<tr>
			<td align="left" style="padding-left:60px;" >
				<a href="#" id="submitId"><img src="${ctx }/static/images/btn_query_<fmt:message key="antifake.web.local" />.jpg" width="88" height="26" border="0" /></a>
				<a href="#" id="resetId"><img src="${ctx }/static/images/btn_reset_<fmt:message key="antifake.web.local" />.jpg" width="88" height="26" border="0" /></a>
			</td>
		</tr>
	</table>
</div>
<!-- 疑似假货报告页面 -->
<div id="ReportDiv" class="to_hidden">
<!-- 查询返回ID -->
<input id="queryLogId" type="hidden"/>
<input type="hidden" id="resultCount"></input>
<input type="hidden" id="queryTimes"></input>
<h1><fmt:message key="antifake.report.title" /></h1>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<!-- 客户姓名 -->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.userName"/>:
				</label>
				<input id="RuserNameId" class="Rvalidate" maxlength="32" type="text" />
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td >
				<!-- 联系电话 -->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.phoneNo"/>:
				</label>
				<input id="RphoneNoId" class="Rvalidate"  maxlength="11" type="text" />
				<span class="required">*</span>
			</td>
		</tr>
		
		<tr>
			<td>
				<!-- 购买地址-->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.buyaddress"/>:
				</label>
				<input id="buyaddress" class="Rvalidate empty"  maxlength="200" type="text" />
				<span class="required">*</span>
			</td>
		</tr>
		<tr>
			<td >
				<!-- 卖家名称-->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.salesName"/>:
				</label>
				<input id="salesName"  class="empty" type="text" maxlength="100"/>
				
			</td>
		</tr>		
		<tr>
			<td >
				<!-- 卖家电话-->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.salesPhone"/>:
				</label>
				<input id="salesPhone" type="text" class="Rvalidate empty" maxlength="50" />
				
			</td>
		</tr>		
		<tr>
			<td >
				<!-- 产品型号-->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.productLine"/>:
				</label>
				<input id="productLine" class="empty" type="text" maxlength="50" />
				
			</td>
		</tr>
		
		<tr>
			<td>
				<!-- 总金额-->
				<label class="labelClass_<fmt:message key="antifake.web.local" />">
					<fmt:message key="antifake.report.amount"/>:
				</label>
				<input id="amount"  type="text" class="Rvalidate empty" maxlength="50" />
				
			</td>
		</tr>		
		<tr>
			<td align="left" style="padding-left:60px;" >
				<a href="#" id="submitReport">
				<img src="${ctx }/static/images/btn_submit_<fmt:message key="antifake.web.local" />.png" width="88" height="26" border="0" />
				</a>
			
			</td>
		</tr>
	</table>
</div>	
<!-- 疑似假货报告提交完成页面 -->
<div id="report_success" class="to_hidden">
<h1><fmt:message key="antifake.report.title" /></h1>
	<!-- 
	<p>您所查询的防伪码<b> <span id="resultId1"></span></b><b class="green"> 存在，为第 1 次查询</b>。</p>
	 -->
	<fmt:message key="antifake.web.result.Rline1"/> 
	<fmt:message key="antifake.web.result.Rline2"/>
	<!-- 再查一个 -->
	<p><a href="#" class="more"><img class="resultMoreImg_<fmt:message key="antifake.web.local" />" src="${ctx }/static/images/btn_more_<fmt:message key="antifake.web.local" />.jpg" height="26" border="0" /></a>
	</p>
</div>
<!-- 结果页面 -->
<jsp:include page="./_web_result.jsp" flush="true" />
</body>
</html>
