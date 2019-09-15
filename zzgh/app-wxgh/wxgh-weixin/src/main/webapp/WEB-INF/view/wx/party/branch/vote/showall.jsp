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
    <h1 class="mui-title"><span id = "zhibu">已有${joinNum}人参加评议</h1>
</header>
<div class="mui-content">
    <ul class="mui-table-view">

    </ul>
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
        }else{
            wxgh.request.get("api/get_result.json",{id:id},function (data) {
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
    })


</script>
</body>
</html>

