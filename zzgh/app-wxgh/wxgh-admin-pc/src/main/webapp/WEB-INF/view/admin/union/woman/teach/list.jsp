<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/10
  Time: 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
<div class="row">

    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-plus"></span> 新增课堂</a>
                <a id="delBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>课堂名称</th>
                    <th>课程介绍</th>
                    <th>开课时间</th>
                    <th>报名人数</th>
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

            <form style="margin-top: 20px;width: 50%;margin-left: 25%;">
                <div style="width: 70%;margin-left: 15%;">
                    <div class="form-group">
                        <label>课程名称</label>
                        <input type="text" name="name" class="form-control" placeholder="课程名称不能为空">
                    </div>
                    <div class="form-group">
                        <label>报名人数</label>
                        <input type="number" name="joinNum" class="form-control" placeholder="请输入活动人数">
                    </div>
                    <div class="form-group">
                        <label>课程时间</label>
                        <div class="input-inline">
                            <input type="text" name="startTime" class="form-control ui-time" placeholder="开始时间">
                            <input type="text" name="endTime" class="form-control ui-time" placeholder="结束时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>课程内容</label>
                        <textarea class="form-control" name="content" maxlength="800" rows="3"
                                  placeholder="课程内容不能为空"></textarea>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea class="form-control" name="remark" maxlength="200" rows="3"
                                  placeholder="其他补充信息"></textarea>
                    </div>

                    <div class="form-group">
                        <label>是否推送</label>
                        <label class="checkbox-inline">
                            <input name="push" type="radio" value="1"> 推送
                        </label>
                        <label class="checkbox-inline">
                            <input name="push" type="radio" checked value="0"> 不推送
                        </label>
                    </div>

                    <div class="form-group">
                        <label>封面</label>
                        <div id="coverDiv"></div>
                    </div>

                    <div class="form-group">
                        <label>附件</label>
                        <input id="fujianFile" type="file" name="upfiles">
                    </div>
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
        <td><input class="ui-check" type="checkbox"/></td>
        <td>{{=it.name}}</td>
        <td>{{=it.content}}</td>
        <td>{{=it.time}}</td>
        <td>{{=it.joinNum}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-view"><span class="fa fa-edit"></span> 查看</a>
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(function () {
        var $addForm = $('#addPane form');
        $addForm.form();
        ui.check.init();

        var __self = 'union/woman/teach/api';

        var myTable = ui.table('wxgh', {
            req: {
                url: __self + '/list.json',
            },
            dataConver: function (d) {
                d['content'] = d.content ? d.content : '暂无介绍'
                return d
            },
            empty: {
                col: 5,
                html: '暂无课堂'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + '/delete.json')
            })

            $item.on('click','.ui-edit',function () {
                var url='union/woman/teach/edit'
                ui.history.go(url,{id:d.id})
            })

            $item.on('click','.ui-view',function () {
                var url='union/woman/teach/detail'
                ui.history.go(url,{id:d.id})
            })

        })
        myTable.init();

        var upload = ui.uploader('#coverDiv');

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
        });

        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson();
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            upload.upload(function (f) {
                if(!f)
                {
                    ui.alert('请选择封面上传！！！')
                    return;
                }
                info['cover'] = f[0];
                ui.ajaxFile(__self + '/add.json', info, 'fujianFile', function () {
                    ui.alert('新增成功', function () {
                        ui.refresh();
                    });
                });
            });
        });

        //批量删除
        $('#delBtn').on('click', function () {
            var ids = myTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的项目！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！')
                ui.refresh();
            }, __self + '/delete.json')
        })

        $('.ui-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            startDate: new Date(),
            autoclose: true
        })

//        $(".ui-time").datetimepicker({
//            format: 'hh:ii',
//            autoclose: true,
//            todayBtn: true,
//            startView: 'hour',
//            maxView: 'hour'
//        });

        function verify(info) {
            if (!info.name) {
                return '课程名称不能为空';
            }
            if(!info.joinNum){
                return '课程参加人数不能为空'
            }else{
                var reg = new RegExp("^[0-9]*$");
                if(!reg.test(info.joinNum)){
                    return '请输入数字'
                }
            }
            if (!info.content) {
                return '课程介绍不能为空';
            }
            if (!info['startTime']) {
                return '请选择开始时间';
            }
            if (!info.endTime) {
                return '请选择结束时间';
            }
            if (info.startTime >= info.endTime) {
                return '开始时间不能大于等于结束时间';
            }
        }
    })
</script>
