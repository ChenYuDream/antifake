<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
	<div data-role="page"  id="result_report" data-dom-cache="true">
			<div data-role="header" data-theme="f" data-position="fixed">
				<h1><fmt:message key="antifake.mobile.title"/></h1>
			</div>
			<div class="box" data-role="content" align="left">
				<div data-role="fieldcontain" style="line-height:24px;" >
						<fmt:message key="antifake.mobile.result.Rline1"></fmt:message>
					    <fmt:message key="antifake.mobile.result.Rline2"></fmt:message>
						<!-- 再查一个 -->
				<button data-theme="f" value="<fmt:message key="antifake.result.more"/>" class="more"></button>
				</div>
				<div align="center" class="resultFooter">
				</div>
			</div>
		</div>






