<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/28
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${goods.name}详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        html, body {
            height: 100%;
        }

        .mui-content {
            height: 100%;
        }

        .mui-btn-block {
            font-size: 16px;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="mui-scroll-wrapper" style="bottom: 60px;">
        <div class="mui-scroll">

            <div class="ui-img-div" style="height: 200px;">
                <img src="${home}${goods.path}">
            </div>

            <p style="min-height: 40px;padding-top:4px;background-color: white;font-size: 18px;color:#000;line-height: 25px;padding-left: 10px;">
                ${goods.name}   <span>(${goods.points}积分)</span>
            </p>

            <div style="background-color: white;padding: 10px;border-bottom: 1px solid gainsboro;">
                <h4>介绍</h4>
                <p>
                    ${goods.info}
                </p>
            </div>


        </div>
    </div>

    <div class="ui-fixed-bottom">
        <button data-id="${goods.id}" type="button" class="mui-btn mui-btn-primary">我要兑换</button>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>

    var examId = '${param.id}';
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
    $(function () {
        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var $self = $(this)
            var id = $self.data('id')
            ui.confirm('是否兑换？', function () {
                wxgh.request.post('api/exchange.json', {goodsId: id}, function (res) {
                    ui.showToast('兑换成功~', function () {
                        window.location.reload(true);
                    });
                })
            });
        })
    })
</script>
</body>
</html>