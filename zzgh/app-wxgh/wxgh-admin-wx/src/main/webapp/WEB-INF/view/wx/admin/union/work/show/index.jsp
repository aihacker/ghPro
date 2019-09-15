<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/7
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>工作坊详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .ui-title-content {
            padding: 5px 15px;
            background-color: #fff;
        }

        .mui-table-view-cell.mui-active {
            background-color: #fff;
        }

        .mui-table-view-cell small {
            float: right;
            margin-right: 10px;
            color: #777;
            position: relative;
            top: 3px;
        }

        .mui-scroll-wrapper {
            bottom: 50px;
        }

        .ui-user-list {
            float: right;
            margin-right: 20px;
            position: relative;
            top: 4px;
        }

        .ui-user-list img {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            margin: 0 2px;
        }

        .ui-img-div {
            height: 100px;
        }

        .ui-img-div img {
            height: 100%;
            width: auto;
        }

        #resultImgList:after,
        #resultImgList:before {
            height: 0 !important;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <h5 class="ui-h5-title">工作坊信息</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    类&nbsp;&nbsp;型:
                    <small>${s.shopTypeName}</small>
                </li>
                <li class="mui-table-view-cell">
                    带头人：
                    <small>${s.teamLeaderName}-${s.deptName}</small>
                </li>
                <li class="mui-table-view-cell">
                    项目名称：
                    <p>${s.itemName}</p>
                </li>
                <li class="mui-table-view-cell">
                    项目场地：
                    <p>${empty s.address?'暂无场地':s.address}</p>
                </li>
                <li class="mui-table-view-cell">
                    成&nbsp;&nbsp;果：
                    <div id="resultHide" class="mui-hidden">${s.content}</div>
                    <p id="resultP"></p>
                    <div class="mui-loading">
                        <div class="mui-spinner"></div>
                    </div>
                    <ul class="mui-table-view mui-grid-view" id="resultImgList">
                    </ul>
                </li>
                <li class="mui-table-view-cell">
                    团队名称：
                    <p>${s.teamName}</p>
                </li>
                <li class="mui-table-view-cell">
                    <a data-url="${home}/wx/admin/union/work/user/index.html?id=${s.id}" class="mui-navigate-right">
                        项目成员：
                        <div class="ui-user-list">
                            <c:forEach items="${imgs}" var="img">
                                <img data-preview-src="" data-preview-group="img" src="${img}"/>
                            </c:forEach>
                        </div>
                    </a>
                </li>
            </ul>
            <h5 class="ui-h5-title ui-margin-top-10">审核信息</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    申请人：
                    <small>${s.userName}</small>
                </li>
                <c:if test="${s.applyStatus gt 0}">
                    <li class="mui-table-view-cell">
                        审核时间：
                        <small><fmt:formatDate value="${s.auditTime}" pattern="yyyy-MM-dd HH:mm"/></small>
                    </li>
                    <li class="mui-table-view-cell">
                        审核状态：
                        <small>${s.applyStatus eq 0?'未审核':(s.applyStatus eq 1?'已通过':'未通过')}</small>
                    </li>
                    <li class="mui-table-view-cell">
                        审核意见：
                        <p>${empty s.auditIdea?'暂无意见':s.auditIdea}</p>
                    </li>
                </c:if>
            </ul>
            <div class="ui-textarea-div">
                <textarea name="idea" rows="3" maxlength="200" placeholder="您的审核意见..."></textarea>
            </div>
        </div>
    </div>

    <div class=" ui-fixed-bottom ui-bottom-btn-group ui-flex">
        <button${s.applyStatus eq 2?' disabled':''} data-status="2" type="button"
                                                    class="mui-btn mui-btn-outlined ui-btn mui-btn-danger">不通过
        </button>
        <button${s.applyStatus eq 1?' disabled':''} data-status="1" type="button"
                                                    class="mui-btn mui-btn-outlined ui-btn mui-btn-success">通&nbsp;过
        </button>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">
    var applyId = '${s.applyId}'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
    $(function () {
        var result = JSON.parse($('#resultHide').text())
        var $span = $('<span></span>')
        $('#resultP').next('.mui-loading').remove()
        $span.text(decodeURIComponent(result.txt))
        $('#resultP').append($span)
        var $imgList = $('#resultImgList')
        var hasImg = 0
        if (result.imgs && result.imgs.length > 0) {
            for (var i in result.imgs) {
                var img = result.imgs[i].url
                if (img) {
                    var $item = $('<li class="mui-table-view-cell mui-media mui-col-xs-4">' +
                        '<a href="#"> <div class="ui-img-div">' +
                        '<img src="' + wxgh.get_image(img) + '">' +
                        '</div> </a> </li>')
                    $imgList.append($item)
                    hasImg++
                }
            }
        }
        if (hasImg <= 0) {
            $imgList.addClass('mui-hidden')
        }
        wxgh.previewImageInit()
        $('a[data-url]').on('tap', function () {
            var url = $(this).data('url')
            if (url) {
                mui.openWindow(url)
            }
        })

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
                mui.post(homePath + '/wx/admin/union/advice/show/apply.json', {
//                    action: 'apply',
                    status: status,
                    id: applyId,
                    auditIdea: idea
                }, function (res) {
                    loading.hide()
                    if (res.ok) {
                        ui.showToast('审核成功！', function () {
                            window.location.reload(true);
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
