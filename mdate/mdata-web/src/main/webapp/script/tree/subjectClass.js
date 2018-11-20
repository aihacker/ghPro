/**
 * Created by sheng on 2017/5/24.
 */
window.onload=function(){
};
//window.name =function(parameter)

//获取主题目录
function ftp_change(){
    pub.postJson('show.json', {
            action: 'getData',
        }, function (result) {
            if (result.ok) {
                document.getElementById("fileName").value="";
                document.getElementById("pathName").value="";
                var zNodes = result.data.allData;
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
                                }else document.getElementById("pathName").value="/";
                                //alert(dir);
                                //document.getElementById("fileName").value=treeNode.name;
                                if(!treeNode.isParent)
                                    document.getElementById("fileName").value=treeNode.name;
                                else document.getElementById("fileName").value="";
                            }
                        }
                    },
                    zTree;
                zTree = $.fn.zTree.init($("#ftpTreeSelect"), setting,zNodes);
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
function run(id) {
    window.location.href='operate.do?action=run&id=' + id;
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
    var periodHour = document.getElementsByName("periodHour");
    if(document.getElementById("everyMonth").checked){
        document.getElementById("periodDay").value=document.getElementById("dayPeriod").value;
        if(periodHour[1].value == "" || periodHour[1].value == null){
            alert("请输入时间！");
            return false;
        }else  document.getElementById("periodHour").value="1970-01-01 " + periodHour[1].value;
    }
    if(document.getElementById("everyWeek").checked){
        if(document.getElementById("weekPeriod").value == "" || document.getElementById("weekPeriod").value == null)
        {
            alert("请选择日期！");
            return false;
        }else document.getElementById("periodDay").value=document.getElementById("weekPeriod").value;
        if(periodHour[2].value == "" || periodHour[2].value == null){
            alert("请输入时间！");
            return false;
        }else  document.getElementById("periodHour").value="1970-01-01 " + periodHour[2].value;
    }
    if(document.getElementById("everyDay").checked){
        if(periodHour[3].value == "" || periodHour[3].value == null){
            alert("请输入时间！");
            return false;
        }else  document.getElementById("periodHour").value="1970-01-01 " + periodHour[3].value;
    }
    document.getElementById("startTime").value=document.getElementById("choiceTime").value.replace("T"," ");

    if(document.getElementById("fileName").value == "" || document.getElementById("fileName").value == null){
        alert("请选择源文件！");
        return false;
    }

    alert("保存成功！");
    return true;
}

