<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/10
  Time: 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<style>
    .addPane {
        margin: 0 auto;
        text-align: center;
        width: 50%;
    }

    .addTime {
        margin: 0 auto;
        text-align: center;
        width: 60%;
        clear: both;
    }

    .small {
        width: 100%;
        height: 10%;
        clear: both;
    }

    .open_body {
        width: 100%;
        height: 100%;
        margin: 0 auto;
        margin-left: 10px;
    }

    .open_time {
        width: 700px;
        height: 600px;
        margin-top: 200px;
    }

    .start_time {
        margin-left: 20px;
    }

    .end_time {
        margin-left: 30px;
    }

    .start {
        margin-left: 5px;
    }

    .end {
        margin-left: 5px;
    }

    .week {
        margin-left: 10px;
        font-size: 15px;
    }

    .monday {
        width: 100%;
        height: 50px;
    }

    .tuesday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .wednesday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .thursday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .firday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .saturday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .sunday {
        width: 100%;
        height: 50px;
        clear: both;
    }

    .imagePlay {
        margin-top: -150px;
        height: 267px;
        width: 340px;
    }
    #image1{
        height: 267px;
        width: 340px;
    }
    #image2{
        height: 267px;
        width: 340px;
    }
</style>
<div class="row">
    <div class="addPane">
        <div class="header">
            <h4>新增小屋</h4>
        </div>
        <div class="pull-left">
            <a data-toggle="tab" class="btn btn-empty-theme back"><span
                    class="fa fa-chevron-circle-left"></span> 返回</a>
        </div>
        <div class="small">
            <from class="form-horizontal container-fluid" role="form">
                <div class="form-group">
                    <label class="col-sm-2 control-label">小屋名称：</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="name" value="${mom.name}" placeholder=""/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">比赛封面：</label>
                    <div class="col-sm-3">
                        <div id="img">

                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">小屋介绍：</label>
                    <div class="col-sm-8 ">
                        <textarea name="info" class="form-control" rows="3" placeholder="">${mom.info}</textarea>
                    </div>
                </div>
            </from>
        </div>
    </div>
    <div class="imagePlay pull-left">
        <div id="myCarousel" class="carousel slide">
            <!-- 轮播（Carousel）指标 -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
            </ol>
            <!-- 轮播（Carousel）项目 -->
            <div class="carousel-inner">
                <c:forEach items="${image}" var="item">
                    <c:if test="${item.id==1}">
                        <div class="item active">
                            <img src="${home}${item.imagePath}" alt="First slide" id="image1">
                            <div class="carousel-caption">封面一</div>
                        </div>
                    </c:if>
                    <c:if test="${item.id==2}">
                        <div class="item">
                            <img src="${home}${item.imagePath}" alt="scend slide" id="image2">
                            <div class="carousel-caption">封面二</div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
            <!-- 轮播（Carousel）导航 -->
            <a class="carousel-control left" href="#myCarousel"
               data-slide="prev">&lsaquo;
            </a>
            <a class="carousel-control right" href="#myCarousel"
               data-slide="next">&rsaquo;
            </a>
        </div>
    </div>
    <div class="addTime">
        <div class="open_time">
            <h4 class="title">开放时间</h4>
            <div class="open_body">
                <div class="monday">
                    <input type="checkbox" value="1" id="mon" class="pull-left check_1">
                    <label class="week pull-left">周一</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker" id="start_1"
                               name="startTime"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_1"
                               placeholder="">
                    </div>
                </div>
                <div class="tuesday">
                    <input type="checkbox" value="2" id="tue" class="pull-left check_2">
                    <label class="week pull-left">周二</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker"
                               name="startTime" id="start_2"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_2"
                               placeholder="">
                    </div>
                </div>
                <div class="wednesday">
                    <input type="checkbox" value="3" id="wed" class="pull-left check_3">
                    <label class="week pull-left">周三</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker"
                               name="startTime" id="start_3"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_3"
                               placeholder="">
                    </div>
                </div>
                <div class="thursday">
                    <input type="checkbox" value="4" id="thu" class="pull-left check_4">
                    <label class="week pull-left">周四</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker"
                               name="startTime" id="start_4"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_4"
                               placeholder="">
                    </div>
                </div>
                <div class="firday">
                    <input type="checkbox" value="5" id="fir" class="pull-left check_5">
                    <label class="week pull-left">周五</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker"
                               name="startTime" id="start_5"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_5"
                               placeholder="">
                    </div>
                </div>
                <div class="saturday">
                    <input type="checkbox" value="6" id="sat" class="pull-left check_6">
                    <label class="week pull-left">周六</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker"
                               name="startTime" id="start_6"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_6"
                               placeholder="">
                    </div>
                </div>
                <div class="sunday">
                    <input type="checkbox" value="7" id="sun" class="pull-left check_7">
                    <label class="week pull-left">周日</label>
                    <label class="start_time pull-left">开始时间：</label>
                    <div class="start pull-left">
                        <input type="text" class="form-control start_datetimepicker"
                               name="startTime" id="start_7"
                               placeholder="">
                    </div>
                    <label class="end_time pull-left">结束时间：</label>
                    <div class="end pull-left">
                        <input type="text" class="form-control end_datetimepicker" name="endTime" id="end_7"
                               placeholder="">
                    </div>
                </div>
                <button type="button" class="btn btn-primary" id="submit">保存</button>
            </div>
        </div>
    </div>

</div>
<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script>
    $(function () {
        var upload = ui.uploader('#img', {multiple: true})
//        $('.start_datetimepicker').datetimepicker({
//            format: 'hh:ii',
//            autoclose: true
//        });
//        $('.end_datetimepicker').datetimepicker({
//            format: 'hh:ii',
//            autoclose: true
//        });
        $(".start_datetimepicker").datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            todayBtn: true,
            startView: 'hour',
            maxView: 'hour'
        });
        $(".end_datetimepicker").datetimepicker({
            format: 'hh:ii',
            autoclose: true,
            todayBtn: true,
            startView: 'hour',
            maxView: 'hour'
        });

        var path='/admin/union/woman/mom/api/get_open_time.json'
        ui.post(path,{id:${mom.id}},function (json) {
            $(json).each(function (index, item) {
                $("#start_"+item.week).val(item.startTime)
                $("#end_"+item.week).val(item.endTime)
                $('.check_'+item.week).prop("checked",true);
            })
        })

        $('#submit').on('click', function () {
            var weeks = []
            var name = $("input[name=name]").val()
            var info = $("textarea[name=info]").val()
            var week = "";
            var id = ${mom.id}
                $("input[type='checkbox']:checked").each(function (i) {
                    var value = $(this).val()
                    if (i == 0)
                        week = value
                    else
                        week += ("," + value)
                    var start = $("#start_" + value).val()
                    var end = $("#end_" + value).val()
                    if (start && end) {

                    } else {
                        ui.alert("请选择好开始和结束时间")
                        throw SyntaxError();
                        return
                    }
                })

                $("input[type='checkbox']:checked").each(function (i) {
                    var value = $(this).val()
                    if (i == 0)
                        week = value
                    else
                        week += ("," + value)
                    var start = $("#start_" + value).val()
                    var end = $("#end_" + value).val()
                    if (start && end) {
                        if(start >= end) {
                            ui.alert("结束时间不能小于等于开始时间")
                            throw SyntaxError();
                        }
                        var map = {week: value, startTime: start, endTime: end}
                        weeks.push(map)
                    } else {
                        ui.alert("请选择好开始和结束时间")
                        return
                    }
                })
            var data = {
                id: id,
                name: name,
                info: info,
                week: week,
                times: weeks
            }
            var verifyRes = verify(data);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            var url = '/admin/union/woman/mom/api/update.json'
            upload.upload(function (fileIds) {
                if (fileIds && fileIds.length > 0) {
                    var imgPath = ""
                    for (var i = 0; i < fileIds.length; i++) {
                        if (i != 0)
                            imgPath = imgPath + "," + fileIds[i]
                        else
                            imgPath = fileIds[i]
                    }
                    data['cover'] = imgPath
                }
                var womanTime = JSON.stringify(data)
                ui.post(url, {womanMom: womanTime}, function () {
                    ui.alert("添加成功")
                })
            })

            //数据提交
        });

        $(".back").on('click',function () {
            ui.history.go("union/woman/mom/list")
        })

        //表单验证
        function verify(data) {
            if (!data['name'])
                return '小屋名不能为空'
            if (!data['info'])
                return '小屋介绍不能为空'
        }
    })

</script>