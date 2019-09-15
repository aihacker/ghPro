<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${a.title}</title>
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
            <h5 class="ui-title">${a.title}</h5>
            <small class="ui-tip"><fmt:formatDate value="${a.addTime}" pattern="yyyy-MM-dd"/></small>
        </div>
        <div class="ui-card-content ui-html">
            <p>${a.content}</p>
        </div>
        <p>
            <span>阅读 ${a.seeNumb}</span>&nbsp;&nbsp;&nbsp;
            <span id="zanBtn"><span class="fa fa-thumbs-o-up${isZan?' ui-text-primary':''}"></span> <small>${a.zanNumb}</small></span>
        </p>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    var id = '${param.id}'
    $(function () {
        $('#zanBtn').on('tap', function () {
            var $self = $(this)
            var url = homePath + '/wx/party/zan.json'
            mui.getJSON(url, {id: id}, function (res) {
                if (res.ok) {
                    var $small = $self.find('small')
                    var numb = Number($small.text().trim())
                    if (res.data == true) {
                        $small.text(numb + 1)
                        $self.find('span.fa').addClass('ui-text-primary')
                    } else {
                        $small.text(numb - 1)
                        $self.find('span.fa').removeClass('ui-text-primary')
                    }
                } else {
                    alert(res.msg)
                }
            })
        })
    })
</script>
</body>

</html>
