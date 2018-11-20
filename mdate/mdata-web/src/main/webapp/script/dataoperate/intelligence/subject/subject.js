/**
 * Created by sheng on 2017/6/9.
 */
var choiceDir;
var choiceId;
var clickId;
function drawTree(datas){
    var zNodes = datas.allSubjectDir;
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
                    sessionStorage.setItem("SubjectClassId", treeNode.id);
                    sessionStorage.setItem("SubjectQueryName", "");
                    sessionStorage.setItem("SubjectStatus", "");
                    sessionStorage.setItem("SubjectPageSize", "");
                    choiceDir=treeNode.name;
                    choiceId=treeNode.id;
                    clickId = treeNode.tId;
                    getRemark(treeNode.id);
                    getSubject(treeNode.id);
                }
            }
        },
        zTree;
    zTree = $.fn.zTree.init($("#subjectDirTree"), setting,zNodes);
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
        alert("请填写目录名！");
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
    if(choiceId == "" || choiceId == undefined){
        alert("请选择主题目录！！");
    }else{
        document.getElementById("modifyClassName").value=choiceDir;
        document.getElementById("modifySubject").setAttribute("data-toggle","modal");
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
        alert("请选择主题目录！！");
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

function getSubject(classId){
    var url = _home+'/dataoperate/intelligence/subject/query2.html?classId='+classId;
    $("#subject_iframe").load(url);
}

//function getSubject(classId){
//    $('#subjectTable tbody').html("");
//    pub.postJSON('query.json',{action:'getSubject',classId:classId,subjectName:'test'},function(json){
//        var html="";
//        for(var i=0;i<json.data.length;i++){
//            html += '<tr>' +
//                '<td><input type="checkbox" value="' + json.data[i].subject_id + '"></td>' +
//                '<td><a href="'+ "show.html?id=" + json.data[i].subject_id +'">'+ json.data[i].subject_name +'</a> </td>' +
//                '<td>'+ json.data[i].subject_class_id +'</td>' +
//                '<td>'+ json.data[i].remark +'</td>' +
//                '<td>'+ json.data[i].status +'</td>' +
//                '<td class="op">' +
//                '<a href="javascript:edit('+ json.data[i].subject_id + ');">'+ "编辑" +'</a>' +
//                '<a href="javascript:del('+ json.data[i].subject_id + ');">'+ "删除" +'</a>' +
//                '<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal_2">'+ "发布授权" +'</button> ' +
//                '<button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal_3">'+ "查看授权" +'</button> ' +
//                '</td>' +
//                '</tr>';
//        }
//        $('#subjectTable tbody').html(html);
//    });
//}

function querySubject(){
    if($("#classId").val() == "" || $("#classId").val() == undefined){
        alert("请选择主题目录！！");
    }else{
        sessionStorage.setItem("SubjectQueryName", $("#name").val());
        sessionStorage.setItem("SubjectStatus", $("#choiceStatus").val());
        var url = _home+'/dataoperate/intelligence/subject/query2.html?classId=' + $("#classId").val() + "&name=" + $("#name").val() + "&status=" + $("#choiceStatus").val();
        $("#subject_iframe").load(url);
    }
}
function new_(class_id,pageNo){
    if($("#classId").val() == "" || $("#classId").val() == undefined){
        alert("请选择主题目录！！");
    }
    else{
        if(pageNo == 0){
            pageNo = 1;
        }
        sessionStorage.setItem("SubjectPageNo", pageNo);
        window.location.href='operate.do?action=new_&class_id=' + class_id;
    }
}
function edit(subject_id,pageNo){
    if(choiceId == undefined || choiceId == ''){
        choiceId = $('#classId').val();
    }
    sessionStorage.setItem("SubjectPageNo", pageNo);
    window.location.href='operate.do?action=edit&subject_id=' + subject_id + "&choice_id=" +choiceId;
}
function del(subject_id,pageNo,pageSize){
    if(window.confirm('你确定要删除吗？')){
        pub.postJSON('operate.json',{
            action:'delete',
            subject_id:subject_id
        },function(json){
            if(json.ok){
                $("#subject_iframe").load(_home+'/dataoperate/intelligence/subject/query2.html?classId=' + $("#classId").val() + '&pageNo=' + pageNo + '&pageSize=' + pageSize);
                alert("删除成功！");
                return true;
            }else alert("该主题暂不可删除");
        });
    }else{
        return false;
    }

}
function resetQuery(){
    $("#name").val('');
    $("#choiceStatus").val('');
}
function publishingAuthorization(subject_id){
    $('#subjectId').val(subject_id);
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
    var list = [];
    for(var i=0;i<$('#publishingAuthorizationRoleTable tbody tr').length;i++){
        if($('#publishingAuthorizationRoleTable tbody tr').eq(i).find('input[type=checkbox]:checked').val() != undefined){
            var withGrantOption;
            if($('input[name=reAuthorization]:checked').val() != undefined){
                withGrantOption=1;
            }else withGrantOption=0;
            var obj = {
                subjectId: $('#subjectId').val(),
                roleId:$('#publishingAuthorizationRoleTable tbody tr').eq(i).find('input[type=checkbox]:checked').val(),
                withGrantOption: withGrantOption
            };
            list.push(obj);
        }
    }
    if(list == ""){
        alert("请选择角色！");
        return false
    }
    pub.postJSON('operate.json',{
        action:'saveAutho',
        jsonRoles:JSON.stringify(list)
    },function(json){
        if(json.ok){
            alert("授权成功！");
            $("#subject_iframe").load(_home+'/dataoperate/intelligence/subject/query2.html?classId=' + $("#classId").val()  + "&pageNo=" + pageNo );
        }else{
            alert("授权失败！");
        }
    });
    $('#savePublishingAuthorizationBtn').attr('data-dismiss','modal')
}
function viewAuthorization(subjectId){
    $('#viewAuthorizationRoleTable tbody').html("");
    pub.postJSON('operate.json',{
        action:'getRoleNameByAytho',
        subjectId:subjectId
    },function(json){
        if(json.ok){
            var html="";
            for(var i=0;i<json.data.length;i++){
                html += '<tr><td>' + json.data[i] + '</td></tr>'
            }
            $('#viewAuthorizationRoleTable tbody').html(html);
        }
    });
}