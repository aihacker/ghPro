//合同类型目录结构
var setting ={
    data:{
        simpleData:{
            enable:true
        }
    },
    check:{
        enable:true,
        chkStyle:"radio",
        radioType:"all"
    },
    view:{
      showLine:true
    },
    callback:{//回调函数,
        onClick: onClick,//节点点击事件
        onCheck: onCheck,//节点选中事件

    }
};

    //重点就在于zNodes的配置。就是要配置好id、pId、name;这三个属性。
//这是用的for循环遍历的方式
/*var zNodes =[
    <#if selectNewsTypeAll?size gt 0>
     <#list selectNewsTypeAll as sNewsTypeAll>
{
    id:${sNewsTypeAll.id},
    pId:${sNewsTypeAll.parentId},
    name:"${sNewsTypeAll.name}"
},
</#list>
</#if>
];*/
//初始化,生成树形菜单
$(document).ready(function() {
    $.fn.zTree.init($("#treeConfig"), setting, eval(dates));
});
//点击事件
function onClick(e, treeId, treeNode) {

}

