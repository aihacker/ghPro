<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>权限配置管理</title>

    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>


    <script type="text/javascript">
        ${jsValidator}
        function onFormSubmit() {
            var moduleCode = $("#moduleCode").val();
            var moduleName = $("#moduleName").val();
            if(null == moduleName || moduleName == ''){
                alert("请输入模块名称");
                return false;
            }
            if(null == moduleCode || moduleCode == ''){
                alert("请输入模块代码");
                return false;
            }

            if (!window.confirm('确定保存吗?')) {
                return false;
            }
        }
        function edit() {
            var $body = $(document.body);
            $body.removeClass('view');
        }
    </script>

</head>
<body class="${param.edit == 1 or sysModule.moduleId == null? 'edit': 'view'}">
<div class="">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="javascript:location.href=document.referrer;">返回</a></li>
                    <li><a href="javascript:location.href=document.referrer;">配置管理</a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="main clearfix">
        <form:form modelAttribute="sysModule" action="operate.json" id="baseInfo" method="post">
        <form:hidden path="moduleId" cssClass="form-control"/>
        <input type="hidden" name="action" value="save"/>
        <div class="clearfix mt20 form-horizontal">
            <div class="col-xs-12 mt10">
                <div class="form-group">
                    <label class="col-sm-3 control-label mg20">上级菜单：</label>
                    <div class="col-sm-3">
                        <select name="parentId" style="width: 130px;height: 30px">
                            <c:forEach items="${parents}" var="parent">
                                <option value="${parent.moduleId}" <c:if
                                        test="${parent.moduleId == moduleParent}"> selected="selected"</c:if>>${parent.moduleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-xs-12 mt10">
                <div class="form-group">
                    <label class="col-sm-3 control-label mg20">模块名称：</label>
                    <div class="col-sm-3">
                        <form:input path="moduleName" cssClass="form-control h30"/>
                    </div>
                </div>
            </div>
            <div class="col-xs-12 mt10">
                <div class="form-group">
                    <label class="col-sm-3 control-label mg20">模块代码：</label>
                    <div class="col-sm-3">
                        <form:input path="moduleCode" onchange="testingCode();" cssClass="form-control h30" data-toggle="tooltip" data-placement="top" title="该值是唯一的"/>
                    </div>
                </div>
            </div>

            <div class="col-xs-12 mt10">
                <div class="form-group">
                    <label class="col-sm-3 control-label mg20" style="margin-top: -7px">是否为系统菜单：</label>
                    <div class="col-sm-3">
                        <input type="checkbox" name="ifSystem" onclick="this.value=this.checked?0:1"
                                <c:if test="${ifSystem eq 0}"> checked="checked" </c:if>  value="${ifSystem == 0 ? 0 : ifSystem}"/>
                        <%--<form:checkbox path="ifSystem" onclick="this.value=this.checked?1:0" value="${null == ifSystem ? 1 : ifSystem}" />--%>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-3 clear-float center-block mt20 clearfix">
        <input type="submit" value="保存" class="btn btn-danger pull-left"/>
        <input type="button" value="返回" class="btn btn-primary pull-right" onclick="location.href=document.referrer;"/>
    </div>
    </form:form>
</div>
</div>
<script src="${home}/script/test.js"></script>
<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
    //showCheck("ifSystem");
    function testingCode(){
        var moduleCode = $("#moduleCode").val();
        $.ajax({
            dataType: 'text',
            url: 'show.html?action=testingCode',
            data:{code:moduleCode},
            success: function (result) {
                var result= jQuery.parseJSON(result);
                if(!result.ok){
                    $("#moduleCode").val('');
                    alert(result.msg);

                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
        /*
        pub.getJSON("show.html?action=testingCode",{code:moduleCode},function(result){
            /!*if(!result.ok){
                console.log(moduleCode);
                $("#moduleCode").innerHTML='';
                $("#moduleCode").val('');
                $("#moduleCode").value='';
                console.log($("#moduleCode").val());
                console.log($("#moduleCode").innerHTML);
                console.log($("#moduleCode").html);
                alert(result.msg);
            }*!/
        });*/
    }

</script>
</body>
</html>
