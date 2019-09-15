//百度地图相关变量
var baiduConfig = {
    ak: 'uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua',
    default_city: "广州",
    zoom: 15,
    circle_distance: 400
}
var map, marker, geocoder, infowindow, isLoad = false,
    isLocation = false,
    localSearch,
    index = 0,
    markers = [],
    loading;

function baiduPageInit() {
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
    init_size()
    baiduInit()
    $(window).on('resize', init_size())

    //确定按钮点击事件
    $('#mapOkBtn').on('tap', function () {
        var $el;
        if (index == 0) $el = $('#nearByItem')
        else if (index == 1) $el = $('#badmintonItem')
        else if (index == 2) $el = $('#pingpongballItem')
        else if (index == 3) $el = $('#basketballItem')

        var poi = $el.find('.mui-table-view-radio li.mui-selected').attr('data-poi')
        poi = JSON.parse(poi)

        $('input[name=address]').val(poi.address)
        $('input[name=lat]').val(poi.point.lat)
        $('input[name=lng]').val(poi.point.lng)
        if (poi.title && poi.address != poi.title) {
            $('input[name=addrTitle]').val(poi.title)
            $('textare[name=addrRemark]').val(poi.title)
            $('#addressSmall').text(poi.title)
        } else {
            $('#addressSmall').text(poi.address)
        }
        mui.back();
    })

    $('#cateSlider').on('slide', function (e) {
        index = e.originalEvent.detail.slideNumber;
        var point = marker.getPosition();
        initAddressList(index, point);
    })
}

function initAddressList(index, p) {
    for (var i = 0; i < markers.length; i++) {
        map.removeOverlay(markers[i])
    }
    markers = []
    switch (index) {
        case 0:
            var $el = $('#nearByItem .mui-scroll')
            $el.html('<div class="mui-loading"><div class="mui-spinner"></div></div>')
            getNearByAddress(p);
            break;
        case 1:
            var $el = $('#badmintonItem .mui-scroll')
            $el.html('<div class="mui-loading"><div class="mui-spinner"></div></div>')
            new getSearchPoi('羽毛', p, function (item) {
                if (typeof item === 'string') {
                    $el.html(item);
                } else {
                    var loading = $el.find('.mui-loading');
                    if (loading) {
                        loading.remove()
                    }
                    $el.append($(item));
                }
            })
            break;
        case 2:
            var $el = $('#pingpongballItem .mui-scroll')
            $el.html('<div class="mui-loading"><div class="mui-spinner"></div></div>');
            new getSearchPoi('乒乓球', p, function (item) {
                if (typeof item === 'string') {
                    $el.html(item);
                } else {
                    var loading = $el.find('.mui-loading');
                    if (loading) {
                        loading.remove()
                    }
                    $el.append($(item));
                }
            })
            break;
        case 3:
            var $el = $('#basketballItem .mui-scroll')
            $el.html('<div class="mui-loading"><div class="mui-spinner"></div></div>');
            new getSearchPoi('篮球', p, function (item) {
                if (typeof item === 'string') {
                    $el.html(item);
                } else {
                    var loading = $el.find('.mui-loading');
                    if (loading) {
                        loading.remove()
                    }
                    $el.append($(item));
                }
            })
            break;
    }
}

/**
 * 百度地图初始化工作
 */
function baiduInit() {
    if (!map) {
        map = new BMap.Map("mapDiv");
        map.centerAndZoom(baiduConfig.default_city, baiduConfig.zoom);
        //						map.enableScrollWheelZoom(); //启用滚轮放大缩小
    }

    //添加定位控件
    var geolocationControl = new BMap.GeolocationControl({
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
        showAddressBar: false
    });
    //定位成功事件
    geolocationControl.addEventListener("locationSuccess", function (e) {
        addMarker(e.point, parseAddress(e.addressComponent))
        getNearByAddress(e.point)
        loading.hide()
    });
    //定位失败事件
    geolocationControl.addEventListener("locationError", function (error) {
        loading.hide();
        console.error(error);
        mui.toast('定位失败');
    });
    map.addControl(geolocationControl);

    //地图加载完成事件
    map.addEventListener("tilesloaded", function () {
        if (!isLoad) {
            geolocationControl.location(); //开始定位
            isLoad = true;
            if (!loading) {
                loading = ui.loading('定位中...')
            }
            loading.show()
        }
    });

    searchInit(map) //初始化搜索控件
}

function createMarker(poi) {
    var marker = new BMap.Marker(poi.point);
    marker.addEventListener('click', function () {

        var el = document.createElement("div");
        el.className = 'info-panel';

        el.innerHTML = '<h4>' + poi.title + '</h4><a href="' + poi.detailUrl + '" class="mui-pull-right">详情</a><span>地址：' + poi.address + '</span><br /><span>电话：' + (poi.phoneNumber ? poi.phoneNumber : '未知电话') + '</span>';

        var infoWindow = getInfoWindow(el);
        marker.openInfoWindow(infoWindow);
    });

    map.addOverlay(marker);
    markers.push(marker);
    return marker;
}

function addMarker(p, a, t) {
    if (!marker) {
        var markeIcon = new BMap.Icon(homePath + "/weixin/image/act/location.png", new BMap.Size(30, 30))

        marker = new BMap.Marker(p, {
            enableDragging: true,
            raiseOnDrag: true,
            icon: markeIcon
        })

        marker.addEventListener("click", function () {
            var address = getAddress(function (res) {
                marker.openInfoWindow(getInfoWindow(res));
            });
        });

        //拖动中事件
        marker.addEventListener("dragging", function (e) {
        });

        //拖拽结束
        marker.addEventListener('dragend', function (e) {
            initAddressList(index, e.point)
        });
        marker.setAnimation(BMAP_ANIMATION_BOUNCE);
        map.addOverlay(marker)
    } else {
        marker.setPosition(p);
    }

    map.centerAndZoom(p, baiduConfig.zoom);
}

function getSearchPoi(key, p, func) {
    if (!localSearch) {
        localSearch = new BMap.LocalSearch(map)
    }
    localSearch.setSearchCompleteCallback(function (rs) {
        var item;
        if (localSearch.getStatus() == BMAP_STATUS_SUCCESS) {
            if (!rs) {
                item = '<div class="mui-content-padded mui-text-center">搜索失败</div>';
            }
            var numb = rs.getCurrentNumPois(),
                ul_e;
            if (numb && numb > 0) {
                ul_e = document.createElement("ul");
                ul_e.className = 'mui-table-view mui-table-view-radio';
                ul_e.addEventListener("selected", function (e) {
                    var res = e.detail.el.getAttribute('data-poi');
                    if (res) res = JSON.parse(res)
                    map.centerAndZoom(new BMap.Point(res.point.lng, res.point.lat), baiduConfig.zoom)
                });

                for (var i = 0; i < numb; i++) {
                    var poi = rs.getPoi(i);
                    createMarker(poi)
                    var li_e = document.createElement("li");
                    li_e.className = 'mui-table-view-cell';
                    if (i == 0) {
                        li_e.classList.add('mui-selected')
                        map.centerAndZoom(poi.point, baiduConfig.zoom)
                    }
                    li_e.innerHTML = '<a class="mui-navigate-right">' + poi.title + '<p class="mui-ellipsis"><b>地址：</b>' + poi.address + '</p><p><b>电话：</b>' + (poi.phoneNumber ? poi.phoneNumber : '暂无电话') + '</p></a>';

                    li_e.setAttribute('data-poi', JSON.stringify(poi));

                    ul_e.appendChild(li_e);
                }
                item = ul_e;
            } else {
                item = '<div class="mui-content-padded mui-text-center">暂无结果</div>';
            }
        } else {
            item = '<div class="mui-content-padded mui-text-center">附近没有' + key + '场地</div>';
        }
        if (func) func(item)
    })
    localSearch.searchNearby(key, p, 1000)
}

/**
 * 获取指定经纬度，附近的地址
 * @param {Object} p 经纬度
 */
function getNearByAddress(p) {
    getAddressByPoint(p, function (res) {
        var addrs = res.surroundingPois;

        var ul_e = document.createElement("ul");
        ul_e.className = 'mui-table-view mui-table-view-radio';
        ul_e.addEventListener('selected', function (e) {
            //console.log(e.detail.el.getAttribute('data-poi'));
            var res = e.detail.el.getAttribute('data-poi');
            if (res) res = JSON.parse(res)
            addMarker(new BMap.Point(res.point.lng, res.point.lat), res.address, res.title)
        });

        var curaddr = {address: res.address, title: res.address, point: res.point}
        var li_e1 = document.createElement("li");
        li_e1.className = 'mui-table-view-cell';
        li_e1.classList.add('mui-selected')
        map.centerAndZoom(res.point, baiduConfig.zoom)
        li_e1.setAttribute('data-poi', JSON.stringify(curaddr));
        li_e1.innerHTML = '<a class="mui-navigate-right">' + curaddr.title + '<p class="mui-ellipsis">' + curaddr.address + '</p></a>';
        ul_e.appendChild(li_e1);

        if (addrs) {
            // if (addrs.length <= 0) {
            //     ul_e = '<div class="mui-content-padded mui-text-center">没有找到附近地址</div>';
            // } else {

            for (var i = 0; i < addrs.length; i++) {
                var li_e = document.createElement("li");
                li_e.className = 'mui-table-view-cell';
                // if (i == 0) {
                //     li_e.classList.add('mui-selected')
                //     map.centerAndZoom(addrs[i].point, baiduConfig.zoom)
                // }
                li_e.setAttribute('data-poi', JSON.stringify(addrs[i]));
                li_e.innerHTML = '<a class="mui-navigate-right">' + addrs[i].title + '<p class="mui-ellipsis">' + addrs[i].address + '</p></a>';

                ul_e.appendChild(li_e);
            }

            var $elParent = $('#nearByItem .mui-scroll');
            $elParent.empty()
            if (typeof ul_e === 'string') {
                $elParent.html(ul_e);
            } else {
                var loading = $('#nearByItem .mui-loading');
                if (loading) {
                    loading.remove()
                }
                $elParent.append($(ul_e));
            }
        }
    })
}

/**
 * 获取当前marker的地址
 * @param {Object} func
 */
function getAddress(func) {
    var point = marker.getPosition(); //获取markder坐标点
    var self = this;
    getAddressByPoint(point, function (res) {
        func(parseAddress(res.addressComponents));
    });
}

/**
 * 根据经纬度，获取地址
 * @param {Object} point 经纬度
 * @param {Object} func 成功回调
 */
function getAddressByPoint(point, func) {
    if (!geocoder) {
        geocoder = new BMap.Geocoder();
    }
    geocoder.getLocation(point, function (res) {
        //console.log(res);
        func(res);
    });
}

/**
 * 根据地址获取经纬度
 * @param {Object} addr 地址描述
 * @param {Object} city 城市
 * @param {Object} func 成功回调
 */
function getAddressByAddr(addr, city, func) {
    if (!geocoder) {
        geocoder = new BMap.Geocoder();
    }
    geocoder.getPoint(addr, function (point) {
        func(point);
    });
}

/**
 * 地址解析
 * @param {Object} addressComponent
 */
function parseAddress(addressComponent) {
    console.log(addressComponent);
    var address = "";
    if (addressComponent) {
        var province = addressComponent.province;
        var city = addressComponent.city;
        var district = addressComponent.district;
        var street = addressComponent.street;
        var streetNumber = addressComponent.streetNumber;

        address = city + district + street + streetNumber;
    }
    return address;
}

/**
 * 获取点击marker弹出的infoWindow
 * @param {Object} content 提示内容
 */
function getInfoWindow(content) {
    if (!infowindow) {
        infowindow = new BMap.InfoWindow(content);
        infowindow.addEventListener("close", function (e) {
            //mui.toast("close");
        });
        infowindow.addEventListener("open", function (e) {
            //mui.toast("open");
        });
    }
    infowindow.setContent(content);
    return infowindow;
}

/**
 * 搜索框初始化
 * @param {Object} map
 */
function searchInit(map) {
    var ac = new BMap.Autocomplete({
        'input': 'baiduSearchInput',
        'location': map
    });
    //	放在下拉列表事件
    ac.addEventListener("onhighlight", function (e) {
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province + _value.city + _value.district + _value.street + _value.business;
        }
        str += "FormItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province + _value.city + _value.district + _value.street + _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        $('#searchResultPanel').html(str)
    });
    //点击下拉列表后的事件
    ac.addEventListener("onconfirm", function (e) {
        var myValue = "";
        var _value = e.item.value;
        myValue = _value.province + _value.city + _value.district + _value.street + _value.business;
        $('#searchResultPanel').html("onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue)

        getAddressByAddr(myValue, _value.city, function (point) {
            if (point) {
                addMarker(point, myValue)
                initAddressList(index, point)
            }
        })
    });
}

//初始化界面宽高
function init_size() {
    var height = $(window).height() - $('.ui-head').outerHeight() - $('#searchDiv').outerHeight() - $('#mapDiv').outerHeight() - $('#sliderSegmented').outerHeight();

    $('#cateSlider').css('height', height - 40)
    $('.mui-slider-group .mui-control-content').css('height', height - 80)
}