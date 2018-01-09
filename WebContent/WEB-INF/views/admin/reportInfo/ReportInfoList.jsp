<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>疑似假货报告管理</title>
	<script src="${ctx }/static/jquery/jquery-1.9.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx }/static/bootstrap-datetimepicker/css/datetimepicker.css"/>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<script src="${ctx }/static/js-messages/messages_cn.js"></script>
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
			//关闭编辑框
			$("#edit_box_close").on("click",function(){
				$("#edit_box_from input").val("");
				$('#edit_box').modal("hide");
			});
			$("#edit_box_save").on("click",function(){
				if(validateReportForm($(".Rvalidate"))){
					updateReportInfo();
				}
			});
		});
		function edit_model(report_id){
			$("#id").val(report_id);
			getReportInfo(report_id);
		}
	</script>
	 <style type="text/css">
	    #fortable{
	    	overflow:auto !important;
	    	width:100% !important;
	    }
	    #contentTable{
	    	width:4400px !important;
	    	max-width: 2400px !important;
	    }
    </style>
</head>

<body>
<div class="row">
				<div class="span3 bs-docs-sidebar">
			        <ul class="nav nav-list bs-docs-sidenav affix">
			          <li class=""><button class="btn btn-success" id="queryLog_btn">日志管理</button></li>
			          <li class="active"><button class="btn btn-success" id="reportinfo_btn">疑似假货报告管理</button></li>
			        </ul>
			    </div>
			    <div class="span12">
			    	<div id="content">
			    		<!-- 页头 -->
							<div id="header">
		<h1> 疑似假货报告管理
		<!-- 
		<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
		 -->
		</h1>
	</div>
	<!-- 查询 不需要指定actin ？ -->
	<form id="formSearch" class="form-search well" action="#" autocomplete="off">
		<input id="export_inp" name="export" type="hidden" value="0" />
		<div>
		<label>防&nbsp;&nbsp;伪&nbsp;&nbsp;码：</label> <input type="text" name="search_LIKE_labelNo" class="input-medium" value="${param.search_LIKE_labelNo}">
		<label>联系电话：</label> <input type="text" name="search_LIKE_clientPhone" class="input-medium" value="${param.search_LIKE_clientPhone}">
		<label>客户姓名：</label> <input type="text" name="search_LIKE_clientName" class="input-medium" value="${param.search_LIKE_clientName}">
		</div>
		<br/>
		<div>
		<label>开始时间：</label> <input type="text" name="search_GTE_createTime" class="input-medium form_datetime" value="${param.search_GTE_createTime}">
		<label>结束时间：</label> <input type="text" name="search_LTE_createTime" class="input-medium form_datetime" value="${param.search_LTE_createTime}">
		<button type="submit" class="btn btn-primary offset1" id="search_btn">搜索</button>
		<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
		</div>
    </form>
    <!-- Excel 导出 -->
    <div class="excelDiv">
		<button class="btn btn-success" id="excel_btn">导出Excel</button>
    </div>
    <br />
    <div id="fortable">
	<!-- 列表 -->
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-responsive">
		<thead>
			<tr>
				<th>防伪码</th>
				<th>联系电话</th>
				<th>客户姓名</th>
				<th>购买地址</th>
				<th>IP地址</th>
				<th>产品型号</th>
				<th>查询结果</th>
				<th>查询来源</th>
				<th>查询时间</th>
				<th>客户姓名</th>
				<th>手机号码</th>
				<!-- <th>GPS地址</th> -->
				<th>商家名称</th>
				<th>商家电话</th>
				<th>产品型号</th>
				<th>总金额</th>
				<th>上报时间</th>
				<th>上报类别</th>
				<th>篡改内容</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="rp">
			<tr>
				<td>${rp.labelNo}</td>
				<td>${rp.queryClientPhone}</td>
				<td>${rp.queryClientName}</td>
				<td>${rp.salesAddress}</td>
				<td>${rp.ip}</td>
				<td>${rp.materialNo}</td>
				<td>
					<c:if test="${rp.queryIsExist=='1'}">
						<c:if test="${rp.queryCount>10}">
						超过10次
						</c:if>
						<c:if test="${rp.queryCount<=10}">
						存在
						</c:if>
					</c:if>
					<c:if test="${rp.queryIsExist=='0'}">
						不存在
					</c:if>
				</td>
				<td>${rp.client}</td>
				<td>${rp.createTime}</td>
				<td>${rp.clientName}</td>
				<td>${rp.clientPhone}</td>
				<%-- <td>${rp.gpsAddress}</td> --%>
				<td>${rp.salesName}</td>
				<td>${rp.salesPhone}</td>
				<td>${rp.productType}</td>
				<td>${rp.amount}</td>
				<td>${rp.createTime}</td>
				<td>
					<c:if test="${not empty rp.reportType}">
						<c:if test="${rp.reportType=='01'}">
							存在
						</c:if>
						<c:if test="${rp.reportType=='02'}">
							不存在
						</c:if>
						<c:if test="${rp.reportType=='03'}">
							超过10次
						</c:if>
						<c:if test="${rp.reportType=='04'}">
							仿冒
						</c:if>
					</c:if>
					<%-- <c:if test="${empty rp.reportType}">
							仿冒
					</c:if> --%>
				</td>
				<td>
					<c:if test="${not empty rp.tamperContent}">
						<c:if test="${rp.tamperContent=='01'}">
							扫描的防伪码不是12位数字
						</c:if>
						<c:if test="${rp.tamperContent=='02'}">
							扫描的网址不是施耐德官网
						</c:if>
						<c:if test="${rp.tamperContent=='03'}">
							 扫描的二维码已被仿冒
						</c:if>
						<%-- <c:if test="${rp.tamperContent!='03'&&rp.tamperContent!='01'&&rp.tamperContent!='02'}">
						</c:if> --%>
					</c:if>
					<c:if test="${empty rp.tamperContent}">
					</c:if>
				</td>
				<%-- <td><a href="javascript:void(0);" onclick="edit_model('${rp.id}')">编辑</a></td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
	<!-- 分页 -->
	<tags:pagination page="${pagelist}" paginationSize="10"/>
		<!-- 编辑模态框 -->
	<div id="edit_box" class="modal hide fade">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	    <h3>疑似假货报告编辑</h3>
	  </div>
	  <div class="modal-body">
	    <form class="form-horizontal" id="edit_box_from">
	    	<input type="hidden" id="id"/>
		  <div class="control-group"><!-- 客户姓名 -->
		    <label class="control-label" for="userName">客户姓名</label>
		    <div class="controls">
		      <input type="text" class="Rvalidate" id="RuserNameId" placeholder="">
		    </div>
		  </div>
		  <div class="control-group"><!-- 客户电话 -->
		    <label class="control-label" for="phoneNo">手机号码</label>
		    <div class="controls">
		      <input type="text" class="Rvalidate" id="RphoneNoId" placeholder="">
		    </div>
		  </div>
		  <div class="control-group"><!-- 购买地址 -->
		    <label class="control-label" for="buyaddress">购买地址</label>
		    <div class="controls">
		      <input type="text" class="Rvalidate" id="buyaddress" placeholder="">
		    </div>
		  </div>
		  <div class="control-group"><!-- 卖家名称 -->
		    <label class="control-label" for="salesName">卖家名称</label>
		    <div class="controls">
		      <input type="text" id="salesName" placeholder="">
		    </div>
		  </div>
		  <div class="control-group"><!-- 买家电话 -->
		    <label class="control-label" for="salesPhone">买家电话</label>
		    <div class="controls">
		      <input type="text" class="Rvalidate" id="salesPhone" placeholder="">
		    </div>
		  </div>
		  <div class="control-group"><!-- 产品型号-->
		    <label class="control-label" for="productLine">产品型号</label>
		    <div class="controls">
		      <input type="text"  id="productLine" placeholder="">
		    </div>
		  </div>
		   <div class="control-group"><!-- 总金额-->
		    <label class="control-label" for="amount">总金额</label>
		    <div class="controls">
		      <input type="text" class="Rvalidate" id="amount" placeholder="">
		    </div>
		  </div>
		  <div class="control-group"><!-- 备注-->
		    <label class="control-label" for="comments">备注</label>
		    <div class="controls">
		      <textarea id="comments" rows="5" cols="10"></textarea>
		    </div>
		  </div>
		  <div class="control-group"><!-- 状态-->
		    <label class="control-label" for="comments">状态</label>
		    <div class="controls">
		      <select id="state">
		      	<option value="">请选择</option>
		      	 <c:forEach items="${statelist}" var="state">
		      	 	<option value="${state.val}">${state.key}</option>
		  		 </c:forEach>
		      </select>
		    </div>
		  </div>
	  </div>
	  <div class="modal-footer">
	    <a href="#" class="btn" id="edit_box_close">关闭</a>
	    <a href="#" class="btn btn-success" id="edit_box_save">保存</a>
	  </div>
	</div>
					</div>
			    </div>
			</div>
</body>
</html>
