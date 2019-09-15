<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/25
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${advice.title}</title>

    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <style>
        .mui-card-header.mui-card-media {
            min-height: 60px;
        }

        .mui-card-header.mui-card-media .mui-media-body {
            margin-top: 5px;
        }

        .mui-card-header.mui-card-media img {
            width: 40px;
            height: 40px;
        }

        .mui-card-content p {
            color: #000;
            font-size: 15px;
            margin-top: 8px;
        }

        .ui-manner-div {
            padding-bottom: 25px;
            border-bottom: 1px solid gainsboro;
            font-size: 13px;
            color: #777;
            position: relative;
        }

        .ui-seenumb-div {
            display: block;
            position: absolute;
            right: 10px;
        }

        .ui-manner-div .ui-seenumb {
            font-size: 16px;
            display: inline-block;
        }

        .ui-panel-title {
            font-size: 15px;
            margin: 10px 8px;
            padding: 4px 0;
            padding-left: 4px;
            border-left: 4px solid dodgerblue;
        }

        .ui-seenumb-div span.fa-heart-o {
            margin-left: 20px;
        }

        .ui-eval-footer {
            padding: 5px 10px 5px 0;
            color: #777;
        }

        .ui-panel-cnt img.mui-pull-left {
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .mui-scroll-wrapper {
            padding-bottom: 40px;
        }

        .mui-bar-tab {
            height: 48px;
        }

        .mui-bar-tab .mui-content-padded {
            margin: 2px 10px;
        }

        .ui-pinlun-img-div {
            display: -webkit-flex;
            display: flex;
            -webkit-flex-wrap: wrap;
            flex-wrap: wrap;
        }

        .ui-pinlun-item {
            padding: 5px;
        }

        .ui-pinlun-item img {
            flex: 1;
            width: 100px;
        }

        .mui-table-view.mui-grid-view .mui-table-view-cell .ui-img-div {
            max-height: 80px;
        }

        .ui-popover-btn {
            position: absolute;
            bottom: 0;
            width: 100%;
            z-index: 999;
            background-color: #fff;
            -webkit-border-radius: 7px;
            border-radius: 7px;
            padding-top: 4px;
        }

        #confirmPopverBtn {
            height: 35px;
            width: 95%;
            line-height: 25px;
            margin-bottom: 4px;
        }

        #commPopover .textare-div {
            margin-bottom: 0px;
            padding-bottom: 0px;
        }

        #commPopover .textare-div textarea {
            margin-bottom: 0px;
        }

        #ingImg {
            position: absolute;
            width: 40px;
            top: -5px;
            right: -5px;
            z-index: 10;
        }
    </style>
</head>

<body>

<div class="mui-scroll-wrapper mui-content">
    <div class="mui-scroll">
        <div class="mui-content-padded">
            <div class="mui-card-header mui-card-media">
                <c:choose>
                    <c:when test="${empty advice.avatar}">
                        <img src="${home}/image/default/user.png"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${advice.avatar}"/>
                    </c:otherwise>
                </c:choose>

                <div class="mui-media-body">
                    ${advice.username}
                    <p>提出于 ${advice.addTimeStr}</p>
                    <span class="mui-badge mui-badge-success mui-pull-right"
                          style="position: absolute;top: 10px;right: 10px;">
                        <c:if test="${advice.type eq 1}">技能</c:if>
                        <c:if test="${advice.type eq 2}">营销</c:if>
                        <c:if test="${advice.type eq 3}">服务</c:if>
                        <c:if test="${advice.type eq 4}">管理</c:if>
                        <c:if test="${advice.type eq 5}">其他</c:if>
                    </span>
                </div>
            </div>
            <div class="mui-card-content mui-content-padded">
                <h4 class="mui-text-center">${advice.title}</h4>
                <c:if test="${advice.status eq 3}">
                    <img id="ingImg" src="${home}/weixin/image/union/innovation/icon/ing.png">
                </c:if>
                <p>
                    ${advice.advice}
                </p>
            </div>

            <div class="ui-manner-div ui-margin-top-15">
                <div class="ui-seenumb-div mui-text-right">
                    <div class="ui-seenumb"><span class="mui-icon mui-icon-eye ui-text-info"></span> ${advice.seeNumb}
                    </div>
                    <div id="zanNumbDiv" class="ui-seenumb"><span
                            class="fa fa-heart-o ${advice.zanIs?'ui-text-danger':'ui-text-info'}"></span>
                        <span>${advice.zanNumb}</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="ui-panel">
            <div class="ui-panel-title">
                全部评论
            </div>
            <div class="ui-panel-cnt">
                <div class="mui-loading">
                    <div class="mui-spinner"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="mui-bar mui-bar-tab">
    <div class="mui-content-padded">
        <button id="nowCommBtn" type="button" class="mui-btn mui-btn-block mui-btn-success">立即评论</button>
    </div>
</div>

<div style="display: flex;align-items: center;width: 100%;height: 100%;height: 400px">
    <div id="commPopover" class="mui-popover" style="height: 300px;">
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <form class="ui-margin-top-15">
                    <div class="textare-div">
                        <label class="mui-text-center">想评论点什么呢：</label>
                    <textarea name="cnt" rows="4"
                              placeholder="说点什么呢？"></textarea>
                    </div>
                    <div class="mui-row ui-choose-img">
                        <div class="" id="chooseBtn">
                            <%--<img src="${home}/union/innovation/image/icon/icon_add1.png"/>--%>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="ui-popover-btn">
            <button id="confirmPopverBtn" type="button" class="weui_btn weui_btn_plain_primary">评论 <span
                    class="fa fa-sp"></span></button>
        </div>
    </div>
</div>


<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script type="text/javascript">

    var homePath = '${home}'
    var resultId = '${advice.id}'

    var $upload_iamge;

    var page = {
        init: function () {

            mui.init()
            mui('.mui-scroll-wrapper').scroll({
                deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
            });
            wxgh.wxInit('${weixin}')
            $upload_iamge = wxgh.imagechoose($("#chooseBtn"), {});
            this.evalDiv = ghmui.query('.ui-panel-cnt');
//            this.evalDiv = document.querySelectorAll(".ui-panel-cnt");

            document.getElementById('zanNumbDiv').addEventListener('tap', function () {
                var e_span = this.querySelector('span.fa-heart-o')
                if (!e_span.classList.contains('ui-text-danger')) {
                    page.request_zan(null, resultId, e_span);
                } else {
                    mui.toast('您已经赞过啦哦');
                }
            });

            var popver = document.getElementById('commPopover')
            popver.style.left = ((window.screen.availWidth - 280) / 2) + 'px';
            popver.style.top = ((window.screen.availHeight - 380) / 2) + 'px';

            page.popverShow = false;

            //立即评论
            document.getElementById('nowCommBtn').addEventListener('tap', function () {
                mui('#commPopover').popover('show')
                if (!page.popverShow) {
                    //$upload_iamge = wxgh.imagechoose($("#chooseBtn"), {});
                    page.popverShow = true;
                }
            });

            //评论按钮
            document.getElementById('confirmPopverBtn').addEventListener('tap', function () {
                var self = this;
                ghmui.disabled(self)
                var infoMsg = popver.querySelector('textarea[name=cnt]').value;
                if (!infoMsg) {
                    mui.toast('评论内容不能为空哦');
                    ghmui.no_disabled(self)
                    return;
                }
                self.innerText = '评论中...'
                $upload_iamge.upload(function (mediaIds) {
                    if (mediaIds) {
                        page.request_pinlun(infoMsg, mediaIds.toString())
                    } else {
                        page.request_pinlun(infoMsg, '');
                    }
                    ghmui.no_disabled(self)
                    self.innerText = '评论'
                })
            });

            wxgh.previewImageInit();

            this.get_comments();

            this.popver = popver;
        },
        request_pinlun: function (txt, imgs) {
            var url = homePath + '/wx/union/innovation/advice/show/addcomm.json';
            var info = {
                adviceId: resultId,
                txt: txt,
                imgs: imgs
            }
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    /*wxgh.clearForm(page.popver);
                    $upload_iamge.clearItem();
                    mui('#commPopover').popover('hide')


                    var comUl = page.evalDiv.querySelector('ul.mui-table-view')
                    if (!comUl) {
                        comUl = document.createElement('ul')
                        comUl.className = 'mui-table-view'

                        page.evalDiv.innerHTML = ''
                        page.evalDiv.appendChild(comUl)
                    }

                    var liFirstEl = comUl.childNodes[0]
                    if (liFirstEl) {
                        comUl.innerHTML = '';
                        comUl.insertBefore(page.create_item(res.data), liFirstEl)
                    } else {
                        comUl.appendChild(page.create_item(res.data));
                    }
                    this.get_comments();
//                    window.location.href = '';*/
                    window.location.reload(true);
                }
            })
        },
        request_zan: function (commId, id, el) {
            var url = homePath + '/wx/union/innovation/advice/show/zan.json';
            var info = {
                id: id
            }
            if (commId) info['commId'] = commId;
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    el.classList.remove('ui-text-info')
                    el.classList.add('ui-text-danger')

                    var nextEl = el.nextElementSibling;
                    nextEl.innerText = Number(nextEl.innerText.trim()) + 1;
                } else {
                    mui.toast(res.msg);
                }
            })
        },
        get_comments: function () {
            var url = homePath + '/wx/union/innovation/advice/show/listcom.json?adviceId=' + resultId;

            mui.getJSON(url, function (res) {
                if (res.ok) {
                    var comms = res.data

                    page.evalDiv.innerHTML = ''

                    var e_ul = document.createElement('ul')
                    e_ul.className = 'mui-table-view'
                    if (comms && comms.length > 0) {
                        for (var i = 0; i < comms.length; i++) {
                            e_ul.appendChild(page.create_item(comms[i]))
                        }
                    } else {
                        e_ul.innerHTML = '<li class="mui-table-view-cell"><div class="mui-content-padded mui-text-center">暂无评论哦</div></li>'
                    }

                    page.evalDiv.appendChild(e_ul)
                }
            });
        },
        create_item: function (c) {
            var e_item = document.createElement('li')
            e_item.className = 'mui-table-view-cell mui-media'

            var e_a = document.createElement('a')
            e_a.href = 'javascript:;'

            var cnt = c.cnt.replace(/\n/g, "");
            if (typeof cnt == 'string') {
                cnt = JSON.parse(cnt)
            }

            var e_img = document.createElement('img')
            e_img.className = 'mui-media-object mui--pullleft'
            e_img.setAttribute('data-preview-src', '')
            e_img.setAttribute('data-preview-group', 'act_result_comment_imgs')
            e_img.src = c.avatar

            var e_body = document.createElement('div')
            e_body.className = 'mui-media-body'

            var e_username = document.createElement('span')
            e_username.innerText = c.username

            var e_cnt = document.createElement('p')
            e_cnt.className = 'ui-ellipsis-4'
            e_cnt.innerText = cnt.txt
            // 2017-8 改
//            e_cnt.innerText = c.content;

            e_body.appendChild(e_username)
            e_body.appendChild(e_cnt)

            var imgs = cnt.imgs;
            // 2017-8 改
//            var imgs = c.image_ids;
            if (imgs) {

                imgs = imgs.split(',');
                if (imgs.length > 0) {
                    var e_img_list = document.createElement('div')
                    e_img_list.className = 'ui-pinlun-img-div'

                    for (var i = 0; i < imgs.length; i++) {
                        var e_img_item = document.createElement('div')
                        e_img_item.className = 'ui-pinlun-item'
                        e_img_item.innerHTML = '<img data-preview-src="" data-preview-group="act_pinlun_imgs" src="' + (wxgh.get_image(imgs[i])) + '"/>'

                        e_img_list.appendChild(e_img_item);
                    }
                    e_body.appendChild(e_img_list)
                }
            }

            var e_footer = document.createElement('div')
            e_footer.className = 'ui-eval-footer'
            e_footer.innerHTML = '<small class="ui-text-black">' + c.addTimeStr + '</small>' +
                    '<span class="mui-pull-right"><span class="fa fa-heart ' + (c.zanIs ? 'ui-text-danger' : 'ui-text-info') + '"></span> <span>' + c.zanNumb
                    + '</span></span>'

            e_body.appendChild(e_footer)

            e_a.appendChild(e_img)
            e_a.appendChild(e_body)
            e_item.appendChild(e_a)

            e_footer.querySelector('span.mui-pull-right').addEventListener('tap', function () {
                var e_span = this.querySelector('.fa-heart')
                if (e_span.classList.contains('ui-text-danger')) {
                    mui.toast('对不起，你已经赞过啦哦');
                } else {
                    page.request_zan(c.id, c.adviceId, e_span)
                }
            })

            return e_item;
        }
    }

    window.onload = page.init();
</script>
</body>

</html>
