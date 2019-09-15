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
        <div style="margin-top: 20px;">
            <ul id="cateNav" class="nav nav-tabs">
                <li data-status="1" class="nav-li active">
                    <a href="javascript:;">报名列表 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="2"  class="nav-li">
                    <a href="javascript:;">作品列表 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </ul>
        </div>
        <table class="table ui-table">
            <thead>
            <tr>
                <th>序号</th>
                <th>作者</th>
                <th>作品名</th>
                <th>作品类型</th>
                <th id="change_td">报名时间</th>
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
        <td>{{=it.orderId}}</td>
        <td>{{=it.name}}</td>
        <td>{{=it.workName}}</td>
        <td>{{=it.type}}</td>
        {{? it.vote!=null}}
        <td>{{=it.vote}}</td>
        {{??}}
        <td>{{=it.addTime}}</td>
        {{?}}
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    var state;
    $(function () {
        var url='/admin/tribe/photo/api/total_human.json'
        //初始化
        ui.check.init()
        state=1;
        var userid=${id}
        var lawTable=ui.table('law',{
            req:{
                url:url,
                data:{
                    status:1,
                    userid:userid
                }
            },
            dataConver:function (d) {
                d['addTime']=new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                d['type']=veffy(d.type)
                return d
            },
            empty:{
                col:6,
                html:'暂时未录入数据'
            },
            success:function () {
                if(state==2){
                    $('#change_td').text('票数')
                }else if(state==1){
                    $('#change_td').text('报名时间')
                }

            }
        },function ($item,d) {
            $item.data("data",d)

            var _self;
            if(state==1)
                _self='/admin/tribe/photo/api/del_detail.json'
            else if(state==2)
                _self='/admin/tribe/photo/api/del_work.json'
            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, _self)
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
            lawTable.refresh({status:status,userid:userid})
        })

        function veffy(value) {
            if(value==1)
                return '照片'
            else if(vaule==2)
                return '视频'
        }

    })
</script>
