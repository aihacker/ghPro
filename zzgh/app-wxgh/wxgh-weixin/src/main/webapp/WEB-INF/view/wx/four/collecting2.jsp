<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>需要更换的设备台账汇总</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body, .mui-content {
            /*background-color: #fff;*/
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .mui-search {
            margin: 22px 12px -6px 12px;
        }

        #groupSelect {
            margin-bottom: 0px;
            padding: 0;
        }

        #mainScroll {
            top: 84px;
            bottom: 45px;
        }

        #mysegmented {
            top: 44px;
        }

        #totalPriceDiv {
            width: 100%;
            height: 45px;
            position: absolute;
            bottom: 0px;
            text-align: right;
            padding-left: 10px;
            padding-right: 10px;
            background-color: #08A8F4;
            color: #fff;
        }

        #ul-collecting .mui-table-view-cell {
            padding: 5px 4px;
            text-align: center;
        }

        #ul-collecting .ui-remark .mui-table-view-cell {
            text-align: left;
            padding-left: 10px;
            color: #777;
        }

        #ul-collecting .mui-row {
            border-bottom: 1px solid gainsboro;
        }

        #ul-collecting .ui-remark .mui-table-view-cell label {
            display: inline-block;
        }

        #ul-collecting .ui-remark .mui-table-view-cell span {
            display: inline-block;
        }

        #totalPriceDiv div[class^=mui-col-] {
            text-align: center;
            color: #fff;
        }

        #totalPriceDiv small {
            position: relative;
            top: -8px;
        }
        #totalPriceDiv span{
            display: block;
        }

        .mui-table-view-cell {
            position: relative;
            /* overflow: hidden; */
            padding: 11px 15px;
            -webkit-touch-callout: none;
        }
    </style>
</head>

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">需要更换的设备台账汇总</h1>
</header>

<div id="mysegmented"
     class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
    <div class="mui-scroll">
        <c:forEach items="${years}" var="y" varStatus="i">
            <c:choose>
                <c:when test="${year eq y}">
                    <a class="mui-control-item mui-active" href="javascript:;" data-year="${y}">${y}年</a>
                </c:when>
                <c:otherwise>
                    <a class="mui-control-item" href="javascript:;" data-year="${y}">${y}年</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</div>

<div id="totalPriceDiv">
    <div class="mui-row">
        <div class="mui-col-sm-3 mui-col-xs-3">
            <span>资本投资</span>
            <small id="zbPrice">${empty zbCount?0.0:zbCount}元</small>
        </div>
        <div class="mui-col-sm-3 mui-col-xs-3">
            <span>福利费</span>
            <small id="flPrice">${empty flCount?0.0:flCount}元</small>
        </div>
        <div class="mui-col-sm-3 mui-col-xs-3">
            <span>工会经费</span>
            <small id="ghPrice">${empty ghCount?0.0:ghCount}元</small>
        </div>
        <div class="mui-col-sm-3 mui-col-xs-3">
            <span>总 计</span>
            <small id="totalPrice">${empty fourCount?0.0:fourCount}元</small>
        </div>
    </div>
</div>

<div id="mainScroll" class="mui-scroll-wrapper">
    <div class="mui-scroll">

        <ul class="mui-table-view" id="ul-collecting">
            <div class="mui-row">
                <div class="mui-col-sm-3 mui-col-xs-3">
                    <li class="mui-table-view-cell">
                        <select id="groupSelect">
                            <option value="1">名称</option>
                            <option value="2">品牌</option>
                            <option value="3">设备情况</option>
                            <option value="4">资产所属</option>
                        </select>
                        <span style="position: absolute;top: 8px;right: 5px;z-index: 9;"
                              class="mui-icon mui-icon-arrowdown"></span>
                        <label id="title-name" class="mui-text-center ui-li-label" style="width: 65%;font-weight: 600 !important;">名称</label>
                    </li>
                </div>
                <div class="mui-col-sm-4 mui-col-xs-4">
                    <li class="mui-table-view-cell">
                        资金来源
                    </li>
                </div>
                <div class="mui-col-sm-3 mui-col-xs-3">
                    <li class="mui-table-view-cell">
                        预算单价
                        <small class="ui-text-info">(元)</small>
                    </li>
                </div>
                <div class="mui-col-sm-2 mui-col-xs-2">
                    <li class="mui-table-view-cell">
                        数量
                    </li>
                </div>
            </div>

            <c:choose>
                <c:when test="${empty infos}">
                    <div class="mui-row">
                        <div class="mui-col-sm-12 mui-col-xs-12">
                            <li class="mui-table-view-cell">
                                <div class="mui-text-center">暂无结果</div>
                            </li>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${infos}" var="info">
                        <%--<a href="${home}/four/repair.html?type=1&deptId=${param.deptId}&mid=${param.mid}&typeName=${info.name}&year=${year}">--%>
                        <div data-url="${home}/wx/four/repair.html?type=1&deptId=${param.deptId}&mid=${param.mid}&typeName=${info.name}&year=${year}"
                             class="mui-row">
                            <div class="mui-col-sm-3 mui-col-xs-3">
                                <li class="mui-table-view-cell">
                                        ${info.name}
                                </li>
                            </div>
                            <div class="mui-col-sm-4 mui-col-xs-4">
                                <li class="mui-table-view-cell">
                                        ${empty info.priceSource?'资本投资':info.priceSource}
                                </li>
                            </div>
                            <div class="mui-col-sm-3 mui-col-xs-3">
                                <li class="mui-table-view-cell">
                                        ${info.price}
                                </li>
                            </div>
                            <div class="mui-col-sm-2 mui-col-xs-2">
                                <li class="mui-table-view-cell">
                                        ${info.numb}
                                </li>
                            </div>
                            <div class="mui-col-sm-12 mui-col-xs-12 ui-remark">
                                <li class="mui-table-view-cell">
                                    <label><label>型号
                                        <small>(备注)</small>
                                        ：</label></label>
                                    <span>${info.modelName}<c:if test="${!empty info.remark}">
                                        <small>(${info.remark})</small>
                                        </c:if></span>
                                </li>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
    var homePath = '${home}'
    var deptId = '${param.deptId}'
    var mid = '${param.mid}';
    var foshanDeptid = '${param.foshanDeptid}'

    var type = 1;
    var year = '${year}';

    $(document).ready(function () {

        $('#mysegmented').on('tap', 'a', function () {
            year = $(this).attr('data-year');
            get_list(type);
        })

        $('#groupSelect').on('change', function () {
            var val = $(this).val();
            var text = $("#groupSelect").find("option:selected").text()
            $("#title-name").html(text)
            type = val;
            get_list(type);
        })

        $('#ul-collecting').on('click', '.mui-row:not(:first)', function () {
            mui.openWindow($(this).attr('data-url'));
        })

        function get_list(val) {

            if (!this.loading) {
                this.loading = ui.loading('加载中...')
            }
            this.loading.show();

            var url = ""
            if (deptId) {
                url = homePath + '/wx/four/collecting2.json?deptId=' + deptId + '&type=' + val;
            } else if (mid) {
                url = homePath + '/wx/four/collecting2.json?mid=' + mid + '&type=' + val;
            } else {
                url = homePath + '/wx/four/collecting2.json?foshanDeptid=' + foshanDeptid + '&type=' + val;
            }
            url += ('&year=' + year);

            var self = this;
            $.getJSON(url, function (res) {

                self.loading.hide();

                $('#ul-collecting').find('.mui-row:not(:first)').remove()
                var infos = res.infos;
                if (infos && infos.length > 0) {
                    for (var i = 0; i < infos.length; i++) {
                        var url = homePath + '/wx/four/repair.html?type=' + val + '&deptId=' + deptId + '&mid=' + mid + '&typeName=' + infos[i].name + '&year=' + year;

                        var remark = infos[i].remark;
                        var model = infos[i].modelName;
                        if (remark) {
                            model += ("<small>(" + remark + ")</small>")
                        }

                        var $row = $('<div data-url="' + url + '" class="mui-row"><div class="mui-col-sm-3 mui-col-xs-3"> <li class="mui-table-view-cell">'
                        + infos[i].name +
                        '</li></div><div class="mui-col-sm-4 mui-col-xs-4"><li class="mui-table-view-cell">'
                        + (infos[i].priceSource ? infos[i].priceSource : '资本投资') +
                        '</li></div><div class="mui-col-sm-3 mui-col-xs-3"><li class="mui-table-view-cell">'
                        + (infos[i].price == 0 ? '0.0' : infos[i].price) +
                        '</li></div><div class="mui-col-sm-2 mui-col-xs-2"><li class="mui-table-view-cell">'
                        + infos[i].numb +
                        '</li></div><div class="mui-col-sm-12 mui-col-xs-12 ui-remark">'
                        + '<li class="mui-table-view-cell"><label>型号<small>(备注)</small>：</label>'
                        + '<span>' + model + '</span></li></div></div>');
                        $('#ul-collecting').append($row);

                        $row.click(function () {
                            mui.openWindow($(this).attr('data-url'));
                        })
                    }
                } else {
                    $('#ul-collecting').append('<div class="mui-row"><div class="mui-col-sm-12 mui-col-xs-12"><li class="mui-table-view-cell"> <div class="mui-text-center">暂无结果</div></li></div></div>')
                }
                $('#totalPrice').text(res.fourCount ? res.fourCount : '0.0' + ' 元');
                $('#zbPrice').text(res.zbCount ? res.zbCount : '0.0' + ' 元');
                $('#flPrice').text(res.flCount ? res.flCount : '0.0' + ' 元');
                $('#ghPrice').text(res.ghCount ? res.ghCount : '0.0' + ' 元');
            })
        }
    });
</script>

</body>

</html>
