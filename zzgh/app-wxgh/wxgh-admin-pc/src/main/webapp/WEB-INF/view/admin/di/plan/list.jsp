<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 添加计划</a>
                <a id="delAllBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            </div>

            <div style="margin-top: 20px;">
                <ul id="cateNav" class="nav nav-tabs">
                    <c:forEach items="${groups}" var="c" varStatus="i">
                        <li data-id="${c.value}" class="${i.first?'active':''}">
                            <a href="javascript:;">${c.name} <i class="fa fa-spinner fa-pulse hidden"></i></a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>计划时间</th>
                    <th>计划内容</th>
                    <th>创建时间</th>
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
                <input type="hidden" name="groupId">
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>开始时间</label>
                    <input type="text" name="startTime" class="form-control ui-input-time">
                </div>
                <div class="form-group">
                    <label>结束时间</label>
                    <input type="text" name="endTime" class="form-control ui-input-time">
                </div>
                <div class="form-group">
                    <label>计划内容</label>
                    <textarea class="form-control" name="content" maxlength="200" rows="3"></textarea>
                </div>
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
        <td title="{{=it.time}}">{{=it.startTime}}-{{=it.endTime}}</td>
        <td>{{=it.content}}</td>
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
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"/>
<script>
    $(function () {
        ui.check.init()

        var $addForm = $('#addPane form')
        var $cateNav = $('#cateNav');

        $(".ui-input-time").datetimepicker({format: 'yyyy-mm-dd hh:ii', autoclose: true})

        var defaultId = $cateNav.find('li.active').data('id');
        $addForm.find('input[name=groupId]').val(defaultId);

        var wxghTable = ui.table('wxgh', {
            dataConver: function (d) {
                d['time'] = new Date(d.startTime).format('yyyy-MM-dd hh:mm') + '-' + new Date(d.endTime).format('yyyy-MM-dd hh:mm');
                d['startTime'] = new Date(d.startTime).format('yyyy-MM-dd hh:mm');
                d['endTime'] = new Date(d.endTime).format('yyyy-MM-dd hh:mm');
                d['addTime'] = d.addTime ? (new Date(d.addTime).format('yyyy-MM-dd hh:mm')) : '未知时间'
                return d
            },
            req: {
                url: 'di/plan/api/list.json',
                data: {groupId: defaultId}
            },
            empty: {
                col: 5,
                html: '暂无计划'
            }
        }, function ($item, d) {
            console.log(d)
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'di/plan/api/del.json')
            })

            //编辑
            $item.on('click', '.ui-edit', function () {
                $addForm.data('data', d)
            })
            $item.on('shown.bs.tab', '.ui-edit', function () {
                var d = $addForm.data('data')
                console.log(d)
                if (d) {
                    $addForm.find('input[name=id]').val(d.id);
                    $addForm.find('input[name=startTime]').val(d.startTime)
                    $addForm.find('input[name=endTime]').val(d.endTime)
                    $addForm.find('textarea[name=content]').val(d.content)
                }
            })
        })
        wxghTable.init()

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
        })

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                wxghTable.refresh({groupId: $self.data('id')}, function () {
                    $self.find('i.fa').addClass('hidden')
                    $addForm.find('input[name=groupId]').val($self.data('id'));
                })
            }
        })

        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }
            ui.post('di/plan/api/add.json', info, function () {
                ui.alert((info['id'] ? '编辑' : '添加') + '成功！', function () {
                    ui.refresh();
                })
            })
        })

        //批量删除
        $('#delAllBtn').on('click', function () {
            var ids = wxghTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的学习计划！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    ui.refresh()
                })
            }, 'di/plan/api/del.json')
        })

        function verify(info) {
            if (!info['startTime']) {
                return '请选择计划开始时间'
            }
            if (!info['endTime']) {
                return '请选择计划结束时间'
            }
            if (!info['content']) {
                return '计划内容不能为空'
            }
        }
    })
</script>