<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<style>
    .match{
        margin: 0 auto;
        text-align: center;
        width: 60%;
    }
    .header{
    }
</style>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<div class="row">
    <div class="match">
        <div class="header">
            <h4>发布比赛</h4>
        </div>
        <from class="form-horizontal container-fluid" role="form">
            <div class="form-group">
                <label class="col-sm-2 control-label">比赛名称：</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="name" placeholder="请输入比赛名称"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">比赛封面：</label>
                <div class="col-sm-3">
                    <div id="img"></div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系电话：</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="tel" placeholder="请输入联系电话"/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">开始时间：</label>
                <div class="col-sm-3">
                    <input readonly="readonly" id="start_datetimepicker" type="text" class="form-control"
                           name="startTime"
                           placeholder="">
                </div>
                <label class="col-sm-2 control-label">结束时间：</label>
                <div class="col-sm-3">
                    <input readonly="readonly" id="end_datetimepicker" type="text" class="form-control" name="endTime"
                           placeholder="">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">比赛规则：</label>
                <div class="col-sm-8 ">
                    <textarea name="rule" class="form-control" rows="3" placeholder="请输入比赛规则"></textarea>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">比赛内容：</label>
                <div class="col-sm-8 ">
                    <textarea name="content" class="form-control" rows="3" placeholder="请输入比赛内容"></textarea>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">投票方式：</label>
                <div class="col-sm-8">
                    <input type="text" name="type" readonly="readonly" class="form-control" value="全名投票" placeholder=""/>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">作品类型：</label>
                <div class="col-sm-8 ">
                    <select class="form-control" id="select" name="workType">
                        <option value="0" selected>--请选择--</option>
                        <option value="1">照片</option>
                        <option value="2">视频</option>
                    </select>
                </div>
            </div>
            <button type="button" class="btn btn-primary" id="sure">保存</button>
            <button type="submit" class="btn btn-primary" id="submit">提交</button>
        </from>
    </div>
</div>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script>
    $(function () {
        var upload = ui.uploader('#img')
        var url = '/admin/tribe/photo/api/save.json';
        $("#submit").click(function () {
            var data=getInfo()
            upload.upload(function (fileIds) {
                if(fileIds && fileIds.length > 0){
                    data['cover']=fileIds[0]
                }
                ui.post(url,data,function () {
                    ui.alert("添加成功")
                })
            })
        })

        $('#start_datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true
        });
        $('#end_datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true
        });

        function getInfo() {
            var name=$("input[name='name']").val()
            var content=$("textarea[name='content']").val()
            var rule=$("textarea[name='rule']").val()
            var tel=$("input[name='tel']").val()
            var startTime=$("input[name='startTime']").val()
            var endTime=$("input[name='endTime']").val()
            var workType=$("select option:selected").val()
            if(name.length==0){
                ui.alert("请填写比赛的名字")
                return
            }
            if(workType==0){
                ui.alert("请选择作品类型")
                return
            }
            if(tel.length>11||tel.length==0){
                ui.alert("请填写正确的手机号码")
                return
            }
            var data={
                name:name,
                content:content,
                rule:rule,
                tel:tel,
                startTime:startTime,
                endTime:endTime,
                worksType:workType,
                type:1
            }
            return data;
        }
    })

</script>
