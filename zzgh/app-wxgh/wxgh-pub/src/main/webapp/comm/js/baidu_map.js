/**
 * Created by Administrator on 2017/4/24.
 */
function Baidu(id) {
    //引入js文件
    //	var $js = $('<script src="http://api.map.baidu.com/api?type=quick&ak=' + ak + '&v=1.0"></script>')
    //	$('body').append($js)

    var $cnt = $('#' + id)

    var CSS = '.mui-scroll .ui-addr-title {background-color: #fff;padding: 2px 10px;margin: 5px 10px;font-size: 18px;color: #333;border-left: 2px solid orange;}' +
        '.mui-scroll .mui-table-view:before,.mui-scroll .mui-table-view:after {height: 0;}'
    //append css
    var $style = $('<style></style>')
    $style.html(CSS)
    $('head').append($style)

    //创建搜索框
    var $search = $('<div class="mui-content-padded" id="searchDiv">' +
        '<div class="mui-input-row mui-search">' +
        '<input id="baiduSearchInput" type="search" placeholder="输入您要搜索的地址" class="mui-input-clear"/>' +
        '</div> <div id="searchResultPanel" style="border:1px solid #C0C0C0;width:150px;height:auto; display:none;"></div>' +
        '</div>')
    $cnt.append($search)
    $('#baiduSearchInput').css('margin-bottom', '0')
    //创建地图
    var $map = $('<div style="width:100%;height:240px;"></div>')
    $cnt.append($map)

    //创建loading
    var $loading = $('<div class="mui-loading"><div class="mui-spinner"></div></div>')

    //创建附近地址
    var $addrList = $('<div class="mui-scroll-wrapper"><div class="mui-scroll"><h5 class="ui-addr-title">附近地址</h5></div></div>')
    $cnt.append($addrList)
    $addrList.find('.mui-scroll').append($loading)

    this.$search = $search
    this.$map = $map
    this.$addrList = $addrList
    this.$loading = $loading

    this.resize()
    this.initMap()

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
}
Baidu.prototype.initMap = function () {

    var self = this

    var m = new BMap.Map(this.$map[0])
    var point = new BMap.Point(116.404, 39.915); // 创建点坐标
    m.centerAndZoom(point, 11); // 初始化地图,设置中心点坐标和地图级别

    //添加定位
    var navigationControl = new BMap.NavigationControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        type: BMAP_NAVIGATION_CONTROL_LARGE,
        enableGeolocation: true
    });
    m.addControl(navigationControl);

    // 添加定位控件
    var geolocationControl = new BMap.GeolocationControl();
    geolocationControl.addEventListener("locationSuccess", function (e) {
        self.addMarker(e.point)
        self.nearby(e.point)
    });
    geolocationControl.addEventListener("locationError", function (e) {
        // 定位失败事件
        alert('定位失败');
    });
    m.addControl(geolocationControl);

    //创建自动完成对象
    var bac = new BMap.Autocomplete({
        input: 'baiduSearchInput',
        location: m
    });

    var $searchResult = self.$search.find('#searchResultPanel')
    bac.addEventListener('onhighlight', function (e) {
        console.log(e)
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
        $searchResult.html(str)
    })
    var myValue;
    bac.addEventListener('onconfirm', function (e) {
        console.log(e)
        var _value = e.item.value;
        myValue = _value.province + _value.city + _value.district + _value.street + _value.business;

        m.clearOverlays(); //清除地图上所有覆盖物
        function myFun() {
            var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
            self.addMarker(pp)
            self.nearby(pp)
        }

        var local = new BMap.LocalSearch(m, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);
    })
    this.m = m
    geolocationControl.location() //开始定位
}
Baidu.prototype.addMarker = function (p) {
    if (!this.marke) {
        this.marke = new BMap.Marker(p)
        this.m.addOverlay(this.marke); //添加标注
    } else {
        this.marke.setPosition(p)
    }
    this.m.centerAndZoom(p, 18);
}
Baidu.prototype.resize = function () {
    var listHeight = this.$search.outerHeight(true) + this.$map.outerHeight(true) + 50
    this.$addrList.css('top', listHeight + 'px')
}
Baidu.prototype.getSelected = function () {
    var $el = this.$addrList.find('.mui-scroll .mui-table-view-radio li.mui-selected')
    if ($el.length > 0) {
        return $el.data('data')
    }
}
Baidu.prototype.nearby = function (p) {
    var self = this
    if (!self.geo) {
        self.geo = new BMap.Geocoder()
    }
    self.geo.getLocation(p, function (rs) {
        var pois = []
        pois.push({
            address: rs.address,
            point: rs.point
        })
        var ps = rs.surroundingPois
        if (ps && ps.length > 0) {
            for (var i in ps) {
                pois.push({
                    address: ps[i].address,
                    title: ps[i].title,
                    point: ps[i].point
                })
            }
        }

        self.clearItem()
        for (var i in pois) {
            self.addItem(pois[i])
        }
    })
}
Baidu.prototype.clearItem = function () {
    this.$addrList.find('.mui-scroll').empty()
}
Baidu.prototype.addItem = function (a) {
    var self = this
    var $scroll = self.$addrList.find('.mui-scroll')
    var $ul
    if ($scroll.find('.mui-loading').length > 0) {
        $scroll.find('.mui-loading').remove()
    }

    if ($scroll.find('.mui-table-view').length <= 0) {
        $ul = $('<ul class="mui-table-view mui-table-view-radio"></ul>')
        $scroll.append($ul)
        $ul.on('selected', function (e) {
            var d = $(e.target).data('data')
            self.addMarker(d.point)
        })
    } else {
        $ul = $scroll.find('.mui-table-view')
    }

    var $item = $('<li class="mui-table-view-cell"><a class="mui-navigate-right">' + (a.title ? a.title : a.address) +
        '<p>' + a.address + '</p></a></li>')
    $item.data('data', a)
    $ul.append($item)

    if ($item.index() == 0) {
        $item.addClass('mui-selected')
    }
}