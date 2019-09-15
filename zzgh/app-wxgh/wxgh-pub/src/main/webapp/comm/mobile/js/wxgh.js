/**
 * Created by XDLK on 2016/8/19.
 */

var autoTextarea = function (elem, extra, maxHeight) {
    //判断elem是否为数组
    if (elem.length > 0) {
        for (var i = 0; i < elem.length; i++) {
            e(elem[i]);
        }
    } else {
        e(elem);
    }

    function e(elem) {
        extra = extra || 0;
        var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
            isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
            addEvent = function (type, callback) {
                elem.addEventListener ?
                    elem.addEventListener(type, callback, false) :
                    elem.attachEvent('on' + type, callback);
            },
            getStyle = elem.currentStyle ? function (name) {
                    var val = elem.currentStyle[name];

                    if (name === 'height' && val.search(/px/i) !== 1) {
                        var rect = elem.getBoundingClientRect();
                        return rect.bottom - rect.top -
                            parseFloat(getStyle('paddingTop')) -
                            parseFloat(getStyle('paddingBottom')) + 'px';
                    }
                    ;

                    return val;
                } : function (name) {
                    return getComputedStyle(elem, null)[name];
                },
            minHeight = parseFloat(getStyle('height'));

        elem.style.resize = 'none';

        var change = function () {
            var scrollTop, height,
                padding = 0,
                style = elem.style;

            if (elem._length === elem.value.length) return;
            elem._length = elem.value.length;

            if (!isFirefox && !isOpera) {
                padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
            }
            ;
            scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

            elem.style.height = minHeight + 'px';
            if (elem.scrollHeight > minHeight) {
                if (maxHeight && elem.scrollHeight > maxHeight) {
                    height = maxHeight - padding;
                    style.overflowY = 'auto';
                } else {
                    height = elem.scrollHeight - padding;
                    style.overflowY = 'hidden';
                }
                ;
                style.height = height + extra + 'px';
                scrollTop += parseInt(style.height) - elem.currHeight;
                document.body.scrollTop = scrollTop;
                document.documentElement.scrollTop = scrollTop;
                elem.currHeight = parseInt(style.height);
            }
            ;
        };

        addEvent('propertychange', change);
        addEvent('input', change);
        addEvent('focus', change);
        change();
    }
};


window.ghmui = {
    show: function (el) {
        if (el.classList.contains("mui-hidden")) {
            el.classList.remove("mui-hidden");
        }
    }
    ,
    hide: function (el) {
        if (!el.classList.contains("mui-hidden")) {
            el.classList.add("mui-hidden");
        }
    }
    ,
    disabled: function (el) {
        if (!el.classList.contains("mui-disabled")) {
            el.classList.add("mui-disabled");
        }
    }
    ,
    no_disabled: function (el) {
        if (el.classList.contains("mui-disabled")) {
            el.classList.remove("mui-disabled");
        }
    }
    ,
    readOnly: function (el) {
        if (!el.getAttribute("readonly")) {
            if (el.classList.contains("mui-input-clear")) {
                el.classList.remove("mui-input-clear");
            }
            el.setAttribute("readonly", "readonly");
        }
    }
    ,
    no_readOnly: function (el) {
        if (el.getAttribute("readonly")) {
            el.removeAttribute("readonly");
            if (!el.classList.contains("mui-input-clear")) {
                el.classList.add("mui-input-clear");
            }
        }
    }
    ,
    progress: {
        show: function () {
            mui('body').progressbar({
                progress: undefined
            }).show();
        }
        ,
        hide: function () {
            mui('body').progressbar().hide();
        }
    }
    ,
    start_progress: function (el) {
        this.disabled(el);
        this.progress.show();
    }
    ,
    end_progress: function (el) {
        this.no_disabled(el);
        this.progress.hide();
    }
    ,
    getElement: function (id) {
        return document.getElementById(id);
    }
    ,
    query: function (selector) {
        return document.querySelector(selector);
    }
    ,
    queryAll: function (selector) {
        return document.querySelectorAll(selector);
    }
    ,
    getHost: function () {
        return window.location.host;
    }
    ,
    onchange: function (el, func) {
        el.addEventListener("input", func);
        el.addEventListener("propertychange", func);
    }
    ,
};

String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
    return true;
};


window.wxgh = {
    config: {
        ADMIN_PATH: '/weixin-app-admin',
        APP_PATH: '/fsgh'
    },
    get_avatar: function (avatar, thumb) {
        if (avatar) {
            if (!thumb && false != thumb) {
                var last = avatar.substring(avatar.length - 1, avatar.length)
                avatar = avatar + (last == '/' ? '64' : '')
            } else {
                avatar = avatar;
            }
        } else {
            avatar = homePath + '/image/default/user.png'
        }
        return avatar
    },
    get_thumb: function (d, def) {
        var path
        if (d.thumb) {
            path = d.thumb;
        } else if (d.path) {
            path = d.path;
        } else {
            if (def) {
                path = ((def.indexOf('/') > 0) ? def : '/image/default/' + def);
            } else {
                path = '/image/default/bbs.png';
            }
        }
        return homePath + path;
    },
    get_wx_img: function (localId, func) {
        if (window.__wxjs_is_wkwebview && window.__wxjs_is_wkwebview == true) {
            wx.getLocalImgData({
                localId: localId,
                success: function (res) {
                    func(res.localData);
                }
            });
        } else {
            func(localId);
        }
    },
    init: function (weixin) {
        wxgh.autoTextarea($('.ui-text-div textarea'))

        $('.mui-table-view-cell').on('change', 'select[name]', function () {
            var $self = $(this)
            var val = $self.val()
            var txt = $self.find('option[value="' + val + '"]').text()
            $self.parent().find('.ui-right').text(txt)
        })
        if (weixin) wxgh.wxInit(weixin)
    },
    LOADING_HTML: '<div class="mui-loading"><div class="mui-spinner"></div></div>',
    wxJsApi: function (jsApiFun) {
        if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
            jsApiFun();
        } else {
            document.attachEvent && document.attachEvent("WeixinJSBridgeReady", jsApiFun);
            document.addEventListener && document.addEventListener("WeixinJSBridgeReady", jsApiFun);
        }
    },
    wxInit: function (weixin) {
        console.log("weixin")
        console.log(weixin)
        if (typeof weixin == 'string') {
            weixin = JSON.parse(weixin);
        }
        if (typeof weixin != 'string') {
            wx.config({
                beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
                debug: (weixin.debug == 'true' ? true : false),
                appId: weixin.appId,
                timestamp: weixin.timestamp,
                nonceStr: weixin.nonceStr,//
                signature: weixin.signature, //
                jsApiList: weixin.apiList
            });

            wx.ready(function () {
                if (weixin.menuList) {
                    wx.hideMenuItems({
                        menuList: weixin.menuList
                    })
                }
            })
        }
    },
    wxShareInit: function (title, link, imgUrl, info, func, cancelFunc) {
        if (!imgUrl.startWith('http')) {
            imgUrl = 'http://' + wxgh.getHost() + imgUrl
        }
        //分享到朋友圈
        wx.onMenuShareTimeline({
            title: title, // 分享标题
            link: link, // 分享链接
            imgUrl: imgUrl, // 分享图标
            success: function () {
                if (func) func()
            },
            cancel: function () {
                if (cancelFunc) cancelFunc()
            }
        });

        //分享给朋友
        wx.onMenuShareAppMessage({
            title: title, // 分享标题
            desc: info, // 分享描述
            link: link, // 分享链接
            imgUrl: imgUrl, // 分享图标
            success: function () {
                if (func) func()
            },
            cancel: function () {
                if (cancelFunc) cancelFunc()
            }
        });
        //分享给qq
        wx.onMenuShareQQ({
            title: title, // 分享标题
            desc: info, // 分享描述
            link: link, // 分享链接
            imgUrl: imgUrl, // 分享图标
            success: function () {
                if (func) func()
            },
            cancel: function () {
                if (cancelFunc) cancelFunc()
            }
        });
        //分享到微博
        wx.onMenuShareWeibo({
            title: title, // 分享标题
            desc: info, // 分享描述
            link: link, // 分享链接
            imgUrl: imgUrl, // 分享图标
            success: function () {
                if (func) func()
            },
            cancel: function () {
                if (cancelFunc) cancelFunc()
            }
        });
        //分享到qq空间
        wx.onMenuShareQZone({
            title: title, // 分享标题
            desc: info, // 分享描述
            link: link, // 分享链接
            imgUrl: imgUrl, // 分享图标
            success: function () {
                if (func) func()
            },
            cancel: function () {
                if (cancelFunc) cancelFunc()
            }
        });
    },
    wxContactOpen: function (json, func, params) {
        if (typeof json == 'string') {
            json = JSON.parse(json)
        }
        var DEFAULT = {
            'departmentIds': [0],    // 非必填，可选部门ID列表（如果ID为0，表示可选管理组权限下所有部门）
            'tagIds': [0],    // 非必填，可选标签ID列表（如果ID为0，表示可选所有标签）
            'userIds': [],    // 非必填，可选用户ID列表
            'mode': 'multi',    // 必填，选择模式，single表示单选，multi表示多选
            'type': ['user'],    // 必填，选择限制类型，指定department、tag、user中的一个或者多个
            'selectedDepartmentIds': [],
            'selectedTagIds': [],
            'selectedUserIds': [],    // 非必填，已选用户ID列表
        }
        if (!params) params = {}
        mui.extend(DEFAULT, params)
        wxgh.wxJsApi(function () {
            wx.invoke("selectEnterpriseContact", {
                "fromDepartmentId": 0,// 必填，-1表示打开的通讯录从自己所在部门开始展示, 0表示从最上层开始
                "mode": "multi",// 必填，选择模式，single表示单选，multi表示多选
                "type": ["user"],// 必填，选择限制类型，指定department、user中的一个或者多个
/*                "groupId": json.groupId,
                "timestamp": json.timestamp,
                "nonceStr": json.nonceStr,
                "signature": json.signature,
                "params": DEFAULT*/
            }, function (res) {

                console.log("res")
                console.log(res)

                if (res.err_msg.indexOf('function_not_exist') > -1) {
                    alert('版本过低请升级');
                } else if (res.err_msg.indexOf('openEnterpriseContact:fail') > -1) {
                    return;
                }
                if(typeof res.result == 'string')
                {
                    res.result = JSON.parse(res.result) //由于目前各个终端尚未完全兼容，需要开发者额外判断result类型以保证在各个终端的兼容性
                }
                var selectAll = result.selectAll;
                if (!selectAll) {
                    var depts = res.result.departmentList
                    var users = res.result.userList
                    func(false, selectAll, users)
                } else {
                    func(true)
                }
            })
        })
    },
    autoTextarea: function ($e) {
        if ($e.length <= 0) return;
        autoTextarea($e)
        $.each($e, function () {
            var $self = $(this)
            var len = $self.attr('maxlength')
            var $small = $('<small></small>')
            $self.parent().append($small)
            $small.text($e.val().length + ' / ' + len)
        })
        $e.on('keyup', function () {
            var $self = $(this)
            var val = $self.val()
            var len = $self.attr('maxlength')
            if (val.length > Number(len)) {
                val = $self.val().substring(0, len)
                $self.val(val)
            }
            $self.next().text(val.length + ' / ' + len)
        })
    },

    get_image: function (path) { //获取图片路径
        if (!path) return "";
        if (path.startWith('weixin://resourceid') || path.startWith('wxLocalResource://')) {
            return path;
        }
        if (path.startWith('http://')) {
            return path;
        }
        if (path.startWith('${admin}')) {
            return path.replace('${admin}', wxgh.config.ADMIN_PATH)
        }
        if (path.startWith('${home}')) {
            return path.replace('${home}', wxgh.config.APP_PATH)
        }
        if (path.startWith('/weixin-app-server')) {
            return path.replace('/weixin-app-server', wxgh.config.APP_PATH)
        }
        if (path.startWith('/weixin-app-admin')) {
            return path.replace('/weixin-app-admin', wxgh.config.ADMIN_PATH)
        }
        if (path.startWith('/wx-dev')) {
            return path.replace('/wx-dev', wxgh.config.APP_PATH)
        }
        if (path.startWith('/wxgh')) {
            return path.replace('/wxgh', wxgh.config.APP_PATH)
        }
        if (path.indexOf('/') == -1) {
            return wxgh.config.APP_PATH + "/uploads/image/material/" + path;
        }
        return wxgh.config.APP_PATH + path;
    },
    getHost: function () {
        return window.location.host;
    },
    serialize: function (form) {
        var info = {};
        var inputs = form.querySelectorAll("input[name], textarea[name], select[name]");
        for (var i = 0; i < inputs.length; i++) {
            var item = inputs[i];
            if (item.type && item.type == "file") {
            } else {
                info[item.name] = item.value;
            }
        }
        return info;
    }
    ,
    clearForm: function (form) {
        mui.each(form.querySelectorAll("input[name], textarea[name]"), function () {
            this.value = "";
        });
        mui.each(form.querySelectorAll("select[name]"), function () {
            var options = this.querySelectorAll("option");
            for (var i = 0; i < options.length; i++) {
                var item = options[i];
                if (item.value == 0) {
                    item.setAttribute("selected", "selected");
                } else {
                    if (item.getAttribute("selected")) {
                        item.removeAttribute("selected");
                    }
                }
            }
        })
    }
    ,
    replace: function (str, obj) {
        var pattern;
        if (!pattern) {
            pattern = new RegExp(/#{\w*\s?}/);
        }
        var res = str.matches(pattern);
        if (typeof obj === "string") {
            mui.each(res, function () {
                str = str.replace(this, obj);
            });
        } else if (typeof obj === "object") {
            mui.each(res, function () {
                var self = this.replace("#", "")
                    .replace("{", "")
                    .replace("}", "");
                if (res) self.trim();
                str = str.replace(this, obj[self]);
            });
        }
        return str;
    }
    ,
    day_next: function (time, num) {
        if (!num) num = 1;
        var times = time.split('-')
        var today = new Date(times[0], Number(times[1]) - 1, times[2]);            //月份为0-11
        var yesterday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * num;
        var yesterday = new Date();
        yesterday.setTime(yesterday_milliseconds);

        var strYear = yesterday.getFullYear();
        var strDay = yesterday.getDate();
        var strMonth = yesterday.getMonth() + 1;
        if (strMonth < 10) {
            strMonth = "0" + strMonth;
        }
        if (strDay < 10) {
            strDay = "0" + strDay;
        }
        var strYesterday = strYear + "-" + strMonth + "-" + strDay;
        return strYesterday;
    }
    ,
    day_preview: function (time, num) {
        if (!num) num = 1;
        var times = time.split('-')
        var today = new Date(times[0], Number(times[1]) - 1, times[2]);            //月份为0-11
        var yesterday_milliseconds = today.getTime() - 1000 * 60 * 60 * 24 * num;
        var yesterday = new Date();
        yesterday.setTime(yesterday_milliseconds);

        var strYear = yesterday.getFullYear();
        var strDay = yesterday.getDate();
        var strMonth = yesterday.getMonth() + 1;
        if (strMonth < 10) {
            strMonth = "0" + strMonth;
        }
        if (strDay < 10) {
            strDay = "0" + strDay;
        }
        var strYesterday = strYear + "-" + strMonth + "-" + strDay;
        return strYesterday;
    }
    ,
    redirectTip: function (rootUrl, tip) {
        var def = {
            type: 1,
            urlMsg: '返回首页',
            title: '操作完成'
        }
        mui.extend(def, tip)
        var url = ''
        var i = 0;
        for (var k in def) {
            if (i == 0) url += '?'
            else url += '&'
            url += (k + '=' + encodeURIComponent(def[k]))
            i++
        }
        mui.openWindow(rootUrl + '/wx/pub/tip/show.html' + url)
    }
    ,
    checkEmail: function (emailValue) {
        var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
        if (!reg.test(emailValue)) {
            return false;
        }
        return true;
    }
    ,
    checkPhoneNum: function (phoneNum) {
        if (!(/0?(13|14|15|17|18)[0-9]{9}/.test(phoneNum)) || phoneNum.length > 11) {
            return false;
        }
        return true;
    },
    checkUrl: function (url) {
        var strRegex = "^((https|http|ftp|rtsp|mms)://)?[a-z0-9A-Z]{3}\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?/$"
        var re = new RegExp(strRegex);
        if (!url || !re.test(url)) {
            return false;
        }
        return true;
    },
    checkUrlV2: function (url) {
        var reg= /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
        if(!reg.test(url)){
            return false;
        }
        return true;
    },

    checkIdcard: function (idcard) {
        var city = {
            11: "北京",
            12: "天津",
            13: "河北",
            14: "山西",
            15: "内蒙古",
            21: "辽宁",
            22: "吉林",
            23: "黑龙江 ",
            31: "上海",
            32: "江苏",
            33: "浙江",
            34: "安徽",
            35: "福建",
            36: "江西",
            37: "山东",
            41: "河南",
            42: "湖北 ",
            43: "湖南",
            44: "广东",
            45: "广西",
            46: "海南",
            50: "重庆",
            51: "四川",
            52: "贵州",
            53: "云南",
            54: "西藏 ",
            61: "陕西",
            62: "甘肃",
            63: "青海",
            64: "宁夏",
            65: "新疆",
            71: "台湾",
            81: "香港",
            82: "澳门",
            91: "国外 "
        };
        var tip = "";
        var pass = true;

        if (!idcard || !/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/i.test(idcard)) {
            tip = "身份证号格式错误";
            pass = false;
        } else if (!city[idcard.substr(0, 2)]) {
            tip = "地址编码错误";
            pass = false;
        } else {
            //18位身份证需要验证最后一位校验位
            if (idcard.length == 18) {
                idcard = idcard.split('');
                //∑(ai×Wi)(mod 11)
                //加权因子
                var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
                //校验位
                var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++) {
                    ai = idcard[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if (parity[sum % 11] != idcard[17]) {
                    tip = "校验位错误";
                    pass = false;
                }
            }
        }
        //if (!pass) alert(tip);
        return pass;
    }
    ,
    checkeNumber: function (number) {
        if (number == null || number <= 0) {
            return false;
        }
        return true;
    }
    ,
    previewImageInit: function () {
        var preview = mui.previewImage();
        window.addEventListener('preview:open', function (e) {
            var url = window.location.href
            if (url.charAt(url.length - 1) != '#') {
                var state = {
                    title: "title",
                    url: "#"
                };
                window.history.pushState(state, "title", "#");
            }
        })

        window.addEventListener('preview:close', function (e) {
            var url = window.location.href
            if (url.charAt(url.length - 1) == '#') {
                window.history.back()
            }
        })

        window.addEventListener('popstate', function () {
            if (preview.isShown) {
                preview.close();
            }
        })

        return preview;
    },
    request: {
        ajax: function (url, data, config, success, error) {
            if (!url.startWith(homePath)) {
                if (url.startWith('/')) {
                    url = homePath + url
                }
            }
            var info = {
                url: url,
                dataType: 'json',
                success: function (data) {
                    $('.ui-loading-block').removeClass('show')
                    if (data.ok) {
                        if (success) success(data.data)
                    } else {
                        if (error) {
                            error(data)
                        } else {
                            console.log(data.msg)
                            // 修复当 函数alert 参数为null 时报错
                            if (data.msg)
                                ui.alert(data.msg)
                        }
                    }
                },
                error: function (xhr, type, errorThrown) {
                    $('.ui-loading-block').removeClass('show')
                    console.log(type)
                    console.log(errorThrown)
                    ui.alert('请检查您的网络连接！')
                }
            }
            $.extend(info, config ? config : {})
            if (data) {
                info['data'] = data
            }
            $.ajax(info)
        },
        get: function (url, data, success, error) {
            wxgh.request.ajax(url, data, {type: 'get'}, success, error);
        },
        getURL: function (url, success, error) {
            wxgh.request.ajax(url, '', {type: 'get'}, success, error);
        },
        post: function (url, data, success, error) {
            wxgh.request.ajax(url, data, {type: 'post'}, success, error);
        },
        postJson: function (url, data, success, error) {
            if (data && (typeof data != 'string')) {
                data = JSON.stringify(data);
            }
            wxgh.request.ajax(url, data, {
                type: 'post',
                contentType: 'application/json; charset=utf-8'
            }, success, error);
        }
    },


    show: function (el) {
        if (el.classList.contains("mui-hidden")) {
            el.classList.remove("mui-hidden");
        }
    }
    ,
    hide: function (el) {
        if (!el.classList.contains("mui-hidden")) {
            el.classList.add("mui-hidden");
        }
    }
    ,
    disabled: function (el) {
        if (!el.classList.contains("mui-disabled")) {
            el.classList.add("mui-disabled");
        }
    }
    ,
    no_disabled: function (el) {
        if (el.classList.contains("mui-disabled")) {
            el.classList.remove("mui-disabled");
        }
    }
    ,
    readOnly: function (el) {
        if (!el.getAttribute("readonly")) {
            if (el.classList.contains("mui-input-clear")) {
                el.classList.remove("mui-input-clear");
            }
            el.setAttribute("readonly", "readonly");
        }
    }
    ,
    no_readOnly: function (el) {
        if (el.getAttribute("readonly")) {
            el.removeAttribute("readonly");
            if (!el.classList.contains("mui-input-clear")) {
                el.classList.add("mui-input-clear");
            }
        }
    }
    ,
    progress: {
        show: function () {
            mui('body').progressbar({
                progress: undefined
            }).show();
        }
        ,
        hide: function () {
            mui('body').progressbar().hide();
        }
    }
    ,
    start_progress: function (el) {
        this.disabled(el);
        this.progress.show();
    }
    ,
    end_progress: function (el) {
        this.no_disabled(el);
        this.progress.hide();
    }
    ,
    getElement: function (id) {
        return document.getElementById(id);
    }
    ,
    query: function (selector) {
        return document.querySelector(selector);
    }
    ,
    queryAll: function (selector) {
        return document.querySelectorAll(selector);
    }
    ,
    getHost: function () {
        return window.location.host;
    }
    ,
    onchange: function (el, func) {
        el.addEventListener("input", func);
        el.addEventListener("propertychange", func);
    }
    ,
    setCookie: function (name, value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 2 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    },
    setCookie: function (name, value, time) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + time * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    },
    getCookie: function (name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }

};

//数组
Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

//格式化时间代码
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 微信选择图片
 * @param el 组件包含在el内
 * @param option 配置参数，参考ImageChoose.DEFAULT
 */
!(function ($) {
    var ImageChoose = function (el, options) {
        this.el = el
        this.options = options
    }
    ImageChoose.HTML = '<ul class="mui-table-view mui-grid-view ui-upload-list no">'
        + '<li class="mui-table-view-cell mui-media ui-choose">'
        + '<a href="javascript:;"> <div class="ui-img-div"> <img class="mui-media-object" src="' + wxgh.config.APP_PATH + '/image/common/icon_add.png">'
        + '</div> </a> </li> </ul>'
    ImageChoose.DEFAULT = {
        wx: {
            count: 9,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
        },
        clear: false,
        col: 4
    }
    ImageChoose.prototype = {
        init: function () {
            var $html = $(ImageChoose.HTML)
            $html.find('.mui-table-view-cell').addClass('mui-col-xs-' + this.options.col);
            this.el.append($html)

            this.localIds = []
            this.mediaIds = []

            var self = this
            $html.on('tap', '.ui-choose', function () {
                var config = self.options.wx
                config['success'] = function (res) {
                    var ids = res.localIds
                    if (ids && ids.length > 0) {
                        self.clearItem()
                        if (self.options.clear) {
                            self.localIds = []
                        }
                        for (var i = 0, len = ids.length; i < len; i++) {
                            var id = ids[i];
                            if (id.indexOf('wxlocalresource') != -1) {
                                id = id.replace("wxlocalresource", "wxLocalResource");
                                ids.splice(i, 1, id);
                            }
                        }
                        mui.extend(self.localIds, ids)
                        if (self.localIds.length > self.options.wx.count) {
                            mui.toast('最多选择' + self.options.wx.count + '张图片哦。');
                            return
                        }
                        for (var i in self.localIds) {
                            self.createItem(self.localIds[i])
                        }
                    }
                }
                wx.chooseImage(config)
            })
            this.$html = $html
        },
        clearItem: function () {
            this.$html.find('li:not(.ui-choose)').remove()
        },
        createItem: function (src) {
            if (!this.imgWidth) {
                this.imgWidth = this.$html.find('.ui-choose img').width()
            }
            var $item = $('<li class="mui-table-view-cell mui-media mui-col-xs-' + this.options.col + '">'
                + '<a href="javascript:;"> <div class="ui-img-div">'
                + '<img class="mui-media-object"> </div>'
                + '<span class="mui-icon mui-icon-close-filled"></span> </a> </li>')

            $item.find('.ui-img-div').css('height', (this.imgWidth && this.imgWidth > 0) ? this.imgWidth : 80);
            this.$html.append($item)
            var self = this;
            $item.on('tap', '.mui-icon', function () {
                var index = self.localIds.indexOf(src);
                self.localIds.splice(index, 1);
                $(this).parent().parent().remove()
            });

            wxgh.get_wx_img(src, function (imgSrc) {
                imgSrc.replace(/wxgh/,"fsgh")
                $item.find('img').attr('src', imgSrc)
            });
        },
        upload: function (func, clear) {
            var self = this
            if (this.localIds.length > 0) {
                var localId = self.localIds.pop()
                wx.uploadImage({
                    localId: localId.toString(),
                    isShowProgressTips: 0,
                    success: function (res) {
                        var serverId = res.serverId
                        self.mediaIds.push(serverId)
                        if (self.localIds.length > 0) {
                            self.upload(func)
                        } else {
                            self.localIds = []
                            if (func) func(self.mediaIds)
                            self.mediaIds = []
                            if (clear === true) { //清空
                                self.clearItem()
                            }
                        }
                    }
                })
            } else {
                if (func) func([])
            }
        }
    }

    wxgh.imagechoose = function (el, options) {
        var newOptions = {}
        $.extend(true, newOptions, ImageChoose.DEFAULT, options ? options : {})
        var img = new ImageChoose(el, newOptions)
        img.init()
        return img
    }
})(jQuery);

!function (a) {
    /**
     * 进度框
     * @param {Object} options
     */
    var LoadingAlert = function (msg) {
        if (!msg) {
            msg = "正在加载...";
        }

        var e_div = document.createElement("div");
        e_div.className = "ui-loading-block";
        e_div.innerHTML = '<div class="ui-loading-cnt"><i class="ui-loading-bright"></i><p>' + msg + '</p></div>';

        document.body.appendChild(e_div);

        this.$self = e_div;
    };
    LoadingAlert.prototype = {
        //进度框显示
        show: function () {
            if (!this.$self.classList.contains("show")) {
                this.$self.classList.add("show");
            }
        },
        //进度框隐藏
        hide: function () {
            if (this.$self.classList.contains("show")) {
                this.$self.classList.remove("show");
            }
        },
        clear: function () {
            document.body.removeChild(this.$self);
        }
    };

    a.loading = function (msg) {
        var load = new LoadingAlert(msg)
        return load;
    }

    /**
     * 弹出框
     * @param msg
     * @param func
     */
    a.showToast = function (msg, func) {
        var defMsg = '已完成'
        if (msg) {
            defMsg = msg
        }

        var e_div = document.createElement('div')
        e_div.id = 'weuiToast'
        //		e_div.className = ''
        var cnt = '<div class="weui_mask_transparent"></div><div class="weui_toast animated fadeInDown"><i class="weui_icon_toast"></i><p class="weui_toast_content">' + defMsg + '</p></div>'

        e_div.innerHTML = cnt
        document.body.appendChild(e_div)

        var e_toast = e_div.querySelector('.weui_toast')
        mui.later(function () {
            e_toast.classList.add('fadeOutDown')
            mui.later(function () {
                document.body.removeChild(e_div)
                if (func) func()
            }, 1000)
        }, 1500)
    }

    /**
     * 微信confirm框
     * @param title
     * @param message
     * @param btnArray
     * @param func
     * @param showFunc
     */
    a.wxconfirm = function (title, message, btnArray, func, showFunc) {
        if (typeof(jQuery) == 'undefined') {
            alert('请先引入jQuery文件')
            return
        }

        if (!btnArray || btnArray.length != 2) {
            btnArray = ['取消', '确定']
        }

        if ($('.weui_dialog_confirm').length > 0) {
            var $mdig = $('.weui_dialog_confirm .weui_dialog')
            $mdig.find('.weui_dialog_title').text(title)
            $mdig.find('weui_dialog_bd').html(message)
            $mdig.find('.weui_btn_dialog.default').text(btnArray[0])
            $mdig.find('.weui_btn_dialog.primary').text(btnArray[1])
            $('.weui_dialog_confirm').show()
            $mdig.fadeIn(300, function () {
                if (showFunc) showFunc($mdig)
            })
            return
        }

        var cnt = '<div class="weui_dialog_confirm">'
            + '<div class="weui_mask" style="z-index: 9999;"></div>'
            + '<div class="weui_dialog" style="display: none;z-index: 9999;">'
            + '<div class="weui_dialog_hd"><strong class="weui_dialog_title">' + title + '</strong></div>'
            + '<div class="weui_dialog_bd">' + message + '</div>'
            + '<div class="weui_dialog_ft">'
            + '<a href="#" class="weui_btn_dialog default">' + btnArray[0] + '</a>'
            + '<a href="#" class="weui_btn_dialog primary">' + btnArray[1] + '</a>'
            + '</div></div></div>';
        var $cnt = $(cnt)
        $('body').append($cnt)

        var $dialog = $cnt.find('.weui_dialog')

        $cnt.on('tap', 'a.weui_btn_dialog', function () {
            if (func) func($(this).index(), $dialog)
            $cnt.fadeOut(300)
        })

        $dialog.fadeIn(300, function () {
            if (showFunc) showFunc($dialog)
        })
    }

    a.alert = function (msg, func, title) {
        mui.alert(msg, title ? title : '提示', '确定', func)
    }

    a.confirm = function (msg, okFunc, cancelFunc, title) {
        mui.confirm(msg, title ? title : '提示', ['否', '是'], function (e) {
            if (e.index == 1) {
                if (okFunc) okFunc()
            } else {
                if (cancelFunc) cancelFunc()
            }
        })
    }

    a.timeAgo = function (time, onlyDate) {
        if (!time) return '未知时间';
        var stamp = new Date().getTime() - new Date(time).getTime();

        //超过30天，返回具体日期
        if (stamp > 1000 * 60 * 60 * 24 * 30) {
            stamp = new Date(time).toLocaleString();
            onlyDate && (stamp = stamp.replace(/\s[\S]+$/g, ''));
            return stamp;
        }

        //30天以内，返回“多久前”
        if (stamp >= 1000 * 60 * 60 * 24) {
            return ((stamp / 1000 / 60 / 60 / 24) | 0) + '天前';
        } else if (stamp >= 1000 * 60 * 60) {
            return ((stamp / 1000 / 60 / 60) | 0) + '小时前';
        } else if (stamp >= 1000 * 60 * 3) { //3分钟以内为：刚刚
            return ((stamp / 1000 / 60) | 0) + '分钟前';
        } else if (stamp < 0) {
            return '未来';
        } else {
            return '刚刚';
        }
    }
}(window.ui = {});

$.fn.extend({
    serializeJson: function () {
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
});

