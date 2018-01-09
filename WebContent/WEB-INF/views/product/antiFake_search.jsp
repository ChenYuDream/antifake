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
    <title>soa防伪码查询</title>

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
    <!--avalon-->
    <style>
        .ms-controller{
            visibility: hidden
        }
    </style>
    <script>
        //定义全局变量
        var _CTX = "${ctx}";
    </script>
    <!--项目js-->
    <!-- 公共重置CSS -->
    <link  rel="stylesheet" href="${ctx}/static/styles/antifake/common.css" >
    <link rel="stylesheet" href="${ctx}/static/styles/antifake/index.css">

</head>
<body>
<!--头部  S-->
<div class="headBox clearfix">
    <a href="#this" class="pull-left">
        <h3 class="headtitle" >防伪码查询</h3>
    </a>
</div>
<!--头部  E-->
<!--leftMenu S-->
<div class="leftMenu">
    <div class="menuList">
        <ul>
            <li>
                <a href="javascript:void(0);">
                    <i class="iconfont icon-shangchuan1"></i>
                    <span>工厂防伪数据上传</span>
                </a>
            </li>
            <li class="active">
                <a href="${ctx}/operation/log/search/page">
                    <i class="iconfont icon-chaxun"></i>
                    <span>SOA防伪码查询</span>
                </a>
            </li>
            <li>
                <a href="${ctx}/operation/log/export/page">
                    <i class="iconfont icon-daochu"></i>
                    <span>记录导出</span>
                </a>
            </li>
        </ul>
    </div>
</div>
<!--leftMenu E-->
<!--mainbox S-->
<div class="mainbox ms-controller" ms-controller="vm_anti_search">
    <div class="maincont-tit">
        <h4>防伪码查询&数据记录</h4>
    </div>
    <div class="miaTable">
        <form class="layui-form" action="">
            <table>
                <tr>
                    <th colspan="3" class="text-center">搜索信息</th>
                </tr>
                <tr>
                    <td><span class="colorRed">*</span>Lable NO.：</td>
                    <td><input type="text" name="lableNo" ms-duplex="@search_lableNo" id="lableNo"  lay-verify="required" placeholder="请输入防伪码"
                               autocomplete="off" class="layui-input"></td>
                    <td><span class="btn btn-submit" onclick="anti_search()">查询</span></td>
                </tr>
                <tr ms-if="@search_success==1">
                    <td colspan="3" class="text-center"><span class="colorRed" >查询成功</span></td>
                </tr>
                <tr ms-if="@search_success==-1">
                    <td colspan="3" class="text-center"><span class="colorRed" >查询失败：防伪码不正确</span></td>
                </tr>
            </table>
            <table>
                <tr>
                    <th colspan="6" class="text-center">记录信息</th>
                </tr>
                <tr>
                    <th>查询客户信息</th>
                    <td colspan="5"></td>
                </tr>
                <tr>
                    <td><span class="colorRed">*</span>Customer：</td>
                    <td colspan="3"><input type="text" name="" ms-duplex="@data.customerName" lay-verify="required" placeholder="请输入姓名"
                                           autocomplete="off" class="layui-input"></td>
                    <td><span class="colorRed">*</span>Customer Phone：</td>
                    <td><input type="text" name="" ms-duplex="@data.customerContact"  lay-verify="required|phone" placeholder="请输入电话"
                               autocomplete="off" class="layui-input"></td>
                </tr>
                <tr>
                    <td>Province：</td>
                    <td>
                        <select name="provinceNodeClint" id="provinceNodeClint"  lay-filter="provinceNodeClint" >
                            <option value="">==请选择省份==</option>
                        </select>
                    </td>
                    <td>City：</td>
                    <td>
                        <select name="cityNodeClient" id="cityNodeClient"  lay-filter="cityNodeClient" >
                            <option value="">==请选择城市==</option>
                        </select>
                    </td>
                    <td>Customer Type：</td>
                    <td>
                        <select name="customerType" ms-duplex="@data.customerType"  lay-filter="customerType">
                            <option value="">==请选择客户类型==</option>
                            <!--Distributor,Personal，End User,Internal,OEM,Design，Retailer,Other-->
                            <option value="Distributor">Distributor</option>
                            <option value="Personal">Personal</option>
                            <option value="End User">End User</option>
                            <option value="Internal">Internal</option>
                            <option value="OEM">OEM</option>
                            <option value="Design">Design</option>
                            <option value="Retailer">Retailer</option>
                            <option value="Other">Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>供应商信息</th>
                    <td colspan="5"></td>
                </tr>
                <tr>
                    <td>Company Name：</td>
                    <td colspan="2"><input type="text" name="" ms-duplex="@data.companyName"   placeholder="请输入公司名称"
                                           autocomplete="off" class="layui-input"></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>Supplier Type：</td>
                    <td>
                        <select name="suppliersType"  ms-duplex="@data.supplierType" lay-filter="suppliersType" >
                            <!--经销商 ，实体门店，网店，集成商，非直接采购，内部同事，其他-->
                            <option value="">==请选择供应商类型==</option>
                            <option value="经销商">经销商</option>
                            <option value="实体门店">实体门店</option>
                            <option value="网店">网店</option>
                            <option value="集成商">集成商</option>
                            <option value="非直接采购">非直接采购</option>
                            <option value="内部同事">内部同事</option>
                            <option value="其他">其他</option>
                        </select>
                    </td>
                    <td>Supplier Province：</td>
                    <td>
                        <select name="provinceNodeSupplier" id="provinceNodeSupplier" lay-filter="provinceNodeSupplier" >
                        </select>
                    </td>
                    <td>Supplier City：</td>
                    <td>
                        <select name="cityNodeSupplier"  id="cityNodeSupplier" lay-filter="cityNodeSupplier" >
                            <option value="">==请选择城市==</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>产品信息</th>
                    <td colspan="5"></td>
                </tr>
                <tr>
                    <td>Function：</td>
                    <td><select name="productInfo_function" ms-duplex="@data.productFunction" id="productInfo_function" lay-filter="productInfo_function" >
                        <option value="">==请选择==</option>
                    </select>
                    </td>
                    <td>Family：</td>
                    <td>
                        <select name="productInfo_family" ms-duplex="@data.productFamily" id="productInfo_family" lay-filter="productInfo_family">
                            <option value="">==请选择==</option>
                        </select>
                    </td>
                    <td>Range：</td>
                    <td>
                        <select name="productInfo_Range" ms-duplex="@data.productRange" lay-filter="productInfo_Range" id="productInfo_Range">
                            <option value="">==请选择==</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Remark：</td>
                    <td>
                        <input type="text"  ms-duplex="@data.remark"  placeholder="请输入remark"
                               autocomplete="off" class="layui-input">
                    </td>
                    <td>Value：</td>
                    <td><input type="text" ms-duplex="@data.value" class="layui-input" placeholder="请输入value"></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>Lable NO.：</td>
                    <td>
                        <input type="text"  ms-duplex="@data.lableNo"   placeholder=""
                               autocomplete="off" class="layui-input" readonly>
                    </td>
                    <td>ProdDate：</td>
                    <td><input type="text" ms-duplex="@data.prodate" class="layui-input" id="ProdDate" placeholder="请选择日期"></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
            <table>
                <tr>
                    <th colspan="4" class="text-center">搜索结果</th>
                </tr>
                <tr>
                    <td>Lable NO.：</td>
                    <td colspan="3">
                        <input type="text"  ms-duplex="@data.lableNo"  readonly=""  placeholder=""
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>物料号：</td>
                    <td colspan="3">
                        <input type="text"   readonly=""  placeholder="" ms-duplex="@search.snoMaterial"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>描述：</td>
                    <td colspan="3">
                        <input type="text"   required readonly=""  placeholder="" ms-duplex="@desc"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>周号：</td>
                    <td colspan="3">
                        <input type="text" name="title" required readonly=""  placeholder=""
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>产品系列号：</td>
                    <td colspan="3">
                        <input type="text"   readonly  placeholder="" ms-duplex="@search.snoProductinfo"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>工厂：</td>
                    <td colspan="3">
                        <input type="text" readonly  placeholder="" ms-duplex="@search.snoCompany"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>生产线：</td>
                    <td colspan="3">
                        <input type="text"  readonly=""  placeholder=""  ms-duplex="@search.snoProductionline"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>包装时间：</td>
                    <td colspan="3">
                        <input type="text"   readonly=""  placeholder="" ms-duplex="search.snoCreatedate|transferTime"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td>生产订单编号：</td>
                    <td colspan="3">
                        <input type="text" readonly=""  placeholder="" ms-duplex="@search.snoProductionorder"
                               autocomplete="off" class="layui-input">
                    </td>
                </tr>
                <tr>
                    <td><span class="colorRed">*</span>自检情况：</td>
                    <td colspan="3">
                       <%-- <input type="text" ms-duplex="@data.ifCheck"  required readonly=""  placeholder=""
                               autocomplete="off" class="layui-input">--%>
                           <select name="ifCheck" id="ifCheck" lay-filter="ifCheck" lay-verify="required">
                                <option value="">==请选择==</option>
                               <option value="自检通过">自检通过</option>
                               <option value="自检不通过">自检不通过</option>
                           </select>
                    </td>
                </tr>
                <tr>
                    <td>假货：</td>
                    <td>
                        <input type="radio" name="yesno" ms-duplex="@data.fake" value="是" title="是" >
                        <input type="radio" name="yesno" value="否" ms-duplex="@data.fake" title="否" checked>
                    </td>
                    <td>
                        已查询次数
                    </td>
                    <td id="search_times">{{countNum}}</td>
                </tr>
            </table>
            <div class="btnWrap">
                <span class="btn btn-default" onclick="custom_close()">关闭</span>
                <span class="btn btn-submit" lay-submit lay-filter="*">提交</span>
            </div>
        </form>
    </div>
</div>
<!--mainbox E-->
</body>
</html>
<script src="${ctx}/static/js/antifake_search.js"></script>
<script language="javascript">
    function custom_close(){
       layer.confirm("您确定关闭页面吗？",function () {
           window.opener=null;
           window.open('','_self');
           window.close();
       })}
    avalon.filters.transferTime = function(a){
        if(a == '' ||a == null){
            return '';
        }
        return formatDateTime(a);
    }
    function formatDateTime(timeStamp) {
        var date = new Date();
        date.setTime(timeStamp);
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        var d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        var h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        var minute = date.getMinutes();
        var second = date.getSeconds();
        minute = minute < 10 ? ('0' + minute) : minute;
        second = second < 10 ? ('0' + second) : second;
        return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    };
</script>