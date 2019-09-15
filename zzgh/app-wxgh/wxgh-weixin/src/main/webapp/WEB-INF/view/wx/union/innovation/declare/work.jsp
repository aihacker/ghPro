<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>工作坊</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<style>
    .imgList {
        margin-top: 10px;
        padding-left: 15px;
    }

    .imgList li {
        display: inline-block;
        position: relative;
        margin-right: 7px;
    }

    .imgList img {
        width: 100px;
        height: 100px;
        border-radius: 6px;
        border: 1px solid #919191;
    }

    .imgDelBtn {
        width: 20px;
        height: 20px;
        border-radius: 50%;
        position: absolute;
        background: #efeff4 url("${home}/image/common/x.png") no-repeat center;
        background-size: 100% 100%;
        top: -6px;
        right: -6px;
    }

    #addImgBtn {
        color: #1596d5;
    }

    #searchBox {
        display: none;
    }

    .memberList li {
        display: flex;
        background-color: #ffffff;
        padding: 8px 12px;
        border-bottom: 1px solid #ebebeb;
        position: relative;
    }

    .memberList img {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        border: 1px solid #ebebeb;
    }

    .memberList div {
        flex: 1;
        padding-left: 12px;
    }

    .memberList .checked {
        background: url("${home}/image/common/checked.png") no-repeat right;
        background-size: 20px 20px;
    }

    .memberList span {
        display: block;
        height: 25px;
        line-height: 25px;
    }

    .searchBtn {
        text-align: center;
        margin-top: 4px;
        margin-right: 12px;
        display: block;
        width: 80px;
        height: 33px;
        line-height: 33px;
        border-radius: 5px;
        background-color: #0099FF;
        color: #ffffff;
    }

    .ckBox {
        display: block;
        width: 20px;
        height: 20px;
        position: absolute;
        right: 12px;
        top: 50%;
        margin-top: -10px;
    }

    .checked {
        background: url("${home}/image/common/checked.png") no-repeat center;
        background-size: 100% 100%;
    }

    #selUsers {
        padding-left: 12px;
        padding-top: 8px;
    }

    #selUsers li {
        display: inline-block;
        padding: 4px;
        background-color: lightgrey;
        border-radius: 4px;
        height: 30px;
        line-height: 30px;
        width: 80px;
        position: relative;
        margin-right: 4px;
        color: gray;
    }

    #selUsers small {
        position: absolute;
        top: 0;
        width: 50px;
        overflow-x: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    #selUsers a {
        display: block;
        height: 24px;
        width: 24px;
        border-radius: 50%;
        background: url("${home}/image/common/x.png") no-repeat center;
        background-size: 100% 100%;
        position: absolute;
        top: 50%;
        margin-top: -12px;
        right: 4px;
    }

    #selUsers span {
        display: block;
        position: absolute;
        height: 14px;
        line-height: 14px;
        font-size: .6em;
        top: -7px;
        left: 2px;
        /*width: ;*/
        background-color: gray;
        border-radius: 8px;
        padding: 0 3px;
        color: #ffffff;
    }

    input, select {
        color: gray;
    }

    h4 {
        padding-bottom: 5px;
        padding-top: 7px;
        text-align: center;
    }
</style>
<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

<%--<h1 class="mui-title">工作坊</h1>--%>
<%--&lt;%&ndash;<a class="mui-btn mui-btn-link mui-pull-right" id="submitBtn">提交</a>&ndash;%&gt;--%>
<%--</header>--%>

<div class="mui-content">

    <div class="ui-fixed-bottom">
        <button type="button" id="submitBtn" class="mui-btn mui-btn-primary">提交</button>
    </div>

    <div style="padding-bottom: 60px;">
        <div class="mui-input-group">
            <div class="mui-input-row">
                <h4>基本信息</h4>
            </div>
            <div class="mui-input-row">
                <label>团队名称</label>
                <input name="teamName" type="text" class="mui-input-clear" placeholder="请输入团队名称（20字以内）"/>
            </div>
            <div class="mui-input-row">
                <label>项目名称</label>
                <input name="itemName" type="text" class="mui-input-clear" placeholder="请输入项目名称（20字以内）"/>
            </div>
            <%--</div>--%>
            <%--<div class="mui-input-group ui-margin-top-10">--%>
            <div class="mui-input-row">
                <label>工作坊类型</label>
                <select name="shopType">
                    <option value="0">请选择</option>
                    <option value="1">创新工作坊</option>
                    <option value="2">劳模工作室</option>
                </select>
            </div>
            <div class="mui-input-row">
                <label>项目场地</label>
                <input name="address" type="text" class="mui-input-clear" placeholder="请输入项目场地（30字以内）"/>
            </div>
            <%--</div>--%>
            <%--<div class="mui-input-group ui-margin-top-10">--%>
            <div class="mui-table-view-cell">
                <a class="mui-navigate-right" id="addLeader">项目带头人</a>
            </div>
            <div class="mui-table-view-cell">
                <a class="mui-navigate-right" id="addMember">项目成员</a>
            </div>
        </div>
        <div id="selUsers">
            <ul>
                <%--<li>--%>
                <%--<small>usernameusername</small>--%>
                <%--<a></a>--%>
                <%--</li>--%>
            </ul>
        </div>
        <div class="mui-input-group ui-margin-top-10">
            <textarea rows="5" placeholder="成果描述" id="txt"></textarea>
        </div>
        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-table-view-cell">
                <a class="mui-navigate-right" id="addImgBtn">添加附件</a>
            </div>
        </div>
        <ul class="imgList">
            <%--<li>--%>
            <%--<img src="${home}/image/default/activities.png">--%>
            <%--<a class="imgDelBtn"></a>--%>
            <%--</li>--%>
        </ul>
    </div>
</div>

<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script src="${home}/script/request.js" type="text/javascript"></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>


<script type="text/javascript">
    var homePath = "${home}";
    var img = {
        url: '',
        info: ''
    };
    var imgs = [];
    var localIdArr = [];

    wxgh.wxInit('${weixin}');

    function select_con(func) {
        wxgh.wxContactOpen('${wx_contact}', function (all, users) {
            if (all) {
                alert('不支持全选哦')
                return
            }
            if (users && users.length > 0) {
                var selectAll = all;     // 是否全选（如果是，其余结果不再填充）
                if (!selectAll) {
                    var selectedUserList = users;    // 已选的成员列表
                    for (var i = 0; i < selectedUserList.length; i++) {
                        var user = selectedUserList[i];
                        var userId = user.id;    // 已选的单个成员ID
                        var userName = user.name;    // 已选的单个成员名称
                        func(userId, userName);
                    }
                }
            }
        })
    }

    document.querySelector('#addLeader').onclick = function () {
        select_con(function (userId, userName) {
            buildSelLeader(userId, userName)
        })
    }

    document.querySelector('#addMember').onclick = function () {
        select_con(function (userId, userName) {
            buildSelUsers(userId, userName)
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
    };

    document.querySelector('#addLeader').onclick = function () {
        evalWXjsApi(function () {
            WeixinJSBridge.invoke("openEnterpriseContact", {
                "groupId": '${wx_contact.groupId}',    // 必填，管理组权限验证步骤1返回的group_id
                "timestamp": '${wx_contact.timestamp}',    // 必填，管理组权限验证步骤2使用的时间戳
                "nonceStr": '${wx_contact.nonceStr}',    // 必填，管理组权限验证步骤2使用的随机字符串
                "signature": '${wx_contact.signature}',  // 必填，管理组权限验证步骤2生成的签名
                "params": {
                    'departmentIds': [0],    // 非必填，可选部门ID列表（如果ID为0，表示可选管理组权限下所有部门）
                    'tagIds': [],    // 非必填，可选标签ID列表（如果ID为0，表示可选所有标签）
//                    'userIds' : ['zhangsan','lisi'],    // 非必填，可选用户ID列表
                    'mode': 'single',    // 必填，选择模式，single表示单选，multi表示多选
                    'type': ['user'],    // 必填，选择限制类型，指定department、tag、user中的一个或者多个
                    'selectedDepartmentIds': [],    // 非必填，已选部门ID列表
                    'selectedTagIds': [],    // 非必填，已选标签ID列表
                    'selectedUserIds': []    // 非必填，已选用户ID列表
                }
            }, function (res) {
                if (res.err_msg.indexOf('function_not_exist') > -1) {
                    alert('版本过低请升级');
                } else if (res.err_msg.indexOf('openEnterpriseContact:fail') > -1) {
                    return;
                }
                //var str = JSON.stringify(res);
                //alert(str)

                var result = JSON.parse(res.result);    // 返回字符串，开发者需自行调用JSON.parse解析
                var selectAll = result.selectAll;     // 是否全选（如果是，其余结果不再填充）
                if (!selectAll) {
                    var selectedUserList = result.userList;    // 已选的成员列表
                    for (var i = 0; i < selectedUserList.length; i++) {
                        var user = selectedUserList[i];
                        var userId = user.id;    // 已选的单个成员ID
                        var userName = user.name;    // 已选的单个成员名称
                        buildSelLeader(userId, userName);
                    }
                }
            })
        });
    }
    --%>

    <%--
    /*
     * weixin jsdk select contact
     * */
    document.querySelector('#addMember').onclick = function () {
        evalWXjsApi(function () {
            WeixinJSBridge.invoke("openEnterpriseContact", {
                "groupId": "${wx_contact.groupId}",    // 必填，管理组权限验证步骤1返回的group_id
                "timestamp": "${wx_contact.timestamp}",    // 必填，管理组权限验证步骤2使用的时间戳
                "nonceStr": "${wx_contact.nonceStr}",    // 必填，管理组权限验证步骤2使用的随机字符串
                "signature": "${wx_contact.signature}",  // 必填，管理组权限验证步骤2生成的签名
                "params": {
                    'departmentIds': [0],    // 非必填，可选部门ID列表（如果ID为0，表示可选管理组权限下所有部门）
                    'tagIds': [],    // 非必填，可选标签ID列表（如果ID为0，表示可选所有标签）
//                    'userIds' : ['zhangsan','lisi'],    // 非必填，可选用户ID列表
                    'mode': 'multi',    // 必填，选择模式，single表示单选，multi表示多选
                    'type': ['user'],    // 必填，选择限制类型，指定department、tag、user中的一个或者多个
                    'selectedDepartmentIds': [],    // 非必填，已选部门ID列表
                    'selectedTagIds': [],    // 非必填，已选标签ID列表
                    'selectedUserIds': []    // 非必填，已选用户ID列表
                }
            }, function (res) {
                if (res.err_msg.indexOf('function_not_exist') > -1) {
                    alert('版本过低请升级');
                } else if (res.err_msg.indexOf('openEnterpriseContact:fail') > -1) {
                    return;
                }

//                var str = JSON.stringify(res);
//                alert(str)

                var result = JSON.parse(res.result);    // 返回字符串，开发者需自行调用JSON.parse解析
                var selectAll = result.selectAll;     // 是否全选（如果是，其余结果不再填充）
                if (!selectAll) {
                    var selectedUserList = result.userList;    // 已选的成员列表
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

    /*
     * windows init function
     * */
    $(function () {
        addDefaultLeader();
        chooseImg.init();
    });

    var $submitBtn = document.getElementById('submitBtn');
    $submitBtn.addEventListener('tap', function () {
        submitBtnClickEvent();
    });

    /*
     * submit all data
     * */
    function submitBtnClickEvent() {
        var loading = new ui.loading("正在提交..");
        var txt = encodeURIComponent($('#txt').val());
        var userIds = [];
        var usernames = [];
        var shopType = $('select[name=shopType]').val();
        var teamName = $('input[name=teamName]').val();
        var itemName = $('input[name=itemName]').val();
        var address = $('input[name=address]').val();

        $('#selUsers').find('li').each(function () {
            var userId = $(this).attr('data-userid');
            var username = $(this).attr('data-username');
            userIds.push(userId);
            usernames.push(username);//first one is leader
        });

        if (teamName == '') {
            mui.toast('请输入团队名称');
            return;
        }
        if (teamName.length > 20) {
            mui.toast('团队名称输入过长咯');
            return;
        }
        if (itemName == '') {
            mui.toast('请输入项目名称');
            return;
        }
        if (itemName.length > 20) {
            mui.toast('项目名称输入过长咯');
            return;
        }
        if (shopType == 0) {
            mui.toast('请输选择工作坊类型');
            return;
        }
        if (address == '') {
            mui.toast('请输入项目地址');
            return;
        }
        if (address.length > 30) {
            mui.toast('项目地址输入过长咯');
            return;
        }
        if ($('#selUsers .leader').length <= 0) {
            mui.toast('请选择项目带头人');
            return;
        }
        if (userIds.length <= 0) {
            mui.toast('请选择团队成员');
            return;
        }
        if (txt == '') {
            mui.toast('请输入成果');
            return;
        }

        loading.show();
        var data;
        chooseImg.upload(function (mediaIds) {
            /* var content = {
             "txt": txt,
             "imgs": mediaIds
             };
             if (mediaIds) {
             var date = new Date();
             var fileDate = date.getFullYear() + '' + (date.getMonth() + 1) + '' + date.getDate();
             for (var n = 0; n < mediaIds.length; n++) {
             var img = {
             "url": homePath + "/uploads/image/material/" + fileDate + "/" + mediaIds[n],
             "info": ""
             };
             content.imgs.push(img);
             }
             }*/

            data = {
                shopType: shopType,
                teamName: teamName,
                itemName: itemName,
                address: address,
                content: txt,
                userIds: userIds,
                mediaIds: mediaIds,
                usernames: usernames
            };
            requestServer(data, homePath + '/wx/union/innovation/declare/work/submit.json', function (res) {
                if (res.data == 'success') {
//                    mui.toast("提交成功");
                    wxgh.redirectTip(homePath, {
                        msg: '您的申请已提交，请静候管理员的审核吧~',
                        url: homePath + '/wx/union/innovation/index.html',
                        title: '申请已提交',
                        urlMsg: '返回首页'
                    });
                    wxgh.end_progress(subBtn);
                } else {
                    wxgh.end_progress(subBtn);
                    mui.toast(res.msg);
                }
                loading.hide();
            });
        });
    }

    /*
     * select member
     * */
    function buildSelUsers(userId, userName) {
        var $li = $('<li data-userid="' + userId + '" data-username="' + userName + '">' +
            '<small>' + userName + '</small>' +
            '<a></a>' +
            '</li>');

        if ($('#selUsers li[data-userid="' + userId + '"]').length == 0) {
            $('#selUsers').append($li);
        }

        $('#selUsers').find('a').each(function () {
            $(this).click(function () {
                $(this).parent().remove();
            });
        });
    }

    function buildSelLeader(userId, userName) {
        if ($('#selUsers .leader').length > 0) {
            $('#selUsers').find(' .leader').remove();
        }
        var style = 'style="background-color:#f4f4f4;border:1px solid lightgrey;"';
        var $li = $('<li class="leader" data-userid="' + userId + '"' +
            ' data-username="' + userName + '" ' + style + '>' +
            '<small>' + userName + '</small>' +
            '<a></a>' +
            '<span>带头人</span>' +
            '</li>');
        $('#selUsers li[data-userid="' + userId + '"]').remove();
        if ($('#selUsers li').length <= 0) {
            $('#selUsers').append($li);
        } else {
            $li.insertBefore($('#selUsers li:eq(0)'));
        }

        $('#selUsers .leader').find('a').click(function () {
            $(this).parent().remove();
        });
    }

    /*
     * add default leader
     * */
    function addDefaultLeader() {
        var leaderName = '${wxgh_user.name}';
        var leaderUserId = '${wxgh_user.userid}';
//        alert(leaderName + ' eee ' + leaderUserId);
        var style = 'style="background-color:#f4f4f4;border:1px solid lightgrey;"';
        var $li = $('<li class="leader" data-userid="' + leaderUserId + '"' +
            ' data-username="' + leaderName + '" ' + style + '>' +
            '<small>' + leaderName + '</small>' +
            '<a></a>' +
            '<span>带头人</span>' +
            '</li>');
        $('#selUsers').append($li);
    }
</script>
<script>
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
    var chooseImg = {
        init: function (options) {
            var defaultOp = {
                clear: false,
                count: 9
            };
            if (options) $.extend(defaultOp, options)

            var chooseBtn = $('#addImgBtn');

            this.localIds = []
            var self = this

            chooseBtn.on('click', function () {
//                alert("123")
                var count = $('.imgList').find('li').length;
                if (count == 9) {
                    mui.toast("最多只能上传9张图片哦！");
                    return false;
                } else {
                    wx.chooseImage({
                        count: 9 - count, // 默认9
                        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有//'original',
                        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                        success: function (res) {
                            var localIds = res.localIds
                            if (localIds && localIds.length > 0) {
                                if (defaultOp.clear) { //是否清除已经有的Item
                                    self.clear_item()
                                }
                                for (var i = 0; i < localIds.length; i++) {
                                    self.create_item(localIds[i])
                                }
                            }
                            $.extend(self.localIds, localIds);
                        }
                    });
                }
            });
            this.chooseParent = $('.imgList');
        },
        create_item: function (imgSrc) {
//            alert(imgSrc);
            var e_item = $('<li><img><a class="imgDelBtn"></a></li>');
            wxgh.get_wx_img(imgSrc, function (src) {
                e_item.find('img').attr('src', src);
            });
            var self = this;
            $(e_item).children('.imgDelBtn').click(function () {
                self.localIds.remove($(e_item).children('img').attr('src'));
                $(this).parent().remove();
            });

            this.chooseParent.append(e_item);
        },
        upload: function (func) {
            if (this.localIds.length > 0) {
                if (!this.isFirst) {
                    mediaIds = [];
                    this.isFirst = true
                }

                var localId = this.localIds.pop();
                wx.uploadImage({
                    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 0, // 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回图片的服务器端ID
                        mediaIds.push(serverId)
                        if (chooseImg.localIds.length > 0) {
                            chooseImg.upload(func)
                        } else {
                            chooseImg.isFirst = false
                            chooseImg.localIds = []
                            if (func) func(mediaIds)
                        }
                    }
                });
            } else {
                mediaIds = [];
                if (func) func(mediaIds)
            }
        }
    };
</script>
</body>

</html>
