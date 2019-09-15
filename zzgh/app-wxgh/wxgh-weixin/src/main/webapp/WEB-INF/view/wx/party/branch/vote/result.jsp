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
    </style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
    <h1 class="mui-title"><%--<span id = "zhibu">本支部共有${num}人,--%></span>已有${joinNum}人参加评议</h1>
</header>
<div class="mui-content">
            <ul class="mui-table-view">

            </ul>
</div>
<div class="ui-fixed-bottom">
    <button id="inShowBtn" type="button" class="mui-btn mui-btn-danger" style="width:96%">进入投票</button>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        var id = '${param.id}';
        $ul = $(".mui-table-view")
        var voteType = '${voteType}';
        console.log(voteType)
        if(voteType != 3){
            $("#zhibu").hide();
            wxgh.request.get("api/get_result.json",{id:id},function (data) {
                console.log(data)
                for (var i = 0; i < data.length; i++) {
                    var d = data[i];
                    var name = d.orderNumb + '、' + d.name
                    var question = $('<li class="mui-table-view-cell">' + name + '</li>')
                    var answers = data[i].answers;
                    var $form = $("<div class='mui-card'></div>")
                    if(d.type != 3){
                        for (var q = 0; q < answers.length; q++) {
                            if(answers[q].count == null){
                                answers[q].count = 0
                            }
                            debugger
                            var answer = $(
                                "<span><label>" + answers[q].name + "</label><span class='mui-badge'>"+answers[q].count+"</span></span>")
                            $form.append(answer)
                        }
                    } else {
                        debugger
                        $form.append("<span><a href='api/text_list.html?id=" + d.id + "'>查看所有答案</a></span>");
                    }
                    question.append($form)
                    $ul.append(question)
                }
            })
        }else{
            wxgh.request.get("api/get_result3.json",{id:id},function (data) {
                console.log(data)
                for (var i = 0; i < data.length; i++) {
                    var d = data[i];
                    var name = d.orderNumb + '、' + d.name
                    var question = $('<li class="mui-table-view-cell">' + name + '</li>')
                    var answers = data[i].answers;
                    var $form = $("<div class='mui-card'></div>")
                    for (var q = 0; q < answers.length; q++) {
                        if(answers[q].count == null){
                            answers[q].count = 0
                        }
                        var answer = $(
                            "<span><label>" + answers[q].name + "</label><span class='mui-badge'>"+answers[q].count+"</span></span>")
                        $form.append(answer)
                    }
                    question.append($form)
                    $ul.append(question)
                }
            })
        }


        $("#inShowBtn").on("tap",function () {
            mui.openWindow('${home}/wx/party/branch/vote/show.html?id=' + id);
        })
    })


</script>
</body>
</html>
