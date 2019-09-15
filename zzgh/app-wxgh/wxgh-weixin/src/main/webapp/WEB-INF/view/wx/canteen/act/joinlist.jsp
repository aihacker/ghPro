<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>参加活动</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-act-div {
            background-color: #fff;
        }

        .ui-act-item {
            position: relative;
        }

        .ui-act-item a {
            display: flex;
        }

        .ui-act-item .ui-act-img {
            flex: 3;
            height: 142px;
            padding: 10px;
            width: 100px;
        }

        .ui-act-body {
            flex: 7;
            padding: 8px;
        }

        .ui-act-time {
            position: absolute;
            right: 10px;
            top: 8px;
            font-size: 13px;
        }

        .ui-act-body h5 {
            font-weight: 500;
            color: #000;
            padding-right: 64px;
            line-height: 18px;
            font-size: 16px;
        }

        .ui-act-body div {
            font-size: 14px;
            line-height: 24px;
        }

        .ui-ellipsis {
            display: -webkit-box;
            overflow: hidden;
            text-overflow: ellipsis;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 1;
        }

        .ui-text-info .fa {
            padding-right: 5px;
            font-size: 17px;
            width: 20px;
        }

        #segmentedControl {
            background-color: #fff;
            border-bottom: 1px solid #ddd;
        }

        #refreshContainer {
            top: 48px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="0" class="mui-control-item mui-active">饭堂订餐
        </a>
        <a data-type="1" class="mui-control-item">定期活动
        </a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div class="ui-act-div"></div>
        </div>
    </div>
</div>

<script type="text/template" id="itemTpl">
    <div class="ui-act-item ui-border">
        <a href="javascript:;">
            <img class="ui-act-img" src="{{=it.path}}"/>
            <div class="ui-act-body">
                <h5 class="ui-ellipsis">{{=it.name}}</h5>
                {{? it.money <= 0}}
                <div style="height: 8px;"></div>
                {{?}}
                <div class="ui-text-info"><span class="fa fa-clock-o"></span>{{=it.time}}</div>
                {{? it.money > 0}}
                <div class="ui-text-info"><span class="fa fa-rmb"></span>{{=it.money}} 元</div>
                {{?}}
                <div class="ui-text-info"><span class="fa fa-phone"></span>{{=it.phone}}</div>
                <div class="ui-text-info ui-ellipsis"><span class="fa fa-map-marker"></span>{{=it.address}}</div>
            </div>
        </a>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/script/canteen/refresh.js"></script>
<script>
    mui.init({
        gestureConfig: {
            longtap: true, //默认为false
        }
    });
    $(function () {
        var groupId = '${param.id}';
        var refresh = window.refresh('#refreshContainer', {
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            dataEl: '.ui-act-div',
            emptyHtml: '<div class="mui-content-padded ui-empty">暂无活动哦</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d, 'activities.png');
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(homePath + '/wx/canteen/act/sign/show.html?id=' + d['id'])
            })

           /* //长按取消活动
            if (refresh.options.data['regular'] == 2) {
                $item.on('longtap', function () {
                    ui.confirm('是否取消活动？', function () {
                        wxgh.request.post('cancel.json', {id: d.id}, function () {
                            $item.remove()
                        })
                    })
                })
            }*/
            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var type = $(this).data('type')
            refresh.refresh({regular: type})
        })

       /* $('#addActSheet').on('tap', 'a[data-type]', function () {
            var type = $(this).data('type')
            mui.openWindow(homePath + '/wx/canteen/act/add.html?id=' + groupId + '&type=' + type)
        })*/
    })
</script>
</body>
</html>