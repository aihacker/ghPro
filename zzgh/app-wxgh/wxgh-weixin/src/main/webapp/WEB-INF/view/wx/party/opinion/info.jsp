<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>【${p.title}】详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .bg-img {
            height: 200px;
        }

        .mui-table-view-cell {
            padding: 10px 15px;
        }

        .mui-backdrop{
            z-index: 0;
        }
    </style>
</head>
<body>

<div class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <div class="mui-content">
            <div>
                <div id="actName" style="max-height: 80px;min-height: 50px;" class="ui-content mui-ellipsis-2">
                    <h5 class="ui-title">意见征集标题</h5>
                    ${p.title}
                </div>
                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">发布对象</h5>
                    <p title="${p.object}" class="mui-ellipsis-2">
                        ${p.object}
                    </p>
                </div>
                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">征集简介</h5>
                    <p id="actInfo" title="${p.info}" class="mui-ellipsis-2">
                        ${p.info}
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="ui-fixed-bottom ui-flex">
    <button type="button" id="add" class="mui-btn">我要发布</button>
    <button type="button" id="list" class="mui-btn">查看意见列表</button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>

    var id = '${param.id}';

    $(function () {
        $('#list').on('tap', function () {
            wxgh.openUrl("list.html?id=" + id)
        })
        $('#add').on('tap', function () {
            wxgh.openUrl("add.html?id=" + id)
        })
    })

</script>
</body>
</html>