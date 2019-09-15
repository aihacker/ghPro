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
    <title>女子课堂</title>

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
                <img src="${home}${lesson.path}">
            </div>

            <p style="color: #333;text-align: center;height: 40px;font-size: 18px;line-height: 40px;padding-left: 10px;margin-bottom: 0px;">
                ${lesson.name}
            </p>

            <div class="mui-row" style="font-size: 17px;padding: 10px;border-bottom: 1px solid gainsboro;">
                <div class="mui-col-xs-3 mui-col-sm-3">课堂内容：</div>
                <div class="mui-col-xs-9 mui-col-sm-9">
                    ${lesson.content}
                </div>
            </div>

            <c:if test="${filesMap != null && fn:length(filesMap) > 0}">
                <div style="padding: 10px;">
                    <h5>附件：课程预习资料</h5>
                    <c:forEach items="${filesMap}" var="item">
                        <p class="mui-ellipsis" style="position: relative;">
                            <i class="fa fa-file-text" style="color: #03A9F4;font-size: 17px;"></i><span
                                style="margin-left: 5px;">${item.name}</span><a class="mui-pull-right"
                                                                                href="${item.url}" target="_blank"><i
                                class="fa fa-download" style="font-size: 17px;position: absolute;right: 0px;top: 2px;"></i></a>
                        </p>
                    </c:forEach>

                </div>
            </c:if>

            <div style="padding: 10px;">
                <h5>课程开设时间</h5>
                    <p class="mui-ellipsis" style="position: relative;">
                       ${lesson.startTime} 至 ${lesson.endTime}
                    </p>

            </div>

            <div style="position: relative;height: 50px;" class="mui-row" onclick="window.location.href='${home}/wx/common/female/lesson_join/index.html?fid=${param.id}'">
                <div class="mui-col-xs-6 mui-col-sm-6" style="height: 100%;text-indent: 10px;line-height: 50px;">
                    已报名（${joinCount}）
                </div>
                <div class="mui-col-xs-6 mui-col-sm-6" style="height: 100%;padding: 5px;">
                    <c:if test="${joinList != null && fn:length(joinList) >0}">
                        <c:forEach items="${joinList}" var="item">
                            <img src="${item.avatar}" style="height: 100%;margin-right: 10px;" class="mui-pull-right">
                        </c:forEach>
                    </c:if>
                   <p class="mui-pull-left" style="margin-top: 10px;position: absolute;top: 6px;right: -6px;">
                       <c:if test="${joinCount != null && joinCount >3}">
                           <span class="mui-icon mui-icon-forward"></span>
                       </c:if>
                   </p>
                </div>
            </div>

        </div>
    </div>
    <c:choose>
        <c:when test="${open eq 3}">
            <button style="margin-bottom: 10px;position: fixed;bottom: 0px;width: 300px;left: 50%;margin-left: -150px;"
                    class="mui-btn mui-btn-gray mui-btn-block btn-join" disabled="disabled">课程已结束
            </button>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${join}">
                    <button style="margin-bottom: 10px;position: fixed;bottom: 0px;width: 300px;left: 50%;margin-left: -150px;"
                            class="mui-btn mui-btn-green mui-btn-block btn-join" disabled="disabled">已报名
                    </button>
                </c:when>
                <c:otherwise>
                    <button style="margin-bottom: 10px;position: fixed;bottom: 0px;width: 300px;left: 50%;margin-left: -150px;"
                            class="mui-btn mui-btn-blue mui-btn-block btn-join">我要报名
                    </button>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>

<script>
    mui.init({
        swipeBack: true
    });
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });


    window.onload = function () {

        document.querySelector(".btn-join").addEventListener("tap", function () {
            func.join();
        })

        var func = {
            join: function () {
                $.ajax({
                    url: "${home}/wx/common/female/lesson/join.json",
                    data: {
                        fid: ${param.id}
                    },
                    success: function (rs) {
                        if (rs.ok){
                            mui.alert("报名成功", function () {
                                //wx.closeWindow();
                                window.location.reload();
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
