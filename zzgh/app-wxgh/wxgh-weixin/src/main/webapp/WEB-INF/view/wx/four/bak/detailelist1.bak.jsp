<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>公司</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body, .mui-content {
            /*background-color: #fff;*/
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .mui-search {
            margin: 22px 12px -6px 12px;
        }

        a.mui-icon-arrowdown {
            width: 50px;
            height: 30px;
            top: 10px;
            text-align: center;
            position: absolute;
            right: 10px;
        }
    </style>
</head>

<header class="mui-bar mui-bar-nav ui-head">
    <h1 class="mui-title">公司</h1>
</header>

<div class="mui-content" style="padding-top: 0;margin-top: 44px;">

    <ul class="mui-table-view" id="ul-list">
        <li class="mui-table-view-cell">
                <div class="div-dept"data-href="${home}/wx/four/detailelist2.html?deptid=1">
                    <div>
                        佛山电信分公司
                        <a style="color: black;" href="#popover6" class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                    </div>
                </div>
        </li>
        <li class="mui-table-view-cell">
            <div class="div-dept" data-href="${home}/wx/four/detailelist2.html?deptid=331">
                <div>
                    顺德区分公司
                    <a style="color: black;" href="#popover1" class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                </div>
            </div>
        </li>
        <li class="mui-table-view-cell">
            <div class="div-dept" data-href="${home}/wx/four/detailelist2.html?deptid=334">
                <div>
                    南海区分公司
                    <a style="color: black;" href="#popover2" class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                </div>
            </div>

        </li>
        <li class="mui-table-view-cell">
            <div class="div-dept" data-href="${home}/wx/four/detailelist2.html?deptid=332">
                <div>
                    禅城区分公司
                    <a style="color: black;" href="#popover3"
                       class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                </div>
            </div>

        </li>
        <li class="mui-table-view-cell">
            <div class="div-dept" data-href="${home}/wx/four/detailelist2.html?deptid=335">
                <div>
                    三水区分公司
                    <a style="color: black;" href="#popover4"
                       class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                </div>
            </div>

        </li>
        <li class="mui-table-view-cell">
            <div class="div-dept" data-href="${home}/wx/four/detailelist2.html?deptid=333">
                <div>
                    高明区分公司
                    <a style="color: black;" href="#popover5"
                       class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                </div>
            </div>
        </li>

        <li class="mui-table-view-cell">
            <div class="div-dept" data-href="${home}/wx/four/detailelist2.html?deptid=330">
                <div>
                    佛山电信分公司本部
                    <a style="color: black;" href="#popover7"
                       class="mui-icon mui-icon-arrowdown mui-pull-right"></a>
                </div>
            </div>
        </li>
    </ul>

</div>
<div id="popover1" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=331">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=331">需要更换的设备</a></li>
    </ul>
</div>
<div id="popover2" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=334">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=334">需要更换的设备</a></li>
    </ul>
</div>
<div id="popover3" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=332">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=332">需要更换的设备</a></li>
    </ul>
</div>
<div id="popover4" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=335">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=335">需要更换的设备</a></li>
    </ul>
</div>
<div id="popover5" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=333">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=333">需要更换的设备</a></li>
    </ul>
</div>
<div id="popover6" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class=""
                                           href="${home}/wx/four/collecting.html?foshanDeptid=1&type=1">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?foshanDeptId=1">需要更换的设备</a>
        </li>
    </ul>
</div>

<div id="popover7" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting.html?deptId=330">查看台帐汇总</a></li>
        <li class="mui-table-view-cell"><a class="" href="${home}/wx/four/collecting2.html?deptId=330">需要更换的设备</a></li>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()
    var homePath = '${home}'
</script>
<script>
    $(function () {
        $("div.div-dept").click(function () {
            var href = $(this).attr("data-href");
            window.location.href = href;
        });

        $(".mui-popover").click(function () {
            if (!this.loading) {
                this.loading = ui.loading('请稍候...')
            }
            this.loading.show();
        });
    });
</script>

</body>

</html>
