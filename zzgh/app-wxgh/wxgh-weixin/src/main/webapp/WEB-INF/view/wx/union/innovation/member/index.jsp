<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/4
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人审核进度</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #refreshContainer {
            top: 45px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="2" class="mui-control-item mui-active">工作坊
            <small>（${shopCount}）</small>
        </a>
        <a data-type="4" class="mui-control-item">创新建议
            <small>（${adviceCount}）</small>
        </a>
    </div>

    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var userid = '${seuser.userid}'
    mui({gestureConfig: {longtap: true}})
    $(function () {
        var info = {type: 2, userid: userid}
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/union/innovation/member/list.json',
            data: info,
            ispage: true,
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
            }
        })

        function bindfn(d) {
            var arts = d.applys
            if (arts && arts.length > 0) {
                for (var i in arts) {
                    var a = arts[i]
                    var $item = $('<li class="mui-table-view-cell mui-media">' +
                        '<a href="javascript:;">' +
                        '<div class="mui-media-body">' + a.name + get_status(a.status) +
                        '<p class="mui-ellipsis">' + (new Date(a.addTime).format('yyyy-MM-dd')) + '</p>' +
                        '</div></a></li>')
                    $item.data('id', a.mid)
                    $item.data('type', a.applyType)
                    refresh.addItem($item[0])

                    $item.on('longtap', function () {
                        var cf = confirm('是否删除？')
                        if (cf) {
                            var $self = $(this)
                            var id = $self.data('id')
                            mui.getJSON(homePath + '/wx/union/innovation/member/del.json', {
                                id: id
                            }, function (res) {
                                if (res.ok) {
                                    ui.showToast('删除成功！', function () {
                                        $self.remove()
                                    })
                                } else {
                                    alert('删除失败')
                                }
                            })
                        }
                    }).on('tap', function () {
                        var type = $(this).data('type')
                        var id = $(this).data('id')
                        if (type == 2) { //工作坊
                            mui.openWindow(homePath + '/wx/union/innovation/work/detail.html?id=' + id + '&showType=' + type)
                        } else if (type == 4) { //创新建议
                            mui.openWindow(homePath + '/wx/union/innovation/member/detail.html?id=' + id + '&showType=' + type)
                        }
                    })
                }
            } else {
                refresh.addItem('<li class="mui-table-view-cell"><div class="mui-text-center">暂无数据哦</div></li>')
            }
        }

        var $segment = $('#segmentedControl')
        $segment.data('type', 0)
        $segment.on('tap', 'a.mui-control-item', function () {
            var type = $(this).attr('data-type')
            if (type != $segment.data('type')) {
                var param = {type: type}
                refresh.refresh(param)
                $segment.data('type', type)
            }
        })

        function get_status(s) {
            var status, nam
            if (s == 0) {
                status = '未审核'
                nam = 'info'
            } else if (s == 1 || s >= 3) {
                status = '已通过'
                nam = 'success'
            } else if (s == 2) {
                status = '未通过'
                nam = 'danger'
            }
            return '<span class="mui-badge mui-pull-right mui-badge-' + nam + '">' + status + '</span>'
        }
    })
</script>
</body>
</html>
