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
    <title>评论列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <style>
        .footer {
            /*position:absolute;
            bottom:0;*/
            background-color: white;
            z-index: 999;
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            _position: absolute;
            /* for IE6 */
            _top: expression(documentElement.scrollTop + documentElement.clientHeight-this.offsetHeight);
            /* for IE6 */
            overflow: visible;
        }

        .add_btn, .sure_btn {
            color: #fff;
            width: 100%;
            height: 44px;
        }

        .img_info {
            margin-left: 10px;
            margin-right: 10px;
            padding-top: 20px;
        }

        #left {
            float: left;
        }

        #right {
            float: right;
        }

        #right a {
            color: black;
            font-size: 20px;
            margin-top: 13%;
        }

        .mui-card-header {
            min-height: 50px;
        }

        .mui-table-view-cell {
            position: relative;
            overflow: hidden;
            padding: 0;
        }

        .ui-suggest-footer.ui-comment {
            display: flex;
        }

        .ui-suggest-footer input {
            margin: 0 4px;
            margin-top: 3px;
            height: 38px;
        }

        .mui-bar.ui-suggest-footer .mui-btn {
            padding: 0 15px 0 10px;
            top: 5px;
            height: 35px;
            margin: 0 2px;
            margin-right: 4px;
        }
        .mui-content {
            transform: translate3d(0px, 0px, 0px) translateZ(0px);
            height: 100%;
        }
    </style>
</head>
<body>
<%--<header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title">查看全部</h1>
</header>--%>
<div class="mui-content mui-scroll-wrapper" style="height:100%;">
    <div class="mui-slider">
        <ul class="mui-input-group mui-table-view" style="margin-bottom: 50px;margin-top: 0;">

            <c:forEach items="${comments}" var="comment">
                <li class="mui-table-view-cell">
                    <div class="mui-card-header mui-card-media">
                        <img src="${comment.avatar}"/>

                        <div class="mui-media-body">
          <span id="left">
            <span>${comment.username}</span>
            <p>发表于 <fmt:formatDate value="${comment.addTime}" pattern="yyyy-MM-dd  HH:mm"/></p>
          </span>
                        </div>
        <span style="color: black;">
                ${comment.content}
        </span>
                    </div>
                </li>
            </c:forEach>

        </ul>

    </div>

</div>
<nav class="mui-bar mui-bar-tab ui-suggest-footer ui-comment">
    <input type="text" placeholder="你想说点什么呢？" class="content"/>
    <button type="button" dataId="${work.id}" class="mui-btn mui-btn-blue submit_btn">评论</button>
</nav>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $(function () {
        //comment
        $('.submit_btn').click(function () {
            var workId = $(this).attr("dataId");
            var content = $('.content').val();

            if (!content) {
                mui.alert("请填写你的评论！");
                return;
            }
            var url = "${home}/wx/party/beauty/comments/add_comment.json";
            var data = {
                content: content,
                workId: workId
            }
            mui.getJSON(url, data, function (res) {
                if (res.ok) {
                    mui.toast("评论成功！");
                    $('.content').val("");
                    $('.footer').show();
                    $('.ui-comment').addClass('mui-hidden');
                    window.location.reload();
                } else {
                    mui.toast("评论失败！");
                }
            });
        });

    });
</script>
</body>
</html>
