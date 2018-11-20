/**
 * Created by Administrator on 2017-05-15.
 */
$(document).ready(function(){
    $('#tableName').change(function(e, v) {
        //$('#').html('你选择了第' + e.target.selectedIndex + '项，值是' +e.target.value );
        $.ajax({
            dataType: 'json',
            url: 'show.json',
            data: {action: 'queryCloum', tId:e.target.value},
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
    $('#fieldName').change(function() {
        var map={};
        $("#fieldName option").each(function(){  //遍历所有option
            var txt = $(this).val();
            var key= $(this).attr("key");//获取option值
            map[txt]=key;
        });
        var val=$("#fieldName").val();
        for(var key in map){
            if(val==key){
                $("#fieldType").val(map[key]);
            }
      }
});

});


//check回显
function showCheck(id){
    if($("#"+id).val()==1){
        $("#"+id).attr("checked",true);
    }else{
        $("#"+id).attr("checked",false);
    }
}
