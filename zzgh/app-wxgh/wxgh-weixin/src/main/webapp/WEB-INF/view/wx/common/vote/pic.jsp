<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/22
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>图片投票</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .vote-status {
            position: absolute;
            right: 10px;
            top: 10px;
        }

        .mui-table-view-cell.mui-collapse .mui-table-view .mui-table-view-cell {
            padding-left: 54px;
        }

        .mui-table-view-cell > .mui-btn {
            position: static;
            padding: 6px 0;
            top: 0;
            right: 0;
            -webkit-transform: none;
            transform: none;
        }

        .ui-vote-time {
            z-index: 999;
            color: #777;
        }
    </style>
</head>

<body>

<%--<div class="ui-fixed-bottom">--%>
<%--<a href="${home}/wx/common/vote/add/index.html">--%>
<%--<button type="button" class="mui-btn mui-btn-primary">发布投票</button>--%>
<%--</a>--%>
<%--</div>--%>


<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div class="vote-list"></div>
    </div>
</div>


<div class="ui-fixed-btn right">
    <%--<a href="${home}/wx/common/vote/add/index.html">--%>
    <a href="#sheet1">
        <span class="fa fa-plus"  style="line-height: 50px;font-size: 28px;"></span>
    </a>
</div>


<div class="ui-fixed-btn right">
    <a href="${home}/wx/common/vote/pic/index.html"><span class="fa fa-plus"
                                                          style="line-height: 50px;font-size: 28px;"></span></a>
</div>
<!-- 取消菜单 -->
<ul class="mui-table-view">
    <li class="mui-table-view-cell">
        <a href="#sheet1"><b>取消</b></a>
    </li>
</ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/js/refresh.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">

    var homePath = "${home}";
    var erMsg = '${msg}';
    var voteType = "";




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

    var page = {
        createVotes: function (votes) {
            var votes = votes.voteds
            if (votes && votes.length > 0) {
                for (var i = 0; i < votes.length; i++) {
                    refresh.addItem(page.__createItem(votes[i]))
                }
            } else {
                refresh.addItem('<div class="mui-content-padded mui-text-center ui-text-info">暂无投票信息</div>')
            }
        },
        __createItem: function (v) {
            var cardDiv = document.createElement('div')
            cardDiv.className = 'mui-card'

            var cardHead = document.createElement("div")
            cardHead.className = "mui-card-header mui-card-media"
            var carheadHtml = '<img src="' + wxgh.get_avatar(v.headimg) + '" />' +
                '<div class="mui-media-body">' + v.userName + '<p>' + v.createTimeStr + '</p></div>';
            if (v.status == 1) carheadHtml += '<span class="mui-badge mui-badge-primary vote-status">投票中</span>'
            else if (v.status == 2) {
                carheadHtml += '<span class="mui-badge vote-status">已结束</span>'
            } else {
                carheadHtml += '<span class="mui-badge vote-status">未开始</span>'
            }

            carheadHtml += '<small class="ui-vote-time"><span class="fa fa-clock-o"></span> ' + new Date(v.startTime).format('yyyy-MM-dd hh:mm') + '~' + new Date(v.effectiveTime).format('yyyy-MM-dd hh:mm') + '</small>'

            cardHead.innerHTML = carheadHtml

            var cardCnt = document.createElement("div")
            cardCnt.className = 'mui-card-content'

            var e_theme = document.createElement("div")
            e_theme.className = "mui-content-padded"
            e_theme.style.marginBottom = '0px'
            e_theme.innerHTML = v.theme

            var chevronUl = document.createElement("ul")
            chevronUl.className = 'mui-table-view mui-table-view-chevron'

            var chevronLi = document.createElement("li")
            chevronLi.className = 'mui-table-view-cell mui-collapse'

            var e_a = document.createElement("a")
            e_a.className = 'mui-navigate-right'
            e_a.href = 'javascript:;'
            e_a.innerHTML = '选项预览<span class="mui-badge mui-badge-success" id="totalVote' + v.id + '">已投' + v.optionnum + '票</span>'

            chevronLi.appendChild(e_a)

            //图片投票选项
            chevronLi.appendChild(this.__create_option_pic(v))


            chevronUl.appendChild(chevronLi)

            cardCnt.appendChild(e_theme)
            cardCnt.appendChild(chevronUl)

            cardDiv.appendChild(cardHead)
            cardDiv.appendChild(cardCnt)

            return cardDiv
        },

        __create_option_pic: function (v) {
            var e_ul = document.createElement("ul")
            e_ul.className = 'mui-table-view mui-table-view-chevron'

            var options = v.votePicOptions
            for (var i = 0; i < options.length; i++) {
                var o = options[i]
                var e_li = document.createElement("li")
                e_li.className = 'mui-table-view-cell mui-checkbox mui-left'
                e_li.innerHTML = '<input value="' + o.id + '" type="checkbox" name="vote' + v.id + '"/>' +
                    '<span class="mui-badge mui-badge-success">' + o.ticketNum + ' 票</span>' +
                    '<div style="float:left;margin-right:10px">'+
                    '<a href="javascript:;">'+
                    ' <div class="ui-img-div" style="float:left;margin-left:30px">'+
                    '<img data-preview-group="img" data-preview-src="'+homePath +o.path+'" class="mui-media-object" src="'+homePath + o.path+'"/>'+
                    '</div>'+
                    '</a>'+
                    '</div>'
                var e_div = document.createElement("div")
                e_div.innerHTML = '<p>'+o.options+'</p>'
                e_li.appendChild(e_div)
                e_ul.appendChild(e_li)
            }

            var e_footer = document.createElement("li")
            e_footer.className = 'mui-table-view-cell'

            var e_btn = document.createElement("button")
            e_btn.type = 'button'
            e_btn.className = 'mui-btn mui-btn-block mui-btn-primary vote-buttons'
            if (v.userVote > 0) {
                e_btn.classList.add('ui-disabled')
                e_btn.innerText = '你已投票哦'
            } else {
                e_btn.setAttribute('data-id', v.id)
                if (v.status == 2) {
                    e_btn.classList.add('ui-disabled')
                    e_btn.innerText = '投票已结束'
                } else if (v.status == 0) {
                    e_btn.classList.add('ui-disabled')
                    e_btn.innerText = '投票未开始'
                } else {
                    e_btn.innerText = '立即投票'
                }
            }
            new Vote(e_btn).init();
            e_footer.appendChild(e_btn)

            e_ul.appendChild(e_footer)

            return e_ul
        }
    }

    var Vote = function (btn) {
        this.btn = btn;
    }
    Vote.prototype = {
        init: function () {
            var self = this;
            self.btn.addEventListener("tap", function (e) {
                self.click(e);
            });
        },
        click: function (e) {
            var self = this;
            if (self.btn.classList.contains('ui-disabled')) {
                e.preventDefault()
                mui.alert(self.btn.innerText, '投票结果', function () {
                });
                return;
            }
            var voteId = self.btn.getAttribute("data-id");

            var e_totalSpan = wxgh.getElement('totalVote' + voteId)
            var totalNumb = Number(e_totalSpan.innerText.trim().replace('票', '').replace('已投', '').trim())
            mui.each(wxgh.queryAll("input[name=vote" + voteId + "]"), function () {
                if (this.checked) {
                    var optionId = this.value;

                    var voteNumb_e = this.nextElementSibling;

                    var voteNumb = Number(voteNumb_e.innerText.trim().replace("票", "").trim());

                    //显示开始进度条
                    wxgh.start_progress(self.btn);

                    self.request(voteId, optionId, function (res) {
                        if (res.ok) {
                            wxgh.end_progress(self.btn)
                            if (!self.btn.classList.contains('ui-disabled')) {
                                self.btn.classList.add('ui-disabled')
                            }
                            self.btn.innerText = "你已投票哦";
                            voteNumb_e.innerText = (voteNumb + 1) + " 票";
                            e_totalSpan.innerText = '已投' + (totalNumb + 1) + '票'
                            mui.toast('投票成功');
                        } else {
                            wxgh.end_progress(self.btn);
                            mui.toast('投票失败');
                        }
                    });

                    return;
                }
            });
        },
        request: function (voteId, optionId, func) {
            var url = homePath + "/wx/common/vote/votes.json";
            var info = {
//                action: 'vote',
                votedId: voteId,
                optionId: optionId
            }
            mui.getJSON(url, info, function (res) {
                func(res);
            });
        }
    };

    ///////////
    var refresh = refresh('#refreshContainer', {
        url: homePath + '/wx/common/vote/pic_list.json',
//        data: {action: 'list'},
        dataEl: '.vote-list',
        ispage: true,
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
        }
    })



    function bindfn(d) {
        page.createVotes(d)
    }





</script>
</body>

</html>
