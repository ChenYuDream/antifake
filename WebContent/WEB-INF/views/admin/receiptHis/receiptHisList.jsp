<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String init = request.getAttribute("flag")!=null?request.getAttribute("flag").toString():"";
%>
<html>
<head>
	<title>收货历史查询 </title>
	<meta   http-equiv= "Pragma"   content= "no-cache" /> 
	<meta   http-equiv= "Cache-Control"   content= "no-cache" /> 
	<meta   http-equiv= "Expires"   content= "0" /> 
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
		    $(".form_datetime").datetimepicker({
		    	format: 'yyyy-mm-dd hh:ii'
		    	,language: 'zh-CN'
		    	,autoclose: 1
		    });
			//重置
		    $("#reset_btn").on("click",function(){
		    	$("#formSearch input").val("");
		    	$("#export_inp").val("0");
		    	$("#search_EQ_receiveStatus option[value='2']").attr("selected",true);
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
			//设置收货单位文本框联想功能
		    oo=new mSift('oo',20);
			var plantArray = new Array();
			<c:forEach items="${plantList}" var="plant">
			 	plantArray.push('${plant.recipient}');
		    </c:forEach>
			oo.Data = plantArray;
			oo.Create(document.getElementById('search_LIKE_recipient'));
			//初始化下拉菜单
			var receiveStatus = '';
			if('init'=='<%=init%>'){
				receiveStatus = '2';
			}else{
				receiveStatus = '${param.search_EQ_receiveStatus}';
			}
			$("#search_EQ_receiveStatus option").each(function(){
				if($(this).val()==receiveStatus){
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
			          <li class=""><button class="btn btn-success" id="productOrder_btn">产品订单管理</button></li>
			          <li class=""><button class="btn btn-success" id="labelOrder_btn">标签订单管理</button></li>
			          <li class="active"><button class="btn btn-success" id="receiptHis_btn">收货历史查询 </button></li>
			          <li class=""><button class="btn btn-success" id="labelState_btn">标签状态统计</button></li>
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
		<h1> 收货历史查询
		<!-- 
		<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
		 -->
		</h1>
	</div>
	<!-- 查询 不需要指定actin ？ -->
	<form id="formSearch" class="form-search well" action="#" autocomplete="off">
		<input id="export_inp" name="export" type="hidden" value="0" />
		<div>
		<label>订单号：</label> <input type="text" name="search_LIKE_orderNo" class="input-medium" value="${param.search_LIKE_orderNo}">
		<label>卷号：</label> <input type="text" name="search_LIKE_reelNo" class="input-medium" value="${param.search_LIKE_reelNo}">
		<label>收货单位：</label> <input type="text" id="search_LIKE_recipient" name="search_LIKE_recipient" class="input-medium" value="${param.search_LIKE_recipient}">
		</div>
		<br/>
		<div>
		<label>收货状态：</label> <select id="search_EQ_receiveStatus" name="search_EQ_receiveStatus">
									<option value="2">全部</option>
									<option value="-1">待收货</option>
									<option value="0">拒签</option>
									<option value="1">已收货</option>
							 </select>
		<label></label>
		<button type="submit" class="btn btn-primary" id="search_btn">搜索</button>
		<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
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
				<th>订单号</th>
				<th>收货单位</th>
				<th>快递公司</th>
				<th>单号</th>
				<th>标签规格</th>
				<th>卷号</th>
				<th>数量</th>
				<th>收货状态</th>
				<th>备注</th>
				<th>收货时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${queryLogs.content}" var="log">
			<tr>
				<td>${log.orderNo}</td>
				<td>${log.plant}</td>
				<td>${log.expressCompany}</td>
				<td>${log.trackingNo}</td>
				<td>${log.labelSize}</td>
				<td>${log.reelNo}</td>
				<td>${log.count}</td>
				<td>
					<c:if test="${log.receiveStatus=='1'}">
						已确认收货
					</c:if>
					<c:if test="${log.receiveStatus=='0'}">
						拒签
					</c:if>
					<c:if test="${log.receiveStatus==null||log.receiveStatus==''||log.receiveStatus=='-1'}">
						待收货
					</c:if>
				</td>
				<td>${log.remark}</td>
				<td><fmt:formatDate value="${log.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
