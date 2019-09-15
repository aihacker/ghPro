<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/24
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${a.atlName}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>
    <style>
        body,
        .mui-content {
            background-color: #fff;
        }

        .ui-card {
            padding: 10px 15px;
        }

        .ui-card .ui-text-body {
            margin-bottom: 0;
            padding-top: 10px;
        }

        .mui-card-header > img:first-child {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .mui-table-view.mui-grid-view:before,
        .mui-table-view.mui-grid-view:after {
            height: 0;
        }

        .ui-article-title {
            font-size: 20px;
            color: #000;
        }

        .ui-img-div {
            height: 100px;
        }

        .mui-table-view.mui-grid-view .mui-table-view-cell .ui-img-div .mui-media-object {
            height: 100%;
            width: auto;
            max-width: none;
        }

        .mui-card-header.mui-card-media {
            display: block;
            padding: 26px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <div class="ui-card">
        <div class="ui-article-title">
            ${a.atlName}
        </div>
        <div class="mui-card-header mui-card-media">
            <img src="${a.avatar}">
            <div class="mui-media-body">
                ${a.userName}
                <small class="mui-pull-right">${a.createFormatDate}</small>
                <p>${a.deptname}</p>
            </div>
        </div>
        <div class="mui-card-content">
            <p class="ui-text-body">
                ${a.atlContent}
            </p>
            <c:if test="${!empty a.fileList}">
                <ul class="mui-table-view mui-grid-view">
                    <c:forEach items="${a.fileList}" var="f" varStatus="i">
                        <li class="mui-table-view-cell mui-media mui-col-xs-4${i.count gt 9?' mui-hidden':''}">
                            <a href="javascript:;">
                                <div class="ui-img-div">
                                    <img data-preview-group="article_imgs" data-preview-src=""
                                         class="mui-media-object" src="${home}${f.path}">
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </div>

    <c:choose>
        <c:when test="${a.status eq 1}">
            <div class="ui-fixed-bottom">
                <button data-status="3" type="button" class="mui-btn mui-btn-block mui-btn-danger ui-btn">
                    取消显示
                </button>
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${a.status eq 3}">
                    <div class="ui-fixed-bottom">
                        <button data-status="1" type="button"
                                class="mui-btn mui-btn-block mui-btn-primary ui-btn">正常显示
                        </button>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="ui-bottom-btn-group">
                        <button data-status="2" type="button" class="mui-btn mui-btn-danger">审核失败</button>
                        <button data-status="1" type="button" class="mui-btn mui-btn-success">审核通过</button>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">
    $(function () {
        var id = '${param.id}'

        wxgh.previewImageInit();

        //删除按钮
        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var status = $(this).data('status')
            ui.confirm(status == 3 ? '是否取消显示？' : '是否显示？', function () {
                if (status == 3) {
                    updateStatus(status, '取消成功')
                } else {
                    updateStatus(status, '显示成功')
                }
            })
        })

        $('.ui-bottom-btn-group').on('tap', 'button', function () {
            var status = $(this).data('status')
            var msg
            if (status == 1) {
                msg = '是否同意该审核？'
            } else {
                msg = '是否不同意该审核？'
            }
            ui.confirm(msg, function () {
                updateStatus(status, '审核成功')
            })
        })

        function updateStatus(status, sucMsg) {
            wxgh.request.post('api/apply.json', {id: id, status: status}, function () {
                ui.showToast(sucMsg, function () {
                    window.location.reload()
                })
            })
        }
    })
</script>
</body>
</html>
