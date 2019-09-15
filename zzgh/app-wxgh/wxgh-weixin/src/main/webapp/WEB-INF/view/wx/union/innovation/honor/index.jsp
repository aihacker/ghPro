<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/29
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>荣誉墙</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<style>
    .mui-table-view .mui-media-object {
        width: 60px;
        height: 60px;
        max-width: 60px;
        -webkit-border-radius: 50%;
        border-radius: 50%;
        border: 1px solid gainsboro;
        padding: 1px;
    }

    .mui-media-body {
        margin-top: 5px;
    }

    .mui-media-body p {
        margin-top: 10px;
    }

    .mui-table-view:before {
        height: 0;
    }
</style>
<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>


<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell"
        style="background-color: white; margin-bottom: 7px;">
        <a href="javascript:void(0);" style="color: #000;">
            <%--<img src="{{=it.avatar}}" style="float: left;width: 14%;height: 49px;">--%>
            <%--<div style="float: left;">--%>
                <%--<div style="padding-left:10px;">--%>
                    <%--<p class="mui-ellipsis" style="color: black;font-size: 18px;">--%>
                        <%--{{=it.username}}--%>
                    <%--</p>--%>
                    <%--<p class="mui-ellipsis">--%>
                        <%--上传时间：{{=it.applyTime}}--%>
                    <%--</p>--%>
                <%--</div>--%>
                <h4>名称：{{=it.name}}</h4>
                <h4>奖项：{{=it.awardGrade}}</h4>
                <h4>单位/个人：{{=it.people}}</h4>
            </div>
        </a>
    </li>
</script>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>

    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + "/wx/union/innovation/honor/list.json",
            data: {status: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无信息',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['applyTime'] = formatTime(d.applyTime)
            d['avatar'] = d.avatar ? (wxgh.get_image(d.avatar)) : homePath + '/image/default/chat.png'
            var $item = refresh.getItem(d)

            return $item[0];
        }

//        mui('.mui-scroll-wrapper').scroll({
//            indicators: true,
//            deceleration: 0.0005//是否显示滚动条
//        });
        function formatTime(time) {
            //   格式：yyyy-MM-dd hh:mm:ss
            var date = new Date(time);
            Y = date.getFullYear() + '-';
            M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
            D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
            h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
            m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes();
            return Y + M + D + h + m;
        }

    })


</script>
</body>

</html>
