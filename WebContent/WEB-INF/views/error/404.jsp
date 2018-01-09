<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>404 - 页面不存在</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/styles/error.css">
	<script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="demo">
		<p><span>4</span><span>0</span><span>4</span></p>
		<p>该页面不存在，<span id="sec">5</span> 秒后返回首页</p>
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