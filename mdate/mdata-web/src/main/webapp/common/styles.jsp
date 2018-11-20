<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/11/3
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageName = request.getServletPath();
    boolean showPage = false;
    boolean queryPage = false;
    boolean indexPage = false;
    boolean informationPage = false;
    boolean newPage = false;
    if (pageName.endsWith("query.jsp")) {
        queryPage = true;
    } else if (pageName.endsWith("information.jsp")) {
        informationPage = true;
    } else if (pageName.endsWith("new.jsp")) {
        newPage = true;
    } else if (pageName.endsWith("index.jsp")) {
        indexPage = true;
    }else if (pageName.endsWith("show.jsp")) {
        showPage = true;
    }
%>
<link rel="stylesheet" href="${home}/style/lib/bootstrap.min.css"/>
<link rel="stylesheet" href="${home}/style/my.css">

<% if (queryPage) {%>
<link rel="stylesheet" href="${home}/style/query.css">
<%}%>

<% if (informationPage) {%>
<link rel="stylesheet" href="${home}/style/query.css">
<%}%>

<% if (newPage) {%>
<link rel="stylesheet" href="${home}/style/show.css">
<%}%>

<% if (showPage) {%>
<link rel="stylesheet" href="${home}/style/show.css">
<%}%>

<% if (indexPage) {%>
<link rel="stylesheet" href="${home}/style/index.css">
<%}%>
