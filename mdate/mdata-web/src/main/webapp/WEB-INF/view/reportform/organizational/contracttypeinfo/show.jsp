<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>合同小类型新增</title>

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
    <body class="${param.edit == 1 or contractTypeInfo.id == null? 'edit': 'view'}">
        <div class="">
            <div class="">
                <div class="three-nav col-xs-12">
                    <div class="row">
                        <ol class="breadcrumb">
                            <li><a href="javascript:location.href=document.referrer;">返回</a></li>
                            <li><a href="javascript:location.href=document.referrer;">小类型新增</a></li>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="main clearfix">
                <form:form modelAttribute="contractTypeInfo" action="operate.json" method="post">
                    <input type="hidden" name="action" value="save"/>
                    <form:hidden path="id" cssClass="form-control"  />
                    <div class="clearfix mt20 form-horizontal">

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">合同小类名称：</label>
                                <div class="col-sm-3">
                                    <form:input path="typeName" cssClass="form-control h30"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">合同小类型编码：</label>
                                <div class="col-sm-3">
                                    <form:input path="typeCode" cssClass="form-control h30" value=""/>&nbsp;&nbsp;&nbsp;(请参照前面的类型编码递增)
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-12 mt10">
                            <div class="form-group">
                                <label  class="col-sm-3 control-label mg20">所属父类编码：</label>
                                <div class="col-sm-3">
                                    <form:input path="parentNum" cssClass="form-control h30" value=""/>
                                </div>
                            </div>
                        </div>

                    </div>
                    </div>
                    <div class="col-xs-6 clear-float center-block clearfix">
                        <input type="submit" value="保存" class="btn btn-danger"/>
                        <input type="button" value="返回" class="btn btn-primary" onclick="location.href=document.referrer;"/>
                    </div>
                </form:form>
            </div>
        </div>
        <script src="${home}/script/test.js"></script>
        <script>
            showCheck("ss");
        </script>
    </body>
</html>
