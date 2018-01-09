<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>标签状态统计</title>
	<link rel="stylesheet" type="text/css" href="${ctx }/static/bootstrap-datetimepicker/css/datetimepicker.css"/>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="${ctx }/static/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<script src="${ctx }/static/js/antifake.js"></script>
	<script src="${ctx }/static/js/lenovoInput.js"></script>
	
	<!-- 提示插件 -->
	<link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-tipsy/tipsy.css"/>
	<script src="${ctx }/static/jquery-tipsy/jquery.tipsy.js"></script>
	<script type="text/javascript">
		var baseUrl = "${ctx }";
		var oo;
		$(function(){
			//时间控件
		    /* $(".form_datetime").datetimepicker({
		    	format: 'yyyy-mm-dd hh:ii'
		    	,language: 'zh-CN'
		    	,autoclose: 1
		    }); */
			//重置
		    $("#reset_btn").on("click",function(){
		    	$("#formSearch input").val("");
		    	$("#export_inp").val("0");
		    	$("#search_flag option[value='']").attr("selected",true);
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
		    //设置警戒值
		    $("#warnValue_btn").on("click",function(){
				$('#edit_box').modal("show");
		    });
		    //关闭警戒值框
		    $("#edit_box_close").on("click",function(){
		    	$("#warn_value").val("");
		    	$("#recipientNo").val("");
				$('#edit_box').modal("hide");
		    });
		    //保存警戒值
		    $("#edit_box_save").on("click",function(){
		    	if(!(/^[1-9]\d*$/.test($("#warn_value").val()))){
		    		alert("警戒值必须为大于0的整数！");
		    	}else{
		    		saveWarnValue();
		    	}
		    });
		  //设置收货单位文本框联想功能
		    oo=new mSift('oo',20);
			var plantArray = new Array();
			<c:forEach items="${plantList}" var="plant">
			 	plantArray.push('${plant.recipient}');
		    </c:forEach>
			oo.Data = plantArray;
			oo.Create(document.getElementById('search_recipient'));
			//初始化下拉菜单
			var receiveStatus = '${param.search_flag}';
			$("#search_flag option").each(function(){
				if($(this).val()==receiveStatus){
					$(this).attr("selected",true);
				}
			});
		});
		 //设置单个警戒值
	    var showPlantWarnValue = function(recipientNo){
	    	$("#recipientNo").val(recipientNo);
	    	$.ajax({
	    		url:baseUrl+"/admin/labelState/getRecipientWarnValue",
	    		type:"post",
	    		data:{"recipientNo":recipientNo},
	    		success:function(data){
	    			$("#warn_value").val(data);
	    			$('#edit_box').modal("show");
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
			          <li class="active"><button class="btn btn-success" id="labelState_btn">标签状态统计</button></li>
			          <li class=""><button class="btn btn-success" id="labelScrap_btn">作废标签管理</button></li>
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
		<h1> 标签状态统计
		<!-- 
		<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
		 -->
		</h1>
	</div>
	<!-- 查询 不需要指定actin ？ -->
	 <form id="formSearch" class="form-search well" action="#" autocomplete="off">
		<input id="export_inp" name="export" type="hidden" value="0" />
		<div>
		<label>收货单位：</label> <input type="text" id="search_recipient" name="search_recipient" class="input-medium" value="${param.search_recipient}">
		<label>库存是否低于警戒值：</label>
		<select name="search_flag" id="search_flag">
			<option value="">全部</option>
			<option value="0">是</option>
			<option value="1">否</option>
		</select>
		<button type="submit" class="btn btn-primary" id="search_btn">搜索</button>
		<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
		</div>
		<br/>
    </form>
    <!-- Excel 导出 -->
    <div class="excelDiv">
		<button class="btn btn-success" id="excel_btn">导出Excel</button>
		<button class="btn btn-success" id="warnValue_btn">统一设置警戒值</button>
    </div>
    <br />
	<!-- 列表 -->
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>工厂</th>
				<th>已使用标签数</th>
				<th>库存数</th>
				<th>作废数</th>
				<th>库存是否低于警戒值</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${queryLogs}" var="log">
			<tr>
				<td>${log.recipient}</td>
				<td>${log.used}</td>
				<td>${log.inventory}</td>
				<td>${log.scrap}</td>
				<td>${log.flag}</td>
				<td><a href="javascript:showPlantWarnValue('${log.recipientNo}')">设置警戒值</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<!-- 设置警戒值DIV -->
	<div id="edit_box" class="modal hide fade">
		  <div class="modal-header">
		    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		    <h3>设置警戒值</h3>
		  </div>
		  <div class="modal-body">
		    <form class="form-horizontal" id="edit_box_from" enctype="multipart/form-data" method="post">
		      <input type="hidden" id=recipientNo name="recipientNo"/>
			  <div class="control-group">
			    <label class="control-label" for="userName">警戒值</label>
			    <div class="controls">
			      <input type="text" id="warn_value" name="warnValue"/>
			    </div>
			  </div>
			  </form>
		  </div>
		  <div class="modal-footer">
		    <a href="javascript:void(0);" class="btn" id="edit_box_close">关闭</a>
		    <a href="javascript:void(0);" class="btn btn-success" id="edit_box_save">保存</a>
		  </div>
	</div>
	<!-- 设置警戒值DIV -->
	<!-- 分页 -->
	<%-- <tags:pagination page="${queryLogs}" paginationSize="10"/> --%>
					</div>
			    </div>
			</div>
</body>
</html>
