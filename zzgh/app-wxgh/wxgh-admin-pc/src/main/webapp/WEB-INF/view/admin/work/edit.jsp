<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/28 0028
  Time: 下午 3:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        #prevImag{
            width: 440px;
            height: 300px;
        }
    </style>
</head>
<body>
<div style="margin-left: 5%">
    <div class="btn btn-empty-theme ui-back"><span
            class="fa fa-chevron-circle-left"></span> 返回
    </div>
</div>
<form id="addForm" class="col-lg-7 col-md-7 ui-margin-top-20" enctype="multipart/form-data">
    <div class="form-group">
        <label>是否显示</label>
        <select class="form-control" name="display">
            <option value="1">是</option>
            <option value="0">否</option>
        </select>
    </div>
    <div class="form-group">
        <label>图片类型</label>
        <select class="form-control" name="type">
            <option value="1">岗位创新</option>
            <option value="2">女工园地</option>
        </select>
    </div>
    <div class="form-group">
        <label>封面图片</label><br/>
        <input name="img" id="uploadNewsImg" accept="image/*" class="ui-inline-block" type="file"/>

        <div class="ui-inline-block" style="margin-left: 10px;">推荐尺寸：900像素x500像素</div>
    </div>
    <div class="form-group">
        <label>图片摘要</label>
        <small class="text-danger"></small>
        <input name="briefInfo" type="text" class="form-control" value="${data.briefInfo}">
    </div>
    <%--<div id="contentTxt" class="form-group">--%>
    <%--<label>公告内容</label>--%>
    <%--<script id="lawContent" type="text/plain" style="width:100%;height:240px;"></script>--%>
    <%--</div>--%>
    <%--<div id="contentUrl" class="form-group" style="display: none;">--%>
    <%--<label>公告链接</label>--%>
    <%--<input class="form-control" type="text" name="content">--%>
    <%--</div>--%>
    <div class="form-group">
        <label>排序序号</label>
        <small style="font-weight: 500;">（序号越小，排名越前哦）</small>
        <input name="sortId" type="number" class="form-control" placeholder="默认为 0" value="${data.sortId}">
    </div>
    <div class="form-group">
        <button id="addLawBtn" type="button" class="btn btn-theme">确定修改</button>
    </div>
</form>
<div class="col-lg-5 col-md-5" style="margin-top:5%">
    <div class="ui-img-div">
        <img id="prevImag" src="${home}/${data.path}">
        <p id="prevInfo"></p>
    </div>
</div>
<script>
    var __self = "work/api/"

    $("select[name=display]").each(function(index, element) {
        $(element).find("option[value='${data.display}']").attr('selected','selected');
    })

    $("select[name=type]").each(function(index, element) {
        $(element).find("option[value='${data.type}']").attr('selected','selected');
    })

    $(".ui-back").click(function(){
        ui.history.go("work/carousel")
    })

    //上传图片
    var $preImage = $('#prevImag')
    var $prevInfo = $("#prevInfo")
    var turn = false
    $('#uploadNewsImg').on('change', function (e) {
        turn = true
        var files = e.target.files;
        if (files && files.length > 0) {
            if (typeof FileReader != 'undefined') {
                var reader = new FileReader();
                reader.readAsDataURL(files[0]);
                reader.onload = function () {
                    $preImage.attr('src', this.result)
                }
            }
        } else {
            $preImage.attr('src', homePath + '/image/party/icon_preview.png')
        }
    })

    $("#addLawBtn").click(function(){
        var $form = $('#addForm');
        var info = $form.serializeJson();
        info['id'] = '${param.id}';
        if(verifyForm(info)){
            ui.alert(verifyForm(info))
            return
        }
        if(turn){
            ui.ajaxFile(__self + 'updatepic.json', info, 'uploadNewsImg', function () {
                ui.alert("修改成功", function () {
                    ui.history.go("work/carousel")
                });
            });
        }else{
            ui.post(__self + 'updatewithoutpic.json', info,function(){
                ui.alert("修改成功", function () {
                    ui.history.go("work/carousel")
                });
            })
        }
    })

    function verifyForm(info) {
        if (!info['briefInfo']) {
            return '摘要不能为空哦';
        }
    }


</script>
</body>
</html>
