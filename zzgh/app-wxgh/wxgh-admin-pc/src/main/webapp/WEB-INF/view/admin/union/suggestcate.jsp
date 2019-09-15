<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css">
<style>

</style>
<div class="row">
    <div class="ui-content ui-link-group">
        <button type="button" class="btn btn-empty-theme ui-back"><span
                class="fa fa-angle-double-left"></span> 返回
        </button>
        <a href="#mainPane" data-toggle="tab" class="hidden"></a>
    </div>
</div>
<div class="row" style="margin-top: 20px;">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-plus-se"></span>
                    添加分类</a>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>分类名称</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="cateList">
                </tbody>
            </table>

            <ul class="pagination" id="catePage"></ul>
        </div>
        <div class="tab-pane" id="addPane">
            <form id="addForm" class="col-md-4 col-md-offset-4" style="margin-top: 20px;">
                <div class="form-group">
                    <label>分类名称</label>
                    <input name="name" type="text" class="form-control">
                </div>
                <input type="hidden" name="id">
                <div class="form-group">
                    <button id="addLawBtn" type="button" class="btn btn-theme">确定添加</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="cateItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.name}}</td>
        <td style="text-align: right">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var __self = 'union/api/'

        var $form = $('#addForm')
        var $addBtn = $('#addLawBtn')

        ui.check.init()
        var cateTable = ui.table('cate', {
            req: {
                url: __self+"suggestcatelist.json",
                data: {action: 'list'}
            },
            empty: {
                col: 3,
                html: '暂无分类哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！',function () {
                        $item.remove()
                    })
                }, __self + 'suggestcatedel.json')
            })

            //编辑
            $item.on('click', '.ui-edit', function () {
                $form.data('data', d)
                if (d) {
                    $('input[name=name]').val(d['name'])
                    $('input[name=id]').val(d['id'])
                    $addBtn.text('确定编辑')
                }
            })

            //编辑tab
            $item.on('shown.bs.tab', '.ui-edit', function () {
                var d = $form.data('data')
            })
        })
        cateTable.init()

        //返回按钮
        $('.ui-back').click(function () {
            if ($('#addPane').hasClass('active')) {
                $("a[href='#mainPane']").tab('show')
            } else {
                ui.history.go('union/suggest')
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
            var ids = cateTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的提案分类！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function () {
                    cateTable.request(null, cateTable)
                })
            }, __self + 'suggestcatedel.json')
        })

        $addBtn.on('click', function () {
            var info = $form.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return;
            }
            info['action'] = 'add'
            if($addBtn.text()=="确定添加"){
                ui.post(__self+"suggestcateadd.json", info, function () {
                    ui.alert(info['id'] ? '编辑成功！' : '添加成功！',function(){
                        if ($('#addPane').hasClass('active')) {
                            $("a[href='#mainPane']").tab('show')
                        } else {
                            ui.history.go('union/suggest')
                        }
                        cateTable.request(null, cateTable)
                    })
                })
            }else{
                ui.post(__self+"suggestcatedit.json", info, function () {
                    ui.alert(info['id'] ? '编辑成功！' : '添加成功！',function(){
                        if ($('#addPane').hasClass('active')) {
                            $("a[href='#mainPane']").tab('show')
                        } else {
                            ui.history.go('union/suggest')
                        }
                        cateTable.request(null, cateTable)
                    })
                })
            }
        })

        /**
         * 表单验证
         * @param info
         * @returns {*}
         */
        function verify(info) {
            if (!info.name) {
                return '分类名称不能为空'
            }
        }
    })
</script>

