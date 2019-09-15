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

</style>
<div class="row">
    <div class="ui-content tab-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <!--<a id="cateManager" class="btn btn-empty-theme"><span class="fa fa-cog"></span> 提案分类管理</a>-->
                <a href="#exportModal" data-toggle="modal" class="btn btn-empty-theme"><span
                        class="fa fa-sign-in"></span> 活动导出</a>
            </div>

            <div style="margin-top: 20px;">
                <ul id="cateNav" class="nav nav-tabs">
                    <li data-status="3" class="active">
                        <a href="javascript:;">本部 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-status="4">
                        <a href="javascript:;">区分公司 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                    <li data-status="5">
                        <a href="javascript:;">分公司 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                    </li>
                </ul>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>活动名称</th>
                    <th>活动时间</th>
                    <th>联系电话</th>
                    <th>活动地址</th>
                    <th>活动类型</th>
                    <th>状态</th>
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
            <form class="col-md-8" style="margin-top: 20px;">
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>活动名称</label>
                    <div id="formName"></div>
                </div>
                <div class="form-group">
                    <label>活动时间</label>
                    <div id="formTime"></div>
                </div>
                <div class="form-group">
                    <label>联系电话</label>
                    <div id="formPhone"></div>
                </div>
                <div class="form-group">
                    <label>活动地址</label>
                    <div id="formAddress"></div>
                </div>
                <div class="form-group">
                    <label>活动详情</label>
                    <div id="formInfo"></div>
                </div>
                <div class="form-group">
                    <label>参加人数</label>
                    <div id="formActNumb"></div>
                </div>
                <div class="form-group">
                    <div><label>活动图片</label></div>
                    <div id="formPath">

                    </div>
                </div>

                <div class="ui-file-div">
                    <%--<div class="ui-file-item">--%>
                    <%--&lt;%&ndash;<img src="${home}/image/xlkai/icon_add1.png"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input id="fileInput" type="file" accept="image/*" name="imgfile">&ndash;%&gt;--%>
                    <%--</div>--%>
                </div>

                <%--<div class="form-group">--%>
                    <%--<button id="editSaveContinueBtn" type="button" class="btn btn-theme">保存继续添加</button>--%>
                    <%--<button id="editSaveBtn" type="button" class="btn btn-default">保存</button>--%>
                <%--</div>--%>
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
                <form class="form-horizontal" style="width: 80%;margin-left:10%">
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-4 control-label">部门：</label>--%>
                        <%--<div class="col-sm-8">--%>
                            <%--<select name="actType" class="form-control">--%>
                                <%--<option value="3">本部</option>--%>
                                <%--<option value="4">区分公司</option>--%>
                                <%--<option value="5">分公司</option>--%>
                            <%--</select>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class=" control-label">选择活动：</label>
                        <div class="">
                            <select name="actId" class="form-control">
                                <c:forEach items="${acts}" var="item">
                                    <option value="${item.actId}">${item.name}</option>
                                </c:forEach>
                            </select>
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
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td class="ui-suggest-title" title="提案内容：{{=it.content}}"><p>{{=it.name}}</p></td>
        <td title="{{=it.deptname}}">
            {{=it.time}}
        </td>
        <td style="text-align: center;">
            {{=it.phone}}
        </td>
        <td>{{=it.address}}</td>
        <td>{{=it.linkIs}}</td>
        <td>{{=it.statusName}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                <div class="dropdown">
                    <a data-toggle="dropdown" class="btn btn-empty-danger ui-apply">审核 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        {{? it.status===0 || it.status===2}}
                        <li data-status="1"><a href="javascript:;">通过</a></li>
                        {{?}}
                        {{? it.status===0 || it.status===1}}
                        <li data-status="2"><a href="javascript:;">不通过</a></li>
                        {{?}}
                    </ul>
                </div>
                {{? it.linkIs == "常规活动"}}
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-eye"></span> 查看</a>
                {{?}}
                {{? it.linkIs == "外链活动" }}
                <a class="btn btn-empty-theme ui-edit" href="{{=it.link}}"><span
                        class="fa fa-eye"></span> 查看外链</a>
                {{?}}
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var __self = 'act/api/'

        var $cateNav = $('#cateNav')

        var $modal = $('#deviceEditModule')

        var $addFrom = $('#addPane form')

        ui.check.init()
        //list
        var lawTable = ui.table('law', {
            req: {
                url: __self+'actlist.json',
                //data: {action: 'list'}
            },
            dataConver: function (d) {
                d['statusName'] = get_status(d.status)
                d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                if(d.link){
                    d['linkIs'] = "外链活动"
                }else{
                    d['linkIs'] = "常规活动"
                }
                if(!d.time){
                    d.time = "无需设置"
                }
                if(!d.phone){
                    d.phone = "无需设置"
                }
                if(!d.address){
                    d.address = "无需设置"
                }
                return d
            },
            empty: {
                col: 8,
                html: '暂无大型活动'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.find('.ui-suggest-title p').ellipsis({row: 2})

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'del.json')
            })

            //审核
            $item.on('click', '.dropdown-menu li', function () {
                var $self = $(this)
                var status = $self.data('status')
                var msg = $.trim($self.text())
                ui.confirm('是否' + msg + '审核？', function () {
                    var info = {
                        id: d.id,
                        status: status
                    }
                    apply_suggest(info, $item)
                })
            })

            //查看
            $item.on('click','.ui-see',function(){
                var id = d.id
                ui.history.go('group/show', {groupId: $item.data('data').groupId})
//                editId = id;
//                $modal.data('id', id)
//                $modal.modal('show')
//
//                var url = __self+"groupget.json"
//                ui.get(url,{id:id},function (d) {
//                    initModal(d)
//                })
//
//                function initModal(d) {
//                   $("#deviceForm").html("");
//                   for(i=0;i<d.datas.length;i++){
//                       $("#deviceForm").append("<p>"+d.datas[i].name+"<p>")
//                   }
//                }
            })

            //查看 
            $item.on('click','.ui-edit',function(){
                $("#editSaveContinueBtn").hide()
                $addFrom.find("#formName").text(d['name'])
                $addFrom.find("#formPhone").text(d['phone'])
                $addFrom.find("#formAddress").text(d['address'])
                $addFrom.find("#formTime").text(d['time'])
                $addFrom.find("#formInfo").text(d['info'])
                $addFrom.find("#formActNumb").text(d['actNumb'])
                var pathlist = d['path'].split(",");
                $addFrom.find("#formPath").html("")
                for(var i = 0;i<pathlist.length;i++){
                    var img = $('<div class="ui-file-div" style="width:30%;margin-right:3%;margin-top:2%"><img class="/wxgh'+pathlist[i]+'" style="height:auto"></div>')
                    $addFrom.find("#formPath").append(img)
                }
            })
        })
        lawTable.init()

        $cateNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $cateNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                lawTable.refresh({actType: $self.data('status')}, function () {
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
                ui.alert('删除成功！',function(){
                    lawTable.request(null, lawTable)
                })
            }, __self + 'del.json')
        })

        //活动导出
//        $("select[name='actType']").change(function(){
//            var selfval = $(this).val();
//            var info = $('#exportModal form').serializeJson()
//            var actName =  $("select[name='actName']")
//            info['limit'] = false
//            ui.post(__self+"actlist.json", info, function (res) {
//                var datas = res.datas;
//                if(datas.length == 0){
//                    actName.html("")
//                    actName.append("<options value='-1'>没有已通过活动</options>")
//                }else{
//                    //actName.html("")
//                    for(i=0;i<datas.length;i++){
//                        actName.append("<options value='"+datas[i].id+"'>"+datas[i].name+"</options>")
//                    }
//                }
//            })
//        })
        $('#exportModal').on('click', '#okBtn', function () {
            var info = $('#exportModal form').serializeJson()
            console.log(info)
            ui.openUrl("${home}/act/admin/template/export.html", info)
        })

        //审核
        function apply_suggest(info, $item) {
            info['action'] = 'apply'
            ui.post(__self+"apply.json", info, function () {
                ui.alert('审核成功！',function(){
                    ui.refresh();
                })
            })
        }

        function get_status(status) {
            var str
            if (status == 1) {
                str = '正常'
            } else if (status == 0) {
                str = '未审核'
            } else if (status == 2) {
                str = '审核未通过'
            } else {
                str = '未知'
            }
            return str
        }
    })
</script>



