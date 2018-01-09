//google GA 代码
(function (i, s, o, g, r, a, m) {
    i['GoogleAnalyticsObject'] = r;
    i[r] = i[r] || function () {
        (i[r].q = i[r].q || []).push(arguments)
    }, i[r].l = 1 * new Date();
    a = s.createElement(o),
        m = s.getElementsByTagName(o)[0];
    a.async = 1;
    a.src = g;
    m.parentNode.insertBefore(a, m)
})(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

ga('create', 'UA-44968712-1', 'schneider-electric.com');
ga('send', 'pageview');

var url_ok = 'http://antifake.schneider-electric.com;https://antifake.schneider-electric.com;https://antifake.schneider-electric.com;https://antifake.schneider-electric.com';

$.ajaxSetup({cache: false});

//切换中英文
function changeLanguage(lan) {
    $.ajax({
        type: "POST",
        url: baseUrl + "/changeLanguage",
        data: {"lan": lan},
        dataType: "json",
        async: false,
        error: function (data, error) {
            alert(messageStrings["ajax.error"]);
        },
        success: function (data) {
            //window 会记住上一次请求的url，如果url 中带lan 参数，不能正常切换语言
            window.location.reload();
        }
    });
}

//web 查询调用 
function getProduct(type) {
    //记录日志字段:防伪码，手机，客户姓名，IP(notes 手动传)，客户类型
    //如果参数带:#、? 没有编码，会报404
    var lableNo = $("#lableNoId").val();

    var params = {};
    params["phoneNo"] = $("#phoneNoId").val();
    params["userName"] = encodeURI($("#userNameId").val());
    params["kaptcha"] = $("#kaptchaId").val();
    params["client"] = type;

    //var remoteUrlPrefix = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
    var remoteUrl = baseUrl + "/products/query/" + lableNo + ".json";
    $.ajax({
        type: "GET",
        url: remoteUrl,
        data: params,
        dataType: "json",
        async: false,
        beforeSend: function () {
            $('#submitId').attr("disabled", "disabled");
        },
        complete: function () {
            //$('#submitId').attr("disabled","disabled");
        },
        error: function (data, error) {
            //parseerror: 服务器停止 + 数据库无法连接 + 没有跟验证码 + 接口返回值为null
            //console.log(data);
            //console.log(error);
            //alert(messageStrings["ajax.error"]);	别让用户知道出错了，让他重新试一次就可以了
        },
        success: function (data) {
            querySuccess(type, lableNo, data);
        }
    });
}


//保存举报信息
function saveReportInfo(type) {
    //记录日志字段:防伪码，手机，客户姓名，IP(notes 手动传)，客户类型
    //如果参数带:#、? 没有编码，会报404
    var lableNo = $("#lableNoId").val();


    var params = {};
    params["clientName"] = $("#RuserNameId").val();
    params["clientPhone"] = $("#RphoneNoId").val();
    params["salesAddress"] = $("#buyaddress").val();
    params["salesName"] = $("#salesName").val();
    params["salesPhone"] = $("#salesPhone").val();
    params["productType"] = $("#productLine").val();
    params["amount"] = $("#amount").val();
    params["labelNo"] = lableNo;
    params["client"] = type;
    params["queryId"] = $("#queryLogId").val();

    var resultCount = $("#resultCount").val();
    var queryTimes = $("#queryTimes").val();
    if (resultCount == 0) {
        params["reportType"] = "02";
    } else if (resultCount > 0 && queryTimes > 5) {
        params["reportType"] = "03";
    } else if (resultCount > 0 && queryTimes <= 5) {
        params["reportType"] = "01";
    }


    //var remoteUrlPrefix = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}";
    var remoteUrl = baseUrl + "/products/saveReportInfo";

    $.ajax({
        type: "POST",
        url: remoteUrl,
        data: params,
        /*dataType:"json",*/
        async: false,
        beforeSend: function () {
            $('#submitId').attr("disabled", true);
        },
        complete: function () {
            $('#submitId').attr("disabled", false);
        },
        error: function (data, error) {
        },
        success: function (data) {
            if (type == 'mobile') {
                location.href = "#result_report";
            } else {
                $(".empty").val("");
                $("#report_success").show();
                $("#ReportDiv").hide();
            }
        }
    });
}


/**
 * 跨越请求
 $('#do').click(function(){
	$.getJSON("http://localhost:8888/se-antifake/products/" + "123.jsonp?callback=?", function (data) {
			console.log(data);
	});
});
 */

//查询，查询成功回调方法
function querySuccess(type, lableNo, data) {
    var resultCount = data.resultCount;
    var queryTimes = data.queryTimes;
    var queryLogId = data.queryId;
    var lan = data.lan;
    var countArray = ['0', '1st', '2nd', '3rd', '4th', '5th', '6th', '7th', '8th', '9th', '10th'];
    //验证码错误
    //上线时打开
    /**
     *
     */
    if (-1 == resultCount) {
        alert(messageStrings["validation.error.kaptchaId"]);
        $('.anotherImage').click();
        //有值时手机不能focus ？
        $("#kaptchaId").val("").focus();
        return false;
    }
    $("#queryLogId").val(queryLogId);
    $("#queryTimes").val(queryTimes);
    $("#resultCount").val(resultCount);
    //web 回调处理
    if ("web" == type) {
        $(".to_hidden").hide();
        if (0 == resultCount) {									//没有记录
            $("#resultId3").html(" " + lableNo);
            $("#result_3").show();
        } else if (resultCount > 0 && queryTimes <= 5) {		//被查询一次
            $("#resultId1").html(" " + lableNo);
            if ("en" == lan)
                $("#resultQueryId").html(" " + countArray[queryTimes]);
            else
                $("#resultQueryId").html(" " + queryTimes);
            $("#result_1").show();
        } else if (resultCount > 0 && queryTimes > 5) {		//被查询多次
            $("#resultId2").html(" " + lableNo);
            $("#result_2").show();
        }
        //mobile 回调处理
    } else {
        //结果正确
        if (0 == resultCount) {									//没有记录
            $("#resultId3").html(" " + lableNo);
            location.href = "#result_3";
        } else if (resultCount > 0 && queryTimes <= 5) {		//被查询一次
            $("#resultId1").html(" " + lableNo);
            if ("en" == lan)
                $("#resultQueryId").html(" " + countArray[queryTimes]);
            else
                $("#resultQueryId").html(" " + queryTimes);
            location.href = "#result_1";
        } else if (resultCount > 0 && queryTimes > 5) {		//被查询多次
            $("#resultId2").html(" " + lableNo);
            location.href = "#result_2";
        }
    }
}

//校验
function validateForm($Ele) {
    var isPass = true;
    $Ele.each(function () {
        var self = $(this);
        var _value = self.val();
        var _id = self.attr("id");
        //console.log("id:'"+_id+"' value:'"+_value+"'");
        //必填字段校验
        if ("" == $.trim(_value)) {
            alert(messageStrings["validation.empty." + _id]);
            self.focus();
            return isPass = false;
        }
        //防伪码必须12 位 数字
        if ("lableNoId" == _id && !(/^\d{12}$/.test(_value))) {
            alert(messageStrings["validation.error." + _id]);
            self.focus();
            return isPass = false;
        }
        //手机号码校验
        if ("phoneNoId" == _id && !(/^0*(13|15|18|14|17)\d{9}$/.test(_value))) {
            alert(messageStrings["validation.error." + _id]);
            self.focus();
            return isPass = false;
        }
    });
    return isPass;
}

function validateReportForm($Ele) {
    var isPass = true;
    $Ele.each(function () {
        var self = $(this);
        var _value = self.val();
        var _id = self.attr("id");
        //console.log("id:'"+_id+"' value:'"+_value+"'");
        //必填字段校验
        if ("" == $.trim(_value) && "salesPhone" != _id && "amount" != _id) {
            alert(messageStrings["validation.empty." + _id]);
            self.focus();
            return isPass = false;
        }
        //手机号码校验
        if ("RphoneNoId" == _id && !(/^0*(13|15|18|14|17)\d{9}$/.test(_value))) {
            alert(messageStrings["validation.error." + _id]);
            self.focus();
            return isPass = false;
        }
        //(0\d{2,4}-\d{7,8})|
        var r = /^(0*(1[35847]\d{9})|(0\d{2,4}-\d{7,8}))$/;
        //卖家电话校验
        if ("salesPhone" == _id && "" != $.trim(_value) && !(r.test(_value))) {
            alert(messageStrings["validation.error." + _id]);
            self.focus();
            return isPass = false;
        }
        //总金额校验
        if ("amount" == _id && "" != $.trim(_value) && checkNum(_value)) {
            alert(messageStrings["validation.error." + _id]);
            self.focus();
            return isPass = false;
        }
    });
    return isPass;
}

function checkNum(_value) {
    if (!isNaN(_value)) {
        var val = parseFloat(_value);
        if (typeof(val) == 'NaN') {
            return true;
        } else if (val > 0) {
            return false;
        } else {
            return true;
        }
    } else {
        return true;
    }
}

//后台疑似报告页面编辑
function getReportInfo(id) {
    var params = {};
    params["id"] = id;
    $.ajax({
        type: "post",
        data: params,
        url: baseUrl + "/admin/reportInfo/getReportInfo",
        beforeSend: function () {
            $("#edit_box_close").attr("disabled", true);
            $("#edit_box_save").attr("disabled", true);
        },
        complete: function () {
            $("#edit_box_close").attr("disabled", false);
            $("#edit_box_save").attr("disabled", false);
        },
        success: function (data) {
            console.warn(data + "," + data.clientName);
            var obj = JSON.parse(data);
            $("#RuserNameId").val(obj.clientName);
            $("#RphoneNoId").val(obj.clientPhone);
            $("#buyaddress").val(obj.salesAddress);
            $("#salesName").val(obj.salesName);
            $("#salesPhone").val(obj.salesPhone);
            $("#productLine").val(obj.productType);
            $("#amount").val(obj.amount);
            $("#comments").val(obj.comments);
            $("#state option").each(function () {
                if ($(this).val() == obj.state) {
                    $(this).attr("selected", "selected");
                }
            });
            $('#edit_box').modal("show");
        },
        error: function (error) {

        }
    });
}

//疑似报告编辑保存
function updateReportInfo() {
    var params = {};
    params["id"] = $("#id").val();
    params["clientName"] = $("#RuserNameId").val();
    params["clientPhone"] = $("#RphoneNoId").val();
    params["salesAddress"] = $("#buyaddress").val();
    params["salesName"] = $("#salesName").val();
    params["salesPhone"] = $("#salesPhone").val();
    params["productType"] = $("#productLine").val();
    params["amount"] = $("#amount").val();
    params["comments"] = $("#comments").val();
    params["state"] = $("#state").val();
    $.ajax({
        url: baseUrl + "/admin/reportInfo/updateReportInfo",
        type: "post",
        data: params,
        beforeSend: function () {
            $("#edit_box_close").attr("disabled", true);
            $("#edit_box_save").attr("disabled", true);
        },
        complete: function () {
            $('#edit_box').modal("hide");
        },
        success: function () {
            window.location.reload();
        }
    });
}

//设置警戒值
function saveWarnValue() {
    if ($("#warn_value").val()) {
        $("#edit_box_from").attr("action", baseUrl + "/admin/labelState/updateWarnValue").submit();
    }
}

$(function () {
    //刷新验证码
    $('.anotherImage').click(function () {
        $("#kaptchaImageId").hide().attr('src', baseUrl + '/getCaptchaImage?' + Math.floor(Math.random() * 100)).fadeIn();
    });
    //后台日志管理界面
    $("#queryLog_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/queryLog/list";
    });
    //疑似报告
    $("#reportinfo_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/reportInfo/list";
    });
    //产品订单管理
    $("#productOrder_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/productOrder/list";
    });
    //标签订单管理
    $("#labelOrder_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/labelOrder/list";
    });
    //收货历史查询
    $("#receiptHis_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/recepitHis/list";
    });
    //标签状态统计
    $("#labelState_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/labelState/list";
    });
    //作废标签管理
    $("#labelScrap_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/labelScrap/list";
    });
    //KPI统计
    $("#kpi_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/kpi/list";
    });
    //SFBD工厂防伪数据上传 addby wangxueqiang
    $("#sfbd_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/sfdb/list";
    });
    //SOA防伪码查询 addby wangxueqiang
    $("#soa_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/querySoa/list";
    });

    //SOA防伪码查询 addby yu_chen
    $("#export_btn").click(function () {
        if ($(this).parent().attr("class") == 'active') {
            return;
        }
        window.location.href = baseUrl + "/admin/export/page";
    });

});



