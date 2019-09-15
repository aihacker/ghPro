<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/21
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>报名情况</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        #refreshContainer {
            top: 48px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control white mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="1" class="mui-control-item mui-active">参加
        </a>
        <a data-type="2" class="mui-control-item">请假
        </a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view no">
            </ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell">
        <img class="mui-media-object ui-circle mui-pull-left"
             src="{{=it.avatarSrc}}"
             data-preview-src="{{=it.avatar}}"
             data-preview-group="user_avatar">
        <div class="mui-media-body">
            {{=it.username}}
            <p class="mui-ellipsis">
                {{=it.deptname}}
            </p>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var actId = '${param.id}';
    $(function () {
        wxgh.previewImageInit()

        var refresh = window.refresh('#refreshContainer', {
            data: {actId: actId, status: 1},
            url: 'api/list_join.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['avatarSrc'] = wxgh.get_avatar(d.avatar);
            var $item = refresh.getItem(d)
            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var $self = $(this);
            if (!$self.hasClass('mui-active')) {
                refresh.refresh({status: $self.data('type')})
            }
        })
    })
</script>
</body>
</html>
