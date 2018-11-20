/**
 * Created by sheng on 2017/6/14.
 */
var choiceId;
var cloneChoiceFieldList;
$(function(){
    if(fieldsJson.length > 0){
        $('.clone_filtration_tt').eq(0).find('td').eq(1).find('select[name=selectField]').empty();
        $('.clone_filtration_tr').eq(1).find('td').eq(1).find('select[name=selectField]').empty();
        for(var i=0; i<fieldsJson.length; i++){
            var name = '';
            if(fieldsJson[i].fieldChnName != undefined && fieldsJson[i].fieldChnName != ""){
                name = fieldsJson[i].fieldChnName;
            }else if(fieldsJson[i].fieldName != undefined){
                name = fieldsJson[i].fieldName;
            }
            $('.clone_filtration_tt').eq(0).find('td').eq(1).find('select[name=selectField]').append('<option value='+ fieldsJson[i].id +'>' + name + '</option>');
            $('.clone_filtration_tr').eq(1).find('td').eq(1).find('select[name=selectField]').append('<option value='+ fieldsJson[i].id +'>' + name + '</option>');
        }
    }

    if(jsons.length > 0){
        $('#tbody').find('.initTr').remove();
        for(var i = 0; i< jsons.length ; i++){
            var clone_filtration_tt = $('.clone_filtration_tt').eq(0).clone(true).show(0);
            clone_filtration_tt.find("td").eq(0).find('input[type="radio"]').attr("name",randomString(4));
            clone_filtration_tt.find("td").eq(4).find('input[type="radio"]').attr("name",randomString(4));

            //Sheng new
            clone_filtration_tt.find("td").eq(0).find("input[type='radio'][value='" + jsons[i].isFixed + "']").prop("checked","checked");
            //clone_filtration_tt.find("td").eq(1).find('select[name=selectField]').prepend("<option value="+jsons[i].subjectColId+">"+jsons[i].subjectColName+"</option>");
            clone_filtration_tt.find("td").eq(1).find('select[name=selectField]').val(jsons[i].subjectColId);
            clone_filtration_tt.find("td").eq(2).find('select[name=operator]').val(jsons[i].operationChar);
            clone_filtration_tt.find("td").eq(3).find('input[name=conditionValue]').val(jsons[i].conditionValue);
            //clone_filtration_tt.find("td").eq(4).find("input[type='radio'][value='" + jsons[i].logicChar + "']").prop("checked","checked");
            $("input[name=typeIsChoice][value='" + jsons[i].logicChar + "']").prop("checked","checked");
            clone_filtration_tt.find("td").eq(4).find('.filtration_del').attr('filtartionId',jsons[i].id);
            $('#tbody').append(clone_filtration_tt);

        }
    }

    if(choiceFieldList.length > 0){
        $("#choiceFieldList tbody").html("");
        for(var i = 0;i < choiceFieldList.length; i++){
            var field_name = '';
            var field_chn_name = '';
            if(choiceFieldList[i].field_name != field_name){
                field_name = choiceFieldList[i].field_name;
            }
            if(choiceFieldList[i].field_chn_name != undefined){
                field_chn_name = choiceFieldList[i].field_chn_name;
            }
            var html="";
            html += '<tr>' +
                '<td><input type="checkbox" id="'+ choiceFieldList[i].tid +'" name="rightChoiceField" aria-label="..." value="'+ choiceFieldList[i].subject_col_id +'"' +
                'fieldId="'+ choiceFieldList[i].id +'"  ></td>' +
                '<td>'+ field_name +'</td>' +
                '<td>'+ field_chn_name +'</td>' +
                '<td>' +
                '<a onclick="click_up($(this))"><span class="glyphicon glyphicon-chevron-up click_up" aria-hidden="true"></span></a>' +
                '<a onclick="click_down($(this))"><span class="glyphicon glyphicon-chevron-down click_down" aria-hidden="true"></span></a>' +
                '</td>' +
                '</tr>';
            $("#choiceFieldList tbody").append(html);
        }
    }

    if($('input[name=typeIsChoice]:checked').val() == undefined){
        $("input[name=typeIsChoice][value='and']").prop("checked","checked");
    }
});
function drawTree(datas){
    var zNodes = datas;
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
                    choiceId=treeNode.id;
                    getSubjectInfo(treeNode.id);
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#subjectDirTree"), setting,zNodes);
}
function getSubjectClass(){
    $('#querySubjectName').val('');
    $('#allSubject tbody').html("");
    pub.postJSON('operate.json',{action:'getSubjectClass'},function(json){
        drawTree(json.data)
    });
}
//获取主题
function getSubjectInfo(dirId){
    if(dirId == undefined){
        dirId=choiceId;
        if(dirId == undefined)
            alert("请选择主题目录！");
    }
    pub.postJSON('operate.json',{
        action:'getSubjectInfo',
        classId:dirId,
        name:$('#querySubjectName').val(),
        //status:$('#queryStatus').find('option:selected').val()，
        status:1
    },function(json){
        //操作table
        var html="";
        $('#allSubject tbody').html("");
        for(var i=0;i<json.data.length;i++){
            if(json.data[i].remark == null){
                json.data[i].remark='';
            }
            html += '<tr>' +
                '<td><input type="radio" name="choiceSubject" value="' + json.data[i].subjectId + '" subjectName="' + json.data[i].subjectName + '"></td>' +
                '<td>' + json.data[i].subjectName + '</td>' +
                '<td>' + json.data[i].remark + '</td>' +
                '</tr>';
        }
        $('#allSubject tbody').html(html);
    });
}
function resetQuery() {
    $("#querySubjectName").val('');
}
function settingSubject(){
    if($('input[name=choiceSubject]:checked').val() == undefined){
        alert("请选择主题！");
        $('#settingSubjectBtn').removeAttr('data-dismiss');

    }else{
        $('#subjectText').attr('subjectId',$('input[name=choiceSubject]:checked').val());
        $('#subjectText').val($('input[name=choiceSubject]:checked').attr('subjectName'));
        $('#optionalFieldList tbody').html("");
        $('#choiceFieldList tbody').html("");


        for(var i = $('#filtrationTable tbody tr').length; i > 0 ; i--){
            $('#filtrationTable tbody tr').eq(i).remove();
        }

        $('.clone_filtration_tt').eq(0).find('td').eq(1).find('select[name=selectField]').empty();
        pub.postJSON('operate.json',{
            action:'getOutPutField',
            subjectId:$('#subjectText').attr('subjectId')
        },function(json){
            if (json.ok) {
                for(var i=0; i<json.data.length; i++){
                    var name = '';
                    if(json.data[i].fieldChnName != undefined && json.data[i].fieldChnName != ""){
                        name = json.data[i].fieldChnName;
                    }else if(json.data[i].fieldName != undefined){
                        name = json.data[i].fieldName;
                    }
                    $('.clone_filtration_tt').eq(0).find('td').eq(1).find('select[name=selectField]').append('<option value='+ json.data[i].srcId +'>' + name + '</option>');
                }

                var first_filtration = $('.clone_filtration_tt').eq(0).clone(true).show(0);
                first_filtration.find("td").eq(0).find('input[type="radio"]').attr("name",randomString(4));
                first_filtration.find("td").eq(4).find('input[type="radio"]').attr("name",randomString(4));
                $('#filtrationTable tbody').append(first_filtration);
            }
        });

        $('#settingSubjectBtn').attr('data-dismiss','modal')
    }
}
function outPutFieldModal(){
    if($('#subjectText').val() == "" || $('#subjectText').val() == undefined){
        alert("请选择主题！");
        $('#outPutFieldModalBtn').removeAttr("data-toggle");
        return;
    }else $('#outPutFieldModalBtn').attr("data-toggle","modal");

    pub.postJSON('operate.json',{
        action:'getAllFieldBySubject',
        subjectId:$('#subjectText').attr('subjectId')
    },function(json){
        if (json.ok) {
            $("#optionalFieldList tbody").html("");
            for(var i=0;i<json.data.length;i++){
                if(json.data[i].fieldName == undefined) json.data[i].fieldName='';
                if(json.data[i].fieldChnName == undefined) json.data[i].fieldChnName='';
                var html="";
                html += '<tr>' +
                    '<td><input type="checkbox" name="leftChoiceField" value="' + json.data[i].id + '" fieldId="'+ json.data[i].fieldId +'" aria-label="..."></td>' +
                    '<td>'+json.data[i].fieldName+'</td>' +
                    '<td>'+json.data[i].fieldChnName+'</td>' +
                    '</tr>';
                $("#optionalFieldList tbody").append(html);
            }
        }
    });

    cloneChoiceFieldList = $("#choiceFieldList tbody").clone();

    //pub.postJSON('operate.json',{
    //    action:'getFieldBySubject',
    //    templateId:$('#templateId').val()
    //},function(json){
    //    if (json.ok) {
    //        $("#choiceFieldList tbody").html("");
    //        for(var i=0;i<json.data.length;i++){
    //            var field_name = '';
    //            var field_chn_name = '';
    //            if(json.data[i].field_name != field_name){
    //                field_name = json.data[i].field_name;
    //            }
    //            if(json.data[i].field_chn_name != undefined){
    //                field_chn_name = json.data[i].field_chn_name;
    //            }
    //            var html="";
    //            html += '<tr>' +
    //                '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." value="'+ json.data[i].subject_col_id +'"' +
    //                'fieldId="'+ json.data[i].id +'"  ></td>' +
    //                '<td>'+ field_name +'</td>' +
    //                '<td>'+ field_chn_name +'</td>' +
    //                '<td>' +
    //                '<a onclick="click_up($(this))"><span class="glyphicon glyphicon-chevron-up click_up" aria-hidden="true"></span></a>' +
    //                '<a onclick="click_down($(this))"><span class="glyphicon glyphicon-chevron-down click_down" aria-hidden="true"></span></a>' +
    //                '</td>' +
    //                '</tr>';
    //            $("#choiceFieldList tbody").append(html);
    //        }
    //    }
    //});
}
function recoveryChoiceFieldList(){
    $("#choiceFieldList").find('tbody').remove();
    $("#choiceFieldList").append(cloneChoiceFieldList);
}
function allCopyLeft(){
    // $("#choiceFieldList tbody").html("");
    // for(var i = 1 ; i < $('#optionalFieldList tr').length; i++){
    //     //$('#optionalFieldList tr').eq(i).find('td').eq(3).html();
    //     var html="";
    //     html += '<tr>' +
    //         '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." value="'+ $('#optionalFieldList tr').eq(i).find('td').eq(0).find('input[name=leftChoiceField]').val() +'"></td>' +
    //         '<td>'+$('#optionalFieldList tr').eq(i).find('td').eq(1).html()+'</td>' +
    //         '<td>'+$('#optionalFieldList tr').eq(i).find('td').eq(2).html()+'</td>' +
    //         '<td>' +
    //         '<a onclick="click_up($(this))"><span class="glyphicon glyphicon-chevron-up click_up" aria-hidden="true"></span></a>' +
    //         '<a onclick="click_down($(this))"><span class="glyphicon glyphicon-chevron-down click_down" aria-hidden="true"></span></a>' +
    //         '</td>' +
    //         '</tr>';
    //     $("#choiceFieldList tbody").append(html);
    // }

    var flag=1;
    for(var j =1 ; j < $('#optionalFieldList tr').length ; j++){
        flag=1;
        for(var i = 1 ; i < $('#choiceFieldList tr').length; i++){
            // if($('#optionalFieldList tr').eq(j).find('td').eq(1).html() == $('#choiceFieldList tr').eq(i).find('td').eq(1).html()){
            //     flag=0;
            // }
            if($('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('fieldId') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('fieldId')
                && $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('value') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('value')){
                flag=0;
            }
        }
        if(flag == 1){
            var html="";
            html += '<tr>' +
                '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." fieldId="'+ $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('fieldId') +'" ' +
                'value="'+ $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').val() +'"></td>' +
                '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(1).html()+'</td>' +
                '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(2).html()+'</td>' +
                '<td>' +
                '<a onclick="click_up($(this))"><span class="glyphicon glyphicon-chevron-up click_up" aria-hidden="true"></span></a>' +
                '<a onclick="click_down($(this))"><span class="glyphicon glyphicon-chevron-down click_down"  aria-hidden="true"></span></a>' +
                '</td>' +
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
                // if($('#optionalFieldList tr').eq(j).find('td').eq(1).html() == $('#choiceFieldList tr').eq(i).find('td').eq(1).html()){
                //     flag=0;
                // }
                if($('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('fieldId') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('fieldId')
                    && $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('value') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('value')){
                    flag=0;
                }
            }
            if(flag == 1){
                var html="";
                html += '<tr>' +
                    '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." fieldId="'+ $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('fieldId') +'" ' +
                    'value="'+ $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').val() +'"></td>' +
                    '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(1).html()+'</td>' +
                    '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(2).html()+'</td>' +
                    '<td>' +
                    '<a onclick="click_up($(this))"><span class="glyphicon glyphicon-chevron-up click_up" aria-hidden="true"></span></a>' +
                    '<a onclick="click_down($(this))"><span class="glyphicon glyphicon-chevron-down click_down"  aria-hidden="true"></span></a>' +
                    '</td>' +
                    '</tr>';
                $("#choiceFieldList tbody").append(html);
            }
        }
    }
}
function allDeleteRight(){
    $("#choiceFieldList tbody").html("");
}
function deleteRight(){
    for(var j = $('#choiceFieldList tr').length ; j >0 ; j--){
        if($('#choiceFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').is(':checked')) {
            $('#choiceFieldList tr').eq(j).remove();
        }
    }
}
function click_up(e){
    var tr_prev = e.parents("tr").prev('tr');
    e.parents("tr").insertBefore(tr_prev)
}
function click_down(e){
    //console.log(e)
    var tr_next = e.parents("tr").next('tr');
    e.parents("tr").insertAfter(tr_next)
}
function saveSelectedField(){
    $('#saveSelectedFieldBtn').removeAttr('data-dismiss');
    if($('#choiceFieldList tbody tr').length == 0){
        alert("请选择输出字段！");
        return;
    }

    $('#saveSelectedFieldBtn').attr('data-dismiss','modal');
}
function addField(a){
    a.empty();
    pub.postJSON('operate.json',{
        action:'getOutPutField',
        subjectId:$('#subjectText').attr('subjectId')
    },function(json){
        if (json.ok) {
            for(var i=0; i<json.data.length; i++){
                var name = '';
                if(json.data[i].fieldChnName != undefined){
                    name = json.data[i].fieldChnName;
                }else if(json.data[i].fieldName != undefined){
                    name = json.data[i].fieldName;
                }
                a.append('<option value='+ json.data[i].srcId +'>' + name + '</option>');
            }
        }
    });
}
function delFiltration(a){
    //pub.postJSON('operate.json',{
    //    action:'delCondition',
    //    id:a.attr('filtartionId')
    //},function(json){
    //    if (json.ok) {
    //        alert("删除成功！");
    //    }
    //});
}

function opRadio(a){
    //if($('#templateId').val() == ""){
    //    alert("请设置输出字段！");
    //    return false;
    //}
    if(!($('#choiceFieldList tbody tr').length > 0)){
        alert("请设置输出字段！");
        return false;
    }
}
function typeRadio(a){
//    if($('#templateId').val() == ""){
//        alert("请设置输出字段！");
//        return false;
//    }
//    if(a.parents('.clone_filtration_tr').find('input[isFixed=isFixed]:checked').val() == undefined){
//        alert("请选择是否固定条件！");
//        return false;
//    }
//    if(a.parents('.clone_filtration_tr').find('select[name=selectField]').val() == undefined){
//        alert("请选择字段！");
//        return false;
//    }
//    if(a.parents('.clone_filtration_tr').find('input[name=conditionValue]').val() == ""){
//        alert("请填写值！");
//        return false;
//    }
//    pub.postJSON('operate.json',
//        {
//            id:a.parents('.clone_filtration_tr').find('.filtration_del').attr('filtartionId'),
//            action:'saveFiltration',
//            templateId:$('#templateId').val(),
//            subjectColId:a.parents('.clone_filtration_tr').find('select[name=selectField]').val(),
//            operationChar:a.parents('.clone_filtration_tr').find('select[name=operator]').val(),
//            logicChar:a.parents('.clone_filtration_tr').find('input[logicChar=logicChar]:checked').val(),
//            conditionValue:a.parents('.clone_filtration_tr').find('input[name=conditionValue]').val(),
//            isFixed:a.parents('.clone_filtration_tr').find('input[isFixed=isFixed]:checked').val()
//        }
//        ,function(json){
//            if (json.ok) {
//                a.parents('.clone_filtration_tr').find('.filtration_del').attr('filtartionId',json.data);
//            }
//        });
}

function del(template_id){
    $.ajax({
        type: "POST",
        url: "operate.json?action=delete",
        data: {template_id:template_id},
        dataType: "json",
        success: function(data){
        }
    });
}
function save(){
    if($('#templateName').val() == "" || $('#templateName').val() == undefined){
        alert("请输入模板名！");
        return;
    }
    if($('#subjectText').val() == "" || $('#subjectText').val() == undefined){
        alert("请选择主题！");
        return;
    }
    if(!($('#choiceFieldList tbody tr').length > 0)){
        alert("请选择输出字段！");
        return;
    }

    if($('#filtrationTable tbody tr').eq(1).find('td').eq(0).find('input[type=radio]:checked').val() != undefined){
        if($('#filtrationTable tbody tr').eq(1).find('td').eq(1).find('select[name=selectField]').val() == undefined){
            alert("请填写过滤条件！");
            return;
        }
    }
    for(var i = 2 ; i < $('#filtrationTable tbody tr').length; i++){
        if($('#filtrationTable tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == undefined
            || $('#filtrationTable tbody tr').eq(i).find('td').eq(1).find('select[name=selectField]').val() == undefined){
            alert("请填写过滤条件！");
            return;
        }
    }

    //if($('input[name=typeIsChoice]:checked').val() == undefined && $('#filtrationTable tbody tr').eq(1).find('td').eq(0).find('input[type=radio]:checked').val() != undefined){
    //    alert("请选择关联关系！");
    //    return;
    //}

    //保存
    var field='';
    for(var i = 1 ; i < $('#choiceFieldList tr').length; i++) {
        field += $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('id') + ':' + $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').val() + ',';
    }
    var logicChar = $('input[name=typeIsChoice]:checked').val();
    if($('#filtrationTable tbody tr').length > 1 && $('#filtrationTable tbody tr').eq(1).find('input[type=radio]:checked').val() != undefined){
        var list = [];
        for(var i = 1 ; i < $('#filtrationTable tbody tr').length; i++){
            var obj = {
                id:$('#filtrationTable tbody tr').eq(i).find('.filtration_del').attr('filtartionId'),
                templateId:$('#templateId').val(),
                subjectColId:$('#filtrationTable tbody tr').eq(i).find('select[name=selectField]').val(),
                operationChar:$('#filtrationTable tbody tr').eq(i).find('select[name=operator]').val(),
                fieldValue:$('#filtrationTable tbody tr').eq(i).find('input[type=text]').val(),
                logicChar:logicChar,
                conditionValue:$('#filtrationTable tbody tr').eq(i).find('input[name=conditionValue]').val(),
                isFixed:$('#filtrationTable tbody tr').eq(i).find('input[type=radio]:checked').val()
            };
            list.push(obj);
        }
    }
    pub.postJSON('operate.json', {
        action:'saveTemplate',
        templateId:$('#templateId').val(),
        subjectId:$('#subjectText').attr('subjectId'),
        templateName:$('#templateName').val(),
        templateClassId:$('#classId').val(),
        remark:$('#remark').val(),
        createTime:$('#createTime').val(),
        field:field,
        json:JSON.stringify(list)
    },function(json){
        if (json.ok) {
            alert("保存成功！");
            //window.history.back();
            backForData();
        }else{
            alert(json.msg);
        }
    });
}

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