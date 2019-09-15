<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/1
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>基本信息填写</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--<h1 class="mui-title">基本信息填写</h1>--%>
    <%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">下一步</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">下一步</button>
</div>

<div class="mui-content">
    <form id="applyForm">

        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                <input name="name" type="text" value="${wxgh_user.name}" class="mui-input-clear" placeholder="联系人姓名"/>
            </div>
            <div class="mui-input-row">
                <label>性&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                <span class="input-row-span ui-text-info">${wxgh_user.gender eq 1?"男":"女"}</span>
            </div>
            <div class="mui-input-row">
                <label>联系电话</label>
                <input name="mobile" value="${wxgh_user.mobile}" type="number" class="mui-input-clear mui-input-numbox"
                       placeholder="联系人电话">
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <label>资助类别</label>
                <select name="cateId">
                    <option value="0">请选择</option>
                    <option value="jb">疾病资助</option>
                    <option value="jy">教育资助</option>
                    <option value="zc">因公致残资助</option>
                    <option value="qs">去世资助</option>
                    <option value="zh">自然灾害资助</option>
                    <option value="zx">直系亲属去世慰问</option>
                    <option value="pk">困难家庭资助</option>
                </select>
            </div>
            <div class="mui-input-row">
                <label>申请金额</label>
                <input name="applyMoney" type="number" class="mui-input-clear mui-input-numbox" placeholder="申请资助金额">
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="textare-div">
                <label>备注</label>
                <textarea name="remark" rows="5" placeholder="备注点什么呢？"></textarea>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

    var e_nextBtn = wxgh.getElement("nextBtn");
    var form = {
        init: function () {
            var self = wxgh.getElement("applyForm");
            form.self = self;
        },
        submit: function () {
            var info = wxgh.serialize(form.self);
            var verify_res = form.verify(info);
            if (verify_res) {
                mui.toast(verify_res);
                return;
            }
            if (!form.loading) {
                form.loading = new ui.loading("提交中，请稍候...");
            }

            wxgh.start_progress(e_nextBtn);
            form.loading.show();

            form.request(info);
        },
        request: function (info) {
            var url = homePath + "/wx/common/disease/add.json";
            mui.post(url, info, function (res) {
                form.loading.hide();
                wxgh.end_progress(e_nextBtn);
                if (res.ok) {
                    wxgh.clearForm(form.self);
                    if (res.data) {
                        mui.openWindow(res.data);
                    }
                } else {
                    mui.toast(res.msg);
                }
            });
        },
        verify: function (info) {
            if (!info.name) {
                return "姓名不能为空哦";
            }
            if (!info.mobile) {
                return "联系人号码不能为空哦";
            }
            if (info.cateId == 0) {
                return "请选择申请资助金类别";
            }
            if (!info.applyMoney) {
                return "申请资助金额不能为空哦";
            }
        }
    };

    form.init();
    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });

</script>
<script>
    $(function(){
        $("input[name=mobile]").keyup(function () {
            var val = $(this).val();
            if (val.length > 11) {
                $(this).val(val.substr(0, 11));
                mui.toast("请填写11个数字哦~");
            }
        });
    });
</script>
</body>

</html>
