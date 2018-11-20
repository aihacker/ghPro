<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>纪检监察核查分析系统</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%--<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />--%>
    <meta http-equiv="Cache-Control" content="no-siteapp" />

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${home}/css/title/font.css">
    <link rel="stylesheet" href="${home}/css/title/xadmin.css">
    <script src="${home}/script/lib/jquery-3.1.1.min.js"></script>
    <%--<script type="text/javascript" src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>--%>
    <script src="${home}/script/layui/layui.js" charset="utf-8"></script>
    <script type="text/javascript" src="${home}/script/js/xadmin.js"></script>


    <script>
        /**
         * 系统菜单点击响应事件
         * */
        function sys_menu_clicked(e, nodeUrl) {
            //样式修改
            // var a = $(e);
            // $('.submenu').find('a.selected').removeClass('selected');
            // a.addClass('selected');
            $('.nav-pills').find('a').css("background-color","transparent");
            $(e).css("background-color","lightcyan");
            var url;
            if(nodeUrl) {
                url = nodeUrl;
            }
            $('#frameQuery').attr('src', url);

        }
    </script>

</head>
<body style="background-color:#5FB1FF;">
<!-- 顶部开始 -->
<div class="container" style="background: url(${home}/image/icon-top.png) no-repeat;">
    <div class="logo">
        <!--        	<img src="images/shadow.png" style="position: absolute;width: 52%;" />-->
        <!--<img src="images/icon4.png"/>-->
    </div>
    <!--<div class="left_open">
        <i title="展开左侧栏" class="iconfont">&#xe699;</i>
    </div>-->
    <ul class="layui-nav right nav-R" lay-filter="" style="	background-color: #1e72b8;">
        <li class="layui-nav-item">
                <span id="" style="font-size: 10px;">
                	admin,欢迎您！
                </span>
        </li>
        <li class="layui-nav-item">
            <a href="login.html" style="font-size: 12px;">
                <i style="margin-right: 4px;"><img src="${home}/image/admin.png"/></i>
                用户信息</a>
        </li>
        <li class="layui-nav-item to-index"><a href="./login.html" style="font-size: 12px;"><i style="margin-right: 4px;"><img src="${home}/image/exit.png"/></i>退出系统</a></li>
    </ul>
</div>
<!-- 顶部结束 -->
<!-- 中部开始 -->
<!-- 左侧菜单开始 -->
<div class="left-nav" style="padding-top: 0px;">
    <div id="side-nav">
        <div id="" style="background: url(${home}/image/nav-top.png);width: 100%;height: 41px;position: relative;background-size: 240px auto;">
      		<span id=""  style="position: absolute;top: 10px;left: 44px;color: #ff5a00;font-weight: 700;">
      			菜单导航
      		</span>
            <span class="hidden-icon"></span>
        </div>

        <ul id="nav">
            <li style="background: url(${home}/image/icon6.png);">
                <a _href="${home}/common/welcome.jsp">
                    <cite style="padding-left: 20px;">首页</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
            </li>
            <!-- 数据统计查询 -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">数据统计查询</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>采购数据</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/source/proportion/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购方式统计</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/form/allmost/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购合同统计</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/form/allcontract/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购合同查询</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/cleanInventory/show/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">即时清结查询</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/info/companylegal/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">供应商信息查询</cite>

                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>公车数据</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/ytcard/monthcarout/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">出入情况查询</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/daoexcel/examinepdf/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">通行情况查询</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/ytcard/ascription/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">粤通卡查询</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/ytcard/platenumber/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">加油卡查询</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/daoexcel/leadanalyze/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">加油站查询</cite>

                                </a>
                            </li >
                        </ul>
                    </li>
                </ul>
            </li>
            <!-- 采购异常情况排查 -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">采购异常情况排查</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>拆单倾向分析</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/dismantling/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购合同</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/cleanInventory/dismant/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">即时清洁</cite>

                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>围标串标分析</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/biddes/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购合同</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/cleanInventory/biddes/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">即时清洁</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/info/companyinfo/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">公开采购</cite>

                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>陪标组合分析</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/sort/company/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购合同</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/cleanInventory/sort/company/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">即时清洁</cite>
                                </a>
                            </li >
                        </ul>
                    </li >
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>单一来源采购分析</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/source/proportion/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购类型所占比例</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/supplier/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量及金额最多供应商</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/depart/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量及金额最多经办部门</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/type/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量及金额最多合同类型</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/moneymax/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量最高的前10个项目</cite>

                                </a>
                            </li>

                        </ul>
                    </li >
                    <li>
                        <a _href="${home}/reportform/exclude/undisclosed/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">应招未招排查</cite>

                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/exclude/reverse/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">合同倒签排查</cite>

                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/form/contractlessthreebytype/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">侯选数不达标排查</cite>

                        </a>
                    </li >
                </ul>
            </li>
            <!-- 公车使用分析 -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">公车使用分析</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                    </li>
                </ul>
            </li>
            <!-- 关键人员监督 -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">关键人员监督</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>任职关联度排查</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/tractorcontract/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购合同</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="#">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">即时清结</cite>
                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>经商情况排查</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="#">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">领导本人经商排查</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/cleanInventory/relativesrel/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">亲属围绕企业经商排查</cite>
                                </a>
                            </li >
                        </ul>
                    </li>
                </ul>
            </li>
            <!-- 数据采集维护 -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">数据采集维护</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>采购数据</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/daoexcel/purchase/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入采购合同清单</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/daoexcel/account/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入合同清单</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="#">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入公开采购清单</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/daoexcel/cleaninventory/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入即时清结清单</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/daoexcel/kinsfolk/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入经商申报清单</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/daoexcel/bidcompanyinfo/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入公开采购投标公司信息-new</cite>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:">
                                    <i><img src="${home}/image/icon7.png"/></i>
                                    <cite>机器人实时取数?</cite>
                                    <i class="iconfont nav_right">&#xe697;</i>
                                </a>
                                <ul class="sub-menu">
                                    <li>
                                        <a _href="${home}/reportform/daoexcel/updata/query.html">
                                            <i class="iconfont">&#xe6a7;</i>
                                            <cite>根据时间段获取合同数据</cite>
                                        </a>
                                    </li >
                                    <li>
                                        <a _href="${home}/reportform/exclude/single/query.html">
                                            <i class="iconfont">&#xe6a7;</i>
                                            <cite style="font-size: 12px;">根据合同编号获取单条合同数据</cite>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a _href="${home}/reportform/info/rulersinfo/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">招标规则文档</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="#">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">领导履职经历</cite>
                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/daoexcel/kinsfolk/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">领导亲属资料</cite>
                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>公车数据</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/daoexcel/pdfrecognition/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">粤通卡发票识别</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/ytcard/mileageentryinterface/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">车辆里程数录入</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/daoexcel/refuelbill/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">导入IC卡对账单</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="#">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">车牌油卡关联</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="#">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">车牌粤通卡关联</cite>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
            <!-- 系统管理 -->
            <li style="background: url(${home}/image/icon6.png)no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">系统管理</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${home}/system/role/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">角色配置</cite>

                        </a>
                    </li>
                    <li>
                        <a _href="${home}/system/user/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">用户配置</cite>

                        </a>
                    </li>
                    <li>
                        <a _href="">
                            <%--<a _href="${home}/system/authority/query.html">--%>
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>功能权限录入</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="">
                            <%--<a _href="${home}/system/sysparameter/query.html">--%>
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>系统参数管理</cite>
                        </a>
                    </li >
                </ul>
            </li>




























            <!-- 20181113 zxc 统计分析
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">-统计分析</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${home}/reportform/form/allcontract/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">常规合同查看</cite>

                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/form/contractmost/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">签订数量最多的供应商</cite>

                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/form/moneymost/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">签订金额最多的供应商</cite>

                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/form/contractandmoney/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">签订数量及金额均最多的供应商</cite>

                        </a>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>按合同类型排序</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/form/contractmostbytype/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">签订数量最多的供应商</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/form/moneymostbytype/query.html"><%--======--%>
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">签订金额最高的供应商</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/form/contractandmoneybytype/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">签订数量及金额最高的供应商</cite>

                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>按经办部门排序</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/form/contractmostbydept/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">签订数量最多的供应商</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/form/moneymostbydept/query.html"><%--==========--%>
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">签订金额最高的供应商</cite>

                                </a>
                            </li >
                            <li>
                                <a _href="${home}/reportform/form/contractandmoneybydept/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">签订数量及金额最高的供应商</cite>

                                </a>
                            </li >
                        </ul>
                    </li>
                    <li>
                        <a _href="${home}/reportform/form/contractlessthreebytype/query.html"><%--=========--%>
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">某项目类型供应商不超过3个</cite>

                        </a>
                    </li>
                </ul>
            </li>
            -->
            <!-- 20181113 zxc 合同风险排查
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">-合同风险排查</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>单一来源方式</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/exclude/source/proportion/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">采购类型所占比例</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/supplier/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量及金额最多供应商</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/depart/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量及金额最多经办部门</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/type/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量及金额最多合同类型</cite>

                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/exclude/source/moneymax/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">合同数量最高的前10个项目</cite>

                                </a>
                            </li>

                        </ul>
                    <li>
                        <a _href="${home}/reportform/exclude/biddes/query.html">
                            <%--<a _href="${home}/reportform/info/companyinfo/query.html">--%>
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">合同围标可能性排查</cite>

                        </a>
                    </li>
                    <%--<h2 style="margin:5px"><a href="#" onclick="sys_menu_clicked(this,'${home}/reportform/exclude/biddes/query.html');">  围标可能性排查</a></h2>--%>

                    <li>
                        <a _href="${home}/reportform/exclude/relatives/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">领导亲属经商可能性排查</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/exclude/dismantling/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">合同拆单可能性排查</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/exclude/reverse/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">合同倒签可能性排查</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/exclude/undisclosed/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">应招未招,应公开未公开</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/exclude/tractorcontract/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">拖拉机合同排查</cite>
                        </a>
                    </li>

                </ul>
            </li>
            -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">--即时清结项目</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">

                    <li>
                        <a _href="${home}/reportform/cleanInventory/show/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">数据查看</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/cleanInventory/updata/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">根据时间更新数据-no</cite>
                        </a>
                    </li>

                    <li>
                        <a _href="${home}/reportform/cleanInventory/dismant/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">即时清结拆单可能性分析</cite>
                        </a>
                    </li>


                    <li>
                        <a _href="${home}/reportform/cleanInventory/sort/company/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">中标公司陪标可能性排查</cite>
                        </a>
                    </li>

                    <li>
                        <a _href="${home}/reportform/cleanInventory/biddes/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">即时清结围标可能性排查</cite>
                        </a>
                    </li>


                    <li>
                        <a _href="${home}/reportform/cleanInventory/relativesrel/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">领导亲属经商可能性排查</cite>
                        </a>
                    </li>
                </ul>
            </li>

            <%--<li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">--%>
                <%--<a href="javascript:">--%>
                    <%--<cite style="padding-left: 20px;">粤通卡功能分析</cite>--%>
                    <%--<i class="iconfont nav_right">&#xe697;</i>--%>
                <%--</a>--%>
                <%--<ul class="sub-menu">--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/daoexcel/pdfrecognition/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">粤通卡发票识别</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/daoexcel/examinepdf/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">查看粤通卡数据</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/daoexcel/leadanalyze/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">查看加油站清单表</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/ytcard/ascription/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">查看佛山本部粤通卡号</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/ytcard/platenumber/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">查看车牌号和油卡对应关系表</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/ytcard/monthcarout/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">公车私用及违规排查</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/ytcard/outinformation/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">查看车辆出入停车场信息</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a _href="${home}/reportform/ytcard/mileagetypein/query.html">--%>
                            <%--<i class="iconfont">&#xe6a7;</i>--%>
                            <%--<cite style="font-size: 12px;">里程录入界面</cite>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>
            <%--</li>--%>
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">--车辆使用情况分析</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>数据录入界面-no</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/ytcard/carulestypein/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">车辆规则录入界面-no</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/ytcard/insertholidaytime/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">节假日时间录入界面-no</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/ytcard/mileageentryinterface/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">里程录入界面</cite>
                                </a>
                            </li>
                            <%--<li>
                                <a _href="${home}/reportform/ytcard/carrepairfeetypein/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">修车费录入界面</cite>
                                </a>
                            </li>--%>
                        </ul>
                    <li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>车辆数据查看界面</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li><%--11--%>
                                <a _href="${home}/reportform/daoexcel/examinepdf/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">查看粤通卡数据</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/daoexcel/leadanalyze/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">查看加油站清单表</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/ytcard/ascription/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">查看佛山本部粤通卡号</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/ytcard/platenumber/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">查看车牌号和油卡对应关系表</cite>
                                </a>
                            </li>
                            <li>
                                <a _href="${home}/reportform/ytcard/monthcarout/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">查看车辆出入停车场信息</cite>
                                </a>
                            </li>
                        </ul>
                    <li>
                    <li>
                        <a href="javascript:">
                            <i><img src="${home}/image/icon7.png"/></i>
                            <cite>车辆文件识别和导入</cite>
                            <i class="iconfont nav_right">&#xe697;</i>
                        </a>
                        <ul class="sub-menu">
                            <li>
                                <a _href="${home}/reportform/daoexcel/pdfrecognition/query.html">
                                    <i class="iconfont">&#xe6a7;</i>
                                    <cite style="font-size: 12px;">粤通卡发票识别</cite>
                                </a>
                            </li>
                        </ul>
                    <li>
                </ul>
            </li>
            <%--=================--%>
            <!-- zxc
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">--导入数据</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${home}/reportform/daoexcel/purchase/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>导入采购合同台账单</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/daoexcel/kinsfolk/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>导入领导亲属经商库</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/daoexcel/cleaninventory/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>导入即时清结清单</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/daoexcel/refuelbill/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>导入IC卡台账对账单</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/daoexcel/pdfrecognition/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">导入粤通卡发票识别</cite>
                        </a>
                    </li>
                    <li>
                        <a _href="${home}/reportform/daoexcel/bidcompanyinfo/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>导入公开采购投标公司信息-no</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/daoexcel/account/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>导入合同台账单表</cite>
                        </a>
                    </li >
                </ul>
            </li>
            -->
            <!-- zxc
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">--机器人取数</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${home}/reportform/daoexcel/updata/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite>根据时间段获取合同数据</cite>
                        </a>
                    </li >
                    <li>
                        <a _href="${home}/reportform/exclude/single/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">根据合同编号获取单条合同数据</cite>
                        </a>
                    </li>
                </ul>
            </li>
             -->
            <li style="background: url(${home}/image/icon6.png) no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">--招标规则信息</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${home}/reportform/info/companyinfo/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">公开采购投标公司信息</cite>
                        </a>
                    </li>

                    <li>
                        <a _href="${home}/reportform/info/rulersinfo/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">招标规则文档说明</cite>
                        </a>
                    </li>
                   <%-- <li>
                        <a _href="${home}/reportform/info/tenderingrules/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">采购方式适用列表</cite>
                        </a>
                    </li>--%>

                    <li>
                        <a _href="${home}/reportform/organizational/contracttype/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">合同类型查看-no</cite>
                        </a>
                    </li>
                </ul>
            </li>
            <!-- zxc
            <li style="background: url(${home}/image/icon6.png)no-repeat; background-size: 240px auto;">
                <a href="javascript:">
                    <cite style="padding-left: 20px;">--供应商信息库</cite>
                    <i class="iconfont nav_right">&#xe697;</i>
                </a>
                <ul class="sub-menu">
                    <li>
                        <a _href="${home}/reportform/info/companylegal/query.html">
                            <i class="iconfont">&#xe6a7;</i>
                            <cite style="font-size: 12px;">供应商信息查看</cite>
                        </a>
                    </li>
                </ul>
            </li>
            -->


        </ul>
    </div>
</div>
<!-- <div class="x-slide_left"></div> -->
<!-- 左侧菜单结束 -->
<!-- 右侧主体开始 -->
<div class="page-content">
    <div class="layui-tab tab" lay-filter="xbs_tab" lay-allowclose="false">
        <ul class="layui-tab-title" style="background: url(${home}/image/top.png);">
            <li class="home"><i class="layui-icon">&#xe68e;</i>我的桌面</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <iframe src='${home}/common/welcome.jsp' frameborder="0" scrolling="yes" class="x-iframe" id="frameQuery"></iframe>
            </div>
        </div>
    </div>
</div>
<div class="page-content-bg">
    <span class="show-icon"></span>
</div>
<!-- 右侧主体结束 -->
<!-- 中部结束 -->
<!-- 底部开始 -->
<div class="footer" style="background-color: #2f8dd3;height: 41px;"></div>

<!-- 底部结束 -->
</body>
</html>

