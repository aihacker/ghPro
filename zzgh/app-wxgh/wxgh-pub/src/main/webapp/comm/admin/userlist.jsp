<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/18
  Time: 16:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .breadcrumb {
        margin-bottom: 0;
    }

    input[type=checkbox] {
        float: right;
        margin-right: 10px;
        position: relative;
        top: 6px;
    }

    input[type=checkbox]:before {
        content: url(https://res.wx.qq.com/mmocbiz/zh_CN/tmt/pc/dist/img/comm/checkbox_78672677.png);
        display: block;
    }

    input[type=checkbox]:checked:before {
        content: url(https://res.wx.qq.com/mmocbiz/zh_CN/tmt/pc/dist/img/comm/checkbox_check_22de0796.png);
        display: block;
    }

    .ui-dept-list li a {
        cursor: pointer;
        color: #000;
    }

    .ui-dept-list li a img {
        width: 24px;
        height: 24px;
    }

    .ui-dept-list li {
        padding: 2px 6px;
    }

    .panel > .list-group:last-child .list-group-item:last-child {
        border-bottom: 1px solid #ddd;
    }

    .ui-list-panel {
        height: 280px;
        margin-bottom: 0;
        overflow-y: auto;
    }

    .breadcrumb li a {
        padding: 5px 0;
    }

    .ui-search-result {
        position: relative;
    }

    #searchResult {
        width: 100%;
        max-height: 270px;
        position: absolute;
        z-index: 999;
        overflow-y: auto;
        -webkit-box-shadow: 0 4px 8px rgba(0, 0, 0, .2);
        box-shadow: 0 4px 8px rgba(0, 0, 0, .2);
    }

    #searchResult li {
        cursor: pointer;
    }

    #searchResult ul.list-group {
        margin-bottom: 0;
    }

    #searchResult li img {
        width: 24px;
        height: 24px;
    }

    .ui-user-item img {
        width: 35px;
        height: 35px;
    }

    .ui-list-user {
        margin-bottom: 0;
        max-height: 198px;
        overflow-y: auto;
        margin-top: 15px;
    }

    .ui-user-item {
        text-align: center;
        position: relative;
        padding-top: 10px;
        padding-bottom: 10px;
        font-size: 11px;
    }

    .ui-user-item span.fa-close {
        position: absolute;
        top: 4px;
        right: -2px;
        cursor: pointer;
    }

    .ui-user-item p {
        margin-bottom: 0;
        margin-top: 4px;
    }

    .user-item {
        display: inline-block;
        margin: 5px;
    }
</style>

<div id="userlistModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title text-center">选择用户</h4>
            </div>

            <div class="modal-body ui-bg-lightgreen">
                <div class="container-fluid">
                    <div class="form-group">
                        <div class="ui-search-result">
                            <input id="searchInput" type="text" placeholder="搜索添加部门、成员" class="form-control">
                            <div id="searchResult" class="hidden">
                                <ul class="list-group">
                                </ul>
                            </div>
                        </div>
                    </div>

                    <ol class="breadcrumb" id="breadCrumb">
                        <li>
                            <a class="active">全部</a>
                        </li>
                    </ol>

                    <div class="panel panel-info ui-list-panel">
                        <ul class="list-group ui-dept-list" id="userdeptListUl">
                        </ul>
                    </div>
                    <div class="panel panel-info ui-list-user hidden">
                        <div class="container-fluid">
                            <div class="row" id="userListDiv">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button id="userModalBtn" type="button" class="btn btn-green">确认</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${home}/libs/jquery/jquery-ui.min.js"></script>
<script>

    (function ($) {

        var UserList = function (options) {
            this.options = options;
            this.$el = $("#userlistModal")
        }
        UserList.DEFAULT = {
            checkable_dept: false,
            checkable_user: true,
            callback: function (lists) {
            }
        }

        UserList.prototype.show = function (call) {
            this.$el.modal('show')
            if (call) this.options.callback = call
        }

        UserList.prototype.toggle = function () {
            this.$el.modal('toggle')
        }

        UserList.prototype.hide = function () {
            this.$el.modal('hide')
        }
        UserList.prototype.init = function () {
            this.$el.find('.modal-dialog').draggable();
            this.$el.css('overflow', 'hidden')

            var url = homePath + '/app/user/';
            var $ul = $('#userdeptListUl')

            var first = true;
            var $breadCrumb = $('#breadCrumb')

            var $searchReult = $('#searchResult')
            var $searchInput = $('#searchInput')
            var $userList = $('#userListDiv')
            var $userPanel = $('.ui-list-user')

            var self = this;

            //显示事件
            this.$el.on('show.bs.modal', function () {
                if (first) {
                    userlist();
                    first = false;
                }
            })

            $('#userModalBtn').click(function () {
                var userlist = [];
                $.each($userList.children(), function () {
                    var user = {};
                    var type = $(this).attr('data-type')
                    var id = $(this).attr('data-id')
                    var name = $(this).find('.ui-user-item p').text()
                    user['type'] = type
                    user['id'] = id
                    user['name'] = name
                    if (type == 1) {
                        user['avatar'] = $(this).find('.ui-user-item img').attr('src')
                    }
                    userlist.push(user)
                })
                self.hide()
                $ul.find('li[data-id] input:checkbox').attr('checked', false)
                $userList.empty()
                $userPanel.addClass('hidden')
                self.options.callback(userlist)
            })

            //搜索框搜索事件
            $searchInput.keyup(function () {
                var val = $(this).val().trim()
                if (val) {
                    $.getJSON(url + 'search.json', {
                        key: val
                    }, function (res) {
                        var users = res.data.users;
                        var depts = res.data.depts;
                        $searchReult.find('.list-group').empty()
                        for (var i in depts) {
                            var $li = $('<li data-id="' + depts[i].deptid + '" data-type="2" class="list-group-item">' +
                                '<img src="https://res.wx.qq.com/mmocbiz/zh_CN/tmt/pc/dist/img/icon-organization-24_714a2dc7.png"/>' +
                                ' <span>' + depts[i].deptname + '</span></li>')
                            $searchReult.find('.list-group').append($li)
                            if (self.options.checkable_dept) {
                                initSearchListEvent($li)
                            }
                        }
                        for (var j in users) {
                            var avatar = users[j].avatar;
                            avatar = avatar ? avatar : homePath + '/image/default/user.png'
                            var $li = $('<li data-id="' + users[j].userid + '" data-type="1" class="list-group-item">' +
                                '<img src="' + avatar + '"/>' +
                                ' <span>' + users[j].name + '</span></li>')
                            $searchReult.find('.list-group').append($li)
                            if (self.options.checkable_user) {
                                initSearchListEvent($li)
                            }
                        }
                        $searchReult.removeClass('hidden')
                    })
                }
            })

            function initSearchListEvent($li) {
                $li.click(function () {
                    var type = $(this).attr('data-type')
                    var id = $(this).attr('data-id')
                    var name = $(this).text()
                    $searchInput.val(name)
                    $searchReult.addClass('hidden')
                    var avatar = $(this).find('img').attr('src')
                    creauserlist(name, id, avatar, type);
                })
            }

            function userlist(id) {

                $.getJSON(url + 'list.json', {id: id}, function (res) {
                    if (res.ok) {
                        var users = res.data.users;
                        var depts = res.data.depts;
                        $ul.empty()
                        if (depts && depts.length > 0) {
                            for (var i = 0; i < depts.length; i++) {
                                var $li = $('<li data-type="2" data-id="' + depts[i].deptid + '" class="list-group-item">'
                                    + '<a class="btn btn-link"><img src="https://res.wx.qq.com/mmocbiz/zh_CN/tmt/pc/dist/img/icon-organization-24_714a2dc7.png"/> '
                                    + depts[i].deptname + '</a>' + (self.options.checkable_dept ? '<input class="ui-check" type="checkbox"/>' : '') + '</li>');
                                $ul.append($li)
                                initEvelt($li, 2)
                            }
                        }
                        if (users && users.length > 0) {
                            for (var j = 0; j < users.length; j++) {
                                var avatar = users[j].avatar
                                avatar = avatar ? avatar : homePath + '/image/default/user.png'
                                var $li = $('<li data-type="1" data-id="' + users[j].userid + '" class="list-group-item">'
                                    + '<a class="btn btn-link"><img src="' + avatar + '"/> '
                                    + users[j].name + '</a>' + (self.options.checkable_user ? '<input class="ui-check" type="checkbox"/>' : '') + '</li>')
                                $ul.append($li)
                                initEvelt($li, 1)
                            }
                        }
                    } else {
                        alert('获取失败');
                    }
                })
            }

            function initEvelt($li, type) {
                $li.on('change', '.ui-check', function () {
                    var name = $li.find('a').text();
                    var id = $li.attr('data-id')
                    var avatar = $li.find('img').attr('src')
                    if ($(this).is(':checked')) {
                        creauserlist(name, id, avatar, $li.attr('data-type'))
                    } else {
                        $userList.find('div[data-id=' + id + ']').remove()
                    }
                })
                if (type == 2) {
                    $li.on('click', 'a.btn', function () {
                        var id = $(this).parent().attr('data-id');
                        var name = $(this).text();
                        userlist(id)
                        $.each($breadCrumb.find('li'), function () {
                            $(this).removeClass('active')
                            var $a = $(this).find('a')
                            if ($a.html()) {
                                $a.addClass('btn btn-link')
                            } else {
                                var $i = $('<a class="btn btn-link">' + $(this).text() + '</a>')
                                $(this).empty().append($i)
                                initBreadCrumbEvent($i)
                            }
                        })
                        $breadCrumb.append('<li data-id="' + id + '" class="active">' + name + '</li>')
                    })
                }
            }

            function initBreadCrumbEvent($a) {
                $a.click(function () {
                    var $parent = $(this).parent();
                    var id = $parent.attr('data-id')
                    var text = $(this).text()

                    //删除后面的
                    $parent.nextAll().remove()
                    $(this).remove()
                    $parent.text(text)

                    userlist(id)
                })
            }

            function creauserlist(name, id, avatar, type) {
                avatar = avatar ? avatar : 'https://res.wx.qq.com/mmocbiz/zh_CN/tmt/pc/dist/img/icon-organization-24_714a2dc7.png';
                var $item = $('<div data-type="' + type + '" data-id="' + id + '" class="user-item">'
                    + '<div class="ui-user-item">'
                    + '<img src="' + avatar + '"/>'
                    + '<p>' + name + '</p>'
                    + '<span class="fa fa-close"></span></div></div>')
                $item.on('click', '.fa-close', function () {
                    var $par = $(this).parent().parent()

                    $ul.find('li[data-id=' + id + '] input:checkbox').attr('checked', false)

                    var $pp = $par.parent()
                    $par.remove()

                    if ($pp.children().length <= 0) {
                        $userPanel.addClass('hidden')
                    } else {
                        $userPanel.removeClass('hidden')
                    }
                })
                $userList.append($item)
                $userPanel.removeClass('hidden')
            }
        }

        window.userlist = function (options) {
            var newOptions = $.extend(UserList.DEFAULT, options ? options : options = {})
            var userli = new UserList(newOptions)
            userli.init()
            return userli
        }

    })(jQuery)
</script>