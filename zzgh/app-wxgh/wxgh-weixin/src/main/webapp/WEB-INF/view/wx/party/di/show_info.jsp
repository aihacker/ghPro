<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/3
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${s.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <c:choose>
            <c:when test="${s.endIs}">
                <button data-type="0" type="button" class="mui-btn mui-btn-danger mui-disabled">考试已结束哦</button>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${s.joinIs}">
                        <c:choose>
                            <c:when test="${s.exam eq 0}">
                                <button data-type="1" type="button" class="mui-btn mui-btn-primary">参加考试</button>
                            </c:when>
                            <c:otherwise>
                                <button data-type="0" type="button" class="mui-btn mui-btn-danger mui-disabled">
                                    你已参加考试哦
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <button data-type="0" type="button" class="mui-btn mui-btn-danger mui-disabled">你还没有报名哦
                        </button>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="ui-html">
        ${s.content}
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var examId = '${param.id}';
    $(function () {
        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var $self = $(this)
            var type = $self.data('type')
            if (type == 0) {
                ui.alert($self.text())
            } else if (type == 1) {
                mui.openWindow(homePath + '/wx/party/di/exam.html?id=' + examId);
            }
        })
    })
</script>
</body>
</html>