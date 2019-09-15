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
            <h4>课堂详情</h4>
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
                        <input type="text" name="name" readonly   value="${teach.name}" class="form-control" placeholder="课程名称不能为空">
                    </div>
                    <div class="form-group">
                        <label>报名人数</label>
                        <input type="text" name="joinNum" readonly   value="${teach.joinNum}"class="form-control" placeholder="请输入活动人数">
                    </div>
                    <div class="form-group">
                        <label>课程时间</label>
                        <div class="input-inline">
                            <input type="text" name="startTime" readonly   value="${teach.startTime}" class="form-control ui-time" placeholder="开始时间">
                            <input type="text" name="endTime" readonly   value="${teach.endTime}" class="form-control ui-time" placeholder="结束时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>课程内容</label>
                        <textarea class="form-control" readonly  name="content" maxlength="800" rows="3"
                                  placeholder="课程内容不能为空">${teach.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea class="form-control" readonly  name="remark" maxlength="200" rows="3"
                                  placeholder="其他补充信息">${teach.remark}</textarea>
                    </div>

                    <div class="form-group">
                        <label>封面</label>
                        <div id="coverDiv">
                            <img src="${home}${teach.path}" width="400" height="400">
                        </div>
                    </div>

                    <div class="form-group">
                        <label>附件</label>
                        ${html}
                    </div>
                </div>

                <input type="hidden" name="id" value="${teach.id}">

            </form>
        </div>
    </div>

</div>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(".back").on('click',function () {
        ui.history.go("union/woman/teach/list")
    })
</script>