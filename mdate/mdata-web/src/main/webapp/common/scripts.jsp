<%--
	Created by IntelliJ IDEA.
	User: zzl
	Date: 2010-5-24
	Time: 15:34:55
	To change this template use File | Settings | File Templates.
--%>
<%%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%
    String pageName = request.getServletPath();
    boolean showPage = false;
    boolean queryPage = false;
    boolean selectPage = false;
    boolean newPage = false;
    boolean indexPage = false;
    boolean informationPage = false;
    if (pageName.endsWith("query.jsp")) {
        queryPage = true;
    }
    if (pageName.endsWith("index.jsp")) {
        indexPage = true;
        queryPage = true;
    }
    if (pageName.endsWith("new.jsp")) {
        newPage = true;
    }
    if (pageName.endsWith("information.jsp")) {
        informationPage = true;
    }
    if (pageName.endsWith("show.jsp")) {
        showPage = true;
    }
    if(pageName.endsWith("select.jsp")) {
        queryPage = true;
    }
    String params = "" + request.getAttribute("javax.servlet.include.query_string");
    boolean traits_validate = showPage || params.contains("validate");
%>
<script type="text/javascript">
    var _home = '${home}';
</script>

<script src="${home}/script/lib/jquery-3.1.1.min.js"></script>
<script src="${home}/script/lib/bootstrap.min.js"></script>
<script type="text/javascript" src="${home}/script/lib/jquery.form.js"></script>
<script type="text/javascript" src="${home}/script/pub.js"></script>
<script type="text/javascript" src="${home}/script/site.js"></script>


<%if(indexPage) {%>
<script type="text/javascript" src="${home}/script/lib/jquery.splitter.js"></script>
<script type="text/javascript" src="${home}/script/index.js"></script>
<%}%>


<% if (queryPage) {%>
<script type="text/javascript" src="${home}/script/query.js"></script>
<script type="text/javascript" src="${home}/script/querystyle.js"></script>
<%}%>

<% if (newPage) {%>
<script type="text/javascript" src="${home}/script/query.js"></script>
<%}%>

<% if (informationPage) {%>
<script type="text/javascript" src="${home}/script/query.js"></script>
<%}%>

<% if (showPage) {%>
<script type="text/javascript" src="${home}/script/show.js"></script>
<%}%>

<% if (traits_validate) {%>
<script type="text/javascript" src="${home}/script/lib/jquery.validate.js"></script>
<script type="text/javascript" src="${home}/script/lib/jquery.validate.ex.js"></script>
<%}%>