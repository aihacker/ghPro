(function ($) {
    function JsonToStr(str){
        return JSON.stringify(str).replace(/"/g,"'");
    }

    var defaults = {
        processData: {},//步骤节点数据
        //processUrl:'',//步骤节点数据
        fnRepeat: function () {
            alert("步骤连接重复");
        },
        fnClick: function () {
            alert("单击");
        },
        fnDbClick: function () {
            alert("双击");
        },
        canvasMenus: {
            "one": function (t) {
                alert('画面右键')
            }
        },
        processMenus: {
            "one": function (t) {
                alert('步骤右键')
            }
        },
        /*右键菜单样式*/
        menuStyle: {
            border: '1px solid #5a6377',
            minWidth: '150px',
            padding: '5px 0'
        },
        itemStyle: {
            fontFamily: 'verdana',
            color: '#333',
            border: '0',
            /*borderLeft:'5px solid #fff',*/
            padding: '5px 40px 5px 20px'
        },
        itemHoverStyle: {
            border: '0',
            /*borderLeft:'5px solid #49afcd',*/
            color: '#fff',
            backgroundColor: '#5a6377'
        },
        //这是连接线路的绘画样式
        connectorPaintStyle: {
            lineWidth: 2,
            strokeStyle: "#49afcd",
            joinstyle: "round"
        },
        //鼠标经过样式
        connectorHoverStyle: {
            lineWidth: 2,
            strokeStyle: "#da4f49"
        }

    };
    /*defaults end*/

    var initEndPoints = function () {
        $(".process-flag").each(function (i, e) {
            var p = $(e).parent();
            jsPlumb.makeSource($(e), {
                parent: p,
                anchor: "Continuous",
                endpoint: ["Dot", {radius: 1}],
                connector: ["StateMachine", {stub: [5, 5]}],
                connectorStyle: defaults.connectorPaintStyle,
                hoverPaintStyle: defaults.connectorHoverStyle,
                dragOptions: {},
                maxConnections: -1
            });
        });
    };

    /*设置隐藏域保存关系信息*/
    var aConnections = [];
    var setConnections = function (conn, remove) {
        if (!remove) aConnections.push(conn);
        else {
            var idx = -1;
            for (var i = 0; i < aConnections.length; i++) {
                if (aConnections[i] == conn) {
                    idx = i;
                    break;
                }
            }
            if (idx != -1) aConnections.splice(idx, 1);
        }
        if (aConnections.length > 0) {
            var s = "";
            for (var j = 0; j < aConnections.length; j++) {
                var from = $('#' + aConnections[j].sourceId).attr('process_id');
                var target = $('#' + aConnections[j].targetId).attr('process_id');
                var sr = from+','+target;
                var sr2 = target+','+from;
                s = s + '<input type="hidden" value="'+sr+'" name="'+sr2+'"/>';
            }
            $('#leipi_process_info').html(s);
        } else {
            $('#leipi_process_info').html('');
        }
        jsPlumb.repaintEverything();//重画
    };

    /*Flowdesign 命名纯粹为了美观，而不是 formDesign */
    $.fn.Flowdesign = function (options) {
        var _canvas = $(this);
        //右键步骤的步骤号
        _canvas.append('<input type="hidden" id="leipi_active_id" value="0"/><input type="hidden" id="leipi_copy_id" value="0"/>');
        _canvas.append('<div id="leipi_process_info"></div>');


        /*配置*/
        $.each(options, function (i, val) {
            if (typeof val == 'object' && defaults[i])
                $.extend(defaults[i], val);
            else
                defaults[i] = val;
        });
        /*画布右键绑定*/
        var contextmenu = {
            bindings: defaults.canvasMenus,
            menuStyle: defaults.menuStyle,
            itemStyle: defaults.itemStyle,
            itemHoverStyle: defaults.itemHoverStyle
        };
        $(this).contextMenu('canvasMenu', contextmenu);

        jsPlumb.importDefaults({
            DragOptions: {cursor: 'pointer'},
            EndpointStyle: {fillStyle: '#225588'},
            Endpoint: ["Dot", {radius: 1}],
            ConnectionOverlays: [
                ["Arrow", {location: 1}],
                ["Label", {
                    location: 0.1,
                    id: "label",
                    cssClass: "aLabel"
                }]
            ],
            Anchor: 'Continuous',
            ConnectorZIndex: 5,
            HoverPaintStyle: defaults.connectorHoverStyle
        });
        if ($.browser.msie && $.browser.version < '9.0') { //ie9以下，用VML画图
            jsPlumb.setRenderMode(jsPlumb.VML);
        } else { //其他浏览器用SVG
            jsPlumb.setRenderMode(jsPlumb.SVG);
        }


        //初始化原步骤
        var lastProcessId = 0;
        var processData = defaults.processData;
        if (processData.list) {
            $.each(processData.list, function (i, row) {

                var nodeDiv = document.createElement('div');
                var nodeId = "window" + row.id, badge = 'badge-inverse', icon = 'icon-star';
                if (lastProcessId == 0)//第一步
                {
                    badge = 'badge-info';
                    icon = 'icon-play';
                }
                if (row.icon) {
                    icon = row.icon;
                }
                var $style="";
                if(row.style){
                    var rowStyle = row.style;
                    var background = rowStyle.indexOf("background-image");
                    var cssIndex = rowStyle.indexOf('/css/');
                    var end = rowStyle.substring(cssIndex,rowStyle.length);
                    var index = end.indexOf('");');
                    var $xx = '"';
                    if(index == -1){
                        $xx = '';
                    }
                    $style += rowStyle.substring(0,background+16)
                            +':url('+$xx
                        +window.parent.home
                        +end;
                }
                console.log($style);

                $(nodeDiv).attr("id", nodeId)
                    .attr("style", $style)
                    .attr("process_to", row.process_to)
                    .attr("process_id", row.id)
                    .attr("component_type",row.componentType)
                    .attr("component_id",row.componentId)
                    .attr("table_schema",row.table_schema)
                    .addClass("process-step btn_tip_icon")
                    .html('<div class="btn_top_content"></div>' +
                    '<span class="process-flag btn_tip_flag ' + badge + '"></span>' +
                    '<div class="process_name">' + row.process_name + '</div>')
                    .mousedown(function (e) {
                        if (e.which == 3) { //右键绑定
                            _canvas.find('#leipi_active_id').val(row.id);
                            contextmenu.bindings = defaults.processMenus;
                            $(this).contextMenu('processMenu', contextmenu);
                        }
                    });
                _canvas.append(nodeDiv);
                //索引变量
                lastProcessId = row.id;
            });//each
        }

        var timeout = null;
        //点击或双击事件,这里进行了一个单击事件延迟，因为同时绑定了双击事件
        $(".process-step").live('click', function () {
            defaults.fnClick($(this));
        }).live('dblclick', function () {
            defaults.fnDbClick();
        });

        //使之可拖动
        jsPlumb.draggable(jsPlumb.getSelector(".process-step"));
        initEndPoints();

        //绑定添加连接操作。画线-input text值
        jsPlumb.bind("jsPlumbConnection", function (info) {
            setConnections(info.connection)
        });
        //绑定删除connection事件
        jsPlumb.bind("jsPlumbConnectionDetached", function (info) {
            setConnections(info.connection, true);
        });
        //绑定删除确认操作
        jsPlumb.bind("click", function (c) {
            console.log(c);
            if (confirm("你确定取消连接吗?"))
                jsPlumb.detach(c);
        });

        jsPlumb.makeTarget(jsPlumb.getSelector(".process-step"), {
            dropOptions: {hoverClass: "hover", activeClass: "active"},
            anchor: "Continuous",
            maxConnections: -1,
            endpoint: ["Dot", {radius: 1}],
            paintStyle: {fillStyle: "#ec912a", radius: 1},
            hoverPaintStyle: this.connectorHoverStyle,
            beforeDrop: function (params) {
                var sourceType = $('#' + params.sourceId).attr('component_type');
                var targetType = $('#' + params.targetId).attr('component_type');
                if(targetType<=sourceType){
                    return;
                }
                $('#leipi_process_info').find('input').each(function (i) {

                    var sourceId = $('#' + params.sourceId).attr('process_id');
                    var targetId = $('#' + params.targetId).attr('process_id');

                    if(Number(targetId)<= 200){
                        j++;
                        return;
                    }

                    var str = sourceId + ','+targetId;
                    var str2 = targetId + ','+sourceId;

                    if (str == $(this).val()||str2 == $(this).val()) {
                        j++;

                    }
                });
                if (j > 0) {
                    return false;
                    //alert("非法步骤");
                } else {
                    return true;
                }
            }
        });
        //reset  start
        var _canvas_design = function () {

            //连接关联的步骤
            $('.process-step').each(function (i) {
                var id = $(this).attr('process_id');
                var nodeId = "window" + id;
                var prcsto = $(this).attr('process_to');
                var toArr = prcsto.split(",");
                $.each(toArr, function (j, n) {
                    if (n != '' && n != 0) {
                        jsPlumb.connect({
                            source: nodeId,
                            target: "window" + n
                            /* ,labelStyle : { cssClass:"component label" }
                             ,label : id +" - "+ n*/
                        });
                    }
                })
            });
        };//_canvas_design end reset
        _canvas_design();

//-----外部调用----------------------
//flowdesign_canvas 不传也能获取？
        var Flowdesign = {
            addProcess: function (row) {

                if (row.id <= 0) {
                    return false;
                }
                var nodeDiv = document.createElement('div');
                var nodeId = "window" + row.id, badge = 'badge-inverse', icon = 'icon-star';

                if (row.icon) {
                    icon = row.icon;
                }
                $(nodeDiv).attr("id", nodeId)
                    .attr("style", row.style)
                    .attr("process_to", row.process_to)
                    .attr("process_id", row.id)
                    .attr("component_type",row.componentType)
                    .attr("component_id",row.componentId)
                    .attr("table_schema",row.table_schema)
                    .addClass("process-step btn_tip_icon")
                    .html('<div class="btn_top_content"></div>' +
                    '<span class="process-flag btn_tip_flag ' + badge + '"></span>' +
                    '<div class="process_name">' + row.process_name + '</div>')
                    .mousedown(function (e) {
                        if (e.which == 3) { //右键绑定
                            _canvas.find('#leipi_active_id').val(row.id);
                            contextmenu.bindings = defaults.processMenus;
                            $(this).contextMenu('processMenu', contextmenu);
                        }
                    });

                _canvas.append(nodeDiv);
                //使之可拖动 和 连线
                jsPlumb.draggable(jsPlumb.getSelector(".process-step"));
                initEndPoints();
                //使可以连接线
                jsPlumb.makeTarget(jsPlumb.getSelector(".process-step"), {
                    dropOptions: {hoverClass: "hover", activeClass: "active"},
                    anchor: "Continuous",
                    maxConnections: -1,
                    endpoint: ["Dot", {radius: 1}],
                    paintStyle: {fillStyle: "#ec912a", radius: 1},
                    hoverPaintStyle: this.connectorHoverStyle,
                    beforeDrop: function (params) {
                        var j = 0;
                        var sourceType = $('#' + params.sourceId).attr('component_type');
                        var targetType = $('#' + params.targetId).attr('component_type');
                        if(targetType<=sourceType){
                            return;
                        }
                        $('#leipi_process_info').find('input').each(function (i) {



                            var sourceId = $('#' + params.sourceId).attr('process_id');
                            var targetId = $('#' + params.targetId).attr('process_id');

                            var str = sourceId + ','+targetId;
                            var str2 = targetId + ','+sourceId;

                            if (str == $(this).val()||str2 == $(this).val()) {
                                j++;

                            }
                        });
                        if (j > 0) {

                        } else {
                            return true;
                        }
                    }
                });
                return true;
            },
            getActiveId: function () {
                return _canvas.find("#leipi_active_id").val();
            },
            getProcessInfo: function () {
                try {
                    /*连接关系*/
                    var aProcessData = {};
                    //
                    var processInfo = $("#leipi_process_info input[type=hidden]");
                    processInfo.each(function (i) {
                        var processVal = $(this).val().split(",");
                        if (processVal.length == 2) {
                            if (!aProcessData[processVal[0]]) {
                                aProcessData[processVal[0]] = {"id":processVal[0],"table_schema":"","componentId":"","componentType":"","process_name":"","style":"","top": 0, "left": 0, "process_to": [],"parentIds":[]};
                            }
                            aProcessData[processVal[0]]["process_to"].push(processVal[1]);
                        }
                    });

                    /*位置*/
                    _canvas.find("div.process-step").each(function (i) { //生成Json字符串，发送到服务器解析
                        if ($(this).attr('id')) {
                            var pId = $(this).attr('process_id');
                            var pLeft = parseInt($(this).css('left'));
                            var pTop = parseInt($(this).css('top'));
                            if (!aProcessData[pId]) {
                                aProcessData[pId] = {"id":pId,"table_schema":"","componentId":"","componentType":"","process_name":"","style":"","top": 0, "left": 0, "process_to": [],"parentIds":[]};
                            }
                            aProcessData[pId]["top"] = pTop;
                            aProcessData[pId]["left"] = pLeft;
                            aProcessData[pId]["style"] = $(this).attr('style');
                            aProcessData[pId]["process_name"] = $(this).find("div").text();
                            aProcessData[pId]["componentType"] = $(this).attr('component_type');
                            aProcessData[pId]["componentId"] = $(this).attr('component_id');
                            aProcessData[pId]["table_schema"] = $(this).attr('table_schema');


                        }
                    });

                    processInfo.each(function (i) {
                        var processVal = $(this).val().split(",");
                        if (processVal.length == 2) {
                            aProcessData[processVal[1]]["parentIds"].push(processVal[0]);
                        }
                    });
                    return aProcessData;
                } catch (e) {
                    return '';
                }

            },
            clear: function () {
                try {

                    jsPlumb.detachEveryConnection();
                    jsPlumb.deleteEveryEndpoint();
                    $('#leipi_process_info').html('');
                    jsPlumb.repaintEverything();
                    return true;
                } catch (e) {
                    return false;
                }
            }, refresh: function () {
                try {
                    //jsPlumb.reset();
                    this.clear();
                    _canvas_design();
                    return true;
                } catch (e) {
                    return false;
                }
            }
        };
        return Flowdesign;


    }//$.fn
})(jQuery);