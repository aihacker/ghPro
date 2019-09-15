<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .ui-suggest-title,
    .ui-suggest-title p {
        width: 180px;
        max-width: 180px;
    }

    #addPane, #module {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #f5f5f5;
        display: none;
        z-index: 1000;
    }

</style>
<div class="row">
    <div class="ui-content" id="list-content">
        <div class="ui-link-group">
            <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>

            <a href="#importModal" data-toggle="modal" class="btn btn-empty-theme"><span
                    class="fa fa-sign-in"></span> 四小台账导入</a>

            <a id="down" class="btn btn-empty-theme"><span
                    class="fa fa-sign-in"></span> 四小台账模板下载</a>

        </div>

        <div class="row" style="margin-top:5px">
            <div class="panel panel-default" style="margin-bottom: 0;">
                <div class="panel-body">
                    <label>区公司：</label>
                    <select id="select-zone" name="selectZone" style="width:150px;height:30px">
                        <option value="-1">请选择</option>
                        <c:forEach items="${depts}" var="d">
                            <option value="${d.deptid}">${d.name}</option>
                        </c:forEach>
                    </select>

                    <label>营销中心：</label>
                    <select id="select-center" name="selectCenter" style="width:150px;height:30px">
                        <option value="-1">请选择</option>
                    </select>
                </div>
            </div>
        </div>

        <div style="margin-top: 20px;">
            <ul id="cateNav" class="nav nav-tabs">
                <li data-status="0" class="active">
                    <a href="javascript:;">全部 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="1">
                    <a href="javascript:;">已上传图片 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="2">
                    <a href="javascript:;">未上传图片 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </ul>
        </div>

        <table class="table ui-table">
            <thead>
            <tr>
                <th><input class="ui-check-all" type="checkbox"/></th>
                <th>分公司</th>
                <th>营销中心</th>
                <th>四小项目</th>
                <th>项目内容</th>
                <th>品牌</th>
                <th>规格型号</th>
                <th>数量</th>
                <th>单价</th>
                <th>备注</th>
                <th>使用年限</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="lawList">
            </tbody>
        </table>
        <ul class="pagination" id="lawPage"></ul>

    </div>

    <div id="addPane">

        <div>
            <div class="ui-content ui-link-group">
                <button id="pane_back" type="button" class="btn btn-empty-theme ui-back"><span
                        class="fa fa-angle-double-left"></span> 返回
                </button>
            </div>
        </div>
        <div class="row ui-content" style="margin-top: 20px;">
            <div class="panel panel-info">
                <div class="panel-body">

                    <form class="col-md-4 col-md-offset-4" style="margin-top: 20px;">
                        <input type="hidden" name="id">
                        <div class="form-group">
                            <label>图片</label><br/>
                            <input name="img" id="uploadNewsImg" accept="image/*" multiple="multiple"
                                   class="ui-inline-block" type="file"/>
                        </div>
                        <div class="form-group">
                            <button id="addBtn" type="button" class="btn btn-theme">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div id="module">
        <div>
            <div class="ui-content ui-link-group">
                <button type="button" class="btn btn-empty-theme ui-back"><span
                        class="fa fa-angle-double-left"></span> 返回
                </button>
            </div>
        </div>
        <div class="row ui-content" style="margin-top: 20px;">
            <div class="panel panel-info">
                <div class="panel-heading" id="marketing"></div>
                <div class="panel-body">
                    <div class="ui-member-item">
                        <label>分公司：</label>
                        <span id="companyName"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>营销中心：</label>
                        <span id="deptName"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>四小项目：</label>
                        <span id="projectName"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>项目内容：</label>
                        <span id="projectContent"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>品牌：</label>
                        <span id="brand"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>规格型号：</label>
                        <span id="modelName"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>数量：</label>
                        <span id="numb"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>预算单价：</label>
                        <span id="price"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>备注：</label>
                        <span id="remark"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>设备状态：</label>
                        <span id="condit"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>使用年限：</label>
                        <span id="usefulLife"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>购买时间：</label>
                        <span id="buyTime"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>计划更新时间：</label>
                        <span id="planUpdate"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>资金来源：</label>
                        <span id="priceSource"></span>
                    </div>
                    <div class="ui-member-item">
                        <label>图片：</label>
                    </div>
                    <span id="imgs"></span>
                </div>
            </div>
        </div>
    </div>


</div>

<!-- 导入modal -->
<div class="modal fade" id="importModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">四小台账导入</h4>
            </div>
            <form class="form-horizontal" enctype="multipart/form-data" method="post" id="form"
                  action="${home}/admin/four/_import" style="width: 80%;">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">选择Excel文件：</label>
                        <div class="col-sm-8">
                            <input type="file" name="file"
                                   accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                    <button id="upBtn" type="button" class="btn btn-theme">上传</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.companyName}}</td>
        <td>{{=it.deptName}}</td>
        <td>{{=it.projectName}}</td>
        <td>{{=it.projectContent}}</td>
        <td>{{=it.brand}}</td>
        <td>{{=it.modelName}}</td>
        <td>{{=it.numb}}</td>
        <td>{{=it.price}}</td>
        <td>{{=it.remark}}</td>
        <td>{{=it.usefulLife}}年</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                {{? it.path == null}}
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 上传图片</a>
                {{??}}
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑图片</a>
                {{?}}
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/javascript" src="${home}/libs/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<script>
    $(function () {

        // 模板下载
        $("#down").click(function () {
            ui.openUrl('four/export_template.html');
        })

        // 导出
        $('#exportModal').on('click', '#okBtn', function () {
            var info = $('#exportModal form').serializeJson()
            var data = [];
            if (info['deptid'] && info['deptid'] > 0)
                data['deptid'] = info['deptid'];
            if (info['mid'] && info['mid'] > 0)
                data['mid'] = info['mid'];
            if (info['fpId'] && info['fpId'] > 0)
                data['fpId'] = info['fpId'];
            ui.openUrl(homePath + '/admin/four/export.html', data)
        })

        // 导入
        function checkData() {
            var fileDir = $("input[name = file]").val();
            var suffix = fileDir.substr(fileDir.lastIndexOf("."));
            if ("" == fileDir) {
                ui.alert("选择需要导入的Excel文件！");
                return false;
            }
            if (".xls" != suffix && ".xlsx" != suffix) {
                ui.alert("选择Excel格式的文件导入！");
                return false;
            }
            return true;
        }

        $('#upBtn').click(function(){
            if(checkData()){
                $('#form').ajaxSubmit({
                    url: homePath + '/admin/four/_import.json',
                    dataType: 'json',
                    success: resutlMsg,
                    error: errorMsg
                });
                function resutlMsg(data){
                    if(data.ok){
                        ui.alert("导入成功", function () {
                            lawTable.refresh();
                            $('#importModal').modal('hide');
                        });
                        $("input[name = file]").val("");
                    }else{
                        ui.alert(data.msg);
                    }
                }
                function errorMsg(){
                    ui.alert("导入excel出错！");
                }
            }
        });

        // modal下拉监听

        $("select[name=deptid]").change(function () {
            var deptid = $('select[name=deptid] option:selected').val();
            onSelect('four/api/list_market.json', {deptid: deptid}, $("select[name=mid]"))
            clearSelect($("select[name=fpId]"))
        })

        $("select[name=mid]").change(function () {
            var mid = $('select[name=mid] option:selected').val();
            onSelect('four/api/list_fp.json', {mid: mid}, $("select[name=fpId]"))
        })

        function clearSelect($select) {
            $select.html("<option value='-1'>请选择</option>")
        }

        // end

        var __self = 'four/api/'

        var $cateNav = $('#cateNav')

        var $modal = $('#module')

        var $addForm = $('#addPane form')
        var $addPane = $("#addPane")
        $addPane.hide();

        ui.check.init()
        //list
        var lawTable = ui.table('law', {
            req: {
                url: __self + 'export_list.json',
            },
            dataConver: function (d) {
                d['buyTime'] = formatTime(d.buyTime)
                d['planUpdate'] = formatTime(d.planUpdate)
                return d
            },
            empty: {
                col: 12,
                html: '暂无台账数据哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.find('.ui-suggest-title p').ellipsis({row: 2})

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'export_delete.json')
            })

            //上传图片
            $item.on('click', '.ui-edit', function () {
                $addPane.show();
                $addForm.data('data', d)
            })
            $item.on('shown.bs.tab', '.ui-edit', function () {
                var d = $addForm.data('data')
                if (d) {
                    $addForm.find('input[name=id]').val(d.id)
                }
            })

            //查看
            $item.on('click', '.ui-see', function () {
                var id = d.id
                $modal.data('id', id)
                $modal.show()

                $("#list-content").hide();

                initModal(d)

                function initModal(d) {
                    $("#companyName").text(d.companyName);
                    $("#deptName").text(d.deptName);
                    $("#projectName").text(d.projectName);
                    $("#projectContent").text(d.projectContent);
                    $("#modelName").text(d.modelName);
                    $("#brand").text(d.brand);
                    $("#numb").text(d.numb + " " + d.unit);
                    $("#price").text(d.price);
                    $("#remark").text(d.remark);
                    $("#condit").text(d.condit);
                    $("#usefulLife").text(d.usefulLife);
                    $("#buyTime").text(d.buyTime);
                    $("#planUpdate").text(d.planUpdate);
                    $("#priceSource").text(d.priceSource);
                    if (d.path) {
                        var data = d.path.split(",");
                        var img = '';
                        for (i in data) {
                            img += "<img width='500' src='" + homePath + data[i] + "'><br><br><br>"
                        }
                        $("#imgs").html(img);
                    } else {
                        $("#imgs").html('无');
                    }
                }

                $(".ui-back").click(function () {
                    $modal.hide()
                    $("#list-content").show();
                })
            })
        })
        lawTable.init()

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                var s = $self.data('status');
                var data = {img: s};

                lawTable.refresh(data, function () {
                    $self.find('i.fa').addClass('hidden')
                })
            }
        })

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
            }, __self + 'export_delete.json')
        })

        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson()
            $.ajaxFileUpload({
                url: __self + "add_export_pic.json",
                dataType: 'json',
                data: info,
                secureuri: false,
                fileElementId: 'uploadNewsImg',
                success: function (data, status) {
                    if (data.ok) {
                        if (info['id'])
                            var msg = "修改成功";
                        else
                            var msg = "添加成功"
                        ui.alert(msg, function () {
                            ui.history.go("four/export_list")
                        })
                    } else {
                        ui.alert(data.msg)
                    }
                }
            })
        });

        $("#pane_back").on('click', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
            $addPane.hide();
        })

        //选择公司 ，营销中心
        $("select[name=selectZone]").change(function () {
            var zoneId = $('select[name=selectZone] option:selected').val();
            if(zoneId < 0 || !zoneId){
                lawTable.refresh({deptid: null, mid: null});
                return ;
            }
            onSelect('four/api/list_market.json', {deptid: zoneId}, $("select[name=selectCenter]"))
            lawTable.refresh({deptid: zoneId, mid: null});

            clearSelect($("select[name=fpId]"))
        })

        $("select[name=selectCenter]").change(function () {
            var zoneId = $('select[name=selectZone] option:selected').val();
            var midId = $('select[name=selectCenter] option:selected').val();
            if (midId == -1) {
                lawTable.refresh({deptid: zoneId, mid: null});
            } else {
                lawTable.refresh({deptid: zoneId, mid: midId});
            }
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

        function formatTime(time) {
            //   格式：yyyy-MM-dd hh:mm:ss
            var date = new Date(time);
            Y = date.getFullYear() + '-';
            M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
            D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
//            h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
//            m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes();
            return Y + M + D;
        }

    })
</script>

