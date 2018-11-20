/**
 * Created by Administrator on 2017-06-02.
 */
function execute() {
    var text = $("#text").val();
    if (text == "" || text == null) {
        alert("内容不能为空");
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'show', sql: text},
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

function reRun(id, type) {
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {action: 'reRun', id: id, type: type},
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

//批量执行
function batchRun() {
    var ids = [];
    $('input[name="selectbody"]:checked').each(function () {
        var data = $(this).val();
        var id;
        if(null != data && data != ""){
            id = data.split(",")[0];
        }
        ids.push(id);
    });
    if (ids.length == 0) {
        alert("至少选择一条数据");
        return;
    }
    if (!window.confirm('确定执行?')) {
        return;
    }
    var data = {
        action: "batchRun",
        ids: ids
    };
    pub.getJSON(operateUrl, data, function (result) {
        if (result.ok) {
            alert(result.msg);
            location.reload();
        }
    });
}

//任务失效
function invalidClick() {
    var ids = [];
    $('input[name="selectbody"]:checked').each(function () {
        ids.push($(this).val());
    });
    if (ids.length == 0) {
        alert("至少选择一条数据");
        return;
    }
    if (!window.confirm('确定设置为失效?')) {
        return;
    }
    var data = {
        action: "invalid",
        ids: ids
    };
    pub.getJSON(operateUrl, data, function () {
        alert("设置成功！");
        location.reload();
    });
}

//任务生效
function validClick() {
    var ids = [];
    $('input[name="selectbody"]:checked').each(function () {
        ids.push($(this).val());
    });
    if (ids.length == 0) {
        alert("至少选择一条数据");
        return;
    }
    if (!window.confirm('确定设置为生效?')) {
        return;
    }
    var data = {
        action: "valid",
        ids: ids
    };
    pub.getJSON(operateUrl, data, function () {
        alert("设置成功！");
        location.reload();
    })
}

//任务复制
function copy() {
    var ids = [];
    $('input[name="selectbody"]:checked').each(function () {
        var data = $(this).val();
        var id;
        if (null != data && data != "") {
            id = data.split(",")[0];
        }
        ids.push(id);
    });
    if (ids.length == 0) {
        alert("至少选择一条数据");
        return;
    }
    if (!window.confirm('确定复制任务?')) {
        return;
    }
    var data = {
        action: "copy",
        ids: ids
    };
    pub.getJSON(operateUrl, data, function () {
        //alert("设置成功！");
        location.reload();
    });
}

//全选、取消全选的事件
function selectAll() {
    var checked = $("#selectAll").attr("checked");
    if (checked == undefined) {
        $("#selectAll").attr("checked", true);
        $("input[name='selectbody']").attr("checked", true);
    } else {
        $("#selectAll").attr("checked", false);
        $("input[name='selectbody']").attr("checked", false);
    }
}