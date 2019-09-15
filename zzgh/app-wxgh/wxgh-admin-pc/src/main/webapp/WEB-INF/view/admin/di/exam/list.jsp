<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<style>
    table.ui-table td > img {
        width: 72px;
        height: 54px;
        margin-right: 2px;
    }

    .td_name {
        width: 210px;
        max-width: 210px;
    }

    .td_info {
        width: 350px;
        max-width: 350px;
    }

    .ui-exame-name > label {
    }

    .ui-exame-name > span {
        font-weight: 600;
    }

    .ui-exame-name > input {
        width: 80%;
        display: inline-block;
        height: 30px;
        padding: 2px 10px;
    }

    .ui-exame-body > label {
        display: block;
        font-weight: 500;
    }

    .ui-exame-body > label > input[type=text] {
        width: 80%;
        display: inline-block;
        height: 30px;
        padding: 2px 10px;
    }
</style>

<div class="row ui-content">
    <div class="home">
        <div class="ui-link-group">
            <a id="delAllExamBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
            <a href="#exportModal" data-toggle="modal" class="btn btn-empty-theme"><span
                    class="fa fa-share-square-o"></span>
                考试记录导出</a>
        </div>

        <div style="margin-top: 20px;">
            <ul id="cateNav" class="nav nav-tabs">
                <c:forEach items="${group}" var="c" varStatus="i">
                    <li data-id="${c.value}" class="${i.first?'active':''}">
                        <a href="javascript:;">${c.name} <i
                                class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <table class="table ui-table" id="fourListTable">
            <thead>
            <tr>
                <th width="5%"><input class="ui-check-all" type="checkbox"/></th>
                <th width="15%">考试名称</th>
                <th width="10%">图片</th>
                <th width="30%">考试介绍</th>
                <th width="10%">添加时间</th>
                <th width="10%">截止时间</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
            <tbody id="indexList">
            </tbody>
        </table>
        <ul class="pagination" id="indexPage"></ul>
    </div>
    <div class="addPane" style="display: none">
        <form class="col-lg-4 col-md-4 col-lg-offset-4 col-md-offset-4">
            <div class="form-group">
                <label>考试名称</label>
                <input id="exameName" class="form-control" type="text"/>
            </div>
            <div class="form-group">
                <label>简&nbsp;&nbsp;介</label>
                <textarea name="" id="exameInfo" class="form-control" rows="3" maxlength="200"></textarea>
                <small class="pull-right"></small>
            </div>

            <div class="form-group">
                <label>学习内容</label>
                <script type="text/plain" id="myEditor" style="height:250px;"></script></div>
            <div>
                <button id="subBtn" type="button" class="btn btn-theme">确认修改</button>
                <button id="back" type="button" class="btn btn-theme">取消修改</button>
            </div>
        </form>
    </div>
</div>

<div id="exportModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">考试记录导出</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label>Excel文件格式</label>
                        <select name="exportType" class="form-control">
                            <option value="1">按群体导出</option>
                            <option value="2">按考试导出</option>
                        </select>
                    </div>
                    <div class="form-group" id="q-list">
                        <label>选择需要导出的群体</label>
                        <div>
                            <label class="checkbox-inline">
                                <input name="id" type="checkbox" value="0"> 全部
                            </label>
                            <c:forEach items="${group}" var="c" varStatus="i">
                                <label class="checkbox-inline">
                                    <input name="id" type="checkbox" value="${c.value}"> ${c.name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group" id="e-list" style="display:none;">
                        <label>选择需要导出的考试</label>
                        <div>
                            <label class="checkbox-inline">
                                <input name="idd" type="checkbox" value="0"> 全部
                            </label>
                            <c:forEach items="${exams}" var="c" varStatus="i">
                                <label class="checkbox-inline">
                                    <input name="idd" type="checkbox" value="${c.value}"> ${c.name}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="exportBtn" type="button" class="btn btn-theme">确定导出</button>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="indexItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td title="{{=it.name}}"><span class="td_name">{{=it.name}}</span></td>
        <td><img src="{{=it.coverPath}}"></td>
        <td title="{{=it.info}}"><span class="td_info">{{=it.info}}</span></td>
        <td>{{=it.addTime}}</td>
        <td>{{=it.endTime}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑或重新推送</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<jsp:include page="/comm/admin/upload.jsp"/>
<jsp:include page="/comm/admin/umeditor.jsp"/>
<script>
    $(function () {
        var um = UM.getEditor('myEditor');
        var _self = '/admin/di/exam/api/get.json';
        var $cateNav = $('#cateNav'),
            $exportForm = $('#exportModal form');
        ui.check.init()
        var indexTable = ui.table('index', {
            req: {
                url: _self,
                data: {groupId: $cateNav.find('li.active').data('id')}
            }, dataConver: function (d) {
                if (!d.name) {
                    d.name = '无'
                }
                var img = d.coverPath
                if (!img) {
                    img = homePath + '/image/common/nopic.gif'
                } else {
                    img = ui.get_image(d.coverPath)
                }
                d['coverPath'] = img
                if (!d.info) {
                    d.info = '无'
                }
                if (d.addTime) {
                    d.addTime = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                } else {
                    d.addTime = '未知时间'
                }
                if (d.endTime) {
                    d.endTime = new Date(d.endTime).format('yyyy-MM-dd hh:mm')
                } else {
                    d.endTime = '未知时间'
                }
                return d;
            },
            empty: {
                col: 7,
                html: '暂无考试'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    indexTable.request(null, indexTable)
                }, 'di/exam/api/del_exam.json')
            })

            $item.on('click', '.ui-see', function () {
                ui.history.go('di/exam/exam', {id: d.id}, function () {

                })
            })

            //查看
            $item.on('click', '.ui-edit', function () {
                var url = 'di/exam/edit.html'
                ui.history.go(url, {id: d.id, groupId: $cateNav.find('li.active').data('id')})
            })

            $item.find('.td_name').ellipsis({row: 2})
            $item.find('.td_info').ellipsis({row: 2})

        })
        indexTable.init()

        //批量删除
        $('#delAllExamBtn').on('click', function () {
            var ids = indexTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的考试！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    indexTable.request(null, indexTable)
                })
            }, 'di/exam/api/del_exam.json')
        });

        $("select[name=exportType]").on('change', function () {
            var i = $(this).val();
            if(i==1){
                $("#q-list").show();
                $("#e-list").hide();
            }else{
                $("#q-list").hide();
                $("#e-list").show();
            }
        })

        //导出考试记录
        $('#exportModal').on('click', '#exportBtn', function () {
            var info = $exportForm.serializeJson();
            if(info['exportType'] == 1)
            {
                if (!info.id) {
                    ui.alert('请选择需要导出的群体');
                    return;
                }
            }else
            {
                if (!info.idd) {
                    ui.alert('请选择需要导出的考试');
                    return;
                }
            }

            if(info['exportType'] != 1)
                info['id'] = info['idd']

            var ids = info.id;
            if (ids.indexOf('0') < 0) {
                info['id'] = ids.toString();
            } else {
                info['id'] = 0;
            }
            ui.openUrl('di/exam/api/export_exam.html', info);
        });

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                indexTable.refresh({groupId: $self.data('id')}, function () {
                    $self.find('i.fa').addClass('hidden')
                    $('.td_name').ellipsis({row: 2})
                    $('.td_info').ellipsis({row: 2})
                })
            }
        })
    });
</script>