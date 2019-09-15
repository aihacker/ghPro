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
    <title>台账汇总</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body, .mui-content {
            /*background-color: #fff;*/
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
            z-index:100
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .mui-search {
            margin: 22px 12px -6px 12px;
        }

        #selectCompany, #selectMarketing {
            position: absolute;
            width: 100px;
            right: 0px;
            background: transparent;
            color: #fff;
        }

        #selectMarketing {
            width: 120px;
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
    </style>
</head>

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">台账汇总</h1>

    <%--<c:if test="${!empty param.deptId}">--%>
        <%--<select id="selectCompany">--%>
            <%--<option value="0">选择公司</option>--%>
            <%--<c:forEach items="${depts}" var="d">--%>
                <%--<option value="${d.deptid}">${d.name}</option>--%>
            <%--</c:forEach>--%>
        <%--</select>--%>
    <%--</c:if>--%>
    <%--<c:if test="${!empty param.mid}">--%>
        <%--<select id="selectMarketing">--%>
            <%--<option value="0">选择营销中心</option>--%>
            <%--<c:forEach items="${markets}" var="d">--%>
                <%--<option value="${d.mid}">${d.name}</option>--%>
            <%--</c:forEach>--%>
        <%--</select>--%>
    <%--</c:if>--%>
</header>

<div id="totalPriceDiv">
    <div class="mui-row">
        <div class="mui-col-sm-6 mui-col-xs-6">
            <span style="margin-top: 10px;" id="totalPrice">数量总计：${empty nums?0:nums}</span>
        </div>
    </div>
</div>

<div class="mui-scroll-wrapper" style="top: 44px;">
    <div class="mui-scroll">
        <ul class="mui-table-view" id="ul-collecting">
            <li class="mui-table-view-cell" id="li-title">
                <select id="groupSelect" style="width: 65%;">
                    <option value="1">名称</option>
                    <option value="2">品牌</option>
                    <option value="3">设备情况</option>
                    <option value="4">资产所属</option>
                </select>
                <label id="title-name" class="mui-text-center ui-li-label" style="width: 65%;font-weight: 600 !important;">名称</label>
                <span style="font-weight: 600;">数量</span>

                <span class="mui-icon mui-icon-arrowdown" style="position: absolute;left:42%;top:20px;"></span>
            </li>
            <c:choose>
                <c:when test="${empty infos}">
                    <li class="mui-table-view-cell">
                        <div class="mui-text-center">暂无结果</div>
                    </li>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${infos}" var="info">
                        <li class="mui-table-view-cell">
                            <a href="${home}/wx/four/details_item2.html?deptId=${param.deptId}&mid=${param.mid}&type=1&typeName=${info.name}">
                                <label class="mui-text-left ui-li-label ui-text-info"
                                       style="width:65%;padding-left: 10px;">${info.name}</label>
                                <span>${info.numb}</span>
                            </a>
                        </li>
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
    var fpId = '${param.fpID}'

    var type = 1;

    $(document).ready(function () {

        $('#selectCompany').on('change', function () {
            var val = $(this).val();
            if (val != 0) {
                deptId = val;
            } else {
                deptId = '${param.deptId}';
            }
            get_list(type);
        })

        $('#selectMarketing').on('change', function () {
            var val = $(this).val();
            if (val != 0) {
                mid = val;
            } else {
                mid = '${param.mid}'
            }
            get_list(type);
        })

        $('#groupSelect').on('change', function () {
            var val = $(this).val();
            var text = $("#groupSelect").find("option:selected").text()
            $("#title-name").html(text)
            type = val;
//            wxgh.setCookie("four-type", type, 60*60);
            get_list(type);
        })

//        if(wxgh.getCookie("four-type") != null)
//            get_list(wxgh.getCookie("four-type"));

        function get_list(val) {

            if (!this.loading) {
                this.loading = ui.loading('加载中...');
            }
            this.loading.show();
            var self = this;

            var url = ""
            if (deptId) {
                url = homePath + '/wx/four/collecting.json?deptId=' + deptId + '&type=' + val;
            } else if (mid) {
                if (fpId) {
                    url = homePath + '/wx/four/collecting.json?mid=' + mid + '&fpID=' + fpId + '&type=' + val;
                } else {
                    url = homePath + '/wx/four/collecting.json?mid=' + mid + '&type=' + val;
                }
            } else {
                url = homePath + '/wx/four/collecting.json?foshanDeptid=' + foshanDeptid + '&type=' + val;
            }
            $.getJSON(url, function (res) {
                self.loading.hide();
                if (res.ok) {
                    $('#ul-collecting').find('li:not(:first)').remove()
                    var infos = res.data;
                    if (infos && infos.length > 0) {
                        for (var i = 0; i < infos.length; i++) {
                            //' + homePath + '/four/details_item2.html?type=' + val + '&deptId=' + deptId + '&mid=+' + mid + '&name=' + infos[i].name + '
                            $('#ul-collecting').append('<li class="mui-table-view-cell">' +
                                '<a href="' + homePath + '/wx/four/details_item2.html?type=' + val + '&deptId=' + deptId + '&mid=' + mid + '&typeName=' + infos[i].name + '">' +
                                '<label class="mui-text-left ui-li-label ui-text-info" style="width:65%;padding-left: 10px;">' + infos[i].name + '</label>' +
                                '<span>' + infos[i].numb + '</span></a></li>');
                        }
                    } else {
                        $('#ul-collecting').append('<li class="mui-table-view-cell"><div class="mui-text-center">暂无结果</div></li>')
                    }
                }
            })
        }
    });

</script>

</body>

</html>
