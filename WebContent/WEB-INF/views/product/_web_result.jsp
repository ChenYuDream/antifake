<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- 一条记录 结果页面 -->
<div id="result_1" class="to_hidden">
<h1><fmt:message key="antifake.web.title" /></h1>
	<!-- 
	<p>您所查询的防伪码<b> <span id="resultId1"></span></b><b class="green"> 存在，为第 1 次查询</b>。</p>
	 -->
	<fmt:message key="antifake.mobile.result1.line1"/>
	<fmt:message key="antifake.web.result.proposal"/> 
	<!-- 再查一个 -->
	<p><a href="#" class="more"><img class="resultMoreImg_<fmt:message key="antifake.web.local" />" src="${ctx }/static/images/btn_more_<fmt:message key="antifake.web.local" />.jpg" height="26" border="0" /></a>
	</p>
</div>	
<!-- 多条记录 结果页面 -->
<div id="result_2" class="to_hidden">
<h1><fmt:message key="antifake.web.title" /></h1>
	<!-- 
	<p>您所查询的防伪码<b> <span id="resultId2"></span></b><b class="green"> 存在</b>，已被查询<b><span id="resultQueryId"></span> </b>次。</p>
	 -->
	<fmt:message key="antifake.mobile.result2.line1"/>
	<!-- 
	<p>如有疑问，请拨打热线 <b class="green">4008101315</b> 咨询。</p>
	<p>如有疑问，请拨打热线 <a href="tel:4008101315" style="color:green;">4008101315</a> 咨询。</p>
	 -->
	<fmt:message key="antifake.web.result.proposal"/> 
	<fmt:message key="antifake.mobile.result1.line2"/>
	<p>
	<a href="#" class="report"><img class="resultReportImg_<fmt:message key="antifake.web.local" />" src="${ctx }/static/images/btn_report_<fmt:message key="antifake.web.local" />.png" height="26" border="0" /></a>
	<a href="#" class="more"><img class="resultMoreImg_<fmt:message key="antifake.web.local" />" src="${ctx }/static/images/btn_more_<fmt:message key="antifake.web.local" />.jpg" height="26" border="0" /></a>
	<!-- 疑似假货报告 -->
	</p>
</div>	
<!-- 无记录 结果页面 -->
<div id="result_3" class="to_hidden">
<h1><fmt:message key="antifake.web.title" /></h1>
	<!-- 
	<p>抱歉，您所查询的防伪码<b> <span id="resultId3"></span></b><b class="red"> 不存在 </b>，谨防假冒！</p>
	 -->
	<fmt:message key="antifake.mobile.result3.line1"/>
	<!-- 
	<p>如有疑问，请拨打热线 <b class="green">4008101315</b> 协助维权。</p>
	<p>如有疑问，请拨打热线 <a href="tel:4008101315" style="color:green;">4008101315</a> 协助维权。</p>
	 -->
	<fmt:message key="antifake.web.result.proposal"/>
	<fmt:message key="antifake.mobile.result1.line2"/>
	<p>
	<a href="#" class="report"><img class="resultReportImg_<fmt:message key="antifake.web.local" />" src="${ctx }/static/images/btn_report_<fmt:message key="antifake.web.local" />.png" height="26" border="0" /></a>
	<a href="#" class="more"><img class="resultMoreImg_<fmt:message key="antifake.web.local" />" src="${ctx }/static/images/btn_more_<fmt:message key="antifake.web.local" />.jpg" height="26" border="0" /></a>
	<!-- 疑似假货报告 -->
	</p>
</div>






