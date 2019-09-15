<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/28
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>评议结果</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-fixed-bottom{
            display: flex;justify-content:space-around;
        }
        .mui-content{
            position: relative;
        }
        .mui-scroll-wrapper{
            overflow: scroll;
        }
        .mui-card{
            display: flex;
            justify-content: space-between;
            padding-top:5px;
            padding-bottom:5px;
        }
        .mui-card span{
            margin-left: 5px;
        }
        #main {
            height: 400px;
            width: 100%;
            margin: 0 auto;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title"><span id = "zhibu">本支部共有${num}人,</span>已有${joinNum}人参加评议</h1>
</header>
<div class="mui-content">
    <div class="tbody center-block clearfix" id="main">

    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script src="${home}/libs/echarts/echarts.min.js"></script>
<script src="${home}/comm/admin/js/pub.js"></script>
<script>
    $(function () {
        var id = '${param.id}';
        var voteid = '${param.voteid}'

        var myChart = echarts.init(document.getElementById('main'));

        function Jecharts(text, title, data) {
            var options = {
                title: {
                    text: text,
                    subtext: '数据来自统计数据',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'horizontal',
                    bottom: '-1%',
                    data: title
                },
                series: [
                    {
                        name: '所占比例',
                        type: 'pie',
                        radius: '50%',
                        center: ['50%', '53%'],
                        label: {
                            normal: {
                                formatter: '{b}:{c}'+'票'+'\n{a}\n{d}%',
                                backgroundColor: '#eee',
                                borderColor: '#aaa',
                                borderWidth: 1,
                                borderRadius: 4,
                                rich: {
                                    a: {
                                        color: '#999',
                                        lineHeight: 22,
                                        align: 'center'
                                    },
                                    hr: {
                                        borderColor: '#aaa',
                                        width: '100%',
                                        borderWidth: 0.5,
                                        height: 0
                                    },
                                    b: {
                                        fontSize: 16,
                                        lineHeight: 33
                                    },
                                    per: {
                                        color: '#eee',
                                        backgroundColor: '#334455',
                                        padding: [2, 4],
                                        borderRadius: 2
                                    }
                                }
                            }
                        },
                        data: data,
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            }
            return options
        }

        Jecharts("比例图")


        wxgh.request.get("api/get_result_chart.json",{id:id,voteid:voteid},function(json){
            var data = []
            var title = []
            $(json).each(function (index, item) {
                data.push(item)
                title.push(item.name)
            })

            var text = '评价比例'

            myChart.setOption(Jecharts(text, title, data))
        })
    })


</script>
</body>
</html>

