<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/10
  Time: 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<style>
    #addPane {
        margin: 0 auto;
        text-align: center;
        width: 60%;
    }

    .small {
        width: 100%;
        height: 10%;
        clear: both;
    }

    .open_body {
        width: 100%;
        height: 100%;
        margin: 0 auto;
        margin-left: 10px;
    }

    .open_time {
        width: 700px;
        height: 600px;
        margin-top: 200px;
    }

    .start_time {
        margin-left: 20px;
    }

    .end_time {
        margin-left: 30px;
    }

    .start {
        margin-left: 5px;
    }

    .end {
        margin-left: 5px;
    }

    .week {
        margin-left: 10px;
        font-size: 15px;
    }

    .monday {
        width: 100%;
        height: 50px;
    }

    .tuesday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .wednesday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .thursday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .firday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .saturday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .sunday {
        width: 100%;
        height: 50px;
        clear: both;
    }
    .td_info {
        width: 350px;
        max-width: 350px;
    }

</style>
<div class="row">

    <div class="tab-content ui-content">
        <div class="tab-pane active" id="mainPane">
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-plus"></span> 新增小屋</a>
            </div>

            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>小屋名称</th>
                    <th>小屋介绍</th>
                    <th>开放日期</th>
                    <th>开放状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="wxghList">
                </tbody>
            </table>
            <ul class="pagination" id="wxghPage"></ul>
        </div>

        <div class="tab-pane" id="addPane">
            <div class="header">
                <h4>新增小屋</h4>
            </div>
            <div class="pull-left">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <div class="small">
                <from class="form-horizontal container-fluid" role="form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">小屋名称：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" name="name" placeholder="请输入小屋名称"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">比赛封面：</label>
                        <div class="col-sm-3">
                            <div id="img">

                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">小屋介绍：</label>
                        <div class="col-sm-8 ">
                            <textarea name="info" class="form-control" rows="3" placeholder="请输入小屋介绍"></textarea>
                        </div>
                    </div>
                </from>
            </div>
            <div class="open_time">
                <h4 class="title">开放时间</h4>
                <div class="open_body">
                    <div class="monday">
                        <input type="checkbox" value="1" id="mon" class="pull-left">
                        <label class="week pull-left">周一</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker" id="start_1"
                                   name="startTime"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_1"
                                   placeholder="">
                        </div>
                    </div>
                    <div class="tuesday">
                        <input type="checkbox" value="2" id="tue" class="pull-left">
                        <label class="week pull-left">周二</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker"
                                   name="startTime" id="start_2"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_2"
                                   placeholder="">
                        </div>
                    </div>
                    <div class="wednesday">
                        <input type="checkbox" value="3" id="wed" class="pull-left">
                        <label class="week pull-left">周三</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker"
                                   name="startTime" id="start_3"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_3"
                                   placeholder="">
                        </div>
                    </div>
                    <div class="thursday">
                        <input type="checkbox" value="4" id="thu" class="pull-left">
                        <label class="week pull-left">周四</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker"
                                   name="startTime" id="start_4"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_4"
                                   placeholder="">
                        </div>
                    </div>
                    <div class="firday">
                        <input type="checkbox" value="5" id="fir" class="pull-left">
                        <label class="week pull-left">周五</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker"
                                   name="startTime" id="start_5"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_5"
                                   placeholder="">
                        </div>
                    </div>
                    <div class="saturday">
                        <input type="checkbox" value="6" id="sat" class="pull-left">
                        <label class="week pull-left">周六</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker"
                                   name="startTime" id="start_6"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_6"
                                   placeholder="">
                        </div>
                    </div>
                    <div class="sunday">
                        <input type="checkbox" value="7" id="sun" class="pull-left">
                        <label class="week pull-left">周日</label>
                        <label class="start_time pull-left">开始时间：</label>
                        <div class="start pull-left">
                            <input type="text" class="form-control start_datetimepicker"
                                   name="startTime" id="start_7"
                                   placeholder="">
                        </div>
                        <label class="end_time pull-left">结束时间：</label>
                        <div class="end pull-left">
                            <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_7"
                                   placeholder="">
                        </div>
                    </div>
                    <button type="button" class="btn btn-primary" id="submit">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script type="text/template" id="wxghItem">
    <tr>
        <td><input class="ui-check" type="checkbox"/></td>
        <td>{{=it.name}}</td>
        <td title="{{=it.info}}"><span class="td_info">{{=it.info}}</span></td>
        <td>{{=it.week}}</td>
        <td>{{=it.statusTxt}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 预约列表</a>
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {

//        $('.start_datetimepicker').datetimepicker({
//            format: 'hh:ii',
//            autoclose: true
//        });
//        $('.end_datetimepicker').datetimepicker({
//            format: 'hh:ii',
//            autoclose: true
//        });

        $(".start_datetimepicker").datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            todayBtn: true,
            startView: 'hour',
            maxView: 'hour'
        });
        $(".end_datetimepicker").datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            todayBtn: true,
            startView: 'hour',
            maxView: 'hour'
        });

        var upload = ui.uploader('#img', {multiple: true})

        var $addForm = $('#addPane form');
        $addForm.form();
        ui.check.init();

        var __self = 'union/woman/mom/api';


        var myTable = ui.table('wxgh', {
            req: {
                url: __self + '/list.json',
            },
            dataConver: function (d) {
                d['info'] = d.info ? d.info : '暂无介绍';
                d['week']=weeks(d.week)
                d['statusTxt'] = ui.get_status(d.status, ['未开放', '已开放'])
                return d
            },
            empty: {
                col: 6,
                html: '暂无小屋'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + '/delete.json')
            })

            $item.on('click','.ui-edit',function () {
                var url='union/woman/mom/edit'
                ui.history.go(url,{id:d.id})
            })

            $item.on('click','.ui-see',function () {
                var url='union/woman/mom/yuyue'
                ui.history.go(url,{id:d.id});
            })

            $('.td_info').ellipsis({row: 2})

        })
        myTable.init();

        $("a[href='#mainPane']").on('shown.bs.tab', function () {
            $addForm.removeData('data')
            $addForm.clearForm()
        });

        $('#submit').on('click', function () {
            var weeks = []
            var name = $("input[name=name]").val()
            var info = $("textarea[name=info]").val()
            var week = "";
            $("input[type='checkbox']:checked").each(function (i) {
                var value = $(this).val()
                if (i == 0)
                    week = value
                else
                    week += ("," + value)
                var start = $("#start_" + value).val()
                var end = $("#end_" + value).val()
//                var startTime=new Date(start)
//                var endTime=new Date(end)
//                var cha=parseInt(endTime - startTime)
//                if(cha<=0){
//                    ui.alert("结束时间必须在开始时间之后")
//                    return
//                }
                if (start && end) {
                    if(start >= end) {
                        ui.alert("结束时间不能小于等于开始时间")
                        throw SyntaxError();
                    }
                    var map = {week:value,startTime: start, endTime: end}
                    weeks.push(map)
                } else {
                    ui.alert("请选择好开始和结束时间")
                    return
                }
            })
            var data = {
                name: name,
                info: info,
                week: week,
                times: weeks
            }
            var verifyRes = verify(data);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            var url = '/admin/union/woman/mom/api/add_mom.json'
            upload.upload(function (fileIds) {
                if (fileIds && fileIds.length > 0) {
                    var imgPath = ""
                    for (var i = 0; i < fileIds.length; i++) {
                        if (i != 0)
                            imgPath = imgPath + "," + fileIds[i]
                        else
                            imgPath = fileIds[i]
                    }
                    data['cover'] = imgPath
                }
                var womanTime=JSON.stringify(data)
                ui.post(url, {womanMom:womanTime}, function () {
                    ui.alert('添加成功', function () {
                        ui.refresh();
                    });
                })
            })

            //数据提交
        });

        //表单验证
        function verify(data) {
            if (!data['name'])
                return '小屋名不能为空'
            if (!data['info'])
                return '小屋介绍不能为空'
        }

        function weeks(week) {
            if(week){
                var weeks=week.split(",")
                var s="";
                for(var i=0;i<weeks.length;i++){
                    if(weeks[i]==1){
                        s=s+"周一 "
                    }
                    if(weeks[i]==2){
                        s=s+"周二 "
                    }
                    if(weeks[i]==3){
                        s=s+"周三 "
                    }
                    if(weeks[i]==4){
                        s=s+"周四 "
                    }
                    if(weeks[i]==5){
                        s=s+"周五 "
                    }
                    if(weeks[i]==6){
                        s=s+"周六 "
                    }
                    if(weeks[i]==7){
                        s=s+"周日"
                    }
                }
                return s
            }else{
                return "无"
            }
        }
    })
</script>
