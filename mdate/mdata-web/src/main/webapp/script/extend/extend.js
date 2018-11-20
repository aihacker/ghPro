/**
 * @author hhl
 * Created by WIN on 2016-12-06.
 * 自定义输入框验证模板
 */
$.extend($.fn.validatebox.defaults.rules, {
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
    english : {// 验证英语
        validator : function(value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message : '请输入英文'
    },
    ip : {// 验证IP地址
        validator : function(value) {
            return /\d+\.\d+\.\d+\.\d+/.test(value);
        },
        message : 'IP地址格式不正确'
    },
    ZIP: {
        validator: function (value, param) {
            return /^[0-9]\d{5}$/.test(value);
        },
        message: '邮政编码不存在'
    },
    QQ: {
        validator: function (value, param) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'QQ号码不正确'
    },
    mobile: {
        validator: function (value, param) {
            return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
        },
        message: '手机号码不正确'
    },
    tel:{
        validator:function(value,param){
            return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
        },
        message:'电话号码不正确'
    },
    mobileAndTel: {
        validator: function (value, param) {
            return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/.test(value);
        },
        message: '请正确输入电话号码'
    },
    number: {
        validator: function (value, param) {
            return /^[0-9]+.?[0-9]*$/.test(value);
        },
        message: '请输入数字'
    },
    money:{
        validator: function (value, param) {
            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
        },
        message:'请输入正确的金额'

    },
    mone:{
        validator: function (value, param) {
            return (/^(([1-9]\d*)|\d)(\.\d{1,5})?$/).test(value);
        },
        message:'请输入整数或小数'

    },
    integer:{
        validator:function(value,param){
            return /^[+]?[1-9]\d*$/.test(value);
        },
        message: '请输入最小为1的整数'
    },
    integ:{
        validator:function(value,param){
            return /^[+]?[0-9]\d*$/.test(value);
        },
        message: '请输入整数'
    },
    range:{
        validator:function(value,param){
            if(/[0-9]*(\.?)[0-9]*/.test(value)){
                return value >= param[0] && value <= param[1]
            }else{
                return false;
            }
        },
        message:'输入的数字在{0}到{1}之间'
    },
    minLength:{
        validator:function(value,param){
            return value.length >=param[0]
        },
        message:'至少输入{0}个字'
    },
    maxLength:{
        validator:function(value,param){
            return value.length<=param[0]
        },
        message:'最多{0}个字'
    },
    //select即选择框的验证
    selectValid:{
        validator:function(value,param){
            if(value == param[0]){
                return false;
            }else{
                return true ;
            }
        },
        message:'请选择'
    },
    idCode:{
        validator:function(value,param){
            return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
        },
        message: '请输入正确的身份证号'
    },
    loginName: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
    equalTo: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的字符不一至'
    },
    englishOrNum : {// 只能输入英文和数字
        validator : function(value) {
            return /^[a-zA-Z0-9_]{1,}$/.test(value);
        },
        message : '请输入英文、数字或下划线'
    },
    xiaoshu:{
        validator : function(value){
            return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
        },
        message : '最多保留两位小数！'
    },
    ddPrice:{
        validator:function(value,param){
            if(/^[1-9]\d*$/.test(value)){
                return value >= param[0] && value <= param[1];
            }else{
                return false;
            }
        },
        message:'请输入1到100之间正整数'
    },
    jretailUpperLimit:{
        validator:function(value,param){
            if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
                return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
            }else{
                return false;
            }
        },
        message:'请输入0到100之间的最多俩位小数的数字'
    },
    jretailUpperLimit1:{
        validator:function(value,param){
            if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
                return parseFloat(value) >= parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
            }else{
                return false;
            }
        },
        message:'请输入0到100之间的最多俩位小数的数字'
    },
    rateCheck:{
        validator:function(value,param){
            if(/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)){
                return parseFloat(value) > parseFloat(param[0]) && parseFloat(value) <= parseFloat(param[1]);
            }else{
                return false;
            }
        },
        message:'请输入0到1000之间的最多俩位小数的数字'
    },
    date: {
        validator: function (value) {
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
        },
        message: '请输入合适的日期格式:yyyy-MM-dd'
    },
    positiveNumber:{
        validator:function(value,param){
           return /^\d+(\.{0,1}\d+){0,1}$/.test(value);
        },
        message:'请输入大于0的数'
    }
});

/**
 * 自定义时间格式化
 * @param format
 * @returns {*}
 */
Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1, // 月
        "d+" : this.getDate(), // 天
        "h+" : this.getHours(), // 时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 刻
        "S" : this.getMilliseconds() //毫秒
        // millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

/*
 *  datagrid 获取正在编辑状态的行，使用如下：
 *  $('#id').datagrid('getEditingRowIndexs'); //获取当前datagrid中在编辑状态的行编号列表
 */
$.extend($.fn.datagrid.methods, {
    getEditingRowIndexs: function(jq) {
        var rows = $.data(jq[0], "datagrid").panel.find('.datagrid-row-editing');
        var indexs = [];
        rows.each(function(i, row) {
            var index = row.sectionRowIndex;
            if (indexs.indexOf(index) == -1) {
                indexs.push(index);
            }
        });
        return indexs;
    }
});

$(function(){
    var clicktag = 0;
    $("a").click(function () {
        if (clicktag == 0) {
            clicktag = 1;
            setTimeout(function () { clicktag = 0 }, 1000);
        }else{
            $.messager.show({
                title:'提示-1s后自动关闭',
                msg:'稍微休息1s吧.',
                timeout:1000,
                showType:'slide'
            });
            return false;
        }
    });

    //日志
    var allA = document.getElementsByClassName("easyui-linkbutton");
    var allArr = [];
    for(var i=0;i<allA.length;i++){
        allArr.push(allA[i]);
    }
    allArr.forEach(function(val,index){
        val.addEventListener('click',function(){
            console.log($(this).attr("name"));
            var name = $(this).attr("name");
            var param = [];
            var optModle = "";
            var optDescription = "";
            var optMenu = "";
            if(name != undefined && name != null && name != ""){
                param = name.split("-");
                if(param[0] != undefined){
                    optModle = param[0];
                }
                if(param[1] != undefined){
                    optMenu = param[1];
                }
                if(param[2] != undefined){
                    optDescription = param[2];
                }
            }else{
                return false;
            }
            $.ajax({
                url:"/online-pharmacy/admin/record/operate.do?action=save",
                data:{optModle:optModle,optDescription:optDescription,optMenu:optMenu},
                type:"post",
                dataType:"json",
                success:function(res){}
            });
        });
    });
});
