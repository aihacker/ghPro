/**
 * Created by Administrator on 2017-05-14.
 */
function drawTree(datas){
    var zNodes = datas.allBasOrg;
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
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#treeSelect"), setting,zNodes);
}
