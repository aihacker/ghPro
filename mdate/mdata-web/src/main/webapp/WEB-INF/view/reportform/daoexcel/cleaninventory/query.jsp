<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>导入即时清结数据</title>

    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>

    <script type="text/javascript">
        function check() {
            var fileName = $('#file').val();
            if (fileName == "") {
                alert("请选择上传的文件!");
                return false;
            }
            var fileType = (fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length)).toLowerCase();
            if (fileType !== 'xls' && fileType !== 'xlsx') {
                alert('文件格式不是有效的xls或xlsx文件！');
                return false;
            }
            $('#upload').attr('disabled', 'disabled').text('识别中..');

            var params = $("#file_form").serializeArray();
            $("#file_form").ajaxSubmit({
                method: 'post',
                data:params,
                url: 'operate.json?action=daoExcel',
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


    </script>
</head>

<body>
<div class="main">


    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">导入即时清洁数据</span></b></li>
                </ol>
            </div>
        </div>
    </div>

    <div><br><br><br><br><br></div>

    <div style="width: 100%;display: flex;border: 0px solid #000000">
        <div style="height:400px;border: 0px solid #85ff37;width: 300px" >
        </div>
        <div id="line" style="height:125px;border: 2px solid #010BFF" >
            <div align="center" >
                <!--  注意action地址，还有enctype要写成multipart/form-data，和method="POST"  -->
                <form name="file_form" id="file_form" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="daoExcel"/>
                    <table align="center" border="1" width="450" cellpadding="4" cellspacing="2" bordercolor="#010BFF">
                        <tr height="40"><td align="center" colspan="2" style="font-size: 20px;">请选择要上传的Excel文件:</td></tr>
                        <tr height="40"><td align="center"><input name="file" id="file" size="60" type="file"></td></tr>
                        <tr height="40"><td align="center">
                        <input name="upload" id="upload" type="button" onclick="check();" value="开始上传" class="btn btn-success"/></td></tr>
                    </table>
                </form>
            </div>
        </div>

        <div style="height:400px;border: 0px solid #85ff37;width: 100px" >
        </div>

        <div style="height:400px;border: 0px solid #85ff37;width:20px" >
        </div>
    </div>



</div>
</body>
</html>
