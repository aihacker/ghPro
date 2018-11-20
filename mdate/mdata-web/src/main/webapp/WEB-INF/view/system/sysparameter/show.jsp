<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>系统参数管理</title>

        <jsp:include page="/common/styles.jsp"/>
        <jsp:include page="/common/scripts.jsp"/>


        <script type="text/javascript">
            ${jsValidator}
            function onFormSubmit() {
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
    <body class="${param.edit == 1 or sysVar.id == null? 'edit': 'view'}">
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
                <form:form modelAttribute="sysVar" action="operate.json" method="post" id="baseInfo">
                    <input type="hidden" name="action" value="save"/>
                    <form:hidden path="id" cssClass="form-control"  />
                    <div class="clearfix mt20 form-horizontal">

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">变量名：</label>
                                <div class="col-sm-3">
                                    <form:input path="varName" id="varName" cssClass="form-control h30" />
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">变量值：</label>
                                <div class="col-sm-3">
                                    <form:input path="varValue" id="varValue" cssClass="form-control h30" />
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">变量代码：</label>
                                <div class="col-sm-3">
                                    <form:input path="varCode" id="varCode" cssClass="form-control h30" data-toggle="tooltip" data-placement="top" title="该值是唯一的"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">描述：</label>
                                <div class="col-sm-3">
                                    <form:textarea path="description" cssClass="form-control h30" />
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label class="col-sm-3 control-label mg20" style="margin-top: -7px">是否可用：</label>
                                <div class="col-sm-3">
                                    <input type="checkbox"  <c:if test="${sysVar.isEnable eq 1 }"> checked="checked" </c:if> name="isEnable" value="0" id="isEnable">
                                    <%--<form:checkbox path="isEnable" id="isEnable" onclick="this.value=this.checked?1:0" value="0"  label="可用"  />--%>
                                </div>
                            </div>
                        </div>
                    </div>
                    </div>
                    <div class="col-xs-6 clear-float center-block clearfix">
                        <input type="button" onclick="submitForm();" value="保存" class="btn btn-danger"/>
                        <input type="button" value="返回" class="btn btn-primary" onclick="location.href=document.referrer;"/>
                    </div>
                </form:form>
            </div>
        </div>
        <%--<script src="${home}/script/test.js"></script>--%>
        <script>
            $(function () {
                $('[data-toggle="tooltip"]').tooltip();
            });

            $("#isEnable").change(function(){
                if($("#isEnable").prop("checked")){
                    $("#isEnable").val(1);
                }else{
                    $("#isEnable").val(0);
                }
            });

            function submitForm(){
                var $varCode = $("#varCode").val();
                var $varValue = $("#varValue").val();
                var $varName = $("#varName").val();
                if($varName == ""){
                    alert("变量名称不能为空！");
                    return false;
                }
                if($varValue == ""){
                    alert("变量值不能为空！");
                    return false;
                }
                if($varCode == ""){
                    alert("变量代码不能为空！");
                    return false;
                }
                $.ajax({
                    url: 'operate.json',
                    data: $("#baseInfo").serialize(),
                    traditional: true,
                    success: function (result) {
                        if (result.ok) {
                            alert("保存成功!");
                            window.location = 'query.html';
                        }else{
                            alert(result.msg);
                        }
                    },
                    error: function () {
                        alert('form submit error..')
                    }
                });
            }
            /*function showCheck(){
                if($("#isEnable").val()==1){
                    $("#isEnable").attr("checked",true);
                    $("#isEnable").val(1);
                }else{
                    $("#isEnable").attr("checked",false);
                    $("#isEnable").val(0);
                }
            }
            showCheck();*/

        </script>
    </body>
</html>
