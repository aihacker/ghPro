<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/20
  Time: 16:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>积分兑换</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .mui-content {
            background-color: #fff;
        }

        .mui-bar a.mui-icon-left-nav {
            color: #fff;
        }

        .ui-bg {
            position: relative;
        }

        .ui-bg img {
            width: 100%;
        }

        .ui-bg .ui-score-total {
            font-family: "微软雅黑";
            color: #fff;
            font-size: 28px;
            width: 100%;
            height: 100%;
            position: absolute;
            left: 0;
            top: 0;
            text-align: center;
            top: 50%;
            margin-top: -25px;
        }

        .ui-img-div {
            height: 150px;
            border: 1px solid #ccc;
        }

        .ui-item {
            padding: 15px;
            text-align: center;
        }

        .ui-score-item {
            padding-top: 2px;
            padding-bottom: 4px;
            word-wrap: break-word;
        }

        .ui-score-item span {
            color: #1a75d0;
            font-size: 18px;
        }

        .ui-score-item s {
            color: gray;
            font-size: 14px;
        }

        .ui-item button.mui-btn:active {
            color: #1e80e2;
            background-color: #ddd;
        }

        .ui-item button {
            border: 1px solid #1a75d0;
            color: #1a75d0;
        }

        .ui-item button.mui-disabled {
            border: 1px solid #ccc;
            color: #ccc;
        }

        .ui-item button.mui-disabled:active {
            color: ghostwhite;
            background-color: #ccc;
        }

        .ui-score-name {
            padding-top: 2px;
            text-align: left;
        }

        .ui-tip {
            background-color: #dff0d8;
            border: 1px solid #d6e9c6;
            border-radius: 4px;
            margin: 5px 15px;
            padding: 4px 8px;
            color: #3c763d;
            font-size: 13px;
        }

        #refreshContainer {
            top: 270px;
        }

        .ui-good-item {
            width: 50%;
            display: inline-block;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-bg">
        <img src="${home}/weixin/image/score/score_bg.png"/>
        <div class="ui-score-total">${sum}</div>
    </div>

    <div class="ui-tip">
        系统提示：<br/>
        &nbsp;&nbsp;1.以下奖品图片，仅供参考，请以实物为准！<br/>
        &nbsp;&nbsp;2.以下物品只能使用工会个人积分进行兑换（不包含系统赠送积分）;<br/>
        &nbsp;&nbsp;3.兑换时间：长期有效
    </div>

    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll" style="background-color: #fff;">
            <div id="goodsList">
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <div class="ui-good-item">
        <div class="ui-item">
            <div class="ui-img-div"><img src="{{=it.path}}"/></div>
            <div class="ui-score-name">{{=it.name}}</div>
            <div class="ui-score-item">
                <span><b>{{=it.points}}积分</b></span> &nbsp;<!--<em>{{=it.info}}</em>--></div>
            <button type="button" class="ui-duihuan mui-btn">立即兑换</button>
        </div>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var sumScore = '${sum}';
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/good_list.json',
            ispage: true,
            isBind: true,
            dataEl: '#goodsList',
            tplId: '#itemTpl',
            emptyHtml: '<div class="ui-empty">暂无可兑换商品</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                mui.toast('加载失败...');
            }
        })

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d);
            var $item = refresh.getItem(d)

            var $btn = $item.find('button.ui-duihuan')
            if (sumScore < d.points) {
                $btn.addClass('mui-disabled')
            }

            $item.on('tap', 'div.ui-img-div', function () {
                window.location.href = "show.html?id="+d.id;
            })

            $item.on('tap', 'button.ui-duihuan', function () {
                ui.confirm('是否兑换？', function () {
                    var id = d.id
                    wxgh.request.post('api/exchange.json', {goodsId: id}, function (res) {
                        ui.showToast('兑换成功~', function () {
                            window.location.reload(true);
                        });

                    })
                });
            })
            return $item[0];
        }
    })
</script>
</body>
</html>