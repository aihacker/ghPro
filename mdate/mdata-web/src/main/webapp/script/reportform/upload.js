//上传excel文件
function upload() {
    var formData = new FormData($("#file_form")[0]);
    $.ajax({
        Type: 'POST',
        url: 'query.json',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,

        success: function (result) {
            if (result.ok) {
                //var datas = result.data;
            alert(result.msg);
            }
            //  location.href = home+'/configModule/rightPane/taticConfig/new_config.html';
        },
        error: function (result) {
           alert(result.msg);
        }
    });
}

