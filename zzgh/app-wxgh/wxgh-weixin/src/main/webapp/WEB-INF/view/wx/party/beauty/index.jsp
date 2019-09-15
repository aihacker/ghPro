<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/28
  Time: 9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作品集</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .ui-item-info,
        .mui-content {
            background-color: #fff;
        }

        .item {
            float: left;
            background-color: #fff;
            border: 1px solid #ddd;
            padding: 8px 4px;
            border-radius: 4px;
        }

        .item img {
            width: 100%;
        }

        .ui-item-info {
            padding: 0 5px;
        }

        .ui-item-info .ui-name {
            font-size: 15px;
            font-weight: 600;
        }

        #container {
            background-color: #fff;
        }

        #container p {
            margin: 0;
            color: #333;
        }

        #refreshContainer {
            top: 48px;
        }

        #segmentedControl {
            width: 70%;
            margin: 10px auto 8px auto;
            text-align: center;
        }

        #segmentedControl .mui-control-item {
            line-height: 32px;
        }

        .ui-add-btn {
            position: fixed;
            right: 20px;
            bottom: 20px;
            z-index: 2;
        }

        .ui-add-btn img {
            width: 40px;
            height: 40px;
        }

        .ui-img-div {
            height: 160px;
        }

        #container span.fa-stack {
            position: absolute;
            left: 50%;
            margin-left: -18px;
            top: 45px;
            color: #fff;
        }

        #container span.fa-stack .fa-play {
            position: relative;
            right: 4px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl" class="mui-segmented-control">
        <a data-type="1" class="mui-control-item mui-active">
            图片
        </a>
        <a data-type="2" class="mui-control-item">
            视频
        </a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div id="container">
            </div>
        </div>
    </div>

    <div class="ui-add-btn">
        <img src="${home}/image/common/addArt.png"/>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<script src="${home}/comm/js/jquery.masonry.min.js"></script>
<script src="${home}/comm/js/refresh.js"></script>
<script>
    $(function () {
        var $container = $('#container');
        $container.imagesLoaded(function () {
            resize()
            $container.masonry({
                itemSelector: '.item',
            })
        })

        var mtype = 1

        $(window).on('resize', resize())

        var info = {action: 'list', type: 1}
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/party/beauty/list.json',
            data: info,
            dataEl: '#container',
            ispage: true,
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
            }
        })

        $('.ui-add-btn').on('tap', function () {
            mui.openWindow(homePath + '/wx/party/beauty/add/index.html')
        })

        function bindfn(d) {
            var works = d.works
            if (works && works.length > 0) {
                for (var i in works) {
                    var w = works[i]
                    var img = homePath + w.previewImage

                    var $item
                    if (w.type == 1) {
                        $item = $('<div class="item">' +
                            '<img src="' + (w.type == 1 ? img : '') + '"/>' +
                            '<div class="ui-item-info">' +
                            '<p class="ui-name">' + w.name + '</p>' +
                            '<p class="mui-ellipsis-2">' + w.remark + '</p>' +
                            '</div></div>')
                    } else {
                        $item = $('<div class="mui-card"><div class="mui-card-content">' +
                            '<div class="ui-img-div">' +
                            '<img src="' + img + '" />' +
                            '</div><span class="fa-stack fa-lg">' +
                            '<i class="fa fa-circle fa-stack-2x ui-text-white"></i>' +
                            '<i class="fa fa-play fa-stack-1x ui-text-info"></i>' +
                            '</span><div class="ui-item-info">' +
                            '<p class="ui-name">' + w.name + '</p>' +
                            '<p class="mui-ellipsis-2">' + w.remark + '</p>' +
                            '</div></div></div>')
                    }
                    $item.data('id', w.id)
                    refresh.addItem($item[0])

                    $item.on('tap', function () {
                        var id = $(this).data('id')
                        mui.openWindow(homePath + '/wx/party/beauty/show/index.html?id=' + id)
                    })
                }

                if (mtype == 1) {
                    resize()
                    $container.imagesLoaded(function () {
                        $container.masonry('reload')
                    })
                } else {
                    $container.css('height', 'auto')
                }
            } else {
                refresh.addItem('<div class="mui-content-padded mui-text-center ui-text-info">暂无作品哦</div>')
            }
        }

        $('#segmentedControl').on('tap', 'a', function () {
            if (!$(this).hasClass('mui-active')) {
                var type = $(this).data('type')
                refresh.refresh({type: type})
                mtype = type
            }
        })

        function resize() {
            var width = $(window).outerWidth()
            var w = (width - 20 - 20) / 2
            $('#container .item').css('width', w).css('margin', 5)
            $('#container').css('width', w * 2 + 21).css('left', 10)
        }
    })
</script>
</body>
</html>
