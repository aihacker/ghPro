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
    <title>民主评议</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            max-width: 65px;
            width: 65px;
            height: 68px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <%--<div id="segmentedControl"--%>
         <%--class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">--%>
        <%--<a data-status="0" class="mui-control-item " id="0">意见征集</a>--%>
        <%--<a data-status="1" class="mui-control-item mui-active" id="1">问卷调查</a>--%>
    <%--</div>--%>
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
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
                <%--<p style="text-overflow:ellipsis ">{{=it.name}}</p>--%>
                <%--{{=it.title}}--%>
                <%--<p class="mui-ellipsis">{{=it.info}}</p>--%>
                <%--<p>--%>
                <%--<small class="addTime">{{=it.addTime}}</small>--%>
                <%--<small class="addTime">{{=it.time}}</small>--%>
                <%--</p>--%>
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
            url: 'get_list.json',
            data:{status:1},
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
                d.groupName = name(d.type);
                d['time'] = ""
            }
//            d['content'] = '';
            d['path'] = wxgh.get_thumb(d, 'activities.png');
            var $item = refresh.getItem(d)

            $item.on("tap",function () {
                mui.openWindow('${home}/wx/manage/judge/show.html?id=' + d.id);
            })

            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var status = $(this).data('status')
            if(status == 0)
                wxgh.openUrl("${home}/wx/party/opinion/index.html")
            else
                refresh.refresh({status: status})
        })

        function name(names){
            var temp = ""
            switch (names){
                case 1:
                    temp = "中央精神"
                    break;
                case 2:
                    temp = "主题教育";
                    break
                case 3:
                    temp = "支部简讯";
                    break
                case 4:
                    temp = "企业党建";
                    break
                case 5:
                    temp = "党员学习";
                    break
                case 6:
                    temp = "党史理论";
                    break
            }
            return temp
        }
    })
</script>
</body>
</html>

