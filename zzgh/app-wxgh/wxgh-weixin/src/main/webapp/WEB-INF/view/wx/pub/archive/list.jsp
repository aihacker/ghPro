<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hhl
  Date: 2017-07-27
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>我的健康档案</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>
    <style>
        .mui-table-view.mui-grid-view .mui-table-view-cell .mui-media-object {
            height: 200px;
        }
        .mui-table-view{
            margin-bottom: 3rem;
        }
    </style>
</head>

<body>

<!--<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">我的健康档案</h1>
    <a id="addBtn" href="${home}/wx/pub/archive/add.html" class="mui-btn mui-btn-link mui-pull-right">添加</a>
</header>-->
<a href="${home}/wx/pub/archive/add.html">
    <div class="ui-fixed-bottom">
        <button id="addActBtn" type="button" class="mui-btn mui-btn-primary">添加档案</button>
    </div>
</a>
<div class="mui-content">
    <c:choose>
        <c:when test="${empty archives}">
            <div class="mui-content-padded">
                <div class="mui-text-center">您没有健康档案哦</div>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="mui-table-view mui-grid-view">
                exist
                <c:forEach items="${archives}" var="arch" varStatus="i">
                    <li class="mui-table-view-cell mui-media mui-col-xs-6">
                        <c:forEach items="${arch.imgIdList}" var="img" varStatus="j">
                            <c:choose>
                                <c:when test="${j.index eq 0}">
                                    <a href="#">
                                        <img class="mui-media-object"
                                             src="${home}${img}"
                                             data-preview-src="${home}${img}"
                                             data-preview-group="${i.index}">
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="#">
                                        <img class="mui-media-object mui-hidden"
                                             src="${home}${img}"
                                             data-preview-src="${home}${img}"
                                             data-preview-group="${i.index}">
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <div class="mui-media-body">${arch.name}</div>

                        <time class="ui-text-info"><date:formatDate value="${arch.addTime}"
                                                                    type="date"/></time>
                        &nbsp;&nbsp;<span class="mui-icon mui-icon-closeempty del" style="color: red;"
                                          data-id="${arch.id}"></span>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script>
    $(document).ready(function(){
        wxgh.previewImageInit();
        $("body").on("tap", "span.del", function () {
            var id = $(this).attr("data-id");
            console.log(id)
            mui.confirm('您确定要删除吗？', '操作确认', ['否', '是'], function (e) {
                if (e.index == 1) {
                    $.ajax({
                        type: 'post',
                        dataType: 'json',
                        url: '${home}/wx/pub/archive/del.json',
                        data: {
                            action: 'del',
                            id: id
                        },
                        success: function (result) {
                            mui.toast(result.msg);
                            window.location.reload();
                        },
                        error: function (result) {
                            mui.toast(result.msg);
                        }
                    });
                } else {
                    return;
                }
            })
        });
    });
</script>
</body>

</html>


