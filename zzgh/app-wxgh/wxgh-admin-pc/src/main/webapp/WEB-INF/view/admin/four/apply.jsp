<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .ui-suggest-title,
    .ui-suggest-title p {
        width: 180px;
        max-width: 180px;
    }

    #module {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #f5f5f5;
        display: none;
        z-index: 1000;
    }
</style>
<div class="row">
    <div class="ui-content">
        <div class="ui-link-group">
            <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            <!--<a id="cateManager" class="btn btn-empty-theme"><span class="fa fa-cog"></span> 提案分类管理</a>-->
        </div>

        <div style="margin-top: 20px;">
            <ul id="cateNav" class="nav nav-tabs">
                <li data-status="0" class="active">
                    <a href="javascript:;">待审核 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="1">
                    <a href="javascript:;">已通过 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="2">
                    <a href="javascript:;">未通过 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </ul>
        </div>

        <table class="table ui-table">
            <thead>
            <tr>
                <th><input class="ui-check-all" type="checkbox"/></th>
                <th>申请单位</th>
                <th>申请人</th>
                <th>提出时间</th>
                <th>
                    <div class="dropdown" style="cursor: pointer;">
                        <a data-toggle="dropdown">
                            类型
                            <span class="caret"></span>
                        </a>
                        <ul id="fourApplyType" class="dropdown-menu">
                            <li data-type="1">
                                <a href="javascript:;">新购</a>
                            </li>
                            <li data-type="2">
                                <a href="javascript:;">维修</a>
                            </li>
                            <li data-type="0">
                                <a href="javascript:;">全部</a>
                            </li>
                        </ul>
                    </div>
                </th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="lawList">
            </tbody>
        </table>
        <ul class="pagination" id="lawPage"></ul>
    </div>

    <div id="module">
        <div>
            <div class="ui-content ui-link-group">
                <button type="button" class="btn btn-empty-theme ui-back"><span
                        class="fa fa-angle-double-left"></span> 返回
                </button>
            </div>
        </div>
        <div class="row ui-content" style="margin-top: 20px;">
            <div class="panel panel-info">
                <div class="panel-heading" id="marketing"></div>
                <div class="panel-body">
                    <div class="ui-member-item">
                        <label>四小项目：</label>
                        <span id="fpName"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>项目内容：</label>
                        <span id="fpcName"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>品牌：</label>
                        <span id="brand">${act.address}</span>
                    </div>
                    <div class="ui-member-item">
                        <label>规格型号：</label>
                        <span id="modelName">${act.actTime}</span>
                    </div>
                    <div class="ui-member-item">
                        <label>预算单价：</label>
                        <span id="budget">${act.joinNumb}</span>
                    </div>
                    <div class="ui-member-item">
                        <label>申请类型：</label>
                        <span id="deviceStatus">${act.actCost}</span>
                    </div>
                    <div class="ui-member-item">
                        <label>申请人：</label>
                        <span id="username">${act.actInfo}</span>
                    </div>
                    <div class="ui-member-item">
                        <label>备注：</label>
                        <span id="remark">${act.reason}</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td class="ui-suggest-title"><p>{{=it.marketing}}</p></td>
        <td title="{{=it.deptName}}">
            {{=it.username}}
        </td>
        <td>{{=it.applyTime}}</td>
        <td>{{=it.statusName}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                <div class="dropdown">
                    <a data-toggle="dropdown" class="btn btn-empty-danger ui-apply">审核 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        {{? it.status===0 || it.status===2}}
                        <li data-status="1"><a href="javascript:;">通过</a></li>
                        {{?}}
                        {{? it.status===0 || it.status===1}}
                        <li data-status="2"><a href="javascript:;">不通过</a></li>
                        {{?}}
                    </ul>
                </div>
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var __self = 'four/api/'

        var $cateNav = $('#cateNav')

        var $modal = $('#module')

        ui.check.init()
        //list
        var lawTable = ui.table('law', {
            req: {
                url: __self + 'fourlist.json',
                //data: {action: 'list'}
            },
            dataConver: function (d) {
                d['statusName'] = get_status(d.deviceStatus)
                d['applyTime'] = new Date(d.applyTime).format('yyyy-MM-dd hh:mm')
                return d
            },
            empty: {
                col: 8,
                html: '暂无申请'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.find('.ui-suggest-title p').ellipsis({row: 2})

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'fourdel.json')
            })

            //审核
            $item.on('click', '.dropdown-menu li', function () {
                var $self = $(this)
                var status = $self.data('status')
                var msg = $.trim($self.text())
                ui.confirm('是否' + msg + '审核？', function () {
                    var info = {
                        id: d.id,
                        status: status
                    }
                    ui.confirm("是否通知用户", function () {
                        info['notify'] = true
                        apply_suggest(info, $item)
                    }, "审核结果", function () {
                        apply_suggest(info, $item)
                    })

                })
            })

            //查看
            $item.on('click', '.ui-see', function () {
                var id = d.id
                editId = id;
                $modal.data('id', id)
                $modal.show()

                var url = __self + "fourlist.json"
                ui.get(url, {id: id}, function (d) {
                    initModal(d)
                })

                function initModal(d) {
                    $("#marketing").text(d.datas[0].marketing);
                    $("#fpcName").text(d.datas[0].fpcName + "x" + d.datas[0].numb);
                    $("#fpName").text(d.datas[0].fpName);
                    $("#brand").text(d.datas[0].brand);
                    $("#modelName").text(d.datas[0].modelName);
                    $("#budget").text(d.datas[0].budget + "/" + d.datas[0].unit);
                    $("#deviceStatus").text(get_status(d.datas[0].deviceStatus));
                    $("#username").text(d.datas[0].username);
                    $("#remark").text(d.datas[0].remark);
                }

                $(".ui-back").click(function () {
                    $modal.hide()
                })
            })
        })
        lawTable.init()

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                lawTable.refresh({status: $self.data('status'), deviceStatus: ''}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的项目！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    lawTable.request(null, lawTable)
                })
            }, __self + 'fourdel.json')
        })

        //管理
        $('#cateManager').on('click', function () {
            ui.history.go('union/suggestcate')
        });

        $('#fourApplyType').on('click', 'li', function () {
            var $self = $(this);
            if (!$self.hasClass('this')) {
                $('#fourApplyType').find('li.this').removeClass('this');
                $self.addClass('this');
                var type = $self.data('type');
                lawTable.refresh({deviceStatus: type == 0 ? '' : type});
            }
        });

        function apply_suggest(info, $item) {
            ui.post(__self + "fourchange.json", info, function () {
                ui.alert('审核成功！', function () {
                    $item.remove()
                })
            })
        }

        function get_status(status) {
            var str
            if (status == 1) {
                str = '新购'
            } else if (status == 2) {
                str = '维修'
            } else {
                str = '未知'
            }
            return str
        }
    })
</script>

