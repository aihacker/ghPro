<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/7
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创新建议详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-title-content {
            padding: 5px 15px;
            background-color: #fff;
        }

        .mui-table-view-cell.mui-active {
            background-color: #fff;
        }

        .mui-table-view-cell small {
            float: right;
            margin-right: 10px;
            color: #777;
            position: relative;
            top: 3px;
        }

        .mui-scroll-wrapper {
            bottom: 50px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <h5 class="ui-h5-title">创新建议信息</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    类&nbsp;&nbsp;型:
                    <small>${a.typeName}</small>
                </li>
                <li class="mui-table-view-cell">
                    标&nbsp;&nbsp;题：
                    <p>${a.title}</p>
                </li>
                <li class="mui-table-view-cell">
                    建&nbsp;&nbsp;议：
                    <p>${a.advice}</p>
                </li>
            </ul>
            <h5 class="ui-h5-title ui-margin-top-10">审核信息</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    申请人：
                    <small>${a.username}-${a.deptname}</small>
                </li>
                <c:if test="${a.applyStatus gt 0}">
                    <li class="mui-table-view-cell">
                        审核时间：
                        <small><fmt:formatDate value="${s.auditTime}" pattern="yyyy-MM-dd HH:mm"/></small>
                    </li>
                    <li class="mui-table-view-cell">
                        审核状态：
                        <small>${a.applyStatus eq 0?'未审核':(a.applyStatus eq 1?'已通过':'未通过')}</small>
                    </li>
                    <li class="mui-table-view-cell">
                        审核意见：
                        <p>${a.auditIdea}</p>
                    </li>
                </c:if>
            </ul>
            <div class="ui-textarea-div">
                <textarea name="idea" rows="3" maxlength="200" placeholder="您的审核意见..."></textarea>
            </div>
        </div>
    </div>

    <div class="ui-bottom-btn-group">
        <button${a.applyStatus eq 2?' disabled':''} data-status="2" type="button" class="mui-btn ui-btn mui-btn-danger">不通过</button>
        <button${a.applyStatus eq 1?' disabled':''} data-status="1" type="button" class="mui-btn ui-btn mui-btn-success">通&nbsp;过</button>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    var applyId = '${a.pid}'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
    $(function () {
        wxgh.autoTextarea($('.ui-textarea-div textarea'))

        var loading = new ui.loading('加载中...')

        $('.ui-bottom-btn-group').on('tap', 'button[data-status]', function () {
            var idea = $('textarea[name=idea]').val()
            if (!idea) {
                alert('请输入您的审核意见哦！')
                return
            }

            var status = $(this).data('status')
            var txt = status == 1 ? '通过' : '不通过'
            var cf = confirm('是否' + txt + '？')
            if (cf) {
                loading.show()
                mui.post(homePath + '/wx/admin/union/advice/show/apply.json', {
//                    action: 'apply',
                    status: status,
                    id: applyId,
                    auditIdea: idea
                }, function (res) {
                    loading.hide()
                    if (res.ok) {
                        ui.showToast('审核成功！', function () {
                           window.location.reload(true);
                        })
                    } else {
                        alert('审核失败！')
                    }
                }, 'json')
            }
        })
    })
</script>
</body>
</html>
