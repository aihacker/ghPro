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

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" id="a-back" style="display: none;"></a>

    <a id='showUserPicker' class="mui-icon mui-icon-right-nav mui-pull-right mui-hidden" style="font-size: 15px;   line-height: 28px;">未审核</a>

    <h1 class="mui-title">互助会入会审核</h1>
</header>
<div class="mui-content"></div>


</body>

</html>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script>
    (function($, doc) {
        mui.init();
        mui.ready(function() {
            //普通示例
            var userPicker = new mui.PopPicker();
            userPicker.setData([{
                value: 0,
                text: '未审核'
            }, {
                value: 1,
                text: '已通过'
            }, {
                value: 2,
                text: '不通过'
            }, {
                value: 3,
                text: '全部'
            }]);
            var showUserPickerButton = doc.getElementById('showUserPicker');
            var userResult = doc.getElementById('userResult');
            showUserPickerButton.addEventListener('tap', function(event) {
                userPicker.show(function(items) {
                   alert(JSON.stringify(items[0].value));
                    //返回 false 可以阻止选择框的关闭
                    //return false;
                });
            }, false);
            //----------------------------------------

        });
    })(mui, document);
</script>
<script type="text/javascript">
    //启用双击监听
    mui.init({
        gestureConfig:{
            doubletap:true
        },
        subpages:[{
            url:'main.html',
            id:'main.html',
            styles:{
                top: '45px',
                bottom: '0px'
            }
        }]
    });

    var contentWebview = null;
    document.querySelector('header').addEventListener('doubletap',function () {
        if(contentWebview==null){
            contentWebview = plus.webview.currentWebview().children()[0];
        }
        contentWebview.evalJS("mui('#pullrefresh').pullRefresh().scrollTo(0,0,100)");
    });
</script>