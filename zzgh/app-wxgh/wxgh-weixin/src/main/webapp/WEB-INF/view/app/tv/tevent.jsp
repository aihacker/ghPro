<%--
  Created by IntelliJ IDEA.
  User: hasee
  Date: 2018/5/11
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>今日比赛</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-status="1" class="mui-control-item mui-active">1号场
            <small></small>
        </a>
        <a data-status="2" class="mui-control-item">2号场
            <small></small>
        </a>
        <a data-status="3" class="mui-control-item">3号场
            <small></small>
        </a>
        <a data-status="4" class="mui-control-item">4号场
            <small></small>
        </a>
        <a data-status="5" class="mui-control-item">5号场
            <small></small>
        </a>
        <a data-status="6" class="mui-control-item">6号场
            <small></small>
        </a>
    </div>

    <div id="refreshContainer">
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<style type="text/css">
    .span_team{
        width: 100%;
        text-align: center;
    }
    .fs12{
        font-size: 12px;
    }
</style>
<script>
    $(function () {
        var $segment = $('#segmentedControl')
        var timer;
        $segment.data('status', 1)
        $segment.on('tap', 'a.mui-control-item', function () {
            var status = $(this).attr('data-status')
            getjson(status);
        })
        getjson(1);

        function getjson(status) {
            mui.getJSON("today_event.json", {status:status}, function (res) {
                if (res.ok) {
                    var result = res.data;
                    if(result.length>0){
                        var $rec = $('#refreshContainer');
                        $rec.html('')
                        for(var i=0;i<result.length;i++){
                            var ehtml = '<ul class="mui-table-view" style="padding: 10px 0">' +
                                '<div class="ui-img-div">' +
                                '<span class="span_team fs12">' + result[i].eventname + '(' + result[i].stagename + ')  ' + result[i].project_name + '</span>' +
                                '</div>' +
                                '<div class="ui-img-div">' +
                                '<span class="span_team">' + result[i].team_a +
                                '</span>' +
                                '<span class="span_team" style="font-size: 24px">' +
                                '<div><b>VS</b></div>';
                            switch (result[i].game_over){
                                case 0:ehtml += '<span style="background-color: dodgerblue;color: #FFFFFF" class="fs12">未开始</span>';break;
                                case 1:ehtml += '<span style="background-color: gray;color: #FFFFFF" class="fs12">已结束</span>';break;
                                case 2:ehtml += '<span style="background-color: green;color: #FFFFFF" class="fs12">直播中</span>';
                                    ehtml+='<div><a href="query.html?code=' + result[i].code + '" class="fs12">比分直播</a></div>';
                                    break;
                            }

                            ehtml += '</span>' +
                                '<span class="span_team">' + result[i].team_b +
                                '</span>' +
                                '</div>' +
                                '</ul>';
                            $rec.append(ehtml);
                        }
                    } else {
                        var $rec = $('#refreshContainer');
                        $rec.html('<ul class="mui-table-view">' +
                            '<li class="mui-table-view-cell">' +
                            '<div class="mui-text-center ui-text-info">' + status + '号场没有比赛安排</div>' +
                            '</li>' +
                            '</ul>')
                    }
                } else {
                    alert(res.msg)
                }
            })
        }
    })
</script>
</body>
</html>
