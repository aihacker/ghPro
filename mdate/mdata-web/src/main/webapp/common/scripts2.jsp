<%--
  Created by IntelliJ IDEA.
  User: gan
  Date: 2017/7/19
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageName = request.getServletPath();
    boolean show = false;
    boolean query = false;
    boolean needKendo = false;
    boolean needGrid = false;
    boolean bootstrap = false;
    boolean dateInput = false;
    boolean multiSelect = false;
    boolean upload = false;
    boolean jvalidate = false;
    boolean needPage = false;

    if (pageName.endsWith("query.jsp")) {
        query = true;
    }
    else if (pageName.endsWith("show.jsp")) {
        show = true;
    }
    String qs = "&" + request.getAttribute("javax.servlet.include.query_string") + "&";
    if(qs.contains("&show&")) {
        show = true;
    }
    else if(qs.contains("&query&")) {
        query = true;
    }

    if(qs.contains("&dateInput&")) {
        bootstrap = true;
        dateInput = true;
    }
    if(qs.contains("&multiSelect&")) {
        bootstrap = true;
        multiSelect = true;
    }
    if(qs.contains("&upload&")) {
        upload = true;
    }
    if(qs.contains("&needPage&")) {
        needPage = true;
    }

    if(show) {
        bootstrap = true;
        dateInput = true;
        multiSelect = true;
    }
    if(query) {
        needKendo = true;
        needGrid = true;
    }
%>
<script>
    var _home = '${home}';
</script>

<script src="${home}/script/lib/jquery.js"></script>
<script src="${home}/script/lib/jquery.serialize-object.js"></script>
<script src="${home}/script/pub.js"></script>
<script src="${home}/script/app.js"></script>

<% if (needKendo) {%>
<script src="${home}/script/lib/kendo.custom.js"></script>
<%}%>
<% if (needGrid) {%>
<%--<script src="${home}/script/lib/kendo.grid.min.js"></script>--%>
<script src="${home}/script/lib/kendo.grid.js"></script>
<%--<script src="${home}/script/lib/kendo.grid.js"></script>--%>
<%}%>
<% if (query) {%>
<script src="${home}/script/query.js"></script>
<script src="${home}/script/grid.js"></script>
<%}%>
<% if (bootstrap) {%>
<script src="${home}/script/lib/bootstrap.js"></script>
<%}%>
<% if (dateInput) {%>
<script src="${home}/script/lib/bootstrap-datepicker.js"></script>
<script src="${home}/script/lib/bootstrap-datepicker.zh-CN.js"></script>
<script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
<%}%>

<% if (multiSelect) {%>
<script src="${home}/script/lib/bootstrap-multiselect.js"></script>
<%}%>

<% if (upload) {%>
<script src="${home}/script/upload.js"></script>
<%}%>

<% if (needPage) {%>
<script src="${home}/script/page.js"></script>
<%}%>

<% if (show) {%>
<script src="${home}/script/lib/jquery.form.js"></script>
<script src="${home}/script/lib/jquery.validate.js"></script>
<script src="${home}/script/lib/jquery.validate.ex.js"></script>
<script src="${home}/script/show.js"></script>
<%}%>
