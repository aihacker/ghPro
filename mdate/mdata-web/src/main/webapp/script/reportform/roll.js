//获取页面要取的数据
//var aa = document.getElementById("aa").innerHTML;
//var aa= [];//获取采购方式名称列表
var bb= [];//获取采购方式数量
$(".tablesorter tbody tr").each(function () {
    //aa.push($(this).find(".aa").html());
    bb.push($(this).find(".bb").html());
});
// 基于准备好的dom，初始化echarts实例
var line = echarts.init(document.getElementById('line'));
var roll = echarts.init(document.getElementById('roll'));
// 指定图表的配置项和数据
//柱状图设置
var option1 = {
    title: {
        text: '签订合同数量'
    },
    tooltip: {},//鼠标悬停显示的数量
    legend: {
        data: ['数量']
    },
    xAxis: {
        data: ["公开招标", "公开比选", "邀请询价", "单一公示", "单一非公示"]
    },
    yAxis: {},
    series: [
        {
            name: '数量',
            type: 'bar',
            data: bb,
            itemStyle: {
                normal: {
                    label: {
                        show: true,//设置柱状图顶端显示数值
                        position: "top",
                        textStyle: {
                            color: "black"
                        }
                    },
                }
            }
        },
      /*  {
            name: '数量',
            type: 'line',
            data: bb,
        },*/
    ],
};
//饼状图设置
var option2 = {
    title: {
        text: '各采购方式数量所占总比',
        subtext: '签订合同数量',
        x: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['公开招标', '公开比选', '邀请询价', '单一公示', '单一非公示']
    },
    series: [
        {
            name: '所占比例',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data:
                [
                {value: bb[0], name: '公开招标'},
                {value: bb[1], name: '公开比选'},
                {value: bb[2], name: '邀请询价'},
                {value: bb[3], name: '单一公示'},
                {value: bb[4], name: '单一非公示'}
            ],
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};

// 使用刚指定的配置项和数据显示图表。
line.setOption(option1);
roll.setOption(option2);

