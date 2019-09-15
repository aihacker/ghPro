<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/8
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>7天步数</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .mui-content {
            background-color: #fff;
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
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-body ui-margin-top-15">
        <div class="ui-echart-div">
            <div id="echartDiv"></div>
        </div>
        <div class="ui-sport-tip">
            <div class="ui-sport-item">
                <span>${h.avgStep}</span>
                <p>日均步数（步）</p>
            </div>
            <div class="ui-item-line"></div>
            <div class="ui-sport-item">
                <span id="scoreSpan">${h.distance}</span>
                <p id="scoreText">周里程（km）</p>
            </div>
            <div class="ui-item-line"></div>
            <div class="ui-sport-item">
                <span>${h.todayStep}</span>
                <p>今日步数（步）</p>
            </div>
        </div>
        <%--<div class="ui-sport-tip">--%>
            <%--<div class="ui-sport-item">--%>
                <%--<span>${h.totalStep}</span>--%>
                <%--<p>总步数（步）</p>--%>
            <%--</div>--%>
            <%--<div class="ui-item-line"></div>--%>
            <%--<div class="ui-sport-item">--%>
                <%--<span id="scoreSpan">${h.distance}</span>--%>
                <%--<p id="scoreText">总里程（km）</p>--%>
            <%--</div>--%>
            <%--<div class="ui-item-line"></div>--%>
            <%--<div class="ui-sport-item">--%>
                <%--<span>${h.rank}</span>--%>
                <%--<p>总排名</p>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div class="ui-sport-tip">
            <div class="ui-sport-item">
                <span>${totalstep}</span>
                <p>总步数（步）</p>
            </div>
            <div class="ui-item-line"></div>
            <div class="ui-sport-item">
                <span id="scoreSpan">${rank}</span>
                <p id="scoreText">总排名</p>
            </div>
            <div class="ui-item-line"></div>
            <div class="ui-sport-item">
                <span>${totaldistance}</span>
                <p>总里程（km）</p>
            </div>
        </div>
    </div>
    <div class="mui-content-padded" style="margin-top: 20px;">
        <h4>温馨提示：</h4>
        <p style="padding-left: 10px;">
            1. 每日7点~11点整点同步微信运动步数（存在延迟）.<br>
            2. 当日最后步数可能需要隔日才能同步.<br>
            3. 每日步数推送不是当日最终步数.<br>
            4. 每日必须保证自己出现在微信运动排行榜.
        </p>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/echarts/echarts.min.js"></script>
<script>
    $(function () {
        var userid = '${param.userid}';
        var myChart = echarts.init(document.getElementById('echartDiv'));
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
        initChart();

        function initChart() {
            myChart.showLoading()
            wxgh.request.getURL('api/history_list.json?userid=' + userid, function (data) {
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
    })
</script>
</body>
</html>