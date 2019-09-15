<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/12
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>新成员审核</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-media-object {
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .mui-control-content {
            height: 100%;
            width: 100%;
        }

        .mui-segmented-control .mui-control-item {
            line-height: 30px;
        }

        .ui-apply-btn {
            padding: 3px 10px;
        }

        .mui-table-view .mui-media-object {
            width: 42px;
        }

        #refreshContainer {
            top: 50px;
        }
    </style>
</head>

<body>

<div class="mui-content">

    <div id="uiControleHead" style="padding: 10px 20%;">
        <div id="segmentedControl" class="mui-segmented-control">
            <a data-status="0" class="mui-control-item mui-active" href="#item1">
                未审核（${c.apply}）
            </a>
            <a data-status="2" class="mui-control-item" href="#item2">
                未通过（${c.no}）
            </a>
        </div>
    </div>

    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>

    <script type="text/template" id="itemTpl">
        <li class="mui-table-view-cell mui-media">
            <a href="javascript:;">
                <img class="mui-media-object mui-pull-left" src="{{=it.avatar}}">
                <div class="mui-media-body">
                    {{=it.username}}
                    <button class="mui-btn mui-btn-primary mui-pull-right ui-apply-btn">同意</button>
                    <p class="mui-ellipsis">申请加入兴趣协会</p>
                </div>
            </a>
        </li>
    </script>

    <jsp:include page="/comm/mobile/scripts.jsp"/>
    <script src="${home}/comm/mobile/js/refresh.js"></script>
    <script type="text/javascript">
        var groupId = '${param.id}';
        $(function () {
            var refresh = window.refresh('#refreshContainer', {
                data: {status: 0, groupId: groupId},
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
                var $item = refresh.getItem(d)
                $item.on('tap', '.ui-apply-btn', function () {
                    var $self = $(this)
                    console.log(d)
                    mui.confirm('同意他加入协会吗？', '同意确认', ['不同意', '同意'], function (e) {
                        if (e.index == 1) {
                            apply_request(1, d.id, $self)
                            window.location.reload(true);
                        } else {
                            mui.confirm('你将拒绝他的入会申请', '拒绝确认', ['否', '是'], function (e) {
                                if (e.index == 1) {
                                    apply_request(2, d.id, $self)
                                    window.location.reload(true);
                                }
                            })
                        }
                    })
                })
                return $item[0]
            }

            $('#segmentedControl').on('tap', 'a[data-status]', function () {
                refresh.refresh({status: $(this).data('status')})
            })

            var loading = ui.loading('审核中...')

            function apply_request(status, id, $btn) {
                loading.show()
                wxgh.request.post('/wx/union/group/apply/apply.json', {
                    status: status,
                    id: id,
                    groupId: groupId
                }, function () {
                    ui.showToast('审核成功')
                    if (status == 1) {
                        $btn.text('已同意')
                    } else {
                        $btn.text('已拒绝')
                    }
                })
            }
        })
    </script>
</body>

</html>
