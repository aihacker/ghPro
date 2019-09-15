<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/29
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>【${r.name}】考试结果</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .mui-content {
            background-color: #fff;
        }

        .exam-title {
            color: #777;
            font-size: 16px;
            padding: 8px 10px;
            position: relative;
        }

        .exam-title:not(.no):after {
            position: absolute;
            right: 0;
            bottom: 0;
            left: 5px;
            height: 1px;
            content: '';
            -webkit-transform: scaleY(.5);
            transform: scaleY(.5);
            background-color: #c8c7cc;
        }

        .exam-result {
            text-align: center;
            padding: 10px 0;
        }

        .exam-result span {
            margin: 5px 20px;
        }

        .exam-result.red {
            color: #fc544c;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="closeBtn" type="button" class="mui-btn mui-btn-danger">考试结束</button>
    </div>

    <div class="exam-title">
        <span>${r.name}考试</span>
        <span class="mui-pull-right" style="color: #007aff;">
							<span class="fa fa-heart"></span> <small>已做完${r.complete}题</small>
				</span>
    </div>
    <div class="exam-result red ui-margin-top-44">
        <span>做错</span>
        <span>${r.mistake}题</span>
    </div>
    <div class="exam-result">
        <span>做对</span>
        <span>${r.right}题</span>
    </div>
    <div class="exam-result">
        <span>获得积分</span>
        <span>${r.mistake gt 0? 0 : 10}分</span>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    $(function () {
        wxgh.wxInit('${weixin}')
        $('#closeBtn').on('tap', function () {
            wx.closeWindow()
        })
    })
</script>
</body>
</html>