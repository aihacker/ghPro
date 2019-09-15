<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/9
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/app.css" rel="stylesheet"/>
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }

        #hideBtn, #hideBtn2 {
            display: none;
            position: absolute;
            right: 20px;
            top: 12px;
            color: #007aff;
        }
    </style>
</head>

<body>


<div class="mui-scroll-wrapper ui-scroll-wrapper" style="padding-top: 0px;">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">活动名称</label>
                </a>

                <div id="name" style="word-break: break-all;">${act.actName}</div>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">活动举办时间</label>
                    <span><fmt:formatDate value="${act.actTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate> </span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${act.applyTime}"
                                          pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请人</label>
                    <span>${act.userName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">所属部门</label>
                    <span>${adminApply.deptName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">联系电话</label>
                    <span>${act.mobile}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">活动费用</label>
                    <span>${act.actCost}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">预计参加人数</label>
                    <span>${act.joinNumb}</span>
                </a>
            </li>
            <li class="mui-table-view-cell" style="position: relative;">
                <label class="ui-li-label ui-text-info">活动介绍：</label>

                <div id="remark" style="height: auto;word-break: break-all;">${act.actInfo}</div>
                <small id="hideBtn" style="">展开全文</small>
            </li>
            <li class="mui-table-view-cell" style="height: auto;position: relative;">
                <label class="ui-li-label ui-text-info">申请原因：</label>

                <div id="remark2" style="height: auto;word-break: break-all;">${act.reason}</div>
                <small id="hideBtn2" style="">展开全文</small>
            </li>
        </ul>


        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell mui-text-center">
                管理员操作
            </li>
            <li class="mui-table-view-cell mui-text-center mui-input-row">
                <label>审批金额</label>
                <input id="money" type="text" placeholder="请输入数字金额">
            </li>
            <li class="mui-table-view-cell mui-text-center">
                <label>审批说明：</label>
                <br>
                <textarea id="idea" placeholder="请输入说明"></textarea>
            </li>
            <li class="mui-table-view-cell">
                <p class="mui-button-row mui-text-center">
                    <button class="mui-btn mui-btn-green mui-left mui-pull-left shenhe" value="1" itemId="${act.id}">
                        通过
                    </button>

                    <button class="mui-btn mui-btn-danger mui-right mui-pull-right shenhe" value="2" itemId="${act.id}">
                        不通过
                    </button>
                </p>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script type="text/javascript">
    mui.init()
    mui('.ui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
</script>
<script>
    var homePath = '${home}';
    $(function () {
        if ('${thisStatus}' * 1 != 0) {
            mui.alert("该数据已不存在或被其他管理员审核", function () {
                mui.openWindow(homePath + '/wx/admin/common/act/index.html');
            });
        }
        hideRemarkEvent();
        hideRemarkEvent2();
        $(document).ready(function () {
            $("#a-back", parent.document).show();
        });
        $("button.shenhe").click(function () {
            var id = $(this).attr("itemId");
            var status = $(this).attr("value");
            var money = $("#money").val();
            var idea = $("#idea").val();

            if (status == 2) {

            } else {
                if (!status || !money || !idea || isNaN(money)) {
                    mui.alert("请把信息填写完整", "提示", "确定", function () {

                    });
                    return;
                }
            }


            $.ajax({
                dataType: "json",
                url: '${home}/wx/admin/common/act/shenhe.json',
                data: {
//                    action: 'shenhe',
                    id: id,
                    status: status,
                    auditIdea: idea,
                    auditCost: money
                },
                success: function (result) {
                    dealData(result);
                }
            });

            function dealData(result) {
                if ("success" == result.msg) {
                    mui.toast("审核成功");
                    window.location.href = "${home}/wx/admin/common/act/index.html";
                } else {
                    mui.toast("审核失败");
                }
            }
        });
    });

    function hideRemarkEvent2() {
        var $remark = $('#remark2');
        var $hide = $('#hideBtn2');

        var height = $remark.height();
        if (height > 200) {
            $remark.css('height', '180px').css('overflow', 'hidden');
            $hide.show();
        }

        $hide.click(function () {
            if ($(this).text() == '展开全文') {
                $remark.css('height', 'auto');
                $(this).text('收起文章');
            } else {
                $remark.css('height', '180px').css('overflow', 'hidden');
                $(this).text('展开全文');
            }
        });
    }
    function hideRemarkEvent() {
        var $remark = $('#remark');
        var $hide = $('#hideBtn');

        var height = $remark.height();
        if (height > 200) {
            $remark.css('height', '180px').css('overflow', 'hidden');
            $hide.show();
        }

        $hide.click(function () {
            if ($(this).text() == '展开全文') {
                $remark.css('height', 'auto');
                $(this).text('收起文章');
            } else {
                $remark.css('height', '180px').css('overflow', 'hidden');
                $(this).text('展开全文');
            }
        });
    }
</script>

<script type="text/javascript ">
    mui.init()
    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
        s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

        return Y + M + D + h + m + s;
    }
    $(document).ready(function () {
        //时间选择
        var selectTime = {
            init: function () {
                var self = $("#selectTime");
                //self.addEventListener("tap", selectTime.click);
                self.click(selectTime.click);

                selectTime.input = $("#selectTime");
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
                    var txt = rs.text;
                    selectTime.self.value = txt;
                    $("#selectTime").val(txt);
                    //page.curTime = txt //设置当前时间
                    if (txt) {
                        selectTime.self.innerText = txt;
                        $('#historyTitle').text(txt)
                        txt = txt.replace(/-/g, '')
                    }

                });
            }
        };
        selectTime.init();

    });
</script>
</body>

</html>
