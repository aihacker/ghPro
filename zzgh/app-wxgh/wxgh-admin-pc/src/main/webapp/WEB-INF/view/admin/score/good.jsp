<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a id="goodDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a href="#addPane" id="add" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-plus-square-o"></span> 新增商品</a>
            </div>
            <div style="margin-top: 20px;">
                <ul id="goodNav" class="nav nav-tabs">
                    <li data-type="-1" class="active">
                        <a href="javascript:;">全部 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-type="1">
                        <a href="javascript:;">健步商品 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-type="2">
                        <a href="javascript:;">个人积分商品 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                </ul>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>商品名称</th>
                    <th>商品图片</th>
                    <th>兑换积分</th>
                    <th>商品介绍</th>
                    <th>添加时间</th>
                    <th>商品类型</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="goodList">
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
                <div class="form-group" style="margin-top: 20px;">
                    <label>商品类型</label>
                    <select name="type" class="form-control">
                        <option value="-1">请选择</option>
                        <option value="1">健步积分</option>
                        <option value="2">个人积分</option>
                    </select>
                </div>
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>商品名称
                        <small>（必填）</small>
                    </label>
                    <input name="name" type="text" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>商品积分
                        <small>（必填）</small>
                    </label>
                    <input name="points" type="number" class="form-control"/>
                </div>
                <div class="form-group">
                    <label>商品介绍</label>
                    <textarea name="info" class="form-control" rows="2" maxlength="100"></textarea>
                    <small class="pull-right"></small>
                </div>

                <div class="ui-file-div">
                    <%--<div class="ui-file-item">--%>
                        <%--&lt;%&ndash;<img src="${home}/image/xlkai/icon_add1.png"/>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input id="fileInput" type="file" accept="image/*" name="imgfile">&ndash;%&gt;--%>
                    <%--</div>--%>
                </div>

                <div class="form-group">
                    <button id="editSaveContinueBtn" type="button" class="btn btn-theme">保存继续添加</button>
                    <button id="editSaveBtn" type="button" class="btn btn-default">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/template" id="goodItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.name}}</td>
        <td><img class="ui-viewer-img" src="{{=it.img}}"></td>
        <td>{{=it.points}}</td>
        <td>{{=it.info}}</td>
        <td>{{=it.addTime}}</td>
        <td>{{=it.typename}}</td>
        <td>
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/viewer/viewer-jquery.min.js"></script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
        var __self = 'score/api/'

        var $goodNav = $('#goodNav')

        var upload = "";

        var type = -1

        ui.check.init()

        var $addFrom = $('#addPane form')

        //获取列表
        var goodTable = ui.table("good",{
            req:{
                url:__self+"goodslist.json",
                data:{
                    type:type
                }
            },
            dataConver:function(d){
                d.addTime = new Date(d.addTime).format("yyyy-MM-dd hh:mm")
                d.typename = get_type(d.type)
                return d
            },
            empty:{
                col:8,
                html:"暂时没有商品"
            }
        },function($item,d){
            $item.data('data',d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！',function(){
                        $item.remove()
                    })
                }, __self + 'goodsdel.json')
            })

            //编辑
            $item.on('click','.ui-edit',function(){
                $("#editSaveContinueBtn").hide()
                for (var k in d) {
                    if (k == 'path') {
                        create_file_item("/wxgh"+d[k])
                    }
                    else if (k == 'type') {
                        $addFrom.find('select[name=' + k + '] option[value=' + d[k] + ']').attr('selected', true)
                    } else {
                        $addFrom.find('input[name=' + k + ']').val(d[k])
                        $addFrom.find('textarea[name=' + k + ']').val(d[k])
                    }
                }

                $addFrom.on('click', 'button[id]', function () {
                    var selfId = $(this).attr('id')
                    var info = $addFrom.serializeJson()
                    info.id = d.id

                    var url = __self+"goodschange.json"
                    if($(".ui-file-item").length==2){
                        upload.upload(function (fileIds) {
                            info.img = fileIds.toString()
                            ui.post(url, info, function () {
                                addSuccess(selfId,2)
                            })
                        })
                    }else{
                        ui.post(url, info, function () {
                            addSuccess(selfId,2)
                        })
                    }
                })

                function create_file_item(img){
                    var $item = $('<div class="ui-file-item">' +
                        '<img src="' + img + '"/><span class="fa fa-close"></span></div>')
                    $(".ui-file-div").html("").append($item)

                    $item.on('click', '.fa-close', function () {
                        $(this).parent().remove()
                        $(".ui-file-div").html("").append("<div id='img'></div>")
                        upload = ui.uploader("#img")
                    })
                }
            })
        })

        goodTable.init()

        //批量删除
        $('#goodDelBtn').on('click', function () {
            var ids = goodTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择删除的商品哦！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function(){
                    goodTable.request(null, goodTable)
                })
            }, __self + 'goodsdel.json')
        })

        $goodNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $goodNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')
                goodTable.refresh({type: $self.data('type')}, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

        //添加商品
        $("#add").on("click",function () {
            $("#editSaveContinueBtn").show()
            $(".ui-file-div").append("<div id='img'></div>")
            upload = ui.uploader("#img")

            $addFrom.form()

            $addFrom.on('click', 'button[id]', function () {
                var selfId = $(this).attr('id')
                var info = $addFrom.serializeJson()
                var verifyRes = verify(info)
                if (verifyRes) {
                    ui.alert(verifyRes)
                    return
                }
                var url = __self + "goodsadd.json"
                if ($(".ui-file-item").length == 2) {
                    upload.upload(function (fileIds) {
                        info.img = fileIds.toString()
                        ui.post(url, info, function () {
                            addSuccess(selfId,1)
                        })
                    })
                }else{
                    ui.alert("请先上传商品图片")
                }
            })
        })


        function addSuccess(selfId,type) {
            if(type == 1){
                ui.alert('添加成功！')
            }else{
                ui.alert("修改成功！")
            }

            if (selfId == 'editSaveBtn') {
                ui.history.go("score/good")
            } else {
                $addFrom.clearForm()
            }
        }

        $("a[href='#mainPane']").on('show.bs.tab', function () {
            ui.history.go("score/good")
        })


        /**
         * 表单验证
         * @param info
         * @returns {*}
         */
        function verify(info) {
            if (info['type'] == -1) {
                return '请选择商品类型'
            }
            if (!info['name']) {
                return '其商品名称不能为空哦！'
            }
            if (!info['points']) {
                return '商品积分不能为空哦！'
            }
        }

        function get_type(type) {
            var str;
            if (type == 1) {
                str = '健步积分'
            } else if (type == 2) {
                str = '个人积分'
            }
            return str
        }

    })
</script>