<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>导入采购合同台账</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script type="text/javascript">
        function check(){
            var fileName= document.getElementById("file").value;
            if(fileName==""){
                alert("请选择文件");
                return false;
            }
            var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();
            if (fileType !== 'xls' && fileType !== 'xlsx') {
                alert('文件格式不是有效的xls或xlsx文件！');
                return false;
            }
            $('#upload').attr('disabled', 'disabled').text('识别中..');
        }

    </script>
</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">导入IC卡台帐对帐单</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div><br><br><br><br><br></div>
    <div align="center">
        <!--  注意action地址，还有enctype要写成multipart/form-data，和method="POST"  -->
        <form name="file_form" id="file_form" method="post"  onsubmit="return check();" enctype="multipart/form-data">
        <%--<form:form modelAttribute="importForm">--%>
            <input type="hidden" name="action" value="daoExcel"/>
            <table border="2" width="450" cellpadding="4" cellspacing="2" bordercolor="#9BD7FF">
                <tr>
                    <td align="center" style="font-size: 20px">请选择要上传的excel文件:</td>
                </tr>
                <tr>
                    <td><input name="file"  id="file" size="60" type="file"></td>
                </tr>
            </table>
            <br>
            <table>
                <tr>
                    <td align="center"><input name="upload" id="upload" type="submit" value="开始上传" class="btn btn-success" /></td>
                </tr>
            </table>
        </form>

    </div>

</div>
</body>
</html>
