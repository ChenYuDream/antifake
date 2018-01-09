<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>SFBD工厂防伪数据上传</title>
		<script src="${ctx }/static/js/antifake.js"></script>
		<script src="${ctx }/static/jquery/jquery-1.9.1.min.js"></script>
		<script src="${ctx }/static/js/lenovoInput.js"></script>
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
					<li class=""><button class="btn btn-success" id="kpi_btn">KPI统计</button></li>
					<li class="active"><button class="btn btn-success" id="sfbd_btn"> SFBD工厂防伪数据上传</button></li>
					<li class=""><button class="btn btn-success" id="soa_btn">SOA防伪码查询</button></li>
					<li class=""><button class="btn btn-success" id="export_btn">记录导出</button> </li>
				</ul>
			</div>
			<div class="span12">
				<div id="content">
					<!-- 页头 -->
					<div id="header">
						<h1> SFBD工厂防伪数据上传
						<!-- 
						<small><a id="logout" href="${ctx}/admin/logout">注销</a></small>
						 -->
						</h1>
					</div>
					<form id="sfdbFrom" class="form-search well"  method="post" >
						<div>
							<label>公司代码：</label> 
							<select name="import_recipient" id="import_recipient">
						      	<option value="">--请选择--</option>
						      	<c:forEach items="${plantList}" var="plant">
								 	<option value="${plant.recipient_no}">${plant.recipient}</option>
							    </c:forEach>
						    </select>
						</div>
						<br/>
						<div>
							<label>选择文件：</label> 
						    <input type="file" class="input-medium" id="file1"> 
						</div><br/>
						<button  class="btn btn-primary" id="sfdbImportData_btn" onclick="if(upload()) {return true;}else{return false;}">确定</button>
						<button type="reset" class="btn btn-danger" id="reset_btn">重置</button>
					</form>
					<!-- <h4 style="color:green" class="msg"></h4> -->
					<!--异步提交  start-->
					<script type="text/javascript">
					var baseUrl = "${ctx }";
					function upload() { 
						if(! $("#import_recipient").val().length>0){
							alert("请选择公司代码！");
							return false;
						}
						if(! $("#file1").val().length>0){
							alert("请选择上传文件！");
							return false;
						}
					    var formData = new FormData();
			            formData.append("file", document.getElementById("file1").files[0]);  
			            formData.append("import_recipient", document.getElementById("import_recipient").value);
					    $.ajax({    
					        url:"${ctx}/admin/sfdb/import",    
					        type:"POST",  
					        data:formData,  
		                    contentType: false,//必须false才会自动加上正确的Content-Type
		                    processData: false,//必须false才会避开jQuery对 formdata 的默认处理XMLHttpRequest会对 formdata 进行正确的处理
					        success: function(data){  					    
					            if("file-error" == data){
					            	//$(".msg").val("文件类型错误！");
					            	alert("文件类型错误！");
					            }else if("no-login" == data){
					            	window.location.href="${ctx}/admin";
					            }else if("error" == data){
					            	//$(".msg").val("上传失败！");
					            	alert("上传失败1！");
					            }else if("not-support" == data){
					            	//$(".msg").val("请上传2003版excel文件！");
					            	alert("请上传2003版excel文件！");
					            }else if("success" == data){
					            	//$(".msg").val("文件上传成功！");
					            	alert("文件上传成功！");
					            }
					        },
		                    error: function () {
		                    	//$(".msg").val("上传失败！");
		                    	alert("上传失败！");
		                    } 
					    });  
					};					
					</script>
					<!--异步提交  start-->
				</div>
			</div>
		</div>
	</body>
</html>