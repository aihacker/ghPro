<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/15
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <table class="table" id="fourListTable">
        <caption>
            <a id="addBtn" title="" type="button" href="javascript:;"
               class="btn btn-link">添加党员</a>
        <form id="searchForm" class="form-inline pull-right" style="display: inline-block;">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="请输入党员名字/手机号">
            </div>
            <button type="button" class="btn">搜索</button>
        </form>
        </caption>
        <thead>
        <tr>
            <th>党员ID</th>
            <th>姓名</th>
            <th>电话号码</th>
            <th>工作单位</th>
            <th>
                <div class="dropdown" id="isRepublican">
                    <a title="全部" id="contentTh" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        党员属性
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="contentTh">
                        <li role="presentation" class="disabled"><a data-type="IsRepublican" data-val="">全部</a>
                        </li>
                        <li role="presentation"><a data-type="IsRepublican" data-val="1" class="people_type">正式党员</a>
                        </li>
                        <li role="presentation"><a data-type="IsRepublican" data-val="2" class="people_type">预备党员</a>
                        </li>
                    </ul>
                </div>
            </th>
            <th>
                <div class="dropdown" id="groupid">
                    <a title="全部" id="conditTh1" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        所属党支部
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="contentTh1">
                        <li role="presentation" class="disabled"><a data-type="groupid" data-val="">全部</a>
                        </li>
                        <c:forEach items="${group}" var="itm">
                            <li role="presentation" class="group_li"><a data-type="groupid"
                                                       data-val="${itm.groupId}" class="group_name">${itm.ccpdepart}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </th>
            <th>籍贯</th>
            <th>
                <div class="dropdown" id="nation">
                    <a title="全部" id="conditTh2" data-toggle="dropdown" aria-haspopup="true"
                       aria-expanded="false">
                        民族
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="conditTh2">
                        <li role="presentation" class="disabled"><a data-type="Nation" data-val="">全部</a>
                        </li>
                        <c:forEach items="${nations}" var="itm">
                            <li role="presentation" class="nation_li"><a data-type="Nation" data-val="${itm.nation}" class="nation">${itm.nation}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </th>
            <th>学历</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="indexList">
        </tbody>
    </table>
    <ul class="pagination" id="indexPage"></ul>
</div>
<script type="text/template" id="indexItem">
    <tr>
        <td>{{=it.id}}</td>
        <td>{{=it.username}}</td>
        <td>{{=it.mobile}}</td>
        <td>{{=it.company}}</td>
        <td>{{=it.isRepublican}}</td>
        <td>{{=it.ccpdepart}}</td>
        <td>{{=it.nativePlace}}</td>
        <td>{{=it.nation}}</td>
        <td>{{=it.education}}</td>
        <td class="ui-table-operate">
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>
            </div>
        </td>
    </tr>
</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    $(function () {
       var _self='/admin/party/api/get.json'
        ui.check.init()
        var indexTable = ui.table('index',{
            req:{
                url:_self
            }, dataConver: function (d) {
                d['isRepublican'] = isRepublican(d.isRepublican)
                d['company']=hasCompany(d.company)
                return d
            },
            empty:{
                col:11,
                html:'暂时未录入数据'
            }
        },function ($item,d) {
          $item.data('data',d)
        })
        indexTable.init()

        //按照所属支部查询
        $('.group_li').on('click','.group_name',function () {
            var groupId = $(this).data('val')
            indexTable.refresh({groupId: groupId})
        })

        //按正式党员预备党员查询
        $('.dropdown-menu').on('click','.people_type',function () {
            var isRepublican=$(this).data('val')
            indexTable.refresh({IsRepublican:isRepublican})
        })

        //模糊查询
        $('.btn').click(function () {
            var searchKey=$('.form-control').val()
            indexTable.refresh({searchKey:searchKey})
        })

        function isRepublican(status) {
            var str
            if (status == 1) {
                str = '正式党员'
            } else if (status == 2) {
                str = '预备党员'
            } else {
                str = ''
            }
            return str
        }
        
        function hasCompany(company) {
            var str
            if(!company)
                str=''
            return str
        }
    });
</script>

