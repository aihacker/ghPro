<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>系统参数信息</title>
        <link rel="stylesheet" href="${home}/style/lib/bootstrap.css"/>
        <script src="${home}/script/lib/jquery.js"></script>
        <script src="${home}/script/lib/bootstrap.min.js"></script>
        <script src="${home}/script/lib/jquery.form.js"></script>
        <script  src="${home}/script/pub.js"></script>
        <script  src="${home}/script/site.js"></script>
        <script  src="${home}/script/show.js"></script>

        <script src="${home}/script/lib/jquery.validate.js"></script>
        <script src="${home}/script/lib/jquery.validate.ex.js"></script>
        <script type="text/javascript">
            ${jsValidator}
            //            $(function () {
//                $('.form-pane').find('select').kendoDropDownList();
//                $('.btn-cancel').click(function () {
//                    var $body = $(document.body);
//                    if ($body.hasClass('edit')) {
//                        location.href=document.referrer;
//                    }
//                    else {
//                        $body.addClass('view');
//                    }
//                });
//            });

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
            <div class="col-xs-12">
                <div class="three-nav col-xs-12">
                    <div class="row">
                        <ol class="breadcrumb">
                            <li><a href="javascript:location.href=document.referrer;">返回</a></li>
                            <li><a href="javascript:location.href=document.referrer;">系统参数管理</a></li>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>

            <div class="main clearfix">
                <form:form modelAttribute="sysVar" action="operate.json">
                    <input type="hidden" name="action" value="save"/>
                    <div class="clearfix mt20">
                        <div class="col-xs-6 ">
                            <div class="form-inline clearfix">
                                <div class="form-group col-xs-12" style="position: relative">
                                    <label  class="font-color">参数名称：</label>
                                    <form:input path="varName" cssClass="form-control width70" placeholder="参数名称"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="form-inline clearfix">
                                <div class="form-group col-xs-12">
                                    <label class="font-color">参数值：</label>
                                    <form:input path="varValue" cssClass="form-control width70" placeholder="参数值"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="clearfix mt20">
                        <div class="col-xs-12">
                            <div class="form-inline clearfix">
                                <div class="form-group col-xs-12">
                                    <label  class="font-color">详细信息：</label>
                                    <form:textarea path="description" rows="3" class="form-control width90 sysm font-color" contenteditable="true" />
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-xs-3 clear-float center-block mt20 clearfix">
                        <input type="submit" value="保存" class="btn btn-danger pull-left" />
                        <%--<input type="reset" value="重置" class="btn btn-primary pull-right" />--%>
                        <input type="button" value="返回" class="btn btn-primary pull-right" onclick="location.href=document.referrer;"/>
                    </div>
                </form:form>
            </div>
        </div>
    </body>
</html>
