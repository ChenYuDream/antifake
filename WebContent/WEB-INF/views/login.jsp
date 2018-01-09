<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>登录页</title>
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js" type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			$("#loginForm").validate();
			$(".alert").fadeIn(1000).fadeOut(1000);
		});
	</script>
</head>

<body>
	<!-- 页头 -->
	<div id="header">
	    <h1> 登录 </h1>
	</div>
	<!-- 提示 -->
	<c:if test="${not empty message}">
		<div class="alert alert-error controls input-large">
			${message } <button class="close" data-dismiss="alert">×</button>
		</div>
	</c:if>
	<!-- 表单 -->
	<form id="loginForm" class="form-horizontal" action="${ctx}/admin/login" method="post">
		<div class="control-group">
			<label for="username" class="control-label">名称:</label>
			<div class="controls">
				<!-- 用户名 -->
				<input type="text" id="username" name="userName" value="${userName}" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				<!-- 密码 -->
				<input type="password" id="password" name="password" class="input-medium required"/>
			</div>
		</div>
		<div class="control-group">
			<div class="controls">
				<input id="submit_btn" class="btn btn-success" type="submit" value="登录"/>
			</div>
		</div>
	</form>
</body>
</html>
