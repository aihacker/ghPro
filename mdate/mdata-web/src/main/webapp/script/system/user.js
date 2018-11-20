/**
 * Created by Administrator on 2017-05-12.
 */
function drawTree(datas){
    //var zNodes =[
    //    { id:1, pId:0, name:"公司领导", open:true},
    //    { id:100, pId:1, name:"区公司领导", open:true},
    //    { id:101, pId:1, name:"大客户经理",open:true},
    //    { id:102, pId:1, name:"业务经理",open:true},
    //    { id:103, pId:1, name:"业务员",open:true},
    //    {"id":21,"pId":100,"name":"首页",nocheck:true},
    //    {"id":22,"pId":100,"name":"系统配置",nocheck:true},
    //    {"id":23,"pId":100,"name":"元数管理",nocheck:true},
    //    {"id":24,"pId":100,"name":"数据同步",nocheck:true},
    //    {"id":25,"pId":100,"name":"数据操纵平台",nocheck:true},
    //    {"id":26,"pId":100,"name":"监控管理",nocheck:true},
    //    {"id":21,"pId":101,"name":"首页",nocheck:true},
    //    {"id":22,"pId":101,"name":"系统配置",nocheck:true},
    //    {"id":23,"pId":101,"name":"元数管理",nocheck:true},
    //    {"id":24,"pId":101,"name":"数据同步",nocheck:true},
    //    {"id":25,"pId":101,"name":"数据操纵平台",nocheck:true},
    //    {"id":26,"pId":101,"name":"监控管理",nocheck:true},
    //    {"id":21,"pId":102,"name":"首页",nocheck:true},
    //    {"id":22,"pId":103,"name":"系统配置",nocheck:true},
    //    {"id":23,"pId":103,"name":"元数管理",nocheck:true},
    //    {"id":24,"pId":103,"name":"数据同步",nocheck:true},
    //    {"id":25,"pId":103,"name":"数据操纵平台",nocheck:true},
    //    {"id":26,"pId":103,"name":"监控管理",nocheck:true},
    //];
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
                onClick: function(e, treeId, treeNode, clickFlag) {
                    zTree.checkNode(treeNode, !treeNode.checked, true);
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#treeSelect"), setting,eval(datas));
}
//提交选中节点
function onCheck(){
    var treeObj=$.fn.zTree.getZTreeObj("treeSelect");
    var nodes=treeObj.getCheckedNodes(true);
    var ids = [];
    var html="";
    for(var i=0;i<nodes.length;i++){
        ids.push(nodes[i].id);
        html+='<input type="hidden" name="roles" value="'+nodes[i].id+'" >';
    }
    console.log("----"+ids);
    $("#hiddenRoles").html(html);
}

function ajaxSubmit(rid,idstr){
    console.log("base______"+$("baseInfo").serialize());
    $.ajax({
        dataType: 'json',
        url: 'operate.json?action=save',
        data: $("baseInfo").serialize(),
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

