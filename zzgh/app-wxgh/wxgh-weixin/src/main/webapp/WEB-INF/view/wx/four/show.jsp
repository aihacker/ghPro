<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/17
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${four.fpcName}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">

    <style>
        body {
            font-family: "微软雅黑";
        }

        .xlk-head {
            background: -webkit-gradient(linear, 0 0, 0 bottom, from(#08A8F4), to(#1088C0));
        }

        .xlk-head .mui-icon-left-nav {
            color: #fff;
        }

        .xlk-head .mui-title {
            color: #fff;
        }

        body {
            background-color: #fff;
        }

        .mui-content {
            background-color: #fff;
        }

        .item-div span:first-child {
        }

        .item-div span:last-child {
            color: #8f8f94;
            font-size: 14px;
        }

        .mui-table-view-cell {
            padding-left: 20px;
            padding-right: 20px;
        }

        .dept-name {
            padding: 20px 0px;
        }

        .dept-name img {
            width: 25px;
            height: 25px;
            margin-right: 10px;
        }

        .beizhu-li {
            padding: 12px 20px;
            color: #000;
            position: relative;
        }

        .beizhu-li a {
            color: #000;
        }

        .beizhu-li .item-div span:first-child {
            margin-top: 40px;
        }

        .beizhu-li .item-div span:last-child {
            left: 100px;
            position: absolute;
            top: 12px;
            display: block;
            padding-right: 20px;
        }

        .ui-img-div {
            display: -webkit-flex;
            display: flex;
            justify-content: center;
            overflow: hidden;
            align-items: center;
            /*max-height: 200px;*/
        }
    </style>
</head>
<body>

<header class="mui-bar mui-bar-nav xlk-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">${four.fpcName}</h1>
</header>

<div class="mui-content">
    <div class="dept-name mui-text-center">
        <img src="${home}/weixin/image/four/four-dept.png"/> ${four.deptName}
    </div>
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>设施项目</span>
                <span class="mui-pull-right">${four.fpcName}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>四小项目</span>
                <span class="mui-pull-right">${four.fpName}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <c:choose>
                    <c:when test="${type eq 1}">
                        <span>费用建议</span>
                        <span class="mui-pull-right">${four.suggest}</span>
                    </c:when>
                    <c:otherwise>
                        <span>设备情况</span>
                        <span class="mui-pull-right">${four.condit}</span>
                    </c:otherwise>
                </c:choose>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>数量</span>
                <span class="mui-pull-right">${four.numb}${four.unit}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>型号</span>
                <span class="mui-pull-right">${four.modelName}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>品牌</span>
                <span class="mui-pull-right">${four.brand}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>单价</span>
                <span class="mui-pull-right">${four.price} 元/${four.unit}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>资金来源</span>
                <span class="mui-pull-right">${empty four.priceSource?'未知':four.priceSource}</span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>计划更换时间</span>
                 <span class="mui-pull-right">
                <c:choose>
                    <c:when test="${four.panUpdateStr == null}">
                        暂无
                    </c:when>
                    <c:otherwise>
                        ${four.panUpdateStr} 年
                    </c:otherwise>
                </c:choose>
                     </span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>使用年限</span>
                 <span class="mui-pull-right">
                <c:choose>
                    <c:when test="${four.usefulLife == null || four.usefulLife == ''}">
                        暂无
                    </c:when>
                    <c:otherwise>
                        ${four.usefulLife}年
                    </c:otherwise>
                </c:choose>
                     </span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>维修次数</span>
                 <span class="mui-pull-right">
                <c:choose>
                    <c:when test="${four.repairCount == null || four.repairCount == ''}">
                        暂无
                    </c:when>
                    <c:otherwise>
                        ${four.repairCount}年
                    </c:otherwise>
                </c:choose>
                     </span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <c:choose>
                    <c:when test="${type eq 1}">
                        <span>预算</span>
                        <span class="mui-pull-right">${four.budget}</span>
                    </c:when>
                    <c:otherwise>
                        <span>采购时间</span>
                        <span class="mui-pull-right">${four.buyTime}</span>
                    </c:otherwise>
                </c:choose>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="item-div">
                <span>台账图片</span>
                <c:choose>
                    <c:when test="${imgList[0] == null || imgList[0] == ''}">
                        <span class="mui-pull-right">暂无</span>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${imgList}" var="item" step="1" varStatus="k">
                            <br>

                            <div class="ui-img-div">
                                <img data-preview-src="${home}${item}" data-preview-group="fujian"
                                     src="${home}${thumbList[k.index]}" class="mui-media-object"
                                     style="width: 100%;">
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </a>
        </li>
    </ul>
    <div class="beizhu-li">
        <a class="item-div">
            <span>备注说明</span>
            <span class="mui-pull-right">${empty four.remark?'暂无备注信息':four.remark}</span>
        </a>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>

<script type="text/javascript">
    mui.init()

    wxgh.previewImageInit();
</script>
</body>
</html>
