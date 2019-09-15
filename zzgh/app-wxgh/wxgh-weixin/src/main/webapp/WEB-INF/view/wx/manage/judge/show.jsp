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
    <title></title>
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
        #Btn{
            display: none;
        }
    </style>
</head>
<body>
<header class="mui-bar mui-bar-nav">
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>
    <h1 class="mui-title">${s.name}</h1>
</header>
<div class="mui-content">
    <p id="briefInfo">${s.briefInfo}</p>
</div>
<div class="mui-content">
    <div class="mui-scroll-wrapper">

        <div class="mui-scroll">
            <ul class="mui-table-view">

            </ul>
        </div>
    </div>
</div>
<div class="ui-fixed-bottom">
    <button id="subBtn" type="button" class="mui-btn mui-btn-danger" style="width:47%">提交结果</button>
    <button id="reBtn" type="button" class="mui-btn mui-btn-primary" style="width:47%">查看结果</button>
    <%--<button id="Btn" type="button" class="mui-btn mui-btn-primary" style="width:47%">查看结果</button>--%>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
        });

        var height = $(window).height() - ($("header").height() + $("#briefInfo").height() + $(".ui-fixed-bottom").height()+20)
        $(".mui-scroll-wrapper").height(height)

        var examId = '${param.id}';
        var examType = '${s.type}'
        console.log(examType)

        $ul = $(".mui-table-view")
        wxgh.request.get("api/get_question.json",{examid:examId},function (data) {
            for(var i = 0;i<data.length;i++) {
                var d = data[i];
                var name = d.orderNumb + '、' + d.name
                var question = $('<li class="mui-table-view-cell">'+name+'</li>')
                var answers = data[i].answers;
                var $form = $("<div class='mui-card'><form class='mui-input-group'></form></div>")
                if(d.type==1){
                    for(var q=0;q<answers.length;q++){
                        var answer = $("<div class='mui-input-row mui-radio'>" +
                            "<label>"+answers[q].name+"</label><input name="+d.id+" value="+answers[q].id+" type= 'radio'></input></div>")
                        $form.append(answer)
                    }
                }else{
                    for(var q=0;q<answers.length;q++){
                        var answer = $("<div class='mui-input-row mui-checkbox'>" +
                            "<label>"+answers[q].name+"</label><input name="+d.id+" value= '' type= 'checkbox'></input></div>")
                        $form.append(answer)
                    }
                }
                question.append($form)
                $ul.append(question)
            }

            $("#subBtn").click(function(){
                if(data[0].type==1){
                    typeRadio(data)
                }else{
                    console.log(2)
                }
            })
        })

        function typeRadio(data) {
            var info = []
            for(var i = 0;i<data.length;i++){
                var d = data[i]
                if($("input[name="+d.id+"]:checked").length==0){
                    ui.alert("您还有选项未选择");
                    return
                }else{
                    var answer = {}
                    answer.questionId = d.id;
                    answer.answerId = $("input[name="+d.id+"]:checked").val()
                    info.push(answer)
                }
            }
            wxgh.request.post("api/add_record.json",{voteId:examId,answer:JSON.stringify(info)},function(res){
                ui.alert(res)
            })
        }
        <%--if(examType == 3){--%>
        <%--$("#reBtn").hide();--%>
        <%--$("#Btn").show().on("tap",function () {--%>
        <%--mui.openWindow('${home}/wx/party/branch/vote/resultlist.html?id=' + examId);--%>
        <%--})--%>
        <%--}else{--%>
        <%--$("#reBtn").on("tap",function () {--%>
        <%--mui.openWindow('${home}/wx/party/branch/vote/result.html?id=' + examId);--%>
        <%--})--%>
        <%--}--%>
        $("#reBtn").on("tap",function () {
            mui.openWindow('${home}/wx/manage/judge/result.html?id=' + examId);
        })
    })
</script>
</body>
</html>
