<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/19
  Time: 9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>会员提案发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .suggest-form label {
            color: gray;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">发布</h1>
    <button type="button" id="sendSuggest" class="mui-btn mui-btn-link mui-pull-right">立即发布</button>
</header>
<div class="mui-content">
    <div class="mui-content-padded">
        <form class="suggest-form" action="${home}/wx/common/suggest/save.json">
            <div>
                <label>选择类别</label>
                <select name="cateId">
                    <option value="0">- 选择 -</option>
                    <c:forEach items="${cates}" var="cate">
                       <%-- <c:if test="${cate.id ne 3}">--%>
                            <option value="${cate.id}">${cate.name}</option>
                       <%-- </c:if>--%>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label>标题</label>
                <input type="text" name="title" class="mui-input-clear" placeholder="取个名字吧，15个字符之内哦~">
            </div>
            <div>
                <label>内容</label>
                <textarea rows="4" name="content" placeholder="你想要说点什么呢..."></textarea>
            </div>

        </form>
    </div>

</div>

<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    var homePath = '${home}';
    var userId = '${wxgh_user.userid}';

    wxgh.wxInit('${weixin}')

    var e_sendBtn = document.getElementById("sendSuggest");
    var e_form = document.querySelector(".suggest-form");
    var e_cateId = document.querySelector(".suggest-form select[name=cateId]");
    var e_title = document.querySelector(".suggest-form input[name=title]");
    var e_content = document.querySelector(".suggest-form textarea[name=content]");

    var sendLoading = null;

    e_sendBtn.addEventListener("tap", function () {
        var cateId = e_cateId.value;
        var title = e_title.value;
        var content = e_content.value;

        if (cateId == 0) {
            mui.toast("喔噢，分类不见了");
            return;
        }

        var titleStr = title.trim()
        if (!titleStr) {
            mui.toast("你还没有告诉我标题呢？");
            return;
        }
        if (titleStr.length <= 2 && titleStr.length > 60) {
            mui.toast('标题长度在2~60字符间哦')
            return
        }

        var cntStr = content.trim()
        if (cntStr == "") {
            mui.toast("内容不见了哦");
            return;
        }
        if (cntStr.length <= 5 && cntStr.length > 200) {
            mui.toast('内容长度在5~200字符间哦')
            return
        }

        // mui('body').progressbar({progress: undefined}).show();
        if (sendLoading == null) {
            sendLoading = new ui.loading("发布中，请稍候...");
        }
        sendLoading.show();
        ghmui.start_progress(e_sendBtn);

        var url = e_form.getAttribute("action");
        mui.post(url, {
            cateId: cateId,
            title: title,
            content: content
        }, function (res) {
            //mui('body').progressbar().hide();
            ghmui.end_progress(e_sendBtn);
            sendLoading.hide();
            if (res.ok) {
                wxgh.clearForm(e_form)

                wxgh.redirectTip(homePath, {
                    msg: '请静候管理员审核成功才可以查看哦~',
                    url: homePath + "/wx/common/suggest/index.html",
                    title: '发布成功',
                    urlMsg: '返回首页'
                })
            } else {
                mui.toast(res.msg);
            }
        }, 'json');
    });

    $(function () {
        $("input[name=title]").keyup(function () {
            var val = $(this).val();
            if (val.length > 15) {
                $(this).val(val.substr(0, 15));
                mui.toast("请填写15个字符以内哦~");
            }
        });
    })

</script>
</body>

</html>
