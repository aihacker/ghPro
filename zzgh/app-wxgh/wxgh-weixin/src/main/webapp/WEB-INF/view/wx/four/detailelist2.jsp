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
    <title>${deptname}</title>
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

        .ui-select-head {
            text-align: center;
        }

        .ui-select-head,
        .ui-select-head li,
        .ui-select-head select {
            background-color: #efeff4;
        }

        .ui-select-head select {
            padding: 0px 5px;
            margin-bottom: 0px;
            line-height: 18px;
        }

        .left-img {
            width: 40px;
            height: 40px;
            display: block;
            border-radius: 50%;
            color: #fff;
            margin-top: 36px;
            margin-right: 10px;
            text-align: center;
        }

        .buytime-span {
            font-size: 14px;
            color: #8f8f94;
        }

        .mui-media .mui-ellipsis {
            color: #000;
            padding-right: 20px;
            padding-top: 4px;
        }

        .left-color-zhise {
            background-color: #d0659b;
        }

        .left-color-huang {
            background-color: #ff972d;
        }

        .left-color-green {
            background-color: #5ed198;
        }

        .left-color-blue {
            background-color: #669aff;
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
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">${deptname}</h1>
</header>

<div class="mui-content" style="padding-top: 0;margin-top: 50px;">

    <div class="mui-input-row mui-search">
        <input type="search" class="mui-input-clear" placeholder="搜索该公司的台帐项目" id="search" deptid="${deptid}">
    </div>

    <ul class="mui-table-view" id="ul-list">
        <c:choose>
            <c:when test="${empty list}">
                <div class="mui-content-padded mui-text-center" style="background-color: #efeff4;margin: 0;">
                    暂无
                </div>
            </c:when>
            <c:otherwise>
                <c:forEach items="${list}" var="item" varStatus="status">
                    <li class="mui-table-view-cell mui-media">
                        <div class="div-dept" data-href="${home}/wx/four/detailelist3.html?mid=${item.id}">
                            <c:choose>
                                <c:when test="${item.avatar != null && item.avatar != ''}">
                                    <img style="width: 37px;" class="mui-media-object mui-pull-left"
                                         src="${item.avatar}">
                                </c:when>
                                <c:otherwise>
                                    <img style="width: 37px;" class="mui-media-object mui-pull-left"
                                         src="${home}/image/common/nopic.gif">
                                </c:otherwise>
                            </c:choose>
                            <div class="mui-media-body">
                                <p class="mui-ellipsis"
                                   style="margin-top: 10px;padding-right: 0px;">${item.name}
                                    <a style="color: black;" href="#popover${status.index}"
                                       class="mui-icon mui-icon-arrowdown mui-pull-right">
                                    </a>
                                </p>
                            </div>
                        </div>

                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </ul>

</div>

<c:forEach items="${list}" var="item" varStatus="status">
    <div id="popover${status.index}" class="mui-popover">
        <ul class="mui-table-view">
            <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?mid=${item.id}&type=1&isRepair=0">查看台帐汇总</a></li>
            <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting3.html?mid=${item.id}&isRepair=1">需要更换的设备</a></li>
        </ul>
    </div>
</c:forEach>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}'

    //    window.onload = page.init()

    $(function () {
        var ul_list_origin = $("#ul-list").html();
        $("#search").on("input", function () {
            var $this = $(this);
            var value = $this.val();
            var deptid = $this.attr("deptid");
            $.ajax({
                type: "get",
                url: "${home}/wx/four/search.json",
                data: {
                    action: "",
                    search: value,
                    deptId: deptid
                },
                success: function (result) {
                    callBack(result, value);
                }
            })
            function callBack(result, value) {
                $("#ul-list").html("");
                var data = result.data;
                var ps = "";
                if (value) {
                    for (var i = 0; i < data.length; i++) {
                        var time = data[i].buyTime.trim();
                        if ("" == time.trim() || time == null) {
                            time = "未知时间";
                        } else {
                        }
                        ps = ps + '<li class="mui-table-view-cell mui-media"><a class="mui-navigate-right"href="${home}/wx/four/show.html?id=' + data[i].id + '"><div class="mui-media-object mui-pull-left"><span class="left-img left-color-green">' + (i + 1) + '</span></div><div class="mui-media-body"><p class="mui-ellipsis">' + data[i].fpcName + '<span class="mui-right mui-pull-right buytime-span">品牌：' + data[i].brand + '</span></p><p class="mui-ellipsis"></p><p class="mui-ellipsis"><span class="buytime-span">采购时间：' + time + '</span></p><p class="mui-ellipsis"><span class="buytime-span">型号：' + data[i].modelName + '</span></p><p class="mui-ellipsis"><span class="buytime-span">营销中心：' + data[i].marketName + '</span></p></div></a></li>';
                    }
                } else {
                    ps = ul_list_origin;
                }

                if(data.length == 0)
                    mui.toast("你搜索的东西太潮了，四小君找不到。- - ");

                $("#ul-list").html(ps);
            }
        })
    })


</script>
<script>
    $(function () {
        $("div.div-dept").click(function () {
            var href = $(this).attr("data-href");
            window.location.href = href;
        });
    });
</script>

</body>

</html>
