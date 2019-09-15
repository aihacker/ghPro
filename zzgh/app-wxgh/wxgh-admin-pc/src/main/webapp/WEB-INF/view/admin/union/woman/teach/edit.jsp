<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<style>
    .addPane {
        margin: 0 auto;
        text-align: center;
        width: 50%;
    }

    .small {
        width: 100%;
        height: 10%;
        clear: both;
    }

</style>
<div class="row">
    <div class="addPane">
        <div class="header">
            <h4>编辑课堂</h4>
        </div>
        <div class="pull-left">
            <a data-toggle="tab" class="btn btn-empty-theme back"><span
                    class="fa fa-chevron-circle-left"></span> 返回</a>
        </div>
        <div class="small" id="addPane">
            <form style="margin-top: 20px;width: 70%;margin-left: 25%;">
                <div style="width: 70%;margin-left: 15%;">
                    <div class="form-group">
                        <label>课程名称</label>
                        <input type="text" name="name" value="${teach.name}" class="form-control" placeholder="课程名称不能为空">
                    </div>
                    <div class="form-group">
                        <label>报名人数</label>
                        <input type="text" name="joinNum" value="${teach.joinNum}"class="form-control" placeholder="请输入活动人数">
                    </div>
                    <div class="form-group">
                        <label>课程时间</label>
                        <div class="input-inline">
                            <input type="text" name="startTime" value="${teach.startTime}" class="form-control ui-time" placeholder="开始时间">
                            <input type="text" name="endTime" value="${teach.endTime}" class="form-control ui-time" placeholder="结束时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>课程内容</label>
                        <textarea class="form-control" name="content" maxlength="800" rows="3"
                                  placeholder="课程内容不能为空">${teach.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea class="form-control" name="remark" maxlength="200" rows="3"
                                  placeholder="其他补充信息">${teach.remark}</textarea>
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

                <input type="hidden" name="id" value="${teach.id}">

                <div class="form-group">
                    <button id="addBtn" type="button" class="btn btn-theme">保存</button>
                </div>
            </form>
        </div>
    </div>

</div>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script>
    $(function () {
        var upload = ui.uploader('#coverDiv');
        var __self = 'union/woman/teach/api';
        var $addForm = $('#addPane form');

        $('.ui-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            startDate: new Date(),
            autoclose: true
        })

        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson();
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            upload.upload(function (f) {
                if(f)
                {
                    info['cover'] = f[0];
                }
                ui.ajaxFile(__self + '/edit.json', info, 'fujianFile', function () {
                    ui.alert('编辑成功', function () {
                        ui.history.go("union/woman/teach/list")
                    });
                });
            });
        });

        $(".back").on('click',function () {
            ui.history.go("union/woman/teach/list")
        })

        //表单验证
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