<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .imgStyle {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }

    #searchResult ul.list-group {
        margin-bottom: 0;
    }
</style>
<div class="row ui-content">
    <div class="ui-link-group">
        <a id="delAllNoticeBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
        <a id="pushPreviewBtn" href="#pushPreviewModal" class="btn btn-empty-theme"><span
                class="fa fa-picture-o"></span> 推送预览</a>
        <a id="pushBtn" href="#pushModal" class="btn btn-empty-theme"><span class="fa fa-send-o"></span> 批量推送</a>
    </div>

    <div style="margin-top: 20px;">
        <ul id="cateNav" class="nav nav-tabs">
            <li data-id="0" class="active">
                <a href="javascript:;">所有人 <i
                        class="fa fa-spinner fa-pulse hidden"></i></a>
            </li>
            <c:forEach items="${group}" var="c" varStatus="i">
                <li data-id="${c.value}">
                    <a href="javascript:;">${c.name} <i
                            class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <table class="table ui-table" id="fourListTable">
        <thead>
        <tr>
            <th><input class="ui-check-all" type="checkbox"/></th>
            <th>封面</th>
            <th>标题</th>
            <th>作者</th>
            <th>发布时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="indexList">
        </tbody>
    </table>
    <ul class="pagination" id="indexPage"></ul>
</div>

<div class="modal fade" role="dialog" id="pushPreviewModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">推送预览</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <div style="position: relative;">
                            <input id="choiceUserId" class="hidden" type="text">
                            <input id="searchInput" type="text" placeholder="通过用户名或手机搜索用户" class="form-control">
                            <div id="searchResult" class="hidden" style="max-height: 270px;overflow-y: auto;">
                                <ul class="list-group">
                                </ul>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="sendPreviewNoticeBtn" type="button" class="btn btn-theme">确认</button>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" role="dialog" id="pushModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">推送公告</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <div style="position: relative;">
                            <select id="selectGroup" class="form-control" name="group_id">
                                <option value="0">所有人</option>
                                <c:forEach items="${group}" var="g" varStatus="i">
                                    <option value="${g.value}">${g.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="sendNoticeBtn" type="button" class="btn btn-theme">确认</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="indexItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td><img src="{{=it.image}}"></td>
        <td>{{=it.title}}</td>
        <td>{{=it.author}}</td>
        <td>{{=it.addTime}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
        var _self = '/admin/di/notice/api/get.json';
        var $cateNav = $('#cateNav')
        ui.check.init()
        var indexTable = ui.table('index', {
            req: {
                url: _self,
                data: {groupId: $cateNav.find('li.active').data('id')}
            }, dataConver: function (d) {
                if (d.addTime) {
                    d.addTime = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                } else {
                    d.addTime = '未知时间'
                }
                if (!d.title) {
                    d.title = '无'
                }
                var img = d.image
                if (!img) {
                    img = homePath + '/image/common/nopic.gif'
                } else {
                    img = ui.get_image(d.image)
                }
                d['image'] = img
                if (!d.author) {
                    d.author = '无'
                }
                return d;
            },
            empty: {
                col: 6,
                html: '暂无公告'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    indexTable.request(null, indexTable)
                }, 'di/notice/api/del_notice.json')
            })
        })
        indexTable.init()

        //批量删除
        $('#delAllNoticeBtn').on('click', function () {
            var ids = indexTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的公告！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    indexTable.request(null, indexTable)
                })
            }, 'di/notice/api/del_notice.json')
        })

        //批量推送
        $('#sendNoticeBtn').on('click', function () {
            var ids = indexTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要推送的公告！')
                return
            }
            if (ids.length > 4) {
                ui.alert('每次推送不能超过4条公告哦')
                return
            }
            ui.confirm('是否推送？', function () {
                ui.post('di/notice/api/push.json', {
                    id: ids.toString(),
                    group_id: $('#selectGroup').val()
                }, function () {
                    ui.alert('推送成功！',function(){
                        $('#pushBtn').removeAttr('data-toggle')
                        $('#pushBtn').modal('hide');
                        $('table.ui-table').find('input[type=checkbox]').prop('checked', false)
                    })
                })
            })
        })

        //搜索框搜索事件
        var $searchInput = $('#searchInput');
        var $searchResult = $('#searchResult')

        $searchInput.keyup(function () {
            var val = $(this).val().trim()
            if (val) {
                ui.post('di/notice/api/search_user.json', {key: val}, function (res) {
                    var users = res;
                    $searchResult.find('.list-group').empty()
                    for (var j in users) {
                        var avatar = users[j].avatar;
                        avatar = avatar ? avatar : homePath + '/image/default/user.png'
                        var $li = $('<li data-id="' + users[j].userid + '" class="list-group-item">' +
                            '<img class="imgStyle" src="' + avatar + '"/>' +
                            ' <span class="userName">' + users[j].name + '</span>' +
                            '<span style="display:block;float:right;">' + users[j].department + '</span></li>')
                        $searchResult.find('.list-group').append($li);
                        initSearchListEvent($li);
                    }
                    $searchResult.removeClass('hidden');
                })
            }
        })

        function initSearchListEvent($li) {
            $li.click(function () {
                $('#choiceUserId').attr('value', $(this).attr('data-id'));
                var name = $(this).find('.userName').text();
                $searchInput.val(name)
                $searchResult.addClass('hidden')
            })
        }

        $('#pushPreviewBtn').on('click', function () {
            var ids = indexTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要推送的公告！')
                return
            }
            if (ids.length > 4) {
                ui.alert('每次推送不能超过4条公告哦')
                return
            }
            $('#pushPreviewBtn').attr('data-toggle', 'modal')
        })

        $('#pushBtn').on('click', function () {
            var ids = indexTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要推送的公告！')
                return
            }
            if (ids.length > 4) {
                ui.alert('每次推送不能超过4条公告哦')
                return
            }
            $('#pushBtn').attr('data-toggle', 'modal')
        })

        $('#sendPreviewNoticeBtn').on('click', function () {
            if ($searchInput.val() == "") {
                ui.alert("请输入用户！");
                return;
            }
            var ids = indexTable.get_checked_ids()
            ui.confirm('是否推送？', function () {
                ui.post('di/notice/api/push.json', {
                    id: ids.toString(),
                    userid: $('#choiceUserId').attr('value')
                }, function () {
                    ui.alert('推送成功！',function(){
                        $('#pushPreviewBtn').removeAttr('data-toggle');
                        $('#pushPreviewBtn').modal('hide');
                        $('table.ui-table').find('input[type=checkbox]').prop('checked', false)
                    })
                })
            })
        })

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                indexTable.refresh({groupId: $self.data('id')}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })
    });

</script>