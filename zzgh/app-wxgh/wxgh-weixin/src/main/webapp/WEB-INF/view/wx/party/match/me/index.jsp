<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/7
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的作品</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-table-view-cell.mui-active {
            background-color: #fff;
        }

        .mui-table-view:first-child .mui-table-view-cell:first-child {
            padding: 5px 15px;
        }

        .ui-img-div {
            height: 120px;
        }

        .ui-img-div img {
            height: 100%;
            width: auto;
        }

        .mui-table-view-cell > a > .mui-icon {
            position: absolute;
            top: 2px;
            right: -2px;
            color: indianred;
            background-color: #fff;
            border-radius: 50%;
        }

        .mui-table-view-cell.mui-active > a > .mui-icon {
            color: darkred;
        }

        button.mui-btn .mui-icon {
            font-size: 18px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            ${join.name}
        </li>
        <li class="mui-table-view-cell">
            <p id="meRemark" data-cnt="${join.remark}" style="min-height: 40px;">
            </p>
            <div class="mui-loading">
                <div class="mui-spinner"></div>
            </div>
        </li>
    </ul>

    <h5 class="ui-h5-title ui-margin-top-10">作品列表</h5>
    <ul class="mui-table-view mui-grid-view">
        <c:choose>
            <c:when test="${empty files}">
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">您未上传作品哦</div>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach items="${files}" var="f">
                    <li class="mui-table-view-cell mui-media mui-col-xs-6">
                        <a href="#">
                            <div class="ui-img-div">
                                <img data-preview-src="${home}${f.path}" data-preview-group="img" src="${home}${f.thumb}">
                            </div>
                            <span data-id="${f.id}" class="mui-icon mui-icon-close-filled mui-hidden"></span>
                        </a>
                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </ul>

    <div class="ui-bottom-btn-group">
        <button id="editBtn" type="button" class="mui-btn mui-btn-outlined mui-btn-danger ui-btn">
            <span class="mui-icon mui-icon-compose"></span>
            编辑作品
        </button>
        <button id="addBtn" type="button" class="mui-btn mui-btn-outlined mui-btn-primary ui-btn">
            <span class="mui-icon mui-icon-plus"></span>
            新增上传
        </button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script>
    $(function () {
        var _self = homePath + '/wx/party/match/me'
        var joinId = '${param.id}'
        var joinType = '${join.type}'

        var $cnt = $('#meRemark')
        var cnt = $cnt.data('cnt')
        if (cnt) {
            cnt = decodeURIComponent(cnt)
        } else {
            cnt = '暂无简介'
        }
        $cnt.next('.mui-loading').remove()
        $cnt.text(cnt)

        wxgh.previewImageInit()

        $('.mui-table-view-cell .mui-icon-close-filled').on('tap', function () {
            var cf = confirm('是否删除？')
            if (cf) {
                var $self = $(this)
                var id = $self.data('id')
                mui.getJSON(_self + "/delete.json", {action: 'delete', id: id}, function (res) {
                    if (res.ok) {
                        ui.showToast('删除成功~', function () {
                            $self.parent().parent().remove()
                        })
                    } else {
                        alert('删除失败！')
                    }
                })
            }
        })

        //编辑
        $('#editBtn').on('tap', function () {
            var $self = $(this)
            if ($self.text().trim() == '编辑作品') {
                $('.mui-table-view-cell .mui-icon-close-filled').removeClass('mui-hidden')
                $self.text('取消编辑')
            } else {
                $('.mui-table-view-cell .mui-icon-close-filled').addClass('mui-hidden')
                $self.html('<span class="mui-icon mui-icon-compose"></span> 编辑作品')
            }
        })

        //新增上传
        $('#addBtn').on('tap', function () {
            mui.openWindow(homePath + '/wx/party/match/add.html?id=' + joinId + '&joinType=' + joinType)
        })
    })
</script>
</body>
</html>
