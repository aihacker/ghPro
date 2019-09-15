<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>答案</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
                <c:forEach items="${ats}" var="at">
                    <c:if test="${!empty at.answer}">
                        <li class="mui-table-view-cell">
                            ${at.answer}
                            <%--<p>
                                ${at.username}
                            </p>--%>
                    </li>
                    <hr/>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
        });

        var height = $(window).height() - ($("header").height() + $("#briefInfo").height() + $(".ui-fixed-bottom").height() + 20)
        $(".mui-scroll-wrapper").height(height)
    });
</script>
</body>
</html>
