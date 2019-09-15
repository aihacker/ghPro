<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <table class="table ui-table">
        <thead>
        <tr>
            <th>序号</th>
            <th>标题</th>
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
        <td>{{=it.deptid}}</td>
        <td>{{=it.title}}</td>
        <td>{{=it.addTime}}</td>
        <td><img style="height: 30px;" src=${home}{{=it.image}} /></td>
        <td>{{=it.type}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
        var url='/admin/tribe/api/new_lists.json'
        var _self='/admin/tribe/api/'
        ui.check.init()
        var lawTable=ui.table('law',{
            req:{
                url:url
            },
            dataConver:function (d) {
                d['addTime']=new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                d['type']=vetify(d.type)
                return d
            },
            empty:{
                col:6,
                html:'暂时没有新闻'
            }
        },function ($item,d) {
            $item.data("data",d)

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
                return '要闻速要'
            }else if(value==2){
                return '前线快讯'
            }else if(value==3){
                return '先进典型'
            }else{
                return '未知'
            }
        }
    })
</script>