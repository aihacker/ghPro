/**
 * Created by sheng on 2017/6/9.
 */
var choiceDir;
var choiceId;
var clickId;
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
                    sessionStorage.setItem("TemplateClassId", treeNode.id);
                    sessionStorage.setItem("TemplateQueryTemplateName", "");
                    sessionStorage.setItem("TemplateQuerySubjectName", "");
                    sessionStorage.setItem("TemlateSize", "");
                    choiceDir=treeNode.name;
                    choiceId=treeNode.id;
                    clickId = treeNode.tId;
                    getRemark(treeNode.id);
                    getTemplate(treeNode.id);
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#templateDirTree"), setting,zNodes);
}
function addDir(){
    if(choiceDir == undefined){
        choiceDir = '';
    }
    document.getElementById("createDirParent").setAttribute("placeholder",choiceDir);
    document.getElementById("parentClassId").value=choiceId;
}
function saveAddDir(){
    if($('#addClassName').val() == ''){
        alert("请填写目录！");
        return;
    }
    var data = {
        action:'addDir',
        className:$('#addClassName').val(),
        parentClassId:choiceId,
        remark:$('#addRemark').val()
    };
    pub.postJSON('operate.json',data,function(json){
        if (json.ok) {
            alert('保存成功');
            window.location.reload();
        }
    });
}
function modifyModal(){
    if(choiceId == "" || choiceId ==undefined){
        alert("请选择模板目录！！");
    }else{
        document.getElementById("modifyClassName").value=choiceDir;
        document.getElementById("modifyTemplate").setAttribute("data-toggle","modal");
        document.getElementById("modifyClassId").value=choiceId;
    }
}
function saveModifyDir(){
    if($('#modifyClassName').val() == ''){
        alert("请填写目录名！");
        return;
    }
    var data = {
        action:'modifyDir',
        classId:choiceId,
        className:$('#modifyClassName').val(),
        remark:$('#modifyRemark').val()
    };
    pub.postJSON('operate.json',data,function(json){
        if (json.ok) {
            alert('保存成功');
            window.location.reload();
        }
    });
}
function delDir(){
    if(choiceId == "" || choiceId == undefined){
        alert("请选择模板目录！！");
    }else{
        if (window.confirm('确定删除吗?')) {
            pub.postJSON('operate.json',{action:'delDir',classId:choiceId},function(json){
                if(json.ok){
                    //$('#'+clickId).remove();
                    alert("删除成功！");
                    window.location.reload();
                }else{
                    alert(json.msg);
                }
            });
        }
    }
}
function getRemark(classId){
    pub.postJSON('operate.json',{action:'getRemark',classId:classId},function(json){
        $('#remark').text(json.data.remark);
        $('#modifyRemark').val(json.data.remark);
        $('#modifyClassName').val(json.data.className);
    });
}
function getTemplate(classId){
    var url = _home+'/dataoperate/intelligence/template/query2.html?classId='+classId;
    $("#template_iframe").load(url);
}
function queryTemplate(){
    if($("#classId").val() == "" || $("#classId").val() ==undefined){
        alert("请选择模板目录！！");
    }else{
        sessionStorage.setItem("TemplateQueryTemplateName", $("#templateName").val());
        sessionStorage.setItem("TemplateQuerySubjectName", $("#subjectName").val());
        var url = _home+'/dataoperate/intelligence/template/query2.html?classId=' + $("#classId").val() + "&tempName=" + $("#templateName").val() + "&subjectName=" + $("#subjectName").val();
        $("#template_iframe").load(url);
    }
}
function new_(class_id,pageNo){
    if($("#classId").val() == "" || $("#classId").val() == undefined){
        alert("请选择模板目录！！");
    }
    else{
        if(pageNo == 0){
            pageNo = 1;
        }
        sessionStorage.setItem("TemplatePageNo", pageNo);
        window.location.href='operate.do?action=new_&class_id=' + class_id;
    }
}
function edit(template_id,pageNo){
    if(choiceId == undefined || choiceId == ''){
        choiceId = $('#classId').val();
    }
    sessionStorage.setItem("TemplatePageNo", pageNo);
    window.location.href='operate.do?action=edit&template_id=' + template_id + "&choice_id=" +choiceId;
}
function del(template_id,pageNo,pageSize){
    //需要传this、del(this) del(a)
    //$('#tBody tr').eq($(a).closest('tr')[0].rowIndex - 1).remove();
    if(window.confirm('你确定要删除吗？')){
        if(template_id != undefined){
            pub.postJSON('operate.json',{
                action:'delete',
                template_id:template_id
            },function(json){
                if(json.ok){
                    alert("删除成功！");
                    $("#template_iframe").load(_home+'/dataoperate/intelligence/template/query2.html?classId='+$("#classId").val() + '&pageNo=' + pageNo + '&pageSize=' + pageSize);
                }else alert("该主题暂不可删除");
            });
        }
    }else{
        return false;
    }
}
function resetQuery(){
    $("#templateName").val('');
    $("#subjectName").val('');
}
function publishingAuthorization(template_id){
    $('#templateId').val(template_id);
    $('#publishingAuthorizationRoleTable tbody').html("");
    pub.postJSON('operate.json',{
        action:'getAllRoles'
    },function(json){
        if(json.ok){
            var html="";
            for(var i=0;i<json.data.length;i++){
                html += '<tr>' +
                    '<td><input type="checkbox" value=" ' + json.data[i].roleId + ' "/>' +
                    '<td>'+ json.data[i].roleName +'</td>' +
                    '</tr>'
            }
            $('#publishingAuthorizationRoleTable tbody').html(html);
        }
    });
}
function queryRoleName(){
    $('#publishingAuthorizationRoleTable tbody').html("");
    pub.postJSON('operate.json',{
        action:'selectRole',
        roleName:$('#roleName').val()
    },function(json){
        if(json.ok){
            var html="";
            for(var i=0;i<json.data.length;i++){
                html += '<tr>' +
                    '<td><input type="checkbox" value=" ' + json.data[i].roleId + ' "/>' +
                    '<td>'+ json.data[i].roleName +'</td>' +
                    '</tr>'
            }
            $('#publishingAuthorizationRoleTable tbody').html(html);
        }
    });

}
function resetRoleName(){
    $("#roleName").val('');
}

function savePublishingAuthorization(pageNo){
    $('#savePublishingAuthorizationBtn').removeAttr('data-dismiss');
    var list= [];
    for(var i=0;i<$('#publishingAuthorizationRoleTable tbody tr').length;i++){
        if($('#publishingAuthorizationRoleTable tbody tr').eq(i).find('input[type=checkbox]:checked').val() != undefined){
            var withGrantOption;
            if($('input[name=reAuthorization]:checked').val() != undefined){
                withGrantOption=1;
            }else withGrantOption=0;
            var obj = {
                templateId: $('#templateId').val(),
                roleId:$('#publishingAuthorizationRoleTable tbody tr').eq(i).find('input[type=checkbox]:checked').val(),
                withGrantOption: withGrantOption
            };
            list.push(obj);
        }
    }
    if(list == ''){
        alert("请选择角色名！");
        return;
    }
    pub.postJSON('operate.json',{
        action:'saveAutho',
        jsonRoles:JSON.stringify(list)
    },function(json){
        if(json.ok){
            alert("授权成功！");
            $("#template_iframe").load(_home+'/dataoperate/intelligence/template/query2.html?classId='+$("#classId").val() + "&pageNo=" + pageNo ) ;
        }else{
            alert("授权失败！");
        }
    });
    $('#savePublishingAuthorizationBtn').attr('data-dismiss','modal')
}
function shareTemplate(template_id){
    $('#templateId').val(template_id);
    $('#shareTable tbody').html('');
    pub.postJSON('operate.json',{
        action:'getUsers',
        templateId:$('#templateId').val()
    },function(json){
        if(json.ok){
            var html="";
            for(var i=0;i<json.data.length;i++){
                var orgName='';
                var isShare;
                if(json.data[i].isShare == 1){
                    isShare='是';
                }else if(json.data[i].isShare == 2){
                    isShare='否';
                }
                if(json.data[i].orgName != undefined){
                    orgName=json.data[i].orgName;
                }
                html += '<tr>' +
                    '<td><input type="checkbox" value="' + json.data[i].userId + '"/>' +
                    '<td>'+ json.data[i].userCode +'</td>' +
                    '<td>'+ json.data[i].userName +'</td>' +
                    '<td>'+ orgName +'</td>' +
                    '<td>'+ isShare +'</td>' +
                    '</tr>'
            }
            $('#shareTable tbody').html(html);
        }
    });

}
function queryUser(){
    $('#shareTable tbody').html('');
    pub.postJSON('operate.json',{
        action:'getUsersByName',
        templateId:$('#templateId').val(),
        name:$('#userName').val()
    },function(json){
        if(json.ok){
            var html="";
            for(var i=0;i<json.data.length;i++){
                var orgName='';
                var isShare;
                if(json.data[i].isShare == 1){
                    isShare='是';
                }else if(json.data[i].isShare == 2){
                    isShare='否';
                }
                if(json.data[i].orgName != undefined){
                    orgName=json.data[i].orgName;
                }
                html += '<tr>' +
                    '<td><input type="checkbox" value="' + json.data[i].userId + '"/>' +
                    '<td>'+ json.data[i].userCode +'</td>' +
                    '<td>'+ json.data[i].userName +'</td>' +
                    '<td>'+ orgName +'</td>' +
                    '<td>'+ isShare +'</td>' +
                    '</tr>'
            }
            $('#shareTable tbody').html(html);
        }
    });
}
function resetShareTemplate(){
    $('#userName').val('');
}
function saveShare(){
    $('#saveShareBtn').removeAttr('data-dismiss');
    var str='';
    for(var i=0;i<$('#shareTable tbody tr').length;i++){
        if($('#shareTable tbody tr').eq(i).find('input[type=checkbox]:checked').val() != undefined)
            str += $('#shareTable tbody tr').eq(i).find('input[type=checkbox]:checked').val() + ',';
    }
    if(str == ''){
        alert("请选择分享用户名！");
        return;
    }
    pub.postJSON('operate.json',{
        action:'shareTemplate',
        templateId:$('#templateId').val(),
        str:str
    },function(json){
        if(json.ok){
            alert("分享成功！");
        }else{
            alert("分享失败！");
        }
    });
    $('#saveShareBtn').attr('data-dismiss','modal');
}
