<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Schneider Electric</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 
	<link rel="icon" href="${ctx}/static/images/favicon.ico"/>
	 -->
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico"
	rel="shortcut icon" />
<link rel="stylesheet"
	href="${ctx }/static/jquery-mobile/jquery.mobile-1.3.1.min.css" />
<link rel="stylesheet" href="${ctx }/static/styles/jqm-demos.css" />
<link rel="stylesheet" href="${ctx }/static/styles/mobile.css" />

<script src="${ctx }/static/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctx }/static/jquery-mobile/jquery.mobile-1.3.1.min.js"></script>
<script
	src="${ctx }/static/js-messages/messages_<fmt:message key="antifake.web.local" />.js"></script>
<script src="${ctx }/static/js/antifake.js"></script>

<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"
	type="text/javascript"></script>

<script type="text/javascript">
	
	<c:if test="${noNumber!=null}">
		alert("该产品序列号不存在，请联系客服4008101315");
	</c:if>
	
		var baseUrl = "${ctx }";
		//手机设备初始化
		$(document).bind("mobileinit", function(){
		  $.support.touchOverflow = true; 
		  $.mobile.touchOverflowEnabled = true;
		  $.mobile.page.prototype.options.domCache = true;	//缓存
		});
		$(function(){
			//查询
			$("#submitId").click(function(){
				//校验 + 提交
				if(validateForm($(".validate"))) getProduct("mobile");
			});
			
			//查询
			$("#submits").click(function(){
				//校验 + 提交
				if(validateReportForm($(".validateS")))saveReportInfo("mobile");
			});
			
			
			//再来一个 
			$(".more").click(function(){
				$(".empty").val("");
				location.href = "#query";
				//防止重复提交2014-12-9
				$('#submitId').attr("disabled",false);
				
				$('.anotherImage').click();
			});
			//自检返回
			$("#selfCheckBack").click(function(){
				$(".empty").val("");
				history.go(-1);
			});
			//自检
			$(".selfCheck").click(function(){
				location.href = "#self_check";
			});
			
			//自检
			$(".report").click(function(){
				location.href = "#report";
				$("#RuserNameId").val($("#userNameId").val());
				$("#RphoneNoId").val($("#phoneNoId").val());
			});
			$("#to_query").click(function(){
				location.href = "#query";
				$('.anotherImage').click();
			});
			$(".re_scan").click(function(){
				location.href = "#query";				
				$('#submitId').attr("disabled",false);				
				$('.anotherImage').click();
				scaning();
			});
			$(".scan_ok").click(function(){
				location.href = "#scan_ok";			
				
			});
		});
		
		
		
		var bForcepc = fGetQuery ("dv") == "pc";
		function fBrowserRedirect(){
		    var sUserAgent = navigator.userAgent.toLowerCase();
		    var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
		    var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
		    //var bIsMidp = sUserAgent.match(/midp/i) == "midp";   
		   // var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
		    //var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
		    var bIsAndroid = sUserAgent.match(/android/i) == "android";
		   // var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
		    //var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
		    $("#index").show();
		    if(bIsIpad || bIsIphoneOs){
		        var sUrl = location.href;
		        if(!bForcepc){
		            document.getElementById("downlink_ios").href="https://itunes.apple.com/us/app/shi-nai-dian-qi-fang-wei-cha/id919955443?l=zh&ls=1&mt=8";
		        }
		        $("#index_content").show();
		        $(".ios_download").show();
		    }else
		    if(bIsAndroid){
		    	document.getElementById("downlink_android").href="http://antifake.schneider-electric.com/cn/app/antifake.apk";
		    	document.getElementById("downlink_android").target="_blank";
		    	$("#index_content").show();
		    	$(".android_download").show();
		    	$(".android_tip").show();
		    	 //document.getElementById("downlink").href="${ctx }/android/eoop_mt.apk";
		    }else{
		    	document.getElementById("downlink_android").href="http://antifake.schneider-electric.com/cn/app/antifake.apk";
		    	document.getElementById("downlink_android").target="_blank";
		    	document.getElementById("downlink_ios").href="https://itunes.apple.com/us/app/shi-nai-dian-qi-fang-wei-cha/id919955443?l=zh&ls=1&mt=8";
		    	$("#index_content").show();
		    	$(".ios_download").show();
		    	$(".android_download").show();
		    }
		    //if(bIsMidp||bIsUc7||bIsUc||bIsCE||bIsWM){   
		        //var sUrl  =  location .href;       
		        //if(!bForcepc){   
		            //window.location.href  =  "http://m.mail.163.com/" ;   
		        //}   
		   // }   
		}   
		function fGetQuery(name){//获取参数值   
		    var sUrl  = window.location.search.substr(1); 
		    var r  =  sUrl.match(new RegExp("(^|&)" + name + "=([^&]*)(&|$)"));
		    return (r  == null ? null : unescape(r[2]));
		}
		
		function check_url(str)
		{
			var urls=url_ok.split(';');
			var result=false;
			for(var i=0;i<urls.length;i++)
			{
				if(urls[i]=='')
					continue;
				if(str.indexOf(urls[i])!=-1)
				{
					result=true;
					break;
				}	
			}
			return result ;	
		}
		
		function scaning()
		{
			try	
			{
				
				
				var app_id=$('#hd_appid').val();
				var timestamp=$('#hd_timestamp').val();
				var nonceStr=$('#hd_nonceStr').val();
				var signature=$('#hd_signature').val();
			
					wx.scanQRCode({
		                desc: 'scanQRCode desc',
		                needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		                scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		                success: function (res) {
		                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
		                    var error=false
		                    
		                    if(!check_url(result))
		                    {
		                    	location.href = "#scan_confirm";
		                    }
		                    else
		                    {
			                    if(result.length>11)
			                    {
			                    	result=result.slice(-12);
			                    }
			                   $('#lableNoId').val(result);
		                   }
		                },
		                error: function (res) {
		                    if (res.errMsg.indexOf('function_not_exist') > 0) {
		                        $.alert("当前微信版本过低请升级", "提示");
		                    } else {
		                        $.alert("扫描产品码失败，请重试！", "提示");
		                    }
		                }
		            });
	            }
	            catch(e)
	            {
	            alert(e.message);
	            }			
		 }
		
		//2014-11-21 edit by kyle（正式环境注释掉）
	window.onload=fBrowserRedirect;
	</script>
<script type="text/javascript">
        wx.config({
        debug: false,
        appId: '${AppId}',
        timestamp: '${timestamp}',
        nonceStr: '${nonceStr}',
        signature: '${signature}',
        jsApiList: ['scanQRCode']
        });
        wx.ready(function(){
        	
        });
        wx.error(function(res){
        alert('Error:'+res.errMsg);
        });
</script>
<style>
.ios_download .android_download {
	display: none;
}
/* 	  .popup_dialog {
	border: none !important;
	min-width: 165px;
}

.popup_dialog .ui-title {
	font-size: 16px;
	color: #8D5D35 !important;
	text-shadow: none !important;
	margin-left: auto !important;
	margin-right: auto !important;
}

.popup_dialog .ui-body-c {
	font-size: 16px;
	color: #8D5D35 !important;
	text-shadow: none !important;
	font-weight: bold;
	text-align: center;
}

.popup_button {
	background-color: #8D5D35;
	border-radius: 0.2em 0.2em 0.2em 0.2em !important;
}

.popup_button .ui-btn-text {
	color: #FFFFFF; */
/* .ui-btn-up-a{background-color:#f9960b ;border-color:#dddddd ;color:#333333 ;text-shadow:0  1px  0  #f3f3f3 ;}
	.ui-btn-hover-a{background-color:#d15d0a ;border-color:#dddddd ;color:#333333 ;text-shadow:0  1px  0  #f3f3f3 ;} */
</style>
</head>
<body>
	<!-- 首页 -->
	<!-- 2011-11-21 edit by kyle（正式环境注释掉） -->
	<div data-role="page" id="index" data-dom-cache="true">
		<!-- 错误提示的时候，header 在中间 data-position="fixed" -->
		<div data-role="header" data-theme="f" data-position="fixed">
			<h1>
				<fmt:message key="antifake.mobile.title" />
			</h1>
			<a
				href="javascript:changeLanguage('<fmt:message key="antifake.mobile.changeLanCode" />')"
				data-icon="gear" class="ui-btn-right"><fmt:message
					key="antifake.mobile.changeLanName" /></a>
		</div>
		<div data-role="content" align="center"
			style="padding:0px;display:none" id="index_content">
			<div data-role="fieldcontain" style="padding:0px;margin-top:20px;">
				<table border="0">
					<tr>
						<td align="center"><img width="100px"
							src="${ctx }/static/images/appdl_logo.png" /></td>
					</tr>
					<tr>
						<td align="center" 	style="letter-spacing:3px;padding-top:1px;font-size:15px;color:#1ca265;font-weight:bold">
							<fmt:message key="antifake.index.title_new"></fmt:message>
						</td>
					</tr>
					<tr>
						<td align="center"  style="padding-bottom:0px;text-align:center"><img width="120px"
							src="${ctx }/static/images/qrcode_schneider-electriccn.png"></img>
							</td>
					</tr>
					<tr>
						<td align="center" style="padding-top:30px;color:#008040"><fmt:message
								key="antifake.index.scan_desc"></fmt:message></td>
					</tr>
					<tr>
						<td align="center"
							style="letter-spacing:2px;padding-top:10px;color:#008040;font-size:13px">
							<fmt:message key="antifake.index.scan_enter"></fmt:message>
						</td>
					</tr>
					<tr class="ios_download_del" style="display:none">
						<td align="center" style="padding-bottom:0px"><img
							width="120px" src="${ctx }/static/images/appdl_ios_QR.png">
						</img></td>
					</tr>
					<tr class="ios_download_del" style="display:none">
						<td width="80px" align="center"
							style="font-size:14px;padding-bottom:0px"><fmt:message
								key="antifake.main.ios.dowload"></fmt:message></td>
					</tr>
					<tr class="ios_download_del" style="display:none">
						<td align="center"><a id="downlink_ios"> <img
								width="200px"
								src="${ctx }/static/images/appdl_btn_apple_<fmt:message key="antifake.web.local" />.png">
							</img>
						</a></td>
					</tr>
					<tr class="android_download_del" style="display:none">
						<td align="center" style="padding-bottom:0px"><img
							width="120px" src="${ctx }/static/images/appdl_android_QR.png">
						</img></td>
					</tr>
					<tr class="android_download_del" style="display:none">
						<td width="80px" align="center"
							style="font-size:14px;padding-bottom:0px"><fmt:message
								key="antifake.main.android.dowload"></fmt:message></td>
					</tr>
					<tr class="android_download_dl ;" style="display:none">
						<td align="center"><a id="downlink_android"> <img
								width="200px"
								src="${ctx }/static/images/appdl_btn_android_<fmt:message key="antifake.web.local" />.png">
							</img>
						</a></td>
					</tr>
					<tr style="display:none">
						<td align="center" id="to_query"><img width="280px"
							src="${ctx }/static/images/appdl_btn_enter_<fmt:message key="antifake.web.local" />.png" />
						</td>
					</tr>
					<tr class="android_tip_del" style="display:none;">
						<td width="200px" align="left"
							style="font-size:14px;padding-bottom:0px;color:#26965C"><fmt:message
								key="antifake.index.androidTip" /> <fmt:message
								key="antifake.index.androidTipOne" /> <fmt:message
								key="antifake.index.androidTipTwo" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<!-- 查询页面 -->
	<div data-role="page" id="query" data-dom-cache="true">
		<!-- 错误提示的时候，header 在中间 data-position="fixed" -->
		<div data-role="header" data-theme="f" data-position="fixed">
			<h1>
				<fmt:message key="antifake.mobile.title" />
			</h1>
			<a
				href="javascript:changeLanguage('<fmt:message key="antifake.mobile.changeLanCode" />')"
				data-icon="gear" class="ui-btn-right"><fmt:message
					key="antifake.mobile.changeLanName" /></a>
		</div>
		<div data-role="content" align="center">
			<div data-role="fieldcontain">
				<input type="hidden" id="hd_appid" value="${AppId}" /> <input
					type="hidden" id="hd_timestamp" value="${timestamp}" /> <input
					type="hidden" id="hd_nonceStr" value="${nonceStr}" /> <input
					type="hidden" id="hd_signature" value="${signature}" />

				<table border="0">
					<tr>
						<!-- 防 伪 码 -->
						<td><b class="red" style="padding-bottom:1px;">*</b>
							<fmt:message key="antifake.form.lableNo" />：</td>
						<td style="padding-bottom:1px;text-align:left">
						<input id="lableNoId" style="width:120px"
							placeholder="<fmt:message key="antifake.form.required"/>"
							class="empty validate" type="text" maxlength="12" value="${sn}" />
						</td>
					   <td style="text-align:left;padding-bottom:1px;">
							<img style="cursor:pointer" src="${ctx }/static/images/scan_<fmt:message key="antifake.web.local"/>.png" onclick="scaning();"/>
						</td>
						
					</tr>
					<tr>
						<td></td>
						<td colspan="2"><a href="#popHelp" data-rel="popup"
							style="color:green; font-size:12px; line-height:20px; height:20px;">
								<img style="vertical-align: middle;"
								src="${ctx }/static/images/question.gif" width="18" height="14" />
								<!-- 如何查看防伪码 --> <fmt:message key="antifake.form.help" />
						</a>  <!--  弹出帮助页面 
						-->
							<div data-role="popup" id="popHelp" style="height:180px;">
								<a href="#" data-rel="back" data-role="button"
									data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>
								<img height="178px"
									src='${ctx }/static/images/help_get_<fmt:message key="antifake.web.local" />.jpg' />
							</div></td>
					</tr>
					<tr>
						<!-- 客户姓名 -->
						<td><b class="red">*</b> <fmt:message
								key="antifake.form.userName" />：</td>
						<td colspan="2"><input id="userNameId"
							placeholder="<fmt:message key="antifake.form.required"/>"
							class="validate" type="text" maxlength="32" /></td>
					</tr>
					<tr>
						<!-- 联系电话 -->
						<td><b class="red">*</b> <fmt:message
								key="antifake.form.phoneNo" />：</td>
						<td colspan="2"><input id="phoneNoId"
							placeholder="<fmt:message key="antifake.form.required"/>"
							class="validate" type="text" maxlength="11" value="" /></td>
					</tr>
					<tr>
						<!-- 验 证 码 -->
						<td width="40%"><b class="red">*</b> <fmt:message
								key="antifake.form.kaptcha" />：</td>
						<!-- type="number" maxlength 不好使 -->
						<td width="30%">
							<!-- 上线时打开 class="empty validate" --> <input id="kaptchaId"
							class="empty validate"
							placeholder="<fmt:message key="antifake.form.required"/>"
							type="number" maxlength="4" size="4" max="9999"
							onkeyup="value=this.value.replace(/\D+/g,'')" />
						</td>
						<td width="30%"><img class="anotherImage" id="kaptchaImageId"
							src="${ctx }/getCaptchaImage"
							style="vertical-align:middle; height:30px; width:60px;" /></td>
					</tr>
				</table>
				<!-- 查询 -->
				<button data-theme="f"
					value="<fmt:message key="antifake.form.submit"/>" id="submitId"></button>
			</div>
		</div>
	</div>

	<!-- 疑似假货报告页面 -->
	<div data-role="page" id="report" data-dom-cache="true">
		<!-- 查询返回ID -->
		<input id="queryLogId" type="hidden" /> <input type="hidden"
			id="resultCount"></input> <input type="hidden" id="queryTimes"></input>
		<div data-role="header" data-theme="f" data-position="fixed">

			<h1>
				<fmt:message key="antifake.report.title" />
			</h1>
		</div>
		<div data-role="content" align="center">
			<div data-role="fieldcontain">
				<table border="0" width="100%">
					<tr>
						<!-- 客户姓名 -->
						<td width="40%"><b class="red">*</b> <fmt:message
								key="antifake.report.userName" />：</td>
						<td width="60%" colspan="2"><input id="RuserNameId"
							placeholder="<fmt:message key="antifake.form.required"/>"
							class="validateS" type="text" maxlength="32" /></td>
					</tr>
					<tr>
						<!-- 联系电话 -->
						<td width="40%"><b class="red">*</b> <fmt:message
								key="antifake.report.phoneNo" />：</td>
						<td width="60%" colspan="2"><input id="RphoneNoId"
							placeholder="<fmt:message key="antifake.form.required"/>"
							class="validateS" type="text" maxlength="11" value="" /></td>
					</tr>
					<tr>
						<!-- 购买地址-->
						<td width="40%"><b class="red">*</b> <fmt:message
								key="antifake.report.buyaddress" />：</td>
						<td width="60%" colspan="2"><input id="buyaddress"
							placeholder="<fmt:message key="antifake.form.required"/>"
							class="validateS empty" type="text" maxlength="200" value="" /></td>
					</tr>
					<tr>
						<!-- 卖家名称-->
						<td width="40%"><fmt:message key="antifake.report.salesName" />：</td>
						<td width="60%" colspan="2"><input id="salesName"
							placeholder="<fmt:message key="antifake.report.required1"/>"
							maxlength="50" class="empty" type="text" value="" /></td>
					</tr>
					<tr>
						<!-- 卖家电话-->
						<td width="40%"><fmt:message key="antifake.report.salesPhone" />：</td>
						<td width="60%" colspan="2"><input id="salesPhone"
							placeholder="<fmt:message key="antifake.report.required2"/>"
							class="validateS empty" type="text" maxlength="50" value="" /></td>
					</tr>
					<tr>
						<!-- 产品型号-->
						<td width="40%"><fmt:message
								key="antifake.report.productLine" />：</td>
						<td width="60%" colspan="2"><input id="productLine"
							placeholder="<fmt:message key="antifake.report.required3"/>"
							class="empty" type="text" value="" maxlength="50" /></td>
					</tr>
					<tr>
						<!-- 总金额-->
						<td width="40%"><fmt:message key="antifake.report.amount" />：</td>
						<td width="60%" colspan="2"><input id="amount"
							placeholder="<fmt:message key="antifake.report.required4"/>"
							class="validateS empty" type="text" value="" maxlength="50" /></td>
					</tr>
				</table>
				<!-- 查询 -->
				<button data-theme="f"
					value="<fmt:message key="antifake.report.submit"/>" id="submits"></button>
			</div>
		</div>
	</div>



	<!-- 结果页面 -->
	<jsp:include page="./_mobile_result.jsp" flush="true" />
	<!-- 疑似报告结果页面 -->
	<jsp:include page="./_mobile_report_result.jsp" flush="true" />
</body>
</html>
