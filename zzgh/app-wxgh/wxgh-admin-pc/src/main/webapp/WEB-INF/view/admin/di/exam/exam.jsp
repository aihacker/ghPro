<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    td > img {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }
</style>
<div class="row ui-content">
    <div class="ui-link-group">
        <a onclick="backForList()" data-toggle="tab" class="btn btn-empty-theme"><span
                class="fa fa-chevron-circle-left"></span> 返回</a>
        <a id="exportBtn" class="btn btn-empty-theme"><span class="fa fa-share-square-o"></span>考试记录导出</a>
    </div>

    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist" style="margin-top: 10px;">
            <li role="presentation" class="active"><a href="#first" aria-controls="first" role="tab" data-toggle="tab">已报名考试</a>
            </li>
            <li role="presentation"><a href="#second" aria-controls="second" role="tab" data-toggle="tab">已进行考试</a></li>
            <li role="presentation"><a href="#three" aria-controls="three" role="tab" data-toggle="tab">未考试</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="first">
                <table class="table ui-table">
                    <thead>
                    <tr>
                        <th><input class="ui-check-all" type="checkbox"/></th>
                        <th>考试名称</th>
                        <th>考试人</th>
                        <th>报名时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="examJoinList">
                    </tbody>
                </table>
                <ul class="pagination" id="examJoinPage"></ul>
            </div>
            <div role="tabpanel" class="tab-pane" id="second">
                <table class="table ui-table">
                    <thead>
                    <tr>
                        <th><input class="ui-check-all" type="checkbox"/></th>
                        <th>考试名称</th>
                        <th>考试人</th>
                        <th>考试时间</th>
                        <th>考试成绩</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="examFinishList">
                    </tbody>
                </table>
                <ul class="pagination" id="examFinishPage"></ul>
            </div>
            <div role="tabpanel" class="tab-pane" id="three">
                <table class="table ui-table">
                    <thead>
                    <tr>
                        <th><input class="ui-check-all" type="checkbox"/></th>
                        <th>考试名称</th>
                        <th>考试人</th>
                        <th>报名状态</th>
                        <th>报名时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="examNotDoList">
                    </tbody>
                </table>
                <ul class="pagination" id="examNotDoPage"></ul>
            </div>
        </div>

    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="examJoinItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.examName}}</td>
        <td>{{=it.examinee}}</td>
        <td>{{=it.addTime}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del del-first"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script type="text/template" id="examFinishItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.examName}}</td>
        <td>{{=it.examinee}}</td>
        <td>{{=it.addTime}}</td>
        <td>{{=it.score}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span>重新考试</a>
                <a class="btn btn-empty-theme ui-del del-second"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script type="text/template" id="examNotDoItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.examName}}</td>
        <td>{{=it.username}}</td>
        <td>{{=it.joinInString}}</td>
        <td>{{=it.addTime}}</td>
        <td>
            <div class="ui-link-group">
                {{? it.joinId}}
                <a class="btn btn-empty-theme ui-del del-second"><span class="fa fa-trash"></span> 删除</a>
                {{??}}
                无
                {{?}}
            </div>
        </td>
    </tr>
</script>
<script>
    $(function () {
        var examId = '${param.id}';
        ui.check.init()
        //list
        var examJoinTable = ui.table('examJoin', {
            req: {
                url: 'di/exam/api/get_exam_join.json',
                data: {eid: examId, exam: 0}
            },
            dataConver: function (d) {
                if (!d.examName) {
                    d.examName = '无'
                }
                if (!d.examinee) {
                    d.examinee = '无'
                }
                if (d.addTime) {
                    d.addTime = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                } else {
                    d.addTime = '未知时间'
                }
                return d
            },
            empty: {
                col: 5,
                html: '暂没人报名哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.on('click', '.del-first', function () {
                ui.del(d.id, function () {
                    examJoinTable.request(null, examJoinTable)
                }, 'di/exam/api/del_exam_join.json')
            })

        })
        examJoinTable.init()

        var examFinishTable = ui.table('examFinish', {
            req: {
                url: 'di/exam/api/get_exam_join.json',
                data: {eid:${param.id}, exam: 1}
            },
            dataConver: function (d) {
                if (!d.examName) {
                    d.examName = '无'
                }
                if (!d.examinee) {
                    d.examinee = '无'
                }
                if (d.addTime) {
                    d.addTime = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                } else {
                    d.addTime = '未知时间'
                }
                return d
            },
            empty: {
                col: 6,
                html: '暂没人完成考试哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.on('click', '.del-second', function () {
                ui.del(d.id, function () {
                    examFinishTable.request(null, examFinishTable)
                }, 'di/exam/api/del_exam_join.json')
            })

            $item.on('click', '.ui-edit', function () {
                var url = '/admin/di/exam/api/update_exam.json'
                ui.post(url, {id: d.id, by_id:${id}, userid: d.userid}, function () {
                    ui.alert("修改成功", function () {
                        window.location.reload()
                    })
                })
            })

        })
        examFinishTable.init()

        // 未考试
        var examNotDoTable = ui.table('examNotDo', {
            req: {
                url: 'di/exam/api/get_exam_not_do.json',
                data: {id: examId}
            },
            dataConver: function (d) {
                if (!d.examName) {
                    d.examName = '无'
                }
                if (d.joinIn) {
                    d.joinInString = '已报名'
                }else
                    d.joinInString = '未报名';
                if (d.addTime) {
                    d.addTime = new Date(d.addTime).format('yyyy-MM-dd hh:mm')
                } else {
                    d.addTime = '未知时间'
                }
                return d
            },
            empty: {
                col: 5,
                html: '暂没人未考试哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            $item.on('click', '.del-first', function () {
                ui.del(d.joinId, function () {
                    examNotDoTable.request(null, examNotDoTable)
                }, 'di/exam/api/del_exam_not_do.json')
            })

        })
        examNotDoTable.init()

        $('#exportBtn').click(function () {
            ui.openUrl('di/exam/api/export_exam.html', {exportType: 3, id: examId});
        });

    })
    function backForList() {
        ui.history.go('di/exam/list');
    }
</script>