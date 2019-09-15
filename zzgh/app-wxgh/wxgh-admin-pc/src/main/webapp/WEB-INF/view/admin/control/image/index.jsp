<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/28
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    #fileInfo p {
        word-break: break-all;
    }
</style>
<div class="row">
    <form id="urlForm" class="col-lg-4 col-md-4 col-lg-offset-4 col-md-offset-4">
        <div class="form-group">
            <label>地址链接</label>
            <textarea class="form-control" name="url" rows="4" placeholder="请输入链接地址"></textarea>
        </div>
        <button data-loading-text="获取中..." id="subBtn" type="submit" class="btn btn-primary">获取文件信息</button>
    </form>
</div>
<div class="row">
    <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3">
        <h5>文件信息：</h5>
        <div class="panel panel-default">
            <div class="panel-body" id="fileInfo">
            </div>
        </div>
    </div>
</div>

<script>
    $(function () {
        var $fileInfo = $('#fileInfo');
        var $subBtn = $('#subBtn');
        $('#urlForm').submit(function () {
            var $self = $(this);
            var url = $self.find('textarea[name=url]').val();
            if (!url) {
                ui.alert('请输入地址链接！');
                return;
            }
            $subBtn.button('loading');
            ui.post('control/image/api/get.json', {url: url}, function (f) {
                $fileInfo.parent().show();
                $fileInfo.empty();
                if (f.fileId) {
                    $fileInfo.append('<p>文件名称：' + f.filename + '</p>');
                    $fileInfo.append('<p>文件大小：' + f.size + ' kb</p>');
                    $fileInfo.append('<p>Content-Type：' + f.mimeType + '</p>');
                    $fileInfo.append('<p>文件（ID）：' + f.fileId + '</p>');
                    $fileInfo.append('<p>保存路径：' + f.filePath + '</p>');
                    $fileInfo.append('<p>略缩图路径：' + f.thumbPath + '</p>');
                } else {
                    $fileInfo.append('<p>文件：' + (f ? f.toString() : '上传失败') + '</p>')
                }
                $subBtn.button('reset');
            })
            return false;
        })
    })
</script>
