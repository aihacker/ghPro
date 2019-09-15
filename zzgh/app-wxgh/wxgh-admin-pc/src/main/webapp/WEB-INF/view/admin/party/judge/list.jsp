<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/22
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<style>
    .select_head {
        margin-top: 15px;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a id="delAllExamBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
                <a id="cateManager" class="btn btn-empty-theme"><span class="fa fa-cog"></span> 新增问卷</a>
            </div>
           <%-- &lt;%&ndash;<div class="ui-search-head well">
                <select id="searchParty" class="form-control">
                    <option value="0">全部</option>
                    <option value="1">全部党员</option>
                    &lt;%&ndash;<option value="2">特定群</option>
                    <option value="3">支部评述</option>
                    <option value="4">分党委</option>&ndash;%&gt;
                </select>&ndash;%&gt;
            </div>--%>
            <table class="table ui-table">
                <thead>
                <tr class="tr_head">
                    <th width="5%"><input class="ui-check-all" type="checkbox"/></th>
                    <th>名称</th>
                    <th>简介<th>
                   <%-- <th>面向类型</th>
                    <th>面向对象</th>--%>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="lawList">
                </tbody>
            </table>
            <ul class="pagination" id="lawPage"></ul>
        </div>
    </div>
</div>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.name}}</td>
        <td>{{it.briefInfo}}</td>
       <%-- {{? it.type == 1}}
        <td>全体党员</td>
        {{?? it.type == 2}}
        <td>特定群</td>
        {{?? it.type == 3}}
        <td>支部评述</td>
        {{?? it.type == 4}}
        <td>分党委</td>
        {{?}}
        {{? it.gname}}
        <td>{{=it.gname}}</td>
        {{?? !it.gname}}
        <td>未知</td>
        {{?}}--%>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    var status;
    $(function () {
        var url = '/admin/party/judge/api/list.json';
        ui.check.init()
        var lawTable = ui.table('law', {
            req: {
                url: url
            },
            dataConver: function (d) {
                d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd')
                return d
            },
            empty: {
                col: 5,
                html: '暂时未录入数据'
            }

        }, function ($item, d) {

            $item.data('data', d)

            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    lawTable.request(null, lawTable)
                }, 'party/judge/api/del.json')
            })

        })
        lawTable.init()

        $('#searchParty').change(function () {
            var val = $(this).val();
            val = val == 0 ? null : val;
            lawTable.refresh({type: val});
        })

        //管理
        $('#cateManager').on('click', function () {
            ui.history.go('party/judge/add')
        })


        //批量删除
        $('#delAllExamBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的问卷！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！', function () {
                    lawTable.request(null, lawTable)
                })
            }, 'party/judge/api/del.json')
        });
    })

</script>