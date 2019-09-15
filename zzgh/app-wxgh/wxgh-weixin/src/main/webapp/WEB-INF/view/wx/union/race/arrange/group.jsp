<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/21
  Time: 9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>分组抽签</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-group-div * {
            color: #5b5b5b;
        }

        .ui-group-div .ui-white-bg,
        table {
            background-color: #fff;
        }

        .ui-group-div .ui-white-bg {
            padding: 2px 5px;
        }

        .ui-group-div .ui-table-div table {
            width: auto;
        }

        .ui-group-div table td,
        .ui-group-div table th {
            border: solid #cfcfcf;
            border-width: 0px 0.5px 0.5px 0px;
            padding: 10px 10px;
            min-width: 120px;
            word-break: break-all;
            word-wrap: break-word;
        }

        .ui-group-div table {
            border: solid #cfcfcf;
            border-width: 0.5px 0px 0px 0.5px;
        }

        .ui-group-div tr td:first-child {
            min-width: 60px;
        }
    </style>
</head>

<body>

<div class="mui-content ui-group-div">
    <div id="tabDiv"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted ui-margin-top-10">
        <div class="mui-scroll">
            <div class="ui-table-div">
                <table id="mainTable">
                </table>
            </div>
        </div>
    </div>
    <div class="mui-content-padded">
        <button id="groupBtn" type="button" class="mui-btn mui-btn-primary">分组抽签</button>
    </div>
    <div class="mui-content-padded">
        <p>
            抽签规则介绍：<br>
            1.系统随机打乱参赛者的顺序，并采用蛇形分组法
        </p>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">

    var raceId = '${param.id}'

    mui.init()
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005,
        scrollX: true,
        scrollY: false,
        indicators: false
    });

    $(function () {
        $(window).on('resize', initSize())

        var $table = $('#mainTable')

        request('list')

        $('#groupBtn').on('tap', function () {
            request('group')
        })

        function request(action) {
            var url = homePath + '/wx/union/race/arrange/api/'+action+'.json'
            wxgh.request.post(url, {id: raceId, action: action}, function (res) {
                var d = res
                if (d && d.length > 0) {
                    createTable(d)
                } else {
                    $table.append('<tr><td class="mui-text-center">暂未分组哦</td></tr>')
                }
            })
        }

        function createTable(d) {
            $table.empty()
            for (var i in d) {
                var $tr = $('<tr></tr>')
                var des = d[i].details
                var g = d[i].group
                $tr.append('<td>' + g.name + '</td>')
                for (var j in  des) {
                    $tr.append('<td>' + des[j].name + '</td>')
                }
                $table.append($tr)
            }
            initSize()
        }

        function initSize() {
            var $tabDiv = $('#tabDiv')
            $tabDiv.css('height', $tabDiv.find('.ui-table-div').outerHeight())
        }
    })
</script>
</body>

</html>
