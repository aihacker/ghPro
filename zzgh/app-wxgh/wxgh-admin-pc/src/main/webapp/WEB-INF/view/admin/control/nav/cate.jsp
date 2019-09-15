<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/10
  Time: 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="ui-content">
        <div class="ui-link-group">
            <a data-toggle="modal" data-target="#addCateModal" class="btn btn-empty-theme"><span
                    class="fa fa-plus"></span> 添加分类</a>
        </div>

        <table class="table ui-table">
            <thead>
            <tr>
                <th><input class="ui-check-all" type="checkbox"/></th>
                <th>菜单名称</th>
                <th>菜单介绍</th>
                <th>可用状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="myList">
            </tbody>
        </table>
    </div>
</div>

<div id="addCateModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加分类</h4>
            </div>
            <div class="modal-body">
                <form id="cateAddForm">
                    <div class="form-group">
                        <label>分类名称</label>
                        <input type="text" name="name" class="form-control" placeholder="Nav菜单名称">
                    </div>
                    <div class="form-group">
                        <label>菜单介绍</label>
                        <textarea class="form-control" name="info" maxlength="200" rows="3"
                                  placeholder="Nav菜单介绍"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveCateBtn" type="button" class="btn btn-theme">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="myItem">
    <tr>
        <td><input class="ui-check" type="checkbox"/></td>
        <td>{{=it.name}}</td>
        <td>{{=it.info}}</td>
        <td>{{=it.statusTxt}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>

<script>
    $(function () {
        var $form = $('#cateAddForm')
        $form.form()
        ui.check.init()

        var myTable = ui.table('my', {
            req: {
                url: 'control/nav/api/cate_list.json',
            },
            dataConver: function (d) {
                d['statusTxt'] = ui.get_status(d.status, ['不可用', '可用'])
                d['info'] = d.info ? d.info : '暂无介绍'
                return d
            },
            hasPage: false,
            empty: {
                col: 5,
                html: '暂无菜单分类哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'control/nav/api/del_cate.json')
            })
        })
        myTable.init()

        $('#saveCateBtn').on('click', function () {
            var info = $form.serializeJson()
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }
            ui.post('control/nav/api/add_cate.json', info, function () {
                $('#addCateModal').modal('hide')
                $form.clearForm()
                ui.alert('添加成功', function () {
                    myTable.request(null, myTable)
                })
            })
        })

        function verify(info) {
            if (!info['name']) {
                return 'Nav菜单名称不能为空哦'
            }
        }
    })
</script>
