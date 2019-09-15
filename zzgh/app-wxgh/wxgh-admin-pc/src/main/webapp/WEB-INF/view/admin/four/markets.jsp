<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/18
  Time: 9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 添加营销中心</a>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>区分公司</th>
                    <th>营销中心名称</th>
                    <th>图标</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="wxghList">
                </tbody>
            </table>
            <ul class="pagination" id="wxghPage"></ul>
            <div id="baiduMap"></div>
        </div>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>

            <form class="col-md-4 col-md-offset-4" enctype="multipart/form-data" style="margin-top: 20px;">
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>区分公司</label>
                    <select name="deptid" class="form-control">
                        <c:forEach items="${depts}" var="d">
                            <option value="${d.deptid}">${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>营销中心名称</label>
                    <input type="text" name="name" class="form-control" placeholder="名称不能为空">
                </div>
                <div class="form-group">
                    <label>图标图片</label><br/>
                    <input name="img" id="uploadNewsImg" class="ui-inline-block" type="file"/>
                </div>
                <div class="form-group">
                    <button id="addBtn" type="button" class="btn btn-theme">保存</button>
                </div>
            </form>
        </div>


    </div>
</div>

<script type="text/template" id="wxghItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.companyName}}</td>
        <td>{{=it.name}}</td>
        <td><img src="${home}{{=it.avatar}}"></td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <%--<a class="btn btn-empty-danger ui-del"><span class="fa fa-trash"></span> 删除</a>--%>
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<script>
    $(function () {
        ui.check.init();

        var $addForm = $('#addPane form')

        var wxghTable = ui.table('wxgh', {
            dataConver: function (d) {
                return d
            },
            req: {
                url: 'four/api/markets.json',
            },
            empty: {
                col: 5,
                html: '暂无营销中心信息哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //编辑
            $item.on('click', '.ui-edit', function () {
                $addForm.data('data', d)
            })
            $item.on('shown.bs.tab', '.ui-edit', function () {
                var d = $addForm.data('data')
                if (d) {
                    $("select[name=deptid]").find("option[value="+d.deptid+"]").attr("selected",true);
                    $addForm.find('input[name=id]').val(d.id)
                    $addForm.find('input[name=name]').val(d.name)
                }
            })
        })
        wxghTable.init()

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
            upload.clear()
        })

        $('#addBtn').on('click', function () {
            var info = $addForm.serializeJson()
            console.log(info)
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }


                $.ajaxFileUpload({
                    url: "four/api/add_market.json",
                    dataType: 'json',
                    data: info,
                    secureuri: false,
                    fileElementId: 'uploadNewsImg',
                    success: function (data, status) {
                        if (data.ok) {
                            if(info['id'])
                                var msg = "修改成功";
                            else
                                var msg = "添加成功"
                            ui.alert(msg, function () {
                                ui.history.go("four/markets")
                            })
                        } else {
                            ui.alert(data.msg)
                        }
                    }
                })


        })

        //批量删除
        $('#delAllBtn').on('click', function () {
            var ids = wxghTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的信息！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    ui.refresh()
                })
            }, 'four/api.json')
        })

        function verify(info) {
            if (!info['name']) {
                return '请输入营销中心名称'
            }
            if (!info['deptid']) {
                return '请选择分公司'
            }
        }
    })
</script>