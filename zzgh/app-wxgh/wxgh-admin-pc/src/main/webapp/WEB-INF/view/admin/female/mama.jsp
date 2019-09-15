<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/8
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"></link>
<div class="row">
    <div class="ui-content tab-content">
        <div class="tab-pane active" id="mainpane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>名称</th>
                    <th>封面</th>
                    <th>添加时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="lawList">
                </tbody>
            </table>
            <ul class="pagination" id="lawPage"></ul>
        </div>
        <!--<a id="contactDelBtn" class="btn btn-link">批量删除</a>-->
    </div>
</div>
<%--<div class="row">--%>
<%--<table class="table">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th><input class="ui-check-all" type="checkbox"/></th>--%>
<%--<th>名称</th>--%>
<%--<th>封面</th>--%>
<%--<th>开始时间</th>--%>
<%--<th>结束时间</th>--%>
<%--<th>添加时间</th>--%>
<%--<th>操作</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody id="femaleLessonTbody">--%>
<%--</tbody>--%>
<%--</table>--%>
<%--<ul class="pagination" id="pagination"></ul>--%>
<%--</div>--%>


<div id="deviceEditModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <div class="modal-body">
                <form id="deviceForm">
                    <div class="form-group">
                        <label>名称</label>
                        <input class="form-control" type="text" name="name" placeholder="名称">
                    </div>
                    <div class="form-group">
                        <label>封面</label>
                        <%--<input class="form-control" type="file" name="coverFile" id="coverFile">--%>
                        <%--<img src="${home}/image/default/act.png" style="height: 200px;" name="cover">--%>
                        <div id="converImg"></div>
                    </div>
                    <div class="form-group">
                        <label>内容</label>
                        <textarea class="form-control" name="content" placeholder="内容" rows="5"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="deviceOkBtn" type="button" class="btn btn-theme">确定</button>
            </div>
        </div>
    </div>
</div>


<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.name}}</td>
        <td style="text-align: center;"><img style="height: 30px;" src="{{=it.cover}}"></td>
        <td>{{=it.addTime}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-close"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var __self = "female/api/"

        var $modal = $('#deviceEditModal')

        var editId = ''

        ui.check.init()

        var lawTable = ui.table("law",{
            req:{
                url:__self+"mamalist.json"
            },
            dataConver:function (d){
                d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                return d
            },
            empty: {
                col: 8,
                html: '暂未发布'
            }
        },function($item,d){
            $item.data('data',d)

            //delete
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'mamadel.json')
            })

            //edit
            $item.on('click','.ui-edit',function(){
                var id = d.id
                editId = id;
                $modal.data('id', id)
                $modal.modal('show')

                var url = __self+"mamaget.json"
                ui.get(url,{id:id},function (d) {
                    initModal(d)
                })

                function initModal(d) {
                    $("input[name=name]").val(d.name);
                    $("input[name=startTime]").val(new Date(d.startTime).format('yyyy-MM-dd hh:mm'));
                    $("input[name=endTime]").val(new Date(d.endTime).format('yyyy-MM-dd hh:mm'));
                    $("img[name=cover]").attr("src",d.cover);
                    $("textarea[name=content]").val(d.content);
                }
            })
        })

        lawTable.init();

        //初始化图片上传
        var upload = ui.uploader('#converImg')


        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()

            if (ids.length <= 0) {
                ui.alert('请选择需要删除的会员提案！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function(){
                    lawTable.request(null, lawTable)
                })
            }, __self + 'mamadel.json')
        })

        //初始化时间选择器
        $('#startTime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true,
            language: 'cn'
        });
        $('#endTime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'cn',
            autoclose: true
        });


        //提交修改
        $("#deviceOkBtn").click(function () {

            var name = $("input[name=name]").val();
            var startTime = $("input[name=startTime]").val();
            var endTime = $("input[name=endTime]").val();
            var content = $("textarea[name=content]").val();

            name = encodeURIComponent(name);
            content = encodeURIComponent(content);

            var data = {
                action: "edit",
                name : name,
                startTime: startTime,
                endTime: endTime,
                content: content,
                id: editId
            }

            if($(".ui-file-item").length==2){
                upload.upload(function (fileIds) {
                    data.cover = fileIds.toString()
                    ui.post(__self+"mamaapply.json", data, function () {
                        ui.alert('修改成功！',function(){
                            $modal.modal("hide");
                            lawTable.request(null,lawTable)
                        })
                    })
                })
            }else{
                ui.post(__self+"mamaapply.json", data, function () {
                    ui.alert('修改成功！',function(){
                        $modal.modal("hide");
                        lawTable.request(null,lawTable)
                    })
                })
            }
        });


    })
</script>
<%--<script>--%>
<%--$('#startTime').datetimepicker({--%>
<%--format: 'yyyy-mm-dd hh:ii',--%>
<%--autoclose: true,--%>
<%--language: 'cn'--%>
<%--});--%>
<%--$('#endTime').datetimepicker({--%>
<%--format: 'yyyy-mm-dd hh:ii',--%>
<%--language: 'cn',--%>
<%--autoclose: true--%>
<%--});--%>
<%--$(function () {--%>
<%--ui.check.init()--%>
<%--var $body = $('#femaleLessonTbody')--%>

<%--var $modal = $('#deviceEditModal')--%>

<%--var editId = "";--%>


<%--get_list()--%>

<%--var isinit = false--%>

<%--//批量删除--%>
<%--$('#contactDelBtn').on('click', function () {--%>
<%--var ids = []--%>
<%--$.each($body.find('input.ui-check:checked'), function () {--%>
<%--ids.push($(this).parent().parent().data('id'))--%>
<%--})--%>
<%--if (ids.length <= 0) {--%>
<%--alert('请选择需要删除的好友哦！！！')--%>
<%--return--%>
<%--}--%>

<%--ui.del(ids.toString(), function () {--%>
<%--alert('删除成功')--%>
<%--window.location.reload()--%>
<%--}, '/wxgh/female/lesson.json?action=del')--%>
<%--})--%>


<%--$("input[name=coverFile]").change(function () {--%>
<%--var src = getObjectURL(this.files[0]); //获取路径--%>
<%--$("img[name=cover]").attr("src", src);--%>
<%--});--%>

<%--function getObjectURL(file) {--%>
<%--var url = null;--%>
<%--if (window.createObjectURL != undefined) {--%>
<%--url = window.createObjectURL(file)--%>
<%--} else if (window.URL != undefined) {--%>
<%--url = window.URL.createObjectURL(file)--%>
<%--} else if (window.webkitURL != undefined) {--%>
<%--url = window.webkitURL.createObjectURL(file)--%>
<%--}--%>
<%--return url--%>
<%--};--%>

<%--$("#deviceOkBtn").click(function () {--%>

<%--var name = $("input[name=name]").val();--%>
<%--var startTime = $("input[name=startTime]").val();--%>
<%--var endTime = $("input[name=endTime]").val();--%>
<%--var content = $("textarea[name=content]").val();--%>

<%--name = encodeURIComponent(name);--%>
<%--content = encodeURIComponent(content);--%>

<%--var data = {--%>
<%--action: "edit",--%>
<%--name : name,--%>
<%--startTime: startTime,--%>
<%--endTime: endTime,--%>
<%--content: content,--%>
<%--id: editId--%>
<%--}--%>

<%--$.ajaxFileUpload({--%>
<%--url: '${home}/wxgh/female/lesson.json',--%>
<%--dataType: 'json',--%>
<%--secureuri: false,--%>
<%--data: data,--%>
<%--fileElementId: 'coverFile',--%>
<%--success: function () {--%>
<%--backcall()--%>
<%--}--%>
<%--});--%>
<%--function backcall() {--%>
<%--$modal.modal("hide");--%>
<%--window.location.reload();--%>
<%--}--%>

<%--});--%>


<%--function get_list(param) {--%>
<%--var url = 'female/api/lessonlist.json'--%>

<%--var info = $.extend({action: 'list'}, param)--%>

<%--ui.post(url, info, function (d) {--%>
<%--var list = d.list--%>
<%--// console.log(JSON.stringify(list))--%>
<%--$body.empty()--%>
<%--if (list && list.length > 0) {--%>
<%--for (var i in list) {--%>
<%--var u = list[i]--%>
<%--// var html = $('#statusSelect').clone().prop('id', '').css('width', 120).hide().prop('outerHTML')--%>
<%--var $item = $('<tr><td><input class="ui-check" type="checkbox"/></td>' +--%>
<%--'<td>' + u.name + '</td>' +--%>
<%--'<td style="text-align: center;"><img style="height: 30px;" src="' + u.cover + '"></td>' +--%>
<%--'<td>' + (new Date(u.startTime).format('yyyy-MM-dd hh:mm')) + '</td>' +--%>
<%--'<td>' + (new Date(u.endTime).format('yyyy-MM-dd hh:mm')) + '</td>' +--%>
<%--'<td>' + (new Date(u.addTime).format('yyyy-MM-dd hh:mm')) + '</td>' +--%>
<%--'<td><div class="ui-link-group">' +--%>
<%--'<a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>' +--%>
<%--'<a class="btn btn-empty-theme ui-del"><span class="fa fa-close"></span> 删除</a>' +--%>
<%--'</div></td></tr>')--%>
<%--$item.data('id', u.id)--%>
<%--$body.append($item)--%>
<%--ui.check.addItem($item)--%>

<%--//事件初始化--%>
<%--$item.on('click', '.ui-del', function () {--%>
<%--var $parent = $(this).parent().parent().parent()--%>
<%--var id = $parent.data('id')--%>
<%--ui.del(id, function () {--%>
<%--ui.alert('删除成功！')--%>
<%--$parent.remove()--%>
<%--}, '/wxgh/female/lesson.json?action=del')--%>
<%--})--%>

<%--$item.on('click', '.ui-edit', function () {--%>

<%--var id = $(this).parent().parent().parent().data('id')--%>
<%--editId = id;--%>
<%--$modal.data('id', id)--%>
<%--$modal.modal('show')--%>
<%--var url = '/wxgh/female/lesson.json'--%>
<%--ui.get(url, {action: 'get', id: id}, function (d) {--%>
<%--initModal(d)--%>
<%--})--%>

<%--function initModal(d) {--%>
<%--console.log(JSON.stringify(d));--%>

<%--$("input[name=name]").val(d.name);--%>
<%--$("input[name=startTime]").val(new Date(d.startTime).format('yyyy-MM-dd hh:mm'));--%>
<%--$("input[name=endTime]").val(new Date(d.endTime).format('yyyy-MM-dd hh:mm'));--%>
<%--$("img[name=cover]").attr("src",d.cover);--%>
<%--$("textarea[name=content]").val(d.content);--%>
<%--}--%>

<%--//ui.alert('暂未开放！')--%>
<%--})--%>

<%--/*//编辑状态--%>
<%--$item.on('click', '.ui-edit-status', function () {--%>
<%--var $self = $(this)--%>
<%--var $status = $self.find('.ui-status')--%>
<%--var $select = $self.find('select')--%>
<%--if ($select.is(':hidden')) {--%>
<%--$status.hide()--%>
<%--$select.show()--%>
<%--} else {--%>
<%--$status.show()--%>
<%--$select.hide()--%>
<%--}--%>
<%--})--%>

<%--$item.on('click', '.ui-edit-status select', function (e) {--%>
<%--e.stopPropagation()--%>
<%--}).on('change', '.ui-edit-status select', function () {--%>
<%--var $self = $(this)--%>
<%--var id = $self.parent().parent().data('id')--%>
<%--var cf = confirm('是否修改？')--%>
<%--if (cf) {--%>
<%--var val = $self.val()--%>
<%--var txt = $self.find('option[value=' + val + ']').text()--%>
<%--var $status = $self.parent().find('.ui-status')--%>
<%--if (val != -1)--%>
<%--edit_user({id: id, status: val}, function () {--%>
<%--$status.text(txt)--%>
<%--$status.show()--%>
<%--$self.hide()--%>
<%--})--%>
<%--}--%>
<%--})*/--%>
<%--}--%>
<%--console.log(JSON.stringify(d.page))--%>
<%--var p = d.page--%>
<%--if (!isinit) {--%>
<%--//初始化分页--%>
<%--ui.page(function (num, type) {--%>
<%--var minfo = {currentPage: num}--%>
<%--get_list(minfo)--%>
<%--}, p)--%>
<%--isinit = true--%>
<%--} else {--%>
<%--$('#pagination').jqPaginator('option', {--%>
<%--totalPages: p.page,--%>
<%--currentPage: p.current--%>
<%--})--%>
<%--}--%>
<%--}--%>
<%--})--%>
<%--}--%>


<%--function edit_user(info, func) {--%>
<%--var url = '/wxgh/female/lesson.json'--%>
<%--info['action'] = 'edit'--%>
<%--ui.post(url, info, function () {--%>
<%--alert('修改成功！')--%>
<%--if (func) func()--%>
<%--})--%>
<%--}--%>
<%--})--%>
<%--</script>--%>

