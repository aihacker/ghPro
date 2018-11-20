<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>配置管理</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <link rel="stylesheet" href="${home}/style/tree/metroStyle/metroStyle.css" type="text/css">
    <link rel="stylesheet" href="${home}/style/tree//zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript">
        ${jsValidator}

        /*  function onFormSubmit() {
              if (!window.confirm('确定保存吗?')) {
                  return false;
              }
          }*/
        function edit() {
            var $body = $(document.body);
            $body.removeClass('view');
        }
    </script>

</head>
<body class="${param.edit == 1 or userData.userId == null? 'edit': 'view'}">
<div class="">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="javascript:location.href=document.referrer;">返回</a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="main clearfix">
        <form:form modelAttribute="userData" action="operate.json?action=save" method="post" id="baseInfo" enctype="multipart/form-data" >
        <%--<input type="hidden" name="action" value="saveAndToJOB"/>--%>
        <form:hidden path="userId" cssClass="form-control"/>
        <div class="clearfix mt20 form-horizontal">
            <div class="col-xs-6 mt20">
                <div class="form-group">
                    <label class="col-sm-3 control-label">旧密码：</label>

                    <div class="col-sm-9">
                        <form:password path="oldPass" cssClass="form-control" id="old"/>
                    </div>
                </div>
            </div>
            <div class="col-xs-6 mt20">
                <div class="form-group">
                    <label class="col-sm-3 control-label">新密码：</label>

                    <div class="col-sm-9">
                        <form:password path="newPass" cssClass="form-control" id="new"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-3 clear-float center-block mt20 clearfix">
        <input type="button" value="保存" class="btn btn-danger pull-left" id="save"/>
        <input type="button" value="返回" class="btn btn-primary pull-right" onclick="location.href=document.referrer;"/>
    </div>
    </form:form>
</div>
</div><%--
<script src="${home}/script/system/user.js"></script>--%>
<script>
    //drawTree(${userData.tree});
    //onCheck();
/*    $('#saveAndToJOB').click(function () {
     var  flag = window.confirm('是否保存？')
     if(!flag){return}
     $('#baseInfo').submit();
     });*/


    $('#saveAndToJOB').click(function () {
        if(!window.confirm('确定保存吗?')){return ;}
            var old = $('#old').val();
            var news = $('#new').val();
            var userId =${userData.userId};
            var params = {userId:userId , oldPass:old , newPass :news};
            $.ajax({
                url:'operate.json?action=saveAndToJOB',
                data:params,
                success:function(res){
                    if(res.ok){
                        alert("保存成功!");
                        window.location.href="login.html";
                    }else{
                        alert("保存失败!");
                    }
                }
            });
    });
</script>
</body>
</html>
