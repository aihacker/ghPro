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
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/app.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }
        li a{
            overflow: scroll;
        }
    </style>
</head>

<body>


<div class="mui-scroll-wrapper ui-scroll-wrapper" style="top:0;padding-top: 0px;height:100%">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <a style="overflow: auto;">
                    <label class="ui-li-label ui-text-info">主题</label>
                    <span id="name">${vote.theme}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a style="overflow: auto;">
                    <label class="ui-li-label ui-text-info">选项</label>
                </a>
                <c:choose>
                    <c:when test="${!empty options}">
                        <c:forEach items="${options}" varStatus="i" var="row">
                            <div style="line-height: 30px;margin-top: 20px;">
                                <div>
                                    <p style="width:30px;height:30px;float:left;text-align:center;background:#2196F3;border-radius:50%;color:white;font-size:25px;margin-right:10px;margin-top: 5px;">${i.index+1}</p>


                                    <span >
                                    <a href="javascript:;">
                                        <div class="ui-img-div" style="float:left;margin-left:30px">
                                            <img data-preview-group="img" data-preview-src="${home}${row.path}" class="mui-media-object" src="${home}${row.path}"/>
                                        </div>
                                    </a>

                                    </span>
                                </div>

                                <p>简介：${row.options}</p>




                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>对不起，暂无选项</c:otherwise>
                </c:choose>
            </li>
            <li class="mui-table-view-cell">
                <a style="overflow: auto;">
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${vote.createTime}"
                                          pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate> </span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a style="overflow: auto;">
                    <label class="ui-li-label ui-text-info">投票有效期</label>
                    <span><fmt:formatDate value="${vote.effectiveTime}"
                                          pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a style="overflow: auto;">
                    <label class="ui-li-label ui-text-info">创建人</label>
                    <span>${vote.userName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell" style="display: none;">
                <a style="overflow: auto;">
                    <label class="ui-li-label ui-text-info">创建人ID</label>
                    <span>${vote.userid}</span>
                </a>
            </li>
        </ul>


        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell mui-text-center">
                管理员操作
            </li>
            <li class="mui-table-view-cell">
                <p class="mui-button-row mui-text-center">
                    <button class="mui-btn mui-btn-green mui-left mui-pull-left shenhe" value="1" itemId="${vote.id}">
                        通过
                    </button>

                    <button class="mui-btn mui-btn-danger mui-right mui-pull-right shenhe" value="2"
                            itemId="${vote.id}">不通过
                    </button>
                </p>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
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
                mui.openWindow(homePath + '/wx/admin/common/vote/index.html');
            });
        }
        $(document).ready(function () {
            $("#a-back", parent.document).show();
        });
        $("button.shenhe").click(function () {
            var id = $(this).attr("itemId");
            var status = $(this).attr("value");
            if (status == 2) {

            } else {
                if (!status) {
                    mui.alert("请把信息填写完整", "提示", "确定", function () {

                    });
                    return;
                }
            }


            $.ajax({
                dataType: "json",
                url: '${home}/wx/admin/common/vote/shenhe.json',
                data: {action: 'shenhe', id: id, status: status},
                success: function (result) {
                    dealData(result);
                }
            });

            function dealData(result) {
                if ("success" == result.msg) {
                    mui.toast("审核成功");
                    window.location.href = "${home}/wx/admin/common/vote/main.html";
                } else {
                    mui.toast("审核失败");
                }
            }
        });
    });
</script>

<script type="text/javascript ">
    mui.init()

    wxgh.previewImageInit()

    mui('body').on('tap', 'a', function() {
        //获取URL
        var href = this.getAttribute('href');
        //如果不是plus环境直接跳
        if(!mui.os.plus) {
            location.href = href;
            return;
        }
    })

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
