<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    .checkbox-inline + .checkbox-inline {
        margin: 0;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 添加管理员</a>
                <a id="delAllBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            </div>

            <div style="margin-top: 20px;">
                <ul id="cateNav" class="nav nav-tabs">
                    <c:forEach items="${cates}" var="c" varStatus="i">
                        <li data-id="${c.id}" class="${i.first?'active':''}">
                            <a title="${c.info}" href="javascript:;">${c.name} <i
                                    class="fa fa-spinner fa-pulse hidden"></i></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>管理员账号</th>
                    <th>管理员密码</th>
                    <th>权限</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="wxghList">
                </tbody>
            </table>
            <ul class="pagination" id="wxghPage"></ul>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>

            <form class="col-md-4 col-md-offset-4" style="margin-top: 20px;">
                <div class="form-group">
                    <label>管理员账号&nbsp;&nbsp;<span title="用户管理员登录系统" class="fa fa-question-circle-o"></span></label>
                    <input type="text" name="name" class="form-control" placeholder="账号唯一，不能为空">
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input type="password" name="password" class="form-control" placeholder="默认密码：123456">
                </div>
                <div class="form-group">
                    <label>再次密码</label>
                    <input type="password" name="repassword" class="form-control" placeholder="默认密码：123456">
                </div>
                <div class="form-group">
                    <c:forEach items="${cates}" var="c" varStatus="i">
                        <label title="${c.info}" class="checkbox-inline">
                            <input name="cateId" type="checkbox" value="${c.id}"> ${c.name}
                        </label>
                    </c:forEach>
                </div>
                <div class="form-group">
                    <label>备注</label>
                    <textarea class="form-control" name="remark" maxlength="200" rows="3"
                              placeholder="管理员备注"></textarea>
                </div>
                <div class="form-group">
                    <button id="addBtn" type="button" class="btn btn-theme">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑管理员</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <c:forEach items="${cates}" var="c" varStatus="i">
                            <label title="${c.info}" class="checkbox-inline">
                                <input name="cateId" type="checkbox" value="${c.id}"> ${c.name}
                            </label>
                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea class="form-control" name="remark" maxlength="200" rows="3"
                                  placeholder="管理员备注"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="editBtn" type="button" class="btn btn-theme">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="wxghItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.name}}</td>
        <td>******</td>
        <td>{{=it.cateName}}</td>
        <td>{{=it.remark}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-danger ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
        ui.check.init()

        var $addForm = $('#addPane form');
        var $editForm = $('#editModal form');
        var $editModal = $('#editModal');

        var $cateNav = $('#cateNav')

        var wxghTable = ui.table('wxgh', {
            dataConver: function (d) {
                d['remark'] = d.remark ? d.remark : '无';
                return d;
            },
            req: {
                url: 'control/admin/api/list_admin.json',
                data: {cateId: $cateNav.find('li:eq(0)').data('id')}
            },
            empty: {
                col: 6,
                html: '暂无管理员哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'control/admin/api/del.json')
            })

            //编辑
            $item.on('click', '.ui-edit', function () {
                $editForm.find('textarea[name=remark]').val(d.remark == '无' ? '' : d.remark);
                var cates = d.cateId.split(',');
                $.each(cates, function (index) {
                    $editForm.find('input[name="cateId"][value="' + cates[index] + '"]').prop('checked', true);
                })
                $editModal.modal('show');
                $editModal.data('admin', d);
            })
        })
        wxghTable.init()

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
        })

        //新增管理员
        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson()
            if (info.cateId) info['cateId'] = info.cateId.toString();
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }

            ui.post('control/admin/api/add.json', info, function () {
                ui.alert('新增成功', function () {
                    ui.refresh()
                })
            })
        })

        //编辑管理员
        $('#editBtn').on('click', function () {
            var info = $editForm.serializeJson();
            if (info['cateId']) {
                info['cateId'] = info.cateId.toString();
            }
            var d = $editModal.data('admin');
            if (info['remark'] == d['remark'] && d['cateId'] == info['cateId']) {
                $editModal.modal('hide');
                return;
            }
            info['id'] = d.id;
            ui.post('control/admin/api/edit.json', info, function () {
                $editModal.modal('hide');
                ui.alert('编辑成功！', function () {
                    $editForm.clearForm();
                    ui.refresh();
                })
            })
        })

        //批量删除
        $('#delAllBtn').on('click', function () {
            var ids = wxghTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的管理员！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    ui.refresh()
                })
            }, 'control/admin/api/del.json')
        });

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                wxghTable.refresh({cateId: $self.data('id')}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

        function verify(info) {
            if (!info['name']) {
                return '管理员账号不能为空'
            }
            if (info['password'] != info['repassword']) {
                return '两次密码不相同'
            }
            if (!info['cateId']) {
                return '请选择管理员身份'
            }
        }
    })
</script>