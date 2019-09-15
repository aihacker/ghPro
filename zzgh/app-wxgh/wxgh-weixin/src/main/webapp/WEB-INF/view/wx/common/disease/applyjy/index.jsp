<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/2
  Time: 9:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>教育资助申请</title>


    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>

    <style>
        .selectTime {
            line-height: 40px;
        }

        .mui-input-row label {
            width: 50% !important;
        }

        .mui-table-view-cell select{
            opacity: 1;
        }
    </style>
    <style>
        .mui-input-row label ~ input, .mui-input-row label ~ select, .mui-input-row label ~ textarea {
            width: 50%;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <%--<h1 class="mui-title">教育资助申请</h1>--%>
    <%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">提交</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">提交</button>
</div>

<div class="mui-content">

    <div class="mui-content-padded mui-text-center ui-text-info">
        <small>PS：左滑可以删除多余项哦</small>
    </div>

    <div class="mui-card">
        <ul class="mui-table-view mui-table-view-chevron ul-card">
            <li class="mui-table-view-cell mui-collapse li-card">
                <a href="#" class="mui-slider-handle">
                    <a class="mui-navigate-right" href="#">请选择关系</a>
                    <ul class="mui-table-view mui-table-view-chevron">
                        <li class="mui-table-view-cell">
                            <div class="mui-input-group mui-table-view-chevron">
                                <div class="mui-input-row">
                                    <label>关&nbsp;&nbsp;&nbsp;&nbsp;系</label>
                                    <select name="relation">
                                        <option value="0">请选择关系</option>
                                        <option value="儿子">儿子</option>
                                        <option value="女儿">女儿</option>
                                    </select>
                                </div>
                                <div class="mui-input-row">
                                    <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                                    <input name="name" type="text" class="mui-input-clear" placeholder="子女姓名">
                                </div>
                                <div class="mui-input-row">
                                    <label>出生日期</label>
                                    <input name="birth" type="hidden"/>

                                    <p class="ui-text-info selectTime">出生日期</p>
                                </div>
                                <div class="mui-input-row">
                                    <label>就读学校</label>
                                    <input name="school" type="text" class="mui-input-clear" placeholder="就读学校">
                                </div>
                                <div class="mui-input-row">
                                    <label>班&nbsp;&nbsp;&nbsp;&nbsp;级</label>
                                    <input name="grade" type="text" class="mui-input-clear" placeholder="班级">
                                </div>
                                <div class="mui-input-row">
                                    <label>学制</label>
                                    <select name="xuezhi">
                                        <option value="0">选择学制</option>
                                        <option>两年</option>
                                        <option>三年</option>
                                        <option>四年</option>
                                        <option>五年</option>
                                        <option>六年</option>
                                    </select>
                                </div>
                                <div class="mui-input-row">
                                    <label>学年学费</label>
                                    <input name="tuition" type="number" class="mui-input-clear mui-input-numbox"
                                           placeholder="每年的学费">
                                </div>
                                <div class="textare-div">
                                    <label>申请原因：</label>
                                    <textarea name="reason" rows="3" placeholder="申请的原因"></textarea>
                                </div>
                            </div>
                        </li>
                    </ul>
                </a>

                <div class="mui-slider-right mui-disabled del">
                    <a class="mui-btn mui-btn-red">删除</a>
                </div>
            </li>
        </ul>
    </div>

    <div class="mui-text-center add-div">
        <button id="addOneBtn" type="button" class="mui-btn mui-icon mui-icon-plus"> 添加</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';
    var applyId = '${param.id}';

    var e_nextBtn = wxgh.getElement("nextBtn");

    //关系选择
    var RelationSelect = function (el, e_a) {
        var self = this;
        el.addEventListener("change", function () {
            self.change(this);
        });
        this.self = el;
        this.a = e_a;
    };
    RelationSelect.prototype = {
        change: function (self) {
            this.a.innerText = self.value;
        }
    };

    //添加关系
    var addBtn = {
        init: function () {
            var self = wxgh.getElement("addOneBtn");
            self.addEventListener("tap", addBtn.click);

            var e_cardItem = wxgh.query(".mui-card");

            addBtn.self = self;
            addBtn.item = e_cardItem;

            mui.each(wxgh.queryAll(".mui-card"), function () {
                new RelationSelect(this.querySelector("select[name=relation]"), this.querySelector(".mui-navigate-right"));
            });

            addBtn.contentDiv = wxgh.query(".mui-content");
        },
        click: function () {

            mui.each(addBtn.contentDiv.querySelectorAll('.mui-collapse'), function () {
                if (this.classList.contains('mui-active')) this.classList.remove('mui-active')
                mui.swipeoutClose(this);
            });

            var cloneNode = addBtn.item.cloneNode(true);
            wxgh.clearForm(cloneNode)

            var coleapseDiv = cloneNode.querySelector('.mui-collapse')
            coleapseDiv.classList.add('mui-active');

            cloneNode.querySelector(".selectTime").innerText = "出生日期";
            cloneNode.querySelector('.mui-navigate-right').innerText = '请选择关系'
            

            new RelationSelect(cloneNode.querySelector("select[name=relation]"), cloneNode.querySelector(".mui-navigate-right"));
            mui(cloneNode).on("tap", ".selectTime", selectTime.click);

            addBtn.contentDiv.insertBefore(cloneNode, addBtn.contentDiv.lastElementChild);
        }
    };


    var nowDate = new Date();
    var picker = new mui.DtPicker({
        type: 'date',
        beginYear: nowDate.getFullYear() - 90,
        endYear: nowDate.getFullYear(),
        value: nowDate.format('yyyy-MM-dd')
    });
    //时间选择
    var selectTime = {
        init: function () {
            mui(".mui-input-group").on("tap", ".selectTime", selectTime.click);
        },
        click: function (e) {
            var self = this;
            picker.show(function (rs) {
                self.previousElementSibling.value = rs.text;
                self.innerText = rs.text;
            });
        }
    };

    var form = {
        submit: function () {

            if (!form.loading) {
                form.loading = new ui.loading("提交中，请稍候...");
            }
            wxgh.start_progress(e_nextBtn);
            form.loading.show();

            var url = homePath + "/wx/common/disease/applyjy/add.json";
            mui.post(url, form.getInfo(), function (res) {
                form.loading.hide();
                wxgh.end_progress(e_nextBtn);
                if (res.ok) {
                    var parE = wxgh.query('.mui-content')
                    mui.each(wxgh.queryAll('.mui-card'), function () {
                        parE.removeChild(this)
                    })
                    parE.appendChild(addBtn.item)
                    wxgh.clearForm(addBtn.item)

                    wxgh.redirectTip(homePath, {
                        msg: '申请提交完成，请耐心等候管理员审核通过',
                        title: '提交成功'
                    })
                } else {
                    mui.toast(res.msg);
                }
            }, 'json');
        },
        getInfo: function () {
            var info = [];
            mui.each(wxgh.queryAll(".mui-card"), function () {
                info.push(wxgh.serialize(this));
            });

            var verify_res = form.verify(info);
            if (verify_res) {
                mui.toast(verify_res);
                return;
            }

            var param = {};
            for (var i = 0; i <= info.length; i++) {
                var item = info[i];
                for (var key in item) {
                    param['diseaseJies[' + i + '].' + key] = item[key];
                }
            }
            param['applyId'] = applyId;
            return param;
        },
        verify: function (info) {
            if (info.length <= 0) {
                return "子女信息不能为空哦";
            }
            for (var i = 0; i < info.length; i++) {
                var item = info[i];
                if (item.relation == 0) {
                    return "请选择关系哦";
                }
                if (!item.name) {
                    return "姓名不能为空哦";
                }
                if (!item.birth) {
                    return "出生日期不能为空哦";
                }
                if (!item.school) {
                    return "就读学校不能为空哦";
                }
                if (!item.grade) {
                    return "就读班级不能为空哦";
                }
                if (item.xuezhi == 0) {
                    return "请选择学制哦";
                }
                if (!item.tuition) {
                    return "学年费用不能为空哦"
                }
                if (!item.reason) {
                    return "申请理由不能为空哦"
                }
            }
        }
    };

    addBtn.init();
    selectTime.init();

    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });

</script>
<script>
    $(function () {
        $("body").on("tap", "div.del", function () {
            var $this = $(this);
            var $li = $("ul.ul-card li.li-card");
            if ($li.length <= 1) {
                mui.toast("最后一条不能删除哦~");
                return;
            } else {
                $this.parent("li").remove();
                return;
            }
        });

        /*申请诱因*/
        $("textarea[name=reason]").keyup(function(){
            var val = $(this).val();
            if (val.length > 200){
                val = val.substr(0, 200);
                $(this).val(val);
                mui.toast("申请原因在200个字符以内哦~");
            }
        });
        $("textarea[name=reason]").blur(function(){
            var val = $(this).val();
            if (val.length > 200){
                val = val.substr(0, 200);
                $(this).val(val);
                mui.toast("申请原因在200个字符以内哦~");
            }
        });

        /*姓名*/
        $("input[name=name]").keyup(function(){
            var val = $(this).val();
            if (val.length > 10){
                val = val.substr(0, 10);
                $(this).val(val);
                mui.toast("姓名在10个字符以内哦~");
            }
        });
        $("input[name=name]").blur(function(){
            var val = $(this).val();
            if (val.length > 10){
                val = val.substr(0, 10);
                $(this).val(val);
                mui.toast("姓名在10个字符以内哦~");
            }
        });

        /*就读学校*/
        $("input[name=school]").keyup(function(){
            var val = $(this).val();
            if (val.length > 50){
                val = val.substr(0, 50);
                $(this).val(val);
                mui.toast("就读学校在50个字符以内哦~");
            }
        });
        $("input[name=school]").blur(function(){
            var val = $(this).val();
            if (val.length > 50){
                val = val.substr(0, 50);
                $(this).val(val);
                mui.toast("就读学校在50个字符以内哦~");
            }
        });

        /*年级*/
        $("input[name=grade]").keyup(function(){
            var val = $(this).val();
            if (val.length > 10){
                val = val.substr(0, 10);
                $(this).val(val);
                mui.toast("年级在30个字符以内哦~");
            }
        });
        $("input[name=grade]").blur(function(){
            var val = $(this).val();
            if (val.length > 10){
                val = val.substr(0, 10);
                $(this).val(val);
                mui.toast("年级在50个字符以内哦~");
            }
        });
    });
</script>
</body>

</html>
