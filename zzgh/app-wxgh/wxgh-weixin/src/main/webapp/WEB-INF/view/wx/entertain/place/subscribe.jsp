<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/22
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>选择预约时间</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        #timeControl,
        #yuyueControl {
            background-color: #fff;
            color: #646464;
        }

        #timeControl .mui-control-item {
            color: rgba(0, 0, 0, .6);
            padding: 0 5px;
        }

        #timeControl .mui-control-item.mui-active {
            color: #fff;
        }

        #timeControl.mui-segmented-control-inverted .mui-control-item.mui-active {
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
            background-color: #6cc3eb;
        }

        table.mui-table {
            width: auto;
        }

        table td,
        table th {
            border: solid #fff;
            border-width: 0px 3px 3px 0px;
            padding: 5px 0px;
            min-width: 110px;
            position: relative;
        }

        table tr td:first-child {
            border: solid #646464;
            border-width: 1px;
            min-width: 80px;

        }

        .ui-table-div {
            text-align: center;
            width: 100%;
        }

        table tr:first-child td {
            border: solid #646464;
            border-width: 1px;
        }

        table tr td {
            text-align: center;
        }

        .ui-lable-circle {
            background-color: #efeff5;
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

        .ui-table-div {
            padding: 10px 8px;
        }

        td div[class^="ui-yuyue-"] {
            width: 100%;
            height: 100%;
            display: block;
            position: absolute;
            left: 0;
            top: 0;
            -webkit-border-radius: 1px;
            border-radius: 1px;
            text-align: center;
            line-height: 34px;
            font-size: 13px;
        }

        .ui-yuyue-no {
            background-color: #cccccc;
        }

        td div[class^=ui-yuyue-]:active {
            opacity: .5;
        }

        .ui-yuyue-yes {
            background-color: #32c774;
            color: #fff;
        }

        .ui-yuyue-yes.active {
            background-color: rgba(76, 217, 100, .6);
        }

        .ui-yuyue-yes.active:after {
            content: url(${home}/weixin/image/entertain/place/time_selected.png);
            position: absolute;
            right: 2px;
            top: 0px;
        }

        .ui-yuyue-close {
            background-color: orange;
            color: #fff;
        }

        #nowSub {
            padding: 6px;
        }

        #yuyueBtn {
            position: fixed;
            bottom: 0px;
            width: 100%;
            left: 0px;
        }

        #yuyueBtn button {
            margin-bottom: 2px;
        }

        .ui-tip {
            background: #fff;
            padding: 10px;
        }

        .ui-tip .ui-tip-item {
            display: inline-block;
            margin-right: 10px;
        }

        .ui-tip-item span {
            display: inline-block;
            width: 25px;
            height: 25px;
        }

        .ui-tip-item small {
            position: relative;
            top: -8px;
        }

        .ui-tip-item span.green {
            background: #32c774;
        }

        .ui-tip-item span.info {
            background: #CCCCCC;
        }

        .ui-tip-item span.close {
            background: orange;
        }

        #mainScroll {
            top: 124px;
            bottom: 45px;
        }


    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>
<%--<h1 class="mui-title">选择预约时间</h1>--%>
<%--</header>--%>

<div class="mui-content">
    <div id="timeControl"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <c:forEach items="${times}" var="t">
                <a data-week="${t.weekInt}" data-single="${t.isSingle}" data-id="${t.dateId}" class="mui-control-item${t.today?' mui-active':''}">
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
    <hr style="background-color: #efeff4;height: 8px;border: none;">
    <div class="mui-scroll-wrapper" id="mainScroll">
        <div class="mui-scroll">

            <div class="ui-tip">
                <div class="ui-tip-item">
                    <span class="green"></span>
                    <small>可预约</small>
                </div>
                <div class="ui-tip-item">
                    <span class="info"></span>
                    <small>已被预约</small>
                </div>
                <div class="ui-tip-item">
                    <span class="close"></span>
                    <small>关闭</small>
                </div>
            </div>

            <div id="yuyueControl"
                 class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
                <div class="mui-scroll">
                    <div class="ui-table-div">
                        <table class="mui-table" id="yuyueListTable">
                        </table>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div id="yuyueBtn">
        <button id="nowSub" type="button" class="mui-btn mui-btn-block mui-disabled mui-btn-success">立即预约
        </button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()
    //			mui('#timeControl').scroll({
    //				deceleration: 0.0005
    //			});

    var homePath = '${home}';
    var placeId = '${param.id}';
    var cateId = '${param.type}';
    var defaultWeek = '${weekStr}';
    var defaultWeekInt = '${week}';
    var defaultIsSingle = '${isSingle}';
    var dateId = '${dateId}'
    var thisWeek = ${isSingle}

    mui('#yuyueControl').scroll({
        scrollY: false,
        scrollX: true,
        indicators: false,
        deceleration: 0.0005
    });

    mui('#mainScroll').scroll({
        deceleration: 0.0005,
        indicators: false
    });

    $(function () {
        initSize()
        $(window).on('resize', initSize())

        $('#timeControl').on('tap', 'a.mui-control-item', function () {
            var week = $(this).attr('data-week')
            var isSingle = $(this).attr('data-single')
            var weekStr = $(this).find('small').text()
            dateId = $(this).attr('data-id')
            thisWeek = isSingle;
            getList(week, weekStr, isSingle);
        })

        function initSize() {
            var $yuyue = $('#yuyueControl')
            $yuyue.css('height', $yuyue.find('.ui-table-div').outerHeight())
        }

        var $tableList = $('#yuyueListTable')

        getList(defaultWeekInt, defaultWeek, defaultIsSingle);

        function getList(week, weekStr, isSingle) {
            var url = homePath + '/wx/entertain/place/get_list.json'
            var info = {};
            info['placeId'] = placeId
            info['cateId'] = cateId
            info['isSingle'] = isSingle
            info['week'] = week
            //info['action'] = 'get_list'

            if (!this.loading) {
                this.loading = ui.loading('获取中...')
            }
            this.loading.show()
            var self = this;
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    $tableList.empty()
                    var yuyue = res.data
                    createList(yuyue, weekStr)
                }
                self.loading.hide()
            })
        }

        var $btn = $('#nowSub')

        $btn.on('tap', function () {

            mui.confirm('请选择付款方式', '选择付款', ['现金', '积分'], function (e) {
                var $yuyue = $tableList.find('.ui-yuyue-yes.active')
                var payType = 0
                if (e.index == 1) {
                    payType = 1
                } else {
                    payType = 2
                }
                var json = [];
                $.each($yuyue, function () {
                    var timeId = $(this).attr('data-id')
                    var single = $(this).attr('data-single')
                    var siteId = $(this).parent().parent().find('td:eq(0)').attr('data-id')
                    json.push({timeId: timeId, siteId: siteId, dateId: dateId, payType: payType, isSingle: single})
                })
                subYuyue(JSON.stringify(json))
            })
        })

        function initEvent() {
            $('div.ui-yuyue-yes').on('tap', function () {
                if ($(this).hasClass('active')) {
                    $(this).removeClass('active')
                } else {
                    $(this).addClass('active')
                }
                var len = $tableList.find('.ui-yuyue-yes.active').length
                if (len > 0) {
                    $btn.removeClass('mui-disabled')
//                    if (len > 1) {
//                        alert('对不起，目前仅支持预约一个场地')
//                        $(this).removeClass('active')
//                    }
                } else {
                    $btn.addClass('mui-disabled')
                }
            })
        }

        function createList(d, weekStr) {
            var times = d.times
            var sites = d.sites
            var $firstTr = $('<tr></tr>')
            $firstTr.append('<td>' + weekStr + '</td>')
            for (var i in times) {
                $firstTr.append('<td>' + (times[i].startTime + '~' + times[i].endTime) + '</td>')
            }
            $tableList.append($firstTr)
            for (var j in sites) {
                var $tr = $('<tr></tr>')
                $tr.append('<td data-id="' + sites[j].id + '">' + sites[j].name + '</td>')
                var tims = sites[j].times
                var price = sites[j].price
                var score = sites[j].score
                for (var d in tims) {
                    if (tims[d].status != -1) {
                        if(tims[d].status == 4){
                            var txt = "场地关闭";
                            $tr.append('<td><div data-id="' + tims[d].timeId + '" class="ui-yuyue-close">' + txt + '</div></td>')
                        }else{
                            var isYu = false
                            var isGu = false;
                            if (tims[d].status == 0 && tims[d].type == 1) {
                                isYu = true
                            }else if(tims[d].type == 2)
                                isGu = true;
                            var txt = isYu ? (price + '元 / ' + score + '积分') : ((tims[d].status == 1 || tims[d].status == 3)?'已被预约':'已失效')
                            if(isGu)
                                txt = '固定场'
                            var claz = isYu ? 'ui-yuyue-yes' : 'ui-yuyue-no'
                            $tr.append('<td><div data-single="'+thisWeek+'" data-id="' + tims[d].timeId + '" class="' + claz + '">' + txt + '</div></td>')
                        }
                    } else {
                        $tr.append('<td>&nbsp;</td>')
                    }
                }
                $tableList.append($tr)
            }
            initSize()
            initEvent()
        }

        function subYuyue(json) {
            var url = homePath + '/wx/entertain/place/yuyue_json.json';
            var info = {
                json: json
                //action: 'yuyue'
            }
            mui.post(url, info, function (res) {
                if (res.ok) {
                    ui.showToast('预约成功', function () {
                        mui.back();
                    })
                } else {
                    alert(res.msg)
                    location.reload()
                }
            }, 'json')
        }

    })
</script>
</body>

</html>
