<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/22
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请设置你的部门</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="applyBtn" type="button" class="mui-btn mui-btn-primary">立即申请</button>
    </div>

    <ul class="mui-table-view no" id="deptBody">
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    $(function () {
        var $body = $('#deptBody');
        var load = ui.loading('加载中...'),
            userid = '${userid}';

        wxgh.init('${weixin}');

        $('#applyBtn').on('tap', function () {
            var $sel = $body.find('select').last();
            if ($sel.val() == -1) {
                ui.alert('请选择部门！');
                return;
            }
            wxgh.request.post('api/set_dept.json', {deptid: $sel.val(), userid: userid}, function () {
                wx.closeWindow();
            });
        });

        get_dept();

        function get_dept(deptid) {
            load.show();
            wxgh.request.get('api/list_dept.json', {id: deptid}, function (depts) {
                if (depts && depts.length > 0) {
                    var $li = $('<li class="mui-table-view-cell no">' +
                        '<a class="ui-flex mui-navigate-right">' +
                        '<span>请选择</span>' +
                        '<small class="ui-right">请选择</small></a></li>');
                    var $select = $('<select></select>')
                    $select.append('<option value="-1">请选择</option>');
                    for (var i in depts) {
                        $select.append('<option data-id="' + depts[i].id + '" value="' + depts[i].deptid + '">' + depts[i].name + '</option>');
                    }
                    $li.append($select);
                    $body.append($li);

                    $select.on('change', function () {
                        var $self = $(this);
                        var val = $self.val();
                        $self.parent().nextAll().remove();
                        if (val != -1) {
                            var $optiosn = $self.find('option[value="' + val + '"]');
                            var txt = $optiosn.text()
                            $self.parent().find('.ui-right').text(txt)
                            get_dept($optiosn.data('id'));
                        }
                    });
                }
            });
        }
    });
</script>
</body>
</html>