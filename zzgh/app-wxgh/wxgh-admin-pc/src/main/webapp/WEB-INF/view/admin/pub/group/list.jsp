<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 添加群组</a>
                <a id="delAllBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>图标</th>
                    <th>群组名称</th>
                    <th>简介</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="wxghList">
                </tbody>
            </table>
            <ul class="pagination" id="wxghPage"></ul>
            <div id="baiduMap"></div>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>

            <form class="col-md-4 col-md-offset-4" style="margin-top: 20px;">
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>名称</label>
                    <input type="text" name="name" class="form-control" placeholder="名称不能为空">
                </div>
                <div class="form-group">
                    <label>简介</label>
                    <textarea class="form-control" name="info" maxlength="200" rows="3"
                              placeholder="聊天群组介绍"></textarea>
                </div>
                <div id="avarteImg"></div>
                <div class="form-group">
                    <button id="addBtn" type="button" class="btn btn-theme">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/template" id="wxghItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td><img src="${home}{{=it.path}}"></td>
        <td>{{=it.name}}</td>
        <td>{{=it.info}}</td>
        <td>{{=it.addTime}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-danger ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    var type = '${param.type}';
    $(function () {
        ui.check.init();

        var $addForm = $('#addPane form')

        var upload = ui.uploader('#avarteImg')

        var wxghTable = ui.table('wxgh', {
            dataConver: function (d) {
                d['addTime'] = d.addTime ? (new Date(d.addTime).format('yyyy-MM-dd hh:mm')) : '未知时间'
                d['info'] = d.info ? d.info : '暂无简介'
                return d
            },
            req: {
                url: 'pub/group/api/list.json',
                data: {type: type ? type : 1}
            },
            empty: {
                col: 4,
                html: '暂无聊天群组哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'pub/group/api/del_group.json')
            })

            //编辑
            $item.on('click', '.ui-edit', function () {
                $addForm.data('data', d)
            })
            $item.on('shown.bs.tab', '.ui-edit', function () {
                var d = $addForm.data('data')
                if (d) {
                    $addForm.find('input[name=id]').val(d.id)
                    $addForm.find('input[name=name]').val(d.name)
                    $addForm.find('textarea[name=info]').val(d.info == '暂无简介' ? '' : d.info)
                    if (d.path) {
                        upload.addItem(homePath + d.path)
                    }
                }
            })
        })
        wxghTable.init()

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
            upload.clear()
        })

        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson()
            console.log(info)
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }

            upload.upload(function (fileIds) {
                if (fileIds && fileIds.length > 0) {
                    info['avatar'] = fileIds[0];
                }
                info['type'] = type
                var id = info['id']
                ui.post('pub/group/api/add_group.json', info, function () {
                    ui.alert((id ? '编辑' : '添加') + '成功', function () {
                        ui.refresh()
                    })
                }, function () {
                    if (info.avatar) {
                        upload.remove(info.avatar)
                    }
                })
            })
        })

        //批量删除
        $('#delAllBtn').on('click', function () {
            var ids = wxghTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的聊天群组！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    ui.refresh()
                })
            }, 'pub/group/api/del_group.json')
        })

        function verify(info) {
            if (!info['name']) {
                return '请输入群组名称'
            }
        }
    })
</script>