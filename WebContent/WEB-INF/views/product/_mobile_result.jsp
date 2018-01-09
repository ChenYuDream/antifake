<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- 一条记录 结果页面 -->
<div data-role="page"  id="result_1" data-dom-cache="true">
	<div data-role="header" data-theme="f" data-position="fixed">
		<!-- 查询结果 -->
		<h1><fmt:message key="antifake.mobile.title"/></h1>
	</div>
	<div class="box" data-role="content" align="left">
		<div data-role="fieldcontain" style="line-height:24px;" >
			<!-- 
			<p>您所查询的防伪码<b> <span id="resultId1"></span></b><b class="green"> 存在，为第 1 次查询</b>。</p>
			 -->
			<fmt:message key="antifake.mobile.result1.line1"/> 
			<!-- 建议您 -->
			<fmt:message key="antifake.result.proposal.part1"/> 
				<!-- 自检 -->
				<a class="selfCheck" href="#" style="color:green;"><fmt:message key="antifake.result.proposal.part2"/></a>
			<!-- 以获得更准确的结果 -->	
			<fmt:message key="antifake.result.proposal.part3"/>
			<!-- 再查一个 -->
			<button data-theme="f" value="<fmt:message key="antifake.result.more"/>" class="more"></button>
		</div>
		<div align="center" class="resultFooter">
			</div>
	</div>
</div>
<!-- 多条记录 结果页面 -->
<div data-role="page"  id="result_2" data-dom-cache="true">
	<div data-role="header" data-theme="f" data-position="fixed">
		<!-- 查询结果 -->
		<h1><fmt:message key="antifake.mobile.title"/></h1>
	</div>
	<div class="box" data-role="content" align="left">
		<div data-role="fieldcontain" style="line-height:24px">
			<!-- 
			<p>您所查询的防伪码<b> <span id="resultId2"></span></b><b class="green"> 存在</b>，已被查询<b><span id="resultQueryId"></span> </b>次。</p>
			 -->
			<fmt:message key="antifake.mobile.result2.line1"/>
			<!-- 
			<p>如有疑问，请拨打热线 <a href="tel:4008101315" style="color:green;">4008101315</a> 咨询。</p>
			 -->
			<fmt:message key="antifake.web.result.proposal"/>
			<fmt:message key="antifake.mobile.result1.line2"/>
			
			<button data-theme="a" value="<fmt:message key="antifake.result.fake"/>" class="report"></button>
			<!-- 再查一个 -->
			<button data-theme="f" value="<fmt:message key="antifake.result.more"/>" class="more"></button>
		</div>
		<div align="center" class="resultFooter">
		<%-- <!-- 建议您 -->
		<fmt:message key="antifake.result.proposal.part1"/> 
			<!-- 自检 -->
			<a class="selfCheck" href="#" style="color:green;"><fmt:message key="antifake.result.proposal.part2"/></a>
		<!-- 以获得更准确的结果 -->	
		<fmt:message key="antifake.result.proposal.part3"/> --%>
		</div>
	</div>
</div>
<!-- 无记录 结果页面 -->
<div data-role="page"  id="result_3" data-dom-cache="true">
	<div data-role="header" data-theme="f" data-position="fixed">
		<!-- 查询结果 -->
		<h1><fmt:message key="antifake.mobile.title"/></h1>
	</div>
	<div class="box" data-role="content" align="left">
		<div data-role="fieldcontain" style="line-height:24px">
			<!-- 
			<p>抱歉，您所查询的防伪码<b> <span id="resultId3"></span></b><b class="red"> 不存在 </b>，谨防假冒！</p>
			 -->
			<fmt:message key="antifake.mobile.result3.line1"/>
			<!-- 
			<p>如有疑问，请拨打热线 <a href="tel:4008101315" style="color:green;">4008101315</a> 协助维权。</p>
			 -->
			<fmt:message key="antifake.web.result.proposal"/>
			<fmt:message key="antifake.mobile.result1.line2"/>
			
			<button data-theme="a" value="<fmt:message key="antifake.result.fake"/>" class="report"></button>
			<!-- 再查一个 -->
			<button data-theme="f" value="<fmt:message key="antifake.result.more"/>" class="more"></button>
	  	</div>
		<div align="center" class="resultFooter">
	<%-- 	<!-- 建议您 -->
		<fmt:message key="antifake.result.proposal.part1"/> 
			<!-- 自检 -->
			<a class="selfCheck" href="#" style="color:green;"><fmt:message key="antifake.result.proposal.part2"/></a>
		<!-- 以获得更准确的结果 -->	
		<fmt:message key="antifake.result.proposal.part3"/> --%>
		</div>
	</div>
</div>
<!-- 自检页面 -->
<div data-role="page"  id="self_check" data-dom-cache="true">
	<div data-role="header" data-theme="f" data-position="fixed">
		<!-- 查询结果 
		<a href="#" class="ui-btn-left ui-btn ui-btn-icon-left ui-btn-corner-all ui-shadow ui-btn-down-f ui-btn-up-f" data-icon="arrow-l" data-theme="f">
		<span class="ui-btn-inner ui-btn-corner-all">
		<span class="ui-icon ui-icon-arrow-l ui-icon-shadow">
		</span>
		<span class="ui-btn-text">Back</span></span></a>
		-->
		<h1><fmt:message key="antifake.check.title"/></h1>
	</div>
	<div class="box" data-role="content" align="left">
		<div data-role="fieldcontain" style="line-height:24px">
			<a target="_blank" href="${ctx }/static/html/SelfCheck_<fmt:message key="antifake.web.local" />.html"><img id="selfCheckImg" src="${ctx }/static/images/help_check_<fmt:message key="antifake.web.local" />.jpg"/></a>
			<button data-theme="f" value="<fmt:message key="antifake.check.back"/>" id="selfCheckBack"></button>
	  	</div>
	</div>
</div>

<div data-role="page"  id="scan_confirm" data-dom-cache="true">
	<div data-role="header" data-theme="f" data-position="fixed">
		<!-- 查询结果 -->
		<h1><fmt:message key="antifake.mobile.title"/></h1>
	</div>
	<div class="box" data-role="content" align="left">
			<fmt:message key="antifake.result.scan_method"/>
			 <img height="278px"
						src='${ctx }/static/images/help_get_<fmt:message key="antifake.web.local" />.jpg' />
			 
			<fmt:message key="antifake.result.scan_example"/>	
			
			<button data-theme="a" value="<fmt:message key="antifake.result.rescan"/>" class="re_scan"></button>
			<button data-theme="f" value="<fmt:message key="antifake.result.scanok"/>" class="scan_ok"></button>
			
	  	
	</div>
</div>

<div data-role="page"  id="scan_ok" data-dom-cache="true">
	<div data-role="header" data-theme="f" data-position="fixed">
		<!-- 查询结果 -->
		<h1><fmt:message key="antifake.mobile.title"/></h1>
	</div>
	<div class="box" data-role="content" align="center">
		<div data-role="fieldcontain" style="line-height:24px">
			<fmt:message key="antifake.result.scan_fake"/>
			</br>
			<fmt:message key="antifake.result.scan_fake_1"/>
			</br></br></br>
			<button data-theme="a" value="<fmt:message key="antifake.result.fake"/>" class="report"></button>
			<!-- 再查一个 -->
			<button data-theme="f" value="<fmt:message key="antifake.result.more"/>" class="more"></button>
			
	  	</div>
		<div align="center" class="resultFooter">
		</div>
	</div>
</div>






