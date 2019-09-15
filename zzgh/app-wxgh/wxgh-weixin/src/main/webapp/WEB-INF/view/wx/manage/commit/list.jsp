<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/11
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>总经理接待安排表</title>
    <style>
        .table {
            border: 1px solid #cad9ea;
            color: #666;
        }

        .table th {
            background-repeat: repeat-x;
            height: 4rem;
            font-size:2rem
        }

        .table td,
        .table th {
            border: 1px solid #cad9ea;
            padding: 0 1em 0;
        }

        .table tr.alter {
            background-color: #f5fafe;
        }
    </style>
</head>
<body>
<div>
    <div style="width:100%;text-align: center;font-size: 4rem;margin-bottom: 2rem">2019年总经理接待日日程安排表</div>
    <table width="100%" class="table" id="tablevalue">
        <tr>
            <th width=15%>月份</th>
            <th width=15%>日期</th>
            <th width=30%>时间</th>
            <th width=40%>分公司经营班子</th>
        </tr>
        <tr>
            <th width=15%>1月</th>
            <th width=15%>30日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>胡平</th>
        </tr>
        <tr>
            <th width=15%>2月</th>
            <th width=15%>27日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>王伟铸</th>
        </tr>
        <tr>
            <th width=15%>3月</th>
            <th width=15%>27日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>罗永强</th>
        </tr>
        <tr>
            <th width=15%>4月</th>
            <th width=15%>24日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>徐青云</th>
        </tr>
        <tr>
            <th width=15%>5月</th>
            <th width=15%>29日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>刘宝龙</th>
        </tr>
        <tr>
            <th width=15%>6月</th>
            <th width=15%>26日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>胡平</th>
        </tr>
        <tr>
            <th width=15%>7月</th>
            <th width=15%>24日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>王伟铸</th>
        </tr>
        <tr>
            <th width=15%>8月</th>
            <th width=15%>28日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>罗永强</th>
        </tr>
        <tr>
            <th width=15%>9月</th>
            <th width=15%>25日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>徐青云</th>
        </tr>
        <tr>
            <th width=15%>10月</th>
            <th width=15%>30日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>刘宝龙</th>
        </tr>
        <tr>
            <th width=15%>11月</th>
            <th width=15%>27日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>胡平</th>
        </tr>
        <tr>
            <th width=15%>12月</th>
            <th width=15%>25日</th>
            <th width=30%>14:00-16:00</th>
            <th width=40%>王伟铸</th>
        </tr>
    </table>
</div>
</body>
</html>
