(function () {

    var app = {};

    var nextWindowId = 1;

    app.alert = function (msg, title, callback) {

        if(window !== window.top) {
            top.app.alert(msg, title, callback);
            return;
        }

        if(String(msg).length > 256) {
            alert(msg);
            return;
        }

        //window.alert(msg);
        if(typeof(title) === 'function') {
            callback = title;
            title = null;
        }
        if(!title) {
            title = '提示';
        }

        var $dialog = $("#_dialog");
        if(!$dialog.length) {
            $(document.body).append('<div id="_dialog" style="display:none;"></div>');
            $dialog = $("#_dialog");
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

        var $content = $('<div><div class="body"></div><div class="foot"></div></div>');
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
        if(window !== window.top) {
            top.app.confirm(msg, title, callback);
            return;
        }

        if(typeof(title) === 'function') {
            callback = title;
            title = null;
        }
        if(!title) {
            title = '提示';
        }

        var $dialog = $("#_dialog");
        if(!$dialog.length) {
            $(document.body).append('<div id="_dialog"></div>');
            $dialog = $("#_dialog");
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

        var $content = $('<div><div class="body"></div><div class="foot"></div></div>');
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

    app.confirmOrCancel = function (msg, title, callback, cancelCallback) {
        if(window !== window.top) {
            top.app.confirmOrCancel(msg, title, callback, cancelCallback);
            return;
        }

        // if(typeof(title) === 'function') {
        //     callback = title;
        //     title = null;
        // }
        if(!title) {
            title = '提示';
        }

        var $dialog = $("#_dialog");
        if(!$dialog.length) {
            $(document.body).append('<div id="_dialog"></div>');
            $dialog = $("#_dialog");
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

        var $content = $('<div><div class="body"></div><div class="foot"></div></div>');
        $content.find('.body').html('<i class="info"></i>' + msg);
        $content.find('.foot').html('' +
            '<button class="btn btn-ok" tabindex="1">是</button>' +
            '<button class="btn btn-cancel" tabindex="2">否</button>');
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
            cancelCallback();
        });
        $btnCancel.focus();
    };

    app.showToast = function (msg, timeout) {
        if(window !== window.top) {
            top.app.showToast(msg,timeout);
            return;
        }
        //<div id="notification"></div>
        var $notification = $('#_notification');
        if(!$notification.length) {
            $(document.body).append('<div id="_notification" style="display:none;"></div>');
            $notification = $('#_notification');
            timeout = timeout || 10; //s
            // timeout = 600; //10min
            $notification.kendoNotification({
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
        var notification = $notification.data("kendoNotification");
        notification.show(msg);
    };

    function setWindowIcon($window, iconUrl) {
        if(!iconUrl) {
            return;
        }
        var $icon;
        if(iconUrl.indexOf('.') === -1) {
            $icon = $('<i class="g-icon ' + iconUrl + '"></i>');
        }
        else {
            $icon = $('<img src="' + iconUrl + '" class="icon"/>');
        }
        $window.parent().find('.k-window-title').prepend($icon);
    }

    app.popupWindow = function (url, title, options) {
        if(url.indexOf('/') === -1) {
            url = pub.buildFullUrl(url);
        }
        if(typeof(title) === 'object') {
            options = title;
            title = '';
        }
        if(window !== window.top) {
            options = $.extend({
                opener: window
            }, options);
            return top.app.popupWindow(url, title, options);
        }
        options = $.extend({}, {
            width: 960,
            height: 640
        }, options);

        var windowId = '_window_' + (nextWindowId++);
        $(document.body).append('<div id="' + windowId + '" style="display:none;"></div>');
        var $window = $('#' + windowId);
        //
        var maxW = $(window).width() * .9;
        var w = Math.min(options.width, maxW);
        var maxH = $(window).height() * .9;
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
            frameWindow._opener = options.opener || window;
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
                $window.remove();
            }, 400);
        });
        win.center();
        win.open();

        if(title) {
            setWindowIcon($window, options.icon);
        }
        return win;
    };

    app.closeWin = function () {
        setTimeout(function () {
            win.destroy();
            $window.remove();
        }, 400);
    };
    app.closeTab = function () {
        top.closeTab(window);
    };


    app.openTab = function(url, title, callback) {
        url = pub.buildFullUrl(url);
        if(top.addTab) {
            top.addTab(url, title, function (newWindow) {
                newWindow._opener = window;
                newWindow._module = window._module;
                newWindow._menu = window._menu;
                callback && callback(newWindow);
            });
        }
        else {
            window.open(url);
        }
    };

    window.app = app;

})();

if(window !== window.top && window.top.notifyIframeClicked) {
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