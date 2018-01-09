<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>记录导出</title>

    <!--jquery-->
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script>
    <!--省市区数据-->
    <script src="${ctx}/static/js/city.js"></script>
    <!--productInfo数据-->
    <script src="${ctx}/static/js/city.js"></script>
    <script src="${ctx}/static/js/productInfo.js"></script>
    <!--layui组件 -->
    <link rel="stylesheet" href="${ctx}/static/lib/layui/css/layui.css">
    <script src="${ctx}/static/lib/layui/layui.all.js"></script>
    <!--avalonJs-->
    <script src="${ctx}/static/lib/avalon2.2.6/avalon.js"></script>
    <!--字体图标 -->
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_517182_rozmp7b4n0tqpvi.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--[if IE 8]>

    <![endif]-->
    <script>
        //定义全局变量
        var _CTX = "${ctx}";
    </script>
    <!-- 公共重置CSS -->
    <link  rel="stylesheet" href="${ctx}/static/styles/antifake/common.css" >
    <link rel="stylesheet" href="${ctx}/static/styles/antifake/index.css">
</head>
<body>
<!--头部  S-->
<div class="headBox clearfix">
    <a href="#this" class="pull-left">
        <h3 class="headtitle" >记录导出</h3>
    </a>
</div>
<!--头部  E-->
<!--mainbox S-->
<div class="mainbox">
    <div class="maincont-tit">
        <h4>记录导出</h4>
    </div>
    <div class="miaTable">
        <form class="layui-form" action="">
            <table>
                <tr>
                    <th colspan="4" class="text-center">记录导出</th>
                </tr>
                <tr>
                    <td>开始时间：</td>
                    <td>
                        <input type="text" class="layui-input" id="startTime" lay-verify="required">
                    </td>

                    <td>截止时间</td>
                    <td>
                        <input type="text" class="layui-input" id="endTime" lay-verify="required">
                    </td>
                </tr>
            </table>
            <div class="btnWrap">
                <span class="btn btn-submit" lay-submit lay-filter="*">导出</span>
            </div>
        </form>
    </div>
</div>
<!--mainbox E-->
</body>
</html>
<!--项目js-->
<script src="${ctx}/static/js/antifake_export.js"></script>