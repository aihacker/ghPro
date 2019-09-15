<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/viewer/viewer.min.css">
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<style>
    td > img {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }

    .ui-img-div {
        height: 200px;
        overflow: hidden;
        border: 1px solid #ccc;
        position: relative;
    }

    .ui-img-div img {
        width: 100%;
    }

    #prevInfo {
        line-height: 30px;
        position: absolute;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 30px;
        margin: 0;
        text-align: left;
        text-indent: 12px;
        opacity: .8;
        background-color: #000;
    }
</style>
<div class="row">
    <div class="tab-content ui-content">
        <form id="addForm" class="col-lg-7 col-md-7 col-lg-offset-2 col-md-offset-2 ui-margin-top-20"
              enctype="multipart/form-data">
            <div class="form-group">
                <label>场馆名称</label>
                <select class="form-control" name="placeId">
                    <option value="-1">请选择</option>
                    <c:forEach items="${place}" var="g">
                        <option value="${g.value}">${g.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group" id="closeCate" style="display: none;">
                <label>选择项目</label>
                <select class="form-control" name="cateId">
                </select>
            </div>
            <div id="closeSitecloseSite" class="form-group" style="display: none;">
                <label>选择场地</label>
                <div class="siteListDiv">
                    <label class="checkbox-inline">
                        <input name="siteId" type="checkbox" value="-1"> 全部
                    </label>
                </div>
            </div>
            <div class="form-group input-append date form_datetime" data-date-format="dd-mm-yyyy">
                <label>选择日期</label>
                <input name="closeDate" type="text" readonly class="form-control">
                <span class="add-on"><i class="icon-th"></i></span>
            </div>
            <div id="claseTime" class="form-group">
                <label>选择时间</label>
                <div class="siteListDiv">
                    <label class="checkbox-inline">
                        <input name="timeId" type="checkbox" value="1"> 上午
                    </label>
                    <label class="checkbox-inline">
                        <input name="timeId" type="checkbox" value="2"> 下午
                    </label>
                    <label class="checkbox-inline">
                        <input name="timeId" type="checkbox" value="3"> 晚上
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label>停场原因</label>
                <small class="text-danger"></small>
                <textarea class="form-control" name="reason" rows="3"
                          placeholder="简单说明原因，原因将会推送给所有用户，不超过100字符"></textarea>
            </div>
            <div class="form-group">
                <button id="addLawBtn" type="button" class="btn btn-theme">确定发布</button>
            </div>
        </form>
    </div>
</div>
<script src="${home}/libs/viewer/viewer-jquery.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script>
    (function () {
        $(".form_datetime").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            todayBtn: 1,
            autoclose: 1,
        });

        var $selectCate = $("select[name=cateId]"),
            $closeCate = $('#closeCate'),
            $closeSite = $('#closeSite');

        $("select[name=placeId]").on("change", function () {
            var self = $(this)
            if (self.val() != -1) {
                list_cates(self.val());
            } else {
                $closeCate.fadeOut();
                $closeSite.fadeOut();
            }
        });

        $selectCate.change(function () {
//            var cateId = $selectCate.val();
//            if (cateId != -1) {
//                list_site(cateId);
//            }
        });

        //原因
        $('input[name=reason]').keyup(function () {
            var val = $(this).val();
            var $prev = $(this).prev();
            if (val.length > 100) {
                $(this).val(val.substring(0, 100))
                $prev.removeClass('hidden')
                $prev.text('停场原因不能超过100字符哦');
            } else {
                $prev.addClass('hidden')
            }
        })


        $("#addLawBtn").on("click", function () {
            var $form = $('#addForm');
            var info = $form.serializeJson();

            if (info.siteId && info.siteId.length > 0) {
                info['siteId'] = info.siteId.toString();
            }
            if (info.timeId && info.timeId.length > 0) {
                info['timeId'] = info.timeId.toString();
            }
            info['id'] = info['placeId']
            info['type'] = info['cateId']
            info['date'] = info['closeDate']
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return;
            }
            ui.confirm("是否发送停场公告？", function () {
                ui.post("nanhai/place/api/close_v2.json", info, function () {
                    ui.alert("公告发送成功", function () {
                        ui.history.go("nanhai/place/close")
                    })
                })
            })

        })

        function verify(info) {
            if (info['closeDate'] == "") {
                return "请先选择日期"
            } else if (info['reason'] == "") {
                return "请填写停场原因"
            }
            if(!info['cateId'] || info['cateId'] <= 0)
                return "请选择项目";
            var date = Date.parse(new Date(info["closeDate"]));
            var now = new Date().setHours(0, 0, 0, 0);
            if (date < now) {
                return "不能在当前日期之前"
            }
            if(!info['timeId']){
                return "至少选择一个停场时间段";
            }
//            var date2 = Date.parse(new Date(info['date']))
            var now2 = new Date();
            now2.setHours(0);
            now2.setMinutes(0);
            now2.setSeconds(0);
            now2.setMilliseconds(0);
            var verifyTime = getYearWeek(new Date(date))
            var verifyTimeNow = getYearWeek(now2);
            if (verifyTime != verifyTimeNow) {
                return "只能选择本星期内日期"
            }
        }

        //判断周数
        function getYearWeek(date) {
            var date2 = new Date(date.getFullYear(), 0, 1);
            var day1 = date.getDay();
            if (day1 == 0) day1 = 7;
            var day2 = date2.getDay();
            if (day2 == 0) day2 = 7;
            d = Math.round((date.getTime() - date2.getTime() + (day2 - day1) * (24 * 60 * 60 * 1000)) / 86400000);
            return Math.ceil(d / 7) + 1 + date.getYear();
        }

        function list_cates(placeId) {
            ui.get('nanhai/place/api/list_cate.json', {placeId: placeId}, function (cates) {
                if (cates && cates.length > 0) {
                    $selectCate.empty();
                    $selectCate.append('<option value="-1">选择项目</option>');
                    for (var i in cates) {
                        $selectCate.append('<option value="' + cates[i].value + '">' + cates[i].name + '</option>');
                    }
                    $closeCate.fadeIn();
                }
            });
        }

        function list_site(cateid) {
            var placeId = $("select[name=placeId]").val();
            ui.post('nanhai/place/api/getplacesite.json', {placeId: placeId, cateId: cateid}, function (sites) {
                if (sites && sites.length > 0) {
                    var $siteDiv = $closeSite.find('.siteListDiv');
                    $siteDiv.empty();
                    var $defaul = $('<label class="checkbox-inline"><input name="siteId" type="checkbox" value="-1"> 全部</label>');
                    $siteDiv.append($defaul);
                    $defaul.on('change', 'input', function () {
                        $(this).parent().nextAll().find('input').prop('checked', $(this).is(':checked'));
                    });
                    for (var i in sites) {
                        $siteDiv.append('<label class="checkbox-inline"><input name="siteId" type="checkbox" value="' + sites[i].id + '"> ' + sites[i].name + '</label>');
                    }
                    $closeSite.fadeIn();
                }
            });
        }

    })()
</script>

