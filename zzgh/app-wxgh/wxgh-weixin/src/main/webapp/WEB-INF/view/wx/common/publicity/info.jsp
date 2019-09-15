<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>宣传详情</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">


    <style>
        .mui-card-content img {
            width: 98%;
            margin-left: 1%;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            margin-top: 2px;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <%--<h1 class="mui-title" id="notTitle">宣传详情</h1>--%>
<%--</header>--%>

<div class="mui-content">
    <div class="mui-card">
        <div class="mui-card-header" id="notName">宣传标题</div>
        <div id="notLinkDiv" class="mui-card-content mui-hidden">
            <div class="mui-card-content-inner">
                <a href="">点击查看详情</a>
            </div>
        </div>
        <div id="notTxtImgDiv" class="mui-card-content mui-hidden">
            <ul id="notImgs" class="mui-table-view mui-grid-view"></ul>

            <div class="mui-card-content-inner">
                宣传内容
            </div>
        </div>
        <div class="mui-card-footer">
            <a id="notTime" class="mui-card-link ui-text-info">2016-08-22</a>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>

<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';
    var notId = '${param.id}';

    var page = {
        init: function () {
            if (!page.loading) {
                page.loading = new ui.loading("加载中...");
            }
            page.loading.show();

            page.request();
        },
        request: function () {
            var url = homePath + '/wx/common/publicity/info.json?id=' + notId;
            mui.getJSON(url, function (res) {
                if (res.ok) {
                    console.log(res.data)
                    page.setPage(res.data);
                    page.loading.hide();
                } else {
                    page.loading.hide();
                    mui.toast(res.msg);
                }
            });
        },
        setPage: function (info) {
            var e_title = wxgh.getElement("notTitle");
            var e_name = wxgh.getElement("notName");
            var e_time = wxgh.getElement("notTime");
            var e_linkDiv = wxgh.getElement("notLinkDiv");
            var e_txtimgDiv = wxgh.getElement("notTxtImgDiv");
            var e_imgs = wxgh.getElement("notImgs");

//            e_title.innerText = info.name;
            e_name.innerText = info.name;

            e_time.innerText = (info.time ? new Date(info.time).format("yyyy-MM-dd hh:mm") : "未知时间");

//            var content = JSON.parse(info.content);
            var type = info.type; //宣传类型
            if (type == "txtimg") {
                wxgh.hide(e_linkDiv);
                wxgh.show(e_txtimgDiv);

//                if (content.imgs && content.imgs.length > 0) {
//                    page.createImgItem(e_imgs, content.imgs);
//                } else {
//                    wxgh.hide(e_imgs);
//                }
                //e_txtimgDiv.querySelector(".mui-card-content-inner").html = info.content;
                $(".mui-card-content-inner").html(info.content)
                wxgh.previewImageInit();
            } else if (type == "url") {
                wxgh.hide(e_txtimgDiv);
                wxgh.show(e_linkDiv);
                e_linkDiv.querySelector("a").setAttribute("href", content.url);
            }
        },
        createImgItem: function (parent, imgs) {
            if (imgs && imgs.length > 0) {
                for (var i = 0; i < imgs.length; i++) {
                    var e_li = document.createElement("li");
                    e_li.className = "mui-table-view-cell mui-media mui-col-xs-6";
                    var e_a = document.createElement("a");
                    e_a.href = "#";

                    var e_img = document.createElement("img");
                    e_img.className = "mui-media-object";
                    e_img.src = imgs[i].url;
                    e_img.setAttribute("data-preview-src", "");
                    e_img.setAttribute("data-preview-group", "1");

                    var e_div = document.createElement("div");
                    e_div.className = "mui-media-body";
                    e_div.innerHTML = "<span>" + imgs[i].info + "</span>";

                    e_a.appendChild(e_img);
                    e_a.appendChild(e_div);
                    e_li.appendChild(e_a);

                    parent.appendChild(e_li);
                }
            }
        }
    };

    window.onload = page.init();

</script>
</body>

</html>
