/**
 * Created by Administrator on 2017-05-15.
 */
$(document).ready(function () {

    $("#loaduser").click(function () {
            loadUser();
            //
            //$("#loadschema").click(function() {
            //    var ip=$("#ip").val();
            //    var port=$("#port").val();
            //    var dbname=$("#dbname").val();
            //    var username=$("#username").val();
            //    var password=$("#password").val();
            //    var dbtype=$("#dbtype").val();
            //    if(ip==""||ip==null){
            //        alert("请填写ip地址");
            //        return;
            //    }
            //    if(port==""||port==null){
            //        alert("请填写端口");
            //        return;
            //    }
            //    if(dbname==""||dbname==null){
            //        alert("请填写数据库名")
            //        return;
            //    }
            //    if(username==""||username==null){
            //        alert("请填写连接用户");
            //        return;
            //    }
            //    if(password==""||password==null){
            //        alert("请填写密码");
            //        return;
            //    }
            //    if(dbtype==""||dbtype==null) {
            //        alert("请选择数据库类型");
            //        return;
            //    }
            //    $.ajax({
            //        dataType: 'json',
            //        url: 'show.json',
            //        data: {action: 'querySchema',ips:ip,port:port,dbname:dbname,username:username,password:password,dbtype:dbtype},
            //        traditional: true,
            //        success: function (result) {
            //            if (result.ok) {
            //                var html = "";
            //                var datas = result.data;
            //                for (var i = 0; i < datas.length; i++) {
            //                    html += '<div><input type="checkbox" name="schema" value="'+datas[i]+'"/>'+datas[i]+'</div>';
            //                }
            //                $("#selectschema").html(html);
            //            }
            //        },
            //        error: function () {
            //            alert('form submit error..')
            //        }
            //    });
            //});
        }
    );
});

function save() {

}

function loadUser() {
    var id = $("#id").val();
    var ip = $("#ip").val();
    var port = $("#port").val();
    var dbname = $("#dbname").val();
    var username = $("#username").val();
    var password = $("#password").val();
    var dbtype = $("#dbtype").val();
    if (ip == "" || ip == null) {
        alert("请填写ip地址");
        return;
    }
    if (port == "" || port == null) {
        alert("请填写端口");
        return;
    }
    if (dbname == "" || dbname == null) {
        alert("请填写数据库名");
        return;
    }
    if (username == "" || username == null) {
        alert("请填写连接用户");
        return;
    }
    if (password == "" || password == null) {
        alert("请填写密码");
        return;
    }
    if (dbtype == "" || dbtype == null) {
        alert("请选择数据库类型");
        return;
    }
    $.ajax({
        dataType: 'json',
        url: 'show.json',
        data: {
            action: 'queryUser',
            id: id,
            ips: ip,
            port: port,
            dbname: dbname,
            username: username,
            password: password,
            dbtype: dbtype
        },
        traditional: true,
        success: function (result) {
            if (result.ok) {
                var html = "<div>";
                var html2 = "<div>";
                var datas = result.data;
                var msg = result.msg;
                if (msg != null) {
                    alert(msg);
                    return;
                }
                //var users=datas['users'];
                var schemas = datas['schemas'];
                //for (var i = 0; i < users.length; i++) {
                //    html+='<span class="mr30"> <input type="checkbox" name="availableUser" value="'+users[i]+'"/>'+users[i]+'</span>'
                //    if((i+1)%4==0){
                //       html+='</div> <div>';
                //    }
                //}
                for (var i = 0; i < schemas.length; i++) {
                    html2 += '<span class="mr30"> <input type="checkbox" name="schemaName" value="' + schemas[i] + '"/>' + schemas[i] + '</span>';
                    if ((i + 1) % 4 == 0) {
                        html2 += '</div> <div>';
                    }
                }
                html2 += '</div>';
                //html+='</div>';
                //$("#selectuser").html(html);
                $("#selectschema").html(html2);
            }
        },
        error: function () {
            alert('form submit error..')
        }
    });
}
