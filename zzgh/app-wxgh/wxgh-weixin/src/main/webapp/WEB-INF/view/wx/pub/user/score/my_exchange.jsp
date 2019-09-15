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


        #info span {
            font-size: 16px;
        }

        .mui-media-object-body {
            margin-right: 20px;
        }

        .p-img{
            width: 50px;
            float: left;
            margin-right: 5px;
        }

        a {
            text-decoration: none;
        }

        .ui-score {
            font-size: 18px;
            color: #3399ff;
        }

        #refreshContainer {
            background-color: #fff;
        }

    </style>
</head>
<body>

<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="1" class="mui-control-item mui-active">已兑换
        </a>
        <a data-type="2" class="mui-control-item">未兑换
        </a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <%--<div class="ui-act-div"></div>--%>
            <ul class="mui-table-view"></ul>
        </div>
    </div>
</div>

<%--{{? it.confirm != 1}}--%>
<%--<div class="mui-media-object mui-media-object-body mui-pull-right">--%>
    <%--<button class="mui-btn mui-btn-primary mui-pull-right ui-confirm-yes">确认兑换</button>--%>
<%--</div>--%>
<%--<div class="mui-media-object mui-media-object-body mui-pull-right" style="margin-right: 50px;">--%>
    <%--<button class="mui-btn mui-btn-danger mui-btn-wo mui-pull-right ui-confirm-no">取消兑换</button>--%>
<%--</div>--%>
<%--{{?}}--%>

<script type="text/html" id="itemTpl">
    {{? it._type == 1}}
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="p-img" src="${home}{{=it.path}}" />
            <div class="mui-media-body">
                {{=it.goodsName}} [<span class="ui-score">{{=it.score}}</span> 积分]
                <p class="mui-ellipsis">
                    {{=it.addTime}}
                </p>
            </div>
        </a>
    </li>
    {{??}}
    <li class="mui-table-view-cell mui-media">
        <img class="p-img" src="${home}{{=it.path}}" />
        <a href="javascript:;">
            <div class="mui-media-body">
                {{=it.name}} [<span class="ui-score">{{=it.points}}</span> 积分]
                <p class="mui-ellipsis">
                    {{=it.info}}
                </p>
            </div>
        </a>
    </li>
    {{?}}
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {

        var _type = 1;  // 1 为已兑换 2 未兑换

        var refresh = window.refresh('#refreshContainer', {
            url: 'api/my_exchange.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '你没有兑换记录哦',
            bindFn: bindfn,

            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        });

        function bindfn(d) {
            d['addTime'] = ui.timeAgo(d.addTime);
            d['_type'] = _type;
            var $item = refresh.getItem(d)

            if(_type == 1)
                $item.on('tap', function () {
                    window.location.href = "exchange_show.html?id="+d.id;
                })
            else
                $item.on('tap', function () {
                    window.location.href = "show.html?id="+d.id;
                })
            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var type = $(this).data('type')
            _type = type;
            refresh.refresh({type: type})
        })

    })
</script>
</body>
</html>