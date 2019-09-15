<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/9
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>考试信息</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <div class="tite" style="display: none">${id}</div>
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-status="0" class="mui-control-item mui-active" id="0">报名人数</a>
        <a data-status="1" class="mui-control-item" id="1">完成考试</a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view user-list">
            </ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src={{=it.avatar}}>
            {{? it.score==null}}
            <div class="mui-media-body">
                {{=it.name}}
                <p class="mui-ellipsis">{{=it.addTime}}</p>
            </div>
            {{??}}
            <div class="mui-media-body" style="line-height: 45px;">
                {{=it.name}}
                <span class="mui-pull-right">分数: {{=it.score}}</span>
            </div>
            {{?}}

        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var status;
        var id = $('.tite').text()
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/get_people.json',
            data: {id: id, status: 0},
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
            d['avatar'] = wxgh.get_avatar(d.avatar);
            var $item = refresh.getItem(d)
            return $item[0];
        }

        getNumber(id, 0)
        getNumber(id, 1)

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var status = $(this).data('status')
            refresh.refresh({status: status})
        })
    });

    //获取总人数
    function getNumber(id, status) {
        if (status == undefined)
            status = $('.mui-control-item').data('status')
        mui.post('api/get_number.json', {id: id, status: status}, function (json) {
            var number = json.data;
            var a = $('#' + status).text();
            a = a.substr(0, 4);
            $('#' + status).text(a + "(" + number + "人)")
        });
    }


</script>
</body>
</html>
