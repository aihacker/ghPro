<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/19
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    td > img {
        width: 20px;
        height: 20px;
        margin-right: 2px;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        border-radius: 50%;
    }

    .ui-act-name p {
        width: 140px;
        max-width: 140px;
    }
</style>
<div class="row">
    <div class="ui-content ui-link-group">
        <button type="button" class="btn btn-empty-theme ui-back"><span
                class="fa fa-angle-double-left"></span> 返回
        </button>
    </div>
</div>
<div class="row ui-margin-top-20">
    <div class="tab-content ui-content">
        <ul class="nav nav-tabs" id="userNav">
            <li class="active">
                <a id="userTab" href="#userPane" data-toggle="tab">成员列表</a>
            </li>
            <li>
                <a id="actTab" href="#actPane" data-toggle="tab">协会活动</a>
            </li>
        </ul>
        <div class="tab-pane active" id="userPane">
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>成员信息</th>
                    <th>协会职务</th>
                    <th>加入时间</th>
                    <th>
                        <div class="dropdown" id="userStatusDropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown">成员状态
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li data-status="0"><a href="javascirpt:;">待审核</a></li>
                                <li class="active" data-status="1"><a href="javascirpt:;">已加入</a></li>
                                <li data-status="2"><a href="javascirpt:;">加入失败</a></li>
                            </ul>
                        </div>
                    </th>
                    <th>积分
                        <small>（分）</small>
                    </th>
                    <th>剩余会费
                        <small>（元）</small>
                    </th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="userList">
                </tbody>
            </table>
            <ul class="pagination" id="pagination"></ul>
        </div>
        <div class="tab-pane" id="actPane">
            <table class="table ui-table">
                <thead>
                <tr>
                    <th><input class="ui-check-all" type="checkbox"/></th>
                    <th>封面</th>
                    <th>活动名称</th>
                    <th>联系人</th>
                    <th>活动时间</th>
                    <th>活动地点</th>
                    <th>
                        <div class="dropdown" id="actStatusDropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown">审核状态
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li data-status="0" class="active"><a href="javascirpt:;">待审核</a></li>
                                <li data-status="1"><a href="javascirpt:;">已通过</a></li>
                                <li data-status="2"><a href="javascirpt:;">不通过</a></li>
                            </ul>
                        </div>
                    </th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="actList">
                </tbody>
            </table>
            <ul class="pagination" id="paginationAct"></ul>
        </div>
    </div>
</div>

<div id="deviceEditModule" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">活动详情</h4>
            </div>
            <div class="modal-body">
                <form id="deviceForm">
                    <div class="form-group">
                        <label>活动名称</label>
                        <div id="name"></div>
                    </div>
                    <div class="form-group">
                        <label>活动详情</label>
                        <div id="info"></div>
                    </div>
                    <div class="form-group">
                        <label>所属协会</label>
                        <div id="groupName"></div>
                    </div>
                    <div class="form-group">
                        <label>电话</label>
                        <div id="phone"></div>
                    </div>
                    <div class="form-group">
                        <label>开始时间</label>
                        <div id="startTime"></div>
                    </div>
                    <div class="form-group">
                        <label>结束时间</label>
                        <div id="endTime"></div>
                    </div>
                    <div class="form-group">
                        <label>活动地点</label>
                        <div id="address"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/template" id="userItem">
    <tr>
        <td><input class="ui-check" type="checkbox"/></td>
        <td><img src="{{=it.avatar}}"> {{=it.name}}<br>{{=it.deptname}}</td>
        <td>{{=it.typeName}}</td>
        <td>{{=it.joinTime}}</td>
        <td>{{=it.statusName}}</td>
        <td>{{=it.score}}</td>
        <td>{{=it.cost}}</td>
        <td>
            <div class="ui-link-group">
                <div class="dropdown">
                    <a data-toggle="dropdown" class="btn btn-empty-danger ui-apply">审核 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        {{? it.status===0 || it.status===2}}
                        <li data-status="1"><a href="javascript:;">通过</a></li>
                        {{?}}
                        {{? it.status===0 || it.status===1}}
                        <li data-status="2"><a href="javascript:;">不通过</a></li>
                        {{?}}
                    </ul>
                </div>
                <%--<a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span--%>
                        <%--class="fa fa-edit"></span> 编辑</a>--%>
                <%--<a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>--%>
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>
            </div>
        </td>
    </tr>
</script>
<script type="text/template" id="actItem">
    <tr>
        <td><input class="ui-check" type="checkbox"/></td>
        <td><img src="{{=it.headImg}}"></td>
        <td title="活动简介：{{=it.info}}" class="ui-act-name"><p>{{=it.name}}</p></td>
        <td>{{=it.username}}</td>
        <td>{{=it.startTime}}-{{=it.endTime}}</td>
        <td>{{=it.address}}</td>
        <td style="min-width: 60px;" title="{{=it.score}}">{{=it.statusName}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <div class="ui-link-group">
                    <div class="dropdown">
                        <a data-toggle="dropdown" class="btn btn-empty-danger ui-apply">审核 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            {{? it.status===0 || it.status===2}}
                            <li data-status="1"><a href="javascript:;">通过</a></li>
                            {{?}}
                            {{? it.status===0 || it.status===1}}
                            <li data-status="2"><a href="javascript:;">不通过</a></li>
                            {{?}}
                        </ul>
                    </div>
                    <%--<a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span--%>
                    <%--class="fa fa-edit"></span> 编辑</a>--%>
                    <%--<a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>--%>
                    <a class="btn btn-empty-theme ui-see"><span class="fa fa-eye"></span> 查看</a>
            </div>
        </td>
    </tr>
</script>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
        var __self = 'group/api/'
        var groupId = '${param.groupId}'
        var actShow = false

        var $userList = $('#userList')
        $userList.data('status', 1)

        var $actList = $('#actList')
        $actList.data('status', 0)

        var $userNav = $("#userNav")

        var $modal = $('#deviceEditModule')

        ui.check.init()

        //获取用户列表
        var userTable = ui.table('user',{
            req:{
                url:__self+ "userlist.json",
                data:{
                    groupId: groupId,
                    status: $userList.data('status')
                }
            },
            empty:{
                col: 8,
                html: '暂无成员哦'
            },
            dataConver:function(d){
                d.typeName = get_type_name(d.type);
                d.statusName = get_status_name(d.status);
                d.joinTime = new Date(d.joinTime).format("yyyy-MM--dd hh:mm")
                return d
            }
        },function($item,d){
            $item.data('data',d);

            $item.on("click",".ui-see",function(){
                ui.history.go('group/usershow',{id:d.id})
            })

            //审核
            $item.on('click', '.dropdown-menu li', function () {
                var $self = $(this)
                var status = $self.data('status')
                var msg = $.trim($self.text())
                ui.confirm('是否' + msg + '审核？', function () {
                    var info = {
                        userid: d.userid,
                        status: status
                    }
                    ui.post(__self+"userapply.json",info,function () {
                        ui.alert("审核成功",function(){
                            userTable.refresh({status:d.status})
                        })
                    })
                })
            })


        })
        userTable.init()

        function get_type_name(type) {
            var str
            if (type == 1) {
                str = '会长'
            } else if (type == 2) {
                str = '副会长'
            } else if (type == 3) {
                str = '理事'
            } else if (type == 4) {
                str = '会员'
            } else if (type == 5) {
                str = '测试-会员'
            } else {
                str = '未知(' + type + ')'
            }
            return str
        }

        function get_status_name(status) {
            var str
            if (status == 0) {
                str = '未审核'
            } else if (status == 1) {
                str = '已加入'
            } else if (status == 2) {
                str = '加入失败'
            } else {
                str = '未知(' + status + ')'
            }
            return str
        }

        $('.ui-back').on('click', function () {
            ui.history.go('group/list')
        })

        $('#userStatusDropdown').on('click', '.dropdown-menu li', function () {
            var $self = $(this)
            var status = $self.data('status')
            if ($userList.data('status') != status) {
                $('#userStatusDropdown .dropdown-menu li.active').removeClass('active')
                $self.addClass('active')
                $userList.data('status', status)
                var data = {
                    groupId:groupId,
                    status:$userList.data('status')
                }
                userTable.refresh(data)
            }
        })

        $('#actStatusDropdown').on('click', '.dropdown-menu li', function () {
            var $self = $(this)
            var status = $self.data('status')
            if ($actList.data('status') != status) {
                $('#actStatusDropdown .dropdown-menu li.active').removeClass('active')
                $self.addClass('active')
                $actList.data('status', status)
                var data = {
                    groupId:groupId,
                    status:$actList.data('status')
                }
                actTable.refresh(data)
            }
        })

        /**
         * 获取活动列表
         * @param num
         * @param func
         */
            //获取用户列表
        var actTable = ui.table('act',{
                req:{
                    url:__self+ "actshowlist.json",
                    data:{
                        groupId: groupId,
                        status: $actList.data('status')
                    }
                },
                empty:{
                    col: 8,
                    html: '暂无未审核活动哦'
                },
                dataConver:function(d){
                    d.username = d.userName
                    d.startTime = new Date(d.startTime).format("yyyy-MM-dd hh:mm")
                    d.endTime = new Date(d.endTime).format("yyyy-MM-dd hh:mm")
                    d.statusName = get_act_status(d.status)
                    return d
                }
            },function($item,d){
                $item.data('data',d);

                //审核
                $item.on('click', '.dropdown-menu li', function () {
                    var $self = $(this)
                    var status = $self.data('status')
                    var msg = $.trim($self.text())
                    ui.confirm('是否' + msg + '审核？', function () {
                        var info = {
                            id: d.id,
                            status: status
                        }
                        ui.post(__self+"actapply.json",info,function () {
                            ui.alert("审核成功",function(){
                                actTable.refresh({status:d.status})
                            })
                        })
                    })
                })

                //查看
                $item.on('click','.ui-see',function(){
                    var id = d.id
                    editId = id;
                    $modal.data('id', id)
                    $modal.modal('show')

                    var url = __self+"actget.json"
                    ui.get(url,{id:id},function (d) {
                        initModal(d)
                    })

                    function initModal(d) {
                        $("#name").html(d.datas[0].name)
                        $("#info").html(d.datas[0].info)
                        $("#groupName").html(d.datas[0].groupName)
                        $("#startTime").html(new Date(d.datas[0].startTime).format("yyyy-MM-dd hh:mm"))
                        $("#endTime").html(new Date(d.datas[0].endTime).format("yyyy-MM-dd hh:mm"))
                        $("#address").html(d.datas[0].address);
                    }
                })
            })
        actTable.init()


//        $('#actTab').on('show.bs.tab', function () {
//            if (actShow === false) {
//                ui.page2.init(function (num) {
//                    get_acts(num)
//                }, '#paginationAct')
//                actShow = true
//            }
//        })

        $userNav.on('click', 'li', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                $userNav.find('li.active').removeClass('active')
                $self.addClass('active')
                $self.find('i.fa').removeClass('hidden')

                actShow = !actShow;
                if(actShow == false){
                    $("#actPane").removeClass("active")
                    $("#userPane").addClass("active")
                }else {
                    $("#userPane").removeClass("active")
                    $("#actPane").addClass("active")
                }
//                lawTable.refresh({status: $self.data('status')}, function () {
//                    $self.find('i.fa').addClass('hidden')
//                })
            }
        })


        function init_user_tr_event($item) {
            var user = $item.data('user')
            $item.on('click', '.ui-see', function () {
                ui.history.go('group/usershow', {id: user.id})
            })
        }

        function init_act_tr_event($item) {
            var act = $item.data('act')
            $item.on('click', '.ui-see', function () {
                ui.history.go('group/actshow', {id: act.id, groupid: groupId})
            })
            $item.find('.ui-act-name p').ellipsis({row: 2})
        }

        function get_type_name(type) {
            var str
            if (type == 1) {
                str = '会长'
            } else if (type == 2) {
                str = '副会长'
            } else if (type == 3) {
                str = '理事'
            } else if (type == 4) {
                str = '会员'
            } else if (type == 5) {
                str = '测试-会员'
            } else {
                str = '未知(' + type + ')'
            }
            return str
        }

        function get_status_name(status) {
            var str
            if (status == 0) {
                str = '未审核'
            } else if (status == 1) {
                str = '已加入'
            } else if (status == 2) {
                str = '加入失败'
            } else {
                str = '未知(' + status + ')'
            }
            return str
        }

        function get_act_status(status) {
            var str
            if (status == 0) {
                str = '未审核'
            } else if (status == 1) {
                str = '已通过'
            } else if (status == 2) {
                str = '不通过'
            } else {
                str = '未知(' + status + ')'
            }
            return str
        }
    })
</script>

