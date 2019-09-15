<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/29
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>部门信息填写</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>

    <style>

        #deptDiv {
            padding: 10px;
        }

        #deptDiv label {
            display: block;
        }

        #deptDiv select {
            width: 48%;
            text-align: center;
            border: 1px solid whitesmoke !important;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--<h1 class="mui-title">部门信息填写</h1>--%>
    <%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">下一步</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">提交</button>
</div>

<div class="mui-content">
    <form id="applyForm">
        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>会员类别</label>
                <select name="category">
                    <option value="0">选择申请会员类别</option>
                    <option value="在职">在职</option>
                    <option value="离休">离休</option>
                    <option value="退休">退休</option>
                    <option value="内退">内退</option>
                    <option value="其他人员">其他人员</option>
                </select>
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <label>工作岗位</label>
                <input name="work" value="${userinfo.workUnit}" type="text" class="mui-input-clear"
                       placeholder="你在哪里上班呢？（40字以内）">
            </div>
            <div class="mui-input-row">
                <label>职&nbsp;&nbsp;&nbsp;&nbsp;位</label>
                <input name="position" value="${userinfo.job}" type="text" class="mui-input-clear"
                       placeholder="岗位 / 职位">
            </div>
            <div class="mui-input-row">
                <label>岗位级别</label>
                <input name="workLevel" type="text" class="mui-input-clear" placeholder="你的工作岗位级别">
            </div>
            <div class="mui-input-row">
                <label>入职时间</label>
                <input name="workTime" type="hidden" id="timeInput"/>
                <span id="selectTime" class="input-row-span ui-text-info">选择工作时间</span>
            </div>
            <div class="mui-input-row">
                <label>月薪 /元</label>
                <input name="monthly" type="number" class="mui-input-clear mui-input-numbox" placeholder="每月工资">
            </div>
            <div class="mui-input-row">
                <label>员工编号</label>
                <input name="employeeId" type="text" class="mui-input-clear" placeholder="你的员工编号"/>
            </div>
            <div id="deptDiv">
                <label>部门信息</label>

                <c:choose>
                    <c:when test="${empty companys}">
                        <br>
                        <input type="hidden" name="deptId">

                        <div class="dept-info ui-text-info">${dept}</div>
                    </c:when>
                    <c:otherwise>
                        <select id="companySelect">
                            <option value="0">请选择公司</option>
                            <c:forEach items="${companys}" var="comp">
                                <option value="${comp.deptid}">${comp.deptname}</option>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

    var e_nextBtn = wxgh.getElement("nextBtn");

    //时间选择
    var selectTime = {
        init: function () {
            var self = wxgh.getElement("selectTime");
            self.addEventListener("tap", selectTime.click);

            selectTime.input = wxgh.getElement("timeInput");
            selectTime.self = self;

            var nowDate = new Date();
            selectTime.picker = new mui.DtPicker({
                type: 'date',
                beginYear: nowDate.getFullYear() - 90,
                endYear: nowDate.getFullYear(),
                value: nowDate.format('yyyy-MM-dd')
            });
        },
        click: function (e) {
            selectTime.picker.show(function (rs) {
                selectTime.input.value = rs.text;
                selectTime.self.innerText = rs.text;
            });
        }
    };

    //部门信息选择
    var deptSelector = {
        init: function () {
            var self = wxgh.getElement("deptDiv");

            var e_company = self.querySelector("#companySelect"); //选择公司

            //chang事件监听
            e_company.addEventListener("change", this.change);

            this.self = self;
        },
        request: function (parentId) {
            this.requestLoading = new ui.loading('获取中，请稍候...')
            this.requestLoading.show()

            // 获取部门接口
//            var url = homePath + "/wxdept/list.json";
            var url = homePath + "/wx/common/fraternity/get.json";
            var info = {
//                action: "get",
                parentid: parentId
            };

            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    deptSelector.create_select(res.data, res.data.deptype);
                }
                deptSelector.requestLoading.hide()
            });
        },
        create_select: function (options, name) {
            if (options && options.length > 0) {

                var e_select = document.createElement("select");
                e_select.setAttribute("data-type", name);

                var default_option = document.createElement("option");
                default_option.value = 0;
                default_option.innerText = "请选择";

                e_select.appendChild(default_option);
                for (var i = 0; i < options.length; i++) {
                    var item = options[i];
                    var e_option = document.createElement("option");
                    e_option.value = item.deptid;
                    e_option.innerText = item.deptname;

                    e_select.appendChild(e_option);
                }

                e_select.addEventListener("change", deptSelector.change);

                deptSelector.self.appendChild(e_select);
            }

        },
        change: function (e) {

            //清空后面的select
            deptSelector.removeChild(this);

            var val = this.value;
            if (val != 0) {
                deptSelector.request(val);
            }
        },
        removeChild: function (self) {
            var next_e = self.nextElementSibling
            var nexts = []
            if (next_e) {
                do {
                    nexts.push(next_e)
                    next_e = next_e.nextElementSibling
                } while (next_e)
            }

            if (nexts) {
                for (var i = 0; i < nexts.length; i++) {
                    deptSelector.self.removeChild(nexts[i])
                }
            }
        },
        //获取当前用户选择的部门ID
        get_val: function () {
            var deptSelect = this.self.querySelector('select:last-child')
            return deptSelect.value
        }
    };

    var form = {
        init: function () {
            var self = wxgh.getElement("applyForm");

            form.self = self;
        },
        submit: function () {
            var info = wxgh.serialize(form.self);

            if (wxgh.getElement('companySelect')) {
                info['deptid'] = deptSelector.get_val();
            }

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

            var url = homePath + "/wx/common/fraternity/apply/add2.json";
            mui.post(url, info, function (res) {
                form.loading.hide();
                wxgh.end_progress(e_nextBtn);
                if (res.ok) {
                    mui.openWindow(homePath + "/wx/Common/fraternity/apply3.html");
                } else {
                    mui.toast(res.msg);
                }
            }, 'json');
        },
        verify: function (info) {
            if (info.category == 0) {
                return "会员类型还没有选择呢";
            }
            if (!info.work) {
                return "工作岗位不能为空哦";
            }
            if (info.work.length > 40) {
                return "工作岗位太长了哦";
            }
            if (!info.position) {
                return "你是什么职位的呢？";
            }
            if (!info.workLevel) {
                return "职位级别不能为空哦";
            }
            if (!info.workTime) {
                return "入职时间不能为空哦";
            }
            if (!info.monthly) {
                return "月薪不能为空哦";
            }
            if (!info.employeeId) {
                return "员工编号不能为空";
            }
            if (wxgh.getElement('companySelect')) {
                if (data.deptId == 0) {
                    return "请选择部门哦";
                }
            }
        }
    };

    selectTime.init();

    if (wxgh.getElement("companySelect")) {
        deptSelector.init();
    }

    form.init();

    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });
</script>
</body>

</html>
