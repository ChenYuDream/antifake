avalon.config({debug: false});
var layDate,layForm;
<!--layUI初始化-->
layui.use('laydate', function () {
    layDate = layui.laydate;
    //执行一个laydate实例
    layDate.render({
        elem: '#ProdDate' //指定元素
    });
});
layui.use(['form'], function() {
    layForm = layui.form;
    layForm.render();
});
<!--省市数据-->
var len = provinceList.length;
var pStr = '<option value="">==请选择省份==</option>';
for(i=0;i<len;i++){
    obj = provinceList[i];
    pStr = pStr + '<option value="'+obj.ProID+'">'+obj.ProName+'</option>';
}
$("#provinceNodeClint").html(pStr);
$("#provinceNodeSupplier").html(pStr);
layForm.on('select(provinceNodeClint)', function(data){
    var v = $(data.elem).val();
    var len = cityList.length;
    var obj = null,cityStr = '<option value="">==请选择城市==</option>';
    for(var i=0;i<len;i++){
        obj = cityList[i];
        if(v==obj.ProID){
            cityStr = cityStr + '<option value="'+obj.CityID+'">'+obj.CityName+'</option>';
        }
    }
    $("#cityNodeClient").html(cityStr);
    layForm.render('select');
    var len = provinceList.length;
    for(i=0;i<len;i++){
        obj = provinceList[i];
        if(data.value == obj.ProID){
            vm_anti_search.data.customerProvince=obj.ProName;
            vm_anti_search.data.customerCity='';
            break;
        }
    }
});
layForm.on('select(provinceNodeSupplier)', function(data){
    var v = $(data.elem).val();
    var len = cityList.length;
    var obj = null,cityStr = '<option value="">==请选择城市==</option>';
    for(var i=0;i<len;i++){
        obj = cityList[i];
        if(v==obj.ProID){
            cityStr = cityStr + '<option value="'+obj.CityID+'">'+obj.CityName+'</option>';
        }
    }
    $("#cityNodeSupplier").html(cityStr);
    layForm.render('select');
    var len = provinceList.length;
    for(i=0;i<len;i++){
        obj = provinceList[i];
        if(data.value == obj.ProID){
            vm_anti_search.data.companyProvince=obj.ProName;
            vm_anti_search.data.companyCity='';
            break;
        }
    }
});
<!--productInfo数据下拉框-->
var len = productInfo_function_list.length;
var pStr = '<option value="">==请选择==</option>';
for(i=0;i<len;i++){
    obj = productInfo_function_list[i];
    pStr = pStr + '<option value="'+obj.functionName+'">'+obj.functionName+'</option>';
}
$("#productInfo_function").html(pStr);
layForm.on('select(productInfo_function)', function(data){
    var v = $(data.elem).val();
    var len = productInfo_family_list.length;
    var obj = null,cityStr = '<option value="">==请选择==</option>';
    for(var i=0;i<len;i++){
        obj = productInfo_family_list[i];
        if(v==obj.functionName){
            cityStr = cityStr + '<option value="'+obj.familyName+'">'+obj.familyName+'</option>';
        }
    }
    $("#productInfo_family").html(cityStr);
    $("#productInfo_Range").html('<option value="">==请选择==</option>');
    layForm.render('select');
    vm_anti_search.data.productFunction=data.value;
});
layForm.on('select(productInfo_family)', function(data){
    var pro_fun = $("#productInfo_function").val();
    var v = $(data.elem).val();
    var len = productInfo_Range_list.length;
    var obj = null,cityStr = '<option value="">==请选择==</option>';
    for(var i=0;i<len;i++){
        obj = productInfo_Range_list[i];
        if(v==obj.familyName&&pro_fun==obj.functionName){
            cityStr = cityStr + '<option value="'+obj.rangeName+'">'+obj.rangeName+'</option>';
        }
    }
    $("#productInfo_Range").html(cityStr);
    layForm.render('select');
    vm_anti_search.data.productFamily=data.value;
});
//productInfo_Range
layForm.on('select(productInfo_Range)', function(data){
    vm_anti_search.data.productRange=data.value;
});
//suppliersType
layForm.on('select(suppliersType)', function(data){
    vm_anti_search.data.supplierType=data.value;
});
//customerType
layForm.on('select(customerType)', function(data){
    vm_anti_search.data.customerType=data.value;
});
layForm.on('select(ifCheck)', function(data){
    vm_anti_search.data.ifCheck=data.value;
});
layForm.on('radio', function(data){
    vm_anti_search.data.fake=data.value;
});
//cityNodeClient
layForm.on('select(cityNodeClient)', function(data){
    var len = cityList.length;
    var obj = null;
    for(var i=0;i<len;i++){
        obj = cityList[i];
        if(data.value==obj.CityID){
            vm_anti_search.data.customerCity=obj.CityName;
            break;
        }
    }
});
//cityNodeSupplier
layForm.on('select(cityNodeSupplier)', function(data){
    var len = cityList.length;
    var obj = null;
    for(var i=0;i<len;i++){
        obj = cityList[i];
        if(data.value==obj.CityID){
            vm_anti_search.data.companyCity=obj.CityName;
            break;
        }
    }
});
//监听提交
layForm.on('submit', function(data){
    if(vm_anti_search.search_success == "0"){
        layer.msg('请先查询', {icon:5});
        return;
    }
    if(vm_anti_search.search_success == "-1"){
        layer.msg('请输入正确的防伪码', {icon:5});
        return;
    }
    vm_anti_search.data.productLine = vm_anti_search.search.snoProductionline;
    vm_anti_search.data.plant = vm_anti_search.search.snoCompany;
    vm_anti_search.data.queryTimes = vm_anti_search.countNum;
    $.ajax({
        url:_CTX+"/operation/log/insert",
        data:vm_anti_search.data,
        type:"post",
        beforeSend:function () {
            layer.load(2);
        },
        success:function (data) {
            if(data.code!='0'){
                layer.msg('提交失败', {icon:5});
                layer.closeAll("loading");
            }else{
                layer.msg('提交成功', function(){
                    layer.closeAll("loading");
                    location.reload();
                });
            }
        }
    });
    return false;
});
layForm.render();
//avalon定义开始，avalon用于渲染页面数据和绑定数据
var vm_anti_search = avalon.define({
    $id:"vm_anti_search",
    search_lableNo:'',
    search_success:"0",   //0表示没有查询 1表示查询成功  -1 表示查询失败
    data:{
        lableNo:'',
        customerName:'',
        customerContact:'',
        customerExtension:'',
        customerProvince:'',
        customerCity:'',
        customerType:'',
        productLine:'',
        productFunction:'',
        productFamily:'',
        productRange:'',
        remark:'',
        value:'',
        prodate:'',
        ifCheck:'',
        fake:'否',
        plant:'',
        companyName:'',
        companyProvince:'',
        companyCity:'',
        supplierType:'',
        queryTimes:'0'
    },
    search:{
        snoCompany:'',  //工厂
        snoProductionline:'', //生产线
        snoMaterial:'',//物料号
        snoProductionorder:'', //生产订单
        snoCreatedate:'',
        snoProductinfo:''//生产系列号
    },
    countNum:'0',
    desc:''
});

//查询
function anti_search() {
    var lableNo = vm_anti_search.search_lableNo;
    if(lableNo == ''){
        layer.msg('请输入防伪码', {icon:5});
        $("#lableNo").focus();
        return;
    }
    $.ajax({
        url:_CTX+"/operation/log/query",
        beforeSend:function () {
            layer.load(2);
        },
        data:{"lableNo":lableNo},
        type:"post",
        success:function (data) {
            if(data.code!="0"){
                vm_anti_search.search_success="-1";
                layer.msg('请输入正确的防伪码', {icon:5});
            }else{
                vm_anti_search.data.lableNo = data.data.snoSerialno;
                vm_anti_search.search_success="1";
                vm_anti_search.search = data.data;
                vm_anti_search.desc = data.data_desc;
                vm_anti_search.countNum = data.data_countNum;
                console.log(data);
            }
            layer.closeAll("loading");
        }
    });
}
