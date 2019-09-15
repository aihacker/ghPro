<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/26
  Time: 9:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>协会成员</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #segmentedControl {
            background-color: #fff;
            border-bottom: 1px solid #ddd;
        }

        #refreshContainer {
            top: 48px;
        }

        .mui-media-body > span {
            line-height: 40px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="1" class="mui-control-item mui-active">参加
        </a>
        <a data-type="2" class="mui-control-item">请假
        </a>
        <a data-type="3" class="mui-control-item">缺席
        </a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view"></ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell">
        <div class="mui-slider-right mui-disabled">
            {{? it.type == 1}}
            <a data-type="3" class="mui-btn mui-btn-red">缺席</a>
            <a data-type="2" class="mui-btn mui-btn-warning">已请假</a>
            {{?}}

            {{? it.type == 2}}
            <a data-type="3" class="mui-btn mui-btn-red">缺席</a>
            <a data-type="1" class="mui-btn mui-btn-primary">已参加</a>
            {{?}}
            {{? it.type == 3}}
            <a data-type="2" class="mui-btn mui-btn-warning">已请假</a>
            <a data-type="1" class="mui-btn mui-btn-primary">已参加</a>
            {{?}}
        </div>
        <div class="mui-slider-handle">
            <img class="mui-media-object mui-pull-left ui-circle" src="{{=it.avatar}}">
            <div class="mui-media-body">
                <span>{{=it.username}}</span>
            </div>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var actId = '${param.id}';
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'list_user.json',
            data: {actId: actId, pageIs: true, type: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无成员',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['avatar'] = wxgh.get_avatar(d.avatar);
            var $item = refresh.getItem(d)
            $item.on('tap', 'a[data-type]', function () {
                var type = $(this).data('type')
                var $parent = $(this).parent().parent()
                ui.confirm('是否修改？', function () {
                    wxgh.request.post('/wx/union/group/account/edit.json', {
                        id: actId,
                        type: type,
                        userid: d.userid
                    }, function () {
                        mui.swipeoutClose($parent[0])
                        ui.showToast('修改成功！', function () {
                            $parent.remove()
                            refresh.refresh({type: d.type})
                        })
                    })
                })
            })
            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var type = $(this).data('type')
            refresh.refresh({type: type})
        })
    })
</script>
</body>
</html>