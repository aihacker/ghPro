<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>识别PDF文件</title>
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
            if (fileType !== 'pdf') {
                alert('文件格式不是有效的pdf！');
                return false;
            }
            $('#upload').attr('disabled', 'disabled').text('识别中..');

            var params = $("#file_form").serializeArray();
            $("#file_form").ajaxSubmit({
                method: 'post',
                data:{params,files:fileName},
                url: 'query.json?action=daoExcel',
                async: false,
                success: function (result) {
                    if (result.ok) {
                        alert(result.msg);
                        location.reload();
                    }else {
                        alert(result.msg);
                        location.reload();
                    }

                },
                error: function (result) {
                    alert(result.msg);
                    return false;
                }
            });
        }

       /* function demo() {
            var file = document.querySelector('[type=file]');
            var formdata = new FormData();
            formdata.append('upload', file.files[0]);

            pub.getJSON('dao_excel.json', {
                file: JSON.stringify(formdata)
            }, function (data) {
                alert(data);
                grid.refresh();
            }, function (data) {
                alert("失败");
            });
        }*/


    </script>
</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">识别PDF文件</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div><br><br><br><br><br></div>
    <div align="center">
        <!--  注意action地址，还有enctype要写成multipart/form-data，和method="POST"  -->
        <form name="file_form" id="file_form" method="post"  onsubmit="return check();" enctype="multipart/form-data" id="file2">
            <%--<form:form modelAttribute="importForm">--%>
            <%--<input type="hidden" name="action" value="daoExcel"/>--%>
            <table border="2" width="450" cellpadding="4" cellspacing="2" bordercolor="#9BD7FF">
                <tr>
                    <td align="center" style="font-size: 20px">请选择要上传的pdf文件:</td>
                </tr>
                <tr>
                    <td><input name="file" id="file" size="60" type="file"></td>
                </tr>
            </table>
            <br>
            <table>
                <tr>
                    <td align="center"><input name="upload" id="upload" type="submit" value="开始上传" class="btn btn-success"/></td>
                </tr>
            </table>

        </form>

    </div>

</div>
</body>
</html>
