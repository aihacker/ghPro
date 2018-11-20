/**
 * Created by jyf on 2017/4/1.
 */

//图标点击事件分发
function show_model(myself) {

    var icon = myself.attr('id');
    var componentType = myself.attr('component_type');
    var componentId = myself.attr('component_id');
    var modelTip2 = $('#modelTip2');
    switch (componentType) {
        case '101':
            model_101(modelTip2, componentType, componentId, icon, myself);
            break;
        case '201':
            model_201(modelTip2, componentType, componentId, icon, myself);
            break;
        case '301':
            model_301(modelTip2, componentType, componentId, icon, myself);
            break;
        default:
            return;
            break;
    }

    modelTip2.attr('icon', icon).show();
}
function getTable(modelTip2,patternName,tableName,table){
    pub.postJSON(json_url,{action:'getTableNameByName',patternName:patternName,tableName:tableName},function(json){
        var data = json.data;
        if(data == null){
            data = [];
        }
        var $table = modelTip2.find('.pattern-right-content tbody');
        $table.empty();
        for(var n=0;n<data.length;n++){
            var checked = '';
            if(data[n]==table){
                checked = 'checked="true"';
            }
            var html = $('<tr>' +
            '<td><input type="radio" '+checked+' name="table"></td>' +
            '<td>'+data[n]+'</td>' +
            '</tr>');
            html.appendTo($table);
        }
        $table.find('td input').change(function(){
            var text = $(this).parents('td').siblings('td').text();
            $('#tableName').val(text);
        });
    })
}

function getTableBySchema(model,schema,tableName){
    pub.postJSON(json_url,{action:'getTableNameByName',patternName:schema},function(json){
        var data = json.data;
        var $tarTable = $('#tarTable');
        for(var n=0;n<data.length;n++){
            var info = data[n];
            var html = $('<option>'+info+'</option>');
            html.appendTo(model);
        }
        if(tableName!=undefined){
            model.val(tableName);
        }else{
            $tarTable.val(data[0]);
        }
        model.unbind('change').change(function(){
            var text = $(this).val();
            $tarTable.val(text);
        });
    });
}
//源表
function model_101(modelTip2, componentType, componentId, icon, myself) {
    var html = $('#modelIndex').contents().find('#model' + componentType).html();
    modelTip2.find('.tip').empty().append(html);
    modelTip2.attr('component_id', componentId);

    pub.postJson(json_url,{action:'getPattern',componentId:componentId},function(json){
        var data = json.data;
        var $pattern = modelTip2.find('#pattern');
        $pattern.empty();
        var $checked = '';
        var msg = {};
        if(json.msg!=null){
            msg = JSON.parse(json.msg);
            //表名
            getTable(modelTip2,msg.srcSchema,undefined,msg.srcTable);
            //
            if(msg.isDynTablename==1||msg.isDynTablename =='1'){
                modelTip2.find('#isChecked').attr('checked',true);
            }
            modelTip2.find('#tableName').val(msg.srcTable);
            modelTip2.find('#srcTablePrefix').val(msg.srcTablePrefix);
            modelTip2.find('input[name="reRunSuffix"]').val(msg.reRunSuffix);
            if(msg.byMonOrByDay=='1'){
                modelTip2.find('input[name="byMonOrByDay1"]').attr('checked',true);
                modelTip2.find('input[name="suffixFormat1"]').val(msg.suffixFormat);
                modelTip2.find('input[name="monOrDayAgo1"]').val(msg.monOrDayAgo);
            }else{
                modelTip2.find('input[name="byMonOrByDay2"]').attr('checked',true);
                modelTip2.find('input[name="suffixFormat2"]').val(msg.suffixFormat);
                modelTip2.find('input[name="monOrDayAgo2"]').val(msg.monOrDayAgo);
            }
        }

        for(var n=0;n<data.length;n++){
            var html;
            if(json.msg!=null&&$checked==''){

                if(msg.srcSchema==data[n]){
                    $checked = 'class="checked"';
                }

                html = $('<li '+$checked+' >'+data[n]+'</li>');
            }else{
                html = $('<li>'+data[n]+'</li>');
            }
            html.appendTo($pattern);
        }


        $pattern.find('li').click(function(){
            var patternName = $(this).text();
            if(patternName==''){
                alert("选择的模式有误");
                return;
            }
            $(this).attr('class','checked').siblings().removeClass();
            getTable(modelTip2,patternName);
        });

    });
    modelTip2.find('#patternSelect').unbind('click').click(function(){
        var patternName = modelTip2.find("#pattern .checked").text();
        if(patternName==''){
            alert("模式名不能为空");
            return;
        }
        var tableName = modelTip2.find('input[name="patternSelect"]').val();
        getTable(modelTip2,patternName,tableName);
    });
    modelTip2.find('.model_sure').unbind('click').click(function () {

        var id = modelTip2.attr('component_id');
        var srcSchema = modelTip2.find("#pattern .checked").text();
        if(srcSchema==''){
            alert("模式名不能为空");
            return;
        }
        var srcTable = modelTip2.find("#tableName").val();
        if(srcTable==''){
            alert("表名不能为空");
        }
        var isDynTablename = 0;
        if(modelTip2.find('#isChecked').is(':checked')){
            isDynTablename = 1;
        }
        var srcTablePrefix = modelTip2.find("#srcTablePrefix").val();

        var byMonOrByDay = 1;
        if(modelTip2.find('input[name="byMonOrByDay2"]').is(':checked')){
            byMonOrByDay = 2;
        }
        var suffixFormat ='';
        var monOrDayAgo ='';
        if(byMonOrByDay==undefined){
            console.log("动态表后缀不能为空");
        }else if(byMonOrByDay=='1'){
            suffixFormat = modelTip2.find('input[name="suffixFormat1"]').val();
            monOrDayAgo = modelTip2.find('input[name="monOrDayAgo1"]').val();
        }else{
            suffixFormat = modelTip2.find('input[name="suffixFormat2"]').val();
            monOrDayAgo = modelTip2.find('input[name="monOrDayAgo2"]').val();
        }
        if(isNaN(byMonOrByDay)){
            alert("值不能是字符类型");
            return;
        }
        var reRunSuffix = modelTip2.find('input[name="reRunSuffix"]').val();

        var data = {action:'savePattern',
            id:id,
            taskId:flowId,
            srcSchema:srcSchema,
            srcTable:srcTable,
            isDynTablename:isDynTablename,
            srcTablePrefix:srcTablePrefix,
            suffixFormat:suffixFormat,
            reRunSuffix: reRunSuffix,
            byMonOrByDay:byMonOrByDay,
            monOrDayAgo:monOrDayAgo
        };
        console.log(data);


        pub.postJSON(json_url,data,function(json){
            var data = {
                component_id:json.data,
                table_schema:srcSchema+'&'+srcTable
            };
            $iframe.update_icon_list(icon,data);
            modelTip2.attr('component_id', componentId);
            modelTip2.hide();
        });
    });

    modelTip2.find('.model_cancel').unbind('click').click(function () {
        modelTip2.hide();
    });

}

//操作
function model_201(modelTip2, componentType, componentId, icon, myself) {
    var html = $('#modelIndex').contents().find('#model' + componentType).html();
    modelTip2.find('.tip').empty().append(html);
    var icon1 = icon.replace('window', '');
    //获取源表
    var data = $iframe.save();
    var S = getDateSource(icon1, data);
    var tableAndSchemas = [];
    componentId = flowId;

    S.forEach(function (item) {
        var Icon = $iframe.getICON(item).attr("table_schema");
        if(Icon != undefined){
            tableAndSchemas.push(Icon);
        }
    });

    pub.postJSON(json_url,{action:'getOperationData',componentId:componentId,tableAndSchemas:tableAndSchemas},function(json){
        var data = json.data;
        if(json.msg!=null){
            modelTip2.find('#SqlText').val(json.msg);
        }
        var $ul = modelTip2.find('.operation-model');
        $ul.empty();
        for(var n=0;n<data.length;n++){
            var info = data[n];
            var schemas = '';
            var table = '';
            if(tableAndSchemas[n]!=undefined){
                var index = tableAndSchemas[n].indexOf('&');
                schemas = tableAndSchemas[n].substring(0,index);
                table = tableAndSchemas[n].substring(index+1,tableAndSchemas[n].length);
            }
            var $tbody = '';
            for(var i=0;i<info.name.length;i++){
                var name = info.name[i];
                var type = info.dataType[i];
                $tbody += '<tr>' +
                '<td>' +
                '<input type="checkbox" class="operation-checked" name="operation-checked">' +
                '</td>' +
                '<td>' +name+'</td>' +
                '<td>' +type+'</td>' +
                '</tr>';
            }
            var html_title = $('<div class="form-inline">' +
            '<div class="form-group"><label>表名</label>' +
            '<input type="text" class="form-control tableName" value="'+table+'" disabled="disabled">' +
            '</div>' +
            '<div class="form-group">' +
            '<label >模式</label>' +
            '<input type="text" class="form-control patternName" value="'+schemas+'" disabled="disabled">' +
            '</div>' +
            '<div class="form-group">' +
            '<label >全选</label>' +
            '<input type="checkbox" class="operation-All operation-checked">' +
            '</div>' +
            '</div>');


            var html = $('<li>'+
            '<div class="table-weapper" style="margin-top: 20px; overflow-y: scroll;height: 200px  ">' +
            '<table class="table table-bordered" >' +
            '<thead>' +
            '<tr>' +
            '<th>选择</th>' +
            '<th>字段名</th>' +
            '<th>字段类型</th>' +
            '</tr>' +
            '</thead>' +
            '<tbody>'+$tbody+
            '</tbody>' +
            '</table>' +
            '</div>' +
            '</li>');
            html_title.appendTo($('.ptitle'));
            $('.ptitle').find('.form-inline').hide().eq(0).show();
            html.appendTo($ul);
        }
        var $li = modelTip2.find('.operation-model li');
        $li.hide().eq(0).show();

        //选择字段
        modelTip2.find('.operation-checked').unbind('change').change(function(){
            var $ul = $('.operation-model');
            var $li = $ul.find('li');
            var select = '';
            for(var n=0;n<$li.length;n++){
                var info = $li.eq(n);
                var info2 = $('.form-inline');
                var tableName = info2.find('.tableName').val();
                var patternName = info2.find('.patternName').val();
                var allChecked = info2.eq(n).find('.operation-All');
                if(allChecked.is(':checked')){
                    select += tableName+'.*,';
                }else{
                    var checked = info.find('input[name="operation-checked"]:checked');
                    for(var i=0;i<checked.length;i++){
                        var filed = checked.eq(i).parents('tr').find('td:nth-child(2)').text().trim();
                        select = select.trim() + (tableName+'.'+filed+',');
                    }
                }
            }
            var index = select.lastIndexOf(',');
            if(index+1==select.length){
                select = select.substring(0,index);
            }

            //console.log(select);
            var from = modelTip2.find('#SqlData .from').val();

            modelTip2.find('#SqlText').val('select '+select+' '+from);


        });


    });

    //上下表
    modelTip2.find('.pager a').unbind('click').click(function(){

        var $li = modelTip2.find('.operation-model li');
        var $input = modelTip2.find('.pager input');
        var $pager = $input.val();
        var newP = 0;
        var inline = $('.ptitle').find('.form-inline');
        if($(this).attr('class')=='go-up'){
            if($pager==0){
                return;
            }
            newP = Number($pager)-1;
            console.log(newP);
            $input.val(newP);
            $li.hide().eq(newP).show();
            inline.hide().eq(newP).show();
        }else{
            newP = Number($pager)+1;
            console.log(newP);
            if($li.length<=newP){
                return;
            }
            $li.hide().eq(newP).show();
            $input.val(newP);
            inline.hide().eq(newP).show();
        }

    });

    //
    modelTip2.find('#SqlText').unbind('propertychange input').bind('propertychange input',function(){
        var text = $(this).val();
        var from = text.indexOf(' from');
        if(from!=-1){
            var nText = text.substring(from,text.length);
            modelTip2.find('#SqlData .from').val(nText);
            //console.log(nText);
        }
    });

    modelTip2.find('#testSqlText').unbind('click').click(function(){
        var sqlText = modelTip2.find('#SqlText').val();
        if(sqlText == ''){
            alert("SQL语句不能为空");
            return;
        }
        pub.postJSON(json_url,{action:'testSqlText',sqlText:sqlText},function(json){
            if(json.ok){
                alert("测试成功");
            }else{
                alert("测试失败");
            }
        });
    });

    modelTip2.find('.model_sure').unbind('click').click(function () {
        var sqlText = modelTip2.find('#SqlText').val();
        var data = {action:'saveOperationData',sqlText:sqlText,componentId:flowId};
        pub.postJSON(json_url,data,function(json){
            modelTip2.hide();
        });
    });

    modelTip2.find('.model_cancel').unbind('click').click(function () {
        modelTip2.hide();
    });

}

//输出
function model_301(modelTip2, componentType, componentId, icon, myself) {
    var html = $('#modelIndex').contents().find('#model' + componentType).html();
    modelTip2.find('.tip').empty().append(html);
    modelTip2.attr('component_id', componentId);

    pub.postJSON(json_url,{action:'getPatternAndFields',taskId:flowId,componentId:componentId},function(json){
        var data = json.data;
        var schema = data.schemas;
        var fields = data.dataDescribes;
        var outputSelect = modelTip2.find('.outputSelect');
        outputSelect.empty();
        modelTip2.find('input[name="reRunSuffix"]').val(data.reRunSuffix);
        for(var n=0;n<schema.length;n++){
            if(n==0){
                modelTip2.find('#targetSchema').val(schema[0]);
            }
            var info = schema[n];
            var html = $('<option>'+info+'</option>');

            html.appendTo(outputSelect);
        }

        var $tbody = modelTip2.find('table tbody');
        $tbody.empty();
        for(var n=0;n<fields.length;n++){
            var info = fields[n];
            var html = $('<tr>' +
            '<td>'+info.columnName+'</td>' +
            '<td>'+info.dataType+'</td>' +
            '<td>'+info.tableSchema+'</td>' +
            '<td>'+info.tableName+'</td>' +
            '</tr>');

            html.appendTo($tbody);
        }

        var $tarTable = modelTip2.find('.tarTable');
        if(data.id!=null){
            modelTip2.find('#targetSchema').val(data.tarSchema);
            outputSelect.val(data.tarSchema);

            modelTip2.find('#tarTable').val(data.tarTable);
            getTableBySchema($tarTable,data.tarSchema,data.tarTable);

            var $saveType = modelTip2.find('input[name="saveType"]');
            if(data.saveType==1){ //1先删再增加
                $saveType.eq(1).attr('checked',true);
            }else{
                $saveType.eq(2).attr('checked',true);
            }

            var $isDynTablename = modelTip2.find('input[name="isDynTablename"]');
            if(data.isDynTablename==0){
                $isDynTablename.eq(0).attr('checked',true);
            }else{
                $isDynTablename.eq(1).attr('checked',true);
            }

            modelTip2.find('#tarTablePrefix').val(data.tarTablePrefix);

            var $byMonOrByDay = modelTip2.find('input[name="byMonOrByDay"]');
            if(data.byMonOrByDay==1){
                $byMonOrByDay.eq(0).attr('checked',true);
                modelTip2.find('input[name="suffixFormat1"]').val(data.suffixFormat);
                modelTip2.find('input[name="monOrDayAgo1"]').val(data.monOrDayAgo);
            }else{
                $byMonOrByDay.eq(1).attr('checked',true);
                modelTip2.find('input[name="suffixFormat2"]').val(data.suffixFormat);
                modelTip2.find('input[name="monOrDayAgo2"]').val(data.monOrDayAgo);
            }
        }else{
            if(schema[0]!=undefined){
                getTableBySchema($tarTable,schema[0]);
            }
        }

        outputSelect.unbind('change').change(function(){
            var text = $(this).val();
            modelTip2.find('#targetSchema').val(text);
            getTableBySchema($tarTable,text);
        });

    });

    modelTip2.find('.model_sure').unbind('click').click(function () {
        var id = modelTip2.attr('component_id');
        var tarSchema = modelTip2.find('#targetSchema').val();
        var saveType = modelTip2.find('input[name="saveType"]:checked').val();
        var isDynTablename = modelTip2.find('input[name="isDynTablename"]:checked').val();
        var tarTable = modelTip2.find('#tarTable').val();
        var tarTablePrefix = modelTip2.find('#tarTablePrefix').val();

        var byMonOrByDay = modelTip2.find('input[name="byMonOrByDay"]:checked').val();

        var suffixFormat ='';
        var monOrDayAgo ='';

        if(byMonOrByDay==undefined){
            console.log("动态表后缀不能为空");
        }else if(byMonOrByDay=='1'){
            suffixFormat = modelTip2.find('input[name="suffixFormat1"]').val();
            monOrDayAgo = modelTip2.find('input[name="monOrDayAgo1"]').val();
        }else{
            suffixFormat = modelTip2.find('input[name="suffixFormat2"]').val();
            monOrDayAgo = modelTip2.find('input[name="monOrDayAgo2"]').val();
        }
        var reRunSuffix = modelTip2.find('input[name="reRunSuffix"]').val();
        console.log(reRunSuffix);
        var data = {
            action:'saveTarget',
            id:componentId,
            taskId:flowId,
            tarSchema:tarSchema,
            tarTable:tarTable,
            saveType:saveType,
            isDynTablename:isDynTablename,
            tarTablePrefix:tarTablePrefix,
            suffixFormat:suffixFormat,
            reRunSuffix:reRunSuffix,
            byMonOrByDay:byMonOrByDay,
            monOrDayAgo:monOrDayAgo
        };

        pub.postJSON(json_url,data,function(json){
            $iframe.update_icon_componentId(icon,json.data);
            modelTip2.hide();
        });

    });

    modelTip2.find('.model_cancel').unbind('click').click(function () {
        modelTip2.hide();
    });

}



