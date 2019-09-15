<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/27
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>现有饭堂</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-search {
            position: absolute;
            right: 15px;
            margin-left: 80px;
            width: 82%;
        }

        .ui-search input[type=search] {
            background-color: #fff;
        }

        .mui-table-view .mui-media-object {
            width: 50px;
            height: 50px;
            max-width: 60px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
            border: 1px solid gainsboro;
            padding: 1px;
        }

        .mui-media-body p {
            margin-top: 4px;
            max-height: 20px;
        }

        .mui-media-body {
            margin-top: 2px;
        }

        .mui-media-body p {
            margin-top: 4px;
        }

        .mui-media-body .mui-badge {
            position: absolute;
            right: 10px;
            top: 10px;
        }

        .mui-table-view:before {
            height: 0;
        }

        .mui-media-body button {
            padding: 2px 2px;
            font-size: 12px;
            position: absolute;
            right: 4px;
            bottom: 10px;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <div class="mui-input-row mui-search ui-search mui-active">
        <input id="searchInput" type="search" class="mui-input-clear" placeholder="搜索添加">
    </div>
</header>

<!--下拉刷新容器-->
<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view"></ul>
    </div>
</div>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="{{=it.path}}">
            <div class="mui-media-body">
                <div class="mui-ellipsis">{{=it.name}}</div>
                <p class="mui-ellipsis">{{=it.info}}</p>
                {{=it.statusTxt}}
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/canteen/search/list.json',
            data: {status: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无饭堂哦',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['statusTxt'] = get_status(d.status)
            d['path'] = wxgh.get_thumb(d, 'chat.png');
            d['info'] = d.info ? d.info : '暂无简介'
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id)
            })
            return $item[0];
        }

        $('#searchInput').on('keyup', function () {
            var $self = $(this)
            var val = $self.val()
            var preVal = $self.data('prevVal')
            if (val == preVal) {
                return
            }
            if(!val)
                return
            $self.data('preVal', val)
            refresh.refresh({key: val})
        })

        function get_status(status) {
            var str, color
            if (status == 0) {
                str = '审核中'
                color = 'grey'
            } else if (status == 1) {
                str = '已关注'
                color = 'success'
            } else if (status == 2) {
                str = '未通过'
                color = 'danger'
            } else if (status == 3 || !status) {
                str = '未关注'
                color = 'warning'
            }
            return '<span class="mui-badge mui-badge-' + color + '">' + str + '</span>'
        }
    })
</script>
</body>

</html>
