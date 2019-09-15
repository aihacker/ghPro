<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/9
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>积分申述</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" type="text/css" href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css"/>
</head>
<body>

<div class="mui-content">
    <form id="mForm">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell no">
                <a class="ui-flex">
                    <span>时间</span>
                    <input class="ui-right" readonly name="dateid" type="text" placeholder="请选择时间"/>
                </a>
            </li>
            <li class="mui-table-view-cell no">
                <a>
                    <div class="ui-content">
                        <h5 class="ui-title">备注说明</h5>
                        <div class="ui-text-div">
                            <textarea name="remark" maxlength="200" rows="1"></textarea>
                        </div>
                    </div>
                </a>
            </li>
            <li class="mui-table-view-cell no">
                <a>
                    <div class="ui-content">
                        <h5 class="ui-title">微信运动截图</h5>
                        <div id="chooseImg">

                        </div>
                    </div>
                </a>
            </li>
        </ul>
    </form>

    <div class="ui-fixed-bottom">
        <button id="subBtn" type="button" class="mui-btn mui-btn-primary">确认提交</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script type="text/javascript" src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    $(function () {
        wxgh.init('${weixin}');
        var choose = wxgh.imagechoose($('#chooseImg')),
            year = new Date().getFullYear(),
            pick = new mui.DtPicker({type: 'date', beginYear: year - 1}),
            $form = $('#mForm'),
            laod = ui.loading(),
            id = '${param.id}';

        $('input[name=dateid]').on('tap', function () {
            var $self = $(this);
            pick.show(function (rs) {
                var dateid = rs.text.replace(/-/g, '');
                if (dateid >= get_first_monday()) {
                    ui.alert('暂不能申述，本周积分将在下一周进行结算哦')
                } else {
                    $self.val(rs.text);
                }
            });
        });

        $('#subBtn').on('tap', function () {
            var info = $form.serializeJson();
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            info['dateid'] = info.dateid.replace(/-/g, '');
            info['id'] = id;
            laod.show();
            choose.upload(function (mediaids) {
                if (!mediaids || mediaids.length == 0) {
                    ui.alert('请选择微信运动截图');
                } else {
                    info['imgs'] = mediaids.toString();
                    wxgh.request.post('add.json', info, function () {
                        ui.showToast('静候管理员...', function () {
                            window.location.reload(true);
                        });
                    });
                }
            })
        });

        function verify(info) {
            if (!info.dateid) {
                return '请选择时间';
            }
        }

        function get_first_monday() {
            var now = new Date(),
                nowTime = now.getTime(),
                day = now.getDay(),
                oneDayTime = 24 * 60 * 60 * 1000;
            var moneday = new Date(nowTime - (day - 1) * oneDayTime),
                date = moneday.getDate(),
                month = moneday.getMonth() + 1;
            return moneday.getFullYear().toString() + (month > 9 ? month : '0' + month) + (date > 9 ? date : '0' + date);
        }
    });
</script>
</body>
</html>
