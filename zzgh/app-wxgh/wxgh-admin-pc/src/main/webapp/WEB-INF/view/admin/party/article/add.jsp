<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row" style="margin: 10px;">
    <form>
        <div class="form-group">
            <label>文章标题：</label>
            <input type="text" class="form-control" id="add-title">
            <br>
            <label>封面图片：</label>
            <input type="file" accept="image/*" name="addMyimg" id="addMyimg">

            <p id="p-img-add" style="height: 100px;margin-top:10px;text-align: center;">
                <img id="myimg-add" style="height: 100%;">
            </p>

            <label>选择分类：</label>
            <select id="type">
                <option value="1" class="op-1">中央精神</option>
                <option value="2" class="op-2">主题教育</option>
                <option value="3" class="op-3">支部简讯</option>
                <option value="4" class="op-4">企业党建</option>
                <option value="5" class="op-5">党员学习</option>
                <option value="6" class="op-6">党史理论</option>
            </select>
            <br>
            <label>文章介绍：</label>
            <textarea class="form-control" id="add-brief-info"></textarea>
            <br>
            <label>文章代码内容：</label>
            <div style="margin-top: 20px;">
                <script type="text/plain" id="myEditor" style="width:100%;height:400px;margin: auto"></script>
            </div>
        </div>
        <div style="text-align: center;">
            <button id="addProductConfirm" type="button" class="btn btn-primary">确定</button>
            <button type="button" class="btn btn-default">取消</button>
        </div>
    </form>
</div>

<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<jsp:include page="/comm/admin/umeditor.jsp"/>

<script>
    $(function () {
        //实例化编辑器
        window.um = UM.getEditor('myEditor',{
            /* 传入配置参数,可配参数列表看umeditor.config.js */
            toolbar: [
                'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
                'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
                '| justifyleft justifycenter justifyright justifyjustify |',
                'link unlink | emotion image video ',
                '| horizontal print preview fullscreen', 'drafts', 'formula'
            ]
        });

        $("input[name=addMyimg]").change(function () {
            var src = getObjectURL(this.files[0]); //获取路径
            $(this).siblings("p").children("img").attr("src", src);
        });


        $("#addProductConfirm").click(function () {
            var title = $("#add-title").val();
            var briefInfo = $("#add-brief-info").val();
            var content = um.getContent();
            content = encodeURI(content);
            var type = $("#type").val();
            if (!title) {
                ui.alert("标题不能为空");
                return;
            }
            if (!$("#addMyimg").val()) {
                ui.alert("封面图片不能为空");
                return;
            }

            $.ajaxFileUpload({
                url: 'party/article/api/add_data.json',
                dataType: 'json',
                secureuri: false,
                data: {
                    title: title,
                    briefInfo: briefInfo,
                    content: content,
                    type: type
                },
                fileElementId: 'addMyimg',
                success: function () {
                    ui.alert("添加成功",function(){
                        ui.history.go('party/article/list');
                    });
                }
            });

        })


        function getObjectURL(file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
    })
</script>

