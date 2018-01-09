var laydate,layForm;
layui.use('laydate', function () {
    laydate = layui.laydate;
    //执行一个laydate实例
    laydate.render({
        elem: '#startTime' //指定元素
        ,trigger: 'click'
    });
    laydate.render({
        elem: '#endTime' //指定元素
        ,trigger: 'click'
    });
});
layui.use(['form'], function() {
    layForm = layui.form;
    layForm.render();
});
//监听提交
layForm.on('submit', function(data){
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    if(!checkEndTime()){
        layer.msg('结束时间不能小于开始时间', {icon:5});
        return;
    }
    location.href =_CTX + "/operation/log/export?startTime="+startTime+"&endTime="+endTime;
    return false;
});

//对比时间
function checkEndTime(){
    var startTime=$("#startTime").val();
    var start=new Date(startTime.replace("-", "/").replace("-", "/"));
    var endTime=$("#endTime").val();
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));
    if(end<start){
        return false;
    }
    return true;
}