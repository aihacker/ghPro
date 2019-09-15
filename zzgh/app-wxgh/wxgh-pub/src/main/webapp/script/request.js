/**
 * Created by Administrator on 2016-06-16.
 */
(function () {
    //var homeHttpPath = ${home};
    //访问server
    window.requestServer = function (data, url, callBack) {
        $.ajax({
            dataType: 'json',
            url: url,
            data: data,
            type: 'POST',
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    callBack(result);
                }
                else {
                    var msg = result.msg || result.message;
                    var errors = result.errors;
                    if (!msg && errors) {
                        msg = '';
                        for (var key in errors) {
                            if (!errors.hasOwnProperty(key)) {
                                continue;
                            }
                            if (msg) {
                                msg += '\n';
                            }
                            msg += key + ': ' + errors[key];
                        }
                    }
                    alert(msg);
                }
            },
            error: function () {
                alert('something error')
            }
        });
    };

    //获取URL参数
    window.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    };

    //格式化时间
    window.formatDisplayTime = function (date, format) {
        if (!date) return;
        if (!format) format = "yyyy-MM-dd";
        switch (typeof date) {
            case "string":
                date = new Date(date.replace(/-/, "/"));
                break;
            case "number":
                date = new Date(date);
                break;
        }
        if (!date instanceof Date) return;
        var dict = {
            "yyyy": date.getFullYear(),
            "M": date.getMonth() + 1,
            "d": date.getDate(),
            "H": date.getHours(),
            "m": date.getMinutes(),
            "s": date.getSeconds(),
            "MM": ("" + (date.getMonth() + 101)).substr(1),
            "dd": ("" + (date.getDate() + 100)).substr(1),
            "HH": ("" + (date.getHours() + 100)).substr(1),
            "mm": ("" + (date.getMinutes() + 100)).substr(1),
            "ss": ("" + (date.getSeconds() + 100)).substr(1)
        };
        return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function () {
            return dict[arguments[0]];
        });
    };

    /*
     * show toast
     * img 参数说明：null或者不传参数者不显示图片；0显示加载中图标；1显示已完成图标;
     * */
    window.showZhbToast = function showToast(msg, img) {
        var imgScr = '';
        var imgDisplay = '';
        if (img != null) {
            if (img == 0) {
                imgScr = homeHttpPath + '/image/common/loading.gif';
                imgDisplay = '<img src="' + imgScr + '" id="alertImg">'
            }
            if (img == 1) {
                imgScr = homeHttpPath + '/image/common/check22.png';
                imgDisplay = '<img src="' + imgScr + '" id="alertImg1">'
            }
        }
        var $toast = $('<div id="alertBg"></div>' +
        '<div id="alertBox">' +
        '<div id="alertCtn">' +
        '<div id="alertMsg">' + msg + '</div>' +
        imgDisplay +
        '</div>' +
        '</div>');
        $toast.appendTo($('body'));
    }

    /*
     * hide toast
     * */
    window.hideZhbToast = function hideToast() {
        var $body = $('body');
        var $bg = $body.find('#alertBg');
        var $box = $body.find('#alertBox');

        $bg.remove();
        $box.remove();
    }
})();