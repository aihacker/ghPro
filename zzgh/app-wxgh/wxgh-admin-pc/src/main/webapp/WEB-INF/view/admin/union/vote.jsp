<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .ui-vote-theme,
    .ui-vote-theme p {
        width: 160px;
        max-width: 160px;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <!--<a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-plus-se"></span>
                    添加法律法规</a>-->
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
                    <th>投票主题</th>
                    <th>发布人</th>
                    <th>投票时间</th>
                    <th>发布时间</th>
                    <th>投票状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="lawList">
                </tbody>
            </table>
            <ul class="pagination" id="lawPage"></ul>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <form id="addForm" class="col-md-8 col-md-offset-2" style="margin-top: 20px;">
                <div class="form-group">
                    <label>法律名称</label>
                    <input name="name" type="text" class="form-control">
                </div>
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>正文</label>
                    <script id="lawContent" name="content" type="text/plain" style="width:600px;height:240px;"></script>
                    </div>
                    <div class="form-group">
                        <label>排序序号
                        <small style="font-weight: 500;">（序号越小，排名越前哦）</small>
                    </label>
                    <input name="sortId" type="number" class="form-control" placeholder="默认为 0">
                        </div>
                        <div class="form-group">
                        <button id="addLawBtn" type="button" class="btn btn-theme">确定添加</button>
                        </div>
                        </form>
                        </div>
                        </div>
                        </div>

                        <script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
                    <script type="text/template" id="lawItem">
                        <tr>
                            <td><input class="ui-check" type="checkbox"></td>
                            <td class="ui-vote-theme"><p>{{=it.theme}}</p></td>
                            <td title="{{=it.deptname}}">{{=it.username}}</td>
                            <td>{{=it.startTime}}-{{=it.effectiveTime}}</td>
                            <td>{{=it.createTime}}</td>
                            <td>{{=it.statusName}}</td>
                            <td class="ui-table-operate">
                                <div class="ui-link-group">
                                    <!--<a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                                            class="fa fa-edit"></span> 编辑</a>-->
                                    <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                                    <!--<a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>-->
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
                                </div>
                            </td>
                        </tr>
                    </script>
                    <script>
                        $(function () {
                            var __self = 'union/api/'

                            var $cateNav = $('#cateNav')

                            var $form = $('#addForm')
                            var $addBtn = $('#addLawBtn')

                            ui.check.init()
                            var lawTable = ui.table('law', {
                                dataConver: function (d) {
                                    d['startTime'] = new Date(d.startTime).format('yyyy-MM-dd hh:mm')
                                    d['effectiveTime'] = new Date(d.effectiveTime).format('yyyy-MM-dd hh:mm')
                                    d['createTime'] = new Date(d.createTime).format('yyyy-MM-dd hh:mm')
                                    d['statusName'] = ui.get_status(d.status)
                                    return d;
                                },
                                req: {
                                    url: __self+"votelist.json",
                                    data: {action: 'list'}
                                },
                                empty: {
                                    col: 7,
                                    html: '暂未发布投票哦'
                                }
                            }, function ($item, d) {
                                $item.data('data', d)

                                $item.find('.ui-vote-theme p').ellipsis({row: 2})

                                //删除
                                $item.on('click', '.ui-del', function () {
                                    ui.del(d.id, function () {
                                        ui.alert('删除成功！')
                                        $item.remove()
                                    }, __self+"votedel.json")
                                })

                                //编辑
                                $item.on('click', '.ui-edit', function () {
                                    $form.data('data', d)
                                })

                                $item.on('shown.bs.tab', '.ui-edit', function () {
                                    var d = $form.data('data')
                                    if (d) {
                                        $('input[name=name]').val(d['name'])
                                        $('input[name=sortId]').val(d['sortId'])
                                        $('input[name=id]').val(d['id'])
                                        um.setContent(d['content'])
                                        $addBtn.text('确定编辑')
                                    }
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

                            $("a[href='#mainPane']").on('shown.bs.tab', function () {
                                $form.removeData('data')
                                $form.clearForm()
                            })

                            $("a[href='#addPane']").on('shown.bs.tab', function () {
                                $addBtn.text('确定添加')
                            })

                            //批量删除
                            $('#cateDelBtn').on('click', function () {
                                var ids = lawTable.get_checked_ids()
                                if (ids.length <= 0) {
                                    ui.alert('请选择需要删除的投票！')
                                    return
                                }
                                ui.del(ids, function () {
                                    ui.alert('删除成功！',function () {
                                        lawTable.request(null,lawTable)
                                    })
                                }, __self+"votedel.json")
                            })

                            $addBtn.on('click', function () {
                                var info = $form.serializeJson()
                                var verifyRes = verify(info)
                                if (verifyRes) {
                                    ui.alert(verifyRes)
                                    return;
                                }
                                info['action'] = 'add'
                                if (!info['sortId']) info['sortId'] = 0
                                ui.post(__self, info, function () {
                                    ui.alert(info['id'] ? '编辑成功！' : '添加成功！')
                                    ui.refresh()
                                })
                            })


                            //manage
                            function apply_suggest(info, $item) {
                                info['action'] = 'apply'
                                ui.post(__self+"voteapply.json", info, function () {
                                    ui.alert('审核成功！',function(){
                                        $item.remove()
                                    })
                                })
                            }

                            /**
                             * 表单验证
                             * @param info
                             * @returns {*}
                             */
                            function verify(info) {

                            }
                        })
                    </script>

