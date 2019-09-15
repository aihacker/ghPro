<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/29
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>宣传公告</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link rel="stylesheet" href="${home}/libs/font-awesome-4.7.0/css/font-awesome.min.css"/>
    <style>
        #noticeHead {
            width: 50%;
            margin-left: auto;
            margin-right: auto;
        }

        #refreshContainer {
            /*top: 86px;*/
        }

        #refreshContainer .mui-table-view-cell {
            padding: 8px 10px;
        }

        #refreshContainer .mui-table-view a.ui-popover {
            position: absolute;
            top: 0px;
            right: 0px;
            width: 30px;
            height: 25px;
            text-align: center;
        }

        .mui-table-view a.ui-popover .fa {
            position: absolute;
            top: 0px;
            right: 10px;
            color: #000;
            opacity: .8;
        }

        #popoverCancal {
            width: 80px;
        }

        #popoverCancal li.mui-table-view-cell {
            padding: 8px 12px;
            font-size: 15px;
        }

        .mui-table-view-cell:after {
            left: 5px;
            right: 5px;
        }

        #noticeList .mui-media-body {
            padding-right: 28px;
            color: #000;
        }

        .ui-tuijian-icon {
            width: 20px;
            position: absolute;
            top: 0px;
            left: 0px;
        }

        .ui-add-btn {
            position: fixed;
            right: 20px;
            bottom: 20px;
            z-index: 2;
        }

        .ui-add-btn img {
            width: 50px;
        }

    </style>
</head>

<body>
<%--
<header class="mui-bar mui-bar-nav ui-head">
    <h1 class="mui-title">宣传公告</h1>
    <a id="addNoticeBtn" href="${home}/wx/admin/common/publicity/add.html" class="mui-icon mui-icon-plus mui-pull-right"></a>
</header>
--%>

<div class="mui-content">
    <div id="noticeHead" class="mui-segmented-control mui-segmented-control-inverted">
        <a data-type="1" class="mui-control-item mui-active">
            链接
        </a>
        <a data-type="2" class="mui-control-item">
            图文
        </a>
    </div>

    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view" id="noticeList">
            </ul>
        </div>
    </div>

</div>

<div id="popoverCancal" class="mui-popover">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell ui-attention">
            <a href="#">取消置顶</a>
        </li>
        <li class="mui-table-view-cell ui-del">
            <a style="color: #dd524d;" href="#">删除</a>
        </li>
    </ul>
</div>

<div class="ui-add-btn">
    <img src="${home}/image/common/addArt.png"/>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">

    var homePath = '${home}'
    var type = 'url';

    var refresh = {
        upRefresh: function () {
            if (refresh.isFirst) {
                page.request(true, function () {
                    refresh.endUpRefresh()
                    refresh.isFirst = false
                })
            } else {
                page.request(false, function () {
                    refresh.endUpRefresh()
                })
            }
            refresh.curpage = refresh.curpage + 1;
        },
        //结束上拉刷新
        endUpRefresh: function () {
            var hasData = (refresh.curpage >= refresh.totalPage)
            mui('#refreshContainer').pullRefresh().endPullupToRefresh(hasData)
            if (hasData) {
                mui('#refreshContainer').pullRefresh().disablePullupToRefresh();
            }
        },
        scrollTop: function (time) {
            if (!time) time = 400;
            mui('#refreshContainer').pullRefresh().scrollTo(0, 0, time);
        }
    }

    $('.ui-add-btn').on('tap', function () {
        mui.openWindow(homePath + '/wx/admin/common/publicity/add.html')
    })

    var btnArray = ['否', '是'];
    var popoverEl = wxgh.getElement('popoverCancal');

    var page = {
        init: function () {
            var noticeList = wxgh.getElement('noticeList')

            refresh.curpage = 0
            refresh.isFirst = true

            mui.init({
                pullRefresh: {
                    container: '#refreshContainer', //待刷新区域标识，querySelector能定位的css选择器均可，比如：id、.class等
                    up: {
                        height: 50, //可选.默认50.触发上拉加载拖动距离
                        auto: true, //可选,默认false.自动上拉加载一次
                        contentrefresh: "正在加载...", //可选，正在加载状态时，上拉加载控件上显示的标题内容
                        contentnomore: '没有更多数据了', //可选，请求完毕若没有更多数据时显示的提醒内容；
                        callback: refresh.upRefresh //必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
                    }
                }
            })

            mui('#noticeHead').on('tap', 'a.mui-control-item', function () {
                var txt = this.getAttribute('data-type');
                if (txt == 1) {
                    type = 'url';
                } else if (txt == 2) {
                    type = 'txtimg';
                }
                page.request(true, function () {
//                    refresh.scrollTop();
                })
            })

            mui('#popoverCancal').on('tap', 'li.ui-del', function () {
                var id = popoverEl.getAttribute('data-id')
                var top = popoverEl.getAttribute('data-top')
                if (this.classList.contains('ui-del')) { //删除
                    if (id) {
                        mui.confirm('是否删除', '系统提示', btnArray, function (e) {
                            if (e.index == 1) {
                                page.request_del(id);
                            }
                        })
                    }
                } else { //置顶事件
                    var txt = '';
                    if (top == 1) {
                        txt = '取消置顶'
                    } else {
                        txt = '置顶公告'
                    }
                    mui.confirm('是否' + txt + '？', '系统提示', btnArray, function (e) {
                        if (e.index == 1) {
                            var url = homePath + '/wx/admin/common/publicity/stick.json';
                            var info = {
                                id: id,
                                top: top == 1 ? 0 : 1
                            }
                            mui.getJSON(url, info, function (res) {
                                mui.toast(txt + '成功')
                                window.location.reload(true)
                            })
                        }
                    })
                }
            })

            this.noticeList = noticeList
        },
        request: function (isClear, func) {
            var url = homePath + '/wx/admin/common/publicity/get.json';
            if (isClear) refresh.curpage = 0;

            var info = {
                type: type,
                currentPage: refresh.curpage + 1
            }
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    page.createList(res.data.notices, isClear)
                    refresh.totalPage = res.data.page
                    if (func) func()
                } else {
                    mui.toast(res.msg)
                }
            })
        },
        createList: function (res, isClear) {
            if (isClear) {
                page.noticeList.innerHTML = ''
            }
            if (res && res.length > 0) {
                for (var i = 0; i < res.length; i++) {
                    var e_li = this.create_item(res[i])
                    page.noticeList.appendChild(e_li)
                    page.init_event(e_li)
                }
            } else {
                this.noticeList.innerHTML = '<li class="mui-table-view-cell"><div class="mui-content-padded mui-text-center">暂无公告哦</div></li>'
            }
        },
        create_item: function (n) {

            if(n.type == 'txtimg'){
                var cnt = {url: homePath + '/wx/common/publicity/info.html?id=' + n.id, txt: n.content}
//                var cnt = JSON.parse(n.content);
            }
            else{
                var cnt = {url: n.content, txt: ''}
            }

            var e_li = document.createElement("li");
            e_li.className = 'mui-table-view-cell mui-media'
            e_li.setAttribute('id', n.id + '_li')

            var e_slider_right = document.createElement('div')
            e_slider_right.className = 'mui-slider-right mui-disabled'

            var e_del_btn = document.createElement('a')
            e_del_btn.className = 'mui-btn mui-btn-red ui-delete'
            e_del_btn.innerText = '删除'
            e_del_btn.setAttribute('data-id', n.id);

            e_slider_right.appendChild(e_del_btn)

            var e_handle = document.createElement('div')
            e_handle.className = 'mui-slider-handle'
            e_handle.innerHTML = '<a href="' + cnt.url + '">'
                    + (n.type == 'url' ? '<img class="mui-media-object mui-pull-left" src="' + wxgh.get_image(n.picture) + '">' : '')
                    + '<div class="mui-media-body">' + n.name
                    + '<p class="mui-ellipsis">' + (cnt.txt ? cnt.txt : '') + '</p></div>'
                    + '<a data-id="' + n.id + '" data-top="' + n.top + '" class="ui-popover"><span class="fa fa-caret-down"></span></a>' +
                    (n.top == 1 ? '<img class="ui-tuijian-icon" src="${home}/image/default/tuijian.png" />' : '')
                    + '</a>'

            e_li.appendChild(e_slider_right);
            e_li.appendChild(e_handle);

            return e_li;
        },
        init_event: function (e_li) {
            e_li.querySelector('.ui-popover').addEventListener('tap', function () {

                var id = this.getAttribute('data-id')
                var top = this.getAttribute('data-top')

                popoverEl.setAttribute('data-id', id)
                popoverEl.setAttribute('data-top', top)
                var txt = '置顶'
                if (top == 1) {
                    txt = '取消置顶'
                }
                popoverEl.querySelector('.ui-attention a').innerText = txt;

                mui('#popoverCancal').popover('toggle', this);
            })

            e_li.querySelector('.ui-delete').addEventListener('tap', function () {
                var id = this.getAttribute('data-id')

                mui.confirm('是否删除', '系统提示', btnArray, function (e) {
                    if (e.index == 1) {
                        page.request_del(id);
                    }
                })
            })

            e_li.querySelector('.mui-slider-handle a[href]').addEventListener('tap', function (e) {
                e.preventDefault();
                mui.openWindow(this.getAttribute('href'))
            })
        },
        request_del: function (id) {
            var url = homePath + '/wx/admin/common/publicity/del.json?action=&id=' + id;
            mui.getJSON(url, function (res) {
                mui.toast('删除成功');

                var u_li = wxgh.getElement(id + '_li')
                page.noticeList.removeChild(u_li)
                mui('#popoverCancal').popover('hide', u_li.querySelector('.ui-popover'));
            })
        }
    }

    window.onload = page.init();

</script>
</body>

</html>
