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
                <label class="ui-li-label ui-text-info">姓名</label>
                <span id="name">${fraternity.name}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">性别</label>
                    <span>
                        <c:choose>
                            <c:when test="${fraternity.sex == 1}">
                                男
                            </c:when>
                            <c:otherwise>女</c:otherwise>
                        </c:choose>
                    </span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">出生日期</label>
                <span><fmt:formatDate value="${fraternity.birth}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">出生地点</label>
                <span>${fraternity.address}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">民 族</label>
                <span>${fraternity.nation}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">工资</label>
                <span>${fraternity.monthly}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">联系电话</label>
                <span>${fraternity.mobile}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">身份证号</label>
                <span>${fraternity.idcard}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">岗位级别</label>
                <span>${fraternity.workLevel}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">工作单位</label>
                <span>${fraternity.work}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">职位</label>
                <span>${fraternity.position}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">岗位级别</label>
                <span>${fraternity.workLevel}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">入职时间</label>
                <span><fmt:formatDate value="${fraternity.workTime}"
                                      pattern="yyyy-MM-dd"></fmt:formatDate></span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">员工编号</label>
                <span>${fraternity.employeeId}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">所在部门</label>
                <span>${fraternity.deptName}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">申请时间</label>
                <span><fmt:formatDate value="${fraternity.applyTime}"
                                      pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">个人简历</label>
                <span>
                    <c:choose>
                        <c:when test="${fraternity.resume != null}">
                            ${fraternity.resume}
                        </c:when>
                        <c:otherwise>无</c:otherwise>
                    </c:choose>
                </span>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-5">
            <li class="mui-table-view-cell mui-text-center" style="margin-bottom: 5px;">
                家庭主要成员及其工作单位
            </li>
        </ul>

        <c:forEach items="${families}" var="item">
            <ul class="mui-table-view ui-margin-top-5">
                <li class="mui-table-view-cell">
                    <label class="ui-li-label ui-text-info">姓名</label>
                    <span>${item.name}</span>
                </li>
                <li class="mui-table-view-cell">
                    <label class="ui-li-label ui-text-info">关系</label>
                    <span>${item.relation}</span>
                </li>
                <li class="mui-table-view-cell">
                    <label class="ui-li-label ui-text-info">工作单位</label>
                    <span>${item.workUnit}</span>
                </li>
                <li class="mui-table-view-cell">
                    <label class="ui-li-label ui-text-info">工作职务</label>
                    <span>${item.position}</span>
                </li>
                <li class="mui-table-view-cell">
                    <label class="ui-li-label ui-text-info">月收入</label>
                    <span>${item.monthly}</span>
                </li>
                <li class="mui-table-view-cell">
                    <label class="ui-li-label ui-text-info">备注</label>
                    <span>${item.remark}</span>
                </li>
            </ul>
        </c:forEach>


        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell mui-text-center">
                管理员操作
            </li>
            <li class="mui-table-view-cell">

                审核意见
                <textarea id="idea"></textarea>

            </li>
            <li class="mui-table-view-cell">
                <p class="mui-button-row mui-text-center">
                    <button class="mui-btn mui-btn-green mui-left mui-pull-left shenhe" value="1"
                            itemId="${fraternity.id}">通过
                    </button>

                    <button class="mui-btn mui-btn-danger mui-right mui-pull-right shenhe" value="2"
                            itemId="${fraternity.id}">不通过
                    </button>
                </p>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script type="text/javascript">
    mui.init()
    mui('.ui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
</script>
<script>
    var homePath = '${home}';
    $(function () {
        if ('${thisStatus}' * 1 != 0) {
            mui.alert("该数据已不存在或被其他管理员审核", function () {
                mui.openWindow(homePath + '/wx/admin/common/fraternity/index.html');
            });
        }
        $(document).ready(function () {
            $("#a-back", parent.document).show();
        });
        $("button.shenhe").click(function () {
            var id = $(this).attr("itemId");
            var status = $(this).attr("value");
            var auditIdea = $("#idea").val();
            if (status == 2) {

            } else {
                if (!status || !auditIdea) {
                    mui.alert("请把信息填写完整", "提示", "确定", function () {

                    });
                    return;
                }
            }


            $.ajax({
                dataType: "json",
                url: '${home}/wx/admin/common/fraternity/shenhe.json',
                data: {
//                    action: 'shenhe',
                    id: id,
                    status: status,
                    auditIdea: auditIdea
                },
                success: function (result) {
                    dealData(result);
                }
            });

            function dealData(result) {
                if ("success" == result.msg) {
                    mui.toast("审核成功");
                    window.location.href = "${home}/wx/admin/common/fraternity/index.html";
                } else {
                    mui.toast("审核失败");
                }
            }
        });
    });
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
