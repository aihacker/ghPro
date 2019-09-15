<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>妈妈小屋</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${home}/libs/font-awesome-4.7.0/css/font-awesome.min.css">

    <style>
        html, body {
            height: 100%;
        }

        .mui-content {
            height: 100%;
        }

        .mui-btn-block {
            font-size: 16px;
            padding: 10px 0;
        }
    </style>
</head>

<body>
<div class="mui-content">
    <div class="mui-scroll-wrapper" style="margin-bottom: 60px;">
        <div class="mui-scroll">

            <div class="ui-img-div" style="height: 200px;">
                <img src="${home}${mama.path}">
            </div>

            <p style="background-color: white;color: #333;text-align: center;height: 50px;font-size: 20px;line-height: 50px;padding-left: 10px;margin-bottom: 0px;">
                ${mama.name}
            </p>

            <div class="mui-row"
                 style="background-color: white;font-size: 17px;padding: 10px;border-bottom: 1px solid gainsboro;">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${mama.content}
            </div>

        </div>
    </div>
    <a href="#middlePopover"
       style="margin-bottom: 10px;position: fixed;bottom: 0px;width: 300px;left: 50%;margin-left: -150px;"
       class="mui-btn mui-btn-blue mui-btn-block">我要预约
    </a>
</div>
<div id="middlePopover" class="mui-popover" style="height: 200px;margin: auto;">
    <div class="mui-popover-arrow">

    </div>
    <div name="div-search" class="mui-input-row mui-search"
         style="margin: auto;margin-top: 6px;width: 90%;position: absolute;top: 5px;z-index: 999;left: 5%;text-align: center;">
        时间：${param.date}
    </div>
    <div name="div-search" class="mui-input-row mui-search"
         style="margin: auto;margin-top: 6px;width: 90%;position: absolute;top: 40px;z-index: 999;left: 5%;">
        <input readonly="readonly" id="startTime" type="text" class="" placeholder="请预约开始时间">
    </div>
    <div name="div-search" class="mui-input-row mui-search"
         style="margin: auto;margin-top: 6px;width: 90%;position: absolute;top: 95px;z-index: 999;left: 5%;">
        <input readonly="readonly" id="endTime" type="text" class="" placeholder="请预约结束时间">
    </div>

    <div id="div-yuyue" class="mui-btn mui-btn-block"
         style="position: absolute;bottom: 0px;z-index: 999;background-color: #007aff;color: white;border: none;">确定预约
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/comm/mobile/js/refresh.js"></script>

<script>
    mui.init({
        swipeBack: true
    });

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    initPopver();

    function initPopver() {
        var popver = wxgh.getElement('middlePopover');
        popver.style.left = ((window.screen.availWidth - 280) / 2) + 'px';
        popver.style.top = ((window.screen.availHeight - 380) / 2) + 'px';
    }


    window.onload = function () {

        document.querySelector("#startTime").addEventListener('tap', function () {
            var options = {
                "type": "time",
                "customData": {
                    "i": [
                        {value: "00", text: "00"}

                    ]
                }
            };
            var picker = new mui.DtPicker(options);
            picker.show(function (rs) {
                document.querySelector("#startTime").value = rs.text;
                picker.dispose();
            });
        }, false);

        document.querySelector("#endTime").addEventListener('tap', function () {

            var startTime = document.getElementById("startTime").value;
            if (!startTime) {
                mui.alert("请先选择开始时间");
                return;
            }

            var options = {
                "type": "time",
                "customData": {
                    "i": [
                        {value: "00", text: "00"}

                    ]
                }
            };
            var picker = new mui.DtPicker(options);
            picker.show(function (rs) {

                // 获取某个时间格式的时间戳

                var startTimestamp = Date.parse(new Date("2017-05-06 " + startTime + ":00:00"));
                startTimestamp = startTimestamp / 1000;
                //2014-07-10 10:21:12的时间戳为：1404958872
                console.log(startTimestamp + "的时间戳为：" + startTimestamp);

                var endTimestamp = Date.parse(new Date("2017-05-06 " + rs.text + ":00:00"));
                endTimestamp = endTimestamp / 1000;
                //2014-07-10 10:21:12的时间戳为：1404958872
                console.log(endTimestamp + "的时间戳为：" + endTimestamp);

                if (startTimestamp >= endTimestamp) {
                    mui.alert("结束时间要大于开始时间哦~");
                    return;
                }


                document.querySelector("#endTime").value = rs.text;
                picker.dispose();
            });
        }, false);

        document.getElementById("div-yuyue").addEventListener("tap", function () {
            var startTime = document.getElementById("startTime").value;
            var endTime = document.getElementById("endTime").value;
            if (!startTime || !endTime){
                mui.alert("请选择时间");
                return
            }
            startTime = startTime.split(":")[0];
            endTime = endTime.split(":")[0];
            func.join(startTime, endTime);
        });

        var func = {
            join: function (startTime, endTime) {
                $.ajax({
                    url: "${home}/wx/common/female/mama/join.json",
                    data: {
                        mid: ${param.id},
                        startTime: startTime,
                        endTime: endTime,
                        dateStr: encodeURIComponent("${param.date}"),
                        date: "${param.date}"
                    },
                    success: function (rs) {
                        if (rs.ok) {
                            mui.alert("预约成功", function () {
                                //wx.closeWindow();
                                window.location.reload();
                            });
                        }else {
                            mui.alert(rs.msg, function () {
                                return;
                            });
                        }
                    }
                })
            }
        }
    }

</script>
</body>

</html>
