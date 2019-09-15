<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<style>
    .form-group.time > .form-control {
        width: 45%;
        display: inline-block;
    }

    .form-group.time .center-tip {
        padding: 0 10px;
    }

    #editImgDiv {
        padding: 10px;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane" id="editPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <div style="width: 60%;margin-left: 20%;margin-top: 20px;">
                <div id="editImgDiv"></div>
                <button id="editBtn" type="button" class="btn btn-theme">确定修改</button>
            </div>
        </div>
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-plus-se"></span>
                    新增健步活动</a>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>活动名称</th>
                    <th>活动时间</th>
                    <th>参与部门</th>
                    <th>封面</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="lawList">
                </tbody>
            </table>
            <ul class="pagination" id="lawPage"></ul>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <form id="addForm" class="col-md-6 col-md-offset-3" style="margin-top: 20px;">
                <div class="form-group">
                    <label>活动名称</label>
                    <input class="form-control" name="name" placeholder="活动名称不能为空哦">
                </div>
                <div class="form-group time">
                    <label style="display: block;">活动时间</label>
                    <input readonly class="form-control ui-time" name="start" placeholder="开始时间">
                    <span class="center-tip">~</span>
                    <input readonly class="form-control ui-time" name="end" placeholder="结束时间">
                </div>
                <div class="form-group">
                    <label>开展部门</label>
                    <div>
                        <c:forEach items="${depts}" var="d">
                            <label class="checkbox-inline">
                                <input name="deptids" type="checkbox" value="${d.id}"> ${d.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <label>活动介绍</label>
                    <textarea rows="3" name="info" class="form-control" placeholder="简单介绍几句吧"></textarea>
                </div>

                <div id="imgDiv"></div>

                <div class="form-group">
                    <button id="addLawBtn" type="button" class="btn btn-theme">确定添加</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td title="{{=it.info}}">{{=it.name}}</td>
        <td>{{=it.time}}</td>
        <td>{{=it.deptname}}</td>
        <td><img src="${home}{{=it.imgPath}}"/></td>
        <td>{{=it.statusName}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a href="#editPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑背景</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                {{? it.status == 1}}
                <a class="btn btn-empty-theme ui-start"><span class="fa fa-eye"></span> 结束活动</a>
                {{??}}
                <a class="btn btn-empty-theme ui-end"><span class="fa fa-eye"></span> 开始活动</a>
                {{?}}
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(function () {
        var $form = $('#addForm'),
            $addBtn = $('#addLawBtn'),
            upload = ui.uploader('#imgDiv'),
            upload1 = ui.uploader('#editImgDiv'),
            $editPane = $('#editPane');

        var $modal = $('#module')
        ui.check.init()
        var lawTable = ui.table('law', {
            req: {
                url: 'sport/api/act_list.json',
            },
            empty: {
                col: 7,
                html: '暂未发布健步活动'
            },
            dataConver: function (d) {
                if (d.status == 1) {
                    d['statusName'] = '进行中';
                } else if (d.status == 2) {
                    d['statusName'] = '已结束';
                }
                return d;
            }
        }, function ($item, d) {
            $item.data('data', d);

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'sport/api/act_del.json')
            })

            //编辑
            $item.on('click', '.ui-edit', function () {
                $editPane.data('id', d.id)
            });

            //开始
            $item.on('click', '.ui-start', function () {
                ui.getURL('sport/api/act_start.json', function () {
                    ui.alert('活动已开始！');
                });
            });
            $item.on('click', '.ui-end', function () {
                ui.getURL('sport/api/act_end.json', function () {
                    ui.alert('活动已结束！');
                });
            });

            //查看
            $item.on('click', '.ui-see', function () {
                var id = d.id
                $modal.data('id', id)
                $modal.show();

                initModal(d)

                function initModal(d) {
                    $("#deviceForm").html(d.content);
                    $("#heading").html(d.name)
                }

                $(".ui-back").click(function () {
                    $modal.hide()
                })
            })
        })
        lawTable.init()

        $("input.ui-time").datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            startView: 'month',
            maxView: 'year',
            minView: 'month'
        });

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $form.removeData('data')
            $form.clearForm()
        });

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的健步活动！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    lawTable.request(null, lawTable)
                })
            }, 'sport/api/act_del.json')
        })

        $addBtn.on('click', function () {
            var info = $form.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return;
            }
            info['start'] = info.start.replace(/-/g, '');
            info['end'] = info.end.replace(/-/g, '');
            info['deptids'] = info.deptids.toString();
            upload.upload(function (fileIds) {
                info['img'] = fileIds.toString();
                ui.post("sport/api/act_add.json", info, function () {
                    ui.alert("添加成功", function () {
                        $form.clearForm();
                    })
                })
            });
        });

        $('#editBtn').click(function () {
            upload1.upload(function (fileids) {
                if (fileids.length <= 0) {
                    ui.alert('请选择背景图片');
                    return;
                }
                ui.post('sport/api/act_bg.json', {id: $editPane.data('id'), bg: fileids.toString()}, function () {
                    ui.alert('编辑成功！', function () {
                        ui.refresh();
                    });
                });
            });
        });


        /**
         * 表单验证
         * @param info
         * @returns {*}
         */
        function verify(info) {
            if (!info['name']) {
                return '活动名称不能为空哦';
            }
            if (!info.start) {
                return '活动开始时间不能为空哦';
            }
            if (!info.end) {
                return '活动结束时间不能为空哦';
            }
            if (!info.deptids || info.deptids.length == 0) {
                return '请选择活动开展部门哦';
            }
        }
    })
</script>

