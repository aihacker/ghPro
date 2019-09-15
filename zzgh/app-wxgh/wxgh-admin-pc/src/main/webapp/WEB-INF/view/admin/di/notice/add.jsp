<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/weixin/style/party/notice/index.css"/>
<div class="row">
    <div class="col-lg-7 col-md-7 ui-margin-top-20">
        <form id="mpnewsForm" style="padding-bottom:30px;">
            <div class="form-group">
                <label>标题</label>
                <small class="text-danger"></small>
                <input name="title" class="form-control" type="text" placeholder="请输入标题，标题不多于64个字"/>
            </div>
            <div class="form-group">
                <label>封面图片</label><br/>
                <input name="img" id="uploadNewsImg" accept="image/*" class="ui-inline-block" type="file"/>
                <%--<div id="coverImg"></div>--%>
                <label><input id="showPicBtn" type="checkbox" checked> 是否显示封面</label>
                <input type="hidden" name="show_cover_pic" value="1">

                <div class="ui-inline-block" style="margin-left: 10px;">推荐尺寸：900像素x500像素</div>
            </div>
            <div class="form-group">
                <label>发布对象：</label>
                <select class="form-control" name="groupId">
                    <option value="0">所有人</option>
                    <c:forEach items="${group}" var="g" varStatus="i">
                        <option value="${g.value}">${g.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>正文</label>
                <script id="noticeCnt" name="content" type="text/plain" style="height:300px;width:100%;"></script>
            </div>
            <div class="form-group">
                <label>摘要</label>
                <small>（选填，如果不填写会默认抓取正文前54个字）</small>
                <small class="text-danger"></small>
                <textarea name="digest" class="form-control" rows="3"></textarea>
            </div>
            <div class="form-group">
                <label>作者</label>
                <small>（选填，8个字符内）</small>
                <input name="author" type="text" placeholder="互联网工会" class="form-control"/>
            </div>
            <div class="form-group">
                <label>原文链接</label>
                <small>（选填）</small>
                <input name="content_source_url" type="text" class="form-control"/>
            </div>
        </form>
    </div>

    <div class="col-lg-5 col-md-5 ui-news-prev">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="ui-prev newsPrev">
                    <h4 id="prevTitle">标题</h4>

                    <div class="ui-img-div">
                        <img id="prevImag" src="${home}/image/di/notice/icon_preview.png"/>
                    </div>
                    <p id="prevInfo">摘要</p>
                </div>
                <div class="ui-prev textPrev hidden">
                    <img class="ui-msg-head" src="${home}/image/di/notice/icon_avatar.png"/>

                    <div id="msgBody" class="ui-msg-body">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="panel panel-default col-lg-7 col-md-7" id="fixedSendPanel">
        <div class="panel-body">
            <label><input id="safeMsgBtn" type="checkbox"/> 保密消息</label>
            <input type="hidden" name="safe" value="0">

            <div class="pull-right">
                <button id="sendBtn" data-loading-text="发送中..." autocomplete="off" type="button"
                        class="btn btn-primary">发送
                </button>
                <%--<button type="button" class="btn btn-primary">定时发送</button>--%>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<jsp:include page="/comm/admin/umeditor.jsp"/>
<script type="text/javascript" src="${home}/script/di/notice/index.js"></script>

<script>
    var currentType = "";
    $(function () {
        /*选择*/
        $("#ul-send a").click(function () {
            currentType = $(this).attr("data-type");
        });

//        var upload = ui.uploader('#coverImg')
//        upload.upload(function (fileIds) {
//
//        })

    })
</script>

