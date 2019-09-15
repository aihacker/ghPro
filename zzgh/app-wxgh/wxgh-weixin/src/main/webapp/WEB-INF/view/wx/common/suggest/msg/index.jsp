<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/8
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <jsp:include page="/comm/mobile/styles.jsp"/>

    <title></title>
    <style>
        #showUserPicker {
            height: 100%;
            background: transparent;
            border: none;
        }
        .select-div {
            height: 100%;
        }
    </style>

</head>


<body>
<p class="mui-button-row">
    <button class="mui-btn mui-btn-primary btn-type on" id="status-0" disabled="disabled" value="0">审核中</button>
    <button class="mui-btn btn-type" id="status-2" value="2">不通过</button>
</p>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper ui-margin-top-44">
    <div class="mui-scroll">
        <!--数据列表-->
        <ul class="mui-table-view">

        </ul>
    </div>
</div>



</body>

</html>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script>
    var homePath = "${home}";
</script>
<script>
    //格式化时间
    //时间戳 转 yyyy - MM - dd hh: mm: ss

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

    $(document).ready(function() {
        localStorage.removeItem("userSuggestOldestId");
        //localStorage.removeItem("userSuggestStatus");
        localStorage.setItem("userSuggestStatus", 0);
    });
</script>

<script>
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
        //alert("上面数据");
        $("#pullrefresh ul").html("");
        var status = localStorage.getItem("userSuggestStatus");
        setTimeout(function() {
            var $table = $('.mui-table-view');
            $.ajax({
                url: "${home}/wx/common/suggest/msg/apply_list_refresh.json",
                dataType: 'json',
                data: {
                    status: status
                },
                success: function(result) {
                    callback(result);
                }
            });

            function callback(result) {
                //alert(JSON.stringify(result));
                var data = result.data;
                for(var i = 0; i < data.length; i++) {
                    var ps = '<li class="mui-table-view-cell mui-media"><div class="mui-slider-right mui-disabled"><a class="mui-btn mui-btn-red del" id="'+data[i].id+'">删除</a></div><a class="mui-slider-handle a-detail" detailId="'+data[i].id+'"><div class="mui-media-body">'+data[i].cateName+'<span class="mui-right mui-pull-right">'+data[i].title+'</span><p class="mui-ellipsis">'+data[i].username+'<span class="mui-right mui-pull-right">'+data[i].deptname+'</span></p></div></a></li>';
                    $table.append(ps);
                    count++;

                    if(i == data.length - 1) {
                        //alert("  data[i].text  =  "+ data[i].text);
                        localStorage.setItem("userSuggestOldestId", data[i].id);
                    }
                }
                mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
            }

        }, 800);
    }
    var count = 0;
    /**
     * 上拉加载具体业务实现
     */
    function pullupRefresh() {
        //alert("下面数据");
        setTimeout(function() {
            var userSuggestOldestId = localStorage.getItem("userSuggestOldestId");
            //alert(userSuggestOldestId);
            var isFirst = "";
            if(!userSuggestOldestId) {
                isFirst = 1;
            } else {
                isFirst = 0;
            }
            var status = localStorage.getItem("userSuggestStatus");
            var $table = $('.mui-table-view');
            $.ajax({
                url: "${home}/wx/common/suggest/msg/apply_list_more.json",
                dataType: 'json',
                data: {
                    status: status,
                    userSuggestOldestId: userSuggestOldestId,
                    isFirst: isFirst
                },
                success: function(result) {
                    callback(result);
                }
            });

            function callback(result) {
                //alert(JSON.stringify(result));
                var data = result.data;

                mui('#pullrefresh').pullRefresh().endPullupToRefresh( /*(result.data.length <= 0 )*/ false); //参数为true代表没有更多数据了。

                for(var i = 0; i < data.length; i++) {

                    var ps = '<li class="mui-table-view-cell mui-media"><div class="mui-slider-right mui-disabled"><a class="mui-btn mui-btn-red del" id="'+data[i].id+'">删除</a></div><a class="mui-slider-handle a-detail" detailId="'+data[i].id+'"><div class="mui-media-body">'+data[i].cateName+'<span class="mui-right mui-pull-right">'+data[i].title+'</span><p class="mui-ellipsis">'+data[i].username+'<span class="mui-right mui-pull-right">'+data[i].deptname+'</span></p></div></a></li>';
                    $table.append(ps);
                    count++;

                    if(i == data.length - 1) {
                        localStorage.setItem("userSuggestOldestId", data[i].id);
                    }
                }
                if(i == data.length - 1) {
                    localStorage.setItem("userSuggestOldestId", data[i].id);
                }
            }

        }, 800);
    }
    if(mui.os.plus) {
        mui.plusReady(function() {
            setTimeout(function() {
                mui('#pullrefresh').pullRefresh().pullupLoading();
            }, 1000);

        });
    } else {
        mui.ready(function() {
            mui('#pullrefresh').pullRefresh().pullupLoading();
        });
    }


    /*删除*/
    $("#pullrefresh").on("tap", "a.del", function (){
        var $li = $(this);
        var id = $(this).attr("id");
        var btnArray = ['否', '是'];
        mui.confirm('确定删除吗？', '提示', btnArray, function(e) {
            if (e.index == 1) {

                $.ajax({
                    type: "post",
                    dataType: "json",
                    url: "${home}/wx/common/suggest/msg/del.json",
                    data: {
                        id: id
                    },
                    success: function(result){
                        callback(result);
                    }
                });
                function callback(result){
                    var msg = result.msg;
                    if("success"  == msg){
                        mui.toast("删除成功");
                        $li.parents("li").remove();
                    }else{
                        mui.toast("删除失败");
                    }
                    return;
                }
            } else {
                return;
            }
        })
    });

    /*选择*/
    $("#status-0").click(function(){
        $(this).addClass("mui-btn-primary").siblings().removeClass("mui-btn-primary");
        $(this).siblings().removeProp("disabled");
        localStorage.setItem("userSuggestStatus", 0);
        localStorage.removeItem("userSuggestOldestId");
        $("#pullrefresh ul").html("");
        pullupRefresh();
    });
    $("#status-2").click(function(){
        $(this).addClass("mui-btn-primary").siblings().removeClass("mui-btn-primary");
        $(this).siblings().removeProp("disabled");
        localStorage.setItem("userSuggestStatus", 2);
        localStorage.removeItem("userSuggestOldestId");
        $("#pullrefresh ul").html("");
        pullupRefresh();
    });
    /*选择*/

    $("body").on("tap", ".a-detail", function(){
        var detailId = $(this).attr("detailId");
        window.location.href='${home}/wx/common/suggest/msg/detail.html?id='+detailId;
    });
</script>
