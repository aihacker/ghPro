<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/27
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>健步积分</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .mui-content {
            background-color: #fff;
        }

        .ui-segmented-div {
            padding: 10px 40px;
        }

        .ui-segmented-div .mui-control-item {
            line-height: 30px;
        }

        .ui-echart-div {
            padding: 0 15px;
        }

        #echartDiv {
            height: 200px;
            border-radius: 10px;
        }

        .ui-sport-tip {
            margin-top: 10px;
            display: flex;
        }

        .ui-sport-item {
            text-align: center;
            flex: 1;
            margin: 1px;
        }

        .ui-sport-tip .ui-item-line {
            width: 1px;
            background-color: #ddd;
            height: 40px;
            position: relative;
            top: 8px;
        }

        .ui-sport-item span {
            font-size: 20px;
            font-family: "微软雅黑";
        }

        .ui-score-title {
            background-color: #E2E2E2;
            padding: 5px 15px;
        }

        .mui-table-view-cell.mui-active {
            background-color: #fff;
        }

        .mui-table-view-cell:after,
        .mui-table-view:before,
        .mui-table-view:after {
            width: 90%;
            left: 5%;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-segmented-div">
        <div id="segmentedControl" class="mui-segmented-control mui-segmented-control-primary">
            <a data-type="week" class="mui-control-item mui-active" href="#item1">个人积分</a>
            <a data-type="month" class="mui-control-item" href="#item2">团队积分</a>
        </div>
    </div>
    <div class="ui-body">
        <div class="ui-echart-div">
            <div id="echartDiv"></div>
        </div>
        <div class="ui-sport-tip">
            <div class="ui-sport-item">
                <span>${step}</span>
                <p>总步数（步）</p>
            </div>
            <div class="ui-item-line"></div>
            <div class="ui-sport-item">
                <span>${sum}</span>
                <p>总积分（分）</p>
            </div>
            <div class="ui-item-line"></div>
            <div class="ui-sport-item">
                <span id="scoreSpan">${distance}</span>
                <p id="scoreText">总里程（km）</p>
            </div>
        </div>
        <div class="ui-sport-score">
            <div class="ui-score-title">积分明细</div>
            <ul class="mui-table-view" id="scoreUl">
                <div class="mui-loading">
                    <div class="mui-spinner"></div>
                </div>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/echarts/echarts.min.js"></script>
<script>
    $(function () {
        var $ul = $('#scoreUl'),
            id = '${param.id}';

        var myChart = echarts.init(document.getElementById('echartDiv'))
        myChart.setOption({
            title: {
                subtext: '周步数',
                left: 'right',
                subtextStyle: {
                    color: '#fff'
                },
                top: -25
            },
            grid: {
                show: false,
                top: 20,
                bottom: 20,
                left: 20,
                right: 20
            },
            tooltip: {
                trigger: 'item',
                formatter: '{c}',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                },
                position: 'top'
            },
            backgroundColor: 'rgba(128, 128, 128, 0.8)',
            xAxis: {
                data: [],
                axisLabel: {
                    margin: 5,
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                z: 10
            },
            yAxis: {
                show: true,
                axisLabel: {
                    show: false,
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                splitLine: {
                    lineStyle: {
                        type: 'dotted'
                    }
                }
            },
            dataZoom: [{
                type: 'inside'
            }],
            series: [{ // For shadow
                type: 'bar',
                name: 'shadow',
                itemStyle: {
                    normal: {
                        color: 'rgba(0,0,0,0.05)'
                    }
                },
                barGap: '-100%',
                barCategoryGap: '40%',
                data: [],
                animation: false
            },
                {
                    type: 'bar',
                    name: 'step',
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [{
                                    offset: 0,
                                    color: '#83bff6'
                                },
                                    {
                                        offset: 0.5,
                                        color: '#188df0'
                                    },
                                    {
                                        offset: 1,
                                        color: '#188df0'
                                    }
                                ]
                            )
                        },
                        emphasis: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1, [{
                                    offset: 0,
                                    color: '#2378f7'
                                },
                                    {
                                        offset: 0.7,
                                        color: '#2378f7'
                                    },
                                    {
                                        offset: 1,
                                        color: '#83bff6'
                                    }
                                ]
                            )
                        }
                    },
                    data: []
                }
            ]
        });

        var $control = $('#segmentedControl');
        $control.data('type', 'week')

        $control.on('tap', '.mui-control-item', function (e) {
            var oldtype = $control.data('type')
            var type = $(this).data('type')
            if (oldtype != type) {
                list_sport(type);
            }
        })

        initChart();

        list_sport($control.data('type'));

        /**
         * 初始化echart
         * @param time
         */
        function initChart() {
            myChart.showLoading();
            wxgh.request.getURL('../api/history_list.json', function (data) {
                myChart.hideLoading();
                myChart.setOption({
                    xAxis: {
                        data: data.times,
                    },
                    series: [{ // For shadow
                        data: data.steps,
                        name: 'shadow'
                    },
                        {
                            name: 'step',
                            data: data.steps
                        }
                    ]
                });
            });
        }

        function list_sport(type) {
            var url = 'list.json'
            $ul.empty();
            $ul.append('<li class="mui-table-view-cell">' + wxgh.LOADING_HTML + '</li>')
            wxgh.request.get(url, {id: id, type: type}, function (d) {
                $ul.empty();
                if (d && d.length > 0) {
                    for (var i in d) {
                        var itm = d[i]
                        var $item = $('<li class="mui-table-view-cell">' +
                            type_str(itm.type) +
                            '<span class="mui-pull-right">' + (itm.score > 0 ? '+' + itm.score : itm.score) + '</span>' +
                            '</li>');
                        $ul.append($item)
                    }
                } else {
                    $ul.append('<li class="mui-table-view-cell"><div class="mui-text-center ui-text-info">加油哦，' + (type == 'week' ? '上周' : '上个月') + '未获取积分</div></li>');
                }
                $control.data('type', type);
            })
        }

        function type_str(type) {
            var str = '';
            if (type == 'week') {
                str = '个人达标周积分'
            } else if (type == 'month') {
                str = '团队达标月积分'
            }
            return str;
        }
    })
</script>
</body>
</html>
