<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <table class="table ui-table">
        <thead>
        <tr>
            <th>序号</th>
            <th>名称</th>
            <th>时间</th>
            <th>封面</th>
            <th>类型</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="lawList">
        </tbody>
    </table>
    <ul class="pagination" id="lawPage"></ul>
</div>
<script type="text/template" id="lawItem">
    <tr>
        <td>{{=it.tel}}</td>
        <td>{{=it.name}}</td>
        <td>{{=it.startTime}}</td>
        <td><img style="height: 30px;" src=${home}{{=it.cover}} /></td>
        <td>{{=it.type}}</td>
        <td>
            <div class="ui-link-group">
                <a data-toggle="dropdown" class="btn btn-empty-danger ui-apply">查看详情<span class="caret"></span></a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
        var url='/admin/tribe/photo/api/get_results.json'
        var _self='/admin/tribe/api/'
        ui.check.init()
        var lawTable=ui.table('law',{
            req:{
                url:url
            },
            dataConver:function (d) {
                d['startTime']=new Date(d.startTime).format('yyyy-MM-dd hh:mm')
                d['type']=vetify(d.worksType)
                return d
            },
            empty:{
                col:6,
                html:'暂时未录入比赛'
            }
        },function ($item,d) {
            $item.data("data",d)

            //查看
            $item.on('click','.ui-apply',function () {
                var url='tribe/photo/show'
                ui.history.go(url,{id:d.id})
            })

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, _self + 'del_new.json')
            })

        })
        lawTable.init()

        function vetify(value) {
            if(value==1){
                return '照片'
            }else if(value==2){
                return '视频'
            }else{
                return '未知'
            }
        }
    })
</script>