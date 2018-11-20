/**
 * Created by Administrator on 2017-04-28.
 */
$(document).ready(function () {
    window.i = 0;

    $("#db").change(function(){
        $("#dbSchema option").each(function(index){
            if(index!=0) {
                $(this).remove();
            }
        });
        var dbid = this.value;
        var url = "show.json";
        pub.postJSON(url,{"action":"getUsersByDb","dbid":dbid},function (result) {
            var users = result.data;
            for(var x=0;x<users.length;x++){
                var user = users[x];
                $("<option></option>", { value: user, text: user }).appendTo($("#dbSchema"));
            }
        });
    });

});

function showTable(id) {
    var gpSchema = $("#gpSchema option:selected").val();
    if(gpSchema == ""){
        alert("请选择GP模式");
        return;
    }
    var gpTable = $("#gpTable").val();
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'query', gpTable: gpTable ,gpSchema: gpSchema},//  datasource: datasource,
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                var html = "";
                //0表示改列表为GP表。1表示改列表为DB表
                $("#ifTable").val(0);
                for (var i = 0; i < keywords.length; i++) {
                    html += ' <div> <span><input type="radio" style="margin-top: 10px" name="aaaa"   value="' + keywords[i] + '" />' + keywords[i] + '<span></span></div>';
                }
            }
            //$("#modalShow").html(html);
            //$('#tableTip').modal();
            $("#appendtables").html(html);
            $('#tableModule').modal();
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

function showDbTable(id) {
    /*var datasource = $("#db option:selected").val();
     var schemaName = $("#userSchemas option:selected").val();
     var keywords = $("#keywords").val();*/
    //var url = "${home}/datasyn/datasource/show.json?action=getTableByDb";
    var dbid = $("#db").val();
    var dbSchema =  $("#dbSchema").val();
    if(dbid == ""){
        alert("请选择目标库");
        return;
    }
    if(dbSchema == ""){
        alert("请选择目标模式");
        return;
    }
    var dbTable =  $("#dbTable").val();

    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'getTableByDb',  dbid: dbid, dbSchema: dbSchema, dbTable:dbTable},//datasource: datasource,
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                var html = "";
                //0表示改列表为GP表。1表示改列表为DB表
                $("#ifTable").val(1);
                for (var i = 0; i < keywords.length; i++) {
                    html += ' <div> <span><input type="radio" style="margin-top: 10px" name="aaaa"   value="' + keywords[i] + '" />' + keywords[i] + '<span></span></div>';
                }
            }
            //$("#modalShow").html(html);
            //$('#tableTip').modal();
            $("#appendtables").html(html);
            $('#tableModule').modal();
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

//填充表名
function fillTables() {
    var table = $('#tableModule input[name="aaaa"]:checked ').val();
    var ifTable = $("#ifTable").val();
    if(ifTable == 0){
        $("#gpTableText").val(table);
    }else if(ifTable == 1){
        $("#dbTableText").val(table);
    }
}





function addIndex(type) {//type为0表示关键字段，为1 表示比较字段

    var gpSchema = $("#gpSchema option:selected").val();
    var gpTable = $("#gpTableText").val();
    if(gpSchema == ""){
        alert("请选择GP模式");
        return;
    }
    if(gpTable == ""){
        alert("请选择GP表");
        return;
    }
    var column;
    if(type == 0){
        column = $("#indexColumn").val();
    }else{
        column = $("#compareColumn").val();
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'queryColumn',  gpSchema: gpSchema, gpTable: gpTable ,column: column},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                var html = "";
                if(type == 0){
                    $("#ifColumn").val(0);
                }else{
                    $("#ifColumn").val(1);
                }
                for (var i = 0; i < keywords.length; i++) {
                    html += ' <div> <input type="checkbox" name="cccc"   value="' + keywords[i] + '" />' + keywords[i] + '</div>';
                }

            }
            //$("#modalShow").html(html);
            //$('#tableTip').modal();
            $("#appendindexColumn").html(html);
            $('#columnIndexModule').modal();
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

//填充主键
function fillColumn2() {
    // var column = $('#forms input[type="checkbox"]:checked ').val();
    var adIds = "";
    $('#columnIndexModule input:checkbox[name="cccc"]:checked').each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    if($("#ifColumn").val() == 0){
        $("#indexColumnText").val(adIds);
    }else if ($("#ifColumn").val() == 1){
        $("#compareColumnText").val(adIds);
    }
}

function save(){
    var id = $("#id").val();
    var gpSchema = $("#gpSchema").val();
    var gpTable = $("#gpTableText").val();
    var gpIsSource = $("#gpIsSource").val();
    var db = $("#db").val();
    var dbSchema = $("#dbSchema").val();
    var dbTable = $("#dbTableText").val();
    var indexColumn = $("#indexColumnText").val();
    var compareColumn = $("#compareColumnText").val();
    var addWheres = $("#addWheres").val();
    var defRowcount = $("#defRowcount").val();
    if(gpSchema == ""){
        alert("请选择GP模式");
        return;
    }
    if(gpTable == ""){
        alert("请选择GP表");
        return;
    }
    if(db == ""){
        alert("请选择目标库");
        return;
    }
    if(dbSchema == ""){
        alert("请选择目标模式");
        return;
    }
    if(dbTable == ""){
        alert("请选择目标表");
        return;
    }
    if(indexColumn == ""){
        alert("请选择关键字段");
        return;
    }
    if(compareColumn == ""){
        alert("请选择比较字段");
        return;
    }

    $.ajax({
        url: 'operate.json?action=save',
        data: {"id":id,"gpSchema":gpSchema,"gpTable":gpTable,"gpIsSource":gpIsSource,"targDbId":db,"targSchema":dbSchema,
            "targTable":dbTable,"keyFields":indexColumn,"compFields":compareColumn,"addWheres":addWheres,
            "defRowcount":defRowcount},
        success: function (result) {
            if (result.ok) {
                alert("保存成功");
                //window.location = 'query.html';
                location.href=document.referrer;
            }else{
                alert("保存失败");
            }
        },
        error: function () {
            alert('form submit error..');
        }
    });

}
//});