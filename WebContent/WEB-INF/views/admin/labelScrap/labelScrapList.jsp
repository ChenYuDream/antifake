<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>作废标签管理  </title>
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
			//批量审批弹出
			$("#approve_btn").on("click",function(){
				$("#edit_box").modal("show");
			});
			//批量审批隐藏
			$("#edit_box_close").on("click",function(){
				$("#edit_box").modal("hide");
			});
			//批量审批上传
			$("#edit_box_save").on("click",function(){
				if($("#edit_box_from input").val()){
					$("#edit_box_from").attr("action",baseUrl+"/admin/labelScrap/batchApprove").submit();
				}
			});
		});
		function approve(id,status){
			if(!status){
				$("#input_serialNumber").val(id);
				if($("#input_serialNumber").val()){
					$("#appvoe_form").attr("action",baseUrl+"/admin/labelScrap/approve").submit();
				}
			}else{
				alert("已审核");
			}
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
			          <li class="active"><button class="btn btn-success" id="labelScrap_btn">作废标签管理</button></li>
			          <li class=""><button class="btn btn-success" id="kpi_btn">KPI统计</button></li>
			          <li class=""><button class="btn btn-success" id="sfbd_btn"> SFBD工厂防伪数据上传</button></li>		
					  <li class=""><button class="btn btn-success" id="soa_btn">SOA防伪码查询</button></li>
					  <li class=""><button class="btn btn-success" id="export_btn">记录导出</button> </li>
			        </ul>
			    </div>
			    <div class="span12">
			    	<div id="content">
							<!-- 页头 -->
	<div id="header">
		<h1> 作废标签管理 
		<!-- 
		<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
		 -->
		</h1>
	</div>
	<!-- 查询 不需要指定actin ？ -->
	<form id="formSearch" class="form-search well" action="#" autocomplete="off">
		<input id="export_inp" name="export" type="hidden" value="0" />
		<div>
		<label>防伪码：</label> <input type="text" name="search_LIKE_serialNumber" class="input-medium" value="${param.search_LIKE_serialNumber}">
<%-- 		<label>收货单位：</label> <input type="text" name="search_LIKE_recipient" class="input-medium" value="${param.search_LIKE_recipient}">
		<label>审核状态：</label> <input type="text" name="search_LIKE_reelNo" class="input-medium" value="${param.search_LIKE_reelNo}"> --%>
		<button type="submit" class="btn btn-primary" id="search_btn">搜索</button>
		<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
		</div>
		<br/>
    </form>
    <!-- Excel 导出 -->
    <div class="excelDiv">
    	<button class="btn btn-success" id="approve_btn">批量审批</button>
		<button class="btn btn-success" id="excel_btn">导出Excel</button>
    </div>
    <br />
	<!-- 列表 -->
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>防伪码</th>
				<th>订单号</th>
				<th>卷号</th>
				<th>收货单位</th>
				<th>报废标记</th>
				<th>报废时间</th>
				<th>审核状态</th>
				<th>审核时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${queryLogs.content}" var="log">
			<tr>
				<td>${log.serialNumber}</td>
				<td>${log.orderNo}</td>
				<td>${log.reelNo}</td>
				<td>${log.recipient}</td>
				<td>
				<c:if test="${log.scrapType=='1'}">
					IQC报废
				</c:if>
				<c:if test="${log.scrapType=='2'}">
					贴错产品报废
				</c:if>
				<c:if test="${log.scrapType=='3'}">
					标签印制问题报废
				</c:if>
				</td>
				<td><fmt:formatDate value="${log.scrapTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
				<c:if test="${log.status=='1'}">
					已审核
				</c:if>
				<c:if test="${log.status==''||log.status==null}">
					未审核
				</c:if>
				</td>
				<td><fmt:formatDate value="${log.approveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><a style="cursor:pointer;" onclick="approve('${log.serialNumber}','${log.status}')">审核</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<tags:pagination page="${queryLogs}" paginationSize="10"/>
		<div id="edit_box" class="modal hide fade">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h3>批量审批</h3>
		  </div>
		  <div class="modal-body">
		    <form class="form-horizontal" id="edit_box_from" enctype="multipart/form-data" method="post">
			  <div class="control-group">
			    <label class="control-label" for="userName"></label>
			    <div class="controls">
			      <input type="file"name="file"/>
			    </div>
			    <label class="control-label" for="downloadModel"></label>
			    <div class="controls">
			     <%--  <a href="${ctx }/static/uploadExcel/productOrder.xls">点击下载模板</a> --%>
			    </div>
			  </div>
			  </form>
			  <form method="post" id="appvoe_form" style="display:none">
			  		<input id="input_serialNumber" name="serialNumber" type="hidden"/>
			  </form>
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
