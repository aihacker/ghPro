<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .group-list {
        width: 220px;
        float: left;
    }

    .groupuser-list {
        margin-left: 240px;
    }
</style>
<div class="row">
    <div class="ui-content">
        <div class="group-list">
            <div class="list-group" id="groupList">
                <c:forEach items="${groups}" var="g" varStatus="i">
                    <a data-groupid="${g.groupId}" data-id="${g.id}"
                       class="list-group-item${i.first?' active':''}">${g.name}</a>
                </c:forEach>
            </div>
        </div>
        <div class="groupuser-list">
            <div class="ui-link-group">
                <a href="#addUserModal" data-toggle="modal" class="btn btn-empty-theme"><span class="fa fa-plus"></span>
                    添加成员</a>
                <a id="delAllBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>姓名</th>
                    <th>联系电话</th>
                    <th>部门</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="wxghList">
                </tbody>
            </table>
            <ul class="pagination" id="wxghPage"></ul>
        </div>
    </div>
</div>

<div class="modal fade" role="dialog" id="addUserModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label>用户选择</label>
                        <a id="chooseUserBtn" class="btn btn-link">点击选择</a>
                        <small>请选择</small>
                        <input type="hidden" name="userid">

                    </div>
                    <div class="form-group">
                        <label>用户备注</label>
                        <textarea class="form-control" name="remark" maxlength="200" rows="3"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="addUserBtn" type="button" class="btn btn-theme">确认添加</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="wxghItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td><img src="{{=it.avatar}}">{{=it.username}}</td>
        <td>{{=it.mobile}}</td>
        <td>{{=it.deptname}}</td>
        <td>{{=it.remark}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-danger ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑备注</a>
                {{? it.type != 1}}
                <a class="btn btn-empty-theme ui-set"><span class="fa fa-cog"></span> 设置管理员</a>
                {{??}}
                <a class="btn btn-empty-theme ui-cancel"><span class="fa fa-cog"></span> 取消管理员</a>
                {{?}}
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<jsp:include page="/comm/admin/userlist.jsp"/>
<script>

    var type = '${param.type}';
    $(function () {
        ui.check.init()

        var $chooseUser = $("#chooseUserBtn")
        var $form = $('#addUserModal form')

        var userlist = window.userlist({
            callback: function (lists) {
                var users = []
                $.each(lists, function () {
                    users.push(this.id)
                })
                if (users.length > 0) {
                    var $userid = $form.find('input[name=userid]')
                    $userid.next().text('已选择')
                    $userid.val(users.toString())
                }
            }
        })

        var $groupList = $('#groupList')
        var cfg = {
            dataConver: function (d) {
                d['remark'] = d.remark ? d.remark : '无'
                return d
            },
            req: {
                url: 'pub/group/api/list_user.json'
            },
            empty: {
                col: 6,
                html: '暂无聊天群组哦'
            }
        }
        var groupId = $groupList.find('.list-group-item').eq(0).data('id')
        cfg.req['data'] = {groupId: groupId}
        var wxghTable = ui.table('wxgh', cfg, function ($item, d) {
            $item.data('data', d)
            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'pub/group/api/del_user.json')
            })

            //设置为管理员
            $item.on('click', '.ui-set', function () {
                ui.confirm('是否设置为管理员？', function () {
                    edit_type(1, d.id);
                })
            })

            //取消管理员
            $item.on('click', '.ui-cancel', function () {
                edit_type(2, d.id);
            })

            //编辑备注
            $item.on('click', '.ui-edit', function () {
                ui.alert('暂未开放');
            })
        })
        wxghTable.init()

        //批量删除
        $('#delAllBtn').on('click', function () {
            var ids = wxghTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的用户！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    ui.refresh()
                })
            }, 'pub/group/api/del_user.json')
        })

        //点击选择
        $chooseUser.on('click', function () {
            userlist.show()
        })

        //确认添加
        $('#addUserBtn').on('click', function () {
            var info = $form.serializeJson()
            if (!info['userid']) {
                ui.alert('请选择用户！')
                return
            }
            var groupId = $groupList.find('.list-group-item.active').data('groupid')
            info['groupId'] = groupId;
            ui.post('pub/group/api/add_user.json', info, function () {
                ui.alert('添加成功！', function () {
                    $('#addUserModal').modal('hide')
                })
            })
        })

        $groupList.on('click', '.list-group-item', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                var id = $self.data('id')
                wxghTable.refresh({groupId: id}, function () {
                    $groupList.find('.list-group-item.active').removeClass('active')
                    $self.addClass('active')
                })
            }
        })

        function edit_type(type, id) {
            ui.post('pub/group/api/add_user.json', {id: id, type: type}, function () {
                ui.alert('操作成功！', function () {
                    ui.refresh()
                })
            })
        }
    })
</script>