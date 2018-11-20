/**
 * Created by Administrator on 2017-05-15.
 */
$(document).ready(function(){
    $('#schemaName').change(function(e, v) {
        //$('#').html('你选择了第' + e.target.selectedIndex + '项，值是' +e.target.value );
        $.ajax({
            dataType: 'json',
            url: 'show.json',
            data: {action: 'queryTable', schema:e.target.value},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    var html = "<option value=''>请选择</option>";
                    var datas = result.data;
                    for (var i = 0; i < datas.length; i++) {
                        html += '<option value="' + datas[i] + '">' + datas[i] + ' </option>'
                    }
                    $("#tableName").html(html);
                    $('#tableName').comboSelect();

                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    });

    $('#tableName').change(function(e, v) {
        //$('#').html('你选择了第' + e.target.selectedIndex + '项，值是' +e.target.value );
        var schemaName=$("#schemaName option:selected").val();
        console.log("table-------"+schemaName);
        $.ajax({
            dataType: 'json',
            url: 'show.json',
            data: {action: 'queryColumn', tableName:e.target.value,schema:schemaName},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    var html = "<option value=''>请选择</option>";
                    var datas = result.data;
                    for (var i = 0; i < datas.length; i++) {
                        html += '<option value="' + datas[i] + '">' + datas[i] + ' </option>'
                    }
                    $("#fieldName").html(html);
                    $('#fieldName').comboSelect();

                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    });
});

function save(){

}
