<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>审核状态</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        html,
        body {
            background-color: #efeff4;
        }

        .mui-bar ~ .mui-content .mui-fullscreen {
            top: 44px;
            height: auto;
        }

        .mui-pull-top-tips {
            position: absolute;
            top: -20px;
            left: 50%;
            margin-left: -25px;
            width: 40px;
            height: 40px;
            border-radius: 100%;
            z-index: 1;
        }

        .mui-bar ~ .mui-pull-top-tips {
            top: 24px;
        }

        .mui-pull-top-wrapper {
            width: 42px;
            height: 42px;
            display: block;
            text-align: center;
            background-color: #efeff4;
            border: 1px solid #ddd;
            border-radius: 25px;
            background-clip: padding-box;
            box-shadow: 0 4px 10px #bbb;
            overflow: hidden;
        }

        .mui-pull-top-tips.mui-transitioning {
            -webkit-transition-duration: 200ms;
            transition-duration: 200ms;
        }

        .mui-pull-top-tips .mui-pull-loading {
            /*-webkit-backface-visibility: hidden;
            -webkit-transition-duration: 400ms;
            transition-duration: 400ms;*/
            margin: 0;
        }

        .mui-pull-top-wrapper .mui-icon,
        .mui-pull-top-wrapper .mui-spinner {
            margin-top: 7px;
        }

        .mui-pull-top-wrapper .mui-icon.mui-reverse {
            /*-webkit-transform: rotate(180deg) translateZ(0);*/
        }

        .mui-pull-bottom-tips {
            text-align: center;
            background-color: #efeff4;
            font-size: 15px;
            line-height: 40px;
            color: #777;
        }

        .mui-pull-top-canvas {
            overflow: hidden;
            background-color: #fafafa;
            border-radius: 40px;
            box-shadow: 0 4px 10px #bbb;
            width: 40px;
            height: 40px;
            margin: 0 auto;
        }

        .mui-pull-top-canvas canvas {
            width: 40px;
        }

        .mui-slider-indicator.mui-segmented-control {
            background-color: #efeff4;
        }

        .mui-control-item.mui-active .mui-badge {
            color: #fff;
            background-color: #007aff
        }
    </style>
</head>

<body>

<!--
<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">审核状态</h1>
</header>-->

<div class="mui-content">
    <div id="applySlider" class="mui-slider mui-fullscreen">
        <div class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
            <div class="mui-scroll">
                <%--<a class="mui-control-item mui-active" href="#item_fraternity">--%>
                <%--互助会入会--%>
                <%--<span class="mui-badge">0</span>--%>
                <%--</a>--%>
                <a class="mui-control-item mui-active" href="#item_activities">
                    活动申办
                    <span class="mui-badge">${activities}</span>
                </a>
                <a class="mui-control-item" href="#item_groups">
                    兴趣协会
                    <span class="mui-badge">${groups}</span>
                </a>
               <%--<a class="mui-control-item" href="#item_bigact">--%>
                    <%--大型活动--%>
                    <%--<span class="mui-badge">${bigacts}</span>--%>
               <%--</a>--%>
                <a class="mui-control-item" href="#item_disease">
                    互助资金
                    <span class="mui-badge">${disease}</span>
                </a>
                <a class="mui-control-item" href="#item_four">
                    四小需求
                    <span class="mui-badge">${four}</span>
                </a>
                <%--<a class="mui-control-item" href="#item_honor">--%>
                    <%--个人荣誉--%>
                    <%--<span class="mui-badge">${honor}</span>--%>
                <%--</a>--%>
            </div>
        </div>

        <div class="mui-slider-group">
            <%--<div id="item_fraternity" class="mui-slider-item mui-control-content mui-active">
                <div class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading mui-content-padded">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>--%>
            <div id="item_activities" class="mui-slider-item mui-control-content">
                <div class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading mui-content-padded">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="item_groups" class="mui-slider-item mui-control-content">
                <div class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading mui-content-padded">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="item_bigact" class="mui-slider-item mui-control-content">
                <div class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading mui-content-padded">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="item_disease" class="mui-slider-item mui-control-content">
                <div class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading mui-content-padded">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="item_four" class="mui-slider-item mui-control-content">
                <div class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <div class="mui-loading mui-content-padded">
                            <div class="mui-spinner">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--<div id="item_honor" class="mui-slider-item mui-control-content">--%>
                <%--<div class="mui-scroll-wrapper">--%>
                    <%--<div class="mui-scroll">--%>
                        <%--<div class="mui-loading mui-content-padded">--%>
                            <%--<div class="mui-spinner">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.pullToRefresh.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.pullToRefresh.material.js"></script>
<script type="text/javascript">

    mui.init()

    var homePath = '${home}'

    var page = {
        init: function () {
            page.numb = 0;

            this.request("item_activities")

            var applyslider = wxgh.getElement('applySlider')

            var deceleration = mui.os.ios ? 0.003 : 0.0009;
            mui('.mui-scroll-wrapper').scroll({
                bounce: false,
                indicators: true, //是否显示滚动条
                deceleration: deceleration
            });

            applyslider.addEventListener('slide', function (e) {
                var numb = e.detail.slideNumber

                page.numb = numb

                var slideItem = wxgh.queryAll('.mui-slider-group .mui-slider-item')[numb]
                var id = slideItem.getAttribute('id')

                if (slideItem.querySelector('.mui-loading')) page.request(id)
            })
            this.applyslider = applyslider
        },
        request: function (type) {
            var thtype = type.replace('item_', '')
            var url = homePath + '/wx/pub/apply/get.json?action=&type=' + thtype
            mui.getJSON(url, function (res) {
                page.init_page(res.data, thtype)
            })
        },
        init_page: function (data, id) {

            var sliderCnt = wxgh.query('#item_' + id + ' .mui-scroll')
            sliderCnt.innerHTML = ''

            if (data && data.length > 0) {
                var e_ul = document.createElement("ul")
                e_ul.className = 'mui-table-view'
                for (var i = 0; i < data.length; i++) {
                    e_ul.appendChild(this.__create_item(data[i], id))
                }

                page.applyslider.querySelectorAll('.mui-scroll .mui-control-item')[page.numb].querySelector('.mui-badge').innerText = data.length

                sliderCnt.appendChild(e_ul)
            } else {
                sliderCnt.innerHTML = '<div class="mui-content-padded mui-text-center">暂无申请记录哦</div>'
            }
        },
        __create_item: function (r, type) {
            var e_li = document.createElement('li')
            e_li.className = 'mui-table-view-cell mui-media'

            var e_a = document.createElement('a');
            e_a.href = homePath + '/wx/pub/apply/info.html?type=' + type + '&id=' + r.id
//            if (type != 'groups') {
//                e_a.href = homePath + '/apply/info.html?type=' + type + '&id=' + r.id
//            } else {
//                e_a.href = 'javascript:;'
//            }

            var e_body = document.createElement('div')
            e_body.className = 'mui-media-body mui-ellipsis'
            e_body.innerHTML = (r.title ? r.title : '') + '<p class="mui-ellipsis-2">' + r.content + '</p>' +
                    '<div><small class="ui-text-info">' + r.applyTime + '</small><small class="mui-pull-right ui-text-secondary">' + r.status + '</small></div>'

            e_a.appendChild(e_body)
            e_li.appendChild(e_a)

            return e_li
        }
    }

    window.onload = page.init()

</script>
</body>

</html>
