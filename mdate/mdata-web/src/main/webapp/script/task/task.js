/**
 * Created by Administrator on 2017-04-28.
 */
$(document).ready(function () {
    fillOwers();
    window.i = 0;
    $('#tableSchema').change(function () {
        //$('#').html('你选择了第' + e.target.selectedIndex + '项，值是' +e.target.value );
        var schema = $("#tableSchema").val();
        if (schema == "" || schema == null) {
            return;
        }
        $.ajax({
            dataType: 'json',
            url: 'show.json',
            data: {action: 'queryTable', schema: schema},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    var html = "<option value=''>请选择</option>";
                    var datas = result.data;
                    for (var i = 0; i < datas.length; i++) {
                        html += '<option value="' + datas[i] + '">' + datas[i] + ' </option>'
                    }
                    $("#dbTable").html(html);
                    $("#desTable").val(datas[0]);
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    });
    //show();
    $("input[name='incrementPeriod']").change(function () {
        $("input[name='incrementVarHour']").val("");
    });
    $("input[name='fullPeriod']").change(function () {
        $("input[name='fullVarHour']").val("");
    });
});
function showdb() {

    $('#tableInput').val($('#dbTable').val());
}

function show() {
    var tag = $("input[name='incrementPeriod'][checked]").val();
    var incrementVarHour = $("input[name='incrementVarHour']:first").val();
    if (incrementVarHour == "") {
    } else {
        $("input[name='incrementVarHour']").val("");
        if (tag == 0) {
            $("input[name='incrementVarHour']:first").val(incrementVarHour);
        } else if (tag == 1) {
            $("input[name='incrementVarHour']").eq(1).val(incrementVarHour);
        } else if (tag == 2) {
            $("input[name='incrementVarHour']").eq(2).val(incrementVarHour);
        } else if (tag = 3) {
            $("input[name='incrementVarHour']").eq(3).val(incrementVarHour);
        }
    }
    var tag2 = $("input[name='fullPeriod'][checked]").val();
    var fullVarHour = $("input[name='fullVarHour']:first").val();
    if (fullVarHour == "") {
    } else {
        $("input[name='fullVarHour']").val("");
        if (tag2 == 0) {
            $("input[name='fullVarHour']:first").val(fullVarHour);
        } else if (tag2 == 1) {
            $("input[name='fullVarHour']").eq(1).val(fullVarHour);
        } else if (tag2 == 2) {
            $("input[name='fullVarHour']").eq(2).val(fullVarHour);
        } else if (tag2 = 3) {
            $("input[name='fullVarHour']").eq(3).val(fullVarHour);
        }
    }


}


function showTable(id) {
    var datasource = $("#dataSource option:selected").val();
    var keywords = $("#keywords").val();
    var userName = $("#remoteUserName option:selected").val();
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'query', datasource: datasource, keywords: keywords, user: userName},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                //var html = '<div style="display: none" id="tableTip"><div class="modal fade in" data-backdrop="false" data-show="false"  style="display: block"> <div class="modal-dialog" style="width: 500px;"> '
                //    + '<div class="modal-content"><div class="modal-header"><h5 class="modal-title">表名列表</h5></div>'
                //    + '<div class="modal-body"> <div class="form-group" style="min-height: 50px;overflow-y: auto"><div class="col-xs-12 "><div class="form-inline"> ' +
                //    '<div class="form-group col-xs-12" style="position: relative" id="forms">'
                //for (var i = 0; i < keywords.length; i++) {
                //    html += ' <div class="checkbox-inline"> <span><input type="radio" style="margin-top: 10px" name="table"   value="' + keywords[i] + '" />' + keywords[i] + '<span></span></div>';
                //}
                //html += '</div> </div> </div> </div> </div><div class="modal-footer"> <button type="button" class="btn btn-default" data-dismiss="modal">关闭'
                //    + '</button> <button type="button" class="btn btn-primary" onclick="fillTables()" data-dismiss="modal">确定 </button>'
                //    + '</div> </div> </div> </div> <div class="modal-backdrop fade in"></div></div>';

                var html = "";
                if (keywords != null) {
                    for (var i = 0; i < keywords.length; i++) {
                        html += ' <div> <span><input type="radio" style="margin-top: 10px" name="aaaa"   value="' + keywords[i] + '" />' + keywords[i] + '<span></span></div>';
                    }
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
    $("#remoteTable").val(table);
}


function fillOwers() {
    $("#dataSource").bind("change", function () {
        var id = $('#dataSource option:selected').val();
        $.ajax({
            dataType: 'json',
            url: 'show.json',
            data: {action: 'queryOwer', id: id},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    var html = "<option value=''>请选择</option>";
                    var datas = result.data;
                    if (datas != null) {
                        for (var i = 0; i < datas.length; i++) {
                            html += '<option value="' + datas[i] + '">' + datas[i] + ' </option>'
                        }
                    } else {
                        html = "<option value=''>没有可选用户</option>";
                    }
                    $("#remoteUserName").html(html);
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    })
}


function addCloum(id) {
    var datasource = $("#dataSource option:selected").val();
    var owner = $("#remoteUserName option:selected").val();
    var table = $("#remoteTable").val();
    if (table == '') {
        alert("请先查询远程数据表");
        return;
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'queryColumn', datasource: datasource, table: table, owner: owner},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                var html = "";
                for (var i = 0; i < keywords.length; i++) {
                    html += ' <div> <input type="checkbox" name="bbbb"   value="' + keywords[i] + '" />' + keywords[i] + '</div>';
                }

            }
            //$("#modalShow").html(html);
            //$('#tableTip').modal();
            $("#appendcolumn").html(html);
            $('#columnModule').modal();
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

function getGPUser() {
    var data = {action: 'getGPUser'};

    pub.getJSON(jsonUrl, data, function (result) {
        var user = result.data;
        var html = "";
        $("#dateType").val(0);
        for (var i = 0; i < user.length; i++) {
            html += ' <div> <input type="checkbox" name="gp_user" value="' + user[i] + '" />' + user[i] + '</div>';
        }
        $("#appendGPUser").html(html);
        $('#gpUserModule').modal();
    })

}

function getEnColumn() {
    var datasource = $("#dataSource option:selected").val();
    var owner = $("#remoteUserName option:selected").val();
    var table = $("#remoteTable").val();
    var column = $("#column").val();
    if (datasource == null || datasource == "") {
        alert("请选择数据源!");
        return;
    }
    if (owner == null || owner == "") {
        alert("请选择远程模式!");
        return;
    }
    if (table == null || table == "") {
        alert("请选择远程数据表!");
        return;
    }
    if (column == null || column == "") {
        alert("请选择同步列!");
        return;
    }
    var data = {action: 'queryEnColumn', datasource: datasource, column: column, table: table, owner: owner};
    pub.getJSON(jsonUrl, data, function (result) {
        var user = result.data;
        var html = "";
        $("#dateType").val(1);
        for (var i = 0; i < user.length; i++) {
            html += ' <div> <input type="checkbox" name="gp_user" value="' + user[i] + '" />' + user[i] + '</div>';
        }
        $("#appendGPUser").html(html);
        $('#gpUserModule').modal();
    })
}

function setNull() {
    $("#enColumn").empty();
}

//填充GP用户名
function addGPUser() {
    // var column = $('#forms input[type="checkbox"]:checked ').val();
    var users = "";
    $('#appendGPUser input:checkbox:checked').each(function (i) {
        if (0 == i) {
            users = $(this).val();
        } else {
            users += ("," + $(this).val());
        }
    });
    if (0 == $("#dateType").val()) {//dateType 为0是授权用户 ,为1是加密字段
        $("#gpUser").val(users);
    } else {
        $("#enColumn").val(users);
    }


}

//填充同步列
function fillColumn() {
    // var column = $('#forms input[type="checkbox"]:checked ').val();
    var adIds = "";
    $('#columnModule input:checkbox[name="bbbb"]:checked').each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    //alert(adIds);
    //console.log("---------" + $("#column").html())
    $("#column").val(adIds);
}


function addIndex(id) {
    var datasource = $("#dataSource option:selected").val();
    var owner = $("#remoteUserName option:selected").val();
    var table = $("#remoteTable").val();
    if (table == '') {
        alert("请先查询远程数据表");
        return;
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'queryColumn', datasource: datasource, table: table, owner: owner},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                var html = "";
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
    $("#pk").val(adIds);
}


//增量索引
function addPK() {
    var datasource = $("#dataSource option:selected").val();
    var owner = $("#remoteUserName option:selected").val();
    var table = $("#remoteTable").val();
    if (table == '') {
        alert("请先查询远程数据表");
        return;
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'queryColumn', datasource: datasource, table: table, owner: owner},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                //var html = '<div style="display: none" id="tableTip"><div class="modal fade in" data-backdrop="false" data-show="false"  style="display: block"> <div class="modal-dialog" style="width: 500px;"> '
                //    + '<div class="modal-content"><div class="modal-header"><h5 class="modal-title">表名列表</h5></div>'
                //    + '<div class="modal-body"> <div class="form-group"><div class="col-xs-12 "><div class="form-inline"> ' +
                //    '<div class="form-group col-xs-12" style="position: relative" id="forms">'
                //for (var i = 0; i < keywords.length; i++) {
                //    html += ' <div> <input type="radio" name="table"   value="' + keywords[i] + '" />' + keywords[i] + '</div>';
                //}
                //html += '</div> </div> </div> </div> </div><div class="modal-footer"> <button type="button" class="btn btn-default" data-dismiss="modal">关闭'
                //    + '</button> <button type="button" class="btn btn-primary" onclick="fillIndex()" data-dismiss="modal">确定 </button>'
                //    + '</div> </div> </div> </div> <div class="modal-backdrop fade in"></div></div>';
                var html = "";
                for (var i = 0; i < keywords.length; i++) {
                    html += ' <div> <input type="radio" name="ffff"   value="' + keywords[i] + '" />' + keywords[i] + '</div>';
                }
            }
            //$("#modalShow").html(html);
            //$('#tableTip').modal();
            $("#appendIncrement").html(html);
            $('#incrementModal').modal();
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

//填充增量索引
function fillIndex() {
    var table = $('#appendIncrement input[name="ffff"]:checked ').val();
    $("#increment_col").val(table);
}

//本地表索引
function addCloumIndex(id) {
    var select_column = $("#column").val();
    if (select_column == '') {
        alert("请先选择同步列");
        return;
    }
    if (select_column == "*") {
        checkIndex(id);
    } else {
        //var s = "update_time,create_time,id";
        var strs = [];
        strs = select_column.valueOf().split(",");
        var html = '  <table class="table" border="1" cellspacing="0" cellpadding="0" id="tableIndex"> <thead> <tr> <td><input type="checkbox"/></td> <td>字段名称</td> </tr></thead>';
        for (var i = 0; i < strs.length; i++) {
            html += ' <tr> <td><input type="checkbox"    name="eeee" value="' + strs[i] + '" /></td><td>' + strs[i] + '</td>';
            //'<td><select name="' + i + '"><option value="normal ">normal</option>' +
            //'<option value="unique">unique</option><option value="bitmap">bitmap</option></select></td>';
        }
        html += '</table>';
        //$("#modalShow").html(html);
        //$('#tableTip').modal();
        $("#appendIndex").html(html);
        $('#indexJsonModule').modal();
    }


}

function checkIndex(id) {
    var datasource = $("#dataSource option:selected").val();
    var owner = $("#remoteUserName option:selected").val();
    var table = $("#remoteTable").val();
    if (table == '') {
        alert("请先查询远程数据表");
        return;
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'queryColumn', datasource: datasource, table: table, owner: owner},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var keywords = result.data;
                var strs = [];
                strs = keywords;
                var html = '  <table class="table" border="1" cellspacing="0" cellpadding="0" id="tableIndex"> <thead> <tr> <td><input type="checkbox"/></td> <td>字段名称</td>  </tr></thead>';
                for (var i = 0; i < strs.length; i++) {
                    html += ' <tr> <td><input type="checkbox"    name="eeee" value="' + strs[i] + '" /></td><td>' + strs[i] + '</td>';
                    //'<td><select name="' + i + '"><option value="normal ">normal</option>' +
                    //'<option value="unique">unique</option><option value="bitmap">bitmap</option></select></td>';
                }
                html += '</table>';
                $("#appendIndex").html(html);
                $('#indexJsonModule').modal();
            }

        },
        error: function () {
            alert('form submit error..')
        }
    });
}
function drawTable(i) {
    var column = "";
    var strs = "";
    var html = $('#index_table_body').html();
    $("#tableIndex input:checkbox[name=eeee]:checked").each(function (i) {
        column = $(this).val();
        //var ipts = $(this).parents("tr").find("select");
        //index = ipts.map(function () {
        //    return $(this).val();
        //}).get();
        strs = strs + column + ",";
    });
    strs = strs.substring(0, strs.length - 1);
    var len = $("#index_table_body").find("tr").length;
    for (var i = 0; i < len; i++) {
        var numberStr = $("#index_table_body tr").eq(i).find("td:first").find("input").eq(1).val();
        //numberStr = numberStr.substring(0, numberStr.length - 1);
        if (numberStr == strs) {
            alert('重复的索引');
            return;
        }
    }
    var indexType = $("#indexType").val();
    html += '<tr>'
        + ' <td>'
        + '<input name="dsTaskIndexes[' + len + '].id" type="hidden" value="" >' + strs
        + '<input name="dsTaskIndexes[' + len + '].indexCols" type="hidden" value="' + strs + '"/>'
        + '<input name="dsTaskIndexes[' + len + '].indexType" type="hidden" value="' + indexType + '"/></td>'
        + ' </td>'
        + '<td>' + indexType
        + '<td onclick="del(this)" style="cursor:hand" >删除</td> '
        + '</tr> ';
    $("#index_table_body").html(html);
    //$("#" + id).val(adIds);
    //$("#increment_col").val(table);
    // 找到选中行的input
    //$("#tableIndex input:checkbox:checked").closest("tr").find("select").each(function(i, eleDom){
    //    // 遍历每个被选中的复选框所在行的文本框的值, 看你怎么用了，你要哪个
    //    alert('第 ' + (i+1) + ' 个文本框的值: ' + eleDom.value);
    //});
}
function del(k) {
    var id = $(k).parent().find(":input:first").val();
    if (id == '') {
        $(k).parent().remove();
        reName()
    } else {
        $.ajax({
            dataType: 'json',
            url: 'operate.json',
            data: {action: 'deleteDsTaskIndex', id: id},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    $(k).parent().remove();
                    reName();
                } else {
                    alert(result.msg)
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    }
}
//重命名
function reName() {
    var leng = $("#index_table_body tr").length;
    //console.log("--leng-"+leng);
    for (var i = 0; i < leng; i++) {
        var inputs = $("#index_table_body tr").eq(i).find("td:first").find('input');
        console.log();
        for (var a = 0; a < inputs.length; a++) {
            var name = $(inputs[a]).attr("name");
            //dsTaskIndexes[1].id
            var resultname = 'dsTaskIndexes[' + i + '].' + name.split(".")[1];
            $(inputs[a]).attr("name", resultname);
        }
    }
}
function selectAll(name) {
    $('input[name="' + name + '"]').attr("checked", "checked");

}

function addDistribute() {
    var select_column = $("#column").val();
    if (select_column == '') {
        alert("请先选择同步列");
        return;
    }
    if (select_column == "*") {
        var datasource = $("#dataSource option:selected").val();
        var owner = $("#remoteUserName option:selected").val();
        var table = $("#remoteTable").val();
        if (table == '') {
            alert("请先查询远程数据表");
            return;
        }
        $.ajax({
            dataType: 'json',
            url: 'show.json',
            data: {action: 'queryColumn', datasource: datasource, table: table, owner: owner},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    var keywords = result.data;
                    var html = "";
                    for (var i = 0; i < keywords.length; i++) {
                        html += ' <div> <input type="checkbox" name="ff"   value="' + keywords[i] + '" />' + keywords[i] + '</div>';
                    }
                    $("#appendDistribute").html(html);
                    $('#distributeModule').modal();

                }

            },
            error: function () {
                alert('form submit error..')
            }
        });
    } else {
        var strs = [];
        strs = select_column.toString().split(",");
        var html = "";
        for (var i = 0; i < strs.length; i++) {
            html += ' <div> <input type="checkbox" name="ff"   value="' + strs[i] + '" />' + strs[i] + '</div>';
        }
        $("#appendDistribute").html(html);
        $('#distributeModule').modal();
    }

}


function fillDistribute() {
    var adIds = "";
    $('#distributeModule input:checkbox[name="ff"]:checked').each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    $("#distributedBy").val(adIds);
}

function saveAs() {
    if (!window.confirm('确定保存吗?')) {
        return false;
    }
    var increment_col = $("#increment_col").val();
    var tag;
    var taskName = $("#taskName").val();
    var incrementStatus = $("#incrementStatus").prop("checked") == true ? 0 : 1;
    var fullStatus = $("#fullStatus").prop("checked") == true ? 0 : 1;
    var dsId = $("#dataSource").val();
    var srcTable = $("#remoteTable").val();
    var selectCol = $("#column").val();
    var pk = $("#pk").val();
    var srcUsername = $("#remoteUserName");
    var desUsername = $("#tableSchema").val();
    var desTable = $("#tableInput").val();
    var ip = $("input[name='incrementPeriod']:checked");
    var fp = $("input[name='fullPeriod']:checked");

    if (taskName == "" || taskName == null || taskName == undefined) {
        alert("任务名不能为空!");
        return false;
    }
    if (incrementStatus == 1) {
        removeInData();
    } else {
        if (ip.val() != undefined) {
            if (ip.val() == 4) {
                if ($("#incrementTimeScript").val() == "" || $("#incrementTimeScript").val() == null) {
                    alert("请输入任务调度周期!");
                    return;
                }
            } else if(ip.val() != 5) {
                var iv = $("#incrementVarHour_" + ip.val()).val();
                if ("" == iv || null == iv) {
                    alert("请输入增量重跑时间");
                    return;
                }
            }
        } else {
            alert("请选择增量同步模式！");
            return;
        }

        if ($("#increment_col").val() == null || $("#increment_col").val() == "") {
            alert("请添加增量字段！");
            return;
        }

    }

    if (fullStatus == 1) {
        removeFuData();
    } else {
        if (fp.val() != undefined) {
            if (fp.val() == 4) {
                if ($("#fullTimeScript").val() == "" || $("#fullTimeScript").val() == null) {
                    alert("请输入任务调度周期!");
                    return;
                }
            } else if(fp.val() != 5) {
                var fv = $("#fullVarHour_" + fp.val()).val();
                if ("" == fv || null == fv) {
                    alert("请输入全量重跑时间");
                    return;
                }
            }
        } else {
            alert("请选择全量同步模式！");
            return;
        }
    }

    if (dsId == "" || dsId == null) {
        alert("请选择数据源");
        return false;
    }
    if (selectCol == "" || selectCol == null) {
        alert("请选择同步列");
        return false;
    }
    if (desUsername == "" || desUsername == null) {
        alert("请选择本地schema!");
        return false;
    }
    if (srcUsername == "" || srcUsername == null) {
        alert("请选择远程SCHEMA!");
        return false;
    }
    if (increment_col == '*') {
        alert("增量索引不能为*");
        tag = 0;
    }
    if (tag == 0) {
        return false;
    }
    document.getElementById("nextIncrement").value = document.getElementById("nextIncrement").value.replace("T", " ");
    $.ajax({
        dataType: 'json',
        url: 'operate.json?action=saveAs',
        data: $("#baseInfo").serialize(),
        traditional: true,
        success: function (result) {
            if (result.ok) {
                //window.location = 'query.html';
                //window.history.back();
                location.href = document.referrer;
            }
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

function save() {
    //console.log($("#baseInfo").serialize());
    if (!window.confirm('确定保存吗?')) {
        return false;
    }
    var increment_col = $("#increment_col").val();
    var tag;
    var taskName = $("#taskName").val();
//        var incrementStatus=$("#incrementStatus").val();
//        var fullStatus=$("#fullStatus").val();
    var incrementStatus = $("#incrementStatus").prop("checked") == true ? 0 : 1;
    var fullStatus = $("#fullStatus").prop("checked") == true ? 0 : 1;

    var dsId = $("#dataSource").val();
    var srcTable = $("#remoteTable").val();
    var selectCol = $("#column").val();
    var pk = $("#pk").val();
    var srcUsername = $("#remoteUserName");
    var desUsername = $("#tableSchema").val();
    var desTable = $("#tableInput").val();
    var ip = $("input[name='incrementPeriod']:checked");
    var fp = $("input[name='fullPeriod']:checked");

    if (taskName == "" || taskName == null || taskName == undefined) {
        alert("任务名不能为空!");
        return false;
    }

    /*if (incrementStatus == 1 && fullStatus == 1) {
     alert("请启用增量或全量同步模式");
     return false;
     }*/

    if (incrementStatus == 1) {
        removeInData();
    } else {
        if (ip.val() != undefined) {
            if (ip.val() == 4) {
                if ($("#incrementTimeScript").val() == "" || $("#incrementTimeScript").val() == null) {
                    alert("请输入任务调度周期!");
                    return;
                }
            } else if(ip.val() != 5) {
                var iv = $("#incrementVarHour_" + ip.val()).val();
                if ("" == iv || null == iv) {
                    alert("请输入增量重跑时间");
                    return;
                }
            }
        } else {
            alert("请选择增量同步模式！");
            return;
        }

        if ($("#increment_col").val() == null || $("#increment_col").val() == "") {
            alert("请添加增量字段！");
            return;
        }

    }

    if (fullStatus == 1) {
        removeFuData();
    } else {
        if (fp.val() != undefined) {
            if (fp.val() == 4) {
                if ($("#fullTimeScript").val() == "" || $("#fullTimeScript").val() == null) {
                    alert("请输入任务调度周期!");
                    return;
                }
            } else if(fp.val() != 5) {
                var fv = $("#fullVarHour_" + fp.val()).val();
                if ("" == fv || null == fv) {
                    alert("请输入全量重跑时间");
                    return;
                }
            }
        } else {
            alert("请选择全量同步模式！");
            return;
        }
    }

    if (dsId == "" || dsId == null) {
        alert("请选择数据源");
        return false;
    }

    //用户

    //if (srcTable == "" || srcTable == null) {
    //    alert("请填写远程数据表");
    //    return false;
    //}

    if (selectCol == "" || selectCol == null) {
        alert("请选择同步列");
        return false;
    }
//        if (pk == ""||pk==null ){
//            alert("请添加表主键!");
//            return false;
//        }
    if (desUsername == "" || desUsername == null) {
        alert("请选择本地schema!");
        return false;
    }
    if (srcUsername == "" || srcUsername == null) {
        alert("请选择远程SCHEMA!");
        return false;
    }
    //if (desTable == "" || desTable == null) {
    //    alert("请填写本地数据表!");
    //    return false;
    //}


    if (increment_col == '*') {
        alert("增量索引不能为*");
        tag = 0;
    }
    if (tag == 0) {
        return false;
    }

    document.getElementById("nextIncrement").value = document.getElementById("nextIncrement").value.replace("T", " ");
    //document.getElementById("nextFull").value=document.getElementById("nextFull").value.replace("T"," ");
    $.ajax({
        dataType: 'json',
        url: 'operate.json',
        data: $("#baseInfo").serialize(),
        traditional: true,
        success: function (result) {
            if (result.ok) {
                //window.location = 'query.html';
                //window.history.back();
                location.href = document.referrer;
            }
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

function radioRemoveChecked(the) {
    if (the.checked == false) {
        if (the.name == "incrementStatus") {
            removeInData();
        } else {
            removeFuData();
        }
    }
}

//清空incrementStatus下的数据
function removeInData() {
    $("input:radio[name='incrementPeriod']").each(function () {
        this.checked = false;
    });
    $("input[name='incrementVarHour']").val("");
    $("#incrementVarDay option[value='']").removeAttr("selected");
    $("#incrementVarDay option[value='']").attr("selected", "selected");
    $("#incrementVarDayWeek option[value='']").removeAttr("selected");
    $("#incrementVarDayWeek option[value='']").attr("selected", "selected");
    $("#incrementTimeScript").val("");
}

//清空fullStatus下的数据
function removeFuData() {
    $("input:radio[name='fullPeriod']").each(function () {
        this.checked = false;
    });
    $("input[name='fullVarHour']").val("");
    $("#fullVarDay option[value='']").removeAttr("selected");
    $("#fullVarDay option[value='']").attr("selected", true);
    $("#fullValDayWeek option[value='']").removeAttr("selected");
    $("#fullValDayWeek option[value='']").attr("selected", true);
    $("#fullTimeScript").val("");
}

function clickRadio(the) {
    var v = the.value;
    var name = the.name;
    if (name == "incrementPeriod") {
        var is = $("#incrementStatus");
        //提示开启上级
        /*if (is.prop("checked") == false) {
            alert("请开启增量同步");
            $("input:radio[name='incrementPeriod']").each(function () {
                this.checked = false;
            });
            return;
        }*/
        if (v == 0) {
            $("input[name='incrementVarHour']").val("");
            $("#incrementVarDayWeek option[value='']").removeAttr("selected");
            $("#incrementVarDayWeek option[value='']").attr("selected", "selected");
            $("#incrementTimeScript").val("");
        } else if (v == 1) {
            $("input[name='incrementVarHour']").val("");
            $("#incrementVarDay option[value='']").removeAttr("selected");
            $("#incrementVarDay option[value='']").attr("selected", "selected");
            $("#incrementTimeScript").val("");
        } else {
            $("#incrementVarDay option[value='']").removeAttr("selected");
            $("#incrementVarDay option[value='']").attr("selected", "selected");
            $("#incrementVarDayWeek option[value='']").removeAttr("selected");
            $("#incrementVarDayWeek option[value='']").attr("selected", "selected");
            $("input[name='incrementVarHour']").val("");
            $("#incrementTimeScript").val("");
        }
    } else {
        var is = $("#fullStatus");
        //提示开启上级
        /*if (is.prop("checked") == false) {
            alert("请开启全量同步");
            $("input:radio[name='fullPeriod']").each(function () {
                this.checked = false;
            });
            return;
        }*/
        if (v == 0) {
            $("input[name='fullVarHour']").val("");
            $("#fullValDayWeek option[value='']").removeAttr("selected");
            $("#fullValDayWeek option[value='']").attr("selected", "selected");
            $("#fullTimeScript").val("");
        } else if (v == 1) {
            $("input[name='fullVarHour']").val("");
            $("#fullVarDay option[value='']").removeAttr("selected");
            $("#fullVarDay option[value='']").attr("selected", "selected");
            $("#fullTimeScript").val("");
        } else {
            $("#fullValDayWeek option[value='']").removeAttr("selected");
            $("#fullValDayWeek option[value='']").attr("selected", "selected");
            $("#fullVarDay option[value='']").removeAttr("selected");
            $("#fullVarDay option[value='']").attr("selected", "selected");
            $("input[name='fullVarHour']").val("");
            $("#fullTimeScript").val("");
        }
    }
}

function selectClick(the, value) {//value为0代表增量，为1代表全量
    if (value == 0) {
        var ip = $("input[name='incrementPeriod']:checked");
        if ((the.name == "incrementVarDay" && ip.val() == 0) || (the.name == "incrementVarDayWeek" && ip.val() == 1)) {
        } else {
            alert("请先选择上级");

        }
    } else {
        var ip = $("input[name='fullPeriod']:checked");
        if ((the.name == "fullVarDay" && ip.val() == 0) || (the.name == "fullValDayWeek" && ip.val() == 1)) {
        } else {
            alert("请先选择上级");

        }
    }

}
