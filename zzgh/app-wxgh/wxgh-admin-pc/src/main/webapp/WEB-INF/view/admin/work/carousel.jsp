<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
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

    .ui-img-div {
        height: 200px;
        overflow: hidden;
        border: 1px solid #ccc;
        position: relative;
    }

    .ui-img-div img {
        width: 100%;
    }

    #prevInfo {
        line-height: 30px;
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 30px;
        margin: 0;
        text-align: left;
        text-indent: 12px;
        opacity: .8;
        background-color: #000;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-add"><span
                        class="fa fa-plus-se"></span>
                    添加图片</a>
                <%--<a id="pushBtn" class="btn btn-empty-theme"><span class="fa fa-send-o"></span> 批量推送</a>--%>
            </div>

            <div style="margin-top: 20px;">
                <ul id="cateNav" class="nav nav-tabs">
                    <li data-type="1" class="active">
                        <a href="javascript:;">岗位创新 <i
                                class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-type="2">
                        <a href="javascript:;">女工园地 <i
                                class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                </ul>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>图片</th>
                    <th>摘要</th>
                    <th>添加时间</th>
                    <th>是否显示</th>
                    <th title="序号越小，排序越前">排序序号</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="lawList">
                </tbody>
            </table>
            <ul class="pagination" id="lawPage"></ul>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 5%">
                <div class="btn btn-empty-theme ui-back"><span
                        class="fa fa-chevron-circle-left"></span> 返回
                </div>
            </div>
            <form id="addForm" class="col-lg-7 col-md-7 ui-margin-top-20" enctype="multipart/form-data">
                <div class="form-group">
                    <label>是否显示</label>
                    <select class="form-control" name="display">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>图片类型</label>
                    <select class="form-control" name="type">
                        <option value="1">岗位创新</option>
                        <option value="2">女工园地</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>封面图片</label><br/>
                    <input name="img" id="uploadNewsImg" accept="image/*" class="ui-inline-block" type="file"/>

                    <div class="ui-inline-block" style="margin-left: 10px;">推荐尺寸：900像素x500像素</div>
                </div>
                <div class="form-group">
                    <label>图片摘要</label>
                    <small class="text-danger"></small>
                    <input name="briefInfo" type="text" class="form-control">
                </div>
                <%--<div id="contentTxt" class="form-group">--%>
                <%--<label>公告内容</label>--%>
                <%--<script id="lawContent" type="text/plain" style="width:100%;height:240px;"></script>--%>
                <%--</div>--%>
                <%--<div id="contentUrl" class="form-group" style="display: none;">--%>
                <%--<label>公告链接</label>--%>
                <%--<input class="form-control" type="text" name="content">--%>
                <%--</div>--%>
                <div class="form-group">
                    <label>排序序号</label>
                    <small style="font-weight: 500;">（序号越小，排名越前哦）</small>
                    <input name="sortId" type="number" class="form-control" placeholder="默认为 0" value="0">
                </div>
                <div class="form-group">
                    <button id="addLawBtn" type="button" class="btn btn-theme">确定添加</button>
                </div>
            </form>
            <div class="col-lg-5 col-md-5" style="margin-top:5%">
                <div class="ui-img-div">
                    <img id="prevImag" src="${home}/image/di/notice/icon_preview.png"/>
                    <p id="prevInfo"></p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<script src="${home}/libs/viewer/viewer-jquery.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td><img src="${home}{{=it.img}}"></td>
        <td>{{=it.briefInfo}}</td>
        <td>{{=it.addTime}}</td>
        <td>{{=it.type}}</td>
        <td>{{=it.sortId}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                <!--<a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>-->
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var __self = "work/api/";

        ui.check.init()

        //上传图片
        var $preImage = $('#prevImag')
        var $prevInfo = $("#prevInfo")
        $('#uploadNewsImg').on('change', function (e) {
            var files = e.target.files;
            if (files && files.length > 0) {
                if (typeof FileReader != 'undefined') {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[0]);
                    reader.onload = function () {
                        $preImage.attr('src', this.result)
                    }
                }
            } else {
                $preImage.attr('src', homePath + '/image/party/icon_preview.png')
            }
        })

        //图文 摘要
        $('input[name=digest]').keyup(function () {
            var val = $(this).val();
            $prevInfo.text(val)

            var $prev = $(this).prev();
            if (val.length > 20) {
                $(this).val(val.substring(0, 20))
                $prev.removeClass('hidden')
                $prev.text('标题不能超过20字符哦');
            } else {
                $prev.addClass('hidden')
            }
        })

        //list
        var lawTable = ui.table('law', {
            req: {
                url: __self + 'piclist.json',
                data: {type: 1}
            },
            dataConver: function (d) {
                d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                d['type'] = getType(d.display)
                return d
            },
            empty: {
                col: 8,
                html: '暂无图片'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.find('.ui-suggest-title p').ellipsis({row: 2})

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'picdel.json')
            })

            //审核
            $item.on('click', '.dropdown-menu li', function () {
                var $self = $(this)
                var status = $self.data('status')
                var msg = $.trim($self.text())
                ui.confirm('是否' + msg + '审核？', function () {
                    var info = {
                        atlId: d.atlId,
                        status: status
                    }
                    apply_suggest(info, $item)
                })
            })

            //查看
            $item.on('click', '.ui-edit', function () {
                var id = d.id
                ui.history.go("work/edit",{id:id})
//
//                var url = __self + "piclist.json"
//                ui.get(url, {id: id}, function (d) {
//                    t = d.datas[0]
//                    //显示
//                    $("select[name=display]").val(t.display);
//                    //摘要
//                    $("input[name=digest]").val(t.briefInfo);
//                    //sortId
//                    $("input[name=sortId]").val(t.sortId);
//
//                    $preImage.attr('src', t.path);
//                    $prevInfo.text(t.briefInfo)
//
//                    $("#addLawBtn").text("确定修改").on("click", function () {
//                        submit("update", id)
//                    })
//                })

            })
        })
        lawTable.init()

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()

            if (ids.length <= 0) {
                ui.alert('请选择需要删除的项目！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    lawTable.request(null, lawTable)
                })
            }, __self + 'picdel.json')
        })

        //add
        $(".ui-add").click(function () {
            $("#addLawBtn").click(function () {
                submit("add")
            })
        })

        var $cateNav = $('#cateNav');
        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                lawTable.refresh({type: $self.data('type')}, function () {
                    $self.find('i.fa').addClass('hidden')
                    $('.td_name').ellipsis({row: 2})
                    $('.td_info').ellipsis({row: 2})
                })
            }
        })


        function submit(type, id) {
            var $form = $('#addForm');
            var info = $form.serializeJson();
            if (type == "add") {
                var verifyRes = verifyForm(info)
                if (verifyRes) {
                    ui.alert(verifyRes)
                    return;
                }
                $.ajaxFileUpload({
                    url: __self + "addpic.json",
                    dataType: 'json',
                    data: info,
                    secureuri: false,
                    fileElementId: 'uploadNewsImg',
                    success: function (data, status) {
                        if (data.ok) {
                            ui.alert("添加成功", function () {
                                ui.history.go("work/carousel")
                            })
                        } else {
                            ui.alert(data.msg)
                        }
                    }
                })
            } else {
                info['id'] = id;
                ui.ajaxFile(__self + 'updatepic.json', info, 'uploadNewsImg', function () {
                    ui.alert("修改成功", function () {
                        ui.history.go("work/carousel")
                    });
                });
            }
        }

        function verifyForm(info) {

            if (!info['briefInfo']) {
                return '摘要不能为空哦';
            }
        }

        function getType(status) {
            if (status == 0) {
                return "不显示"
            } else {
                return "显示"
            }
        }

        $('.ui-back').click(function () {
            ui.history.go("work/carousel")
        })
    })
</script>
