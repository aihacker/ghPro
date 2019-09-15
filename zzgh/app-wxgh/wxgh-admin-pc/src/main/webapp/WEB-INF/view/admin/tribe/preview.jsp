<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/22
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="ui-content">
        <div class="ui-link-group">
            <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>
        </div>
        <div style="margin-top: 20px;">
            <ul id="cateNav" class="nav nav-tabs">
                <li data-status="1" class="nav-li active">
                    <a href="javascript:;">活动预告 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="2"  class="nav-li">
                    <a href="javascript:;">正在众筹 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="3"  class="nav-li">
                    <a href="javascript:;">历史活动 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </ul>
        </div>
        <table class="table ui-table">
            <thead>
            <tr>
                <th>全选</th>
                <th>序号</th>
                <th>标题</th>
                <th>时间</th>
                <th>联系人/电话</th>
                <th>发起部门</th>
                <th>需要人数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="lawList">
            </tbody>
        </table>
        <ul class="pagination" id="lawPage"></ul>
    </div>
</div>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.deptid}}</td>
        <td>{{=it.theme}}</td>
        <td>
            {{=it.startTime}}-
            {{=it.endTime}}
        </td>
        <td>
            {{=it.linkman}}
            <br/>
            {{=it.phone}}
        </td>
        <td>{{=it.deptname}}</td>
        <td>{{=it.totalNumber}}</td>
        <td>
            <div class="ui-link-group">
                <a data-toggle="dropdown" class="btn btn-empty-danger ui-apply">查看 <span class="caret"></span></a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 修改状态</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    var state;
    $(function () {
        var url='/admin/tribe/api/get_act.json'
        var _self='/admin/tribe/api/'
        //初始化
        ui.check.init()
        state=1;
        var lawTable=ui.table('law',{
            req:{
                url:url,
                data:{
                    status:1
                }
            },
            dataConver:function (d) {
                d['startTime']=new Date(d.startTime).format('yyyy-MM-dd hh:mm')
                d['endTime']=new Date(d.endTime).format('yyyy-MM-dd hh:mm')
                return d
            },
            empty:{
                col:8,
                html:'暂时未录入数据'
            }
        },function ($item,d) {
            $item.data("data",d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, _self + 'del_act.json')
            })

            //修改状态
            $item.on('click', '.ui-see', function () {
                switch (state){
                    case 1:
                        updatestatus("状态已改为“正在众筹”",2,d.id);break;
                    case 2:
                        updatestatus("状态已改为“往期回顾”",3,d.id);break;
                }
                function updatestatus(msg,state,id) {
                    var url='/admin/tribe/api/update_status.json'
                    ui.post(url,{status:state,id:id},function () {
                        ui.alert(msg,function () {
                            window.location.reload()
                        })
                    })
                }
            })

            //查看
            $item.on('click','.ui-apply',function () {
                var url='tribe/detail'
                ui.history.go(url,{id:d.id})
            })

        })
        lawTable.init()

        $('#cateNav').on('click','.nav-li',function () {
            var status=$(this).data('status')
            if(!$(this).hasClass('active')){
                $('#cateNav').find('li.active').removeClass('active')
                $(this).addClass('active')
            }
            state=status
            lawTable.refresh({status:status})
        })

        //批量删除
        $('#cateDelBtn').on('click', function () {
            var ids = lawTable.get_checked_ids()
            if (ids.length <= 0) {
                ui.alert('请选择需要删除的活动！')
                return
            }
            ui.del(ids, function () {
                ui.alert('删除成功！',function(){
                    lawTable.request(null, lawTable)
                })
            }, _self + 'del_act.json')
        })

    })
</script>
