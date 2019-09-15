<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/15
  Time: 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动成果发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-success">立即发布</button>
    </div>
    <form id="applyForm">
        <ul class="mui-table-view no ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right ui-flex">
                    <span>选择活动</span>
                    <small class="ui-right">请选择</small>
                </a>
                <select name="actId">
                    <option value="-1">请选择</option>
                    <c:forEach items="${acts}" var="a">
                        <option value="${a.value}">${a.name}</option>
                    </c:forEach>
                </select>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10 no">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right ui-flex">
                    <span>推送人员</span>
                    <small class="ui-right">协会成员</small>
                </a>
                <select name="pushType">
                    <option value="1">协会成员</option>
                    <option value="2">公司成员</option>
                </select>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10 no">
            <li class="mui-table-view-cell no">
                <a class="ui-flex">
                    <span>成果名称</span>
                    <input class="ui-right" name="title" type="text" placeholder="请输入"/>
                </a>
            </li>
            <li class="mui-table-view-cell no">
                <a>
                    <div class="ui-content">
                        <h5 class="ui-title">成果内容</h5>
                        <div class="ui-text-div">
                            <textarea name="info" maxlength="800" rows="2" placeholder="成果介绍（800字以内）"></textarea>
                        </div>
                    </div>
                </a>
            </li>
        </ul>

        <div id="resultImg">
        </div>
    </form>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    var groupId = '${param.id}';
    $(function () {
        wxgh.init('${weixin}')

        var choose = wxgh.imagechoose($('#resultImg'))

        var $form = $('#applyForm')
        $('#addBtn').on('tap', function () {
            var info = $form.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }

            choose.upload(function (mediaIds) {
                if (mediaIds.length < 0) {
                    ui.alert('请上传成果图片')
                    return
                }
                info['imgs'] = mediaIds.toString()
                info['groupId'] = groupId
                wxgh.request.post('api_add.json', info, function () {
                    ui.showToast('发布成功~', function () {
                        wxgh.clearForm($form[0]);
                        window.location.reload(true)
                    })
                })
            })
        })

        function verify(info) {
            if (info['actId'] == -1) {
                return '请选择活动'
            }
            if (!info['title']) {
                return '成果标题不能为空'
            }
            if (!info['info']) {
                return '成果内容不能为空'
            }
        }
    })
</script>
</body>
</html>