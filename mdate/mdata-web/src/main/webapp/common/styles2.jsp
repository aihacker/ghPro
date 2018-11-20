<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/11/3
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageName = request.getServletPath();
    boolean show = false;
    boolean query = false;
    boolean kendo = false;
    boolean bootstrap = false;
    boolean dateInput = false;
    boolean multiSelect = false;
    boolean upload = false;
    boolean form = false;
    boolean needPage = false;
    if (pageName.endsWith("query.jsp")) {
        query = true;
    }
    else if (pageName.endsWith("show.jsp")) {
        show = true;
        dateInput = true;
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
        multiSelect = true;
        bootstrap = true;
    }
    if(qs.contains("&form&")) {
        form = true;
    }
    if(qs.contains("&upload&")) {
        upload = true;
    }
    if(qs.contains("&needPage&")) {
        needPage = true;
    }
    if(query) {
        kendo = true;
    }
    if(needPage){
        needPage = true;
        query = false;
    }
    if(show) {
        bootstrap = true;
        multiSelect = true;
        form = true;
    }
%>
<% if (kendo) {%>
    <link href="${home}/style/lib/kendo.common.css" rel="stylesheet"/>
    <link href="${home}/style/lib/kendo.default.css" rel="stylesheet"/>
    <link href="${home}/style/lib/kendo.override.css" rel="stylesheet"/>
<%}%>
<% if (bootstrap) {%>
    <link href="${home}/style/lib/bootstrap.css" rel="stylesheet"/>
<%}%>

<% if (multiSelect) {%>
    <link href="${home}/style/lib/bootstrap-multiselect.css" rel="stylesheet"/>
<%}%>

<% if (dateInput) {%>
<link href="${home}/style/lib/bootstrap-datepicker3.css" rel="stylesheet"/>
<%}%>

<% if (dateInput) {%>
<link href="${home}/style/upload.css" rel="stylesheet"/>
<%}%>

<link href="${home}/style/app.css" rel="stylesheet"/>

<% if (query) {%>
    <link href="${home}/style/grid.css" rel="stylesheet"/>
    <link href="${home}/style/query.css" rel="stylesheet">
<%}%>

<% if (show) {%>
<link href="${home}/style/show.css" rel="stylesheet">
<%}%>

<% if (form) {%>
<link href="${home}/style/form.css" rel="stylesheet">
<%}%>

<% if (needPage) {%>
<link href="${home}/style/page.css" rel="stylesheet">
<%}%>
