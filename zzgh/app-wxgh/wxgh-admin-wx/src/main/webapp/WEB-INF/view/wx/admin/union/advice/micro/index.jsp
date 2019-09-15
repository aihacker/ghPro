<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/8
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>资金申请详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }

        .ui-img-div {
            display: -webkit-flex;
            display: flex;
            justify-content: center;
            overflow: hidden;
            align-items: center;
            height: 60px;
        }

        .mui-scroll-wrapper {
            bottom: 50px;
        }
    </style>
</head>
<body>

<div class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view" style="margin-top: 0px;">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">成果名称</label>
                <span class="mui-right mui-pull-right">${data.name}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">团队名称</label>
                <span class="mui-right mui-pull-right">${data.team}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">类别</label>
                <span class="mui-right mui-pull-right">
                    <c:choose>
                        <c:when test="${data.type == 1}">技能</c:when>
                        <c:when test="${data.type == 2}">营销</c:when>
                        <c:when test="${data.type == 3}">服务</c:when>
                        <c:when test="${data.type == 4}">管理</c:when>
                        <c:otherwise>未知</c:otherwise>
                    </c:choose>
                </span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">部门</label>
                <span class="mui-right mui-pull-right">${data.deptname}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">所属大类</label>
                <span class="mui-right mui-pull-right">${data.cate1}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">所属细类</label>
                <span class="mui-right mui-pull-right">${data.cate2}</span>
            </li>
            <li onclick="window.location.href='${home}/work/member.wx?id=${data.id}&workType=3'"
                class="mui-table-view-cell">

                <label class="ui-li-label ui-text-info">成员</label>
                <span class="mui-right mui-pull-right">
                <c:forEach items="${users}" var="item" varStatus="status">
                    <c:if test="${status.index < 2}">
                        <img src="${item.userImg}" style="height: 25px;width:25px;border-radius: 50%;">
                    </c:if>
                </c:forEach>
                <c:if test="${fn:length(users) > 1}">
                    ...<span class="mui-icon mui-icon-forward"></span>
                </c:if>
                    </span>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">创新工作点</label>
                <br>
                <div data-content="${data.point}">
                    ${fn:substring(data.point, 0, 50)}
                    <c:if test="${fn:length(data.point)>= 50}">......
                    </c:if>
                </div>
                <c:if test="${fn:length(data.point)>= 50}">
                    <p class="p-more"
                       style="text-align: right;color: #50B4E8;background-color: white;padding-bottom: 10px;"
                       data-status="btn-more">
                        查看全文 <span class="mui-icon mui-icon-arrowdown"></span></p>
                </c:if>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">创新举措</label>
                <br>
                <div data-content="${data.behave}">
                    ${fn:substring(data.behave, 0, 50)}
                    <c:if test="${fn:length(data.behave)>= 50}">......
                    </c:if>
                </div>
                <c:if test="${fn:length(data.behave)>= 50}">
                    <p class="p-more"
                       style="text-align: right;color: #50B4E8;background-color: white;padding-bottom: 10px;"
                       data-status="btn-more">
                        查看全文 <span class="mui-icon mui-icon-arrowdown"></span></p>
                </c:if>
            </li>
        </ul>


        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info"> 成果描述</label>
                <div data-content="${txt}">
                    ${fn:substring(txt, 0, 50)}
                    <c:if test="${fn:length(txt)>= 50}">......
                    </c:if>
                </div>
                <c:if test="${fn:length(txt)>= 50}">
                    <p class="p-more"
                       style="text-align: right;color: #50B4E8;background-color: white;padding-bottom: 10px;"
                       data-status="btn-more">
                        查看全文 <span class="mui-icon mui-icon-arrowdown"></span></p>
                </c:if>
            </li>
            <li class="mui-table-view-cell">
                <c:choose>
                    <c:when test="${imgList[0] == null || imgList[0] == ''}">
                        <div class="mui-text-center ui-text-info">暂无附件</div>
                    </c:when>
                    <c:otherwise>
                        <br>

                        <ul class="mui-table-view mui-grid-view ul-img"
                            style="background: whitesmoke;padding-top: 10px;">
                            <c:forEach items="${imgList}" var="img">
                                <li class="mui-table-view-cell mui-media mui-col-xs-4 li-img">
                                    <a href="#" style="padding:0px 10px;" class="show-a">
                                        <div class="ui-img-div">
                                            <img class="mui-media-object show-img"
                                                 style="width: 100%;"
                                                 src="${img}"
                                                 data-preview-src="" data-preview-group="0">
                                        </div>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
        <ul class="mui-table-view">
            <li class="mui-table-view-cell">
                申请人：
                <small>${data.username}-${data.deptname}</small>
            </li>
            <c:if test="${data.applyStatus gt 0}">
                <li class="mui-table-view-cell">
                    审核时间：
                    <small><fmt:formatDate value="${data.auditTime}" pattern="yyyy-MM-dd HH:mm"/></small>
                </li>
                <li class="mui-table-view-cell">
                    审核状态：
                    <small>${data.applyStatus eq 0?'未审核':(data.applyStatus eq 1?'已通过':'未通过')}</small>
                </li>
                <li class="mui-table-view-cell">
                    审核意见：
                    <p>${data.auditIdea}</p>
                </li>
            </c:if>
        </ul>

        <div class="ui-textarea-div">
            <textarea name="idea" rows="3" maxlength="200" placeholder="您的审核意见..."></textarea>
        </div>
    </div>
</div>

<div class="ui-bottom-btn-group">
    <button${data.applyStatus eq 2?' disabled':''} data-status="2" type="button" class="mui-btn ui-btn mui-btn-danger">
        不通过
    </button>
    <button${data.applyStatus eq 1?' disabled':''} data-status="1" type="button" class="mui-btn ui-btn mui-btn-success">
        通&nbsp;过
    </button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script>
    var applyId = '${data.pid}'

    wxgh.previewImageInit();
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $(function () {
        $("p.p-more").click(function () {
            var $contenttarget = $(this).siblings("div");
            var status = $(this).attr("data-status");
            if (status == "btn-more") {
                var less_content = $contenttarget.text();
                var content = $contenttarget.attr("data-content");
                $contenttarget.text(content);
                $contenttarget.attr("data-content", less_content);
                $(this).attr("data-status", "btn-less");
                $(this).html('收起<span class="mui-icon mui-icon-arrowup"></span>');
            } else if ("btn-less") {
                var more_content = $contenttarget.text();
                var content = $contenttarget.attr("data-content");
                $contenttarget.text(content);
                $contenttarget.attr("data-content", more_content);
                $(this).attr("data-status", "btn-more");
                $(this).html('查看全文<span class="mui-icon mui-icon-arrowdown"></span>');
                mui('.ui-scroll-wrapper').scroll().scrollTo(0, 0, 200)
            }
        });

        wxgh.autoTextarea($('.ui-textarea-div textarea'))

        var loading = new ui.loading('加载中...')

        $('.ui-bottom-btn-group').on('tap', 'button[data-status]', function () {
            var idea = $('textarea[name=idea]').val()
            if (!idea) {
                alert('请输入您的审核意见哦！')
                return
            }
            var status = $(this).data('status')
            var txt = status == 1 ? '通过' : '不通过'
            var cf = confirm('是否' + txt + '？')
            if (cf) {
                loading.show()
                mui.post(homePath + '/wx/admin/union/advice/micro/apply.json', {
//                    action: 'apply',
                    status: status,
                    id: applyId,
                    auditIdea: idea,
                    adviceId: '${data.adviceId}',
                }, function (res) {
                    loading.hide()
                    if (res.ok) {
                        ui.showToast('审核成功！', function () {
                            mui.back()
                        })
                    } else {
                        alert('审核失败！')
                    }
                }, 'json')
            }
        })
    })
</script>
</body>
</html>
