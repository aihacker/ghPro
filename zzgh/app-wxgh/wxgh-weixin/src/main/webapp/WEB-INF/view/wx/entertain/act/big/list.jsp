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
    <title>大型活动</title>
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
    </style>
</head>
<body>
<div id="sliderSegmentedControl"
     class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted white">
    <div class="mui-control-item mui-active" href="#item1mobile" data-type="3">
        工会小组
    </div>
    <%--<div class="mui-control-item" href="#item2mobile" data-type="4">
        区分公司
    </div>--%>
    <div class="mui-control-item" href="#item3mobile" data-type="5">
        公司
    </div>
</div>
<div class="mui-content">
    <div id="refreshContainer" class="mui-scroll-wrapper" style="top:45px;">
        <div class="mui-scroll">
            <div class="ui-act-div"></div>
        </div>
    </div>
</div>

<div class="ui-fixed-btn right">
    <%--<a href="add.html"><span class="fa fa-plus"--%>
                             <%--style="line-height: 50px;font-size: 28px;"></span></a>--%>
    <a href="#sheet1">
        <span class="fa fa-plus" style="line-height:50px;font-size:28px"></span>
    </a>
</div>

<div id="sheet1" class="mui-popover mui-popover-bottom mui-popover-action ">
    <!-- 可选择菜单 -->
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="add.html">常规活动</a>
        </li>
        <li class="mui-table-view-cell">
            <a href="addlink.html">外链活动</a>
        </li>
    </ul>
    <!-- 取消菜单 -->
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="#sheet1"><b>取消</b></a>
        </li>
    </ul>
</div>

<script type="text/template" id="itemTpl">
    <div class="ui-act-item ui-border">
        <a href="javascript:;">
            <div class="ui-img-div ui-act-img">
                <img src="{{=it.path}}"/>
            </div>
            <div class="ui-act-body">
                <h5 class="ui-ellipsis">{{=it.name}}</h5>
                {{? it.money <= 0}}
                <div style="height: 8px;"></div>
                {{?}}
                {{? it.link}}
                <div class="ui-text-info"><span class="fa fa-star"></span>此活动为外链活动</div>
                {{?}}
                {{? it.time}}
                <div class="ui-text-info"><span class="fa fa-clock-o"></span>{{=it.time}}</div>
                <div class="ui-text-info"><span class="fa fa-phone"></span>{{=it.phone}}</div>
                <div class="ui-text-info ui-ellipsis"><span class="fa fa-map-marker"></span>{{=it.address}}</div>
                {{?}}
            </div>
        </a>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var actType = "";

        var refresh = window.refresh('#refreshContainer', {
            url: 'list_api.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            dataEl: '.ui-act-div',
            data:{actType:actType},
            emptyHtml: '<div class="mui-content-padded ui-empty">暂无活动哦</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

       mui("#sliderSegmentedControl").on("tap",".mui-control-item",function(){
           var self = $(this)
           actType = self.data("type")
           refresh.refresh({
               actType:actType
           })
       })


        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d, 'activities.png');
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                var link = d.link;
                if(link){
                    mui.openWindow(link)
                }else{
                    mui.openWindow(homePath + '/wx/entertain/act/big/show.html?id=' + d['id'])
                }
            })
            return $item[0]
        }

    })
</script>
</body>
</html>