<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统管理</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script src="${home}/script/index/jquerySession.js"></script>
    <script>
        $(function () {
            $("#MySplitter").splitter({
                type: "v",
                sizeLeft: 150,
                minLeft: 100,
                minRight: 100,
                resizeToWidth: true,
                dock: "left"
            });
        });
    </script>
</head>
<body>
<div id="splitter-container">
    <div id="MySplitter" style="height: 100%;">
        <div id="LeftPane">
            <div class="left-list">
                <ul class="nav nav-pills nav-stacked">
                    <li code="0102"><a class="bg-ico" href="#"
                                       onclick="sys_menu_clicked(this,'${home}/system/role/query.html');">角色配置 </a></li>
                    <li code="0101"><a class="bg-ico" href="#"
                                       onclick="sys_menu_clicked(this,'${home}/system/user/query.html');">用户配置</a></li>
                    <%--如果下要下拉效果请添加下面注释代码，注意 ria-labelledby="dLabel" 要跟a的id一样--%>
                    <%--<ul class="dropdown-menu nav nav-pills nav-stacked" aria-labelledby="dLabel">
                        <li role="presentation"><a href="#">应用管理</a></li>
                    </ul>--%>
                    <li code="0103"><a class="bg-ico" href="#" onclick="sys_menu_clicked(this,'${home}/system/authority/query.html');">功能权限录入</a></li>
                    <li code="0104"><a class="bg-ico" href="#" onclick="sys_menu_clicked(this,'${home}/system/sysparameter/query.html');" >系统参数管理 </a></li>

                </ul>
            </div>
        </div>

        <div id="RightPane">
            <iframe src="" frameborder="0" id="frameQuery"></iframe>
        </div>
    </div>
</div>
<script>
    function setData(){
        var name = '<%=session.getAttribute("userName")%>';
        var perCode = "<%=session.getAttribute("system")%>";
        if (name && perCode) {
            $.session.set('name', name);
            $.session.set('perCode', perCode);
        } else {
            location.href = "${home}"
        }
    }
    setData();
    $(document).ready(function () {
        sys_menu_css();
    });
</script>
<script src="${home}/script/index/hidePageAndButton.js"></script>
</body>
</html>
