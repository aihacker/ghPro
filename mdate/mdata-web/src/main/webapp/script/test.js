$(document).ready(function(){
    $("#DBbtn").click(function(){
        console.log("db");
        var port=$("#port").val();
        var ip=$("#ip").val();
        var db_name=$("#db_name").val();
        var username=$("#username").val();
        var password=$("#password").val();
        var db_type=$("#db_type").val();
        if(port==''||ip==''||db_name==''||username==''||password==''||db_type==''){
            alert("请检查各参数是否为空！");
            return false;
        }
        $.ajax({
            dataType: 'text',
            url: 'show.json',
            data:$("#baseInfo").serialize() ,
            traditional: true,
            success: function (result) {
                var result= jQuery.parseJSON(result);
                if (result.ok) {
                    alert(result.data)
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    });

    $("#FTPbtn").click(function(){
        var port=$("#port").val();
        var ip=$("#ip").val();
        var username=$("#username").val();
        var password=$("#password").val();
        if(port==''||ip==''||username==''||password==''){
            alert("请检查各参数是否为空！");
            return false;
        }
        $.ajax({
            dataType: 'text',
            url: 'show.json',
            data:$("#baseInfo").serialize() ,
            traditional: true,
            success: function (result) {
                var result= jQuery.parseJSON(result);
                if (result.ok) {
                    alert(result.data)
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    });
});
//check回显
function showCheck(id){
    if($("#"+id).val()==0){
        $("#"+id).attr("checked",true);
    }else{
        $("#"+id).attr("checked",false);
    }
}