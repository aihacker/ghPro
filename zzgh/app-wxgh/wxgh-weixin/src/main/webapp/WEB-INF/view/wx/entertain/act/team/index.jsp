<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/19
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css">
    <style>
        html,
        body {
            background-color: #efeff4;
        }

        .mui-views,
        .mui-view,
        .mui-pages,
        .mui-page,
        .mui-page-content {
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
            background-color: #efeff4;
        }

        .mui-pages {
            height: auto;
        }

        .mui-scroll-wrapper,
        .mui-scroll {
            background-color: #efeff4;
        }

        .mui-page.mui-transitioning {
            -webkit-transition: -webkit-transform 300ms ease;
            transition: transform 300ms ease;
        }

        .mui-page-left {
            -webkit-transform: translate3d(0, 0, 0);
            transform: translate3d(0, 0, 0);
        }

        .mui-ios .mui-page-left {
            -webkit-transform: translate3d(-20%, 0, 0);
            transform: translate3d(-20%, 0, 0);
        }

        .mui-page-shadow {
            position: absolute;
            right: 100%;
            top: 0;
            width: 16px;
            height: 100%;
            z-index: -1;
            content: '';
        }

        .mui-page-shadow {
            background: -webkit-linear-gradient(left, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0) 10%, rgba(0, 0, 0, .01) 50%, rgba(0, 0, 0, .2) 100%);
            background: linear-gradient(to right, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0) 10%, rgba(0, 0, 0, .01) 50%, rgba(0, 0, 0, .2) 100%);
        }

        .mui-page {
            display: none;
        }

        .mui-pages .mui-page {
            display: block;
        }
    </style>
    <style>
        .bg-img {
            background-color: #fff;
            padding: 10px 10px 0 10px;
        }

        .bg-img img {
            width: 100%;
            background-color: #fff;
            padding: 10px 10px 0 10px;
            max-height: 150px;
            min-height: 140px;
        }

        .mui-table-view-cell > a:not(.mui-btn) {
            margin: -14px -15px;
        }

        .mui-table-view:after,
        .mui-table-view:before,
        .mui-input-group:after,
        .mui-input-group:before {
            height: 0;
        }

        #mapDiv {
            width: 100%;
            height: 200px;
        }

        .mui-slider-group .mui-control-content {
            background-color: #fff;
        }

        .mui-scroll .mui-loading {
            padding: 10px;
        }

        #baiduSearchInput {
            margin-bottom: 0;
        }

        .score-rule p > span {
            margin: 0 8px;
        }

        #addRulePopover form {
            padding: 10px;
        }

        #addRulePopover .mui-input-row > label {
            display: inline-block;
        }

        #addRulePopover .rule-add-btn {
            position: absolute;
            bottom: 0;
            width: 100%;
            padding: 0 10px;
            z-index: 4;
        }

        .rule-add-btn button {
            width: 100%;
            padding: 6px 0;
        }

    </style>
</head>
<body class="mui-fullscreen">

<div id="app" class="mui-views">
    <div class="mui-view">
        <div class="mui-pages">
        </div>
    </div>
</div>

<!-- 添加活动 -->
<div id="addActPage" class="mui-page">
    <div class="mui-page-content">
        <div class="ui-fixed-bottom">
            <button id="addActBtn" type="button" class="mui-btn mui-btn-primary">立即发布</button>
        </div>
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">

                <%--<div id="chooseImgBtn">--%>
                    <%--<div class="bg-img ui-img-div" id="actImgBtn">--%>
                        <%--<img src="${home}/image/common/icon_add1.png"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                    <div class="mui-row ui-choose-img ui-margin-top-15">
                        <div class="" id="chooseBtn">
                        </div>
                    </div>


                <ul class="mui-table-view ui-margin-top-10">
                    <li class="mui-table-view-cell no">
                        <a class="ui-flex">
                            <span>活动名称</span>
                            <input class="ui-right" name="name" type="text" placeholder="请输入"/>
                        </a>
                    </li>
                    <li class="mui-table-view-cell no">
                        <a class="ui-flex">
                            <span>联系电话</span>
                            <input class="ui-right" name="phone" type="text" value="${wxgh_user.mobile}"
                                   placeholder="请输入"/>
                        </a>
                    </li>
                </ul>

                <ul class="mui-table-view ui-margin-top-10">
                    <c:if test="${param.type eq 1}">
                        <li id="chooseWeekLi" class="mui-table-view-cell">
                            <a class="mui-navigate-right ui-flex">
                                <span>定期星期</span>
                                <small class="ui-right">请选择</small>
                                <input type="hidden" name="week">
                            </a>
                        </li>
                    </c:if>
                    <li class="mui-table-view-cell">
                        <a data-options="{}" class="mui-navigate-right ui-flex">
                            <span>开始时间</span>
                            <small class="ui-right">选择时间</small>
                            <input type="hidden" name="startTime">
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a data-options="{}" class="mui-navigate-right ui-flex">
                            <span>结束时间</span>
                            <small class="ui-right">选择时间</small>
                            <input type="hidden" name="endTime">
                        </a>
                    </li>
                </ul>

                <ul class="mui-table-view ui-margin-top-10">
                    <li class="mui-table-view-cell">
                        <a href="#pageBaidu" class="mui-navigate-right ui-flex">
                            <span>活动地点</span>
                            <small id="addressSmall" class="ui-right">选择地点</small>
                            <input type="hidden" name="address">
                            <input type="hidden" name="lat">
                            <input type="hidden" name="lng">
                            <input type="hidden" name="addrTitle">
                        </a>
                    </li>
                    <li class="mui-table-view-cell no">
                        <a>
                            <div class="ui-content">
                                <h5 class="ui-title">地址备注</h5>
                                <div class="ui-text-div">
                                    <textarea name="addrRemark" maxlength="50" rows="1"></textarea>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>

                <div class="mui-input-group ui-margin-top-10" style="padding: 0 15px;">
                    <label class="ui-input-div" style="line-height: 40px">是否推送</label>

                    <div class="mui-switch mui-pull-right"
                         style="display: inline-block;position: relative;top: 4px;">
                        <div class="mui-switch-handle"></div>
                    </div>
                    <input type="hidden" value="0" name="push"/>
                </div>
                <ul class="mui-table-view" id="remindUl">
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right ui-flex">
                            <span>推送提醒</span>
                            <small class="ui-right">不提醒</small>
                        </a>
                        <select name="remind">
                            <option value="0">不提醒</option>
                            <option value="0.5">提前30分钟</option>
                            <option value="1">提前1小时</option>
                            <option value="2">提前2小时</option>
                            <option value="12">提前12小时</option>
                            <option value="18">提前18小时</option>
                        </select>
                    </li>
                </ul>

                <ul class="mui-table-view ui-margin-top-10">
                    <li class="mui-table-view-cell no">
                        <a>
                            <div class="ui-content">
                                <h5 class="ui-title">活动介绍</h5>
                                <div class="ui-text-div">
                                    <textarea name="info" maxlength="500" rows="2"></textarea>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- 选择地址 -->
<div id="pageBaidu" class="mui-page">
    <div class="mui-page-content">
        <div class="mui-content-padded" id="searchDiv">
            <div class="mui-input-row mui-search">
                <input id="baiduSearchInput" type="search" placeholder="输入您要搜索的地址" class="mui-input-clear"/>
            </div>
            <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
        </div>
        <div id="mapDiv"></div>
        <div id="cateSlider" class="mui-slider">
            <div id="sliderSegmented" class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
                <a class="mui-control-item mui-active" href="#nearByItem">
                    附近
                </a>
                <a class="mui-control-item" href="#badmintonItem">
                    羽毛球
                </a>
                <a class="mui-control-item" href="#pingpongballItem">
                    乒乓球
                </a>
                <a class="mui-control-item" href="#basketballItem">
                    篮球
                </a>
            </div>
            <div class="mui-slider-group">
                <!-- 附近 -->
                <div id="nearByItem" class="mui-slider-item mui-control-content mui-active">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <div class="mui-loading">
                                <div class="mui-spinner"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 羽毛球 -->
                <div id="badmintonItem" class="mui-slider-item mui-control-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <div class="mui-loading">
                                <div class="mui-spinner"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 乒乓球 -->
                <div id="pingpongballItem" class="mui-slider-item mui-control-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <div class="mui-loading">
                                <div class="mui-spinner"></div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 篮球 -->
                <div id="basketballItem" class="mui-slider-item mui-control-content">
                    <div class="mui-scroll-wrapper">
                        <div class="mui-scroll">
                            <div class="mui-loading">
                                <div class="mui-spinner"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui-fixed-bottom">
            <button id="mapOkBtn" type="button" class="mui-btn mui-btn-primary">确定选择</button>
        </div>
    </div>
</div>


<c:if test="${param.type eq 1}">
    <script type="text/template" id="chooseTimeTmp">
        <div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周一</label>
                <input name="checkbox1" value="1" type="checkbox">
            </div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周二</label>
                <input name="checkbox1" value="2" type="checkbox">
            </div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周三</label>
                <input name="checkbox1" value="3" type="checkbox">
            </div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周四</label>
                <input name="checkbox1" value="4" type="checkbox">
            </div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周五</label>
                <input name="checkbox1" value="5" type="checkbox">
            </div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周六</label>
                <input name="checkbox1" value="6" type="checkbox">
            </div>
            <div class="mui-input-row mui-checkbox mui-right">
                <label>周日</label>
                <input name="checkbox1" value="7" type="checkbox">
            </div>
        </div>
    </script>
</c:if>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.view.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua"></script>
<script src="${wx}/script/union/act/act_baidu.js"></script>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    var addUrl = '/wx/entertain/act/team/add.json';
    var groupId = '${param.groupId}';
    var actType = '${param.type}';
    var subjectType = 1;
    mui.init({
        swipeBack: true
    })
    mui('.mui-scroll-wrapper').scroll();
    wxgh.init('${weixin}')
</script>
<script src="${wx}/script/union/act/add.js"></script>
<script type="text/javascript" src="${home}/comm/mobile/js/confirm.js"></script>
<script>
    $(function () {
        if (actType == 1) {
            var timeConfirm = new Confirm('选择时间', $('#chooseTimeTmp').html(), function ($dialog) {
                var weeks = [], weekSts = []
                $dialog.find('input:checkbox[name=checkbox1]:checked').each(function () {
                    weeks.push($(this).val())
                    weekSts.push($(this).prev().text())
                })
                var $input = $('input[name=week]')
                $input.prev('small').text(weekSts.toString())
                $input.val(weeks.toString())
            })
            $('#chooseWeekLi').on('tap', function () {
                timeConfirm.show()
            })
        }
    })
</script>
</body>
</html>
