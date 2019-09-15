<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>台账列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body, .mui-content {
            background-color: #fff;
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .ui-select-head {
            text-align: center;
        }

        .ui-select-head,
        .ui-select-head li,
        .ui-select-head select {
            background-color: #efeff4;
        }

        .ui-select-head select {
            padding: 0px 5px;
            margin-bottom: 0px;
            line-height: 18px;
        }

        .left-img {
            width: 40px;
            height: 40px;
            display: block;
            border-radius: 50%;
            color: #fff;
            margin-top: 12px;
            margin-right: 10px;
            text-align: center;
        }

        .buytime-span {
            font-size: 14px;
            color: #8f8f94;
        }

        .mui-media .mui-ellipsis {
            color: #000;
            padding-right: 20px;
            padding-top: 4px;
        }

        .left-color-zhise {
            background-color: #d0659b;
        }

        .left-color-huang {
            background-color: #ff972d;
        }

        .left-color-green {
            background-color: #5ed198;
        }

        .left-color-blue {
            background-color: #669aff;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head">
    <h1 class="mui-title">台账列表</h1>
</header>

<div class="mui-content">
    <ul class="mui-table-view mui-grid-view ui-select-head">
        <li class="mui-table-view-cell mui-col-xs-3">
            <select id="selectCompany">
                <option value="0">请选择</option>
                <c:forEach items="${depts}" var="d">
                    <option value="${d.deptid}">${d.deptname}</option>
                </c:forEach>
            </select>
        </li>
        <%--<li class="mui-table-view-cell mui-col-xs-3 mui-col-sm-3">--%>
        <%--<select>--%>
        <%--<option value="0">请选择</option>--%>
        <%--</select>--%>
        <%--</li>--%>
        <%--<li class="mui-table-view-cell mui-col-xs-3 mui-col-sm-3">--%>
        <%--<select>--%>
        <%--<option value="0">请选择</option>--%>
        <%--</select>--%>
        <%--</li>--%>
        <%--<li class="mui-table-view-cell mui-col-xs-3 mui-col-sm-3">--%>
        <%--<select>--%>
        <%--<option value="0">请选择</option>--%>
        <%--</select>--%>
        <%--</li>--%>
    </ul>

    <div id="detaileMain">
        <%--<div class="mui-loading ui-margin-top-15">--%>
        <%--<div class="mui-spinner">--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<ul class="mui-table-view">--%>
        <%--<li class="mui-table-view-cell mui-media">--%>
        <%--<a href="${home}/four/show.html?id=${d.id}&type=2" class="mui-navigate-right">--%>
        <%--<div class="mui-media-object mui-pull-left"><span class="left-img left-color-green">1</span></div>--%>
        <%--<div class="mui-media-body">--%>
        <%--部门名称--%>
        <%--<p class="mui-ellipsis">台账物品名称</p>--%>
        <%--<span class="buytime-span">采购时间：2008年10月</span>--%>
        <%--</div>--%>
        <%--</a>--%>
        <%--</li>--%>
        <%--</ul>--%>
    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}'

    var selectInput = {
        init: function () {
            var self = wxgh.query('.ui-select-head')
            this.init_change_event(wxgh.getElement('selectCompany'))

            this.self = self;
        },
        get_depts: function (parentid) {
            var url = homePath + '/wx/four/get.json?parentid=' + parentid
            mui.getJSON(url, function (res) {
                if (res.data && res.data.length > 0) {
                    var liItem = selectInput.createItem(res.data)
                    selectInput.self.appendChild(liItem)
                }
            })
        },
        init_change_event: function (el) {
            var self = this
            el.addEventListener('change', function () {

                var e_Load = document.createElement('div')
                e_Load.className = 'mui-loading ui-margin-top-15'
                e_Load.innerHTML = '<div class="mui-spinner"></div>'
                page.self.innerHTML = ''
                page.self.appendChild(e_Load)

                var parentid = this.value

                page.request(parentid)

                self.removeChild(el.parentNode)

                if (parentid != 0) self.get_depts(parentid)
            })
        }, removeChild: function (self) {
            var next_e = self.nextElementSibling
            var nexts = []
            if (next_e) {
                do {
                    nexts.push(next_e)
                    next_e = next_e.nextElementSibling
                } while (next_e)
            }
            if (nexts) {
                for (var i = 0; i < nexts.length; i++) {
                    this.self.removeChild(nexts[i])
                }
            }
        },
        createItem: function (depts) {
            var e_li = document.createElement('li')
            e_li.className = 'mui-table-view-cell mui-col-xs-3'

            var e_select = document.createElement('select')
            this.init_change_event(e_select)

            var e_defOptions = document.createElement('option')
            e_defOptions.innerHTML = '<option value="0">请选择</option>'
            e_select.appendChild(e_defOptions)

            for (var i = 0; i < depts.length; i++) {
                var e_options = document.createElement('option')
                e_options.value = depts[i].deptid
                e_options.innerText = depts[i].deptname
                e_select.appendChild(e_options)
            }

            e_li.appendChild(e_select)
            return e_li
        }
    }

    var page = {
        init: function () {
            var self = wxgh.getElement('detaileMain')

            selectInput.init()

            this.self = self
        },
        request: function (deptid) {
            var url = homePath + '/wx/four/detaile_list.json?deptid=' + deptid
            mui.getJSON(url, function (res) {
                page.create_Details(res.data)
            })
        },
        create_Details: function (details) {
            this.self.innerHTML = ''

            if (details && details.length > 0) {
                var e_ul = document.createElement('ul')
                e_ul.className = 'mui-table-view'
                this.self.innerHTML = ''

                for (var i = 0; i < details.length; i++) {
                    e_ul.appendChild(this.create_item(details[i], i + 1))
                }
                this.self.appendChild(e_ul)
            } else {
                this.self.innerHTML = '<div class="mui-content-padded mui-text-center">暂无台账明细列表哦</div>'
            }
        },
        create_item: function (d, i) {
            var e_li = document.createElement('li')
            e_li.className = 'mui-table-view-cell mui-media'

            var e_a = document.createElement('a')
            e_a.className = 'mui-navigate-right'
            e_a.href = homePath + '/wx/four/show.html?id=' + d.id + '&type=2'

            var e_obj = document.createElement('div')
            e_obj.className = 'mui-media-object mui-pull-left'
            e_obj.innerHTML = '<span class="left-img left-color-green">' + i + '</span>'

            e_body = document.createElement('div')
            e_body.className = 'mui-media-body'
            e_body.innerHTML = d.deptStr + '<p class="mui-ellipsis">' + d.fpcName + '</p><span class="buytime-span">采购时间：' + (d.buyTime ? d.buyTime : '未知时间') + '</span>'

            e_a.appendChild(e_obj)
            e_a.appendChild(e_body)

            e_li.appendChild(e_a)

            return e_li
        }
    }

    window.onload = page.init()

</script>
</body>

</html>
