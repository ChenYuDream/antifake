<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>SFBD工厂防伪数据上传</title>
    <link rel="stylesheet" type="text/css" href="${ctx }/static/bootstrap-datetimepicker/css/datetimepicker.css"/>
    <script type="text/javascript"
            src="${ctx }/static/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
            src="${ctx }/static/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>

    <script src="${ctx }/static/js/antifake.js"></script>
    <script src="${ctx }/static/js/lenovoInput.js"></script>

    <!-- 提示插件 -->
    <link rel="stylesheet" type="text/css" href="${ctx }/static/jquery-tipsy/tipsy.css"/>
    <script src="${ctx }/static/jquery-tipsy/jquery.tipsy.js"></script>
    <script type="text/javascript">
        var baseUrl = "${ctx }";
    </script>
    <style type="text/css">
        .search_resut label {
            width: 150px
        }
    </style>
</head>
<body>
<div class="row">
    <div class="span4 bs-docs-sidebar">
        <ul class="nav nav-list bs-docs-sidenav affix">
            <li class="">
                <button class="btn btn-success" id="productOrder_btn">产品订单管理</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="labelOrder_btn">标签订单管理</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="receiptHis_btn">收货历史查询</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="labelState_btn">标签状态统计</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="labelScrap_btn">作废标签管理</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="kpi_btn">KPI统计</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="sfbd_btn"> SFBD工厂防伪数据上传</button>
            </li>
            <li class="">
                <button class="btn btn-success" id="soa_btn">SOA防伪码查询</button>
            </li>
            <li class="active">
                <button class="btn btn-success" id="export_btn">记录导出</button>
            </li>
        </ul>
    </div>
    <div class="span12">
        <div id="content">
            <iframe height="800" width="100%" frameborder="n"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" src="${ctx}/operation/log/export/page"></iframe>
        </div>
    </div>
</div>
</body>
</html>