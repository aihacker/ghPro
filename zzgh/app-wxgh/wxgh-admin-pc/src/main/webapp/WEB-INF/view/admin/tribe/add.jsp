<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>

<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/23
  Time: 14:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${home}/libs/mui-v3.7.0/css/mui.min.css" rel="stylesheet">
<link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<div clss="row">
    <form class="form-horizontal container-fluid" role="form">
        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">活动主题:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="theme"
                           placeholder="">
                </div>
            </div>

            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">跨域积分:</label>
                <div class="col-sm-8">
                    <input type="number" class="form-control" name="crossPoint"
                           placeholder="">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">开始时间:</label>
                <div class="col-sm-8">
                    <input readonly="readonly" id="start_datetimepicker" type="text" class="form-control"
                           name="startTime"
                           placeholder="">
                </div>
            </div>

            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">结束时间:</label>
                <div class="col-sm-8">
                    <input readonly="readonly" id="end_datetimepicker" type="text" class="form-control" name="endTime"
                           placeholder="">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">发&nbsp;&nbsp;起&nbsp;&nbsp;人:</label>
                <div class="col-sm-8">
                    <input readonly="readonly" type="text" class="form-control search-input" data-type="leader"
                           placeholder="" id="input-show-1" name="leader">
                    <input readonly="readonly" type="hidden" name="leadername">
                </div>
            </div>

            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">发起人部门:</label>
                <div class="col-sm-8">
                    <select style="background-color: #EDEEF2;" class="form-control" name="deptid">
                        <option data-id="0">请选择部门</option>
                        <c:forEach items="${list}" var="item">
                            <option data-id="${item.id}">${item.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">联&nbsp;&nbsp;系&nbsp;&nbsp;人:</label>
                <div class="col-sm-8">
                    <input name="linkman" readonly="readonly" type="text" class="form-control search-input"
                           data-type="linkman"
                           placeholder="" id="input-show-2">
                    <input readonly="readonly" type="hidden" name="linkmanname">
                </div>
            </div>

            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">联系电话:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" name="phone"
                           placeholder="">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">需要人数:</label>
                <div class="col-sm-8">
                    <input type="number" class="form-control" name="totalNumber"
                           placeholder="">
                </div>
            </div>

        </div>

        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label
                        class="col-sm-4 control-label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</label>
                <div class="col-sm-7" style="padding-right: 0px;">
                    <input type="text" class="form-control" name="address"
                           placeholder="" id="suggestId">
                </div>
                <div id="searchResultPanel"
                     style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>
                <div class="col-sm-1" style="padding: 0px;">
                    <a id="a-map" title="点我选点"><i class="fa fa-map-marker fa-2x col-sm-3"></i></a>
                    <input type="hidden" class="form-control" name="lng"
                           placeholder="">
                    <input type="hidden" class="form-control" name="lat"
                           placeholder="">
                </div>
            </div>
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <button type="button" class="btn btn-info">展开地图</button>
            </div>
        </div>
        <div id="allmap" style="height:250px;width:80%;margin-left:5%;">
            <div id="map" style="height: 100%;width:100%"></div>
        </div>
        <hr>

        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">活动内容:</label>
                <div class="col-sm-8">
                    <textarea class="form-control" rows="3" name="content">${act.content}</textarea>
                </div>
            </div>
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">封面图片:</label>
                <div class="col-sm-8">
                    <div id="userImg">
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-xs-5 col-sm-5 col-md-5 col-lg-5">
                <label class="col-sm-4 control-label">备注:</label>
                <div class="col-sm-8">
                    <textarea class="form-control" rows="3" name="remark">${act.remark}</textarea>
                </div>
            </div>
        </div>
        <div class="row form-group">
            <div class="text-center">
                <button id="saveBtn" type="button" class="btn btn-default">保存</button>
                <button type="button" class="btn btn-primary" id="btn-add">确定</button>
            </div>
        </div>
    </form>


    <!-- 模态框（Modal） -->
    <div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        搜索用户
                    </h4>
                </div>
                <div class="modal-body">
                    <input id="input-key" class="form-control" placeholder="请输入用户名或手机号">
                    <ul class="mui-table-view mui-table-view-chevron">

                    </ul>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button type="button" class="btn btn-primary">
                        提交更改
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script type="text/javascript">
    $(function () {

        var act = localStorage.getItem('add_act')
        if (act) {
            act = JSON.parse(act)
            for (var k in act) {
                if (k == 'content') {
                    $('textarea[name=' + k + ']').val(act[k])
                } else if (k == 'deptid') {
                    var $sel = $('select[name=' + k + ']')
                    $sel.find('option').attr('selected', false)
                    $sel.find('option[data-id=' + act[k] + ']').attr('selected', true)
                } else if (k == 'cover') {
                } else if (k == 'remark') {
                    $('textarea[name=' + k + ']').val(act[k])
                }
                else {
                    $('input[name=' + k + ']').val(act[k])
                }
            }
        }

        $(".btn-info").on('click', function () {
            var text = $(".btn-info").html()
            if (text == "展开地图")
                $(".btn-info").html("收起地图")
                $("#map").css("display", "block")
            if (text == "收起地图") {
                $(".btn-info").html("展开地图")
                $("#map").css("display", "none")
            }
            ui.history.map(function () {
                // 百度地图API功能
                var map = new BMap.Map("map");
                var point = new BMap.Point(113.11, 23.05);
                map.centerAndZoom(point, 12);
                var marker = new BMap.Marker(point);//创建标注
                map.addOverlay(marker);
                marker.enableDragging();//可拖拉
                map.enableScrollWheelZoom(true);
                var geoc = new BMap.Geocoder();

                marker.addEventListener("dragend", getAttr);
                function getAttr() {
                    var pt = marker.getPosition();       //获取marker的位置
                    //逆地址解析
                    geoc.getLocation(pt, function (rs) {
                        var addComp = rs.addressComponents;
                        var lng = pt.lng;
                        var lat = pt.lat;
                        var address = addComp.province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber;
                        $("input[name=address]").val(address);
                        $("input[name=lng]").val(lng);
                        $("input[name=lat]").val(lat);
                    });
                }

                $('#a-map').click(function () {
                    //地址解析器
                    var myGeo = new BMap.Geocoder();
                    var address = $("input[name=address]").val();
                    // 将地址解析结果显示在地图上,并调整地图视野
                    myGeo.getPoint(address, function (point) {
                        if (point) {
                            map.centerAndZoom(point, 16);
                            map.addOverlay(new BMap.Marker(point));
                        } else {
                            ui.alert("您选择地址没有解析到结果!");
                        }
                    }, "佛山市");
                })

                function G(id) {
                    return document.getElementById(id);
                }


                var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
                    {
                        "input": "suggestId"
                        , "location": map
                    });

                ac.addEventListener("onhighlight", function (e) {  //鼠标放在下拉列表上的事件
                    var str = "";
                    var _value = e.fromitem.value;
                    var value = "";
                    if (e.fromitem.index > -1) {
                        value = _value.province + _value.city + _value.district + _value.street + _value.business;
                    }
                    str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

                    value = "";
                    if (e.toitem.index > -1) {
                        _value = e.toitem.value;
                        value = _value.province + _value.city + _value.district + _value.street + _value.business;
                    }
                    str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
                    G("searchResultPanel").innerHTML = str;
                });

                var myValue;
                ac.addEventListener("onconfirm", function (e) {    //鼠标点击下拉列表后的事件
                    var _value = e.item.value;
                    myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
                    G("searchResultPanel").innerHTML = "onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;

                    setPlace();
                });
                function setPlace() {
                    map.clearOverlays();    //清除地图上所有覆盖物
                    function myFun() {
                        var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
                        map.centerAndZoom(pp, 18);
                        map.addOverlay(new BMap.Marker(pp));    //添加标注

                        $("input[name=lng]").val(pp.lng);
                        $("input[name=lat]").val(pp.lat);
                    }

                    var local = new BMap.LocalSearch(map, { //智能搜索
                        onSearchComplete: myFun
                    });
                    local.search(myValue);
                }

            });
        })

        $('#saveBtn').click(function () {
            var info = getInfo()
            if (info) {
                localStorage.setItem('add_act', JSON.stringify(info))
                ui.alert('保存成功')
            }
        })

        var upload = ui.uploader('#userImg')
        $('#start_datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true
        });
        $('#end_datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            autoclose: true
        });

        $('#btn-add').on('click', function () {
            var data = getInfo()
            var url = '/admin/tribe/api/add_act.json'
            upload.upload(function (fileIds) {
                if (fileIds && fileIds.length > 0) {
                    data['coverImg'] = fileIds[0]
                }
                ui.post(url, data, function () {
                    ui.alert('添加成功')
                    localStorage.removeItem('add_act')
                })
            })
        })
    })

    function getInfo() {
        var theme = $("input[name=theme]").val();
        var start_time = $("input[name=startTime]").val();
        var end_time = $("input[name=endTime]").val();
        var address = $("input[name=address]").val();
        var lng = $("input[name=lng]").val();
        var lat = $("input[name=lat]").val();
        var leader = $("input[name=leadername]").val();
        var linkman = $("input[name=linkmanname]").val();
        var phone = $("input[name=phone]").val();
        var total_number = $("input[name=totalNumber]").val();
        var cross_point = $("input[name=crossPoint]").val();
        var content = $("textarea[name=content]").val();
        var remark = $("textarea[name=remark]").val();
        var deptid = $("select[name=deptid] option:checked").attr("data-id");
        if (phone.length > 11) {
            ui.alert("联系电话长度过长，请检查，不能大于11位数字");
            return;
        }
        if (cross_point.length > 5) {
            ui.alert("跨域积分长度过长，请检查，不能大于5位数字");
            return;
        }
        if (theme.length > 100) {
            ui.alert("活动主题长度过长，请检查，不能大于100位字符");
            return;
        }

        var data = {
            theme: theme,
            startTime: start_time,
            endTime: end_time,
            address: address,
            lng: lng,
            lat: lat,
            leader: leader,
            linkman: linkman,
            phone: phone,
            crossPoint: cross_point,
            content: content,
            deptid: deptid,
            totalNumber: total_number,
            remark: remark,
            coverImg: 1
        }
        return data
    }

    var search_input;
    $(".search-input").click(function () {

        search_input = $(this).attr("data-type");

        $("#searchModal").modal("show");
    });

    $("#input-key").keyup(function () {
        $("#searchModal ul").html("");
        var key = $(this).val();
        $.ajax({
            url: "${home}/admin/tribe/api/search.json",
            data: {
                key: key
            },
            success: function (rs) {
                callback(rs);
            }
        });
        function callback(rs) {
            var data = rs.data;
            if (data.length > 0) {
                var ps = "";
                var path = '${home}/weixin/image/defalut/user.png'
                for (var i = 0; i < data.length; i++) {
                    var avatar = data[i].avatar != null ? data[i].avatar : path

                    ps = ps + '<li data-userid="' + data[i].userid + '" class="mui-table-view-cell"style="background-color: white; margin-bottom: 5px;"><a><img style="width: 37px;border-radius: 50%;"class="mui-media-object mui-pull-left"src="' + avatar + '"><div class="mui-media-body mui-ellipsis"style="margin-top: 10px;"><span class="span-username">' + data[i].name + '</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + data[i].department + '<span class="mui-ellipsis mui-right mui-pull-right">' + data[i].mobile + '</span></div></a></li>';
                }
                $("#searchModal ul").append(ps);
            }
        }
    });

    $("#searchModal ul").on("click", "li", function () {
        var username = $(this).find(".span-username").text();
        var userid = $(this).attr("data-userid");
        if (search_input == "leader") {
            $("#input-show-1").val(username);
            $("input[name=leadername]").val(userid);
        } else if (search_input == "linkman") {
            $("#input-show-2").val(username);
            $("input[name=linkmanname]").val(userid);
        }

        $("#searchModal").modal("hide");
    });

</script>
