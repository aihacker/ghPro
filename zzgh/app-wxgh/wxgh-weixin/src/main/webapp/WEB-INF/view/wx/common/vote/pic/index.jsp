<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/24
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=UTF-8" />
    <title>发布投票</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <style>
        .mui-input-row label {
            width: 22%;
        }

        .mui-input-row label ~ input {
            width: 78%;
        }

        .mui-input-row {
            background-color: #fff;
            border-bottom: 1px solid whitesmoke;
        }

        .ui-vote-option-close {
            position: absolute;
            right: 4px;
            top: 6px;
        }

        div.select-effert-time {
            width: 80%;
            margin: auto !important;
            background: white;
        }

        div.select-start-time {
            width: 80%;
            margin: auto !important;
            background: white;
        }

        .mui-col-xs-4 {
            width: 20%;
        }

    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

<%--<h1 class="mui-title">发布投票</h1>--%>
<%--<a id="addVoteBtn" class="mui-btn mui-btn-link mui-pull-right">发布</a>--%>
<%--</header>--%>
<div class="ui-fixed-bottom">
    <button type="button" id="addVoteBtn" class="mui-btn mui-btn-primary">发布</button>
</div>

<div class="mui-content">

    <div class="mui-content-padded">
        <textarea name="voteTheme" rows="3" placeholder="投票主题（2-80字哦）"></textarea>
    </div>

    <div class="mui-text-center select-start-time">
        <input name="seeTime" type="hidden" id="timeInput2"/>
        <span id="selectTime2" class="input-row-span mui-text-info" style="margin-right: 10px;">选择投票开始时间</span>
    </div>

    <div class="mui-text-center select-effert-time">
        <input name="seeTime" type="hidden" id="timeInput"/>
        <span id="selectTime" class="input-row-span mui-text-info" style="margin-right: 10px;">选择投票结束时间</span>
    </div>

    <%--<div class="mui-content-padded">--%>
        <%--<ul id="mainUl" class="mui-table-view no">--%>
            <%--<li id="toUserLi" class="mui-table-view-cell">--%>
                <%--<a class="ui-flex mui-navigate-right">--%>
                    <%--投票范围--%>
                    <%--<input type="hidden" name="toUserid" id="Userids">--%>
                    <%--<small class="ui-right">请选择</small>--%>
                <%--</a>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>

    <div id="optionList" class="mui-content-padded">
        <div class="mui-input-row">
            <div class="mui-text-center margin-top-10">图片选项1</div>
            <div class="mui-row ui-choose-img ui-margin-top-20">
                <div class="voteImage" id="chooseBtn1" >
                </div>
            </div>
            <span class="mui-icon mui-icon-close ui-text-secondary ui-vote-option-close mui-hidden"></span>
            <label>简介</label>
            <input type="text" placeholder="1">
            <span class="mui-icon mui-icon-close ui-text-secondary ui-vote-option-close mui-hidden"></span>
        </div>
        <div class="mui-input-row">
            <div class="mui-text-center margin-top-10">图片选项2</div>
            <div class="mui-row ui-choose-img ui-margin-top-20">
                <div class="voteImage" id="chooseBtn2" >
                </div>
            </div>
            <span class="mui-icon mui-icon-close ui-text-secondary ui-vote-option-close mui-hidden"></span>
            <label>简介</label>
            <input type="text" placeholder="2">
            <span class="mui-icon mui-icon-close ui-text-secondary ui-vote-option-close mui-hidden"></span>
        </div>
        <div class="mui-content-padded mui-text-center">
            <button id="addOption" type="button" class="mui-btn"><i class="mui-icon mui-icon-plus"></i> 添加选项</button>
        </div>
    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/webuploader/js/webuploader.js"></script>
<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script type="text/javascript">
    $(function () {
        wxgh.wxInit('${weixin}');

        <%--var $toUserid = $('input[name=toUserid]');--%>

        <%--$('#toUserLi').on('tap', function () {--%>
            <%--wxgh.wxContactOpen('${wx_contact}', function (all, users) {--%>
                <%--if (all == true) {--%>
                    <%--ui.alert('不支持全选！');--%>
                    <%--return;--%>
                <%--}--%>
                <%--var ids="";--%>
                <%--var names="";--%>
                <%--for (var i=0; i<users.length; i++){--%>
                    <%--ids += users[i].id+",";--%>
                    <%--names += users[i].name+",";--%>
                <%--}--%>
                <%--$toUserid.val(ids.substring(0,ids.length-1));--%>
                <%--$toUserid.next().text(names.substring(0,names.length-1));--%>
            <%--})--%>
        <%--})--%>
    })
    mui.init()

    var homePath = '${home}';
    var userId = '${wxgh_user.userid}';
    var userName = '${wxgh_user.name}';

    //添加选项按钮
    var addOption = {
        init: function () {
            this.self = wxgh.getElement("addOption");
            this.self.addEventListener("tap", addOption.click);
            this.parentList = wxgh.getElement("optionList");
            this.numb = 2;

            mui(this.parentList).on('tap', '.ui-vote-option-close', function () {
                addOption.event_close(this)
            })
        },
        click: function (e) {
            addOption.numb++;

            addOption.parentList.insertBefore(addOption.createItem(addOption.numb), this.parentNode);

            var choose = "#chooseBtn"+addOption.numb
            var upload = wxgh.imagechoose($(choose), options);
            uploads.push(upload)

            var closeEls = addOption.parentList.querySelectorAll('.ui-vote-option-close')
            if (closeEls && closeEls.length > 2) {
                mui.each(closeEls, function (i) {
                    if((i+1)==closeEls.length)
                    {wxgh.show(this)}else{wxgh.hide(this)}
                })
            }

        },
        createItem: function (num) {
            var e_div = document.createElement("div");
            e_div.className = "mui-input-row";

            var e_divl = document.createElement('div')
            e_divl.className = "mui-text-center margin-top-10"
            e_divl.innerText = '图片选项'+num

            var e_div2 = document.createElement('div')
            e_div2.className = "mui-row ui-choose-img ui-margin-top-20"

            var e_div21 = document.createElement('div')
            e_div21.className = "voteImage"
            e_div21.id="chooseBtn"+num

            e_div2.appendChild(e_div21)

            var e_close = document.createElement('span')
            e_close.className = 'mui-icon mui-icon-close ui-text-secondary ui-vote-option-close mui-hidden'
            e_close.addEventListener('tap', function () {
                addOption.event_close(this)
            })

            var e_label = document.createElement('label')
            e_label.innerText = '简介'

            var e_input = document.createElement('input')
            e_input.type = 'text'
            e_input.placeholder = num


            e_div.appendChild(e_divl)
            e_div.appendChild(e_div2)
            e_div.appendChild(e_label)
            e_div.appendChild(e_input)
            e_div.appendChild(e_close)

            return e_div;
        },
        event_close: function (self) {

            uploads.pop()
            var parent = self.parentNode

            addOption.parentList.removeChild(parent)

            var closeEls = addOption.parentList.querySelectorAll('.ui-vote-option-close')

            /*mui.each(closeEls, function (i) {
                // this.previousElementSibling.setAttribute('placeholder', i + 1)
                this.previousElementSibling.previousElementSibling.innerText='图片选项'+(i+1)
            })*/

            if (!closeEls || closeEls.length < 3) {
                mui.each(closeEls, function () {
                    wxgh.hide(this)
                })
            }else {
                mui.each(closeEls, function (i) {
                    if((i+1)<closeEls.length)
                    {wxgh.hide(this)}else{wxgh.show(this)}
                })
            }
            addOption.numb--;
        }
    };

    //投票主题textarea
    var voteTheme = {
        init: function () {
            voteTheme.self = wxgh.query("textarea[name=voteTheme]");
            voteTheme.val = voteTheme.self.value;

            wxgh.onchange(voteTheme.self, function () {
                voteTheme.val = voteTheme.self.value;
            });
        }
    };

    var options = {
        wx: {
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
        },
        clear: false
    }
    var uploads = []


    var upload1 = wxgh.imagechoose($("#chooseBtn1"), options);
    var upload2 = wxgh.imagechoose($("#chooseBtn2"), options);
    uploads.push(upload1)
    uploads.push(upload2)

    //file图片选项
    /*var voteOptions = {
        vals: function () {
            var vals = [];
            mui.each(wxgh.queryAll(".mui-input-row input[type=file]"), function () {
                vals.push(this.id);
            });
            // alert(vals);
            return vals;
        }
    };*/

    //投票选项
    var voteOptions = {
        vals: function () {
            var vals = [];
            mui.each(wxgh.queryAll(".mui-input-row input[type=text]"), function () {
                vals.push(this.value);
            });
            return vals;
        }
    };

    //发布投票
    var addVote = {
        init: function () {
            addVote.self = wxgh.getElement("addVoteBtn");
            addVote.self.addEventListener("tap", function () {

                var verify_res = addVote.verify()
                if (verify_res) {
                    mui.toast(verify_res);
                    return;
                }
                // wxgh.start_progress(addVote.self);

                addVote.loading.show();
                addVote.request();

            });
            addVote.loading = new ui.loading("发布中，请稍候...");
        },
        verify: function () {

            var theme = voteTheme.val;

            var start_time = selectTime2.input.value
            var startDate = new Date(start_time.replace(/-/g, "/"));
            if (!start_time) {
                return "请选择投票开始时间"
            }

            var effective_time = selectTime.input.value
            var endDate = new Date(effective_time.replace(/-/g, "/"));
            if (!effective_time) {
                return "请选择投票结束时间"
            }
            var nowDate = new Date();
            if (startDate <= nowDate) {
                return "投票开始时间不能小于或等于当前时间哦";
            }

            if (endDate <= nowDate) {
                return "投票结束时间不能小于或等于当前时间哦";
            }

            if (endDate <= startDate) {
                return "投票结束时间不能小于或等于投票开始时间哦";
            }

            if (theme.trim().length < 2 || theme.trim().length > 80) {
                return "投票主题应在2~80字符之间哦"
            }

            var options = voteOptions.vals();
            for (var i = 0; i < options.length; i++) {
                if (options[i].trim().length <= 0) {
                    return "简介" + (i + 1) + "内容不能为空哦"
                }
            }

        },
        //请求服务器
        request: function () {
            var picIds = []

            // console.log(uploads)

            //每个图片逐个异步上传，再异步上传服务器
            function post(n) {
                if(n<uploads.length){
                    uploads[n].upload(function (mediaIds) {
                        var picId = mediaIds.toString()
                        // alert(picId)
                        if (picId) {
                            picIds.push(picId)
                        } else {
                            addVote.loading.hide()
                            alert('上传图片失败，需关闭所有图片项重新选择图片，并确保所有选项添加图片')
                            return ;
                        }

                        if((n+1)==uploads.length){

                            var url = homePath + "/wx/common/vote/pic/add.json";
                            var info = {
                                //                action: "add",
                                theme: voteTheme.val,
                                userid: userId,
                                userName: userName,
                                options: voteOptions.vals(),
                                effectiveTime: selectTime.input.value,
                                startTime: selectTime2.input.value,
                                imageIds:  picIds.toString(),
//                                userids: $('#Userids').val()
                            };
                            console.log(info.options)

//                            $.ajaxFileUpload({
//                                url: url,
//                                secureuri: false,
//                                data: info,
//                                type: 'post',
////                                 fileElementId: voteOptions.vals(),
//                                // fileElementId: ['file1','file2'],
//                                success: function (data) {
//                                    wxgh.clearForm(wxgh.query('.mui-content'))
//                                    selectTime.self.innerText = '选择投票结束时间'
//
////                                    wxgh.redirectTip(homePath, {
////                                        msg: '投票发布成功，请耐心等候管理员审核通过',
////                                        url: homePath + "/wx/common/vote/index.html",
////                                        title: '发布成功',
////                                        urlMsg: '返回首页'
////                                    })
//                                }
//                            });
                            mui.post(url, info, function (res) {
                                addVote.loading.hide();
                                wxgh.end_progress(addVote.self);

                                if (res.ok) {

                                    wxgh.clearForm(wxgh.query('.mui-content'))
                                    selectTime.self.innerText = '选择投票结束时间'

                                    wxgh.redirectTip(homePath, {
                                        msg: '投票发布成功，请耐心等候管理员审核通过',
                                        url: homePath + "/wx/common/vote/index.html",
                                        title: '发布成功',
                                        urlMsg: '返回首页'
                                    })

                                } else {
                                    mui.toast(res.msg);
                                }
                            }, 'json');
                        }else {
                            post(n + 1)
                        }
                    });
                }
            }
            post(0)

        }
    };

    //时间选择  （结束时间）
    var selectTime = {
        init: function () {
            var self = wxgh.getElement("selectTime");
            self.addEventListener("tap", selectTime.click);

            selectTime.input = wxgh.getElement("timeInput");
            selectTime.self = self;

            var nowDate = new Date();
            selectTime.picker = new mui.DtPicker({
                type: 'datetime',
                beginDate: nowDate,
                endYear: nowDate.getFullYear() + 1,
                value: nowDate.format('yyyy-MM-dd HH:mm')
            });
        },
        click: function (e) {
            selectTime.picker.show(function (rs) {
                selectTime.input.value = rs.text;
                //alert(" rs.text   =  "+   rs.text);
                selectTime.self.innerText = rs.text;


            });
        }
    };

    //时间选择  （开始时间）
    var selectTime2 = {
        init: function () {
            var self = wxgh.getElement("selectTime2");
            self.addEventListener("tap", selectTime2.click);

            selectTime2.input = wxgh.getElement("timeInput2");
            selectTime2.self = self;

            var nowDate = new Date();
            selectTime2.picker = new mui.DtPicker({
                type: 'datetime',
                beginDate: nowDate,
                endYear: nowDate.getFullYear() + 1,
                value: nowDate.format('yyyy-MM-dd HH:mm')
            });
        },
        click: function (e) {
            selectTime.picker.show(function (rs) {
                selectTime2.input.value = rs.text;
                //alert(" rs.text   =  "+   rs.text);
                selectTime2.self.innerText = rs.text

            });
        }
    };

    selectTime.init();
    selectTime2.init();
    addOption.init();
    voteTheme.init();
    addVote.init();
</script>
</body>

</html>
