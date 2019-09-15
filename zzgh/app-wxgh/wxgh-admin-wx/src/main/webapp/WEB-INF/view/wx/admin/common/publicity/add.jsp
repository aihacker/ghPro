<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/29
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title></title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

    <style>
        .ui-input-lable {
            padding: 4px 10px;
            display: block;
        }

        #noticeForm textarea {
            padding: 2px 10px 6px 10px;
        }

        #noticeForm .mui-input-group {
            margin: 8px 0;
        }

        .ui-switch-lable {
            display: inline-block;
            min-height: 40px;
            line-height: 40px;
            padding: 0 10px;
        }

        .mui-input-group .mui-switch {
            margin-top: 5px;
            margin-right: 10px;
        }

        .ui-btn {
            padding: 8px;
        }
    </style>
</head>

<body>
<!--
<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">发布公告</h1>
</header>
-->

<div class="mui-content">

    <div id="noticeHead" class="mui-segmented-control mui-segmented-control-inverted">
        <a data-type="1" class="mui-control-item mui-active">
            链接
        </a>
        <a data-type="2" class="mui-control-item">
            图文
        </a>
    </div>

    <div class="mui-content-padded">
        <form id="noticeForm">
            <div class="mui-input-group">
                <label class="ui-input-lable">公告标题</label>
                <textarea name="name" rows="2" placeholder=""></textarea>
            </div>
            <div class="mui-input-group" id="url">
                <label class="ui-input-lable">公告链接
                    <small class="ui-text-info">（请确认链接可以访问哦）</small>
                </label>
                <textarea name="url" rows="2" placeholder=""></textarea>
            </div>

            <div class="mui-input-group" id="content">
                <label class="ui-input-lable">公告内容
                </label>
                <textarea name="content" maxlength="10000" placeholder=""></textarea>
            </div>

            <div class="mui-input-group">
                <label class="ui-switch-lable">是否置顶</label>

                <div class="mui-switch mui-pull-right">
                    <div class="mui-switch-handle"></div>
                </div>
                <input type="hidden" name="top" value="0"/>
            </div>

            <div class="mui-input-group">
                <label class="ui-switch-lable">是否推送</label>

                <div class="mui-switch mui-active mui-pull-right">
                    <div class="mui-switch-handle"></div>
                </div>
                <input type="hidden" name="push" value="1"/>
            </div>

            <div class="mui-row ui-choose-img ui-margin-top-15">
                <div class="" id="chooseBtn">
                    <%--<img src="${home}/image/default/icon_add1.png"/>--%>
                </div>
            </div>
            <button id="addBtn" type="button" class="mui-btn mui-btn-success mui-btn-block ui-btn">立即发布</button>
        </form>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<script type="text/javascript">
    mui.init()

    wxgh.wxInit('${weixin}')

    var homePath = '${home}'

    var page = {
        init: function () {
            var form = wxgh.getElement('noticeForm')
            this.form = form;


            var $upload = wxgh.imagechoose($('#chooseBtn'), {count: 1})

            wxgh.getElement('addBtn').addEventListener('tap', function () {
                console.log("点击后的")
                var info = wxgh.serialize(page.form)
                var verif_res = page.verify(info)
                if (verif_res) {
                    mui.toast(verif_res)
                    return;
                }

                if (!page.loading) {
                    page.loading = new ui.loading('发布中...');
                }
                page.loading.show();

                $upload.upload(function (mediaIds) {
                    if (mediaIds && mediaIds.length > 0) {
                        info['picture'] = mediaIds.toString();
                        page.request(info);
                    } else {
                        if(type != 'url')
                            mui.confirm('您是否不使用图片呢？', '系统提示', ['否', '是'], function (e) {
                                if (e.index == 1) {
                                    page.request(info);
                                } else {
                                    page.loading.hide();
                                }
                            })
                        else
                            page.request(info);
                        console.log("结束")
                    }
                })
            })

            mui('#noticeForm').on('toggle', '.mui-switch', function (e) {
                var isActive = e.detail.isActive;
                var $input = this.nextElementSibling;
                $input.value = isActive ? 1 : 0;
            })

            wxgh.autoTextarea($("textarea[name=content]"))

            inity(1);
            mui('#noticeHead').on('tap', 'a.mui-control-item', function () {
                inity(this.getAttribute('data-type'));
            })

            function inity(txt) {
                if (txt == 1) {
                    type = 'url';
                    $(".ui-choose-img").hide();
                    $("#content").hide();
                    $("#url").show();
                } else if (txt == 2) {
                    type = 'txtimg';
                    $(".ui-choose-img").show();
                    $("#content").show();
                    $("#url").hide();
                }
            }

        },
        request: function (info) {
            var url = homePath + '/wx/admin/common/publicity/add1.json';

            var cnt = {};
            cnt['url'] = info['url']
            if(type == 'url')
                info['content'] = info['url'];
            info['type'] = type

            mui.getJSON(url, info, function (res) {
                page.loading.hide();
                ui.showToast('发布成功', function () {
                    mui.back();
                })
            })

        },
        verify: function (info) {
            if (!info['name']) {
                return '公告标题不能为空哦';
            }
            if (!info['name'].length > 200) {
                return '公告标题不能超过200字符哦';
            }
            if(type == 'url'){
                if (!info['url']) {
                    return '链接地址不能为空哦';
                }
                if ( !wxgh.checkUrlV2(info['url']) ) {
                    return '链接地址不合法哦';
                }
            }else{

            }
        }
    }

    window.onload = page.init();

</script>
</body>

</html>
