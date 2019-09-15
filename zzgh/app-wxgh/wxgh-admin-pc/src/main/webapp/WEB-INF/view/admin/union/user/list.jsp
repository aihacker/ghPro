<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/13
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<style>
    #deptList {
        font-size: 13px;
        color: #676a6c;
    }

    .tree-item {
        position: relative;
    }

    .tree-item a.a-itme {
        color: #000;
        cursor: pointer;
        display: block;
    }

    #deptList a.a-itme:hover {
        text-decoration: none;
    }

    ul.tree-list {
        list-style: none;
        margin: 0;
        padding: 0 0 0 15px;
    }

    .tree-item a.a-itme {
        padding: 3px 0;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .tree-item .fa {
        font-size: 18px;
        color: #676a6c;
    }

    .tree-item .ui-caret {
        width: 10px;
    }

    .tree-item .fa-folder-open {
        font-size: 16px;
        color: #0399F0;
        margin: 0 2px;
    }

    .tree-item span.righ-other {
        margin-right: 4px;
        display: none;
        font-size: 16px;
        color: #777;
        padding: 0 5px;
    }

    .menu {
        display: none;
        width: 110px;
        position: absolute;
    }

    .menu .list-group-item {
        padding: 5px 15px;
        font-size: 13px;
        color: #777;
    }

    .tree-item > a.a-itme.active {
        background-color: #beebff;
    }

    #userList td > img {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }
</style>
<div class="row">
    <ul class="nav nav-tabs hidden">
        <li><a data-target="#mainPane" data-toggle="tab"></a></li>
        <li><a data-target="#editPane" data-toggle="tab"></a></li>
    </ul>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade in active" id="mainPane">
            <div class="col-md-2">
                <div style="height: 40px">
                    <input id="msearchInput" type="text" class="pull-right form-control" placeholder="搜索姓名、手机号">
                </div>
                <div id="deptList">
                    <div class="tree-item">
                        <a data-deptid="1" style="padding: 5px 10px;font-size: 17px;" class="active">
                            中国电信郑州市工会
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-10">
                <div class="ui-link-group">
                    <a id="userDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                    <a id="addUserBtn" class="btn btn-empty-theme"><span
                            class="fa fa-plus-square-o"></span> 新增成员</a>
                    <a href="#exportModal" data-toggle="modal" class="btn btn-empty-theme"><span class="fa fa-sign-in"></span>
                        通讯录导出</a>

                </div>

                <table class="table ui-table">
                    <thead>
                    <tr>
                        <th><input class="ui-check-all" type="checkbox"/></th>
                        <th>姓名</th>
                        <th>职位</th>
                        <th>手机号码</th>
                        <th>邮箱</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="userList">
                    </tbody>
                </table>
                <ul class="pagination" id="userPage"></ul>
            </div>
        </div>

        <!--添加用户、编辑用户 -->
        <div role="tabpanel" class="tab-pane fade" id="editPane">
            <div style="margin-left: 20%">
                <a id="backBtn" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <form class="center-block" style="width: 40%;margin-top: 20px;">
                <div class="form-group" style="margin-top: 20px;">
                    <label>姓名
                        <small>（必填）</small>
                    </label>
                    <input name="name" type="email" class="form-control"/>
                </div>
                <input type="hidden" name="id">
                <input type="hidden" name="userid">
                <div class="form-group">
                    <label>手机号码
                        <small>（必填）</small>
                    </label>
                    <input name="mobile" type="text" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>邮箱</label>
                    <input name="email" type="text" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>微信号</label>
                    <input name="weixinid" type="text" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>职位</label>
                    <input name="position" type="text" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>所在部门</label><br/>
                    <input type="hidden" value="1" name="deptid">
                    <a id="editChooseDept" class="btn btn-empty-theme"><span class="fa fa-plus-square-o"></span> 请选择</a>
                    <span id="editDeptLabel" class="label label-primary">佛山分公司</span>
                </div>
                <div class="form-group">
                    <button id="editSaveContinueBtn" type="button" class="btn btn-theme">保存继续添加</button>
                    <button id="editSaveBtn" type="button" class="btn btn-default">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="menu" id="myMenu">
    <ul class="list-group">
        <li class="list-group-item add">
            <a>添加子部门</a>
        </li>
        <li class="list-group-item edit">
            <a>修改名称</a>
        </li>
        <li class="list-group-item del">
            <a>删除</a>
        </li>
    </ul>
</div>

<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">修改名称</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label>部门名称</label>
                        <input name="name" class="form-control">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="okBtn">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 导出modal -->
<div class="modal fade" id="exportModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">通讯录导出</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" style="width: 80%;">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">区公司：</label>
                        <div class="col-sm-8">
                            <select name="deptid" class="form-control">
                                <option value="0">请选择</option>
                                <c:forEach items="${depts}" var="d">
                                    <option value="${d.id}">${d.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">营销中心：</label>
                        <div class="col-sm-8">
                            <select name="mid" class="form-control">
                                <option value="-1">请选择</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                <button id="exportBtn" type="button" class="btn btn-theme">确定导出</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="deptItem">
    <li class="tree-item">
        <a class="a-itme" data-deptid="{{=it.deptid}}" title="{{=it.name}}">
            <span class="fa fa-caret-right ui-caret"></span>
            <span class="fa fa-folder-open"></span> {{=it.name}}
            <span class="fa fa-ellipsis-v pull-right righ-other"></span>
        </a>
    </li>
</script>
<script type="text/template" id="userItem">
    <tr title="{{=it.dept}}">
        <td><input class="ui-check" type="checkbox"></td>
        <td style="text-align: left"><img class="img-circle" src="{{=it.avatar}}">{{=it.username}}</td>
        <td>{{=it.position}}</td>
        <td>{{=it.mobile}}</td>
        <td>{{=it.email}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<jsp:include page="/comm/admin/userlist.jsp"/>
<script>
    $(function () {
        var __self = 'union/user/api/',
            $dept = $('#deptList'),
            deptTPL = doT.template($('#deptItem').html()),
            mdeptid = 1,
            $form = $('#editPane form'),
            $deptLabel = $('#editDeptLabel'),
            $addContinuBtn = $('#editSaveContinueBtn'),
            $addBtn = $('#editSaveBtn'),
            $menu = $('#myMenu'),
            $modal = $('#editModal'),
            $search = $('#msearchInput');

        ui.check.init();
        get_depts(mdeptid, $dept);
        var userTable = ui.table('user', {
            dataConver: function (d) {
                if (!d.position) d['position'] = '未知';
                d['avatar'] = ui.get_avatar(d.avatar);
                return d;
            },
            req: {
                url: __self + 'list_user.json'
            },
            empty: {
                col: 5,
                html: '暂无用户'
            }
        }, function ($item, d) {
            $item.data('data', d);

            //删除用户
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    $item.remove();
                }, __self + 'del_user.json');
            });

            //编辑按钮
            $item.on('click', '.ui-edit', function () {
                $addContinuBtn.hide();
                set_from(d);
                $('a[data-target="#editPane"]').tab('show');
            });
        });
        userTable.init();

        //搜索
        $search.keyup(function () {
            var $self = $(this);
            var val = $self.val();
            if (val && val != $search.data('oldval')) {
                $search.data('oldval', val);
                userTable.refresh({search: $.trim(val)});
            }
        });

        //保存
        $addBtn.click(function () {
            var info = $form.serializeJson();
            var verifyRes = addVeriy(info);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            ui.post(__self + 'update_user.json', info, function () {
                $('a[data-target="#mainPane"]').tab('show');
                userTable.refresh()
            });
        });

        //批量删除
        $('#userDelBtn').click(function () {
            var ids = userTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的用户！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    userTable.refresh();
                })
            }, __self + 'del_user.json')
        });

        //选择部门
        var userlist = window.userlist({
            checkable_dept: true,
            checkable_user: false,
            callback: function (lists) {
                if (lists.length > 1) {
                    ui.alert('只能选择一个部门哦！')
                    return
                }
                if(lists.length == 0){
                    ui.alert('请选择一个部门！')
                    return
                }
                var d = lists[0]
                $deptLabel.text(d.name)
                $form.find('input[name=deptid]').val(d.id)
            }
        });
        $('#editChooseDept').click(function () {
            userlist.show();
        });

        //返回按钮
        $('#backBtn').click(function () {
            $('a[data-target="#mainPane"]').tab('show');
        });

        $('#addUserBtn').click(function () {
            $('a[data-target="#editPane"]').tab('show');
        });
        $("a[href='#mainPane']").on('show.bs.tab', function () {
            $form.clearForm();
            $deptLabel.text('佛山分公司');
            $form.find('input[name=deptid]').val(1);
            $addContinuBtn.show();
        });

        //部门菜单
        $menu.on('click', '.del', function () {
            var deptid = $menu.data('itm').deptid;
            ui.del(deptid, function () {
                ui.refresh();
            }, __self + 'del_dept.json');
        });
        $menu.on('click', '.edit', function () {
            $modal.find('.modal-title').text('修改名称');
            $menu.data('type', 1);
            $modal.modal('show');
        });
        $menu.on('click', '.add', function () {
            $modal.find('.modal-title').text('新建部门');
            $menu.data('type', 2);
            $modal.modal('show');
        });
        $modal.on('click', '#okBtn', function () {
            var name = $modal.find('input[name=name]').val();
            if (!name) {
                ui.alert('请输入部门名称');
                return;
            }
            var type = $menu.data('type');
            var info = {name: name};
            var itm = $menu.data('itm');
            if (type == 1) { //编辑
                info['id'] = itm.id;
                info['deptid'] = itm.deptid;
            } else {
                info['parentid'] = itm.deptid;
            }
            ui.post(__self + 'add_dept.json', info, function () {
                $modal.modal('hide');
                $modal.find('input[name=name]').val('');
                ui.refresh();
            });
        });

        function get_depts(deptid, $itm, func) {
            ui.get(__self + 'list_dept.json', {deptid: deptid}, function (depts) {
                var $ul = $('<ul class="tree-list"></ul>');
                for (var i in depts) {
                    var d = depts[i];
                    var $item = $(deptTPL(d));
                    $item.data('itm', d);
                    $ul.append($item);

                    if (!d.hasChild) {
                        $item.find('.ui-caret').css('visibility', 'hidden');
                    }
                    init_dept_item_event($item);
                }
                $itm.append($ul);
                if (func) func();
            })
        }

        function init_dept_item_event($item) {
            $item.on('click', function (e) {
                e.stopPropagation();
                var $self = $(this);
                var $a = $item.find('a').eq(0);

                var has = $self.data('itm').hasChild;
                var deptid = $self.data('itm').id;

                if (has == 1) {
                    var $caret = $self.find('.ui-caret').eq(0);
                    var $trees = $self.find('.tree-list');
                    if ($caret.hasClass('fa-caret-right')) {
                        $caret.removeClass('fa-caret-right')
                            .addClass('fa-caret-down');
                        if ($trees.length <= 0) {
                            get_depts(deptid, $self);
                        } else {
                            $trees.eq(0).show();
                        }
                    } else {
                        $caret.removeClass('fa-caret-down')
                            .addClass('fa-caret-right');
                        $self.find('.tree-list').eq(0).hide();
                    }
                }
                mdeptid = deptid;
                if (!$a.hasClass('active')) {
                    userTable.refresh({deptid: deptid});
                    $('.tree-item a').removeClass('active');
                    $a.addClass('active');
                }
            })

            $item.find('a.a-itme').hover(function () {
                $(this).find('.righ-other')
                    .show();
            }, function () {
                $(this).find('.righ-other').hide();
            });
            $item.on('click', '.righ-other', function (e) {
                e.stopPropagation();
                $menu.css('left', (e.pageX || e.clientX) - 220)
                    .css('top', (e.pageY || e.clientY) - 60)
                    .show();
                $menu.data('itm', $item.data('itm'));
            });
        }

        $(document).click(function (e) {
            if ($(e.target).closest('#myMenu').length == 0) {
                $menu.hide();
            }
        });

        function set_from(d) {
            $form.find("input[name=id]").val(d.id);
            $form.find('input[name=name]').val(d.username);
            $form.find('input[name=mobile]').val(d.mobile);
            $form.find('input[name=email]').val(d.email);
            $form.find('input[name=weixinid]').val(d.weixinid);
            $form.find('input[name=deptid]').val(d.deptid);
            $form.find('input[name=position]').val(d.position);
            $form.find('input[name=userid]').val(d.userid);
            $deptLabel.text(d.deptname);
        }

        function addVeriy(info) {
            if (!info['name']) {
                return '姓名不能为空哦！'
            }
            if (!info['mobile']) {
                return '手机号码不能为空哦！'
            }
            if (!ui.verify.mobile(info['mobile'])) {
                return '手机号码格式不正确哦！'
            }
            if (info['email'] && !ui.verify.email(info['email'])) {
                return '邮箱地址格式不正确哦！'
            }
        }
    })

    //导出
    $('#exportModal').on('click', '#exportBtn', function () {
        var info = $('#exportModal form').serializeJson()
        var data = [];
        if (info['deptid'] && info['deptid'] > 0)
            data['deptid'] = info['deptid'];
        if (info['mid'] && info['mid'] > 0)
            data['mid'] = info['mid'];
        ui.openUrl(homePath + '/admin/union/user/export.html', data)
    })

    // modal下拉监听
    $("select[name=deptid]").change(function () {
        var deptid = $('select[name=deptid] option:selected').val();
        if(!deptid || deptid < 0)
            return ;
        onSelect('union/user/api/list_market.json', {deptid: deptid}, $("select[name=mid]"))
    })

    /**
     *
     * @param url
     * @param data
     * @param $select
     * @param id 返回json数据 对应的value
     * @param name
     */
    function onSelect(url, data, $select, id, name) {
        if (!id) id = 'id'
        if (!name) name = 'name'
        ui.post(url, data, function (data) {
            var len = data.length;
            var deptHtml = "";
            deptHtml += "<option value=\"-1\">请选择</option>"
            for (var i = 0; i < len; i++)
                deptHtml += "<option value=\"" + data[i][id] + "\">" + data[i][name] + "</option>"
            $select.html(deptHtml)
        })
    }

    function clearSelect($select) {
        $select.html("<option value='-1'>请选择</option>")
    }
    // end

</script>
