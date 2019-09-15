<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/15
  Time: 8:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .addFrom{
        width: 50%;
        margin: 0 auto;
    }
    .form-group{

    }
    .ui-media-cnt {
        margin-top: 5px;
    }

    .ui-media-body {
        width: 30px;
        height: 30px;
        margin-right: 10px;
    }
    .ui-searh-list .ui-media-cnt p {
        margin-bottom: 0px;
        font-size: 16px;
    }

    .ui-searh-list .ui-media-cnt small {
        color: #777;
    }
    .ui-searh-list {
        position: absolute;
        width: 90%;
        z-index: 999;
    }
    #searchResultPanel {
        overflow: scroll;
        height: 220px;
        overflow-x: hidden;
        overflow-y: auto;
    }
    .ui-searh-list .ui-media-cnt small {
        color: #777;
    }
</style>
<div class="row">
        <form id="addUserForm" class="addFrom">
            <div class="modal-header">
                <h4 class="modal-title">添加党员</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-group">
                        <label class="col-xs-4 control-label">姓名：</label>
                        <div class="col-xs-8">
                            <input type="text" name="username" class="form-control"/>
                            <input type="hidden" name="userId">

                            <div class="ui-searh-list hidden">
                                <ul class="list-group" id="searchResultPanel">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">性别：</label>
                        <div class="col-xs-8">
                            <select name="sex" class="form-control">
                                <option value="0">----请选择----</option>
                                <option value="男">
                                    男
                                </option>
                                <option value="女">
                                    女
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-4 control-label">手机号：</label>
                        <div class="col-xs-8">
                            <input type="text" name="mobile" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">个人图片：</label>
                        <div class="col-xs-8">
                            <div id="userImg">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">出生日期：</label>
                        <div class="col-xs-8">
                            <input type="text" name="birthday" class="form-control add-input-time"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">所属党支部：</label>
                        <div class="col-xs-8">
                            <select name="groupId" class="form-control">
                                <option value="">----请选择----</option>
                                <c:forEach items="${allgroup}" var="groups">
                                    <option value="${groups.id}">${groups.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">区内党支部：</label>
                        <div class="col-xs-8">
                            <input type="text" name="ccpdepart" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">身份证号：</label>
                        <div class="col-xs-8">
                            <input type="text" name="identificationNum" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">籍贯：</label>
                        <div class="col-xs-8">
                            <input type="text" name="nativePlace" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">民族：</label>
                        <div class="col-xs-8">
                            <select name="nation" class="form-control">
                                <option value="0">----请选择----</option>
                                <c:forEach items="${nations}" var="item">
                                    <option value="${item.id}">${item.nation}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">学历：</label>
                        <div class="col-xs-8">
                            <input type="text" name="education" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">工作单位：</label>
                        <div class="col-xs-8">
                            <input type="text" name="company" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">户口所在地：</label>
                        <div class="col-xs-8">
                            <input type="text" name="familyPlace" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">党内职务：</label>
                        <div class="col-xs-8">
                            <input type="text" name="position" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">正式/预备党员：</label>
                        <div class="col-xs-8">
                            <select name="isRepublican" class="form-control">
                                <option value="0">----请选择----</option>
                                <option value="1">正式党员</option>
                                <option value="2">预备党员</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">是否新调度党员：</label>
                        <div class="col-xs-8">
                            <select name="isNew" class="form-control">
                                <option value="">----请选择----</option>
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">工作时间：</label>
                        <div class="col-xs-8">
                            <input type="text" name="workDate" class="form-control add-input-time"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">退休时间：</label>
                        <div class="col-xs-8">
                            <input type="text" name="retiredDate" class="form-control add-input-time"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">入党时间：</label>
                        <div class="col-xs-8">
                            <input type="text" name="inDate" class="form-control add-input-time"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">转正时间：</label>
                        <div class="col-xs-8">
                            <input type="text" name="zhuanzhengDate" class="form-control add-input-time"/>
                        </div>
                    </div>
                    <div class="form-group">
                            <label class="col-xs-4 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
                        <div class="col-xs-8">
                            <textarea name="remark" form-group="3" class="form-control "></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="addUserBtn" type="button" class="btn btn-info">添加</button>
                <button type="button" class="btn btn-primary ui-close">关闭</button>
            </div>
        </form>
</div>
<script>
    $(function () {
        var upload = ui.uploader('#userImg')
        var $form = $('#addUserForm');//添加党员表单
        var url='/admin/party/api/add.json';
        $('#addUserBtn').on('click',function () {
           var info=$form.serializeJson();
           var verifyres = verify(info)
            if(verifyres){
               ui.alert(verifyres)
                return;
            }
            upload.upload(function (fileIds) {
                if(fileIds && fileIds.length > 0){
                    info['avatarURL'] = fileIds[0]
                    alert(fileIds[0])
                }
                ui.post(url,info,function () {
                    ui.alert('添加成功')

                })
            })

        })

        function verify(info) {
            if(!info['mobile']){
                return '请输入手机号'
            }else if(!info['identificationNum']){
                return '请输入身份证号'
            }else if(!info['sex']){
                return '请选择性别'
            }else if(!info['isRepublican']){
                return '请选择是否是正式党员'
            }else if(!info['zhuanzhengDate']){
                return '请选择转正时间'
            }
        }

        var $mobile = $('.row input[name=mobile]');
        var $searchPanel = $('.row .ui-searh-list')
        var $searchResult = $searchPanel.find('#searchResultPanel')
        $('.row input[name=username]').on('input', function () {
            var $self = $(this)
            var val=$self.val()
            if(!val){
                $searchPanel.addClass('hidden')
                return false;
            }
            $searchResult.removeClass('hidden')
            var url='/admin/party/api/search.json?'
            ui.post(url,{name:val},function (json) {
                    var users = json.users
                    $searchResult.empty();
                    if (users && users.length > 0) {
                        for (var i = 0; i < users.length; i++) {
                            var user = users[i];
                            var $li = $('<li class="list-group-item">' +
                                '<img class="ui-media-body pull-left" src="' + (user.avatar ? user.avatar : homePath + '/weixin/image/defalut/user.png') + '"/>' +
                                '<div class="ui-media-cnt"><p>' + user.name + '</p>' +
                                '<small>' + user.mobile + '</small></div></li>')
                            $li.attr('users', JSON.stringify(user));

                            $searchResult.append($li)
                            $searchPanel.removeClass('hidden')

                            $li.on('click', function () {
                                var user = JSON.parse($(this).attr('users'))
                                $self.val(user.name)
                                $self.next().val(user.userid)
                                $mobile.val(user.mobile)
                                $searchPanel.addClass('hidden')
                            })
                        }
                    } else {
                        $searchResult.append('<li class="list-group-item"><div class="text-center">没有结果哦</div></li>')
                        $mobile.val('');
                    }
            })
        })



    })
</script>
