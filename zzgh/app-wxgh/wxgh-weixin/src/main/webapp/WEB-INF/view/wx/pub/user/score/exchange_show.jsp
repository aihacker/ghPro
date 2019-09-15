<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                <img src="${home}${d.path}">
            </div>

            <p style="min-height: 40px;padding-top:4px;background-color: white;font-size: 18px;color:#000;line-height: 25px;padding-left: 10px;">
                ${d.goodsName}   <span>(${d.score}积分)</span>
            </p>

            <div style="background-color: white;padding: 10px;border-bottom: 1px solid gainsboro;">
                <h4>介绍</h4>
                <p>
                    ${d.info}
                </p>
            </div>

            <div style="background-color: white;padding: 10px;border-bottom: 1px solid gainsboro;">
                <h4>兑换时间</h4>
                <p>
                    <fmt:formatDate value="${d.addTime}" pattern="yyyy年MM月dd日 HH:mm:ss"/>
                </p>
            </div>

            <c:if test="${d.confirm == 1}">
                <div style="background-color: white;padding: 10px;border-bottom: 1px solid gainsboro;">
                    <h4>兑换结果</h4>
                    <p>
                        兑换成功
                    </p>
                </div>
            </c:if>

        </div>
    </div>

    <c:if test="${d.confirm != 1}">
    <div class="ui-fixed-bottom ui-flex">
        <button type="button" class="mui-btn mui-btn-primary ui-confirm-yes">确认兑换</button>
        <button type="button" class="mui-btn mui-btn-danger ui-confirm-no">取消兑换</button>
    </div>
    </c:if>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>

    var _id = '${d.id}'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
    $(function () {

        $(".ui-confirm-yes").on('tap', function () {
            var $self = $(this)
            mui.confirm('是否确认已兑换了该商品？', '是否确认', ['取消', '确认'], function (e) {
                if (e.index == 1) {
                    wxgh.request.post('api/confirm_exchange.json', {
                        id: _id,
                    }, function () {
                        ui.showToast('确认兑换成功')
                        setInterval(function () {
                            window.location.reload(true);
                        }, 2000)
                    })
                }
            })
        })

        $(".ui-confirm-no").on('tap', function () {
            var $self = $(this)
            mui.confirm('是否取消兑换该商品？', '是否确认', ['取消', '确认'], function (e) {
                if (e.index == 1) {
                    wxgh.request.post('api/confirm_cancel.json', {
                        id: _id,
                    }, function () {
                        ui.showToast('取消兑换成功')
                        setInterval(function () {
                            wxgh.openUrl("my_exchange.html")
                        }, 2000)
                    })
                }
            })
        })

    })
</script>
</body>
</html>