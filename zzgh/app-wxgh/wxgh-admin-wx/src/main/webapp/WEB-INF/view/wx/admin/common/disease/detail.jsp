<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/9
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/app.css" rel="stylesheet"/>
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }

        .mui-preview-image.mui-fullscreen {
            position: fixed;
            z-index: 20;
            background-color: #000;
        }

        .mui-preview-header,
        .mui-preview-footer {
            position: absolute;
            width: 100%;
            left: 0;
            z-index: 10;
        }

        .mui-preview-header {
            height: 44px;
            top: 0;
        }

        .mui-preview-footer {
            height: 50px;
            bottom: 0px;
        }

        .mui-preview-header .mui-preview-indicator {
            display: block;
            line-height: 25px;
            color: #fff;
            text-align: center;
            margin: 15px auto 4px;
            width: 70px;
            background-color: rgba(0, 0, 0, 0.4);
            border-radius: 12px;
            font-size: 16px;
        }

        .mui-preview-image {
            display: none;
            -webkit-animation-duration: 0.5s;
            animation-duration: 0.5s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
        }

        .mui-preview-image.mui-preview-in {
            -webkit-animation-name: fadeIn;
            animation-name: fadeIn;
        }

        .mui-preview-image.mui-preview-out {
            background: none;
            -webkit-animation-name: fadeOut;
            animation-name: fadeOut;
        }

        .mui-preview-image.mui-preview-out .mui-preview-header,
        .mui-preview-image.mui-preview-out .mui-preview-footer {
            display: none;
        }

        .mui-zoom-scroller {
            position: absolute;
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
            -webkit-box-align: center;
            -webkit-align-items: center;
            align-items: center;
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            left: 0;
            right: 0;
            bottom: 0;
            top: 0;
            width: 100%;
            height: 100%;
            margin: 0;
            -webkit-backface-visibility: hidden;
        }

        .mui-zoom {
            -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            width: auto;
            height: auto;
            max-width: 100%;
            max-height: 100%;
        }

        .mui-android-4-1 .mui-slider .mui-slider-group .mui-slider-item img {
            width: 100%;
        }

        .mui-android-4-1 .mui-slider.mui-preview-image .mui-slider-group .mui-slider-item {
            display: inline-table;
        }

        .mui-android-4-1 .mui-slider.mui-preview-image .mui-zoom-scroller img {
            display: table-cell;
            vertical-align: middle;
        }

        .mui-preview-loading {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            display: none;
        }

        .mui-preview-loading.mui-active {
            display: block;
        }

        .mui-preview-loading .mui-spinner-white {
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -25px;
            margin-top: -25px;
            height: 50px;
            width: 50px;
        }

        .mui-preview-image img.mui-transitioning {
            -webkit-transition: -webkit-transform 0.5s ease, opacity 0.5s ease;
            transition: transform 0.5s ease, opacity 0.5s ease;
        }

        @-webkit-keyframes fadeIn {
            0% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }

        @keyframes fadeIn {
            0% {
                opacity: 0;
            }
            100% {
                opacity: 1;
            }
        }

        @-webkit-keyframes fadeOut {
            0% {
                opacity: 1;
            }
            100% {
                opacity: 0;
            }
        }

        @keyframes fadeOut {
            0% {
                opacity: 1;
            }
            100% {
                opacity: 0;
            }
        }

        p img {
            max-width: 100%;
            height: auto;
        }

        #hideBtn {
            display: none;
            position: absolute;
            right: 20px;
            top: 12px;
            color: #007aff;
        }

        #remark {
            word-break: break-all;
        }

        .ui-scroll-wrapper {
            top: 0px;
            bottom: 0px;
        }
    </style>
</head>

<body>


<div class="mui-scroll-wrapper ui-scroll-wrapper" style="padding-top: 0px;">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">资助金类别</label>
                <span id="name">${info.category}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">姓名</label>
                <span>${info.name}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">联系电话</label>
                <span>${info.mobile}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">申请资金</label>
                <span>${info.applyMoney}</span>
            </li>
            <li class="mui-table-view-cell" style="position: relative;">
                <label class="ui-li-label ui-text-info">备注信息</label>

                <div id="remark" style="word-break: break-all;">${info.remark}</div>
                <small id="hideBtn" style="">展开全文</small>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">申请时间</label>
                <span><fmt:formatDate value="${info.applyTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
            </li>
        </ul>

        <c:choose>
            <c:when test="${type == 'jb'}">

                <%--疾病--%>
                <ul class="mui-table-view" id="jb">
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">医院名称</label>
                        <span>${jb.hospital}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">就诊时间</label>
                        <span><fmt:formatDate value="${jb.seeTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">疾病类别名称</label>
                        <span>${jb.categoryName}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">添加时间</label>
                        <span><fmt:formatDate value="${jb.addTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">附件</label>
                        <c:forEach items="${files}" var="item">
                            <img src="${item.savePath}" style="width:100%;" data-preview-src="" data-preview-group="1">
                        </c:forEach>
                    </li>
                </ul>
            </c:when>
            <c:when test="${type == 'jy'}">

                <%--教育--%>
                <c:forEach items="${jy}" var="item">
                    <ul class="mui-table-view ui-margin-top-10">
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">关系</label>
                            <span>${item.relation}</span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">姓名</label>
                            <span>${item.name}</span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">出生日期</label>
                <span><fmt:formatDate value="${item.birth}"
                                      pattern="yyyy-MM-dd"></fmt:formatDate></span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">就读学校</label>
                            <span>${item.school}</span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">班级</label>
                            <span>${item.grade}</span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">学制</label>
                            <span>${item.xuezhi}</span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">学费</label>
                            <span>${item.tuition}</span>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">申请原因</label>

                            <div style="word-break: break-all;">${item.reason}</div>
                        </li>
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">添加时间</label>
                        <span><fmt:formatDate value="${item.addTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                        </li>
                    </ul>
                </c:forEach>
            </c:when>
            <c:when test="${type == 'qs'}">

                <%--去世--%>
                <ul class="mui-table-view" id="qs">
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">去世时间</label>
                        <span><fmt:formatDate value="${qs.happenTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">介绍</label>
                        <span>${qs.info}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">添加时间</label>
                <span><fmt:formatDate value="${qs.addTime}"
                                      pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">附件</label>
                        <c:forEach items="${files}" var="item">
                            <img src="${item.savePath}" style="width:100%;" data-preview-src="" data-preview-group="1">
                        </c:forEach>
                    </li>
                </ul>
            </c:when>
            <c:when test="${type == 'zc'}">
                <%--自残--%>
                <ul class="mui-table-view" id="zc">
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">发生时间</label>
                        <span><fmt:formatDate value="${zc.happenTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">等级</label>
                        <span>${zc.level} </span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">介绍</label>
                        <span>${zc.info}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">认证机构</label>
                        <span>${zc.dentification}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">添加时间</label>
                <span><fmt:formatDate value="${zc.addTime}"
                                      pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">附件</label>
                        <c:forEach items="${files}" var="item">
                            <img src="${item.savePath}" style="width:100%;">
                        </c:forEach>
                    </li>
                </ul>

            </c:when>
            <c:when test="${type == 'zh'}">
                <%--灾害--%>
                <ul class="mui-table-view" id="zh">
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">发生时间</label>
                        <span><fmt:formatDate value="${zh.happenTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">灾害等级</label>
                        <span>${zh.level}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">介绍</label>
                        <span>${zh.info}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">添加时间</label>
                <span><fmt:formatDate value="${zh.addTime}"
                                      pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">附件</label>
                        <c:forEach items="${files}" var="item">
                            <img src="${item.savePath}" style="width:100%;">
                        </c:forEach>
                    </li>
                </ul>

            </c:when>
            <c:when test="${type == 'zx'}">
                <%--直系--%>
                <ul class="mui-table-view" id="zx">
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">去世时间</label>
                        <span><fmt:formatDate value="${zx.happenTime}"
                                              pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">介绍</label>
                        <span>${zx.info}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">添加时间</label>
                <span><fmt:formatDate value="${zx.addTime}"
                                      pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">姓名</label>
                        <span>${zx.name}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">亲属关系</label>
                        <span>${zx.relation}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">附件</label>
                        <c:forEach items="${files}" var="item">
                            <img src="${item.savePath}" style="width:100%;">
                        </c:forEach>
                    </li>
                </ul>
            </c:when>
            <c:when test="${type == 'pk'}">
                <%--困难家庭资助--%>
                <ul class="mui-table-view" id="zx">
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">资助类别</label>
                        <span>${pk.pkCate}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">介绍</label>
                        <span>${pk.info}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">添加时间</label>
                <span><fmt:formatDate value="${pk.addTime}"
                                      pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">本人年收入</label>
                        <span>${pk.ownerIncome}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">家庭年收入</label>
                        <span>${pk.familyIncome}</span>
                    </li>
                    <li class="mui-table-view-cell">
                        <label class="ui-li-label ui-text-info">附件</label>
                        <c:forEach items="${files}" var="item">
                            <br><img src="${item.savePath}" style="width:100%;" data-preview-src=""
                                     data-preview-group="1">
                        </c:forEach>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>

        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell mui-text-center">
                管理员操作
            </li>
            <li class="mui-table-view-cell mui-text-center" style="display: flex;">
                <label style="width: 100px;padding:7px 5px 0 0;">审批金额</label>
                <input id="apply-money" type="text" placeholder="请输入数字金额，如99">
            </li>
            <li class="mui-table-view-cell mui-text-center">
                <label>审核说明</label>
                <textarea id="apply-idea" placeholder="请输入审核意见"></textarea>
            </li>
            <li class="mui-table-view-cell">
                <p class="mui-button-row mui-text-center">
                    <button class="mui-btn mui-btn-green mui-left mui-pull-left shenhe" value="1" itemId="${info.id}">
                        通过
                    </button>

                    <button class="mui-btn mui-btn-danger mui-right mui-pull-right shenhe" value="2"
                            itemId="${info.id}">不通过
                    </button>
                </p>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script type="text/javascript">
    mui.init();
    wxgh.previewImageInit();
    mui('.ui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
</script>
<script>
    var homePath = '${home}';
    $(function () {
        if ('${thisStatus}' * 1 != 0) {
            mui.alert("该数据已不存在或被其他管理员审核", function () {
                mui.openWindow(homePath + '/wx/admin/common/disease/index.html');
            });
        }
        hideRemarkEvent();

        $(document).ready(function () {
            $("#a-back", parent.document).show();
            $("div.mui-imageviewer").hide();
        });

        $("button.shenhe").click(function () {
            var id = $(this).attr("itemId");
            var status = $(this).attr("value");
            var money = $("#apply-money").val();
            var idea = $("#apply-idea").val();

            if (status == 2) {

            } else {
                if (!status || !money || !idea || isNaN(money)) {
                    mui.alert("请把信息填写完整", "提示", "确定", function () {

                    });
                    return;
                }
            }


            $.ajax({
                dataType: "json",
                url: '${home}/wx/admin/common/disease/shenhe.json',
                data: {
//                    action: 'shenhe',
                    id: id,
                    status: status,
                    auditIdea: idea,
                    auditMoney: money
                },
                success: function (result) {
                    dealData(result);
                }
            });

            function dealData(result) {
                if ("success" == result.msg) {
                    mui.toast("审核成功");
                    window.location.href = "${home}/wx/admin/common/disease/index.html";
                } else {
                    mui.toast("审核失败");
                }
            }
        });
    });

    function hideRemarkEvent() {
        var $remark = $('#remark');
        var $hide = $('#hideBtn');

        var height = $remark.height();
        if (height > 200) {
            $remark.css('height', '190px').css('overflow', 'hidden');
            $hide.show();
        }

        $hide.click(function () {
            if ($(this).text() == '展开全文') {
                $remark.css('height', 'auto');
                $(this).text('收起文章');
            } else {
                $remark.css('height', '190px').css('overflow', 'hidden');
                $(this).text('展开全文');
            }
        });
    }
</script>

<script type="text/javascript ">
    mui.init()
    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
        s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

        return Y + M + D + h + m + s;
    }
    $(document).ready(function () {
        //时间选择
        var selectTime = {
            init: function () {
                var self = $("#selectTime");
                //self.addEventListener("tap", selectTime.click);
                self.click(selectTime.click);

                selectTime.input = $("#selectTime");
                selectTime.self = self;

                var nowDate = new Date();
                selectTime.picker = new mui.DtPicker({
                    type: 'date',
                    beginYear: nowDate.getFullYear() - 90,
                    endYear: nowDate.getFullYear(),
                    value: nowDate.format('yyyy-MM-dd')
                });
            },
            click: function (e) {
                selectTime.picker.show(function (rs) {
                    var txt = rs.text;
                    selectTime.self.value = txt;
                    $("#selectTime").val(txt);
                    //page.curTime = txt //设置当前时间
                    if (txt) {
                        selectTime.self.innerText = txt;
                        $('#historyTitle').text(txt)
                        txt = txt.replace(/-/g, '')
                    }
                });
            }
        };
        selectTime.init();

    });
</script>
</body>

</html>
