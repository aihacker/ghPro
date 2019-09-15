<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${t.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .bg-img {
            height: 220px;
        }

        .file-list {
            font-size: 14px;
            color: #777;
            display: flex;
            padding: 2px 0;
        }

        .file-list span {
            line-height: 22px;
        }

        .file-list .name {
            padding-left: 5px;
        }

        .file-list .download {
            float: right;
            color: #0e90d2;
            padding: 0 6px;
        }

        .join-list img {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            margin-top: 4px;
        }
    </style>
</head>
<body>
<div class="mui-scroll-wrapper" style="bottom: 60px;">
    <div class="mui-scroll">
        <div class="mui-content">
            <div>
                <div class="bg-img ui-img-div">
                    <img src="${home}${empty t.path?'/image/default/notice.png':t.path}"/>
                </div>
                <div style="max-height: 80px;min-height: 50px;" class="ui-content mui-ellipsis-2">
                    ${t.name}
                </div>
            </div>

            <ul class="mui-table-view no ui-margin-top-10">
                <li class="mui-table-view-cell no">
                    <a class="ui-flex">
                        <span class="fa fa-clock-o"></span>
                        <span>开课时间</span>
                        <small class="ui-right">${t.time}</small>
                    </a>
                </li>
                <c:if test="${!empty t.remark}">
                    <div class="ui-content">
                        <h5 class="ui-title">课堂备注</h5>
                        <p>
                                ${t.remark}
                        </p>
                    </div>
                </c:if>
            </ul>

            <div class="ui-content ui-margin-top-10">
                <h5 class="ui-title">课程介绍</h5>
                <p>
                    ${t.content}
                </p>
            </div>

            <ul class="mui-table-view ui-margin-top-10 no">
                <c:if test="${t.joinNum gt 0}">
                    <li class="mui-table-view-cell no">
                        <a class="ui-flex">
                            <span>报名限制</span>
                            <small class="ui-right">限${t.joinNum}人报名</small>
                        </a>
                    </li>
                </c:if>
                <li class="mui-table-view-cell no">
                    <a class="ui-flex">
                        <span>报名情况</span>
                        <small class="ui-right">${empty t.joinTotal?0:t.joinTotal}人报名</small>
                    </a>
                </li>
            </ul>

            <c:if test="${!empty t.fileList}">
                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">课堂附件</h5>
                    <c:forEach items="${t.fileList}" var="f">
                        <div class="file-list">
                            <span class="fa fa-file"></span>
                            <span class="mui-ellipsis name">${f.filename}</span>
                            <span data-url="${home}${f.path}" class="fa fa-download download"></span>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${!empty t.joins}">
                <ul class="mui-table-view no ui-margin-top-10">
                    <li class="mui-table-view-cell" style="padding: 0 15px;">
                        <a href="join.html?id=${t.id}" class="ui-flex mui-navigate-right" style="margin: 0 -15px;">
                            <span style="line-height: 40px;">报名列表</span>
                            <small class="ui-right join-list">
                                <c:forEach items="${t.joins}" var="j">
                                    <img src="${j}"/>
                                </c:forEach>
                            </small>
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a href="#" onclick="sendReq();" class="mui-navigate-right ui-flex">
                            <span>下载报名信息</span>
                            <small class="ui-right" style="color: #0e90d2;">点击下载</small>
                        </a>
                        <%--<div style="height: 40px;"><a href="javascript:void(0);" onclick="();"><span style="line-height: 40px;">下载报名信息</span></a></div>--%>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
</div>

<div class="ui-fixed-bottom">
    <c:choose>
        <c:when test="${t.joinIs eq 1}">
            <button id="joinBtn" class="mui-btn mui-btn-danger mui-disabled">
                已报名
            </button>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${t.joinNum gt t.joinTotal}">
                    <button id="joinBtn" class="mui-btn mui-btn-primary">
                        我要报名
                    </button>
                </c:when>
                <c:otherwise>
                    <button id="joinBtn" class="mui-btn mui-btn-danger mui-disabled">
                        报名已满
                    </button>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

    <%--<button id="joinBtn" type="button" class="mui-btn ${t.joinIs eq 1?'mui-btn-danger mui-disabled':'mui-btn-primary'}">--%>
        <%--${t.joinIs eq 1?'已报名':'我要报名'}--%>
    <%--</button>--%>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var teachId = '${param.id}';
    $(function () {
        mui('.mui-scroll-wrapper').scroll();

        $('#joinBtn').on('click', function () {
            ui.confirm('是否报名？', function () {
                wxgh.request.post('api/join.json', {id: teachId}, function () {
                    ui.showToast('报名成功！', function () {
                        window.location.reload(true);
                    });
                });
            });
        });

        $('.file-list').on('tap', '.download', function () {
            var $self = $(this);
            ui.confirm('是否下载附件？', function () {
                wxgh.openUrl($self.data('url'));
            });
        });
    })

    function sendReq(){

        /*$.ajax({
            type:'post',
            url:'api/downmsg.html',
            data:'id='+teachId,
            success:function(){
            }
        });*/
        ui.confirm('是否下载报名信息？', function () {
            /*wxgh.openUrl($self.data('url'));*/
            window.location.href="api/downmsg.html?id="+teachId;
        });

    }
</script>
</body>
</html>