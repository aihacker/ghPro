<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>公司</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body, .mui-content {
            /*background-color: #fff;*/
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .mui-search {
            margin: 22px 12px -6px 12px;
        }

        a.mui-icon-arrowdown {
            width: 50px;
            height: 30px;
            top: 10px;
            text-align: center;
            position: absolute;
            right: 10px;
        }
    </style>
</head>

<header class="mui-bar mui-bar-nav ui-head">
    <h1 class="mui-title">公司</h1>
</header>

<div class="mui-content" style="padding-top: 0;margin-top: 44px;">

    <ul class="mui-table-view" id="ul-list">

        <c:forEach var="item" items="${list}">
        <li class="mui-table-view-cell">
                <div class="div-dept"data-href="${home}/wx/four/detailelist2.html?deptid=${item.deptid}">
                    <div>
                        ${item.name}
                        <a style="color: black;" href="#popover${item.deptid}" class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                    </div>
                </div>
        </li>
        </c:forEach>

    </ul>

</div>

<c:forEach var="item" items="${list}">
    <c:choose>
        <c:when test="${item.deptid eq 1}">
            <div id="popover${item.deptid}" class="mui-popover">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell"><a class=""
                                                       href="${home}/wx/four/collecting.html?foshanDeptid=${item.deptid}&type=1">查看台帐汇总</a></li>
                    <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?foshanDeptId=${item.deptid}">需要更换的设备</a>
                    </li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <div id="popover${item.deptid}" class="mui-popover">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=${item.deptid}">查看台帐汇总</a></li>
                    <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=${item.deptid}">需要更换的设备</a></li>
                </ul>
            </div>
        </c:otherwise>
    </c:choose>

</c:forEach>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()
    var homePath = '${home}'
</script>
<script>
    $(function () {
        $("div.div-dept").click(function () {
            var href = $(this).attr("data-href");
            window.location.href = href;
        });

        $(".mui-popover").click(function () {
            if (!this.loading) {
                this.loading = ui.loading('请稍候...')
            }
            this.loading.show();
        });
    });
</script>

</body>

</html>
