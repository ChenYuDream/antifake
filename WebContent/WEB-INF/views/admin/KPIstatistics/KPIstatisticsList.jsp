<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>KPI统计</title>
	<link rel="stylesheet" type="text/css" href="${ctx }/static/bootstrap-datetimepicker/css/datetimepicker.css"/>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<script src="${ctx }/static/js/antifake.js"></script>
	
	<!-- 提示插件 -->
	<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-tipsy/tipsy.css"/>
	<script src="${ctx }/static/jquery-tipsy/jquery.tipsy.js"></script>
	<script type="text/javascript">
		var baseUrl = "${ctx }";
		$(function(){
			//时间控件
		   $(".form_datetime").datetimepicker({
			   startView: 3,
			   maxView:3,
			   minView:3,
		       format: 'yyyy-mm',
		       language: 'zh-CN',
		       autoclose: 1
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
			//详情导出
			$("#detail_excel_btn").on("click",function(){
		    	$("#detail_export_inp").val("1");	//设置为导出
		    	//$("#detail_formSearch").submit();	//执行导出
		    	//$("#detail_export_inp").val("0");	//设置回查询
		    });
		    
		    $("#dowload_box_close").on("click",function(){
		    	$("#detail_box").modal("hide");
		    	window.location.reload();
		    })
		    //详情搜索
		    $("#detail_search_btn").click(function(){
		    	var detail_productionDate = $("#detail_productionDate").val();
		    	if(detail_productionDate){
		    		var plant_no = $("#detail_plantNo_inp").val();
		    		showDetail(plant_no,detail_productionDate);
		    	}
		    });
		});
		
		var showDetail = function(plantNo,detail_productionDate){
			$("#detail_plantNo_inp").val(plantNo);
			$.ajax({
		        type: "GET",
		        url:  baseUrl + "/admin/kpi/showDetail.json",
		        data: {"plantNo":plantNo,"productionDate":detail_productionDate},
		        dataType:"json",
		        error: function(data, error) {alert(messageStrings["ajax.error"]);},
		        success: function(data) {
		        	$("#detail_box_tbody").html("");
		        	var innerHtml = "";
		        	$.each(data,function(i,obj){
		        		innerHtml += "<tr>"+
							"<td>"+obj.moNo+"</td>"+
							"<td>"+obj.collectsTagNum+"</td>"+
							"<td>"+obj.productOrderNum+"</td>"+
							"<td>"+obj.rightKpi+"</td>"+
							"<td>"+obj.hourNum+"</td>"+
							"<td>"+obj.uploadNum+"</td>"+
							"<td>"+obj.timeKpi+"</td>"+
							"<td>"+obj.productionDate+"</td>"+
							"<td><a href='javascript:void(0);' onclick="+
							'detailCount("'+obj.moNo+'","'+detail_productionDate+'")>统计</a></td>'+
							"</tr>";
		        	});
		        	$("#detail_box_tbody").html(innerHtml);
		        	$("#detail_box").modal("show");
		        }
		    });
		}
		
		var count = function(plantNo){
			$.ajax({
				url:baseUrl + '/admin/kpi/countPlantKpi',
				 type: "GET",
				 data: {"plantNo":plantNo},
			     error: function(data, error) {alert(messageStrings["ajax.error"]);},
			     success: function(data) {
			        window.location.reload();
			     }
			});
		}
		
		var detailCount = function(spcialNo,detail_productionDate){
			$.ajax({
				url:baseUrl + '/admin/kpi/countDetailKpi',
				 type: "GET",
				 data: {"spcialNo":spcialNo},
			     error: function(data, error) {alert(messageStrings["ajax.error"]);},
			     success: function(data) {
			    	 var plantNo = $("#detail_plantNo_inp").val();
			    	 showDetail(plantNo,detail_productionDate);
			     }
			});
		}
	</script>
</head>

<body>

<div class="row">
				<div class="span4 bs-docs-sidebar">
			        <ul class="nav nav-list bs-docs-sidenav affix">
			          <li class=""><button class="btn btn-success" id="productOrder_btn">产品订单管理</button></li>
			          <li class=""><button class="btn btn-success" id="labelOrder_btn">标签订单管理</button></li>
			          <li class=""><button class="btn btn-success" id="receiptHis_btn">收货历史查询 </button></li>
			          <li class=""><button class="btn btn-success" id="labelState_btn">标签状态统计</button></li>
			          <li class=""><button class="btn btn-success" id="labelScrap_btn">作废标签管理</button></li>
			          <li class="active"><button class="btn btn-success" id="kpi_btn">KPI统计</button></li>
			       	  <li class=""><button class="btn btn-success" id="sfbd_btn"> SFBD工厂防伪数据上传</button></li>
					  <li class=""><button class="btn btn-success" id="soa_btn">SOA防伪码查询</button></li>
					  <li class=""><button class="btn btn-success" id="export_btn">记录导出</button> </li>
			        </ul>
			    </div>
			    <div class="span12">
			    	<div id="content">
							<!-- 页头 -->
	<div id="header">
		<h1> KPI统计
		<!-- 
		<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
		 -->
		</h1>
	</div>
	<!-- 查询 不需要指定actin ？ -->
	 <form id="formSearch" class="form-search well" action="${ctx }/admin/kpi/list">
		<input id="export_inp" name="export" type="hidden" value="0" />
		
		<div>
		<label>统计月份：</label> <input type="text" name="search_productionDate" class="input-medium form_datetime" data-date-format="mm-yyyy" value="${param.search_productionDate}">
		<button type="submit" class="btn btn-primary" id="search_btn">搜索</button>
		<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
		</div>
		<br/>
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
				<th>工厂</th>
				<th>采集标签数</th>
				<th>订单产品总数</th>
				<th>准确性KPI</th>
				<th>24小时之内上传数</th>
				<th>上传总数</th>
				<th>及时性KPI</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${queryLogs}" var="log">
			<tr>
				<td><a href="javascript:void(0);" onclick="showDetail('${log.recipientNo}','${param.search_productionDate}')">${log.recipient}</a></td>
				<td>${log.collectsTagNum}</td>
				<td>${log.productOrderNum}</td>
				<td>${log.rightKpi}</td>
				<td>${log.hourNum}</td>
				<td>${log.uploadNum}</td>
				<td>${log.timeKpi}</td>
				<td><a href="javascript:void(0);" onclick="count('${log.recipientNo}')">统计</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<%-- <tags:pagination page="${queryLogs}" paginationSize="10"/> --%>
	<!-- alert div start -->
	<div id="detail_box" class="modal hide fade" style="width:760px;">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h3>KPI详情</h3>
		  </div>
		  <form id="detail_formSearch" class="form-search well" action="${ctx }/admin/kpi/list">
		  	<input id="detail_export_inp" name="detail_export" type="hidden" value="0" />
		  	<input id="detail_plantNo_inp" name="detail_plantNo" type="hidden" value="0" />
			<div>
			<label>统计月份：</label> <input type="text" name="search_detail_productionDate" id="detail_productionDate" class="input-medium form_datetime" data-date-format="mm-yyyy" value="${param.search_productionDate}">
			<button type="button" class="btn btn-primary" id="detail_search_btn">搜索</button>
			<button type="reset" class="btn btn-danger" id="detail_reset_btn">重置</button>
			<button style="float:right;" class="btn btn-success" id="detail_excel_btn">导出Excel</button>
			</div>
	      </form>
		  <div class="modal-body">
		    <table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>订单号</th>
						<th>采集标签数</th>
						<th>订单产品总数</th>
						<th>准确性KPI</th>
						<th>24小时之内上传数</th>
						<th>上传总数</th>
						<th>及时性KPI</th>
						<th>订单日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="detail_box_tbody">
				</tbody>
			</table>
		  </div>
		  <div class="modal-footer">
		    <a href="#" class="btn" id="dowload_box_close">关闭</a>
		  </div>
		</div>
		<!-- alert div end -->
					</div>
			    </div>
			</div>
</body>
</html>
