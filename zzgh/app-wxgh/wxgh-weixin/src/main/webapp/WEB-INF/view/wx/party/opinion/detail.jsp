<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${p.title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

        body,
        .mui-content,
        .mui-content-padded {
            background: #fff;
        }
    </style>
</head>

<body class="html">

<div class="mui-content">
    <div class="ui-card">
        <div class="ui-card-header">
            <h5 class="ui-title">${p.title}</h5>
            <small class="ui-tip">
                <fmt:formatDate value="${p.addTime}" pattern="yyyy-MM-dd"/>
                <span>${p.username}</span>
            </small>
        </div>
        <div class="ui-card-content ui-html">
            <p>${p.content}</p>
        </div>
        <p>
            <span>阅读 ${p.view}</span>
        </p>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    var id = '${param.id}'
    $(function () {
        var content=$(".ui-html p").html()
//        if(!content){
//            document.getElementById("link").click();
//        }
        ui.initHtml();
    })
</script>
</body>

</html>
