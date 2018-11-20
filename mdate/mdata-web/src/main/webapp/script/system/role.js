function save(id) {
    var tables = [];
    var name = "";
    var authority = "";
    var schemaname = "";
    var tag=1;
    $("#tableIndex input:checkbox[name=table]:checked").each(function (i) {
        name = $(this).val();
        schemaname = $(this).parents("tr").find("td").eq(1).text();
        var ipts = $(this).parents("tr").find("input:radio:checked");
        authority = ipts.map(function () {
            return $(this).val();
        }).get();
        if (typeof(authority[0]) == "undefined") {

            tag=0;
            return;
        }
        var table = {};
        table['tablename'] = name;
        table['authority'] = authority[0];
        table["schema"] = schemaname;
        tables.push(table);
    });
    if(tag==0){
        alert("请勾选权限");
        return ;
    }
    if (tables.length == 0) {
        alert("请选择表");
        return;
    }
    var temp = JSON.stringify(tables);
    $.ajax({
        dataType: 'json',
        url: 'config.json',
        data: {action: "cfg", tables: temp, roleId: id},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                alert("保存成功");
                cfg(id);
            }
        },
        error: function () {
            alert('form submit error..')
        }
    });
}

function cfg(id) {
    var operateUri = 'config.html';
    loadUrl(operateUri + '?roleId=' + id);
}
function formTree(id) {
    $.ajax({
        dataType: 'json',
        url: 'query.json',
        data: {action: 'getModule', roleId: id},
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var datas = result.data;
                var tree = datas['tree'];
                var roleId = datas['roleId'];
                $("#roleId").val(id);
                console.log("-----" + roleId);
                drawTree(tree);
                $('#myModal').modal();
                /*$('#submit').bind("click", function () {
                    onCheck(roleId);
                });*/
            }

        },
        error: function () {
            $("#errorMsg").html("数据加载出错");
        }
    });
}
function drawTree(datas) {
    var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: function (e, treeId, treeNode, clickFlag) {
                    zTree.checkNode(treeNode, !treeNode.checked, true);
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#treeSelect"), setting, eval(datas));
}

//获取选中节点
function onCheck() {
    var rid = $("#roleId").val();
    var treeObj = $.fn.zTree.getZTreeObj("treeSelect");
    var nodes = treeObj.getCheckedNodes(true);
    var ids = [];
    var v = [];
    for (var i = 0; i < nodes.length; i++) {
        ids.push(nodes[i].id);
        v.push(nodes[i].value);
    }
    ajaxSubmit(rid, ids);
}
function ajaxSubmit(rid, ids) {
    //var json={"roleId":roleId,"ids":ids};
    var datas = {};
    datas['rid'] = rid;
    datas['ids'] = ids;
    $.ajax({
        dataType: 'json',
        url: 'query.json',
        data: {action: 'saveTree', datas: JSON.stringify(datas)},
        traditional: true,
        success: function (result) {
            if (result.ok) {
            }
        },
        error: function () {
            $("#errorMsg").html("数据加载出错");
        }
    });
}