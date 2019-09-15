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

</div>
<div class="row ui-content" style="margin-top: 20px;">
    <a href="#" onclick="downexcel();">导出本部预约列表</a>
    <a href="#" onclick="downexcel2();">导出禅城预约列表</a>
</div>

<script type="text/template" id="scoreItem">
    <tr>

        <%--<td>--%>
        <%--<div class="ui-link-group">--%>
        <%--<a href="#addPane" data-toggle="tab" class="btn btn-empty-theme ui-edit"><span--%>
        <%--class="fa fa-edit"></span> 编辑</a>--%>
        <%--<a class="btn btn-empty-theme ui-del"><span class="fa fa-trash"></span> 删除</a>--%>
        <%--</div>--%>
        <%--</td>--%>
    </tr>
</script>
<script type="text/template" id="costItem">

</script>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script>
    function downexcel(){
        ui.confirm('是否下载文件？', function () {
            /*wxgh.openUrl($self.data('url'));*/
            window.location.href="union/api/downexcel.html";
        });
    }
    function downexcel2(){
        ui.confirm('是否下载文件？', function () {
            /*wxgh.openUrl($self.data('url'));*/
            window.location.href="union/api/downexcelsecond.html";
        });
    }
</script>
