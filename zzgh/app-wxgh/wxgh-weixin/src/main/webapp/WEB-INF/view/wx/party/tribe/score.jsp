<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>我的积分</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .ui-list-title {
            background-color: #fff;
        }

        .ui-list-title {
            padding: 10px;
        }

        .ui-bg {
            background-color: #63afe3;
            width: 100%;
            height: 180px;
            position: relative;
            text-align: center;
        }

        .ui-head-div {
            position: absolute;
            width: 100%;
            margin-top: 24px;
        }

        .ui-head-div .ui-total-score {
            color: #fff;
            display: block;
        }

        .ui-user-head {
            width: 95px;
            height: 95px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .ui-score {
            font-size: 18px;
            color: #3399ff;
            margin-right: 5px;
        }

        #refreshContainer {
            top: 220px;
        }

        .mui-media-object {
            margin-right: -50px;
        }
    </style>
</head>

<body>

<div class="mui-content">
    <div class="ui-bg">
        <div class="ui-head-div">
            <img class="ui-user-head" src="${empty wxgh_user.avatar?'../image/default/user.png':wxgh_user.avatar}"/>
            <span class="ui-total-score">总积分：${sumScore}</span>
        </div>
    </div>

    <div class="ui-list-title">积分详情：</div>

    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view mui-table-view-chevron">
            </ul>
        </div>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="mui-media-object mui-pull-right">
                <span class="ui-score">{{=it.score}}分</span></div>
            <div class="mui-media-body">
                {{=it.info}}
                <p class="mui-ellipsis">{{=it.addTime}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/comm/mobile/js/refresh.js"></script>

<script type="text/javascript">
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/list_score.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
            d['info'] = d.info ? d.info : '暂无介绍'
            d['score'] = d.score > 0 ? ('+' + d.score) : d.score
            var $item = refresh.getItem(d)
            return $item[0]
        }
    });
</script>
</body>

</html>