<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%response.setStatus(200);%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html>
<html>
<head>
	<title>500 - 系统内部错误</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/error.css">
	<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="demo">
		<p><span>5</span><span>0</span><span>0</span></p>
		<p>系统内部错误，<span id="sec">5</span> 秒后返回首页</p>
	</div>

	
	<script type="text/javascript">
	$(function () {            
	   setTimeout("lazyGo();", 1000);
	});
	function lazyGo() {
		var sec = $("#sec").text();
		$("#sec").text(--sec);
		if (sec > 0)
			setTimeout("lazyGo();", 1000);
		else
			window.location.href = "<c:url value="/admin"/>";
	}
	</script>
</body>
</html>
