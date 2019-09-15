<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/9
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="alert alert-info alert-dismissible fade in" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span>
        </button>
        <h4><span class="fa fa-lightbulb-o"></span> 操作提示</h4>
    </div>
</div>
<div class="row">
    <form id="giveForm" class="form-horizontal center-form col-lg-4 col-md-5 col-sm-5">
        <div class="form-group">
            <label class="control-label col-xs-3">积分类型：</label>
            <div class="col-xs-9">
                <select name="scoreType" class="form-control">
                    <option value="0">请选择</option>
                    <option value="1">个人积分</option>
                    <option value="2">青年部落积分</option>
                    <option value="3">纪检学习积分</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3">赠送用户：</label>
            <div class="col-xs-9 area">
                <a id="giveUserBtn" class="btn btn-link"><span class="fa fa-plus-square-o"></span> 请选择</a>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3">赠送积分：</label>
            <div class="col-xs-9">
                <input name="score" class="form-control" type="number" placeholder="赠送积分"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-xs-3">积分介绍：</label>
            <div class="col-xs-9">
                <textarea class="form-control" name="info" placeholder="用一句话介绍积分"></textarea>
            </div>
        </div>
        <button id="giveBtn" type="button" class="btn btn-danger ui-btn">确定</button>
    </form>
</div>

<jsp:include page="/comm/admin/userlist.jsp"/>

<script>
    $(function () {

        var __url = "score/api/"
        var userlist = window.userlist({
            checkable_dept: true,
            callback: function (lists) {
                $('#giveUserBtn').data('data', lists)
                $("#giveUserBtn").hide();
                listchoose(lists)
            }
        })

        function listchoose(a){
            avatar = 'https://res.wx.qq.com/mmocbiz/zh_CN/tmt/pc/dist/img/icon-organization-24_714a2dc7.png'
            for(i=0;i<a.length;i++){
                if(a[i].type == 2){
                    $(".area").append('<div data-num='+i+' data-id="' + a[i].id + '" class="user-item">'
                        + '<div class="ui-user-item">'
                        + '<img src="' + avatar + '"/>'
                        + '<p>' + a[i].name + '</p>'
                        + '<span class="fa fa-close"></span></div></div>')
                }else{
                    $(".area").append('<div data-num='+i+' data-id="' + a[i].id + '" class="user-item">'
                        + '<div class="ui-user-item">'
                        + '<img src="/wxgh/image/default/user.png"/>'
                        + '<p>' + a[i].name + '</p>'
                        + '<span class="fa fa-close"></span></div></div>')
                }
            }
            $(".area").on("click",".fa-close",function(){
                var  id = $(this).parent().parent().data('id')
                $(this).parent().parent().remove();
                for(i=0;i<a.length;i++){
                    var self = a[i];
                    if(self.id == id){
                        a.splice(i,1)
                    }
                    $('#giveUserBtn').data('data', a)
                }
                if($("#giveUserBtn").data("data").length == 0){
                    $("#giveUserBtn").show();
                }
            })
        }

        $('#giveBtn').on('click', function () {
            var $self = $(this)
            var $form = $('#giveForm')
            var info = $form.serializeJson()
            var users = $('#giveUserBtn').data('data')
            var verify_res = verify(info, users)
            if (verify_res) {
                ui.alert(verify_res)
                return
            }
            $self.button('赠送人数较多，请稍候...')
            info['results'] = users
            var pm = {action: 'add', json: JSON.stringify(info)}
            ui.post(__url+"give.json",pm, function () {
                ui.alert('赠送成功！',function () {
                    window.location.reload()
                })
            })
        })

        $('#giveUserBtn').on('click', function () {
            var self = $(this);
            userlist.show()
        })

        function verify(info, users) {
            if (!users || users.length <= 0) {
                return '请选择赠送用户或部门哦'
            }
            if (!info['scoreType'] && info['scoreType'] == 0) {
                return '请选择积分类型'
            }
            if (!info['score']) {
                return '请输入赠送积分'
            }
            if (!info['info']) {
                return '请输入积分介绍'
            }
        }
    })
</script>