<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/18
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    .table {
        border-radius: 5px;
        -moz-border-radius: 5px;
        -webkit-border-radius: 5px;
    }

    .row {
        margin-left: 20px;
        margin-right: 20px;
    }

    .panel-body {
        padding: 6px 20px;
    }

    #th-apply {
        display: inline-block;
        max-width: 20%;
    }
</style>
<link href="${home}/weixin/style/tribe/admin/point/index.css" rel="stylesheet">
<div class="row">
    <div class="container-fluid">
        <div class="row">
            <div class="panel panel-info" style="margin-top: 8px">
                <div class="panel-body">
                    选择活动：
                    <select class="form-control" id="th-apply">
                        <c:forEach items="${acts}" var="act">
                            <option value="${act.id}">${act.theme}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <table class="table table-hover table-bordered content">
                <tr id="tr-title" style="background-color: #D2F4FE;">
                    <th class="text-center">全选</th>
                    <th class="text-center">序号</th>
                    <th class="text-center">姓名</th>
                    <th class="text-center">部门</th>
                    <th class="text-center">电话</th>
                    <th class="text-center">获得积分</th>
                    <th class="text-center">操作</th>

                </tr>
                <tbody id="integralList">
                </tbody>
            </table>
            <ul class="pagination" id="integralPage"></ul>
            <div class="panel panel-default">
                <div class="panel-body">
                    <label><input type="checkbox" value="" id="checkbox-all"> 全选</label>
                    <button type="button" class="btn btn-link" id="btn-all-del">删除</button>
                    <button type="button" class="btn btn-link" id="btn-all-agree">确定</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="integralItem">
    <tr>
        <td><input type="checkbox" value="" id="checkbox"></td>
        <td>{{=it.id}}</td>
        <td name="username">{{=it.username}}</td>
        <td>{{=it.deptname}}</td>
        <td>{{=it.phone}}</td>
        <td><input type="text" name="point" value={{=it.score}}></td>
        <td class="text-center td-ver">
            <button class="btn btn-info sure_btn">确定</button>
            <button class="btn btn-info del_btn">删除</button>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    var by_id;
    $(function () {
        by_id=$('#th-apply option:first').val();
        //查询
        var _self='/admin/tribe/api/get.json'
        //修改
        var url='/admin/tribe/api/update.json'
        //删除
        var path='/admin/tribe/api/delete.json'

        ui.check.init()
        var integralTable=ui.table('integral',{
            req:{
                url:_self,
                by_id:by_id
            },
            empty:{
                col:7,
                html:'暂时未录入数据'
            }
        },function ($item,d) {
            $item.data('data',d)
        })
        integralTable.init()

        $('#th-apply').change(function () {
            by_id=$(this).val()
            integralTable.refresh({by_id: by_id})
        })

        $('.content').on('click','.sure_btn',function () {
            $shenheTr = $(this).parent().parent("tr");
            var point = $shenheTr.find("input[name='point']").val()
            var username=$shenheTr.find("td[name='username']").text()
            var data={
                by_id:by_id,
                point:point,
                username:username
            }
            ui.post(url,data,function () {
                alert("修改成功！")
            })
        })

        //点击全选
        $('#checkbox-all').change(function () {
            var $checkbox=$('#integralList input[type=checkbox]')
            if($(this).is(':checked')){
                $checkbox.each(function () {
                    $(this).prop('checked',true);
                })
            }else{
                $checkbox.each(function () {
                    $(this).prop('checked',false);
                })
            }
        })

        //批量确定
        $('#btn-all-agree').click(function () {
            var $checkbox=$('#integralList input[type=checkbox]:checked')
            var size=$checkbox.length
            if(size>=0){
                $('#integralList input[type=checkbox]:checked').each(function () {
                    var point=$(this).parent().parent("tr").find("input[name='point']").val()
                    var username=$(this).parent().parent("tr").find("td[name='username']").text()
                    var data={
                        by_id:by_id,
                        point:point,
                        username:username
                    }
                    ui.post(url,data,function () {
                        alert("修改成功！")
                    })
                })
            }
        })

        //点击删除
        $('.content').on('click','.del_btn',function () {
            $shenheTr = $(this).parent().parent("tr");
            var username=$shenheTr.find("td[name='username']").text()
            var data={
                by_id:by_id,
                username:username
            }
            ui.post(path,data,function () {
                alert("删除成功！")
            })
        })

        //批量删除
        $('#btn-all-del').click(function () {
            var $checkbox=$('#integralList input[type=checkbox]:checked')
            var size=$checkbox.length
            if(size>=0){
                $('#integralList input[type=checkbox]:checked').each(function () {
                    var point=$(this).parent().parent("tr").find("input[name='point']").val()
                    var username=$(this).parent().parent("tr").find("td[name='username']").text()
                    var data={
                        by_id:by_id,
                        username:username
                    }
                    ui.post(path,data,function () {
                        alert("删除成功！")
                    })
                })
            }
        })
    });

</script>

