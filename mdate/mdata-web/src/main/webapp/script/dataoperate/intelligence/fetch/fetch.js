/**
 * Created by Sheng on 2017/7/4.
 */
$(function(){

});
function drawTree(datas){
    var zNodes = datas.allTemplateDir;
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
                    sessionStorage.setItem("FetchClassId", treeNode.id);
                    sessionStorage.setItem("FetchPageSize", "");
                    $('#templateId').val('');
                    $('#fetchLogTable tbody').empty();
                    var url = _home+'/dataoperate/intelligence/fetch/query2.html';
                    $("#fetch_iframe").load(url);
                    getRemark(treeNode.id);
                    getTemplate(treeNode.id);
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#templateDirTree"), setting,zNodes);
}
function getTemplate(templateClassId,FetchTemplateId){
    $('#choiceTemplate').empty();
    pub.postJSON('query.json',{
        action:'getTemplatesByClassId',
            templateClassId:templateClassId
    },
        function(json){
            if(json.ok){
                for(var i =0 ; i < json.data.length ; i++){
                    var disabled ='';
                    var templateName = json.data[i].templateName;
                    if(json.data[i].status == 0){
                        disabled = 'disabled';
                        templateName += '(未生效)';
                    }
                    $('#choiceTemplate').append('<label class="col-sm-12"><input type="radio" name="template" value="'+ json.data[i].templateId +'" onclick="getFetch($(this))" '+disabled+'> '+ templateName + '</label>');
                }
                if(FetchTemplateId != '' && FetchTemplateId != undefined){
                    $('#choiceTemplate').find("input[type='radio'][value='"+ FetchTemplateId + "']").attr("checked","checked");
                }
            }
    });
}
function getFetch(e){
    sessionStorage.setItem("FetchTemplateId", e.val());
    $('#templateId').val(e.val());
    $('#fetchLogTable tbody').empty();
    var url = _home+'/dataoperate/intelligence/fetch/query2.html?templateId='+ e.val();
    $("#fetch_iframe").load(url);
}
function getRemark(templateClassId){
    pub.postJSON('query.json',{action:'getRemark',templateClassId:templateClassId},function(json){
        $('#remark').text(json.data.remark);
    });
}
function newFetch(){
    if($('#templateId').val() == undefined || $('#templateId').val() == ''){
        alert("请选择模板！");
        return;
    }

    sessionStorage.setItem("FetchPageNo", 1);
    window.location.href='operate.do?action=new_data_show&templateId=' + $('#templateId').val();
}
function executeInstance(id){
    var flag = 1;
    $.ajax({
        url:'operate.json?action=isExistsName',
        type:'POST',
        async:false,
        data:{
            instanceId:id
        },
        dataType:"json",
        success:function(json){
            if(json.ok){
            }else{
                alert(json.msg);
                flag = 0;
            }
        },error: function (json) {
            alert("出错了！");
            flag = 0;
        }
    });
    if(flag == 0){
        return;
    }
    $.ajax({
        url:'operate.json?action=executeInstance',
        type:'POST',
        data:{
            instanceId:id
        },
        dataType:"json",
        success:function(json){
            if(json.ok){
                //alert("保存成功！");
                $('#fetchLogTable tbody').empty();
                pub.postJSON('query.json',
                    {
                        action:'getInstanceLog',
                        instanceId:id
                    },function(json){
                        for(var i = 0; i< json.data.length; i++){
                            var status;
                            if(json.data[i].status == 0){
                                status = '未运行';
                            }else if(json.data[i].status == 1){
                                status = '运行成功';
                            }else if(json.data[i].status == 2){
                                status = '运行错误';
                            }
                            $('#fetchLogTable tbody').append('<tr>' +
                                '<td>' + json.data[i].id + '</td>' +
                                '<td>' + datetimeFormat(json.data[i].start_time) + '</td>' +
                                '<td>' + datetimeFormat(json.data[i].end_time) + '</td>' +
                                '<td>' + json.data[i].effect_count + '</td>' +
                                '<td>' + status + '</td>' +
                                '</tr>')
                        }
                    });
            }
        },error: function (json) {
            alert("出错了！");
        }
    });
    alert("正在取数中！");
}
function edit(id,pageNo){
    sessionStorage.setItem("FetchPageNo", pageNo);
    window.location.href='operate.do?action=edit&templateId=' + $('#templateId').val() + "&instanceId=" +id;
}
function delFetch(){
    if($('#fetchTable tbody').find('input:checked').length == 0){
        alert("请选择取数！");
        return;
    }
    var idList='';
    for(var i = 0;i < $('#fetchTable tbody').find('input:checked').length; i++){
        idList += $('#fetchTable tbody').find('input:checked').eq(i).val() + ',';
    }
    if(window.confirm('你确定要删除吗？')){
        pub.postJSON('operate.json',
            {
                action:'delInstance',
                instanceIds:idList
            }, function(json){
                alert("删除成功！");
                var url = _home+'/dataoperate/intelligence/fetch/query2.html?templateId='+ $('#templateId').val();
                $("#fetch_iframe").load(url);
                return true;
            });
    }else{
        return false;
    }
}