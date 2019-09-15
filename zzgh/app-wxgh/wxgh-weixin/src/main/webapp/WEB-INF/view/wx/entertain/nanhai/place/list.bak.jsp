<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/14
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>我的场地预约</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #uiControleHead {
            padding: 0px 10px;
            background-color: #fff;
        }

        #controlItem {
            margin-top: 10px;
        }

        .mui-media-body {
            color: #000;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

<%--<h1 class="mui-title">我的场地预约</h1>--%>
<%--</header>--%>

<div class="mui-content">

    <div id="uiControleHead">
        <div id="segmentedControl" class="mui-segmented-control mui-segmented-control-inverted">
            <a id="useCount" class="mui-control-item mui-active" href="#item1">
                未使用（${nouserCount}）
            </a>
            <a id="nouseCount" class="mui-control-item" href="#item2">
                已失效（${nothingCount}）
            </a>
        </div>
    </div>

    <div id="controlItem">
        <div id="item1" class="mui-control-content mui-active">
            <div class="mui-scroll-wrapper">
                <div class="mui-scroll">
                    <ul class="mui-table-view ui-use">
                        <c:choose>
                            <c:when test="${empty nouse}">
                                <li class="mui-table-view-cell">
                                    <div class="mui-text-center">暂无可用场地哦</div>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${nouse}" var="n">
                                    <li class="mui-table-view-cell mui-media">
                                        <div class="mui-slider-right mui-disabled">
                                            <a data-id="${n.id}" data-type="1" class="mui-btn mui-btn-red">取消预约</a>
                                        </div>
                                        <div class="mui-slider-handle">
                                            <a href="javascript:;">
                                                <div class="mui-media-body">
                                                        ${n.placeName}
                                                    <p class="mui-ellipsis">
                                                        <small>${n.cateName}：${n.siteName}</small>
                                                        <small class="mui-pull-right">${n.dateId} - ${n.weekName}${n.startTime}~${n.endTime}</small>
                                                    </p>
                                                </div>
                                            </a>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
        <div id="item2" class="mui-control-content">
            <div class="mui-scroll-wrapper">
                <div class="mui-scroll">
                    <ul class="mui-table-view ui-nouse">
                        <c:choose>
                            <c:when test="${empty nothings}">
                                <li class="mui-table-view-cell">
                                    <div class="mui-text-center">您没有失效的场地哦</div>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${nothings}" var="n">
                                    <li class="mui-table-view-cell mui-media">
                                        <div class="mui-slider-right mui-disabled">
                                            <a data-id="${n.id}" data-type="0" class="mui-btn mui-btn-red">删除</a>
                                        </div>
                                        <div class="mui-slider-handle">
                                            <a href="javascript:;">
                                                <div class="mui-media-body">
                                                        ${n.placeName}
                                                    <p class="mui-ellipsis">
                                                        <small>${n.cateName}：${n.siteName}</small>
                                                        <small class="mui-pull-right">${n.dateId} - ${n.weekName}${n.startTime}~${n.endTime}</small>
                                                    </p>
                                                </div>
                                            </a>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

    $(function () {

        init_size()

        $('#controlItem').on('tap', '.mui-btn-red[data-id]', function () {
            var $li = this.parentNode.parentNode;
            var $self = $(this)
            mui.confirm('是否放弃已预约的场地', '系统提示', ['否', '是'], function (e) {
                if (e.index == 1) {
                    del($self.attr('data-id'),$self.attr('data-type'),$li);
                } else {
                    mui.swipeoutClose($li);
                }
            })
        })

        $(window).on('resize', init_size)

        function init_size() {
            var cntHeight = $(window).outerHeight() - $('.ui-head').outerHeight() - $('#uiControleHead').outerHeight();
            $('#controlItem .mui-control-content').css('height', cntHeight);
        }

        function del(id,type,$li) {
            var url = homePath + '/wx/entertain/nanhai/place/delete.json';
            var data={
                id:id,
                type:type
            }
            mui.getJSON(url,data,function (res) {
                if (res.ok) {
                    var $parent = $($li).parent()
                    $($li).remove();
                    if ($parent.find('li').length < 1) {
                        var msg = ''
                        if ($parent.hasClass('ui-use')) {
                            msg = '暂无可用场地哦'
                            var useCount = $('#useCount').text().replace('未使用（', '')
                                .replace('）', '')
                            $('#useCount').text('未使用（' + (Number(useCount) - 1) + '）')
                        } else {
                            msg = '您没有失效的场地哦'
                            var nouseCount = $('#nouseCout').text().replace('已失效（', '')
                                .replace('）', '')
                            $('#nouseCount').text('已失效（' + (Number(nouseCount - 1)) + '）')
                        }
                        $parent.append('<li class="mui-table-view-cell"><div class="mui-text-center">' + msg + '</div></li>')
                    }
                    mui.swipeoutClose($li);
                }
            })
        }
    })
</script>
</body>

</html>