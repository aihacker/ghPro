<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/14
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>我的场地预约</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #uiControleHead {
            padding: 0px 10px;
            background-color: #fff;
        }

        #controlItem {
            margin-top: 10px;
        }

        .mui-media-body {
            color: #000;
        }

        .ul-list{
            background-color: #fff;
        }
    </style>
</head>

<body>


<div id="segmentedControl"
     class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
    <a data-status="1" class="mui-control-item mui-active" id="0">未使用（${nouserCount}）</a>
    <a data-status="3" class="mui-control-item" id="1">已失效（${nothingCount}）</a>
</div>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view" id="ul-list" >
        </ul>
    </div>
</div>

<script type="text/template" id="itemTpl">
    {{? it.status == 1}}
    <li class="mui-table-view-cell mui-media">
        <div class="mui-slider-right mui-disabled">
            <a data-id="{{=it.id}}" data-type="1" data-week="{{=it.week}}" class="mui-btn mui-btn-red">取消预约</a>
        </div>
        <div class="mui-slider-handle">
            <a href="javascript:;">
                <div class="mui-media-body">
                    {{=it.placeName}}
                    <p class="mui-ellipsis">
                        <small>{{=it.cateName}}：{{=it.siteName}}</small>
                        <small class="mui-pull-right">{{=it.dateId}} - {{=it.weekName}}{{=it.startTime}}~{{=it.endTime}}</small>
                    </p>
                </div>
            </a>
        </div>
    </li>
    {{?}}
    {{? it.status == 3}}
    <li class="mui-table-view-cell mui-media">
        <div class="mui-slider-right mui-disabled">
            <a data-id="{{=it.id}}" data-type="0" data-week="{{=it.week}}" class="mui-btn mui-btn-red">删除</a>
        </div>
        <div class="mui-slider-handle">
            <a href="javascript:;">
                <div class="mui-media-body">
                    {{=it.placeName}}
                    <p class="mui-ellipsis">
                        <small>{{=it.cateName}}：{{=it.siteName}}</small>
                        <small class="mui-pull-right">{{=it.dateId}} - {{=it.weekName}}{{=it.startTime}}~{{=it.endTime}}</small>
                    </p>
                </div>
            </a>
        </div>
    </li>
    {{?}}
</script>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

    $(function () {

        init_size()

        var v_status = 1;

        var refresh = window.refresh('#pullrefresh', {
            url: homePath + '/wx/entertain/nanhai/place/api/my_yuyue.json',
            data: {status: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无记录哦',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d.status = v_status
            var $item = refresh.getItem(d)
//            $item.on('tap', function () {
//                mui.openWindow(homePath + '/wx/party/di/show.html?id=' + d.id)
//            })
            $item.on('tap', '.mui-btn-red[data-id]', function () {
                var $li = this.parentNode.parentNode;
                var $self = $(this)
                mui.confirm('是否放弃已预约的场地', '系统提示', ['否', '是'], function (e) {
                    if (e.index == 1) {
                        del($self.attr('data-week'),$self.attr('data-id'),$self.attr('data-type'),$li);
                    } else {
                        mui.swipeoutClose($li);
                    }
                })
            })
            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var status = $(this).data('status')
            v_status = status;
            refresh.refresh({status: status})
        })

        $('#controlItem').on('tap', '.mui-btn-red[data-id]', function () {
            var $li = this.parentNode.parentNode;
            var $self = $(this)
            mui.confirm('是否放弃已预约的场地', '系统提示', ['否', '是'], function (e) {
                if (e.index == 1) {
                    del($self.attr('data-id'),$self.attr('data-type'),$li);
                } else {
                    mui.swipeoutClose($li);
                }
            })
        })

        $(window).on('resize', init_size)

        function init_size() {
            var cntHeight = $(window).outerHeight() - $('.ui-head').outerHeight() - $('#uiControleHead').outerHeight();
            $('#controlItem .mui-control-content').css('height', cntHeight);
        }

        function del(week,id,type,$li) {
            var url = homePath + '/wx/entertain/nanhai/place/delete.json';
            var data={
                id:id,
                type:type
            }
            mui.getJSON(url,data,function (res) {
                if (res.ok) {
                    var $parent = $($li).parent()
                    $($li).remove();
                    if ($parent.find('li').length < 1) {
                        var msg = ''
                        if ($parent.hasClass('ui-use')) {
                            msg = '暂无可用场地哦'
                            var useCount = $('#useCount').text().replace('未使用（', '')
                                .replace('）', '')
                            $('#useCount').text('未使用（' + (Number(useCount) - 1) + '）')
                        } else {
                            msg = '您没有失效的场地哦'
                            var nouseCount = $('#nouseCout').text().replace('已失效（', '')
                                .replace('）', '')
                            $('#nouseCount').text('已失效（' + (Number(nouseCount - 1)) + '）')
                        }
                        $parent.append('<li class="mui-table-view-cell"><div class="mui-text-center">' + msg + '</div></li>')
                    }
                    mui.swipeoutClose($li);
                }
            })
        }
    })
</script>
</body>

</html>