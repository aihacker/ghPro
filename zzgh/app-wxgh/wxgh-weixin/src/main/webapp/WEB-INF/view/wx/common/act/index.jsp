<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/28
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>活动申请</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--<h1 class="mui-title">活动申请</h1>--%>
    <%--<a id="addBtn" class="mui-btn mui-btn-link mui-pull-right">立即申请</a>--%>
<%--</header>--%>
<div  class="ui-fixed-bottom">
    <button id="addBtn" type="button" class="mui-btn mui-btn-primary">立即申请</button>
</div>

<div class="mui-content">
    <form id="applyForm">

        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>活动名称</label>
                <input name="actName" type="text" class="" placeholder="活动名称（20字以内）"/>
            </div>
            <div class="mui-input-row">
                <label>活动时间</label>
                <input name="actTime" id="timeInput" type="hidden"/>
                <span id="selectTime" class="input-row-span ui-text-info">请选择时间</span>
            </div>
            <div class="mui-input-row">
                <label>参加人数</label>
                <input name="joinNumb" type="number" class="mui-input-numbox" placeholder="预计参加人数"/>
            </div>
            <div class="mui-input-row">
                <label>金&nbsp;&nbsp;&nbsp;&nbsp;额</label>
                <input name="actCost" type="number" class="mui-input-numbox" placeholder="预算金额"/>
            </div>
            <div class="mui-input-row">
                <label>联系电话</label>
                <input name="mobile" type="number" value="${wxgh_user.mobile}" class="mui-input-numbox"
                       placeholder="联系人手机号码">
            </div>
            <div class="textare-div">
                <label>活动地点</label>
                <textarea name="address" rows="3" placeholder="活动进行的地址（100字以内）"></textarea>
            </div>
            <div class="textare-div">
                <label>活动内容</label>
                <textarea name="actInfo" rows="3" placeholder="活动的内容"></textarea>
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="textare-div">
                <label>申请原因</label>
                <textarea name="reason" rows="4" placeholder="活动申请原因"></textarea>
            </div>
        </div>
    </form>
</div>




<jsp:include page="/comm/mobile/scripts.jsp"/>
<link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>

<script type="text/javascript">
    mui.init()

    wxgh.wxInit('${weixin}');

    var homePath = '${home}';

    var e_addBtn = wxgh.getElement("addBtn");

    var form = {
        init: function () {
            var self = wxgh.getElement("applyForm");

            self.querySelector('input[name=actName]').addEventListener('input', function () {
                if (this.value.length > 20) {
                    this.value = this.value.substr(0, 20);
                    mui.toast('活动名称在20字符间哦');
                }
            })

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
                form.loading = new ui.loading("申请中，请稍候...");
            }

            wxgh.start_progress(e_addBtn);
            form.loading.show();

            var url = homePath + "/wx/common/act/add.json";
            mui.post(url, info, function (res) {
                form.loading.hide();
                wxgh.end_progress(e_addBtn);
                if (res.ok) {
                    wxgh.clearForm(form.self);
                    selectTime.self.innerText = "请选择时间"

                    wxgh.redirectTip(homePath, {
                        msg: '活动申请成功，请耐心等候管理员审核',
                        title: '申请成功',
//                        url: homePath + '/wx/pub/apply/index.html',
//                        urlMsg: '去个人中心查看申请进度'
                    })
                } else {
                    mui.toast(res.msg);
                }
            }, 'json');
        },
        //表单验证
        verify: function (info) {
            if (!info.actName) {
                return "活动名称不能为空哦"
            }
            if (info.actName.length > 20) {
                return "活动名称有点长哦"
            }
            if (!info.actTime) {
                return "请选择活动时间哦";
            }
            if (!info.joinNumb || info.joinNumb <= 0 || info.joinNumb.toString().indexOf(".") != -1) {
                return "预计参加人数输入不合法哦";
            }
            if (!info.actCost || info.actCost <= 0) {
                return "预算金额输入不合法哦";
            }
            if (!info.mobile) {
                return "联系人电话不能为空哦";
            }
            if (wxgh.checkPhoneNum(info.mobile) == false) {
                return "联系人电话输入不合法哦";
            }
            if (!info.address) {
                return "活动地址信息不能为空哦";
            }
            if (info.address.length > 100) {
                return "活动地址有点长哦";
            }
            if (!info.actInfo) {
                return "活动内容介绍不能为空哦";
            }
        },
        //弹出确认消息框，让用户选择是否留在本页
        showDialog: function () {
            var btns = ['取消', '关闭页面'];
            mui.confirm('是否留在当前页面？', '提示', btns, function (e) {
                if (e.index == 1) {
                    wx.closeWindow(); //关闭当前页面
                }
            });
        }
    };

    //时间选择
    var selectTime = {
        init: function () {
            var self = wxgh.getElement("selectTime");
            self.addEventListener("tap", selectTime.click);

            selectTime.input = wxgh.getElement("timeInput");
            selectTime.self = self;

            var nowDate = new Date();
            selectTime.picker = new mui.DtPicker({
                beginYear: nowDate.getFullYear(),
                endYear: nowDate.getFullYear() + 2,
                value: nowDate.format('yyyy-MM-dd hh:mm')
            });
        },
        click: function (e) {
            selectTime.picker.show(function (rs) {
                selectTime.input.value = rs.text;
                selectTime.self.innerText = rs.text;
            });
        }
    };

    form.init();
    selectTime.init();

    e_addBtn.addEventListener("tap", function () {
        form.submit();
    });
</script>
<script>
    $(function(){
        $("textarea[name=address]").on("input", function(){
            if ($(this).val().length > 100){
                $(this).val($(this).val().substr(0, 100));
                mui.toast("活动地址不能超过100个字符哦~")
            }
        });
    })
</script>
</body>

</html>
