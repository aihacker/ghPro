<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/7/4
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pager">
    共 <em>${queryResult.rowCount}</em> 条记录，
    当前 <em><span>${queryResult.pageNo}/<span class="pageCount"
        >${queryResult.pageCount}</span></em> 页&nbsp;
    每页显示
    <%request.setAttribute("pageSizes", new Integer[]{10, 15, 20, 30, 50});%>
    <select class="pageSize">
        <c:forEach items="${pageSizes}" var="pageSize">
            <option
                    <c:if test="${queryResult.pageSize == pageSize}">selected</c:if>
                    >${pageSize}</option>
        </c:forEach>
    </select>
    条&nbsp;
    <span class="<c:if test="${queryResult.pageNo < 2}">disabled</c:if>">
        <a href="javascript:void page(1);">首页</a>
        <a href="javascript:void page(${queryResult.pageNo - 1});">上一页</a>
    </span>
    <span class="<c:if test="${queryResult.pageCount != -1
        && queryResult.pageNo >= queryResult.pageCount}"
        >disabled</c:if>">
        <a href="javascript:void page(${queryResult.pageNo + 1});">下一页</a>
        <a href="javascript:void page(${queryResult.pageCount});">末页</a>
    </span>
    跳转到<input type="text" size="2" style="width:2.2em;margin:0 4px;" id="txtPageNo"/>页
    <input type="button" value="GO" class="go-btm" onclick="page(Number(document.getElementById('txtPageNo').value));"/>
</div>