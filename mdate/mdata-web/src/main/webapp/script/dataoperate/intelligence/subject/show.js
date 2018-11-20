/**
 * Created by sheng on 2017/6/14.
 */
var _a;
var cloneChoiceFieldList;
$(function(){
    //$('.filtration').show(0);

    $("input[name=mainChoice]").click(function(){
        //alert("您是..." + $(this).val());
    });

    $("select[name=selectSource]").change(function(){
        var a = $(this);

        a.parents('.clone_filtration_tr').find('select[name=selectField]').empty();
        if(a.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == undefined || a.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == "")
            return false;

        var sourceType = '';
        var srcSchema = '';
        var srcTable = '';
        var srcSubjectId = '';
        var index = a.parents('.clone_filtration_tr').find('select[name=selectSource]').find('option:selected').index();
        if(index == 0){
            if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(0).find('td').eq(1).find('.input-first').attr('srcSchema');
                srcTable = $('#tbody tr').eq(0).find('td').eq(1).find('.input-first').attr('placeholder');
            }else if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSubjectId = $('#tbody tr').eq(0).find('td').eq(1).find('.input-second').attr('srcSubjectid');
            }
        }else if(index == 1){
            if($('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(0).find('td').eq(4).find('.input-third').attr('srcSchema');
                srcTable = $('#tbody tr').eq(0).find('td').eq(4).find('.input-third').attr('placeholder');
            }else if($('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSubjectId = $('#tbody tr').eq(0).find('td').eq(4).find('.input-fourth').attr('srcSubjectid');
            }
        }else if(index > 1){
            if($('#tbody tr').eq(index-1).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-third').attr('srcSchema');
                srcTable = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-third').attr('placeholder');
            }else if($('#tbody tr').eq(index-1).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSubjectId = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-fourth').attr('srcSubjectid');
            }
        }

        var flag = a.parents('.clone_filtration_tr').attr('queryType');
        if(flag != 1){
            flag = 0;
        }

        pub.postJSON('operate.json',{
            action:'getFieldsBySource',
            sourceType:sourceType,
            srcSchema:srcSchema,
            srcTable:srcTable,
            srcSubjectId:srcSubjectId,
            flag:flag
        },function(json){
            if (json.ok) {
                for(var i=0; i<json.data.length; i++){
                    var name = '';
                    if(json.data[i].fieldChnName != undefined){
                        name = json.data[i].fieldChnName;
                    }else if(json.data[i].fieldName != undefined){
                        name = json.data[i].fieldName;
                    }
                    a.parents('.clone_filtration_tr').find('select[name=selectField]').append('<option value='+ json.data[i].fieldId +'>' + name + '</option>');
                }
            }
        });
    });

    if(_sourceList.length  > 0 && _sourceList != ""){
        var firstTr=$('#tbody').find('.clone_tr').eq(0).find('td');
        firstTr.eq(0).find("input[type='radio'][value='" + _sourceList[0].source_type + "']").attr("checked","checked");
        if(_sourceList[0].source_type == 1){
            firstTr.eq(1).find('.input-first').attr('srcSchema',_sourceList[0].src_schema);
            firstTr.eq(1).find('.input-first').attr('placeholder',_sourceList[0].src_table);
            firstTr.eq(1).find('.db_input_second').attr("disabled","disabled").removeAttr("data-toggle","modal");
            firstTr.eq(1).find('.db_input_first').attr("data-toggle","modal").removeAttr("disabled","disabled");
            //firstTr.eq(2).find('select[name=associationMode]').attr('src1Name',_sourceList[0].src_table);
        }else if(_sourceList[0].source_type == 2){
            firstTr.eq(1).find('.input-second').attr('srcSubjectId',_sourceList[0].src_subject_id);
            firstTr.eq(1).find('.input-second').attr('placeholder',_sourceList[0].subject_name);
            firstTr.eq(1).find('.db_input_first').attr("disabled","disabled").removeAttr("data-toggle","modal");
            firstTr.eq(1).find('.db_input_second').attr("data-toggle","modal").removeAttr("disabled","disabled");
            //firstTr.eq(2).find('select[name=associationMode]').attr('src1Name',_sourceList[0].subject_name);
        }
        firstTr.eq(2).find('select[name=associationMode]').attr('src1Id',_sourceList[0].id);
        if(_sourceList[1] != undefined){
            firstTr.eq(2).find('select[name=associationMode]').attr('src2Id',_sourceList[1].id);
            firstTr.eq(3).find("input[type='radio'][value='" + _sourceList[1].source_type + "']").attr("checked","checked");
        }
        if(_sourceList[1] != undefined){
            if(_sourceList[1].source_type == 1){
                firstTr.eq(4).find('.input-third').attr('srcSchema',_sourceList[1].src_schema);
                firstTr.eq(4).find('.input-third').attr('placeholder',_sourceList[1].src_table);
                firstTr.eq(4).find('.db_input_fourth').attr("disabled","disabled").removeAttr("data-toggle","modal");
                firstTr.eq(4).find('.db_input_third').attr("data-toggle","modal").removeAttr("disabled","disabled");
                //firstTr.eq(2).find('select[name=associationMode]').attr('src2Name',_sourceList[1].src_table);
            }else if(_sourceList[1].source_type == 2){
                firstTr.eq(4).find('.input-fourth').attr('srcSubjectId',_sourceList[1].src_subject_id);
                firstTr.eq(4).find('.input-fourth').attr('placeholder',_sourceList[1].subject_name);
                firstTr.eq(4).find('.db_input_third').attr("disabled","disabled").removeAttr("data-toggle","modal");
                firstTr.eq(4).find('.db_input_fourth').attr("data-toggle","modal").removeAttr("disabled","disabled");
                //firstTr.eq(2).find('select[name=associationMode]').attr('src2Name',_sourceList[1].subject_name);
            }
        }
        if( _relaList.length  > 0){
            firstTr.eq(2).find('select[name=associationMode]').val(_relaList[0].rel_type);
            firstTr.eq(5).find('.input-fifth').val(_relaList[0].rel_condtion);
            firstTr.eq(5).find('.input-fifth').attr('relCondtionJson',_relaList[0].conditionsJson);
            firstTr.eq(6).find('.del_tr').attr('relaId',_relaList[0].id);
        }

        for(var i = 1;i < _relaList.length; i++){
            var j = i + 1;
            var model = $('#model_clone');
            var clone_tr = model.find('.clone_tr').clone(true).addClass('clone_tt');
            clone_tr.find('td').eq(2).find('input[type="radio"]').attr("name",randomString(4));

            clone_tr.find('td').eq(1).find('select[name=associationMode]').attr('src1Id',_relaList[i].src1_id);
            clone_tr.find('td').eq(1).find('select[name=associationMode]').attr('src2Id',_relaList[i].src2_id);
            if(_sourceList[j].source_type == 1){
                //clone_tr.find('td').eq(1).find('select[name=associationMode]').attr('src1Name',_sourceList[j].src_table);
                clone_tr.find('td').eq(3).find('.input-third').attr('srcSchema',_sourceList[j].src_schema);
                clone_tr.find('td').eq(3).find('.input-third').attr('placeholder',_sourceList[j].src_table);
                clone_tr.find('td').eq(3).find('.db_input_fourth').attr("disabled","disabled").removeAttr("data-toggle","modal");
                clone_tr.find('td').eq(3).find('.db_input_third').attr("data-toggle","modal").removeAttr("disabled","disabled");
            }else if(_sourceList[j].source_type == 2){
                //clone_tr.find('td').eq(1).find('select[name=associationMode]').attr('src1Name',_sourceList[j].subject_name);
                clone_tr.find('td').eq(3).find('.input-fourth').attr('srcSubjectId',_sourceList[j].src_subject_id);
                clone_tr.find('td').eq(3).find('.input-fourth').attr('placeholder',_sourceList[j].subject_name);
                clone_tr.find('td').eq(3).find('.db_input_third').attr("disabled","disabled").removeAttr("data-toggle","modal");
                clone_tr.find('td').eq(3).find('.db_input_fourth').attr("data-toggle","modal").removeAttr("disabled","disabled");
            }
            clone_tr.find('td').eq(2).find("input[type='radio'][value='" + _sourceList[j].source_type + "']").attr("checked","checked");


            //clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ _relaList[i].src2_id +'>'
            //    + _relaList[i].srcName +'</option>');

            for(var x=0 ; x< i; x++){
                if(x == 0){
                    if($('#tbody tr').eq(x).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                        clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ $('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src1Id') +'>'
                            + $('#tbody tr').eq(x).find('td').eq(1).find('.input-first').attr('placeholder') +'</option>');
                    }else if($('#tbody tr').eq(x).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                        clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ $('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src1Id') +'>'
                            + $('#tbody tr').eq(x).find('td').eq(1).find('.input-second').attr('placeholder') +'</option>');
                    }
                    if($('#tbody tr').eq(x).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                        clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ $('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src2Id') +'>'
                            + $('#tbody tr').eq(x).find('td').eq(4).find('.input-third').attr('placeholder') +'</option>');
                    }else if($('#tbody tr').eq(x).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                        clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ $('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src2Id') +'>'
                            + $('#tbody tr').eq(x).find('td').eq(4).find('.input-fourth').attr('placeholder') +'</option>');
                    }
                }else{
                    if($('#tbody tr').eq(x).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                        clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ $('#tbody tr').eq(x).find('td').eq(1).find('select[name=associationMode]').attr('src2Id') +'>'
                            + $('#tbody tr').eq(x).find('td').eq(3).find('.input-third').attr('placeholder') +'</option>');
                    }else if($('#tbody tr').eq(x).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                        clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').append('<option value='+ $('#tbody tr').eq(x).find('td').eq(1).find('select[name=associationMode]').attr('src2Id') +'>'
                            + $('#tbody tr').eq(x).find('td').eq(3).find('.input-fourth').attr('placeholder') +'</option>');
                    }
                }
            }
            clone_tr.find('td').eq(0).find('select[name=choiceSrc2]').val(_relaList[i].src1_id);

            clone_tr.find('td').eq(1).find('select[name=associationMode]').val(_relaList[i].rel_type);
            clone_tr.find('td').eq(4).find('.input-fifth').val(_relaList[i].rel_condtion);
            clone_tr.find('td').eq(4).find('.input-fifth').attr('relCondtionJson',_relaList[i].conditionsJson);
            clone_tr.find('td').eq(5).find('.del_tr').attr('relaId',_relaList[i].id);

            $('#tbody').append(clone_tr);
        }
    }

    if(_subConditionsJson.length > 0){
        $('#filtration_tbody').find('.initTr').remove();
        for(var i = 0; i< _subConditionsJson.length ; i++){
            var clone_filtration_tt = $('.clone_filtration_tt').eq(0).clone(true).show(0);
            clone_filtration_tt.find('input[type="radio"]').attr("name",randomString(5));

            //clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').prepend("<option value="+_subConditionsJson[i].srcId+">"+_subConditionsJson[i].sourceName+"</option>");

            for(var x=0;x<$('#tbody tr').length;x++){
                if(x == 0){
                    if($('#tbody tr').eq(x).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                        clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').append("<option value="+$('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src1Id')+">"
                            +$('#tbody tr').eq(x).find('td').eq(1).find('.input-first').attr('placeholder')+"</option>");
                    }else if($('#tbody tr').eq(x).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                        clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').append("<option value="+$('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src1Id')+">"
                            +$('#tbody tr').eq(x).find('td').eq(1).find('.input-second').attr('placeholder')+"</option>");
                    }
                    if($('#tbody tr').eq(x).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                        clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').append("<option value="+$('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src2Id')+">"
                            +$('#tbody tr').eq(x).find('td').eq(4).find('.input-third').attr('placeholder')+"</option>");
                    }else if($('#tbody tr').eq(x).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                        clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').append("<option value="+$('#tbody tr').eq(x).find('td').eq(2).find('select[name=associationMode]').attr('src2Id')+">"
                            +$('#tbody tr').eq(x).find('td').eq(4).find('.input-fourth').attr('placeholder')+"</option>");
                    }
                }else{
                    if($('#tbody tr').eq(x).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                        clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').append("<option value="+$('#tbody tr').eq(x).find('td').eq(1).find('select[name=associationMode]').attr('src2Id')+">"
                            +$('#tbody tr').eq(x).find('td').eq(3).find('.input-third').attr('placeholder')+"</option>");
                    }else if($('#tbody tr').eq(x).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                        clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').append("<option value="+$('#tbody tr').eq(x).find('td').eq(1).find('select[name=associationMode]').attr('src2Id')+">"
                            +$('#tbody tr').eq(x).find('td').eq(3).find('.input-fourth').attr('placeholder')+"</option>");
                    }
                }
            }
            clone_filtration_tt.find("td").eq(0).find('select[name=selectSource]').val(_subConditionsJson[i].srcId);

            //clone_filtration_tt.find("td").eq(1).find('select[name=selectField]').prepend("<option value="+_subConditionsJson[i].fieldId+">"+_subConditionsJson[i].subjectColName+"</option>");

            clone_filtration_tt.find("td").eq(1).find('select[name=selectField]').empty();
            for(var j = 0;j<_subConditionsJson[i].fields.length; j++){
                var name;
                if(_subConditionsJson[i].fields[j].fieldChnName!= undefined && _subConditionsJson[i].fields[j].fieldChnName!=''){
                    name =_subConditionsJson[i].fields[j].fieldChnName;
                }else if(_subConditionsJson[i].fields[j].fieldName!= undefined){
                    name =_subConditionsJson[i].fields[j].fieldName;
                }
                clone_filtration_tt.find("td").eq(1).find('select[name=selectField]').append("<option value="+_subConditionsJson[i].fields[j].fieldId+">"+ name +"</option>");
            }
            clone_filtration_tt.find("td").eq(1).find('select[name=selectField]').val(_subConditionsJson[i].fieldId);

            clone_filtration_tt.find("td").eq(2).find('select[name=operator]').val(_subConditionsJson[i].operationChar);
            clone_filtration_tt.find("td").eq(3).find('input[name=conditionValue]').val(_subConditionsJson[i].fieldValue);
            //clone_filtration_tt.find("td").eq(4).find("input[type='radio'][value='" + _subConditionsJson[i].logicChar + "']").prop("checked","checked");
            $("input[name=typeIsChoice][value='" + _subConditionsJson[i].logicChar + "']").prop("checked","checked");
            clone_filtration_tt.find("td").eq(4).find('.filtration_del').attr('filtartionId',_subConditionsJson[i].id);
            $('#filtration_tbody').append(clone_filtration_tt);
        }
    }

    if(_colsList.length > 0){
        $("#choiceFieldList tbody").html("");
        for(var i = 0;i < _colsList.length; i++){
            var field_name = '';
            var field_chn_name = '';
            var alias_field_name = '';
            if(_colsList[i].field_name != field_name){
                field_name = _colsList[i].field_name;
            }
            if(_colsList[i].field_chn_name != undefined){
                field_chn_name = _colsList[i].field_chn_name;
            }
            if(_colsList[i].alias_field_name != undefined){
                alias_field_name = _colsList[i].alias_field_name;
            }
            var html="";
            html += '<tr>' +
                '<td><input type="checkbox" id="'+ _colsList[i].cid + '" name="rightChoiceField" aria-label="..." srcId="'+ _colsList[i].src_id +'"' +
                'fieldId="'+ _colsList[i].id +'" sourceIndex="'+ _colsList[i].sourceIndex +'" ></td>' +
                '<td>'+ field_name +'</td>' +
                '<td><input type="text" class="form-control" name="alias_field_name" value="'+alias_field_name+'"></td>' +
                '<td>'+ field_chn_name +'</td>' +
                '<td>' +
                '<a onclick="click_up($(this))"><span class="glyphicon glyphicon-chevron-up click_up" aria-hidden="true"></span></a>' +
                '<a onclick="click_down($(this))"><span class="glyphicon glyphicon-chevron-down click_down" aria-hidden="true"></span></a>' +
                '</td>' +
                '</tr>';
            $("#choiceFieldList tbody").append(html);
        }
    }
});

//点击选择表页，查询所有schema的名字
function getSchemaName(e){
    if(e.parents('td').index() == 1 && e.parents('tr').index() == 0){
        $('#modifySource').val(0);
    }else if(e.parents('td').index() == 4 && e.parents('tr').index() == 0){
        $('#modifySource').val(1);
    }else if(e.parents('td').index() == 3 && e.parents('tr').index() > 0){
        $('#modifySource').val(e.parents('tr').index()+1);
    }

    $('#queryName').val('');
    $('#allTable tbody').html("");
    $("#selectSchema").empty();
    pub.postJSON('operate.json',{action:'getSchemaName'},function(json){
        //设置select
        $("#selectSchema").append("<option value=''>请选择</option>");
        for(var i=0;i<json.data.length;i++){
            $("#selectSchema").append("<option value='"+ json.data[i] +"' >" + json.data[i] + "</option>");
        }
    });
}

//返回模式下所有的表
function getTableInfo(){
    $('#allTable tbody').html("");
    pub.postJSON('operate.json',{
        action:'getTableInfo',
        schemaName:$('#selectSchema').find('option:selected').val(),
        queryName:$('#queryName').val()
    },function(json){
        //操作table
        var html="";
        for(var i=0;i<json.data.length;i++){
            html += '<tr>' +
                '<td><input type="radio" name="choiceTable" value="' + json.data[i] + '"></td>' +
                '<td>'+json.data[i]+'</td>' +
                '</tr>';
        }
        $('#allTable tbody').html(html);
    });
}
function getSubjectClass(e){
    if(e.parents('td').index() == 1 && e.parents('tr').index() == 0){
        $('#modifySource').val(0);
    }else if(e.parents('td').index() == 4 && e.parents('tr').index() == 0){
        $('#modifySource').val(1);
    }else if(e.parents('td').index() == 3 && e.parents('tr').index() > 0){
        $('#modifySource').val(e.parents('tr').index()+1);
    }

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
        status:$('#queryStatus').find('option:selected').val()
    },function(json){
        //操作table
        var html="";
        $('#allSubject tbody').html("");
        for(var i=0;i<json.data.length;i++){
            if(json.data[i].subjectId == $('#subjectId').val())
                continue;
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
function resetQuery(){
    $("#querySubjectName").val('');
}

//设置数据
function settingData(){
    if($('.radio-check1').css('display') != 'none'){
        if($('input[name=choiceTable]:checked').val() == undefined){
            alert("请选择表！");
            $('#settingData').removeAttr('data-dismiss');

        }
        else{
            if($input.hasClass("db_input_first")){
                $input.parents('.input-group').find(".input-first").attr("placeholder",$('input[name=choiceTable]:checked').val());
                $input.parents('.input-group').find(".input-first").attr('srcSchema',$('#selectSchema').find('option:selected').val());
            }else if($input.hasClass("db_input_third")){
                $input.parents('.input-group').find(".input-third").attr("placeholder",$('input[name=choiceTable]:checked').val());
                $input.parents('.input-group').find(".input-third").attr('srcSchema',$('#selectSchema').find('option:selected').val());
            }

            if($input.parents('.db_input').index() == 1){
                //pub.postJSON('operate.json',{
                //    action:'saveSource',
                //    id:$input.parents('.clone_tr').find('select[name=associationMode]').attr('src1Id'),
                //    subjectId: $('#subjectId').val(),
                //    sourceType: $input.parents('.clone_tr').find('input[order=radio_1]:checked').val(),
                //    srcSchema: $input.parents('.clone_tr').find('.input-first').attr('srcSchema'),
                //    srcTable: $input.parents('.clone_tr').find('.input-first').attr('placeholder'),
                //    srcSubjectId: ""
                //},function(json){
                //    if(json.ok){
                //        $input.parents('.clone_tr').find('.associationMode').attr('src1Id',json.data);
                //        $input.parents('.clone_tr').find('.associationMode').attr('src1Name',$input.parents('.clone_tr').find('.input-first').attr('placeholder'));
                //    }
                //});
            }


            $('#optionalFieldList tbody').html("");
            $('#choiceFieldList tbody').html("");
            if($('#modifySource').val() == 0 || $('#modifySource').val() == 1){
                $('#tbody tr').eq(0).find('.input-fifth').val('');
                $('#tbody tr').eq(0).find('.input-fifth').attr('relCondtionJson','');
            }else if($('#modifySource').val() > 1){
                $('#tbody tr').eq($('#modifySource').val()-1).find('.input-fifth').val('');
                $('#tbody tr').eq($('#modifySource').val()-1).find('.input-fifth').attr('relCondtionJson','');
            }
            for(var i = $('#filtration_tbody tr').length; i > 0 ; i--){
                if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == $('#modifySource').val()){
                    $('#filtration_tbody tr').eq(i).remove();
                }
            }
            $('#settingData').attr('data-dismiss','modal')
        }
    }else if($('.radio-check2').css('display') != 'none'){
        if($('input[name=choiceSubject]:checked').val() == undefined){
            alert("请选择主题！");
            $('#settingData').removeAttr('data-dismiss');

        }
        else{
            if($input.hasClass("db_input_second")){
                $input.parents('.input-group').find(".input-second").attr("placeholder",$('input[name=choiceSubject]:checked').attr('subjectName'));
                $input.parents('.input-group').find(".input-second").attr('srcSubjectId',$('input[name=choiceSubject]:checked').val());
            }else if($input.hasClass("db_input_fourth")){
                $input.parents('.input-group').find(".input-fourth").attr("placeholder",$('input[name=choiceSubject]:checked').attr('subjectName'));
                $input.parents('.input-group').find(".input-fourth").attr('srcSubjectId',$('input[name=choiceSubject]:checked').val());
            }

            if($input.parents('.db_input').index() == 1){
                //pub.postJSON('operate.json',{
                //    action:'saveSource',
                //    id:$input.parents('.clone_tr').find('select[name=associationMode]').attr('src1Id'),
                //    subjectId: $('#subjectId').val(),
                //    sourceType:$input.parents('.clone_tr').find('input[order=radio_1]:checked').val(),
                //    srcSchema:"",
                //    srcTable:"",
                //    srcSubjectId:$input.parents('.clone_tr').find('.input-second').attr('srcSubjectId')
                //},function(json){
                //    if(json.ok){
                //        $input.parents('.clone_tr').find('.associationMode').attr('src1Id',json.data);
                //        $input.parents('.clone_tr').find('.associationMode').attr('src1Name',$('input[name=choiceSubject]:checked').attr('subjectName'));
                //    }
                //});
            }

            $('#optionalFieldList tbody').html("");
            $('#choiceFieldList tbody').html("");
            if($('#modifySource').val() == 0 || $('#modifySource').val() == 1){
                $('#tbody tr').eq(0).find('.input-fifth').val('');
                $('#tbody tr').eq(0).find('.input-fifth').attr('relCondtionJson','');
            }else if($('#modifySource').val() > 1){
                $('#tbody tr').eq($('#modifySource').val()-1).find('.input-fifth').val('');
                $('#tbody tr').eq($('#modifySource').val()-1).find('.input-fifth').attr('relCondtionJson','');
            }
            for(var i = $('#filtration_tbody tr').length ;i > 0 ; i--){
                if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == $('#modifySource').val()){
                    $('#filtration_tbody tr').eq(i).remove();
                }
            }
            $('#settingData').attr('data-dismiss','modal')
        }
    }
}
function saveRelationships(a,flag){
    for(var i = $("#condition_tbody tr").length; i > 0; i--){
        $("#condition_tbody tr").eq(i).remove();
    }
    _a=a;
    var list= [];
    var subjectId=$('#subjectId').val();
    $('#myModalLabel').attr('relaId',a.parents('.clone_tr').find('.del_tr').attr('relaId'));
    var name1 = '';
    var name2 = '';
    if(flag==0){
        $('#source1Id').val(a.parents('.clone_tr').index());
        $('#source2Id').val(a.parents('.clone_tr').index()+1);
        if(a.parents('.clone_tr').find('input[order=radio_1]:checked').val() == undefined || a.parents('.clone_tr').find('input[order=radio_2]:checked').val() == undefined){
            alert("请选择关联类型！");
            //$('input[name=relCondition]').removeAttr('data-toggle');
            a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
            return;
        }
        if(a.parents('.clone_tr').find('input[order=radio_1]:checked').val() == 1){
            if(a.parents('.clone_tr').find('.input-first').attr('placeholder') == "点击选择表页") {
                alert("请选择表！");
                //$('input[name=relCondition]').removeAttr('data-toggle');
                a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
                return;
            }else {
                name1 = a.parents('.clone_tr').find('.input-first').attr('placeholder');
                var obj = {
                    id:a.parents('.clone_tr').find('select[name=associationMode]').attr('src1Id'),
                    subjectId: subjectId,
                    sourceType: a.parents('.clone_tr').find('input[order=radio_1]:checked').val(),
                    srcSchema: a.parents('.clone_tr').find('.input-first').attr('srcSchema'),
                    srcTable: a.parents('.clone_tr').find('.input-first').attr('placeholder'),
                    srcSubjectId: ""
                };
                list.push(obj);
            }
        }
        if(a.parents('.clone_tr').find('input[order=radio_1]:checked').val() == 2 ){
            if(a.parents('.clone_tr').find('.input-second').attr('placeholder') == "点击选择主题"){
                alert("请选择主题！");
                //$('input[name=relCondition]').removeAttr('data-toggle');
                a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
                return;
            }else{
                name1 = a.parents('.clone_tr').find('.input-second').attr('placeholder');
                var obj = {
                    id:a.parents('.clone_tr').find('select[name=associationMode]').attr('src1Id'),
                    subjectId: subjectId,
                    sourceType:a.parents('.clone_tr').find('input[order=radio_1]:checked').val(),
                    srcSchema:"",
                    srcTable:"",
                    srcSubjectId:a.parents('.clone_tr').find('.input-second').attr('srcSubjectId')
                };
                list.push(obj);
            }
        }
        if(a.parents('.clone_tr').find('input[order=radio_2]:checked').val() == 1 ){
            if(a.parents('.clone_tr').find('.input-third').attr('placeholder') == "点击选择表页"){
                alert("请选择表！");
                a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
                return;
            }else{
                name2 = a.parents('.clone_tr').find('.input-third').attr('placeholder');
                var obj = {
                    id:a.parents('.clone_tr').find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:a.parents('.clone_tr').find('input[order=radio_2]:checked').val(),
                    srcSchema:a.parents('.clone_tr').find('.input-third').attr('srcSchema'),
                    srcTable:a.parents('.clone_tr').find('.input-third').attr('placeholder'),
                    srcSubjectId:""
                };
                list.push(obj);
            }
        }
        if(a.parents('.clone_tr').find('input[order=radio_2]:checked').val() == 2 ){
            if( a.parents('.clone_tr').find('.input-fourth').attr('placeholder') == "点击选择主题"){
                alert("请选择主题！");
                //$('input[name=relCondition]').removeAttr('data-toggle');
                a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
                return;
            }else{
                name2 = a.parents('.clone_tr').find('.input-fourth').attr('placeholder');
                var obj = {
                    id:a.parents('.clone_tr').find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:a.parents('.clone_tr').find('input[order=radio_2]:checked').val(),
                    srcSchema:"",
                    srcTable:"",
                    srcSubjectId:a.parents('.clone_tr').find('.input-fourth').attr('srcSubjectId')
                };
                list.push(obj);
            }
        }
    }
    if(flag == 1){
        if(a.parents('.clone_tr').find('select[name=choiceSrc2]').val() == undefined){
            alert("请选择关联！");
            a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
            return;
        }
        $('#source1Id').val(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index());
        $('#source2Id').val(a.parents('.clone_tr').index()+1);
        name1 = a.parents('.clone_tr').find('select[name=choiceSrc2]').text().trim();
        if(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index() == 0){
            if($('#tbody tr').eq(0).find('input[order=radio_1]:checked').val() == 1){
                var obj = {
                    id:$('#tbody tr').eq(0).find('select[name=associationMode]').attr('src1Id'),
                    subjectId: subjectId,
                    sourceType: $('#tbody tr').eq(0).find('input[order=radio_1]:checked').val(),
                    srcSchema: $('#tbody tr').eq(0).find('.input-first').attr('srcSchema'),
                    srcTable: $('#tbody tr').eq(0).find('.input-first').attr('placeholder'),
                    srcSubjectId: ""
                };
                list.push(obj);
            }
            if($('#tbody tr').eq(0).find('input[order=radio_1]:checked').val() == 2 ){
                var obj = {
                    id:$('#tbody tr').eq(0).find('select[name=associationMode]').attr('src1Id'),
                    subjectId: subjectId,
                    sourceType:$('#tbody tr').eq(0).find('input[order=radio_1]:checked').val(),
                    srcSchema:"",
                    srcTable:"",
                    srcSubjectId:$('#tbody tr').eq(0).find('.input-second').attr('srcSubjectId')
                };
                list.push(obj);
            }
        }else if(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index() == 1){
            if($('#tbody tr').eq(0).find('input[order=radio_2]:checked').val() == 1 ){
                var obj = {
                    id:$('#tbody tr').eq(0).find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:$('#tbody tr').eq(0).find('input[order=radio_2]:checked').val(),
                    srcSchema:$('#tbody tr').eq(0).find('.input-third').attr('srcSchema'),
                    srcTable:$('#tbody tr').eq(0).find('.input-third').attr('placeholder'),
                    srcSubjectId:""
                };
                list.push(obj);
            }
            if($('#tbody tr').eq(0).find('input[order=radio_2]:checked').val() == 2 ){
                var obj = {
                    id:$('#tbody tr').eq(0).find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:$('#tbody tr').eq(0).find('input[order=radio_2]:checked').val(),
                    srcSchema:"",
                    srcTable:"",
                    srcSubjectId:$('#tbody tr').eq(0).find('.input-fourth').attr('srcSubjectId')
                };
                list.push(obj);
            }
        }else{
            if($('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('input[order=radio_2]:checked').val() == 1 ){
                name2 = $('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('.input-third').attr('placeholder');
                var obj = {
                    id:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('input[order=radio_2]:checked').val(),
                    srcSchema:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('.input-third').attr('srcSchema'),
                    srcTable:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('.input-third').attr('placeholder'),
                    srcSubjectId:""
                };
                list.push(obj);
            }
            if($('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('input[order=radio_2]:checked').val() == 2 ){
                var obj = {
                    id:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('input[order=radio_2]:checked').val(),
                    srcSchema:"",
                    srcTable:"",
                    srcSubjectId:$('#tbody tr').eq(a.parents('.clone_tr').find('select[name=choiceSrc2]').find('option:selected').index()-1).find('.input-fourth').attr('srcSubjectId')
                };
                list.push(obj);
            }
        }

        if(a.parents('.clone_tr').find('input[order=radio_2]:checked').val() == 1 ){
            if(a.parents('.clone_tr').find('.input-third').attr('placeholder') == "点击选择表页"){
                alert("请选择表！");
                a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
                return;
            }else{
                name2 = a.parents('.clone_tr').find('.input-third').attr('placeholder');
                var obj={
                    id:a.parents('.clone_tr').find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:a.parents('.clone_tr').find('input[order=radio_2]:checked').val(),
                    srcSchema:a.parents('.clone_tr').find('.input-third').attr('srcSchema'),
                    srcTable:a.parents('.clone_tr').find('.input-third').attr('placeholder'),
                    srcSubjectId:""
                };
                list.push(obj);
            }
        }
        if(a.parents('.clone_tr').find('input[order=radio_2]:checked').val() == 2 ){
            if( a.parents('.clone_tr').find('.input-fourth').attr('placeholder') == "点击选择主题"){
                alert("请选择主题！");
                //$('input[name=relCondition]').removeAttr('data-toggle');
                a.parents('.clone_tr').find('button[name=relCondition]').removeAttr('data-toggle');
                return;
            }else{
                name2 = a.parents('.clone_tr').find('.input-fourth').attr('placeholder');
                var obj={
                    id:a.parents('.clone_tr').find('select[name=associationMode]').attr('src2Id'),
                    subjectId: subjectId,
                    sourceType:a.parents('.clone_tr').find('input[order=radio_2]:checked').val(),
                    srcSchema:"",
                    srcTable:"",
                    srcSubjectId:a.parents('.clone_tr').find('.input-fourth').attr('srcSubjectId')
                };
                list.push(obj);
            }
        }
    }
    //alert(JSON.stringify(list));

    pub.postJSON('operate.json',{
        action:'getFieldBySources',
        sourceList:JSON.stringify(list)
    },function(json){
        if(json.ok){
            $('select[name=src1Field]').empty();
            $('input[name=src1Name]').val("");
            $('select[name=src2Field]').empty();
            $('input[name=src2Name]').val("");

            $('input[name=src1Name]').val(name1);
            $('input[name=src2Name]').val(name2);

            for(var i=0;i<json.data.field0.length;i++){
                $('select[name=src1Field]').append('<option value='+json.data.field0[i].fieldId+'>' + json.data.field0[i].fieldName + '</option>');
            }
            for(var i=0;i<json.data.field1.length;i++){
                $('select[name=src2Field]').append('<option value='+json.data.field1[i].fieldId+'>' + json.data.field1[i].fieldName + '</option>');
            }

            //回显
            var relaConditionList = a.parents('.clone_tr').find('.input-fifth').attr('relCondtionJson');
            if(relaConditionList != undefined && relaConditionList != ''){
                var objList = eval('(' + relaConditionList + ')');
                for(var i =0; i <objList.length; i++){
                    var obj = eval('(' + 'objList[i]' + ')');
                    var tr = $('#condition_tbody tr').eq(0).clone().show(0);
                    tr.appendTo("#condition_tbody");
                    $('#condition_tbody tr').eq(i+1).attr('id',obj.id);
                    $('#condition_tbody tr').eq(i+1).find('select[name=src1Field]').val(obj.field1Id);
                    $('#condition_tbody tr').eq(i+1).find('select[name=src2Field]').val(obj.field2Id);
                }
            }
        }
    });

    a.parents('.clone_tr').find('button[name=relCondition]').attr('data-toggle','modal');
}
function saveAssociationCondition(){
    var list = [];
    var str='';
    for(var i=1;i<$('#condition_tbody tr').length;i++){
        str += 's1.' + $('#condition_tbody tr').eq(i).find('select[name=src1Field] option:selected').text() + ' = s2.' + $('#condition_tbody tr').eq(i).find('select[name=src2Field] option:selected').text() +' and ';
        var obj = {
            id:$('#condition_tbody tr').eq(i).attr('id'),
            relaId:$('#myModalLabel').attr('relaId'),
            src1Id:$('input[name=src1Name]').attr('src1Id'),
            source1Id:$('#source1Id').val(),
            field1Id:$('#condition_tbody tr').eq(i).find('select[name=src1Field]').val(),
            src2Id:$('input[name=src2Name]').attr('src2Id'),
            source2Id:$('#source2Id').val(),
            field2Id:$('#condition_tbody tr').eq(i).find('select[name=src2Field]').val()
        };
        list.push(obj);
    }
    _a.parents('.clone_tr').find('.input-fifth').attr('relCondtionJson',JSON.stringify(list));
    //alert(JSON.stringify(list))

    _a.parents('.clone_tr').find('.input-fifth').val(str.substr( 0 , str.length-5));

    $('#saveAssociationCondition').attr('data-dismiss','modal');
}
function delSrcRelaCondition(e){
    //if(e.parents('tr').attr('id') != undefined){
    //    pub.postJSON('operate.json',
    //        {
    //            action:'delRelation',
    //            relaId:$('#myModalLabel').attr('relaId'),
    //            id:e.parents('tr').attr('id')
    //        }
    //        ,function(json){
    //            if (json.ok) {
    //                alert("删除成功！");
    //                e.parents('.condition_tr').remove();
    //                return true;
    //            }
    //        });
    //}

    e.parents('.condition_tr').remove();
}

function addChoice(a){
    a.empty();

    for(var i=0;i<a.parents('tr').index();i++){
        if(i == 0){
            if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src1Id')+'>'
                        + $('#tbody tr').eq(i).find('td').eq(1).find('.input-first').attr('placeholder')+'</option>');
            }else if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src1Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(1).find('.input-second').attr('placeholder')+'</option>');
            }
            if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(4).find('.input-third').attr('placeholder')+'</option>');
            }else if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(4).find('.input-fourth').attr('placeholder')+'</option>');
            }
        }else{
            if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(1).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(3).find('.input-third').attr('placeholder')+'</option>');
            }else if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(1).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(3).find('.input-fourth').attr('placeholder')+'</option>');
            }
        }
    }

    a.parents('tr').find('.input-fifth').val('');
    a.parents('tr').find('.input-fifth').attr('relCondtionJson','');
}
function outPutFieldModal(){
    //拼接source
    var sourceList = [];
    for(var i = 0 ; i < $('#tbody tr').length; i++){
        var sourceType;
        var srcSchema;
        var srcTable;
        var srcSubjectId;
        if(i == 0){
            if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(i).find('td').find('.input-first').attr('srcSchema');
                srcTable = $('#tbody tr').eq(i).find('td').find('.input-first').attr('placeholder');
                srcSubjectId = '';
            }else if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSchema = '';
                srcTable = '';
                srcSubjectId = $('#tbody tr').eq(i).find('td').find('.input-second').attr('srcSubjectId');
            }
            var sourceObj = {
                id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src1Id'),
                subjectId: $('#subjectId').val(),
                sourceType: sourceType,
                srcSchema: srcSchema,
                srcTable: srcTable,
                srcSubjectId: srcSubjectId
            };
            sourceList.push(sourceObj);
            if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(i).find('td').find('.input-third').attr('srcSchema');
                srcTable = $('#tbody tr').eq(i).find('td').find('.input-third').attr('placeholder');
                srcSubjectId = '';
            }else if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSchema = '';
                srcTable = '';
                srcSubjectId = $('#tbody tr').eq(i).find('td').find('.input-fourth').attr('srcSubjectId');
            }
            var sourceObj2 = {
                id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src2Id'),
                subjectId: $('#subjectId').val(),
                sourceType: sourceType,
                srcSchema: srcSchema,
                srcTable: srcTable,
                srcSubjectId: srcSubjectId
            };
            if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1 || $('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2)
                sourceList.push(sourceObj2);
        }else{
            if($('#tbody tr').eq(i).find('td').eq(0).find('select[name=choiceSrc2]').val() == undefined){
                break;
            }
            if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(i).find('td').find('.input-third').attr('srcSchema');
                srcTable = $('#tbody tr').eq(i).find('td').find('.input-third').attr('placeholder');
                srcSubjectId = '';
            }else if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSchema = '';
                srcTable = '';
                srcSubjectId = $('#tbody tr').eq(i).find('td').find('.input-fourth').attr('srcSubjectId');
            }
            var sourceObj = {
                id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src2Id'),
                subjectId: $('#subjectId').val(),
                sourceType: sourceType,
                srcSchema: srcSchema,
                srcTable: srcTable,
                srcSubjectId: srcSubjectId
            };
            if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 1 || $('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 2)
                sourceList.push(sourceObj);
        }
    }
    //alert(JSON.stringify(sourceList))

    pub.postJSON('operate.json',{
        action:'getAllSourceField',
        sourceJson:JSON.stringify(sourceList)
    },function(json){
        if (json.ok) {
            $("#optionalFieldList tbody").html("");
            for(var i=0;i<json.data.length;i++){
                var srcSchema='';
                var name='';
                if(json.data[i].srcSchema != undefined){
                    srcSchema=json.data[i].srcSchema;
                }
                if(json.data[i].srcTable != undefined){
                    name=json.data[i].srcTable;
                }else if(json.data[i].subjectName !=undefined){
                    name=json.data[i].subjectName;
                }
                if(json.data[i].fieldName == undefined) json.data[i].fieldName='';
                if(json.data[i].fieldChnName == undefined) json.data[i].fieldChnName='';
                var html="";
                html += '<tr>' +
                    '<td><input type="checkbox" name="leftChoiceField" fieldId="'+json.data[i].fieldId+'" srcId="'+json.data[i].srcId+'" sourceIndex="'+json.data[i].sourceIndex+'" aria-label="..."></td>' +
                    '<td>'+srcSchema+'</td>' +
                    '<td>'+ name +'</td>' +
                    '<td>'+json.data[i].fieldName+'</td>' +
                    '<td>'+json.data[i].fieldChnName+'</td>' +
                    '</tr>';
                $("#optionalFieldList tbody").append(html);
            }
        }
    });

    cloneChoiceFieldList = $("#choiceFieldList tbody").clone();

    $('.btn-info').attr('data-toggle','modal')

    //$('.btn-info').removeAttr('data-toggle')
}
function recoveryChoiceFieldList(){
    $("#choiceFieldList").find('tbody').remove();
    $("#choiceFieldList").append(cloneChoiceFieldList);
}
function addSource(a){
    //if($(".input-fifth").eq(0).val() == undefined || $(".input-fifth").eq(0).val() == "")
    //    return false;
    a.empty();
    for(var i=0;i<$('#tbody tr').length;i++){
        if(i == 0){
            if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src1Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(1).find('.input-first').attr('placeholder')+'</option>');
            }else if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src1Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(1).find('.input-second').attr('placeholder')+'</option>');
            }
            if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(4).find('.input-third').attr('placeholder')+'</option>');
            }else if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(2).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(4).find('.input-fourth').attr('placeholder')+'</option>');
            }
        }else{
            if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(1).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(3).find('.input-third').attr('placeholder')+'</option>');
            }else if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                a.append('<option value='+$('#tbody tr').eq(i).find('td').eq(1).find('select[name=associationMode]').attr('src2Id')+'>'
                    + $('#tbody tr').eq(i).find('td').eq(3).find('.input-fourth').attr('placeholder')+'</option>');
            }
        }
    }

    a.parents('.clone_filtration_tr').find('select[name=selectField]').empty();
    if(a.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == undefined || a.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == "")
        return false;

    var sourceType = '';
    var srcSchema = '';
    var srcTable = '';
    var srcSubjectId = '';
    var index = a.parents('.clone_filtration_tr').find('select[name=selectSource]').find('option:selected').index();
    if(index == 0){
        if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
            sourceType = 1;
            srcSchema = $('#tbody tr').eq(0).find('td').eq(1).find('.input-first').attr('srcSchema');
            srcTable = $('#tbody tr').eq(0).find('td').eq(1).find('.input-first').attr('placeholder');
        }else if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
            sourceType = 2;
            srcSubjectId = $('#tbody tr').eq(0).find('td').eq(1).find('.input-second').attr('srcSubjectid');
        }
    }else if(index == 1){
        if($('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
            sourceType = 1;
            srcSchema = $('#tbody tr').eq(0).find('td').eq(4).find('.input-third').attr('srcSchema');
            srcTable = $('#tbody tr').eq(0).find('td').eq(4).find('.input-third').attr('placeholder');
        }else if($('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
            sourceType = 2;
            srcSubjectId = $('#tbody tr').eq(0).find('td').eq(4).find('.input-fourth').attr('srcSubjectid');
        }
    }else if(index > 1){
        if($('#tbody tr').eq(index-1).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
            sourceType = 1;
            srcSchema = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-third').attr('srcSchema');
            srcTable = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-third').attr('placeholder');
        }else if($('#tbody tr').eq(index-1).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
            sourceType = 2;
            srcSubjectId = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-fourth').attr('srcSubjectid');
        }
    }

    var flag = a.parents('.clone_filtration_tr').attr('queryType');
    if(flag != 1){
        flag = 0;
    }

    pub.postJSON('operate.json',{
        action:'getFieldsBySource',
        sourceType:sourceType,
        srcSchema:srcSchema,
        srcTable:srcTable,
        srcSubjectId:srcSubjectId,
        flag:flag
    },function(json){
        if (json.ok) {
            for(var i=0; i<json.data.length; i++){
                var name = '';
                if(json.data[i].fieldChnName != undefined){
                    name = json.data[i].fieldChnName;
                }else if(json.data[i].fieldName != undefined){
                    name = json.data[i].fieldName;
                }
                a.parents('.clone_filtration_tr').find('select[name=selectField]').append('<option value='+ json.data[i].fieldId +'>' + name + '</option>');
            }
        }
    });
}
function addField(a){
    a.empty();
    if(a.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == undefined || a.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == "")
        return false;

    var sourceType = '';
    var srcSchema = '';
    var srcTable = '';
    var srcSubjectId = '';
    var index = a.parents('.clone_filtration_tr').find('select[name=selectSource]').find('option:selected').index();
    if(index == 0){
        if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
            sourceType = 1;
            srcSchema = $('#tbody tr').eq(0).find('td').eq(1).find('.input-first').attr('srcSchema');
            srcTable = $('#tbody tr').eq(0).find('td').eq(1).find('.input-first').attr('placeholder');
        }else if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
            sourceType = 2;
            srcSubjectId = $('#tbody tr').eq(0).find('td').eq(1).find('.input-second').attr('srcSubjectid');
        }
    }else if(index == 1){
        if($('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
            sourceType = 1;
            srcSchema = $('#tbody tr').eq(0).find('td').eq(4).find('.input-third').attr('srcSchema');
            srcTable = $('#tbody tr').eq(0).find('td').eq(4).find('.input-third').attr('placeholder');
        }else if($('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
            sourceType = 2;
            srcSubjectId = $('#tbody tr').eq(0).find('td').eq(4).find('.input-fourth').attr('srcSubjectid');
        }
    }else if(index > 1){
        if($('#tbody tr').eq(index-1).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
            sourceType = 1;
            srcSchema = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-third').attr('srcSchema');
            srcTable = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-third').attr('placeholder');
        }else if($('#tbody tr').eq(index-1).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
            sourceType = 2;
            srcSubjectId = $('#tbody tr').eq(index-1).find('td').eq(3).find('.input-fourth').attr('srcSubjectid');
        }
    }

    pub.postJSON('operate.json',{
        action:'getFieldsBySource',
        sourceType:sourceType,
        srcSchema:srcSchema,
        srcTable:srcTable,
        srcSubjectId:srcSubjectId
    },function(json){
        if (json.ok) {
            for(var i=0; i<json.data.length; i++){
                var name = '';
                if(json.data[i].fieldChnName != undefined){
                    name = json.data[i].fieldChnName;
                }else if(json.data[i].fieldName != undefined){
                    name = json.data[i].fieldName;
                }
                a.append('<option value='+ json.data[i].fieldId +'>' + name + '</option>');
            }
        }
    });
}
function saveSelectedField(){
    $('#saveSelectedFieldBtn').removeAttr('data-dismiss');
    //保存可选字段列表
    if($('#choiceFieldList tbody tr').length == 0){
        alert("请选择输出字段！");
        return;
    }
    var list = [];
    for(var i = 1 ; i < $('#choiceFieldList tr').length; i++){
        if($('#choiceFieldList tr').eq(i).find('td').eq(2).find('input[type=text]').val() == ''){
            list.push($('#choiceFieldList tr').eq(i).find('td').eq(1).text());
        }else{
            list.push($('#choiceFieldList tr').eq(i).find('td').eq(2).find('input[type=text]').val());
        }
    }
    for(var j =0; j<list.length; j++){
        var flag = 0;
        for(i = 0; i < list.length; i++){
            if(list[j] == list[i]){
                flag += 1;
            }
        }
        if(flag == 2){
            alert("有重复字段，请重新输入！");
            return;
        }
    }
    $('#saveSelectedFieldBtn').attr('data-dismiss','modal');
}
var choiceId;
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
function allCopyLeft(){
    // $("#choiceFieldList tbody").html("");
    // for(var i = 1 ; i < $('#optionalFieldList tr').length; i++){
    //     //$('#optionalFieldList tr').eq(i).find('td').eq(3).html();
    //     var html="";
    //     html += '<tr>' +
    //         '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." ' +
    //         'sourceIndex="'+$('#optionalFieldList tr').eq(i).find('td').eq(0).find('input[name=leftChoiceField]').attr('sourceIndex')+'" ' +
    //         'fieldId="'+$('#optionalFieldList tr').eq(i).find('td').eq(0).find('input[name=leftChoiceField]').attr('fieldId')+'" ' +
    //         'srcId="'+$('#optionalFieldList tr').eq(i).find('td').eq(0).find('input[name=leftChoiceField]').attr('srcId')+'" ></td>' +
    //         '<td>'+$('#optionalFieldList tr').eq(i).find('td').eq(3).html()+'</td>' +
    //         '<td>'+$('#optionalFieldList tr').eq(i).find('td').eq(4).html()+'</td>' +
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
            // if($('#optionalFieldList tr').eq(j).find('td').eq(3).html() == $('#choiceFieldList tr').eq(i).find('td').eq(1).html()){
            //     flag=0;
            // }
            if($('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('fieldId') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('fieldId')
                && $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('srcId') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('srcId')
                && $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('sourceIndex') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('sourceIndex')){
                flag=0;
            }
        }
        if(flag == 1){
            var html="";
            html += '<tr>' +
                '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." ' +
                'sourceIndex="'+$('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('sourceIndex')+'" ' +
                'fieldId="'+$('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('fieldId')+'" ' +
                'srcId="'+$('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('srcId')+'" ></td>' +
                '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(3).html()+'</td>' +
                '<td><input type="text" class="form-control" name="alias_field_name" value=""></td>'+
                '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(4).html()+'</td>' +
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
                // if($('#optionalFieldList tr').eq(j).find('td').eq(3).html() == $('#choiceFieldList tr').eq(i).find('td').eq(1).html()){
                //     flag=0;
                // }
                if($('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('fieldId') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('fieldId')
                    && $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('srcId') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('srcId')
                    && $('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').attr('sourceIndex') == $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[type=checkbox]').attr('sourceIndex')){
                    flag=0;
                }
            }
            if(flag == 1){
                var html="";
                html += '<tr>' +
                    '<td><input type="checkbox" id="" name="rightChoiceField" aria-label="..." ' +
                    'sourceIndex="'+$('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('sourceIndex')+'" ' +
                    'fieldId="'+$('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('fieldId')+'" ' +
                    'srcId="'+$('#optionalFieldList tr').eq(j).find('td').eq(0).find('input[name=leftChoiceField]').attr('srcId')+'" ></td>' +
                    '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(3).html()+'</td>' +
                    '<td><input type="text" class="form-control" name="alias_field_name" value=""></td>'+
                    '<td>'+$('#optionalFieldList tr').eq(j).find('td').eq(4).html()+'</td>' +
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
    var flag = 0;
    var colsString='';
    for(var i = 1 ; i < $('#choiceFieldList tr').length; i++) {
        colsString += $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('fieldId') + ',';
    }
    $.ajax({
        type: "POST",
        url: "operate.json?action=checkField",
        data: {
            subjectId:$('#subjectId').val(),
            colsIds:colsString
        },
        dataType: "json",
        async: false,
        success: function(json){
            if(json.ok){
                flag = 1;
            }else{
                alert(json.msg);
                flag = 0;
            }
        }
    });
    if(flag == 1){
        $("#choiceFieldList tbody").html("");
    }else
}
function deleteRight(){
    var flag = 0;
    var colsString='';
    for(var i = 1 ; i < $('#choiceFieldList tr').length; i++) {
        if($('#choiceFieldList tr').eq(i).find('input[type=checkbox]').is(':checked'))
            colsString += $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('fieldId') + ',';
    }
    $.ajax({
        type: "POST",
        url: "operate.json?action=checkField",
        data: {
            subjectId:$('#subjectId').val(),
            colsIds:colsString
        },
        dataType: "json",
        async: false,
        success: function(json){
            if(json.ok){
                flag = 1;
            }else{
                alert(json.msg);
                flag = 0;
            }
        }
    });

    if(flag == 1){
        for(var j = $('#choiceFieldList tr').length ; j >0 ; j--){
            if($('#choiceFieldList tr').eq(j).find('td').eq(0).find('input[type=checkbox]').is(':checked')) {
                $('#choiceFieldList tr').eq(j).remove();
            }
        }
    }else
}
function delRelation(a,flag){
    //var srcId1;
    //var srcId2;
    if(( $('#tbody tr').length > 1 && flag == 0 ) || ( a.parents('.clone_tr').index() < $('#tbody tr').length - 1 ) ){
        alert("请先删除下面的关联！");
        return false;
    }
    //var sourceIds = '';
    //if(flag == 0){
    //    sourceIds = a.parents('tr').find('select[name=associationMode]').attr('src1Id') + ',' + a.parents('tr').find('select[name=associationMode]').attr('src2Id');
    //    srcId1=a.parents('tr').find('select[name=associationMode]').attr('src1Id');
    //    srcId2=a.parents('tr').find('select[name=associationMode]').attr('src2Id');
    //}else if(flag == 1){
    //    sourceIds = a.parents('tr').find('select[name=associationMode]').attr('src1Id') + ',';
    //    srcId2=a.parents('tr').find('select[name=associationMode]').attr('src2Id');
    //}

    if(flag == 0){
        var del = document.getElementsByClassName('delFirst');
        for(var i=0;i<del.length;i++){
            del[i].checked = false;
        }
        var firstTr = a.parents('.clone_tr').find('td');
        //firstTr.eq(0).find('input[type=radio]').removeAttr('checked',"true");
        firstTr.eq(1).find('.input-first').attr("placeholder","点击选择表页");
        firstTr.eq(1).find('.input-first').removeAttr("srcSchema");
        firstTr.eq(1).find('.db_input_first').attr("disabled","disabled").removeAttr("data-toggle","modal");
        firstTr.eq(1).find('.input-second').attr("placeholder","点击选择主题");
        firstTr.eq(1).find('.input-second').removeAttr("srcSubjectId");
        firstTr.eq(1).find('.db_input_second').attr("disabled","disabled").removeAttr("data-toggle","modal");
        firstTr.eq(2).find('select[name=associationMode]').removeAttr('src1Id');
        //firstTr.eq(2).find('select[name=associationMode]').removeAttr('src1Name');
        firstTr.eq(2).find('select[name=associationMode]').removeAttr('src2Id');
        //firstTr.eq(2).find('select[name=associationMode]').removeAttr('src2Name');
        //firstTr.eq(3).find('input[type=radio]').removeAttr('checked',"true");
        firstTr.eq(4).find('.input-third').attr("placeholder","点击选择表页");
        firstTr.eq(4).find('.input-third').removeAttr("srcSchema");
        firstTr.eq(4).find('.db_input_third').attr("disabled","disabled").removeAttr("data-toggle","modal");
        firstTr.eq(4).find('.input-fourth').attr("placeholder","点击选择主题");
        firstTr.eq(4).find('.input-fourth').removeAttr("srcSubjectId");
        firstTr.eq(4).find('.db_input_fourth').attr("disabled","disabled").removeAttr("data-toggle","modal");
        firstTr.eq(5).find('.input-fifth').val('');
        firstTr.eq(5).find('.input-fifth').attr('relCondtionJson','');
        firstTr.eq(6).find('.del_tr').removeAttr('relaId');

        for(var i = $('#filtration_tbody tr').length -1;i > 0 ; i--){
            if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == 0){
                $('#filtration_tbody tr').eq(i).remove();
            }
            if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == 1){
                $('#filtration_tbody tr').eq(i).remove();
            }
        }
    }else if(flag == 1){
        for(var i = $('#filtration_tbody tr').length - 1 ;i > 0 ; i--){
            if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == (a.parents('tr').index()+1)){
                $('#filtration_tbody tr').eq(i).remove();
            }
        }
        a.parents('.clone_tr').remove();
    }
    //清空选择输出字段
    $("#choiceFieldList tbody").html("");
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


function typeRadio(e){
    //if($(".input-fifth").eq(0).val() == undefined || $(".input-fifth").eq(0).val() == ""){
    //    alert("请先设置关联！");
    //    return false;
    //}
    // if(e.parents('.clone_filtration_tr').find('select[name=selectSource]').val() == undefined){
    //     alert("请先设置关联！");
    //     return false;
    // }
    //pub.postJSON('operate.json',
    //    {
    //        action:'saveFilterCondition',
    //        id:e.parents('.clone_filtration_tr').find('.filtration_del').attr('filtartionId'),
    //        subjectId:$('#subjectId').val(),
    //        srcId:e.parents('.clone_filtration_tr').find('select[name=selectSource]').val(),
    //        fieldId:e.parents('.clone_filtration_tr').find('select[name=selectField]').val(),
    //        operationChar:e.parents('.clone_filtration_tr').find('select[name=operator]').val(),
    //        fieldValue:e.parents('.clone_filtration_tr').find('input[type=text]').val(),
    //        logicChar:e.parents('.clone_filtration_tr').find('input[type=radio]:checked').val()
    //    }
    //    ,function(json){
    //        if (json.ok) {
    //            e.parents('.clone_filtration_tr').find('.filtration_del').attr('filtartionId',json.data);
    //        }
    //    });
}
function del(subject_id){
    $.ajax({
        type: "POST",
        url: "operate.json?action=delete",
        data: {subject_id:subject_id},
        dataType: "json",
        success: function(data){
        }
    });
}
function save(){
    var flag = 0;
    if($('#subjectName').val() == ""){
        alert("请填写主题名！");
        return;
    }
    if($('input[name=mainChoice]:checked').val() == undefined){
        alert("请选择关联！");
        return;
    }
    if($('input[name=mainChoice]:checked').val() == 1){
        if($('#tbody tr').eq(0).find('.input-first').attr('placeholder') == "点击选择表页") {
            alert("请选择表！");
            $('#tbody tr').eq(0).find('button[name=relCondition]').removeAttr('data-toggle');
            return;
        }
    }else if($('input[name=mainChoice]:checked').val() == 2){
        if($('#tbody tr').eq(0).find('.input-second').attr('placeholder') == "点击选择主题"){
            alert("请选择主题！");
            $('#tbody tr').eq(0).find('button[name=relCondition]').removeAttr('data-toggle');
            return;
        }
    }
    if($('#tbody tr').eq(0).find('td').eq(0).find('input[type=radio]').is(':checked') && $('#tbody tr').eq(0).find('td').eq(3).find('input[type=radio]').is(':checked') && $('#tbody tr').eq(0).find('td').eq(5).find('input[type=text]').val()==""){
        alert("请填写关联关系！");
        return;
    }
    for(var i = 1; i < $('#tbody tr').length; i++){
        if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]').is(':checked') && $('#tbody tr').eq(i).find('td').eq(4).find('input[type=text]').val()==""){
            alert("请填写关联关系！");
            return;
        }
    }
    $.ajax({
        type: "POST",
        url: "operate.json?action=getCountColsBySubject",
        data: {
            subjectId:$('#subjectId').val()
        },
        dataType: "json",
        async: false,
        success: function(json){
            if(json.ok){
                if(json.data == 0){
                    alert("请选择输出字段！");
                }else flag = 1;
            }else{
                alert("出错了！");
            }
        }
    });
    if(flag == 0){
        return;
    }
    if($('#filtration_tbody tr').eq(1).find('select[name=selectSource]').val() != undefined){
        if($('#filtration_tbody tr').eq(1).find('select[name=selectField]').val() == undefined){
            alert("请填写过滤条件！");
            return;
        }
    }
    for(var i = 2 ; i < $('#filtration_tbody tr').length; i++){
        if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').val() == undefined
            || $('#filtration_tbody tr').eq(i).find('select[name=selectField]').val() == undefined){
            alert("请填写过滤条件！");
            return;
        }
    }

    //保存主题
    $.ajax({
        type: "POST",
        url: "operate.json?action=saveThemes",
        data: {
            subjectId:$('#subjectId').val(),
            subjectClassId:_classId,
            subjectName:$('#subjectName').val(),
            remark:$('#remark').val(),
            createTime:$('#createTime').val()
        },
        dataType: "json",
        async: false,
        success: function(data){
        }
    });

    if($('#filtration_tbody tr').length > 1 && $('#filtration_tbody tr').eq(1).find('select[name=selectSource]').val() != undefined
        && $('#filtration_tbody tr').eq(1).find('select[name=selectField]').val() != undefined){
        var list = [];
        for(var i = 1 ; i < $('#filtration_tbody tr').length; i++){
            var obj = {
                id:$('#filtration_tbody tr').eq(i).find('.filtration_del').attr('filtartionId'),
                subjectId:$('#subjectId').val(),
                srcId:$('#filtration_tbody tr').eq(i).find('select[name=selectSource]').val(),
                fieldId:$('#filtration_tbody tr').eq(i).find('select[name=selectField]').val(),
                operationChar:$('#filtration_tbody tr').eq(i).find('select[name=operator]').val(),
                fieldValue:$('#filtration_tbody tr').eq(i).find('input[type=text]').val(),
                logicChar:$('#filtration_tbody tr').eq(i).find('input[type=radio]:checked').val()
            };
            list.push(obj);
        }
        $.ajax({
            type: "POST",
            url: "operate.json?action=saveFilterCondition",
            data: {
                json:JSON.stringify(list)
            },
            dataType: "json",
            async: false,
            success: function(data){
                if(data.ok){
                }else{
                    alert("保存失败！");
                    return false;
                }
            }
        });
    }
    alert("保存成功！");
    //window.history.back();
    backForData();
}

function saveSubject(){
    if($('#subjectName').val() == ""){
        alert("请填写主题名！");
        return;
    }
    if($('input[name=mainChoice]:checked').val() == undefined){
        alert("请选择关联！");
        return;
    }
    if($('input[name=mainChoice]:checked').val() == 1){
        if($('#tbody tr').eq(0).find('.input-first').attr('placeholder') == "点击选择表页") {
            alert("请选择表！");
            $('#tbody tr').eq(0).find('button[name=relCondition]').removeAttr('data-toggle');
            return;
        }
    }else if($('input[name=mainChoice]:checked').val() == 2){
        if($('#tbody tr').eq(0).find('.input-second').attr('placeholder') == "点击选择主题"){
            alert("请选择主题！");
            $('#tbody tr').eq(0).find('button[name=relCondition]').removeAttr('data-toggle');
            return;
        }
    }
    for(var i = 0; i < $('#tbody tr').length; i++){
        if(i == 0){
            if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]').is(':checked') && $('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]').is(':checked') && $('#tbody tr').eq(i).find('td').eq(5).find('input[type=text]').val()==""){
                alert("请填写关联关系！");
                return;
            }
        }
        if(i > 0){
            if($('#tbody tr').eq(i).find('td').eq(0).find('select[name=choiceSrc2]').val() == ''
                || $('#tbody tr').eq(i).find('td').eq(0).find('select[name=choiceSrc2]').val() == undefined
                || $('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == undefined
                || $('#tbody tr').eq(i).find('td').eq(4).find('input[type=text]').val()==""){
                alert("请填写关联关系！");
                return;
            }
        }
    }
    if($('#filtration_tbody tr').eq(1).find('select[name=selectSource]').val() != undefined){
        if($('#filtration_tbody tr').eq(1).find('select[name=selectField]').val() == undefined
            || $('#filtration_tbody tr').eq(1).find('input[type=text]').val() == ""){
            alert("请填写过滤条件！");
            return;
        }
    }
    for(var i = 2 ; i < $('#filtration_tbody tr').length; i++){
        if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').val() == undefined
                || $('#filtration_tbody tr').eq(i).find('select[name=selectField]').val() == undefined
                || $('#filtration_tbody tr').eq(i).find('input[type=text]').val() == ""){
            alert("请填写过滤条件！");
            return;
        }
    }

    if($('input[name=typeIsChoice]:checked').val() == undefined && $('#filtration_tbody tr').eq(1).find('select[name=selectSource]').val() != undefined){
        alert("请选择关联关系！");
        return;
    }


    if(!($('#choiceFieldList tbody tr').length > 0)){
        alert("请选择输出字段！");
        return;
    }

    //拼接source
    var sourceList = [];
    for(var i = 0 ; i < $('#tbody tr').length; i++){
        var sourceType;
        var srcSchema;
        var srcTable;
        var srcSubjectId;
        if(i == 0){
            if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(i).find('td').find('.input-first').attr('srcSchema');
                srcTable = $('#tbody tr').eq(i).find('td').find('.input-first').attr('placeholder');
                srcSubjectId = '';
            }else if($('#tbody tr').eq(i).find('td').eq(0).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSchema = '';
                srcTable = '';
                srcSubjectId = $('#tbody tr').eq(i).find('td').find('.input-second').attr('srcSubjectId');
            }
            var sourceObj = {
                id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src1Id'),
                subjectId: $('#subjectId').val(),
                sourceType: sourceType,
                srcSchema: srcSchema,
                srcTable: srcTable,
                srcSubjectId: srcSubjectId
            };
            sourceList.push(sourceObj);
            if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(i).find('td').find('.input-third').attr('srcSchema');
                srcTable = $('#tbody tr').eq(i).find('td').find('.input-third').attr('placeholder');
                srcSubjectId = '';
            }else if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSchema = '';
                srcTable = '';
                srcSubjectId = $('#tbody tr').eq(i).find('td').find('.input-fourth').attr('srcSubjectId');
            }
            var sourceObj2 = {
                id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src2Id'),
                subjectId: $('#subjectId').val(),
                sourceType: sourceType,
                srcSchema: srcSchema,
                srcTable: srcTable,
                srcSubjectId: srcSubjectId
            };
            if($('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 1 || $('#tbody tr').eq(i).find('td').eq(3).find('input[type=radio]:checked').val() == 2){
                sourceList.push(sourceObj2);
            }
        }else{
            if($('#tbody tr').eq(i).find('td').eq(0).find('select[name=choiceSrc2]').val() == undefined){
                break;
            }
            if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 1){
                sourceType = 1;
                srcSchema = $('#tbody tr').eq(i).find('td').find('.input-third').attr('srcSchema');
                srcTable = $('#tbody tr').eq(i).find('td').find('.input-third').attr('placeholder');
                srcSubjectId = '';
            }else if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() == 2){
                sourceType = 2;
                srcSchema = '';
                srcTable = '';
                srcSubjectId = $('#tbody tr').eq(i).find('td').find('.input-fourth').attr('srcSubjectId');
            }
            var sourceObj = {
                id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src2Id'),
                subjectId: $('#subjectId').val(),
                sourceType: sourceType,
                srcSchema: srcSchema,
                srcTable: srcTable,
                srcSubjectId: srcSubjectId
            };
            if($('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() ==1 ||
                $('#tbody tr').eq(i).find('td').eq(2).find('input[type=radio]:checked').val() ==2) {
                sourceList.push(sourceObj);
            }
        }
    }
    //alert(JSON.stringify(sourceList));

    var relaList = [];
    for(var i = 0 ; i < $('#tbody tr').length; i++){
        if(i == 0){
            var id = $('#tbody tr').eq(i).find('td').eq(6).find('.del_tr').attr('relaId');
            if(id == undefined){
                id = '';
            }
            var relaObj = {
                id:id,
                subjectId:$('#subjectId').val(),
                src1Id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src1Id'),
                source1Id:$('#tbody tr').eq(i).index(),
                relType:$('#tbody tr').eq(i).find('select[name=associationMode]').val(),
                src2Id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src2Id'),
                source2Id:$('#tbody tr').eq(i).index() + 1,
                relCondtion:$('#tbody tr').eq(i).find('td').eq(5).find('input[type=text]').val(),
                relCondtionJson:$('#tbody tr').eq(i).find('td').eq(5).find('input[type=text]').attr('relCondtionJson')
            };
            relaList.push(relaObj);
        }else{
            if($('#tbody tr').eq(i).find('td').eq(0).find('select[name=choiceSrc2]').val() == undefined){
                break;
            }
            var id = $('#tbody tr').eq(i).find('td').eq(5).find('.del_tr').attr('relaId');
            if(id == undefined){
                id = '';
            }
            var relaObj = {
                id:id,
                subjectId:$('#subjectId').val(),
                src1Id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src1Id'),
                source1Id:$('#tbody tr').eq(i).find('td').eq(0).find('select[name=choiceSrc2]').find('option:selected').index(),
                relType:$('#tbody tr').eq(i).find('select[name=associationMode]').val(),
                src2Id:$('#tbody tr').eq(i).find('select[name=associationMode]').attr('src2Id'),
                source2Id:$('#tbody tr').eq(i).index() + 1,
                relCondtion:$('#tbody tr').eq(i).find('td').eq(4).find('input[type=text]').val(),
                relCondtionJson:$('#tbody tr').eq(i).find('td').eq(4).find('input[type=text]').attr('relCondtionJson')
            };
            relaList.push(relaObj);
        }

    }
    //alert(JSON.stringify(relaList));

    var filtrationList = [];
    var logicChar = $('input[name=typeIsChoice]:checked').val();
    if($('#filtration_tbody tr').length > 1 && $('#filtration_tbody tr').eq(1).find('select[name=selectSource]').val() != undefined
        && $('#filtration_tbody tr').eq(1).find('select[name=selectField]').val() != undefined) {
        for (var i = 1; i < $('#filtration_tbody tr').length; i++) {
            var srcType = '';
            if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == 0){
                if($('#tbody tr').eq(0).find('input[order=radio_1]:checked').val() == 1){
                    srcType = 1;
                }
                if($('#tbody tr').eq(0).find('input[order=radio_1]:checked').val() == 2 ){
                    srcType = 2;
                }
            }else if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index() == 1){
                if($('#tbody tr').eq(0).find('input[order=radio_2]:checked').val() == 1 ){
                    srcType = 1;
                }
                if($('#tbody tr').eq(0).find('input[order=radio_2]:checked').val() == 2 ){
                    srcType = 2;
                }
            }else{
                if($('#tbody tr').eq($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index()-1).find('input[order=radio_2]:checked').val() == 1 ){
                    srcType = 1;
                }
                if($('#tbody tr').eq($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index()-1).find('input[order=radio_2]:checked').val() == 2 ){
                    srcType = 2;
                }
            }
            var srcId = '';
            if($('#filtration_tbody tr').eq(i).find('select[name=selectSource]').val() != undefined){
                srcId = $('#filtration_tbody tr').eq(i).find('select[name=selectSource]').val();
            }
            var fieldId = '';
            if($('#filtration_tbody tr').eq(i).find('select[name=selectField]').val() != undefined){
                fieldId = $('#filtration_tbody tr').eq(i).find('select[name=selectField]').val();
            }
            var filtrationObj = {
                id: $('#filtration_tbody tr').eq(i).find('.filtration_del').attr('filtartionId'),
                subjectId: $('#subjectId').val(),
                srcType:srcType,
                srcId: srcId,
                sourceIndex:$('#filtration_tbody tr').eq(i).find('select[name=selectSource]').find('option:selected').index(),
                fieldId: fieldId,
                operationChar: $('#filtration_tbody tr').eq(i).find('select[name=operator]').val(),
                fieldValue: $('#filtration_tbody tr').eq(i).find('input[type=text]').val(),
                logicChar: logicChar
            };
            filtrationList.push(filtrationObj);
        }
    }
    //alert(JSON.stringify(filtrationList));

    var colsString='';
    for(var i = 1 ; i < $('#choiceFieldList tr').length; i++) {
        var srcId = '';
        if($('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('srcId') != undefined
            && $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('srcId') != 'null' ){
            srcId = $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('srcId');
        }
        colsString += $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('id')  + ':'
            + srcId + ':'
            + $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('fieldId') + ':'
            + $('#choiceFieldList tr').eq(i).find('td').eq(0).find('input[name=rightChoiceField]').attr('sourceIndex') + ':'
            + $('#choiceFieldList tr').eq(i).find('td').eq(2).find('input[type=text]').val() + ',';
    }


    pub.postJSON('operate.json',{
        action:'saveSubject',
        subjectId:$('#subjectId').val(),
        subjectClassId:_classId,
        subjectName:$('#subjectName').val(),
        remark:$('#remark').val(),
        createTime:$('#createTime').val(),

        sourceList:JSON.stringify(sourceList),
        relaList:JSON.stringify(relaList),
        filtrationList:JSON.stringify(filtrationList),
        colsString:colsString

    },function(json){
        if (json.ok) {
            alert("保存成功！");
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