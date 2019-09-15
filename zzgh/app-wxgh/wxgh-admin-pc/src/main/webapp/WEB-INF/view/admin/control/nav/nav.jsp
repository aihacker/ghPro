<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/10
  Time: 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .form-group.two {
        display: none;
    }
</style>
<div class="row">
    <div class="ui-content">
        <div class="ui-link-group">
            <a data-toggle="modal" data-target="#addCateModal" class="btn btn-empty-theme"><span
                    class="fa fa-plus"></span> 添加菜单</a>
        </div>

        <div style="margin-top: 20px;">
            <ul id="cateNav" class="nav nav-tabs">
                <c:forEach items="${cates}" var="c" varStatus="i">
                    <li data-id="${c.id}" class="${i.first?'active':''}">
                        <a title="${c.info}" href="javascript:;">${c.name} <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <table class="table ui-table">
            <thead>
            <tr>
                <th><input class="ui-check-all" type="checkbox"/></th>
                <th>菜单名称</th>
                <th>菜单链接</th>
                <th>菜单图标</th>
                <th>菜单类别</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="myList">
            </tbody>
        </table>
    </div>
</div>

<div id="addCateModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加菜单</h4>
            </div>
            <div class="modal-body">
                <form id="cateAddForm">
                    <div class="form-group">
                        <label>菜单类型</label>
                        <select name="navType" class="form-control">
                            <option value="0">一级菜单</option>
                            <option value="1">二级菜单</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Nav菜单</label>
                        <select name="cateId" class="form-control">
                            <option value="-1">请选择</option>
                            <c:forEach items="${cates}" var="c">
                                <option value="${c.id}">${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group two">
                        <label>一级菜单</label>
                        <select name="parentId" class="form-control">
                            <option value="-1">请选择</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>名&nbsp;&nbsp;称</label>
                        <input type="text" name="name" class="form-control" placeholder="菜单名称">
                    </div>
                    <div class="form-group two">
                        <label>链&nbsp;&nbsp;接</label>
                        <input type="text" name="url" class="form-control" placeholder="菜单链接">
                    </div>
                    <div class="form-group one">
                        <label>图&nbsp;&nbsp;标
                            <small class="ui-text-info" style="font-weight: 500;">（二级菜单无图标）</small>
                        </label>
                        <input type="text" name="icon" class="form-control" placeholder="菜单链接">
                    </div>
                    <div class="form-group">
                        <label>介&nbsp;&nbsp;绍</label>
                        <textarea class="form-control" name="info" maxlength="200" rows="3"
                                  placeholder="菜单介绍"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="saveCateBtn" type="button" class="btn btn-theme">保存</button>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="myItem">
    <tr>
        <td><input class="ui-check" type="checkbox"/></td>
        <td title="{{=it.info}}">{{=it.name}}</td>
        <td>{{=it.url}}</td>
        <td><span class="fa fa-{{=it.icon}}"></span></td>
        <td title="{{=it.tip}}">{{=it.cateName}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>

<script>
    $(function () {
        var $form = $('#cateAddForm')
        $form.form()
        ui.check.init()

        var cateId = $('#cateNav li[data-id]').eq(0).data('id')

        var myTable = ui.table('my', {
            req: {
                url: 'control/nav/api/nav_list.json',
                data: {cateId: cateId}
            },
            dataConver: function (d) {
                d['info'] = d.info ? d.info : '暂无介绍'
                d['url'] = d.url ? d.url : '无'
                if (d.parentId == 0) {
                    d['cateName'] = '一级菜单'
                    d['tip'] = ''
                } else {
                    d['cateName'] = '二级菜单'
                    d['tip'] = '父级菜单：' + d.parentName
                }
                return d
            },
            hasPage: false,
            empty: {
                col: 5,
                html: '暂无菜单分类哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'control/nav/api/del_nav.json')
            })
        })
        myTable.init()

        var $cateNav = $('#cateNav')
        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                myTable.refresh({cateId: $self.data('id')}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

        var $navType = $('select[name=navType]')
        $navType.on('change', function () {
            var $self = $(this)
            if ($self.val() == 0) { //一级菜单
                $form.find('.form-group.two').hide()
                $form.find('.form-group.one').show()
            } else {
                $form.find('.form-group.one').hide()
                $form.find('.form-group.two').show()
            }
        })
        var $navParent = $('select[name=parentId]')
        $('select[name=cateId]').on('change', function () {
            var navType = $navType.val()
            if (navType == 1) {
                var $self = $(this)
                var val = $self.val()
                get_one_nav(val, function (navs) {
                    $navParent.find('option').not('[value=-1]').remove()
                    for (var i in navs) {
                        var $option = $('<option></option>')
                        $option.text(navs[i].name)
                        $option.attr('value', navs[i].value)
                        $navParent.append($option)
                    }
                })
            }

        })

        $('#saveCateBtn').on('click', function () {
            var info = $form.serializeJson()
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }
            if (info['navType'] == 0) { //一级菜单
                info['url'] = ''
                info['parentId'] = 0
            } else {
                info['icon'] = ''
            }
            ui.post('control/nav/api/add_nav.json', info, function () {
                $('#addCateModal').modal('hide')
                $form.clearForm()
                ui.alert('添加成功', function () {
                    myTable.request(null, myTable)
                })
            })
        })

        function verify(info) {
            if (!info['name']) {
                return '菜单名称不能为空哦'
            }
            if (info['cateId'] == -1) {
                return '请选择Nav菜单'
            }
            var navType = info['navType']
            if (navType == 0 && !info['icon']) {
                return '一级菜单图标不能为空'
            }

            if (navType == 1 && info['parentId'] == -1) {
                return '请选择父级菜单'
            }
        }

        function get_one_nav(cateId, func) {
            ui.get('control/nav/api/one_nav_list.json', {cateId: cateId}, function (navs) {
                if (func) func(navs)
            })
        }
    })
</script>
