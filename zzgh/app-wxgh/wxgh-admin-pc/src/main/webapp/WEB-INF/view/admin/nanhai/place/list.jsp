<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>场馆名称</th>
                    <th>详细地址</th>
                    <th>手机号码</th>
                    <th>场馆项目</th>
                    <th>价格</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="cateList">
                </tbody>
            </table>
            <ul class="pagination" id="pagination"></ul>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <form class="col-md-4 col-md-offset-4" style="margin-top: 20px;">
            </form>
        </div>
    </div>
</div>

<script type="text/template" id="cateItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.title}}</td>
        <td>{{=it.address}}</td>
        <td>{{=it.phone}}</td>
        <td>{{=it.typeName}}</td>
        <td>{{=it.price}}元</td>
        <td>
            <div class="ui-link-group">
                <a href="${home}/admin/nanhai/place/placeedit.html?id={{=it.id}}" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>


<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function(){
        var __self = "nanhai/place/api/"

        ui.check.init();

        var $addFrom = $('#addPane form')
        $addFrom.form()

        var cateTable = ui.table("cate",{
            req:{
                url:__self+"placelist.json"
            },
            dataConver:function (d) {
                d['statusName'] = get_status(d.status)
                return d
            },
            empty: {
                col: 8,
                html: '暂未发布'
            }
        },function($item,d){
            $item.data('data',d)

            //delete
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'placedel.json')
            })

            //edit
            $item.on('click','.ui-edit',function(){
                for (var k in d) {
                    if (k == 'imgPath') {
                        create_file_item(d[k])
                    } else if (k == 'status') {
                        $addFrom.find('select[name=' + k + '] option[value=' + d[k] + ']').attr('selected', true)
                    } else {
                        $addFrom.find('input[name=' + k + ']').val(d[k])
                        $addFrom.find('textarea[name=' + k + ']').val(d[k])
                    }
                }
                $("#editSaveBtn").click(function(){
                    var info = $addFrom.serializeJson()
                    info.id = d.id

                    var url = __self+"catechange.json"
                    if($(".ui-file-item").length==2){
                        upload.upload(function (fileIds) {
                            info.imgPath = fileIds.toString()
                            ui.post(url, info, function () {
                                ui.alert('修改成功！',function(){
                                    ui.history.go('nanhai/place/cate')
                                })
                            })
                        })
                    }else{
                        ui.post(url, info, function () {
                            ui.alert('修改成功！',function(){
                                ui.history.go('nanhai/place/cate')
                            })
                        })
                    }
                })

                function create_file_item(img){
                    var $item = $('<div class="ui-file-item">' +
                        '<img src="' + img + '"/><span class="fa fa-close"></span></div>')
                    $(".ui-file-div").html("").append($item)

                    $item.on('click', '.fa-close', function () {
                        $(this).parent().remove()
                        $(".ui-file-div").html("").append("<div id='coverImg'></div>")
                        upload = ui.uploader("#coverImg")
                    })
                }
            })
        })

        cateTable.init();

        var $cateNav = $("#cateNav");
        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                cateTable.refresh({status: $self.data('status')}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

        function get_status(status) {
            var str;
            if (status == 1) {
                str = '可用'
            } else if (status == 0) {
                str = '不可用'
            }
            return str
        }

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = cateTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的项目！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function(){
                    cateTable.request(null, cateTable)
                })
            }, __self + 'placedel.json')
        })

        //back
        $("a[href='#mainPane']").on('show.bs.tab', function () {
            ui.history.go("nanhai/place/cate")
        })

        $(".add").click(function(){
            ui.history.go("nanhai/place/cateadd")
        })

    })
</script>

