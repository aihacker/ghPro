<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/1
  Time: 19:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>创新建议</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

        .select-image-div label {
            display: block;
            position: relative;
            top: 0px;
            width: 100%;
        }

        .select-image-div {
            padding-top: 10px;
        }

        .mui-table-view-cell select{
            opacity: 1;
        }

        .select-item {
            float: right;
            width: 60%;
           /* padding: 0px; */
        }

        .fadeIn {
            -webkit-animation-name: fadeIn; /*动画名称*/
            -webkit-animation-duration: 1s; /*动画持续时间*/
            -webkit-animation-iteration-count: 1; /*动画次数*/
            -webkit-animation-delay: 0.2s; /*延迟时间*/
        }
        .mui-table-view-cell.mui-active{
            background-color: #fff;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head" style="position: fixed;">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">合理化建议</h1>
    <a id="addBtn" class="mui-btn mui-btn-link mui-pull-right">提交</a>
</header>--%>

<div class="mui-content">


    <ul class="mui-table-view" style="margin-top: 0px;">
        <li class="mui-table-view-cell">
            <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">建议类别</label>
            <select class="select-item" id="type">
                <option>选择建议类别</option>
                <option value="1">技能</option>
                <option value="2">营销</option>
                <option value="3">服务</option>
                <option value="4">管理</option>
                <option value="5">其它</option>
            </select>
        </li>
        <li class="mui-table-view-cell mui-input-row">
            <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">标题</label>
            <input name="title" type="text" placeholder="20个字符以内" style="width: 60%;"/>
        </li>
        <%--<li class="mui-table-view-cell">
            <label class="ui-li-label ui-text-info">单位部门</label>
            <select class="select-item" id="deptid">
                <option>请选择部门</option>
                <c:forEach items="${dept}" var="item">
                    <option value="${item.deptid}">${item.deptname}</option>
                </c:forEach>
            </select>
        </li>--%>
        <li class="mui-table-view-cell">
            建议内容
            <div class="ui-textarea-div">
                <textarea rows="3" id="advice" maxlength="200" placeholder="输入200个字符以内"></textarea>
            </div>
        </li>
    </ul>
</div>

<div class="ui-fixed-bottom">
    <button id="addBtn" class="mui-btn mui-btn-block mui-btn-blue ui-btn">提交
    </button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">

    $(function () {

        wxgh.autoTextarea($('.ui-textarea-div textarea'), 200)

        $("input[name=title]").keyup(function () {
            var $selt = $(this);
            var val = $(this).val();
            if (val.length > 20) {
                val = val.substr(0, 20);
                $selt.val(val);
                mui.toast("标题在20个字符以内哦~");
            }
        });
        $("input[name=title]").blur(function () {
            var $selt = $(this);
            var val = $(this).val();
            if (val.length > 20) {
                val = val.substr(0, 20);
                $selt.val(val);
                mui.toast("标题在20个字符以内哦~");
            }
        });
        $("#advice").keyup(function () {
            var $selt = $(this);
            var val = $(this).val();
            if (val.length > 200) {
                val = val.substr(0, 200);
                $selt.val(val);
                mui.toast("建议内容在200个字符以内哦~");
            }
        });
        $("#advice").blur(function () {
            var $selt = $(this);
            var val = $(this).val();
            if (val.length > 200) {
                val = val.substr(0, 200);
                $selt.val(val);
                mui.toast("建议内容在200个字符以内哦~");
            }
        });

    })

    $(document).ready(function () {
        //添加图片
        document.getElementById("addBtn").addEventListener('tap', function () {
            var self = this;
            var type = $("#type option:checked").attr("value");
            if (!type) {
                mui.toast("请选择申报类别哦~");
                return;
            }

            /* var deptid = $("#deptid option:checked").attr("value");
             if (!deptid) {
             mui.toast("请选择部门哦~");
             return;
             }*/

            var title = $("input[name=title]").val();
            if (!title) {
                mui.toast("请输入标题哦~");
                return;
            }

            var advice = $("#advice").val();
            if (!advice) {
                mui.toast("请输入建议内容哦~");
                return;
            }

            if (!this.loading) {
                this.loading = new ui.loading('请稍候...')
            }

            this.loading.show();

            var self = this;
            var upload_data = {
                type: type,
                title: title,
                advice: advice
            }
            var url = "${home}/wx/union/innovation/declare/advice/add.json";
            mui.post(url, upload_data, function (res) {
                if (res.ok) {
                    self.loading.hide();
                    ui.showToast('提交成功', function () {
                        wxgh.redirectTip(homePath, {
                            msg: '您的建议已经提交哦，请静候管理员审核~',
                            url: homePath + "/wx/union/innovation/index.html",
                            title: '发布成功',
                            urlMsg: '返回首页'
                        })
                    })
                } else {
                    mui.toast(res.msg)
                }
            }, 'json')
        });
    });

</script>


</body>

</html>
