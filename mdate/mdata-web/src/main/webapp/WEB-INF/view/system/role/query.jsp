<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>角色管理</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <link rel="stylesheet" href="${home}/style/tree/metroStyle/metroStyle.css" type="text/css">
    <link rel="stylesheet" href="${home}/style/tree//zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.excheck.js"></script>
</head>
<style>
    table {
        font-size: 13px !important;
    }
</style>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="">角色管理</a></li>
                    <li><a href=""></a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <form:form modelAttribute="queryData" method="get">
        <div>
            <div class="form-inline clearfix">
                <div class="form-group col-xs-12">
                    <label class="font-color">角色名：</label>
                    <form:input path="name" cssClass="form-control width40"/>
                    <button type="submit" class="btn btn-danger">查询</button>
                </div>
            </div>
        </div>
    </form:form>

    <div class="add-table col-xs-12">
        <p><a href="operate.do?action=new_">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
            新增角色配置</a>
        </p>
        <table class="table table-bordered">
            <thead>
            <tr>
                <td><input type="checkbox"/></td>
                <td>角色名称</td>
                <td>角色代码</td>
                <td>是否可用</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <td><input type="checkbox" value="${row.role_id}"></td>
                        <%--<td>${status.index+1}</td>--%>
                    <td><a href="show.html?id=${row.role_id}">${row.role_name}</a></td>
                    <td>${row.role_code}</td>
                    <td>
                        <c:if test="${row.is_enable==0}">
                            是
                        </c:if>
                        <c:if test="${row.is_enable==1}">
                            否
                        </c:if>
                    </td>
                    <td class="op" style="width: 25%">
                        <a href="javascript:cfg(${row.role_id});">配置表数据权限</a>
                        <a href="javascript:formTree(${row.role_id});">配置功能权限</a>
                        <a href="javascript:edit(${row.role_id});">编辑</a>
                        <a href="javascript:del(${row.role_id});">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header" style="height: 25px">
                        <input type="hidden" id="roleId" value="">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <div>功能列表</div>
                    </div>
                    <div style="vertical-align: middle; text-align: center;">
                        <div>
                            <ul id="treeSelect" class="ztree"></ul>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" onclick="onCheck()" data-dismiss="modal" id="submit">
                            提交
                        </button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>
        <script src="${home}/script/system/role.js"></script>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
