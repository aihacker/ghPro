<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>活动详情</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        /*plus*/
        html, body {
            height: 100%;
        }

        #main-bg-img {
            width: 100%;
        }

        #btns {
            width: 100%;
            position: absolute;
            top: 40%;
        }

        .p-1 {
            text-align: center;
        }

        .p-1 .img-1 {
            width: 46%;
        }

        .p-2 {
            margin: 0px;
        }

        .p-2 img {
            width: 25%;
            margin-left: 6%;
        }

    </style>
</head>

<body>

<div class="mui-content" id="main-bg">
    <img src="${home}${act.bgPath}" id="main-bg-img">

    <div id="btns">
        <!--style="visibility: hidden"-->
        <p class="p-1" style="visibility: hidden"><img src="${home}/weixin/image/entertain/sport/act/join.png"
                                                       class="img-1"></p>

        <p class="p-2">
            <img src="${home}/weixin/image/entertain/sport/act/check.png" class="img-1">
            <img src="${home}/weixin/image/entertain/sport/act/rank.png" class="img-2">
            <img src="${home}/weixin/image/entertain/sport/act/change.png" class="img-3"></p>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    var id = '${param.id}';
    alert(${act.bgPath});

    mui('.p-1').on('tap', '.img-1', function () {
//        mui.alert('活动还未开始哦', '活动提示', function () {
//
//        });
       /*  mui.ajax('../',{
            data:{
                deptid: ,
                start:,
                end:,
                userid:,
                actId:
            } ,
            dataType : 'json',
            type : 'post',
            success:function(){
                mui.alert('报名成功!')
            },
            error:function () {

            }
         });*/
    })
    mui('.p-2').on('tap', 'img', function () {
        var cls = this.getAttribute('class')
        if (cls == 'img-1') {
            mui.openWindow('../score/show.html?id=' + id);
        } else if (cls == 'img-2') {
            mui.openWindow('../list.html')
        } else {
            // mui.openWindow(homePath + '/wx/pub/user/score/exchange.html')
            mui.alert('暂未开放', '活动提示', function () {

            });
        }
    })
</script>

</body>

</html>
