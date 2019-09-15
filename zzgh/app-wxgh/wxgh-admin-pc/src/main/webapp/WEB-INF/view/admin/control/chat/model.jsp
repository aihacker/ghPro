<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/11
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .chat-type .list-group-item {
        padding: 8px 15px;
        cursor: pointer;
    }

    .chat-type .list-group-item:first-child,
    .chat-type .list-group-item:last-child {
        border-radius: 0;
    }

    .chat-type a,
    .chat-type a:hover,
    .chat-type a:active,
    .chat-type a:focus {
        color: #000;
        text-decoration: none;
    }

    .chat-type .list-group-item.active,
    .chat-type .list-group-item.active:focus,
    .chat-type .list-group-item.active:hover {
        z-index: 2;
        color: #fff;
        background-color: #337ab7;
        border-color: #337ab7;
    }
</style>

<div class="row">
    <div class="col-lg-2 col-md-2 col-sm-3">
        <ul class="list-group chat-type">
            <li data-type="1" class="list-group-item active">
                <a href="#group">兴趣协会</a>
            </li>
            <li data-type="2" class="list-group-item">
                <a href="#team">项目团队</a>
            </li>
            <li data-type="3" class="list-group-item">
                <a href="#tribe">青年部落</a>
            </li>
            <li data-type="4" class="list-group-item">
                <a href="#di">清风纪语</a>
            </li>
            <li data-type="5" class="list-group-item">
                <a href="#party">党建在线</a>
            </li>
        </ul>
    </div>
    <div class="col-lg-10 col-md-10 col-sm-9">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="ui-link-group">
                    <a id="batchDel" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                    <a href="#addModel" data-toggle="modal" class="btn btn-empty-theme"><span
                            class="fa fa-plus-square-o"></span> 新增功能</a>
                </div>
                <div id="chatTabPane" class="tab-content"></div>
            </div>
        </div>
    </div>
</div>

<div id="addModel" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加菜单</h4>
            </div>
            <div class="modal-body">
                <form id="addForm">
                    <div class="form-group">
                        <label>名&nbsp;&nbsp;称</label>
                        <input type="text" name="name" class="form-control" placeholder="功能名称">
                    </div>
                    <div class="form-group">
                        <label>链&nbsp;&nbsp;接</label>
                        <input type="text" name="url" class="form-control" placeholder="功能链接">
                    </div>
                    <div class="form-group">
                        <label>介&nbsp;&nbsp;绍</label>
                        <textarea class="form-control" name="info" maxlength="200" rows="3"
                                  placeholder="功能介绍"></textarea>
                    </div>
                    <div id="modelImg">

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveBtn" type="button" class="btn btn-theme">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="tabPane">
    <div class="tab-pane">
        <table class="ui-table table">
            <thead>
            <tr>
                <th><input type="checkbox" class="ui-check-all"></th>
                <th>名称</th>
                <th>介绍</th>
                <th>图标</th>
                <th>链接</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody class="model-list">

            </tbody>
        </table>
    </div>
</script>
<script type="text/template" id="tabItem">
    <tr>
        <td><input type="checkbox" class="ui-check"></td>
        <td>{{=it.name}}</td>
        <td>{{=it.info}}</td>
        <td><img src="${home}{{=it.path}}"/></td>
        <td>{{=it.url}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var $tabPane = $('#chatTabPane')
        var tabpaneHtml = $('#tabPane').html()
        $('#tabPane').remove()

        var $addModal = $('#addModel')
        $addModal.data('type', 1)
        var itemTpl = doT.template($('#tabItem').html())

        $.each($('.chat-type li.list-group-item'), function (index) {
            var $self = $(this)
            var $html = $(tabpaneHtml)
            var id = $self.find('a[href]').attr('href')
            $html.attr('id', id.substring(1, id.length))
            if (index == 0) {
                $html.addClass('active')

                list_model($self.data('type'), $html.find('.model-list'), function () {
                    $self.data('request', true)
                })
            }
            $tabPane.append($html)
        })

        ui.check.init()

        var upload = ui.uploader('#modelImg')

        $('.chat-type').on('click', 'li.list-group-item', function (e) {
            e.preventDefault()
            var $self = $(this)
            var $a = $self.find('a[href]')
            var $pan = $($a.attr('href'))
            if (!$self.hasClass('active')) {
                $self.parent().find('li.list-group-item.active').removeClass('active')
                $self.addClass('active')

                $pan.parent().find('.tab-pane.active').removeClass('active')
                $pan.addClass('active')
            }
            var type = $self.data('type')
            if (!$self.data('request')) {
                list_model(type, $pan.find('tbody.model-list'), function () {
                    $self.data('request', true)
                })
            }
            $addModal.data('type', $self.data('type'))
        })

        //批量删除
        $('#batchDel').on('click', function () {
            var ids = get_checked_id()
            if (ids.length <= 0) {
                ui.alert('请选择删除的功能哦！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！')
                ui.refresh()
            }, 'control/chat/api/del_model.json')
        })

        //添加功能
        var $addForm = $('#addForm')
        $('#saveBtn').on('click', function () {
            var info = $addForm.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }
            info['type'] = $addModal.data('type')
            upload.upload(function (fileId) {
                info['icon'] = fileId[0]
                ui.post('control/chat/api/add_model.json', info, function () {
                    ui.alert('添加成功！', function () {
                        $addForm.clearForm()
                        $('#addModel').modal('hide')
                    })
                })
            })
        })

        function list_model(type, $pane, func) {
            ui.get('control/chat/api/list_model.json', {type: type}, function (models) {
                if (models && models.length > 0) {
                    for (var i in models) {
                        var d = models[i]
                        var $item = $(itemTpl(d))
                        $item.data('data', d)
                        ui.check.addItem($item)
                        $pane.append($item)
                        init_item_event($item, d)
                    }
                } else {
                    $pane.append(ui.emptyTable(6, '暂无功能'))
                }
                if (func) func()
            })
        }

        function init_item_event($item, d) {
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'control/chat/api/del_model.json')
            })
        }

        function get_checked_id() {
            var ids = []
            $.each($tabPane.find('.tab-pane.active tbody.model-list').find('input.ui-check:checked'), function () {
                ids.push($(this).parent().parent().data('data').id)
            })
            return ids
        }

        function verify(info) {
            if (!info['name']) {
                return '功能名称不能为空'
            }
            if (!info['url']) {
                return '功能链接不能为空'
            }
        }
    })
</script>