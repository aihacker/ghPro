<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .table > tbody > tr > td[class^=td-] {
        line-height: 60px;
    }

    .table > tbody > tr > td.td-info,
    .table > tbody > tr > td.td-title {
        line-height: 25px;
    }

    .table > tbody > tr > td.td-info,
    .table > thead > tr > th.th-info {
        width: 40%;
    }

    .table > tbody > tr > td.td-title,
    .table > thead > tr > th.th-title {
        width: 15%;
    }

    .table > tbody > tr > td.td-img {
        width: 90px;
    }

    .td-info p,
    .td-title p {
        width: 100%;
        height: 100%;
        margin-bottom: 0;
        max-height: 70px;
    }

    td.td-time {
        min-width: 130px;
    }

    td.td-other {
        min-width: 90px;
    }

    td.td-img img.thumbnail {
        margin-bottom: 0;
    }

    td.td-other a:last-child {
        border-left: 1px solid #777;
        padding-left: 5px;
    }

    .ui-img-div {
        height: 60px;
        width: 80px;
        display: -webkit-flex;
        display: flex;
        justify-content: center;
        overflow: hidden;
        align-items: center;
    }

    .ui-ellipsis-3 {
        display: -webkit-box;
        overflow: hidden;
        white-space: normal !important;
        text-overflow: ellipsis;
        word-wrap: break-word;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical
    }

    .ui-img-div img {
        width: 100%;
    }

    #typeSelect {
        width: 120px;
        height: 30px;
    }

    .imgStyle {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }

    #searchResult ul.list-group {
        margin-bottom: 0;
    }
</style>

<div id="processDialog" class="hidden" style="width: 100%;height: 100%;position: absolute;left: 0;top: 0;z-index: 100;">
    <div style="display: flex;width: 100%;height: 100%;justify-content: center;align-items: center;text-align: center;">
        <span class="fa fa-spin fa-spinner fa-2x text-info"></span>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <span>文章类型：</span>
                <select id="typeSelect">
                    <option value="1" class="op-1">中央精神</option>
                    <option value="2" class="op-2">主题教育</option>
                    <option value="3" class="op-3">支部简讯</option>
                    <option value="4" class="op-4">企业党建</option>
                    <option value="5" class="op-5">党员学习</option>
                    <option value="6" class="op-6">党史理论</option>
                </select>

                <a class="btn btn-link" id="pushBtn">推送文章</a>

                <a class="btn btn-link" id="pushPreviewBtn" href="#pushPreviewModal">推送预览</a>
            </div>
        </div>
    </div>
    <div class="row">
        <table class="table ui-table">
            <thead>
            <tr>
                <th><input class="ui-check-all" type="checkbox"/></th>
                <th>封面</th>
                <th class="th-title">标题</th>
                <th class="th-info">简介</th>
                <th>发布时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="articleList">
            </tbody>
        </table>
        <ul class="pagination" id="articlePage"></ul>
    </div>
</div>

<!-- 模态窗口布局 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editProductModalLabel">编辑</h4>
            </div>
            <form name="editProductForm">
                <div class="modal-body">
                    <div class="form-group">
                        文章标题：
                        <input type="text" class="form-control" id="edit-title">
                        <br>
                        封面图片：
                        <input type="file" name="editMyimg" id="editMyimg">

                        <p id="p-img-edit" style="height: 100px;margin-top:10px;text-align: center;">
                            <img id="myimg-edit" style="height: 100%;">
                        </p>
                        <label>选择分类：</label>
                        <select id="edit-type">
                            <option value="1" id="edit-option-1" class="op-1">中央精神</option>
                            <option value="2" id="edit-option-2" class="op-2">主题教育</option>
                            <option value="3" id="edit-option-3" class="op-3">支部简讯</option>
                            <option value="4" id="edit-option-4" class="op-4">企业党建</option>
                            <option value="5" id="edit-option-5" class="op-5">党员学习</option>
                            <option value="6" id="edit-option-6" class="op-6">党史理论</option>
                        </select>
                        <br>
                        文章介绍：
                        <textarea class="form-control" id="edit-brief-info"></textarea>

                        <script type="text/plain" id="editEditor" style="width:550px;height:240px;margin: auto"></script>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button data-id="" id="editProductConfirm" type="button" class="btn btn-primary">确定</button>
                </div>
            </form>
        </div>
    </div>
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

<script type="text/template" id="articleItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td class="td-img"><div class="ui-img-div"><img class="thumbnail" src="{{=it.img}}"></div></td>
        <td class="td-title">{{=it.title}}</td>
        <td class="td-info">{{=it.briefInfo}}</td>
        <td>{{=it.addTime}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<jsp:include page="/comm/admin/umeditor.jsp"/>

<script>

    //实例化编辑器
    var editUm = UM.getEditor('editEditor',{
        /* 传入配置参数,可配参数列表看umeditor.config.js */
        toolbar: [
            'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
            'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
            '| justifyleft justifycenter justifyright justifyjustify |',
            'link unlink | emotion image video ',
            '| horizontal print preview fullscreen', 'drafts', 'formula'
        ]
    });

    $(function () {

        var _self='/admin/party/article/api/get.json'
        ui.check.init()
        var articleTable = ui.table('article',{
            req:{
                url:_self,
                data: {type:1}
            }, dataConver: function (d) {
                if(d.addTime){
                    d.addTime = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                }else {
                    d.addTime = '未知时间'
                }
                if(!d.title){
                    d.title = '无'
                }
                var img = d.img
                if (!img) {
                    img = homePath + '/image/common/nopic.gif'
                } else {
                    img = ui.get_image(d.img)
                }
                d['img'] = img
                if(!d.briefInfo){
                    d.briefInfo = '无'
                }
                return d;
            },
            empty:{
                col:6,
                html:'暂时未录入数据'
            }
        },function ($item,d) {
            $item.data('data',d)

            $item.on('click','.ui-edit', function () {
                ui.post('party/article/api/get_one.json',
                        {id: d.id},function(data){
                            $("#edit-title").val(data.title);
                            $("#myimg-edit").attr("src", ui.get_image(data.img));
                            $("#edit-brief-info").val(data.briefInfo);
                            editUm.setContent(data.content)
                            $("#editProductConfirm").attr("data-id", data.id);

                            var type = data.type;
                            switch (type) {
                                case 1:
                                    $("#edit-option-1").attr("selected", "selected");
                                    break;
                                case 2:
                                    $("#edit-option-2").attr("selected", "selected");
                                    break;
                                case 3:
                                    $("#edit-option-3").attr("selected", "selected");
                                    break;
                                case 4:
                                    $("#edit-option-4").attr("selected", "selected");
                                    break;
                                case 5:
                                    $("#edit-option-5").attr("selected", "selected");
                                    break;
                                case 6:
                                    $("#edit-option-6").attr("selected", "selected");
                                    break;
                            }

                            $("#editModal").modal("show");

                            $("input[name=editMyimg]").change(function () {
                                var src = getObjectURL(this.files[0]); //获取路径
                                $(this).siblings("p").children("img").attr("src", src);
                            });
                        })
            })

            $item.on('click','.ui-del', function () {
                ui.del(d.id, function () {
                    articleTable.request(null,articleTable)
                }, 'party/article/api/del_article.json')
            })
        })
        articleTable.init()

        //编辑模态窗口
        $("#editProductConfirm").click(function () {
            var title = $("#edit-title").val();
            var briefInfo = $("#edit-brief-info").val();
            var id = $(this).attr("data-id");
            var type = $("#edit-type").val();

            var url = 'party/article/api/update_data.json'
            var content = editUm.getContent();
//            content = encodeURI(content);
            var info = {
                title: title,
                briefInfo: briefInfo,
                content:content,
                id: id,
                type: type
            }

            if ($('#editMyimg').get(0).files.length > 0) {
                $.ajaxFileUpload({
                    url: url,
                    dataType: 'json',
                    secureuri: false,
                    data: info,
                    fileElementId: 'editMyimg',
                    success: function () {
                        backcall()
                    }
                });
            } else {
                $.post(url, info, function (res) {
                    backcall()
                }, 'json')
            }

            function backcall() {
                ui.alert("更改成功",function(){
                    $("#editModal").modal("hide");
                    articleTable.setData({type:type})
                    $('#typeSelect').val(type)
                    articleTable.request(null,articleTable)
                });
            }
        });

        $('#typeSelect').on('change', function () {
            articleTable.setData({type:$(this).val()})
            articleTable.request(null,articleTable)
        })


        //批量推送
        $('#pushBtn').on('click', function () {
            var ids = articleTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要推送的文章！')
                return
            }
            if (ids.length > 4) {
                ui.alert('每次推送不能超过4条文章哦')
                return
            }
            ui.confirm('是否推送？', function () {
                ui.post('party/article/api/push.json', {
                    id: ids.toString(),
                    isAllPush: true
                }, function () {
                    ui.alert('推送成功！',function(){
                        $('table.ui-table').find('input[type=checkbox]').prop('checked', false)
                    })
                })
            })
        })

        //推送预览
        $('#pushPreviewBtn').on('click', function () {
            var ids = articleTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要推送的文章！')
                return
            }
            if (ids.length > 4) {
                ui.alert('每次推送不能超过4条文章哦')
                return
            }
            $('#pushPreviewBtn').attr('data-toggle', 'modal')
        })


        //搜索框搜索事件
        var $searchInput = $('#searchInput');
        var $searchResult = $('#searchResult')

        $searchInput.keyup(function () {
            var val = $(this).val().trim()
            if (val) {
                ui.post('party/article/api/search_user.json', {key: val}, function (res) {
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

        $('#sendPreviewNoticeBtn').on('click', function () {
            if ($searchInput.val() == "") {
                ui.alert("请输入用户！");
                return;
            }
            var ids = articleTable.get_checked_ids()
            ui.confirm('是否推送？', function () {
                ui.post('party/article/api/push.json', {
                    id: ids.toString(),
                    userid: $('#choiceUserId').attr('value')
                }, function () {
                    ui.alert('推送成功！')
                    $('#pushPreviewBtn').removeAttr('data-toggle');
                    $('#pushPreviewBtn').modal('hide');
                    $('table.ui-table').find('input[type=checkbox]').prop('checked', false)
                })
            })
        })


        function getObjectURL(file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
    })
</script>