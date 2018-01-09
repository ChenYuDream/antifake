<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!-- 
 -->
<div data-role="popup" id="popCheck">
	<a href="#" data-rel="back" data-role="button"  data-icon="delete" data-iconpos="notext" class="ui-btn-right">Close</a>	
	<img src="${ctx }/static/images/help_check_<fmt:message key="antifake.web.local" />.jpg"/>
</div>
