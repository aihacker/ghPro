<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/8
  Time: 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>今日排行</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .bg-div {
            position: relative;
        }

        .bg-div .ui-img-div {
            height: 200px;
        }

        .user-bg {
            width: 100%;
            height: 100%;
            position: absolute;
            left: 0;
            top: 0;
            padding-top: 70px;
            text-align: center;
            background-color: rgba(0, 0, 0, .4);
        }

        .user-bg span {
            line-height: 45px;
            display: inline-block;
            font-size: 15px;
            color: #fff;
            margin-left: 2px;
            position: relative;
            top: -16px;
        }

        .user-bg img {
            width: 45px;
            height: 45px;
            display: inline-block;
        }

        #refreshContainer {
            top: 270px;
        }

        #sportList {
            background-color: #fff;
        }

        .sport-item {
            display: flex;
            padding: 5px 15px;
            background-color: #fff;
            position: relative;
        }

        .sport-item:not(.no):after {
            position: absolute;
            right: 0;
            bottom: 0;
            left: 15px;
            height: 1px;
            content: '';
            -webkit-transform: scaleY(.5);
            transform: scaleY(.5);
            background-color: #c8c7cc;
        }

        .sport-item:last-child:after {
            height: 0;
        }

        .sport-item img.ui-circle {
            width: 45px;
            height: 45px;
            line-height: 50px;
        }

        .sport-item .item-body {
            flex: 1;
            padding: 0 6px;
        }

        .item-body p {
            margin: 0;
        }

        .sport-item .step {
            max-width: 80px;
            line-height: 45px;
            display: inline-block;
            min-width: 60px;
            text-align: center;
            color: #4cd964;
            font-size: 18px;
        }

        .sport-item .numb {
            width: 40px;
            height: 32px;
            background-repeat: no-repeat;
            background-size: 25px 32px;
            background-position: center;
            line-height: 30px;
            font-size: 14px;
            text-align: center;
            color: #fff;
            position: relative;
            top: 8px;
        }

        .numb.num-1 {
            background-image: url(${home}/weixin/image/sport/nob-1.png);
        }

        .numb.num-2 {
            background-image: url(${home}/weixin/image/sport/nob-2.png);
        }

        .numb.num-3 {
            background-image: url(${home}/weixin/image/sport/nob-3.png);
        }

        .numb.other {
            background-image: url(${home}/weixin/image/sport/nob-other.png);
            background-size: 38px 42px;
            height: 45px;
            line-height: 40px;
            top: 2px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="bg-div">
        <div class="ui-img-div">
            <img src="${home}/weixin/image/sport/wikibg.png"/>
        </div>
        <div class="user-bg">
            <div class="mui-loading">
                <div class="mui-spinner"></div>
            </div>
        </div>
    </div>
    <div onclick="javascript:mui.openWindow('history.html?userid=${s.userid}');" class="sport-item no">
        <img class="ui-circle" src="${wxgh_user.avatar}"/>
        <div class="item-body">
            <span>${wxgh_user.name}</span>
            <p class="mui-ellipsis">${empty s?'您今天还没有步数哦':s.deptname}</p>
        </div>
        <span class="step" id="currentStep">${s.stepCount}</span>
        <span class="numb other">${empty s?0:s.paiming}</span>
    </div>
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <div id="sportList">
            </div>
        </div>
    </div>
</div>
<script type="text/html" id="itemTpl">
    <div class="sport-item">
        <img class="ui-circle" src="{{=it.avatar}}"/>
        <div class="item-body">
            <span class="name">{{=it.username}}</span>
            <p class="mui-ellipsis">{{=it.deptname}}</p>
        </div>
        <span class="step">{{=it.stepCount}}</span>
        <span class="numb"></span>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var $userBg = $('.user-bg'),
            dateid = '${param.dateid}';
        var $load = $userBg.find('.mui-loading');
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/today.json',
            data: {dateId: dateid},
            //ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            dataEl: '#sportList',
            bindFn: bindfn,
            success: function (type) {
                $load.remove();
            },
            emptyHtml: '<div class="ui-empty">暂无排行哦~</div>',
            errorFn: function (type) {
                console.log(type)
                mui.toast('获取失败...')
            }
        });



       function bindfn(d) {
            d['avatar'] = wxgh.get_avatar(d.avatar);
            var $item = refresh.getItem(d);
            var paiming = d.paiming;
            if (paiming < 4) {
                $item.find('.numb').addClass('num-' + paiming);
            } else {
                $item.find('.numb').addClass('other').text(paiming);
            }

            if (paiming == 1) {
                $userBg.empty();
                $userBg.append('<img class="ui-circle" src="' + d['avatar'] + '"/><span>' + d['username'] + '占领了封面</span>');
            }

            $item.on('tap', function () {
                mui.openWindow('history.html?userid=' + d.userid);
            });
            paiming = paiming + 1;
            return $item[0]
        }
    })
</script>
</body>
</html>