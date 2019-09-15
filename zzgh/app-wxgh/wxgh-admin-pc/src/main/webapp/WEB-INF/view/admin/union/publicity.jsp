<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css">
<link rel="stylesheet" href="${home}/libs/viewer/viewer.min.css">
<style>
    td > img {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-plus-se"></span>
                    添加宣传</a>
                <a id="pushBtn" class="btn btn-empty-theme"><span class="fa fa-send-o"></span> 批量推送</a>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>封面图片</th>
                    <th>公告标题</th>
                    <th>添加时间</th>
                    <th>公告类型</th>
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
                    <label>公告类型</label>
                    <select class="form-control" name="type">
                        <option value="txtimg">自定义</option>
                        <option value="url">链接</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>公告名称</label>
                    <input name="name" type="text" class="form-control">
                </div>
                <input type="hidden" name="id">
                    <div id="contentTxt" class="form-group">
                        <label>公告内容</label>
                        <script id="lawContent" type="text/plain" style="width:100%;height:240px;"></script>
                    </div>
                    <div id="contentUrl" class="form-group" style="display: none;">
                        <label>公告链接</label>
                        <input class="form-control" type="text" name="content">
                    </div>
                        <div class="form-group">
                        <label>是否置顶</label>
                        <select class="form-control" name="top">
                        <option value="0">不置顶</option>
                        <option value="1">置顶</option>
                        </select>
                        </div>
                <div class="form-group">
                        <label>封面图片</label>
                    <%--<div id="fileDiv" class="ui-file-div">--%>
                        <%--<div class="ui-file-item">--%>
                        <%--<img src="${home}/image/xlkai/icon_add1.png"/>--%>
                        <%--<input id="fileInput" type="file" accept="image/*" name="imgfile">--%>
                        <%--</div>--%>
                    <%--</div> --%>
                        <div id="imgUpload"></div>
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
                    <script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"/>
                    <script src="${home}/libs/viewer/viewer-jquery.min.js"></script>
                    <script type="text/template" id="lawItem">
                        <tr>
                            <td><input class="ui-check" type="checkbox"></td>
                            <td><img src="{{=it.picture}}"></td>
                            <td>{{=it.name}}</td>
                            <td>{{=it.time}}</td>
                            <td>{{=it.typeName}}</td>
                            <td class="ui-table-operate">
                                <div class="ui-link-group">
                                    <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                                            class="fa fa-edit"></span> 编辑</a>
                                    <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                                    <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>
                                </div>
                            </td>
                        </tr>
                    </script>
                    <script>
                        $(function () {
                            var __self = 'union/api/'

                            var $form = $('#addForm')
                            var $addBtn = $('#addLawBtn')

                            var $contentUrl = $('#contentUrl')
                            var $contentTxt = $('#contentTxt')

//                            var $fileDiv = $('#fileDiv')
//                            var $fileInputs = $('#fileInput')

                            var upload = ui.uploader("#imgUpload")

                            ui.check.init()
                            var lawTable = ui.table('law', {
                                dataConver: function (d) {
                                    d['typeName'] = d.type == 'url' ? '链接' : '自定义'
                                    d['time'] = new Date(d.time).format('yyyy-MM-dd hh:mm')
                                    var img = d.picture
                                    if (!img) {
                                        img = homePath + '/image/common/nopic.gif'
                                    } else {
                                        img = ui.get_image(d.picture)
                                    }
                                    d['picture'] = img
                                    return d;
                                },
                                req: {
                                    url: __self+'publicitylist.json',
                                    data: {action: 'list'}
                                },
                                empty: {
                                    col: 6,
                                    html: '暂未发布宣传公告哦'
                                },
                                success: function () {
                                    $('td>img').viewer()
                                }
                            }, function ($item, d) {
                                $item.data('data', d)

                                //删除
                                $item.on('click', '.ui-del', function () {
                                    ui.del(d.id, function () {
                                        ui.alert('删除成功！')
                                        $item.remove()
                                    }, __self + 'publicitydel.json')
                                })

                                //编辑
                                $item.on('click', '.ui-edit', function () {
                                    $form.data('data', d)
                                })

                                //查看
                                $item.on('click', '.ui-see', function () {
                                    if (d.type == 'url') {
                                        ui.openWindow(d.content, true)
                                    } else {
                                        ui.openWindow('/publicity/info.html?id=' + d.id, true)
                                    }
                                })

                                $item.on('shown.bs.tab', '.ui-edit', function () {
                                    $addBtn.text('确定编辑')
                                    $('.edui-container').css('width', $form.width())
                                    var d = $form.data('data')
                                    if (d) {
                                        $('select[name=type]').attr("disabled", true);
                                        var type = d['type']
                                        for (var k in d) {
                                            console.log(d[k])
                                            if (k == 'content') {
                                                if (type == 'url') {
                                                    $contentTxt.hide()
                                                    $contentUrl.show()
                                                    $("input[name='" + k + "']").val(d[k])
                                                } else {
                                                    $contentTxt.show()
                                                    $contentUrl.hide()
                                                    console.log(um)
                                                    if (!um) {
                                                        um = UM.getEditor('lawContent');
                                                        um.setContent(d[k])
                                                        $('.edui-container').css('width', $form.width())
                                                    }
                                                }
                                            } else if (k == 'type') {
                                                $("select[name='" + k + "']").find("option[value='" + d[k] + "']").prop('selected', true)
                                            } else if (k == 'picture') {
                                                create_file_item(d[k], $fileDiv, $fileInputs[0])
                                            } else {
                                                $("input[name='" + k + "']").val(d[k])
                                            }
                                        }
                                    }
                                })
                            })
                            lawTable.init()

                            var um;
                            $("a[href='#mainPane']").on('shown.bs.tab', function () {
                                $form.removeData('data')
                                $form.clearForm()
                                um.setContent('')
                                $('select[name=type]').removeAttr("disabled");
                                $('select[name=type]').find('option[value=txtimg]').prop("selected", true);
                                $contentTxt.show()
                                $contentUrl.hide()
                                $fileDiv.find('.ui-file-item:not(:first)').remove()
                            })

                            $('select[name=type]').on('change', function () {
                                if ($(this).val() == 'url') {
                                    $contentTxt.hide()
                                    $contentUrl.show()
                                } else {
                                    $contentTxt.show()
                                    $contentUrl.hide()
                                }
                            })

                            $('#pushBtn').on('click', function () {
                                var ids = lawTable.get_checked_ids()
                                if (ids.length <= 0) {
                                    ui.alert('请选择需要推送的公告哦！')
                                    return
                                }
                                if (ids.length > 8) {
                                    ui.alert('每次推送不能超过8条公告哦')
                                    return
                                }
                                ui.confirm('是否推送？', function () {
                                    ui.post(__self + "push.json", {action: 'push', id: ids.toString()}, function () {
                                        ui.alert('推送成功！')
                                        $('table.ui-table').find('input[type=checkbox]').prop('checked', false)
                                    })
                                })
                            })

//                            $fileInputs.on('change', function () {
//                                var self = this
//                                $fileDiv.find('.ui-file-item:not(:first)').remove()
//                                if (self.files.length > 0) {
//                                    var file = self.files[0]
//                                    var reader = new FileReader()
//                                    reader.onload = function () {
//                                        create_file_item(this.result, $fileDiv, self)
//                                    }
//                                    reader.readAsDataURL(file)
//                                }
//                            })

                            $("a[href='#addPane']").on('shown.bs.tab', function () {
                                if (!um) {
                                    um = UM.getEditor('lawContent');
                                    $('.edui-container').css('width', $form.width())
                                }

                                $addBtn.text('确定添加')
                            })

                            //批量删除
                            $('#cateDelBtn').on('click', function () {
                                var ids = lawTable.get_checked_ids()
                                if (ids.length <= 0) {
                                    ui.alert('请选择需要删除的宣传公告！')
                                    return
                                }
                                ui.del(ids, function () {
                                    ui.alert('删除成功！',function(){
                                        lawTable.request(null,lawTable)
                                    })
                                }, __self + 'publicitydel.json')
                            })

                            $addBtn.on('click', function () {
                                var info = $form.serializeJson()
                                if (info.type == 'txtimg') {
                                    info['content'] = um.getContent()
                                }
                                var verifyRes = verify(info)
                                if (verifyRes) {
                                    ui.alert(verifyRes)
                                    return;
                                }
                                info['action'] = 'add'
//                                var files = $fileInputs[0].files
//                                if (files.length > 0) {
//                                    $.ajaxFileUpload({
//                                        url: homePath + __self,
//                                        data: info,
//                                        secureuri: false,
//                                        fileElementId: 'fileInput',
//                                        dataType: 'json',
//                                        success: function (res) {
//                                            if (res.ok) {
//                                                ui.alert(info['id'] ? '编辑成功！' : '添加成功！')
//                                                ui.refresh()
//                                            } else {
//                                                ui.alert(info['id'] ? '编辑失败' : '添加失败')
//                                            }
//                                        }
//                                    });
//                                } else {
//                                    ui.post(__self, info, function () {
//                                        ui.alert(info['id'] ? '编辑成功！' : '添加成功！')
//                                        ui.refresh()
//                                    })
//                                }
                                var url = __self+"publicityapply.json"
                                if($(".ui-file-item").length==2){
                                    upload.upload(function (fileIds) {
                                        console.log(fileIds)
                                        Ids = fileIds.toString();
                                        info['picture'] = Ids;
                                        ui.post(url, info, function () {
                                            ui.alert(info['id'] ? '编辑成功！' : '添加成功！',function(){
                                                ui.history.go('union/publicity')
                                            })
                                        })
                                    })
                                }else{
                                    ui.post(url, info, function () {
                                        ui.alert(info['id'] ? '编辑成功！' : '添加成功！',function(){
                                            ui.history.go('union/publicity')
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
                                    return '公告名称不能为空'
                                }
                                if (info['type'] == 'txtimg' && !um.hasContents()) {
                                    return '公告内容不能为空'
                                }
                                if (info['type'] == 'url' && !ui.verify.url(info['content'])) {
                                    return '公告链接不合法'
                                }
                            }

                            function create_file_item(img, $div, self) {
                                var $item = $('<div class="ui-file-item">' +
                                    '<img src="' + img + '"/><span class="fa fa-close"></span></div>')
                                $div.append($item)

                                $item.on('click', '.fa-close', function () {
                                    ui.clearFile(self)
                                    $(this).parent().remove()
                                })
                            }
                        })
                    </script>

