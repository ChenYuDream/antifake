<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>日志管理</title>
	<link rel="stylesheet" type="text/css" href="${ctx }/static/bootstrap-datetimepicker/css/datetimepicker.css"/>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<script src="${ctx }/static/js-messages/messages_<fmt:message key="antifake.web.local" />.js"></script>
	<script src="${ctx }/static/js/antifake.js"></script>
	<!-- 提示插件 -->
	<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-tipsy/tipsy.css"/>
	<script src="${ctx }/static/jquery-tipsy/jquery.tipsy.js"></script>
	<script type="text/javascript">
		var baseUrl = "${ctx }";
		$(function(){
			//时间控件
		    $(".form_datetime").datetimepicker({
		    	format: 'yyyy-mm-dd hh:ii'
		    	,language: 'zh-CN'
		    	,autoclose: 1
		    });
			//重置
		    $("#reset_btn").on("click",function(){
		    	$("#formSearch input").val("");
		    	$("#export_inp").val("0");
		    	$("#formSearch").submit();
		    });
			//导出Excel
		    $("#excel_btn").on("click",function(){
		    	//列表记录数
		    	var pageCount = $(".pagination").attr("total-count");
		    	if(pageCount > 60000) {
		    		alert("导出记录大于60000，请通过查询条件过滤记录！");
		    		return false;
		    	}
		    	$("#export_inp").val("1");	//设置为导出
		    	$("#formSearch").submit();	//执行导出
		    	$("#export_inp").val("0");	//设置回查询
		    });
			
		  //初始化下拉菜单
			var search_client = '${param.search_EQ_client}';
			$("#search_client option").each(function(){
				if($(this).val()==search_client){
					$(this).attr("selected",true);
				}
			});
		});
	</script>
</head>

<body>

<div class="row">
				<div class="span4 bs-docs-sidebar">
			        <ul class="nav nav-list bs-docs-sidenav affix">
			          <li class="active"><button class="btn btn-success" id="queryLog_btn">日志管理</button></li>
			          <li class=""><button class="btn btn-success" id="reportinfo_btn">疑似假货报告管理</button></li>
			        </ul>
			    </div>
			    <div class="span12">
			    	<div id="content">
							<!-- 页头 -->
	<div id="header">
		<h1> 日志管理 
		<!-- 
		<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
		 -->
		</h1>
	</div>
	<!-- 查询 不需要指定actin ？ -->
	<form id="formSearch" class="form-search well" action="#">
		<input id="export_inp" name="export" type="hidden" value="0" />
		<div>
		<label>防&nbsp;&nbsp;伪&nbsp;&nbsp;码：</label> <input type="text" name="search_LIKE_lableNo" class="input-medium" value="${param.search_LIKE_lableNo}">
		<label>联系电话：</label> <input type="text" name="search_LIKE_phoneNo" class="input-medium" value="${param.search_LIKE_phoneNo}">
		<label>客户姓名：</label> <input type="text" name="search_LIKE_userName" class="input-medium" value="${param.search_LIKE_userName}">
		</div>
		<br/>
		<div>
		<label>开始时间：</label> <input type="text" name="search_GTE_queryTime" class="input-medium form_datetime" value="${param.search_GTE_queryTime}">
		<label>结束时间：</label> <input type="text" name="search_LTE_queryTime" class="input-medium form_datetime" value="${param.search_LTE_queryTime}">
		<label>查询来源：</label>
		<select name="search_EQ_client" id="search_client" style="width:100px;">
			<option value="">全部</option>
			<option value="app">app</option>
			<option value="web">web</option>
			<option value="mobile">mobile</option>
			<option value="notes">notes</option>
		</select>
		<button type="submit" class="btn btn-primary offset1" id="search_btn">搜索</button>
		<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
		</div>
		<br/>
		<div>
		</div>
    </form>
    <!-- Excel 导出 -->
    <div class="excelDiv">
		<button class="btn btn-success" id="excel_btn">导出Excel</button>
    </div>
    <br />
	<!-- 列表 -->
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>防伪码</th>
				<th>联系电话</th>
				<th>客户姓名</th>
				<th>IP地址</th>
				<th>产品型号</th>
				<th>查询次数</th>
				<th>查询结果</th>
				<th>是否存在</th>
				<th>查询来源</th>
				<th>查询时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${queryLogs.content}" var="log">
			<tr>
				<td>${log.lableNo}</td>
				<td>${log.phoneNo}</td>
				<td>${log.userName}</td>
				<td>${log.ip}</td>
				<td>${log.materialNo}</td>
				<td>${log.queryCount}</td>
				<td>
					<c:if test="${log.isExist=='1'}">
						<c:if test="${log.queryCount>10}">
						超过10次
						</c:if>
						<c:if test="${log.queryCount<=10}">
						存在
						</c:if>
					</c:if>
					<c:if test="${log.isExist=='0'}">
						不存在
					</c:if>
				</td>
				<td>${log.isExist=="1" ? "存在" : "不存在"}</td>
				<td>${log.client}</td>
				<td>${log.queryTime}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<tags:pagination page="${queryLogs}" paginationSize="10"/>
					</div>
			    </div>
			</div>
</body>
</html>
