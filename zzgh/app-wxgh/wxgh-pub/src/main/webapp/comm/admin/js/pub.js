/**
 * Created by Administrator on 2017/2/9.
 */
/**
 * 判断一个字符串是否以某个字符串开头
 * @example "ssss".startWith("dd")
 * @param str
 * @returns {boolean}
 */
String.prototype.startWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substr(0, str.length) == str)
        return true;
    else
        return false;
    return true;
}

String.prototype.endWith = function (str) {
    if (str == null || str == "" || this.length == 0 || str.length > this.length)
        return false;
    if (this.substring(this.length - str.length) == str)
        return true;
    else
        return false;
    return true;
}

/**
 * 格式化时间代码
 * @example new Date().format('yyyy-MM-dd hh:mm:ss')
 * @param fmt
 * @returns {*}
 */
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


//Jquery
$.fn.extend({
    form: function () {
        $.each($(this).find('input[name],textarea[name]'), function () {
            var $self = $(this)
            if ($self.hasAttr('maxlength')) {
                var $small = $('<small class="ui-input-tip"></small>')
                $small.text($self.val().length + ' / ' + $self.attr('maxlength'))
                $self.parent().append($small)
            }
        })

        $(this).find('input[name],textarea[name]').keyup(function () {
            var $self = $(this)

            if ($self.hasAttr('maxlength')) {
                $self.parent().find('small').text($self.val().length + ' / ' + $self.attr('maxlength'))
            }
        })

    },
    //清空表单
    clearForm: function () {
        this.find('input[name],textarea[name]').each(function () {
            var $self = $(this)
            $self.val('');
            if ($self.hasAttr('maxlength')) {
                $self.parent().find('small').text($self.val().length + ' / ' + $self.attr('maxlength'))
            }
        })
        this.find('input[type=file]').each(function () {
            ui.clearFile(this)
        })
        this.find('select[name]').each(function () {
            $("this").find("option[value='-1']").attr("selected", true);
        })
    },
    //表单序列化成Json
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
    },
    //判断是否有某个属性
    hasAttr: function (attr) {
        return typeof($(this).attr(attr)) != 'undefined'
    }
})

//获取当前域名
window.host = (window.location.protocol + '//' + window.location.host);

!(function (u) {
    u.check = {
        init: function () {
            $('.ui-check-all').on('change', function () {
                $('.ui-check').prop('checked', $(this).is(':checked'))
            })
            $('.ui-check').on('change', function () {
                var $self = $(this)
                if (!$self.is(':checked')) {
                    $('.ui-check-all').prop('checked', false)
                } else {
                    var checkLen = $('.ui-check:checked').length
                    var len = $('.ui-check').length
                    if (len == checkLen) {
                        $('.ui-check-all').prop('checked', true)
                    }
                }
            })
        },
        addItem: function ($item) {
            $item.on('change', '.ui-check', function () {
                var $self = $(this)
                if (!$self.is(':checked')) {
                    $('.ui-check-all').prop('checked', false)
                } else {
                    var checkLen = $('.ui-check:checked').length
                    var len = $('.ui-check').length
                    if (len == checkLen) {
                        $('.ui-check-all').prop('checked', true)
                    }
                }
            })
        }
    }
    u.page = function (func, p, selector) {
        if (!p) p = {page: 1, current: 1}
        if (p.page <= 1) return;
        if (!selector) selector = '#pagination'
        $.jqPaginator(selector, {
            totalPages: p.page,
            visiblePages: 6,
            currentPage: p.current,
            prev: '<li class="prev"><a href="javascript:;"><span class="fa fa-caret-left"></span> 上一页</a></li>',
            next: '<li class="next"><a href="javascript:;">下一页 <span class="fa fa-caret-right"></span></a></li>',
            page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
            onPageChange: function (num, type) {
                //	$('#p1').text(type + '：' + num);
                if (func) func(num, type)
            }
        });
    }
    u.page2 = {
        init: function (func, selector) {
            if (!selector) selector = '#pagination'
            $(selector).hide()
            $(selector).jqPaginator({
                totalPages: 1,
                visiblePages: 6,
                currentPage: 1,
                prev: '<li class="prev"><a href="javascript:;"><span class="fa fa-caret-left"></span> 上一页</a></li>',
                next: '<li class="next"><a href="javascript:;">下一页 <span class="fa fa-caret-right"></span></a></li>',
                page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
                onPageChange: function (num, type) {
                    //	$('#p1').text(type + '：' + num);
                    if (func) func(num, type)
                }
            });
        },
        setPage: function (p, selector) {
            if (!selector) selector = '#pagination'
            if (p && p.page > 1) {
                $(selector).jqPaginator('option', {
                    totalPages: p.page,
                    currentPage: p.current
                })
                $(selector).show()
            } else {
                $(selector).hide()
            }
        }
    }
    u.request = function (type, url, data, sucFunc, failFunc) {
        if (url.startWith('/')) {
            url = homePath + url
        }
        var cfg = {
            url: url,
            type: type,
            datatype: 'json',
            success: function (res) {
                if (res.ok) {
                    if (sucFunc) sucFunc(res.data)
                } else {
                    if (failFunc) {
                        failFunc()
                    } else {
                        ui.alert(res.msg)
                    }
                }
            },
            error: function (request, s, e) {
                console.log(request)
                console.log(e + '：' + s)
                alert('error....')
            }
        }
        if (data) cfg['data'] = data
        $.ajax(cfg)
    }
    u.get = function (url, data, sucFunc, failFunc) {
        u.request('get', url, data, sucFunc, failFunc)
    }
    u.getURL = function (url, success, error) {
        u.request(url, '', 'get', success, error)
    }
    u.post = function (url, data, sucFunc, failFunc) {
        u.request('post', url, data, sucFunc, failFunc)
    }

    u.ajaxFile = function (url, data, fileEl, sucFunc, failFunc) {
        var $input = $('#' + fileEl);
        var files = $input[0].files;
        if (files && files.length > 0) {
            $.ajaxFileUpload({
                url: url,
                dataType: 'json',
                data: data,
                secureuri: false,
                cache:false,
                fileElementId: fileEl,
                success: function (data) {
                    if (data.ok) {
                        if (sucFunc) sucFunc(data.data);
                    } else {
                        if (failFunc) failFunc();
                        else ui.alert(data.msg);
                    }
                }
            });
        } else {
            u.post(url, data, sucFunc, failFunc);
        }
    }

    //删除
    u.del = function (id, sucFunc, url) {
        ui.confirm('是否删除？', function () {
            if ($.isArray(id)) id = id.toString()
            if (!url) url = 'api/delete.json'
            ui.get(url, {id: id}, function () {
                if (sucFunc) sucFunc()
            })
        })
    }

    //刷新页面
    u.refresh = function (isRe) {
        if (!isRe && isRe != false) isRe = false
        window.location.reload(isRe)
    }

    u.clearFile = function (f) {
        if (f.value) {
            try {
                f.value = ''; //for IE11, latest Chrome/Firefox/Opera...
            } catch (err) {
            }
            if (f.value) { //for IE5 ~ IE10
                var form = document.createElement('form'), ref = f.nextSibling, p = f.parentNode;
                form.appendChild(f);
                form.reset();
                p.insertBefore(f, ref);
            }
        }
    }

    //获取文件
    u.get_file = function (name) {
        var img_url = homePath + '/image/filetype/file_';
        if (name) {
            var ext = name.substr(name.lastIndexOf('.'), name.length);
            if (ext == '.xlsx' || ext == '.xls') {
                img_url += 'excel.png';
            } else if (ext == '.gif') {
                img_url += 'gif.png';
            } else if (ext == '.html' || ext == '.xhtml') {
                img_url += 'html.png';
            } else if (ext == '.jpg') {
                img_url += 'jpg.png';
            } else if (ext == '.mp3') {
                img_url += 'mp3.png';
            } else if (ext == '.mp4') {
                img_url += 'mp4.png';
            } else if (ext == '.pdf') {
                img_url += 'pdf.png';
            } else if (ext == '.png') {
                img_url += 'png.png';
            } else if (ext == '.ppt' || ext == '.pptx') {
                img_url += 'ppt.png';
            } else if (ext == '.rar') {
                img_url += 'rar.png';
            } else if (ext == '.txt') {
                img_url += 'txt.png';
            } else if (ext == '.doc' || ext == '.docx') {
                img_url += 'word.png';
            } else if (ext == '.zip') {
                img_url += 'zip.png';
            } else {
                img_url += 'unknow.png';
            }
        } else {
            img_url += 'unknow.png';
        }
        return img_url;
    }

    u.verify = {
        mobile: function (str) {
            var reg = /^1[0-9]{10}$/
            return reg.test(str)
        },
        email: function (str) {
            var reg = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/
            return reg.test(str)
        },
        url: function (str) {
            var reg = /[a-zA-z]+:\/\/[^\s]*/
            return reg.test(str)
        }
    }

    //打开页面
    u.openWindow = function (url, blank) {
        var url = (url.startWith('http') ? url : homePath + url)
        if (blank == true) {
            window.open(url)
        } else {
            window.location.href = url
        }
    }

    //empty
    u.emptyTable = function (col, msg) {
        return $('<tr><td colspan="' + col + '"><div class="ui-empty">' + (msg ? msg : '暂无数据') + '</div></td></tr>')
    }
    u.loadingTable = function (col) {
        return $('<tr><td colspan="' + col + '"><div class="ui-empty-loading"><i class="fa fa-spinner fa-spin"></i></div></td></tr>')
    }

    //config
    u.config = {
        ADMIN_PATH: '/weixin-app-admin',
        APP_PATH: '/wxgh'
    }

    u.get_image = function (path) { //获取图片路径
        if (!path) return "";
        if (path.startWith('weixin://resourceid') || path.startWith('wxLocalResource://')) {
            return path;
        }
        if (path.startWith('http://') || path.startWith('data:')) {
            return path;
        }
        if (path.startWith('${admin}')) {
            return path.replace('${admin}', ui.config.ADMIN_PATH)
        }
        if (path.startWith('${home}')) {
            return path.replace('${home}', ui.config.APP_PATH)
        }
        if (path.startWith('/weixin-app-server')) {
            return path.replace('/weixin-app-server', ui.config.APP_PATH)
        }
        if (path.startWith('/weixin-app-admin')) {
            return path.replace('/weixin-app-admin', ui.config.ADMIN_PATH)
        }
        if (path.startWith('/wx-dev')) {
            return path.replace('/wx-dev', ui.config.APP_PATH)
        }
        if (path.startWith('/wxgh')) {
            return path.replace('/wxgh', ui.config.APP_PATH)
        }
        if (path.indexOf('/') == -1) {
            return ui.config.APP_PATH + "/uploads/image/material/" + path;
        }
        return ui.config.APP_PATH + path;
    }

    u.WEEK_NAMES = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']

    u.getWeekName = function (week) {
        return ui.WEEK_NAMES[Number(week) - 1]
    }

    u.STATUS = ['待审核', '正常', '未通过']
    u.get_status = function (status, strs) {
        if (!strs) {
            strs = ui.STATUS
        }
        status = Number(status)
        if (status + 1 > strs.length) {
            return '未知(' + status + ')'
        }
        return strs[status]
    }

    u.openUrl = function (url, params) {
        var $form = $('<form method="post" action="' + url + '"></form>')
        if (params) {
            for (var k in params) {
                $form.append('<input type="hidden" name="' + k + '" value="' + params[k] + '">')
            }
        }
        $form.appendTo('body').submit().remove();
    }

    u.getParams = function (url) {
        var params = {};
        if (url.indexOf("?") != -1) {
            var str = url.split('?')[1]
            var strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                params[strs[i].split("=")[0]] = strs[i].split("=")[1];
            }
        }
        return params;
    }

    u.get_avatar = function (avatar, thumb) {
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
        return avatar;
    }
    u.get_thumb = function (d, def) {
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
    }
})
(window.ui = {})

/**
 * Confirm确认框
 */
+
(function ($, d) {

    //confirm框
    var Confirm = function (options, okFunc, cancelFunc) {
        this.options = options
        this.okFunc = okFunc
        this.cancelFunc = cancelFunc
        this.el = $('<div class="modal ui-confirm fade"></div>')
    }

    Confirm.HTML = '<div class="modal-dialog"><div class="modal-content">' +
        '<div class="modal-header"><button type="button" class="close" data-dismiss="modal">' +
        '<span>&times;</span></button>' +
        '<h4 class="modal-title">{{=it.title}}</h4></div>' +
        '<div class="modal-body ui-bg-lightgreen">{{=it.content}}</div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-{{=it.bgcolor}}">{{=it.buttons[1]}}</button>' +
        '<button type="button" class="btn btn-default" data-dismiss="modal">{{=it.buttons[0]}}</button>' +
        '</div></div></div>'

    Confirm.DEFAULT = {
        title: '系统提示',
        content: '我是内容',
        bgcolor: 'green',
        buttons: ['取消', '确定'],
        draggable: true
    }

    Confirm.prototype.show = function () {
        this.el.modal('show')
    }
    Confirm.prototype.hide = function () {
        this.el.modal('hide')
    }
    Confirm.prototype.toggle = function () {
        this.el.modal('toggle')
    }

    Confirm.prototype.init = function () {
        var pageFn = d.template(Confirm.HTML)
        this.el.html(pageFn(this.options))

        var btns = this.options.buttons
        var self = this
        this.el.find('.modal-footer').on('click', 'button', function () {
            var index = $(this).index()
            if (index == 0) {
                if (self.okFunc) self.okFunc()
                self.hide()
            } else {
                if (self.cancelFunc) self.cancelFunc()
            }
        })
        // this.el.find('.modal-dialog').draggable()
        $('body').append(this.el)
    }

    ui.confirm = function (cnt, func, title, cancelFunc) {
        var options = {content: cnt}
        if (title) options['title'] = title
        var newOptions = $.extend(true, {}, Confirm.DEFAULT, options);
        var conf = new Confirm(newOptions, func, cancelFunc)
        conf.init()
        conf.show()
        return conf
    }

    ui.alert = function (msg, func) {
        var options = {content: msg, title: '系统消息'}
        var newOptions = $.extend(true, {}, Confirm.DEFAULT, options);
        var conf = new Confirm(newOptions, func, func)
        conf.init()
        conf.show()
        return conf
    }

})(jQuery, doT)

/**
 * loading动画
 */
+
(function ($) {
    var Loading = function (options) {
        this.options = options
        this.type = options['type']
    }
    Loading.HTML = {
        wave: '<div class="sk-wave" style="display:none;"><div class="sk-rect sk-rect1"></div>' +
        '<div class="sk-rect sk-rect2"></div>' +
        '<div class="sk-rect sk-rect3"></div>' +
        '<div class="sk-rect sk-rect4"></div>' +
        '<div class="sk-rect sk-rect5"></div></div>',
        three: '<div class="sk-three-bounce" style="display:none;">' +
        '<div class="sk-child sk-bounce1"></div>' +
        '<div class="sk-child sk-bounce2"></div>' +
        '<div class="sk-child sk-bounce3"></div>' +
        '</div>',
        cube: '<div class="sk-cube-grid" style="display:none;">' +
        '<div class="sk-cube sk-cube1"></div>' +
        '<div class="sk-cube sk-cube2"></div>' +
        '<div class="sk-cube sk-cube3"></div>' +
        '<div class="sk-cube sk-cube4"></div>' +
        '<div class="sk-cube sk-cube5"></div>' +
        '<div class="sk-cube sk-cube6"></div>' +
        '<div class="sk-cube sk-cube7"></div>' +
        '<div class="sk-cube sk-cube8"></div>' +
        '<div class="sk-cube sk-cube9"></div></div>'
    }
    Loading.DEFAULT = {
        color: '#1ab394',
        type: 'wave',
        backdrop: false,
        opacity: '0.1',
        selector: 'body'
    }

    Loading.prototype.init = function () {
        var el = $(Loading.HTML[this.type])
        if (this.options.backdrop) {
            var $drop = $('<div class="modal-backdrop"></div>')
            $drop.css('opacity', this.options.opacity)
            $(this.options.selector).append($drop)
            el.css('z-index', 99999).css('position', 'absolute')
            this.$drop = $drop
        }
        $(this.options.selector).append(el)
        if (this.type == 'wave') {
            el.find('.sk-rect').css('background-color', this.options.color)
        } else if (this.type == 'three') {
            el.find('.sk-child').css('background-color', this.options.color)
        } else if (this.type == 'cube') {
            el.find('.sk-cube').css('background-color', this.options.color)
        }
        this.el = el
    }
    Loading.prototype.show = function () {
        this.el.fadeIn(500)
    }
    Loading.prototype.hide = function () {
        var self = this
        this.el.fadeOut(400, function () {
            if (self.$drop) self.$drop.remove()
        })

    }
    ui.loading = function (type, options) {
        var newOptions = {}
        if (!options) options = {}
        if (type) options['type'] = type
        newOptions = $.extend(true, newOptions, Loading.DEFAULT, options);
        var load = new Loading(newOptions)
        load.init()
        return load
    }
})(jQuery)

+ (function ($) {
    $.fn.ellipsis = function (options) {

        // default option
        var defaults = {
            'row': 1, // show rows
            'onlyFullWords': false, // set to true to avoid cutting the text in the middle of a word
            'char': '...', // ellipsis
            'callback': function () {
            },
            'maxWidth': 0,
            'position': 'tail' // middle, tail
        };

        options = $.extend(defaults, options);

        this.each(function () {
            // get element text
            var $this = $(this);
            var text = $this.text();
            var origText = text;
            var origLength = origText.length;
            var origHeight = $this.height();

            // get height
            $this.text('a');
            var lineHeight = parseFloat($this.css("lineHeight"), 10);
            var rowHeight = $this.height();
            var gapHeight = lineHeight > rowHeight ? (lineHeight - rowHeight) : 0;
            var targetHeight = gapHeight * (options.row - 1) + rowHeight * options.row;

            if (origHeight <= targetHeight) {
                $this.text(text);
                options.callback.call(this);
                return;
            }
            var start = 1, length = 0;
            var end = text.length;
            if (options.position === 'tail') {
                while (start < end) { // Binary search for max length
                    length = Math.ceil((start + end) / 2);
                    $this.text(text.slice(0, length) + options['char']);
                    if ($this.height() <= targetHeight) {
                        start = length;
                    } else {
                        end = length - 1;
                    }
                }
                text = text.slice(0, start);
                if (options.onlyFullWords) {
                    text = text.replace(/[\u00AD\w\uac00-\ud7af]+$/, ''); // remove fragment of the last word together with possible soft-hyphen characters
                }
                text += options['char'];
            } else if (options.position === 'middle') {
                var sliceLength = 0;
                while (start < end) { // Binary search for max length
                    length = Math.ceil((start + end) / 2);
                    sliceLength = Math.max(origLength - length, 0);

                    $this.text(
                        origText.slice(0, Math.floor((origLength - sliceLength) / 2)) +
                        options['char'] +
                        origText.slice(Math.floor((origLength + sliceLength) / 2), origLength)
                    );

                    if ($this.height() <= targetHeight) {
                        start = length;
                    } else {
                        end = length - 1;
                    }
                }
                sliceLength = Math.max(origLength - start, 0);
                var head = origText.slice(0, Math.floor((origLength - sliceLength) / 2));
                var tail = origText.slice(Math.floor((origLength + sliceLength) / 2), origLength);

                if (options.onlyFullWords) {
                    // remove fragment of the last or first word together with possible soft-hyphen characters
                    head = head.replace(/[\u00AD\w\uac00-\ud7af]+$/, '');
                }
                text = head + options['char'] + tail;
            }
            $this.text(text);
            options.callback.call(this);
        });
        return this;
    };
})(jQuery);

+(function () {
    var Table = function (name, options, bindItem) {
        this.name = name
        this.options = options
        this.bindItem = bindItem
    }

    Table.DEFAULT = {
        el: {
            tbody: '',
            item: '',
            page: ''
        },
        dataConver: function (d) {
            return d
        },
        hasPage: true,
        hasCheck: true,
        req: {
            url: 'api/list.json',
            name: 'datas',
            data: {}
        },
        empty: {
            col: 0,
            html: ''
        },
        success: function () {
        }
    }
    Table.prototype = {
        setData: function (data) {
            var newData = this.options.req.data
            $.extend(newData, data)
            this.options.req['data'] = newData
        },
        addItem: function ($item) {
            this.$tableList.append($item)
        }
    }

    Table.prototype.init = function () {
        var el = this.options.el
        if (this.name) {
            this.$tableList = $('#' + this.name + 'List')
            this.$tableItem = $('#' + this.name + 'Item')
            this.pageSelector = '#' + this.name + 'Page'
        } else {
            this.$tableList = $(el.tbody)
            this.$tableItem = $(el.item)
            this.pageSelector = (el.page ? el.page : '#pagination')
        }
        var self = this
        if (self.options.hasPage) {
            ui.page2.init(function (num) {
                self.request(num, self)
            }, self.pageSelector)
        } else {
            self.request(null, self)
        }
    }

    Table.prototype.get_checked_ids = function (data) {
        var ids = []
        $.each(this.$tableList.find('input.ui-check:checked'), function () {
            ids.push($(this).parent().parent().data(data ? data : 'data').id)
        })
        return ids
    }

    Table.prototype.get_checked_atlids = function (data) {
        var ids = []
        $.each(this.$tableList.find('input.ui-check:checked'), function () {
            ids.push($(this).parent().parent().data(data ? data : 'data').atlId)
        })
        return ids
    }

    Table.prototype.request = function (num, self) {
        var emp = self.options.empty
        var $load = ui.loadingTable(emp.col)
        self.$tableList.empty()
        self.addItem($load)
        var req = self.options.req
        var info = req.data ? req.data : {}
        if (num) info['currentPage'] = num
        ui.post(req.url, info, function (d) {
            self.$tableList.empty();
            var datas = d[req.name]
            if (datas && datas.length > 0) {
                var tpl = doT.template(self.$tableItem.html())
                for (var i in datas) {
                    //数据转换
                    var itd
                    if (self.options.dataConver) {
                        itd = self.options.dataConver(datas[i], Number(i))
                    }
                    var $item = $(tpl(itd))
                    self.addItem($item)

                    //check item事件绑定
                    if (self.hasCheck) ui.check.addItem($item)

                    //绑定Itme事件
                    if (self.bindItem) {
                        self.bindItem($item, itd, Number(i))
                    }
                }
            } else {
                var item
                if (emp.col > 0) {
                    item = ui.emptyTable(emp.col, emp.html)
                } else {
                    item = emp.html
                }
                self.addItem(item)
            }
            if (self.options.hasPage) {
                ui.page2.setPage(d.page, self.pageSelector)
            }
            if (self.options.success) {
                self.options.success()
            }
        }, function (msg) {
            self.$tableList.empty();
            self.addItem(ui.emptyTable(emp.col, '发生错误'))
            ui.alert(msg)
        })
    }

    Table.prototype.refresh = function (data, func) {
        this.setData(data)
        if (func) this.options.success = func
        this.request(1, this)
    }

    ui.table = function (name, options, bindItem) {
        var newOptions = {}
        if (!options) options = {}
        $.extend(true, newOptions, Table.DEFAULT, options);
        var tab = new Table(name, newOptions, bindItem)
        // tab.init()
        return tab
    }
})(jQuery);

+(function ($) {
    var Upload = function (el, options) {
        this.$el = $(el);
        this.options = options
    }

    Upload.DEFAULT = {
        url: homePath + '/app/file/upload.json', //文件上传地址
        delUrl: homePath + '/app/file/delete.json', //文件删除地址
        data: {},
        accept: 'image/*',
        multiple: false
    }
    Upload.HTML = '<div class="ui-file-div">' +
        '<div class="ui-file-item choose">' +
        '<img src="' + homePath + '/image/common/icon_add.png"/>' +
        '<input type="file" name="_img">' +
        '</div> </div>'

    Upload.prototype = {
        init: function () {
            var self = this
            //加载js
            $.getScript(homePath + '/libs/upload/ajaxfileupload.js')
                .done(function () {
                    var $upload = $(Upload.HTML)
                    var $input = $upload.find('input[type=file]')
                    $input.attr('accept', self.options.accept)
                    if (self.options.multiple == true) {
                        $input.attr('multiple', true)
                    }
                    $input.on('change', function () {
                        var files = this.files
                        if (files && files.length > 0) {
                            self.clear()
                            for (var i = 0, len = files.length; i < len; i++) {
                                self.preview(files[i])
                            }
                        }
                    })

                    self.$el.append($upload)
                    self.$upload = $upload
                    self.$input = $input
                }).fail(function (e) {
                console.log(e)
            })
        },
        preview: function (f) {
            var self = this
            var reader = new FileReader()
            reader.onload = function (e) {
                self.addItem(e.target.result)
            }
            reader.readAsDataURL(f)
        },
        clear: function () {
            this.$upload.find('.ui-file-item').not('.choose').remove()
        },
        upload: function (func) {
            var self = this
            var files = self.$input[0].files
            if (files && files.length > 0) {
                $.ajaxFileUpload({
                    url: self.options.url,
                    data: self.options.data,
                    secureuri: false,
                    fileElementId: self.$input,
                    dataType: 'json',
                    success: function (res) {
                        if (res.ok) {
                            if (func) func(res.data)
                        } else {
                            ui.alert('上传失败')
                        }
                    }
                });
            } else {
                if (func) func()
            }
        },
        addItem: function (src) {
            var $item = $('<div class="ui-file-item">' +
                '<img src="' + src + '"/><span class="fa fa-close"></span></div>')
            $item.on('click', '.fa-close', function () {
                $(this).parent().remove()
                ui.clearFile(f)
            })
            this.$upload.append($item)
        },
        remove: function (fileId) {
            var self = this
            var url = self.options.delUrl + '?id=' + fileId
            ui.getURL(url, function () {
            }, function () {
                console.error('删除失败：' + fileId)
            })
        }
    }

    ui.uploader = function (el, options) {
        var newOption = $.extend({}, Upload.DEFAULT, options)
        var upload = new Upload(el, newOption)
        upload.init()
        return upload
    }
})(jQuery);