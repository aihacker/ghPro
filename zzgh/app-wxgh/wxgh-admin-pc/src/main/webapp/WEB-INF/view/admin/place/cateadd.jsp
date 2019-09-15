<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/15
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    td > img {
        width: 25px;
        height: 25px;
        margin-right: 2px;
    }
</style>
        <div class="tab-pane" id="addPane">
            <div style="margin-left: 20%">
                <a href="#mainPane" data-toggle="tab" class="btn btn-empty-theme"><span
                        class="fa fa-chevron-circle-left"></span> 返回</a>
            </div>
            <form class="col-md-4 col-md-offset-4" style="margin-top: 20px;">
                <div class="form-group" style="margin-top: 20px;">
                    <label>名称
                        <small>（必填）</small>
                    </label>
                    <input name="name" type="text" class="form-control"/>
                </div>
                <input type="hidden" name="id">
                <div class="form-group">
                    <label>简介</label>
                    <textarea name="info" class="form-control" rows="2" maxlength="100"></textarea>
                    <small class="pull-right"></small>
                </div>
                <div class="form-group">
                    <label>状态</label>
                    <select name="status" class="form-control">
                        <option value="1">可用</option>
                        <option value="0">不可用</option>
                    </select>
                </div>

                <div class="ui-file-div">
                    <div id="coverImg"></div>
                </div>

                <div class="form-group">
                    <%--<button id="editSaveContinueBtn" type="button" class="btn btn-theme">保存继续添加</button>--%>
                    <button id="editSaveBtn" type="button" class="btn btn-default">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $(function() {
        var __self = "place/api/"

        var upload = ui.uploader("#coverImg")

        var $addFrom = $('#addPane form')
        $addFrom.form()

        $("#editSaveBtn").click(function () {
            var info = $addFrom.serializeJson()
            var verifyRes = verify(info)
            if (verifyRes) {
                ui.alert(verifyRes)
                return
            }

            var url = __self + "cateadd.json"
            if ($(".ui-file-item").length == 2) {
                upload.upload(function (fileIds) {
                    info.imgPath = fileIds.toString()
                    ui.post(url, info, function () {
                        ui.alert('添加成功！', function () {
                            ui.history.go('place/cate')
                        })
                    })
                })
            }
        })

        /**
         * 表单验证
         * @param info
         * @returns {*}
         */
        function verify(info) {
            if (!info['name']) {
                return '请输入项目名称哦'
            }
        }

        //back
        $("a[href='#mainPane']").on('show.bs.tab', function () {
            ui.history.go("place/cate")
        })
    })

</script>


