
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
                <th>活动名称</th>
                <th>用户信息</th>
                <th>提出时间</th>
                <th>提案状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="lawList">
            </tbody>
        </table>
        <ul class="pagination" id="lawPage"></ul>
    </div>
</div>

<div id="deviceEditModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">查看</h4>
            </div>
            <div class="modal-body">
                <form id="deviceForm">
                    <div class="form-group">
                        <label>活动名称</label>
                        <div id="actName"></div>
                    </div>
                    <div class="form-group">
                        <label>活动时间</label>
                        <%--<input class="form-control" type="file" name="coverFile" id="coverFile">--%>
                        <%--<img src="${home}/image/default/act.png" style="height: 200px;" name="cover">--%>
                        <div id="actTime"></div>
                    </div>
                    <div class="form-group">
                        <label>参加人数</label>
                        <div id="joinNum"></div>
                    </div>
                    <div class="form-group">
                        <label>金额</label>
                        <div id="actCost"></div>
                    </div>
                    <div class="form-group">
                        <label>联系电话</label>
                        <div id="mobile"></div>
                    </div>
                    <div class="form-group">
                        <label>活动地点</label>
                        <div id="address"></div>
                    </div>
                    <div class="form-group">
                        <label>活动内容</label>
                        <div id="actInfo"></div>
                    </div>
                    <div class="form-group">
                        <label>活动目的</label>
                        <div id="reason"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td class="ui-suggest-title" title="提案内容：{{=it.content}}"><p>{{=it.actName}}</p></td>
        <td title="{{=it.deptname}}">
            {{=it.userName}}<br>{{=it.mobile}}
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
        var __self = 'bid/api/'

        var $cateNav = $('#cateNav')

        var $modal = $('#deviceEditModule')

        ui.check.init()
        //list
        var lawTable = ui.table('law', {
            req: {
                url: __self+'actlist.json',
                //data: {action: 'list'}
            },
            dataConver: function (d) {
                d['statusName'] = get_status(d.status)
                d['applyTime'] = new Date(d.applyTime).format('yyyy-MM-dd hh:mm')
                return d
            },
            empty: {
                col: 8,
                html: '暂无活动哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.find('.ui-suggest-title p').ellipsis({row: 2})

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'actdel.json')
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
                    apply_suggest(info, $item)
                })
            })

            //查看
            $item.on('click','.ui-see',function(){
                var id = d.id
                editId = id;
                $modal.data('id', id)
                $modal.modal('show')

                var url = __self+"applyget.json"
                ui.get(url,{id:id},function (d) {
                    initModal(d)
                })

                function initModal(d) {
                    $("#actName").text(d.datas.actName);
                    $("#actTime").text(d.datas.actTime);
                    $("#joinNum").text(d.datas.joinNumb);
                    $("#actCost").text(d.datas.actCost);
                    $("#address").text(d.datas.address);
                    $("#actInfo").text(d.datas.actInfo);
                    $("#reason").text(d.datas.reason);
                }
            })
        })
        lawTable.init()

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                lawTable.refresh({status: $self.data('status')}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的会员提案！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function(){
                    lawTable.request(null, lawTable)
                })
            }, __self + 'actdel.json')
        })

        //管理
        $('#cateManager').on('click', function () {
            ui.history.go('union/suggestcate')
        })

        function apply_suggest(info, $item) {
            info['action'] = 'apply'
            ui.post(__self+"actchange.json", info, function () {
                ui.alert('审核成功！',function(){
                    $item.remove()
                })
            })
        }

        function get_status(status) {
            var str
            if (status == 1) {
                str = '正常'
            } else if (status == 0) {
                str = '未审核'
            } else if (status == 2) {
                str = '审核未通过'
            } else {
                str = '未知'
            }
            return str
        }
    })
</script>
