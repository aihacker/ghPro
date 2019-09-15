<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/12
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动签到</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-address-div {
            font-size: 15px;
            color: #777;
            padding: 8px 10px;
        }

        .ui-images .ui-img-div {
            height: 80px;
        }

        .ui-images.mui-table-view.mui-grid-view {
            padding: 5px 10px 15px 0;
        }

        .ui-select-act select {
            margin-bottom: 0;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-textarea-div">
        <textarea name="remark" rows="4" maxlength="200" placeholder="签到前说点什么呢？"></textarea>
    </div>
    <div class="ui-address-div">
        <span class="fa fa-map-marker"></span>
        <span id="address">
            <span class="ui-txt">正在获取位置...</span>
            <span class="fa fa-refresh fa-spin ui-text-primary"></span>
        </span>
    </div>
    <div id="chooseImg"></div>

    <div class="ui-fixed-bottom">
        <button id="signBtn" type="button" class="mui-btn mui-btn-block ui-btn mui-btn-primary">立即签到</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<!--uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua--->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=20&ak=ba0C6ujM5Zkg90fMo6j1yQInRD9qrcAT"></script>
<script>
    var actId = '${param.id}';
    var dateid = '${param.dateid}';
    $(function () {
        var $addr = $('#address')

        wxgh.init('${weixin}')

        var choose = wxgh.imagechoose($('#chooseImg'), {
            col: 3
        })

        var load = ui.loading('签到中...');

        //立即签到
        $('#signBtn').on('tap', function () {
            var addr = $addr.data('data')
            if (!addr) {
                alert('正在定位！')
                return
            }
            var remark = $('textarea[name=remark]').val()

            ui.confirm('是否签到？', function () {
                load.show();
                choose.upload(function (medisIds) {
                    var info = {action: 'add'}
                    info['actId'] = actId;
                    info['remark'] = remark;
                    info['imgs'] = medisIds.toString();
                    if (dateid) {
                        info['dateid'] = dateid;
                    }
                    mui.extend(info, addr)
                    wxgh.request.post('add_api.json', info, function () {
                        ui.showToast('签到成功！', function () {
                            mui.back()
                        })
                    })
                })
            })
        })

        var geoc = new BMap.Geocoder();

        $addr.find('.fa-refresh').on('tap', function () {
            $addr.find('.ui-txt').text('正在获取位置...')
            $(this).addClass('fa-spin')
            getLocation()
        })

        getLocation()

        function getLocation() {
            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    geoc.getLocation(r.point, function (rs) {
                        $addr.find('.ui-txt').text(rs.address)
                        $addr.find('.fa-refresh').removeClass('fa-spin')
                        var p = rs.point
                        $addr.data('data', {address: rs.address, lat: p.lat, lng: p.lng})
                    })
                }
                else {
                    $addr.text('获取位置失败，点击重试！')
                }
            }, {enableHighAccuracy: true})
        }
    })
</script>
</body>
</html>
