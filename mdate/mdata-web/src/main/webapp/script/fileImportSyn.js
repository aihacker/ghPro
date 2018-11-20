/**
 * Created by sheng on 2017/5/24.
 */
//window.name =function(parameter)

function db_schema_change(schema){
    pub.postJson('show.json', {
            action: 'queryTable',
            schema: schema
        }, function (result) {
            if (result.ok) {
                var html = "";
                var datas = result.data;
                for (var i = 0; i < datas.length; i++) {

                    html += '<option value="' + datas[i] + '" >' + datas[i] + ' </option>'
                }
                $("#dbTable").html(html);
                $("#tableInput").val(datas[0]);
                //$('#dbTable').comboSelect();
            }
        },
        pub.ajaxFailure
    );
}
function change_table_show(){
    if($("#dbTableField").is(":hidden"))
        document.getElementById("dbTableField").style.display="";
    else document.getElementById("dbTableField").style.display="none";
}
function change_all_check(){
    var checklist = document.getElementsByName("variable");
    if(document.getElementById("checkAll").checked)
    {
        for(var i=0;i<checklist.length;i++)
        {
            checklist[i].checked = 1;
        }
    }else{
        for(var j=0;j<checklist.length;j++)
        {
            checklist[j].checked = 0;
        }
    }
}
function show_table(){
    $('#tableInput').val($('#dbTable').val());
}

$(document).ready(function() {
    $("#formSub").ajaxForm(function(result){
        console.log(result);
        if (result.ok) {
            location.href=document.referrer;
        }
    });
});

function formOnclik() {
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
    //document.getElementById("startTime").value=document.getElementById("choiceTime").value.replace("T"," ");
    /*console.log(document.getElementById("startTime").value);
        return ;*/
    var file = $('#file').val();
    if($("#fileShow").val() == ""){
        if(!file){
            alert("请选择源文件！");
            return false;
        }
    }
    return ;
    var fileArr = file.toString().split(".");
    var name = fileArr[fileArr.length-1].toString().toLocaleLowerCase();
    if((name!="csv") && (name!="txt")){
        alert("源文件格式错误，请选择csv或txt文件格式");
        return false;
    }
    if(document.getElementById("tableInput").value == "" || document.getElementById("tableInput").value == null){
        alert("请选择或输入导入表！");
        return false;
    }
    if (!window.confirm('确定保存吗?')) {
        return false;
    }

    return true;
}

function filesShow(){
    if($('#file').val() != ""){
        document.getElementById("fileShow").style.display="none";
    }else{
        document.getElementById("fileShow").style.display="block";
    }
}

