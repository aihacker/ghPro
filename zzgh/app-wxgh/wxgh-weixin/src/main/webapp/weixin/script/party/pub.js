/**
 * Created by Administrator on 2017/2/9.
 */

//表单序列化成Json
$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
}

//清空表单
$.fn.clearForm = function () {
    this.find('input[name],textarea[name]').each(function () {
        $(this).val('');
    })
}

//获取当前域名
window.host = (window.location.protocol + '//' + window.location.host);