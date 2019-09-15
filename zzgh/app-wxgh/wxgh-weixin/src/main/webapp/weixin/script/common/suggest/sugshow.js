/**
 * Created by XDLK on 2016/9/27.
 */
//评论列表
var commentList = {
    init: function () {
        this.self = wxgh.query('.ui-panel-cnt')
        this.request()
    },
    request: function () {
        var url = homePath + '/wx/common/suggest/show/list.json';
        var self = this
        mui.getJSON(url, {id: sugId}, function (res) {
            self.createlist(res.data)
        })
    },
    createItem: function (comm) {
        var e_li = document.createElement("li")
        e_li.className = "mui-table-view-cell mui-media"
        e_li.setAttribute('id', 'item_' + comm.id)

        //头像
        var e_img = document.createElement("img")
        e_img.className = "mui-media-object mui-pull-left"
        e_img.src = wxgh.get_avatar(comm.avatar)

        //body
        var e_body = document.createElement("div")
        e_body.className = "mui-media-body"
        e_body.innerHTML = comm.username + '<p>' + comm.content + '</p>'

        //回复列表
        var e_comment
        if (comm.suggestComms && comm.suggestComms.length > 0) {
            e_comment = document.createElement("div")
            e_comment.className = "ui-item-comment"

            for (var j = 0; j < comm.suggestComms.length; j++) {
                var childComm = comm.suggestComms[j]

                var e_item = document.createElement("div")
                e_item.className = "ui-item"
                e_item.innerText = childComm.username + "：" + childComm.content

                e_comment.appendChild(e_item)
            }
        }

        //footer
        var e_footer = document.createElement("div")
        e_footer.className = "ui-item-footer"

        var e_small_time = document.createElement('small')
        e_small_time.className = 'ui-text-info'
        e_small_time.innerText = comm.timeStr

        var e_iconDiv = document.createElement('div')
        e_iconDiv.className = 'mui-pull-right ui-icon-div'
        e_iconDiv.setAttribute('data-id', comm.id)

        var e_icon_lov = document.createElement('a')
        e_icon_lov.className = 'ui-item-lov'
        e_icon_lov.href = 'javascript:;'

        if (comm.lovIs == true) {
            e_icon_lov.classList.add('mui-disabled')
        }

        var e_icon_lov_icon = document.createElement('span')
        e_icon_lov_icon.className = 'fa'

        var e_icon_lov_numb = document.createElement('span')
        e_icon_lov_numb.className = 'ui-lov-numb'
        e_icon_lov_numb.innerText = comm.lovNum

        if (comm.lovIs == true) {
            e_icon_lov_icon.className = 'fa fa-heart ui-text-danger';
            e_icon_lov_numb.classList.add('ui-text-danger')
        } else {
            e_icon_lov_icon.className = 'fa fa-heart-o'
        }
        e_icon_lov.appendChild(e_icon_lov_icon)
        e_icon_lov.appendChild(e_icon_lov_numb)

        var e_icon_reply = document.createElement('a')
        e_icon_reply.href = 'javascript:;'
        e_icon_reply.innerHTML = '<span class="fa fa-commenting-o ui-item-reply"></span>'

        var e_icon_other = document.createElement('a')
        e_icon_other.href = 'javascript:;'
        e_icon_other.className = 'ui-item-other'
        e_icon_other.innerHTML = '<span class="fa fa-ellipsis-h"></span>'

        e_iconDiv.appendChild(e_icon_lov)
        e_iconDiv.appendChild(e_icon_reply)
        e_iconDiv.appendChild(e_icon_other)


        e_footer.appendChild(e_small_time)
        e_footer.appendChild(e_iconDiv)

        //e_footer.innerHTML = '<small class="ui-text-info">' + comm.timeStr + '</small><div class="mui-pull-right ui-icon-div" data-id="' + comm.id + '"> ' +
        //    '<a class="ui-item-lov' + (comm.lovIs ? ' mui-disabled' : '') + '" href="javascript:;">' +
        //    '<span class="fa ' + (comm.lovIs ? 'fa-heart ui-text-danger' : 'fa-heart-o') + '"></span>&nbsp;<span class="ui-lov-numb' + (comm.lovIs ? ' ui-text-danger' : '') + '">' + comm.lovNum + '</span></a> ' +
        //    '<a href="javascript:;"><span class="fa fa-commenting-o ui-item-reply"></span></a>' +
        //    '<a href="javascript:;"><span class="fa fa-ellipsis-h"></span></a></div>'

        e_li.appendChild(e_img)
        e_li.appendChild(e_body)
        if (e_comment) e_li.appendChild(e_comment)
        e_li.appendChild(e_footer)

        this.initItemEvent(e_li)

        return e_li
    },
    //初始化评论列表事件
    initItemEvent: function (e_li) {
        e_li.querySelector('.ui-item-reply').addEventListener('tap', function (e) {
            e.stopImmediatePropagation()
            wxgh.hide(replyBtn.btnGroups)
            wxgh.show(replyBtn.commentInputDiv)
            replyBtn.replyInput.focus()
            replyBtn.commentBtn.setAttribute('data-type', 2)
            replyBtn.commentBtn.setAttribute('data-id', this.parentNode.parentNode.getAttribute('data-id'))
        })

        //喜欢按钮点击
        e_li.querySelector('.ui-item-lov').addEventListener('tap', function (e) {
            e.stopImmediatePropagation()
            var self_el = this;
            if (!self_el.classList.contains('mui-disabled')) {
                var thId = this.parentNode.getAttribute('data-id')
                var url = homePath + "/wx/common/suggest/show/lov.json?id=" + thId + "&type=2";
                mui.getJSON(url, function (res) {
                    if (res.ok) {
                        var e_icon = self_el.querySelector('.fa')
                        var e_label = self_el.querySelector('.ui-lov-numb')
                        e_icon.classList.remove('fa-heart-o')
                        e_icon.classList.add('fa-heart')
                        e_icon.classList.add('ui-text-danger')
                        e_label.classList.add('ui-text-danger')
                        e_label.innerText = Number(e_label.innerText.trim()) + 1
                        self_el.classList.add('mui-disabled')
                    } else {
                        mui.toast(res.msg)
                    }
                })
            }
        })

        //其他按钮
        e_li.querySelector('.ui-item-other').addEventListener('tap', function () {
            mui('#otherPopover').popover('toggle')
        })
    },
    createlist: function (comms) {
        if (comms && comms.length > 0) {
            var ul_e = document.createElement("ul")
            ul_e.className = "mui-table-view"

            for (var i = 0; i < comms.length; i++) {
                ul_e.appendChild(this.createItem(comms[i]))
            }

            this.self.innerHTML = ''
            this.self.appendChild(ul_e)
        } else {
            this.self.innerHTML = '<div class="mui-content-padded mui-text-center ui-text-info">暂无评论哦</div>'
        }
    }
}

//喜欢按钮
var lovBtns = {
    init: function () {
        var self = wxgh.getElement('lovBtn')
        self.addEventListener('tap', this.lovClick)
        this.self = self
    },
    lovClick: function (e) {
        if (!this.classList.contains('mui-disabled')) {
            lovBtns.request()
        }
    },
    changeStatus: function (type) {
        var e_icon = this.self.querySelector('.fa')
        var e_label = this.self.querySelector('.mui-tab-label')
        if (type == 1) {
            e_icon.classList.remove('fa-heart-o')
            e_icon.classList.add('fa-heart')
            e_icon.classList.add('ui-text-danger')
            e_label.classList.add('ui-text-danger')
            e_label.innerText = Number(e_label.innerText.trim()) + 1
            this.self.classList.add('mui-disabled')
        } else {
            e_label.classList.remove('ui-text-danger')
            e_icon.classList.remove('ui-text-danger')
            e_icon.classList.remove('fa-heart')
            e_icon.classList.add('fa-heart-o')
            e_label.innerText = Number(e_label.innerText.trim()) - 1
            this.self.classList.remove('mui-disabled')
        }
    },
    //更新喜欢数量
    request: function () {
        var url = homePath + "/wx/common/suggest/show/lov.json?id=" + sugId + "&type=1";
        var self = this
        mui.getJSON(url, function (res) {
            if (res.ok) {
                self.changeStatus(1)
            } else {
                mui.toast(res.msg)
            }
        })
    }
}

//回复按钮
var replyBtn = {
    init: function () {
        var self = wxgh.getElement('replyBtn')
        self.addEventListener('tap', this.click)
        this.btnGroups = wxgh.getElement('suggestBtngroup')

        //回复按钮点击事件
        this.commentInputDiv = wxgh.query('.ui-suggest-footer.ui-comment')
        this.commentBtn = this.commentInputDiv.querySelector('button')
        this.commentBtn.addEventListener('tap', this.reply)

        //评论数量
        this.replyNumb_e = self.querySelector('.mui-tab-label')
        this.replyInput = this.commentInputDiv.querySelector('input')

        wxgh.query('.mui-scroll-wrapper').addEventListener('tap', function () {
            wxgh.hide(replyBtn.commentInputDiv)
            wxgh.show(replyBtn.btnGroups)
        })

        this.self = self
    },
    click: function (e) {
        wxgh.hide(replyBtn.btnGroups)
        wxgh.show(replyBtn.commentInputDiv)
        replyBtn.replyInput.focus()
        replyBtn.commentBtn.setAttribute('data-type', 1)
    },
    //评论按钮点击事件
    reply: function (e) {

        var msugId = this.getAttribute('data-id') //ID
        var mtype = this.getAttribute("data-type") //评论类型、回复或评论

        var e_input = replyBtn.replyInput
        if (!e_input.value.trim()) {
            mui.toast("评论内容不能为空哦")
            return
        }
        if (!this.loading) {
            this.loading = new ui.loading('评论中，请稍候...')
        }
        wxgh.disabled(this)
        this.loading.show()

        var url = homePath + '/wx/common/suggest/show/comment.json'
        var data = {
            content: e_input.value,
            sugId: sugId
        }
        if (mtype && mtype == 2) { //回复消息，带上parentId
            data['parentid'] = msugId
        }
        var self = this
        mui.post(url, data, function (res) {
            if (res.ok) {
                e_input.value = ''
                if (res.data) {
                    replyBtn.createItem(mtype, res.data)
                }
                if(!data['parentid'])
                replyBtn.replyNumb_e.innerText = Number(replyBtn.replyNumb_e.innerText.trim()) + 1

                self.loading.hide()
            } else {
                self.loading.hide()
                mui.toast(res.msg)
            }
            wxgh.no_disabled(self)
        }, 'json')
    },
    createItem: function (type, data) {
        if (type == 1) {
            var item = commentList.createItem(data)
            if (commentList.self.querySelector('.mui-loading')) {
                commentList.self.innerHTML = ''
            }
            var e_ul = commentList.self.querySelector('.mui-table-view')

            if (e_ul) {
                // var first_li = e_ul.querySelector('li:first-child')
                // if (first_li)
                //     e_ul.insertBefore(item, first_li)
                // else
                    e_ul.appendChild(item)
            } else {
                e_ul = document.createElement("ul")
                e_ul.className = "mui-table-view"

                e_ul.appendChild(item)

                commentList.self.appendChild(e_ul)
            }
        } else {

            var e_item = document.createElement('div')
            e_item.className = 'ui-item'
            e_item.innerText = data.username + '：' + data.content

            var e_li = wxgh.getElement('item_' + data.parentid)
            var e_comment = e_li.querySelector('.ui-item-comment')
            if (e_comment) {
                //var firstItem = e_comment.querySelector('.ui-item:first-child')
                //if (firstItem) e_comment.insertBefore(e_item, firstItem)
                //else
                e_comment.appendChild(e_item)
            } else {
                e_comment = document.createElement('div')
                e_comment.className = 'ui-item-comment'
                e_comment.appendChild(e_item)

                e_li.insertBefore(e_comment, e_li.querySelector('.ui-item-footer'))
            }
        }
    }
}

window.onload = function () {
    commentList.init()
    lovBtns.init()
    replyBtn.init()

    mui('#otherPopover').on('tap', '.mui-popover-action li>a', function () {
        mui('#otherPopover').popover('toggle');
        if (this.innerText != '取消') {
            mui.later(function () {
                mui.toast('举报成功')
            }, 500)
        }
    })

}