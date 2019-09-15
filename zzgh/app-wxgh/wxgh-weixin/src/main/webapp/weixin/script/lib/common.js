/**
 * Created by XDLK on 2016/6/20.
 */

/**
 * 打印提示信息
 * @param msg
 */
function toast(msg) {
    alert(msg);
}

/**
 * 返回上一页
 */
function goBack() {
    history.back();
}

/**
 * 获取请求地址#号后面的参数
 * @param {String} url
 */
function get_url_param(url) {
    var firstIndex = url.indexOf('#');
    url = url.substring(firstIndex + 1, url.length);
    var url_split = url.split('&');
    var re_param = {};
    for (var i = 0; i < url_split.length; i++) {
        var param = url_split[i].split('=');
        re_param[param[0]] = param[1];
    }
    return re_param;
}

!function (a) {
    /**
     * 进度框
     * @param {Object} options
     */
    a.LoadingAlert = function (msg) {
        if (!msg) {
            msg = "正在加载...";
        }

        var e_div = document.createElement("div");
        e_div.className = "ui-loading-block";
        e_div.innerHTML = '<div class="ui-loading-cnt"><i class="ui-loading-bright"></i><p>' + msg + '</p></div>';

        document.body.appendChild(e_div);

        this.$self = e_div;
    };
    a.LoadingAlert.prototype = {
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
}(window.ui = {})