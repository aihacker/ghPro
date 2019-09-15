<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/2/6
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title></title>
    <jsp:include page="/comm/admin/styles.jsp"></jsp:include>
    <link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${home}/comm/mobile/css/wxgh.css"/>

    <style>
        .ui-checkbox-label {
            margin-bottom: 0;
            line-height: 38px;
            display: inline-block;
            padding-left: 10px;
            padding-right: 10px;
        }

        .ui-time-group input.form-control {
            width: 40%;
            display: inline-block;
        }

        .ui-time-group label {
            display: inline-block;
            padding-left: 15px;
            padding-right: 15px;
            font-size: 18px;
        }

        .ui-btn-group button.btn-block {
            width: 80%;
            margin-left: 10%;
        }

        .ui-type-group.nav-pills > li.active > a,
        .ui-type-group.nav-pills > li.active > a:focus,
        .ui-type-group.nav-pills > li.active > a:hover {
            color: #43D3B0;
            background-color: #ffffff;
        }

        .ui-type-group {
            margin-top: 20px;
        }

        .ui-type-group.nav > li > a {
            padding: 10px 8px;
            color: #000000;
        }

        .ui-changdi-list tr td {
            text-align: center;
        }

        .ui-delete {
            position: absolute;
            top: -5px;
            right: 10px;
            font-size: 20px;
        }

        .ui-delete:hover {
            opacity: .8;
        }

        .ui-select-div {
            display: inline-block;
            padding: 10px 20px;
        }

        .ui-select-div select.form-control {
            display: inline-block;
            width: 140px;
        }

        .list-group.list-horizontal {
            margin-bottom: 0;
            list-style: none;
        }

        .list-group.list-horizontal li.list-group-item {
            display: inline-block;
            margin: 4px;
        }

        #yuyueManager table tr th,
        #yuyueManager table tr td {
            text-align: center;
        }

        .breadcrumb {
            margin-bottom: 0;
        }

        .btn-cate-add {
            margin-top: 20px;
        }

        .btn-link[class^=a-changdi] {
            padding: 0;
        }

        #scheduleManager input[type=checkbox] {
            top: 0px;
        }

        label input[type=checkbox] {
            margin-left: 8px;
        }

        #table-paiban .list-group-item small {
            color: #777;
        }

        #gudingManager table th,
        #gudingManager table td {
            text-align: center;
        }

        #gudingManager .ui-select {
            width: 120px;
            display: inline-block;
            margin: 0 5px;
        }
    </style>

</head>

<body>

<div class="container">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="javascript:history.back()">场馆列表</a></li>
            <li class="active">场馆详情</li>
        </ol>
    </div>

    <div style="margin-top: 5px;">
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#baseInfo" data-toggle="tab">基本信息</a>
            </li>
            <li>
                <a href="#placeManager" data-toggle="tab">场地管理</a>
            </li>
            <li>
                <a href="#pictureManager" data-toggle="tab">图片管理</a>
            </li>
            <li>
                <a href="#scheduleManager" data-toggle="tab">排班管理</a>
            </li>
            <li>
                <a href="#gudingManager" data-toggle="tab">固定场管理</a>
            </li>
            <!--<li>
                <a href="#yuyueManager" data-toggle="tab">预约规则管理</a>
            </li>-->
        </ul>

        <div class="tab-content">
            <!--
                作者：1293140072@qq.com
                时间：2017-02-06
                描述：基本信息
            -->
            <div role="tabpanel" class="tab-pane active" id="baseInfo">
                <div class="container-fluid">
                    <div class="row">
                        <form id="placeForm"
                              class="form-horizontal col-md-offset-2 col-lg-offset-2 col-md-7 col-lg-7 ui-margin-top-15">
                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">场馆名称</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <input type="text" name="title" value="${place.title}" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">经纬度</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <input id="input-zuobiao" type="text" value="${place.lng}, ${place.lat}"
                                           class="form-control"/>
                                    <input type="hidden" name="lng" value="${place.lng}"/>
                                    <input type="hidden" name="lat" value="${place.lat}"/>
                                    <input type="hidden" name="id" value="${param.id}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">详细地址</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <textarea class="form-control" name="address" rows="3">${place.address}</textarea>
                                    <input type="hidden" name="province" value="${place.province}"/>
                                    <input type="hidden" name="city" value="${place.city}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <a id="a-map"> <label class="col-md-2 col-lg-2 col-sm-3 control-label">点我选址</label>
                                </a>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">项&nbsp;&nbsp;目</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <c:forEach items="${allCates}" var="c">
                                        <label class="ui-checkbox-label">
                                            <input name="typeInt" value="${c.id}"
                                                   type="checkbox"/> ${c.name}</label>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">联系电话</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <input type="text" name="phone" value="${place.phone}" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">邮&nbsp;&nbsp;编</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <input type="text" name="postcode" value="${place.postcode}" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">价&nbsp;&nbsp;格</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <input type="text" name="price" value="${place.price}" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">营业时间</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <div class="ui-time-group">
                                        <input type="text" name="startTime" value="${place.startTime}"
                                               class="form-control"/>
                                        <label>~</label>
                                        <input type="text" name="endTime" value="${place.endTime}"
                                               class="form-control"/>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">场地简介</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <textarea class="form-control" name="info" rows="4">${place.info}</textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-lg-2 col-sm-3 control-label">地址链接</label>

                                <div class="col-md-10 col-lg-10 col-sm-9">
                                    <input name="url" value="${place.url}" type="url" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group ui-btn-group">
                                <div class="col-md-4">
                                    <button type="reset" class="btn btn-success btn-block" id="btn-reset">重置</button>
                                </div>
                                <div class="col-md-4">
                                    <button id="btn-save" type="button" class="btn btn-success btn-block">保存</button>
                                </div>
                                <div class="col-md-4">
                                    <button type="button" class="btn btn-success btn-block" onclick="history.go(-1)">
                                        返回上一页
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!--
                作者：1293140072@qq.com
                时间：2017-02-06
                描述：场地管理
            -->
            <div role="tabpanel" class="tab-pane" id="placeManager">
                <div class="container-fluid">
                    <div class="row">
                        <ul class="nav nav-pills ui-type-group">
                            <c:forEach items="${cates}" var="c" varStatus="i">
                                <li class="li-cate ${i.first?"active":""}">
                                    <a href="#" data-id="${c.id}">${c.name}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="row">
                        <table class="table table-bordered ui-changdi-list">
                            <%--<tr id="tr-changdi">--%>
                            <%--<td>1号场</td>--%>
                            <%--<td>--%>
                            <%--<button type="button" class="btn btn-default"><span--%>
                            <%--class="fa fa-plus-square-o"></span> 添加--%>
                            <%--</button>--%>
                            <%--</td>--%>
                            <%--</tr>--%>
                        </table>
                    </div>
                </div>
            </div>

            <!--
                作者：1293140072@qq.com
                时间：2017-02-06
                描述：图片管理yuyueManager
            -->
            <div role="tabpanel" class="tab-pane" id="pictureManager">
                <div class="container-fluid ui-margin-top-15">
                    <div class="row" id="div-place-img">
                        <%--<div class="col-md-3 col-sm-3 col-xs-2">
                            <a class="thumbnail" href="#">
                                <img src="${home}/image/xlkai/icon_add1.png"/>
                            </a>
                            <span class="fa fa-remove text-danger ui-delete"></span>
                        </div>

                        <div class="col-md-3 col-sm-3 col-xs-2">
                            <a class="thumbnail" href="#">
                                <img src="${home}/image/xlkai/icon_add1.png"/>
                            </a>
                            <input type="file">
                        </div>
                        --%>
                    </div>
                </div>
            </div>

            <!--
                作者：1293140072@qq.com
                时间：2017-02-06
                描述：排班管理
            -->
            <div role="tabpanel" class="tab-pane" id="scheduleManager">
                <div class="container-fluid">
                    <div class="row">
                        <div class="ui-select-div">
                            <label>项目：</label>
                            <select class="form-control" id="select-paiban">
                                <option value="0">请选择项目</option>
                                <c:forEach items="${cates}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="ui-select-div">
                            <label>场地：</label>
                            <select class="form-control" id="select-changdi">
                                <option value="0">请选择场地</option>
                            </select>
                        </div>

                        <label style="display: inline-block;margin-left: 10px;"><input
                                type="checkbox" id="input-sync">&nbsp;其他场地使用该排班</label>
                    </div>

                    <div style="float:right;">
                        <label style="float:left;">状态说明：</label>
                        <div style="border:1px solid black;background-color: #0bb20c;width: 20px;height: 20px;float:left;margin-left: 5px;"></div>
                        <label style="float:left;margin-left: 5px;">已预约</label>
                        <div style="border:1px solid black;background-color: #9d9d9d;width: 20px;height: 20px;float:left;margin-left: 5px;"></div>
                        <label style="float:left;margin-left: 5px;">已失效</label>
                        <div style="border:1px solid black;background-color: #3c3c3c;width: 20px;height: 20px;float:left;margin-left: 5px;"></div>
                        <label style="float:left;margin-left: 5px;">管理员取消</label>
                        <div style="border:1px solid black;background-color: #f9f900;width: 20px;height: 20px;float:left;margin-left: 5px;"></div>
                        <label style="float:left;margin-left: 5px;">固定场</label>
                        <div style="border:1px solid black;background-color: #ffffff;width: 20px;height: 20px;float:left;margin-left: 5px;"></div>
                        <label style="float:left;margin-left: 5px;">未预约</label>
                    </div>

                    <div class="row">
                        <table class="table table-bordered table-hover ui-margin-top-10" id="table-paiban">
                            <thead>
                            <tr>
                                <th class="col-md-1 col-sm-2 col-xs-2 text-center">星&nbsp;&nbsp;&nbsp;期</th>
                                <th>排班列表</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期一</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-1">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期二</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-2">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期三</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-3">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期四</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-4">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期五</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-5">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期六</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-6">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td class="col-md-1 col-sm-2 col-xs-2 text-center">星期日</td>
                                <td>
                                    <ul class="list-group list-horizontal" id="ul-placeTime-7">
                                        <li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i></li>
                                    </ul>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>

            <!--
                作者：1293140072@qq.com
                时间：2017-02-06
                描述：固定场管理
            -->
            <div role="tabpanel" class="tab-pane" id="gudingManager">
                <div class="container-fluid">
                    <div class="row" style="padding: 10px 5px;">
                        <a class="btn btn-link" data-toggle="modal" data-target="#addGudingModal">立即添加</a>
                        <select id="gudingCateSelect" class="form-control ui-select">
                            <option value="0">请选择项目</option>
                            <c:forEach items="${cates}" var="c">
                                <option value="${c.id}">${c.name}</option>
                            </c:forEach>
                        </select>
                        <select id="gudingSiteSelect" class="form-control ui-select">
                            <option value="0">请选择场地</option>
                        </select>
                        <select id="gudingWeekSelect" class="form-control ui-select">
                            <option value="0">请选择时间</option>
                            <option value="1">周一</option>
                            <option value="2">周二</option>
                            <option value="3">周三</option>
                            <option value="4">周四</option>
                            <option value="5">周五</option>
                            <option value="6">周六</option>
                            <option value="7">周日</option>
                        </select>
                    </div>
                    <div class="row">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>场地信息</th>
                                <th>时间</th>
                                <th>用户信息</th>
                                <th>固定场状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="gudingBody">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>


            <!--
                作者：1293140072@qq.com
                时间：2017-02-06
                描述：预约权限管理
            -->
            <div role="tabpanel" class="tab-pane" id="yuyueManager">
                <div class="container-fluid ui-margin-top-15">

                    <div class="panel panel-info">
                        <a class="btn btn-link" data-toggle="modal" data-target="#ruleModal">添加规则</a>
                    </div>

                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>规则名称</th>
                            <th>规则介绍</th>
                            <th>状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:choose>
                            <c:when test="${empty rules}">
                                <tr>
                                    <td class="text-center" colspan="5">没有添加任何预约规则哦 <a data-toggle="modal"
                                                                                       data-target="#ruleModal"
                                                                                       class="btn btn-link">马上添加</a>
                                    </td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${rules}" var="r" varStatus="i">
                                    <tr data-id="${r.id}">
                                        <td>${i.count}</td>
                                        <td>${r.name}</td>
                                        <td>${r.info}</td>
                                        <td><span data-status="${r.status}"
                                                  class="label ui-status ${r.status eq 1 ? 'label-info':'label-danger'}">${r.status eq 1?'可用':'不可用'}</span>
                                        </td>
                                        <td class="ui-yuyue-manager-opration">
                                            <a class="ui-edit btn btn-link">编辑</a>
                                            <a class="ui-del btn btn-link">删除</a>
                                            <c:if test="${r.type eq 1}">
                                                <a href="${home}/place/admin/nanhai/expection/show.html?id=${r.id}&placeId=${param.id}">查看例外</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 点击地图选择地点 -->
<div class="modal fade" id="mapModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">点击地图选择地点</h4>
            </div>
            <div class="modal-body" id="allmap" style="width:100%;height:400px;">


            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<!-- 添加场地 -->
<div class="modal fade" id="addCateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    添加场地
                </h4>
            </div>
            <div class="modal-body">
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>名称:</label>
                    <input type="text" name="name" class="form-control" id="input-cate-name"
                           placeholder="请输入场地名称">
                </div>
                <div class="form-group">
                    <label>价格：
                        <small class="ui-text-info">（单位：元/次）</small>
                    </label>
                    <input type="text" name="price" class="form-control" id="input-cate-price"
                           placeholder="请输入价格">
                </div>
                <div class="form-group">
                    <label>积分：
                        <small class="ui-text-info">（单位：分/次）</small>
                    </label>
                    <input type="text" name="score" class="form-control" id="input-cate-score"
                           placeholder="请输入场地预约积分">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<!-- 添加图片 -->
<div class="modal fade" id="addPlaceImgModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    添加图片
                </h4>
            </div>
            <div class="modal-body" id="modal-body-img">
                <%--<input type="file" class="form-control" id="fileimg" name="fileimg" accept="image/*">--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<!-- 添加新排班 -->
<div class="modal fade" id="paibanModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    添加新排班
                </h4>
            </div>
            <div class="modal-body">
                <div class="form-group link">
                    开始时间：
                    <input type="text" class="form-control form_datetime" id="start-time" readonly>
                    结束时间：
                    <input type="text" class="form-control form_datetime" id="end-time" readonly>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<!-- 添加预约规则 -->
<div class="modal fade" id="ruleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    添加预约规则
                </h4>
            </div>
            <div class="modal-body">
                <form id="ruleForm">
                    <div class="form-group">
                        <label>规则名称：</label>
                        <input name="name" class="form-control" type="text">
                    </div>

                    <input type="hidden" name="id" value="">

                    <div class="form-group">
                        <label>规则介绍：</label>
                        <textarea name="info" class="form-control" rows="2"></textarea>
                    </div>

                    <div class="form-group">
                        <label>规则类型：</label>
                        <select name="type" id="ruleTypeSelect" class="form-control">
                            <option value="1">限制预约数量</option>
                            <option value="2">限制场地预约</option>
                        </select>
                    </div>

                    <div id="numbRuleDiv">
                        <div class="form-group">
                            <label>限制类型：</label>
                            <select name="numb_time" class="form-control">
                                <option value="day">天</option>
                                <option value="week">周</option>
                                <option value="month">月</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>预约数量：</label>
                            <input name="numb" type="number" class="form-control">
                        </div>
                    </div>

                    <div id="siteRuleDiv" class="hidden">
                        <div class="form-group">
                            <label>预订用户<a id="chooseUserBtn" class="btn btn-link">选择用户</a></label>
                            <input type="text" name="name" class="form-control" readonly>
                            <input type="hidden" name="userid">
                        </div>
                        <div class="form-group">
                            <label>预订时间、场地<a id="chooseTimeBtn" class="btn btn-link">选择预约时间段</a></label>
                            <input type="text" name="time" class="form-control" readonly>
                            <input type="hidden" name="timeId">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button id="addRuleBtn" type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>

<!-- 选择预订时间、场地 -->
<div class="modal fade" id="chooseTimeModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    选择场地、时间
                </h4>
            </div>
            <div class="modal-body">
                <div id="chooseBody">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button id="chooseTimeModalBtn" type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>
<style>
    .ui-guding-timelist .btn-link {
        color: #777777;
    }

    .ui-guding-timelist .btn-link.active {
        color: #43D3B0;
        border: 1px solid #777;
    }
</style>
<!-- 添加、编辑固定场 -->
<div class="modal fade" id="addGudingModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    添加固定场
                </h4>
            </div>
            <div class="modal-body">
                <div class="ui-guding-timelist">
                    <div id="gudingCateList">
                        <c:forEach items="${cates}" var="c">
                            <a data-id="${c.id}" class="btn btn-link">${c.name}</a>
                        </c:forEach>
                    </div>
                    <div id="gudingSiteList">
                    </div>

                    <div id="gudingWeekList" style="display: none">
                        <a data-week="1" class="btn btn-link active">周一</a>
                        <a data-week="2" class="btn btn-link">周二</a>
                        <a data-week="3" class="btn btn-link">周三</a>
                        <a data-week="4" class="btn btn-link">周四</a>
                        <a data-week="5" class="btn btn-link">周五</a>
                        <a data-week="6" class="btn btn-link">周六</a>
                        <a data-week="7" class="btn btn-link">周日</a>
                    </div>

                    <ul id="gudingTimeList" class="list-group list-horizontal">
                    </ul>
                </div>
                <form>
                    <div class="input-group">
                        <label>用户类型</label>
                        <select id="gudingUserType" class="form-control">
                            <option value="1">系统用户</option>
                            <option value="2">外部用户</option>
                        </select>
                        <div>
                            <a id="gudingChooseUser" class="btn btn-link">选择用户</a>
                            <small></small>
                        </div>
                    </div>
                    <input type="hidden" name="userid">
                    <div id="outUserDiv" style="display: none">
                        <div class="form-group">
                            <label>姓名</label>
                            <input name="username" class="form-control" type="text" placeholder="用户姓名（若不清楚，则填0）">
                        </div>
                        <div class="form-group">
                            <label>部门</label>
                            <input name="deptname" class="form-control" type="text" placeholder="用户部门">
                        </div>
                        <div class="form-group">
                            <label>联系电话</label>
                            <input name="mobile" class="form-control" type="text" placeholder="用户联系电话">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>备注</label>
                        <textarea name="remark" class="form-control" placeholder="备注信息" rows="3"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button id="addGudingModalBtn" type="button" class="btn btn-primary">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>

<%--Sheng--%>
<!-- 场次状态 -->
<div class="modal fade" id="changeStatusModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="changeStatusTitle">
                    场次状态
                </h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">
                    确认
                </button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="cancelReasonModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width: 400px;margin:100px auto;">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                &times;
            </button>
            <h4 class="modal-title">系统提示</h4>
        </div>
        <div class="modal-body">
            <input id="choiceCancelId" type="hidden">
            <label>取消的理由：</label>
            <input class="form-control" id="cancelReason" type="text">
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-green" onclick="cancelBtn()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        </div>
    </div>
</div>

<jsp:include page="/comm/admin/scripts.jsp"/>
    <script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=3NDsGBtCfU8VlHvwz64jibfzthKlbyya"></script>
<%--<script type="text/javascript" src="${home}/script/xlkai/ajaxfileupload.js"></script>--%>
<script>
    var homePath = '${home}';
    var myPlaceId = '${place.id}';
    var myTypeInt = '${place.typeInt}';
    var self_url = '${home}/admin/nanhai/place/api/';
    var self_rule = homePath+'/wxgh/place/rule/edit.json';

    $(".form_datetime").datetimepicker({
        format: 'hh:ii',
        autoclose: true,
        todayBtn: true,
        startView: 'hour',
        maxView: 'hour'
    });
</script>
<%--<jsp:include page="/comm/userlist.jsp"/>--%>
<script type="text/javascript" src="${home}/script/nanhai_place_edit.js"></script>
<jsp:include page="/comm/admin/userlist.jsp"></jsp:include>
</body>

</html>

