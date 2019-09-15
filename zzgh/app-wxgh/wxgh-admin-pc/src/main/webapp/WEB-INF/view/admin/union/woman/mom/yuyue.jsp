<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/22
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .select_head {
        margin-top: 15px;
    }
    .body{
        clear: both;
        margin-top: 30px;
    }
    .back_button{
        margin-left: 14px;
    }
</style>
<div class="row">
    <div class="back_button">
        <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme back"><span
                class="fa fa-chevron-circle-left"></span> 返回</a>
    </div>
    <div class="ui-content body">
        <div class="select_head">
            <ul id="cateNav" class="nav nav-tabs">
                <li data-status="1" class="nav-li active">
                    <a href="javascript:;">周一 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="2" class="nav-li">
                    <a href="javascript:;">周二 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="3" class="nav-li">
                    <a href="javascript:;">周三 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="4" class="nav-li">
                    <a href="javascript:;">周四 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="5" class="nav-li">
                    <a href="javascript:;">周五 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="6" class="nav-li">
                    <a href="javascript:;">周六 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li data-status="7" class="nav-li">
                    <a href="javascript:;">周日 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </ul>
        </div>
        <table class="table ui-table">
            <thead>
            <tr class="tr_head">
                <th>序号</th>
                <th>预约人</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>日期</th>
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
        <td>{{=it.orderid}}</td>
        <td>{{=it.name}}</td>
        <td>{{=it.startTime}}</td>
        <td>{{=it.endTime}}</td>
        <td>{{=it.currentDate}}</td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script type="text/javascript">
    $(function () {
        var path='union/woman/mom/api/get_week.json'
        var week=${week}
        ui.post(path,{id:${id}},function (json) {
            $(json).each(function (index, item) {
                $(".nav-li").each(function (index,state) {
                    var status=$(this).data('status')
                    if(status==item)
                        $(this).addClass("hidden")
                    if(status==week){
                        $('#cateNav').find('li.active').removeClass('active')
                        $(this).addClass('active')
                    }
                })
            })
        })
        ui.check.init();
        var url='/admin/union/woman/mom/api/get_yuyue.json'
        var data={
            id:${id},
            week:week
        }
        var lawTable=ui.table('law',{
            req:{
                url:url,
                data:data
            },
            dataConver:function (d,index) {
                d['orderid']=index+1
                return d
            },
            empty:{
                col:4,
                html:'暂未录入数据'
            }
        },function ($item,d) {
            $item.data('data', d)
        })
        lawTable.init();

        $('#cateNav').on('click','.nav-li',function () {
            var status=$(this).data('status')
            if(!$(this).hasClass('active')){
                $('#cateNav').find('li.active').removeClass('active')
                $(this).addClass('active')
            }
            lawTable.refresh({id:${id},week:status})
        })
        
        $('.back').on('click',function () {
            var url='union/woman/mom/list'
            ui.history.go(url)
        })

    })
</script>
