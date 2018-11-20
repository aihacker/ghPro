/**
 * Created by sheng on 2017/5/24.
 */
window.onload=function(){
};

//获取ftp目录
function ftp_change(id){
    if(id == null || id ==""){
        document.getElementById("fileName").value="";
        document.getElementById("ftpTreeSelect").innerHTML='';
        document.getElementById("scriptName").value="";
        document.getElementById("scriptTreeSelect").innerHTML='';
        return ;
    }
    pub.postJson('show.json', {
            action: 'getFtp',
            id: id
        }, function (result) {
            //console.log(result.data)
            if (result.ok) {
                document.getElementById("fileName").value="";
                document.getElementById("pathName").value="";
                document.getElementById("scriptName").value="";
                document.getElementById("scriptPathName").value="";
                var zNodes = result.data.allData;
                var zTree;
                var setting = {
                        check: {
                            enable: false
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        callback: {
                            onClick: function(e, treeId, treeNode, clickFlag) {
                                zTree.checkNode(treeNode, !treeNode.checked, true);
                                var treeObj = $.fn.zTree.getZTreeObj("ftpTreeSelect");
                                var sNodes = treeObj.getSelectedNodes();
                                if (sNodes.length > 0) {
                                    var node = sNodes[0].getParentNode();
                                }
                                var dir="";
                                if(node.name != "根目录"){
                                    while(node.getParentNode()!=null){
                                        dir = node.name + "/" +dir;
                                        node = node.getParentNode();
                                    }
                                    dir = "/" +dir;
                                    document.getElementById("pathName").value=dir;
                                }else document.getElementById("pathName").value="";
                                //alert(dir);
                                //document.getElementById("fileName").value=treeNode.name;
                                if(!treeNode.isParent)
                                    document.getElementById("fileName").value=treeNode.name;
                                else document.getElementById("fileName").value="/";
                            }
                        }
                    };
                zTree = $.fn.zTree.init($("#ftpTreeSelect"), setting,zNodes);

                var zTree2;
                var setting2 = {
                    // async: {
                    //     enable: true,
                    //     url: "http://host/getNode.php",
                    //     autoParam: ["id"]
                    // },

                    check: {
                        enable: false
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    callback: {
                        onClick: function(e, treeId, treeNode, clickFlag) {
                            zTree2.checkNode(treeNode, !treeNode.checked, true);
                            var treeObj = $.fn.zTree.getZTreeObj("scriptTreeSelect");
                            var sNodes = treeObj.getSelectedNodes();
                            if (sNodes.length > 0) {
                                var node = sNodes[0].getParentNode();
                            }
                            var dir="";
                            if(node.name != "根目录"){
                                while(node.getParentNode()!=null){
                                    dir = node.name + "/" +dir;
                                    node = node.getParentNode();
                                }
                                dir = "/" +dir;
                                document.getElementById("scriptPathName").value=dir;
                            }else document.getElementById("scriptPathName").value="";
                            //alert(dir);
                            //document.getElementById("fileName").value=treeNode.name;
                            if(!treeNode.isParent)
                                document.getElementById("scriptName").value=treeNode.name;
                            else document.getElementById("scriptName").value="/";
                        }
                    }
                };
                zTree2 = $.fn.zTree.init($("#scriptTreeSelect"), setting2,zNodes);
            }
        },
        pub.ajaxFailure
    );
}

//根据目录查询ftp
//function file_change(dir){
//    pub.postJson('show.json', {
//            action: 'queryFile',
//            id: document.getElementById("ftp").value,
//            dir: dir
//        }, function (result) {
//            if (result.ok) {
//                var html = "";
//                var datas = result.data;
//                for (var i = 0; i < datas.length; i++) {
//                    html += '<label><input name="' + "file" + '" ' +
//                        'type="' + "radio" + '" value="' + datas[i] + '" ' +
//                        'onchange="' + "file_select()" + '" ' +
//                        '/>'+datas[i]+'</label><br />'
//                }
//                $("#fileArea").html(html);
////                $('#fileArea').comboSelect();
//
//            }
//        },
//        pub.ajaxFailure
//    );
//}

//下拉选择文件
//function file_select(){
//    var value='';
//    var radio = document.getElementsByName("file");
//    for(var i = 0;i<radio.length;i++)
//    {
//        if(radio[i].checked==true)
//        {
//            value = radio[i].value;
//            break;
//        }
//    }
//    var html = '<span id="selectFileName" style="color:blue;font-size:20px;">' + value + ' </span>';
//    $("#selectFile").html(html);
//    //$('#selectFile').comboSelect();
//}

//改变数据库更新数据表
function db_schema_change(schema){
    pub.postJson('show.json', {
            action: 'queryTable',
            schema: schema
        }, function (result) {
            if (result.ok) {
                var html = "";
                var datas = result.data;
                document.getElementById("inputTable").value="";
                for (var i = 0; i < datas.length; i++) {

                    html += '<option value="' + datas[i] + '" >' + datas[i] + ' </option>'
                }
                $("#dbTable").html(html);
                //$('#dbTable').comboSelect();
            }
        },
        pub.ajaxFailure
    );
}

//弹出表字段选择
//function change_table_show(){
//    if($("#dbTableField").is(":hidden"))
//        document.getElementById("dbTableField").style.display="";
//    else document.getElementById("dbTableField").style.display="none";
//}

//全选表字段
//function change_all_check(){
//    var checklist = document.getElementsByName("variable");
//    if(document.getElementById("checkAll").checked)
//    {
//        for(var i=0;i<checklist.length;i++)
//        {
//            checklist[i].checked = 1;
//        }
//    }else{
//        for(var j=0;j<checklist.length;j++)
//        {
//            checklist[j].checked = 0;
//        }
//    }
//}

//显示表
//function show_table(){
//    pub.postJson('show.json', {
//            action: 'queryColumn',
//            tableName: document.getElementById("dbTable").value,
//            schema:document.getElementById("dbSchema").value
//        }, function (result) {
//            if (result.ok) {
//                var table=document.getElementById("dbTableField");
//                table.innerHTML="";
//                var tr = table.insertRow();
//                var td1 = tr.insertCell();
//                var td2 = tr.insertCell();
//                td1.innerHTML='<input id="checkAll" type="checkbox" onchange="change_all_check()" checked="checked">';
//                td2.innerHTML="字段";
//                var datas = result.data;
//                for (var i = 0; i < datas.length; i++) {
//                    var tr = table.insertRow();
//                    var td1 = tr.insertCell();
//                    var td2 = tr.insertCell();
//                    td1.innerHTML='<input name="variable" type="checkbox" checked="checked" value="name">';
//                    td2.innerHTML=datas[i];
//                }
//            }
//        },
//        pub.ajaxFailure
//    );
//}

//重跑跳转action
function run(id, type) {
    var url = 'operate.json';
    var data = {};
    data.action = 'run';
    data.id = id;
    data.type = type;
    if (!window.confirm('确定执行该操作吗?')) {
        return false;
    }
    pub.getJSON(url,data,function (result) {
        alert(result.msg);
        location.reload();
    })
}

//任务复制
function copy() {
    var ids = [];
    $('input[name="selectbody"]:checked').each(function () {
        var data = $(this).val();
        var id;
        if(null != data && data != ""){
            id = data.split(",")[0];
        }
        ids.push(id);
    });
    if (ids.length == 0) {
        alert("至少选择一条数据");
        return;
    }
    if (!window.confirm('确定复制任务?')) {
        return;
    }
    var data = {
        action: "copy",
        ids: ids
    };
    pub.getJSON("operate.json", data, function () {
        //alert("设置成功！");
        location.reload();
    });
}

function batchRun() {
    var ids = [];
    $('input[name="selectbody"]:checked').each(function () {
        var data = $(this).val();
        var id;
        if(null != data && data != ""){
            id = data.split(",")[0];
        }
        ids.push(id);
    });
    if (ids.length == 0) {
        alert("至少选择一条数据");
        return;
    }
    if (!window.confirm('确定执行?')) {
        return;
    }
    var data = {
        action: "batchRun",
        ids: ids
    };
    pub.getJSON(operateUrl, data, function (result) {
        if (result.ok) {
            alert(result.msg);
            location.reload();
        }
    });
}

function check(){
    var includeField = document.getElementsByName("includeField");
    var isCleanTar = document.getElementsByName("isCleanTar");
    if($('#includeField').is(':checked')){
        $(includeField[0]).val(0);
    }else {
        $(includeField[0]).val(1);
    }
    if($('#isCleanTar').is(':checked')){
        $(isCleanTar[0]).val(0);
    }else {
        $(isCleanTar[0]).val(1);
    }
    var periodHourTime = document.getElementsByName("periodHourTime");
    if(document.getElementById("everyMonth").checked){
        document.getElementById("periodDay").value=document.getElementById("dayPeriod").value;
        if(periodHourTime[0].value == "" || periodHourTime[0].value == null){
            alert("请输入时间！");
            return false;
        }else  document.getElementById("periodHour").value="1970-01-01 " + periodHourTime[0].value;
    }
    if(document.getElementById("everyWeek").checked){
        if(document.getElementById("weekPeriod").value == "" || document.getElementById("weekPeriod").value == null)
        {
            alert("请选择日期！");
            return false;
        }else document.getElementById("periodDay").value=document.getElementById("weekPeriod").value;
        if(periodHourTime[1].value == "" || periodHourTime[1].value == null){
            alert("请输入时间！");
            return false;
        }else  document.getElementById("periodHour").value="1970-01-01 " + periodHourTime[1].value;
    }
    if(document.getElementById("everyDay").checked){
        if(periodHourTime[2].value == "" || periodHourTime[2].value == null){
            alert("请输入时间！");
            return false;
        }else  document.getElementById("periodHour").value="1970-01-01 " + periodHourTime[2].value;
    }
    //document.getElementById("startTime").value=document.getElementById("choiceTime").value.replace("T"," ");

    if(document.getElementById("fileName").value == "" || document.getElementById("fileName").value == null){
        alert("请选择源文件！");
        return false;
    }


    if (!window.confirm('确定保存吗?')) {
        return false;
    }
    return true;
}

//全选、取消全选的事件
function selectAll() {
    var checked = $("#selectAll").attr("checked");
    if (checked == undefined) {
        $("#selectAll").attr("checked", true);
        $("input[name='selectbody']").attr("checked", true);
    } else {
        $("#selectAll").attr("checked", false);
        $("input[name='selectbody']").attr("checked", false);
    }
}

