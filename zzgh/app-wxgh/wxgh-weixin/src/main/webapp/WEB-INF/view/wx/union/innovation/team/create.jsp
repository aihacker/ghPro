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
    <title>创建团队</title>
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

        .input-row-spana {
            line-height: 40px;
            width: 20%;
            display: inline-block;
        }

        #li-user-list p {
            margin: 0px 5px 5px 0px;
            float: left;
            width: 80px;
            background-color: gainsboro;
            border-radius: 10px;
            text-align: center;
        }

        .ui-img-div {
            height: 80px;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head mui-hidden">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">创建团队</h1>
    <a class="mui-btn mui-btn-link mui-pull-right">提交</a>
</header>

<div id="mainScrollWrapper" class="mui-scroll-wrapper mui-content" style="padding-top:0px; bottom: 48px;">
    <div class="mui-scroll">
        <ul class="mui-table-view" style="margin-top: 0px;">
            <li class="mui-table-view-cell mui-input-row">
                <label>创建人</label>
                <span class="input-row-spana ui-text-info mui-text-right">${empty wxgh_user.name?'未知':wxgh_user.name}</span>
            </li>
            <li class="mui-table-view-cell mui-input-row">
                <label>团队名称</label>
                <input name="team" type="text" placeholder="20个字符以内" style="width: 60%;"/>
            </li>

            <li class="mui-table-view-cell mui-input-row ui-choose-img">
                <label>团队头像</label>
                <div class="mui-row">
                    <div class="mui-col-sm-3 mui-col-xs-3" id="chooseBtn">
                        <img src="${home}/weixin/image/union/innovation/icon/icon_add1.png"/>
                    </div>
                    <div class="ui-img-div mui-col-sm-3 mui-col-xs-3 mui-hidden" id="previewImg">
                        <img src=""/>
                    </div>
                </div>
            </li>
            <li class="mui-table-view-cell mui-input-row">
                <a id="a-add-user" class="mui-navigate-right">
                    新增团队成员
                </a>
            </li>
            <li class="mui-table-view-cell ">
                <label>简介</label>
                <textarea rows="5" id="txt" placeholder="输入800个字符以内"></textarea>
            </li>

        </ul>

        <ul class="mui-table-view ui-margin-top-15" id="ul-addUser" style="border-bottom: 2px solid #e4e3e6;">
            <li class="mui-table-view-cell mui-text-center">
                已添加的成员
            </li>
            <li class="mui-table-view-cell" id="li-user-list">
                <%--<p>--%>
                <%--曾子建--%>
                <%--<span class="mui-icon mui-icon-close"></span>--%>
                <%--</p>--%>
            </li>
        </ul>
    </div>
</div>
<div class="ui-fixed-bottom">
    <button id="addBtn" class="mui-btn mui-btn-block ui-btn mui-btn-primary">确定</button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script src="${home}/script/request.js" type="text/javascript"></script>


<script type="text/javascript">

    var homePath = "${home}";
    var own = "${wxgh_user.userid}";//自己

    wxgh.wxInit('${weixin}')

    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
    $(function () {

        $("#txt").on("input", function () {
            var $selt = $(this);
            var val = $(this).val();
            if (val.length > 800) {
                val = val.substr(0, 800);
                $selt.val(val);
                mui.toast("简介在800个字符以内哦~");
            }
        });

    })

    $(document).ready(function () {

        $("#li-user-list").on("tap", "p .mui-icon-close", function () {
            $(this).parent().remove();
        });

        function buildSelUsers(userId, userName) {
            var ps = '<p data-userid="' + userId + '" data-username="' + userName + '">' + userName + '<span class="mui-icon mui-icon-close"></span></p>'
            document.getElementById("li-user-list").innerHTML += ps;
        }

        document.querySelector('#a-add-user').onclick = function () {
            wxgh.wxContactOpen('${wx_contact}', function (all, users) {
                console.log(all)
                console.log(users)
                if (all) {
                    alert('不支持全选哦')
                    return
                }
                if (users && users.length > 0) {
                    var selectAll = all;     // 是否全选（如果是，其余结果不再填充）
                    if (!selectAll) {
                        var selectedUserList = users;    // 已选的成员列表
                        document.getElementById("li-user-list").innerHTML = "";
                        for (var i = 0; i < selectedUserList.length; i++) {
                            var user = selectedUserList[i];
                            var userId = user.id;    // 已选的单个成员ID
                            var userName = user.name;    // 已选的单个成员名称
                            buildSelUsers(userId, userName);
                        }
                    }
                }
            })
        }

        <%--
               var evalWXjsApi = function (jsApiFun) {
                   if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
                       jsApiFun();
                   } else {
                       document.attachEvent && document.attachEvent("WeixinJSBridgeReady", jsApiFun);
                       document.addEventListener && document.addEventListener("WeixinJSBridgeReady", jsApiFun);
                   }
               }

               document.querySelector('#a-add-user').onclick = function () {
                   evalWXjsApi(function () {
                       WeixinJSBridge.invoke("openEnterpriseContact", {
                           "groupId": "${wx_contact.groupId}",    // 必填，管理组权限验证步骤1返回的group_id
                           "timestamp": "${wx_contact.timestamp}",    // 必填，管理组权限验证步骤2使用的时间戳
                           "nonceStr": "${wx_contact.nonceStr}",    // 必填，管理组权限验证步骤2使用的随机字符串
                           "signature": "${wx_contact.signature}",  // 必填，管理组权限验证步骤2生成的签名
                           "params": {
                               'departmentIds': [0],    // 非必填，可选部门ID列表（如果ID为0，表示可选管理组权限下所有部门）
                               'tagIds': [0],    // 非必填，可选标签ID列表（如果ID为0，表示可选所有标签）
       //                        'userIds' : ['zhangsan','lisi'],    // 非必填，可选用户ID列表
                               'mode': 'multi',    // 必填，选择模式，single表示单选，multi表示多选
                               'type': ['user'],    // 必填，选择限制类型，指定department、tag、user中的一个或者多个
                               'selectedDepartmentIds': [],    // 非必填，已选部门ID列表
                               'selectedTagIds': [],    // 非必填，已选标签ID列表
                               'selectedUserIds': [],    // 非必填，已选用户ID列表
                           },
                       }, function (res) {
                           if (res.err_msg.indexOf('function_not_exist') > -1) {
                               alert('版本过低请升级');
                           } else if (res.err_msg.indexOf('openEnterpriseContact:fail') > -1) {
                               return;
                           }
                           var result = JSON.parse(res.result);    // 返回字符串，开发者需自行调用JSON.parse解析
                           var selectAll = result.selectAll;     // 是否全选（如果是，其余结果不再填充）
                           if (!selectAll) {
                               var selectedUserList = result.userList;    // 已选的成员列表
                               document.getElementById("li-user-list").innerHTML = "";
                               for (var i = 0; i < selectedUserList.length; i++) {
                                   var user = selectedUserList[i];
                                   var userId = user.id;    // 已选的单个成员ID
                                   var userName = user.name;    // 已选的单个成员名称
                                   buildSelUsers(userId, userName);
                               }
                           }
                       })
                   });
               }
       --%>


        var $prevImg = $('#previewImg')
        $('#chooseBtn').on('tap', function () {
            wx.chooseImage({
                count: 1, // 默认9
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    if (localIds && localIds.length > 0) {
                        wxgh.get_wx_img(localIds[0], function (src) {
                            $prevImg.removeClass('mui-hidden')
                            $prevImg.find('img').attr('src', src)
                        });
                    }
                }
            });
        })

        //添加图片
        document.getElementById("addBtn").addEventListener('tap', function () {
            var self = this;

            var team = $("input[name=team]").val();
            if (!team) {
                alert("请输入团队名称哦~");
                return;
            }
            var localId = $prevImg.find('img').attr('src')
            if (!localId) {
                alert('请选择团队头像哦')
                return
            }
            var txt = $("#txt").val();
            if (!txt) {
                alert("请输入团队简介哦~");
                return;
            }

            var $users = $("#li-user-list p");
            if ($users.length < 1) {
                alert("请添加团队的成员哦~");
                return;
            }

            if (!this.loading) {
                this.loading = ui.loading('请稍候...')
            }

            this.loading.show()

            var self = this;
            var upload_data = {
                info: txt,
                name: team
            }

            wx.uploadImage({
                localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
                isShowProgressTips: 0,// 默认为1，显示进度提示
                success: function (res) {
                    var serverId = res.serverId; // 返回图片的服务器端ID
                    if (serverId) {
                        upload_data['avatar'] = serverId
                    }
                    var url = homePath + '/wx/union/innovation/team/add.json'
                    mui.post(url, upload_data, function (res) {
                        self.loading.hide()
                        if (res.ok) {
                            var groupId = res.data;
                            var $users = $("#li-user-list p");
                            var url = "${home}/wx/union/innovation/team/add_user.json"
                            for (var i = 0; i < $users.length; i++) {
                                var userid = $($users[i]).attr("data-userid");
                                var username = $($users[i]).attr("data-username");
                                var data = {
//                                    action: "addUser",
                                    userid: userid,
                                    groupId: groupId,
                                    groupName: username
                                }
                                mui.post(url, data, function (res) {
                                    if (res.ok) {
                                    } else {
                                        mui.toast(res.msg)
                                    }
                                }, 'json')
                            }
                            ui.showToast('创建成功', function () {
                                mui.openWindow(homePath + '/wx/union/innovation/team/index.html')
                            })
                        } else {
                            ui.showToast('创建失败')
                        }
                    }, 'json')
                }
            });
        });
    });

</script>


</body>

</html>
