/**
 * Created by WIN on 2017-08-29.
 */
/**
 * Created by WIN on 2017/2/8.
 */
$(function () {

    var typeInt = myTypeInt;

    var typeInts = new Array();

    typeInts = typeInt.split(",");
    var checkboxs = $("input[name=typeInt]");

    for (var i = 0; i < typeInts.length; i++) {
        var myInt = typeInts[i];
        for (var j = 0; j < checkboxs.length; j++) {
            var value = $(checkboxs[j]).attr("value");
            if (myInt == value) {
                $(checkboxs[j]).attr("checked", "checked");
            } else {
                continue;
            }
        }
    }

    $("#btn-reset").click(function () {
        //alert("test");
        $("#placeForm input").each(function () {
            $(this).val("");
            $(this).attr("value", "");
            $(this).removeAttr("checked");
        });
        $("#placeForm textarea").each(function () {
            $(this).text("");
        });
    });

    $("#a-map").click(function () {
        $("#mapModal").modal("show");
    });

    var map = new BMap.Map("allmap");
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);
    function showInfo(e) {
//            alert(e.point.lng + ", " + e.point.lat);
        $("#input-zuobiao").val(e.point.lng + ", " + e.point.lat);
        $("input[name=lng]").val(e.point.lng);
        $("input[name=lat]").val(e.point.lat);
        $("#mapModal").modal("hide");
    }

    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    map.addEventListener("click", showInfo);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            //alert('您的位置：'+r.point.lng+','+r.point.lat);
        }
        else {
//                alert('failed'+this.getStatus());
        }
    }, {enableHighAccuracy: true})

    var geoc = new BMap.Geocoder();

    map.addEventListener("click", function (e) {
        var pt = e.point;
        geoc.getLocation(pt, function (rs) {
            var addComp = rs.addressComponents;
//                alert(addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber);
            var address = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
            $("textarea[name=address]").text(address);
            $("input[name=province]").val(addComp.province);
            $("input[name=city]").val(addComp.city);
        });
    });

    /*点击保存*/
    $("#btn-save").click(function () {
        var checkboxs = $("input[name=typeInt]:checked");
        var typeInt = "";
        for (var i = 0; i < checkboxs.length; i++) {
            var checkbox = $(checkboxs[i]);
            if (i == checkboxs.length - 1) {
                typeInt = typeInt + checkbox.attr("value");
            } else {
                typeInt = typeInt + checkbox.attr("value") + ",";
            }
        }
        var info = wxgh.serialize(document.getElementById('placeForm'))

        info['typeInt'] = typeInt;

        info['action'] = 'updatePlace'
        $.ajax({
            url: self_url+"placeedit.json",
            data: info,
            success: function () {
                alert("保存成功");
            }
        });
    });

    /*场地管理*/

    var placeId = myPlaceId;
    var cateId = typeInts[0];
    var current_cateId = "";
    var $cdList = $('table.ui-changdi-list')
    changdiguanli(placeId, cateId);

    $("li.li-cate").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
        current_cateId = $(this).children("a").attr("data-id");
        changdiguanli(placeId, current_cateId);
    });

    function changdiguanli(placeId, cateId) {
        $cdList.empty()
        $.ajax({
            url: self_url+"getplacesite.json",
            data: {
                action: "getPlaceSite",
                placeId: placeId,
                cateId: cateId
            },
            success: function (result) {
                placeSiteCallback(result)
            }
        });
        function placeSiteCallback(result) {
            var data = result.data;
            var ps = '<td><button  data-id="' + cateId + '" type="button"class="btn-cate-add btn btn-default"><span class="fa fa-plus-square-o"></span> 添加</button></td>'
            if (data.length > 0) {
                var $tr
                for (var i = 0; i < data.length; i++) {
                    if (i % 10 == 0) {
                        $tr = $('<tr></tr>')
                        $cdList.append($tr)
                    }
                    var $td = $('<td>' + data[i].name + '<br><small class="ui-text-info">（' + data[i].price + '元 | ' + data[i].score + '分）</small>' + '<br><a class="a-changdi-del btn btn-link" data-id="' + data[i].id + '">删除</a> <a class="a-changdi-edit btn btn-link" data-id="' + data[i].id + '">编辑</a></td>')
                    $td.data('site', data[i])
                    $tr.append($td)
                }
                $cdList.find('tr:last').append(ps)
            } else {
                $cdList.append('<tr>' + ps + '</tr>')
            }
        }
    }

    /*添加场地*/
    var currentCateId = "";
    var $addSiteModal = $('#addCateModal')
    $("body").on("click", ".btn-cate-add", function () {
        currentCateId = $(this).attr("data-id");
        $("#addCateModal").modal("show");
    });
    $("#addCateModal button.btn-primary").click(function () {
        var name = $("#input-cate-name").val();
        if (!name) {
            alert("名称不能为空");
            return;
        }
        var price = $('#input-cate-price').val();
        if (isNaN(price) || price < 0) {
            alert('请输入合法价格');
            return;
        }
        var score = $('#input-cate-score').val();
        if (isNaN(score) || score < 0) {
            alert('请输入合法积分');
            return;
        }

        var info = {
            action: "addPlaceCate",
            placeId: placeId,
            cateId: currentCateId,
            name: name,
            score: score,
            price: price
        }
        var msg = '添加成功'
        var id = $addSiteModal.find('input[name=id]').val()
        if (id) {
            info['id'] = id
            msg = '编辑成功'
        }

        $.ajax({
            url: self_url+"addplacecate.json",
            data: info,
            success: function (result) {
                if (result.ok) {
                    alert(msg);
                    $("#addCateModal").modal("hide");
                    changdiguanli(placeId, currentCateId);
                }
            }
        });
    });

    /*删除场地*/
    $("body").on("click", "a.a-changdi-del", function () {
        var id = $(this).attr("data-id");
        if (window.confirm("确定删除吗？")) {
            $.ajax({
                url: self_url+"delplacesite.json",
                data: {
                    action: "delPlaceSite",
                    id: id
                },
                success: function () {
                    alert("删除成功");
                    changdiguanli(placeId, current_cateId);
                }
            });
        } else {
            return;
        }
    });

    $addSiteModal.on('hide.bs.modal', function () {
        $addSiteModal.find('input[name]').val('')
    })
    $('body').on('click', 'a.a-changdi-edit', function () {
        var $td = $(this).parent()
        var site = $td.data('site')
        for (var k in site) {
            $addSiteModal.find('input[name=' + k + ']').val(site[k])
        }
        $("#addCateModal").modal("show");
    })

    /*图片管理*/
    placeImgeAdmin();
    function placeImgeAdmin() {
        $("#div-place-img").html("");
        $.ajax({
            url: self_url+"getplaceimg.json",
            data: {
                action: "getPlaceImg",
                placeId: placeId
            },
            success: function (rs) {
                placeImgcallback(rs);
            }
        });
        function placeImgcallback(rs) {
            var data = rs.data;
            var ps = "";
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    ps = ps + '<div class="col-md-3 col-sm-3 col-xs-2"><a class="thumbnail"href="#"><img style="height: 100px;" src="' + (data[i].path) + '"/></a><span data-id="' + data[i].id + '" class="fa fa-remove text-danger ui-delete"></span></div>';
                    if (i == data.length - 1) {
                        ps = ps + '<div class="col-md-3 col-sm-3 col-xs-2"><a class="thumbnail"href="#"><img class="add" src="../../image/di/notice/icon_add.png"/></a></div>';
                    }
                }
            } else {
                ps = ps + '<div class="col-md-3 col-sm-3 col-xs-2"><a class="thumbnail"href="#"><img class="add" src="../../image/di/notice/icon_add.png"/></a></div>';
            }
            $("#div-place-img").append(ps);
        }
    }

    /*点击添加*/
    $("body").on("click", "a.thumbnail img.add", function () {
        $("#addPlaceImgModal").modal("show");
        $("#modal-body-img").append("<div id='coverImg'></div>")
        upload = ui.uploader("#coverImg")
    });
    $("#addPlaceImgModal button.btn-primary").click(function () {
        var data = {placeId: placeId}
        if($(".ui-file-item").length==2){
            upload.upload(function (fileIds) {
                data.imgPath = fileIds.toString()
                $.ajax({
                    url: self_url+"addplaceimg.json",
                    data: data,
                    success: function (rs) {
                        ui.alert('上传成功！',function(){
                            $("#addPlaceImgModal").modal("hide");
                            $("#coverImg").remove()
                            placeImgeAdmin();
                        })
                    }
                });
            })
        }else{
            ui.alert("请先上传图片")
        }
    });

    /*删除*/
    $("#div-place-img").on("click", "span.ui-delete", function () {
        if (window.confirm("是否删除？")) {
            var id = $(this).attr("data-id");
            $.ajax({
                url: self_url+"delplaceimg.json",
                data: {
                    action: "delplaceimg",
                    id: id
                },
                success: function () {
                    alert("删除成功");
                    placeImgeAdmin();
                }
            });
        } else {
            return;
        }
    });

    /*排班管理*/
    /*筛选分类*/
    var paiban_current_cateId = "";
    var paiban_current_siteId = "";
    $("#select-paiban").change(function () {
        paiban_current_cateId = $(this).children("option:selected").attr("value");
        if (paiban_current_cateId != 0) {
            $.ajax({
                url: self_url+"getplacesite.json",
                data: {
                    action: "getPlaceSite",
                    placeId: placeId,
                    cateId: paiban_current_cateId
                },
                success: function (result) {
                    paibanChangdiCallback(result)
                }
            });
        }
        function paibanChangdiCallback(result) {
            $("#select-changdi").html('<option value="0">请选择场地</option>');
            var data = result.data;
            if (data.length > 0) {
                var ps = "";
                for (var i = 0; i < data.length; i++) {
                    ps = ps + '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                }
                $("#select-changdi").append(ps);
            }
        }
    });

    $("#select-changdi").change(function () {
        paiban_current_siteId = $(this).children("option:selected").attr("value");
        if (paiban_current_siteId != 0) {
            getPlaceTime(paiban_current_cateId, paiban_current_siteId);
        }
    });

    function getPlaceTime(cateId, siteId) {
        $("#ul-placeTime-1").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $("#ul-placeTime-2").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $("#ul-placeTime-3").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $("#ul-placeTime-4").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $("#ul-placeTime-5").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $("#ul-placeTime-6").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $("#ul-placeTime-7").html('<li class="paiban-wait"><i class="fa fa-refresh fa-spin"></i>');
        $.ajax({
            url: self_url+"getplacetime.json",
            data: {
                action: "getPlaceTime",
                cateId: cateId,
                siteId: siteId
            },
            success: function (rs) {
                getPlaceTimeCallBack(rs);
            }
        });
        function getPlaceTimeCallBack(rs) {
            var data = rs.data;

            var yips = "";
            var yi = data[1];
            if (yi.length > 0) {
                for (var i = 0; i < yi.length; i++) {
                    //yips = yips + '<li class="list-group-item">' + yi[i].startTime + '~' + yi[i].endTime + (yi[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + yi[i].id + '"></span></li>';
                    yips = yips + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+yi[i].id+'" status="'+yi[i].status+'" timeType="'+yi[i].timeType+'" ' +
                        'style="background-color:'+(yi[i].timeType == 2 ? '#f9f900' : (yi[i].status == 0 ? '#ffffff': '')+(yi[i].status == 1 ? '#0bb20c': '')+(yi[i].status == 2 ? '#9d9d9d': '')+(yi[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + yi[i].startTime + '~' + yi[i].endTime +  '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + yi[i].id + '"></span></li>';
                    if (i == yi.length - 1) {
                        yips = yips + '<li class="list-group-item li-add-paiban" data-id="1"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                yips = '<li class="list-group-item li-add-paiban" data-id="1"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-1").html("");
            $("#ul-placeTime-1").append(yips);

            var erps = "";
            var er = data[2];
            if (er.length > 0) {
                for (var i = 0; i < er.length; i++) {
                    //erps = erps + '<li class="list-group-item">' + er[i].startTime + '~' + er[i].endTime + (er[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + er[i].id + '"></span></li>';
                    erps = erps + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+er[i].id+'" status="'+er[i].status+'" timeType="'+er[i].timeType+'" ' +
                        'style="background-color:'+(er[i].timeType == 2 ? '#f9f900' : (er[i].status == 0 ? '#ffffff': '')+(er[i].status == 1 ? '#0bb20c': '')+(er[i].status == 2 ? '#9d9d9d': '')+(er[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + er[i].startTime + '~' + er[i].endTime + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + er[i].id + '"></span></li>';
                    if (i == er.length - 1) {
                        erps = erps + '<li class="list-group-item li-add-paiban" data-id="2"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                erps = '<li class="list-group-item li-add-paiban" data-id="2"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-2").html("");
            $("#ul-placeTime-2").append(erps);

            var sanps = "";
            var san = data[3];
            if (san.length > 0) {
                for (var i = 0; i < san.length; i++) {
                    //sanps = sanps + '<li class="list-group-item">' + san[i].startTime + '~' + san[i].endTime + (san[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + san[i].id + '"></span></li>';
                    sanps = sanps + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+san[i].id+'" status="'+san[i].status+'" timeType="'+san[i].timeType+'" ' +
                        'style="background-color:'+(san[i].timeType == 2 ? '#f9f900' : (san[i].status == 0 ? '#ffffff': '')+(san[i].status == 1 ? '#0bb20c': '')+(san[i].status == 2 ? '#9d9d9d': '')+(san[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + san[i].startTime + '~' + san[i].endTime + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + san[i].id + '"></span></li>';
                    if (i == san.length - 1) {
                        sanps = sanps + '<li class="list-group-item li-add-paiban" data-id="3"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                sanps = '<li class="list-group-item li-add-paiban" data-id="3"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-3").html("");
            $("#ul-placeTime-3").append(sanps);

            var sips = "";
            var si = data[4];
            if (si.length > 0) {
                for (var i = 0; i < si.length; i++) {
                    //sips = sips + '<li class="list-group-item">' + si[i].startTime + '~' + si[i].endTime + (si[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + si[i].id + '"></span></li>';
                    sips = sips + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+si[i].id+'" status="'+si[i].status+'" timeType="'+si[i].timeType+'" ' +
                        'style="background-color:'+(si[i].timeType == 2 ? '#f9f900' : (si[i].status == 0 ? '#ffffff': '')+(si[i].status == 1 ? '#0bb20c': '')+(si[i].status == 2 ? '#9d9d9d': '')+(si[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + si[i].startTime + '~' + si[i].endTime + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + si[i].id + '"></span></li>';
                    if (i == si.length - 1) {
                        sips = sips + '<li class="list-group-item li-add-paiban" data-id="4"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                sips = '<li class="list-group-item li-add-paiban" data-id="4"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-4").html("");
            $("#ul-placeTime-4").append(sips);

            var wups = "";
            var wu = data[5];
            if (wu.length > 0) {
                for (var i = 0; i < wu.length; i++) {
                    //wups = wups + '<li class="list-group-item">' + wu[i].startTime + '~' + wu[i].endTime + (wu[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + wu[i].id + '"></span></li>';
                    wups = wups + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+wu[i].id+'" status="'+wu[i].status+'" timeType="'+wu[i].timeType+'" ' +
                        'style="background-color:'+(wu[i].timeType == 2 ? '#f9f900' : (wu[i].status == 0 ? '#ffffff': '')+(wu[i].status == 1 ? '#0bb20c': '')+(wu[i].status == 2 ? '#9d9d9d': '')+(wu[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + wu[i].startTime + '~' + wu[i].endTime + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + wu[i].id + '"></span></li>';
                    if (i == wu.length - 1) {
                        wups = wups + '<li class="list-group-item li-add-paiban" data-id="5"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                wups = '<li class="list-group-item li-add-paiban" data-id="5"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-5").html("");
            $("#ul-placeTime-5").append(wups);

            var liups = "";
            var liu = data[6];
            if (liu.length > 0) {
                for (var i = 0; i < liu.length; i++) {
                    //liups = liups + '<li class="list-group-item">' + liu[i].startTime + '~' + liu[i].endTime + (liu[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + liu[i].id + '"></span></li>';
                    liups = liups + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+liu[i].id+'" status="'+liu[i].status+'" timeType="'+liu[i].timeType+'" ' +
                        'style="background-color:'+(liu[i].timeType == 2 ? '#f9f900' : (liu[i].status == 0 ? '#ffffff': '')+(liu[i].status == 1 ? '#0bb20c': '')+(liu[i].status == 2 ? '#9d9d9d': '')+(liu[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + liu[i].startTime + '~' + liu[i].endTime + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + liu[i].id + '"></span></li>';
                    if (i == liu.length - 1) {
                        liups = liups + '<li class="list-group-item li-add-paiban" data-id="6"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                liups = '<li class="list-group-item li-add-paiban" data-id="6"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-6").html("");
            $("#ul-placeTime-6").append(liups);

            var qips = "";
            var qi = data[7];
            if (qi.length > 0) {
                for (var i = 0; i < qi.length; i++) {
                    //qips = qips + '<li class="list-group-item">' + qi[i].startTime + '~' + qi[i].endTime + (qi[i].status == 3 ? '<small>（已取消）</small>' : '') + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + qi[i].id + '"></span></li>';
                    qips = qips + '<li class="list-group-item" onclick="changeStatus($(this))"' +
                        'id="'+qi[i].id+'" status="'+qi[i].status+'" timeType="'+qi[i].timeType+'" ' +
                        'style="background-color:'+(qi[i].timeType == 2 ? '#f9f900' : (qi[i].status == 0 ? '#ffffff': '')+(qi[i].status == 1 ? '#0bb20c': '')+(qi[i].status == 2 ? '#9d9d9d': '')+(qi[i].status == 3 ? '#3c3c3c': '') )+';">'
                        + qi[i].startTime + '~' + qi[i].endTime + '<span style="right: -2px;top:-4px;" class="fa fa-remove text-danger ui-delete" data-id="' + qi[i].id + '"></span></li>';
                    if (i == qi.length - 1) {
                        qips = qips + '<li class="list-group-item li-add-paiban" data-id="7"><span class="fa fa-plus-square-o"></span>添加</li>';
                    }
                }
            } else {
                qips = '<li class="list-group-item li-add-paiban" data-id="7"><span class="fa fa-plus-square-o"></span>添加</li>';
            }
            $("#ul-placeTime-7").html("");
            $("#ul-placeTime-7").append(qips);

            var $lis = $('#table-paiban li.list-group-item')
            //initCancelBtn($lis)
        }
    }


    /*添加排班*/
    var otherPaibanlist = "";
    var currentWeek = "";
    $("body").on("click", "li.li-add-paiban", function () {
        $("#paibanModal").modal("show");
        otherPaibanlist = $(this).siblings("li");
        currentWeek = $(this).attr("data-id");
    });
    $("#paibanModal button.btn-primary").click(function () {
        var startTime = $("#start-time").val();
        var endTime = $("#end-time").val();
        var startHour = startTime.split(":")[0];
        var startMin = startTime.split(":")[1];
        var endHour = endTime.split(":")[0];
        var endMin = endTime.split(":")[1];
        if (startHour > endHour) {
            alert("开始时间不能大于等于结束时间");
            return;
        } else if (startHour == endHour) {
            if (startMin > endMin) {
                alert("开始时间不能大于等于结束时间");
            }
        }
        if ($("#input-sync").is(':checked')) {
            var siteList = $("#select-changdi option");
            for (var i = 0; i < siteList.length; i++) {
                var value = $(siteList[i]).attr("value");
                if (value) {
                    addPaiban(currentWeek, paiban_current_cateId, value, startTime, endTime)
                } else {
                    continue;
                }
            }
        } else {


            if (otherPaibanlist.length > 0) {
                for (var i = 0; i < otherPaibanlist.length; i++) {
                    var oneStartTime = $(otherPaibanlist[i]).text().split("~")[0].split(":")[0];
                    var oneEndTime = $(otherPaibanlist[i]).text().split("~")[1].split(":")[0];
                    if ((oneStartTime < startTime && oneEndTime > startTime) || (oneStartTime < endTime && oneEndTime > endTime)) {
                        alert("所选时间与其它时间有冲突，请重新选择");
                        return;
                    }
                }
                addPaiban(currentWeek, paiban_current_cateId, paiban_current_siteId, startTime, endTime)
            } else {
                addPaiban(currentWeek, paiban_current_cateId, paiban_current_siteId, startTime, endTime)
            }

        }

        function addPaiban(week, cateId, siteId, startTime, endTime) {
            $.ajax({
                url: self_url+"addplacetime.json",
                data: {
                    action: "addPlaceTime",
                    siteId: siteId,
                    cateId: cateId,
                    week: week,
                    startTime: startTime,
                    endTime: endTime
                },
                success: function () {
                    //alert("添加成功");
                    ui.alert("添加成功",function () {
                        getPlaceTime(paiban_current_cateId, paiban_current_siteId);
                        $("#paibanModal").modal("hide");
                    })
                }
            });
        }
    });


    /*删除排班*/
    $("#table-paiban").on("click", "span.ui-delete", function () {
        if (window.confirm("是否删除？")) {
            var id = $(this).attr("data-id");
            $.ajax({
                url: self_url+"delplacetime.json",
                data: {
                    action: "delplacetime",
                    id: id
                },
                success: function () {
                    ui.alert("删除成功",function(){
                        getPlaceTime(paiban_current_cateId, paiban_current_siteId);
                    });
                }
            });
        } else {
            return;
        }

    });

    function initCancelBtn($li) {
        $li.on('click', function () {
            var id = $(this).find('.ui-delete').attr('data-id')
            var msg, status
            if ($li.find('small').length > 0) {
                msg = '取消强制预约？'
                status = 0
            } else {
                msg = '强制预约该时间段？'
                status = 3
            }
            var cmf = confirm(msg)
            if (cmf) {
                $.getJSON(self_url, {action: 'update_time', id: id, status: status}, function (res) {
                    if (res.ok) {
                        alert('操作成功')
                        window.location.reload()
                    } else {
                        alert(res.msg)
                    }
                })
            }
        })
    }


    //预约规则管理
    var $chooseTimeModal = $('#chooseTimeModal')
    var $chooseBody = $('#chooseBody')
    var $ruleModal = $('#ruleModal');
    var $siteRuleDiv = $('#siteRuleDiv')

    //选择用户初始化
    var userlist = window.userlist()
    $chooseTimeModal.on('show.bs.modal', function () {
        if (!$chooseBody.attr('data-get')) {
            $.getJSON(self_rule, {action: 'list', placeId: myPlaceId}, function (res) {
                $chooseBody.attr('data-get', true)
                if (res.ok) {
                    var tims = res.data
                    var $ul = $('<ul class="nav nav-tabs"></ul>')
                    var $div = $('<div class="tab-content"></div>')
                    $div.css('padding', '10px 10px 0 10px')
                    if (tims) {
                        var i = 0
                        for (var k in tims) {
                            var id = 'site_' + i
                            var $li = $('<li><a href="#' + id + '" data-toggle="tab">' + k + '</a></li>')
                            $ul.append($li)

                            var $cnt = $('<div></div>')
                            $cnt.addClass('tab-pane')
                            $cnt.attr('id', id)
                            if (i == 0) {
                                $li.addClass('active')
                                $cnt.addClass('active')
                            }
                            var times = tims[k]
                            if (times && times.length > 0) {
                                var $btns = $('<div class="btn-group" data-toggle="buttons"></div>')
                                for (var t in times) {
                                    var $lable = $('<label class="btn btn-success"><input type="radio" name="options" autocomplete="off" checked>' + times[t].startTime + ' - ' + times[t].endTime + '（周' + times[t].week + '）</label>')
                                    $lable.data('id', times[t].id)
                                    $btns.append($lable)
                                }
                                $cnt.append($btns)
                            } else {
                                $cnt.text('暂无可预约时间')
                                $cnt.addClass('text-center')
                            }
                            $div.append($cnt)
                            i++
                        }
                        $chooseBody.append($ul)
                        $chooseBody.append($div)
                    }
                }
            })
        }
    })

    //选择时间段，确认按钮
    $chooseTimeModal.on('click', '#chooseTimeModalBtn', function () {
        var $input = $chooseTimeModal.find('.tab-content .tab-pane.active input:radio:checked')
        if ($input.length <= 0) {
            alert('请选择时间段')
            return
        }
        var $parent = $input.parent()
        var $timeInput = $siteRuleDiv.find('input[name=timeId]')
        $timeInput.val($parent.data('id'))
        $timeInput.prev().val($parent.text())

        $chooseTimeModal.modal('hide')
    })

    $('#chooseUserBtn').click(function () {
        userlist.show(function (lists) {
            if (lists && lists.length > 0) {
                var $input = $siteRuleDiv.find('input[name=userid]')
                $input.val(lists[0].id)
                $input.prev().val(lists[0].name)
            }
        })
    })

    $('#chooseTimeBtn').click(function () {
        $chooseTimeModal.modal('show')
    })

    var type = 1;
    $('#ruleTypeSelect').change(function () {
        var val = $(this).val()
        type = val;
        if (val == 1) {
            $('#numbRuleDiv').removeClass('hidden')
            $('#siteRuleDiv').addClass('hidden')
        } else {
            $('#numbRuleDiv').addClass('hidden')
            $('#siteRuleDiv').removeClass('hidden')
        }
    })

    $ruleModal.find('input[name=numb]').keyup(function () {
        if ($(this).val() < 0) {
            $(this).val(0)
            alert('限制预约数量不能小于0哦');
        }
    })

    //添加规则
    $('#addRuleBtn').click(function () {
        var name = $ruleModal.find('input[name=name]').val();
        if (!name) {
            alert('请输入规则名称')
            return;
        }
        if (name.length > 80) {
            alert('规则名称必须在80字符内哦')
            return;
        }
        var info = $ruleModal.find('textarea[name=info]').val();
        if (info.length > 200) {
            alert('规则介绍必须在200字符内哦')
            return;
        }

        var rul = {};
        if (type == 1) {
            var numbTime = $ruleModal.find('select[name=numb_time]').val();
            var numb = $ruleModal.find('input[name=numb]').val();
            if (numb < 0) {
                alert('限制预约数量必须大于0哦');
                return;
            }
            rul['type'] = numbTime;
            rul['num'] = numb;
        } else {
            var userid = $siteRuleDiv.find('input[name=userid]').val()
            var timeId = $siteRuleDiv.find('input[name=timeId]').val()
            var name = $siteRuleDiv.find('input[name=name]').val()
            var time = $siteRuleDiv.find('input[name=time]').val()
            if (!userid) {
                alert('请选择预订用户哦')
                return
            }
            if (!timeId) {
                alert('请选择预订时间哦')
                return
            }
            rul['userid'] = userid
            rul['timeId'] = timeId
            rul['name'] = name
            rul['time'] = time
        }
        var json = {};
        json['name'] = name;
        json['info'] = info;
        json['type'] = type;
        json['typeName'] = $('#ruleTypeSelect').find('option:selected').text();
        json['rule'] = JSON.stringify(rul);
        json['id'] = $ruleModal.find('input[name=id]').val()

        json['action'] = 'add'
        $.post(self_rule, json, function (res) {
            if (res.ok) {
                $ruleModal.modal('hide')
                window.location.reload()
            } else {
                alert(res.msg)
            }
        }, 'json')
    })

    //修改规则状态
    $('#yuyueManager').on('click', '.ui-status', function () {
        if (confirm('是否修改规则的状态？')) {
            var $self = $(this)
            var id = $self.parent().parent().attr('data-id')
            var status = $self.attr('data-status')
            $.post(self_rule, {id: id, status: status, action: 'edit'}, function (res) {
                if (res.ok) {
                    $self.attr('data-status', res.data)
                    if (res.data == 1) {
                        $self.addClass('label-info')
                        $self.removeClass('label-danger')
                        $self.text('可用')
                    } else {
                        $self.addClass('label-danger')
                        $self.removeClass('label-info')
                        $self.text('不可用')
                    }
                } else {
                    alert('修改失败')
                }
            })
        }
    })

    //规则删除
    $('.ui-yuyue-manager-opration').on('click', '.ui-del', function () {
        var id = $(this).parent().parent().attr('data-id');

        var confr = confirm('确定要删除吗？')
        if (confr) {
            $.getJSON(self_rule, {action: 'delete', id: id}, function (res) {
                if (res.ok) {
                    alert('删除成功');
                    window.location.reload();
                } else {
                    alert(res.msg)
                }
            })
        }
    })

    //规则编辑
    $('.ui-yuyue-manager-opration').on('click', '.ui-edit', function () {
        var id = $(this).parent().parent().attr('data-id');

        $.getJSON(self_rule, {action: 'get', id: id}, function (res) {
            if (res.ok) {
                initModal(res.data);
                $ruleModal.modal('show')
            }
        })

    })

    //例外编辑
    $('.ui-yuyue-manager-opration').on('click', '.ui-other-edit', function () {
        var id = $(this).parent().parent().attr('data-id')

    })

    function initModal(rule) {
        $ruleModal.find('input[name=id]').val(rule.id)
        $ruleModal.find('input[name=name]').val(rule.name)
        $ruleModal.find('textarea[name=info]').val(rule.info)
        $ruleModal.find('select[name=type]').find('option[value=' + rule.type + ']').attr('selected', true)
        if (rule.type == 1) {
            $('#numbRuleDiv').removeClass('hidden')
            $('#siteRuleDiv').addClass('hidden')

            var r = rule.rule;
            if (r) {
                r = JSON.parse(r)
                $ruleModal.find('select[name=numb_time]').find('option[value=' + r.type + ']').attr('selected', true)
                $ruleModal.find('input[name=numb]').val(r.num)
            }
        } else if (rule.type == 2) {
            $siteRuleDiv.removeClass('hidden')
            $('#numbRuleDiv').addClass('hidden')

            var r = rule.rule
            if (r) {
                r = JSON.parse(r)
                for (var k in r) {
                    $siteRuleDiv.find('input[name=' + k + ']').val(r[k])
                }
            }
        }
        type = rule.type
    }

    $ruleModal.on('hidden.bs.modal', function () {
        clearModal()
    })

    function clearModal() {
        $('#ruleForm').find('input[name],textarea[name]').each(function () {
            $(this).val('');
        })

        $('#numbRuleDiv').removeClass('hidden')
        $('#siteRuleDiv').addClass('hidden')
    }


    /**
     * 固定场相关js
     */
    var $gudingModal = $('#addGudingModal')
    var $gudingBody = $('#gudingBody')
    var $outUserDiv = $('#outUserDiv')
    var $gudingWeekList = $('#gudingWeekList')
    var $gudingChooseUser = $('#gudingChooseUser')
    var curCateId, curSiteId, curWeek = 1
    var weekStrs = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']

    var $gudingCateSelect = $('#gudingCateSelect')
    var $gudingSiteSelect = $('#gudingSiteSelect')
    var $gudingWeekSelect = $('#gudingWeekSelect')
    //获取所有的固定场
    listGuding()

    $gudingCateSelect.on('change', function () {
        var val = $(this).val()
        var url = "/admin/place/api/getplacesite.json"
        if (val != 0) {
            ui.post(url, {action: 'getPlaceSite', placeId: myPlaceId, cateId: val}, function (res) {
                if (res!=null) {
                    var sites = res
                    $gudingSiteSelect.empty()
                    $gudingSiteSelect.append('<option value="0">请选择场地</option>')
                    if (sites && sites.length > 0) {
                        for (var i in sites) {
                            $gudingSiteSelect.append('<option value="' + sites[i].id + '">' + sites[i].name + '</option>')
                        }
                    }
                }
            })
        }
        listGuding()
    })

    $gudingSiteSelect.on('change', function () {
        listGuding()
    })

    $gudingWeekSelect.on('change', function () {
        listGuding()
    })

    function listGuding() {
        var info = {action: 'list_guding', placeId: placeId}

        var cateId = $gudingCateSelect.val()
        var siteId = $gudingSiteSelect.val()
        var week = $gudingWeekSelect.val()

        if (cateId && cateId != 0) info['cateId'] = cateId
        if (siteId && siteId != 0) info['siteId'] = siteId
        if (week && week != 0) info['week'] = week

        $gudingBody.empty()
        $gudingBody.append('<tr><td class="text-center" colspan="6"><i class="fa fa-spinner fa-2x fa-spin ui-text-info"></i></td></tr>')

        $.post(self_url+"listguding.json", info, function (res) {
            if (res.ok) {
                var gs = res.data
                $gudingBody.empty()
                if (gs && gs.length > 0) {
                    for (var i in gs) {
                        var g = gs[i]
                        var $tr = $('<tr><td>' + (Number(i) + 1) + '</td><td>' + g.cateName + '<br>' + g.siteName + '</td>'
                            + '<td>' + (weekStrs[Number(g.week) - 1]) + '<br>' + g.time + '</td>'
                            + '<td>' + g.username + '(' + g.mobile + ')<br>' + g.deptname + '</td>'
                            + '<td>' + guding_get_status(g.status) + '</td>'
                            + '<td>' //<a class="btn btn-link ui-edit">编辑</a>
                            //+ '<a class="btn btn-link ui-status">' + (g.status == 1 ? '取消场地' : '使用场地') + '</a>'
                            + '<a class="btn btn-link ui-del">删除固定场</a>'
                            + '</td></tr>')
                        $tr.data('id', g.id)
                        $tr.data('status', g.status)
                        $gudingBody.append($tr)

                        //初始化事件
                        initgudingEvent($tr)
                    }
                } else {
                    $gudingBody.append('<tr><td class="text-center" colspan="6">暂无固定场哦 <a class="btn btn-link" data-toggle="modal" data-target="#addGudingModal">立即添加</a></td></tr>')
                }
            }
        })
    }

    function guding_get_status(s) {
        var status
        if (s == 1) {
            status = '正常'
        } else if (s == 0) {
            status = '已取消'
        }
        return status
    }

    //固定场项目分类点击
    $('#gudingCateList').on('click', 'a.btn', function () {
        var $self = $(this)
        if (!$self.hasClass('active')) {
            $gudingWeekList.hide()

            var id = $self.attr('data-id')
            $.getJSON(self_url+"getplacesite.json", {action: 'getPlaceSite', placeId: placeId, cateId: id}, function (res) {
                if (res.ok) {
                    var $siteDiv = $('#gudingSiteList')
                    $siteDiv.empty()
                    var sites = res.data
                    if (sites && sites.length > 0) {
                        for (var i in sites) {
                            var $a = $('<a class="btn btn-link">' + sites[i].name + '</a>')
                            $a.data('id', sites[i].id)
                            $a.data('cateId', id)
                            $siteDiv.append($a)
                            initSiteEvent($a)
                        }
                    } else {
                        $siteDiv.append('<small>暂无场地</small>')
                    }
                    $('#gudingCateList').find('a.btn.active').removeClass('active')
                    $self.addClass('active')
                    curCateId = id
                    curWeek = 1
                }
            })
        }
    })

    //用户类型选择框改变事件
    $('#gudingUserType').on('change', function () {
        var val = $(this).val()
        if (val == 1) {
            $outUserDiv.hide()
            $gudingChooseUser.show()
        } else {
            $outUserDiv.show()
            $gudingChooseUser.hide()
        }
    })

    $gudingChooseUser.on('click', function () {
        var $self = $(this)
        userlist.show(function (users) {
            if (users && users.length > 0) {
                var $input = $gudingModal.find('input[name=userid]')
                $input.val(users[0].id)
                $self.next().text(users[0].name)
            }
        })
    })

    //确定提交
    $('#addGudingModalBtn').on('click', function () {
        var timeId = $gudingModal.data('timeId')
        if (!timeId) {
            alert('请先选择场地时间段哦')
            return
        }
        var usertype = $('#gudingUserType').val()

        var info = {}
        if (usertype == 1) { //系统用户
            info['userid'] = $gudingModal.find('input[name=userid]').val()
            if (!info['userid']) {
                alert('请先选择用户哦')
                return
            }
        } else {
            var serialize = function (form) {
                var info = {};
                var inputs = form.querySelectorAll("input[name], textarea[name], select[name]");
                for (var i = 0; i < inputs.length; i++) {
                    var item = inputs[i];
                    if (item.type && item.type == "file") {
                    } else {
                        info[item.name] = item.value;
                    }
                }
                return info;
            }
            var form = document.getElementById('outUserDiv');
            info = serialize(form);
            var res = gudingVerify(info)
            if (res) {
                alert(res)
                return
            }
        }
        info['remark'] = $gudingModal.find('textarea[name=remark]').val()
        info['userType'] = usertype
        info['timeId'] = timeId
        info['action'] = 'add_guding'
        $.post(self_url+"addguding.json", info, function (res) {
            if (res.ok) {
                alert('添加成功')
                window.location.reload()
            } else {
                alert(res.msg)
            }
        }, 'json')
    })

    //日期点击事件
    $gudingWeekList.on('click', 'a.btn', function () {
        var $self = $(this)
        if (!$self.hasClass('active')) {
            var week = $self.attr('data-week')
            getGudingTime(curSiteId, curCateId, week, function () {
                $gudingWeekList.find('a.btn.active').removeClass('active')
                $self.addClass('active')
            })
        }
    })
    //初始化场地item事件
    function initSiteEvent($a) {
        $a.on('click', function () {
            var $self = $(this)
            if (!$self.hasClass('active')) {
                var id = $a.data('id')
                var cateId = $a.data('cateId')
                getGudingTime(id, cateId, curWeek, function () {
                    $gudingWeekList.show()
                    $('#gudingSiteList').find('a.btn.active').removeClass('active')
                    $self.addClass('active')
                })
            }
        })
    }

    //获取预约时间段
    function getGudingTime(siteid, cateid, week, func) {
        $.getJSON(self_url+"listtime.json", {
            action: 'list_time',
            siteId: siteid,
            cateId: cateid,
            week: week
        }, function (res) {
            if (res.ok) {
                var tms = res.data
                var $timeDiv = $('#gudingTimeList')
                $timeDiv.empty()
                if (tms && tms.length > 0) {
                    for (var i in tms) {
                        var $li = $('<li class="list-group-item">'
                            + tms[i].startTime + '~'
                            + tms[i].endTime + '</li>')
                        $li.data('id', tms[i].id)
                        $timeDiv.append($li)
                        $li.on('click', function () {
                            var $this = $(this)
                            if (!$this.hasClass('active')) {
                                $('#gudingTimeList').find('li.active').removeClass('active')
                                $this.addClass('active')
                                $gudingModal.data('timeId', $this.data('id'))
                            }
                        })
                    }
                } else {
                    $timeDiv.append('<li class="list-group-item"><div class="text-muted text-center">暂无可用时间</div></li>')
                }
                curWeek = week
                curSiteId = siteid
                curCateId = cateid
                if (func) func()
            }
        })
    }

    function gudingVerify(info) {
        if (!info['username']) {
            return '请输入用户姓名'
        }
        if (!info['deptname']) {
            return '请输入用户部门'
        }
        if (!info['mobile']) {
            return '请输入用户手机号码'
        }
    }

    function initgudingEvent($tr) {
        //删除
        $tr.on('click', '.ui-del', function () {
            var $self = $(this)
            var $parent = $self.parent().parent()
            var id = $parent.data('id')
            var cm = confirm('是否删除？')
            if (cm) {
                $.getJSON(self_url+"delguding.json", {action: 'del_guding', id: id}, function (res) {
                    if (res.ok) {
                        $parent.remove();
                    } else {
                        alert('删除失败！')
                    }
                })
            }
        })

        //编辑
        $tr.on('click', '.ui-edit', function () {
            var $self = $(this)
            var $parent = $self.parent().parent()
            var id = $parent.data('id')
        })

        //修改状态
        $tr.on('click', '.ui-status', function () {
            var $self = $(this)
            var $parent = $self.parent().parent()
            var id = $parent.data('id')
            var status = $parent.data('status')
            var cf = confirm(status == 1 ? '是否取消场地？取消成功将不会删除哦！' : '是否使用场地？')
            if (cf) {
                $.getJSON(self_url, {action: 'editguding.json', id: id, status: status}, function (res) {
                    if (res.ok) {
                        if (res.data == 1) {
                            $self.text('取消场地');
                        } else {
                            $self.text('使用场地')
                        }
                    } else {
                        alert('修改失败！')
                    }
                })
            }
        })
    }
})
var tempE;
function changeStatus(e){
    tempE = e;
    var body =$('#changeStatusModal').find('.modal-body');
    body.empty();
    if(e.attr('timeType') == 2){
        $('#changeStatusTitle').text('固定场次');
        $.ajax({
            type: "POST",
            url: "api/get_guding_by_id.json",
            data: {
                id: e.attr('id')
            },
            dataType: "json",
            async: false,
            success: function(json){
                if(json.data != undefined){
                    var username = '未填写';
                    var deptname = '未填写';
                    var mobile = '未填写';
                    var remark = '未填写';
                    var addTime = '未知时间';
                    if(json.data.username != undefined){
                        username = json.data.username;
                    }
                    if(json.data.deptname != undefined) {
                        deptname = json.data.deptname;
                    }
                    if(json.data.mobile != undefined){
                        mobile = json.data.mobile;
                    }
                    if(json.data.remark != undefined && json.data.remark != ''){
                        remark = json.data.remark;
                    }
                    if(json.data.addTime != undefined){
                        addTime = new Date(json.data.addTime).format('yyyy-MM-dd hh:mm')
                    }
                    body.append('<label>用户姓名：</label><div>'+username+'</div>');
                    body.append('<label>部门名称：</label><div>'+deptname+'</div>');
                    body.append('<label>手机号码：</label><div>'+mobile+'</div>');
                    body.append('<label>备注信息：</label><div>'+remark+'</div>');
                    body.append('<label>添加时间：</label><div>'+addTime+'</div>');
                }else{
                    body.append('<div>未找到相关信息！</div>');
                }
            }
        });

    }else if(e.attr('timeType') == 1){
        if(e.attr('status') == 0){
            ui.confirm("是否取消当前场次",function () {
                $.ajax({
                    type: "POST",
                    url: "api/change_status_by_id.json",
                    data: {
                        id: e.attr('id'),
                        state:3
                    },
                    dataType: "json",
                    async: false,
                    success: function(json){
                        if(json.ok){
                            e.attr('status',3);
                            e.css('background-color','#3c3c3c')
                            ui.alert('取消成功！');
                        }
                    }
                });
            });
        }else if(e.attr('status') == 1){
            $('#changeStatusTitle').text('预约信息');
            $.ajax({
                type: "POST",
                url: "api/get_yuyue_by_id.json",
                data: {
                    timeId: e.attr('id')
                },
                dataType: "json",
                async: false,
                success: function(json){
                    if(json.data != undefined){
                        var username = '未填写';
                        var deptname = '未填写';
                        var mobile = '未填写';
                        var payPrice = '未知'
                        var addTime = '未知时间';
                        if(json.data.username != undefined){
                            username = json.data.username;
                        }
                        if(json.data.deptname != undefined) {
                            deptname = json.data.deptname;
                        }
                        if(json.data.mobile != undefined){
                            mobile = json.data.mobile;
                        }
                        if(json.data.payPrice != undefined && json.data.payPrice != ''){
                            payPrice = json.data.payPrice;
                        }
                        if(json.data.addTime != undefined){
                            addTime = new Date(json.data.addTime).format('yyyy-MM-dd hh:mm')
                        }
                        body.append('<label>用户姓名：</label><div>'+username+'</div>');
                        body.append('<label>部门名称：</label><div>'+deptname+'</div>');
                        body.append('<label>手机号码：</label><div>'+mobile+'</div>');
                        if(json.data.payType == 1 || json.data.payType == 2)
                            body.append('<label>'+ (json.data.payType == 1 ? '支付积分：': '') + (json.data.payType == 2 ? '支付金额：': '') +'</label><div>'+ payPrice +'</div>');
                        body.append('<label>添加时间：</label><div>'+addTime+'</div>');

                        body.append('<button type="button" class="btn btn-default" onclick="cancelModal('+json.data.timeId+')">管理员取消</button>')
                    }else{
                        body.append('<div>未找到相关信息！</div>');
                    }
                }
            });

        }else if(e.attr('status') == 2){
        }else if(e.attr('status') == 3){
            ui.confirm("是否恢复可预约状态",function () {
                $.ajax({
                    type: "POST",
                    url: "api/change_status_by_id.json",
                    data: {
                        id: e.attr('id'),
                        state:0
                    },
                    dataType: "json",
                    async: false,
                    success: function(json){
                        if(json.ok){
                            e.attr('status',0);
                            e.css('background-color','#ffffff')
                            ui.alert('修改成功！');
                        }
                    }
                });
            })
        }
    }

    if((e.attr('timeType') == 1 && e.attr('status') == 1) || (e.attr('timeType') == 2))
        $('#changeStatusModal').modal("show");

}
function cancelModal(timeId){
    $('#cancelReasonModal').modal("show");
    $('#choiceCancelId').val(timeId);
}
function cancelBtn(){
    $.ajax({
        type: "POST",
        url: "api/cancel_state.json",
        data: {
            timeId: $('#choiceCancelId').val(),
            reason:$('#cancelReason').val()
        },
        dataType: "json",
        async: false,
        success: function(json){
            if(json.ok){
                $('#cancelReasonModal').modal("hide");
                $('#changeStatusModal').modal("hide");
                tempE.attr('status',0);
                tempE.css('background-color','#ffffff')
                ui.alert('取消成功！');
            }
        }
    });
}
