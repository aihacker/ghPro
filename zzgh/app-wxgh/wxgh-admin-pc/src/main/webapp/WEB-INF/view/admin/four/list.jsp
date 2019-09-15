<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<style>
    .ui-suggest-title,
    .ui-suggest-title p {
        width: 180px;
        max-width: 180px;
    }

    #module {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #f5f5f5;
        display: none;
        z-index: 1000;
    }

    .ui-select-div select.form-control {
        display: inline-block;
        width: 140px;
    }

    .ui-select-div {
        display: inline-block;
        padding: 10px 20px;
    }

    .fa-spinner {
        display: none;
    }
</style>
<div class="row">
    <div class="ui-content">
        <div class="ui-link-group">
            <a id="cateDelBtn" class="btn btn-empty-theme"><span class="fa fa-trash"></span> 批量删除</a>

            <a href="#exportModal" data-toggle="modal" class="btn btn-empty-theme"><span class="fa fa-sign-in"></span>
                四小台账导出</a>

        </div>

        <div class="row" style="margin-top:5px">
            <div class="panel panel-default" style="margin-bottom: 0;">
                <div class="panel-body">
                    <label>区公司：</label>
                    <select id="select-zone" name="selectZone" style="width:150px;height:30px">
                        <option value="-1">请选择</option>
                        <c:forEach items="${depts}" var="d">
                            <option value="${d.deptid}">${d.name}</option>
                        </c:forEach>
                    </select>

                    <label>营销中心：</label>
                    <select id="select-center" name="selectCenter" style="width:150px;height:30px">
                        <option value="-1">请选择</option>
                    </select>
                </div>
            </div>
        </div>

        <div style="margin-top: 5px;">
            <ul id="cateNav" class="nav nav-tabs">
                <li role="presentation" class="active law">
                    <a href="#fourListPane" data-toggle="tab">查看台账汇总 <i class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
                <li role="presentation" class="repair">
                    <a href="#fourGenhuanPane" data-toggle="tab">需要更换的设备 <i
                            class="fa fa-spinner fa-pulse hidden"></i></a>
                </li>
            </ul>

            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="fourListPane">
                    <table id="lawTable" class="table ui-table">
                        <thead>
                        <tr>
                            <th><input class="ui-check-all" type="checkbox"/></th>
                            <th>项目</th>
                            <th>项目内容</th>
                            <th>品牌</th>
                            <th>规格型号</th>
                            <th>数量</th>
                            <th>购置时间</th>
                            <th>设备情况</th>
                            <th>价格</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="lawList">
                        </tbody>
                    </table>
                    <ul class="pagination" id="lawPage"></ul>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="fourGenhuanPane">
                    <table class="table ui-table" id="repairTable">
                        <thead>
                        <tr>
                            <th><input class="ui-check-all" type="checkbox"/></th>
                            <th>项目</th>
                            <th>项目内容</th>
                            <th>品牌</th>
                            <th>规格型号</th>
                            <th>数量</th>
                            <th>购置时间</th>
                            <th>设备情况</th>
                            <th>价格</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="repairList">
                        </tbody>
                    </table>
                    <ul class="pagination" id="repairPage"></ul>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 导出modal -->
<div class="modal fade" id="exportModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">四小台账导出</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" style="width: 80%;">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">区公司：</label>
                        <div class="col-sm-8">
                            <select name="deptid" class="form-control">
                                <option value="0">请选择</option>
                                <c:forEach items="${depts}" var="d">
                                    <option value="${d.deptid}">${d.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">营销中心：</label>
                        <div class="col-sm-8">
                            <select name="mid" class="form-control">
                                <option value="-1">请选择</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">四小项目：</label>
                        <div class="col-sm-8">
                            <select name="fpId" class="form-control">
                                <option value="-1">请选择</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">取消</button>
                <button id="okBtn" type="button" class="btn btn-theme">确定导出</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" id="fourEditModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">台账信息修改
                    <small class="ui-text-info">（双击编辑台账信息）</small>
                </h4>
            </div>
            <div class="modal-body">
                <form id="fourInfoForm">
                    <div class="form-group ui-inline">
                        <label>营销中心信息：</label>
                        <span id="markInfo" class="form-control">xxx营销中心 - 本部</span>
                    </div>
                    <div class="form-group ui-inline">
                        <label>台账信息：</label>
                        <span id="fourInfo" class="form-control">小休息室 - 铁锅</span>
                    </div>
                    <div class="form-group ui-inline">
                        <label>品牌：</label>
                        <input name="brand" class="form-control" readonly="readonly" value="xxx品牌"/>
                    </div>
                    <div class="form-group ui-inline">
                        <label>规格型号：</label>
                        <input name="modelName" class="form-control" value="xXXX品牌" readonly="readonly"/>
                    </div>
                    <div class="form-group ui-inline">
                        <label>数量：</label>
                        <input name="numb" class="form-control no" value="23" readonly="readonly"/> :
                        <input name="unit" class="form-control no" value="个" readonly="readonly"/>
                    </div>
                    <div class="form-group ui-inline">
                        <label>采购时间：</label>
                        <input name="buyTime" class="form-control m-boot-time" value="2017-09-08" readonly="readonly"/>
                    </div>
                    <div class="ui-inline">
                        <div class="form-group ui-inline">
                            <label>资金来源：</label>
                            <select name="priceSource" class="form-control">
                                <option value="福利费">福利费</option>
                                <option value="工会经费">工会经费</option>
                                <option value="资本投资">资本投资</option>
                            </select>
                        </div>
                        <div class="form-group ui-inline">
                            <label>预算单价：</label>
                            <input name="price" style="width: 100px;min-width: 100px;" class="form-control" value="6000"
                                   readonly="readonly"/> 元
                        </div>
                    </div>

                    <div class="ui-inline">
                        <div class="form-group ui-inline">
                            <label>计划更新时间：</label>
                            <input name="planUpdate" style="width: 100px;min-width: 100px;"
                                   class="form-control m-boot-time"
                                   value="6000" readonly="readonly"/>
                        </div>
                        <div class="form-group ui-inline">
                            <label>计划使用年限：</label>
                            <input name="usefulLife" style="width: 100px;min-width: 100px;" class="form-control"
                                   value="6000"
                                   readonly="readonly"/> 年
                        </div>
                    </div>
                    <div class="form-group ui-inline">
                        <label>资产所属：</label>
                        <select name="condStr" class="form-control">
                            <option value="工会">工会</option>
                            <option value="企业">企业</option>
                        </select>
                    </div>
                    <div class="form-group ui-inline">
                        <label>设备情况：</label>
                        <select name="condit" class="form-control">
                            <option value="良好">良好</option>
                            <option value="可以使用">可以使用</option>
                            <option value="需要更换">需要更换</option>
                        </select>
                    </div>
                    <div class="form-group ui-inline">
                        <label style="vertical-align: top;">备 注：</label>
                        <textarea name="remark" class="form-control" readonly="readonly">暂无备注</textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger">删除</button>
                <button type="button" class="btn btn-theme" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="fourImgModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">台账图片</h4>
            </div>
            <div class="modal-body">
                <div class="ui-file-div" id="fourImgDiv">
                    <div class="ui-file-item choose">
                        <img src="${home}/image/common/icon_add.png"/>
                        <input id="fourImgInput" type="file" name="fourImgs">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-theme" data-dismiss="modal">确定编辑</button>
            </div>
        </div>
    </div>
</div>

<script src="${home}/libs/jqPaginator-1.2.0/jqPaginator.min.js"></script>
<script type="text/template" id="lawItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.fpName}}</td>
        <td>{{=it.fpcName}}</td>
        <td>
            {{=it.brand}}
        </td>
        <td>{{=it.modelName}}</td>
        <td>{{=it.numb}}</td>
        <td>{{=it.buyTime}}</td>
        <td>{{=it.condit}}</td>
        <td>{{=it.price}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-theme ui-edit"><span class="fa fa-edit"></span> 编辑</a>
                <a class="btn btn-empty-danger ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-see"></span> 查看图片</a>
            </div>
        </td>
    </tr>
</script>
<script type="text/template" id="repairItem">
    <tr>
        <td><input class="ui-check" type="checkbox"></td>
        <td>{{=it.fpName}}</td>
        <td>{{=it.fpcName}}</td>
        <td>
            {{=it.brand}}
        </td>
        <td>{{=it.modelName}}</td>
        <td>{{=it.numb}}</td>
        <td>{{=it.buyTime}}</td>
        <td>{{=it.condit}}</td>
        <td>{{=it.price}}</td>
        <td>
            <div class="ui-link-group">
                <a class="btn btn-empty-danger ui-del"><span class="fa fa-trash"></span> 删除</a>
                <a class="btn btn-empty-theme ui-see"><span class="fa fa-see"></span> 查看图片</a>
            </div>
        </td>
    </tr>
</script>
<script type="text/javascript" src="${home}/libs/jquery/jquery.form.min.js"></script>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"/>
<script>
    $(function () {
        var __self = 'four/api/'

        var $cateNav = $('#cateNav'),
            $modal = $('#module'),
            $fourInfoFrom = $('#fourInfoForm'),
            $fourImgDiv = $('#fourImgDiv'),
            $body = $('body');

        var turn = true;

        $('.m-boot-time').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            minView: 'month'
        });

        $fourInfoFrom.on('change', '.m-boot-time', function () {
            var $self = $(this);
            var oldVal = $self.data('data'),
                val = $self.val();
            if (val && val != oldVal) {
                var info = {};
                info[$self.attr('name')] = val;
                update_four(info);
                $self.data('data', val);
            }
        });

        // 导出
        $('#exportModal').on('click', '#okBtn', function () {
            var info = $('#exportModal form').serializeJson()
            var data = [];
            if (info['deptid'] && info['deptid'] > 0)
                data['deptid'] = info['deptid'];
            if (info['mid'] && info['mid'] > 0)
                data['mid'] = info['mid'];
            if (info['fpId'] && info['fpId'] > 0)
                data['fpId'] = info['fpId'];
//            var start = info['startWeek']
//            var end = info['endWeek']
//            info['startWeek'] = start.replace(/-/g, '')
//            info['endWeek'] = end.replace(/-/g, '')
            ui.openUrl(homePath + '/admin/four/export.html', data)
        })

        // modal下拉监听
        $("select[name=deptid]").change(function () {
            var deptid = $('select[name=deptid] option:selected').val();
            if(!deptid || deptid < 0)
                return ;
            onSelect('four/api/list_market.json', {deptid: deptid}, $("select[name=mid]"))
            clearSelect($("select[name=fpId]"))
        })

        $("select[name=mid]").change(function () {
            var mid = $('select[name=mid] option:selected').val();
            onSelect('four/api/list_fp.json', {mid: mid}, $("select[name=fpId]"))
        })

        /**
         *
         * @param url
         * @param data
         * @param $select
         * @param id 返回json数据 对应的value
         * @param name
         */
        function onSelect(url, data, $select, id, name) {
            if (!id) id = 'id'
            if (!name) name = 'name'
            ui.post(url, data, function (data) {
                var len = data.length;
                var deptHtml = "";
                deptHtml += "<option value=\"-1\">请选择</option>"
                for (var i = 0; i < len; i++)
                    deptHtml += "<option value=\"" + data[i][id] + "\">" + data[i][name] + "</option>"
                $select.html(deptHtml)
            })
        }

        function clearSelect($select) {
            $select.html("<option value='-1'>请选择</option>")
        }

        // end

        ui.check.init()
        //list
        var lawTable = ui.table('law', {
            req: {
                url: __self + 'getfourlist.json',
                //data: {action: 'list'}
            },
            dataConver: function (d) {
                d['statusName'] = get_status(d.deviceStatus)
                d['applyTime'] = new Date(d.applyTime).format('yyyy-MM-dd hh:mm')
                if (d.planUpdate) d['planUpdate'] = new Date(d.planUpdate).format('yyyy-MM-dd');
                return d
            },
            empty: {
                col: 10,
                html: '暂无台账哦'
            }
        }, function ($item, d) {
            $item.data('data', d);

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'fourlistdel.json')
            });

            //编辑
            $item.on('click', '.ui-edit', function () {
                $('#markInfo').text(d.marketName + ' - ' + d.deptName);
                $('#fourInfo').text(d.fpName + ' - ' + d.fpcName);
                $("input[name=brand]").val(d.brand);
                $("input[name=modelName]").val(d.modelName);
                $("input[name=numb]").val(d.numb);
                $("input[name=unit]").val(d.unit);
                $('input[name=buyTime]').val(d.buyTime).data('data', d.buyTime);
                $("input[name=price]").val(d.price);
                $("input[name=planUpdate]").val(d.planUpdate).data('data', d.planUpdate);
                $("input[name=usefulLife]").val(d.usefulLife);

                $('select[name=priceSource]').find("option[value='" + d.priceSource + "']").prop('selected', true);
                $('select[name=condStr]').find("option[value='" + d.condStr + "']").prop('selected', true);
                $('select[name=condit]').find("option[value='" + d.condit + "']").prop('selected', true);

                $('textarea[name=remark]').val(d.remark);
                $('#fourEditModal').modal('show');
                $fourInfoFrom.data('id', d.id);
            });

            //查看图片
            $item.on('click', '.ui-see', function () {
                $.each($fourImgDiv.find('.old'), function () {
                    $(this).remove();
                });
//                var imgs = d.imgs;
//                if (imgs) {
//                    imgs = imgs.split(',');
//                    for (var i in imgs) {
//                        create_four_img(imgs[i]);
//                    }
//                }
                var thumbs = d.thumbs;
                if(thumbs){
                    for(var i in thumbs){
                        create_four_img(thumbs[i]);
                    }
                }
                $('#fourImgModal').modal('show');
                $fourInfoFrom.data('id', d.id);
            });
        });
        lawTable.init();

        //list
        var repairTable = ui.table('repair', {
            req: {
                url: __self + 'getfourlistrepair.json',
                data: {updateYear: new Date().getFullYear(),condit:"需要更换"}
            },
            dataConver: function (d) {
                d['statusName'] = get_status(d.deviceStatus)
                d['applyTime'] = new Date(d.applyTime).format('yyyy-MM-dd hh:mm')
                if (d.planUpdate) d['planUpdate'] = new Date(d.planUpdate).format('yyyy-MM-dd');
                return d
            },
            empty: {
                col: 10,
                html: '今年暂无需要更换台账哦'
            }
        }, function ($item, d) {
            $item.data('data', d)

            //删除
            $item.on('click', '.ui-del', function () {
                ui.del(d.id, function () {
                    ui.alert('删除成功！')
                    $item.remove()
                }, __self + 'fourlistdel.json')
            })


            //查看图片
            $item.on('click', '.ui-see', function () {
                $.each($fourImgDiv.find('.old'), function () {
                    $(this).remove();
                });
                var imgs = d.imgs;
                if (imgs) {
                    imgs = imgs.split(',');
                    for (var i in imgs) {
                        create_four_img(imgs[i]);
                    }
                }
                $('#fourImgModal').modal('show');
                $fourInfoFrom.data('id', d.id);
            });
        });
        repairTable.init();

//        $("a[href='#fourGenhuanPane']").on('shown.bs.tab', function (e) {
//            var isInit = $body.data('init');
//            if (!isInit || isInit == false) {
//                repairTable.init({deptId:zoneId});
//                $body.data('init', true);
//            }
//        });

        //批量删除
        $('#cateDelBtn').on('click', function () {
            if (turn) {
                var ids = lawTable.get_checked_ids()
                if (ids.length <= 0) {
                    ui.alert('请选择需要删除的项目！')
                    return
                }
                ui.del(ids, function () {
                    ui.alert('删除成功！', function () {
                        lawTable.request(null, lawTable)
                    })
                }, __self + 'fourlistdel.json')
            } else {
                var ids = repairTable.get_checked_ids()
                if (ids.length <= 0) {
                    ui.alert('请选择需要删除的项目！')
                    return
                }
                ui.del(ids, function () {
                    ui.alert('删除成功！', function () {
                        repairTable.request(null, repairTable)
                    })
                }, __self + 'fourrepairdel.json')
            }
        })

        //管理
        $('#cateManager').on('click', function () {
            ui.history.go('union/suggestcate')
        });

        $fourInfoFrom.on('dblclick', 'input[name],textarea[name]', function () {
            var $self = $(this);
            if ($self.attr('name') == 'planUpdate') return;
            $self.data('data', $self.val());
            $self.removeAttr('readonly');
        });
        $fourInfoFrom.on('blur', 'input[name],textarea[name]', function () {
            var $self = $(this),
                name = $self.attr('name');
            if (name == 'planUpdate') return;
            var oldVal = $self.data('data'),
                val = $self.val();
            if (val && oldVal != val) {
                var info = {};
                info[name] = val;
                update_four(info, function () {
                    $self.attr('readonly', true)
                        .data('data', val);
                });
            }
        });
        $fourInfoFrom.on('change', 'select[name]', function () {
            var $self = $(this);
            var info = {};
            info[$self.attr('name')] = $self.val();
            update_four(info);
        });

        //选择公司 ，营销中心
        $("select[name=selectZone]").change(function () {
            var zoneId = $('select[name=selectZone] option:selected').val();
            if(zoneId < 0 || !zoneId)
                return ;
            onSelect('four/api/list_market.json', {deptid: zoneId}, $("select[name=selectCenter]"))
            lawTable.refresh({deptId: zoneId,mid:null});
//            var isInit = $body.data('init');
//            console.log(isInit)
//            if (isInit == true) {
//                console.log(isInit)
//
//            }
            repairTable.refresh({deptId: zoneId,mid:null});
            $("select[name=selectCenter]").change(function () {
                var midId = $('select[name=selectCenter] option:selected').val();
                if (midId == -1) {
                    lawTable.refresh({deptId: zoneId, mid: null});
                    repairTable.refresh({deptId: zoneId, mid: null});
                } else {
                    lawTable.refresh({deptId: zoneId, mid: midId});
                    repairTable.refresh({deptId: zoneId, mid: midId});
                }
            })
            clearSelect($("select[name=fpId]"))
        });



        $('input[name=fourImgs]').change(function () {
            var files = this.files;
            if (files && files.length > 0) {
                edit_upload()
            }
        });

       function edit_upload() {
           ui.ajaxFile('four/api/edit_four.json', {id: $fourInfoFrom.data('id')}, 'fourImgInput', function (imgs) {
               if (imgs && imgs.length > 0) {
                   for (var i in imgs) {
                       create_four_img(imgs[i]);
                   }
               }
               $("input[name=fourImgs]").replaceWith('<input id="fourImgInput" type="file" name="fourImgs">');
               $("input[name=fourImgs]").change(function () {
                   var files = this.files;
                   if (files && files.length > 0) {
                       edit_upload()
                   }
               });
           });
       }

        function get_status(status) {
            var str
            if (status == 1) {
                str = '新购'
            } else if (status == 2) {
                str = '维修'
            } else {
                str = '未知'
            }
            return str
        }

        //更新台账
        function update_four(info, func) {
            info['id'] = $fourInfoFrom.data('id');
            ui.post('four/api/edit_four.json', info, function () {
                if (func) func();
            });
        }

        function create_four_img(src) {
            var $item = $('<div class="ui-file-item old">' +
                '<img data-img="' + src + '" src="' + ui.get_image(src) + '"/><span class="fa fa-close"></span></div>')
            $item.on('click', '.fa-close', function () {
                var $self = $(this);
                $self.parent().remove();
                var imgs = [];
                $.each($fourImgDiv.find('.ui-file-item.old img'), function () {
                    imgs.push($(this).data("img"));
                });
                update_four({imgs: imgs.toString()});
            });
            $fourImgDiv.append($item);
        }
    })
</script>


