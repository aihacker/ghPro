<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/22
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>场馆预约</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/font-awesome-4.7.0/css/font-awesome.min.css"/>
    <style>
        #uiTopSegmented {
            background-color: #fff;
        }

        #uiTopSegmented .ui-top-segmented {
            width: 60%;
            margin-left: auto;
            margin-right: auto;
        }

        #uiTopSegmented .ui-top-segmented .mui-control-item {
            line-height: 30px;
        }

        #sliderSegmentedControl .ui-m-icon {
            font-size: 28px;
            display: block;
        }

        #sliderSegmentedControl {
            height: 60px;
        }

        #sliderSegmentedControl .mui-scroll {
            height: 60px;
        }

        #sliderSegmentedControl .ui-m-txt {
            color: #000;
        }

        #sliderSegmentedControl {
            margin: 5px 0;
            border: none;
        }

        #sliderSegmentedControl .mui-control-item {
            padding: 6px 15px;
            line-height: 24px;
        }

        #sliderSegmentedControl .mui-control-item.mui-active {
            color: #2AC845;
            background-color: transparent;
        }

        #placeTableList:before,
        #placeTableList:after {
            height: 0;
        }

        #placeTableList .mui-media-object {
            max-width: 90px;
            line-height: 70px;
            height: 70px;
        }

        #headSegmented {
            width: 40%;
            line-height: 30px;
        }

        #headSegmented .mui-control-item {
            line-height: 26px;
            color: #fff;
            opacity: 0.6;
        }

        #headSegmented.mui-segmented-control-inverted .mui-control-item.mui-active {
            color: #fff;
            border-bottom: 2px solid #fff;
            background: 0 0;
            opacity: 1;
        }

        #searchInput {
            position: absolute;
            right: 15px;
            top: 0px;
        }

        #firstPlaceDiv {
            background-color: #fff;
            position: relative;
            padding-top: 4px;
        }

        #firstPlaceDiv a {
            display: block;
            margin: 5px 10px 10px 10px;
            position: relative;
        }

        #firstPlaceDiv .ui-image {
            width: 100%;
            max-height: 180px;
            min-height: 180px;
            overflow: hidden;
            display: flex;
            justify-content: center;
            align-items: center;
            -webkit-border-radius: 2px;
            border-radius: 2px;
        }

        #firstPlaceDiv .ui-image img {
            width: 100%;
        }

        #firstPlaceDiv .ui-first-bg {
            display: block;
            background-color: #000;
            opacity: .6;
            bottom: -10px;
            width: 100%;
            position: absolute;
            padding: 4px 10px;
            -webkit-border-radius: 2px;
            border-radius: 2px;
        }

        .mui-table-view-cell .ui-price {
            color: #eb3c25;
        }

        .mui-table-view-cell .ui-title {
            color: #000;
        }

        .mui-table-view-cell .ui-category {
            font-size: 12px;
        }

        .ui-star-div {
            font-size: 12px;
        }

        .ui-other .mui-media-object {
            margin-top: 8px;
        }

        .ui-text-green {
            color: #32c661;
        }

        .ui-text-yellow {
            color: #eb6f25;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--&lt;%&ndash;<div id="headSegmented" class="mui-segmented-control mui-segmented-control-inverted">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<a class="mui-control-item mui-active">&ndash;%&gt;--%>
    <%--&lt;%&ndash;内部&ndash;%&gt;--%>
    <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<a class="mui-control-item">&ndash;%&gt;--%>
    <%--&lt;%&ndash;商家&ndash;%&gt;--%>
    <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--<h1 class="mui-title">场馆预约</h1>--%>
    <%--&lt;%&ndash;<a id="searchInput" class="mui-icon mui-icon-search"></a>&ndash;%&gt;--%>
<%--</header>--%>

<div class="mui-content">
    <div id="uiTopSegmented">
        <div id="sliderSegmentedControl" class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control">
            <div class="mui-scroll">
                <c:forEach items="${cates}" var="c" varStatus="i">
                    <a data-id="${c.id}" class="mui-control-item ${i.index eq 0?'mui-active':''}">
                        <span class="fa fa-futbol-o ui-m-icon"></span>
                        <small class="ui-m-txt">${c.name}</small>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>

    <div id="mainScrollWrapper" class="mui-scroll-wrapper" style="">
        <div class="mui-scroll">
            <div class="ui-margin-top-15">

                <div id="firstPlaceDiv">
                    <a href="#">
                        <div class="ui-image">
                            <img src="${home}/weixin/image/four/a3.jpg"/>
                        </div>
                        <p class="mui-ellipsis ui-first-bg">健康猫运动生活馆 广州3号馆</p>
                    </a>
                </div>

                <ul class="mui-table-view" id="placeTableList">
                </ul>
            </div>
        </div>
    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">

    var homePath = '${home}';

    mui.init()
    mui('.mui-scroll-wrapper').scroll({
        indicators: false,
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    var page = {
        init: function () {
            this.resize();

            this.placeListUl = wxgh.getElement('placeTableList');

            var cateSlider = wxgh.getElement('sliderSegmentedControl');

            this.placeType = 1;
            this.cateId = cateSlider.querySelector('.mui-control-item').getAttribute('data-id');


            mui('#sliderSegmentedControl').on('tap', '.mui-control-item', function (e) {
                e.preventDefault();
                page.cateId = this.getAttribute('data-id');
                page.request(page.cateId, page.placeType)
            })
            this.defaultLi = document.createElement('li')
            this.defaultLi.className = 'mui-table-view-cell'
            this.defaultLi.innerHTML = '<div class="mui-loading mui-text-center"><div class="mui-spinner"></div><p>加载中...</p></div>'

            this.request(this.cateId, page.placeType);
        },
        resize: function () {
            var slideDiv = wxgh.getElement('uiTopSegmented');

            wxgh.getElement('mainScrollWrapper').style.top = (slideDiv.offsetHeight + 44) + 'px';
        },
        request: function (typeInt, placeType) {
            page.placeListUl.innerHTML = '';
            page.placeListUl.appendChild(page.defaultLi)
            var url = homePath + '/wx/entertain/nanhai/place/place_list.json?action=';
            var info = {
                typeInt: typeInt,
                placeType: placeType
            };
            mui.getJSON(url, info, function (res) {
                if (res.ok) {
                    page.init_ad(res.data.place); //广告商
                    page.create_list(res.data.places); //列表显示
                }
            })
        },
        init_ad: function (p) {
            if (p) {
                var self = wxgh.getElement('firstPlaceDiv');
                self.querySelector('a').href = homePath + '/wx/entertain/nanhai/place/show.html?id=' + p.id;

                var img = wxgh.get_image(p.coverImgPath)

                self.querySelector('img').src = img ? img : homePath + '/weixin/image/four/a3.jpg';
                self.querySelector('.ui-first-bg').innerText = p.address;
            }
        },
        create_list: function (places) {
            if (places) {
                this.placeListUl.innerHTML = '';
                if (places && places.length > 0) {
                    for (var i = 0; i < places.length; i++) {
                        var item = this.create_item(places[i]);
                        this.placeListUl.appendChild(item);
                    }
                } else {
                    this.placeListUl.innerHTML = '<li class="mui-table-view-cell"><div class="mui-content-padded mui-text-center ui-text-info">暂无场地哦</div></li>'
                }
            }
        },
        create_item: function (p) {
            var e_li = document.createElement('li');
            e_li.className = 'mui-table-view-cell mui-media';

            var e_a = document.createElement('a')
            e_a.href = homePath + '/wx/entertain/nanhai/place/show.html?id=' + p.id;

            var e_img = document.createElement('img');
            e_img.className = 'mui-media-object mui-pull-left';
            var img = wxgh.get_image(p.imgPath);
            e_img.src = img ? img : homePath + '/image/common/nopic.gif';

            var e_body = document.createElement('div')
            e_body.className = 'mui-media-body'
            e_body.innerHTML = '<h5 class="ui-title">' + p.title + '</h5>'
                    + '<p class="mui-ellipsis ui-category">' + p.cate + '</p>'
                    + '<small class="ui-price"><span class="fa fa-cny"></span> ' + p.price + '/h</small>'

            e_a.appendChild(e_img)
            e_a.appendChild(e_body)
            e_li.appendChild(e_a)
            return e_li;
        }
    }
    window.onload = page.init();
</script>
</body>

</html>
