<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/26
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${sugget.title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .mui-scroll-wrapper {
            background-color: #fff;
            padding-bottom: 40px;
        }

        .mui-card-content {
            margin-top: 20px;
        }

        .mui-card-content p {
            color: #000;
            font-size: 15px;
            margin-top: 8px;
        }

        .ui-manner-div {
            padding-bottom: 25px;
            border-bottom: 1px solid gainsboro;
            font-size: 13px;
            color: #777;
            position: relative;
        }

        .ui-seenumb-div {
            display: block;
            position: absolute;
            right: 10px;
        }

        .ui-manner-div .ui-seenumb {
            font-size: 16px;
            margin-right: 4px;
        }

        .ui-panel-title {
            font-size: 15px;
            margin: 10px 8px;
            padding: 4px 0;
            padding-left: 4px;
            border-left: 4px solid dodgerblue;
        }

        .ui-panel-cnt .mui-media-object {
            -webkit-border-radius: 50%;
            border-radius: 50%;
            border: 1px solid gainsboro;
            padding: 1px;
        }

        .ui-item-footer small {
            font-size: 13px;
            margin-left: 50px;
        }

        .ui-icon-div a {
            font-size: 12px;
            color: #777;
        }

        .ui-icon-div a:active {
            color: gainsboro;
        }

        .ui-icon-div .fa {
            font-size: 15px;
            margin-left: 10px;
        }

        .ui-panel-cnt .mui-table-view-cell.mui-active {
            background-color: transparent;
        }

        .ui-item-comment .ui-item {
            border-left: 3px solid gainsboro;
            padding-left: 6px;
            margin: 5px 50px;
            font-size: 13px;
            color: #777;
        }

        .mui-bar-tab.ui-suggest-footer .mui-tab-item,
        .mui-bar-tab.ui-suggest-footer {
            height: 45px;
        }

        .mui-bar-tab.ui-suggest-footer .mui-tab-item.mui-active {
            color: #929292;
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

        .ui-lov-numb {
            margin-left: 5px;
        }

        .mui-ellipsis {
            overflow: inherit;
            white-space: inherit;
            text-overflow: clip;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <%--<h1 class="mui-title">${sugget.title}</h1>--%>
<%--</header>--%>

<div class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <div class="mui-content-padded">
            <div class="mui-card-header mui-card-media">
                <img src="${sugget.avatar}"/>

                <div class="mui-media-body">
                    ${sugget.username}
                    <p>发表于 ${sugget.timeStr}</p>

                </div>
            </div>
            <div class="mui-card-content mui-content-padded">
                <h4 class="mui-text-center">${sugget.title}</h4>

                <p>
                    ${sugget.content}
                </p>
            </div>
            <div class="ui-manner-div ui-margin-top-15">
                <div class="ui-seenumb-div">
                    <span class="mui-icon mui-icon-eye ui-text-info ui-seenumb"></span>${sugget.seeNum}
                </div>
            </div>
        </div>

        <div class="ui-panel">
            <div class="ui-panel-title">
                全部评论
            </div>
            <div class="ui-panel-cnt">
                <div class="mui-loading">
                    <div class="mui-spinner"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<nav id="suggestBtngroup" class="mui-bar mui-bar-tab ui-suggest-footer">
    <a id="replyBtn" class="mui-tab-item">
        <span class="fa fa-commenting-o"></span>
        <span class="mui-tab-label">${sugget.commNum}</span>
    </a>
    <c:choose>
        <c:when test="${isLov}">
            <a id="lovBtn" class="mui-tab-item mui-disabled">
                <span class="fa fa-heart ui-text-danger"></span>
                <span class="mui-tab-label">${sugget.lovNum}</span>
            </a>
        </c:when>
        <c:otherwise>
            <a id="lovBtn" class="mui-tab-item">
                <span class="fa fa-heart-o"></span>
                <span class="mui-tab-label">${sugget.lovNum}</span>
            </a>
        </c:otherwise>
    </c:choose>
    <%--<a id="tranBtn" class="mui-tab-item">--%>
        <%--<span class="fa fa-share-square-o"></span>--%>
        <%--<span class="mui-tab-label">${sugget.tranNum}</span>--%>
    <%--</a>--%>
</nav>

<nav class="mui-bar mui-bar-tab ui-suggest-footer ui-comment mui-hidden">
    <input type="text" placeholder="你想说点什么呢？"/>
    <button type="button" class="mui-btn mui-btn-success">评论</button>
</nav>


<div id="otherPopover" class="mui-popover mui-popover-action mui-popover-bottom">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="#">举报该评价</a>
        </li>
        <%--<li class="mui-table-view-cell">--%>
        <%--<a href="#">选取现有的</a>--%>
        <%--</li>--%>
    </ul>
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="#otherPopover"><b>取消</b></a>
        </li>
    </ul>
</div>PreGenerator
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    wxgh.wxInit('${weixin}')

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    var homePath = '${home}'
    var sugId = '${param.id}'

    var loadign = ui.loading('转发中...')

    /**
     * 转发
     */
    $('#tranBtn').on('tap', function () {
        wxgh.wxContactOpen('${wx_contact}', function (all, users) {
            if (all) {
                alert('不能转发给全部人哦！')
            }

            if (users && users.length > 0) {
                var tipStr = ''
                var userids = []
                for (var i in users) {
                    var userid = users[i].id
                    var name = users[i].name
                    tipStr += name + ','
                    userids.push(userid)
                }
                tipStr = tipStr.substring(0, tipStr.length - 1)

                var cf = confirm('是否转发给以下用户：' + tipStr + '？')
                if (cf) {
                    loadign.show()
                    mui.post(homePath + '/wx/common/suggest/show/tran.json', {
                        id: id,
                        users: userids.toString()
                    }, function (res) {
                        loadign.hide()
                        if (res.ok) {
                            alert('转发成功！')
                        } else {
                            alert('转发失败！')
                        }
                    }, 'json')
                }
            }
        })
    })

</script>
<script type="text/javascript" src="${home}/weixin/script/common/suggest/sugshow.js"></script>
</body>

</html>
