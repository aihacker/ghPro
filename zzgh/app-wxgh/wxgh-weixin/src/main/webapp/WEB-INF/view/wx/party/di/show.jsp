<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/28
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${s.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        html, body {
            height: 100%;
        }

        .mui-content {
            height: 100%;
        }

        .mui-btn-block {
            font-size: 16px;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="mui-scroll-wrapper" style="bottom: 60px;">
        <div class="mui-scroll">

            <div class="ui-img-div" style="height: 200px;">
                <img src="${home}${s.path}">
            </div>

            <p style="min-height: 40px;padding-top:4px;background-color: white;font-size: 18px;color:#000;line-height: 25px;padding-left: 10px;">
                ${s.name}
            </p>

            <div style="background-color: white;padding: 10px;border-bottom: 1px solid gainsboro;">
                <h4>课程介绍</h4>
                <p>
                    ${s.info}
                </p>
            </div>

            <ul class="mui-table-view ui-margin-top-10 no">
                <li class="mui-table-view-cell">
                    <a href="show_info.html?id=${s.id}" class="mui-navigate-right">学习内容</a>
                </li>
            </ul>

            <ul class="mui-table-view ui-margin-top-10 no">
                <li class="mui-table-view-cell">
                    <a href="exam_message.html?id=${s.id}" class="mui-navigate-right">考试情况</a>
                </li>
            </ul>

            <c:if test="${!empty s.fileInfos}">
                <div style="background-color: white;padding: 10px;margin-top: 10px;">
                    <h5>学习文件</h5>
                    <c:forEach items="${s.fileInfos}" var="item">
                        <p class="mui-ellipsis" style="position: relative;padding-right: 20px;">
                            <i class="fa fa-file-text" style="color: #03A9F4;font-size: 17px;"></i><span
                                style="margin-left: 5px;">${item.filename}</span><a class="mui-pull-right"
                                                                                    href="${home}${item.path}"
                                                                                    target="_blank"><i
                                class="fa fa-download"
                                style="font-size: 17px;position: absolute;right: 0px;top: 2px;"></i></a>
                        </p>
                    </c:forEach>

                </div>
            </c:if>

        </div>
    </div>

    <div class="ui-fixed-bottom">
        <c:choose>
            <c:when test="${s.endIs}">
                <button data-type="0" type="button" class="mui-btn mui-btn-danger mui-disabled">考试已结束哦</button>

            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${s.joinIs}">
                        <%--<c:choose>--%>
                        <%--<c:when test1="${s.exam eq 0}">--%>
                        <%--<div class="ui-fixed-bottom">--%>
                        <%--<button data-type="1" type="button" class="mui-btn mui-btn-primary">参加考试</button>--%>
                        <%--</div>--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                        <%--<div class="ui-fixed-bottom">--%>
                        <%--<button data-type="0" type="button" class="mui-btn mui-btn-danger mui-disabled">--%>
                        <%--你已参加考试哦--%>
                        <%--</button>--%>
                        <%--</div>--%>
                        <%--</c:otherwise>--%>
                        <%--</c:choose>--%>
                        <button data-type="0" type="button" class="mui-btn mui-btn-primary mui-disabled">已报名</button>
                    </c:when>
                    <c:otherwise>
                        <button data-type="2" type="button" class="mui-btn mui-btn-primary">我要报名</button>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>

    var examId = '${param.id}';
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
    $(function () {
        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var $self = $(this)
            var type = $self.data('type')
            if (type == 0) {
                ui.alert($self.text())
            } else if (type == 1) {
                mui.openWindow(homePath + '/wx/party/di/exam.html?id=' + examId);
            } else if (type == 2) {
                ui.confirm('是否报名参加？', function () {
                    wxgh.request.post('/wx/party/di/api/join.json', {eid: examId}, function () {
                        ui.showToast('报名成功', function () {
                            window.location.reload()
                        })
                    })
                })
            }
        })
    })
</script>
</body>
</html>