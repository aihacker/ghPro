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
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/app.css" rel="stylesheet"/>
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

<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <!--数据列表-->
        <ul class="mui-table-view">

        </ul>
    </div>
</div>



</body>

</html>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
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
        localStorage.removeItem("diseaseOldestId");
        $("#a-back", parent.document).hide();
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
        setTimeout(function() {
            var $table = $('.mui-table-view');
            $.ajax({
                url: "${home}/wx/admin/common/disease/apply_list_refresh.json",
                dataType: 'json',
                data: {
//                    action: 'applyListRefresh',
                    status: 0,
                    step : 2
                },
                success: function(result) {
                    callback(result);
                }
            });

            function callback(result) {
                //alert(JSON.stringify(result));
                var data = result.data;
                for(var i = 0; i < data.length; i++) {
                    var ps = '<li class="mui-table-view-cell mui-media"><div class="mui-slider-right mui-disabled"><a class="mui-btn mui-btn-red del" id="'+data[i].id+'">删除</a></div><a class="mui-slider-handle a-detail" detailId="'+data[i].id+'" cateId="'+data[i].cateId+'"><div class="mui-media-body mui-ellipsis">'+data[i].category+'<span class="mui-right mui-pull-right">'+formatTime(data[i].applyTime)+'</span><p class="mui-ellipsis">'+data[i].name+'<span class="mui-right mui-pull-right">'+data[i].mobile+'</span></p></div></a></li>';
                    $table.append(ps);
                    count++;

                    if(i == data.length - 1) {
                        //alert("  data[i].text  =  "+ data[i].text);
                        localStorage.setItem("diseaseOldestId", data[i].id);
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
            var diseaseOldestId = localStorage.getItem("diseaseOldestId");
            //alert(diseaseOldestId);
            var isFirst = "";
            if(!diseaseOldestId) {
                isFirst = 1;
            } else {
                isFirst = 0;
            }
            var $table = $('.mui-table-view');
            $.ajax({
                url: "${home}/wx/admin/common/disease/apply_list_more.json",
                dataType: 'json',
                data: {
//                    action: 'applyListMore',
                    status: 0,
                    diseaseOldestId: diseaseOldestId,
                    isFirst: isFirst,
                    step : 2
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
                    var ps = '<li class="mui-table-view-cell mui-media"><div class="mui-slider-right mui-disabled"><a class="mui-btn mui-btn-red del" id="'+data[i].id+'">删除</a></div><a class="mui-slider-handle a-detail" detailId="'+data[i].id+'" cateId="'+data[i].cateId+'"><div class="mui-media-body mui-ellipsis">'+data[i].category+'<span class="mui-right mui-pull-right">'+formatTime(data[i].applyTime)+'</span><p class="mui-ellipsis">'+data[i].name+'<span class="mui-right mui-pull-right">'+data[i].mobile+'</span></p></div></a></li>';
                    $table.append(ps);
                    count++;
                    if(i == data.length - 1) {
                        localStorage.setItem("diseaseOldestId", data[i].id);
                    }
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
                    url: "${home}/wx/admin/common/disease/del.json",
                    data: {
//                        action: "del",
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

    $("body").on("tap", ".a-detail", function(){
        var detailId = $(this).attr("detailId");
        var cateId = $(this).attr("cateId");
        var type = "";
        if ("jb" == cateId) {
            type = "getJB";
        }else if ("jy" == cateId) {
            type = "getJY";
        }else if ("qs" == cateId) {
            type = "getQS";
        }else if ("zc" == cateId) {
            type = "getZC";
        }else if ("zh" == cateId) {
            type = "getZH";
        }else if ("zx" == cateId) {
            type = "getZX";
        }else if ("pk" == cateId) {
            type = "getPK";
        }
        window.location.href='${home}/wx/admin/common/disease/detail.html?applyId='+detailId+'&type='+type;
    });
</script>
