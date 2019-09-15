<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>
<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="mui-media-body">
                {{=it.title}}
                <%--<p class="mui-ellipsis">{{=it.info}}</p>--%>
                <p>
                    <small>{{=it.addTime}}</small>
                </p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">
    var id = '${param.id}'

    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            data: {id: id},
            url: 'api/detail_list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = ui.timeAgo(d.addTime)
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('detail.html?id=' + d.id);
            })
            return $item[0]
        }

    })
</script>
</body>
</html>
