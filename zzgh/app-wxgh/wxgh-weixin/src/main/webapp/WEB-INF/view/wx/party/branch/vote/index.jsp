<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/27
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            max-width: 65px;
            width: 65px;
            height: 68px;
        }
        .mui-control-item{
            height:40px;
            vertical-align: center;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div id="segmentedControl"
             class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
            <a data-status="0" class="mui-control-item mui-active" id="0">支部测评</a>
        </div>
        <div class="mui-scroll">
            <ul class="mui-table-view" id="ul1">
            </ul>
        </div>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="mui-media-body">
                {{=it.name}}
                <span class="mui-navigate-right"></span>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">

    $(function () {
        var status;
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/get_list.json',
            data:{status:0},
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
//            var oUl=document.getElementById('ul1');;
//            var aLi=document.getElementsByTagName('li');
//
//            var arr=[];
//            for(var i=0;i<aLi.length;i++){
//                arr[i]=aLi[i];
//            }
//            arr.sort(function(li1,li2){
//                var n1=parseInt(li1.getAttribute("data-time"));
//                var n2=parseInt(li2.getAttribute("data-time"));
//                return n1-n2;
//            });
//            arr.reverse()
//            for(var i=0;i<arr.length;i++){
//                oUl.appendChild(arr[i]);
//            }
//            d["temp"] = d["addTime"]
            d['addTime'] = ui.timeAgo(d.addTime)
            if (!d.info) {
                d['info'] = $(d.content).text();
            }
            if(d.groupName){
                d.groupName = "来自："+d.groupName;
                d['addTime'] = ""
                d['time'] = new Date(d.startTime).format("yyyy-MM-dd") +"至"+new Date(d.endTime).format("yyyy-MM-dd")
            }else{
//                d.groupName = name(d.type);
                d['time'] = ""
            }
//            d['content'] = '';
            d['path'] = wxgh.get_thumb(d, 'activities.png');
            var $item = refresh.getItem(d)

            $item.on("tap",function () {
                    mui.openWindow('${home}/wx/party/branch/vote/show.html?id=' + d.id);
            })

            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var status = $(this).data('status')
            refresh.refresh({status: status})
        })

//        function name(names){
//            var temp = ""
//            switch (names){
//                case 1:
//                    temp = "中央精神"
//                    break;
//                case 2:
//                    temp = "主题教育";
//                    break
//                case 3:
//                    temp = "支部简讯";
//                    break
//                case 4:
//                    temp = "企业党建";
//                    break
//                case 5:
//                    temp = "党员学习";
//                    break
//                case 6:
//                    temp = "党史理论";
//                    break
//            }
//            return temp
//        }
    })
</script>
</body>
</html>

