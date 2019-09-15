<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/19
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>选择时间</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        #siteControl,
        #timeControl {
            background-color: #fff;
        }

        #siteControl .mui-control-item,
        #timeControl .mui-control-item {
            color: rgba(0, 0, 0, .6);
            padding: 0 5px;
        }

        #siteControl .mui-control-item.mui-active,
        #timeControl .mui-control-item.mui-active {
            color: #fff;
        }

        #timeControl.mui-segmented-control-inverted .mui-control-item.mui-active,
        #siteControl.mui-segmented-control-inverted .mui-control-item.mui-active {
            border: none;
        }

        #timeControl {
            height: 70px;
            padding: 5px 8px 10px 8px;
        }

        #timeControl .mui-scroll {
            height: 70px
        }

        #siteControl .mui-control-item.mui-active .ui-lable,
        #timeControl .mui-control-item.mui-active .ui-lable-circle {
            background-color: #32c661;
        }

        #siteControl {
            margin-top: 4px;
            padding: 0 8px;
        }

        .ui-lable {
            background-color: gainsboro;
            padding: 4px 15px;
            -webkit-border-radius: 5px;
            border-radius: 5px;
        }

        .ui-lable-circle {
            background-color: gainsboro;
            width: 60px;
            height: 60px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
            text-align: center;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .ui-lable-circle span {
            display: block;
            line-height: 15px;
        }

        .ui-lable-circle small {
            line-height: 16px;
            display: block;
        }

        #timeListDiv .mui-row {
            background-color: #fff;
            box-sizing: border-box;
        }

        #timeListDiv .mui-row div[class^=mui-col-] {
            padding-top: 5px;
            padding-bottom: 5px;
        }

        .ui-time-item {
            background-color: gainsboro;
            padding: 4px 12px;
            display: inline-block;
            margin: 5px 0;
            -webkit-border-radius: 2px;
            border-radius: 2px;
        }

        .ui-time-item.ui-use {
            background-color: #32c661;
            color: #fff;
        }

        .ui-time-item:active {
            opacity: .8;
        }

        .ui-time-item.ui-me {
            background-color: rgba(8, 168, 244, .6);
            color: #fff;
        }

        .ui-tip-item span {
            display: inline-block;
            width: 20px;
            height: 20px;
            position: relative;
            top: 5px;
        }

        .ui-tip-item {
            display: inline-block;
            padding: 2px 5px;
        }

        .ui-tip-item small {
            display: inline-block;
            line-height: 20px;
            color: #777;
        }

        .ui-tip-item.ui-nouse span {
            background-color: gainsboro;
        }

        .ui-tip-item.ui-meyuyue span {
            background-color: rgba(8, 168, 244, .6);
        }

        .ui-tip-item.ui-useable span {
            background-color: #32c661;
        }

        #myOrderMain {
            background-color: #fff;
        }

        #myOrderMain h5 {
            padding: 8px 10px;
            font-size: 16px;
        }

        #myOrderMain .ui-order-item {
            text-align: center;
            padding: 5px 0;
            position: relative;
            border: 1px solid gainsboro;
            margin: 8px;
            -webkit-border-radius: 5px;
            border-radius: 5px;
        }

        .ui-order-item:after {
            content: '\e434';
            line-height: 1.42;
            font-family: Muiicons;
            font-size: 18px;
            font-weight: normal;
            font-style: normal;
            line-height: 1;
            display: inline-block;
            text-decoration: none;
            -webkit-font-smoothing: antialiased;
            position: absolute;
            right: -8px;
            top: -8px;
            color: #32c661;
        }

        .ui-order-item:active {
            opacity: .8;
        }

        .ui-order-item span,
        .ui-order-item small {
            font-size: 15px;
        }

        #nowSub {
            padding: 6px;
        }

        #yuyueBtn {
            position: absolute;
            bottom: 0px;
            width: 100%;
            left: 0px;
        }

        #yuyueBtn button {
            margin-bottom: 2px;
        }

        #timeListDiv button.active {
            position: relative;
        }

        .mui-btn-success.active {
            background-color: rgba(76, 217, 100, .6);
            border-color: rgba(76, 217, 100, .6);
            opacity: 1;
        }

        .mui-btn-success.active:active {
            opacity: .7;
        }

        #timeListDiv button.active:after {
            content: url(${home}/weixin/image/entertain/place/time_selected.png);
            position: absolute;
            right: 2px;
            top: 2px;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">选择时间</h1>
</header>

<div class="mui-content">
    <!-- 时间选择 -->
    <div id="timeControl"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <c:forEach items="${times}" var="t">
                <a data-week="${t.weekInt}" class="mui-control-item ${t.isToday?'mui-active':''}">
                    <div class="ui-lable-circle">
                        <div>
                            <span>${t.time}</span>
                            <small>${t.week}</small>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>

    <!-- 场馆选择 -->
    <div id="siteControl"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <c:forEach items="${sites}" varStatus="i" var="s">
                <a data-id="${s.id}" class="mui-control-item ${i.first?'mui-active':''}">
                    <span class="ui-lable">${s.name}</span>
                </a>
            </c:forEach>
        </div>
    </div>

    <!-- 选择时间 -->
    <div id="timeListDiv">
        <div class="mui-content">
            <div class="mui-row mui-text-center" id="timeDiv">
                <c:choose>
                    <c:when test="${empty timeList}">
                        <div class="mui-content-padded mui-text-center ui-text-info">暂无可预约时间哦</div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${timeList}" var="tim">
                            <div data-id="${tim.id}" class="mui-col-sm-4 mui-col-xs-6">
                                <button type="button"
                                        class="mui-btn mui-btn-success">${tim.startTime}~${tim.endTime}</button>
                                    <%--<span class="ui-time-item ui-use">${tim.startTime}~${tim.endTime}</span>--%>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>

            <div style="background-color: #fff;padding: 0 5px;">
                <div class="ui-tip-item ui-useable">
                    <span></span>
                    <small>可预约</small>
                </div>
                <div class="ui-tip-item ui-nouse">
                    <span></span>
                    <small>不可预约</small>
                </div>
                <%--<div class="ui-tip-item ui-meyuyue">--%>
                <%--<span></span>--%>
                <%--<small>我的预约</small>--%>
                <%--</div>--%>
            </div>

            <%--<div id="myOrderMain">--%>
            <%--<h5>预约时间：11月20日</h5>--%>

            <%--<div class="mui-row">--%>
            <%--<div class="mui-col-xs-6">--%>
            <%--<div class="ui-order-item">--%>
            <%--<small>00:00-01:00</small>--%>
            <%--<span class="mui-badge">1号场</span>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>

            <div id="yuyueBtn">
                <button id="nowSub" type="button" class="mui-btn mui-btn-block mui-disabled mui-btn-success">立即预约
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}';
    var type = '${param.type}';

    var week = '${weekId}';
    var siteId = '${siteId}';

    var page = {
        init: function () {
            var $timeEl = wxgh.getElement('timeControl')
            var $siteEl = wxgh.getElement('siteControl')
            var $timeDiv = wxgh.getElement('timeDiv')

            var $subBtn = wxgh.getElement('nowSub');

            mui($timeEl).on('tap', '.mui-control-item', function (e) {
                var dataWeek = this.getAttribute('data-week')
                week = dataWeek;
                page.request_time(week, siteId)
            })

            mui($siteEl).on('tap', '.mui-control-item', function (e) {
                var dataSiteId = this.getAttribute('data-id')
                siteId = dataSiteId;
                page.request_time(week, siteId)
            })

            mui($timeDiv).on('tap', 'button', function (e) {
                if (this.classList.contains('active')) {
                    this.classList.remove('active')
                } else {
                    mui.each($timeDiv.querySelectorAll('button.mui-btn-success'), function () {
                        if (this.classList.contains('active')) {
                            this.classList.remove('active')
                        }
                    })
                    this.classList.add('active')
                }
                page.change_btn_status();
            })

            //立即预约
            $subBtn.addEventListener('tap', function () {
                page.sub_yuyue()
            })

            this.$timeDiv = $timeDiv;
            this.$subBtn = $subBtn
        },
        sub_yuyue: function () {
            var timeId = 0;
            mui.each(page.$timeDiv.querySelectorAll('button.mui-btn-success'), function () {
                if (this.classList.contains('active')) {
                    timeId = this.parentNode.getAttribute('data-id')
                }
            })

            var url = homePath + '/wx/entertain/nanhai/place/yuyue_json.json';
            var info = {
                siteId: siteId,
                timeId: timeId
                //action: 'yuyue'
            }
            mui.post(url, info, function (res) {
                if (res.ok) {
                    ui.showToast('预约成功', function () {
                        mui.back();
                    })
                } else {
                    mui.toast(res.msg)
                }
            }, 'json')
        },
        change_btn_status: function () {
            var btns = page.$timeDiv.querySelectorAll('button.active')
            if (btns.length > 0) {
                page.$subBtn.classList.remove('mui-disabled')
            } else {
                page.$subBtn.classList.add('mui-disabled')
            }
        },
        request_time: function (w, s) {

            if (!this.loading) {
                this.loading = ui.loading('获取中...');
            }
            this.loading.show()

            var url = homePath + '/wx/entertain/nanhai/place/get_time.json';
            var info = {
                siteId: s,
                cateId: type,
                week: w
            }
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    var times = res.data;
                    if (times && times.length > 0) {
                        page.$timeDiv.innerHTML = ''
                        for (var i = 0; i < times.length; i++) {
                            page.$timeDiv.appendChild(page.create_time_item(times[i]))
                        }
                    } else {
                        page.$timeDiv.innerHTML = '<div class="mui-content-padded mui-text-center ui-text-info">暂无可预约时间哦</div>';
                    }
                }
                page.loading.hide()
            })
        },
        create_time_item: function (t) {
            var e_item = document.createElement('div')
            e_item.className = 'mui-col-sm-4 mui-col-xs-6'
            e_item.setAttribute('data-id', t.id)
            if (t.status == 0) {
                e_item.innerHTML = '<button type="button" class="mui-btn mui-btn-success">' + t.startTime + '~' + t.endTime + '</button>'
            } else {
                e_item.innerHTML = '<button type="button" class="mui-btn mui-btn-grey mui-disabled">' + t.startTime + '~' + t.endTime + '</button>'
            }
            return e_item;
        }
    }

    window.onload = page.init();

</script>
</body>

</html>
