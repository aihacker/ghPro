<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>

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
                <a href="#exportModal" data-toggle="modal" class="btn btn-empty-theme"><span
                        class="fa fa-sign-in"></span> 预约导出</a>
            </div>
            <div style="margin-top: 20px;">
                <ul id="cateNav" class="nav nav-tabs">
                    <li class="active" data-status="1">
                        <a href="javascript:;">预约成功<i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-status="2">
                        <a href="javascript:;">预约已使用<i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-status="4">
                        <a href="javascript:;">管理员取消<i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-status="3">
                        <a href="javascript:;">预约失效<i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                </ul>
            </div>
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>用户信息</th>
                    <th>预约时间</th>
                    <th>场馆信息</th>
                    <th>添加时间</th>
                    <th>支付方式</th>
                    <th>预约状态</th>
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

<div class="modal fade" id="exportModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">预约信息导出</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" style="width: 80%;">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">选择场馆：</label>
                        <div class="col-sm-8">
                            <select name="id" class="form-control">
                                <option value="-1">请选择</option>
                                <c:forEach items="${places}" var="p">
                                    <option value="${p.id}">${p.title}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">开始时间：</label>
                        <div class="col-sm-8">
                            <input name="startWeek" type="text" class="form-control ui-time" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">结束时间：</label>
                        <div class="col-sm-8">
                            <input name="endWeek" type="text" class="form-control ui-time" readonly>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                <button id="okBtn" type="button" class="btn btn-theme">确定导出</button>
            </div>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="cateItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.userName}}</td>
            <%--<br>{{=it.deptName}}</td>--%>
        <td>{{=it.weekName}}<br>{{=it.startTime}}-{{=it.endTime}}</td>
        <td>{{=it.placeTitle}}<br>{{=it.cateName}}<br>{{=it.siteName}}</td>
        <td>{{=it.addTime}}</td>
        <td>{{=it.payTypeName}}</td>
        <td>{{=it.statusName}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 取消预约</a>
            </div>
        </td>
    </tr>
</script>

<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    $(function () {
        var __self = 'nanhai/place/api/'
        console.log(${data})

        ui.check.init()

        $('.ui-time').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            startView: 'month',
            todayHighlight: true,
            language: 'cn',
            minView: 'month'
        })

        var cateTable = ui.table("cate",{
            req:{
                url:__self+"yuyuelist.json"
            },
            dataConver:function (d) {
                d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                d['payTypeName'] = d.payType == 1 ? '积分' : '现金'
                d['statusName'] = get_status(d.status)
                d['weekName'] = ui.getWeekName(d.week)
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
                var info = {id:d.id}
                ui.confirm("是否通知用户",function(){
                    info['notify'] = true
                    ui.post(__self+"yuyuedel.json", info, function () {
                        ui.alert('取消成功！',function () {
                            cateTable.refresh(null,cateTable)
                        })
                    })
                },"确认",function () {
                    ui.post(__self+"yuyuedel.json", info, function () {
                        ui.alert('取消成功！',function(){
                            cateTable.refresh(null,cateTable)
                        })
                    })
                })
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

        function get_status(status) {
            var str;
            if (status == 1) {
                str = '预约成功'
            } else if (status == 4) {
                str = '管理员取消'
            } else if (status == 3) {
                str = '预约失效'
            } else {
                str = '预约成功'
            }
            return str
        }

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

        $('#exportModal').on('click', '#okBtn', function () {
            var info = $('#exportModal form').serializeJson()
            var start = info['startWeek']
            var end = info['endWeek']
            if (start && end && start >= end) {
                ui.alert('开始时间不能大于或等于结束时间')
                return
            }
            info['startWeek'] = start.replace(/-/g, '')
            info['endWeek'] = end.replace(/-/g, '')
            ui.openUrl(homePath + '/admin/nanhai/place/yuyue/export.html', info)
        })

    })
</script>
