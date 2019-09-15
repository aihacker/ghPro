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
    .ui-law-order {
        width: 120px;
    }

    .ui-law-order > input {
        height: 30px;
        padding: 0 12px;
    }
    .ui-law-order > div {
        height: 30px;
        padding: 0 12px;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-plus-se"></span>
                    添加法律法规</a>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th title="序号越小，排序越前">排序序号</th>
                    <th>法律名称</th>
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
<script type="text/javascript" src="${home}/libs/umeditor1.2.3-utf8-jsp/third-party/template.min.js"></script>
<script type="text/javascript" src="${home}/libs/umeditor1.2.3-utf8-jsp/umeditor.config.js"></script>
<script type="text/javascript" src="${home}/libs/umeditor1.2.3-utf8-jsp/umeditor.js"></script>
<script type="text/javascript" src="${home}/libs/umeditor1.2.3-utf8-jsp/lang/zh-cn/zh-cn.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <%--<td class="ui-law-order"><input class="form-control" type="number" data-val="{{=it.sortId}}"--%>
                                        <%--value="{{=it.sortId}}"/></td>--%>
        <td class="ui-law-order">{{=it.sortId}}</td>
        <td>{{=it.name}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
               <!-- <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>-->
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
        var lawTable = ui.table('law', {
            req: {
                url: __self+'lawlist.json',
            },
            empty: {
                col: 4,
                html: '暂未发布法律法规哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, 'union/api/lawdel.json')
            })

            //编辑
            $item.on('click', '.ui-edit', function () {
                $form.data('data', d)
            })

            $item.on('shown.bs.tab', '.ui-edit', function () {
                $('.edui-container').css('width', $form.width())
                var d = $form.data('data')
                if (d) {
                    $('input[name=name]').val(d['name'])
                    $('input[name=sortId]').val(d['sortId'])
                    $('input[name=id]').val(d['id'])
                    um.setContent(d['content'])
                    $addBtn.text('确定编辑')
                }
            })

            //排序序号
            $item.find('.ui-law-order input').on('blur', function () {
                var $self = $(this)
                var val = $self.val()
                if (val != $self.data('val')) {
                    var info = {
                        action: 'add',
                        sortId: val,
                        id: d.id
                    }
                    ui.post(__self, info, function () {
                    }, function () {
                        ui.alert('编辑失败！')
                    })
                }
            })
        })
        lawTable.init()

        var um = UM.getEditor('lawContent', {
            /* 传入配置参数,可配参数列表看umeditor.config.js */
            toolbar: [
                'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
                'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
                '| justifyleft justifycenter justifyright justifyjustify |',
                'link unlink | emotion image video ',
                '| horizontal print preview fullscreen', 'drafts', 'formula'
            ]
        });

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $form.removeData('data')
            $form.clearForm()
            um.setContent('')
        })

        $("a[href='#addPane']").on('shown.bs.tab', function () {
            $('.edui-container').css('width', $form.width())
            $addBtn.text('确定添加')
        })

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的法律法规！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function(){
                    lawTable.request(null,lawTable)
                })

            }, __self+'lawdel.json')
        })

        $addBtn.on('click', function () {
            var info = $form.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return;
            }
            var cnt = um.getContent();
            info['content'] = cnt
            info['action'] = 'add'
            if (!info['sortId']) info['sortId'] = 0
            if($addBtn.text() == "确定添加"){
                ui.post(__self+"addlaw.json", info, function () {
                    ui.alert("添加成功",function () {
                        if ($('#addPane').hasClass('active')) {
                            $("a[href='#mainPane']").tab('show')
                        } else {
                            ui.history.go('union/suggest')
                        }
                        lawTable.request(null,lawTable)
                    })

                })
            }else{
                ui.post(__self+"update.json", info, function () {
                    ui.alert('编辑成功！',function () {
                        if ($('#addPane').hasClass('active')) {
                            $("a[href='#mainPane']").tab('show')
                        } else {
                            ui.history.go('union/suggest')
                        }
                        lawTable.request(null,lawTable)
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
            if (!info['name']) {
                return '法律法规名称不能为空'
            }
            if (!um.hasContents()) {
                return '法律法规内容不能为空'
            }
        }
    })
</script>

