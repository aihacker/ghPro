/**
 * Created by jyf on 2017/4/1.
 */
//获取字段名
function get_field(icon){
    var icon1 = icon.replace('window','');
    var data = $iframe.save();
    var S = getDateSource(icon1,data);
    var html = "";
    S.forEach(function(item){
        var Icon = $iframe.getICON(item).attr("data");
        if(Icon != undefined){
            var T = JSONParse(Icon);
            var L = T.field.split(T.division);
            for(var n=0;n< L.length;n++){
                html += '<option value="'+L[n]+'">'+L[n]+'</option>';
            }
        }
    });
    return html;
}

//获取数据源
function getDateSource(targetId,data){
    var setObj = new Set();
    $.each(data,function(q,row){
        if(Number(row.componentType)<=200){
            var process_to = row.process_to;
            while(true){
                if(process_to.length==0){
                    return;
                }

                var pa = data[process_to[0]];
                var pr = pa.process_to;
                process_to.splice(0,1);
                for(var i=0;i<pr.length;i++){
                    process_to.push(pr[i]);
                }
                if(pa.id==targetId){
                    setObj.add(row.id);
                    return;
                }

            }
        }
    });
    return setObj;
}

//生成过滤表格
function table_append(value,label,fieldName,id){
    var text = '<tr>' +
        '<td><input type="checkbox" name="recodeCheck" value="'+id+'"></td>' +
        '<td>'+value+'</td>' +
        '<td>'+label+'</td>' +
        '<td>'+fieldName+'</td>' +
        '</tr>';

    return text;
}

//条件事件更新
function condition_update(){
    $('#fieldContext li').unbind('click').click(function(){
        var select_textarea = $('#select_textarea');
        var text = $(this).text();
        var val = $.trim(select_textarea.val())+text;
        select_textarea.val(val);
    });
}

//可变文件截取id&path
function split_val(val) {
    var index = val.indexOf('&');
    var id = val.substring(index + 1);
    var path = val.substring(0, index);
    var data = {id: id, path: path};
    return data;
}

//nav 改变
function onClickModel(divName) {
    $('#datasource').hide();
    $('#dataclean').hide();
    $('#visualization').hide();
    $('#algorithm').hide();
    $('#' + divName).show();

}

//保存模板信息
function save_Model(Model){
    var info1 = $iframe.save();

    var processData = [];
    var taskStr = [];
    $.each(info1,function(a,row){
        var id = row.id.replace(new RegExp('n-*'),'-');
        var processTo = row.process_to.toString().replace(new RegExp(/(n)/g),'-').split(',');
        console.log(processTo);
        var obj = {
            id:id,
            process_to:processTo,
            style:row.style,
            process_name:row.process_name,
            componentType:row.componentType,
            componentId:row.componentId,
            runArgs:row.runArgs,
            path:row.path,
            data:row.data,
            table:row.table
        };
        processData.push(obj);
        var obj2 = {
            id:id,
            modelId:Model.id,
            componentId:row.componentId,
            componentType:row.componentType,
            runArgs:row.runArgs,
            outputFiels:""
        };
        //console.log(JSON.stringify(row.runArgs))
        taskStr.push(JSON.stringify(obj2));
        console.log(taskStr)
    });

    for(var n=0;n<processData.length;n++){
        var info = processData[n];
        var process = info.process_to;
        for(var j=0;j<process.length;j++){
            for(var i=0;i<processData.length;i++){
                if(process[j]==processData[i].id){
                    var process2 = processData[i].process_to;
                    for(var a=0;a<process2.length;a++){
                        if(process2[a] == info.id){
                            alert("程序逻辑编译失败！");
                            return;
                        }
                    }
                }

            }
        }
    }
    console.log(processData);
    var data = {
        action:Model.action,
        id:Model.id,
        modelName:Model.modelName,
        desc:"",
        imageJson:JSON.stringify(processData),
        taskStr:taskStr
    };
    //console.log(data);
    pub.postJson(json_url,data,Model.callback);
}

//保存模板信息回调
function save_Model_CallBack(json){
    flowId = json.data;
    $iframe.update();
    $('#modelTip').hide();
    alert("保存成功");
}
//运行模板信息回调
function run_Model_CallBack(json){
    var result = json.data;
    flowId = result.modelId;
    $iframe.update();
    $('#modelTip').hide();
    var status = result.status;
    cancelMyTimer();
    if(status){
        $('#runInfo').append("<option> " + "运行成功!!" + " </option>");
        getModelResult(json_url + "?action=getModelResult",result.modelId)
    }else {
        $('#runInfo').append("<option> " + "运行失败.." + " </option>");
    }
}
//save=0保存、save=1运行
function SaveOrRunCall(save,action){
    var callback = save_Model_CallBack;
    if(save !=0){
        callback = run_Model_CallBack;
        $('#modelTip').hide();
    }
    if(flowId == 0){
        $('#modelTip').attr('save',save).show();
    }else{
        var model = {
            action:action,
            callback:callback,
            id:flowId,
            modelName:$('#modelName').val()
        };
        save_Model(model);
    }
}
//颜色选择器
function color_check(id,modelTip2){
    modelTip2.find("#"+id).spectrum({
        allowEmpty:true,
        showInitial: true,
        preferredFormat: "hex",
        showInput: true,
        showPalette: true,
        showAlpha: true,
        cancelText:"cancel",
        chooseText:"choose",
        palette: [
            ["#000","#444","#666","#999","#ccc","#eee","#f3f3f3","#fff"],
            ["#f00","#f90","#ff0","#0f0","#0ff","#00f","#90f","#f0f"],
            ["#f4cccc","#fce5cd","#fff2cc","#d9ead3","#d0e0e3","#cfe2f3","#d9d2e9","#ead1dc"],
            ["#ea9999","#f9cb9c","#ffe599","#b6d7a8","#a2c4c9","#9fc5e8","#b4a7d6","#d5a6bd"],
            ["#e06666","#f6b26b","#ffd966","#93c47d","#76a5af","#6fa8dc","#8e7cc3","#c27ba0"],
            ["#c00","#e69138","#f1c232","#6aa84f","#45818e","#3d85c6","#674ea7","#a64d79"],
            ["#900","#b45f06","#bf9000","#38761d","#134f5c","#0b5394","#351c75","#741b47"],
            ["#600","#783f04","#7f6000","#274e13","#0c343d","#073763","#20124d","#4c1130"]
        ]
    }).show();

    modelTip2.find("#btnEnterAColor").click(function() {
        $("#"+id).spectrum("set", $("#enterAColor").val());
    });
}
//删除图标
function delete_icon(id){
    //alert(id);
    var index = id.indexOf('n');
    if(index == 0){
        id = id.replace('n',"-");
    }
    //console.log("delete : " + id)
    if(id<=0){
        return;
    }
    var data = {
        action : "deleteTaskItem",
        taskId : id
    };
    pub.postJSON(json_url,data, function (data) {
        var result = data.data;
        if(result){
            console.log("删除TaskItem成功")
        }else {
            console.log("删除TaskItem失败")
        }
    })
}
//运行图标节点
function run_icon(id,myself){
    //alert(id);
    var VIEW_COMPONENT = 4;
    //console.log("icon_id:" + id)
    var index = id.indexOf('n');
    if(index == 0){
        id = id.replace('n',"-");
    }

    startMyTimer(1000);
    var data = {
        action:'runTaskItem',
        taskId:id
    };
    pub.postJSON(json_url,data, function (data) {
        var taskResult = data.data;
        cancelMyTimer();
        if(taskResult.status != 0){
            var msg = taskResult.msg;
            msg = (msg==null ? "运行失败" : msg);
            $('#runInfo').append("<option> " + msg + " </option>");
            return
        }else {
            $('#runInfo').append("<option> " + "运行成功" + " </option>");
        }
        console.log(taskResult);
        if(VIEW_COMPONENT == taskResult.taskType){
            console.log(myself);
            switch (myself.attr('component_type')){
                case '401':
                    showScatter('imageDiv', taskResult);
                    break;
                case '402':
                    showBar('imageDiv', taskResult);
                    break;
                case '403':
                    showPie('imageDiv', taskResult);
                    break;
                case '404':
                    showRadar('imageDiv', taskResult);
                    break;
                case '405':
                    showGraph('imageDiv', taskResult);
                    break;
                case '406':
                    showHistogram('imageDiv', taskResult);
                    break;
                case '407':
                    showPolyphyly('imageDiv', taskResult);
                    break;
                default :break;
            }
            $('#showImage').click();
        }

    })
}

//查看图标结果
function check_icon(id){
    //console.log("icon_id:" + id)
    var index = id.indexOf('n');
    if(index == 0){
        id = id.replace('n',"-");
    }
    getItemResult(id);
}

//Json转对象
function JSONParse(string){
    return JSON.parse(string.replace(/'/g,'"'))
}
//Json对象转string
function JSONStringify(string){
    return JSON.stringify(string).replace(/"/g,"'");
}
//
var myTimer;
function run() {
    //runModelDemo(home+"/runmodel/index.json?action=runModel",
    //    home+"/runmodel/index.json?action=getModelResult",1022);
    $('#resultInfo').empty();
    startMyTimer(1000);
    SaveOrRunCall(1,'runModel');
}

//启动定时器
function startMyTimer(millisecond) {
    if(millisecond == undefined || millisecond==null || millisecond<100){
        millisecond = 1000;
    }
    $('#runInfo').empty();
    myTimer = setInterval(function () {
        $('#runInfo').append("<option> " + "正在运行.." + " </option>");
    }, millisecond)
}
//取消定时器
function cancelMyTimer() {
    clearInterval(myTimer);
}

function writeResult(result) {
    $('#resultInfo').empty();
    if(result == null){
        return
    }
    var arr = result.split("\n");
    //将结果显示
    for (var i=0; i<arr.length; i++){
        $('#resultInfo').append("<option> " + arr[i] + " </option>");
    }
}

function getItemResult(taskId) {
    var data = {
        action:'getTaskItemResult',
        taskId : taskId
    };
    pub.postJSON(json_url,data, function (data) {
        var taskResult = data.data;
        $('#runInfo').empty();
        if(taskResult.status != 0){
            $('#runInfo').append("<option> " + taskResult.msg + " </option>");
            return
        }else {
            $('#runInfo').append("<option> " + "运行成功" + " </option>");
        }
        writeResult(taskResult.result)
    })

}

//数组重复元素剔除
function unique(arr) {
    var result = [], hash = {};
    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
        if (!hash[elem] && elem != '') {
            result.push(elem);
            hash[elem] = true;
        }
    }
    return result;
}

