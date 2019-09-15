<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/16
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .ui-img-div {
        position: relative;
        width: 100px;
        height: 100px;
    }

    .ui-img-div img {
        width: 100%;
    }

    .ui-img-div input[type=file] {
        opacity: 0;
        background-color: transparent;
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
    }

    #importForm {
        position: relative;
        left: 50px;
        top: 20px;
    }

    #importForm .ui-filename {
        position: absolute;
        left: 130px;
        top: 20px;
    }

    #importForm .ui-import-btn {
        position: absolute;
        left: 130px;
        top: 65px;
    }

    thead th {
        text-align: center;
    }

    #downExcel {
        position: absolute;
        right: 40px;
    }
</style>
<div class="row">
    <div class="rows">
        <form id="importForm">
            <div class="ui-img-div">
                <img src="${home}/weixin/image/party/icon_add1.png"/>
                <input type="file" id="excelFileInput" accept="application/vnd.ms-excel" name="fileimgs"/>
            </div>
            <div class="ui-filename hidden">
            </div>
            <div class="ui-import-btn">
                <button id="addBtn" type="button" class="btn btn-info disabled">立即导入</button>
            </div>
        </form>
        <a id="downExcel" href="${home}/party/admin/template/download.html" class="btn btn-danger">下载模版</a>
    </div>
    <div class="rows">
        <table class="table table-responsive" style="margin-top: 60px;">
            <thead>
            <tr>
                <th style="min-width: 90px;">党支部</th>
                <th style="min-width: 62px;">姓名</th>
                <th style="min-width: 48px;">性别</th>
                <th style="min-width: 48px;">民族</th>
                <th style="min-width: 78px;">籍贯</th>
                <th style="min-width: 94px;">出生日期</th>
                <th style="min-width: 48px;">学历</th>
                <th style="min-width: 160px">身份证</th>
                <th style="min-width: 78px;">党内职务</th>
                <th style="min-width: 94px;">工作时间</th>
                <th style="min-width: 94px;">入党时间</th>
                <th style="min-width: 94px;">转正时间</th>
                <th style="min-width: 84px;">正式/预备</th>
                <th style="min-width: 100px;">新调进党员</th>
                <th style="min-width: 95px;">户口所在地</th>
                <th style="min-width: 110px;">联系电话</th>
                <th style="min-width: 78px;">备注</th>
                <th style="min-width: 50px;">结果</th>
            </tr>
            </thead>
            <tbody id="infoBody">
            </tbody>
        </table>
    </div>
</div>
<script src="${home}/script/lib/ajaxfileupload.js"></script>
