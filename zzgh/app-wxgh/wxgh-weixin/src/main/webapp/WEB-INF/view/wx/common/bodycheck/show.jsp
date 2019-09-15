<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/1
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>"2018健康生活"员工体检活动</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

<%--<h1 class="mui-title">基本信息填写</h1>--%>
<%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">下一步</a>--%>
<%--</header>--%>

<%--<div class="ui-fixed-bottom">--%>
    <%--<button id="nextBtn" type="button" class="mui-btn mui-btn-primary">预约</button>--%>
<%--</div>--%>

<header class="mui-bar mui-bar-nav">
    <a class="mui-icon mui-icon-bars mui-pull-left mui-plus-visible"></a>
    <a id="info" class="mui-icon mui-icon-info-filled mui-pull-right" style="color: #999;"></a>
    <h1 class="mui-title">我的预约</h1>

</header>

<div class="mui-content">
    <c:if test="${has eq 1}"><form id="applyForm">

        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                <span class="input-row-span ui-text-info" id="name">${wxgh_user.name}</span>
            </div>
            <div class="mui-input-row">
                <label>性&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                <span class="input-row-span ui-text-info" id="gender">${info.gender}</span>
            </div>
                <%--<div class="mui-input-row">--%>
                <%--<label>部&nbsp;&nbsp;&nbsp;&nbsp;门</label>--%>
                <%--<span class="input-row-span ui-text-info">${deptname}</span>--%>
                <%--</div>--%>
            <div class="mui-input-row">
                <label>联系电话</label>
                <span class="input-row-span ui-text-info" id="mobile">${info.mobile}</span>
            </div>
            <div class="mui-input-row">
                <label>部&nbsp;&nbsp;&nbsp;&nbsp;门</label>
                <span class="input-row-span ui-text-info" id="dept">${info.dept}</span>
            </div>
            <div class="mui-input-row">
                <label>身份证号</label>
                <span class="input-row-span ui-text-info" id="idcard">${info.idcard}</span>
            </div>
            <div class="mui-input-row">
                <label>年龄</label>
                <span class="input-row-span ui-text-info" id="age">${info.age}</span>
            </div>
            <div class="mui-input-row">
                <label>婚姻状况</label>
                <span class="input-row-span ui-text-info" id="marriage">${info.marriage}</span>
            </div>
            <div class="mui-input-row">
                <label>套餐额度</label>
                <span class="input-row-span ui-text-info" id="quote">${info.quote}</span>
            </div>
            <div class="mui-input-row" style="height:80px">
                <label>选择套餐</label>
                <span id="cateSpan" class="input-row-span ui-text-info">${info.category}</span>
            </div>
            <div class="mui-input-row" id="plus">
                <label>加项套餐</label>
                <span class="input-row-span ui-text-info">${info.plus}</span>
            </div>
            <div class="mui-input-row" id="fuli" style="display: none">
                <label>福利金额</label>
                <span class="input-row-span ui-text-info">${info.fuli}</span>
            </div>
            <div class="mui-input-row">
                <label>额度提示</label>
                <span class="input-row-span ui-text-info" id="money">${info.extra}</span>
            </div>
            <div class="mui-input-row" id="encore" style="display: none">
                <label>需补金额</label>
                <span class="input-row-span ui-text-info">${info.encore}</span>
            </div>
        </div>

    </form></c:if>
    <c:if test="${has eq 0}">暂无预约记录</c:if>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>


<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

    var encore = '${info.encore}'
    if(parseInt(encore)>0){
        $("#encore").show()
    }

    var fuli = '${info.fuli}'
    if(parseInt(fuli)>0){
        $("#fuli").show()
    }
</script>
</body>

</html>

