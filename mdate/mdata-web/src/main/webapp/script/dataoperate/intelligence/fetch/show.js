/**
 * Created by Sheng on 2017/7/6.
 */

//ajax :   http://www.111cn.net/wy/jquery/115318.htm
function saveFetchInfo(e){
    if($('#instanceName').val() == ''){
        alert("请输入名称！");
        $(this).bind('show.bs.tab', function(e) {
            e.preventDefault();  //取消默认事件
         });
        return;
    }else if($('input[name=outputIsReset]:checked').val() == undefined){
        alert("请选择输出结果去重！");
        $(this).on('show.bs.tab', function(e) {
            e.preventDefault();  //取消默认事件
        });
        return;
    }
    $(this).unbind('show.bs.tab');
    $('.nav').find('li').removeClass('active');
    $('.nav').find('li').eq(e).addClass('active');
}
function saveFetTerm(e){ //设置取数条件下一步按钮
    for(var i = 0; i < $('#instanceConditionTable tbody tr').length; i++){
        if($('#instanceConditionTable tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == undefined && $('#instanceConditionTable tbody tr').eq(i).attr('isFixed') == 0){
            alert("请选择操作类型！");
            $(this).on('show.bs.tab', function(e) {
                e.preventDefault();  //取消默认事件
            });
            return;
        }else if($('#instanceConditionTable tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
            if($('#instanceConditionTable tbody tr').eq(i).find('input[type=text]').val() == ""){
                alert("请输入值！");
                $(this).on('show.bs.tab', function(e) {
                    e.preventDefault();  //取消默认事件
                });
                return;
            }
        }else if($('#instanceConditionTable tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
            var select='';
            $('#instanceConditionTable tbody tr').eq(i).find('select[name=columnSelection] :selected').each(function(){
                select += $(this).text();
            });
            if(select == ""){
                alert("请选择列！");
                $(this).on('show.bs.tab', function(e) {
                    e.preventDefault();  //取消默认事件
                });
                return;
            }
        }
    }

    $(this).unbind('show.bs.tab');
    $('.nav').find('li').removeClass('active');
    $('.nav').find('li').eq(e).addClass('active')
}
function saveFetResult(e){  //选择取数结果字段下一步按钮
    if($('#choiceFieldList tbody tr').length == 0){
        alert("请选择字段！");
        $(this).on('show.bs.tab', function(e) {
            e.preventDefault();  //取消默认事件
        });
        return;
    }

    for(var i = 0; i < $('#choiceFieldList tbody tr').length; i ++){
        if($('#choiceFieldList tbody tr').eq(i).find('td').eq(1).find('input[type=text]').val() == ''){
            alert("请填写字段名称！");
            $(this).on('show.bs.tab', function(e) {
                e.preventDefault();  //取消默认事件
            });
            return;
        }else if($('#choiceFieldList tbody tr').eq(i).find('td').eq(1).find('input[type=text]').val() != undefined
            && $('#choiceFieldList tbody tr').eq(i).find('td').eq(1).find('input[type=text]').val().trim().indexOf(" ") >=0){
            alert("字段名称不能有空格！");
            $(this).on('show.bs.tab', function(e) {
                e.preventDefault();  //取消默认事件
            });
            return;
        }
        if($('#choiceFieldList tbody tr').eq(i).find('td').eq(1).find('input[type=text]').val() != undefined
            && $('#choiceFieldList tbody tr').eq(i).find('td').eq(2).find('input[type=text]').val() == ''){
            alert("请填写表达式！");
            $(this).on('show.bs.tab', function(e) {
                e.preventDefault();  //取消默认事件
            });
            return;
        }else if($('#choiceFieldList tbody tr').eq(i).find('td').eq(1).find('input[type=text]').val() != undefined
            && $('#choiceFieldList tbody tr').eq(i).find('td').eq(2).find('input[type=text]').val() != undefined
            && $('#choiceFieldList tbody tr').eq(i).find('td').eq(2).find('input[type=text]').val().trim().indexOf(" ") >=0){
            alert("表达式不能有空格！");
            $(this).on('show.bs.tab', function(e) {
                e.preventDefault();  //取消默认事件
            });
            return;
        }
    }

    $(this).unbind('show.bs.tab');
    $('.nav').find('li').removeClass('active');
    $('.nav').find('li').eq(e).addClass('active')
}
function fetPrev(e){  //上一步按钮
    $(this).unbind('show.bs.tab');
    $('.nav').find('li').removeClass('active');
    $('.nav').find('li').eq(e).addClass('active')
}
function doUpload(){
    var flag = 0;
    for(var i = 0; i < $('#instanceConditionTable tbody tr').length; i++){
        if($('#instanceConditionTable tbody tr').eq(i).find('td').eq(3).find('input:radio:checked').val() == 2){
            flag = 1;
        }
    }
    if(flag == 0){
        alert("请选择导入文件！");
        return;
    }
    var formData = new FormData($("#uploadForm")[0]);
    $.ajax({
        url: 'query.json?action=uploadFile' ,
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (json) {
            if(json.ok){
                alert(json.msg);
                $('select[name=columnSelection]').empty();
                var obj = {
                    id:$('#inputFileId').val(),
                    instanceId:$('#instanceId').val(),
                    fileName:json.data.tbGetdataInstanceInputfile.fileName,
                    tempSchemaName:json.data.tbGetdataInstanceInputfile.tempSchemaName,
                    tempTableName:json.data.tbGetdataInstanceInputfile.tempTableName,
                    cols:json.data.tbGetdataInstanceInputfile.cols
                };
                $('#tbGetdataInstanceInputfile').val(JSON.stringify(obj));
                for(var i = 0; i < json.data.filed.length; i++){
                    $('select[name=columnSelection]').append('<option value="'+ json.data.filed[i] +'">'+json.data.filed[i]+'</option>');
                }
            }else alert(json.msg)
        },
        error: function (data) {
            alert("请求失败！");
        }
    });
}
function manualInput(e){
    e.parents("tr").find('td').eq(5).find('input[type=text]').removeAttr('readonly')
}
function setUploadFile(e){
    e.parents("tr").find('td').eq(5).find('input[type=text]').attr('readonly','readonly');

}
function addInstanceCondition(){
    var html='<select name="fieldNameSelection" class="form-control associationMode">';
    var randomName = randomString(5);
    var columnSelection ='';
    for(var i = 0; i < $('#instanceConditionTable tbody tr').length; i++){
        if($('#instanceConditionTable tbody tr').eq(i).attr('isFixed') == 0){
            columnSelection = $('#instanceConditionTable tbody tr').eq(i).find('td').eq(4).find('select[name=columnSelection]').clone();
            break;
        }
    }
    if(columnSelection == ''){
        columnSelection = '<select name="columnSelection" multiple class="form-control"></select>';
    }


    pub.postJSON('query.json',{
        action:'getFieldByTemplate',
        templateId:$('#templateId').val(),
    },function(json){
        if(json.ok){
            for(var i = 0; i < json.data.length; i++){
                var name = '';
                if(json.data[i].field_chn_name != undefined && json.data[i].field_chn_name != ""){
                    name = json.data[i].field_chn_name;
                }else if(json.data[i].field_name != undefined && json.data[i].field_name != ""){
                    name = json.data[i].field_name;
                }
                html = html + '<option value="'+ json.data[i].subject_col_id +'">'+ name +'</option>';
            }
            html = html + '</select>';
            $('#instanceConditionTable tbody').append('<tr isFixed="0">' +
                '<td><input type="checkbox"/></td>' +
                '<td>'+ html +'</td>' +
                '<td>' +
                '<select name="operator" class="form-control">' +
                '<option value="=">=</option>' +
                '<option value=">">&gt;</option>' +
                '<option value="<">&lt;</option>' +
                '<option value=">=">&ge;</option>' +
                '<option value="<=">&le;</option>' +
                '<option value="<>">&lt;&gt;</option>' +
                '<option value="in">in</option>' +
                '<option value="not in">not in</option>' +
                '<option value="%like">%like</option>' +
                '<option value="like%">like%</option>' +
                '<option value="%like%">%like%</option>' +
                '</select>' +
                '</td>' +
                '<td>' +
                '<div class="radio">' +
                '<label><input type="radio" value="1" name="'+ randomName +'" onclick="manualInput($(this))"/> 手动输入</label>' +
                '<label><input type="radio" value="2" name="'+ randomName +'" onclick="setUploadFile($(this))"/> 导入文件</label>' +
                '</div>' +
                '</td>' +
                '<td></td>' +
                //'<td><select name="columnSelection" multiple class="form-control"></select></td>' +
                '<td><input type="text" class="form-control" readonly="readonly"/></td>' +
                '</tr>');
            $('#instanceConditionTable tbody tr:last').find('td').eq(4).append(columnSelection);
        }
    });
}
function delInstanceCondition(){
    for(var i = $('#instanceConditionTable tbody tr').length; i >= 0 ; i-- ){
        if($('#instanceConditionTable tbody tr').eq(i).find('td').eq(0).find('input[type=checkbox]').is(':checked') && ($('#instanceConditionTable tbody tr').eq(i).attr('isFixed') == 0)){
            $('#instanceConditionTable tbody tr').eq(i).remove();
        }
    }
}

function templateFieldsSelectChange(){
    $('#optionalFieldList tbody').empty();
    var queryName;
    if($('#templateFieldsSelect option:selected').text() == "全部") {
        pub.postJSON('query.json',{
            action:'getFieldByTemplate',
            templateId:$('#templateId').val(),
        },function(json){
            if(json.ok){
                for(var i = 0; i<json.data.length; i++){
                    var name = '';
                    if(json.data[i].field_chn_name != undefined && json.data[i].field_chn_name != ""){
                        name = json.data[i].field_chn_name;
                    }else if(json.data[i].field_name != undefined && json.data[i].field_name != ""){
                        name = json.data[i].field_name;
                    }
                    $('#optionalFieldList tbody').append('<tr>' +
                        '<td><input type="checkbox" value="'+ json.data[i].subject_col_id +'"/></td>' +
                        '<td>'+ name +'</td>' +
                        '</tr>')
                }
            }
        });
    }else{
        queryName = $('#templateFieldsSelect option:selected').text();
        pub.postJSON('query.json',{
            action:'getFieldByTemplateAndName',
            templateId:$('#templateId').val(),
            fieldName:queryName
        },function(json){
            if(json.ok){
                for(var i = 0; i<json.data.length; i++){
                    var name = '';
                    if(json.data[i].field_chn_name != undefined && json.data[i].field_chn_name != ""){
                        name = json.data[i].field_chn_name;
                    }else if(json.data[i].field_name != undefined && json.data[i].field_name != ""){
                        name = json.data[i].field_name;
                    }
                    $('#optionalFieldList tbody').append('<tr>' +
                        '<td><input type="checkbox" value="'+ json.data[i].subject_col_id +'"/></td>' +
                        '<td>'+ name +'</td>' +
                        '</tr>')
                }
            }
        });
    }
}
function queryTemplateFields(){
    $('#optionalFieldList tbody').empty();
    pub.postJSON('query.json',{
        action:'getFieldByTemplateAndName',
        templateId:$('#templateId').val(),
        fieldName:$('#queryName').val()
    },function(json){
        if(json.ok){
            for(var i = 0; json.data.length; i++){
                var name = '';
                if(json.data[i].field_chn_name != undefined && json.data[i].field_chn_name != ""){
                    name = json.data[i].field_chn_name;
                }else if(json.data[i].field_name != undefined && json.data[i].field_name != ""){
                    name = json.data[i].field_name;
                }
                $('#optionalFieldList tbody').append('<tr>' +
                    '<td><input type="checkbox" value="'+ json.data[i].subject_col_id +'"/></td>' +
                    '<td>'+ name +'</td>' +
                    '</tr>')
            }
        }
    });
}
function resetQuery(){
    $('#queryName').val('');
}
function allCopyLeft(){
    for(var i = 0 ; i < $('#optionalFieldList tbody tr').length; i++){
        var flag=1;
        for(var j = 0 ; j < $('#choiceFieldList tbody tr').length; j++){
            if($('#optionalFieldList tbody tr').eq(i).find('td').eq(0).find('input[type=checkbox]').val()
                == $('#choiceFieldList tbody tr').eq(j).find('td').eq(0).find('input[type=checkbox]').val()){
                flag=0;
            }
        }
        if(flag == 1){
            var html="";
            html += '<tr>' +
                '<td><input type="checkbox" value="'+ $('#optionalFieldList tbody tr').eq(i).find('td').eq(0).find('input[type=checkbox]').val() +'"/></td>' +
                '<td>'+ $('#optionalFieldList tbody tr').eq(i).find('td').eq(1).html() +'</td>' +
                '<td><input type="text" class="form-control"/></td>' +
                '</tr>';
            $("#choiceFieldList tbody").append(html);
        }
    }
}
function copyLeft(){
    var flag=1;
    for(var j =1 ; j < $('#optionalFieldList tr').length ; j++){
        flag=1;
        if($('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').is(':checked')){
            for(var i = 1 ; i < $('#choiceFieldList tr').length; i++){
                if($('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').val() == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').val()){
                    flag=0;
                }
            }
            if(flag == 1){
                var html="";
                html += '<tr>' +
                    '<td><input type="checkbox" value="'+ $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').val() +'"/></td>' +
                    '<td>'+ $('#optionalFieldList tr').eq(j).find('td').eq(1).html() +'</td>' +
                    '<td><input type="text" class="form-control"/></td>' +
                    '</tr>';
                $("#choiceFieldList tbody").append(html);
            }
        }
    }
}
function allDeleteRight(){
    for(var i = $('#choiceFieldList tbody tr').length ; i > 0 ; i--){
        if($('#choiceFieldList tbody tr').eq(i-1).find('td').eq(0).find('input[type=checkbox]').attr('value') != undefined && $('#choiceFieldList tbody tr').eq(i-1).find('td').eq(0).find('input[type=checkbox]').attr('value') !="") {
            $('#choiceFieldList tbody tr').eq(i-1).remove();
        }
    }
}
function deleteRight(){
    for(var i = $('#choiceFieldList tbody tr').length ; i > 0 ; i--){
        if($('#choiceFieldList tbody tr').eq(i-1).find('td').eq(0).find('input[type=checkbox]').is(':checked') && $('#choiceFieldList tbody tr').eq(i-1).find('td').eq(0).find('input[type=checkbox]').attr('value') != undefined) {
                $('#choiceFieldList tbody tr').eq(i-1).remove();
        }
    }
}
function addChoiceFieldList(){
    var html = '<tr>' +
        '<td><input type="checkbox"/></td>' +
        '<td><input type="text" class="form-control"/></td>' +
        '<td><input type="text" class="form-control"/></td>' +
        '</tr>';
    // $("#choiceFieldList tbody").append(html);
    $("#choiceFieldList tbody").prepend(html);
}
function delChoiceFieldList(){
    for(var i = $("#choiceFieldList tbody tr").length; i >= 0; i--){
        if($('#choiceFieldList tbody tr').eq(i).find('td').eq(0).find('input[type=checkbox]').is(':checked')){
            $('#choiceFieldList tbody tr').eq(i).remove();
        }
    }
}

//保存
function saveData(){
    var tar_schema;
    var tar_table_name;
    var tar_ftp_conf_id;
    var tar_file_name;
    var run_schedule_time;
    if($('input[name=outputType]:checked').val() == 2){
        if($('#fileName').val() == ""){
            alert("请输入文件名！");
            return;
        }
        tar_ftp_conf_id = $('select[name=ftpNameSelect] option:selected').val();
        tar_file_name = $('#fileName').val();
    }else if($('input[name=outputType]:checked').val() == 1){
        tar_schema = $('select[name=schemaNameSelect] option:selected').val();
        tar_table_name = $('#tableName').val();
    }else if($('input[name=outputType]:checked').val() == undefined){
        alert("请选择类型！");
        return;
    }
    if($('input[name=runType]:checked').val() == undefined){
        alert("请选择运行类型！");
        return;
    }
    else if($('input[name=runType]:checked').val() == 2){
        if($('#runScheduleTime').val() == ""){
            alert("请输入时间！");
            return;
        }
        run_schedule_time = $('#runScheduleTime').val();
    }
    var conditionList = [];
    for(var i=0;i<$('#instanceConditionTable tbody tr').length;i++){
        var inputFileCol='';
        var inputType = $('#instanceConditionTable tbody tr').eq(i).find('input[type=radio]:checked').val();
        $('#instanceConditionTable tbody tr').eq(i).find('select[name=columnSelection] :selected').each(function(){
            if(inputType == 2){
                inputFileCol +=$(this).text() + '|';
            }
        });
        if($('#instanceConditionTable tbody tr').eq(i).find('input[type=radio]:checked').val() == undefined){
            inputType = 1;
        }
        var obj = {
            id:$('#instanceConditionTable tbody tr').eq(i).attr('id'),
            instanceId:$('#instanceId').val(),
            subjectColId:$('#instanceConditionTable tbody tr').eq(i).find('select[name=fieldNameSelection] :selected').val(),
            operationChar:$('#instanceConditionTable tbody tr').eq(i).find('select[name=operator] :selected').val(),
            fieldValue:$('#instanceConditionTable tbody tr').eq(i).find('input[type=text]').val(),
            logicRel:"and",
            inputType:inputType,
            inputFileCol:inputFileCol,
            isFixed:$('#instanceConditionTable tbody tr').eq(i).attr('isFixed')
        };
        conditionList.push(obj);
    }
    var colsList = [];
    for(var i=0;i<$('#choiceFieldList tbody tr').length;i++){
        var fieldType='';
        var subjectColId='';
        var fieldChnName='';
        if($('#choiceFieldList tbody tr').eq(i).find('input[type=checkbox]').val() != "on" && $('#choiceFieldList tbody tr').eq(i).find('input[type=checkbox]').val() != ""){
            fieldType = 1;
            subjectColId=$('#choiceFieldList tbody tr').eq(i).find('input[type=checkbox]').val();
            fieldChnName = $('#choiceFieldList tbody tr').eq(i).find('td').eq(1).text();
        }else {
            fieldType = 2;
            fieldChnName = $('#choiceFieldList tbody tr').eq(i).find('td').eq(1).find('input[type=text]').val();
        }
        var obj = {
            id:$('#choiceFieldList tbody tr').eq(i).attr('id'),
            instanceId:$('#instanceId').val(),
            fieldType:fieldType,
            subjectColId:subjectColId,
            expressionCol:$('#choiceFieldList tbody tr').eq(i).find('td').eq(2).find('input[type=text]').val(),
            fieldChnName:fieldChnName
        };
        colsList.push(obj);
    }

    var flag = 1;
    $.ajax({
        url:'operate.json?action=isExistsName',
        type:'POST',
        async:false,
        data:{
            id:$('#instanceId').val(),
            templateId:$('#templateId').val(),
            instanceName:$('#instanceName').val(),
            isDistinceData:$('input[name=outputIsReset]:checked').val(),
            remark:$('#remark').val(),
            outputType:$('input[name=outputType]:checked').val(),
            tarSchema:tar_schema,
            tarTableName:tar_table_name,
            tarFtpConfId:tar_ftp_conf_id,
            tarFileName:tar_file_name,
            runType:$('input[name=runType]:checked').val(),
            runScheduleTime:run_schedule_time,
            createTime:$('#createTime').val()
        },
        dataType:"json",
        success:function(json){
            if(json.ok){
            }else{
                alert(json.msg);
                flag = 0;
            }
        },error: function (json) {
            alert("出错了！");
            flag = 0;
        }
    });
    if(flag == 0){
        return;
    }

    $.ajax({
        url: 'operate.json?action=saveInstance' ,
        type: 'POST',
        data: {
            id:$('#instanceId').val(),
            templateId:$('#templateId').val(),
            instanceName:$('#instanceName').val(),
            isDistinceData:$('input[name=outputIsReset]:checked').val(),
            remark:$('#remark').val(),
            outputType:$('input[name=outputType]:checked').val(),
            tarSchema:tar_schema,
            tarTableName:tar_table_name,
            tarFtpConfId:tar_ftp_conf_id,
            tarFileName:tar_file_name,
            runType:$('input[name=runType]:checked').val(),
            runScheduleTime:run_schedule_time,
            createTime:$('#createTime').val(),

            conditionListJson:JSON.stringify(conditionList),
            colsJson:JSON.stringify(colsList),
            inputFileJson:$('#tbGetdataInstanceInputfile').val()
        },
        dataType: "json",
        success: function (json) {
            if(json.ok){
                $('#instanceId').val(json.data);
            }
        },
        error: function (data) {
        }
    });

    alert("保存中，请等待！");
    window.location.href='query.html?isBack=1';
}

//返回上一层
function backForData(){
    window.location.href='query.html?isBack=1';
}


/*随机生成input=radio 的name*/
function randomString(len){
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz';
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}
//去除字符串两头的空格
function Trim(str)
{
    return str.replace(/(^\s*)|(\s*$)/g, "");
}