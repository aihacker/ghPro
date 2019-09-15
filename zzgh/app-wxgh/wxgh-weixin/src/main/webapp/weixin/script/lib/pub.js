/**
 * Created by Administrator on 2016/5/26.
 */
(function () {
    window.http = 'http://healthmdata.com.cn:8088/weixin-app-server/';
    //window.http = 'http://localhost:8080/hexi-app-server/';
    //window.http = 'http://yingyujif.imwork.net/hexi-app-server/';
    window.pub = function request(url,data,callback){
        $.ajax({
            dataType: 'json',
            url: http+url,
            data: data,
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    if(callback!=null||callback!=''){
                        callback(result);
                    }
                }
                else {
                    var msg = result.msg || result.message;
                    var errors = result.errors;
                    if(!msg && errors) {
                        msg = '';
                        for(var key in errors) {
                            if(!errors.hasOwnProperty(key)) {
                                continue;
                            }
                            if(msg) {
                                msg += '\n';
                            }
                            msg += key + ': ' + errors[key];
                        }
                    }
                    alert(msg);
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    }
    window.param = function getUrlParam(name){
        //构造一个含有目标参数的正则表达式对象
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        //匹配目标参数
        var r = window.location.search.substr(1).match(reg);
        //返回参数值
        if (r!=null) return unescape(r[2]);
        return null;
    }

    window.param2 = function GetQueryString(name)
    {
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }

})();
