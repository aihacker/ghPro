<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/17
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<head>
    <title>女子课堂报名列表</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    
    <style>
        .mui-table-view-cell:after {
            background-color: initial;
        }

        .mui-card {
            margin: 0px;
        }

        p.p-status {
            /* background: black; */
            /* opacity: 0.5; */
            height: 35px;
            /* margin-top: -22px; */
            text-indent: 10px;
            font-size: 15px;
            color: black;
            line-height: 35px;
        }

        .mui-card {
            padding: 0px 15px;
        }

        .mui-card-footer {
            padding-left: 0px;
            padding-right: 0px;
        }
    </style>
</head>
<body>


<!--下拉刷新容器-->
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <!--数据列表-->
        <ul class="mui-table-view" id="ul-list" style="background-color: #efeff4;">

        </ul>
    </div>
</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
        s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
        return Y + M + D + h + m + s;
    }

    mui.init({
        pullRefresh: {
            container: '#pullrefresh',
            down: {
                callback: pulldownRefresh
            },
            up: {
                contentrefresh: '正在加载...',
                callback: pullupRefresh
            }
        }
    });


    /**
     * 下拉刷新具体业务实现
     */
    function pulldownRefresh() {
        //				alert("上面数据");
        setTimeout(function () {
            document.getElementById("ul-list").innerHTML = "";
            list.isRefresh = true;
            list.get();
            mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
        }, 1500);

    }
    /**
     * 上拉加载具体业务实现
     */
    function pullupRefresh() {
        //				alert("下面数据");
        setTimeout(function () {
            mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //参数为true代表没有更多数据了。
            list.isRefresh = false;
            list.get();
        }, 1500);
    }
    if (mui.os.plus) {
        mui.plusReady(function () {
            setTimeout(function () {
                mui('#pullrefresh').pullRefresh().pullupLoading();
            }, 1000);

        });
    } else {
        mui.ready(function () {
            mui('#pullrefresh').pullRefresh().pullupLoading();
        });
    }

    var list = {
        isFirst: true,
        indexID: 0,
        isRefresh: true,
        get: function () {
            $.ajax({
                dataType: "jsonp",
                url: "${home}/wx/common/female/lesson_join/get_list_data.json",
                data: {
                    indexID: list.indexID,
                    isFirst: list.isFirst,
                    isRefresh: list.isRefresh,
                    fid: "${param.fid}"
                },
                success: function (rs) {
                    callback(rs);
                }
            });

            function callback(rs) {
                var data = rs.data;
                //console.log(JSON.stringify(data));
                if (data.length != "undefined") {
                    var ps = '';
                    for (var i = 0; i < data.length; i++) {

                        if (i == data.length - 1) {
                            list.indexID = data[i].id
                            console.log(data[i].id);
                        }


                        ps += '<li class="mui-table-view-cell"><div class="mui-row"style="height: 50px;line-height: 50px;"><div class="mui-col-xs-4 mui-col-sm-4"style="height: 100%;"><img src="'+data[i].avatar+'" height="100%;"></div><div class="mui-col-xs-4 mui-col-sm-4"style="height: 100%;">'+data[i].username+'</div><div class="mui-col-xs-4 mui-col-sm-4"style="height: 100%;">'+data[i].deptname+'</div></div></li>';
                    }
                    document.getElementById("ul-list").innerHTML += ps;
                }

                if (list.isFirst) {
                    list.isFirst = false
                }
            }
        }
    }

    $(function () {
        $("body").on("tap", ".li-item", function () {
            var id = $(this).attr("data-id");
            <%--window.location.href = "${home}/di/notice_show.wx?id=" + id;--%>
        })
    });

</script>
</body>
</html>
