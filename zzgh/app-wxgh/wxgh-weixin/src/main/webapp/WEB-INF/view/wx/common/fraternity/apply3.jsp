<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/29
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>家庭主要成员填写</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

    <style>
        .del-btn-div {
            display: block;
            height: 30px;
        }

        .del-btn-div .del-btn {
            position: absolute;
            right: 8px;
            top: 4px;
        }

        .mui-table-view-cell.mui-collapse .mui-table-view .mui-table-view-cell {
            padding-left: 4px;
            padding-right: 15px;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<h1 class="mui-title">家庭主要成员填写</h1>--%>
    <%--<a id="submitBtn" class="mui-btn mui-btn-link mui-pull-right">提交</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">提交</button>
</div>

<div class="mui-content">

    <c:choose>
        <c:when test="${empty families}">
            <div class="mui-card">
                <div class="del-btn-div mui-hidden">
                    <a class="del-btn"><i class="mui-icon mui-icon-close"></i></a>
                </div>
                <ul class="mui-table-view mui-table-view-chevron">
                    <li class="mui-table-view-cell mui-collapse mui-active">
                        <a class="mui-navigate-right" href="#">请选择关系</a>
                        <ul class="mui-table-view mui-table-view-chevron">
                            <li class="mui-table-view-cell">
                                <form class="applyForm">
                                    <div class="mui-input-group mui-table-view-chevron">
                                        <div class="mui-input-row">
                                            <label>关&nbsp;&nbsp;&nbsp;&nbsp;系</label>
                                            <select name="relation">
                                                <option value="0">请选择关系</option>
                                                <option value="爸爸">爸爸</option>
                                                <option value="妈妈">妈妈</option>
                                                <option value="姐姐">姐姐</option>
                                                <option value="哥哥">哥哥</option>
                                                <option value="弟弟">弟弟</option>
                                                <option value="妹妹">妹妹</option>
                                            </select>
                                        </div>
                                        <div class="mui-input-row">
                                            <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                                            <input name="name" type="text" class="mui-input-clear" placeholder="成员姓名">
                                        </div>
                                        <div class="mui-input-row">
                                            <label>工作单位</label>
                                            <input name="workUnit" type="text" class="mui-input-clear"
                                                   placeholder="工作的单位（40字以内）">
                                        </div>
                                        <div class="mui-input-row">
                                            <label>职&nbsp;&nbsp;&nbsp;&nbsp;务</label>
                                            <input name="position" type="text" class="mui-input-clear"
                                                   placeholder="工作的职务">
                                        </div>
                                        <div class="mui-input-row">
                                            <label>月收入</label>
                                            <input name="monthly" type="number" class="mui-input-clear mui-input-numbox"
                                                   placeholder="该成员的月收入(元)">
                                        </div>
                                        <div class="textare-div">
                                            <label>备注：</label>
                                            <textarea name="remark" rows="3" placeholder="你还想要说点什么呢？"></textarea>
                                        </div>
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${families}" var="f">
                <div class="mui-card">
                    <div class="del-btn-div mui-hidden">
                        <a class="del-btn"><i class="mui-icon mui-icon-close"></i></a>
                    </div>
                    <ul class="mui-table-view mui-table-view-chevron">
                        <li class="mui-table-view-cell mui-collapse mui-active">
                            <a class="mui-navigate-right" href="#">${f.relation}</a>
                            <ul class="mui-table-view mui-table-view-chevron">
                                <li class="mui-table-view-cell">
                                    <form class="applyForm">
                                        <div class="mui-input-group mui-table-view-chevron">
                                            <div class="mui-input-row">
                                                <label>关&nbsp;系</label>
                                                <select name="relation">
                                                    <option value="0">请选择关系</option>
                                                    <c:choose>
                                                        <c:when test="${f.relation eq '爸爸'}">
                                                            <option value="爸爸" selected>爸爸</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="爸爸">爸爸</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${f.relation eq '妈妈'}">
                                                            <option value="妈妈" selected>妈妈</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="妈妈">妈妈</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${f.relation eq '姐姐'}">
                                                            <option value="姐姐" selected>姐姐</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="姐姐">姐姐</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${f.relation eq '哥哥'}">
                                                            <option value="哥哥" selected>哥哥</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="哥哥">哥哥</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${f.relation eq '弟弟'}">
                                                            <option value="弟弟" selected>弟弟</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="弟弟">弟弟</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${f.relation eq '妹妹'}">
                                                            <option value="妹妹" selected>妹妹</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="妹妹">妹妹</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </div>
                                            <div class="mui-input-row">
                                                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                                                <input name="name" value="${f.name}" type="text" class="mui-input-clear"
                                                       placeholder="成员姓名">
                                            </div>
                                            <div class="mui-input-row">
                                                <label>工作单位</label>
                                                <input name="workUnit" value="${f.workUnit}" type="text"
                                                       class="mui-input-clear"
                                                       placeholder="工作的单位（40字以内）">
                                            </div>
                                            <div class="mui-input-row">
                                                <label>职&nbsp;&nbsp;&nbsp;&nbsp;务</label>
                                                <input name="position" value="${f.position}" type="text"
                                                       class="mui-input-clear"
                                                       placeholder="工作的职务">
                                            </div>
                                            <div class="mui-input-row">
                                                <label>月收入</label>
                                                <input name="monthly" value="${f.monthly}" type="number"
                                                       class="mui-input-clear mui-input-numbox"
                                                       placeholder="该成员的月收入(元)">
                                            </div>
                                            <div class="textare-div">
                                                <label>备注：</label>
                                                <textarea name="remark" rows="3"
                                                          placeholder="你还想要说点什么呢？">${f.remark}</textarea>
                                            </div>
                                        </div>
                                    </form>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>


    <div class="mui-text-center add-div">
        <button id="addOneBtn" type="button" class="mui-btn mui-icon mui-icon-plus"> 添加</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

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

            addBtn.contentDiv = wxgh.query(".mui-content");

            addBtn.initCloseBtn(addBtn.contentDiv.querySelector('.del-btn-div').querySelector('.del-btn'));
        },
        click: function () {

            wxgh.show(addBtn.contentDiv.querySelector('.del-btn-div'));

            var cloneItem = addBtn.item.cloneNode(true);

            mui.each(addBtn.contentDiv.querySelectorAll('.mui-collapse'), function () {
                if (this.classList.contains('mui-active')) this.classList.remove('mui-active')
            });

            cloneItem.querySelector('.mui-collapse').classList.add('mui-active');

            new RelationSelect(cloneItem.querySelector("select[name=relation]"), cloneItem.querySelector(".mui-navigate-right"));
            addBtn.contentDiv.insertBefore(cloneItem, addBtn.contentDiv.lastElementChild);
            addBtn.initCloseBtn(cloneItem.querySelector('.del-btn'));
        },
        initCloseBtn: function (btn) {
            btn.addEventListener("tap", function (e) {
                addBtn.contentDiv.removeChild(this.parentNode.parentNode);

                var cards = addBtn.contentDiv.querySelectorAll('.mui-card');
                if (cards.length < 2) {
                    wxgh.hide(addBtn.contentDiv.querySelector('.del-btn-div'));
                }
            });
        }
    };

    //提交按钮
    var submitBtn = {
        init: function () {
            var self = wxgh.getElement("submitBtn");
            self.addEventListener("tap", submitBtn.click);

            submitBtn.self = self;
        },
        click: function () {
            var info = [];

            mui.each(wxgh.queryAll(".mui-card"), function () {
                info.push(wxgh.serialize(this.querySelector(".applyForm")));
            });

            var verify_res = submitBtn.verify(info);
            if (verify_res) {
                mui.toast(verify_res);
                return;
            }

            if (!submitBtn.loading) {
                submitBtn.loading = new ui.loading("提交中，请稍候...");
            }
            wxgh.start_progress(submitBtn.self);
            submitBtn.loading.show();

            var url = homePath + "/wx/common/fraternity/apply/add3.json";

            var param = {};
            for (var i = 0; i <= info.length; i++) {
                var item = info[i];
                for (var key in item) {
                    param['familys[' + i + '].' + key] = item[key];
                }
            }

            mui.post(url, param, function (res) {
                submitBtn.loading.hide();
                wxgh.end_progress(submitBtn.self);
                if (res.ok) {
                    //mui.toast("申请已经提交，请等待验证");
                    mui.openWindow(homePath + "/wx/common/fraternity/status.html")
                } else {
                    mui.toast(res.msg);
                }
            }, 'json');
        },
        verify: function (info) {
            if (info.length <= 0) {
                return "家庭成员信息不能为空哦";
            }
            for (var i = 0; i < info.length; i++) {
                var item = info[i];
                if (item.relation == 0) {
                    return "请选择关系";
                }
                if (!item.name) {
                    return "姓名不能为空哦";
                }
                if (!item.workUnit) {
                    return "工作单位不能为空哦";
                }
                if (item.workUnit.length > 40) {
                    return "工作单位有点长哦";
                }
                if (!item.position) {
                    return "工作职务不能为空哦";
                }
                if (!item.monthly) {
                    return "月薪不能为空哦";
                }
            }
        }
    };

    addBtn.init();
    submitBtn.init();

    mui.each(wxgh.queryAll(".mui-card"), function () {
        new RelationSelect(this.querySelector("select[name=relation]"), this.querySelector(".mui-navigate-right"));
    });

</script>
</body>

</html>

