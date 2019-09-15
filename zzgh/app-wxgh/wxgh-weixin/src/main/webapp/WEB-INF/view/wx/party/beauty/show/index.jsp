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
    <title>作品详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
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

        ul {
            margin-top: 0;
            /*margin-bottom: 50px;*/
        }

        .img_info {
            margin-left: 5.8px;
            margin-right: 10px;
            padding-top: 20px;
            padding-bottom: 10px;
            margin-bottom: 10px;
        }

        #left {
            float: left;
        }

        #right {
            float: right;
        }

        #right a {
            color: black;
            font-size: 15px;
            margin-top: 20%;
        }

        .mui-card-header {
            min-height: 50px;
        }

        .mui-table-view-cell {
            position: relative;
            overflow: hidden;
            padding: 0;
        }

        h1, h2, h3, h4, h5, h6 {
            line-height: 0;
            /* margin-top: 5px; */
            margin-bottom: 5px;
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

        .comententcontent {
            color: black;
        }

        .seeall {
            padding-top: 15px;
            padding-bottom: 15px;
        }

        .seeall .all {
            margin-left: 10px;
        }

        /*.ui-img-div {*/
            /*height: 130px;*/
        /*}*/

        /*.ui-img-div.one {*/
            /*height: 160px;*/
        /*}*/

        .mui-table-view.mui-grid-view .mui-table-view-cell .ui-img-div .mui-media-object {
            height: auto;
            width: 100%;
            max-width: 100%;
        }
    </style>
</head>
<body>
<div class="mui-contenmui-scroll-wrapper" style="height: 100%;">
    <div class="mui-slider">
        <div class="mui-card-header mui-card-media">
            <img src="${work.avatar}"/>
            <div class="mui-media-body">
		<span id="left">
			<span>${work.username}</span>
			<p>发表于<fmt:formatDate value="${work.addTime}" pattern="yyyy-MM-dd  HH:mm"/></p>
        </span>
                <span id="right">
			<a href="${home}/wx/party/beauty/comments/index.html?id=${work.id}" class="mui-icon mui-icon-chat">(<span
                    id="countCom">${countCom}</span>)</a>
			<a class="mui-icon mui-icon-star zan" workid="${work.id}" userzan="${userZan}">(<span
                    id="countZan">${countZan}</span>)</a>
        </span>
            </div>
        </div>

        <ul class="mui-input-group">
            <div class="img_info">
                <h4 style="text-align: center;margin-bottom: 20px;">${work.name}</h4>
                <span>${work.remark}</span>
                <div class="mui-card-content">
                    <c:choose>
                        <c:when test="${work.type == 1}">
                            <ul class="mui-table-view mui-grid-view">
                                <c:set var="fileCount" value="${fn:length(work.workFiles)}"/>
                                <c:forEach items="${work.workFiles}" var="file" varStatus="i">
                                    <li class="mui-table-view-cell mui-media mui-col-xs-${fileCount gt 1?6:12}  <%--${i.count gt 4?' mui-hidden':''}--%> ">
                                        <a href="javascript:;">
                                            <div class="ui-img-div${fileCount eq 1?' one':''}">
                                                <img data-preview-group="img" data-preview-src="${file.path}"
                                                     class="mui-media-object" src="${file.thumb}">
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                            <%--<c:forEach items="${work.workFiles}" var="file">--%>
                            <%--<img data-preview-src="${file.path}" data-preview-group="img" src="${file.thumb}" alt=""--%>
                            <%--width="100%" style="margin: 2px;"/>--%>
                            <%--</c:forEach>--%>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${work.workFiles}" var="file">
                                <video src="${file.path}" controls="controls" width="100%" height="400px"
                                       preload="preload" poster="${file.thumb}">
                                </video>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </ul>

        <ul class="com_group mui-input-group mui-table-view" style="margin-bottom: 50px;">

            <c:choose>
                <c:when test="${countCom == 0}">
                    <li class="nocomment mui-table-view-cell">暂无评论</li>
                </c:when>
                <c:otherwise>
                    <li class="seeall mui-table-view-cell" dataid="${work.id}">
                      <span class="mui-navigate-right">
                        <span class="all">查看全部</span>
                      </span>
                    </li>
                    <%--</ul>--%>
                    <%--<ul class="mui-input-group mui-table-view" style="margin-bottom: 50px;">--%>
                    <ul class="showcomment">

                    </ul>
                </c:otherwise>
            </c:choose>

        </ul>

    </div>


</div>
<nav class="mui-bar mui-bar-tab ui-suggest-footer ui-comment">
    <input type="text" placeholder="你想说点什么呢？" class="content"/>
    <button type="button" dataId="${work.id}" class="mui-btn mui-btn-blue submit_btn">评论</button>
</nav>

<%--<div class="footer">--%>
<%--<button type="button" style="width: 49.5%; float: left;" class="com_btn sure_btn mui-btn mui-btn-blue" >评论</button>--%>
<%--<button type="button" style="width: 49.5%; float: right;" userZan="${userZan}" dataId="${work.id}" class="zan_btn sure_btn mui-btn mui-btn-blue" >点赞</button>--%>
<%--<button type="button" style="width: 49.5%; float: right;" userZan="${userZan}" dataId="${work.id}" class="cancel_btn sure_btn mui-btn mui-btn-blue mui-hidden" >取消点赞</button>--%>
<%--</div>--%>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>

<script id="comTmpl" type="text/template">
    {{ for (var i in it) { }}
    <li class="mui-table-view-cell">
        <div class="mui-card-header mui-card-media">
        <img src="{{=it[i].avatar}}"/>
        <div class="mui-media-body">
        <span id="left">
        <span>{{=it[i].username}}</span>
        <p>发表于{{=it[i].addTime}}" </p>
        </span>
        </div>
        <span class="comententcontent">
        {{=it[i].content}}
        </span>
        </div>
    </li>
    {{}}}
</script>

<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    wxgh.previewImageInit()

    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes();
//    s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

        return Y + M + D + h + m;
    }

    var id = '${param.id}';
    wxgh.request.post('/wx/party/beauty/show/list3.json', {id: id}, function (data) {
        for(var i in data)
            data[i]['addTime'] = formatTime(data[i]['addTime']);
        var evalText = doT.template($("#comTmpl").text());
        $(".showcomment").html(evalText(data));
    })

    $(function () {

        //点赞
        $('.zan').click(function () {
            var addzan = $(this).attr('userzan');
            var workId = $(this).attr('workid');
            var countzan = $('#countZan').text();
            if (addzan == "0") {
                var myurl = "${home}/wx/party/beauty/show/add_zan.json";
                var mydata = {
                    workId: workId
                };
                mui.getJSON(myurl, mydata, function (result) {
                    if (result.ok) {
                        countzan++;
                        $('.zan').attr('userzan', 1);
                        $('#countZan').text(countzan);
                        mui.toast('点赞成功');
                    } else {
                        mui.toast('点赞失败');
                    }
                });

            } else {
                mui.toast('您已经点过赞了哦');
            }

        });

        //comment
        $('.submit_btn').click(function () {
            var content = $('.content').val();
            var workId = $(".submit_btn").attr("dataId");
            if (!content) {
                mui.alert('请填写你想评论的内容');
                return;
            }
            var url = "${home}/wx/party/beauty/show/add_comment.json";
            var data = {
                content: content,
                workId: workId
            };

            mui.getJSON(url, data, function (res) {
                if (res.ok) {
                    mui.toast("评论成功！");
                    $('.content').val("");
                    //更新评论数值
                    var countCom = $('#countCom').text();
                    countCom++;
                    $('#countCom').text(countCom);

//          window.location.reload();
                    //更新comment
                    var myurl = "${home}/wx/party/beauty/show/comment_list.json?";
                    var mydata = {
                        id: workId
                    };
                    mui.getJSON(myurl, mydata, function (result) {
                        if (result.ok) {
                            var comments = result.data;
                            var dataLength = result.data.length;
                            var html = " ";
                            if (dataLength > 3) {
                                for (var i = 0; i < 3; i++) {
                                    $('.showcomment li').remove();
                                    html += '<li class="mui-table-view-cell">' +
                                        '<div class="mui-card-header mui-card-media">' +
                                        '<img src="' + comments[i].avatar + '" />' +
                                        '<div class="mui-media-body">' +
                                        '<span id="left">' +
                                        '<span>' + comments[i].username + '</span>' +
                                        '<p>发表于 ' + formatTime(comments[i].addTime) + '</p>' +
                                        '</span>' +
                                        '</div>' +
                                        '<span class="comententcontent">' + comments[i].content + '</span>' +
                                        '</div>' +
                                        '</li>';
                                    $('.showcomment').append(html);
                                }
                            } else {
                                if (dataLength == 1) {

                                    for (var i = 0; i < dataLength; i++) {
                                        $('.nocomment').remove();
                                        var li = '<a id="a-showall" data-href="${home}/wx/party/beauty/comments/index.html?id=' + comments[i].workId + '">' +
                                            '<li class="seeall mui-table-view-cell" dataid="' + comments[i].workId + '">' +
                                            '<span class="mui-navigate-right">' +
                                            '<span class="all">查看全部</span>' +
                                            '</span>' +
                                            '</li><ul class="showcomment"></ul>';
                                        $('.com_group').append(li);
                                        html += '<li class="mui-table-view-cell">' +
                                            '<div class="mui-card-header mui-card-media">' +
                                            '<img src="' + comments[i].avatar + '" />' +
                                            '<div class="mui-media-body">' +
                                            '<span id="left">' +
                                            '<span>' + comments[i].username + '</span>' +
                                            '<p>发表于 ' + formatTime(comments[i].addTime) + '</p>' +
                                            '</span>' +
                                            '</div>' +
                                            '<span class="comententcontent">' + comments[i].content + '</span>' +
                                            '</div>' +
                                            '</li>' +
                                            '</a>';
                                        $('.showcomment').append(html);
                                    }
                                } else {
                                    for (var i = 0; i < dataLength; i++) {
                                        $('.showcomment li').remove();
                                        html += '<li class="mui-table-view-cell">' +
                                            '<div class="mui-card-header mui-card-media">' +
                                            '<img src="' + comments[i].avatar + '" />' +
                                            '<div class="mui-media-body">' +
                                            '<span id="left">' +
                                            '<span>' + comments[i].username + '</span>' +
                                            '<p>发表于 ' + formatTime(comments[i].addTime) + '</p>' +
                                            '</span>' +
                                            '</div>' +
                                            '<span class="comententcontent">' + comments[i].content + '</span>' +
                                            '</div>' +
                                            '</li>';
                                        $('.showcomment').append(html);
                                    }
                                }
                            }

                        }
                    });

                } else {
                    mui.toast("评论失败！");
                }
            });
        });


        //see_all_comments
        $('.seeall').click(function () {
            var workId = $(this).attr("dataid");
            self.location.reload();
            window.location.href = '${home}/wx/party/beauty/comments/index.html?id=' + workId;
        });


        $("body").on("tap", "#a-showall", function () {
            self.location.reload();
            window.location.href = $(this).attr("data-href");
        });

    });
</script>
</body>
</html>
