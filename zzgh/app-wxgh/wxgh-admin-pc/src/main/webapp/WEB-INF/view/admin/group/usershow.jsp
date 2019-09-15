<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/21
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .panel-body {
        position: relative;
    }

    .ui-member-avatar {
        position: absolute;
        top: 20px;
        right: 40px;
    }

    .ui-member-avatar img {
        width: 50px;
        height: 50px;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        border-radius: 50%;
    }
</style>
<div class="row">
    <div class="ui-content ui-link-group">
        <button type="button" class="btn btn-empty-theme ui-back"><span
                class="fa fa-angle-double-left"></span> 返回
        </button>
    </div>
</div>
<div class="row ui-content" style="margin-top: 20px;">
    <div class="panel panel-info">
        <div class="panel-heading">会员信息</div>
        <div class="panel-body">
            <div class="ui-member-item">
                <label>姓名：</label>
                <span>${user.name}</span>
            </div>
            <div class="ui-member-item">
                <label>协会职务：</label>
                <c:if test="${user.type eq 1}">
                    <span>会长</span>
                </c:if>
                <c:if test="${user.type eq 2}">
                    <span>副会长</span>
                </c:if>
                <c:if test="${user.type eq 3}">
                    <span>理事</span>
                </c:if>
                <c:if test="${user.type eq 4}">
                    <span>会员</span>
                </c:if>
                <c:if test="${user.type eq 5}">
                    <span>测试-会员</span>
                </c:if>
            </div>
            <div class="ui-member-item">
                <label>协会积分：</label>
                <span>${empty user.score?0.0:user.score}</span>
            </div>
            <div class="ui-member-item">
                <label>剩余会费：</label>
                <span>${empty user.cost?0.0:user.cost}</span>
            </div>

            <div class="ui-member-avatar">
                <img src="${user.avatar}">
            </div>
        </div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">用户协会积分</div>
        <div class="panel-body">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>介绍</th>
                    <th>获得积分</th>
                    <th>花费会费</th>
                    <th>参与类型</th>
                    <th>时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="scoreList">
                </tbody>
            </table>
            <ul class="pagination" id="pagination"></ul>
        </div>
    </div>

    <div class="panel panel-info">
        <div class="panel-heading">会费充值记录</div>
        <div class="panel-body">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr>
                    <th>充值时间</th>
                    <th>充值状态</th>
                    <th>金额</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="costList">
                </tbody>
            </table>
            <ul class="pagination" id="paginationCost"></ul>
        </div>
    </div>
</div>

<script type="text/template" id="scoreItem">
    <tr>
        <td>{{=it.info}}</td>
        <td>{{=it.score}}</td>
        <td>{{=it.cost}}</td>
        <td>{{=it.joinTypeName}}</td>
        <td>{{=it.addTime}}</td>
        <td>
            <div class="ui-link-group">
                <a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span
                        class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script type="text/template" id="costItem">
    <tr>
        <td>{{=it.addTime}}</td>
        <td>{{=it.statusName}}</td>
        <td>{{=it.money}}</td>
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
        var __self = 'place/api/'
        var groupId = '${user.groupId}'
        var userid = '${user.userid}'

        var $scoreList = $('#scoreList')
        var $costList = $('#costList')

        $('.ui-back').on('click', function () {
            ui.history.go('group/show', {groupId: groupId})
        })

//        ui.page2.init(function (num) {
//            get_scores(num)
//        })
//
//        ui.page2.init(function (num) {
//            get_costs(num)
//        }, '#paginationCost')


        function get_scores(num, func) {
            var info = {action: 'score_list', userid: userid}
            if (num) info['currentPage'] = num
            $scoreList.empty()
            ui.get(__self, info, function (d) {
                var scores = d.scores
                if (scores && scores.length > 0) {
                    var tpl = doT.template($('#scoreItem').html())
                    for (var i in scores) {
                        var u = scores[i]

                        u['score'] = u.score ? u.score : 0.0
                        u['cost'] = u.cost ? u.cost : 0.0
                        u['joinTypeName'] = get_join_name(u.joinType)
                        u['addTime'] = new Date(u.addTime).format('yyyy-MM-dd hh:mm')

                        var $item = $(tpl(u))
                        $item.data('score', u)

                        $scoreList.append($item)

                        init_score_tr_event($item)
                    }
                } else {
                    $scoreList.append(ui.emptyTable(6, '暂无积分记录'))
                }
                ui.page2.setPage(d.page)
                if (func) func()
            })
        }

        function get_costs(num, func) {
            var info = {action: 'cost_list', userid: userid, groupid: groupId}
            if (num) info['currentPage'] = num
            $scoreList.empty()
            ui.get(__self, info, function (d) {
                var costs = d.costs
                if (costs && costs.length > 0) {
                    var tpl = doT.template($('#costItem').html())
                    for (var i in costs) {
                        var u = costs[i]

                        u['addTime'] = new Date(u.addTime).format('yyyy-MM-dd hh:mm')
                        u['statusName'] = '成功'

                        var $item = $(tpl(u))
                        $item.data('cost', u)

                        $costList.append($item)

                        init_cost_tr_event($item)
                    }
                } else {
                    $costList.append(ui.emptyTable(4, '暂无充值记录'))
                }
                ui.page2.setPage(d.page, '#paginationCost')
                if (func) func()
            })
        }

        function init_score_tr_event($item) {

        }

        function init_cost_tr_event($item) {

        }

        function get_join_name(type) {
            var str
            if (type == 1) {
                str = '参加'
            } else if (type == 2) {
                str = '请假'
            } else if (type == 3) {
                str = '缺席'
            }
            return str
        }
    })
</script>

