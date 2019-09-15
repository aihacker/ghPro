<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/24
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>本周预约</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        *, body {
            color: #5b5b5b;
        }

        .ui-white-bg,
        table {
            background-color: #fff;
        }

        .ui-white-bg {
            padding: 2px 5px;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 999;
            border-bottom: 1px solid #e2e2e2;
        }

        .ui-table-div table {
            width: auto;
        }

        table td {
            border: 1px solid #cfcfcf;
            border-width: 0px 0.5px 0.5px 0px;
            padding: 10px 10px;
            min-width: 120px;
            word-break: break-all;
            word-wrap: break-word;
        }

        table {
            border: solid #cfcfcf;
            border-width: 0.5px 0px 0px 0.5px;
        }

        td[rowspan] {
            min-width: 60px;
        }

        #tabDiv {
            top: 48px;
        }

        #segmentedDiv {
        }
    </style>
</head>

<body>

<div class="mui-content">
    <div class="ui-white-bg">
        <div id="segmentedDiv" class="mui-segmented-control mui-segmented-control-inverted">
            <c:forEach items="${cates}" var="c" varStatus="i">
                <a data-id="${c.id}" class="mui-control-item ${i.first?'mui-active':''}">
                        ${c.name}
                </a>
            </c:forEach>
        </div>
    </div>
    <div id="tabDiv"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <div class="ui-table-div">
                <table id="listTable">

                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">

    var homePath = '${home}';
    var placeId = '${placeId}';
    var cateId = '${cateId}';

    mui.init()
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005,
        scrollX: true,
        scrollY: false,
        indicators: false
    });

    $(function () {
        initSize()
        $(window).on('resize', initSize())

        var $table = $('#listTable')

        function initSize() {
            var $tabDiv = $('#tabDiv')
            $tabDiv.css('height', $tabDiv.find('.ui-table-div').outerHeight())
        }

        getList()

        $('#segmentedDiv').on('tap', 'a.mui-control-item', function () {
            var id = $(this).attr('data-id')
            cateId = id
            getList()
        })

        function getList() {
            var url = homePath + '/wx/entertain/place/pre_list.json'
            var data={
                id:placeId,
                cateId:cateId
            }
            //var info = {action: 'list', id: placeId, cateId: cateId}
            if (!this.loading) {
                this.loading =ui.loading('加载中...')
            }
            this.loading.show()
            var self = this
            $.getJSON(url, data, function (res) {
                if (res.ok) {
                    console.log(JSON.stringify(res));
                    createList(res.data)
                    self.loading.hide()
                }
            })
        }

        function createList(d) {
            var weeks = d.weeks;
            var sites = d.sites;

            $table.empty()

            //场地
            var $firstTr = $('<tr></tr>')
            $firstTr.append('<td colspan="2">时间</td>')
            for (var i in sites) {
                $firstTr.append('<td border="1">' + sites[i].name + '</td>')
            }
            $table.append($firstTr)

            //时间列表
            for (var i in weeks) {
                var times = weeks[i]
                var tims = times.times;
                for (var j = 0; j < tims.length; j++) {
                    var $tr = $('<tr></tr>')
                    if (j == 0) {
                        $tr.append('<td rowspan="' + tims.length + '">' + times.week + '</td>')
                    }
                    $tr.append('<td border="1">' + tims[j].time + '</td>')
                    var cells = tims[j].cells
                    for (var a = 0; a < cells.length; a++) {
                        var cas = cells[a]
                        if (cas) {
                            var str
                            var username = cas.username
                            if (username) {
                                if(!cas.name)
                                    cas.name = '';
                                if (username == '0') {
                                    str = cas.name+ '<br>' + cas.payType
                                } else {
                                    str = cas.name + '<br>' + username + '<br>' + cas.mobile+ '<br>' + cas.payType
                                }
                            } else {
                                str = cas.status
                            }
                            $tr.append('<td border="1">' + str + '</td>')
                        }
                    }
                    $table.append($tr)
                }
            }
            initSize()
        }
    })
</script>
</body>

</html>
