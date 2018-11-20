/**
 * Created by Administrator on 2017-05-15.
 */

//复选框事件
//全选、取消全选的事件
function selectAll(){
    var checked=$("#selectAll").attr("checked");
    if (checked==undefined) {
        $("#selectAll").attr("checked",true);
        $("input[name='selectbody']").attr("checked", true);
    } else {
        $("#selectAll").attr("checked",false);
        $("input[name='selectbody']").attr("checked", false);
    }
}

function add(id) {
    $.ajax({
        dataType: 'json',
        url: 'query.json',
        data: {action: "query", id: id},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                alert(result.msg);
            }
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

function batChadd() {
    var ids =[];
    $('input[name="selectbody"]:checked').each(function(){
        ids.push($(this).val());
    });
    if(ids.length==0){
        alert("请至少选择一条数据");
        return ;
    }
    $.ajax({
        dataType: 'json',
        url: 'query.json',
        data: {action: "batchAdd", ids: ids},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                alert(result.msg);
            }
        },
        error: function () {
            alert('form submit error..')
        }
    });
}