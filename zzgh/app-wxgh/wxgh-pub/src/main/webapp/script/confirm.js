function Confirm(title, content, okFunc, showFunc) {
    var html = '<div class="weui_dialog_confirm" style="display: none;">' +
        '<div class="weui_mask"></div>' +
        '<div class="weui_dialog">' +
        '<div class="weui_dialog_hd"><strong class="weui_dialog_title">' + title + '</strong></div>' +
        '<div class="weui_dialog_bd">' + content + '</div>' +
        '<div class="weui_dialog_ft">' +
        '<a href="javascript:;" class="weui_btn_dialog default">取消</a>' +
        '<a href="javascript:;" class="weui_btn_dialog primary">确定</a>' +
        '</div></div></div>'

    var $cnt = $(html)
    $('body').append($cnt)

    var $dialog = $cnt.find('.weui_dialog')

    var self = this
    $cnt.on('tap', 'a.weui_btn_dialog', function () {
        var index = $(this).index()
        if (index == 1) {
            if (okFunc) okFunc($dialog)
            if (self.autoClose) {
                $cnt.fadeOut(300)
            }
        } else {
            $cnt.fadeOut(300)
        }
    })

    this.$dialog = $dialog
    this.$cnt = $cnt
    this.showFunc = showFunc
}
Confirm.prototype = {
    setTitle: function (title) {
        this.$dialog.find('.weui_dialog_title').text(title)
    },
    setContent: function (content) {
        var $bd = this.$dialog.find('.weui_dialog_bd')
        $bd.html('')
        $bd.append(content)
    },
    appendContent: function (content) {
        var $bd = this.$dialog.find('.weui_dialog_bd')
        $bd.append(content)
    },
    show: function (auto) {
        var self = this
        if (typeof(auto) == 'undefined') {
            self.autoClose = true
        } else {
            self.autoClose = auto
        }
        self.$cnt.fadeIn(300, function () {
            if (self.showFunc) self.showFunc(self)
        })
    },
    hide: function () {
        this.$cnt.fadeOut(300)
    }
}