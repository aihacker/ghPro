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
    <title>创新项目</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <style>
        .img-item {
            display: inline-block;
            margin: 5px;
            position: relative;
        }

        .img-item input[type=file] {
            opacity: 0;
            background-color: transparent;
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
        }

        .plus-img-div img,
        .img-item img {
            width: 100px;
            height: 100px;
        }

        .img-item .fa-minus-circle {
            content: '';
            position: absolute;
            top: -1px;
            right: -4px;
            color: #a94442;
            background-color: #fff;
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            border-radius: 20px;
        }

        .img-item .fa-minus-circle:hover,
        .img-item .fa-minus-circle:focus {
            color: #843534;
        }

        .ui-popover-btn {
            position: absolute;
            bottom: 0;
            width: 100%;
            z-index: 999;
            background-color: #fff;
            -webkit-border-radius: 7px;
            border-radius: 7px;
        }

        .ui-popover-btn button {
            height: 35px;
            width: 95%;
            line-height: 25px;
            margin-bottom: 4px;
        }

        #deptid, #type {
            float: right;
            width: 60%;
            padding: 0px;
        }

        #ul-list {
            padding-top: 44px;
        }

        .mui-media-body span.ui-user-phone {
            position: absolute;
        }

        input, select {
            color: gray;
        }

        h4 {
            padding-bottom: 5px;
            padding-top: 7px;
            text-align: center;
        }

        .mui-table-view-cell.mui-active {
            background-color: #fff;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

<%--<h1 class="mui-title">微创新</h1>--%>
<%--&lt;%&ndash;<a id="addBtn" class="mui-btn mui-btn-link mui-pull-right">提交</a>&ndash;%&gt;--%>
<%--</header>--%>

<div id="mainScrollWrapper" class="mui-scroll-wrapper mui-content">

    <div class="mui-scroll">
        <div class="mui-input-group">
            <div class="mui-input-row">
                <h4>资金申请</h4>
            </div>
        </div>
        <ul class="mui-table-view" style="margin-top: 0px;">
            <li class="mui-table-view-cell mui-input-row">
                <label class="ui-li-label ui-text-info">资金数额</label>
                <input class="mui-numbox-input" name="applyPrice" type="number" placeholder="创新项目申请金额"
                       style="width: 60%;"/>
            </li>
        </ul>
        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <h4>基本信息</h4>
            </div>
        </div>
        <ul class="mui-table-view">
            <%--<li class="mui-table-view-cell">--%>
                <%--<label class="ui-li-label ui-text-info">申报类别</label>--%>
                <%--<select class="select-item" id="type">--%>
                    <%--<option>选择申报类别</option>--%>
                    <%--<option value="1">技能</option>--%>
                    <%--<option value="2">营销</option>--%>
                    <%--<option value="3">服务</option>--%>
                    <%--<option value="4">管理</option>--%>
                <%--</select>--%>
            <%--</li>--%>
            <li class="mui-table-view-cell mui-input-row">
                <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">成果名称</label>
                <input name="name" type="text" placeholder="20个字符以内" style="width: 60%;"/>
            </li>
            <li class="mui-table-view-cell mui-input-row">
                <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">团队名称</label>
                <input name="team" type="text" placeholder="20个字符以内" style="width: 60%;"/>
            </li>
        </ul>
        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell">
                创新点
                <div class="ui-textarea-div">
                    <textarea rows="3" id="point" maxlength="800" placeholder="输入800个字符以内"></textarea>
                    <%--<small>0 / 800</small>--%>
                </div>
            </li>
        </ul>
        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell">
                创新举措
                <div class="ui-textarea-div">
                    <textarea rows="3" id="behave" maxlength="800" placeholder="输入800个字符以内"></textarea>
                    <%--<small>0 / 800</small>--%>
                </div>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell mui-row ">
                <div class="mui-row ui-choose-img ui-margin-top-15">
                    <label>附件上传</label>
                    <br>
                    <div class="ui-choose-img " id="chooseBtn">
                        <%--<img src="${home}/image/common/icon_add1.png"/>--%>
                    </div>
                </div>

            </li>
        </ul>
        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell">
                成果描述
                <div class="ui-textarea-div">
                    <textarea rows="3" id="txt" maxlength="800" placeholder="输入800个字符以内"></textarea>
                    <%--<small>0 / 800</small>--%>
                </div>
            </li>
        </ul>
        <ul class="mui-table-view ui-margin-top-15">
            <a id="a-add-user" class="mui-navigate-right">
                <li class="mui-table-view-cell">
                    新增成员
                </li>
            </a>
        </ul>

        <ul class="mui-table-view " id="ul-addUser" style="border-bottom: 15px solid #e4e3e6; margin-bottom: 30px;">
            <li class="mui-table-view-cell mui-text-center">
                已添加成员
            </li>
        </ul>

        <div class="ui-fixed-bottom">
            <button id="addBtn" class="mui-btn mui-btn-block mui-btn-blue ui-btn">提交</button>
        </div>
        <%--
            <div id="middlePopover" class="mui-popover" style="height: 400px;margin: auto;">
                <div class="mui-popover-arrow">
                </div>
                <div name="div-search" id="div-search" class="mui-input-row mui-search" style="margin: auto;margin-top: 6px;width: 90%;position: absolute;top: 0px;z-index: 999;left: 5%;">
                    <input id="search" type="search" class="mui-input-clear" placeholder="请输入您要搜索的用户名">
                </div>
                <div class="mui-scroll-wrapper" style="overflow: scroll;margin-top: 50px;">
                    <div class="mui-scroll" id="div-list">
                        <ul class="mui-table-view mui-input-group" id="ul-list">

                        </ul>
                    </div>

                </div>
                <div id="div-hide" class="mui-btn mui-btn-block" style="position: absolute;bottom: 0px;z-index: 999;">关闭</div>
            </div>--%>
    </div>
</div>

<div style="display: flex;align-items: center;width: 100%;height: 100%;height: 400px">
    <div id="middlePopover" class="mui-popover" style="height: 300px;">
        <div name="div-search" id="div-search" class="mui-input-row mui-search"
             style="margin: auto;margin-top: 6px;width: 90%;position: absolute;top: 0px;z-index: 999;left: 5%;background-color: #fff;">
            <input id="search" type="search" class="mui-input-clear" placeholder="请输入您要搜索的用户名"
                   style="margin-bottom: 0px;">
        </div>
        <div id="userScrollWrapper" class="mui-scroll-wrapper" style="margin-bottom:30px;">
            <div class="mui-scroll">
                <ul class="mui-table-view mui-input-group mui-table-view-radio" id="ul-list">
                </ul>
            </div>
        </div>
        <div class="ui-popover-btn">
            <button id="div-hide" type="button" class="weui_btn weui_btn_plain_primary">确定</button>
        </div>
    </div>
</div>


<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">

    var homePath = "${home}";

    mui.init()
    wxgh.wxInit('${weixin}')
    wxgh.autoTextarea($('.ui-textarea-div textarea'), 800)

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $('#a-add-user').on("tap", function () {
        mui('#middlePopover').popover('show')
    })

    $('#div-hide').on('tap', function () {
        mui('#middlePopover').popover('hide')
    })

    initPopver();

    function initPopver() {
        var popver = document.getElementById('middlePopover');
        popver.style.left = ((window.screen.availWidth - 280) / 2) + 'px';
        popver.style.top = ((window.screen.availHeight - 380) / 2) + 'px';
    }

    $(function () {

        $("input[name=name]").on("input", function () {
            var $selt = $(this);
            var val = $(this).val();
            if (val.length > 80) {
                val = val.substr(0, 80);
                $selt.val(val);
                mui.toast("成果名称在80个字符以内哦~");
            }
        });

        /* $("#a-add-user").on("tap", function(){
         $("body").css("overflow", "hidden");
         });*/


        /*搜索*/
        $("#search").keyup(function () {
            $("#ul-list").html("");
            var val = $(this).val();
            if($.trim(val)){
                $.ajax({
                    type: "get",
                    dataType: "json",
                    url: "${home}/wx/union/innovation/member/micro/query.json",
                    data: {
                        name: val
                    },
                    success: function (result) {
                        callbackFunc(result);
                    }
                });
            }
            function callbackFunc(result) {
                var data = result.data;
                for (var i = 0; i < data.length; i++) {
                    var user = data[i];
                    var avatar = user.avatar;
                    if (null == avatar || "" == avatar) {
                        avatar = "${home}/image/common/nopic.gif";
                    }
                    var mobile = user.mobile;
                    if (null == mobile || "" == mobile) {
                        mobile = "未知号码";
                    }
                    mobile = "(" + mobile + ")";
                    var ps = '<li class="mui-table-view-cell mui-media radioUser" userid="' + user.userid + '"><div class="mui-slider-right mui-disabled"><a class="mui-btn mui-btn-red a-del">删除</a></div><div class="mui-slider-handle" style="background-color: #f7f7f7;"><img style="width: 42px;margin: 4px;border-radius: 50%;"class="mui-media-object mui-pull-left"src="' + avatar + '"><div class="mui-media-body">' + user.name + '<span class="ui-user-phone">' + mobile + '</span><p class="mui-ellipsis"><span class="mui-left mui-pull-left">' + user.deptname + '</span></p></div></div></li>';
                    $("#ul-list").append(ps);
                }
                //$('html,body').animate({scrollTop: $('#ul-addUser').offset().top}, 300);
            }
        });

        $("#ul-list").on("tap", "li.radioUser", function () {
            var $this = $(this);
            var userid = $this.attr("userid");
            var $haveUsers = $("#ul-addUser li");
            for (var i = 0; i < $haveUsers.length; i++) {
                var $haveUser = $($haveUsers[i]);
                if (userid == $haveUser.attr("userid")) {
                    mui.toast("该用户已存在哦~");
                    return;
                }
            }
            var userHtml = $this.prop("outerHTML");
            var $ul_addUser = $("#ul-addUser");
            $ul_addUser.append(userHtml);
            $("#ul-addUser input").remove();
            $ul_addUser.children("li").removeClass("mui-active");
            $ul_addUser.children("div.mui-slider-handle").css("background-color", "#ffffff !important");

            mui('#mainScrollWrapper').scroll().reLayout();
            mui('#mainScrollWrapper').scroll().scrollToBottom(500);

            $this.remove();
        });

        $("#ul-addUser").on("tap", ".user-del", function () {
            var $this = $(this);
            var chooseBtn = ["否", "是"];
            mui.confirm("确定删除该成员吗？", "提示", chooseBtn, function (e) {
                if (e.index == 1) {
                    $this.parents(".user-li").remove();
                    mui.toast("删除成功 ");
                } else {
                    return;
                }
            });
        })

        $("#ul-addUser").on("tap", ".a-del", function () {
            $(this).parents(".radioUser").remove();
        });
        /*
         $("#middlePopover").blur(function(){
         $("body").css("overflow", "scroll");
         });*/

    })


    $(document).ready(function () {
        var $upload = wxgh.imagechoose($("#chooseBtn"));
        //添加图片
        document.getElementById("addBtn").addEventListener('tap', function () {
            var self = this;

            var price = $('input[name=applyPrice]').val()
            if (!price) {
                mui.toast('请输入申请金额哦~')
                return
            }

//            var type = $("#type option:checked").attr("value");
//            if (!type) {
//                mui.toast("请选择申报类别哦~");
//                return;
//            }

            var name = $("input[name=name]").val();
            if (!name) {
                mui.toast("请输入成果名称哦~");
                return;
            }

            var team = $("input[name=team]").val();
            if (!team) {
                mui.toast("请输入团队名称哦~");
                return;
            }

            var point = $("#point").val();
            if (!point) {
                mui.toast("请输入创新点哦~");
                return;
            }

            var behave = $("#behave").val();
            if (!behave) {
                mui.toast("请输入创新举措哦~");
                return;
            }

            var txt = encodeURIComponent($("#txt").val());
            if (!txt) {
                mui.toast("请输入成果描述哦~");
                return;
            }

            var $users = $("#ul-addUser li.radioUser");
            if ($users.length < 1) {
                mui.toast("请添加团队的成员哦~");
                return;
            }


            if (!this.loading) {
                this.loading = new ui.loading('请稍候...')
            }

            this.loading.show()

            var self = this;
            var upload_data = {
//                type: type,
                name: name,
                point: point,
                behave: behave,
                txt: txt,
                team: team,
                price: price,
                adviceId: '${param.id}',
            }
            $upload.upload(function (mediaIds) {
                if (mediaIds) {
                    upload_data['imgs'] = mediaIds.toString()
                } else {
                    upload_data['imgs'] = "";
                }
                var url = homePath + '/wx/union/innovation/member/micro/add.json'

                mui.post(url, upload_data, function (res) {
                    self.loading.hide()
                    if (res.ok) {
                        var workId = res.data;
                        var $users = $("#ul-addUser li.radioUser");
                        var url = "${home}/wx/union/innovation/member/micro/add_user.json"
                        for (var i = 0; i < $users.length; i++) {
                            var userid = $($users[i]).attr("userid");
                            var data = {
                                userid: userid,
                                workId: workId
                            }
                            mui.post(url, data, function (res) {
                                if (res.ok) {

                                } else {
                                    mui.toast(res.msg)
                                }
                            }, 'json')
                        }
                        ui.showToast('提交成功', function () {
                            wxgh.redirectTip(homePath, {
                                msg: '您的申报已经提交哦，请静候管理员审核~',
                                title: '系统提示'
                            })
                        })
                    } else {
                        mui.toast(res.msg)
                    }
                }, 'json')
            })
        });
    });

</script>


</body>

</html>
