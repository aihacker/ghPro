(function () {

    var app = {};

    app.alert = function (msg, title, callback) {

        //window.alert(msg);
        if(typeof(title) === 'function') {
            callback = title;
            title = null;
        }
        if(!title) {
            title = '提示';
        }

        var $dialog = top.$("#_dialog");
        if(!$dialog.length) {
            top.$(top.document.body).append('<div id="_dialog" style="display:none;"></div>');
            $dialog = top.$("#_dialog");
        }
        var dlg = $dialog.data('kendoWindow');
        if(!dlg) {
            $dialog.kendoWindow({
                title: title,
                animation: false,
                modal: true
            });
            dlg = $dialog.data('kendoWindow');
        }
        else {
            dlg.title(title);
        }
        $dialog.attr('data-type', 'alert');

        var $content = top.$('<div><div class="body"></div><div class="foot"></div></div>');
        $content.find('.body').html('<i class="info"></i>' + msg);
        $content.find('.foot').html('<button class="btn btn-ok" tabindex="1">确定</button>');
        dlg.content($content).center();
        if(callback) {
            function onClose() {
                dlg.unbind('close', onClose);
                callback();
            }
            dlg.bind('close', onClose);
        }
        dlg.open();
        var $btnOk = $content.find('.btn-ok');
        $btnOk.focus();
        $btnOk.off('click').on('click', function () {
            dlg.close();
        });

    };

    app.confirm = function (msg, title, callback) {
        if(typeof(title) === 'function') {
            callback = title;
            title = null;
        }
        if(!title) {
            title = '提示';
        }

        var $dialog = top.$("#_dialog");
        if(!$dialog.length) {
            top.$(top.document.body).append('<div id="_dialog"></div>');
            $dialog = top.$("#_dialog");
        }
        var dlg = $dialog.data('kendoWindow');
        if(!dlg) {
            $dialog.kendoWindow({
                title: title,
                animation: false,
                modal: true
            });
            dlg = $dialog.data('kendoWindow');
        }
        else {
            dlg.title(title);
        }
        $dialog.attr('data-type', 'confirm');

        var $content = top.$('<div><div class="body"></div><div class="foot"></div></div>');
        $content.find('.body').html('<i class="info"></i>' + msg);
        $content.find('.foot').html('' +
            '<button class="btn btn-ok" tabindex="1">确定</button>' +
            '<button class="btn btn-cancel" tabindex="2">取消</button>');
        dlg.content($content).center();
        dlg.open();
        var $btnOk = $content.find('.btn-ok');
        $btnOk.off('click').on('click', function () {
            dlg.close();
            callback();
        });
        var $btnCancel = $content.find('.btn-cancel');
        $btnCancel.off('click').on('click', function () {
            dlg.close();
        });
        $btnCancel.focus();
    };

    app.showToast = function (msg) {
        //<div id="notification"></div>
        var $notification = top.$('#_notification');
        if(!$notification.length) {
            top.$(top.document.body).append('<div id="_notification" style="display:none;"></div>');
            top.$notification = top.$('#_notification');
            var timeout = 1.88; //s
            // timeout = 600; //10min
            top.$notification.kendoNotification({
                autoHideAfter: timeout * 1000,
                allowHideAfter: 1000,
                button: true,
                animation: {
                    open: {
                        effects: "slideIn:up"
                    },
                    close: {
                        effects: "slideIn:up",
                        reverse: true
                    }
                }
            });
        }
        var notification = top.$notification.data("kendoNotification");
        notification.show(msg);
    };

    function setWindowIcon($window, iconUrl) {
        if(!iconUrl) {
            return;
        }
        var $icon;
        if(iconUrl.indexOf('.') === -1) {
            $icon = top.$('<i class="g-icon ' + iconUrl + '"></i>');
        }
        else {
            $icon = top.$('<img src="' + iconUrl + '" class="icon"/>');
        }
        $window.parent().find('.k-window-title').prepend($icon);
    }

    app.popupWindow = function (url, title, options) {
        if(typeof(title) === 'object') {
            options = title;
            title = '';
        }
        options = $.extend({}, {
            width: 960,
            height: 640
        }, options);

        var $window = top.$('#_window');
        if(!$window.length) {
            top.$(top.document.body).append(
                '<div id="_window" style="display:none;"></div>');
            $window = top.$('#_window');
        }
        //
        var maxW = top.$(top.window).width() * .9;
        var w = Math.min(options.width, maxW);
        var maxH = top.$(top.window).height() * .9;
        var h = Math.min(options.height, maxH);
        var win = $window.kendoWindow({
            iframe: true,
            content: url,
            title: title || '正在加载...',
            width: w,
            height: h,
            modal: true
        }).data('kendoWindow');
        $window.find('iframe')[0].onload = function () {
            var frameWindow = this.contentWindow;
            frameWindow._opener = window;
            frameWindow._window = win;
            var docTitle = frameWindow.document.title;
            if(!title) {
                win.title(docTitle);
                setWindowIcon($window, options.icon);
            }
        };

        win.bind('close', function () {
            setTimeout(function () {
                win.destroy();
            }, 400);
        });
        win.center();
        win.open();

        if(title) {
            setWindowIcon($window, options.icon);
        }
        return win;
    };

    window.app = app;

})();

if(window !== window.top) {
    $(window).click(function () {
        window.top.notifyIframeClicked();
    });
}
//
// function g_test() {
//     alert(document.title);
//     $(document).xx();
// }
// $.fn.xx = function () {
//     alert('xxxxx');
// };