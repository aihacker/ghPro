<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>预约小屋</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css">
    <style>

    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="yuyueBtn" type="button" class="mui-btn mui-btn-primary">立即预约</button>
    </div>
    <ul class="mui-table-view" id="yuyue">
        <li class="mui-table-view-cell">
            <a class="ui-flex mui-navigate-right">
                <span>星期</span>
                <small class="ui-right">选择星期</small>
            </a>
            <select name="week">
                <option value="-1">选择星期</option>
                <c:forEach items="${weeks}" var="w">
                    <option data-dateid="${w.dateid}" value="${w.week}">${w.name}</option>
                </c:forEach>
            </select>
        </li>
        <li class="mui-table-view-cell time">
            <a class="ui-flex mui-navigate-right">
                <span>开始时间</span>
                <small class="ui-right">选择时间</small>
                <input type="hidden" name="start">
            </a>
        </li>
        <li class="mui-table-view-cell time">
            <a class="ui-flex mui-navigate-right">
                <span>结束时间</span>
                <small class="ui-right">选择时间</small>
                <input type="hidden" name="end">
            </a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script>
    var momId = '${param.id}';
    $(function () {
        wxgh.init();

        var load = ui.loading('预约中...');

        $('#yuyueBtn').on('tap', function () {
            var info = wxgh.serialize($('#yuyue')[0]);
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            info['id'] = momId;
            info['dateId'] = $('select[name=week]').find('option[value="' + info.week + '"]').data('dateid');
            load.show();
            wxgh.request.post('api/yuyue.json', info, function () {
                ui.showToast('预约成功~', function () {
                    mui.back();
                });
            });
        });

        var picker = new mui.DtPicker({type: 'time'});

        $('.time').on('tap', function () {
            var $self = $(this);
            picker.show(function (rs) {
                var $input = $self.find("input[name]").val(rs.text);
                $input.prev().text(rs.text);
            });
        });

        function verify(info) {
            if (info.week == -1) {
                return '请选择星期'
            }
            if (!info.start) {
                return '请选择开始时间'
            }
            if (!info.end) {
                return '请选择结束时间'
            }
            if (info.start >= info.end) {
                return '开始时间不能大于等于结束时间'
            }
        }
    })
</script>
</body>
</html>