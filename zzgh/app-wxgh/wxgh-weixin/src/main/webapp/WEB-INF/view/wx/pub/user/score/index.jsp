<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/22
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的积分</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #myscores {
            margin-top: 0px;
        }

        #sumscores {
            padding-top: 20px;
            display: block;
            text-align: center;
        }

        #scores {
            display: block;
            text-align: center;
            padding-bottom: 30px;
            font-size: 28px;
            color: #3399ff;
        }

        #info {
            padding-top: 5px;
            padding-bottom: 5px;
            padding-left: 10px;
            color: #3399ff;
        }

        #info span {
            font-size: 16px;
        }

        .mui-media-object-body {
            margin-right: 20px;
        }

        a {
            text-decoration: none;
        }

        .ui-score {
            font-size: 18px;
            color: #3399ff;
        }

        #refreshContainer {
            top: 210px;
            background-color: #fff;
        }

        #noGetScore {
            font-size: 14px;
            text-decoration: blink;
        }
    </style>
</head>
<body>
<div class="mui-content">

    <div id="myscores" class="mui-table-view">
        <span id="sumscores">积分总额</span><br/>
        <span id="scores">${empty sumScore?0:sumScore}分
        <small id="noGetScore" class="ui-text-info">(包括场馆预约:${yuyueScore}分)</small>
        </span>
        <a style="position: absolute;top: 10px;right: 10px;font-size: 15px;" href="exchange.html">积分兑换</a>
        <a style="position: absolute;top: 40px;right: 10px;font-size: 15px;" href="my_exchange.html">我的兑换</a>
    </div>

    <div id="info"><span>积分明细</span></div>
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>
<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="mui-media-object mui-media-object-body mui-pull-right">
                <span class="ui-score">{{=it.score}} 分</span>
            </div>
            <div class="mui-media-body">
                {{=it.info}}
                <p class="mui-ellipsis">
                    {{=it.addTime}}
                </p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/score_list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '你未获得积分哦',
            bindFn: bindfn,

            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        });

        function bindfn(d) {
            d['score'] = d.score > 0 ? ('+' + d.score) : d.score;
            d['addTime'] = ui.timeAgo(d.addTime);
            var $item = refresh.getItem(d)

            return $item[0]
        }

        $('#noGetScore').on('tap', function () {
            ui.alert('场馆预约积分只能用于预约场馆哦，不可以用于商品兑换；其他积分可兑换商品，不可预约场馆！');
        })
    })


</script>
</body>
</html>