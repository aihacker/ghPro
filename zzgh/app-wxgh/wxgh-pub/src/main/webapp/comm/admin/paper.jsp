<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2015/7/4
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pager">
    共 <em id="totalCount"></em> 条记录，
    当前 <em><span id="span-currentPage"></span>/<span id="span-totalPage"></span></em> 页&nbsp;
    每页显示
    <select id="select-for-show">
        <option selected="selected">10</option>
        <option>15</option>
        <option>20</option>
        <option>30</option>
        <option>50</option>
    </select>
    条&nbsp;
    <span class="">
        <a id="a-fist-page">首页</a>
        <a id="a-last">上一页</a>
    </span>
    <span class="">
        <a id="a-next">下一页</a>
        <a id="a-end-page">末页</a>
    </span>
    跳转到<input min="1" id="input-page-go" type="number" size="2" style="width:2.2em;margin:0 4px;"/>页
    <input id="btn-page-go" type="button" value="GO"/>
</div>