<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${group.name}</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .item_pane {
            background-color: #ffffff;
            padding: 8px 12px;
            display: flex;
        }

        .item_pane label {
            height: 24px;
            line-height: 24px;
            width: 120px;
        }

        .no_flex {
            display: inherit;
        }

        .text_letter_space {
            letter-spacing: 18px;
        }

        .text_auto_line {
            flex: 1;
            color: #555666;
        }

        .text_left_space {
            padding-left: 30px;
        }

        .img_cell {
            min-height: 120px;
            position: relative;
            width: 120px;
        }

        .img_cell img {
            height: 100%;
            position: absolute;
            width: 100%;
        }

        .padding_12_0 {
            padding: 12px 0;
        }
    </style>
</head>

<body>

<!--
<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">${group.name}</h1>
</header>-->
<div class="mui-content">
    <div class="item_pane">
        <label>兴趣组名称</label>

        <div class="text_auto_line text_left_space">${group.name}</div>
    </div>
    <div class="item_pane ui-margin-top-15">
        <label>申请人</label>

        <div class="text_auto_line text_left_space">${group.createBy}</div>
    </div>
    <div class="item_pane ui-margin-top-15">
        <label>申请时间</label>

        <div class="text_auto_line text_left_space" id="createTime">${group.addTime}</div>
    </div>
    <div class="item_pane no_flex ui-margin-top-15">
        <label>兴趣组头像</label>

        <div class="img_cell">
            <c:if test="${group.avatarId==null}">
                <img src="${home}/image/default/activities.png">
            </c:if>
            <c:if test="${group.avatarId!=null}">
                <img src="${group.avatarId}">
            </c:if>
        </div>
    </div>
    <div class="item_pane no_flex ui-margin-top-15">
        <div>兴趣组介绍</div>

        <div class="text_auto_line padding_12_0">${group.briefIntro}</div>
    </div>
    <div class="item_pane" style="margin: 15px 0;">
        <label>审核结果</label>
        <c:if test="${group.status==0}">
            <div class="text_auto_line text_left_space" style="color: #12a9c6">未审核</div>
        </c:if>
        <c:if test="${group.status==1}">
            <div class="text_auto_line text_left_space" style="color: #6fe579">审核成功</div>
        </c:if>
        <c:if test="${group.status==2||group.status==3}">
            <div class="text_auto_line text_left_space" style="color: #f40520">审核失败</div>
        </c:if>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/script/request.js"></script>
</body>

</html>
